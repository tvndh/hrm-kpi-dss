package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "phong_ban")
@Data
@NoArgsConstructor
public class PhongBan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phong_ban")
    private Integer idPhongBan;

    @Column(name = "ten_phong_ban", nullable = false, length = 100)
    private String tenPhongBan;

    @Column(name = "id_truong_phong")
    private Integer idTruongPhong;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();
}
