
package mux

import chisel3._
import chisel3.util.{Decoupled, log2Up}

class Mux2InputBundle(val w: Int) extends Bundle {
  val value1 = UInt(w.W)
  val value2 = UInt(w.W)
  val sel    = Bool()
}

class Mux2OutputBundle(val w: Int) extends Bundle {
  val value = UInt(w.W)
}

/**
  * Compute Gcd using subtraction method.
  * Subtracts the smaller from the larger until register y is zero.
  * value input register x is then the Gcd.
  * Unless first input is zero then the Gcd is y.
  * Can handle stalls on the producer or consumer side
  */
class Mux2(width: Int) extends Module {
  val input = IO(Input(new Mux2InputBundle(width)))
  val output = IO(Output(new Mux2OutputBundle(width)))

  output.value := input.value1
  when(input.sel === true.B) {
    output.value := input.value2
  }

}

