package com.casarick.api.reposiroty;

import com.casarick.api.model.ClotheType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClotheTypeRepository extends JpaRepository<ClotheType, Long> {
    Optional<ClotheType> getClotheTypeByName(String name);
}
