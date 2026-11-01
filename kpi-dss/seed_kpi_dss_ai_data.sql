-- =========================================================================
-- SCRIPT NẠP DỮ LIỆU MẪU KPI, KHUNG NĂNG LỰC, ĐÁNH GIÁ, DSS & DỰ BÁO AI
-- Tuân thủ Check Constraints: 
-- - loai_nang_luc: 'Knowledge', 'Skill', 'Attitude'
-- - muc_do_canh_bao: 'LOW', 'MEDIUM', 'HIGH'
-- =========================================================================

USE HRM_KPI_DSS;
GO

SET NOCOUNT ON;

-- 1. NẠP KHUNG NĂNG LỰC (chuẩn ASK: Knowledge, Skill, Attitude)
DELETE FROM dbo.khung_nang_luc;

INSERT INTO dbo.khung_nang_luc (id_chuc_vu, ten_nang_luc, loai_nang_luc, diem_chuan) VALUES
-- Trưởng phòng CNTT (1)
(1, N'Năng lực Quản lý & Lãnh đạo đội ngũ', 'Skill', 4.50),
(1, N'Tư duy Kiến trúc Hệ thống & Công nghệ', 'Knowledge', 4.50),
(1, N'Trách nhiệm & Thái độ gương mẫu', 'Attitude', 4.50),

-- Senior Dev (2)
(2, N'Kỹ năng Lập trình, Clean Code & Design Patterns', 'Skill', 4.50),
(2, N'Kiến thức Tối ưu hóa hiệu năng & Bảo mật', 'Knowledge', 4.00),
(2, N'Tinh thần hợp tác & Kèm cặp Junior', 'Attitude', 4.00),

-- Chuyên viên HR (3)
(3, N'Kỹ năng Phỏng vấn & Đánh giá nhân tài', 'Skill', 4.00),
(3, N'Kiến thức Luật Lao động & Chế độ phúc lợi', 'Knowledge', 4.50),
(3, N'Lắng nghe, Thấu cảm & Tôn trọng đồng nghiệp', 'Attitude', 4.50),

-- Junior Dev (4)
(4, N'Kiến thức OOP, Cấu trúc dữ liệu & SQL', 'Knowledge', 3.50),
(4, N'Kỹ năng Debug và sử dụng Git/Tools', 'Skill', 3.50),
(4, N'Tinh thần học hỏi & Tiếp thu phản hồi', 'Attitude', 4.00),

-- QA/QC (5)
(5, N'Kiến thức Thiết kế Test Case & Kịch bản kiểm thử', 'Knowledge', 4.00),
(5, N'Kỹ năng Kiểm thử tự động & Báo cáo lỗi', 'Skill', 4.00),
(5, N'Tỉ mỉ, cẩn trọng & Kiên trì với chất lượng', 'Attitude', 4.50),

-- Chuyên viên Marketing (7)
(7, N'Kỹ năng Sáng tạo nội dung & Copywriting', 'Skill', 4.00),
(7, N'Kiến thức Phân tích số liệu chiến dịch Marketing', 'Knowledge', 3.50),
(7, N'Năng động, thích ứng nhanh với xu hướng', 'Attitude', 4.00),

-- Sales (8)
(8, N'Kỹ năng Thuyết phục & Đàm phán chốt hợp đồng', 'Skill', 4.50),
(8, N'Kiến thức sản phẩm và thị trường', 'Knowledge', 4.00),
(8, N'Nhiệt huyết, kiên trì theo đuổi mục tiêu', 'Attitude', 4.50);

-- 2. NẠP DỮ LIỆU DỰ BÁO NGHỈ VIỆC BẰNG MÔ HÌNH AI (du_bao_nghi_viec_ai)
DELETE FROM dbo.du_bao_nghi_viec_ai;

INSERT INTO dbo.du_bao_nghi_viec_ai (id_nhan_vien, ty_le_rui_ro, muc_do_canh_bao, nguyen_nhan_chinh, ngay_du_bao) VALUES
(48, 88.50, 'HIGH', N'Khoảng cách đi làm xa (26km), 4 năm cùng chức vụ chưa được thăng chức', GETDATE()),
(49, 82.00, 'HIGH', N'Lương cơ bản thấp, khoảng cách xa (19km), áp lực chỉ tiêu kinh doanh', GETDATE()),
(50, 78.30, 'HIGH', N'Tình trạng ly hôn, khoảng cách xa (15km), thời gian làm việc với quản lý ngắn', GETDATE()),
(44, 74.00, 'HIGH', N'Đi làm xa (28km), số năm tại công ty 6 năm chưa thăng tiến', GETDATE()),
(34, 69.50, 'MEDIUM', N'Khoảng cách 25km, 6 năm tại công ty', GETDATE()),
(45, 62.00, 'MEDIUM', N'Tuổi 35, 8 năm tại công ty, 4 năm cùng chức vụ', GETDATE()),
(25, 58.00, 'MEDIUM', N'Nhân viên mới, khoảng cách đi làm 18km', GETDATE()),
(12, 54.50, 'MEDIUM', N'Khoảng cách 20km, chỉ tiêu kỹ thuật cao', GETDATE()),
(1,  15.00, 'LOW', N'Lãnh đạo chủ chốt, phúc lợi cao, gắn bó 6 năm', GETDATE()),
(2,  18.20, 'LOW', N'Trưởng phòng, hiệu suất xuất sắc, phúc lợi tốt', GETDATE()),
(3,  22.00, 'LOW', N'Lộ trình thăng tiến rõ ràng, làm việc gần (8km)', GETDATE()),
(4,  25.00, 'LOW', N'Kinh nghiệm 11 năm, ổn định gia đình', GETDATE()),
(6,  19.50, 'LOW', N'Hiệu suất ổn định, môi trường làm việc tốt', GETDATE()),
(10, 14.00, 'LOW', N'Gắn bó lâu năm, phúc lợi cao', GETDATE()),
(19, 16.50, 'LOW', N'Trưởng phòng Marketing, gắn bó lâu dài', GETDATE());

PRINT N'==> ĐÃ NẠP THÀNH CÔNG KHUNG NĂNG LỰC & DỰ BÁO NGHỈ VIỆC AI!';
GO
