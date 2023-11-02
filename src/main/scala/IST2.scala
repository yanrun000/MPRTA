package hardfloat
import Chisel._
// import chisel3 ._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
//这个的使能逻辑由TOP来设计
class IST2  extends Module{
    val io = IO(new Bundle{
        val enable_IST2     = Input(Bool())
        val ist2_continue  = Input(Bool())
        val nodeid_leaf_2= Input(SInt(32.W))
        val rayid_leaf_2    = Input(Bits(32.W))
        val hiT_in                 = Input(Bits(32.W))
        val v11_in                 =  new Float().asInput
        val v22_in                 = new Float().asInput
        val ray_o_in            = new ray().asInput
        val ray_d_in            = new ray().asInput
        val t                            = Input(Bits(32.W))
        val break_in           = Input(Bool())
        val RAY_AABB_1       = Input(Bool())
        val RAY_AABB_2       = Input(Bool())

        val nodeid_ist2_out = Output(SInt(32.W))
        val rayid_ist2_out      = Output(Bits(32.W))
        val hiT_out                    = Output(Bits(32.W))
        val u                                 = Output(Bits(32.W))
        // val ist3_continue       = Output(Bool())
        val pop_2                       = Output(Bool())
        val t_out                         = Output(Bits(32.W))
        val v22_out                   = new Float().asOutput
        val ray_o_out              = new ray().asOutput
        val ray_d_out              = new ray().asOutput
        val enable_IST3         = Output(Bool())
        val break_ist2             = Output(Bool())
        val break_out              = Output(Bool())
        val RAY_AABB_1_out    = Output(Bool())
        val RAY_AABB_2_out    = Output(Bool())
    })

//----------------------------------------------------  
    val temp_0         = RegInit(0.U(32.W))//v11.w+origx*v11.x
    val temp_1         = RegInit(0.U(32.W))//origy*v11.y
    val temp_2         = RegInit(0.U(32.W))//origz*v11.z
    val temp_3         = RegInit(0.U(32.W))//dirx*v11.x
    val temp_4         = RegInit(0.U(32.W))//diry*v11.y
    val temp_5         = RegInit(0.U(32.W))//diry*v11.z

    val nodeid_ist2_temp_1_temp = RegInit(0. S(32.W))
    val rayid_ist2_temp_1_temp     =  RegInit(0.U(32.W))
    val t_temp_1_temp                        =  RegInit(0.U(32.W))
    val hitT_temp_1_temp                 =  RegInit(0.U(32.W))
    val v22_1_temp                               =  RegInit(0.U(128.W))
    val ray_o_in_1_temp                    =   RegInit(0.U(96.W))
    val ray_d_in_1_temp                    =   RegInit(0.U(96.W))
    val enable_1_temp                         =  RegInit(0.U(1.W))
    val break_1_temp                            = RegInit(0.U(1.W))
    val ray_aabb_1_temp                  = RegInit(0. U(1.W))
    val ray_aabb_2_temp                  = RegInit(0. U(1.W))

    nodeid_ist2_temp_1_temp      := io.nodeid_leaf_2
    rayid_ist2_temp_1_temp          := io.rayid_leaf_2
    t_temp_1_temp                             := io.t
    hitT_temp_1_temp                      := io.hiT_in
    v22_1_temp                                    :=  chisel3.util.Cat(io.v22_in.w,io.v22_in.z,io.v22_in.y,io.v22_in.x)
    ray_o_in_1_temp                         :=  chisel3.util.Cat(io.ray_o_in.z,io.ray_o_in.y,io.ray_o_in.x)
    ray_d_in_1_temp                         :=  chisel3.util.Cat(io.ray_d_in.z,io.ray_d_in.y,io.ray_d_in.x)
    enable_1_temp                              := io.enable_IST2
    break_1_temp                                 := io.break_in
    ray_aabb_1_temp                         := io.RAY_AABB_1
    ray_aabb_2_temp                         := io.RAY_AABB_2    
    val FADD_MUL_14     = Module(new MY_MULADD)
        FADD_MUL_14.io.a := io.ray_o_in.x
        FADD_MUL_14.io.b := io.v11_in.x
        FADD_MUL_14.io.c := io.v11_in.w
        // FADD_MUL_14.io.roundingMode := 0.U
        // FADD_MUL_14.io.detectTininess             := 1.U
        // FADD_MUL_14.io.expected.out                := 0.U
        // FADD_MUL_14.io.expected.exceptionFlags := 0.U 
        temp_0                           := FADD_MUL_14.io.out

    val FMUL_7 = Module(new MY_MUL)
        FMUL_7.io.a := io.ray_o_in.y
        FMUL_7.io.b := io.v11_in.y
        // FMUL_7.io.roundingMode := 0.U
        // FMUL_7.io.detectTininess := 1.U
        // FMUL_7.io.expected.out := 0.U
        // FMUL_7.io.expected.exceptionFlags := 0.U 
        temp_1                           := FMUL_7.io.out

    val FMUL_8 = Module(new MY_MUL)
        FMUL_8.io.a := io.ray_o_in.z
        FMUL_8.io.b := io.v11_in.z
        // FMUL_8.io.roundingMode := 0.U
        // FMUL_8.io.detectTininess := 1.U
        // FMUL_8.io.expected.out := 0.U
        // FMUL_8.io.expected.exceptionFlags := 0.U 
        temp_2                           := FMUL_8.io.out   

    val FMUL_9 = Module(new MY_MUL)
        FMUL_9.io.a := io.ray_d_in.x
        FMUL_9.io.b := io.v11_in.x
        // FMUL_9.io.roundingMode := 0.U
        // FMUL_9.io.detectTininess := 1.U
        // FMUL_9.io.expected.out := 0.U
        // FMUL_9.io.expected.exceptionFlags := 0.U 
        temp_3                           := FMUL_9.io.out
    
    val FMUL_10 = Module(new MY_MUL)
        FMUL_10.io.a := io.ray_d_in.y
        FMUL_10.io.b := io.v11_in.y
        // FMUL_10.io.roundingMode := 0.U
        // FMUL_10.io.detectTininess := 1.U
        // FMUL_10.io.expected.out := 0.U
        // FMUL_10.io.expected.exceptionFlags := 0.U 
        temp_4                           := FMUL_10.io.out
    
    val FMUL_11 = Module(new MY_MUL)
        FMUL_11.io.a := io.ray_d_in.z
        FMUL_11.io.b := io.v11_in.z
        // FMUL_11.io.roundingMode := 0.U
        // FMUL_11.io.detectTininess := 1.U
        // FMUL_11.io.expected.out := 0.U
        // FMUL_11.io.expected.exceptionFlags := 0.U 
        temp_5                           := FMUL_11.io.out

    val nodeid_ist2_temp_1 = RegInit(0. S(32.W))
    val rayid_ist2_temp_1     =  RegInit(0.U(32.W))
    val t_temp_1                        =  RegInit(0.U(32.W))
    val hitT_temp_1                 =  RegInit(0.U(32.W))
    val v22_1                               =  RegInit(0.U(128.W))
    val ray_o_in_1                    =   RegInit(0.U(96.W))
    val ray_d_in_1                    =   RegInit(0.U(96.W))
    val enable_1                         =  RegInit(0.U(1.W))
    val break_1                            = RegInit(0.U(1.W))
    val ray_aabb_1                  = RegInit(0. U(1.W))
    val ray_aabb_2                  = RegInit(0. U(1.W))

    nodeid_ist2_temp_1      := nodeid_ist2_temp_1_temp
    rayid_ist2_temp_1          := rayid_ist2_temp_1_temp
    t_temp_1                             := t_temp_1_temp
    hitT_temp_1                      := hitT_temp_1_temp
    v22_1                                    :=  v22_1_temp
    ray_o_in_1                         :=  ray_o_in_1_temp
    ray_d_in_1                         :=  ray_d_in_1_temp
    enable_1                              := enable_1_temp
    break_1                                 := break_1_temp
    ray_aabb_1                         := ray_aabb_1_temp
    ray_aabb_2                         := ray_aabb_2_temp
//----------------------------------------------------  

    //stage(2)
    val nodeid_ist2_temp_2_temp =  RegInit(0.S(32.W))
    val rayid_ist2_temp_2_temp     =  RegInit(0.U(32.W))
    val t_temp_2_temp                        =  RegInit(0.U(32.W))
    val hitT_temp_2_temp                 =  RegInit(0.U(32.W))
    val v22_2_temp                               =  RegInit(0.U(128.W))
    val ray_o_in_2_temp                    =   RegInit(0.U(96.W))
    val ray_d_in_2_temp                    =   RegInit(0.U(96.W))
    val enable_2_temp                        = RegInit(0.U(1.W))
    val break_2_temp                            = RegInit(0.U(1.W))
    val ray_aabb_1_2_temp             = RegInit(0. U(1.W))
    val ray_aabb_2_2_temp             = RegInit(0. U(1.W))

    nodeid_ist2_temp_2_temp      := nodeid_ist2_temp_1
    rayid_ist2_temp_2_temp          := rayid_ist2_temp_1
    t_temp_2_temp                             := t_temp_1
    hitT_temp_2_temp                      := hitT_temp_1 
    v22_2_temp                                    :=  v22_1
    ray_o_in_2_temp                         :=  ray_o_in_1
    ray_d_in_2_temp                         :=  ray_d_in_1
    enable_2_temp                             := enable_1
    break_2_temp                                 := break_1
    ray_aabb_1_2_temp                   := ray_aabb_1
    ray_aabb_2_2_temp                   := ray_aabb_2

    val temp_6                          = RegInit(0.U(32.W))//origy*v11.y+origz*v11.z
    val temp_7                          = RegInit(0.U(32.W))//dirx*v11.x+diry*v11.y
    val temp_0_2                     = RegInit(0.U(32.W))// v11.w+origx*v11.x暂存值
    val temp_0_3                     = RegInit(0.U(32.W))// v11.w+origx*v11.x暂存值

    val temp_5_2                    = RegInit(0.U(32.W))//dirz*v11.z暂存值
    val temp_5_3                    = RegInit(0.U(32.W))//dirz*v11.z暂存值
    temp_0_2                            := temp_0
    temp_0_3                            := temp_0_2
    temp_5_2                            := temp_5 
    temp_5_3                            := temp_5_2 


    val FADD_5 = Module(new MY_ADD)
        FADD_5.io.a := temp_1
        FADD_5.io.b := temp_2
        // FADD_5.io.roundingMode := 0.U
        // FADD_5.io.detectTininess := 1.U
        // FADD_5.io.expected.out := 0.U
        // FADD_5.io.expected.exceptionFlags := 0.U 
        temp_6           := FADD_5.io.out
    
    val FADD_6 = Module(new MY_ADD)
        FADD_6.io.a := temp_3
        FADD_6.io.b := temp_4
        // FADD_6.io.roundingMode := 0.U
        // FADD_6.io.detectTininess := 1.U
        // FADD_6.io.expected.out := 0.U
        // FADD_6.io.expected.exceptionFlags := 0.U 
        temp_7           := FADD_6.io.out

    val nodeid_ist2_temp_2 =  RegInit(0.S(32.W))
    val rayid_ist2_temp_2     =  RegInit(0.U(32.W))
    val t_temp_2                        =  RegInit(0.U(32.W))
    val hitT_temp_2                 =  RegInit(0.U(32.W))
    val v22_2                               =  RegInit(0.U(128.W))
    val ray_o_in_2                    =   RegInit(0.U(96.W))
    val ray_d_in_2                    =   RegInit(0.U(96.W))
    val enable_2                        = RegInit(0.U(1.W))
    val break_2                            = RegInit(0.U(1.W))
    val ray_aabb_1_2             = RegInit(0. U(1.W))
    val ray_aabb_2_2             = RegInit(0. U(1.W))

    nodeid_ist2_temp_2      := nodeid_ist2_temp_2_temp
    rayid_ist2_temp_2          := rayid_ist2_temp_2_temp
    t_temp_2                             := t_temp_2_temp
    hitT_temp_2                      := hitT_temp_2_temp 
    v22_2                                    :=  v22_2_temp
    ray_o_in_2                         :=  ray_o_in_2_temp
    ray_d_in_2                         :=  ray_d_in_2_temp
    enable_2                             := enable_2_temp
    break_2                                 := break_2_temp
    ray_aabb_1_2                    := ray_aabb_1_2_temp
    ray_aabb_2_2                    := ray_aabb_2_2_temp
//----------------------------------------------------  

    //stage(3)
    val nodeid_ist2_temp_3_temp =  RegInit(0.S(32.W))
    val rayid_ist2_temp_3_temp     =  RegInit(0.U(32.W))
    val t_temp_3_temp                        =  RegInit(0.U(32.W))
    val hitT_temp_3_temp                 =  RegInit(0.U(32.W))
    val v22_3_temp                               =  RegInit(0.U(128.W))
    val ray_o_in_3_temp                    =   RegInit(0.U(96.W))
    val ray_d_in_3_temp                    =   RegInit(0.U(96.W))
    val enable_3_temp                        = RegInit(0.U(1.W))
    val break_3_temp                            = RegInit(0.U(1.W))
    val ray_aabb_1_3_temp             = RegInit(0. U(1.W))
    val ray_aabb_2_3_temp             = RegInit(0. U(1.W))

    nodeid_ist2_temp_3_temp         := nodeid_ist2_temp_2
    rayid_ist2_temp_3_temp             := rayid_ist2_temp_2
    t_temp_3_temp                                := t_temp_2
    hitT_temp_3_temp                         := hitT_temp_2
    v22_3_temp                                       := v22_2
    ray_o_in_3_temp                         :=  ray_o_in_2
    ray_d_in_3_temp                         :=  ray_d_in_2
    enable_3_temp                             := enable_2
    break_3_temp                                := break_2
    ray_aabb_1_3_temp                   := ray_aabb_1_2
    ray_aabb_2_3_temp                   := ray_aabb_2_2

    // io.hiT_out                           := hitT_temp_2 
    val Ox                                     =  RegInit(0.U(32.W))
    val Dx                                     =  RegInit(0.U(32.W))

    val FADD_7 = Module(new MY_ADD)
        FADD_7.io.a := temp_0_3
        FADD_7.io.b := temp_6
        // FADD_7.io.roundingMode := 0.U
        // FADD_7.io.detectTininess := 1.U
        // FADD_7.io.expected.out := 0.U
        // FADD_7.io.expected.exceptionFlags := 0.U 
        Ox               := FADD_7.io.out

    val FADD_8 = Module(new MY_ADD)
        FADD_8.io.a := temp_5_3
        FADD_8.io.b := temp_7
        // FADD_8.io.roundingMode := 0.U
        // FADD_8.io.detectTininess := 1.U
        // FADD_8.io.expected.out := 0.U
        // FADD_8.io.expected.exceptionFlags := 0.U 
        Dx               := FADD_8.io.out
    
    val nodeid_ist2_temp_3 =  RegInit(0.S(32.W))
    val rayid_ist2_temp_3     =  RegInit(0.U(32.W))
    val t_temp_3                        =  RegInit(0.U(32.W))
    val hitT_temp_3                 =  RegInit(0.U(32.W))
    val v22_3                               =  RegInit(0.U(128.W))
    val ray_o_in_3                    =   RegInit(0.U(96.W))
    val ray_d_in_3                    =   RegInit(0.U(96.W))
    val enable_3                        = RegInit(0.U(1.W))
    val break_3                            = RegInit(0.U(1.W))
    val ray_aabb_1_3             = RegInit(0. U(1.W))
    val ray_aabb_2_3             = RegInit(0. U(1.W))

    nodeid_ist2_temp_3         := nodeid_ist2_temp_3_temp
    rayid_ist2_temp_3             := rayid_ist2_temp_3_temp
    t_temp_3                                := t_temp_3_temp
    hitT_temp_3                         := hitT_temp_3_temp
    v22_3                                       := v22_3_temp
    ray_o_in_3                         :=  ray_o_in_3_temp
    ray_d_in_3                         :=  ray_d_in_3_temp
    enable_3                             := enable_3_temp
    break_3                                := break_3_temp
    ray_aabb_1_3                    := ray_aabb_1_3_temp
    ray_aabb_2_3                    := ray_aabb_2_3_temp
//----------------------------------------------------  
    //stage(4)
    val nodeid_ist2_temp_4_temp =  RegInit(0.S(32.W))
    val rayid_ist2_temp_4_temp     =  RegInit(0.U(32.W))
    val temp_u                            =  RegInit(0.U(32.W))
    val temp_u_temp                            =  RegInit(0.U(32.W))
    val t_temp_4_temp                        =  RegInit(0.U(32.W))
    val hitT_temp_4_temp                 =  RegInit(0.U(32.W))
    val v22_4_temp                               =  RegInit(0.U(128.W))
    val ray_o_in_4_temp                    =   RegInit(0.U(96.W))
    val ray_d_in_4_temp                    =   RegInit(0.U(96.W))
    val enable_4_temp                        = RegInit(0.U(1.W))
    val break_4_temp                            = RegInit(0.U(1.W))
    val ray_aabb_1_4_temp             = RegInit(0. U(1.W))
    val ray_aabb_2_4_temp             = RegInit(0. U(1.W))
    
    nodeid_ist2_temp_4_temp         := nodeid_ist2_temp_3
    rayid_ist2_temp_4_temp             := rayid_ist2_temp_3
    t_temp_4_temp                                := t_temp_3
    hitT_temp_4_temp                         := hitT_temp_3
    v22_4_temp                                       := v22_3
    ray_d_in_4_temp                         :=  ray_d_in_3
    ray_o_in_4_temp                         :=  ray_o_in_3
    enable_4_temp                             := enable_3
    break_4_temp                               := break_3
    ray_aabb_1_4_temp                    := ray_aabb_1_3
    ray_aabb_2_4_temp                    := ray_aabb_2_3  
    val FADD_MUL_15     = Module(new MY_MULADD)
        FADD_MUL_15.io.a := t_temp_3
        FADD_MUL_15.io.b := Dx
        FADD_MUL_15.io.c := Ox
        // FADD_MUL_15.io.roundingMode := 0.U
        // FADD_MUL_15.io.detectTininess             := 1.U
        // FADD_MUL_15.io.expected.out                := 0.U
        // FADD_MUL_15.io.expected.exceptionFlags := 0.U 
        temp_u                           :=  FADD_MUL_15.io.out

    val nodeid_ist2_temp_4 =  RegInit(0.S(32.W))
    val rayid_ist2_temp_4     =  RegInit(0.U(32.W))
    
    val t_temp_4                        =  RegInit(0.U(32.W))
    val hitT_temp_4                 =  RegInit(0.U(32.W))
    val v22_4                               =  RegInit(0.U(128.W))
    val ray_o_in_4                    =   RegInit(0.U(96.W))
    val ray_d_in_4                    =   RegInit(0.U(96.W))
    val enable_4                        = RegInit(0.U(1.W))
    val break_4                            = RegInit(0.U(1.W))
    val ray_aabb_1_4             = RegInit(0. U(1.W))
    val ray_aabb_2_4             = RegInit(0. U(1.W))

    nodeid_ist2_temp_4         := nodeid_ist2_temp_4_temp
    rayid_ist2_temp_4             := rayid_ist2_temp_4_temp
    t_temp_4                                := t_temp_4_temp
    hitT_temp_4                         := hitT_temp_4_temp
    v22_4                                       := v22_4_temp
    ray_d_in_4                         :=  ray_d_in_4_temp
    ray_o_in_4                         :=  ray_o_in_4_temp
    enable_4                             := enable_4_temp
    break_4                               := break_4_temp
    ray_aabb_1_4                    := ray_aabb_1_4_temp
    ray_aabb_2_4                    := ray_aabb_2_4_temp     
    //stage(5)
    io.nodeid_ist2_out              := nodeid_ist2_temp_4
    io.rayid_ist2_out                  := rayid_ist2_temp_4
    io.t_out                                     := t_temp_4
    io.hiT_out                                := hitT_temp_4
    io.v22_out.x                            := v22_4(31,0)
    io.v22_out.y                            := v22_4(63,32)
    io.v22_out.z                            := v22_4(95,64)
    io.v22_out.w                           := v22_4(127,96)
    io.ray_o_out.x                      := ray_o_in_4(31,0)
    io.ray_o_out.y                      := ray_o_in_4(63,32)
    io.ray_o_out.z                      := ray_o_in_4(95,64)
    io.ray_d_out.x                      := ray_d_in_4(31,0)
    io.ray_d_out.y                      := ray_d_in_4(63,32)
    io.ray_d_out.z                      := ray_d_in_4(95,64)
    io.break_ist2                         := break_4
    io.RAY_AABB_1_out           := ray_aabb_1_4
    io.RAY_AABB_2_out           := ray_aabb_2_4 
    val FCMP_23 = Module(new ValExec_CompareRecF32_lt)
        FCMP_23.io.a := 0.U 
        FCMP_23.io.b := temp_u
        FCMP_23.io.expected.out := 0.U
        FCMP_23.io.expected.exceptionFlags := 0.U
        when(FCMP_23.io.actual.out > 0.U&&(enable_4===1.U)&&(break_4===0.U)){   
            io.u                            := temp_u
            // io.its3_continue  := true.B
            io.pop_2                 := false.B
            io.enable_IST3     := true.B
            io.break_out         := false.B
        }.elsewhen(FCMP_23.io.actual.out < 1.U&&(enable_4===1.U)&&(break_4===0.U)) {
            io.u                           := temp_u
            // io.its3_continue  := false.B
            io.pop_2                 := true.B
            io.enable_IST3     := false.B
            io.break_out         := false.B
        }.elsewhen(FCMP_23.io.actual.out > 0.U&&(enable_4===1.U)&&(break_4===1.U)){   
            io.u                            := temp_u
            // io.its3_continue  := true.B
            io.pop_2                 := false.B
            io.enable_IST3     := true.B
            io.break_out         := false.B
        }.elsewhen(FCMP_23.io.actual.out < 1.U&&(enable_4===1.U)&&(break_4===1.U)) {
            io.u                           := temp_u
            // io.its3_continue  := false.B
            io.pop_2                 := false.B
            io.enable_IST3     := false.B
            io.break_out         := true.B
        }.otherwise{
            io.u                           := temp_u
            // io.its3_continue  := false.B
            io.pop_2                 := false.B
            io.enable_IST3     := false.B
            io.break_out         := false.B
        }
    // }.otherwise{
    //     io.u                                           :=0.U
    //     // io.invDz_div                        :=0.U
    //     io.nodeid_ist2_out          :=0.S
    //     io.rayid_ist2_out              :=0.U
    //     io.hiT_out                            :=0.U
    //     // io.v11_out.x                        := 0.U
    //     // io.v11_out.y                        := 0.U
    //     // io.v11_out.z                        := 0.U
    //     // io.v11_out.w                      := 0.U
    //     io.v22_out.x                        := 0.U
    //     io.v22_out.y                        := 0.U
    //     io.v22_out.z                        := 0.U
    //     io.v22_out.w                      := 0.U
    //     // io.v22_out                
    //     io.ray_o_out.x                  := 0.U
    //     io.ray_o_out.y                  := 0.U
    //     io.ray_o_out.z                  := 0.U    
    //     io.ray_d_out.x                  := 0.U
    //     io.ray_d_out.y                  := 0.U
    //     io.ray_d_out.z                  := 0.U     
    //     // io.ray_d_out  
    //     // io.ist3_continue          := false.B
    //     io.pop_2                         := false.B
    //     io.t_out                           :=0.U
    //     io.enable_IST3            := false.B
    // }
}

object IST2 extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new IST2())
}


//0.82496428489685

//0.80354237556458