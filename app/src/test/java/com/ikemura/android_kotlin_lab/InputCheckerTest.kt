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

    @Test // JUnit4ではテストケースとして実行したいメソッドをorg.junit.Test アノテーションで修飾する
    fun isValid() {
        val target = InputChecker() //InputCheckerのインスタンスを生成
        val actual = target.isValid("foo")  //fooという文字列を渡して、実測値をactualに格納
        assertThat(actual, `is`(true))  //assertThat()で実測値と期待値が一致することを検証
    }
}