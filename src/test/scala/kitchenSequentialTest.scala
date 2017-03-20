import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfter, FlatSpec}
import kitchenSequential._

import scala.util.{Success, Try}
/**
  * Created by Nenyi on 20/03/2017.
  */
class kitchenSequentialTest extends FlatSpec with BeforeAndAfter {
  "grind" should "be able to grind arabica beans" in {
    grind("arabica beans") shouldBe "ground coffee of arabica beans"
  }
  "heatWater" should "be able to heat" in {
    heatWater(Water(25)) shouldBe Water(85)
  }
  "frothMilk" should "froth milk" in {
    frothMilk("goats milk") shouldBe "frothed goats milk"
  }
  "brew" should "coffee from ground beans and hot water" in {
    val ground = grind("arabica beans")
    val water = heatWater(Water(25))

    brew(ground, water) shouldBe "espresso"
  }
  "combine" should "be able to make cappuccino" in {
    val ground = grind("arabica beans")
    val water = heatWater(Water(25))
    val espresso = brew(ground, water)
    val foam = frothMilk("milk")
    combine(espresso, foam) shouldBe "cappuccino"
  }

  "prepareCappuccino" should "make a cappuccino" in {
    prepareCappuccino shouldBe Success("cappuccino")
  }
}
