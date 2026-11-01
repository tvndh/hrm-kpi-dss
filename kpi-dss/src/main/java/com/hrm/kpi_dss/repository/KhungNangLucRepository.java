package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.KhungNangLuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhungNangLucRepository extends JpaRepository<KhungNangLuc, Integer> {
    List<KhungNangLuc> findByIdChucVu(Integer idChucVu);
}
