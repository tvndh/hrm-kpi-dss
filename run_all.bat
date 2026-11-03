@echo off
title Khoi Chay Toan Bo He Thong HRM KPI DSS
color 0A
echo =====================================================================
echo           KHOI CHAY TOAN BO HE THONG HRM KPI DSS (3 TANG)
echo =====================================================================
echo.
echo  [1/3] Khoi dong Python AI Microservice (Port 8000)...
start "1. Python AI Service (Port 8000)" cmd /k "cd /d %~dp0ai-service && call run_ai.bat"

echo.
echo  [2/3] Khoi dong Backend Spring Boot (Port 8088)...
start "2. Spring Boot Backend (Port 8088)" cmd /k "cd /d %~dp0 && call run.bat"

echo.
echo  [3/3] Dang khoi tao dich vu... Trinh duyet se tu dong mo sau 8 giay.
timeout /t 8 /nobreak >nul

echo.
echo  Dang mo Web Admin Dashboard tren trinh duyet...
start http://localhost:8088/

echo.
echo =====================================================================
echo  [HOAN TAT] He thong dang chay tren cac cua so rieng biet:
echo   - Web Admin Dashboard:  http://localhost:8088/
echo   - AI Service API Docs:  http://localhost:8000/docs
echo.
echo  * De chay Mobile App (React Native):
echo    Nhan dup file "mobile-app\run_mobile.bat" hoac go "npm start"
echo =====================================================================
pause
