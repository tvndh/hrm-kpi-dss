@echo off
title HRM AI Attrition Prediction Service (Port 8000)
echo ===================================================
echo     DANG KHOI CHAY PYTHON AI SERVICE (FASTAPI)...
echo     Swagger UI Docs: http://localhost:8000/docs
echo ===================================================
cd /d "%~dp0"
python main.py
pause

