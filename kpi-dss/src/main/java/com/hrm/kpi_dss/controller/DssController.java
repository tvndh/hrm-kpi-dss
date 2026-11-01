package com.hrm.kpi_dss.controller;

import com.hrm.kpi_dss.entity.KetQuaDss;
import com.hrm.kpi_dss.service.DssService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dss")
@CrossOrigin(origins = "*")
public class DssController {

    private final DssService dssService;

    public DssController(DssService dssService) {
        this.dssService = dssService;
    }

    /**
     * API Lấy thống kê phân bố nhân sự theo ma trận 9-Box (Phục vụ biểu đồ)
     */
    @GetMapping("/matrix")
    public ResponseEntity<Map<String, Object>> get9BoxMatrix() {
        return ResponseEntity.ok(dssService.layThongKeMaTran9Box());
    }

    /**
     * API Lấy kết quả DSS và đề xuất chính sách nhân tài của 1 nhân viên
     */
    @GetMapping("/nhan-vien/{idNhanVien}")
    public ResponseEntity<Map<String, Object>> getDssByEmployee(@PathVariable Integer idNhanVien) {
        return dssService.layKetQuaDssNhanVien(idNhanVien)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API Kích hoạt thuật toán DSS tính toán điểm và ra quyết định
     */
    @PostMapping("/tinh-toan/{idPhieuDanhGia}")
    public ResponseEntity<KetQuaDss> calculateDss(@PathVariable Integer idPhieuDanhGia) {
        KetQuaDss result = dssService.tinhToanVaRaQuyetDinhDss(idPhieuDanhGia);
        return ResponseEntity.ok(result);
    }
}
