package com.hrm.kpi_dss.controller;

import com.hrm.kpi_dss.entity.*;
import com.hrm.kpi_dss.service.KpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kpi")
@CrossOrigin(origins = "*")
public class KpiController {

    private final KpiService kpiService;

    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    @GetMapping("/ky-danh-gia")
    public ResponseEntity<List<KyDanhGia>> getKyDanhGia() {
        return ResponseEntity.ok(kpiService.layTatCaKyDanhGia());
    }

    @GetMapping("/tieu-chi")
    public ResponseEntity<List<TieuChiKpi>> getTieuChiKpi() {
        return ResponseEntity.ok(kpiService.layTatCaTieuChiKpi());
    }

    @GetMapping("/khung-nang-luc/{idChucVu}")
    public ResponseEntity<List<KhungNangLuc>> getKhungNangLuc(@PathVariable Integer idChucVu) {
        return ResponseEntity.ok(kpiService.layKhungNangLucTheoChucVu(idChucVu));
    }

    @GetMapping("/phieu-danh-gia/nhan-vien/{idNhanVien}")
    public ResponseEntity<List<PhieuDanhGia>> getPhieuByEmployee(@PathVariable Integer idNhanVien) {
        return ResponseEntity.ok(kpiService.layPhieuDanhGiaTheoNhanVien(idNhanVien));
    }

    @GetMapping("/phieu-danh-gia/{idPhieuDanhGia}")
    public ResponseEntity<Map<String, Object>> getPhieuDetail(@PathVariable Integer idPhieuDanhGia) {
        return ResponseEntity.ok(kpiService.layChiTietPhieu(idPhieuDanhGia));
    }

    @PostMapping("/phieu-danh-gia/khoi-tao")
    public ResponseEntity<PhieuDanhGia> createPhieu(@RequestParam Integer idKyDanhGia,
                                                     @RequestParam Integer idNhanVien,
                                                     @RequestParam Integer idNguoiDanhGia) {
        PhieuDanhGia phieu = kpiService.taoPhieuDanhGia(idKyDanhGia, idNhanVien, idNguoiDanhGia);
        return ResponseEntity.ok(phieu);
    }
}
