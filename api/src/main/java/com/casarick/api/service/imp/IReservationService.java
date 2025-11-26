package com.casarick.api.service.imp;

import com.casarick.api.dto.ReservationDTO;
import com.casarick.api.model.Reservation;

import java.util.List;

public interface IReservationService {
    List<ReservationDTO> getReservations();
    ReservationDTO getReservationById(Long id);
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO);
    void deleteReservation(Long id);
}
