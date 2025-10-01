package com.example.rentacar2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Custom text styles that will be used in multiple locations
object AppTextStyles {
    val carTitleStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    
    val carDetailStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
    
    val creditBalanceStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    
    val buttonTextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
    
    val errorTextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
    
    val totalCostStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

// Custom color styles
object AppColors {
    val successColor = Color(0xFF4CAF50)
    val warningColor = Color(0xFFFF9800)
    val errorColor = Color(0xFFF44336)
    val infoColor = Color(0xFF2196F3)
    
    // Car-specific colors
    val carCardBackground = Color(0xFFF5F5F5)
    val carCardBackgroundDark = Color(0xFF2C2C2C)
    
    // Favorite colors
    val favoriteColor = Color(0xFFE91E63)
    val favoriteColorDark = Color(0xFFAD1457)
}

// Custom spacing
object AppSpacing {
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
    val extraLarge = 32.dp
}

// Custom elevation
object AppElevation {
    val cardElevation = 4.dp
    val buttonElevation = 2.dp
    val dialogElevation = 8.dp
}
