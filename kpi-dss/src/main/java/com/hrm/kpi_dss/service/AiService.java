package com.hrm.kpi_dss.service;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AiService {

    private final DuBaoNghiViecAiRepository duBaoRepository;
    private final NhanVienRepository nhanVienRepository;
    private final HttpClient httpClient;

    private static final String AI_SERVICE_URL = "http://localhost:8000/predict";

    public AiService(DuBaoNghiViecAiRepository duBaoRepository,
                     NhanVienRepository nhanVienRepository) {
        this.duBaoRepository = duBaoRepository;
        this.nhanVienRepository = nhanVienRepository;
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
            int age = nv.getTuoi() != null ? nv.getTuoi() : 30;
            int distance = nv.getKhoangCachDiLam() != null ? nv.getKhoangCachDiLam() : 10;
            int education = nv.getTrinhDoHocVan() != null ? nv.getTrinhDoHocVan() : 3;
            int totalExp = nv.getTongNamKinhNghiem() != null ? nv.getTongNamKinhNghiem() : 5;
            int yearsAtCompany = nv.getNamTaiCongTy() != null ? nv.getNamTaiCongTy() : 3;
            int yearsInRole = nv.getNamCungChucVu() != null ? nv.getNamCungChucVu() : 2;
            int yearsSincePromo = nv.getNamTuKhiThangChuc() != null ? nv.getNamTuKhiThangChuc() : 1;
            int yearsWithMgr = nv.getNamVoiQuanLyHienTai() != null ? nv.getNamVoiQuanLyHienTai() : 2;

            String requestBody = String.format(Locale.US,
                    "{\"id_nhan_vien\":%d,\"age\":%d,\"distance_from_home\":%d,\"education\":%d,\"total_working_years\":%d,\"years_at_company\":%d,\"years_in_current_role\":%d,\"years_since_last_promotion\":%d,\"years_with_curr_manager\":%d,\"income_level\":3}",
                    nv.getIdNhanVien(), age, distance, education, totalExp, yearsAtCompany, yearsInRole, yearsSincePromo, yearsWithMgr
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AI_SERVICE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(4))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                double riskPct = extractDoubleField(responseBody, "ty_le_rui_ro", 50.0);
                String warningLevel = extractStringField(responseBody, "muc_do_canh_bao", "MEDIUM");
                String causes = extractStringField(responseBody, "nguyen_nhan_chinh", "Chưa có thông tin");

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

    private double extractDoubleField(String json, String key, double defaultVal) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*([0-9.]+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1));
            } catch (Exception ignored) {}
        }
        return defaultVal;
    }

    private String extractStringField(String json, String key, String defaultVal) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return defaultVal;
    }
}
