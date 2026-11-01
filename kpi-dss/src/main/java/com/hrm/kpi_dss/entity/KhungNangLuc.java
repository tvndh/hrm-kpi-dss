package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "khung_nang_luc")
@Data
@NoArgsConstructor
public class KhungNangLuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nang_luc")
    private Integer idNangLuc;

    @Column(name = "id_chuc_vu", nullable = false)
    private Integer idChucVu;

    @Column(name = "ten_nang_luc", nullable = false, length = 150)
    private String tenNangLuc;

    @Column(name = "loai_nang_luc", nullable = false, length = 50)
    private String loaiNangLuc; // Chuyên môn, Quản lý, Kỹ năng mềm

    @Column(name = "diem_chuan", nullable = false, precision = 5, scale = 2)
    private BigDecimal diemChuan;
}

