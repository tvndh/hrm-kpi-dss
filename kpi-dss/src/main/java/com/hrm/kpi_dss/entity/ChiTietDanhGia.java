package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "chi_tiet_danh_gia")
@Data
@NoArgsConstructor
public class ChiTietDanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chi_tiet")
    private Integer idChiTiet;

    @Column(name = "id_phieu_danh_gia", nullable = false)
    private Integer idPhieuDanhGia;

    @Column(name = "id_tieu_chi")
    private Integer idTieuChi;

    @Column(name = "id_nang_luc")
    private Integer idNangLuc;

    @Column(name = "trong_so", nullable = false, precision = 5, scale = 2)
    private BigDecimal trongSo = BigDecimal.ZERO;

    @Column(name = "chi_tieu_giao", precision = 10, scale = 2)
    private BigDecimal chiTieuGiao;

    @Column(name = "ket_qua_thuc_te", precision = 10, scale = 2)
    private BigDecimal ketQuaThucTe;

    @Column(name = "diem_tu_cham", precision = 5, scale = 2)
    private BigDecimal diemTuCham;

    @Column(name = "nhan_xet_tu_cham", length = 255)
    private String nhanXetTuCham;

    @Column(name = "diem_quan_ly_cham", precision = 5, scale = 2)
    private BigDecimal diemQuanLyCham;

    @Column(name = "nhan_xet_quan_ly", length = 255)
    private String nhanXetQuanLy;

    @Column(name = "diem_tong_ket", precision = 5, scale = 2)
    private BigDecimal diemTongKet;
}
