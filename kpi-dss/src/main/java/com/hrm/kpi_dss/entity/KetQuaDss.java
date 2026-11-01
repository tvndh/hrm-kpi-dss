package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ket_qua_dss")
@Data
@NoArgsConstructor
public class KetQuaDss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ket_qua")
    private Integer idKetQua;

    @Column(name = "id_phieu_danh_gia", nullable = false)
    private Integer idPhieuDanhGia;

    @Column(name = "tong_diem_kpi", nullable = false, precision = 5, scale = 2)
    private BigDecimal tongDiemKpi;

    @Column(name = "tong_diem_nang_luc", nullable = false, precision = 5, scale = 2)
    private BigDecimal tongDiemNangLuc;

    @Column(name = "diem_danh_gia_chung", nullable = false, precision = 5, scale = 2)
    private BigDecimal diemDanhGiaChung;

    @Column(name = "vi_tri_9_box", nullable = false, length = 50)
    private String viTri9Box;

    @Column(name = "de_xuat", nullable = false, length = 255)
    private String deXuat;

    @Column(name = "ngay_tinh_toan")
    private LocalDateTime ngayTinhToan = LocalDateTime.now();
}

