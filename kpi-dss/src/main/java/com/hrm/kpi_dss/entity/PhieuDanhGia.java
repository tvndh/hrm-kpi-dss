package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieu_danh_gia")
@Data
@NoArgsConstructor
public class PhieuDanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phieu_danh_gia")
    private Integer idPhieuDanhGia;

    @Column(name = "id_ky_danh_gia", nullable = false)
    private Integer idKyDanhGia;

    @Column(name = "id_nhan_vien", nullable = false)
    private Integer idNhanVien;

    @Column(name = "id_nguoi_danh_gia", nullable = false)
    private Integer idNguoiDanhGia;

    @Column(name = "trang_thai", length = 30)
    private String trangThai = "DRAFT"; // DRAFT, SELF_SUBMITTED, MANAGER_APPROVED, COMPLETED

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();
}

