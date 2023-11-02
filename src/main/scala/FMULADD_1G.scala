package hardfloat
import Chisel._
// import chisel3 ._
import chisel3 . iotesters ._


class MULAdd extends Module{
    val io = IO(new Bundle {
        val a = Input(Bits(32.W))
        val b = Input(Bits(32.W))
        val c = Input(Bits(32.W))
        // val roundingMode   = Input(UInt(3.W))
        // val detectTininess = Input(UInt(1.W))        
                  
        // val expected = new Bundle{ 
        //     val out = Input(Bits(32.W))
        //     val exceptionFlags = Input(Bits(5.W))
        //     val recOut =Output(Bits(33.W))
        // }

        // val actual = new Bundle {
            val out = Output(Bits(32.W))
        //     val exceptionFlags = Output(Bits(5.W))
        // }

        // val check = Output(Bool())
        // val pass = Output(Bool())
    })
    val input_a     = RegInit(0.U(32.W))
    val input_b     = RegInit(0.U(32.W))
    val input_c     = RegInit(0.U(32.W))

    input_a         := recFNFromFN(8,24,io.a)
    input_b         := recFNFromFN(8,24,io.b)
    input_c         := recFNFromFN(8,24,io.c)
val muladd_1G = Module (new MulAddRecFN(8,24))

    muladd_1G.io.op := UInt(0)
    muladd_1G.io.a := input_a
    muladd_1G.io.b := input_b
    muladd_1G.io.c := input_c
    muladd_1G.io.roundingMode := 0.U
    muladd_1G.io.detectTininess := 1.U

    // muladd_1G.io.expected.out   := 0.U 
    // muladd_1G.io.expected.exceptionFlags   := 0.U
    // io.expected.recOut   := muladd.io.expected.recOut 

    
    io.out := fNFromRecFN(8,24,muladd_1G.io.out)
    // io.actual.exceptionFlags := muladd.io.actual.exceptionFlags

    // io.check := muladd.io.check
    // io.pass := muladd.io.pass
}

class TesterSimple_1 (dut:MULAdd) extends PeekPokeTester(dut){
    poke(dut.io.a,1067450368.U)//1.25
    poke(dut.io.b,1067450368.U)//1.25
    poke(dut.io.c,1067030937.U)//1.2
    // poke(dut.io.detectTininess,0.U)
    // poke(dut.io.roundingMode,0.U)
    step(3)
    // println("Result is :" +peek(dut.io.actual.out).toString)
    }
object faddmul extends App {
  chisel3.iotesters.Driver.execute(args, () => new MULAdd())(c => new TesterSimple_1(c))
}
// object MUL extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new FMUL())
// }



// object MULAdd extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new MULAdd())
// }
