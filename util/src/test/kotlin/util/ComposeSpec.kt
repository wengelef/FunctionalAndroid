package util

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class ComposeSpec : WordSpec() {

    init {
        "().compose" should {
            "pipe any 2 functions" {
                val f1 = Double::toInt
                val f2 = Int::toString

                val input = 2.0

                f2(f1(input)) shouldBe f1.compose(f2)(input)
            }
        }

        "compose()" should {
            "pipe any 2 functions" {
                val square: (Int) -> Int = { value -> value * value }
                val toString = Int::toString

                val input = 4

                toString(square(input)) shouldBe compose(square, toString)(input)
            }
        }
    }
}