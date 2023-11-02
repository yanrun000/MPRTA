// import hardfloat._
// import chisel3 ._
// import chisel3 . iotesters ._


// class REC_32TO_UI32 extends Module{
//     val io = IO(new Bundle {
//         val in = Input(Bits(32.W))
//         val roundingMode = Input(UInt(3.W))
//         val expected = new Bundle{ 
//             val out = Input(UInt(32.W))
//             val exceptionFlags = Input(UInt(5.W))
//         }

//         val actual = new Bundle {
//             val out = Output(Bits(32.W))
//             val exceptionFlags = Output(UInt(5.W))
//         }

//         val check = Output(Bool())
//         val pass = Output(Bool())
//     })
// val rec_32to_ui32 = Module (new ValExec_RecF32ToUI32)
//     rec_32to_ui32.io.in := io.in
//     rec_32to_ui32.io.roundingMode := io.roundingMode
//     rec_32to_ui32.io.expected.out := io.expected.out
//     rec_32to_ui32.io.expected.exceptionFlags := io.expected.exceptionFlags
//     io.actual.out := rec_32to_ui32.io.actual.out
//     io.actual.exceptionFlags := rec_32to_ui32.io.actual.exceptionFlags
//     io.check := rec_32to_ui32.io.check
//     io.pass := rec_32to_ui32.io.pass
// }

// class TesterSimple (dut:REC_32TO_UI32) extends PeekPokeTester(dut){
// poke(dut.io.in,905969664.U)
// // poke(dut.io.b,1.U)
// poke(dut.io.roundingMode,0.U)
// // poke(dut.io.expected.out,2135739596.U)
// step(10)
// println("Result is :" +peek(dut.io.actual.out).toString)
// println("exceptionFlags is :" +peek(dut.io.actual.exceptionFlags).toString)
// }
// object TesterSimple extends App{
//     chisel3. iotesters.Driver(()=> new REC_32TO_UI32()){ c =>
//         new TesterSimple(c)
//     }
// }


// // object add extends App {
// //   (new chisel3.stage.ChiselStage).emitVerilog(new add())
// // }
