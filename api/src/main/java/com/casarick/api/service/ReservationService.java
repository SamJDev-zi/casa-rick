package com.casarick.api.service;

import com.casarick.api.dto.InventoryRequestDTO;
import com.casarick.api.dto.ReservationRequestDTO;
import com.casarick.api.dto.ReservationResponseDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.*;
import com.casarick.api.reposiroty.*;
import com.casarick.api.service.imp.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService implements IReservationService {

    private static final String STATUS_PENDING = "PENDIENTE";
    private static final String STATUS_CANCELED = "CANCELADA";
    private static final String STATUS_FINALIZED = "FINALIZADA";

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<ReservationResponseDTO> getReservations() {
        return reservationRepository.findAllWithInventory().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ReservationResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with ID: " + id));
        return Mapper.toDTO(reservation);
    }

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        if (reservationRequestDTO == null) {
            throw new IllegalArgumentException("Reservation data cannot be null.");
        }

        Customer customer = customerRepository.findById(reservationRequestDTO.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Employee employee = employeeRepository.findById(reservationRequestDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        Branch branch = branchRepository.findById(reservationRequestDTO.getBranchId())
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        List<Inventory> inventoriesToUpdate = new ArrayList<>();
        List<Inventory> reservationInventories = new ArrayList<>();

        BigDecimal totalProductsValue = BigDecimal.ZERO;
        int totalQuantity = 0;

        if (reservationRequestDTO.getInventoryRequestDTOList() != null) {
            for (InventoryRequestDTO itemDTO : reservationRequestDTO.getInventoryRequestDTOList()) {

                Inventory dbInventory = inventoryRepository.findById(itemDTO.getId())
                        .orElseThrow(() -> new NotFoundException("Inventory item not found ID: " + itemDTO.getId()));

                int quantityToReserve = itemDTO.getStock();

                if (dbInventory.getStock() < quantityToReserve) {
                    throw new IllegalArgumentException("Insufficient stock to reserve Product: " +
                            dbInventory.getProduct().getName());
                }

                dbInventory.setStock(dbInventory.getStock() - quantityToReserve);
                inventoriesToUpdate.add(dbInventory);
                reservationInventories.add(dbInventory);

                BigDecimal itemTotal = dbInventory.getSalePrice().multiply(BigDecimal.valueOf(quantityToReserve));
                totalProductsValue = totalProductsValue.add(itemTotal);

                totalQuantity += quantityToReserve;
            }
        }

        inventoryRepository.saveAll(inventoriesToUpdate);

        BigDecimal deposit = reservationRequestDTO.getAmount() != null ? reservationRequestDTO.getAmount() : BigDecimal.ZERO;

        BigDecimal balanceDue = totalProductsValue.subtract(deposit);
        if (balanceDue.compareTo(BigDecimal.ZERO) < 0) balanceDue = BigDecimal.ZERO;

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expiration = reservationRequestDTO.getExpirationDate() != null ?
                reservationRequestDTO.getExpirationDate() : now.plusDays(7);

        Reservation reservation = Reservation.builder()
                .description(reservationRequestDTO.getDescription())
                .quantity(totalQuantity)
                .amount(deposit)
                .balance(balanceDue)
                .status(STATUS_PENDING)
                .created(now)
                .updated(now)
                .expirationDate(expiration)
                .customer(customer)
                .employee(employee)
                .branch(branch)
                .inventoryList(reservationInventories)
                .build();

        return Mapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (reservationRequestDTO.getStatus() != null) {
            String newStatus = reservationRequestDTO.getStatus().toUpperCase();

            if (newStatus.equals(STATUS_CANCELED) && !reservation.getStatus().equals(STATUS_CANCELED)) {
                returnReservedStock(reservation); // Llama al método auxiliar
            } else if (newStatus.equals(STATUS_FINALIZED) && reservation.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                throw new IllegalArgumentException("Cannot finalize reservation. Balance due: " + reservation.getBalance());
            }

            reservation.setStatus(newStatus);
        }

        if (reservationRequestDTO.getAmount() != null && reservationRequestDTO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal newPayment = reservationRequestDTO.getAmount();

            BigDecimal currentDeposit = reservation.getAmount() != null ? reservation.getAmount() : BigDecimal.ZERO;
            BigDecimal updatedDeposit = currentDeposit.add(newPayment);
            reservation.setAmount(updatedDeposit);

            BigDecimal currentBalance = reservation.getBalance() != null ? reservation.getBalance() : BigDecimal.ZERO;
            BigDecimal updatedBalance = currentBalance.subtract(newPayment);

            if (updatedBalance.compareTo(BigDecimal.ZERO) <= 0) {
                updatedBalance = BigDecimal.ZERO;

                if (!reservation.getStatus().equals(STATUS_FINALIZED)) {
                    reservation.setStatus(STATUS_FINALIZED);
                }
            }
            reservation.setBalance(updatedBalance);
        }

        if (reservationRequestDTO.getDescription() != null) {
            reservation.setDescription(reservationRequestDTO.getDescription());
        }
        reservation.setUpdated(LocalDateTime.now());

        return Mapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }

    private void returnReservedStock(Reservation reservation) {
        List<Inventory> inventoriesToReturn = reservation.getInventoryList();

        if (inventoriesToReturn != null && !inventoriesToReturn.isEmpty()) {
            int totalReservedQuantity = reservation.getQuantity();
            int averageQuantityPerItem = inventoriesToReturn.size() > 0 ? totalReservedQuantity / inventoriesToReturn.size() : 0;

            for (Inventory item : inventoriesToReturn) {
                int reservedQuantity = averageQuantityPerItem;
                Inventory currentInventory = inventoryRepository.findById(item.getId())
                        .orElse(null);
                if (currentInventory != null) {
                    currentInventory.setStock(currentInventory.getStock() + reservedQuantity);
                }
            }
            inventoryRepository.saveAll(inventoriesToReturn);
        }
    }
}
