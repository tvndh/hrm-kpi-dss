package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.DuBaoNghiViecAi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DuBaoNghiViecAiRepository extends JpaRepository<DuBaoNghiViecAi, Integer> {
    Optional<DuBaoNghiViecAi> findByIdNhanVien(Integer idNhanVien);
    List<DuBaoNghiViecAi> findByMucDoCanhBao(String mucDoCanhBao);
    List<DuBaoNghiViecAi> findAllByOrderByTyLeRuiRoDesc();
}

