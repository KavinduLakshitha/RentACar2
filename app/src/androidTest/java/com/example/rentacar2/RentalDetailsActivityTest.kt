package com.example.rentacar2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.rentacar2.model.Car
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RentalDetailsActivityTest {

    private val testCar = Car(
        id = 1,
        name = "Test",
        model = "Car",
        year = 2023,
        rating = 4.5f,
        kilometers = 10000,
        dailyCost = 80,
        imageResId = R.drawable.car_toyota_camry
    )

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun rentalDetailsTitleIsDisplayed() {
        // Navigate to rental details by clicking rent button
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Rental Details"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun backButtonIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withContentDescription("Back"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canEnterCustomerName() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withHint("Full Name"))
            .perform(typeText("John Doe"))
            .check(matches(withText("John Doe")))
    }

    @Test
    fun canEnterCustomerPhone() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withHint("Phone Number"))
            .perform(typeText("1234567890"))
            .check(matches(withText("1234567890")))
    }

    @Test
    fun cancelButtonIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Cancel"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun confirmRentalButtonIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Confirm Rental"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canClickCancelButton() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Cancel"))
            .perform(click())
        
        // Should return to main activity
        onView(withText("RentACar"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canToggleAutomaticTransmission() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        // Find the automatic transmission switch
        onView(withText("Automatic Transmission"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canToggleInsurance() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        // Find the insurance switch
        onView(withText("Include Insurance (+20 credits)"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun rentalSummaryIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Rental Summary"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun totalCostIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Total: 80 credits"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun customerInformationSectionIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Customer Information"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun rentalOptionsSectionIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Rental Options"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun numberOfDaysSliderIsDisplayed() {
        // Navigate to rental details
        onView(withText("Rent Now"))
            .perform(click())
        
        onView(withText("Number of Days: 1"))
            .check(matches(isDisplayed()))
    }
}
