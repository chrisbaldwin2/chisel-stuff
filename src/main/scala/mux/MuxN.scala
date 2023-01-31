
package mux

import chisel3._
import chisel3.util.log2Up

class MuxNInputBundle(val n: Int, val w: Int) extends Bundle {
  val values = Vec(n, UInt(w.W))
  val sel    = UInt(log2Up(n).W)
}

class MuxNOutputBundle(val w: Int) extends Bundle {
  val value = UInt(w.W)
}

/**
  * Compute Gcd using subtraction method.
  * Subtracts the smaller from the larger until register y is zero.
  * value input register x is then the Gcd.
  * Unless first input is zero then the Gcd is y.
  * Can handle stalls on the producer or consumer side
  */
class MuxN(n: Int, width: Int) extends Module {
  val input = IO(Input(new MuxNInputBundle(n, width)))
  val output = IO(Output(new MuxNOutputBundle(width)))

  output.value := input.values(input.sel)

}