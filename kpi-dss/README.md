# HRM KPI DSS - Hệ Thống Quản Trị Nhân Sự & Hỗ Trợ Ra Quyết Định Đánh Giá KPI

> **Đồ án:** Xây dựng hệ thống quản lý nhân sự kết hợp tính năng hỗ trợ ra quyết định (Decision Support System - DSS) và đánh giá hiệu suất KPI dựa trên phân tích chỉ số nhân sự.

---

## 📌 1. Giới thiệu Tổng quan

Dự án **HRM KPI DSS** là nền tảng quản trị nhân sự tập trung, cung cấp cả giao diện Web Dashboard trực quan và hệ thống RESTful API phục vụ cho nhiều client khác nhau (Web Admin, ứng dụng Mobile React Native).

Hệ thống được thiết kế hướng tới việc theo dõi các đặc trưng nâng cao của nhân sự (khoảng cách đi làm, thâm niên công ty, số năm tại vị trí, số năm từ lần thăng chức gần nhất, thời gian làm việc với quản lý hiện tại, trình độ học vấn...) nhằm phục vụ mô hình phân tích và hỗ trợ ra quyết định (DSS).

---

## 🛠️ 2. Công nghệ sử dụng

| Phân hệ | Công nghệ / Thư viện | Chi tiết |
| :--- | :--- | :--- |
| **Backend Core** | **Java 21**, **Spring Boot 4.x** | Nền tảng phát triển ứng dụng Java hiện đại |
| **Framework Web** | `spring-boot-starter-webmvc` | Xây dựng RESTful API chuẩn MVC |
| **ORM / Data** | `spring-boot-starter-data-jpa` (Hibernate) | Quản lý và thao tác cơ sở dữ liệu qua Entity |
| **Cơ sở dữ liệu** | **Microsoft SQL Server (MSSQL)** | Lưu trữ dữ liệu quan hệ nghiệp vụ nhân sự |
| **Tiện ích Code** | **Project Lombok** | Giảm thiểu boilerplate code (`@Data`, `@NoArgsConstructor`) |
| **Frontend UI** | **HTML5, CSS3, JavaScript (Fetch API)** | Dashboard responsive, giao diện hiện đại tích hợp sẵn |
| **Build Tool** | **Maven** (`mvnw` / `mvnw.cmd`) | Quản lý thư viện và vòng đời dự án |

---

## 📂 3. Cấu trúc thư mục dự án

```text
kpi-dss/
├── pom.xml                             # Cấu hình dependency Maven (Java 21, Spring Boot, MSSQL...)
├── mvnw / mvnw.cmd                     # Maven Wrapper chạy độc lập không cần cài đặt trước Maven
├── HELP.md                             # Hướng dẫn mặc định từ Spring Initializr
└── src/
    ├── main/
    │   ├── java/com/hrm/kpi_dss/
    │   │   ├── KpiDssApplication.java  # Class khởi chạy chính của Spring Boot
    │   │   ├── controller/             # Tầng Controller tiếp nhận HTTP Request & trả về JSON
    │   │   │   ├── NhanVienController.java # API danh sách & chi tiết nhân viên
    │   │   │   └── TestController.java     # API kiểm tra trạng thái hệ thống
    │   │   ├── entity/                 # Tầng Entity ánh xạ với bảng Database
    │   │   │   └── NhanVien.java           # Định nghĩa cấu trúc nhân viên và các chỉ số DSS
    │   │   ├── repository/             # Tầng Repository truy vấn cơ sở dữ liệu (Spring Data JPA)
    │   │   │   └── NhanVienRepository.java # Truy vấn theo ID, Tên đăng nhập, Email
    │   │   └── service/                # Tầng Service xử lý logic nghiệp vụ
    │   │       └── NhanVienService.java    # Nghiệp vụ xử lý dữ liệu nhân viên
    │   └── resources/
    │       ├── application.yml         # Cấu hình port (8088), kết nối DB SQL Server, JPA
    │       ├── static/
    │       │   └── index.html          # Web Dashboard quản trị nhân sự hoàn chỉnh
    │       └── templates/              # Thư mục chứa template SSR (nếu mở rộng)
    └── test/                           # Bộ kiểm thử đơn vị & tích hợp
```

---

## 🗄️ 4. Mô hình Dữ liệu (`NhanVien`)

Entity [`NhanVien`](file:///e:/demo_do_an/kpi-dss/src/main/java/com/hrm/kpi_dss/entity/NhanVien.java) ánh xạ với bảng `nhan_vien` trong SQL Server, bao gồm cả thông tin định danh và các chỉ số đặc trưng phục vụ bài toán DSS:

| Thuộc tính (Java) | Cột Database | Kiểu dữ liệu | Ý nghĩa |
| :--- | :--- | :--- | :--- |
| `idNhanVien` | `id_nhan_vien` | `Integer` (PK, Identity) | Mã định danh nhân viên |
| `tenDangNhap` | `ten_dang_nhap` | `VARCHAR(50)` (Unique) | Tên tài khoản hệ thống |
| `matKhau` | `mat_khau` | `VARCHAR` | Mật khẩu tài khoản |
| `hoTen` | `ho_ten` | `NVARCHAR(100)` | Họ và tên đầy đủ |
| `email` | `email` | `VARCHAR(100)` (Unique) | Email liên hệ |
| `soDienThoai` | `so_dien_thoai` | `VARCHAR(20)` | Số điện thoại |
| `gioiTinh` | `gioi_tinh` | `NVARCHAR(10)` | Giới tính |
| `tuoi` | `tuoi` | `Integer` | Độ tuổi |
| `tinhTrangHonNhan`| `tinh_trang_hon_nhan`| `NVARCHAR(20)` | Tình trạng hôn nhân |
| `trinhDoHocVan` | `trinh_do_hoc_van` | `Integer` | Cấp độ học vấn |
| `khoangCachDiLam`| `khoang_cach_di_lam`| `Integer` | Khoảng cách từ nhà đến công ty (km) |
| `idPhongBan` | `id_phong_ban` | `Integer` | Khóa ngoại tới Phòng Ban |
| `idChucVu` | `id_chuc_vu` | `Integer` | Khóa ngoại tới Chức Vụ |
| `idVaiTro` | `id_vai_tro` | `Integer` | Khóa ngoại tới Vai Trò |
| `ngayVaoLam` | `ngay_vao_lam` | `LocalDate` | Ngày chính thức vào công ty |
| `tongNamKinhNghiem` | `tong_nam_kinh_nghiem` | `Integer` | Tổng số năm kinh nghiệm làm việc |
| `namTaiCongTy` | `nam_tai_cong_ty` | `Integer` | Số năm đã làm việc tại công ty |
| `namCungChucVu` | `nam_cung_chuc_vu` | `Integer` | Số năm giữ nguyên vị trí/chức vụ |
| `namTuKhiThangChuc` | `nam_tu_khi_thang_chuc` | `Integer` | Số năm kể từ lần thăng chức gần nhất |
| `namVoiQuanLyHienTai` | `nam_voi_quan_ly_hien_tai` | `Integer` | Thời gian làm việc với quản lý hiện tại |
| `trangThaiHoatDong` | `trang_thai_hoat_dong` | `Boolean` | Trạng thái hoạt động (Active/Inactive) |

---

## 🌐 5. Danh sách RESTful API

Hệ thống đã cấu hình `@CrossOrigin(origins = "*")`, sẵn sàng kết nối trực tiếp với các ứng dụng Web/Mobile khác:

| Phương thức | Đường dẫn API | Mô tả | Trả về |
| :---: | :--- | :--- | :--- |
| `GET` | `/api/status` | Kiểm tra trạng thái máy chủ backend | JSON xác nhận backend & database đang hoạt động |
| `GET` | `/api/nhan-vien/danh-sach` | Lấy danh sách toàn bộ nhân viên | Danh sách mảng JSON đối tượng `NhanVien` |
| `GET` | `/api/nhan-vien/{id}` | Lấy chi tiết thông tin của 1 nhân viên | Đối tượng JSON `NhanVien` hoặc mã `404 Not Found` |

---

## 🖥️ 6. Giao diện Web Dashboard tích hợp

Khi khởi động ứng dụng, giao diện quản trị có sẵn tại địa chỉ root:
👉 `http://localhost:8088/`

### Các tính năng nổi bật trên giao diện:
1. **Thống kê tổng quan:** Thẻ đo lường tổng nhân viên, số lượng đang hoạt động, độ tuổi bình quân và trạng thái hệ thống.
2. **Tìm kiếm thời gian thực:** Lọc danh sách nhân viên ngay khi nhập tên, email, tài khoản hoặc số điện thoại.
3. **Hiển thị danh sách thông minh:** Phân loại màu trạng thái (Đang làm việc / Tạm khóa), tạo avatar chữ cái đầu tự động.
4. **Hộp thoại xem chi tiết (Modal DSS):** Bấm "Xem KPI / DSS" để xem trọn vẹn toàn bộ các chỉ số thâm niên, học vấn, khoảng cách đi làm phục vụ phân tích.

---

## 🚀 7. Hướng dẫn Cài đặt & Chạy ứng dụng

### Yêu cầu hệ thống:
- **Java:** JDK 21 trở lên.
- **Cơ sở dữ liệu:** Microsoft SQL Server (hoặc SQL Server Express).

### Bước 1: Cấu hình Cơ sở dữ liệu
Đảm bảo đã tạo cơ sở dữ liệu `HRM_KPI_DSS` trong SQL Server với tài khoản tương ứng trong file [`application.yml`](file:///e:/demo_do_an/kpi-dss/src/main/resources/application.yml):
```yaml
server:
  port: 8088

spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=HRM_KPI_DSS;encrypt=true;trustServerCertificate=true
    username: sa
    password: "123"
```
*(Nếu thông tin tài khoản SQL Server của bạn khác, vui lòng chỉnh sửa lại trong file này).*

### Bước 2: Khởi chạy Backend
Mở Terminal / PowerShell tại thư mục `kpi-dss` và chạy lệnh:

- **Trên Windows:**
```powershell
.\mvnw.cmd spring-boot:run
```

- **Trên Linux/macOS:**
```bash
./mvnw spring-boot:run
```

Hoặc chạy trực tiếp file [`KpiDssApplication.java`](file:///e:/demo_do_an/kpi-dss/src/main/java/com/hrm/kpi_dss/KpiDssApplication.java) trong IDE (IntelliJ IDEA, Eclipse, VS Code).

### Bước 3: Trải nghiệm ứng dụng
- Mở trình duyệt truy cập: **`http://localhost:8088/`**
- Thử nghiệm gọi API kiểm tra: **`http://localhost:8088/api/status`**
- Thử nghiệm lấy dữ liệu JSON: **`http://localhost:8088/api/nhan-vien/danh-sach`**

---

## 🔮 8. Định hướng phát triển tiếp theo (Roadmap)

- [ ] **Mô-đun Đánh giá KPI:** Thiết lập các chỉ số KPI theo phòng ban và theo dõi tiến độ hoàn thành định kỳ.
- [ ] **Mô-đun Hỗ trợ ra quyết định (DSS):** Tích hợp thuật toán dự đoán nguy cơ nghỉ việc (Employee Attrition Prediction) và đề xuất thăng chức/khen thưởng dựa trên các chỉ số thâm niên và KPI.
- [ ] **Bảo mật & Phân quyền:** Triển khai Spring Security kết hợp JSON Web Token (JWT) và phân quyền RBAC (Admin, Manager, Employee).
- [ ] **Ứng dụng Mobile Client:** Kết nối ứng dụng di động (React Native / Flutter) để nhân viên tự theo dõi KPI cá nhân và chấm công.

