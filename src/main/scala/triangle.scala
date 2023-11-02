// package hardfloat
// import Chisel._
// import consts._
// // import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
 
// class triangle extends Module{
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
//         val hiT_out                = Output(Bits(32.W))
//         val hitT_en                = Output(Bool())         
//         val hitIndex               = Output(SInt(32.W))  
//         val hitIndex_en        = Output(Bool()) 

//         val ray_id_ist1          = Output(SInt(32.W))
//         val ray_id_ist2          = Output(SInt(32.W))
//         val ray_id_ist3          = Output(SInt(32.W))
//     })
//     val IST0                            = Module(new IST0())
//     val SU                               = Module(new SU())
//     val IST1                            = Module(new IST1())
//     val IST2                            = Module(new IST2())
//     val IST3                            = Module(new IST2())
// when(io.To_IST0_enable){
//     IST0.io.nodeid_leaf     := io.nodeid_leaf
//     IST0.io.rayid_leaf         := io.rayid_leaf
//     IST0.io.hitT                      := io.hiT_in
//     IST0.io.v00                       := io.v00_in
//     IST0.io.v11_in                 := io.v11_in
//     IST0.io.v22_in                 := io.v22_in
//     IST0.io.ray_o_in            := io.ray_o_in
//     IST0.io.ray_d_in            := io.ray_d_in
    
//     // when(IST0.io.en){//使能是这样吗？
//     SU.io.valid_en               := IST0.io.enable_SU_out
//     SU.io.Oz                           := IST0.io.Oz
//     SU.io.invDz_div            := IST0.io.invDz_div                  
//     SU.io.node_id_in        := IST0.io.nodeid_ist0_out
//     SU.io.ray_in                   := IST0.io.rayid_ist0_out
//     SU.io.hiT_in                   := IST0.io.hiT_out
//     SU.io.v11                         := IST0.io.v11_out
//     SU.io.v22                         := IST0.io.v22_out
//     SU.io.ray_o_in              := IST0.io.ray_o_out
//     SU.io.ray_d_in              := IST0.io.ray_d_out
//     // SU.io.wr_en                   := 1.U//这个感觉不对

//     IST1.io.enable_IST1    := SU.io.valid_out
//     IST1.io.nodeid_leaf_1:=SU.io.node_id_out
//     IST1.io.rayid_leaf_1    :=SU.io.ray_out
//     IST1.io.hiT_in                 :=SU.io.hitT_out
//     IST1.io.Oz                         :=SU.io.Oz_out
//     IST1.io.invDz                   :=SU.io.fdiv_out
//     IST1.io.v11_in                 :=SU.io.v11_out
//     IST1.io.v22_in                 :=SU.io.v22_out
//     IST1.io.ray_o_in            := IST0.io.ray_o_out
//     IST1.io.ray_d_in            := IST0.io.ray_d_out
//     when(IST1.io.pop_1&&(!IST1.io.enable_IST2){
//         io.pop_1                        := true.B
//         io.ray_id_ist1              := IST1.io.rayid_ist1_out
//         io.hiT_out                    := IST1.io.hiT_out
//         io.pop_2                     := false.B
//         io.pop_3                     := false.B
//         io.hitT_en                  := false.B
//         io.hitIndex                 := 0.U
//         io.hitIndex_en         := false.B
//         io.ray_id_ist2           := 0.U
//         io.ray_id_ist3           := 0.U

//     }.elsewhen(IST1.io.ist1_continue){//
//         IST2.io.enable_IST2       := IST1.io.enable_IST2
//         IST2.io.nodeid_leaf_2   := IST1.io.nodeid_ist0_out
//         IST2.io.rayid_leaf_2       := IST1.io.rayid_ist1_out
//         IST2.io.hitT_in                  := IST1.io.hitT_out
//         IST2.io.v11_in                   := IST1.io.v11_out
//         IST2.io.v22_in                   := IST1.io.v22_out
//         IST2.io.ray_o_in               := IST1.io.ray_o_out
//         IST2.io.ray_d_in               := IST1.io.ray_d_out
//         IST2.io.t                               := IST1.io.t
//         when(IST2.io.enable_IST3 && its3_continue){
//             IST3.io.enable_IST3          := IST2.io.enable_IST3
//             IST3.io.nodeid_leaf_3      := IST2.io.nodeid_ist2_out
//             IST3.io.rayid_leaf_3          := IST2.io.rayid_ist2_out
//             IST3.io.hiT_in                      := IST2.io.hiT_out
//             IST3.io.t_in                           := IST2.io.t_out
//             IST3.io.v22_in                      := IST2.io.v22_out
//             IST3.io.ray_o_in                := IST2.io.ray_o_out
//             IST3.io.ray_d_in                := IST2.io.ray_d_out
//             IST3.io.u_in                          := IST2.io.u
//             // when(IST3.io.hitT_en){
//                 io.pop_3                                := IST3.io.pop_3
//                 io.hiT_out                             := IST3.io.hiT_out
//                 io.hitT_en                             := IST3.io.hitT_en 
//                 io.ray_id_ist3                      := IST3.io.rayid_ist3_out
//                 io.hitIndex_en                    := IST3.io.hitIndex_en
//                 io.hitIndex                            := IST3.io.hitIndex
            
//             // }elsewhen(IST3.io.pop_3&&!IST3.io.hitT_en)
//             //     io.pop_3                                := IST3.io.pop_3
//             //     io.hiT_out                             := IST3.io.hiT_out
//             //     io.hitT_en                             := IST 

//             }.elsewhen(IST2.pop_2){
//                  io.pop_1                        := false.B
//                 io.pop_2                        := true.B
//                 io.pop_3                        := false.B
//                 io.hiT_out                     := IST2.io.hiT_out
//                 io.hitT_en                     := false.B
//                 io.ray_id_ist2              := IST2.io.rayid_ist2_out
//                 io.ray_id_ist1              := 0.U
//                 io.ray_id_ist3              := 0.U
               
//         }.otherwise{
//                 io.pop_1                     := false.B
//                 io.pop_2                     := true.B
//                 io.pop_3                     := false.B
//                 io.hiT_out                  := 0.U
//                 io.hitT_en                  := false.B
//                 io.hitIndex                 := 0.U
//                 io.hitIndex_en         := false.B
//                 io.ray_id_ist1           := 0.U
//                 io.ray_id_ist2           := 0.U
//                 io.ray_id_ist3           := 0.U
//         }            
 
    
//     }.otherwise{
//         io.pop_1                     := false.B
//         io.pop_2                     := false.B
//         io.pop_3                     := false.B
//         io.hiT_out                  := 0.U
//         io.hitT_en                  := false.B
//         io.hitIndex                 := 0.U
//         io.hitIndex_en         := false.B
//         io.ray_id_ist1           := 0.U
//         io.ray_id_ist2           := 0.U
//         io.ray_id_ist3           := 0.U
//     }.otherwise{
//         io.pop_1                     := false.B
//         io.pop_2                     := false.B
//         io.pop_3                     := false.B
//         io.hiT_out                  := 0.U
//         io.hitT_en                  := false.B
//         io.hitIndex                 := 0.U
//         io.hitIndex_en         := false.B
//         io.ray_id_ist1           := 0.U
//         io.ray_id_ist2           := 0.U
//         io.ray_id_ist3           := 0.U
//     }
    
// }

// object triangle extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new triangle())
// }
