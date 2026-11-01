package com.hrm.kpi_dss.controller;

import com.hrm.kpi_dss.entity.DuBaoNghiViecAi;
import com.hrm.kpi_dss.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/du-bao/danh-sach")
    public ResponseEntity<List<Map<String, Object>>> getDuBaoList() {
        return ResponseEntity.ok(aiService.layDanhSachDuBaoRuiRo());
    }

    @GetMapping("/du-bao/nhan-vien/{idNhanVien}")
    public ResponseEntity<DuBaoNghiViecAi> getDuBaoByEmployee(@PathVariable Integer idNhanVien) {
        return aiService.layDuBaoTheoNhanVien(idNhanVien)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/du-bao/kich-hoat/{idNhanVien}")
    public ResponseEntity<DuBaoNghiViecAi> triggerAiForEmployee(@PathVariable Integer idNhanVien) {
        DuBaoNghiViecAi result = aiService.goiAiDuBaoNhanVien(idNhanVien);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/du-bao/dong-bo-toan-bo")
    public ResponseEntity<Map<String, Object>> syncAllEmployees() {
        int synced = aiService.dongBoDuBaoToanCongTy();
        Map<String, Object> res = new HashMap<>();
        res.put("status", "SUCCESS");
        res.put("totalSynced", synced);
        res.put("message", "Đã đồng bộ dự báo nguy cơ nghỉ việc từ Python AI Service cho toàn bộ nhân sự!");
        return ResponseEntity.ok(res);
    }
}
