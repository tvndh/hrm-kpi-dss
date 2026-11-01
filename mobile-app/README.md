# HRM KPI DSS - Ứng Dụng Di Động React Native (Mobile App)

Ứng dụng di động dành riêng cho **Nhân viên (Employee)** trong hệ thống **HRM KPI DSS**, cho phép nhân sự theo dõi hồ sơ, tự đánh giá hiệu suất KPI, kiểm tra khung năng lực và nhận phản hồi quyết sách từ ma trận 9-Box.

---

## 📱 Các Màn Hình Chức Năng

1. **Đăng nhập (`LoginScreen`):**
   - Đăng nhập bằng tên đăng nhập (ví dụ: `nv01`, `nv02`, `dev1`, `admin`) và mật khẩu (mặc định: `123456`).
   - Tự động đồng bộ và xác thực với Backend Spring Boot (`http://localhost:8088/api/nhan-vien/danh-sach`).
2. **Trang chủ Dashboard (`HomeScreen`):**
   - Thẻ thông tin cá nhân, thâm niên công ty, số năm cùng chức vụ, khoảng cách đi làm.
   - Thẻ kết quả phân loại Ma trận 9-Box (`Star`, `Core Player`, `High Potential`...).
   - Chỉ số cảnh báo rủi ro nghỉ việc từ mô hình Machine Learning AI (nếu có).
3. **Tự Chấm Điểm KPI & Năng Lực (`EvaluationScreen`):**
   - Đánh giá chỉ số hiệu suất Sprint / Deadline, chất lượng công việc (1 - 5 sao).
   - Đánh giá khung năng lực ASK: Kiến thức (Knowledge) - Kỹ năng (Skill) - Thái độ (Attitude).
   - Nhập nhận xét cá nhân và nộp phiếu xét duyệt.
4. **Báo Cáo Đánh Giá & DSS (`ResultScreen`):**
   - Bảng điểm chi tiết: Điểm KPI, Điểm Năng lực, Điểm tổng kết (60% KPI + 40% Năng lực).
   - Lời khuyên chính sách nhân tài do Bộ máy DSS đề xuất (thăng chức, khen thưởng, tăng lương...).
5. **Cài đặt kết nối (`SettingsScreen`):**
   - Dễ dàng thay đổi IP Backend khi chạy trên máy ảo Android (`10.0.2.2:8088`) hoặc điện thoại thật cùng mạng Wi-Fi.

---

## 🚀 Hướng Dẫn Cài Đặt & Khởi Chạy

### Yêu cầu:
- Đã cài đặt **Node.js** (v18 trở lên).
- Cài đặt ứng dụng **Expo Go** trên điện thoại (tải miễn phí trên App Store / Google Play).

### Bước 1: Cài đặt thư viện
Mở Terminal tại thư mục `mobile-app` và chạy:
```bash
cd mobile-app
npm install
```

### Bước 2: Khởi chạy ứng dụng
Chạy lệnh:
```bash
npm start
# hoặc chạy file run_mobile.bat
```

### Bước 3: Trải nghiệm trên thiết bị
- **Trên điện thoại thật:** Mở ứng dụng **Expo Go** và quét mã QR hiển thị trên Terminal.
- **Trên trình duyệt Web:** Nhấn phím **`w`** trên terminal để mở phiên bản Web.
- **Trên máy ảo Android:** Nhấn phím **`a`** trên terminal.

