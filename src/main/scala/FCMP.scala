import hardfloat._
import chisel3 ._
import chisel3 . iotesters ._
// import org.scalatest .FlatSpec
class FCMP extends Module{
    val io = IO(new Bundle {
        val a = Input(Bits(32.W))//
        val b = Input(Bits(32.W))//
        val expected = new Bundle{
            val out = Input(Bool())//
            val exceptionFlags = Input(Bits(5.W))//
        }
        // val actual = new Bundle{
            val out = Output(Bool())
        //     val exceptionFlags = Output(Bits(5.W))
        // }
        // val check = Output(Bool())
        // val pass = Output(Bool())
        // val out_1 = Output(Bits(32.W))
        // val out_2 = Output(Bits(32.W))
    })
// val bigger = Bool()
val fcmp = Module(new ValExec_CompareRecF32_le)
    fcmp.io.a := io.a
    fcmp.io.b := io.b
    fcmp.io.expected.out := 0.U
    fcmp.io.expected.exceptionFlags := 0.U
    
    io.out := fcmp.io.actual.out
    
    // io.actual.exceptionFlags := fcmp.io.actual.exceptionFlags
    
    // when(io.actual.out){
    // io.out_1 := io.a
    // io.out_2 := io.b

    // } .otherwise{
    // io.out_1 := io.b
    // io.out_2 := io.a    
    // }
    // io.check := fcmp.io.check
    // io.pass := fcmp.io.pass
}
// class TesterSimple (dut:FCMP) extends PeekPokeTester(dut){
// poke(dut.io.a,1108153466.S)
// poke(dut.io.b,3245971865L.S)
// poke(dut.io.expected.out,3.S)
// poke(dut.io.roundingMode,0.U)
// step(1)
// println("The smaller is :" +peek(dut.io.out_1).toString)
// println("The bigger is :" +peek(dut.io.out_2).toString)
// println(" exceptionFlags is :" +peek(dut.io.actual.exceptionFlags).toString)
// println(" Pass is :" +peek(dut.io.check).toString)
// println(" expected recOut is :" +peek(dut.io.expected.recOut).toString)
// println(peek(dut.io.expected.recOut))
// }
// class WaveformSpec extends FlatSpec with Matchers{
//     "Waveform" should "pass" in{
//         Driver.execute(Array("-generate-vcd-output","on"),() => new add()
//         ){ c=>
//             new WaveformTester(c)
//         }should be (true)
//     }
// }
// object TesterSimple extends App{
//     chisel3. iotesters.Driver(()=> new FCMP()){ c =>
//         new TesterSimple(c)
//     }
// }


object FCMP extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new FCMP())
}
