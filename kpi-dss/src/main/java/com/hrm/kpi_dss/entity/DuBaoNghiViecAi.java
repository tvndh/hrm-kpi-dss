package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "du_bao_nghi_viec_ai")
@Data
@NoArgsConstructor
public class DuBaoNghiViecAi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_du_bao")
    private Integer idDuBao;

    @Column(name = "id_nhan_vien", nullable = false)
    private Integer idNhanVien;

    @Column(name = "ty_le_rui_ro", nullable = false, precision = 5, scale = 2)
    private BigDecimal tyLeRuiRo;

    @Column(name = "muc_do_canh_bao", nullable = false, length = 30)
    private String mucDoCanhBao; // Cao, Trung bình, Thấp

    @Column(name = "nguyen_nhan_chinh", length = 255)
    private String nguyenNhanChinh;

    @Column(name = "ngay_du_bao")
    private LocalDateTime ngayDuBao = LocalDateTime.now();
}
