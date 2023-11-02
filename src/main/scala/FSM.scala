package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
class FSM_1 extends Module{
    val io = IO(new Bundle{
        val request_0       = Input(Bool())
        val request_1       = Input(Bool())
        val grant_0           = Output(Bool())
        val grant_1           = Output(Bool())
    })
//Four states 
// val IDLE :: Grant_0 :: Grant_1  :: Grant_2 :: Nil= Enum(4)
val IDLE         = RegInit(0.U(2.W))
val Grant_0  = RegInit(1.U(2.W))
val Grant_1  = RegInit(2.U(2.W))
val grant       = RegInit(0.U(2.W))
val stateReg = RegInit(0.U(2.W))

    switch(stateReg){
        is (0.U){
            when(io.request_0){
                stateReg  := Grant_0
                grant         := 1.U
            }.elsewhen((!io.request_0) && io.request_1){
                stateReg  := Grant_1
                grant         := 2.U
            }.elsewhen((!io.request_0) && (!io.request_1)){
                stateReg  := IDLE
                grant         := 0.U
            }.otherwise{
                stateReg  := IDLE
                grant         := 0.U
            }
        }
        is (1.U){
            when(io.request_1){
                stateReg  := Grant_1
                grant         := 2.U
            }.elsewhen((!io.request_1)&&io.request_0){
                stateReg  := Grant_0
                grant         := 1.U
            }.elsewhen((!io.request_1)&&(!io.request_0)){
                stateReg  := Grant_0
                grant         := 0.U
            }.otherwise{
                stateReg  := Grant_0
                grant         := 0.U
            }
        }
        is (2.U){
            when(io.request_0){
                stateReg  := Grant_0
                grant         := 1.U
            }.elsewhen((!io.request_0)&&io.request_1){
                stateReg  := Grant_1
                grant         := 2.U
            }.elsewhen((!io.request_1)&&(!io.request_0)){
                stateReg  := Grant_1
                grant         := 0.U
            }.otherwise{
                stateReg  := Grant_1
                grant         := 0.U
            }
        }
        
    }
        // switch(grant){
        // is (0.U) {
        //     io.grant_0    := false.B
        //     io.grant_1    := false.B
        //     io.grant_2    := false.B
        // }
        // is (1.U) {
        //     io.grant_0    := true.B
        //     io.grant_1    := false.B
        //     io.grant_2    := false.B
        // }
        // is (2.U) {
        //     io.grant_0    := false.B
        //     io.grant_1    := true.B
        //     io.grant_2    := false.B
        // }
        // is (3.U) {
        //     io.grant_0    := false.B
        //     io.grant_1    := false.B
        //     io.grant_2    := true.B
        // }
        io.grant_0          := grant === 1.U
        io.grant_1          := grant === 2.U
        // io.grant_2          := grant === 3.U
        }

// object FSM_1 extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new FSM_1())
// }
//  class FSM_test0(dut:FSM_1) extends PeekPokeTester(dut){
//         poke(dut.io.request_0,true.B)
//         poke(dut.io.request_1,true.B)
//         poke(dut.io.request_2,true.B)
//         step(1)
//         poke(dut.io.request_0,false.B)
//         poke(dut.io.request_1,true.B)
//         poke(dut.io.request_2,true.B)
//         step(1)
//         poke(dut.io.request_0,true.B)
//         poke(dut.io.request_1,true.B)
//         poke(dut.io.request_2,true.B)
//         step(1)
//         poke(dut.io.request_0,true.B)
//         poke(dut.io.request_1,true.B)
//         poke(dut.io.request_2,true.B)
//         step(1)
//         poke(dut.io.request_0,false.B)
//         poke(dut.io.request_1,false.B)
//         poke(dut.io.request_2,true.B)
//         step(1)
//         poke(dut.io.request_0,true.B)
//         poke(dut.io.request_1,false.B)
//         poke(dut.io.request_2,false.B)
//         step(3)

// }
// object FSM_test extends App {
//   chisel3.iotesters.Driver.execute(args, () => new FSM_1())(c => new FSM_test0(c))
// }