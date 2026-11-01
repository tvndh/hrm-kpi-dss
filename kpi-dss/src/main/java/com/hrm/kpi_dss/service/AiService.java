package com.hrm.kpi_dss.service;

import com.hrm.kpi_dss.entity.DuBaoNghiViecAi;
import com.hrm.kpi_dss.entity.NhanVien;
import com.hrm.kpi_dss.repository.DuBaoNghiViecAiRepository;
import com.hrm.kpi_dss.repository.NhanVienRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiService {

    private final DuBaoNghiViecAiRepository duBaoRepository;
    private final NhanVienRepository nhanVienRepository;

    public AiService(DuBaoNghiViecAiRepository duBaoRepository,
                     NhanVienRepository nhanVienRepository) {
        this.duBaoRepository = duBaoRepository;
        this.nhanVienRepository = nhanVienRepository;
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
}
