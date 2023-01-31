package utilities

import chisel3._
import chisel3.util.{Decoupled, log2Down, log2Up}


// From console run: sbt 'runMain utilities.VerilogMain'
object NewCount{
    def apply(n: Int) = {
        new NewCount(n)
    }
}


/**
  * Create a counter
  * n:     The value to stop the count
  * inc:   Want to increment when true
  * 
  * wrap:  The value has rolled to zero
  * value: Holds the accumulated count
  * 
  */
class NewCount(n: Int) extends Module {
    require(n > 0)
    val width = log2Up(n)

    val inc = IO(Input(Bool()))
    val wrap = IO(Output(Bool()))
    val value = IO(Output(UInt(width.W)))

    val r_count = RegInit(0.U(width.W))
    val r_wrap = RegInit(false.B)

    r_wrap := false.B
    // when(inc === true.B && r_count === n.U) // Creates an off by one error
    when(inc === true.B && r_count === (n-1).U)
    {
        r_count := 0.U 
        r_wrap := true.B
    }
    .elsewhen(inc === true.B)
    {
        r_count := r_count + 1.U
    }
    value := r_count
    wrap := r_wrap
}

class OtherCount(n: Int) extends Module {
    require(n > 0)
    val width = log2Down(n) + 1

    val inc = IO(Input(Bool()))
    val wrap = IO(Output(Bool()))
    val value = IO(Output(UInt(width.W)))

    val r_count = RegInit(0.U(width.W))
    // No r_wrap needed

    when(inc === true.B && r_count === n.U)
    {
        r_count := 1.U
    }
    .elsewhen(r_count === n.U)
    {
        r_count := 0.U
    }
    .elsewhen(inc === true.B)
    {
        r_count := r_count + 1.U
    }

    value := r_count
    wrap := false.B
    when(r_count === n.U)
    {
        value := 0.U
        wrap := true.B
    }
}

