# KỊCH BẢN THUYẾT TRÌNH BẢO VỆ ĐỒ ÁN & BỘ CÂU HỎI PHẢN BIỆN HỘI ĐỒNG
> **Đề tài:** Nghiên cứu & Xây dựng Hệ thống Quản trị Nhân sự tích hợp Hệ Hỗ trợ Ra Quyết định (DSS) và Trí tuệ Nhân tạo Dự báo Nguy cơ Nghỉ việc (HRM KPI DSS Enterprise)  
> **Sinh viên thực hiện:** Tạ Vũ Nam (MSSV: 1250080234)  
> **Khoa:** Công nghệ Thông tin - Trường ĐH Tài nguyên & Môi trường TP.HCM (HCMUNRE)

---

## 📽️ PHẦN 1: DÀN Ý 16 SLIDE THUYẾT TRÌNH (15 PHÚT)

| Slide | Tiêu đề Slide | Nội dung trình bày chính | Thời lượng |
| :---: | :--- | :--- | :---: |
| **1** | **Trang bìa** | Tên đề tài, Giảng viên hướng dẫn, Sinh viên thực hiện, Trường & Khoa | 30s |
| **2** | **Lý do chọn đề tài** | Thực trạng HRM truyền thống chỉ dừng ở chấm công/lưu trữ; Nhu cầu phân loại nhân tài và ngăn chặn làn sóng nghỉ việc (Attrition) | 1m |
| **3** | **Mục tiêu nghiên cứu** | Xây dựng hệ sinh thái 3 tầng: Quản lý nhân sự + Đánh giá KPI/ASK + DSS Ma trận 9-Box + AI Dự báo nghỉ việc | 1m |
| **4** | **Cơ sở lý thuyết: KPI & Mô hình ASK** | Khái niệm KPI hiệu suất & Khung năng lực 3 trụ cột (Knowledge - Skill - Attitude) | 1m |
| **5** | **Cơ sở lý thuyết: DSS & Ma trận 9-Box** | Nguyên lý ma trận 9 ô của McKinsey/GE: Phân loại Hiệu suất (X) vs Tiềm năng (Y) | 1.5m |
| **6** | **Bài toán Machine Learning: Employee Attrition** | Phân tích bài toán phân loại nhị phân dựa trên tập dữ liệu chuẩn Kaggle IBM HR Analytics | 1m |
| **7** | **Kiến trúc Tổng thể Hệ thống** | Sơ đồ luồng 3 tầng: React Native/Web ↔ Spring Boot (Java 21) ↔ Python AI (FastAPI) ↔ SQL Server | 1.5m |
| **8** | **Mô hình Cơ sở dữ liệu (ERD)** | Cấu trúc 14 bảng chuẩn 3NF, ràng buộc khóa ngoại và Check Constraints | 1m |
| **9** | **Cài đặt Backend & Thuật toán DSS** | Công thức tính điểm tổng hợp ($0.6 \times \text{KPI} + 0.4 \times \text{ASK}$), thuật toán ánh xạ luật quyết sách | 1.5m |
| **10** | **Cài đặt Python AI Microservice** | Pipeline tiền xử lý, thuật toán Random Forest Classifier, giải thích nguyên nhân rủi ro | 1.5m |
| **11** | **Demo: Web Admin Dashboard** | Trực quan hóa Ma trận 9-Box, bảng cảnh báo AI, công cụ chấm điểm & Radar Chart | 1.5m |
| **12** | **Demo: Mobile App React Native** | Giao diện nhân viên: Đăng nhập, tra cứu hồ sơ, tự chấm điểm KPI và xem phản hồi DSS | 1m |
| **13** | **Kết quả Thực nghiệm & Độ chính xác AI** | Kết quả trên 53 nhân sự, Accuracy: **77.33%**, ROC-AUC: **0.828**, phân bổ 9 nhóm nhân tài | 1m |
| **14** | **Đóng góp mới của đề tài** | Tự động hóa việc ra quyết định nhân sự, cảnh báo biến động nguồn lực sớm bằng AI | 30s |
| **15** | **Hạn chế & Hướng phát triển** | Tích hợp chấm công khuôn mặt, LLM Chatbot tư vấn chính sách nhân sự | 30s |
| **16** | **Lời cảm ơn & Q&A** | Lời cảm ơn Hội đồng và sẵn sàng trả lời phản biện | 30s |

---

## 🎯 PHẦN 2: BỘ 12 CÂU HỎI PHẢN BIỆN "XOÁY" CỦA HỘI ĐỒNG & CÂU TRẢ LỜI MẪU

### ❓ Câu 1: Tại sao em lại chọn mô hình Ma trận 9-Box cho hệ hỗ trợ ra quyết định (DSS) mà không dùng các phương pháp khác như AHP hay TOPSIS?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, trong quản trị nhân sự thực tế tại các doanh nghiệp lớn (như GE hay McKinsey), ma trận 9-Box là tiêu chuẩn vàng vì tính **trực quan, dễ hiểu đối với cấp quản lý** và giải quyết trực diện 2 chiều quan trọng nhất: **Hiệu suất trong quá khứ (KPI)** và **Tiềm năng trong tương lai (Năng lực ASK)**. Trong khi các phương pháp như AHP hay TOPSIS mang nặng tính toán ma trận phức tạp và khó giải thích cho phòng nhân sự, Ma trận 9-Box cho phép xuất ngay hành động quyết sách cụ thể (ví dụ: nhóm Star thì thăng chức, nhóm Risk thì đưa vào diện PIP hoặc tinh giản)."

---

### ❓ Câu 2: Trong công thức tính điểm chung: `Điểm = 0.6 * KPI + 0.4 * Năng Lực`, cơ sở nào để em chọn tỷ lệ 60% và 40%?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, tỷ lệ 60/40 là tỷ lệ được khuyến nghị phổ biến trong quản trị hiệu suất hiện đại. Kết quả thực tế (KPI) phản ánh giá trị trực tiếp nhân viên tạo ra cho doanh nghiệp trong kỳ đánh giá nên được ưu tiên trọng số cao hơn (**60%**). Năng lực và thái độ (ASK) phản ánh tiềm năng phát triển dài hạn được gán trọng số **40%**. Hệ thống của em cũng được thiết kế mở, doanh nghiệp hoàn toàn có thể cấu hình lại tỷ lệ này theo từng đặc thù ngành nghề trong bảng quy tắc."

---

### ❓ Câu 3: Dữ liệu nghỉ việc thường bị mất cân bằng (Imbalanced Data) vì số người nghỉ luôn ít hơn số người ở lại. Em xử lý vấn đề này trong mô hình AI như thế nào?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, đây chính là một trong những thách thức lớn nhất của bài toán Employee Attrition. Để xử lý vấn đề này:  
> 1. Trong hàm chia dữ liệu `train_test_split`, em sử dụng tham số `stratify=y` để đảm bảo tỷ lệ giữa nhóm nghỉ việc và ở lại đồng đều giữa tập train và test.  
> 2. Em không chỉ đánh giá mô hình bằng **Accuracy** thông thường (dễ bị đánh lừa khi dữ liệu lệch), mà tập trung đánh giá qua chỉ số **ROC-AUC (đạt 0.828)** và **Precision/F1-Score** trên nhóm nghỉ việc (Class 1) để đảm bảo mô hình thực sự phát hiện được nhóm có nguy cơ cao."

---

### ❓ Câu 4: Tại sao em lại tách riêng Python AI Service thành một microservice chạy FastAPI mà không chạy trực tiếp thư viện Java (như Weka hay Deeplearning4j)?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, việc tách riêng mang lại 3 ưu điểm vượt trội:  
> 1. **Tận dụng tối đa hệ sinh thái AI của Python:** Python là chuẩn mực của Machine Learning với Scikit-Learn, Pandas, NumPy, giúp việc xử lý dữ liệu và huấn luyện mô hình mạnh mẽ hơn rất nhiều so với thư viện Java.  
> 2. **Kiến trúc Microservices độc lập (Decoupling):** Khi mô hình AI cần retrain hoặc nâng cấp lên Deep Learning, server Spring Boot chính vẫn hoạt động bình thường, không gây gián đoạn hệ thống.  
> 3. **Hiệu năng:** FastAPI là framework bất đồng bộ (Asynchronous) cực kỳ nhanh nhẹn, giao tiếp qua REST API chuẩn JSON giúp hệ thống đạt độ trễ thấp."

---

### ❓ Câu 5: Mô hình AI của em có giải thích được tại sao nhân viên đó lại bị cảnh báo nguy cơ nghỉ việc cao hay không, hay nó là một 'hộp đen' (Black-Box)?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, mô hình của em **hoàn toàn có tính năng giải thích quyết định (Explainable AI)**. Dựa trên Feature Importances và phân tích đặc trưng của từng nhân sự, hệ thống tự động chỉ rõ nguyên nhân cốt lõi. Ví dụ với nhân viên `#nv48`, hệ thống đã phát hiện và ghi rõ: *'Khoảng cách đi làm xa (26km); 4 năm cùng chức vụ chưa được thăng chức; Mới thay đổi người quản lý trực tiếp'*. Điều này giúp phòng HR biết chính xác lý do để đưa ra biện pháp can thiệp đúng trọng tâm."

---

### ❓ Câu 6: Hệ thống của em phân quyền như thế nào giữa Admin, Trưởng phòng (Manager) và Nhân viên (Employee)?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, hệ thống phân chia rõ ràng theo bảng `dbo.vai_tro`:  
> • **Admin / HR:** Quản lý toàn bộ danh mục phòng ban, chức vụ, tiêu chí KPI, xem toàn bộ ma trận 9-Box và danh sách rủi ro AI toàn công ty.  
> • **Manager (Trưởng phòng):** Chấm điểm duyệt phiếu đánh giá cho nhân viên thuộc phòng ban mình phụ trách, xem kết quả DSS của cấp dưới.  
> • **Employee (Nhân viên):** Sử dụng Mobile App để tra cứu hồ sơ cá nhân, tự chấm điểm KPI và xem phản hồi quyết sách từ công ty."

---

### ❓ Câu 7: Khi một nhân viên mới được thêm vào hệ thống, quy trình chạy dự báo AI và đánh giá DSS diễn ra như thế nào?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô:  
> 1. Khi nhân viên được tạo trên hệ thống, các đặc trưng như tuổi, thâm niên, khoảng cách đi làm được lưu vào bảng `nhan_vien`.  
> 2. Spring Boot có thể tự động gọi API `POST /predict` của AI Service để lưu dự báo rủi ro ban đầu vào bảng `du_bao_nghi_viec_ai`.  
> 3. Khi đến kỳ đánh giá, nhân viên tự chấm trên Mobile App $\rightarrow$ Trưởng phòng chấm trên Web $\rightarrow$ Bấm nút kích hoạt DSS $\rightarrow$ Hệ thống tự động tính điểm tổng hợp và lưu kết quả 9-Box vào bảng `ket_qua_dss`."

---

### ❓ Câu 8: Khung năng lực ASK của em được ánh xạ như thế nào trong cơ sở dữ liệu?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, trong bảng `dbo.khung_nang_luc`, em đã thiết lập ràng buộc `CHECK CONSTRAINT` bắt buộc cột `loai_nang_luc` chỉ nhận một trong 3 giá trị chuẩn quốc tế: `'Knowledge'` (Kiến thức), `'Skill'` (Kỹ năng), `'Attitude'` (Thái độ). Mỗi vị trí chức danh (như Trưởng phòng, Senior Dev, Sales...) sẽ có bộ tiêu chuẩn và điểm chuẩn riêng từ 1.0 đến 5.0, được trực quan hóa bằng biểu đồ Radar Chart trên Dashboard."

---

### ❓ Câu 9: Tại sao em lại dùng Spring Boot 4.x và Java 21? Phiên bản này có điểm gì nổi bật?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, Java 21 là phiên bản Long-Term Support (LTS) hiện đại nhất với các tính năng vượt trội như Virtual Threads, Record Patterns, và cải tiến hiệu năng bộ nhớ. Kết hợp với Spring Boot thế hệ mới giúp tối ưu hóa thời gian khởi động (Startup time), tương thích hoàn hảo với HTTP/2 và kiến trúc Microservices phân tán."

---

### ❓ Câu 10: Ứng dụng Mobile React Native kết nối với Backend Spring Boot như thế nào khi test trên các môi trường khác nhau?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, trong file `src/api/config.js` của Mobile App, em đã xây dựng cơ chế quản lý Base URL linh hoạt:  
> • Khi chạy trên máy ảo Android Emulator: dùng IP định tuyến `http://10.0.2.2:8088`.  
> • Khi chạy trên điện thoại thật bằng Expo Go: có màn hình Cài đặt (`SettingsScreen`) để người dùng nhập địa chỉ IP Wi-Fi của máy tính (ví dụ `http://192.168.1.5:8088`)."

---

### ❓ Câu 11: Nếu dữ liệu nhân viên bị thiếu (ví dụ nhân viên mới chưa có đủ số năm thăng chức hoặc số năm với quản lý), hệ thống có bị lỗi (Crash) không?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, trong cả Backend Java lẫn Python AI Service, em đều đã xử lý cơ chế giá trị mặc định an toàn (Fallbacks). Các trường số nếu bị `null` sẽ tự động được gán giá trị mặc định hợp lý (ví dụ: `years_since_promotion = 0`, `income_level = 3`), đồng thời câu truy vấn đều được bọc trong khối `try-catch` và transactional chặt chẽ."

---

### ❓ Câu 12: Đồ án này có thể thương mại hóa hoặc áp dụng thực tế cho doanh nghiệp như thế nào?
> **Trả lời:**  
> "Dạ thưa Thầy/Cô, hệ thống hoàn toàn có thể triển khai áp dụng thực tế cho các doanh nghiệp vừa và nhỏ (SMEs). Doanh nghiệp có thể tiết kiệm hàng trăm triệu đồng chi phí mua phần mềm HRM ngoại nhập đắt đỏ mà vẫn sở hữu tính năng cao cấp: Ma trận 9-Box đánh giá nhân tài và AI phát hiện sớm nhân sự có nguy cơ nghỉ việc để kịp thời giữ chân."

