package hardfloat
import Chisel._
// import chisel3 ._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
//我估计这里需要增加传递的光线数据和BVH数据的寄存器，不然还得去取数据
class IST1  extends Module{
    val io = IO(new Bundle{
        val enable_IST1     = Input(Bool())
        val nodeid_leaf_1 = Input(SInt(32.W))
        val rayid_leaf_1     = Input(Bits(32.W))
        val hiT_in                  = Input(Bits(32.W))
        val Oz                         =  Input(Bits(32.W))
        val invDz                   =  Input(Bits(32.W))
        val v11_in                 = new Float().asInput
        val v22_in                 = new Float().asInput
        val ray_o_in            = new ray().asInput
        val ray_d_in            = new ray().asInput
        val break_in            = Input(Bool())
        val RAY_AABB_1       = Input(Bool())
        val RAY_AABB_2       = Input(Bool())

        val nodeid_ist1_out = Output(SInt(32.W))
        val rayid_ist1_out      = Output(Bits(32.W))
        val hiT_out                    = Output(Bits(32.W))
        val t                                   = Output(Bits(32.W))
        val ist2_continue        = Output(Bool())
        val pop_1                       = Output(Bool())
        val v11_out                   = new Float().asOutput
        val v22_out                   = new Float().asOutput
        val ray_o_out              = new ray().asOutput
        val ray_d_out              = new ray().asOutput
        val enable_IST2          = Output(Bool())
        val break_out               = Output(Bool())
        val break_ist1              = Output(Bool())
        val RAY_AABB_1_out    = Output(Bool())
        val RAY_AABB_2_out    = Output(Bool())
    })

    //stage(1)
    // when(io.enable_IST1){
    val enable_1                         =  RegInit(0.U(1.W))
    val nodeid_ist1_temp_1 =  RegInit(0. S(32.W))
    val rayid_ist1_temp_1     =  RegInit(0.U(32.W))
    val hitT_temp_1                 =  RegInit(0.U(32.W))
    val t_1                                     =  RegInit(0.U(32.W))
    val v11_1                               =   RegInit(0.U(128.W))
    val v22_1                               =   RegInit(0.U(128.W))
    val ray_o_in_1                    =   RegInit(0.U(96.W))
    val ray_d_in_1                    =   RegInit(0.U(96.W))
    val break_1                          =    RegInit(0.U(1.W))
    val ray_aabb_1                  = RegInit(0. U(1.W))
    val ray_aabb_2                  = RegInit(0. U(1.W))

    nodeid_ist1_temp_1      := io.nodeid_leaf_1
    rayid_ist1_temp_1          := io.rayid_leaf_1
    hitT_temp_1                      := io.hiT_in
    v11_1                                    :=  chisel3.util.Cat(io.v11_in.w,io.v11_in.z,io.v11_in.y,io.v11_in.x)
    v22_1                                    :=  chisel3.util.Cat(io.v22_in.w,io.v22_in.z,io.v22_in.y,io.v22_in.x)
    ray_o_in_1                         :=  chisel3.util.Cat(io.ray_o_in.z,io.ray_o_in.y,io.ray_o_in.x)
    ray_d_in_1                         :=  chisel3.util.Cat(io.ray_d_in.z,io.ray_d_in.y,io.ray_d_in.x)
    enable_1                              := io.enable_IST1
    break_1                                 := io.break_in
    ray_aabb_1                         := io.RAY_AABB_1
    ray_aabb_2                         := io.RAY_AABB_2
    val FMUL_6 = Module(new MY_MUL)
        FMUL_6.io.a := io.Oz
        FMUL_6.io.b := io.invDz
        // FMUL_6.io.roundingMode := 0.U
        // FMUL_6.io.detectTininess := 1.U
        // FMUL_6.io.expected.out := 0.U
        // FMUL_6.io.expected.exceptionFlags := 0.U 
        t_1                                  := FMUL_6.io.out

    val mul_enable_1                         =  RegInit(0.U(1.W))
    val mul_nodeid_ist1_temp_1 =  RegInit(0. S(32.W))
    val mul_rayid_ist1_temp_1     =  RegInit(0.U(32.W))
    val mul_hitT_temp_1                 =  RegInit(0.U(32.W))
    // val mul_t_1                                     =  RegInit(0.U(32.W))
    val mul_v11_1                               =   RegInit(0.U(128.W))
    val mul_v22_1                               =   RegInit(0.U(128.W))
    val mul_ray_o_in_1                    =   RegInit(0.U(96.W))
    val mul_ray_d_in_1                    =   RegInit(0.U(96.W))
    val mul_break_1                          =    RegInit(0.U(1.W))
    val mul_ray_aabb_1                  = RegInit(0. U(1.W))
    val mul_ray_aabb_2                  = RegInit(0. U(1.W))

    mul_nodeid_ist1_temp_1      := nodeid_ist1_temp_1
    mul_rayid_ist1_temp_1          := rayid_ist1_temp_1
    mul_hitT_temp_1                      := hitT_temp_1
    mul_v11_1                                    :=  v11_1
    mul_v22_1                                    :=  v22_1
    mul_ray_o_in_1                         :=  ray_o_in_1
    mul_ray_d_in_1                         :=  ray_d_in_1
    mul_enable_1                              := enable_1
    mul_break_1                                 := break_1
    mul_ray_aabb_1                          := ray_aabb_1
    mul_ray_aabb_2                           := ray_aabb_2
//--------------------------------------------------------------------------------------------------------

    val nodeid_ist1_temp_2 =  RegInit(0. S(32.W))
    val rayid_ist1_temp_2     =  RegInit(0.U(32.W))
    val hitT_temp_2                 =  RegInit(0.U(32.W))
    val t_2                                     =  RegInit(0.U(32.W))
    val t_min                               = RegInit(0.U(1.W))
    val t_hitT                               = RegInit(0.U(1.W))
    val v11_2                               =   RegInit(0.U(128.W))
    val v22_2                               =   RegInit(0.U(128.W))
    val ray_o_in_2                    =   RegInit(0.U(96.W))
    val ray_d_in_2                    =   RegInit(0.U(96.W))
    val enable_2                        = RegInit(0.U(1.W))
    val break_2                          =  RegInit(0.U(1.W))
    val ray_aabb_1_2             = RegInit(0. U(1.W))
    val ray_aabb_2_2             = RegInit(0. U(1.W))

    nodeid_ist1_temp_2      := mul_nodeid_ist1_temp_1
    rayid_ist1_temp_2          := mul_rayid_ist1_temp_1
    hitT_temp_2                      := mul_hitT_temp_1
    t_2                                          := t_1
    v11_2                                    :=  mul_v11_1
    v22_2                                    :=  mul_v22_1
    ray_o_in_2                         :=  mul_ray_o_in_1
    ray_d_in_2                         :=  mul_ray_d_in_1
    enable_2                             := mul_enable_1
    break_2                                := mul_break_1
    ray_aabb_1_2                    := mul_ray_aabb_1
    ray_aabb_2_2                    := mul_ray_aabb_2
    val FCMP_21 = Module(new ValExec_CompareRecF32_lt)
        FCMP_21.io.a := 0.U 
        FCMP_21.io.b := t_1
        FCMP_21.io.expected.out := 0.U
        FCMP_21.io.expected.exceptionFlags := 0.U
        when(FCMP_21.io.actual.out > 0.U){   
            t_min                 := 1.U
        } .otherwise{
            t_min                 := 0.U}

    val FCMP_22 = Module(new ValExec_CompareRecF32_lt)
        FCMP_22.io.a := t_1 
        FCMP_22.io.b := mul_hitT_temp_1
        FCMP_22.io.expected.out := 0.U
        FCMP_22.io.expected.exceptionFlags := 0.U
        when(FCMP_22.io.actual.out > 0.U){   
            t_hitT                 := 1.U
        } .otherwise{
            t_hitT                 := 0.U}
     
    //stage(3)   
        io.v11_out.x                       := v11_2(31,0)
        io.v11_out.y                       := v11_2(63,32)
        io.v11_out.z                       := v11_2(95,64)
        io.v11_out.w                      := v11_2(127,96)
        io.v22_out.x                           := v22_2(31,0)
        io.v22_out.y                           := v22_2(63,32)
        io.v22_out.z                           := v22_2(95,64)
        io.v22_out.w                         := v22_2(127,96)
        io.nodeid_ist1_out            := nodeid_ist1_temp_2
        io.rayid_ist1_out                := rayid_ist1_temp_2
        io.t                                             := t_2
        io.hiT_out                              := hitT_temp_2
        io.ray_o_out.x                      := ray_o_in_2(31,0)
        io.ray_o_out.y                      := ray_o_in_2(63,32)
        io.ray_o_out.z                      := ray_o_in_2(95,64)
        io.ray_d_out.x                      := ray_d_in_2(31,0)
        io.ray_d_out.y                      := ray_d_in_2(63,32)
        io.ray_d_out.z                      := ray_d_in_2(95,64)
        io.break_ist1                         := break_2
        io.RAY_AABB_1_out          := ray_aabb_1_2
        io.RAY_AABB_2_out          := ray_aabb_2_2
    when (((t_min & t_hitT) ===1.U)&&(enable_2===1.U)&&(break_2===0.U)){
        io.ist2_continue   := true.B
        io.pop_1                  := false.B
        io.enable_IST2      := true.B
        io.break_out          := false.B
    }.elsewhen(((t_min & t_hitT) ===0.U)&&(enable_2===1.U)&&(break_2===0.U)){
        io.ist2_continue   := false.B
        io.pop_1                  := true.B
        io.enable_IST2      := false.B
        io.break_out          := false.B    
    }.elsewhen(((t_min & t_hitT) ===1.U)&&(enable_2===1.U)&&(break_2===1.U)){
        io.ist2_continue   := true.B
        io.pop_1                  := false.B
        io.enable_IST2      := true.B
        io.break_out          := false.B        
    }.elsewhen(((t_min & t_hitT) ===0.U)&&(enable_2===1.U)&&(break_2===1.U)){
        io.ist2_continue   := false.B
        io.pop_1                  := false.B
        io.enable_IST2      := false.B
        io.break_out          := true.B        
    } .otherwise{
        io.ist2_continue   := false.B
        io.pop_1                  := false.B
        io.enable_IST2      := false.B//感觉这个不是很有必要
        io.break_out          := false.B
    }

    // }.otherwise{
    //     io.t                                           :=0.U
    //     // io.invDz_div                        :=0.U
    //     io.nodeid_ist1_out          :=0.S
    //     io.rayid_ist1_out              :=0.U
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
    //     io.ist2_continue          := false.B
    //     io.pop_1                         := false.B
    //     io.enable_IST2            := false.B
    // }



}
object IST1 extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new IST1())
}
