// See README.md for license details.

package mux

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec
import chisel3.experimental.BundleLiterals._

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly mux.Mux2Tester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly mux.Mux2Tester'
  * }}}
  */
class Mux2Spec extends AnyFreeSpec with ChiselScalatestTester {

  "Mux should use raw chisel types" in {
    test(new Mux2(16)) { dut =>

      for ( x <- 0 to 10; y <- 0 to 10; z <- 0 to 1) {
        // print(x, y, z)
        dut.input.value1.poke(x.U)
        dut.input.value2.poke(y.U)
        dut.input.sel.poke(z.U)
        if (z == 0) {
          dut.output.value.expect(x.U) 
        } else {
          dut.output.value.expect(y.U)
        }
      }

    }
  }

  "Mux should use bundles" in {
    test(new Mux2(16)) { dut =>
      // dut.input.initSource()
      // dut.input.setSourceClock(dut.clock)
      // dut.output.initSink()
      // dut.output.setSinkClock(dut.clock)

      val testValues = for { x <- 0 to 10; y <- 0 to 10; z <- 0 to 1} yield (x, y, z)
      val inputSeq = testValues.map { case (x, y, z) => (new Mux2InputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U, _.sel -> z.B) }
      val resultSeq = testValues.map { case (x, y, z) =>
        (new Mux2OutputBundle(16)).Lit(_.value -> {
            var out = x
            if (z == 1) {
              out = y
            }
            out.U
          }
        )
      }

      for (i <- 0 to 10) {
        dut.input.poke(inputSeq(i))
        dut.clock.step()
        dut.output.expect(resultSeq(i)) 
      }

    }
  }
}
