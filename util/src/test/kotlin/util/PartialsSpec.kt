package util

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class PartialsSpec : WordSpec() {

    init {
        "partially()" should {
            "inject 1 dependency into a 2 params function" {
                val dependency = "Hello"
                val argument = "World"
                val expectation = "HelloWorld"

                val funWith2Params: (String, String) -> String = { x, y -> x + y }
                val partial = partially(dependency, funWith2Params)

                partial(argument) shouldBe expectation
            }
            "inject 2 dependencies into a 3 params function" {
                val dependencyA = "Hello"
                val dependencyB = "World"
                val argument = "Again"
                val expectation = "HelloWorldAgain"

                val funWith3Params: (String, String, String) -> String = { x, y, z -> x + y + z }
                val partial = partially(dependencyA, dependencyB, funWith3Params)

                partial(argument) shouldBe expectation
            }
        }
        "().partially" should {
            "inject 1 dependency into a 2 params function" {
                val dependency = 1
                val argument = 2
                val expectation = 3

                val funWith2Params: (Int, Int) -> Int = { x, y -> x + y }
                val partially = funWith2Params.partially(dependency)

                partially(argument) shouldBe expectation
            }

            "inject 2 dependency into a 3 params function" {
                val dependencyA = 1
                val dependencyB = 2
                val argument = 3
                val expectation = 6

                val funWith3Params: (Int, Int, Int) -> Int = { x, y, z -> x + y + z }
                val partially = funWith3Params.partially(dependencyA, dependencyB)

                partially(argument) shouldBe expectation
            }
        }
    }
}