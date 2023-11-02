//这个模块主要是用来和我们的设计连接的浮点出发
// package hardfloat
// import Chisel._
// import consts._
// // import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
package hardfloat
import Chisel._
// import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class FDIV_ds extends Module{
    val io = IO(new Bundle{
        val inValid         = Input(Bool()) //除法器的使能位
        val a                     = Input(Bits(32.W))
        val b                    = Input(Bits(32.W))
        val positive      = Input(Bool())
        val v11               = new Float().asInput
        val v22               =  new Float().asInput
        val ray_in          =  Input(UInt(32.W))
        val Oz                  = Input(UInt(32.W))
        val ray_o_in     = new ray().asInput
        val ray_d_in     = new ray().asInput
        val node_id_in= Input(SInt(32.W))
        val hitT_in         = Input(UInt(32.W))   

        val inReady             = Output(Bool())//表示在忙
        val outValid             = Output(Bool())//当前脉冲输出有效
        val out                       = Output(Bits(32.W))
        val v11_out              = new Float().asOutput
        val v22_out              = new Float().asOutput
        val ray_out              = Output(UInt(32.W))
        val Oz_out               = Output(UInt(32.W))
        val ray_o_out         = new ray().asOutput
        val ray_d_out         = new ray().asOutput
        // val Oz_out               = Output(UInt(32.W))
        val node_id_out   = Output(SInt(32.W))
        val hitT_out            = Output(UInt(32.W))
    })

      // val temp              = RegInit(0.U(1.W))
      // temp                    := io.inValid


      val v11_temp          = RegInit(0.U(128.W))
      val v22_temp          = RegInit(0.U(128.W))
      val ray_temp           = RegInit(0.U(32.W))
      val Oz_temp            = RegInit(0.U(32.W))
      val ray_o_temp      = RegInit(0.U(96.W))
      val ray_d_temp      = RegInit(0.U(96.W))
      val node_id_temp = RegInit(0.S(32.W))
      val hitT_temp          = RegInit(0.U(32.W))
      val positive               = RegInit(0.U(1.W))

val  fdiv_ds  = Module(new DivSqrtRecFN_small(8,24,0))

    fdiv_ds.io.inValid                := io.inValid

    when(fdiv_ds.io.inValid=== 1.U){
      v11_temp                             := chisel3.util.Cat(io.v11.w,io.v11.z,io.v11.y,io.v11.x)
      v22_temp                             := chisel3.util.Cat(io.v22.w,io.v22.z,io.v22.y,io.v22.x)
      ray_temp                              := io.ray_in
      Oz_temp                               := io.Oz
      ray_o_temp                         := chisel3.util.Cat(io.ray_o_in.z,io.ray_o_in.y,io.ray_o_in.x)
      ray_d_temp                         := chisel3.util.Cat(io.ray_d_in.z,io.ray_d_in.y,io.ray_d_in.x)
      node_id_temp                    := io.node_id_in
      hitT_temp                             := io.hitT_in
      positive                                  := io.positive
      }.otherwise{
      v11_temp                             := v11_temp
      v22_temp                             := v22_temp
      ray_temp                              := ray_temp
      Oz_temp                               := Oz_temp
      ray_o_temp                         := ray_o_temp
      ray_d_temp                         := ray_d_temp
      node_id_temp                    := node_id_temp
      hitT_temp                             := hitT_temp
      positive                                  := positive
      }
    
    fdiv_ds.io.sqrtOp                := Bool(false)
    fdiv_ds.io.a                            := io.a
    when(io.positive){
    fdiv_ds.io.b                            := io.b
    }.elsewhen(!io.positive){
    fdiv_ds.io.b                            := offsign(io.b)
    }
    fdiv_ds.io.roundingMode := 1.U 
    fdiv_ds.io.detectTininess := 1.U
    // io.out                                         := fNFromRecFN(8,24,fdiv_ds.io.out)
    io.inReady                               := fdiv_ds.io.inReady
    // when(fdiv_ds.io.outValid_div===1.U){
    //   io.out                                         := fNFromRecFN(8,24,fdiv_ds.io.out)
    // }.elsewhen{
      // io.out                                        := 0.S
    // }
    io.outValid                               := fdiv_ds.io.outValid_div

    when(fdiv_ds.io.outValid_div){
      io.v11_out.x                           := v11_temp(31,0)
      io.v11_out.y                           := v11_temp(63,32)
      io.v11_out.z                           := v11_temp(95,64)
      io.v11_out.w                         := v11_temp(127,96)
      io.v22_out.x                           := v22_temp(31,0)
      io.v22_out.y                           := v22_temp(63,32)
      io.v22_out.z                           := v22_temp(95,64)
      io.v22_out.w                         := v22_temp(127,96)
      // io.v22_out                              := v22_temp
      io.ray_out                               := ray_temp
      io.Oz_out                                := Oz_temp
      io.ray_o_out.x                      := ray_o_temp(31,0)
      io.ray_o_out.y                      := ray_o_temp(63,32)
      io.ray_o_out.z                      := ray_o_temp(95,64)
      io.ray_d_out.x                      := ray_d_temp(31,0)
      io.ray_d_out.y                      := ray_d_temp(63,32)
      io.ray_d_out.z                      := ray_d_temp(95,64)

      // io.ray_d_out                          := ray_d_temp
      io.node_id_out                     := node_id_temp
      io.hitT_out                              := hitT_temp
      when(positive===1.U){
         io.out                                         := fNFromRecFN(8,24,fdiv_ds.io.out)
      }.elsewhen(positive===0.U){
         io.out                                         := offsign(fNFromRecFN(8,24,fdiv_ds.io.out))
      }
      
    }.otherwise{
      io.v11_out.x                              := 0.U
      io.v11_out.y                              := 0.U
      io.v11_out.z                              := 0.U
      io.v11_out.w                             := 0.U
      io.v22_out.x                              := 0.U
      io.v22_out.y                              := 0.U
      io.v22_out.z                              := 0.U
      io.v22_out.w                             := 0.U
      io.ray_out                               := 0.U
      io.Oz_out                                := 0.U
      io.ray_o_out.x                       := 0.U
      io.ray_o_out.y                       := 0.U
      io.ray_o_out.z                       := 0.U
      // io.ray_o_out.w                       := 0.U
      io.ray_d_out.x                       := 0.U
      io.ray_d_out.y                       := 0.U
      io.ray_d_out.z                       := 0.U
      // io.ray_d_out.w                       := 0.U
      // io.ray_d_out                          := 0.U
      io.node_id_out                     := 0.S
      io.hitT_out                              := 0.U
    }
    // io.ready                                    := div.io.input.ready          
    // Div.io.roundingMode:= 1.U
    // Div.io.detectTininess:= 1.U    1065353216

//     nodeid_temp           := io.nodeid_leaf
//     rayid_temp               := io.rayid_leaf
//     hitT_temp                 := io.hiT_in
//     Oz_temp                    := io.Oz_in
//     v11_temp                  := io.v11_i n
//     v22_temp                  := io.v22_in
    
//     when(counter <= 24.U){
//         counter                 := counter + 1.U 
//         io.busy                  := true.B    
//         io.IST1_en           := false.B
//     }.otherwise{
//         counter                 := 0.U
//         io.busy                  := false.B
//         io.IST1_en           := true.B
//         io.invDz                := Div.io.actual.out
        
//         io.nodeid_out        := nodeid_temp
//         io.rayid_out            := rayid_temp
//         io.hitT_out              := hitT_temp
//         io.Oz_out                 := Oz_temp
//         io.v11_out               := v11_temp
//         io.v22_out               := v22_temp    
//     }
// }
}
// object FDIV extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new FDIV())
// }
class fdiv_Simple (dut:FDIV_ds) extends PeekPokeTester(dut){
  poke(dut.io.inValid,1.U)
  // poke(dut.io.a,1067450368.U)
  poke(dut.io.b,1073741824.U)
  step(1)
  poke(dut.io.inValid,0.U)
  // poke(dut.io.hiT_in,3.S)
  // poke(dut.io.Oz_in,4.U)
  // poke(dut.io.invDz_div,1056964608.U)
  // poke(dut.io.v11_in,5.U)
  // poke(dut.io.v22_in,6.U)
  // poke(dut.io.Div_en,1.U)
  step(24)
  poke(dut.io.inValid,1.U)

  // poke(dut.io.a,1067450322.U)
  poke(dut.io.b,1073741822.U)

  step(1)
  poke(dut.io.inValid,0.U)
  step(50)

  }
object FDIV_dstest extends App {
  chisel3.iotesters.Driver.execute(args, () => new FDIV_ds())(c => new fdiv_Simple(c))
}
