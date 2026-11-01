package com.hrm.kpi_dss.service;

import com.hrm.kpi_dss.entity.*;
import com.hrm.kpi_dss.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class KpiService {

    private final KyDanhGiaRepository kyDanhGiaRepository;
    private final TieuChiKpiRepository tieuChiKpiRepository;
    private final KhungNangLucRepository khungNangLucRepository;
    private final PhieuDanhGiaRepository phieuDanhGiaRepository;
    private final ChiTietDanhGiaRepository chiTietDanhGiaRepository;
    private final NhanVienRepository nhanVienRepository;

    public KpiService(KyDanhGiaRepository kyDanhGiaRepository,
                      TieuChiKpiRepository tieuChiKpiRepository,
                      KhungNangLucRepository khungNangLucRepository,
                      PhieuDanhGiaRepository phieuDanhGiaRepository,
                      ChiTietDanhGiaRepository chiTietDanhGiaRepository,
                      NhanVienRepository nhanVienRepository) {
        this.kyDanhGiaRepository = kyDanhGiaRepository;
        this.tieuChiKpiRepository = tieuChiKpiRepository;
        this.khungNangLucRepository = khungNangLucRepository;
        this.phieuDanhGiaRepository = phieuDanhGiaRepository;
        this.chiTietDanhGiaRepository = chiTietDanhGiaRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    public List<KyDanhGia> layTatCaKyDanhGia() {
        return kyDanhGiaRepository.findAll();
    }

    public List<TieuChiKpi> layTatCaTieuChiKpi() {
        return tieuChiKpiRepository.findAll();
    }

    public List<KhungNangLuc> layKhungNangLucTheoChucVu(Integer idChucVu) {
        return khungNangLucRepository.findByIdChucVu(idChucVu);
    }

    public List<PhieuDanhGia> layPhieuDanhGiaTheoNhanVien(Integer idNhanVien) {
        return phieuDanhGiaRepository.findByIdNhanVien(idNhanVien);
    }

    public Map<String, Object> layChiTietPhieu(Integer idPhieuDanhGia) {
        PhieuDanhGia phieu = phieuDanhGiaRepository.findById(idPhieuDanhGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu ID: " + idPhieuDanhGia));

        List<ChiTietDanhGia> chiTiets = chiTietDanhGiaRepository.findByIdPhieuDanhGia(idPhieuDanhGia);
        NhanVien nv = nhanVienRepository.findById(phieu.getIdNhanVien()).orElse(null);

        Map<String, Object> result = new HashMap<>();
        result.put("phieu", phieu);
        result.put("nhanVien", nv);
        result.put("danhSachTieuChi", chiTiets);
        return result;
    }

    /**
     * Khởi tạo phiếu đánh giá kèm theo bộ tiêu chí KPI & khung năng lực cho nhân viên
     */
    @Transactional
    public PhieuDanhGia taoPhieuDanhGia(Integer idKyDanhGia, Integer idNhanVien, Integer idNguoiDanhGia) {
        Optional<PhieuDanhGia> exist = phieuDanhGiaRepository.findByIdKyDanhGiaAndIdNhanVien(idKyDanhGia, idNhanVien);
        if (exist.isPresent()) {
            return exist.get();
        }

        NhanVien nv = nhanVienRepository.findById(idNhanVien)
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại ID: " + idNhanVien));

        PhieuDanhGia phieu = new PhieuDanhGia();
        phieu.setIdKyDanhGia(idKyDanhGia);
        phieu.setIdNhanVien(idNhanVien);
        phieu.setIdNguoiDanhGia(idNguoiDanhGia);
        phieu.setTrangThai("DRAFT");
        phieu.setNgayTao(LocalDateTime.now());
        PhieuDanhGia savedPhieu = phieuDanhGiaRepository.save(phieu);

        // Nạp tiêu chí KPI chung
        List<TieuChiKpi> kpis = tieuChiKpiRepository.findAll();
        for (TieuChiKpi k : kpis) {
            ChiTietDanhGia ct = new ChiTietDanhGia();
            ct.setIdPhieuDanhGia(savedPhieu.getIdPhieuDanhGia());
            ct.setIdTieuChi(k.getIdTieuChi());
            ct.setTrongSo(BigDecimal.valueOf(1.0));
            ct.setChiTieuGiao(k.getChiTieuMucTieu());
            chiTietDanhGiaRepository.save(ct);
        }

        // Nạp khung năng lực theo chức vụ của nhân viên
        List<KhungNangLuc> nangLucs = khungNangLucRepository.findByIdChucVu(nv.getIdChucVu());
        for (KhungNangLuc nl : nangLucs) {
            ChiTietDanhGia ct = new ChiTietDanhGia();
            ct.setIdPhieuDanhGia(savedPhieu.getIdPhieuDanhGia());
            ct.setIdNangLuc(nl.getIdNangLuc());
            ct.setTrongSo(BigDecimal.valueOf(1.0));
            ct.setChiTieuGiao(nl.getDiemChuan());
            chiTietDanhGiaRepository.save(ct);
        }

        return savedPhieu;
    }
}
