import React, { useState, useEffect } from 'react';
import {
  StyleSheet,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  TextInput,
  Alert,
  ActivityIndicator,
} from 'react-native';
import { colors } from '../theme/colors';
import { api } from '../api/config';

export default function EvaluationScreen({ user, onBack, onComplete }) {
  const [kpis, setKpis] = useState([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  // Điểm tự chấm cho các tiêu chí
  const [scores, setScores] = useState({
    kpi1: 4,
    kpi2: 4,
    kpi3: 5,
    ask_k: 4, // Knowledge
    ask_s: 4, // Skill
    ask_a: 5, // Attitude
  });
  const [feedback, setFeedback] = useState('Đã hoàn thành tốt các chỉ tiêu sprint và khóa đào tạo được giao.');

  useEffect(() => {
    loadKpiList();
  }, []);

  const loadKpiList = async () => {
    try {
      const data = await api.getKpiList();
      setKpis(data);
    } catch (e) {
      console.log('Error loading KPI list:', e);
    } finally {
      setLoading(false);
    }
  };

  const handleScoreChange = (key, val) => {
    setScores((prev) => ({ ...prev, [key]: val }));
  };

  const handleSubmit = async () => {
    setSubmitting(true);
    try {
      await api.submitSelfEvaluation({
        idNhanVien: user.idNhanVien,
        scores,
        feedback,
      });

      Alert.alert(
        'Thành Công 🎉',
        'Phiếu tự đánh giá của bạn đã được gửi tới Quản lý để xét duyệt ma trận 9-Box!',
        [{ text: 'Xem Kết Quả', onPress: onComplete }]
      );
    } catch (e) {
      Alert.alert('Lỗi', 'Không thể gửi phiếu: ' + e.message);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color={colors.primary} />
        <Text style={{ marginTop: 12, color: colors.textMuted }}>Đang tải tiêu chí đánh giá...</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity onPress={onBack} style={styles.backBtn}>
          <Text style={styles.backText}>‹ Quay lại</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Tự Đánh Giá KPI & Năng Lực</Text>
      </View>

      <Text style={styles.periodBadge}>Kỳ đánh giá: Quý 4 / 2026</Text>

      {/* Phần 1: Tiêu chí KPI Công Việc */}
      <View style={styles.sectionCard}>
        <Text style={styles.sectionHeader}>📊 1. Hiệu Suất Công Việc (KPI)</Text>
        <Text style={styles.sectionDesc}>Tự đánh giá thang điểm từ 1 (Kém) đến 5 (Xuất sắc)</Text>

        <View style={styles.itemBox}>
          <Text style={styles.itemTitle}>• Tiến độ hoàn thành Sprint / Task Deadline</Text>
          <Text style={styles.itemTarget}>Mục tiêu: 95% đúng hạn</Text>
          <View style={styles.ratingRow}>
            {[1, 2, 3, 4, 5].map((num) => (
              <TouchableOpacity
                key={num}
                style={[styles.rateBtn, scores.kpi1 === num && styles.rateBtnActive]}
                onPress={() => handleScoreChange('kpi1', num)}
              >
                <Text style={[styles.rateNum, scores.kpi1 === num && styles.rateNumActive]}>
                  {num} ⭐
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>

        <View style={styles.itemBox}>
          <Text style={styles.itemTitle}>• Chất lượng công việc & Tỷ lệ lỗi / Bug</Text>
          <Text style={styles.itemTarget}>Mục tiêu: Dưới 5% phát sinh bug</Text>
          <View style={styles.ratingRow}>
            {[1, 2, 3, 4, 5].map((num) => (
              <TouchableOpacity
                key={num}
                style={[styles.rateBtn, scores.kpi2 === num && styles.rateBtnActive]}
                onPress={() => handleScoreChange('kpi2', num)}
              >
                <Text style={[styles.rateNum, scores.kpi2 === num && styles.rateNumActive]}>
                  {num} ⭐
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>

        <View style={styles.itemBox}>
          <Text style={styles.itemTitle}>• Khối lượng công việc đạt được (Story Points)</Text>
          <Text style={styles.itemTarget}>Mục tiêu: Đạt 100 điểm chuẩn kỳ</Text>
          <View style={styles.ratingRow}>
            {[1, 2, 3, 4, 5].map((num) => (
              <TouchableOpacity
                key={num}
                style={[styles.rateBtn, scores.kpi3 === num && styles.rateBtnActive]}
                onPress={() => handleScoreChange('kpi3', num)}
              >
                <Text style={[styles.rateNum, scores.kpi3 === num && styles.rateNumActive]}>
                  {num} ⭐
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      </View>

      {/* Phần 2: Khung Năng Lực ASK */}
      <View style={styles.sectionCard}>
        <Text style={styles.sectionHeader}>🎯 2. Khung Năng Lực (ASK Model)</Text>
        <Text style={styles.sectionDesc}>Đo lường Kiến thức, Kỹ năng và Thái độ làm việc</Text>

        <View style={styles.itemBox}>
          <Text style={styles.itemTitle}>• Kiến thức chuyên môn (Knowledge)</Text>
          <Text style={styles.itemTarget}>Nắm vững kiến trúc, quy trình và công cụ</Text>
          <View style={styles.ratingRow}>
            {[1, 2, 3, 4, 5].map((num) => (
              <TouchableOpacity
                key={num}
                style={[styles.rateBtn, scores.ask_k === num && styles.rateBtnActive]}
                onPress={() => handleScoreChange('ask_k', num)}
              >
                <Text style={[styles.rateNum, scores.ask_k === num && styles.rateNumActive]}>
                  {num} ⭐
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>

        <View style={styles.itemBox}>
          <Text style={styles.itemTitle}>• Kỹ năng thực thi (Skill)</Text>
          <Text style={styles.itemTarget}>Khả năng giải quyết sự cố, tối ưu hiệu năng</Text>
          <View style={styles.ratingRow}>
            {[1, 2, 3, 4, 5].map((num) => (
              <TouchableOpacity
                key={num}
                style={[styles.rateBtn, scores.ask_s === num && styles.rateBtnActive]}
                onPress={() => handleScoreChange('ask_s', num)}
              >
                <Text style={[styles.rateNum, scores.ask_s === num && styles.rateNumActive]}>
                  {num} ⭐
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>

        <View style={styles.itemBox}>
          <Text style={styles.itemTitle}>• Thái độ & Trách nhiệm (Attitude)</Text>
          <Text style={styles.itemTarget}>Hợp tác nhóm, chủ động chia sẻ, tinh thần học hỏi</Text>
          <View style={styles.ratingRow}>
            {[1, 2, 3, 4, 5].map((num) => (
              <TouchableOpacity
                key={num}
                style={[styles.rateBtn, scores.ask_a === num && styles.rateBtnActive]}
                onPress={() => handleScoreChange('ask_a', num)}
              >
                <Text style={[styles.rateNum, scores.ask_a === num && styles.rateNumActive]}>
                  {num} ⭐
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      </View>

      {/* Phần 3: Nhận xét tự chấm */}
      <View style={styles.sectionCard}>
        <Text style={styles.sectionHeader}>✍️ 3. Nhận Xét & Đề Xuất Bản Thân</Text>
        <TextInput
          style={styles.textArea}
          multiline
          numberOfLines={4}
          value={feedback}
          onChangeText={setFeedback}
          placeholder="Nhập ghi chú hoặc nguyện vọng phát triển sự nghiệp..."
        />
      </View>

      {/* Nút gửi */}
      <TouchableOpacity
        style={styles.submitBtn}
        onPress={handleSubmit}
        disabled={submitting}
      >
        {submitting ? (
          <ActivityIndicator color="#fff" />
        ) : (
          <Text style={styles.submitBtnText}>🚀 Nộp Phiếu Tự Đánh Giá</Text>
        )}
      </TouchableOpacity>

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
    marginBottom: 12,
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
  periodBadge: {
    fontSize: 12,
    color: colors.primary,
    fontWeight: '700',
    backgroundColor: colors.primaryLight,
    paddingVertical: 6,
    paddingHorizontal: 12,
    borderRadius: 8,
    alignSelf: 'flex-start',
    marginBottom: 16,
  },
  sectionCard: {
    backgroundColor: colors.card,
    borderRadius: 18,
    padding: 18,
    marginBottom: 16,
    borderWidth: 1,
    borderColor: colors.border,
  },
  sectionHeader: {
    fontSize: 15,
    fontWeight: '800',
    color: colors.text,
    marginBottom: 4,
  },
  sectionDesc: {
    fontSize: 12,
    color: colors.textMuted,
    marginBottom: 14,
  },
  itemBox: {
    marginBottom: 16,
    paddingBottom: 14,
    borderBottomWidth: 1,
    borderBottomColor: colors.border,
  },
  itemTitle: {
    fontSize: 13,
    fontWeight: '700',
    color: colors.text,
  },
  itemTarget: {
    fontSize: 11,
    color: colors.textMuted,
    marginVertical: 4,
  },
  ratingRow: {
    flexDirection: 'row',
    gap: 8,
    marginTop: 6,
  },
  rateBtn: {
    flex: 1,
    paddingVertical: 8,
    borderRadius: 8,
    backgroundColor: colors.background,
    borderWidth: 1,
    borderColor: colors.border,
    alignItems: 'center',
  },
  rateBtnActive: {
    backgroundColor: colors.primary,
    borderColor: colors.primary,
  },
  rateNum: {
    fontSize: 11,
    fontWeight: '700',
    color: colors.text,
  },
  rateNumActive: {
    color: '#ffffff',
  },
  textArea: {
    borderWidth: 1,
    borderColor: colors.border,
    borderRadius: 12,
    padding: 12,
    fontSize: 13,
    backgroundColor: colors.background,
    textAlignVertical: 'top',
    minHeight: 90,
  },
  submitBtn: {
    backgroundColor: colors.primary,
    padding: 16,
    borderRadius: 14,
    alignItems: 'center',
    marginTop: 8,
  },
  submitBtnText: {
    color: '#fff',
    fontSize: 15,
    fontWeight: '800',
  },
});

