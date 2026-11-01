package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongBanRepository extends JpaRepository<PhongBan, Integer> {
}
