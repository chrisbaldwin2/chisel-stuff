
package bun

import chisel3._

class SomeModule extends Module {
    val io = IO(Input(UInt(4.W)))
    val v = Wire(UInt(4.W))
    v := io
}