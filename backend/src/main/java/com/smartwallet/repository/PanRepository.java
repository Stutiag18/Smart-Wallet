package com.smartwallet.repository;

import com.smartwallet.model.PanEntity;
import com.smartwallet.model.PanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanRepository extends JpaRepository<PanEntity, Long> {


    boolean existsByPanNumber(String panNumber);
}
