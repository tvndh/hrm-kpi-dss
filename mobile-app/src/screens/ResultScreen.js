import React, { useState, useEffect } from 'react';
import {
  StyleSheet,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  ActivityIndicator,
} from 'react-native';
import { colors } from '../theme/colors';
import { api } from '../api/config';

export default function ResultScreen({ user, onBack }) {
  const [dss, setDss] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDss();
  }, []);

  const loadDss = async () => {
    try {
      const data = await api.getDssResult(user.idNhanVien);
      setDss(data);
    } catch (e) {
      console.log('Error loading result:', e);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color={colors.primary} />
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={onBack} style={styles.backBtn}>
          <Text style={styles.backText}>‹ Trang chủ</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Báo Cáo Đánh Giá & DSS</Text>
      </View>

      {dss ? (
        <>
          {/* Main 9-Box Result Banner */}
          <View style={styles.mainBanner}>
            <Text style={styles.bannerSubtitle}>XẾP HẠNG MA TRẬN 9-BOX</Text>
            <Text style={styles.bannerBoxName}>{dss.viTri9Box}</Text>
            <View style={styles.scoreRow}>
              <View style={styles.scoreCol}>
                <Text style={styles.scoreNumber}>{dss.tongDiemKpi}</Text>
                <Text style={styles.scoreLbl}>Hiệu suất (KPI)</Text>
              </View>
              <View style={styles.scoreDivider} />
              <View style={styles.scoreCol}>
                <Text style={styles.scoreNumber}>{dss.tongDiemNangLuc}</Text>
                <Text style={styles.scoreLbl}>Năng lực (ASK)</Text>
              </View>
              <View style={styles.scoreDivider} />
              <View style={styles.scoreCol}>
                <Text style={[styles.scoreNumber, { color: colors.primary }]}>
                  {dss.diemDanhGiaChung}
                </Text>
                <Text style={styles.scoreLbl}>Tổng hợp (60/40)</Text>
              </View>
            </View>
          </View>

          {/* Decision Support Policy Card */}
          <View style={styles.policyCard}>
            <Text style={styles.policyHeader}>💡 Đề Xuất Chính Sách Nhân Tài (DSS)</Text>
            <Text style={styles.policyBody}>{dss.deXuat}</Text>
            <View style={styles.dateInfo}>
              <Text style={styles.dateText}>
                Thời điểm tính toán: {dss.ngayTinhToan ? dss.ngayTinhToan.substring(0, 10) : 'Gần nhất'}
              </Text>
            </View>
          </View>

          {/* Radar / Competency Breakdown Card */}
          <View style={styles.infoCard}>
            <Text style={styles.infoCardTitle}>📊 Chi Tiết Năng Lực ASK</Text>
            
            <View style={styles.skillBarRow}>
              <View style={styles.skillBarHeader}>
                <Text style={styles.skillName}>Kiến thức (Knowledge)</Text>
                <Text style={styles.skillScore}>4.2 / 5.0</Text>
              </View>
              <View style={styles.barBg}>
                <View style={[styles.barFill, { width: '84%', backgroundColor: colors.primary }]} />
              </View>
            </View>

            <View style={styles.skillBarRow}>
              <View style={styles.skillBarHeader}>
                <Text style={styles.skillName}>Kỹ năng thực thi (Skill)</Text>
                <Text style={styles.skillScore}>4.0 / 5.0</Text>
              </View>
              <View style={styles.barBg}>
                <View style={[styles.barFill, { width: '80%', backgroundColor: colors.secondary }]} />
              </View>
            </View>

            <View style={styles.skillBarRow}>
              <View style={styles.skillBarHeader}>
                <Text style={styles.skillName}>Thái độ & Trách nhiệm (Attitude)</Text>
                <Text style={styles.skillScore}>4.5 / 5.0</Text>
              </View>
              <View style={styles.barBg}>
                <View style={[styles.barFill, { width: '90%', backgroundColor: colors.success }]} />
              </View>
            </View>
          </View>
        </>
      ) : (
        <View style={styles.emptyContainer}>
          <Text style={styles.emptyIcon}>📋</Text>
          <Text style={styles.emptyTitle}>Chưa có kết quả chính thức</Text>
          <Text style={styles.emptyDesc}>
            Phiếu đánh giá của bạn đang trong quá trình xét duyệt hoặc bạn chưa hoàn thành tự đánh giá kỳ này.
          </Text>
        </View>
      )}

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
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 16,
  },
  backBtn: {
    marginRight: 12,
    padding: 6,
  },
  backText: {
    color: colors.primary,
    fontSize: 16,
    fontWeight: '700',
  },
  headerTitle: {
    fontSize: 17,
    fontWeight: '800',
    color: colors.text,
  },
  mainBanner: {
    backgroundColor: colors.card,
    borderRadius: 20,
    padding: 20,
    borderWidth: 2,
    borderColor: colors.primary,
    alignItems: 'center',
    marginBottom: 16,
  },
  bannerSubtitle: {
    fontSize: 11,
    fontWeight: '800',
    color: colors.primary,
    letterSpacing: 1,
  },
  bannerBoxName: {
    fontSize: 20,
    fontWeight: '800',
    color: colors.text,
    marginVertical: 10,
    textAlign: 'center',
  },
  scoreRow: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    width: '100%',
    paddingTop: 14,
    borderTopWidth: 1,
    borderTopColor: colors.border,
    marginTop: 6,
  },
  scoreCol: {
    alignItems: 'center',
  },
  scoreNumber: {
    fontSize: 20,
    fontWeight: '800',
    color: colors.text,
  },
  scoreLbl: {
    fontSize: 11,
    color: colors.textMuted,
    marginTop: 2,
  },
  scoreDivider: {
    width: 1,
    height: 30,
    backgroundColor: colors.border,
  },
  policyCard: {
    backgroundColor: colors.primaryLight,
    borderRadius: 18,
    padding: 18,
    borderLeftWidth: 5,
    borderLeftColor: colors.primary,
    marginBottom: 16,
  },
  policyHeader: {
    fontSize: 13,
    fontWeight: '800',
    color: colors.primaryDark,
    marginBottom: 6,
  },
  policyBody: {
    fontSize: 13,
    color: '#1e3a8a',
    lineHeight: 20,
  },
  dateInfo: {
    marginTop: 10,
    paddingTop: 8,
    borderTopWidth: 1,
    borderTopColor: 'rgba(37,99,235,0.15)',
  },
  dateText: {
    fontSize: 11,
    color: colors.textMuted,
  },
  infoCard: {
    backgroundColor: colors.card,
    borderRadius: 18,
    padding: 18,
    borderWidth: 1,
    borderColor: colors.border,
  },
  infoCardTitle: {
    fontSize: 14,
    fontWeight: '800',
    color: colors.text,
    marginBottom: 14,
  },
  skillBarRow: {
    marginBottom: 14,
  },
  skillBarHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 6,
  },
  skillName: {
    fontSize: 12,
    fontWeight: '600',
    color: colors.text,
  },
  skillScore: {
    fontSize: 12,
    fontWeight: '700',
    color: colors.primary,
  },
  barBg: {
    height: 8,
    backgroundColor: colors.border,
    borderRadius: 999,
    overflow: 'hidden',
  },
  barFill: {
    height: '100%',
    borderRadius: 999,
  },
  emptyContainer: {
    backgroundColor: colors.card,
    borderRadius: 20,
    padding: 30,
    alignItems: 'center',
  },
  emptyIcon: {
    fontSize: 48,
    marginBottom: 12,
  },
  emptyTitle: {
    fontSize: 16,
    fontWeight: '800',
    color: colors.text,
    marginBottom: 6,
  },
  emptyDesc: {
    fontSize: 13,
    color: colors.textMuted,
    textAlign: 'center',
    lineHeight: 18,
  },
});

