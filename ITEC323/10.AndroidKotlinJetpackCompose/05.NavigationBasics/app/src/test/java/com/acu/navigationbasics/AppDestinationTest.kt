package com.acu.navigationbasics

import com.acu.navigationbasics.navigation.AppDestination
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the navigation route helper.
 */
class AppDestinationTest {
    @Test
    fun studentDetailRoute_ValidId_ReturnsConcreteRoute() {
        val result = AppDestination.studentDetailRoute(7)

        assertEquals("student/7", result)
    }

    @Test
    fun titleForRoute_DetailRoute_ReturnsDetailTitle() {
        val result = AppDestination.titleForRoute("student/2")

        assertEquals("Student Detail", result)
    }
}
