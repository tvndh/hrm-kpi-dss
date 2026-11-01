package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "tieu_chi_kpi")
@Data
@NoArgsConstructor
public class TieuChiKpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tieu_chi")
    private Integer idTieuChi;

    @Column(name = "id_nhom_tieu_chi", nullable = false)
    private Integer idNhomTieuChi;

    @Column(name = "ten_tieu_chi", nullable = false, length = 150)
    private String tenTieuChi;

    @Column(name = "don_vi_tinh", nullable = false, length = 30)
    private String donViTinh;

    @Column(name = "chi_tieu_muc_tieu", nullable = false, precision = 10, scale = 2)
    private BigDecimal chiTieuMucTieu;

    @Column(name = "mo_ta", length = 255)
    private String moTa;
}

