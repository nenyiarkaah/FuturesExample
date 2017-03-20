import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import org.scalatest.time.{Millis, Seconds, Span}
import kitchen._

import scala.util.Failure

/**
  * Created by Nenyi on 20/03/2017.
  */
class kitchenTest extends FlatSpec with BeforeAndAfter with ScalaFutures {
  implicit val defaultPatience =
    PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "grind" should "be able to grind arabica beans" in {
    whenReady(grind("arabica beans")){ result => result shouldBe "ground coffee of arabica beans"}
  }
  "grind" should "throw GrindingException when grinding baked beans" in {
    val groundCoffee = grind("baked beans")
    whenReady(groundCoffee.failed){ e => e shouldBe a [GrindingException]}
  }
  "heatWater" should "be able to heat" in {
    whenReady(heatWater(Water(25))){ result => result shouldBe Water(85)}
  }
  "frothMilk" should "froth milk" in {
    whenReady(frothMilk("goats milk")){ result => result shouldBe "frothed goats milk"}
  }
  "brew" should "coffee from ground beans and hot water" in {
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(20))
    whenReady(brew(groundCoffee.futureValue, heatedWater.futureValue)){ result => result shouldBe "espresso"}
  }

  "combine" should "be able to make cappuccino" in {
    val ground = grind("arabica beans")
    val water = heatWater(Water(25))
    val espresso = brew(ground.futureValue, water.futureValue).futureValue
    val foam = frothMilk("milk").futureValue
    combine(espresso, foam) shouldBe "cappuccino"
  }

  "prepareCappuccino" should "make a cappuccino" in {
    whenReady(prepareCappuccino){ result => result shouldBe "cappuccino"}
  }
}
