# RentACar2 - Car Rental App

A comprehensive Android car rental application built with Jetpack Compose, featuring modern UI design, dark mode support, and comprehensive functionality for renting cars.

## Features

### Core Functionality
- **Car Display**: View 10 different cars with detailed information
- **Navigation**: Browse cars using Previous/Next buttons
- **Rental System**: Rent cars with credit-based system (500 credits initial balance)
- **Credit Management**: Real-time credit balance tracking with 400 credit limit enforcement
- **Car Availability**: Cars are removed from available list when rented

### User Interface Features
- **Search**: Search cars by name or model
- **Sorting**: Sort by Rating (high to low), Year (newest to oldest), or Cost (low to high)
- **Favorites**: Mark cars as favorites with heart icon
- **Dark Mode**: Toggle between light and dark themes
- **Responsive Design**: Modern Material Design 3 UI components

### Rental Details
- **Detailed Rental Form**: Customer information, rental options, and cost calculation
- **Rental Options**: 
  - Number of days (1-7) with slider
  - Automatic transmission toggle
  - Insurance option (+20 credits)
- **Validation**: Form validation with error handling
- **Parcelable Data Transfer**: Efficient data passing between activities

### Technical Features
- **Parcelable Implementation**: Custom Car model with Parcelable for data transfer
- **In-Memory Storage**: No persistent storage, data kept in memory
- **Custom Styles**: Reusable text styles and color schemes
- **Error Handling**: Comprehensive validation and error messages
- **UI Testing**: Espresso tests for main functionality

## App Architecture

### Data Model
```kotlin
data class Car(
    val id: Int,
    val name: String,
    val model: String,
    val year: Int,
    val rating: Float,
    val kilometers: Int,
    val dailyCost: Int,
    val imageResId: Int,
    var isAvailable: Boolean = true,
    var isFavorite: Boolean = false
) : Parcelable
```

### Available Cars
1. **Toyota Camry** (2022) - 4.5★ - 80 credits/day
2. **Honda Civic** (2021) - 4.3★ - 75 credits/day
3. **Ford Focus** (2020) - 4.0★ - 70 credits/day
4. **Nissan Altima** (2023) - 4.7★ - 90 credits/day
5. **Chevrolet Cruze** (2019) - 3.8★ - 65 credits/day
6. **Suzuki Wagon R** (2021) - 4.2★ - 60 credits/day
7. **Toyota Prius** (2023) - 4.8★ - 95 credits/day
8. **Honda Vezel** (2022) - 4.6★ - 85 credits/day
9. **Toyota Yaris** (2021) - 4.4★ - 70 credits/day
10. **Toyota Axio** (2020) - 4.1★ - 65 credits/day

### Key Components
- **CarRepository**: Manages car data and operations
- **MainActivity**: Main car browsing interface
- **RentalDetailsActivity**: Detailed rental form and processing
- **Custom Themes**: Light and dark mode support
- **Custom Styles**: Reusable UI components

## Widgets Used

### Non-TextView Widgets
1. **RatingBar**: Displays car ratings (1-5 stars)
2. **Switch**: Toggle for automatic transmission and insurance options
3. **Slider**: Select number of rental days (1-7)

### UI Components
- **ExposedDropdownMenu**: Sort options
- **FilterChip**: Favorites toggle
- **Card**: Car display and information containers
- **Button**: Navigation and action buttons
- **OutlinedTextField**: Search and form inputs

## Technical Implementation

### Parcelable Usage
The Car model implements Parcelable for efficient data transfer between activities:

**Advantages of Parcelable:**
- **Performance**: Faster than Serializable
- **Memory Efficient**: Direct memory mapping
- **Type Safety**: Compile-time type checking
- **Android Optimized**: Native Android support

**Components of Intent:**
```kotlin
val intent = Intent(context, RentalDetailsActivity::class.java).apply {
    putExtra("car", car) // Parcelable object
}
```

### Error Handling
- **Credit Validation**: Ensures total cost doesn't exceed 400 credits
- **Form Validation**: Required fields validation
- **Availability Check**: Prevents renting unavailable cars
- **Toast Messages**: User feedback for all actions

### Custom Styles
Two custom styles used in multiple locations:

1. **Car Title Style**: Used for car names in both activities
2. **Car Detail Style**: Used for car information in both activities

## User Stories and Use Cases

### User Story 1: Browse and Rent a Car
**As a** customer  
**I want to** browse available cars and rent one  
**So that** I can find a suitable vehicle for my needs

**Use Case:**
1. User opens the app
2. User sees car display with navigation controls
3. User can search or sort cars
4. User clicks "Rent Now" on desired car
5. User fills rental form with details
6. User confirms rental and car becomes unavailable

### User Story 2: Manage Favorites
**As a** customer  
**I want to** mark cars as favorites  
**So that** I can quickly access preferred vehicles

**Use Case:**
1. User browses available cars
2. User clicks heart icon to mark as favorite
3. User can toggle "Favorites" filter to see only favorite cars
4. User can easily access preferred vehicles

## App Layouts

### Layout 1: Main Car Display
```
┌─────────────────────────────────┐
│ RentACar    [Credits] [Dark]    │
├─────────────────────────────────┤
│ [Search Field]                  │
│ [Sort ▼] [Favorites]            │
├─────────────────────────────────┤
│ ┌─────────────────────────────┐ │
│ │        [Car Image]          │ │
│ │                    [♥]      │ │
│ │ Toyota Camry                │ │
│ │ Year: 2022                  │ │
│ │ Rating: ★★★★☆              │ │
│ │ 25000km                     │ │
│ │ 80 credits/day              │ │
│ │ [Rent Now]                  │ │
│ └─────────────────────────────┘ │
│ [Previous] 1 of 10 [Next]      │
└─────────────────────────────────┘
```

### Layout 2: Rental Details
```
┌─────────────────────────────────┐
│ ← Rental Details                │
├─────────────────────────────────┤
│ ┌─────────────────────────────┐ │
│ │        [Car Image]          │ │
│ │ Toyota Camry                │ │
│ │ Year: 2022 | 25000km        │ │
│ │ Base Cost: 80 credits/day   │ │
│ └─────────────────────────────┘ │
│ ┌─────────────────────────────┐ │
│ │ Rental Options              │ │
│ │ Days: [Slider] 1            │ │
│ │ [✓] Automatic Transmission  │ │
│ │ [✓] Insurance (+20)         │ │
│ └─────────────────────────────┘ │
│ ┌─────────────────────────────┐ │
│ │ Customer Information        │ │
│ │ [Name Field]                │ │
│ │ [Phone Field]               │ │
│ └─────────────────────────────┘ │
│ ┌─────────────────────────────┐ │
│ │ Total: 100 credits          │ │
│ └─────────────────────────────┘ │
│ [Cancel] [Confirm Rental]      │
└─────────────────────────────────┘
```

## Design Choices

### UI/UX Decisions
1. **Single Car Display**: Shows one car at a time for focused viewing
2. **Card-Based Layout**: Clean, modern design with clear information hierarchy
3. **Color-Coded Elements**: Credit balance, favorites, and status indicators
4. **Progressive Disclosure**: Detailed rental options in separate screen
5. **Immediate Feedback**: Toast messages and visual state changes

### Technical Decisions
1. **Jetpack Compose**: Modern declarative UI framework
2. **Material Design 3**: Consistent design system
3. **In-Memory Storage**: Simpler for proof-of-concept
4. **Parcelable**: Efficient data transfer between activities
5. **Custom Styles**: Maintainable and consistent theming

## Challenges and Solutions

### Challenge 1: Dark Mode Implementation
**Problem**: Ensuring consistent theming across light and dark modes
**Solution**: Created comprehensive color schemes and custom styles that adapt to both themes

### Challenge 2: State Management
**Problem**: Managing complex UI state across multiple screens
**Solution**: Used Compose state management with remember and mutableStateOf

### Challenge 3: Data Validation
**Problem**: Ensuring data integrity and user input validation
**Solution**: Implemented comprehensive validation with user-friendly error messages

## Testing

### UI Tests
- **MainActivity Tests**: Search, navigation, favorites, and basic functionality
- **RentalDetailsActivity Tests**: Form validation, navigation, and user interactions
- **Espresso Framework**: Automated UI testing for regression prevention

### Test Coverage
- User interface interactions
- Form validation
- Navigation flows
- State changes
- Error handling

## Future Enhancements

1. **Persistent Storage**: SQLite database for data persistence
2. **User Authentication**: Login system with user accounts
3. **Payment Integration**: Real payment processing
4. **Location Services**: GPS-based car pickup locations
5. **Push Notifications**: Rental confirmations and reminders
6. **Admin Panel**: Car management for administrators

## Installation and Setup

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on Android device or emulator
5. Enjoy the car rental experience!

## Dependencies

- **AndroidX Core**: Core Android libraries
- **Jetpack Compose**: Modern UI toolkit
- **Material Design 3**: Design system components
- **Espresso**: UI testing framework
- **Kotlin**: Programming language

---

*This app demonstrates modern Android development practices with Jetpack Compose, Material Design 3, and comprehensive user experience design.*
