package utilities

import chisel3._
import chisel3.experimental.DataMirror
import chisel3.util.{Valid, log2Up, RegEnable}

class TestBundle extends Bundle {
    val v1 = UInt(4.W)
    val v2 = UInt(10.W)
    val v3 = UInt(2.W)
}

class Pipe[T <: Bundle](gen: T) extends Module {
    val in = IO(Flipped(Valid(gen)))
    val out = IO(Valid(gen))

    out.bits := RegEnable(in.bits, in.valid)
    out.valid := RegNext(in.valid, false.B)
}

// https://stackoverflow.com/questions/2886446/how-to-get-methods-list-in-scala
// https://groups.google.com/g/chisel-users/c/vcLw_abF2_g 
