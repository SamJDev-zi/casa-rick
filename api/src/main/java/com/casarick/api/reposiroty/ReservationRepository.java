package com.casarick.api.reposiroty;

import com.casarick.api.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r JOIN FETCH r.inventoryList")
    List<Reservation> findAllWithInventory();
}
