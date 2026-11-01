package com.hrm.kpi_dss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/status")
    public Map<String, Object> checkStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Hệ thống HRM KPI DSS backend đã sẵn sàng và kết nối database thành công!");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}

