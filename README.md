# Playlist Maker

Android-приложение для поиска, воспроизведения и организации музыкальных треков через iTunes API.

<div align="center">

https://github.com/user-attachments/assets/50dcf273-4cee-4e02-9400-5922dbf02282

</div>

## Возможности

### Поиск
- Поиск треков через iTunes Search API
- Клиентская пагинация (загрузка до 200 результатов, отображение по 10)
- История поиска с подсказками

### Воспроизведение
- Проигрывание 30-секундных превью треков
- Мини-плеер с TikTok-style seekbar (drag и tap-to-seek)
- Очередь воспроизведения с автопереключением на следующий трек
- Переключение next/previous из мини-плеера и уведомления
- Фоновое воспроизведение через Foreground Service
- MediaSession — управление из шторки, lock screen и Bluetooth-устройств
- Кэширование превью для офлайн-воспроизведения

### Библиотека
- Избранные треки
- Создание плейлистов с обложкой и описанием
- Добавление/удаление треков из плейлистов
- Живая синхронизация очереди при изменении избранного или плейлиста

### Прочее
- Тёмная и светлая темы
- Edge-to-edge дизайн
- Настройки (тема, ссылки, обратная связь)

## Архитектура

```
┌─────────────────────────────┐
│           UI Layer          │
│  Jetpack Compose + MVVM     │
│  (Screen → ViewModel)       │
├─────────────────────────────┤
│        Domain Layer         │
│  Repository interfaces      │
│  Models (Track, Playlist)   │
├─────────────────────────────┤
│         Data Layer          │
│  Room, Retrofit, DataStore  │
│  MediaPlayer, Cache         │
└─────────────────────────────┘
```

## Технологии

| Категория | Стек |
|---|---|
| Язык | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Архитектура | Clean Architecture, MVVM |
| Асинхронность | Coroutines, StateFlow |
| DI | Koin |
| БД | Room |
| Сеть | Retrofit, Gson |
| Изображения | Coil |
| Хранилище | DataStore |
| Воспроизведение | MediaPlayer, MediaSession |
| Фоновая работа | Foreground Service |
| Навигация | Navigation Component (Compose) |

## Требования

- **Android Studio**: Ladybug или новее
- **JDK**: 11
- **Android SDK**:
  - `minSdk`: 29
  - `compileSdk`: 36

## Запуск

### 1. Клонирование репозитория

```bash
git clone https://github.com/Vector513/playlist-maker-android-PavelkoVladislav.git
cd playlist-maker-android
```

### 2. Открытие проекта

- Запустить **Android Studio**
- Выбрать **Open**
- Указать папку с клонированным проектом

### 3. Сборка

Android Studio автоматически выполнит синхронизацию Gradle.

При необходимости можно собрать вручную:

```bash
./gradlew build
```

Для Windows: `gradlew build`

### 4. Запуск

- Выбрать эмулятор или физическое устройство (Android 10+)
- Нажать **Run ▶** в Android Studio
