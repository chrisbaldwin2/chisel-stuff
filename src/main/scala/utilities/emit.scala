package utilities

import chisel3.stage.ChiselStage

object VerilogMain extends App {
  (new ChiselStage).emitVerilog(
            new DecoupledExample(16),
            Array("--target-dir", "verilog/utilities")
          )
  (new ChiselStage).emitVerilog(
            new NewCount(15),
            Array("--target-dir", "verilog/utilities")
          )
  (new ChiselStage).emitVerilog(
            new OtherCount(15),
            Array("--target-dir", "verilog/utilities")
          )
  (new ChiselStage).emitVerilog(
            new OneHot(16),
            Array("--target-dir", "verilog/utilities")
          )
  (new ChiselStage).emitVerilog(
            new Pipe(new TestBundle),
            Array("--target-dir", "verilog/utilities")
          )
  (new ChiselStage).emitVerilog(
            new Arb,
            Array("--target-dir", "verilog/utilities")
          )
}