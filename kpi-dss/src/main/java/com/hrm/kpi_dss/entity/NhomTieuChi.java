package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nhom_tieu_chi")
@Data
@NoArgsConstructor
public class NhomTieuChi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nhom_tieu_chi")
    private Integer idNhomTieuChi;

    @Column(name = "ten_nhom_tieu_chi", nullable = false, length = 100)
    private String tenNhomTieuChi;

    @Column(name = "mo_ta", length = 255)
    private String moTa;
}

