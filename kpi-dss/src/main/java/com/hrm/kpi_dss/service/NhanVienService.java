package com.hrm.kpi_dss.service;

import com.hrm.kpi_dss.entity.NhanVien;
import com.hrm.kpi_dss.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public List<NhanVien> layDanhSachNhanVien() {
        return nhanVienRepository.findAll();
    }

    public Optional<NhanVien> layThongTinNhanVien(Integer id) {
        return nhanVienRepository.findById(id);
    }
}
