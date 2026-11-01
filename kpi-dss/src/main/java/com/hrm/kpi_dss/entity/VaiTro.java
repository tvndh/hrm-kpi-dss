package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vai_tro")
@Data
@NoArgsConstructor
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vai_tro")
    private Integer idVaiTro;

    @Column(name = "ten_vai_tro", nullable = false, length = 50)
    private String tenVaiTro;

    @Column(name = "mo_ta", length = 255)
    private String moTa;
}
