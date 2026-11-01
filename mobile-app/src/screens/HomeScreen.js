import React, { useState, useEffect } from 'react';
import {
  StyleSheet,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  RefreshControl,
} from 'react-native';
import { colors } from '../theme/colors';
import { api } from '../api/config';

export default function HomeScreen({ user, onNavigate }) {
  const [dssResult, setDssResult] = useState(null);
  const [aiRisk, setAiRisk] = useState(null);
  const [refreshing, setRefreshing] = useState(false);

  const loadData = async () => {
    setRefreshing(true);
    try {
      const [dss, ai] = await Promise.all([
        api.getDssResult(user.idNhanVien),
        api.getAiRisk(user.idNhanVien),
      ]);
      setDssResult(dss);
      setAiRisk(ai);
    } catch (e) {
      console.log('Error loading home data:', e);
    } finally {
      setRefreshing(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [user]);

  const initials = user.hoTen
    ? user.hoTen.split(' ').pop().charAt(0).toUpperCase()
    : 'U';

  return (
    <ScrollView
      style={styles.container}
      refreshControl={
        <RefreshControl refreshing={refreshing} onRefresh={loadData} />
      }
    >
      {/* User Header */}
      <View style={styles.userCard}>
        <View style={styles.avatar}>
          <Text style={styles.avatarText}>{initials}</Text>
        </View>
        <View style={styles.userInfo}>
          <Text style={styles.userName}>{user.hoTen}</Text>
          <Text style={styles.userRole}>
            @{user.tenDangNhap} • #{user.idNhanVien}
          </Text>
          <Text style={styles.userEmail}>{user.email}</Text>
        </View>
      </View>

      {/* Metrics Grid */}
      <View style={styles.grid}>
        <View style={styles.card}>
          <Text style={styles.cardLabel}>THÂM NIÊN</Text>
          <Text style={styles.cardValue}>{user.namTaiCongTy ?? 0} năm</Text>
          <Text style={styles.cardHint}>Tại công ty</Text>
        </View>
        <View style={styles.card}>
          <Text style={styles.cardLabel}>KINH NGHIỆM</Text>
          <Text style={styles.cardValue}>
            {user.tongNamKinhNghiem ?? 0} năm
          </Text>
          <Text style={styles.cardHint}>Tổng số năm</Text>
        </View>
        <View style={styles.card}>
          <Text style={styles.cardLabel}>CÙNG CHỨC VỤ</Text>
          <Text style={styles.cardValue}>{user.namCungChucVu ?? 0} năm</Text>
          <Text style={styles.cardHint}>Vị trí hiện tại</Text>
        </View>
        <View style={styles.card}>
          <Text style={styles.cardLabel}>ĐI LÀM</Text>
          <Text style={styles.cardValue}>
            {user.khoangCachDiLam ?? 0} km
          </Text>
          <Text style={styles.cardHint}>Khoảng cách</Text>
        </View>
      </View>

      {/* 9-Box DSS Status Box */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>🎯 Kết Quả Phân Loại 9-Box (DSS)</Text>
        <View style={styles.dssCard}>
          {dssResult ? (
            <>
              <View style={styles.badgeRow}>
                <Text style={styles.dssBoxName}>{dssResult.viTri9Box}</Text>
              </View>
              <View style={styles.scoreRow}>
                <Text style={styles.scoreText}>
                  Điểm KPI: <Text style={styles.bold}>{dssResult.tongDiemKpi}</Text>
                </Text>
                <Text style={styles.scoreText}>
                  Năng lực: <Text style={styles.bold}>{dssResult.tongDiemNangLuc}</Text>
                </Text>
                <Text style={styles.scoreText}>
                  Tổng: <Text style={styles.boldPrimary}>{dssResult.diemDanhGiaChung}</Text>
                </Text>
              </View>
              <View style={styles.policyBox}>
                <Text style={styles.policyTitle}>💡 Định hướng nhân tài:</Text>
                <Text style={styles.policyText}>{dssResult.deXuat}</Text>
              </View>
            </>
          ) : (
            <View style={styles.emptyBox}>
              <Text style={styles.emptyText}>
                Bạn chưa có kết quả đánh giá 9-Box trong kỳ này.
              </Text>
              <TouchableOpacity
                style={styles.actionBtn}
                onPress={() => onNavigate('evaluate')}
              >
                <Text style={styles.actionBtnText}>Tự Chấm Điểm Ngay ✍️</Text>
              </TouchableOpacity>
            </View>
          )}
        </View>
      </View>

      {/* AI Attrition Warning */}
      {aiRisk && (
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>🤖 Phân Tích Rủi Ro (Mô Hình AI)</Text>
          <View style={styles.aiCard}>
            <View style={styles.aiHeader}>
              <Text style={styles.aiRiskTitle}>Xác suất nguy cơ nghỉ việc:</Text>
              <Text
                style={[
                  styles.aiRiskPct,
                  aiRisk.mucDoCanhBao === 'HIGH'
                    ? styles.textDanger
                    : aiRisk.mucDoCanhBao === 'MEDIUM'
                    ? styles.textWarning
                    : styles.textSuccess,
                ]}
              >
                {aiRisk.tyLeRuiRo}%
              </Text>
            </View>
            <Text style={styles.aiCause}>
              🔍 <Text style={styles.bold}>AI nhận diện:</Text>{' '}
              {aiRisk.nguyenNhanChinh || 'Các chỉ số ổn định, rủi ro thấp.'}
            </Text>
          </View>
        </View>
      )}

      {/* Quick Action Buttons */}
      <View style={styles.actionRow}>
        <TouchableOpacity
          style={[styles.bigBtn, { backgroundColor: colors.primary }]}
          onPress={() => onNavigate('evaluate')}
        >
          <Text style={styles.bigBtnIcon}>📝</Text>
          <Text style={styles.bigBtnText}>Tự Đánh Giá KPI</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[styles.bigBtn, { backgroundColor: colors.secondary }]}
          onPress={() => onNavigate('result')}
        >
          <Text style={styles.bigBtnIcon}>📊</Text>
          <Text style={styles.bigBtnText}>Xem Báo Cáo DSS</Text>
        </TouchableOpacity>
      </View>

      <View style={{ height: 40 }} />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
    padding: 16,
  },
  userCard: {
    backgroundColor: colors.card,
    borderRadius: 20,
    padding: 20,
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 16,
    shadowColor: '#000',
    shadowOpacity: 0.04,
    shadowRadius: 8,
    elevation: 2,
  },
  avatar: {
    width: 56,
    height: 56,
    borderRadius: 16,
    backgroundColor: colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 16,
  },
  avatarText: {
    fontSize: 24,
    fontWeight: '800',
    color: '#ffffff',
  },
  userInfo: {
    flex: 1,
  },
  userName: {
    fontSize: 18,
    fontWeight: '800',
    color: colors.text,
  },
  userRole: {
    fontSize: 13,
    color: colors.textMuted,
    marginTop: 2,
  },
  userEmail: {
    fontSize: 12,
    color: colors.primary,
    marginTop: 2,
  },
  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 12,
    marginBottom: 20,
  },
  card: {
    flex: 1,
    minWidth: '46%',
    backgroundColor: colors.card,
    borderRadius: 16,
    padding: 16,
    borderWidth: 1,
    borderColor: colors.border,
  },
  cardLabel: {
    fontSize: 10,
    fontWeight: '700',
    color: colors.textMuted,
  },
  cardValue: {
    fontSize: 18,
    fontWeight: '800',
    color: colors.text,
    marginVertical: 4,
  },
  cardHint: {
    fontSize: 11,
    color: colors.textMuted,
  },
  section: {
    marginBottom: 20,
  },
  sectionTitle: {
    fontSize: 15,
    fontWeight: '800',
    color: colors.text,
    marginBottom: 10,
  },
  dssCard: {
    backgroundColor: colors.card,
    borderRadius: 18,
    padding: 18,
    borderWidth: 1,
    borderColor: '#bfdbfe',
  },
  badgeRow: {
    marginBottom: 12,
  },
  dssBoxName: {
    fontSize: 16,
    fontWeight: '800',
    color: colors.primaryDark,
  },
  scoreRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingVertical: 10,
    borderTopWidth: 1,
    borderBottomWidth: 1,
    borderColor: colors.border,
    marginBottom: 12,
  },
  scoreText: {
    fontSize: 13,
    color: colors.textMuted,
  },
  bold: {
    fontWeight: '800',
    color: colors.text,
  },
  boldPrimary: {
    fontWeight: '800',
    color: colors.primary,
  },
  policyBox: {
    backgroundColor: colors.primaryLight,
    padding: 12,
    borderRadius: 10,
  },
  policyTitle: {
    fontSize: 12,
    fontWeight: '700',
    color: colors.primaryDark,
    marginBottom: 4,
  },
  policyText: {
    fontSize: 13,
    color: '#1e3a8a',
    lineHeight: 18,
  },
  emptyBox: {
    alignItems: 'center',
    padding: 16,
  },
  emptyText: {
    fontSize: 13,
    color: colors.textMuted,
    textAlign: 'center',
    marginBottom: 12,
  },
  actionBtn: {
    backgroundColor: colors.primary,
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 10,
  },
  actionBtnText: {
    color: '#fff',
    fontWeight: '700',
    fontSize: 13,
  },
  aiCard: {
    backgroundColor: '#fff',
    borderRadius: 16,
    padding: 16,
    borderWidth: 1,
    borderColor: colors.border,
  },
  aiHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8,
  },
  aiRiskTitle: {
    fontSize: 13,
    color: colors.textMuted,
  },
  aiRiskPct: {
    fontSize: 18,
    fontWeight: '800',
  },
  textDanger: { color: colors.danger },
  textWarning: { color: colors.warning },
  textSuccess: { color: colors.success },
  aiCause: {
    fontSize: 12,
    color: colors.text,
    lineHeight: 18,
  },
  actionRow: {
    flexDirection: 'row',
    gap: 12,
    marginTop: 4,
  },
  bigBtn: {
    flex: 1,
    borderRadius: 16,
    padding: 16,
    alignItems: 'center',
  },
  bigBtnIcon: {
    fontSize: 24,
    marginBottom: 6,
  },
  bigBtnText: {
    color: '#ffffff',
    fontSize: 13,
    fontWeight: '700',
  },
});
