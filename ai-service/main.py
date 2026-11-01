"""
AI Microservice - API Dự Báo Nguy Cơ Nghỉ Việc (HRM Attrition Prediction)
Framework: FastAPI
Chạy trên cổng 8000
"""

import sys
if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

import os
import joblib
import pandas as pd
from typing import List, Optional
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

# Khởi tạo FastAPI
app = FastAPI(
    title="HRM AI Attrition Prediction API",
    description="Microservice dự báo nguy cơ nghỉ việc dựa trên mô hình Machine Learning",
    version="1.0.0"
)

# Cấu hình CORS để Spring Boot hoặc Web có thể gọi trực tiếp
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Load model đã huấn luyện
MODEL_PATH = os.path.join(os.path.dirname(__file__), 'model', 'attrition_model.pkl')
model_bundle = None

try:
    model_bundle = joblib.load(MODEL_PATH)
    print(f"[INFO] Loaded model successfully from {MODEL_PATH}")
except Exception as e:
    print(f"[ERROR] Failed to load model: {e}")

# Schema Input & Output
class EmployeeInput(BaseModel):
    id_nhan_vien: Optional[int] = Field(default=None, description="Mã nhân viên")
    age: int = Field(ge=18, le=65, description="Tuổi")
    distance_from_home: int = Field(ge=0, le=100, description="Khoảng cách đi làm (km)")
    education: int = Field(default=3, ge=1, le=5, description="Trình độ học vấn (1-5)")
    total_working_years: int = Field(ge=0, le=50, description="Tổng năm kinh nghiệm")
    years_at_company: int = Field(ge=0, le=50, description="Số năm tại công ty")
    years_in_current_role: int = Field(ge=0, le=50, description="Số năm cùng chức vụ")
    years_since_last_promotion: int = Field(ge=0, le=50, description="Số năm từ lần thăng chức gần nhất")
    years_with_curr_manager: int = Field(ge=0, le=50, description="Số năm làm việc với sếp hiện tại")
    income_level: Optional[int] = Field(default=3, ge=1, le=5, description="Mức lương (1-5)")

class PredictionResult(BaseModel):
    id_nhan_vien: Optional[int]
    ty_le_rui_ro: float
    muc_do_canh_bao: str  # LOW, MEDIUM, HIGH
    nguyen_nhan_chinh: str

def extract_primary_causes(emp: EmployeeInput, risk_pct: float) -> str:
    """Phân tích các yếu tố gây rủi ro cao để giải thích quyết định của AI"""
    causes = []
    if emp.distance_from_home >= 20:
        causes.append(f"Khoảng cách đi làm xa ({emp.distance_from_home}km)")
    elif emp.distance_from_home >= 15:
        causes.append(f"Đi lại tương đối xa ({emp.distance_from_home}km)")
        
    if emp.years_since_last_promotion >= 3 and emp.years_at_company >= 4:
        causes.append(f"{emp.years_since_last_promotion} năm chưa được thăng chức")
        
    if emp.years_in_current_role >= 4:
        causes.append(f"{emp.years_in_current_role} năm giữ nguyên vị trí")
        
    if emp.years_with_curr_manager <= 1 and emp.years_at_company >= 3:
        causes.append("Mới thay đổi người quản lý trực tiếp")
        
    if emp.income_level and emp.income_level <= 2:
        causes.append("Mức lương chưa cạnh tranh so với thâm niên")
        
    if not causes:
        if risk_pct >= 50.0:
            return "Áp lực hiệu suất kết hợp môi trường công việc"
        else:
            return "Chỉ số ổn định, môi trường làm việc tốt"
            
    return "; ".join(causes)

@app.get("/")
def root():
    return {
        "status": "ONLINE",
        "service": "HRM AI Attrition Prediction Service",
        "model_loaded": model_bundle is not None,
        "algorithm": "Random Forest Classifier",
        "target": "Employee Attrition (IBM HR Analytics Standard)"
    }

@app.post("/predict", response_model=PredictionResult)
def predict_attrition(emp: EmployeeInput):
    if not model_bundle:
        raise HTTPException(status_code=500, detail="Mô hình AI chưa được tải vào bộ nhớ")
    
    pipeline = model_bundle['pipeline']
    feature_cols = model_bundle['feature_cols']
    
    # Tạo DataFrame
    df = pd.DataFrame([{
        'age': emp.age,
        'distance_from_home': emp.distance_from_home,
        'education': emp.education,
        'total_working_years': emp.total_working_years,
        'years_at_company': emp.years_at_company,
        'years_in_current_role': emp.years_in_current_role,
        'years_since_last_promotion': emp.years_since_last_promotion,
        'years_with_curr_manager': emp.years_with_curr_manager,
        'income_level': emp.income_level or 3
    }])[feature_cols]
    
    prob = pipeline.predict_proba(df)[0][1]
    risk_pct = round(prob * 100, 2)
    
    if risk_pct >= 70.0:
        warning_level = "HIGH"
    elif risk_pct >= 40.0:
        warning_level = "MEDIUM"
    else:
        warning_level = "LOW"
        
    causes = extract_primary_causes(emp, risk_pct)
    
    return PredictionResult(
        id_nhan_vien=emp.id_nhan_vien,
        ty_le_rui_ro=risk_pct,
        muc_do_canh_bao=warning_level,
        nguyen_nhan_chinh=causes
    )

@app.post("/batch-predict", response_model=List[PredictionResult])
def batch_predict(employees: List[EmployeeInput]):
    results = []
    for emp in employees:
        res = predict_attrition(emp)
        results.append(res)
    return results

if __name__ == '__main__':
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)

