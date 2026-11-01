package com.hrm.kpi_dss.repository;

import com.hrm.kpi_dss.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    // Tự động generate câu lệnh SQL tìm nhân viên theo tài khoản hoặc email
    Optional<NhanVien> findByTenDangNhap(String tenDangNhap);
    Optional<NhanVien> findByEmail(String email);
}
