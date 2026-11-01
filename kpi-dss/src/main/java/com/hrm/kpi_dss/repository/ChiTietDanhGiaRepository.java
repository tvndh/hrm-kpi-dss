package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.ChiTietDanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietDanhGiaRepository extends JpaRepository<ChiTietDanhGia, Integer> {
    List<ChiTietDanhGia> findByIdPhieuDanhGia(Integer idPhieuDanhGia);
}
