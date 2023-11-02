import hardfloat._
import chisel3 ._
import chisel3 . iotesters . PeekPokeTester
import chisel3 . iotesters . Driver
import org. scalatest ._
// import org.scalatest .FlatSpec
class FADD extends Module{
    val io = IO(new Bundle {
        val a = Input(Bits(32.W))
        val b = Input(Bits(32.W))
        val roundingMode = Input(UInt(3.W))
        val detectTininess = Input(UInt(1.W))
        val expected = new Bundle{
            val out = Input(Bits(32.W))
            val exceptionFlags = Input(Bits(5.W))
            // val recOut = Output(Bits(33.W))
        }
        // val actual = new Bundle{
            val out = Output(Bits(32.W))
            // val exceptionFlags = Output(Bits(5.W))
        // }
        // val check = Output(Bool())
        // val pass = Output(Bool())
    })

val fadd = Module(new ValExec_MulAddRecF32_add)
    fadd.io.a := io.a
    fadd.io.b := io.b
    fadd.io.roundingMode := 0.U
    fadd.io.detectTininess := 1.U
    fadd.io.expected.out := 0.U
    fadd.io.expected.exceptionFlags := 0.U
    // io.expected.recOut := fadd.io.expected.recOut
    io.out := fNFromRecFN(8,24,fadd.io.actual.out)
    // io.actual.exceptionFlags := fadd.io.actual.exceptionFlags
    // io.check := fadd.io.check
    // io.pass := fadd.io.pass
}
// class TesterSimple (dut:FADD) extends PeekPokeTester(dut){
// poke(dut.io.a,1044509077.S)
// poke(dut.io.b,1061440250.S)
// poke(dut.io.expected.out,3.S)
// poke(dut.io.roundingMode,0.U)
// step(1)
// println("Result a+b is :" +peek(dut.io.actual.out).toString)
// println(" exceptionFlags is :" +peek(dut.io.actual.exceptionFlags).toString)
// println(" Pass is :" +peek(dut.io.check).toString)
// println(" expected recOut is :" +peek(dut.io.expected.recOut).toString)
// // println(peek(dut.io.expected.recOut))
// }
// class WaveformSpec extends FlatSpec with Matchers{
//     "Waveform" should "pass" in{
//         Driver.execute(Array("--generate-vcd-output","on"),() => new FADD()
//         ){ c=>
//             new TesterSimple(c)
//         }should be (true)
//     }
// }

// object AdderTestGen extends App {
//   chisel3.iotesters.Driver.execute(args, () => new FADD())(c => new TesterSimple(c))
// }
// object TesterSimple extends App{
//     chisel3. iotesters.Driver(()=> new FADD()){ c =>
//         new TesterSimple(c)
//     }
// }


object FADD extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new FADD())
}
