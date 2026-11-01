package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "chuc_vu")
@Data
@NoArgsConstructor
public class ChucVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chuc_vu")
    private Integer idChucVu;

    @Column(name = "ten_chuc_vu", nullable = false, length = 100)
    private String tenChucVu;

    @Column(name = "cap_bac", nullable = false)
    private Integer capBac;

    @Column(name = "luong_co_ban", nullable = false, precision = 18, scale = 2)
    private BigDecimal luongCoBan;
}

