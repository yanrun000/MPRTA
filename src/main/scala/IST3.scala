package hardfloat
import Chisel._
// import chisel3 ._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
//这个的使能逻辑由TOP来设计
class IST3  extends Module{
    val io = IO(new Bundle{
        val enable_IST3    = Input(Bool())
        val nodeid_leaf_3= Input(SInt(32.W))
        val rayid_leaf_3    = Input(Bits(32.W))
        val hiT_in                 = Input(Bits(32.W))
        val t_in                      = Input(Bits(32.W))
        val v22_in                 = new Float().asInput
        // val input_ray          = Input(Bits(192.W))//这个光线是初始的光线
        val ray_o_in            = new ray().asInput
        val ray_d_in            = new ray().asInput
        val u_in                     = Input(Bits(32.W))
        val break_in            = Input(Bool())
        val RAY_AABB_1       = Input(Bool())
        val RAY_AABB_2       = Input(Bool())
        // val Dx                              =  Output(Bits(32.W))
        // val invDz_div          = Output(Bits(32.W))
        val nodeid_ist3_out = Output(SInt(32.W))
        val rayid_ist3_out      = Output(Bits(32.W))
        val hiT_out                    = Output(Bits(32.W))
        val hitT_en                    = Output(Bool())
        // val u                                 = Output(Bits(32.W))
        // val its3_continue       = Output(Bool())
        val pop_3                       = Output(Bool())
        val hitIndex                   = Output(SInt(32.W))
        val hitIndex_en           = Output(Bool())
        val break_out               = Output(Bool())
        val RAY_AABB_1_out    = Output(Bool())
        val RAY_AABB_2_out    = Output(Bool())
    })

//----------------------------------------------------
    //stage(1)
    // when(io.enable_IST3){
    val temp_0         = RegInit(0.U(32.W))//v22.w+origx*v22.x
    val temp_1         = RegInit(0.U(32.W))//origy*v22.y
    val temp_2         = RegInit(0.U(32.W))//origz*v22.z
    val temp_3         = RegInit(0.U(32.W))//dirx*v22.x
    val temp_4         = RegInit(0.U(32.W))//diry*v22.y
    val temp_5         = RegInit(0.U(32.W))//diry*v22.z
    
    val nodeid_ist3_temp_1_temp = RegInit(0. S(32.W))
    val rayid_ist3_temp_1_temp     =  RegInit(0.U(32.W))
    val t_temp_1_temp                        =  RegInit(0.U(32.W))
    val u_temp_1_temp                       = RegInit(0.U(32.W))
    val hitT_temp_1_temp                 =  RegInit(0.U(32.W))
    val enable_1_temp                        = RegInit(0.U(1.W))
    val break_1_temp                           =RegInit(0.U(1.W))
    val ray_aabb_1_temp                  = RegInit(0. U(1.W))
    val ray_aabb_2_temp                  = RegInit(0. U(1.W))   
    nodeid_ist3_temp_1_temp      := io.nodeid_leaf_3
    rayid_ist3_temp_1_temp          := io.rayid_leaf_3
    t_temp_1_temp                             := io.t_in
    u_temp_1_temp                            := io.u_in
    hitT_temp_1_temp                      := io.hiT_in
    enable_1_temp                              := io.enable_IST3
    break_1_temp                                 := io.break_in
    ray_aabb_1_temp                         := io.RAY_AABB_1
    ray_aabb_2_temp                         := io.RAY_AABB_2

    val FADD_MUL_16     = Module(new MY_MULADD)
        FADD_MUL_16.io.a := io.ray_o_in.x
        FADD_MUL_16.io.b := io.v22_in.x
        FADD_MUL_16.io.c := io.v22_in.w
        // FADD_MUL_16.io.roundingMode := 0.U
        // FADD_MUL_16.io.detectTininess             := 1.U
        // FADD_MUL_16.io.expected.out                := 0.U
        // FADD_MUL_16.io.expected.exceptionFlags := 0.U 
        temp_0                           :=  FADD_MUL_16.io.out

    val FMUL_12 = Module(new MY_MUL)
        FMUL_12.io.a := io.ray_o_in.y
        FMUL_12.io.b := io.v22_in.y
        // FMUL_12.io.roundingMode := 0.U
        // FMUL_12.io.detectTininess := 1.U
        // FMUL_12.io.expected.out := 0.U
        // FMUL_12.io.expected.exceptionFlags := 0.U 
        temp_1                           := FMUL_12.io.out

    val FMUL_13 = Module(new MY_MUL)
        FMUL_13.io.a := io.ray_o_in.z
        FMUL_13.io.b := io.v22_in.z
        // FMUL_13.io.roundingMode := 0.U
        // FMUL_13.io.detectTininess := 1.U
        // FMUL_13.io.expected.out := 0.U
        // FMUL_13.io.expected.exceptionFlags := 0.U 
        temp_2                           := FMUL_13.io.out

    val FMUL_14 = Module(new MY_MUL)
        FMUL_14.io.a := io.ray_d_in.x
        FMUL_14.io.b := io.v22_in.x
        // FMUL_14.io.roundingMode := 0.U
        // FMUL_14.io.detectTininess := 1.U
        // FMUL_14.io.expected.out := 0.U
        // FMUL_14.io.expected.exceptionFlags := 0.U 
        temp_3                           := FMUL_14.io.out
    
    val FMUL_15 = Module(new MY_MUL)
        FMUL_15.io.a := io.ray_d_in.y
        FMUL_15.io.b := io.v22_in.y
        // FMUL_15.io.roundingMode := 0.U
        // FMUL_15.io.detectTininess := 1.U
        // FMUL_15.io.expected.out := 0.U
        // FMUL_15.io.expected.exceptionFlags := 0.U 
        temp_4                           :=FMUL_15.io.out
    
    val FMUL_16 = Module(new MY_MUL)
        FMUL_16.io.a := io.ray_d_in.z
        FMUL_16.io.b := io.v22_in.z
        // FMUL_16.io.roundingMode := 0.U
        // FMUL_16.io.detectTininess := 1.U
        // FMUL_16.io.expected.out := 0.U
        // FMUL_16.io.expected.exceptionFlags := 0.U 
        temp_5                           := FMUL_16.io.out
//----------------------------------------------------

    val nodeid_ist3_temp_1 = RegInit(0. S(32.W))
    val rayid_ist3_temp_1     =  RegInit(0.U(32.W))
    val t_temp_1                        =  RegInit(0.U(32.W))
    val u_temp_1                       = RegInit(0.U(32.W))
    val hitT_temp_1                 =  RegInit(0.U(32.W))
    val enable_1                        = RegInit(0.U(1.W))
    val break_1                           =RegInit(0.U(1.W))
    val ray_aabb_1                  = RegInit(0. U(1.W))
    val ray_aabb_2                  = RegInit(0. U(1.W))
    nodeid_ist3_temp_1      := nodeid_ist3_temp_1_temp
    rayid_ist3_temp_1          := rayid_ist3_temp_1_temp
    t_temp_1                             := t_temp_1_temp
    u_temp_1                            := u_temp_1_temp
    hitT_temp_1                      := hitT_temp_1_temp
    enable_1                              := enable_1_temp
    break_1                                 := break_1_temp
    ray_aabb_1                         := ray_aabb_1_temp
    ray_aabb_2                         := ray_aabb_2_temp
//----------------------------------------------------
    val nodeid_ist3_temp_2_temp =  RegInit(0.S(32.W))
    val rayid_ist3_temp_2_temp     =  RegInit(0.U(32.W))
    val t_temp_2_temp                        =  RegInit(0.U(32.W))
    val u_temp_2_temp                       =  RegInit(0.U(32.W))
    val hitT_temp_2_temp                 =  RegInit(0.U(32.W))
    val enable_2_temp                        = RegInit(0.U(1.W))
    val break_2_temp                           =RegInit(0.U(1.W))
    val ray_aabb_1_2_temp             = RegInit(0. U(1.W))
    val ray_aabb_2_2_temp             = RegInit(0. U(1.W))
    nodeid_ist3_temp_2_temp      := nodeid_ist3_temp_1
    rayid_ist3_temp_2_temp          := rayid_ist3_temp_1
    t_temp_2_temp                            := t_temp_1
    u_temp_2_temp                           := u_temp_1
    hitT_temp_2_temp                      := hitT_temp_1 
    enable_2_temp                             := enable_1        
    break_2_temp                               := break_1
    ray_aabb_1_2_temp                  := ray_aabb_1
    ray_aabb_2_2_temp                  := ray_aabb_2

    val temp_6                          = RegInit(0.U(32.W))//origy*v22.y+origz*v22.z
    val temp_7                          = RegInit(0.U(32.W))//dirx*v22.x+diry*v22.y
    val temp_0_2                     = RegInit(0.U(32.W))// v22.w+origx*v22.x暂存值
    val temp_0_3                     = RegInit(0.U(32.W))// v22.w+origx*v22.x暂存值
    val temp_5_2                    = RegInit(0.U(32.W))//dirz*v22.z暂存值
    val temp_5_3                    = RegInit(0.U(32.W))//dirz*v22.z暂存值

    temp_0_2                            := temp_0
    temp_0_3                            := temp_0_2
    temp_5_2                            := temp_5 
    temp_5_3                            := temp_5_2 
    temp_0_2                            := temp_0
    temp_5_2                            := temp_5 

    val FADD_9 = Module(new MY_ADD)
        FADD_9.io.a := temp_1
        FADD_9.io.b := temp_2
        // FADD_9.io.roundingMode := 0.U
        // FADD_9.io.detectTininess := 1.U
        // FADD_9.io.expected.out := 0.U
        // FADD_9.io.expected.exceptionFlags := 0.U 
        temp_6           := FADD_9.io.out
    
    val FADD_10 = Module(new MY_ADD)
        FADD_10.io.a := temp_3
        FADD_10.io.b := temp_4
        // FADD_10.io.roundingMode := 0.U
        // FADD_10.io.detectTininess := 1.U
        // FADD_10.io.expected.out := 0.U
        // FADD_10.io.expected.exceptionFlags := 0.U 
        temp_7           := FADD_10.io.out

    val nodeid_ist3_temp_2 =  RegInit(0.S(32.W))
    val rayid_ist3_temp_2     =  RegInit(0.U(32.W))
    val t_temp_2                        =  RegInit(0.U(32.W))
    val u_temp_2                       =  RegInit(0.U(32.W))
    val hitT_temp_2                 =  RegInit(0.U(32.W))
    val enable_2                        = RegInit(0.U(1.W))
    val break_2                           =RegInit(0.U(1.W))
    val ray_aabb_1_2              = RegInit(0.U(1.W))
    val ray_aabb_2_2              =RegInit(0.U(1.W))
    nodeid_ist3_temp_2      := nodeid_ist3_temp_2_temp
    rayid_ist3_temp_2          := rayid_ist3_temp_2_temp
    t_temp_2                            := t_temp_2_temp
    u_temp_2                           := u_temp_2_temp
    hitT_temp_2                      := hitT_temp_2_temp 
    enable_2                             := enable_2_temp        
    break_2                               := break_2_temp
    ray_aabb_1_2                    := ray_aabb_1_2_temp
    ray_aabb_2_2                    := ray_aabb_2_2_temp
//----------------------------------------------------

    //stage(3)
    val nodeid_ist3_temp_3_temp =  RegInit(0.S(32.W))
    val rayid_ist3_temp_3_temp     =  RegInit(0.U(32.W))
    val t_temp_3_temp                        =  RegInit(0.U(32.W))
    val u_temp_3_temp                       =  RegInit(0.U(32.W))
    val hitT_temp_3_temp                 =  RegInit(0.U(32.W))
    val enable_3_temp                        = RegInit(0.U(1.W))
    val break_3_temp                           =RegInit(0.U(1.W))
    val ray_aabb_1_3_temp             = RegInit(0. U(1.W))
    val ray_aabb_2_3_temp             = RegInit(0. U(1.W))
    nodeid_ist3_temp_3_temp         := nodeid_ist3_temp_2
    rayid_ist3_temp_3_temp             := rayid_ist3_temp_2
    t_temp_3_temp                                := t_temp_2
    u_temp_3_temp                               := u_temp_2
    hitT_temp_3_temp                         := hitT_temp_2
    enable_3_temp                                 := enable_2
    break_3_temp                                    := break_2
    ray_aabb_1_3_temp                       := ray_aabb_1_2
    ray_aabb_2_3_temp                       := ray_aabb_2_2
    val Oy                                     =  RegInit(0.U(32.W))
    val Dy                                     =  RegInit(0.U(32.W))

    val FADD_11 = Module(new MY_ADD)
        FADD_11.io.a := temp_0_3
        FADD_11.io.b := temp_6
        // FADD_11.io.roundingMode := 0.U
        // FADD_11.io.detectTininess := 1.U
        // FADD_11.io.expected.out := 0.U
        // FADD_11.io.expected.exceptionFlags := 0.U 
        Oy               := FADD_11.io.out

    val FADD_12 = Module(new MY_ADD)
        FADD_12.io.a := temp_5_3
        FADD_12.io.b := temp_7
        // FADD_12.io.roundingMode := 0.U
        // FADD_12.io.detectTininess := 1.U
        // FADD_12.io.expected.out := 0.U
        // FADD_12.io.expected.exceptionFlags := 0.U 
        Dy               := FADD_12.io.out



    val nodeid_ist3_temp_3 =  RegInit(0.S(32.W))
    val rayid_ist3_temp_3     =  RegInit(0.U(32.W))
    val t_temp_3                        =  RegInit(0.U(32.W))
    val u_temp_3                       =  RegInit(0.U(32.W))
    val hitT_temp_3                 =  RegInit(0.U(32.W))
    val enable_3                        = RegInit(0.U(1.W))
    val break_3                           =RegInit(0.U(1.W))
    val ray_aabb_1_3             = RegInit(0. U(1.W))
    val ray_aabb_2_3             = RegInit(0. U(1.W))
    nodeid_ist3_temp_3         := nodeid_ist3_temp_3_temp
    rayid_ist3_temp_3             := rayid_ist3_temp_3_temp
    t_temp_3                                := t_temp_3_temp
    u_temp_3                               := u_temp_3_temp
    hitT_temp_3                         := hitT_temp_3_temp
    enable_3                              := enable_3_temp
    break_3                                 := break_3_temp
    ray_aabb_1_3                    := ray_aabb_1_3_temp
    ray_aabb_2_3                    := ray_aabb_2_3_temp
//----------------------------------------------------

        //stage(4)
        val nodeid_ist3_temp_4_temp =  RegInit(0.S(32.W))
        val rayid_ist3_temp_4_temp     =  RegInit(0.U(32.W))
        val t_temp_4_temp                        =  RegInit(0.U(32.W))
        val u_temp_4_temp                       =  RegInit(0.U(32.W))
        val hitT_temp_4_temp                 =  RegInit(0.U(32.W))
        val enable_4_temp                        = RegInit(0.U(1.W))
        val break_4_temp                           =RegInit(0.U(1.W))        
        val ray_aabb_1_4_temp             = RegInit(0. U(1.W))
        val ray_aabb_2_4_temp             = RegInit(0. U(1.W))
        nodeid_ist3_temp_4_temp         := nodeid_ist3_temp_3
        rayid_ist3_temp_4_temp             := rayid_ist3_temp_3
        t_temp_4_temp                                := t_temp_3
        u_temp_4_temp                               := u_temp_3
        hitT_temp_4_temp                         := hitT_temp_3
        enable_4_temp                                 := enable_3
        break_4_temp                                   := break_3
        ray_aabb_1_4_temp                      := ray_aabb_1_3
        ray_aabb_2_4_temp                      := ray_aabb_2_3
    val temp_v                                          =  RegInit(0.U(32.W))

    val FADD_MUL_17     = Module(new MY_MULADD)
        FADD_MUL_17.io.a := t_temp_3
        FADD_MUL_17.io.b := Dy
        FADD_MUL_17.io.c := Oy
        // FADD_MUL_17.io.roundingMode := 0.U
        // FADD_MUL_17.io.detectTininess             := 1.U
        // FADD_MUL_17.io.expected.out                := 0.U
        // FADD_MUL_17.io.expected.exceptionFlags := 0.U 
        temp_v                           :=  FADD_MUL_17.io.out

        val nodeid_ist3_temp_4 =  RegInit(0.S(32.W))
        val rayid_ist3_temp_4     =  RegInit(0.U(32.W))
        val t_temp_4                        =  RegInit(0.U(32.W))
        val u_temp_4                       =  RegInit(0.U(32.W))
        val hitT_temp_4                 =  RegInit(0.U(32.W))
        val enable_4                        = RegInit(0.U(1.W))
        val break_4                           =RegInit(0.U(1.W))        
        val ray_aabb_1_4             = RegInit(0. U(1.W))
        val ray_aabb_2_4             = RegInit(0. U(1.W))
        nodeid_ist3_temp_4         := nodeid_ist3_temp_4_temp
        rayid_ist3_temp_4             := rayid_ist3_temp_4_temp
        t_temp_4                                := t_temp_4_temp
        u_temp_4                               := u_temp_4_temp
        hitT_temp_4                         := hitT_temp_4_temp
        enable_4                                 := enable_4_temp
        break_4                                   := break_4_temp
        ray_aabb_1_4                       := ray_aabb_1_4_temp
        ray_aabb_2_4                       := ray_aabb_2_4_temp

//----------------------------------------------------
    
    //stage(5)
    val u_add_v                          =  RegInit(0.U(32.W))
    val nodeid_ist3_temp_5_temp =  RegInit(0.S(32.W))
    val rayid_ist3_temp_5_temp     =  RegInit(0.U(32.W))
    val t_temp_5_temp                        =  RegInit(0.U(32.W))
    val v_cmp_0_0                                 =  RegInit(0.U(1.W))
    val hitT_temp_5_temp                 =  RegInit(0.U(32.W))
    val enable_5_temp                        = RegInit(0.U(1.W))
    // val v_cmp_0_temp           = RegInit(0.U(1.W))
    val break_5_temp                           =RegInit(0.U(1.W))        
    val ray_aabb_1_5_temp             = RegInit(0. U(1.W))
    val ray_aabb_2_5_temp             = RegInit(0. U(1.W))
    nodeid_ist3_temp_5_temp         := nodeid_ist3_temp_4
    rayid_ist3_temp_5_temp             := rayid_ist3_temp_4
    t_temp_5_temp                                := t_temp_4
    hitT_temp_5_temp                         := hitT_temp_4
    enable_5_temp                                 := enable_4
    break_5_temp                                   := break_4
    ray_aabb_1_5_temp                    := ray_aabb_1_4
    ray_aabb_2_5_temp                    := ray_aabb_2_4
    val FCMP_24 = Module(new ValExec_CompareRecF32_lt)
        FCMP_24.io.a := 0.U 
        FCMP_24.io.b := temp_v
        FCMP_24.io.expected.out := 0.U
        FCMP_24.io.expected.exceptionFlags := 0.U
        when(FCMP_24.io.actual.out > 0.U){   
            v_cmp_0_0                 := 1.U
        } .otherwise{
            v_cmp_0_0                 := 0.U
        }

   val v_cmp_0                           =  RegInit(0.U(1.W))
    v_cmp_0                                 :=  v_cmp_0_0
    
    val FADD_13 = Module(new MY_ADD)
        FADD_13.io.a := temp_v
        FADD_13.io.b := u_temp_4
        // FADD_13.io.roundingMode := 0.U
        // FADD_13.io.detectTininess := 1.U
        // FADD_13.io.expected.out := 0.U
        // FADD_13.io.expected.exceptionFlags := 0.U 
        u_add_v         := FADD_13.io.out
    
    //stage(6)
    // val v_cmp_0_temp           = RegInit(0.U(1.W))


    val nodeid_ist3_temp_5 =  RegInit(0.S(32.W))
    val rayid_ist3_temp_5     =  RegInit(0.U(32.W))
    val t_temp_5                        =  RegInit(0.U(32.W))
    // val v_cmp_0                         =  RegInit(0.U(1.W))
    // val u_add_v                          =  RegInit(0.U(32.W))
    val hitT_temp_5                 =  RegInit(0.U(32.W))
    val enable_5                        = RegInit(0.U(1.W))
    // val v_cmp_0_temp           = RegInit(0.U(1.W))
    val break_5                           =RegInit(0.U(1.W))        
    val ray_aabb_1_5             = RegInit(0. U(1.W))
    val ray_aabb_2_5             = RegInit(0. U(1.W))

    nodeid_ist3_temp_5         := nodeid_ist3_temp_5_temp
    rayid_ist3_temp_5             := rayid_ist3_temp_5_temp
    t_temp_5                                := t_temp_5_temp
    hitT_temp_5                         := hitT_temp_5_temp
    enable_5                                 := enable_5_temp
    break_5                                   := break_5_temp
    ray_aabb_1_5                       := ray_aabb_1_5_temp
    ray_aabb_2_5                       := ray_aabb_2_5_temp
    io.nodeid_ist3_out              := nodeid_ist3_temp_5
    val FCMP_25 = Module(new ValExec_CompareRecF32_le)
        FCMP_25.io.a := u_add_v 
        FCMP_25.io.b := 1065353216.U
        FCMP_25.io.expected.out := 0.U
        FCMP_25.io.expected.exceptionFlags := 0.U
        when(FCMP_25.io.actual.out === 1.U&&(enable_5===1.U)&&(v_cmp_0 === 1.U)&&(break_5===0.U)){   
                io.hiT_out                  :=   t_temp_5  
                io.hitIndex                 :=   nodeid_ist3_temp_5
                io.pop_3                     :=  true.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5
                io.hitT_en                  := true.B
                io.hitIndex_en         := true.B 
                io.break_out            := false.B
        }.elsewhen(FCMP_25.io.actual.out === 0.U&&(enable_5===1.U)&&(v_cmp_0 === 1.U)&&(break_5===0.U)){
                io.hiT_out                  := hitT_temp_5
                io.hitIndex                 := 3212836864L.S
                io.pop_3                     :=  true.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5   
                io.hitT_en                  := false.B
                io.hitIndex_en         := false.B 
                io.break_out            := false.B
        }.elsewhen(FCMP_25.io.actual.out === 0.U&&(enable_5===1.U)&&(v_cmp_0 === 0.U)&&(break_5===0.U)){
                io.hiT_out                  := hitT_temp_5
                io.hitIndex                 := 3212836864L.S
                io.pop_3                     :=  true.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5   
                io.hitT_en                  := false.B
                io.hitIndex_en         := false.B 
                io.break_out            := false.B
        }.elsewhen(FCMP_25.io.actual.out === 1.U&&(enable_5===1.U)&&(v_cmp_0 === 0.U)&&(break_5===0.U)){
                io.hiT_out                  := hitT_temp_5
                io.hitIndex                 := 3212836864L.S
                io.pop_3                     :=  true.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5   
                io.hitT_en                  := false.B
                io.hitIndex_en         := false.B 
                io.break_out            := false.B
        }.elsewhen(FCMP_25.io.actual.out === 1.U&&(enable_5===1.U)&&(v_cmp_0 === 1.U)&&(break_5===1.U)){   
                io.hiT_out                  :=   t_temp_5  
                io.hitIndex                 :=   nodeid_ist3_temp_5
                io.pop_3                     :=  false.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5
                io.hitT_en                  := true.B
                io.hitIndex_en         := true.B 
                io.break_out            := true.B
        }.elsewhen(FCMP_25.io.actual.out === 0.U&&(enable_5===1.U)&&(v_cmp_0 === 1.U)&&(break_5===1.U)){
                io.hiT_out                  := hitT_temp_5
                io.hitIndex                 := 3212836864L.S
                io.pop_3                     :=  false.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5   
                io.hitT_en                  := false.B
                io.hitIndex_en         := false.B 
                io.break_out            := true.B
        }.elsewhen(FCMP_25.io.actual.out === 0.U&&(enable_5===1.U)&&(v_cmp_0 === 0.U)&&(break_5===1.U)){
                io.hiT_out                  := hitT_temp_5
                io.hitIndex                 := 3212836864L.S
                io.pop_3                     :=  false.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5   
                io.hitT_en                  := false.B
                io.hitIndex_en         := false.B 
                io.break_out            := true.B
        }.elsewhen(FCMP_25.io.actual.out === 1.U&&(enable_5===1.U)&&(v_cmp_0 === 0.U)&&(break_5===1.U)){
                io.hiT_out                  := hitT_temp_5
                io.hitIndex                 := 3212836864L.S
                io.pop_3                     :=  false.B
                io.rayid_ist3_out    :=  rayid_ist3_temp_5   
                io.hitT_en                  := false.B
                io.hitIndex_en         := false.B 
                io.break_out            := true.B
        }.otherwise{
                io.hiT_out                  := 0.U
                io.hitIndex                 := 0.S
                io.pop_3                     :=  false.B
                io.rayid_ist3_out    :=  0.U   
                io.hitT_en                  := false.B
                io.hitIndex_en         := false.B 
                io.break_out            := false.B
        }
        io.RAY_AABB_1_out           := ray_aabb_1_5
        io.RAY_AABB_2_out           := ray_aabb_2_5 
        //     when(v_cmp_0 === 1.U){
        //         io.hiT_out                  :=   t_temp_5  
        //         io.hitIndex                 :=   nodeid_ist3_temp_5
        //         io.pop_3                     :=  true.B
        //         io.rayid_ist3_out    :=  rayid_ist3_temp_5
        //         io.hitT_en                  := true.B
        //         io.hitIndex_en         := true.B 
        //     }.otherwise{
        //         io.hiT_out                  := hitT_temp_5
        //         io.hitIndex                 := 3212836864L.S
        //         io.pop_3                     :=  true.B
        //         io.rayid_ist3_out    :=  rayid_ist3_temp_5   
        //         io.hitT_en                  := false.B
        //         io.hitIndex_en         := false.B 
        //     }
        // } .otherwise{
        //         io.hiT_out                  := 0.U
        //         io.hitIndex                 := 0.S
        //         io.pop_3                     :=  false.B
        //         io.rayid_ist3_out    :=  0.U   
        //         io.hitT_en                  := false.B
        //         io.hitIndex_en         := false.B 
        // }
    // }.otherwise{
    //             io.hiT_out                  := 0.U
    //             io.hitIndex                 := 0.S
    //             io.pop_3                     :=  false.B
    //             io.rayid_ist3_out    :=  0.U   
    //             io.hitT_en                  := false.B
    //             io.hitIndex_en         := false.B 
    // }
}

object IST3 extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new IST3())
}
