package bun

import chisel3.stage.ChiselStage

object VerilogMain extends App {
  implicit val p = new HostParms()
  (new ChiselStage).emitVerilog(
            new Host,
            Array("--target-dir", "verilog/bun")
            )
  (new ChiselStage).emitVerilog(
            new SomeModule,
            Array("--target-dir", "verilog/some")
            )
}