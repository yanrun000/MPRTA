// package hardfloat
// import Chisel._
// import consts._
// // import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver

//  class schedule_unit extends Module{
//      val io = IO (new Bundle{
//          val invDz_div          = Input(Bits(32.W))//(dirx*v00.x + diry*v00.y + dirz*v00.z)
//          val valid_in              = Input(Bool())
//          val fdiv_out             = Output(Bits(32.W))
//          val valid_out           = Output(Bool())
//      }) 
    
//     val FIFO_su                  = Module(new FIFO_0(32,10))
//     val FDIV_0                     = Module(new FDIV_ds())
//     val FDIV_1                     = Module(new FDIV_ds())
//     val FDIV_2                     = Module(new FDIV_ds())

//     FIFO_su.io.wr                := io.valid_in
//     FIFO_su.io.datain             := io.invDz_div

//     val fdiv_0_en               = RegInit(0.U(1.W))
//     val fdiv_1_en               = RegInit(0.U(1.W))
//     val fdiv_2_en               = RegInit(0.U(1.W))
//     // val fdiv_0_inready      = FDIV_0.io.

//     when(FDIV_0.io.inReady===1.U&&FIFO_su.io.empty===0.U){
//         FIFO_su.io.rd             := true.B
//         // fdiv_0_en                    := 1.U
//         // FDIV_0.io.b                 := FIFO_su.io.dataout
//         // FDIV_0.io.inValid     := true.B
//         // FDIV_1.io.b                 := 0.U
//         // FDIV_1.io.inValid     := false.B
//         // FDIV_2.io.b                 := 0.U
//         // FDIV_2.io.inValid     := false.B
//     }.elsewhen(FDIV_0.io.inReady===0.U&&FDIV_1.io.inReady===1.U&&FIFO_su.io.empty===0.U){
//         FIFO_su.io.rd             := true.B
//         // fdiv_0_en                    := 1.U
//         // FDIV_1.io.b                 := FIFO_su.io.dataout
//         // FDIV_1.io.inValid     := true.B
//         // FDIV_0.io.b                 := 0.U
//         // FDIV_0.io.inValid     := false.B
//         // FDIV_2.io.b                 := 0.U
//         // FDIV_2.io.inValid     := false.B
//     }.elsewhen(FDIV_0.io.inReady===0.U&&FDIV_1.io.inReady===0.U&&FDIV_2.io.inReady===1.U&&FIFO_su.io.empty===0.U){
//         FIFO_su.io.rd             := true.B
//         // FDIV_2.io.b                 := FIFO_su.io.dataout
//         // FDIV_2.io.inValid     := true.B
//         // FDIV_0.io.b                 := 0.U
//         // FDIV_0.io.inValid     := false.B
//         // FDIV_2.io.b                 := 0.U
//         // FDIV_2.io.inValid     := false.B
//     }.otherwise{
//         FIFO_su.io.rd             := false.B
//         // FDIV_0.io.b                 := 0.U
//         // FDIV_0.io.inValid     := false.B
//         // FDIV_1.io.b                 := 0.U
//         // FDIV_1.io.inValid     := false.B
//         // FDIV_2.io.b                 := 0.U
//         // FDIV_2.io.inValid     := false.B
//     }

//     val fdiv_0_invalid      = RegInit(0.U(1.W))
//     val fdiv_1_invalid      = RegInit(0.U(1.W))
//     val fdiv_2_invalid      = RegInit(0.U(1.W))
//     fdiv_0_invalid             :=  FDIV_0.io.inValid
//     fdiv_1_invalid             :=  FDIV_1.io.inValid
//     fdiv_2_invalid             :=  FDIV_2.io.inValid

//     when(fdiv_0_invalid===1.U){
//         FDIV_0.io.b                 := FIFO_su.io.dataout
//         FDIV_1.io.b                 := 0.U
//         FDIV_2.io.b                 := 0.U
//     }.elsewhen(fdiv_1_invalid===1.U){
//         FDIV_0.io.b                 := 0.U
//         FDIV_1.io.b                 := FIFO_su.io.dataout
//         FDIV_2.io.b                 := 0.U
//     }.elsewhen(fdiv_2_invalid===1.U){
//         FDIV_0.io.b                 := 0.U
//         FDIV_1.io.b                 := 0.U
//         FDIV_2.io.b                 := FIFO_su.io.dataout
//     }.otherwise{
//         FDIV_0.io.b                 := 0.U
//         FDIV_1.io.b                 := 0.U
//         FDIV_2.io.b                 := 0.U
//     }


//     val fdiv_out_temp       = RegInit(0.U(32.W))
//     val valid_out_temp     = RegInit(0.U(1.W))
//     when(FDIV_0.io.outValid){
//         fdiv_out_temp          := FDIV_0.io.out
//         valid_out_temp        := 1.U 
//     }.elsewhen(FDIV_1.io.outValid){
//         fdiv_out_temp          := FDIV_1.io.out
//         valid_out_temp        := 1.U 
//     }.elsewhen(FDIV_2.io.outValid){
//         fdiv_out_temp          := FDIV_2.io.out
//         valid_out_temp        := 1.U 
//     }.otherwise{
//         fdiv_out_temp          := 0.U
//         valid_out_temp        := 0.U
//     }

//     io.fdiv_out                       := fdiv_out_temp
//     io.valid_out                     := valid_out_temp
//  }

// // object SU extends App {
// //   (new chisel3.stage.ChiselStage).emitVerilog(new schedule_unit())
// // }
// class su_Simple (dut:schedule_unit) extends PeekPokeTester(dut){
//   poke(dut.io.invDz_div,1073741824.U)
//   poke(dut.io.valid_in,1.U)
//   step(1)
//   poke(dut.io.invDz_div,1084227584.U)
//   poke(dut.io.valid_in,1.U)
//   step(1)
//   poke(dut.io.invDz_div,1092616192.U)
//   poke(dut.io.valid_in,1.U)
//   step(1)
//   poke(dut.io.valid_in,0.U)

//   // poke(dut.io.a,1067450322.U)
// //   poke(dut.io.b,1073741822.U)

// //   step(1)
// //   poke(dut.io.inValid,0.U)
//   step(50)

//   }
// object su_test extends App {
//   chisel3.iotesters.Driver.execute(args, () => new schedule_unit())(c => new su_Simple(c))
// }
