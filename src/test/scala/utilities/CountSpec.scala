// See README.md for license details.

package utilities

import chisel3._
import chiseltest._
import org.scalatest.matchers.should.Matchers

import org.scalatest.freespec.AnyFreeSpec
import chisel3.experimental.BundleLiterals._

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly utilities.CountSpec
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly utilities.CountSpec'
  * }}}
  */
class CountSpec extends AnyFreeSpec with ChiselScalatestTester with Matchers {
  for (n <- 1 to 10) {
    "NewCount should count to " + n in {
        test(new NewCount(n)) { dut =>
        dut.inc.poke(true.B)


        for (i <- 0 to n+1) {
            // println(s"n=$n, i=$i, e=${i % n} -> r=${dut.value.peek().litValue}")
            // println(s"  expect ${((i%n) == 0 ) && (i>0)} wrap ${dut.wrap.peek().litValue}")
            dut.value.expect((i % n).U)
            dut.wrap.expect(
              {
                if (i == 0)
                  false
                else 
                  (i%n) == 0
              }.B
            )
            dut.clock.step(1)   
        }
    }}
  }
  
  "NewCount should count to 10 intermitently" in {
      test(new NewCount(10)) { dut =>
      val n = 10
      var value: Int = 0
      var wrap: Boolean = false

      for (i <- 0 to 2*n+1) {
        val inc: Boolean = (i%2) == 1

        // println(s"n=$n, i=$i, e=${i % n} -> r=${dut.value.peek().litValue}")
        // println(s"  expect ${((i%n) == 0 ) && (i>0)} wrap ${dut.wrap.peek().litValue}")
        dut.inc.poke(inc.B)
        dut.value.expect(value.U)
        dut.wrap.expect(wrap.B)
        dut.clock.step(1)  
        if (inc && value == (n-1))
          wrap = true
        else 
          wrap = false
        if (inc) 
          value = (value + 1) % n 
      }
  }}

  "An exception should be thrown with n = 0" in {
    an [java.lang.IllegalArgumentException] should be thrownBy test(new NewCount(0)) {dut => 
      dut.clock.step(1)
    } 
  }
}
