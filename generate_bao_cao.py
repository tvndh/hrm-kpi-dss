"""
Script sinh file Word Báo Cáo Đồ Án Hoàn Chỉnh chuẩn học thuật
Đề tài: Hệ Thống Quản Trị Nhân Sự & Hỗ Trợ Ra Quyết Định Đánh Giá KPI (HRM KPI DSS)
"""

import sys
if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

import docx
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_ALIGN_VERTICAL
from docx.oxml import parse_xml, OxmlElement
from docx.oxml.ns import nsdecls, qn

def set_cell_background(cell, hex_color):
    tcPr = cell._tc.get_or_add_tcPr()
    tcPr.append(parse_xml(f'<w:shd {nsdecls("w")} w:fill="{hex_color}"/>'))

def set_cell_margins(cell, top=100, bottom=100, left=150, right=150):
    tcPr = cell._tc.get_or_add_tcPr()
    tcMar = parse_xml(f'<w:tcMar {nsdecls("w")}><w:top w:w="{top}" w:type="dxa"/><w:bottom w:w="{bottom}" w:type="dxa"/><w:left w:w="{left}" w:type="dxa"/><w:right w:w="{right}" w:type="dxa"/></w:tcMar>')
    tcPr.append(tcMar)

def create_report():
    doc = docx.Document()

    # Cấu hình lề trang (Top: 2cm, Bottom: 2cm, Left: 3cm, Right: 2cm)
    sections = doc.sections
    for section in sections:
        section.top_margin = Inches(0.79)
        section.bottom_margin = Inches(0.79)
        section.left_margin = Inches(1.18)
        section.right_margin = Inches(0.79)

    # Phong cách chuẩn Times New Roman
    style = doc.styles['Normal']
    font = style.font
    font.name = 'Times New Roman'
    font.size = Pt(13)
    font.color.rgb = RGBColor(15, 23, 42)

    # ==================== TRANG BÌA ====================
    p_univ = doc.add_paragraph()
    p_univ.alignment = WD_ALIGN_PARAGRAPH.CENTER
    r1 = p_univ.add_run("BỘ TÀI NGUYÊN VÀ MÔI TRƯỜNG\n")
    r1.font.size = Pt(13)
    r1.font.bold = True
    r2 = p_univ.add_run("TRƯỜNG ĐẠI HỌC TÀI NGUYÊN VÀ MÔI TRƯỜNG TP. HỒ CHÍ MINH\n")
    r2.font.size = Pt(14)
    r2.font.bold = True
    r3 = p_univ.add_run("KHOA CÔNG NGHỆ THÔNG TIN\n")
    r3.font.size = Pt(13)
    r3.font.bold = True
    r3_line = p_univ.add_run("-----------------------***-----------------------\n\n\n")

    p_title = doc.add_paragraph()
    p_title.alignment = WD_ALIGN_PARAGRAPH.CENTER
    r_type = p_title.add_run("BÁO CÁO ĐỒ ÁN CHUYÊN NGÀNH CÔNG NGHỆ THÔNG TIN\n\n")
    r_type.font.size = Pt(14)
    r_type.font.bold = True
    r_type.font.color.rgb = RGBColor(37, 99, 235)

    r_main = p_title.add_run("NGHIÊN CỨU & XÂY DỰNG HỆ THỐNG QUẢN TRỊ NHÂN SỰ TÍCH HỢP HỆ HỖ TRỢ RA QUYẾT ĐỊNH (DSS) VÀ TRÍ TUỆ NHÂN TẠO DỰ BÁO NGHỈ VIỆC\n(HRM KPI DSS ENTERPRISE)\n\n\n")
    r_main.font.size = Pt(17)
    r_main.font.bold = True
    r_main.font.color.rgb = RGBColor(15, 23, 42)

    p_info = doc.add_paragraph()
    p_info.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p_info.paragraph_format.left_indent = Inches(1.5)
    p_info.paragraph_format.line_spacing = 1.3
    
    r_info = p_info.add_run(
        "Giảng viên hướng dẫn:  TS. / ThS. Giảng Viên Phụ Trách\n"
        "Sinh viên thực hiện:    Tạ Vũ Nam / Nhóm Tác Giả\n"
        "Mã số sinh viên:        1250080234\n"
        "Ngành đào tạo:          Công nghệ Thông tin\n"
        "Khóa học:               2022 - 2026\n\n\n\n"
    )
    r_info.font.size = Pt(13)

    p_foot = doc.add_paragraph()
    p_foot.alignment = WD_ALIGN_PARAGRAPH.CENTER
    r_foot = p_foot.add_run("TP. HỒ CHÍ MINH, NĂM 2026")
    r_foot.font.size = Pt(12)
    r_foot.font.bold = True

    doc.add_page_break()

    # ==================== LỜI MỞ ĐẦU ====================
    h_intro = doc.add_heading("LỜI NÓI ĐẦU", level=1)
    h_intro.alignment = WD_ALIGN_PARAGRAPH.CENTER
    
    p = doc.add_paragraph(
        "Trong kỷ nguyên chuyển đổi số và cuộc cách mạng công nghiệp 4.0, nguồn nhân lực là tài sản quý giá nhất quyết định sự sống còn và năng lực cạnh tranh của bất kỳ doanh nghiệp nào. Tuy nhiên, các phần mềm quản lý nhân sự truyền thống hiện nay hầu như chỉ dừng lại ở mức độ lưu trữ hồ sơ nhân viên, chấm công và tính lương đơn thuần. Doanh nghiệp vẫn đang đối mặt với bài toán nan giải: Làm thế nào để đánh giá hiệu suất nhân viên một cách khách quan, đa chiều? Làm sao phát hiện sớm nhân tài để quy hoạch bổ nhiệm, cũng như dự báo trước làn sóng nghỉ việc (Employee Attrition) để chủ động giữ chân nhân sự then chốt?"
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    p = doc.add_paragraph(
        "Xuất phát từ nhu cầu thực tiễn đó, đề tài \"Nghiên cứu & Xây dựng Hệ thống Quản trị Nhân sự tích hợp Hệ Hỗ trợ Ra Quyết định (DSS) và Trí tuệ Nhân tạo Dự báo Nguy cơ Nghỉ việc (HRM KPI DSS)\" được thực hiện. Hệ thống không chỉ cung cấp nền tảng quản trị thông tin nhân sự toàn diện mà còn ứng dụng Ma trận 9-Box chuẩn quốc tế kết hợp mô hình Machine Learning (Random Forest) để tự động hóa việc phân loại nhân tài và cảnh báo rủi ro biến động nhân sự trong thời gian thực."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    # ==================== CHƯƠNG 1 ====================
    doc.add_page_break()
    doc.add_heading("CHƯƠNG 1: TỔNG QUAN ĐỀ TÀI & CƠ SỞ LÝ THUYẾT", level=1)

    doc.add_heading("1.1. Bối cảnh nghiên cứu & Tính cấp thiết", level=2)
    p = doc.add_paragraph(
        "Theo các báo cáo của Gartner và Deloitte về xu hướng quản trị nhân tài (HR Trends), chi phí để tuyển dụng và đào tạo một nhân sự mới thay thế cho người rời đi thường tiêu tốn từ 1.5 đến 2 lần mức lương năm của nhân viên đó. Do vậy, việc chủ động nhận diện nhân tài và phát hiện các dấu hiệu nghỉ việc sớm mang lại giá trị kinh tế to lớn cho tổ chức."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("1.2. Cơ sở lý thuyết về Quản trị Hiệu suất & Mô hình ASK", level=2)
    p = doc.add_paragraph(
        "Hệ thống áp dụng phương pháp đánh giá kết hợp giữa Chỉ số Hiệu suất Cốt lõi (Key Performance Indicators - KPI) và Khung Năng lực theo mô hình ASK (Attitude - Skill - Knowledge):\n"
        "• Knowledge (Kiến thức): Đo lường sự am hiểu chuyên môn, kiến trúc hệ thống và quy trình nghiệp vụ.\n"
        "• Skill (Kỹ năng): Đánh giá khả năng áp dụng kiến thức vào giải quyết vấn đề, tối ưu hiệu năng và xử lý sự cố.\n"
        "• Attitude (Thái độ): Thể hiện tinh thần trách nhiệm, văn hóa hợp tác, đạo đức nghề nghiệp và khả năng tiếp thu phản hồi."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("1.3. Hệ Hỗ trợ Ra Quyết định (DSS) và Ma trận 9-Box", level=2)
    p = doc.add_paragraph(
        "Ma trận 9-Box (McKinsey / GE 9-Box Matrix) là công cụ kinh điển được các tập đoàn Fortune 500 tin dùng. Ma trận phân loại nhân viên trên 2 trục tọa độ:\n"
        "• Trục Hoành (X - Performance): Điểm đo lường kết quả công việc KPI (Thấp < 3.0, Trung bình 3.0-3.9, Cao >= 4.0).\n"
        "• Trục Tung (Y - Potential / Competency): Điểm khung năng lực tiềm năng ASK (Thấp < 3.0, Trung bình 3.0-3.9, Cao >= 4.0).\n"
        "Từ đó chia đội ngũ thành 9 nhóm nhân tài riêng biệt: Star (Ngôi sao), High Performer, High Potential, Core Player (Trụ cột), Solid Professional, Effective, Enigma, Dilemma, và Risk (Nguy cơ đào thải) kèm theo các quyết sách hành động tương ứng."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("1.4. Bài toán Dự báo Nguy cơ Nghỉ việc (Employee Attrition Prediction)", level=2)
    p = doc.add_paragraph(
        "Bài toán được xây dựng dựa trên bộ dữ liệu nghiên cứu chuẩn của IBM HR Analytics (Kaggle). Mô hình khai thác các đặc trưng hành vi và thâm niên: Độ tuổi, khoảng cách từ nhà đến công ty, tổng số năm kinh nghiệm, số năm làm việc tại công ty, số năm giữ nguyên chức vụ, số năm kể từ lần thăng chức gần nhất, số năm làm việc với người quản lý trực tiếp và mức lương để dự báo xác suất rời bỏ doanh nghiệp."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    # ==================== CHƯƠNG 2 ====================
    doc.add_page_break()
    doc.add_heading("CHƯƠNG 2: PHÂN TÍCH & THIẾT KẾ HỆ THỐNG", level=1)

    doc.add_heading("2.1. Kiến trúc Tổng thể Hệ thống", level=2)
    p = doc.add_paragraph(
        "Hệ thống được thiết kế theo kiến trúc Microservices phân lớp hiện đại (3-Tier Architecture) nhằm đảm bảo tính độc lập, khả năng mở rộng linh hoạt và bảo mật cao:\n"
        "1. Tầng Trình diễn (Presentation Layer): Gồm Web Admin Dashboard (HTML5, CSS3, Chart.js) dành cho cấp Lãnh đạo/HR và Mobile App (React Native Expo) dành cho Nhân viên tự chấm điểm và theo dõi hồ sơ.\n"
        "2. Tầng Nghiệp vụ (Core Backend Service): Phát triển trên Java 21 và Spring Boot 4.x, chịu trách nhiệm xử lý toàn bộ logic nghiệp vụ nhân sự, quản lý đánh giá KPI và vận hành Bộ máy tính toán DSS Engine.\n"
        "3. Tầng Trí tuệ Nhân tạo (AI Microservice): Phát triển trên Python và FastAPI (cổng 8000), cung cấp REST API chạy mô hình Machine Learning Random Forest để phục vụ dự báo nguy cơ nghỉ việc.\n"
        "4. Tầng Dữ liệu (Database Layer): Hệ quản trị cơ sở dữ liệu Microsoft SQL Server lưu trữ 14 bảng quan hệ với các ràng buộc khóa ngoại (Foreign Keys) và Check Constraints chặt chẽ."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("2.2. Thiết kế Cơ sở Dữ liệu (Database Schema 14 Bảng)", level=2)
    p = doc.add_paragraph(
        "Cơ sở dữ liệu HRM_KPI_DSS được chuẩn hóa ở dạng chuẩn 3NF gồm 14 bảng dữ liệu:"
    )
    
    # Tạo bảng mô tả các table
    table = doc.add_table(rows=1, cols=3)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    hdr = table.rows[0].cells
    hdr[0].text = "Tên Bảng"
    hdr[1].text = "Khóa Chính"
    hdr[2].text = "Chức năng & Ý nghĩa nghiệp vụ"
    for cell in hdr:
        set_cell_background(cell, "2563EB")
        cell.paragraphs[0].runs[0].font.bold = True
        cell.paragraphs[0].runs[0].font.color.rgb = RGBColor(255, 255, 255)
        set_cell_margins(cell, top=120, bottom=120, left=150, right=150)

    tables_info = [
        ("dbo.nhan_vien", "id_nhan_vien", "Lưu trữ thông tin định danh, tài khoản, thâm niên, khoảng cách đi làm phục vụ DSS"),
        ("dbo.phong_ban", "id_phong_ban", "Danh mục phòng ban và trưởng phòng phụ trách"),
        ("dbo.chuc_vu", "id_chuc_vu", "Danh mục chức danh, cấp bậc và mức lương cơ bản"),
        ("dbo.vai_tro", "id_vai_tro", "Phân quyền người dùng (ADMIN, MANAGER, EMPLOYEE)"),
        ("dbo.ky_danh_gia", "id_ky_danh_gia", "Quản lý các đợt đánh giá hiệu suất định kỳ (Tháng / Quý / Năm)"),
        ("dbo.nhom_tieu_chi", "id_nhom_tieu_chi", "Phân nhóm tiêu chí: Hiệu suất công việc & Phát triển bản thân"),
        ("dbo.tieu_chi_kpi", "id_tieu_chi", "Định mức các chỉ số KPI cụ thể (Tiến độ, Chất lượng lỗi, Khối lượng)"),
        ("dbo.khung_nang_luc", "id_nang_luc", "Tiêu chuẩn năng lực theo mô hình ASK cho từng vị trí chức danh"),
        ("dbo.phieu_danh_gia", "id_phieu_danh_gia", "Phiếu đánh giá của nhân viên trong từng kỳ"),
        ("dbo.chi_tiet_danh_gia", "id_chi_tiet", "Điểm tự chấm, điểm quản lý chấm và trọng số cho từng chỉ số"),
        ("dbo.quy_tac_dss", "id_quy_tac", "Tập luật ánh xạ điểm số vào ma trận 9-Box và gợi ý chính sách"),
        ("dbo.ket_qua_dss", "id_ket_qua", "Kết quả tính toán tổng hợp, vị trí 9-Box và đề xuất chính sách nhân tài"),
        ("dbo.du_bao_nghi_viec_ai", "id_du_bao", "Kết quả phân tích xác suất rủi ro nghỉ việc (%) và nguyên nhân từ AI"),
        ("dbo.thong_bao", "id_thong_bao", "Lưu trữ tin nhắn thông báo kết quả đánh giá tới nhân sự")
    ]

    for t_name, pk, desc in tables_info:
        row = table.add_row().cells
        row[0].text = t_name
        row[1].text = pk
        row[2].text = desc
        for c in row:
            set_cell_margins(c, top=80, bottom=80, left=120, right=120)

    # ==================== CHƯƠNG 3 ====================
    doc.add_page_break()
    doc.add_heading("CHƯƠNG 3: CÀI ĐẶT & HIỆN THỰC HÓA HỆ THỐNG", level=1)

    doc.add_heading("3.1. Cài đặt Backend Spring Boot & Thuật toán DSS Engine", level=2)
    p = doc.add_paragraph(
        "Tầng Backend được tổ chức chặt chẽ theo mô hình MVC (Model-View-Controller) kết hợp Service Layer và Repository Pattern:\n"
        "• Thuật toán DSS Engine (DssService.java): Tiếp nhận toàn bộ điểm tự chấm và điểm quản lý chấm, tính toán điểm bình quân có trọng số của KPI và Khung năng lực. Điểm chung được tổng hợp theo công thức:\n"
        "   Điểm Tổng Hợp = 0.6 * Điểm KPI + 0.4 * Điểm Năng Lực ASK\n"
        "Sau đó, hệ thống thực hiện ánh xạ logic 2 chiều để xác định vị trí trong Ma trận 9-Box và xuất ra khuyến nghị quyết sách nhân tài tương ứng (ví dụ: Quy hoạch cán bộ nguồn, tăng lương 15-20%, đào tạo kỹ năng lãnh đạo hoặc thiết lập kế hoạch cải thiện hiệu suất PIP).\n"
        "• Cung cấp đầy đủ các API RESTful đã mở CORS hỗ trợ Web Admin và Mobile Client kết nối đồng thời."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("3.2. Cài đặt Trí tuệ Nhân tạo AI Microservice (FastAPI & Random Forest)", level=2)
    p = doc.add_paragraph(
        "Module AI được đóng gói thành dịch vụ độc lập trong thư mục ai-service/:\n"
        "• Huấn luyện mô hình (train_model.py): Sử dụng thuật toán Random Forest Classifier với 150 cây quyết định (n_estimators=150, max_depth=8) kết hợp tiền xử lý StandardScaler trong một Pipeline hoàn chỉnh.\n"
        "• Cung cấp API POST /predict và POST /batch-predict trên FastAPI: Nhận JSON thông tin nhân sự và trả về tỷ lệ rủi ro nghỉ việc (%), mức độ cảnh báo (LOW, MEDIUM, HIGH) cùng các nguyên nhân cốt lõi được trích xuất tự động.\n"
        "• Giao tiếp 2 chiều: Class AiService.java trong Spring Boot sử dụng java.net.http.HttpClient để gọi API sang Python và tự động đồng bộ kết quả vào cơ sở dữ liệu SQL Server."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("3.3. Hiện thực hóa Giao diện Web Admin & Radar Chart", level=2)
    p = doc.add_paragraph(
        "Giao diện Web Admin Dashboard (static/index.html) được thiết kế hiện đại, responsive với 5 phân hệ:\n"
        "1. Thẻ đo lường thống kê tổng quan (Tổng nhân sự, Đang hoạt động, Tuổi bình quân, Đã đánh giá 9-Box, Cảnh báo AI).\n"
        "2. Bảng quản lý danh sách 53 nhân viên có tìm kiếm realtime và modal xem chi tiết hồ sơ.\n"
        "3. Lưới Ma trận 9-Box tương tác trực quan hiển thị số lượng và danh sách nhân sự từng nhóm.\n"
        "4. Bảng phân tích rủi ro nghỉ việc AI Attrition với thanh tiến trình đo lường rủi ro (%)\n"
        "5. Bộ công cụ chấm điểm KPI và vẽ biểu đồ Radar Chart (Chart.js) mô phỏng 3 trụ cột năng lực ASK."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("3.4. Hiện thực hóa Ứng dụng Di động Mobile React Native", level=2)
    p = doc.add_paragraph(
        "Ứng dụng di động (thư mục mobile-app/) xây dựng trên nền tảng React Native Expo, cung cấp trải nghiệm thuận tiện cho nhân viên:\n"
        "• Màn hình Đăng nhập (LoginScreen): Xác thực trực tiếp với Backend API.\n"
        "• Màn hình Trang chủ (HomeScreen): Theo dõi thâm niên, vị trí 9-Box và rủi ro AI cá nhân.\n"
        "• Màn hình Tự đánh giá (EvaluationScreen): Tự chấm điểm các chỉ số KPI và năng lực ASK từ 1 đến 5 sao.\n"
        "• Màn hình Báo cáo DSS (ResultScreen): Xem kết quả đánh giá chính thức và phản hồi chính sách từ ban giám đốc.\n"
        "• Màn hình Cài đặt (SettingsScreen): Chuyển đổi địa chỉ IP Backend linh hoạt khi test trên máy ảo hoặc thiết bị thật."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    # ==================== CHƯƠNG 4 ====================
    doc.add_page_break()
    doc.add_heading("CHƯƠNG 4: KẾT QUẢ THỰC NGHIỆM & ĐÁNH GIÁ", level=1)

    doc.add_heading("4.1. Kết quả Dữ liệu Thực nghiệm (53 Nhân sự)", level=2)
    p = doc.add_paragraph(
        "Hệ thống đã nạp và kiểm thử thành công trên tập dữ liệu thực nghiệm 53 nhân viên mẫu bao gồm đầy đủ các phòng ban: Công nghệ thông tin (24 nhân sự), Bán hàng & Marketing (19 nhân sự) và Nhân sự (10 nhân sự). Các chỉ số về độ tuổi (bình quân 28 tuổi), khoảng cách đi làm (2 - 28 km), thâm niên (bình quân 6.3 năm) phản ánh sát với cơ cấu thực tế của một doanh nghiệp công nghệ."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("4.2. Đánh giá Hiệu năng Mô hình Trí tuệ Nhân tạo (Machine Learning)", level=2)
    p = doc.add_paragraph(
        "Mô hình Random Forest Classifier sau khi được huấn luyện trên tập dữ liệu phân loại nhị phân đã đạt được các chỉ số hiệu năng rất ấn tượng:\n"
        "• Độ chính xác tổng thể (Accuracy): 77.33%\n"
        "• Chỉ số diện tích dưới đường cong ROC (ROC-AUC Score): 0.828 (cho thấy khả năng phân tách rất tốt giữa nhóm nhân viên có nguy cơ nghỉ việc và nhóm ở lại).\n"
        "• Precision trên nhóm nguy cơ nghỉ việc (Class 1): 69%\n"
        "• F1-Score tổng thể: 0.75"
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("4.3. Đánh giá Kết quả Phân loại Ma trận 9-Box", level=2)
    p = doc.add_paragraph(
        "Thuật toán DSS đã phân loại chính xác nhân sự vào cả 9 nhóm của ma trận. Cụ thể đối với các nhân sự xuất sắc (Star) như Hoàng Văn Thắng (#nv01 - Điểm KPI: 4.6, Năng lực: 4.5), hệ thống đã xuất ra đề xuất chuẩn xác: \"Quy hoạch cán bộ nguồn cấp tốc, đề xuất thăng chức và tăng 15-20% lương cơ bản\". Ngược lại, đối với các nhân sự có hiệu suất và năng lực thấp (Risk), hệ thống đã đưa ra cảnh báo kịp thời nhằm xây dựng kế hoạch cải thiện hoặc cơ cấu lại."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    # ==================== CHƯƠNG 5 ====================
    doc.add_page_break()
    doc.add_heading("CHƯƠNG 5: KẾT LUẬN & HƯỚNG PHÁT TRIỂN", level=1)

    doc.add_heading("5.1. Những kết quả đã đạt được", level=2)
    p = doc.add_paragraph(
        "Sau quá trình nghiên cứu và thực hiện, đồ án đã hoàn thành trọn vẹn toàn bộ các mục tiêu đặt ra:\n"
        "1. Xây dựng hoàn chỉnh kiến trúc 3 tầng: Spring Boot Backend ↔ Python AI Service ↔ Web Dashboard & Mobile App.\n"
        "2. Thiết kế và chuẩn hóa cơ sở dữ liệu SQL Server với 14 bảng quan hệ chặt chẽ.\n"
        "3. Ứng dụng thành công mô hình Machine Learning Random Forest dự báo nguy cơ nghỉ việc với độ chính xác cao.\n"
        "4. Cài đặt hoàn thiện Bộ máy Hỗ trợ ra quyết định (DSS Engine) tự động phân loại nhân tài theo Ma trận 9-Box và gợi ý chính sách nhân sự thiết thực.\n"
        "5. Phát triển đồng bộ giao diện Web Admin trực quan với biểu đồ Radar Chart và ứng dụng di động Mobile React Native tiện ích cho nhân viên."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("5.2. Hạn chế còn tồn tại", level=2)
    p = doc.add_paragraph(
        "• Dữ liệu huấn luyện AI hiện tại được mô phỏng theo phân phối chuẩn của IBM HR Analytics, chưa được kiểm chứng trên tập dữ liệu lịch sử nhiều năm của một doanh nghiệp Việt Nam cụ thể.\n"
        "• Hệ thống hiện mới triển khai cơ chế xác thực tài khoản cơ bản, cần bổ sung thêm Spring Security và JWT (JSON Web Token) để tăng cường tính bảo mật phân quyền đa lớp."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    doc.add_heading("5.3. Hướng phát triển trong tương lai", level=2)
    p = doc.add_paragraph(
        "• Tích hợp chấm công nhận diện khuôn mặt (Face Recognition) qua Camera di động.\n"
        "• Ứng dụng Mô hình Ngôn ngữ Lớn (LLM / RAG) để xây dựng trợ lý AI Chatbot giải đáp tự động các thắc mắc về chính sách nhân sự, bảo hiểm và luật lao động cho nhân viên."
    )
    p.paragraph_format.line_spacing = 1.25
    p.paragraph_format.first_line_indent = Inches(0.5)

    # Lưu file
    file_path = "BAO_CAO_DO_AN_HRM_KPI_DSS.docx"
    doc.save(file_path)
    print(f"[SUCCESS] Da tao thanh cong file bao cao Word: {file_path}")

if __name__ == '__main__':
    create_report()
