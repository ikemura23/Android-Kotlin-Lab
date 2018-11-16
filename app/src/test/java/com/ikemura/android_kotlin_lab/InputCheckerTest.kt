package com.ikemura.android_kotlin_lab

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
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

    @Test
    fun assertJ_collection() {
        val target = listOf("Giants", "Dodgers", "Athletics")
        assertThat(target)
                .hasSize(3) //要素の個数を検証
                .contains("Dodgers") //要素がリストに含まれるかどうか
                .containsOnly("Athletics", "Dodgers", "Giants") //順不同で等価な要素のみが含まれているか
                .containsExactly("Giants", "Dodgers", "Athletics") //等価な要素のみが同じ順序で同じ組み合わせで重複なしに含まれるか
                .doesNotContain("Padres") //含んでいないか
    }

    @Test
    fun assetJ_collection_filtering() {
        data class BallTeam(val name: String, val city: String, val stadium: String)

        val target = listOf(
                BallTeam("Giants", "San Francisco", "AT&T Park"),
                BallTeam("Dodgers", "Los Angels", "Dodger Stadium"),
                BallTeam("Angels", "Los Angels", "Angel Stadium"),
                BallTeam("Athletics", "Oakland", "Oakland Coliseum"),
                BallTeam("Padres", "San Diego", "Petco Park")
        )

        assertThat(target)
                .filteredOn { team -> team.city.startsWith("San") } //Sanで始まり
                .filteredOn { team -> team.city.endsWith(("Francisco")) } //Franciscoで終わるものをフィルタリング
                .extracting("name", String::class.java) //name:Stringプロパティだけを取り出す
                .containsExactly("Giants")  //extractingで取り出されたname要素はGiantsである

        assertThat(target)
                .filteredOn { team -> team.city == "Los Angels" } //cityがLos Angelsのものをフィルタリング
                .extracting("name", "stadium") //nameとstadiumだけ取り出す
                .containsExactly(
                        tuple("Dodgers", "Dodger Stadium"), //tupleでこのプロパティだけをもった一時的な型として扱って比較する
                        tuple("Angels", "Angel Stadium")
                )
    }
}