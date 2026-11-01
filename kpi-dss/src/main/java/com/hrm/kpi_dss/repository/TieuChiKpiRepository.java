package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.TieuChiKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TieuChiKpiRepository extends JpaRepository<TieuChiKpi, Integer> {
    List<TieuChiKpi> findByIdNhomTieuChi(Integer idNhomTieuChi);
}

