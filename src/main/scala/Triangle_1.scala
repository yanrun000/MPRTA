// package hardfloat
// import Chisel._
// import consts._
// // import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
 
// class Triangle extends Module{
//     val io = IO(new Bundle{
//         val To_IST0_enable = Input(Bool())
//         val nodeid_leaf         = Input(SInt(32.W))
//         val rayid_leaf             = Input(UInt(32.W))//光线的地址始终都是正数
//         val hiT_in                    = Input(Bits(32.W))
//         // val Oz_in                     = Input(Bits(32.W))
//         // val invDz_div             = Input(Bits(32.W))
//         val v00_in                   = new Float().asInput
//         val v11_in                   = new Float().asInput
//         val v22_in                   = new Float().asInput
//         val ray_o_in               = new ray().asInput
//         val ray_d_in               = new ray().asInput
//         // val Div_en                  = Input(Bool())
//         val pop_1                   = Output(Bool())
//         // val pop_en_1           = Output(Bool())
//         val pop_2                   = Output(Bool())
//         // val pop_en_2           = Output(Bool())
//         val pop_3                   = Output(Bool())
//         // val pop_en_3           = Output(Bool())
//         val hiT_out_1           = Output(Bits(32.W))
//         val hiT_out_2           = Output(Bits(32.W))
//         val hiT_out_3           = Output(Bits(32.W))
//         val hitT_en                = Output(Bool())         
//         val hitIndex               = Output(SInt(32.W))  
//         val hitIndex_en        = Output(Bool()) 

//         val node_id_out_1 = Output(SInt(32.W))
//         val node_id_out_2 = Output(SInt(32.W))
//         val node_id_out_3 = Output(SInt(32.W))

//         val ray_id_ist1          = Output(UInt(32.W))
//         val ray_id_ist2          = Output(UInt(32.W))
//         val ray_id_ist3          = Output(UInt(32.W))
//         val counter_fdiv      =  Output(UInt(64.W))
//     })
//     val IST0                            = Module(new IST0())
//     val SU                               = Module(new Schedule_unit())
//     // val FDIV                            = Module(new fpDiv(32))
//     val IST1                            = Module(new IST1())
//     val IST2                            = Module(new IST2())
//     val IST3                            = Module(new IST3())

//     // when(io.To_IST0_enable){
//         IST0.io.enable_IST0    := io.To_IST0_enable
//         IST0.io.nodeid_leaf     := io.nodeid_leaf
//         IST0.io.rayid_leaf         := io.rayid_leaf
//         IST0.io.hiT_in                 := io.hiT_in
//         IST0.io.v00                       := io.v00_in
//         IST0.io.v11_in                 := io.v11_in
//         IST0.io.v22_in                 := io.v22_in
//         IST0.io.ray_o_in            := io.ray_o_in
//         IST0.io.ray_d_in            := io.ray_d_in

//         SU.io.valid_in                := IST0.io.enable_SU_out
//         SU.io.Oz                           := IST0.io.Oz
//         SU.io.invDz_div            := IST0.io.invDz_div                  
//         SU.io.node_id_in        := IST0.io.nodeid_ist0_out
//         SU.io.ray_in                   := IST0.io.rayid_ist0_out
//         SU.io.hitT_in                 := IST0.io.hiT_out
//         SU.io.v11                         := IST0.io.v11_out
//         SU.io.v22                         := IST0.io.v22_out
//         SU.io.ray_o_in              := IST0.io.ray_o_out
//         SU.io.ray_d_in              := IST0.io.ray_d_out
//         io.counter_fdiv             := SU.io.counter_fdiv

//         IST1.io.enable_IST1    := SU.io.valid_out
//         IST1.io.nodeid_leaf_1:=SU.io.node_id_out
//         IST1.io.rayid_leaf_1    :=SU.io.ray_out
//         IST1.io.hiT_in                 :=SU.io.hitT_out
//         IST1.io.Oz                         :=SU.io.Oz_out
//         IST1.io.invDz                   :=SU.io.fdiv_out
//         IST1.io.v11_in                 :=SU.io.v11_out
//         IST1.io.v22_in                 :=SU.io.v22_out
//         IST1.io.ray_o_in            := SU.io.ray_o_out
//         IST1.io.ray_d_in            := SU.io.ray_d_out

//         IST2.io.ist2_continue    := IST1.io.ist2_continue
//         IST2.io.enable_IST2       := IST1.io.enable_IST2
//         IST2.io.nodeid_leaf_2   := IST1.io.nodeid_ist1_out
//         IST2.io.rayid_leaf_2       := IST1.io.rayid_ist1_out
//         IST2.io.hiT_in                   := IST1.io.hiT_out
//         IST2.io.v11_in                   := IST1.io.v11_out
//         IST2.io.v22_in                   := IST1.io.v22_out
//         IST2.io.ray_o_in               := IST1.io.ray_o_out
//         IST2.io.ray_d_in               := IST1.io.ray_d_out
//         IST2.io.t                               := IST1.io.t

//         IST3.io.enable_IST3          := IST2.io.enable_IST3
//         IST3.io.nodeid_leaf_3      := IST2.io.nodeid_ist2_out
//         IST3.io.rayid_leaf_3          := IST2.io.rayid_ist2_out
//         IST3.io.hiT_in                      := IST2.io.hiT_out
//         IST3.io.t_in                           := IST2.io.t_out
//         IST3.io.v22_in                      := IST2.io.v22_out
//         IST3.io.ray_o_in                := IST2.io.ray_o_out
//         IST3.io.ray_d_in                := IST2.io.ray_d_out
//         IST3.io.u_in                          := IST2.io.u


//         io.pop_1                        := IST1.io.pop_1
//         io.ray_id_ist1              := IST1.io.rayid_ist1_out
//         io.node_id_out_1     := IST1.io.nodeid_ist1_out
//         io.pop_2                        := IST2.io.pop_2
//         io.ray_id_ist2              := IST2.io.rayid_ist2_out
//         io.node_id_out_2     := IST2.io.nodeid_ist2_out
//         io.pop_3                        := IST3.io.pop_3
//         io.ray_id_ist3              := IST3.io.rayid_ist3_out
//         io.node_id_out_3     := IST3.io.nodeid_ist3_out
//         io.hiT_out_1                := IST1.io.hiT_out 
//         io.hiT_out_2                := IST2.io.hiT_out
//         io.hiT_out_3                := IST3.io.hiT_out
//         io.hitT_en                     := IST3.io.hitT_en
//         io.hitIndex_en            := IST3.io.hitIndex_en
//         io.hitIndex                    := IST3.io.hitIndex

//     // }.otherwise{
//     //     io.pop_1                     := false.B
//     //     io.pop_2                     := false.B
//     //     io.pop_3                     := false.B
//     //     io.hiT_out_1             := 0.U
//     //     io.hiT_out_2             := 0.U
//     //     io.hiT_out_3             := 0.U
//     //     io.hitT_en                  := false.B
//     //     io.hitIndex                 := 0.S
//     //     io.hitIndex_en         := false.B
//     //     io.ray_id_ist1           := 0.U
//     //     io.ray_id_ist2           := 0.U
//     //     io.ray_id_ist3           := 0.U
//     // }
// }
// object Triangle extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new Triangle())
// }
// class triangle_Simple (dut:Triangle) extends PeekPokeTester(dut){
//   step(1)

//   poke(dut.io.To_IST0_enable,1.U)
//   poke(dut.io.nodeid_leaf,53.U)
//   poke(dut.io.rayid_leaf,4.U)
//   poke(dut.io.hiT_in,4.U)
//   poke(dut.io.v00_in.x,510.U)
//   poke(dut.io.v00_in.y,511.U)
//   poke(dut.io.v00_in.z,512.U)
//   poke(dut.io.v00_in.w,513.U)
//   poke(dut.io.v11_in.x,510.U)
//   poke(dut.io.v11_in.y,511.U)
//   poke(dut.io.v11_in.z,512.U)
//   poke(dut.io.v11_in.w,513.U)
//   poke(dut.io.v22_in.x,520.U)
//   poke(dut.io.v22_in.y,521.U)
//   poke(dut.io.v22_in.z,522.U)
//   poke(dut.io.v22_in.w,523.U)
// //   poke(dut.io.Oz,12.U)
//   poke(dut.io.ray_o_in.x,530.U)
//   poke(dut.io.ray_o_in.y,531.U)
//   poke(dut.io.ray_o_in.z,532.U)
//   poke(dut.io.ray_d_in.x,540.U)
//   poke(dut.io.ray_d_in.y,541.U)
//   poke(dut.io.ray_d_in.z,542.U)
 

//   step(10)

//   }
// object triangle_test extends App {
//   chisel3.iotesters.Driver.execute(args, () => new Triangle())(c => new triangle_Simple(c))
// }