package com.example.rentacar2.data

import com.example.rentacar2.R
import com.example.rentacar2.model.Car

object CarRepository {
    private var cars = mutableListOf(
        Car(
            id = 1,
            name = "Suzuki",
            model = "Wagon R",
            year = 2021,
            rating = 4.2f,
            kilometers = 28000,
            dailyCost = 60,
            imageResId = R.drawable.car_suzuki_wagon_r
        ),
        Car(
            id = 2,
            name = "Toyota",
            model = "Prius",
            year = 2023,
            rating = 4.8f,
            kilometers = 12000,
            dailyCost = 95,
            imageResId = R.drawable.car_toyota_prius
        ),
        Car(
            id = 3,
            name = "Honda",
            model = "Vezel",
            year = 2022,
            rating = 4.6f,
            kilometers = 20000,
            dailyCost = 85,
            imageResId = R.drawable.car_honda_vezel
        ),
        Car(
            id = 4,
            name = "Toyota",
            model = "Yaris",
            year = 2021,
            rating = 4.4f,
            kilometers = 30000,
            dailyCost = 70,
            imageResId = R.drawable.car_toyota_yaris
        ),
        Car(
            id = 5,
            name = "Toyota",
            model = "Axio",
            year = 2020,
            rating = 4.1f,
            kilometers = 40000,
            dailyCost = 65,
            imageResId = R.drawable.car_toyota_axio
        )
    )

    fun getAllCars(): List<Car> = cars.map { it.copy() }

    fun getAvailableCars(): List<Car> = cars.filter { it.isAvailable }.map { it.copy() }

    fun getFavoriteCars(): List<Car> = cars.filter { it.isFavorite }.map { it.copy() }

    fun rentCar(carId: Int): Boolean {
        val carIndex = cars.indexOfFirst { it.id == carId }
        return if (carIndex != -1 && cars[carIndex].isAvailable) {
            cars[carIndex] = cars[carIndex].copy(isAvailable = false)
            true
        } else {
            false
        }
    }

    fun cancelRental(carId: Int): Boolean {
        val carIndex = cars.indexOfFirst { it.id == carId }
        return if (carIndex != -1 && !cars[carIndex].isAvailable) {
            cars[carIndex] = cars[carIndex].copy(isAvailable = true)
            true
        } else {
            false
        }
    }

    fun toggleFavorite(carId: Int): Boolean {
        val carIndex = cars.indexOfFirst { it.id == carId }
        return if (carIndex != -1) {
            val car = cars[carIndex]
            cars[carIndex] = car.copy(isFavorite = !car.isFavorite)
            cars[carIndex].isFavorite
        } else {
            false
        }
    }

    fun searchCars(query: String): List<Car> {
        val lowercaseQuery = query.lowercase()
        return cars.filter {
            it.name.lowercase().contains(lowercaseQuery) ||
                    it.model.lowercase().contains(lowercaseQuery)
        }.map { it.copy() }
    }

    fun sortCarsByRating(): List<Car> = cars.sortedByDescending { it.rating }.map { it.copy() }

    fun sortCarsByYear(): List<Car> = cars.sortedByDescending { it.year }.map { it.copy() }

    fun sortCarsByCost(): List<Car> = cars.sortedBy { it.dailyCost }.map { it.copy() }
}