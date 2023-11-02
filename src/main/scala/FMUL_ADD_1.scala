package hardfloat

import chisel3 ._
import chisel3 . iotesters ._


class MULAdd_1 extends Module{
    val io = IO(new Bundle {
        val a = Input(Bits(32.W))
        val b = Input(Bits(32.W))
        val c = Input(Bits(32.W))
        val roundingMode   = Input(UInt(3.W))
        val detectTininess = Input(UInt(1.W))        
                  
        // val expected = new Bundle{ 
        //     val out = Input(Bits(32.W))
        //     val exceptionFlags = Input(Bits(5.W))
        //     val recOut =Output(Bits(33.W))
        // }

        // val actual = new Bundle {
            val out = Output(Bits(33.W))
        //     val exceptionFlags = Output(Bits(5.W))
        // }

        // val check = Output(Bool())
        // val pass = Output(Bool())
    })
val muladd = Module (new ValExec_MulAddRecF32)
    muladd.io.a := io.a
    muladd.io.b := io.b
    muladd.io.c := io.c
    muladd.io.roundingMode := 0.U
    muladd.io.detectTininess := 1.U

    muladd.io.expected.out   := 0.U 
    muladd.io.expected.exceptionFlags   := 0.U
    // io.expected.recOut   := muladd.io.expected.recOut 

    
    io.out := fNFromRecFN(8,24,muladd.io.actual.out)
    // io.actual.exceptionFlags := muladd.io.actual.exceptionFlags

    // io.check := muladd.io.check
    // io.pass := muladd.io.pass
}

class TesterSimple_2 (dut:MULAdd_1) extends PeekPokeTester(dut){
    poke(dut.io.a,1067450368.U)//1.25
    poke(dut.io.b,1067450368.U)//1.25
    poke(dut.io.c,1067030937.U)//1.2
    // poke(dut.io.detectTininess,0.U)
    // poke(dut.io.roundingMode,0.U)
    step(3)
    // println("Result is :" +peek(dut.io.actual.out).toString)
    }
object faddmul_1 extends App {
  chisel3.iotesters.Driver.execute(args, () => new MULAdd_1())(c => new TesterSimple_2(c))
}