package mux 

import chisel3.stage.ChiselStage

object VerilogMain extends App {
  (new ChiselStage).emitVerilog(
            new Mux2(16),
            Array("--target-dir", "verilog/mux")
            )
  (new ChiselStage).emitVerilog(
            new MuxN(5, 16),
            Array("--target-dir", "verilog/mux")
            )
}