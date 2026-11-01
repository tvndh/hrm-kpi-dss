package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.PhieuDanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuDanhGiaRepository extends JpaRepository<PhieuDanhGia, Integer> {
    List<PhieuDanhGia> findByIdKyDanhGia(Integer idKyDanhGia);
    List<PhieuDanhGia> findByIdNhanVien(Integer idNhanVien);
    Optional<PhieuDanhGia> findByIdKyDanhGiaAndIdNhanVien(Integer idKyDanhGia, Integer idNhanVien);
}

