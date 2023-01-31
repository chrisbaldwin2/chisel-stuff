// See README.md for license details.

package bun

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
class HostSpec extends AnyFreeSpec with ChiselScalatestTester {
  for (usingBundle1 <- true :: false :: Nil ; usingBundle2 <- true :: false :: Nil) {
    implicit val p: HostParms = new HostParms(usingBundle1, usingBundle2)
    ("Host should exercise bundle combination b1" + usingBundle1 + " b2 " + usingBundle2) in {
        test(new Host) { dut =>
        val r = new scala.util.Random(0x13eb41e5)
        val numSteps = 10
        val b1_s: Seq[UInt]  = Seq.tabulate(numSteps)(i => r.nextInt().abs.U)
        val b2_s: Seq[UInt]  = Seq.tabulate(numSteps)(i => r.nextInt().abs.U)
        val out_s: Seq[UInt] = Seq.tabulate(numSteps)(i => {
            if(i == 0)
                0.U
            else
                b1_s(i) | b2_s(i-1)
        })

        for (i <- 0 until numSteps) {
            // if(usingBundle1) 
            //     dut.in.b1.poke(b1_s(i))
            // if(usingBundle2) 
            //     dut.in.b2.poke(b2_s(i))

            (usingBundle1, usingBundle2) match {
                case (true, true) => dut.out.value.expect(out_s(i))  
                case (true, false) => dut.out.value.expect(b2_s(i))  
                case (false, true) => dut.out.value.expect(0.U)  
                case (false, false) => dut.out.value.expect(0.U)  
                case y => throw new Exception("Bad match with " + y)
            }

            dut.clock.step(1)
        }}
    }
  }

/*
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
*/
}
