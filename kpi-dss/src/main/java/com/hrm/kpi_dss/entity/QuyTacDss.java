package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "quy_tac_dss")
@Data
@NoArgsConstructor
public class QuyTacDss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quy_tac")
    private Integer idQuyTac;

    @Column(name = "diem_kpi_toi_thieu", nullable = false, precision = 5, scale = 2)
    private BigDecimal diemKpiToiThieu;

    @Column(name = "diem_nang_luc_toi_thieu", nullable = false, precision = 5, scale = 2)
    private BigDecimal diemNangLucToiThieu;

    @Column(name = "phan_loai_9_box", nullable = false, length = 50)
    private String phanLoai9Box;

    @Column(name = "de_xuat_quyet_sach", nullable = false, length = 255)
    private String deXuatQuyetSach;
}
