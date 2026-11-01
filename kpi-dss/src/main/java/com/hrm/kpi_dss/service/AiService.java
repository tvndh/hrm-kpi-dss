package com.hrm.kpi_dss.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrm.kpi_dss.entity.DuBaoNghiViecAi;
import com.hrm.kpi_dss.entity.NhanVien;
import com.hrm.kpi_dss.repository.DuBaoNghiViecAiRepository;
import com.hrm.kpi_dss.repository.NhanVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AiService {

    private final DuBaoNghiViecAiRepository duBaoRepository;
    private final NhanVienRepository nhanVienRepository;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    private static final String AI_SERVICE_URL = "http://localhost:8000/predict";

    public AiService(DuBaoNghiViecAiRepository duBaoRepository,
                     NhanVienRepository nhanVienRepository,
                     ObjectMapper objectMapper) {
        this.duBaoRepository = duBaoRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
    }

    public List<Map<String, Object>> layDanhSachDuBaoRuiRo() {
        List<DuBaoNghiViecAi> duBaos = duBaoRepository.findAllByOrderByTyLeRuiRoDesc();
        List<Map<String, Object>> response = new ArrayList<>();

        for (DuBaoNghiViecAi db : duBaos) {
            Map<String, Object> item = new HashMap<>();
            item.put("idDuBao", db.getIdDuBao());
            item.put("idNhanVien", db.getIdNhanVien());
            item.put("tyLeRuiRo", db.getTyLeRuiRo());
            item.put("mucDoCanhBao", db.getMucDoCanhBao());
            item.put("nguyenNhanChinh", db.getNguyenNhanChinh());
            item.put("ngayDuBao", db.getNgayDuBao());

            Optional<NhanVien> nvOpt = nhanVienRepository.findById(db.getIdNhanVien());
            if (nvOpt.isPresent()) {
                NhanVien nv = nvOpt.get();
                item.put("hoTen", nv.getHoTen());
                item.put("email", nv.getEmail());
                item.put("tuoi", nv.getTuoi());
                item.put("khoangCachDiLam", nv.getKhoangCachDiLam());
                item.put("namTaiCongTy", nv.getNamTaiCongTy());
                item.put("namTuKhiThangChuc", nv.getNamTuKhiThangChuc());
                item.put("namVoiQuanLyHienTai", nv.getNamVoiQuanLyHienTai());
            }

            response.add(item);
        }
        return response;
    }

    public Optional<DuBaoNghiViecAi> layDuBaoTheoNhanVien(Integer idNhanVien) {
        return duBaoRepository.findByIdNhanVien(idNhanVien);
    }

    /**
     * Giao tiếp REST API với Python AI Microservice (Port 8000) để dự báo và cập nhật DB
     */
    @Transactional
    public DuBaoNghiViecAi goiAiDuBaoNhanVien(Integer idNhanVien) {
        NhanVien nv = nhanVienRepository.findById(idNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên ID: " + idNhanVien));

        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("id_nhan_vien", nv.getIdNhanVien());
            payload.put("age", nv.getTuoi() != null ? nv.getTuoi() : 30);
            payload.put("distance_from_home", nv.getKhoangCachDiLam() != null ? nv.getKhoangCachDiLam() : 10);
            payload.put("education", nv.getTrinhDoHocVan() != null ? nv.getTrinhDoHocVan() : 3);
            payload.put("total_working_years", nv.getTongNamKinhNghiem() != null ? nv.getTongNamKinhNghiem() : 5);
            payload.put("years_at_company", nv.getNamTaiCongTy() != null ? nv.getNamTaiCongTy() : 3);
            payload.put("years_in_current_role", nv.getNamCungChucVu() != null ? nv.getNamCungChucVu() : 2);
            payload.put("years_since_last_promotion", nv.getNamTuKhiThangChuc() != null ? nv.getNamTuKhiThangChuc() : 1);
            payload.put("years_with_curr_manager", nv.getNamVoiQuanLyHienTai() != null ? nv.getNamVoiQuanLyHienTai() : 2);
            payload.put("income_level", 3);

            String requestBody = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AI_SERVICE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(4))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                double riskPct = root.path("ty_le_rui_ro").asDouble();
                String warningLevel = root.path("muc_do_canh_bao").asText();
                String causes = root.path("nguyen_nhan_chinh").asText();

                DuBaoNghiViecAi duBao = duBaoRepository.findByIdNhanVien(idNhanVien)
                        .orElse(new DuBaoNghiViecAi());

                duBao.setIdNhanVien(idNhanVien);
                duBao.setTyLeRuiRo(BigDecimal.valueOf(riskPct));
                duBao.setMucDoCanhBao(warningLevel);
                duBao.setNguyenNhanChinh(causes);
                duBao.setNgayDuBao(LocalDateTime.now());

                return duBaoRepository.save(duBao);
            } else {
                throw new RuntimeException("Python AI Service trả về mã lỗi: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("[WARN] Không thể kết nối tới Python AI Service: " + e.getMessage());
            // Trả về bản ghi hiện tại nếu Python service đang tắt
            return duBaoRepository.findByIdNhanVien(idNhanVien).orElse(null);
        }
    }

    /**
     * Đồng bộ kết quả AI cho toàn bộ nhân sự
     */
    @Transactional
    public int dongBoDuBaoToanCongTy() {
        List<NhanVien> danhSach = nhanVienRepository.findAll();
        int count = 0;
        for (NhanVien nv : danhSach) {
            DuBaoNghiViecAi res = goiAiDuBaoNhanVien(nv.getIdNhanVien());
            if (res != null) count++;
        }
        return count;
    }
}
