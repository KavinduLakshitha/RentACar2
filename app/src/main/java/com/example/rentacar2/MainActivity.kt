package com.example.rentacar2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentacar2.data.CarRepository
import com.example.rentacar2.model.Car
import com.example.rentacar2.ui.theme.RentACar2Theme
import com.example.rentacar2.ui.theme.AppTextStyles
import com.example.rentacar2.ui.theme.AppColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            RentACar2Theme(darkTheme = isDarkMode) {
                CarRentalApp(
                    isDarkMode = isDarkMode,
                    onDarkModeToggle = { isDarkMode = it },
                    onRentCar = { car ->
                        val intent = Intent(this@MainActivity, RentalDetailsActivity::class.java).apply {
                            putExtra("car", car)
                            putExtra("isDarkMode", isDarkMode)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarRentalApp(
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    onRentCar: (Car) -> Unit
) {
    var creditBalance by remember { mutableStateOf(500) }
    var currentCarIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var sortType by remember { mutableStateOf("default") }
    var showFavorites by remember { mutableStateOf(false) }
    var carsState by remember { mutableStateOf(CarRepository.getAllCars()) }
    var refreshTrigger by remember { mutableStateOf(0) }

    // Refresh state when activity resumes
    LaunchedEffect(Unit) {
        // This ensures state is fresh when the activity starts
    }

    val allCars = carsState
    val availableCars = carsState.filter { it.isAvailable }
    val favoriteCars = carsState.filter { it.isFavorite }

    // Filter and sort cars based on current state
    val filteredCars = when {
        showFavorites -> favoriteCars.filter { it.isAvailable }
        searchQuery.isNotEmpty() -> carsState.filter {
            it.name.lowercase().contains(searchQuery.lowercase()) ||
                    it.model.lowercase().contains(searchQuery.lowercase())
        }.filter { it.isAvailable }
        else -> when (sortType) {
            "rating" -> carsState.sortedByDescending { it.rating }.filter { it.isAvailable }
            "year" -> carsState.sortedByDescending { it.year }.filter { it.isAvailable }
            "cost" -> carsState.sortedBy { it.dailyCost }.filter { it.isAvailable }
            else -> availableCars
        }
    }

    val currentCar = if (filteredCars.isNotEmpty()) {
        val carId = filteredCars[currentCarIndex % filteredCars.size].id
        // refreshTrigger is used here to force recomposition
        if (refreshTrigger >= 0) {
            carsState.find { it.id == carId }
        } else null
    } else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RentACar",
                        style = AppTextStyles.carTitleStyle.copy(fontSize = 24.sp)
                    )
                },
                actions = {
                    // Credit Balance Display
                    Card(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text(
                            text = "$creditBalance Credits",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            style = AppTextStyles.creditBalanceStyle
                        )
                    }

                    // Dark Mode Toggle
                    DarkModeToggle(
                        isDarkMode = isDarkMode,
                        onToggle = onDarkModeToggle
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search cars by name or model") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Sort and Filter Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Sort Dropdown
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = when (sortType) {
                            "rating" -> "Sort by Rating"
                            "year" -> "Sort by Year"
                            "cost" -> "Sort by Cost"
                            else -> "Default"
                        },
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .width(150.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Default") },
                            onClick = { sortType = "default"; expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Rating") },
                            onClick = { sortType = "rating"; expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Year") },
                            onClick = { sortType = "year"; expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Cost") },
                            onClick = { sortType = "cost"; expanded = false }
                        )
                    }
                }

                // Favorites Toggle
                FilterChip(
                    selected = showFavorites,
                    onClick = { showFavorites = !showFavorites },
                    label = { Text("Favorites") },
                    leadingIcon = {
                        Icon(
                            if (showFavorites) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (currentCar != null) {
                // Car Display Card
                CarDisplayCard(
                    car = currentCar,
                    creditBalance = creditBalance,
                    onRentClick = { car ->
                        if (creditBalance >= car.dailyCost) {
                            CarRepository.rentCar(car.id)
                            carsState = CarRepository.getAllCars().toMutableList()
                            onRentCar(car)
                            creditBalance -= car.dailyCost
                        }
                    },
                    onFavoriteToggle = { carId ->
                        CarRepository.toggleFavorite(carId)
                        carsState = CarRepository.getAllCars().toMutableList()
                        refreshTrigger++
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            currentCarIndex = if (currentCarIndex > 0) currentCarIndex - 1 else filteredCars.size - 1
                        },
                        enabled = filteredCars.size > 1
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Previous")
                    }

                    Text(
                        text = "${currentCarIndex + 1} of ${filteredCars.size}",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        onClick = {
                            currentCarIndex = (currentCarIndex + 1) % filteredCars.size
                        },
                        enabled = filteredCars.size > 1
                    ) {
                        Text("Next")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            } else {
                // No cars available message
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = if (showFavorites) "No favorite cars available" else "No cars available",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun CarDisplayCard(
    car: Car,
    creditBalance: Int,
    onRentClick: (Car) -> Unit,
    onFavoriteToggle: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Car Image and Favorite Button
            Box {
                Image(
                    painter = painterResource(id = car.imageResId),
                    contentDescription = car.getFullName(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )

                // Favorite Button
                IconButton(
                    onClick = { onFavoriteToggle(car.id) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (car.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (car.isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (car.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Car Details
            Text(
                text = car.getFullName(),
                style = AppTextStyles.carTitleStyle
            )

            Text(
                text = "Year: ${car.year}",
                style = AppTextStyles.carDetailStyle
            )

            // Rating Bar
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rating: ",
                    style = AppTextStyles.carDetailStyle
                )
                RatingBar(
                    rating = car.rating,
                    modifier = Modifier.size(120.dp, 20.dp)
                )
            }

            Text(
                text = car.getFormattedKilometers(),
                style = AppTextStyles.carDetailStyle
            )

            Text(
                text = car.getFormattedCost(),
                style = AppTextStyles.carDetailStyle.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Rent Button
            Button(
                onClick = { onRentClick(car) },
                modifier = Modifier.fillMaxWidth(),
                enabled = creditBalance >= car.dailyCost
            ) {
                Text(
                    text = if (creditBalance >= car.dailyCost) "Rent Now" else "Insufficient Credits",
                    style = AppTextStyles.buttonTextStyle
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating.toInt()) Color(0xFFFFD700) else Color(0xFFE0E0E0),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun DarkModeToggle(
    isDarkMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.clickable { onToggle(!isDarkMode) },
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    if (isDarkMode) R.drawable.ic_moon else R.drawable.ic_sun
                ),
                contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
                tint = if (isDarkMode)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarRentalAppPreview() {
    RentACar2Theme {
        CarRentalApp(
            isDarkMode = false,
            onDarkModeToggle = {},
            onRentCar = {}
        )
    }
}