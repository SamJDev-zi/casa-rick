package com.casarick.api.reposiroty;

import com.casarick.api.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
