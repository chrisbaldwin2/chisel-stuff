package utilities

import chisel3._
import chisel3.util.{Valid, log2Up}

class OneHot(n: Int) extends Module {
    val in = IO(Input(UInt(n.W)))
    val m = log2Up(n)
    val out = IO(Output(Valid(UInt(m.W))))
    out.valid := in.orR
    out.bits := DontCare
    for(i <- n-1 to 0 by -1 )
    {
        when(in(i) === true.B)
        {
            out.bits := i.U 
        }
    }
}