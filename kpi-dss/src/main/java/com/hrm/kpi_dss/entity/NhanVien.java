package com.hrm.kpi_dss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "nhan_vien")
@Data
@NoArgsConstructor
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nhan_vien")
    private Integer idNhanVien;

    @Column(name = "ten_dang_nhap", nullable = false, unique = true, length = 50)
    private String tenDangNhap;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "gioi_tinh", nullable = false, length = 10)
    private String gioiTinh;

    @Column(name = "tuoi", nullable = false)
    private Integer tuoi;

    @Column(name = "tinh_trang_hon_nhan", length = 20)
    private String tinhTrangHonNhan;

    @Column(name = "trinh_do_hoc_van", nullable = false)
    private Integer trinhDoHocVan;

    @Column(name = "khoang_cach_di_lam")
    private Integer khoangCachDiLam;

    @Column(name = "id_phong_ban", nullable = false)
    private Integer idPhongBan;

    @Column(name = "id_chuc_vu", nullable = false)
    private Integer idChucVu;

    @Column(name = "id_vai_tro", nullable = false)
    private Integer idVaiTro;

    @Column(name = "ngay_vao_lam", nullable = false)
    private LocalDate ngayVaoLam;

    @Column(name = "tong_nam_kinh_nghiem", nullable = false)
    private Integer tongNamKinhNghiem = 0;

    @Column(name = "nam_tai_cong_ty", nullable = false)
    private Integer namTaiCongTy = 0;

    @Column(name = "nam_cung_chuc_vu", nullable = false)
    private Integer namCungChucVu = 0;

    @Column(name = "nam_tu_khi_thang_chuc", nullable = false)
    private Integer namTuKhiThangChuc = 0;

    @Column(name = "nam_voi_quan_ly_hien_tai", nullable = false)
    private Integer namVoiQuanLyHienTai = 0;

    @Column(name = "trang_thai_hoat_dong")
    private Boolean trangThaiHoatDong = true;
}