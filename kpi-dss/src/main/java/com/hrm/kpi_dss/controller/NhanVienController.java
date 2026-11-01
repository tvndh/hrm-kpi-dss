package com.hrm.kpi_dss.controller;

import com.hrm.kpi_dss.entity.NhanVien;
import com.hrm.kpi_dss.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhan-vien")
@CrossOrigin(origins = "*") // Mở CORS để sau này React Native/Web có thể gọi API
public class NhanVienController {

    private final NhanVienService nhanVienService;

    NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    // API lấy toàn bộ danh sách nhân viên
    @GetMapping("/danh-sach")
    public ResponseEntity<List<NhanVien>> getAllNhanVien() {
        List<NhanVien> danhSach = nhanVienService.layDanhSachNhanVien();
        return ResponseEntity.ok(danhSach);
    }

    // API lấy thông tin 1 nhân viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable Integer id) {
        return nhanVienService.layThongTinNhanVien(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
