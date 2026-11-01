import React, { useState } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  ActivityIndicator,
  Alert,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
} from 'react-native';
import { colors } from '../theme/colors';
import { api } from '../api/config';

export default function LoginScreen({ onLoginSuccess, onOpenSettings }) {
  const [username, setUsername] = useState('nv01');
  const [password, setPassword] = useState('123456');
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    if (!username.trim() || !password.trim()) {
      Alert.alert('Thông báo', 'Vui lòng nhập tài khoản và mật khẩu!');
      return;
    }

    setLoading(true);
    try {
      const list = await api.getAllEmployees();
      const user = list.find(
        (e) => e.tenDangNhap.toLowerCase() === username.trim().toLowerCase()
      );

      if (user) {
        // Kiểm tra mật khẩu (mặc định 123456)
        if (password === '123456' || user.matKhau === password) {
          onLoginSuccess(user);
        } else {
          Alert.alert('Thất bại', 'Mật khẩu không chính xác!');
        }
      } else {
        // Nếu không tìm thấy, cho phép đăng nhập demo với tài khoản này
        Alert.alert(
          'Đăng nhập thử nghiệm',
          `Không tìm thấy username '${username}' trên server. Đăng nhập với tư cách tài khoản mẫu #${username}?`,
          [
            { text: 'Hủy' },
            {
              text: 'Đồng ý',
              onPress: () =>
                onLoginSuccess({
                  idNhanVien: 99,
                  tenDangNhap: username,
                  hoTen: 'Nhân Viên Thử Nghiệm',
                  email: `${username}@company.com`,
                  gioiTinh: 'Nam',
                  tuoi: 28,
                  namTaiCongTy: 3,
                  tongNamKinhNghiem: 5,
                  khoangCachDiLam: 8,
                }),
            },
          ]
        );
      }
    } catch (err) {
      Alert.alert(
        'Lỗi Kết Nối Server',
        `Không thể kết nối tới Backend Spring Boot: ${err.message}.\n\nBạn có muốn vào màn hình Cài đặt để đổi địa chỉ IP của máy chủ không?`,
        [
          { text: 'Đổi IP Server', onPress: onOpenSettings },
          { text: 'Đăng nhập Offline', onPress: () => onLoginSuccess({
              idNhanVien: 1,
              tenDangNhap: 'nv01',
              hoTen: 'Hoàng Văn Thắng (Demo Offline)',
              email: 'nv01@company.com',
              gioiTinh: 'Nam',
              tuoi: 31,
              namTaiCongTy: 6,
              tongNamKinhNghiem: 8,
              khoangCachDiLam: 6,
            })
          }
        ]
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      style={styles.container}
    >
      <ScrollView contentContainerStyle={styles.scroll}>
        <View style={styles.header}>
          <View style={styles.logo}>
            <Text style={styles.logoText}>HR</Text>
          </View>
          <Text style={styles.title}>HRM KPI DSS Mobile</Text>
          <Text style={styles.subtitle}>Cổng Tự Đánh Giá & Theo Dõi Nhân Tài</Text>
        </View>

        <View style={styles.card}>
          <Text style={styles.label}>TÀI KHOẢN ĐĂNG NHẬP</Text>
          <TextInput
            style={styles.input}
            placeholder="Ví dụ: nv01, nv02, admin..."
            value={username}
            onChangeText={setUsername}
            autoCapitalize="none"
          />

          <Text style={styles.label}>MẬT KHẨU</Text>
          <TextInput
            style={styles.input}
            placeholder="Nhập mật khẩu (mặc định: 123456)"
            value={password}
            onChangeText={setPassword}
            secureTextEntry
          />

          <TouchableOpacity
            style={styles.button}
            onPress={handleLogin}
            disabled={loading}
          >
            {loading ? (
              <ActivityIndicator color="#fff" />
            ) : (
              <Text style={styles.buttonText}>Đăng Nhập Ngay</Text>
            )}
          </TouchableOpacity>

          <TouchableOpacity style={styles.settingsBtn} onPress={onOpenSettings}>
            <Text style={styles.settingsText}>⚙️ Cấu hình IP Server Backend</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
  },
  scroll: {
    flexGrow: 1,
    justifyContent: 'center',
    padding: 24,
  },
  header: {
    alignItems: 'center',
    marginBottom: 32,
  },
  logo: {
    width: 68,
    height: 68,
    borderRadius: 18,
    backgroundColor: colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 16,
    shadowColor: colors.primary,
    shadowOffset: { width: 0, height: 6 },
    shadowOpacity: 0.35,
    shadowRadius: 10,
    elevation: 8,
  },
  logoText: {
    fontSize: 28,
    fontWeight: '800',
    color: '#ffffff',
  },
  title: {
    fontSize: 22,
    fontWeight: '800',
    color: colors.text,
    marginBottom: 6,
  },
  subtitle: {
    fontSize: 13,
    color: colors.textMuted,
  },
  card: {
    backgroundColor: colors.card,
    borderRadius: 20,
    padding: 24,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.05,
    shadowRadius: 12,
    elevation: 3,
  },
  label: {
    fontSize: 11,
    fontWeight: '700',
    color: colors.textMuted,
    marginBottom: 8,
    letterSpacing: 0.5,
  },
  input: {
    borderWidth: 1,
    borderColor: colors.border,
    borderRadius: 12,
    padding: 14,
    fontSize: 15,
    marginBottom: 18,
    backgroundColor: colors.background,
  },
  button: {
    backgroundColor: colors.primary,
    padding: 16,
    borderRadius: 12,
    alignItems: 'center',
    marginTop: 8,
    shadowColor: colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.25,
    shadowRadius: 8,
    elevation: 4,
  },
  buttonText: {
    color: '#ffffff',
    fontSize: 16,
    fontWeight: '700',
  },
  settingsBtn: {
    marginTop: 18,
    alignItems: 'center',
  },
  settingsText: {
    fontSize: 13,
    color: colors.primary,
    fontWeight: '600',
  },
});

