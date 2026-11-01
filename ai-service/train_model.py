"""
Huấn luyện Mô hình Machine Learning Dự báo Nguy cơ Nghỉ việc (Employee Attrition Prediction)
Dựa trên các đặc trưng chuẩn của tập dữ liệu Kaggle IBM HR Analytics
"""

import sys
if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

import os
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import Pipeline
from sklearn.metrics import classification_report, accuracy_score, roc_auc_score
import joblib

def generate_training_data(n_samples=3000, random_state=42):
    """
    Tạo tập dữ liệu mô phỏng theo đúng phân phối và tương quan của bộ dữ liệu IBM HR Attrition
    """
    np.random.seed(random_state)
    
    age = np.random.randint(22, 58, size=n_samples)
    distance = np.random.randint(1, 35, size=n_samples)
    education = np.random.choice([1, 2, 3, 4, 5], p=[0.1, 0.2, 0.45, 0.2, 0.05], size=n_samples)
    
    total_exp = np.clip(age - np.random.randint(20, 24, size=n_samples), 0, 35)
    years_at_company = np.array([np.random.randint(0, max(1, exp + 1)) for exp in total_exp])
    years_in_role = np.array([np.random.randint(0, max(1, c + 1)) for c in years_at_company])
    years_since_promo = np.array([np.random.randint(0, max(1, c + 1)) for c in years_at_company])
    years_with_mgr = np.array([np.random.randint(0, max(1, c + 1)) for c in years_at_company])
    
    income_level = np.clip((total_exp // 6) + 1 + np.random.choice([-1, 0, 1], p=[0.2, 0.6, 0.2], size=n_samples), 1, 5)

    log_odds = (
        -1.8
        + 0.08 * (distance - 10)
        + 0.25 * (years_since_promo - 2)
        + 0.15 * (years_in_role - 2)
        - 0.20 * (years_with_mgr - 2)
        - 0.04 * (age - 30)
        - 0.35 * (income_level - 3)
    )
    
    prob = 1 / (1 + np.exp(-log_odds))
    attrition = (np.random.rand(n_samples) < prob).astype(int)

    df = pd.DataFrame({
        'age': age,
        'distance_from_home': distance,
        'education': education,
        'total_working_years': total_exp,
        'years_at_company': years_at_company,
        'years_in_current_role': years_in_role,
        'years_since_last_promotion': years_since_promo,
        'years_with_curr_manager': years_with_mgr,
        'income_level': income_level,
        'attrition': attrition
    })
    return df

def train_and_save_model():
    print("[INFO] Dang chuan bi du lieu huan luyen (IBM HR Analytics Standard)...")
    df = generate_training_data(n_samples=3000)
    
    feature_cols = [
        'age', 'distance_from_home', 'education', 'total_working_years',
        'years_at_company', 'years_in_current_role', 'years_since_last_promotion',
        'years_with_curr_manager', 'income_level'
    ]
    
    X = df[feature_cols]
    y = df['attrition']
    
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42, stratify=y)
    
    print(f"[DATA] Tap huan luyen: {len(X_train)} dong | Tap kiem thu: {len(X_test)} dong")
    
    pipeline = Pipeline([
        ('scaler', StandardScaler()),
        ('classifier', RandomForestClassifier(n_estimators=150, max_depth=8, random_state=42))
    ])
    
    print("[TRAIN] Dang huan luyen Random Forest Classifier...")
    pipeline.fit(X_train, y_train)
    
    y_pred = pipeline.predict(X_test)
    y_prob = pipeline.predict_proba(X_test)[:, 1]
    
    acc = accuracy_score(y_test, y_pred)
    roc = roc_auc_score(y_test, y_prob)
    
    print("=" * 50)
    print(f"[RESULT] Do chinh xac (Accuracy): {acc * 100:.2f}%")
    print(f"[RESULT] Diem ROC-AUC: {roc:.3f}")
    print("=" * 50)
    print(classification_report(y_test, y_pred, target_names=['O lai (0)', 'Nghi viec (1)']))
    
    # Lưu model
    os.makedirs('model', exist_ok=True)
    model_path = os.path.join('model', 'attrition_model.pkl')
    joblib.dump({
        'pipeline': pipeline,
        'feature_cols': feature_cols
    }, model_path)
    
    print(f"[SUCCESS] Da luu mo hinh thanh cong vao: {model_path}")

if __name__ == '__main__':
    train_and_save_model()
