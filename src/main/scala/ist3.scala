// package hardfloat
// import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// // import chisel3.iotesters.PeekPokeTester
// // import chisel3.iotesters.Driver
// //这个的使能逻辑由TOP来设计
// class IST3  extends Module{
//     val io = IO(new Bundle{
//         val enable_IST3    = Input(Bool())
//         val nodeid_leaf_3= Input(SInt(32.W))
//         val rayid_leaf_3    = Input(Bits(32.W))
//         val hiT_in                 = Input(Bits(32.W))
//         val t_in                      = Input(Bits(32.W))
//         val v22_in                 = new Float().asInput
//         // val input_ray          = Input(Bits(192.W))//这个光线是初始的光线
//         val ray_o_in            = new ray().asInput
//         val ray_d_in            = new ray().asInput
//         val u_in                     = Input(Bits(32.W))
//         // val Dx                              =  Output(Bits(32.W))
//         // val invDz_div          = Output(Bits(32.W))
//         // val nodeid_ist3_out = Output(SInt(32.W))
//         val rayid_ist3_out      = Output(Bits(32.W))
//         val hiT_out                    = Output(Bits(32.W))
//         val hitT_en                    = Output(Bool())
//         // val u                                 = Output(Bits(32.W))
//         // val its3_continue       = Output(Bool())
//         val pop_3                       = Output(Bool())
//         val hitIndex                   = Output(SInt(32.W))
//         val hitIndex_en           = Output(Bool())
//     })

//     //stage(1)
//     val temp_0         = RegInit(0.U(32.W))//v22.w+origx*v22.x
//     val temp_1         = RegInit(0.U(32.W))//origy*v22.y
//     val temp_2         = RegInit(0.U(32.W))//origz*v22.z
//     val temp_3         = RegInit(0.U(32.W))//dirx*v22.x
//     val temp_4         = RegInit(0.U(32.W))//diry*v22.y
//     val temp_5         = RegInit(0.U(32.W))//diry*v22.z
    
//     val nodeid_ist3_temp_1 = RegInit(0. S(32.W))
//     val rayid_ist3_temp_1     =  RegInit(0.U(32.W))
//     val t_temp_1                        =  RegInit(0.U(32.W))
//     val u_temp_1                       = RegInit(0.U(32.W))
//     val hitT_temp_1                 =  RegInit(0.U(32.W))
//     val enable_1                        = RegInit(0.U(1.W))

//     nodeid_ist3_temp_1      := io.nodeid_leaf_3
//     rayid_ist3_temp_1          := io.rayid_leaf_3
//     t_temp_1                             := io.t_in
//     u_temp_1                            := io.u_in
//     hitT_temp_1                      := io.hiT_in
//     enable_1                              := io.enable_IST3

//     val FADD_MUL_16     = Module(new ValExec_MulAddRecF32)
//         FADD_MUL_16.io.a := io.ray_o_in.x
//         FADD_MUL_16.io.b := io.v22_in.x
//         FADD_MUL_16.io.c := io.v22_in.w
//         FADD_MUL_16.io.roundingMode := 0.U
//         FADD_MUL_16.io.detectTininess             := 1.U
//         FADD_MUL_16.io.expected.out                := 0.U
//         FADD_MUL_16.io.expected.exceptionFlags := 0.U 
//         temp_0                           :=  fNFromRecFN(8,24,FADD_MUL_16.io.actual.out)

//     val FMUL_12 = Module(new ValExec_MulAddRecF32_mul)
//         FMUL_12.io.a := io.ray_o_in.y
//         FMUL_12.io.b := io.v22.y
//         FMUL_12.io.roundingMode := 0.U
//         FMUL_12.io.detectTininess := 1.U
//         FMUL_12.io.expected.out := 0.U
//         FMUL_12.io.expected.exceptionFlags := 0.U 
//         temp_1                           := fNFromRecFN(8,24,FMUL_12.io.actual.out)

//     val FMUL_13 = Module(new ValExec_MulAddRecF32_mul)
//         FMUL_13.io.a := io.ray_o_in.z
//         FMUL_13.io.b := io.v22.z
//         FMUL_13.io.roundingMode := 0.U
//         FMUL_13.io.detectTininess := 1.U
//         FMUL_13.io.expected.out := 0.U
//         FMUL_13.io.expected.exceptionFlags := 0.U 
//         temp_2                           := fNFromRecFN(8,24,FMUL_13.io.actual.out)   

//     val FMUL_14 = Module(new ValExec_MulAddRecF32_mul)
//         FMUL_14.io.a := io.ray_d_in.x
//         FMUL_14.io.b := io.v22.x
//         FMUL_14.io.roundingMode := 0.U
//         FMUL_14.io.detectTininess := 1.U
//         FMUL_14.io.expected.out := 0.U
//         FMUL_14.io.expected.exceptionFlags := 0.U 
//         temp_3                           := fNFromRecFN(8,24,FMUL_14.io.actual.out)
    
//     val FMUL_15 = Module(new ValExec_MulAddRecF32_mul)
//         FMUL_15.io.a := io.ray_d_in.y
//         FMUL_15.io.b := io.v22.y
//         FMUL_15.io.roundingMode := 0.U
//         FMUL_15.io.detectTininess := 1.U
//         FMUL_15.io.expected.out := 0.U
//         FMUL_15.io.expected.exceptionFlags := 0.U 
//         temp_4                           := fNFromRecFN(8,24,FMUL_15.io.actual.out)
    
//     val FMUL_16 = Module(new ValExec_MulAddRecF32_mul)
//         FMUL_16.io.a := io.ray_d_in.z
//         FMUL_16.io.b := io.v22.z
//         FMUL_16.io.roundingMode := 0.U
//         FMUL_16.io.detectTininess := 1.U
//         FMUL_16.io.expected.out := 0.U
//         FMUL_16.io.expected.exceptionFlags := 0.U 
//         temp_5                           := fNFromRecFN(8,24,FMUL_16.io.actual.out)


//     //stage(2)
//     val nodeid_ist3_temp_2 =  RegInit(0.S(32.W))
//     val rayid_ist3_temp_2     =  RegInit(0.U(32.W))
//     val t_temp_2                        =  RegInit(0.U(32.W))
//     val u_temp_2                       =  RegInit(0.U(32.W))
//     val hitT_temp_2                 =  RegInit(0.U(32.W))
//     val enable_2                        = RegInit(0.U(1.W))

//     nodeid_ist3_temp_2      := nodeid_ist3_temp_1
//     rayid_ist3_temp_2          := rayid_ist3_temp_1
//     t_temp_2                            := t_temp_1
//     u_temp_2                           := u_temp_1
//     hitT_temp_2                      := hitT_temp_1 
//     enable_2                             := enable_1        

//     val temp_6                          = RegInit(0.U(32.W))//origy*v22.y+origz*v22.z
//     val temp_7                          = RegInit(0.U(32.W))//dirx*v22.x+diry*v22.y
//     val temp_0_2                     = RegInit(0.U(32.W))// v22.w+origx*v22.x暂存值
//     val temp_5_2                    = RegInit(0.U(32.W))//dirz*v22.z暂存值

//     temp_0_2                            := temp_0
//     temp_5_2                            := temp_5 

//     val FADD_9 = Module(new ValExec_MulAddRecF32_add)
//         FADD_9.io.a := temp_1
//         FADD_9.io.b := temp_2
//         FADD_9.io.roundingMode := 0.U
//         FADD_9.io.detectTininess := 1.U
//         FADD_9.io.expected.out := 0.U
//         FADD_9.io.expected.exceptionFlags := 0.U 
//         temp_6           := fNFromRecFN(8,24,FADD_9.io.actual.out)
    
//     val FADD_10 = Module(new ValExec_MulAddRecF32_add)
//         FADD_10.io.a := temp_3
//         FADD_10.io.b := temp_4
//         FADD_10.io.roundingMode := 0.U
//         FADD_10.io.detectTininess := 1.U
//         FADD_10.io.expected.out := 0.U
//         FADD_10.io.expected.exceptionFlags := 0.U 
//         temp_7           := fNFromRecFN(8,24,FADD_10.io.actual.out)

//     //stage(3)
//     val nodeid_ist3_temp_3 =  RegInit(0.S(32.W))
//     val rayid_ist3_temp_3     =  RegInit(0.U(32.W))
//     val t_temp_3                        =  RegInit(0.U(32.W))
//     val u_temp_3                       =  RegInit(0.U(32.W))
//     val hitT_temp_3                 =  RegInit(0.U(32.W))
//     val enable_3                        = RegInit(0.U(1.W))

//     nodeid_ist3_temp_3         := nodeid_ist3_temp_2
//     rayid_ist3_temp_3             := rayid_ist3_temp_2
//     t_temp_3                                := t_temp_2
//     u_temp_3                               := u_temp_2
//     hitT_temp_3                         := hitT_temp_2
//     val Oy                                     =  RegInit(0.U(32.W))
//     val Dy                                     =  RegInit(0.U(32.W))
//     enable_3                              := enable_2

//     val FADD_11 = Module(new ValExec_MulAddRecF32_add)
//         FADD_11.io.a := temp_0_2
//         FADD_11.io.b := temp_6
//         FADD_11.io.roundingMode := 0.U
//         FADD_11.io.detectTininess := 1.U
//         FADD_11.io.expected.out := 0.U
//         FADD_11.io.expected.exceptionFlags := 0.U 
//         Oy               := fNFromRecFN(8,24,FADD_11.io.actual.out)

//     val FADD_12 = Module(new ValExec_MulAddRecF32_add)
//         FADD_12.io.a := temp_5_2
//         FADD_12.io.b := temp_7
//         FADD_12.io.roundingMode := 0.U
//         FADD_12.io.detectTininess := 1.U
//         FADD_12.io.expected.out := 0.U
//         FADD_12.io.expected.exceptionFlags := 0.U 
//         Dy               := fNFromRecFN(8,24,FADD_12.io.actual.out)

//         //stage(4)
//         val nodeid_ist3_temp_4 =  RegInit(0.S(32.W))
//         val rayid_ist3_temp_4     =  RegInit(0.U(32.W))
//         val t_temp_4                        =  RegInit(0.U(32.W))
//         val u_temp_4                       =  RegInit(0.U(32.W))
//         val temp_v                            =  RegInit(0.U(32.W))
//         val hitT_temp_4                 =  RegInit(0.U(32.W))
//         val enable_4                        = RegInit(0.U(1.W))
        

//         nodeid_ist3_temp_4         := nodeid_ist3_temp_3
//         rayid_ist3_temp_4             := rayid_ist3_temp_3
//         t_temp_4                                := t_temp_3
//         u_temp_4                               := u_temp_3
//         hitT_temp_4                         := hitT_temp_3
//         enable_4                                 := enable_3


//     val FADD_MUL_17     = Module(new ValExec_MulAddRecF32)
//         FADD_MUL_17.io.a := t_temp_4
//         FADD_MUL_17.io.b := Dy
//         FADD_MUL_17.io.c := Oy
//         FADD_MUL_17.io.roundingMode := 0.U
//         FADD_MUL_17.io.detectTininess             := 1.U
//         FADD_MUL_17.io.expected.out                := 0.U
//         FADD_MUL_17.io.expected.exceptionFlags := 0.U 
//         temp_v                           :=  fNFromRecFN(8,24,FADD_MUL_17.io.actual.out)
    
//     //stage(5)
//     val nodeid_ist3_temp_5 =  RegInit(0.S(32.W))
//     val rayid_ist3_temp_5     =  RegInit(0.U(32.W))
//     val t_temp_5                        =  RegInit(0.U(32.W))
//     val v_cmp_0                         =  RegInit(0.U(1.W))
//     val u_add_v                          =  RegInit(0.U(32.W))
//     val hitT_temp_5                 =  RegInit(0.U(32.W))
//     val enable_5                        = RegInit(0.U(1.W))
//     // val v_cmp_0_temp           = RegInit(0.U(1.W))

//     nodeid_ist3_temp_5         := nodeid_ist3_temp_4
//     rayid_ist3_temp_5             := rayid_ist3_temp_4
//     t_temp_5                                := t_temp_4
//     hitT_temp_5                         := hitT_temp_4
//     enable_5                                 := enable_4

//     val FCMP_24 = Module(new ValExec_CompareRecF32_lt)
//         FCMP_24.io.a := 0.U 
//         FCMP_24.io.b := temp_v
//         FCMP_24.io.expected.out := 0.U
//         FCMP_24.io.expected.exceptionFlags := 0.U
//         when(FCMP_24.io.actual.out > 0.U){   
//             v_cmp_0                 := 1.U
//         } .otherwise{
//             v_cmp_0                 := 0.U
//         }

//     val FADD_13 = Module(new ValExec_MulAddRecF32_add)
//         FADD_13.io.a := temp_v
//         FADD_13.io.b := u_temp_4
//         FADD_13.io.roundingMode := 0.U
//         FADD_13.io.detectTininess := 1.U
//         FADD_13.io.expected.out := 0.U
//         FADD_13.io.expected.exceptionFlags := 0.U 
//         u_add_v         := fNFromRecFN(8,24,FADD_13.io.actual.out)
    
//     //stage(6)
//     // val v_cmp_0_temp           = RegInit(0.U(1.W))

//     val FCMP_25 = Module(new ValExec_CompareRecF32_le)
//         FCMP_25.io.a := u_add_v 
//         FCMP_25.io.b := 1065353216.U
//         FCMP_25.io.expected.out := 0.U
//         FCMP_25.io.expected.exceptionFlags := 0.U
//         when(FCMP_25.io.actual.out > 0.U&&(enable_5===1.U)){   
//             when(v_cmp_0 === 1.U){
//                 io.hiT_out                  :=   t_temp_5  
//                 io.hitIndex                 :=   nodeid_ist3_temp_5
//                 io.pop_3                     :=  true.B
//                 io.rayid_ist3_out    :=  rayid_ist3_temp_5
//                 io.hitT_en                  := true.B
//                 io.hitIndex_en         := true.B 
//             }.otherwise{
//                 io.hiT_out                  := hitT_temp_5
//                 io.hitIndex                 := 3212836864L.S
//                 io.pop_3                     :=  true.B
//                 io.rayid_ist3_out    :=  rayid_ist3_temp_5   
//                 io.hitT_en                  := false.B
//                 io.hitIndex_en         := false.B 
//             }
//         } .otherwise{
//                 io.hiT_out                  := 0.U
//                 io.hitIndex                 := 0.S
//                 io.pop_3                     :=  false.B
//                 io.rayid_ist3_out    :=  0.U   
//                 io.hitT_en                  := false.B
//                 io.hitIndex_en         := false.B 
//         }
// }

// // object IST3 extends App {
// //   (new chisel3.stage.ChiselStage).emitVerilog(new IST3())
// // }
