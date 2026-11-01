package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.KyDanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KyDanhGiaRepository extends JpaRepository<KyDanhGia, Integer> {
    List<KyDanhGia> findByTrangThai(String trangThai);
}
