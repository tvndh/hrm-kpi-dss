import React, { useState } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { colors } from '../theme/colors';
import { getBaseUrl, setBaseUrl } from '../api/config';

export default function SettingsScreen({ onBack }) {
  const [url, setUrl] = useState(getBaseUrl());

  const handleSave = () => {
    if (!url.trim()) {
      Alert.alert('Lỗi', 'URL không được để trống!');
      return;
    }
    setBaseUrl(url);
    Alert.alert('Thành Công', 'Đã lưu cấu hình IP Server mới!', [
      { text: 'OK', onPress: onBack },
    ]);
  };

  const handleSetPreset = (presetUrl) => {
    setUrl(presetUrl);
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={onBack} style={styles.backBtn}>
          <Text style={styles.backText}>‹ Quay lại</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Cấu Hình Server Backend</Text>
      </View>

      <View style={styles.card}>
        <Text style={styles.label}>ĐỊA CHỈ API SPRING BOOT BACKEND</Text>
        <TextInput
          style={styles.input}
          value={url}
          onChangeText={setUrl}
          placeholder="http://192.168.1.x:8088"
          autoCapitalize="none"
          autoCorrect={false}
        />

        <Text style={styles.hint}>
          💡 Gợi ý nhanh:
        </Text>
        <View style={styles.presetRow}>
          <TouchableOpacity
            style={styles.presetBtn}
            onPress={() => handleSetPreset('http://localhost:8088')}
          >
            <Text style={styles.presetText}>Web Localhost</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.presetBtn}
            onPress={() => handleSetPreset('http://10.0.2.2:8088')}
          >
            <Text style={styles.presetText}>Android Emulator</Text>
          </TouchableOpacity>
        </View>

        <TouchableOpacity style={styles.saveBtn} onPress={handleSave}>
          <Text style={styles.saveBtnText}>Lưu Cấu Hình</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
    padding: 16,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
    marginTop: 10,
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
  card: {
    backgroundColor: colors.card,
    borderRadius: 18,
    padding: 20,
    borderWidth: 1,
    borderColor: colors.border,
  },
  label: {
    fontSize: 11,
    fontWeight: '700',
    color: colors.textMuted,
    marginBottom: 8,
  },
  input: {
    borderWidth: 1,
    borderColor: colors.border,
    borderRadius: 10,
    padding: 12,
    fontSize: 14,
    backgroundColor: colors.background,
    marginBottom: 14,
  },
  hint: {
    fontSize: 12,
    fontWeight: '600',
    color: colors.textMuted,
    marginBottom: 8,
  },
  presetRow: {
    flexDirection: 'row',
    gap: 10,
    marginBottom: 20,
  },
  presetBtn: {
    backgroundColor: colors.primaryLight,
    paddingVertical: 8,
    paddingHorizontal: 12,
    borderRadius: 8,
  },
  presetText: {
    fontSize: 12,
    fontWeight: '700',
    color: colors.primary,
  },
  saveBtn: {
    backgroundColor: colors.primary,
    padding: 14,
    borderRadius: 12,
    alignItems: 'center',
  },
  saveBtnText: {
    color: '#fff',
    fontSize: 15,
    fontWeight: '800',
  },
});

