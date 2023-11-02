package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class Sint   extends Module{
    val io = IO(new Bundle{
        val a                = Input(SInt(6.W))
        val a_out       = Output(SInt(6.W))
        val b                = Input(UInt(6.W))
        val b_out       = Output(UInt(6.W))
        val positive  = Output(UInt(1.W))
        val complement    = Output(SInt(6.W))
    })

    io.a_out                := io.a
    io.positive          := positive_s(io.a)
    io.b_out                := io.b

    io.complement := complement_s(io.a)
}
// object fetch extends App {
//   chisel3.Driver.execute(args, () => new fetch())
// }
class sinttest (dut:Sint) extends PeekPokeTester(dut){
        poke(dut.io.a,-25.S)
        // poke(dut.io.b,-25.U)
        step(1)
        poke(dut.io.a,26.U)
        poke(dut.io.b,26.U)
        step(1)
        // poke(dut.io.a,27.U)

        step(1)
}
object Sint_test extends App {
  chisel3.iotesters.Driver.execute(args, () => new Sint())(c => new sinttest(c))
}