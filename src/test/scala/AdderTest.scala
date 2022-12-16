import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AdderTest extends AnyFlatSpec with Matchers {
  it should "work" in {
    val adder = Adder.makeAdder(5)
    adder.add(3) shouldBe 8
  }
}
