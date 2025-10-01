package com.example.rentacar2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentacar2.data.CarRepository
import com.example.rentacar2.model.Car
import com.example.rentacar2.ui.theme.RentACar2Theme
import com.example.rentacar2.ui.theme.AppTextStyles

class RentalDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val car = intent.getParcelableExtra<Car>("car")
        val isDarkMode = intent.getBooleanExtra("isDarkMode", false)
        
        if (car == null) {
            Toast.makeText(this, "Car data not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        setContent {
            RentACar2Theme(darkTheme = isDarkMode) {
                RentalDetailsScreen(
                    car = car,
                    onBackPressed = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalDetailsScreen(
    car: Car,
    onBackPressed: () -> Unit
) {
    var rentalDays by remember { mutableStateOf(1) }
    var includeInsurance by remember { mutableStateOf(false) }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    
    val context = LocalContext.current
    val totalCost = (car.dailyCost * rentalDays) + if (includeInsurance) 20 else 0
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Rental Details",
                        style = AppTextStyles.carTitleStyle.copy(fontSize = 24.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Car Image and Basic Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = car.imageResId),
                        contentDescription = car.getFullName(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = car.getFullName(),
                        style = AppTextStyles.carTitleStyle
                    )
                    
                    Text(
                        text = "Year: ${car.year} | ${car.getFormattedKilometers()}",
                        style = AppTextStyles.carDetailStyle
                    )
                    
                    Text(
                        text = "Base Cost: ${car.getFormattedCost()}",
                        style = AppTextStyles.carDetailStyle.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
            
            // Rental Options
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Rental Options",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Number of Days Slider
                    Text(
                        text = "Number of Days: $rentalDays",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    Slider(
                        value = rentalDays.toFloat(),
                        onValueChange = { rentalDays = it.toInt() },
                        valueRange = 1f..7f,
                        steps = 5,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Insurance Option
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Include Insurance (+20 credits)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Switch(
                            checked = includeInsurance,
                            onCheckedChange = { includeInsurance = it }
                        )
                    }
                }
            }
            
            // Customer Information
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Customer Information",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = customerName,
                        onValueChange = { customerName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = customerName.isEmpty() && customerName.isNotEmpty()
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = customerPhone,
                        onValueChange = { customerPhone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = customerPhone.isEmpty() && customerPhone.isNotEmpty()
                    )
                }
            }
            
            // Total Cost Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Rental Summary",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Base Cost: ${car.dailyCost} × $rentalDays = ${car.dailyCost * rentalDays} credits",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    if (includeInsurance) {
                        Text(
                            text = "Insurance: +20 credits",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Total: $totalCost credits",
                        style = AppTextStyles.totalCostStyle
                    )
                }
            }
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onBackPressed,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = {
                        if (validateInput(customerName, customerPhone, totalCost)) {
                            // Process rental
                            val success = CarRepository.rentCar(car.id)
                            if (success) {
                                Toast.makeText(
                                    context,
                                    "Car rented successfully for $rentalDays days!",
                                    Toast.LENGTH_LONG
                                ).show()
                                onBackPressed()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Car is no longer available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please fill in all required fields and check credit limit",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = validateInput(customerName, customerPhone, totalCost)
                ) {
                    Text("Confirm Rental")
                }
            }
        }
    }
}

private fun validateInput(name: String, phone: String, totalCost: Int): Boolean {
    return name.isNotBlank() && 
           phone.isNotBlank() && 
           totalCost <= 400 && 
           totalCost > 0
}
