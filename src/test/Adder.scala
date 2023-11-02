package test
 
import chisel3._
 
class Adder(n: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(n.W))
    val b = Input(UInt(n.W))
    val s = Output(UInt(n.W))
    val cout = Output(UInt(1.W))  
  })
 
  io.s := (io.a +& io.b)(n-1, 0)
  io.cout := (io.a +& io.b)(n)
}
 
// adderGen.scala
package test
 
object AdderGen extends App {
  chisel3.Driver.execute(args, () => new Adder(args(0).toInt))
}
