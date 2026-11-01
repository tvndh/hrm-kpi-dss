// Cấu hình URL Backend Spring Boot
// Khi chạy trên máy thật cùng mạng Wifi, thay localhost bằng IP máy tính (ví dụ http://192.168.1.5:8088)
// Khi chạy Android Emulator, dùng http://10.0.2.2:8088
let BASE_URL = 'http://10.0.2.2:8088';

export const getBaseUrl = () => BASE_URL;

export const setBaseUrl = (url) => {
  BASE_URL = url.trim();
};

export const api = {
  // Lấy danh sách nhân viên
  getAllEmployees: async () => {
    const res = await fetch(`${BASE_URL}/api/nhan-vien/danh-sach`);
    if (!res.ok) throw new Error('Không thể tải danh sách nhân viên');
    return res.json();
  },

  // Lấy kết quả DSS theo nhân viên
  getDssResult: async (idNhanVien) => {
    const res = await fetch(`${BASE_URL}/api/dss/nhan-vien/${idNhanVien}`);
    if (!res.ok) return null;
    return res.json();
  },

  // Lấy dự báo nghỉ việc AI
  getAiRisk: async (idNhanVien) => {
    const res = await fetch(`${BASE_URL}/api/ai/du-bao/nhan-vien/${idNhanVien}`);
    if (!res.ok) return null;
    return res.json();
  },

  // Lấy danh sách tiêu chí KPI
  getKpiList: async () => {
    const res = await fetch(`${BASE_URL}/api/kpi/tieu-chi`);
    if (!res.ok) return [];
    return res.json();
  },

  // Gửi chấm điểm tự đánh giá
  submitSelfEvaluation: async (payload) => {
    // Giả lập lưu thành công và cập nhật điểm
    return { success: true, message: 'Đã nộp phiếu tự đánh giá thành công!' };
  }
};
