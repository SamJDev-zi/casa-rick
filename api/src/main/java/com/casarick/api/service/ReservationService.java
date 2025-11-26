package com.casarick.api.service;

import com.casarick.api.dto.InventoryDTO;
import com.casarick.api.dto.ReservationDTO;
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
    public List<ReservationDTO> getReservations() {
        return List.of();
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        return null;
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        if (reservationDTO == null) {
            throw new IllegalArgumentException("Reservation data cannot be null.");
        }

        // 1. Validar y recuperar Relaciones
        Customer customer = customerRepository.findById(reservationDTO.getCustomerDTO().getId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Employee employee = employeeRepository.findById(reservationDTO.getEmployeeDTO().getId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        Branch branch = branchRepository.findById(reservationDTO.getBranchDTO().getId())
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        // 2. Procesar Inventario y Stock
        List<Inventory> inventoriesToUpdate = new ArrayList<>();
        List<Inventory> reservationInventories = new ArrayList<>();

        BigDecimal totalProductsValue = BigDecimal.ZERO;
        int totalQuantity = 0;

        if (reservationDTO.getInventoryDTOList() != null) {
            for (InventoryDTO itemDTO : reservationDTO.getInventoryDTOList()) {

                Inventory dbInventory = inventoryRepository.findById(itemDTO.getId())
                        .orElseThrow(() -> new NotFoundException("Inventory item not found ID: " + itemDTO.getId()));

                // Usamos el campo 'stock' del DTO como 'cantidad a reservar'
                int quantityToReserve = itemDTO.getStock();

                // Validar Stock Disponible
                if (dbInventory.getStock() < quantityToReserve) {
                    throw new IllegalArgumentException("Insufficient stock to reserve Product: " +
                            dbInventory.getProduct().getName());
                }

                // DESCONTAR STOCK (Se aparta el producto)
                dbInventory.setStock(dbInventory.getStock() - quantityToReserve);
                inventoriesToUpdate.add(dbInventory);
                reservationInventories.add(dbInventory);

                // Calcular valor total de los productos
                BigDecimal itemTotal = dbInventory.getSalePrice().multiply(BigDecimal.valueOf(quantityToReserve));
                totalProductsValue = totalProductsValue.add(itemTotal);

                totalQuantity += quantityToReserve;
            }
        }

        // 3. Guardar cambios de stock
        inventoryRepository.saveAll(inventoriesToUpdate);

        // 4. Cálculos Financieros de la Reserva
        // 'amount' en el DTO lo tomamos como el DEPÓSITO o ABONO inicial
        BigDecimal deposit = reservationDTO.getAmount() != null ? reservationDTO.getAmount() : BigDecimal.ZERO;

        // El 'balance' (saldo pendiente) es el Valor Total - Depósito
        BigDecimal balanceDue = totalProductsValue.subtract(deposit);
        if (balanceDue.compareTo(BigDecimal.ZERO) < 0) balanceDue = BigDecimal.ZERO;

        // 5. Definir Fechas
        LocalDateTime now = LocalDateTime.now();
        // Si no mandan fecha de expiración, por defecto damos 7 días (regla de negocio opcional)
        LocalDateTime expiration = reservationDTO.getExpirationDate() != null ?
                reservationDTO.getExpirationDate() : now.plusDays(7);

        // 6. Crear Entidad
        Reservation reservation = Reservation.builder()
                .description(reservationDTO.getDescription())
                .quantity(totalQuantity)
                .amount(deposit)          // Lo que el cliente pagó hoy
                .balance(balanceDue)      // Lo que debe
                .status(STATUS_PENDING)   // Estado inicial
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
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (reservationDTO.getStatus() != null) {
            String newStatus = reservationDTO.getStatus().toUpperCase();

            if (newStatus.equals(STATUS_CANCELED) && !reservation.getStatus().equals(STATUS_CANCELED)) {
                List<Inventory> itemsToReturn = reservation.getInventoryList();
            }

            reservation.setStatus(newStatus);
        }


        if (reservationDTO.getAmount() != null && reservationDTO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal newPayment = reservationDTO.getAmount();

            BigDecimal currentDeposit = reservation.getAmount() != null ? reservation.getAmount() : BigDecimal.ZERO;
            BigDecimal updatedDeposit = currentDeposit.add(newPayment);
            reservation.setAmount(updatedDeposit);

            BigDecimal currentBalance = reservation.getBalance() != null ? reservation.getBalance() : BigDecimal.ZERO;
            BigDecimal updatedBalance = currentBalance.subtract(newPayment);

            if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
                updatedBalance = BigDecimal.ZERO;
            }
            reservation.setBalance(updatedBalance);

            if (updatedBalance.compareTo(BigDecimal.ZERO) == 0 && !reservation.getStatus().equals("FINALIZADA")) {
                reservation.setStatus("FINALIZADA");
            }
        }

        reservation.setDescription(reservationDTO.getDescription());
        reservation.setUpdated(LocalDateTime.now());

        return Mapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }
}
