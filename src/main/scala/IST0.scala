package hardfloat
import Chisel._
// import chisel3 ._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver

class IST0  extends Module{
    val io = IO(new Bundle{
        val enable_IST0    = Input(Bool())
        val nodeid_leaf     = Input(SInt(32.W))
        val rayid_leaf          = Input(Bits(32.W))
        val hiT_in                  = Input(Bits(32.W))
        val v00                       =  new Float().asInput
        val v11_in                 = new Float().asInput
        val v22_in                 = new Float().asInput
        // val input_ray          = Input(Bits(192.W))//这个光线是初始的光线
        val ray_o_in            = new ray().asInput
        val ray_d_in            = new ray().asInput
        val RAY_AABB_1       = Input(Bool())
        val RAY_AABB_2       = Input(Bool())
        val break_in            = Input(Bool())
        val Oz                         =  Output(Bits(32.W))
        val invDz_div          = Output(Bits(32.W))
        val nodeid_ist0_out = Output(SInt(32.W))
        val rayid_ist0_out      = Output(Bits(32.W))
        val hiT_out                    = Output(Bits(32.W))
        val v11_out                   = new Float().asOutput
        val v22_out                   = new Float().asOutput
        // val input_ray_out      = Output(Bits(192.W))
        val ray_o_out              = new ray().asOutput
        val ray_d_out              = new ray().asOutput
        val enable_SU_out       = Output(Bool())
        val break_out               = Output(Bool())
        val RAY_AABB_1_out    = Output(Bool())
        val RAY_AABB_2_out    = Output(Bool())
    })

//----------------------------------------------------
    val temp_0         = RegInit(0.U(32.W))//v00.w-origx*v00.x
    val temp_1         = RegInit(0.U(32.W))//origy*v00.y
    val temp_2         = RegInit(0.U(32.W))//origz*v00.z
    val temp_3         = RegInit(0.U(32.W))//dirx*v00.x
    val temp_4         = RegInit(0.U(32.W))//diry*v00.y
    val temp_5         = RegInit(0.U(32.W))//diry*v00.z
    
    val enable_1                         = RegInit(0. U(1.W))
    val nodeid_ist0_temp_1 =  RegInit(0. S(32.W))
    val rayid_ist0_temp_1     =  RegInit(0.U(32.W))
    val hitT_temp_1                 =  RegInit(0.U(32.W))
    val v11_1                               =   RegInit(0.U(128.W))
    val v22_1                               =   RegInit(0.U(128.W))
    val ray_o_in_1                    =   RegInit(0.U(96.W))
    val ray_d_in_1                    =   RegInit(0.U(96.W))
    val break_1                          = RegInit(0. U(1.W))
    val ray_aabb_1                  = RegInit(0. U(1.W))
    val ray_aabb_2                  = RegInit(0. U(1.W))

    nodeid_ist0_temp_1      := io.nodeid_leaf
    rayid_ist0_temp_1          := io.rayid_leaf
    hitT_temp_1                      := io.hiT_in
    v11_1                                    :=  chisel3.util.Cat(io.v11_in.w,io.v11_in.z,io.v11_in.y,io.v11_in.x)
    v22_1                                    :=  chisel3.util.Cat(io.v22_in.w,io.v22_in.z,io.v22_in.y,io.v22_in.x)
    ray_o_in_1                         :=  chisel3.util.Cat(io.ray_o_in.z,io.ray_o_in.y,io.ray_o_in.x)
    ray_d_in_1                         :=  chisel3.util.Cat(io.ray_d_in.z,io.ray_d_in.y,io.ray_d_in.x)
    enable_1                             :=  io.enable_IST0
    break_1                                := io.break_in
    ray_aabb_1                         := io.RAY_AABB_1
    ray_aabb_2                         := io.RAY_AABB_2

    val FADD_MUL_13     = Module(new MY_MULADD)
        FADD_MUL_13.io.a := offsign(io.ray_o_in.x)
        FADD_MUL_13.io.b := io.v00.x
        FADD_MUL_13.io.c := io.v00.w
        temp_0                           :=  FADD_MUL_13.io.out

    val FMUL_1 = Module(new MY_MUL)
        FMUL_1.io.a := io.ray_o_in.y
        FMUL_1.io.b := io.v00.y
        temp_1                           := FMUL_1.io.out

    val FMUL_2 = Module(new MY_MUL)
        FMUL_2.io.a := io.ray_o_in.z
        FMUL_2.io.b := io.v00.z
        temp_2                           := FMUL_2.io.out

    val FMUL_3 = Module(new MY_MUL)
        FMUL_3.io.a := io.ray_d_in.x
        FMUL_3.io.b := io.v00.x
        temp_3                           := FMUL_3.io.out
    
    val FMUL_4 = Module(new MY_MUL)
        FMUL_4.io.a := io.ray_d_in.y
        FMUL_4.io.b := io.v00.y
        temp_4                           := FMUL_4.io.out
    
    val FMUL_5 = Module(new MY_MUL)
        FMUL_5.io.a := io.ray_d_in.z
        FMUL_5.io.b := io.v00.z
        temp_5                           :=FMUL_5.io.out

    val nodeid_ist0_temp_temp =  RegInit(0. S(32.W))
    val rayid_ist0_temp_temp     =  RegInit(0.U(32.W))
    val hitT_temp_temp                 =  RegInit(0.U(32.W))
    val v11_temp                               =   RegInit(0.U(128.W))
    val v22_temp                               =   RegInit(0.U(128.W))
    val ray_o_in_temp                    =   RegInit(0.U(96.W))
    val ray_d_in_temp                    =   RegInit(0.U(96.W))
    val enable_temp                         = RegInit(0. U(1.W))
    val break_temp                          = RegInit(0. U(1.W))
    val ray_aabb_1_temp             = RegInit(0. U(1.W))
    val ray_aabb_2_temp             = RegInit(0. U(1.W))

    nodeid_ist0_temp_temp      := nodeid_ist0_temp_1
    rayid_ist0_temp_temp          := rayid_ist0_temp_1
    hitT_temp_temp                      := hitT_temp_1
    v11_temp                                    :=  v11_1
    v22_temp                                    :=  v22_1
    ray_o_in_temp                         :=  ray_o_in_1
    ray_d_in_temp                         :=  ray_d_in_1
    enable_temp                             := enable_1 
    break_temp                                := break_1
    ray_aabb_1_temp                   := ray_aabb_1 
    ray_aabb_2_temp                   := ray_aabb_2
//----------------------------------------------------


    //stage(3)
    val nodeid_ist0_temp_2 =  RegInit(0.S(32.W))
    val rayid_ist0_temp_2     =  RegInit(0.U(32.W))
    val hitT_temp_2                 =  RegInit(0.U(32.W))
    val v11_2                               =   RegInit(0.U(128.W))
    val v22_2                               =   RegInit(0.U(128.W))
    val ray_o_in_2                    =   RegInit(0.U(96.W))
    val ray_d_in_2                    =   RegInit(0.U(96.W))
    val enable_2                         = RegInit(0. U(1.W))
    val break_2                          = RegInit(0. U(1.W))
    val ray_aabb_1_2             = RegInit(0. U(1.W))
    val ray_aabb_2_2              = RegInit(0. U(1.W))

    nodeid_ist0_temp_2      := nodeid_ist0_temp_temp
    rayid_ist0_temp_2          := rayid_ist0_temp_temp
    hitT_temp_2                      := hitT_temp_temp
    v11_2                                    :=  v11_temp
    v22_2                                    :=  v22_temp
    ray_o_in_2                         :=  ray_o_in_temp
    ray_d_in_2                         :=  ray_d_in_temp
    enable_2                             := enable_temp 
    break_2                                := break_temp
    enable_2                             := enable_temp 
    break_2                                := break_temp
    ray_aabb_1_2                    := ray_aabb_1_temp
    ray_aabb_2_2                    := ray_aabb_2_temp
    val temp_6                          = RegInit(0.U(32.W))//origy*v00.y+origz*v00.z
    val temp_7                          = RegInit(0.U(32.W))//drigx*v00.x+driy*v00.y
    val temp_0_2                     = RegInit(0.U(32.W))// v00.w-origx*v00.x暂存值
    val temp_5_2                    = RegInit(0.U(32.W))//dirz*v00.z暂存值

    temp_0_2                            := temp_0
    temp_5_2                            := temp_5 

    val FADD_1 = Module(new MY_ADD)
        FADD_1.io.a := temp_1
        FADD_1.io.b := temp_2
        // FADD_1.io.roundingMode := 0.U
        // FADD_1.io.detectTininess := 1.U
        // FADD_1.io.expected.out := 0.U
        // FADD_1.io.expected.exceptionFlags := 0.U 
        temp_6           := FADD_1.io.out
    
    val FADD_2 = Module(new MY_ADD)
        FADD_2.io.a := temp_3
        FADD_2.io.b := temp_4
        // FADD_2.io.roundingMode := 0.U
        // FADD_2.io.detectTininess := 1.U
        // FADD_2.io.expected.out := 0.U
        // FADD_2.io.expected.exceptionFlags := 0.U 
        temp_7           := FADD_2.io.out

    val add_nodeid_ist0_temp_2 =  RegInit(0.S(32.W))
    val add_rayid_ist0_temp_2     =  RegInit(0.U(32.W))
    val add_hitT_temp_2                 =  RegInit(0.U(32.W))
    val add_v11_2                               =   RegInit(0.U(128.W))
    val add_v22_2                               =   RegInit(0.U(128.W))
    val add_ray_o_in_2                    =   RegInit(0.U(96.W))
    val add_ray_d_in_2                    =   RegInit(0.U(96.W))
    val add_enable_2                         = RegInit(0. U(1.W))
    val add_break_2                          = RegInit(0. U(1.W))
    val add_ray_aabb_1_2             = RegInit(0. U(1.W))
    val add_ray_aabb_2_2              = RegInit(0. U(1.W))

    add_nodeid_ist0_temp_2      := nodeid_ist0_temp_2
    add_rayid_ist0_temp_2          := rayid_ist0_temp_2
    add_hitT_temp_2                      := hitT_temp_2
    add_v11_2                                    :=  v11_2
    add_v22_2                                    :=  v22_2
    add_ray_o_in_2                         :=  ray_o_in_2
    add_ray_d_in_2                         :=  ray_d_in_2
    add_enable_2                             := enable_2 
    add_break_2                                := break_2
    add_ray_aabb_1_2                    := ray_aabb_1_2
    add_ray_aabb_2_2                    := ray_aabb_2_2

    val add_temp_0_2                     = RegInit(0.U(32.W))// v00.w-origx*v00.x暂存值
    val add_temp_5_2                    = RegInit(0.U(32.W))//dirz*v00.z暂存值

    add_temp_0_2                            := temp_0_2
    add_temp_5_2                            := temp_5_2 

    
//----------------------------------------------------
    val add2_nodeid_ist0_temp_2 =  RegInit(0.S(32.W))
    val add2_rayid_ist0_temp_2     =  RegInit(0.U(32.W))
    val add2_hitT_temp_2                 =  RegInit(0.U(32.W))
    val add2_v11_2                               =   RegInit(0.U(128.W))
    val add2_v22_2                               =   RegInit(0.U(128.W))
    val add2_ray_o_in_2                    =   RegInit(0.U(96.W))
    val add2_ray_d_in_2                    =   RegInit(0.U(96.W))
    val add2_enable_2                         = RegInit(0. U(1.W))
    val add2_break_2                          = RegInit(0. U(1.W))
    val add2_ray_aabb_1_2             = RegInit(0. U(1.W))
    val add2_ray_aabb_2_2              = RegInit(0. U(1.W))

    add2_nodeid_ist0_temp_2      := add_nodeid_ist0_temp_2
    add2_rayid_ist0_temp_2          := add_rayid_ist0_temp_2
    add2_hitT_temp_2                      := add_hitT_temp_2
    add2_v11_2                                    :=  add_v11_2
    add2_v22_2                                    :=  add_v22_2
    add2_ray_o_in_2                         :=  add_ray_o_in_2
    add2_ray_d_in_2                         :=  add_ray_d_in_2
    add2_enable_2                             := add_enable_2 
    add2_break_2                                := add_break_2
    add2_ray_aabb_1_2                    := add_ray_aabb_1_2
    add2_ray_aabb_2_2                    := add_ray_aabb_2_2
//----------------------------------------------------
    io.nodeid_ist0_out         := add2_nodeid_ist0_temp_2
    io.rayid_ist0_out             := add2_rayid_ist0_temp_2
    io.hiT_out                           := add2_hitT_temp_2 
    io.v11_out.x                       := add2_v11_2(31,0)
    io.v11_out.y                       := add2_v11_2(63,32)
    io.v11_out.z                       := add2_v11_2(95,64)
    io.v11_out.w                      := add2_v11_2(127,96)
    // io.v22_out                           := v22_2
    io.v22_out.x                           := add2_v22_2(31,0)
    io.v22_out.y                           := add2_v22_2(63,32)
    io.v22_out.z                           := add2_v22_2(95,64)
    io.v22_out.w                         := add2_v22_2(127,96)
    // io.ray_o_out                      := ray_o_in_2
    // io.ray_d_out                      := ray_d_in_2
    io.ray_o_out.x                      := add2_ray_o_in_2(31,0)
    io.ray_o_out.y                      := add2_ray_o_in_2(63,32)
    io.ray_o_out.z                      := add2_ray_o_in_2(95,64)
    io.ray_d_out.x                      := add2_ray_d_in_2(31,0)
    io.ray_d_out.y                      := add2_ray_d_in_2(63,32)
    io.ray_d_out.z                      := add2_ray_d_in_2(95,64)
    io.enable_SU_out               := add2_enable_2
    io.break_out                         := add2_break_2
    io.RAY_AABB_1_out           := add2_ray_aabb_1_2
    io.RAY_AABB_2_out           := add2_ray_aabb_2_2
    val FADD_3 = Module(new MY_ADD)
        FADD_3.io.a := add_temp_0_2
        FADD_3.io.b := offsign(temp_6)
        // FADD_3.io.roundingMode := 0.U
        // FADD_3.io.detectTininess := 1.U
        // FADD_3.io.expected.out := 0.U
        // FADD_3.io.expected.exceptionFlags := 0.U 
        io.Oz               := FADD_3.io.out

    val FADD_4 = Module(new MY_ADD)
        FADD_4.io.a := add_temp_5_2
        FADD_4.io.b := temp_7
        // FADD_4.io.roundingMode := 0.U
        // FADD_4.io.detectTininess := 1.U
        // FADD_4.io.expected.out := 0.U
        // FADD_4.io.expected.exceptionFlags := 0.U 
        io.invDz_div := FADD_4.io.out
    // }.otherwise{
    //     io.Oz                                       :=0.U
    //     io.invDz_div                        :=0.U
    //     io.nodeid_ist0_out          :=0.S
    //     io.rayid_ist0_out              :=0.U
    //     io.hiT_out                            :=0.U
    //     io.v11_out.x                        := 0.U
    //     io.v11_out.y                        := 0.U
    //     io.v11_out.z                        := 0.U
    //     io.v11_out.w                      := 0.U
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
    //     io.enable_SU_out          := false.B
    // }
}

object IST0 extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new IST0())
}
