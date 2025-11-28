package com.casarick.api.service.imp;

import com.casarick.api.dto.ReservationRequestDTO;
import com.casarick.api.dto.ReservationResponseDTO;

import java.util.List;

public interface IReservationService {
    List<ReservationResponseDTO> getReservations();
    ReservationResponseDTO getReservationById(Long id);
    ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO);
    ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO);
    void deleteReservation(Long id);
}
