package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.QuyTacDss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuyTacDssRepository extends JpaRepository<QuyTacDss, Integer> {
    List<QuyTacDss> findAllByOrderByDiemKpiToiThieuDescDiemNangLucToiThieuDesc();
}

