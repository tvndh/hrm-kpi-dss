import React, { useState } from 'react';
import {
  StyleSheet,
  View,
  SafeAreaView,
  StatusBar,
  Text,
  TouchableOpacity,
} from 'react-native';
import LoginScreen from './src/screens/LoginScreen';
import HomeScreen from './src/screens/HomeScreen';
import EvaluationScreen from './src/screens/EvaluationScreen';
import ResultScreen from './src/screens/ResultScreen';
import SettingsScreen from './src/screens/SettingsScreen';
import { colors } from './src/theme/colors';

export default function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [currentScreen, setCurrentScreen] = useState('home'); // home, evaluate, result, settings

  // Đăng xuất
  const handleLogout = () => {
    setCurrentUser(null);
    setCurrentScreen('home');
  };

  // Nếu chưa đăng nhập
  if (!currentUser) {
    if (currentScreen === 'settings') {
      return (
        <SafeAreaView style={styles.safe}>
          <StatusBar barStyle="dark-content" />
          <SettingsScreen onBack={() => setCurrentScreen('home')} />
        </SafeAreaView>
      );
    }

    return (
      <SafeAreaView style={styles.safe}>
        <StatusBar barStyle="dark-content" />
        <LoginScreen
          onLoginSuccess={(user) => {
            setCurrentUser(user);
            setCurrentScreen('home');
          }}
          onOpenSettings={() => setCurrentScreen('settings')}
        />
      </SafeAreaView>
    );
  }

  // Đã đăng nhập
  return (
    <SafeAreaView style={styles.safe}>
      <StatusBar barStyle="dark-content" />

      {/* Top App Header */}
      <View style={styles.navBar}>
        <View style={styles.brand}>
          <View style={styles.miniLogo}>
            <Text style={styles.miniLogoText}>HR</Text>
          </View>
          <Text style={styles.brandText}>HRM KPI DSS</Text>
        </View>

        <View style={styles.navRight}>
          <TouchableOpacity
            style={styles.iconBtn}
            onPress={() => setCurrentScreen('settings')}
          >
            <Text style={{ fontSize: 16 }}>⚙️</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.logoutBtn} onPress={handleLogout}>
            <Text style={styles.logoutText}>Đăng xuất</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Screen Body */}
      <View style={styles.content}>
        {currentScreen === 'home' && (
          <HomeScreen
            user={currentUser}
            onNavigate={(screen) => setCurrentScreen(screen)}
          />
        )}
        {currentScreen === 'evaluate' && (
          <EvaluationScreen
            user={currentUser}
            onBack={() => setCurrentScreen('home')}
            onComplete={() => setCurrentScreen('result')}
          />
        )}
        {currentScreen === 'result' && (
          <ResultScreen
            user={currentUser}
            onBack={() => setCurrentScreen('home')}
          />
        )}
        {currentScreen === 'settings' && (
          <SettingsScreen onBack={() => setCurrentScreen('home')} />
        )}
      </View>

      {/* Bottom Navigation Bar */}
      <View style={styles.bottomNav}>
        <TouchableOpacity
          style={[styles.tabItem, currentScreen === 'home' && styles.tabItemActive]}
          onPress={() => setCurrentScreen('home')}
        >
          <Text style={styles.tabIcon}>🏠</Text>
          <Text
            style={[
              styles.tabText,
              currentScreen === 'home' && styles.tabTextActive,
            ]}
          >
            Trang chủ
          </Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[
            styles.tabItem,
            currentScreen === 'evaluate' && styles.tabItemActive,
          ]}
          onPress={() => setCurrentScreen('evaluate')}
        >
          <Text style={styles.tabIcon}>✍️</Text>
          <Text
            style={[
              styles.tabText,
              currentScreen === 'evaluate' && styles.tabTextActive,
            ]}
          >
            Tự Đánh Giá
          </Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[
            styles.tabItem,
            currentScreen === 'result' && styles.tabItemActive,
          ]}
          onPress={() => setCurrentScreen('result')}
        >
          <Text style={styles.tabIcon}>🏆</Text>
          <Text
            style={[
              styles.tabText,
              currentScreen === 'result' && styles.tabTextActive,
            ]}
          >
            Kết Quả DSS
          </Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safe: {
    flex: 1,
    backgroundColor: colors.background,
  },
  navBar: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 12,
    backgroundColor: colors.card,
    borderBottomWidth: 1,
    borderBottomColor: colors.border,
  },
  brand: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  miniLogo: {
    width: 28,
    height: 28,
    borderRadius: 8,
    backgroundColor: colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  miniLogoText: {
    color: '#ffffff',
    fontSize: 12,
    fontWeight: '800',
  },
  brandText: {
    fontSize: 16,
    fontWeight: '800',
    color: colors.text,
  },
  navRight: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 10,
  },
  iconBtn: {
    padding: 6,
  },
  logoutBtn: {
    backgroundColor: colors.dangerLight,
    paddingVertical: 6,
    paddingHorizontal: 10,
    borderRadius: 8,
  },
  logoutText: {
    fontSize: 12,
    fontWeight: '700',
    color: colors.danger,
  },
  content: {
    flex: 1,
  },
  bottomNav: {
    flexDirection: 'row',
    backgroundColor: colors.card,
    borderTopWidth: 1,
    borderTopColor: colors.border,
    paddingVertical: 8,
    paddingBottom: 16,
  },
  tabItem: {
    flex: 1,
    alignItems: 'center',
  },
  tabIcon: {
    fontSize: 20,
    marginBottom: 2,
  },
  tabText: {
    fontSize: 11,
    fontWeight: '600',
    color: colors.textMuted,
  },
  tabTextActive: {
    color: colors.primary,
    fontWeight: '800',
  },
});
