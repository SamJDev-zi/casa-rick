package com.casarick.api.reposiroty;

import com.casarick.api.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
