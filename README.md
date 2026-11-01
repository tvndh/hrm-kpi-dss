# HRM KPI DSS - HỆ THỐNG QUẢN TRỊ NHÂN SỰ & HỖ TRỢ RA QUYẾT ĐỊNH ĐÁNH GIÁ KPI KẾT HỢP TRÍ TUỆ NHÂN TẠO (AI)

> **Đồ án Tốt nghiệp / Nghiên cứu Ứng dụng:** Xây dựng hệ thống quản trị nhân sự hiện đại, đánh giá hiệu suất KPI kết hợp khung năng lực ASK (Knowledge - Skill - Attitude), hỗ trợ ra quyết định phân loại nhân tài ma trận 9-Box (Decision Support System - DSS) và tích hợp mô hình học máy (Machine Learning) dự báo rủi ro nghỉ việc.

[![Java](https://img.shields.io/badge/Java-21%2B%20%2F%2025-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![FastAPI](https://img.shields.io/badge/FastAPI-Python_3.13-009688?style=for-the-badge&logo=fastapi&logoColor=white)](https://fastapi.tiangolo.com/)
[![React Native](https://img.shields.io/badge/React_Native-Expo_SDK-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://reactnative.dev/)
[![SQL Server](https://img.shields.io/badge/Microsoft_SQL_Server-2019%2B-CC292B?style=for-the-badge&logo=microsoft-sql-server&logoColor=white)](https://www.microsoft.com/sql-server)
[![Scikit-Learn](https://img.shields.io/badge/Scikit--Learn-Random_Forest-F7931E?style=for-the-badge&logo=scikit-learn&logoColor=white)](https://scikit-learn.org/)

---

## 📌 1. Giới thiệu Đề tài & Mục tiêu

Trong bối cảnh chuyển đổi số công tác quản trị nguồn nhân lực (Digital HRM), việc đánh giá nhân viên không chỉ dừng lại ở các con số KPI định lượng đơn thuần mà cần có cái nhìn đa chiều về năng lực thực tế và xu hướng gắn bó của nhân sự. 

Dự án **HRM KPI DSS** được phát triển nhằm giải quyết triệt để 3 bài toán lớn:
1. **Đánh giá hiệu suất đa chiều:** Kết hợp giữa kết quả công việc định lượng (**KPI - 60%**) và khung năng lực định tính chuẩn quốc tế (**ASK: Kiến thức - Kỹ năng - Thái độ - 40%**).
2. **Hỗ trợ ra quyết định nhân tài (DSS - Decision Support System):** Tự động phân vị nhân sự vào **Ma trận 9-Box (Talent Matrix)**, xác định nhóm "Ngôi sao" (Stars), "Nhân tài tương lai" (High Potentials), "Cốt lõi" (Core Players) hay "Cần cảnh báo" (Underperformers), đồng thời tự động đề xuất chính sách nhân sự (Thăng chức, Tăng lương, Đào tạo, PIP).
3. **Chủ động phòng ngừa rủi ro nhân sự bằng Trí tuệ nhân tạo (AI):** Triển khai mô hình Machine Learning phân loại ngẫu nhiên (**Random Forest**) dựa trên tập dữ liệu chuẩn IBM HR Analytics, phân tích 12+ thuộc tính nhân trắc học và môi trường làm việc để dự báo nguy cơ nhân sự rời bỏ doanh nghiệp với tính minh bạch giải thích (Explainable AI - XAI).

---

## 🏗️ 2. Kiến trúc Hệ thống Tổng thể (3-Tier & Microservices)

Hệ thống được thiết kế theo kiến trúc Microservices phân tách độc lập giữa Tầng dữ liệu nghiệp vụ, Tầng tính toán DSS, Tầng trí tuệ nhân tạo AI và Đa nền tảng giao diện người dùng:

```
+-----------------------------------------------------------------------------------+
|                                 CLIENT APPLICATIONS                               |
|                                                                                   |
|    +----------------------------------+    +----------------------------------+   |
|    |     Web Admin Dashboard          |    |     Mobile App (React Native)    |   |
|    |  (HTML5, Chart.js, Vanilla JS)   |    |  (Expo SDK, Android/iOS Client)  |   |
|    |      http://localhost:8088       |    |         Metro: Port 8081         |   |
|    +-----------------+----------------+    +-----------------+----------------+   |
+----------------------|---------------------------------------|--------------------+
                       | (HTTP / RESTful JSON)                 |
                       +-------------------+-------------------+
                                           |
+------------------------------------------v----------------------------------------+
|                      CORE BACKEND & DSS ENGINE (Spring Boot)                      |
|                               Port: 8088                                          |
|                                                                                   |
|  - NhanVienController: Hồ sơ nhân sự, phòng ban, chức vụ                          |
|  - KpiController: Thiết lập tiêu chí, nhập điểm KPI, chấm khung năng lực ASK     |
|  - DssController: Thuật toán DSS (S = 0.6 KPI + 0.4 ASK) & Phân loại 9-Box        |
|  - AiController: Tích hợp và điều phối dữ liệu với Python AI Service              |
+--------------------+---------------------------------------------+----------------+
                     | (JPA / Hibernate)                           | (HTTP REST)
                     v                                             v
+--------------------+---------------------+   +---------------+--------------------+
|          DATABASE RELATIONAL             |   |        AI MICROSERVICE (Python)    |
|   Microsoft SQL Server (Port 1433)       |   |          FastAPI (Port 8000)       |
|                                          |   |                                    |
|  - HRM_KPI_DSS Database                  |   |  - Random Forest Classifier        |
|  - 14 Bảng dữ liệu quan hệ               |   |  - Huấn luyện: IBM HR Analytics    |
|  - 53+ bản ghi dữ liệu mẫu phong phú     |   |  - Accuracy: 77.33%, AUC: 0.828    |
+------------------------------------------+   +------------------------------------+
```

---

## 🗄️ 3. Cơ sở Dữ liệu Quan hệ (14 Bảng)

Cơ sở dữ liệu Microsoft SQL Server (`HRM_KPI_DSS`) được thiết kế chuẩn hóa mức 3NF, đáp ứng đầy đủ nghiệp vụ quản trị và phân tích:

| STT | Tên bảng | Chức năng nghiệp vụ chính |
| :---: | :--- | :--- |
| 1 | `phong_ban` | Quản lý danh mục phòng ban (Kỹ thuật, Kinh doanh, Nhân sự, Tài chính...) |
| 2 | `chuc_vu` | Quản lý chức vụ (Lập trình viên, Trưởng nhóm, Trưởng phòng, Chuyên viên...) |
| 3 | `vai_tro` | Phân quyền người dùng hệ thống (Admin, Quản lý, Nhân viên) |
| 4 | `nhan_vien` | Hồ sơ 53+ nhân sự chi tiết: Nhân trắc, thâm niên, khoảng cách đi làm, học vấn |
| 5 | `khung_nang_luc` | Danh mục năng lực chuẩn ASK: Kiến thức (`Knowledge`), Kỹ năng (`Skill`), Thái độ (`Attitude`) |
| 6 | `tieu_chi_kpi` | Danh mục chỉ số hiệu suất trọng yếu theo từng phòng ban và vị trí |
| 7 | `ke_hoach_danh_gia`| Quản lý các đợt đánh giá định kỳ (Quý 1, Quý 2, Năm...) |
| 8 | `chi_tiet_kpi_nhan_vien` | Phân bổ chỉ số KPI cụ thể, trọng số và mục tiêu cho từng cá nhân |
| 9 | `ket_qua_kpi` | Tổng hợp điểm KPI định lượng hoàn thành sau đợt đánh giá |
| 10 | `ket_qua_nang_luc_ask` | Điểm đánh giá chi tiết theo từng khía cạnh năng lực ASK (Thang điểm 1 - 5) |
| 11 | `du_bao_nghi_viec_ai` | Lưu trữ kết quả phân tích AI: Xác suất rời bỏ, mức cảnh báo (`LOW`, `MEDIUM`, `HIGH`) |
| 12 | `phan_loai_9_box` | Danh mục 9 ô ma trận nhân tài chuẩn quốc tế và khuyến nghị chính sách |
| 13 | `lich_su_danh_gia_dss`| Nhật ký quyết sách DSS: Điểm tổng hợp, phân loại 9-box và hành động phê duyệt |
| 14 | `luong_thuong_kpi` | Bảng tính thưởng hiệu suất dựa trên kết quả xếp loại và chính sách DSS |

*Dữ liệu mẫu và kịch bản khởi tạo được lưu trữ tại:*
- [`kpi-dss/insert_50_nhan_vien.sql`](file:///e:/demo_do_an/kpi-dss/insert_50_nhan_vien.sql) (Khởi tạo hồ sơ 50+ nhân sự)
- [`kpi-dss/seed_kpi_dss_ai_data.sql`](file:///e:/demo_do_an/kpi-dss/seed_kpi_dss_ai_data.sql) (Nạp bảng năng lực, KPI, phân loại 9-box và lịch sử)

---

## 🚀 4. Các Tính Năng Nổi Bật của Hệ Thống

### 1. Động cơ Hỗ trợ Ra Quyết định (DSS Engine)
- **Công thức tính điểm tổng hợp:**
  $$\text{Score}_{\text{DSS}} = 0.6 \times \text{KPI} + 0.4 \times \text{ASK}$$
- **Thuật toán phân loại Ma trận 9-Box:**
  Tự động ánh xạ điểm Hiệu suất (Performance: Thấp < 70, Trung bình 70-85, Cao > 85) và Tiềm năng Năng lực (Potential: Thấp < 3.0, Trung bình 3.0-4.0, Cao > 4.0) vào 9 nhóm nhân tài:
  - **Star (Ngôi sao):** Hiệu suất cao, Tiềm năng cao $\rightarrow$ *Quy hoạch cán bộ nguồn, thăng chức, thưởng vượt cấp.*
  - **High Potential (Tiềm năng cao):** Tiềm năng cao, Hiệu suất trung bình $\rightarrow$ *Kèm cặp (Mentoring), giao dự án thách thức.*
  - **Core Player (Nhân sự nòng cốt):** Hiệu suất cao, Tiềm năng trung bình $\rightarrow$ *Giữ chân, duy trì đãi ngộ ổn định.*
  - **Underperformer (Cần cải thiện):** Cả 2 chỉ số đều thấp $\rightarrow$ *Kích hoạt kế hoạch cải thiện hiệu suất (PIP) hoặc tái bố trí.*

### 2. Trí tuệ Nhân tạo Dự báo Rủi ro Nghỉ việc (AI Microservice)
- Xây dựng bằng **Python FastAPI + Scikit-Learn**.
- Huấn luyện mô hình **Random Forest Classifier** đạt **Accuracy 77.33%** và **ROC-AUC 0.828**.
- **Giải thích quyết định (Explainable AI):** Trả về xác suất chính xác, gán nhãn mức độ rủi ro và liệt kê chi tiết các nguyên nhân gốc rễ (Làm thêm giờ nhiều, Lương dưới mức trung bình, Khoảng cách đi làm xa, Nhiều năm chưa được thăng chức...).

### 3. Giao diện Web Admin Dashboard Trực Quan
- **Tab 1: Danh sách Nhân viên:** Tra cứu, tìm kiếm, xem nhanh thông tin thâm niên và tình trạng công tác.
- **Tab 2: Ma trận 9-Box Trực quan:** Hiển thị trực quan lưới 9 ô với các thẻ nhân viên tương tác kéo thả / nhấp xem khuyến nghị chính sách.
- **Tab 3: Cảnh báo Rủi ro Nghỉ việc AI:** Danh sách cảnh báo phân cấp màu (Đỏ - Vàng - Xanh), nút bấm phân tích trực tiếp theo thời gian thực từ Python AI Service.
- **Tab 4: Chấm điểm KPI & Biểu đồ Radar ASK:** Tích hợp **Chart.js** vẽ biểu đồ mạng nhện đa giác 3 khía cạnh Kiến thức - Kỹ năng - Thái độ, tính điểm DSS tự động ngay trên giao diện.
- **Tab 5: Tiêu chuẩn KPI:** Quản lý chỉ tiêu chuẩn cho từng vị trí phòng ban.

### 4. Ứng dụng Di động React Native (Mobile App)
- Phát triển bằng **React Native Expo** tương thích cả Android và iOS.
- Nhân viên tự đăng nhập tra cứu hồ sơ cá nhân, lịch sử đánh giá.
- Tự chấm điểm hiệu suất KPI và khảo sát năng lực ASK trực tiếp trên điện thoại.
- Nhận phản hồi xếp loại 9-Box và khuyến nghị phát triển cá nhân.

---

## 🌐 5. Danh mục RESTful API Chính

Hệ thống cung cấp hệ thống API chuẩn RESTful, bật sẵn CORS toàn cục cho phép tích hợp linh hoạt:

| Phân hệ | Phương thức | Endpoint | Chức năng |
| :--- | :---: | :--- | :--- |
| **Nhân sự** | `GET` | `/api/nhan-vien/danh-sach` | Lấy danh sách toàn bộ nhân sự cùng thông tin chi tiết |
| | `GET` | `/api/nhan-vien/{id}` | Lấy chi tiết 1 nhân sự theo mã ID |
| | `GET` | `/api/nhan-vien/phong-ban/{id}` | Lấy danh sách nhân sự trực thuộc phòng ban |
| **KPI & ASK** | `GET` | `/api/kpi/tieu-chi` | Danh mục tiêu chí KPI mẫu |
| | `GET` | `/api/kpi/ket-qua/{idNhanVien}` | Lấy lịch sử kết quả đánh giá KPI & ASK |
| | `POST` | `/api/kpi/danh-gia` | Nộp phiếu đánh giá KPI và khung năng lực ASK |
| **DSS Engine** | `GET` | `/api/dss/phan-loai-9-box` | Lấy ma trận phân bổ 9 ô của toàn bộ nhân viên |
| | `POST` | `/api/dss/tinh-toan` | Kích hoạt tính toán điểm DSS và phân loại 9-box cho nhân sự |
| | `GET` | `/api/dss/lich-su/{idNhanVien}` | Xem lịch sử các quyết sách DSS và lời khuyên nhân tài |
| **AI Service** | `POST` | `/api/ai/du-bao/{idNhanVien}` | Dự báo rủi ro nghỉ việc tức thời cho 1 nhân sự |
| | `POST` | `/api/ai/du-bao-toan-bo` | Quét phân tích AI hàng loạt cho tất cả nhân viên công ty |
| | `GET` | `/api/ai/danh-sach-canh-bao` | Lấy danh sách nhân sự đang ở mức cảnh báo cao (`HIGH`) |
| **Hệ thống** | `GET` | `/api/status` | Kiểm tra trạng thái hoạt động của Backend & MSSQL |

---

## 💻 6. Bảng Phân Bổ Cổng Dịch Vụ (Port Mapping)

| Dịch vụ | Công nghệ | Cổng (Port) | Địa chỉ truy cập / Tài liệu |
| :--- | :--- | :---: | :--- |
| **Backend REST Core** | Spring Boot / Java 21 | **`8088`** | `http://localhost:8088` |
| **Web Admin Dashboard** | Static Web Resource | **`8088`** | `http://localhost:8088/index.html` |
| **AI Microservice API** | FastAPI / Uvicorn | **`8000`** | `http://localhost:8000` (Swagger: `/docs`) |
| **Mobile App Metro** | React Native Expo | **`8081`** | `http://localhost:8081` (Quét mã qua Expo Go) |
| **Database Server** | Microsoft SQL Server | **`1433`** | `localhost:1433` (DB: `HRM_KPI_DSS`) |

---

## ⚡ 7. Hướng Dẫn Cài Đặt & Khởi Chạy Nhanh (1-Click Run)

### Yêu cầu tiên quyết:
- **Java Development Kit (JDK):** Java 21 hoặc Java 25 (đã thiết lập biến môi trường `JAVA_HOME`).
- **Python:** Phiên bản 3.10 trở lên.
- **Node.js:** Phiên bản 18+ (kèm `npm`).
- **Microsoft SQL Server:** Đã tạo cơ sở dữ liệu `HRM_KPI_DSS` và thực thi các script SQL.

---

### Cách 1: Khởi chạy nhanh bằng File Script Batch (.bat)

Dự án đã tích hợp sẵn các tệp kịch bản tự động hóa kiểm tra môi trường và khởi chạy:

1. **Khởi chạy Spring Boot Backend & Web Dashboard:**
   - Nhấp đúp chuột vào file: [`run.bat`](file:///e:/demo_do_an/run.bat) (hoặc chạy lệnh `.\run.bat` trong Terminal).
   - Truy cập giao diện quản trị tại: **`http://localhost:8088`**.

2. **Khởi chạy Python AI Microservice:**
   - Mở Terminal tại thư mục `ai-service` hoặc nhấp đúp file: [`ai-service/run_ai.bat`](file:///e:/demo_do_an/ai-service/run_ai.bat).
   - Xem tài liệu OpenAPI / Swagger tại: **`http://localhost:8000/docs`**.

3. **Khởi chạy Mobile App React Native:**
   - Nhấp đúp file: [`mobile-app/run_mobile.bat`](file:///e:/demo_do_an/mobile-app/run_mobile.bat).
   - Dùng camera điện thoại quét mã QR trong ứng dụng **Expo Go** hoặc bấm phím **`w`** để chạy thử trên trình duyệt.

---

### Cách 2: Khởi chạy thủ công từ dòng lệnh (Manual CLI)

#### Bước 1: Khởi chạy Backend
```bash
cd kpi-dss
# Chạy với Maven Wrapper
.\mvnw.cmd spring-boot:run
```

#### Bước 2: Khởi chạy AI Service
```bash
cd ai-service
# Cài đặt thư viện (nếu chạy lần đầu)
pip install -r requirements.txt
# Khởi chạy server FastAPI
python -m uvicorn main:app --host 0.0.0.0 --port 8000 --reload
```

#### Bước 3: Khởi chạy Mobile App
```bash
cd mobile-app
# Cài đặt thư viện (nếu chạy lần đầu)
npm install
# Khởi chạy Expo
npm start
```

---

## 📚 8. Hồ Sơ Báo Cáo & Kịch Bản Bảo Vệ Tốt Nghiệp

Dự án cung cấp trọn bộ tài liệu bảo vệ học thuật tiêu chuẩn:

1. **Báo cáo Đồ án Toàn diện (Word File):**
   - File tài liệu: [`BAO_CAO_DO_AN_HRM_KPI_DSS.docx`](file:///e:/demo_do_an/BAO_CAO_DO_AN_HRM_KPI_DSS.docx)
   - Kết cấu 5 chương hoàn chỉnh:
     - *Chương 1:* Tổng quan bài toán quản trị nhân sự và mục tiêu nghiên cứu.
     - *Chương 2:* Cơ sở lý thuyết (Mô hình ASK, Ma trận 9-Box, Hệ hỗ trợ ra quyết định DSS, Thuật toán Random Forest).
     - *Chương 3:* Phân tích thiết kế hệ thống (Use Case, Sequence Diagram, Thiết kế 14 bảng CSDL).
     - *Chương 4:* Cài đặt và thực nghiệm (Hiện thực Backend, AI Service, Web Dashboard và Mobile App).
     - *Chương 5:* Kết luận, đánh giá ưu khuyết điểm và hướng phát triển.
2. **Kịch bản Thuyết trình & 12 Câu hỏi Phản biện:**
   - File hướng dẫn: [`KICH_BAN_BAO_VE_VA_CAU_HOI_PHAN_BIEN.md`](file:///e:/demo_do_an/KICH_BAN_BAO_VE_VA_CAU_HOI_PHAN_BIEN.md)
   - Kịch bản trình chiếu chi tiết 16 Slide bảo vệ với thời lượng 15-20 phút.
   - Trọn bộ 12 câu hỏi phản biện hóc búa từ Hội đồng (về độ chính xác AI, mất cân bằng dữ liệu Imbalanced Data, tính bảo mật, độ trễ hệ thống...) kèm câu trả lời mẫu xuất sắc.

---

## 👥 9. Đóng Góp & Giấy Phép

- **Dự án:** Đồ án Tốt nghiệp / Nghiên cứu Hệ thống Quản trị Nhân sự HRM KPI DSS
- **Mã nguồn:** Lưu trữ tại GitHub: [https://github.com/tvndh/hrm-kpi-dss](https://github.com/tvndh/hrm-kpi-dss)
- **Giấy phép:** Phục vụ mục đích học tập, nghiên cứu khoa học và phát triển ứng dụng mã nguồn mở.
