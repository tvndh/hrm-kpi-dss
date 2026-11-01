package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.KetQuaDss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KetQuaDssRepository extends JpaRepository<KetQuaDss, Integer> {
    Optional<KetQuaDss> findByIdPhieuDanhGia(Integer idPhieuDanhGia);
}

