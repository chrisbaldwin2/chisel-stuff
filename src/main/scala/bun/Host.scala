
package bun

import chisel3._
import chisel3.util.{Decoupled, log2Up}


case class HostParms(
  usingBundle1: Boolean = true,
  usingBundle2: Boolean = true
)

trait HasHostParameters {
  implicit val params: HostParms
}

abstract class HostBundle(implicit p : HostParms) extends Bundle with HasHostParameters {
  val params = p
}

class Bundle1 extends Bundle {
  val value = UInt(16.W)
}

class Bundle2 extends Bundle {
  val value = UInt(16.W)
}

class InBundle(implicit p : HostParms) extends HostBundle {
  val b1 = Option.when(params.usingBundle1)(new Bundle1)
  val b2 = Option.when(params.usingBundle2)(new Bundle2)
}

class OutBundle extends Bundle {
  val value = UInt(16.W)
}

class Host(implicit p : HostParms) extends Module {

  val in = IO(Input(new InBundle))
  val out = IO(Output(new OutBundle))

  val r = Reg(UInt(16.W))
  out.value := 0.U

  in.b2 match {
    case None => {}
    case Some(y) => r := y.value
  }
    
  in.b1 match {
    case None => {}
    case Some(y) => out.value := y.value | r
  }

}
