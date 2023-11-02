import hardfloat._
import chisel3 ._
import chisel3 . iotesters ._
// import org.scalatest .FlatSpec
class FMUL extends Module{
    val io = IO(new Bundle {
        val a = Input(Bits(32.W))
        val b = Input(Bits(32.W))
        val roundingMode = Input(UInt(3.W))
        val detectTininess = Input(UInt(1.W))

        val expected = new Bundle{
            val out = Input(Bits(32.W))
            val exceptionFlags = Input(Bits(5.W))
        //     val recOut = Output(Bits(33.W))
        }
        // val actual = new Bundle{
        val out = Output(Bits(32.W))
        //     val exceptionFlags = Output(Bits(5.W))
        // }
        // val check = Output(Bool())
        // val pass = Output(Bool())
    })

val fmul = Module(new ValExec_MulAddRecF32_mul)
    fmul.io.a := io.a
    fmul.io.b := io.b
    fmul.io.roundingMode := 0.U
    fmul.io.detectTininess := 1.U
    fmul.io.expected.out := 0.U
    fmul.io.expected.exceptionFlags := 0.U
    // io.expected.recOut := fmul.io.expected.recOut
    io.out := fNFromRecFN(8,24,fmul.io.actual.out)
    // io.actual.exceptionFlags := fmul.io.actual.exceptionFlags
    // io.check := fmul.io.check
    // io.pass := fmul.io.pass
}
// class TesterSimple (dut:FMUL) extends PeekPokeTester(dut){
// poke(dut.io.a,1051931443.S)
// poke(dut.io.b,1045220556.S)
// // poke(dut.io.expected.out,3.S)
// poke(dut.io.roundingMode,0.U)
// step(1)
// println("Result a*b is :" +peek(dut.io.actual.out).toString)
// println(" exceptionFlags is :" +peek(dut.io.actual.exceptionFlags).toString)
// println(" Pass is :" +peek(dut.io.check).toString)
// // println(" expected recOut is :" +peek(dut.io.expected.recOut).toString)
// // println(peek(dut.io.expected.recOut))
// }
// class WaveformSpec extends FlatSpec with Matchers{
//     "Waveform" should "pass" in{
//         Driver.execute(Array("-generate-vcd-output","on"),() => new add()
//         ){ c=>
//             new WaveformTester(c)
//         }should be (true)
//     }
// }
// object FMUL extends App{
//     chisel3. iotesters.Driver(()=> new FMUL()){ c =>
//         new TesterSimple(c)
//     }
// }


object FMUL extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new FMUL())
}
