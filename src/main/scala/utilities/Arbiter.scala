package utilities

import chisel3._
import chisel3.util.{Decoupled, Arbiter}

// Arb must be a decoupled interface
class Arb extends Module {
    val p0 = IO(Flipped(Decoupled(UInt(4.W))))
    val p1 = IO(Flipped(Decoupled(UInt(4.W))))
    val out = IO(Decoupled(UInt(4.W)))

    val arb = Module(new Arbiter(UInt(), 2))
    arb.io.in(0) <> p0
    arb.io.in(1) <> p1
    out <> arb.io.out
}
