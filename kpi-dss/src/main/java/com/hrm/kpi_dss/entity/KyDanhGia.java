package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "ky_danh_gia")
@Data
@NoArgsConstructor
public class KyDanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ky_danh_gia")
    private Integer idKyDanhGia;

    @Column(name = "ten_ky_danh_gia", nullable = false, length = 100)
    private String tenKyDanhGia;

    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "trang_thai", length = 30)
    private String trangThai = "ACTIVE";
}
