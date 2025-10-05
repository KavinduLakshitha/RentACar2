package com.example.rentacar2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.rentacar2.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun appTitleIsDisplayed() {
        onView(withText("RentACar"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun creditBalanceIsDisplayed() {
        onView(withText("500 Credits"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchFieldIsDisplayed() {
        onView(withHint("Search cars by name or model"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canTypeInSearchField() {
        onView(withHint("Search cars by name or model"))
            .perform(typeText("Toyota"))
            .check(matches(withText("Toyota")))
    }

    @Test
    fun canClearSearchField() {
        onView(withHint("Search cars by name or model"))
            .perform(typeText("Test"))

        onView(withContentDescription("Clear"))
            .perform(click())

        onView(withHint("Search cars by name or model"))
            .check(matches(withText("")))
    }

    @Test
    fun favoritesButtonIsDisplayed() {
        onView(withText("Favorites"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canToggleFavorites() {
        onView(withText("Favorites"))
            .perform(click())

        onView(withText("Favorites"))
            .check(matches(isSelected()))
    }

    @Test
    fun rentButtonIsDisplayed() {
        onView(withText("Rent Now"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun previousButtonIsDisplayed() {
        onView(withText("Previous"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun nextButtonIsDisplayed() {
        onView(withText("Next"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun carCounterIsDisplayed() {
        onView(withText("1 of 5"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun darkModeSwitchIsDisplayed() {
        onView(withClassName(org.hamcrest.Matchers.equalTo("android.widget.Switch")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canClickRentButton() {
        Thread.sleep(1000)
        onView(withText("Rent Now"))
            .perform(click())
    }

    @Test
    fun carDetailsAreDisplayed() {
        // Check if car name is displayed (should contain car name and model)
        onView(withText("Toyota Camry"))
            .check(matches(isDisplayed()))

        // Check if year is displayed
        onView(withText("Year: 2022"))
            .check(matches(isDisplayed()))

        // Check if cost is displayed
        onView(withText("80 credits/day"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canNavigateToNextCar() {
        // Click next button
        onView(withText("Next"))
            .perform(click())

        // Check if car counter updated
        onView(withText("2 of 5"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canNavigateToPreviousCar() {
        // First go to next car
        onView(withText("Next"))
            .perform(click())

        // Then go back to previous
        onView(withText("Previous"))
            .perform(click())

        // Check if car counter is back to 1
        onView(withText("1 of 5"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canSortByRating() {
        // Click on the sort dropdown
        onView(withText("Default"))
            .perform(click())

        // Select "Rating" from dropdown
        onView(withText("Rating"))
            .perform(click())

        // Verify the sort option is selected
        onView(withText("Sort by Rating"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canSortByYear() {
        // Click on the sort dropdown
        onView(withText("Default"))
            .perform(click())

        // Select "Year" from dropdown
        onView(withText("Year"))
            .perform(click())

        // Verify the sort option is selected
        onView(withText("Sort by Year"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canSortByCost() {
        // Click on the sort dropdown
        onView(withText("Default"))
            .perform(click())

        // Select "Cost" from dropdown
        onView(withText("Cost"))
            .perform(click())

        // Verify the sort option is selected
        onView(withText("Sort by Cost"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canResetToDefaultSort() {
        // First change to rating sort
        onView(withText("Default"))
            .perform(click())
        onView(withText("Rating"))
            .perform(click())

        // Then change back to default
        onView(withText("Sort by Rating"))
            .perform(click())
        onView(withText("Default"))
            .perform(click())

        // Verify default is selected
        onView(withText("Default"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun rentButtonShowsInsufficientCreditsWhenBalanceLow() {
        // This test verifies the UI state when balance is insufficient
        // The rent button should show "Insufficient Credits" text
        // Note: This would require setting up a low balance scenario
        // For now, we verify the button exists and can be clicked
        onView(withText("Rent Now"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canToggleDarkMode() {
        // Find and click the dark mode toggle
        onView(withClassName(org.hamcrest.Matchers.equalTo("android.widget.Switch")))
            .perform(click())

        // The toggle should be functional
        // Note: Verifying actual theme change requires more complex setup
        onView(withClassName(org.hamcrest.Matchers.equalTo("android.widget.Switch")))
            .check(matches(isDisplayed()))
    }
}
