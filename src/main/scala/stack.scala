package hardfloat
import chisel3._
import chisel3.util.log2Ceil
import chisel3 . iotesters ._
import org.scalatest._
import chisel3.iotesters.PeekPokeTester
import chisel3.iotesters.Driver
 
class Stack(val depth: Int) extends Module {
    val io = IO(new Bundle {
    val push         = Input(Bool())
    val pop           = Input(Bool())
    // val en              = Input(Bool())
    val dataIn      = Input(SInt(32.W))
    // val hitT_t       = 
    val ray_id      = Input(UInt(32.W))
    val dataOut  = Output(SInt(32.W))
    // val sp              = Output(UInt(log2Ceil(depth+1).W))
    val empty     = Output(Bool())
    val hit_in      = Input(UInt(32.W))
    val hit_out   = Output(UInt(32.W))
    val ray_out  = Output(UInt(32.W))
    val enable    = Output(Bool())
    })
    
    val stack_mem = Mem(depth, SInt(32.W))
    val sp = RegInit(0.U(log2Ceil(depth+1).W))
    val out = RegInit(0.S(32.W))
    val hit_1= RegInit(0.U(32.W))
    val ray_1= RegInit(0.U(32.W))
    val enable = RegInit(0.U(1.W))
    // val empty   = RegInit(0.U(1.W))

//    stack_mem(0) := 987654321.S
 
    // when (io.en) {
    when(io.push && (sp < depth.asUInt)) {
        stack_mem(sp) := io.dataIn
        sp := sp + 1.U
        enable:= 0.U
        // empty  := 0.U 
    } .elsewhen(io.pop && (sp >=1.U)) {
        out := stack_mem(sp-1.U)
        sp := sp - 1.U
        hit_1 := io.hit_in
        ray_1 := io.ray_id
        enable := 1.U
    }.otherwise{
        hit_1  := 0.U
        ray_1 := 0.U
        enable:= 0.U
    }
    // when(sp === 0.U){
    //     io.empty := true.B
    // }.otherwise{
    //     io.empty := false.B    
    // }
    //  // }
    
    io.empty := (sp === 0.U)
    io.dataOut := out
    io.hit_out   := hit_1
    io.ray_out      := ray_1
    io.enable    := enable
}

// object Main extends App {
//   chisel3.Driver.execute(args, () => new Stack(8))
// }
   class Testerstack (dut: Stack) extends PeekPokeTester(dut){

    poke(dut.io.push,true.B)
    poke(dut.io.dataIn,1.S)
    // poke(dut.io.hit_in,10.U)
    // poke(dut.io.ray_id,10.S)
    // poke(dut.io.en,true.B)
    step(1)
    // poke(dut.io.push,true.B)
    // poke(dut.io.dataIn,2.S)
    // poke(dut.io.en,true.B)
    step(1)
    poke(dut.io.push,false.B)
   
    poke(dut.io.pop,true.B)
    poke(dut.io.hit_in,20.U)
    poke(dut.io.ray_id,20.U)
    // step(1)
    // poke(dut.io.pop,true.B)

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

object Stack extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new Stack(32))
}
