// //这个模块主要是用来和我们的设计连接的浮点出发
// package hardfloat
// import Chisel._
// import consts._
// // import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
 
// class SU extends Module{
//     val io = IO(new Bundle{
//         val invDz_div             = Input(Bits(32.W))
//         val wr_en                   = Input(Bits(1.W))
//         val invDz                      = Output(Bits(32.W))
//         val IST1_en                 = Output(Bool())
//         val Oz_out                   = Output(Bits(32.W))
//     })
//         val  FIFO_invDz_div   = Module(new FIFO_0(32,15))
//         val FDIV_1                      = Module(new FDIV())
//         // val FDIV_2                      = Module(new FDIV())
//         FIFO_invDz_div.io.wr           := (io.wr_en===1.U)
//         FIFO_invDz_div.io.datain   := io.invDz_div
//         val fdiv1_valid                             = RegInit(0.U(1.W))
//         val count                       = RegInit(0.U(10.W))
//         when(FDIV_1.io.valid){
//             FIFO_invDz_div.io.rd := 1.U
//             when(FIFO_invDz_div.io.enable){
//                 FIFO_invDz_div.io.rd  := 0.U
//                 FDIV_1.io.Div_en               := 1.U
//             }.otherwise{
//                 FIFO_invDz_div.io.rd  := 0.U
//                 FDIV_1.io.Div_en               := 0.U
//             }
//             FDIV_1.io.invDz_div       := FIFO_invDz_div.io.dataout
//             io.Oz_out                           := FDIV_1.io.Oz_out
//         }
// }
// // object SU extends App {
// //   (new chisel3.stage.ChiselStage).emitVerilog(new SU())
// // }

// // class SUTester (dut:SU) extends PeekPokeTester(dut){

// //         poke(dut.io.invDz_div,1073741824.U)

// //         poke(dut.io.wr_en,1.U)
// //         step(1)

// //         poke(dut.io.invDz_div,1082130432.U)

// //         poke(dut.io.wr_en,1.U)
// //         step(1)
// //         poke(dut.io.wr_en,0.U)
// //         step(70)
// // }
// // object SUtest extends App {
// //   chisel3.iotesters.Driver.execute(args, () => new SU())(c => new SUTester(c))
// // }
// // //         val nodeid_leaf         = Input(SInt(32.W))
// // //         val rayid_leaf             = Input(SInt(32.W))
// // //         val hiT_in                    = Input(Bits(32.W))
// // //         val Oz_in                     = Input(Bits(32.W))
// // //         val invDz_div             = Input(Bits(32.W))
// // //         val v11_in                   = Input(Bits(128.W))
// // //         val v22_in                   = Input(Bits(128.W))
// // //         // val Div_en                  = Input(Bool())
// // //         val wr_en                   = Input(Bool())
// // // 1082130432