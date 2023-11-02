 package hardfloat
import chisel3._
import chisel3.util.log2Ceil
import chisel3 . iotesters ._
import org.scalatest._
import chisel3.iotesters.PeekPokeTester
import chisel3.iotesters.Driver

 class Testerstack (dut: Stack) extends PeekPokeTester(dut){

    poke(dut.io.push,true.B)
    poke(dut.io.dataIn,1.S)
    // poke(dut.io.en,true.B)
    step(1)
    poke(dut.io.push,true.B)
    poke(dut.io.dataIn,2.S)
    // poke(dut.io.en,true.B)
    step(1)
    poke(dut.io.push,false.B)
   
    poke(dut.io.pop,true.B)
    step(1)
    poke(dut.io.pop,true.B)

    // step(1)
    // poke(dut.io.pop,true.B)
    // step(1)
    // poke(dut.io.pop,true.B)

    step(1)
    poke(dut.io.pop,false.B)
    step(20)
}

object stacktest extends App {
  chisel3.iotesters.Driver.execute(args, () => new Stack(8))(c => new Testerstack(c))
}
// class WaveformSpec extends FlatSpec with Matchers {
//     "Waveform" should "pass" in {
//     Driver.execute(Array("--generate-vcd-output", "on"), () =>
//             new Stack(8)) { c =>
//         new WaveformTester (c)
//     } should be (true)
//     }
// }//生成.vcd