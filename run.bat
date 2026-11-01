@echo off
title HRM KPI DSS Enterprise Server
echo ===================================================
echo    DANG KHOI CHAY HE THONG HRM KPI DSS...
echo    Truy cap Dashboard: http://localhost:8088/
echo ===================================================

if not defined JAVA_HOME (
    set "JAVA_HOME=C:\Program Files\Java\jdk-25"
    set "PATH=C:\Program Files\Java\jdk-25\bin;%PATH%"
)

cd /d "%~dp0kpi-dss"
call mvnw.cmd spring-boot:run
pause
