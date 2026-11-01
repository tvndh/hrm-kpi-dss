-- =========================================================================
-- SCRIPT THÊM 50 NHÂN VIÊN MẪU CHO HỆ THỐNG HRM KPI DSS
-- Cơ sở dữ liệu: HRM_KPI_DSS
-- =========================================================================

USE HRM_KPI_DSS;
GO

SET NOCOUNT ON;

-- Xóa dữ liệu mẫu cũ nếu có cùng username nv01 -> nv50
DELETE FROM dbo.nhan_vien WHERE ten_dang_nhap LIKE 'nv[0-9][0-9]';

INSERT INTO dbo.nhan_vien (
    ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai,
    gioi_tinh, tuoi, tinh_trang_hon_nhan, trinh_do_hoc_van, khoang_cach_di_lam,
    id_phong_ban, id_chuc_vu, id_vai_tro, ngay_vao_lam,
    tong_nam_kinh_nghiem, nam_tai_cong_ty, nam_cung_chuc_vu, nam_tu_khi_thang_chuc, nam_voi_quan_ly_hien_tai,
    trang_thai_hoat_dong
) VALUES
-- Khối Công nghệ thông tin (id_phong_ban = 1)
(N'nv01', '123456', N'Hoàng Văn Thắng', 'nv01@company.com', '0901234001', N'Nam', 31, N'Đã kết hôn', 3, 6, 1, 2, 3, '2020-04-15', 8, 6, 3, 2, 4, 1),
(N'nv02', '123456', N'Phạm Minh Tuấn', 'nv02@company.com', '0901234002', N'Nam', 24, N'Độc thân', 3, 12, 1, 4, 3, '2024-02-10', 2, 2, 2, 0, 2, 1),
(N'nv03', '123456', N'Đỗ Thu Hà', 'nv03@company.com', '0901234003', N'Nữ', 27, N'Độc thân', 3, 8, 1, 5, 3, '2022-08-01', 5, 4, 2, 1, 3, 1),
(N'nv04', '123456', N'Vũ Đức Nam', 'nv04@company.com', '0901234004', N'Nam', 34, N'Đã kết hôn', 4, 15, 1, 2, 3, '2019-03-20', 11, 7, 4, 3, 5, 1),
(N'nv05', '123456', N'Nguyễn Quang Dũng', 'nv05@company.com', '0901234005', N'Nam', 23, N'Độc thân', 3, 5, 1, 4, 3, '2025-06-15', 1, 1, 1, 0, 1, 1),
(N'nv06', '123456', N'Trịnh Bích Ngọc', 'nv06@company.com', '0901234006', N'Nữ', 28, N'Đã kết hôn', 3, 18, 1, 5, 3, '2021-11-05', 6, 5, 3, 1, 4, 1),
(N'nv07', '123456', N'Lương Hoàng Phúc', 'nv07@company.com', '0901234007', N'Nam', 29, N'Độc thân', 3, 4, 1, 2, 3, '2021-05-12', 7, 5, 2, 2, 3, 1),
(N'nv08', '123456', N'Đinh Tuấn Kiệt', 'nv08@company.com', '0901234008', N'Nam', 25, N'Độc thân', 2, 9, 1, 4, 3, '2023-09-01', 3, 3, 3, 0, 2, 1),
(N'nv09', '123456', N'Phan Thanh Mai', 'nv09@company.com', '0901234009', N'Nữ', 26, N'Độc thân', 3, 11, 1, 5, 3, '2023-01-15', 4, 3, 2, 1, 2, 1),
(N'nv10', '123456', N'Bùi Minh Trí', 'nv10@company.com', '0901234010', N'Nam', 36, N'Đã kết hôn', 4, 7, 1, 2, 3, '2018-01-10', 13, 8, 4, 3, 6, 1),
(N'nv11', '123456', N'Đoàn Văn Hậu', 'nv11@company.com', '0901234011', N'Nam', 24, N'Độc thân', 3, 14, 1, 4, 3, '2024-07-20', 2, 2, 2, 0, 1, 1),
(N'nv12', '123456', N'Ngô Bảo Châu', 'nv12@company.com', '0901234012', N'Nam', 33, N'Đã kết hôn', 4, 20, 1, 2, 3, '2020-09-01', 10, 6, 3, 2, 4, 1),
(N'nv13', '123456', N'Lý Thùy Trang', 'nv13@company.com', '0901234013', N'Nữ', 25, N'Độc thân', 3, 6, 1, 5, 3, '2024-03-01', 3, 2, 2, 0, 2, 1),
(N'nv14', '123456', N'Dương Gia Bảo', 'nv14@company.com', '0901234014', N'Nam', 28, N'Độc thân', 3, 10, 1, 2, 3, '2022-04-18', 6, 4, 2, 1, 3, 1),
(N'nv15', '123456', N'Hà Anh Tuấn', 'nv15@company.com', '0901234015', N'Nam', 23, N'Độc thân', 2, 16, 1, 4, 3, '2025-01-10', 1, 1, 1, 0, 1, 1),
(N'nv16', '123456', N'Tạ Mỹ Linh', 'nv16@company.com', '0901234016', N'Nữ', 30, N'Đã kết hôn', 3, 8, 1, 5, 3, '2021-02-15', 7, 5, 3, 1, 4, 1),
(N'nv17', '123456', N'Nguyễn Trọng Đại', 'nv17@company.com', '0901234017', N'Nam', 27, N'Độc thân', 3, 13, 1, 4, 3, '2022-10-01', 5, 4, 3, 0, 3, 1),
(N'nv18', '123456', N'Trần Quốc Toản', 'nv18@company.com', '0901234018', N'Nam', 38, N'Đã kết hôn', 4, 22, 1, 1, 2, '2017-06-01', 15, 9, 5, 4, 7, 1),

-- Khối Bán hàng & Marketing (id_phong_ban = 2)
(N'nv19', '123456', N'Bùi Quang Huy', 'nv19@company.com', '0901234019', N'Nam', 37, N'Đã kết hôn', 3, 5, 2, 6, 2, '2019-01-15', 14, 7, 4, 3, 5, 1),
(N'nv20', '123456', N'Nguyễn Thị Mai', 'nv20@company.com', '0901234020', N'Nữ', 29, N'Đã kết hôn', 3, 7, 2, 7, 3, '2021-03-10', 7, 5, 3, 1, 4, 1),
(N'nv21', '123456', N'Lê Minh Khôi', 'nv21@company.com', '0901234021', N'Nam', 26, N'Độc thân', 3, 12, 2, 8, 3, '2023-04-05', 4, 3, 2, 0, 2, 1),
(N'nv22', '123456', N'Hoàng Yến Nhi', 'nv22@company.com', '0901234022', N'Nữ', 24, N'Độc thân', 3, 3, 2, 7, 3, '2024-05-15', 2, 2, 2, 0, 1, 1),
(N'nv23', '123456', N'Phạm Gia Huy', 'nv23@company.com', '0901234023', N'Nam', 32, N'Đã kết hôn', 3, 15, 2, 8, 3, '2020-07-20', 9, 6, 3, 2, 4, 1),
(N'nv24', '123456', N'Đỗ Cẩm Ly', 'nv24@company.com', '0901234024', N'Nữ', 28, N'Đã kết hôn', 3, 9, 2, 7, 3, '2022-02-12', 6, 4, 2, 1, 3, 1),
(N'nv25', '123456', N'Võ Nhật Nam', 'nv25@company.com', '0901234025', N'Nam', 25, N'Độc thân', 2, 18, 2, 8, 3, '2024-08-01', 3, 2, 2, 0, 1, 1),
(N'nv26', '123456', N'Trần Hương Giang', 'nv26@company.com', '0901234026', N'Nữ', 31, N'Đã kết hôn', 4, 6, 2, 7, 3, '2020-10-10', 8, 6, 3, 2, 4, 1),
(N'nv27', '123456', N'Ngô Phương Linh', 'nv27@company.com', '0901234027', N'Nữ', 23, N'Độc thân', 3, 11, 2, 8, 3, '2025-02-18', 1, 1, 1, 0, 1, 1),
(N'nv28', '123456', N'Lê Đăng Khoa', 'nv28@company.com', '0901234028', N'Nam', 35, N'Đã kết hôn', 3, 8, 2, 8, 3, '2019-05-01', 12, 7, 4, 2, 5, 1),
(N'nv29', '123456', N'Chu Kim Ngân', 'nv29@company.com', '0901234029', N'Nữ', 27, N'Độc thân', 3, 14, 2, 7, 3, '2022-09-15', 5, 4, 2, 1, 3, 1),
(N'nv30', '123456', N'Huỳnh Quốc Việt', 'nv30@company.com', '0901234030', N'Nam', 26, N'Độc thân', 2, 5, 2, 8, 3, '2023-11-20', 4, 3, 2, 0, 2, 1),
(N'nv31', '123456', N'Dương Thảo Vy', 'nv31@company.com', '0901234031', N'Nữ', 29, N'Độc thân', 3, 10, 2, 7, 3, '2021-08-10', 7, 5, 3, 2, 3, 1),
(N'nv32', '123456', N'Đặng Thanh Phong', 'nv32@company.com', '0901234032', N'Nam', 30, N'Đã kết hôn', 3, 17, 2, 8, 3, '2021-06-01', 8, 5, 3, 1, 4, 1),
(N'nv33', '123456', N'Phan Thúy Hằng', 'nv33@company.com', '0901234033', N'Nữ', 25, N'Độc thân', 3, 4, 2, 7, 3, '2024-01-15', 3, 2, 2, 0, 1, 1),
(N'nv34', '123456', N'Trịnh Xuân Hinh', 'nv34@company.com', '0901234034', N'Nam', 33, N'Đã kết hôn', 3, 25, 2, 8, 3, '2020-04-10', 10, 6, 3, 1, 4, 1),

-- Khối Nhân sự (id_phong_ban = 3)
(N'nv35', '123456', N'Trương Mỹ Hoa', 'nv35@company.com', '0901234035', N'Nữ', 40, N'Đã kết hôn', 4, 6, 3, 9, 2, '2017-09-01', 17, 9, 5, 3, 7, 1),
(N'nv36', '123456', N'Đặng Thùy Dung', 'nv36@company.com', '0901234036', N'Nữ', 30, N'Đã kết hôn', 3, 9, 3, 3, 3, '2021-04-15', 8, 5, 3, 2, 4, 1),
(N'nv37', '123456', N'Nguyễn Hoàng Lan', 'nv37@company.com', '0901234037', N'Nữ', 26, N'Độc thân', 3, 7, 3, 10, 3, '2023-03-20', 4, 3, 2, 0, 2, 1),
(N'nv38', '123456', N'Lê Văn Bình', 'nv38@company.com', '0901234038', N'Nam', 28, N'Đã kết hôn', 3, 13, 3, 3, 3, '2022-06-10', 6, 4, 2, 1, 3, 1),
(N'nv39', '123456', N'Bùi Kiều Oanh', 'nv39@company.com', '0901234039', N'Nữ', 24, N'Độc thân', 3, 5, 3, 10, 3, '2024-09-05', 2, 2, 2, 0, 1, 1),
(N'nv40', '123456', N'Phạm Ngọc Sơn', 'nv40@company.com', '0901234040', N'Nam', 34, N'Đã kết hôn', 4, 16, 3, 3, 3, '2019-11-12', 11, 7, 4, 2, 5, 1),
(N'nv41', '123456', N'Vũ Thị Hồng', 'nv41@company.com', '0901234041', N'Nữ', 27, N'Độc thân', 3, 8, 3, 10, 3, '2023-08-15', 5, 3, 2, 1, 2, 1),
(N'nv42', '123456', N'Hồ Bảo Trâm', 'nv42@company.com', '0901234042', N'Nữ', 29, N'Đã kết hôn', 3, 11, 3, 3, 3, '2021-12-01', 7, 5, 3, 1, 4, 1),
(N'nv43', '123456', N'Mai Văn Toàn', 'nv43@company.com', '0901234043', N'Nam', 25, N'Độc thân', 2, 14, 3, 10, 3, '2024-04-10', 3, 2, 2, 0, 1, 1),

-- Các nhân viên đa dạng khác (để test bộ lọc trạng thái và phân tích DSS Attrition)
(N'nv44', '123456', N'Lâm Khánh Chi', 'nv44@company.com', '0901234044', N'Nữ', 31, N'Ly hôn', 3, 28, 1, 2, 3, '2020-03-15', 9, 6, 3, 1, 3, 1),
(N'nv45', '123456', N'Ngô Tấn Tài', 'nv45@company.com', '0901234045', N'Nam', 35, N'Đã kết hôn', 3, 21, 2, 8, 3, '2018-08-20', 12, 8, 4, 2, 5, 1),
(N'nv46', '123456', N'Văn Tiến Dũng', 'nv46@company.com', '0901234046', N'Nam', 23, N'Độc thân', 3, 2, 1, 4, 3, '2025-05-01', 1, 1, 1, 0, 1, 1),
(N'nv47', '123456', N'Trần Thùy Chi', 'nv47@company.com', '0901234047', N'Nữ', 28, N'Độc thân', 4, 7, 2, 7, 3, '2022-01-10', 6, 4, 2, 1, 3, 1),
-- 3 nhân sự tạm nghỉ việc / chuyển công tác (trang_thai_hoat_dong = 0) để test bộ lọc
(N'nv48', '123456', N'Lê Minh Chiến', 'nv48@company.com', '0901234048', N'Nam', 33, N'Đã kết hôn', 3, 26, 1, 2, 3, '2019-10-01', 10, 7, 4, 2, 4, 0),
(N'nv49', '123456', N'Đỗ Tuyết Nhung', 'nv49@company.com', '0901234049', N'Nữ', 26, N'Độc thân', 3, 19, 2, 8, 3, '2023-05-15', 4, 3, 2, 0, 2, 0),
(N'nv50', '123456', N'Phạm Hữu Phát', 'nv50@company.com', '0901234050', N'Nam', 29, N'Ly hôn', 3, 15, 3, 3, 3, '2021-07-20', 7, 5, 3, 1, 3, 0);

PRINT N'==> ĐÃ THÊM THÀNH CÔNG 50 NHÂN VIÊN MẪU VÀO BẢNG NHAN_VIEN!';
GO
