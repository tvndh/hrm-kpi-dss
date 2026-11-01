package com.hrm.kpi_dss.service;

import com.hrm.kpi_dss.entity.*;
import com.hrm.kpi_dss.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DssService {

    private final PhieuDanhGiaRepository phieuDanhGiaRepository;
    private final ChiTietDanhGiaRepository chiTietDanhGiaRepository;
    private final KetQuaDssRepository ketQuaDssRepository;
    private final NhanVienRepository nhanVienRepository;

    public DssService(PhieuDanhGiaRepository phieuDanhGiaRepository,
                      ChiTietDanhGiaRepository chiTietDanhGiaRepository,
                      KetQuaDssRepository ketQuaDssRepository,
                      NhanVienRepository nhanVienRepository) {
        this.phieuDanhGiaRepository = phieuDanhGiaRepository;
        this.chiTietDanhGiaRepository = chiTietDanhGiaRepository;
        this.ketQuaDssRepository = ketQuaDssRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    /**
     * Kích hoạt Thuật toán Hỗ trợ Ra quyết định (DSS Engine) cho 1 Phiếu đánh giá
     */
    @Transactional
    public KetQuaDss tinhToanVaRaQuyetDinhDss(Integer idPhieuDanhGia) {
        PhieuDanhGia phieu = phieuDanhGiaRepository.findById(idPhieuDanhGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu đánh giá ID: " + idPhieuDanhGia));

        List<ChiTietDanhGia> chiTiets = chiTietDanhGiaRepository.findByIdPhieuDanhGia(idPhieuDanhGia);

        BigDecimal tongDiemKpi = BigDecimal.ZERO;
        BigDecimal tongTrongSoKpi = BigDecimal.ZERO;
        int countKpi = 0;

        BigDecimal tongDiemNangLuc = BigDecimal.ZERO;
        BigDecimal tongTrongSoNangLuc = BigDecimal.ZERO;
        int countNangLuc = 0;

        for (ChiTietDanhGia ct : chiTiets) {
            BigDecimal diem = ct.getDiemTongKet() != null ? ct.getDiemTongKet() :
                    (ct.getDiemQuanLyCham() != null ? ct.getDiemQuanLyCham() : ct.getDiemTuCham());

            if (diem == null) diem = BigDecimal.valueOf(3.0); // Mặc định trung bình nếu chưa chấm

            BigDecimal trongSo = ct.getTrongSo() != null && ct.getTrongSo().compareTo(BigDecimal.ZERO) > 0 ?
                    ct.getTrongSo() : BigDecimal.ONE;

            if (ct.getIdTieuChi() != null) {
                // Tiêu chí KPI
                tongDiemKpi = tongDiemKpi.add(diem.multiply(trongSo));
                tongTrongSoKpi = tongTrongSoKpi.add(trongSo);
                countKpi++;
            } else if (ct.getIdNangLuc() != null) {
                // Tiêu chuẩn Khung năng lực
                tongDiemNangLuc = tongDiemNangLuc.add(diem.multiply(trongSo));
                tongTrongSoNangLuc = tongTrongSoNangLuc.add(trongSo);
                countNangLuc++;
            }
        }

        BigDecimal diemKpiFinal = countKpi > 0 && tongTrongSoKpi.compareTo(BigDecimal.ZERO) > 0 ?
                tongDiemKpi.divide(tongTrongSoKpi, 2, RoundingMode.HALF_UP) : BigDecimal.valueOf(3.0);

        BigDecimal diemNangLucFinal = countNangLuc > 0 && tongTrongSoNangLuc.compareTo(BigDecimal.ZERO) > 0 ?
                tongDiemNangLuc.divide(tongTrongSoNangLuc, 2, RoundingMode.HALF_UP) : BigDecimal.valueOf(3.0);

        // Điểm chung: 60% KPI + 40% Năng lực
        BigDecimal diemChung = diemKpiFinal.multiply(BigDecimal.valueOf(0.6))
                .add(diemNangLucFinal.multiply(BigDecimal.valueOf(0.4)))
                .setScale(2, RoundingMode.HALF_UP);

        // Xác định phân loại 9-Box và Quyết sách gợi ý
        Map<String, String> decision = xacDinhViTri9BoxVaDeXuat(diemKpiFinal, diemNangLucFinal);
        String viTri9Box = decision.get("viTri9Box");
        String deXuat = decision.get("deXuat");

        // Lưu hoặc cập nhật vào KetQuaDss
        KetQuaDss ketQua = ketQuaDssRepository.findByIdPhieuDanhGia(idPhieuDanhGia)
                .orElse(new KetQuaDss());

        ketQua.setIdPhieuDanhGia(idPhieuDanhGia);
        ketQua.setTongDiemKpi(diemKpiFinal);
        ketQua.setTongDiemNangLuc(diemNangLucFinal);
        ketQua.setDiemDanhGiaChung(diemChung);
        ketQua.setViTri9Box(viTri9Box);
        ketQua.setDeXuat(deXuat);
        ketQua.setNgayTinhToan(LocalDateTime.now());

        KetQuaDss saved = ketQuaDssRepository.save(ketQua);

        // Cập nhật trạng thái phiếu hoàn tất
        phieu.setTrangThai("COMPLETED");
        phieuDanhGiaRepository.save(phieu);

        return saved;
    }

    /**
     * Thuật toán phân loại Ma trận 9-Box chuẩn quốc tế
     * Trục X (Hiệu suất KPI): Thấp (<3.0), Trung bình (3.0 - 3.9), Cao (>=4.0)
     * Trục Y (Khung Năng lực): Thấp (<3.0), Trung bình (3.0 - 3.9), Cao (>=4.0)
     */
    public Map<String, String> xacDinhViTri9BoxVaDeXuat(BigDecimal kpi, BigDecimal nangLuc) {
        double k = kpi.doubleValue();
        double n = nangLuc.doubleValue();

        String viTri;
        String deXuat;

        if (k >= 4.0 && n >= 4.0) {
            viTri = "Star (Ngôi sao)";
            deXuat = "Quy hoạch cán bộ nguồn cấp tốc, đề xuất thăng chức và tăng 15-20% lương cơ bản.";
        } else if (k >= 4.0 && n >= 3.0) {
            viTri = "High Performer (Chuyên gia hiệu suất)";
            deXuat = "Khen thưởng thành tích xuất sắc, bồi dưỡng thêm kỹ năng quản lý và lãnh đạo.";
        } else if (k >= 4.0 && n < 3.0) {
            viTri = "Solid Professional (Chuyên môn vững chắc)";
            deXuat = "Duy trì vị trí chuyên môn hiện tại, kèm cặp nâng cao các kỹ năng mềm còn hạn chế.";
        } else if (k >= 3.0 && n >= 4.0) {
            viTri = "High Potential (Tiềm năng cao)";
            deXuat = "Giao thêm thử thách và dự án quan trọng để khai phóng toàn diện năng lực.";
        } else if (k >= 3.0 && n >= 3.0) {
            viTri = "Core Player (Trụ cột công ty)";
            deXuat = "Giữ vững vị trí nòng cốt, áp dụng chính sách phúc lợi và khen thưởng quý đều đặn.";
        } else if (k >= 3.0 && n < 3.0) {
            viTri = "Effective (Nhân viên trung bình)";
            deXuat = "Bổ sung các khóa đào tạo nâng cao năng lực, theo dõi sát tiến độ hoàn thành chỉ tiêu.";
        } else if (k < 3.0 && n >= 4.0) {
            viTri = "Enigma (Chưa phát huy)";
            deXuat = "Phỏng vấn tìm hiểu trở ngại hiệu suất, xem xét chuyển giao công việc hoặc phòng ban phù hợp hơn.";
        } else if (k < 3.0 && n >= 3.0) {
            viTri = "Dilemma (Cần cải thiện)";
            deXuat = "Thiết lập kế hoạch cải thiện hiệu suất công việc (PIP) thời hạn 3 tháng.";
        } else {
            viTri = "Risk (Nguy cơ đào thải)";
            deXuat = "Cảnh báo nghiêm túc về hiệu suất; xem xét tinh giản hoặc sắp xếp công việc phù hợp hơn.";
        }

        Map<String, String> result = new HashMap<>();
        result.put("viTri9Box", viTri);
        result.put("deXuat", deXuat);
        return result;
    }

    /**
     * Lấy dữ liệu phân bố toàn diện ma trận 9-Box phục vụ vẽ biểu đồ trên Dashboard
     */
    public Map<String, Object> layThongKeMaTran9Box() {
        List<KetQuaDss> allResults = ketQuaDssRepository.findAll();
        Map<String, List<Map<String, Object>>> boxGroups = new LinkedHashMap<>();

        String[] boxNames = {
                "Star (Ngôi sao)", "High Performer (Chuyên gia hiệu suất)", "Solid Professional (Chuyên môn vững chắc)",
                "High Potential (Tiềm năng cao)", "Core Player (Trụ cột công ty)", "Effective (Nhân viên trung bình)",
                "Enigma (Chưa phát huy)", "Dilemma (Cần cải thiện)", "Risk (Nguy cơ đào thải)"
        };

        for (String box : boxNames) {
            boxGroups.put(box, new ArrayList<>());
        }

        for (KetQuaDss kq : allResults) {
            Optional<PhieuDanhGia> phieuOpt = phieuDanhGiaRepository.findById(kq.getIdPhieuDanhGia());
            if (phieuOpt.isPresent()) {
                PhieuDanhGia phieu = phieuOpt.get();
                Optional<NhanVien> nvOpt = nhanVienRepository.findById(phieu.getIdNhanVien());
                if (nvOpt.isPresent()) {
                    NhanVien nv = nvOpt.get();
                    Map<String, Object> empData = new HashMap<>();
                    empData.put("idNhanVien", nv.getIdNhanVien());
                    empData.put("hoTen", nv.getHoTen());
                    empData.put("email", nv.getEmail());
                    empData.put("diemKpi", kq.getTongDiemKpi());
                    empData.put("diemNangLuc", kq.getTongDiemNangLuc());
                    empData.put("diemChung", kq.getDiemDanhGiaChung());
                    empData.put("deXuat", kq.getDeXuat());

                    String viTri = kq.getViTri9Box();
                    if (boxGroups.containsKey(viTri)) {
                        boxGroups.get(viTri).add(empData);
                    }
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalAssessed", allResults.size());
        response.put("matrix", boxGroups);
        return response;
    }

    /**
     * Lấy kết quả DSS theo ID nhân viên
     */
    public Optional<Map<String, Object>> layKetQuaDssNhanVien(Integer idNhanVien) {
        List<PhieuDanhGia> phieus = phieuDanhGiaRepository.findByIdNhanVien(idNhanVien);
        if (phieus.isEmpty()) return Optional.empty();

        // Lấy phiếu mới nhất
        PhieuDanhGia phieuMoiNhat = phieus.get(phieus.size() - 1);
        Optional<KetQuaDss> kqOpt = ketQuaDssRepository.findByIdPhieuDanhGia(phieuMoiNhat.getIdPhieuDanhGia());
        if (kqOpt.isEmpty()) return Optional.empty();

        KetQuaDss kq = kqOpt.get();
        NhanVien nv = nhanVienRepository.findById(idNhanVien).orElse(null);

        Map<String, Object> res = new HashMap<>();
        res.put("idNhanVien", idNhanVien);
        res.put("hoTen", nv != null ? nv.getHoTen() : "");
        res.put("idPhieuDanhGia", phieuMoiNhat.getIdPhieuDanhGia());
        res.put("tongDiemKpi", kq.getTongDiemKpi());
        res.put("tongDiemNangLuc", kq.getTongDiemNangLuc());
        res.put("diemDanhGiaChung", kq.getDiemDanhGiaChung());
        res.put("viTri9Box", kq.getViTri9Box());
        res.put("deXuat", kq.getDeXuat());
        res.put("ngayTinhToan", kq.getNgayTinhToan());

        return Optional.of(res);
    }
}

