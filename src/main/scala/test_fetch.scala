package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class fetch   extends Module{
    val io = IO(new Bundle{
        val a       = Input(UInt(5.W))
        val b       = Output(UInt(2.W))
    })

    io.b                := io.a(1,0)
}
// object fetch extends App {
//   chisel3.Driver.execute(args, () => new fetch())
// }
class fetchter (dut:fetch) extends PeekPokeTester(dut){
        poke(dut.io.a,25.U)
        step(1)
        poke(dut.io.a,26.U)
        step(1)
        poke(dut.io.a,27.U)
        step(1)
}
object fetchtest extends App {
  chisel3.iotesters.Driver.execute(args, () => new fetch())(c => new fetchter(c))
}