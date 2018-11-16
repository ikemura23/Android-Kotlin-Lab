package com.ikemura.android_kotlin_lab

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.assertj.core.api.SoftAssertions
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InputCheckerTest {

    lateinit var target: InputChecker
    @Before
    fun setUp() {
        target = InputChecker() //InputCheckerのインスタンスを生成
    }

    @After
    fun tearDown() {
    }

    @Test // JUnit4ではテストケースとして実行したいメソッドをorg.junit.Test アノテーションで修飾する
    fun isValid() {
        val target = InputChecker()
        val actual = target.isValid("foo")  //fooという文字列を渡して、実測値をactualに格納
        assertThat(actual).isTrue()
    }

    @Test
    fun isValid_givenAlphaNumeric_returnTrue() {
        val actual = target.isValid("abc123")
        assertThat(actual).isTrue()
    }

    /**
     * 例外を検証
     */
    @Test(expected = IllegalArgumentException::class)
    fun isValid_givenNull_throwsIllegalArgumentException() {
        target.isValid(null)
    }

    @Ignore("仮実装なので一時的にスキップ")
    @Test
    fun temporarilySkipThisTest() {
        //略
    }

    @Test
    fun assertJ_test() {
        SoftAssertions().apply {
            assertThat("TOKYO")
                    .`as`("TEXT CHECK TOKYO")   //わかりやすいラベルをつけている、テストが失敗したときこのラベルが表示される
                    .isEqualTo("TOKYO")
                    .isEqualToIgnoringCase("tokyo") //大文字小文字を無視した場合に同一文字列かどうかを検証
                    .isNotEqualTo("KYOTO")
                    .isNotBlank()
                    .startsWith("TO")
                    .endsWith("YO")
                    .contains("OKY")
                    .matches("[A-Z]{5}")
                    .isInstanceOf(String::class.java)
        }.assertAll()
    }

    @Test
    fun assertJ_numeric() {
        assertThat(3.14159)
                .isNotZero()    //ゼロではなく
                .isNotNegative()    //負数ではなく
                .isGreaterThan(3.0) //３より大きく
                .isLessThanOrEqualTo(4.0)   //4以下
                .isBetween(3.0, 3.2)    //3.0から3.2の範囲内
                .isCloseTo(Math.PI, within(0.001))  //Math.PIで定義された円周率の定数から誤差0.001以内である
    }
}