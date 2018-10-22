package com.ikemura.android_kotlin_lab

import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class InputCheckerTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun isValid() {
//        assertThat(1 + 1, `is`(2))
//        assertThat(100, greaterThan(50))
//        assertThat(listOf("for", "bar", "baz"), hasItem("baz"))
        val target = InputChecker()
        val actual = target.isValid("test")
        assertThat(actual, `is`(true))
    }
}