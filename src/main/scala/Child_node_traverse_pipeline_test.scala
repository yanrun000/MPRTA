import hardfloat._
import Chisel._
import chisel3.util._
import chisel3 . iotesters ._
//在实现过程中只使用了六级流水，最后一个输出的端口实在没有必要多加一级
class Child_node_traverse_pipeline extends Module{
    val io = IO(new  Bundle{
        val n0xy    = new Float().asInput 
        val n1xy    = new Float().asInput
        val nz      = new Float().asInput

        val idirx = Input(Bits(32.W))
        val idiry = Input(Bits(32.W))
        val idirz = Input(Bits(32.W))
        val oodx = Input(Bits(32.W))
        val oody = Input(Bits(32.W))
        val oodz = Input(Bits(32.W))
        val tmin = Input(Bits(32.W))
        val hitT = Input(Bits(32.W))
        val child_mask_0 = Input(Bits(8.W))
        val child_mask_1 = Input(Bits(8.W))
        val traverseChild0 = Output(Bool())
        val traverseChild1 = Output(Bool())

        //以下均是打印的观测值
        val out_1 = Output(Bits(32.W)) //作为一个观测的输出值
        val out_2 = Output(Bits(32.W))
        val out_3 = Output(Bits(32.W))
        val out_4 = Output(Bits(32.W))
        val out_5 = Output(Bits(32.W))
        val out_6 = Output(Bits(32.W))
        val out_7 = Output(Bits(32.W))
        val out_8 = Output(Bits(32.W))
        val out_9 = Output(Bits(32.W))
        val out_10 = Output(Bits(32.W))
        val out_11 = Output(Bits(32.W))
        val out_12 = Output(Bits(32.W))

        val outt_1 = Output(Bits(32.W)) //第二组观测输出值
        val outt_2 = Output(Bits(32.W))
        val outt_3 = Output(Bits(32.W))
        val outt_4 = Output(Bits(32.W))
        val outt_5 = Output(Bits(32.W))
        val outt_6 = Output(Bits(32.W))
        val outt_7 = Output(Bits(32.W))
        val outt_8 = Output(Bits(32.W))
        val outt_9 = Output(Bits(32.W))
        val outt_10 = Output(Bits(32.W))
        val outt_11 = Output(Bits(32.W))
        val outt_12 = Output(Bits(32.W))

        val ooutt_1 = Output(Bits(32.W)) //第三组观测输出值
        val ooutt_2 = Output(Bits(32.W))
        val ooutt_3 = Output(Bits(32.W))
        val ooutt_4 = Output(Bits(32.W))
        val ooutt_5 = Output(Bits(32.W))
        val ooutt_6 = Output(Bits(32.W))
        val ooutt_7 = Output(Bits(32.W))
        val ooutt_8 = Output(Bits(32.W))
        val ooutt_9 = Output(Bits(32.W))
        val ooutt_10 = Output(Bits(32.W))
        val ooutt_11 = Output(Bits(32.W))
        val ooutt_12 = Output(Bits(32.W))

        val hmaxout_1 = Output(Bits(32.W))
        val hmaxout_2 = Output(Bits(32.W))
        val hmaxout_3 = Output(Bits(32.W))
        val hmaxout_4 = Output(Bits(32.W))
        val hmaxout_5 = Output(Bits(32.W))
        val hmaxout_6 = Output(Bits(32.W))
        val hmaxout_7 = Output(Bits(32.W))
        val hmaxout_8 = Output(Bits(32.W))
            
        val c0Min_out = Output(Bits(32.W))
        val c0Max_out = Output(Bits(32.W))
        val c1Min_out = Output(Bits(32.W))
        val c1Max_out = Output(Bits(32.W))    

        val cmp0_out = Output(Bool())
        val cmp1_out = Output(Bool())
    })
    //temp for step(1)
    val temp0_1 = RegInit(0.U(32.W))
    val temp0_2 = RegInit(0.U(32.W))
    val temp0_3 = RegInit(0.U(32.W))
    val temp0_4 = RegInit(0.U(32.W))   
    val temp0_5 = RegInit(0.U(32.W))
    val temp0_6 = RegInit(0.U(32.W))
    val temp1_1 = RegInit(0.U(32.W))
    val temp1_2 = RegInit(0.U(32.W))
    val temp1_3 = RegInit(0.U(32.W))
    val temp1_4 = RegInit(0.U(32.W))   
    val temp1_5 = RegInit(0.U(32.W))
    val temp1_6 = RegInit(0.U(32.W))
    val oodx_1  = RegInit(0.U(32.W))
    val oody_1  = RegInit(0.U(32.W))
    val oodz_1  = RegInit(0.U(32.W))
    val tmin_1  = RegInit(0.U(32.W))
    val hitT_1  = RegInit(0.U(32.W))
    val child_mask_1_1  = RegInit(0.U(1.W))
    //temp for step(2)
    val c0lox = RegInit(0.U(32.W))
    val c0hix = RegInit(0.U(32.W))
    val c0loy = RegInit(0.U(32.W))
    val c0hiy = RegInit(0.U(32.W))
    val c0loz = RegInit(0.U(32.W))
    val c0hiz = RegInit(0.U(32.W))
    val c1lox = RegInit(0.U(32.W))
    val c1hix = RegInit(0.U(32.W))
    val c1loy = RegInit(0.U(32.W))
    val c1hiy = RegInit(0.U(32.W))
    val c1loz = RegInit(0.U(32.W))
    val c1hiz = RegInit(0.U(32.W))
    val tmin_2= RegInit(0.U(32.W))
    val hitT_2= RegInit(0.U(32.W))
    val child_mask_1_2  = RegInit(0.U(1.W))
    //temp for step(3)
    val cmpMin0_1 = RegInit(0.U(32.W))
    val cmpMin0_2 = RegInit(0.U(32.W))
    val cmpMin0_3 = RegInit(0.U(32.W))
    val cmpMax0_1 = RegInit(0.U(32.W))
    val cmpMax0_2 = RegInit(0.U(32.W))
    val cmpMax0_3 = RegInit(0.U(32.W))
    val cmpMin1_1 = RegInit(0.U(32.W))
    val cmpMin1_2 = RegInit(0.U(32.W))
    val cmpMin1_3 = RegInit(0.U(32.W))
    val cmpMax1_1 = RegInit(0.U(32.W))
    val cmpMax1_2 = RegInit(0.U(32.W))
    val cmpMax1_3 = RegInit(0.U(32.W))
    val tmin_3    = RegInit(0.U(32.W))
    val hitT_3    = RegInit(0.U(32.W))
    val child_mask_1_3  = RegInit(0.U(1.W))
    //temp for step(4)
    val c0Min_temp_1 = RegInit(0.U(32.W))
    val c0Min_temp_2 = RegInit(0.U(32.W))
    val c0Max_temp_1 = RegInit(0.U(32.W))
    val c0Max_temp_2 = RegInit(0.U(32.W))
    val c1Min_temp_1 = RegInit(0.U(32.W))
    val c1Min_temp_2 = RegInit(0.U(32.W))
    val c1Max_temp_1 = RegInit(0.U(32.W))
    val c1Max_temp_2 = RegInit(0.U(32.W))
    val child_mask_1_4  = RegInit(0.U(1.W))
    //temp for step(5)
    val c0Min = RegInit(0.U(32.W))
    val c0Max = RegInit(0.U(32.W))
    val c1Min = RegInit(0.U(32.W))
    val c1Max = RegInit(0.U(32.W))
    val child_mask_1_5  = RegInit(0.U(1.W))
    //temp for step(6)
    val cmp0 = RegInit(0.U(1.W))
    val cmp1 = RegInit(0.U(1.W))

    // when(!io.child_mask_1 === 0.U)
    // {
    //step(1)
    oodx_1 := io.oodx
    oody_1 := io.oody
    oodz_1 := io.oodz
    tmin_1 := io.tmin
    hitT_1 := io.hitT
    child_mask_1_1 := io.child_mask_1
    val FMUL_1 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_1.io.a := io.n0xy.x
    FMUL_1.io.b := io.idirx
    FMUL_1.io.roundingMode := 0.U
    FMUL_1.io.detectTininess := 1.U
    FMUL_1.io.expected.out := 0.U
    FMUL_1.io.expected.exceptionFlags := 0.U 
    temp0_1 := fNFromRecFN(8,24,FMUL_1.io.actual.out)
    io.out_1 := temp0_1

    val FMUL_2 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_2.io.a := io.n0xy.y
    FMUL_2.io.b := io.idirx
    FMUL_2.io.roundingMode := 0.U
    FMUL_2.io.detectTininess := 1.U
    FMUL_2.io.expected.out := 0.U
    FMUL_2.io.expected.exceptionFlags := 0.U 
    temp0_2 := fNFromRecFN(8,24,FMUL_2.io.actual.out)
    io.out_2 := temp0_2

    val FMUL_3 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_3.io.a := io.n0xy.z
    FMUL_3.io.b := io.idiry
    FMUL_3.io.roundingMode := 0.U
    FMUL_3.io.detectTininess := 1.U
    FMUL_3.io.expected.out := 0.U
    FMUL_3.io.expected.exceptionFlags := 0.U 
    temp0_3 := fNFromRecFN(8,24,FMUL_3.io.actual.out)
    io.out_3 := temp0_3
    
    val FMUL_4 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_4.io.a := io.n0xy.w
    FMUL_4.io.b := io.idiry
    FMUL_4.io.roundingMode := 0.U
    FMUL_4.io.detectTininess := 1.U
    FMUL_4.io.expected.out := 0.U
    FMUL_4.io.expected.exceptionFlags := 0.U 
    temp0_4 := fNFromRecFN(8,24,FMUL_4.io.actual.out)
    io.out_4 := temp0_4

    val FMUL_5 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_5.io.a := io.nz.x
    FMUL_5.io.b := io.idirz
    FMUL_5.io.roundingMode := 0.U
    FMUL_5.io.detectTininess := 1.U
    FMUL_5.io.expected.out := 0.U
    FMUL_5.io.expected.exceptionFlags := 0.U 
    temp0_5 := fNFromRecFN(8,24,FMUL_5.io.actual.out)
    io.out_5 := temp0_5

    val FMUL_6 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_6.io.a := io.nz.y
    FMUL_6.io.b := io.idirz
    FMUL_6.io.roundingMode := 0.U
    FMUL_6.io.detectTininess := 1.U
    FMUL_6.io.expected.out := 0.U
    FMUL_6.io.expected.exceptionFlags := 0.U 
    temp0_6 := fNFromRecFN(8,24,FMUL_6.io.actual.out)
    io.out_6 := temp0_6

    val FMUL_7 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_7.io.a := io.n1xy.x
    FMUL_7.io.b := io.idirx
    FMUL_7.io.roundingMode := 0.U
    FMUL_7.io.detectTininess := 1.U
    FMUL_7.io.expected.out := 0.U
    FMUL_7.io.expected.exceptionFlags := 0.U 
    temp1_1 := fNFromRecFN(8,24,FMUL_7.io.actual.out)
    io.out_7 := temp1_1

    val FMUL_8 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_8.io.a := io.n1xy.y
    FMUL_8.io.b := io.idirx
    FMUL_8.io.roundingMode := 0.U
    FMUL_8.io.detectTininess := 1.U
    FMUL_8.io.expected.out := 0.U
    FMUL_8.io.expected.exceptionFlags := 0.U 
    temp1_2 := fNFromRecFN(8,24,FMUL_8.io.actual.out)
    io.out_8 := temp1_2

    val FMUL_9 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_9.io.a := io.n1xy.z
    FMUL_9.io.b := io.idiry
    FMUL_9.io.roundingMode := 0.U
    FMUL_9.io.detectTininess := 1.U
    FMUL_9.io.expected.out := 0.U
    FMUL_9.io.expected.exceptionFlags := 0.U
    temp1_3 := fNFromRecFN(8,24,FMUL_9.io.actual.out)
    io.out_9 := temp1_3

    val FMUL_10 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_10.io.a := io.n1xy.w
    FMUL_10.io.b := io.idiry
    FMUL_10.io.roundingMode := 0.U
    FMUL_10.io.detectTininess := 1.U
    FMUL_10.io.expected.out := 0.U
    FMUL_10.io.expected.exceptionFlags := 0.U 
    temp1_4 := fNFromRecFN(8,24,FMUL_10.io.actual.out)
    io.out_10 := temp1_4   

    val FMUL_11 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_11.io.a := io.nz.z
    FMUL_11.io.b := io.idirz
    FMUL_11.io.roundingMode := 0.U
    FMUL_11.io.detectTininess := 1.U
    FMUL_11.io.expected.out := 0.U
    FMUL_11.io.expected.exceptionFlags := 0.U 
    temp1_5 := fNFromRecFN(8,24,FMUL_11.io.actual.out)
    io.out_11 := temp1_5    

    val FMUL_12 = Module(new ValExec_MulAddRecF32_mul)
    FMUL_12.io.a := io.nz.w
    FMUL_12.io.b := io.idirz
    FMUL_12.io.roundingMode := 0.U
    FMUL_12.io.detectTininess := 1.U
    FMUL_12.io.expected.out := 0.U
    FMUL_12.io.expected.exceptionFlags := 0.U 
    temp1_6 := fNFromRecFN(8,24,FMUL_12.io.actual.out)
    io.out_12 := temp1_6   

    //step(2)
    tmin_2 := tmin_1
    hitT_2 := hitT_1
    child_mask_1_2 := child_mask_1_1
    val FADD_1 = Module(new ValExec_MulAddRecF32_add)
    FADD_1.io.a := temp0_1
    FADD_1.io.b := offsign(oodx_1)
    FADD_1.io.roundingMode := 0.U
    FADD_1.io.detectTininess := 1.U
    FADD_1.io.expected.out := 0.U
    FADD_1.io.expected.exceptionFlags := 0.U 
    c0lox := fNFromRecFN(8,24,FADD_1.io.actual.out)
    io.outt_1 := c0lox

    val FADD_2 = Module(new ValExec_MulAddRecF32_add)
    FADD_2.io.a := temp0_2
    FADD_2.io.b := offsign(oodx_1)
    FADD_2.io.roundingMode := 0.U
    FADD_2.io.detectTininess := 1.U
    FADD_2.io.expected.out := 0.U
    FADD_2.io.expected.exceptionFlags := 0.U 
    c0hix := fNFromRecFN(8,24,FADD_2.io.actual.out)
    io.outt_2 := c0hix

    val FADD_3 = Module(new ValExec_MulAddRecF32_add)
    FADD_3.io.a := temp0_3
    FADD_3.io.b := offsign(oody_1)
    FADD_3.io.roundingMode := 0.U
    FADD_3.io.detectTininess := 1.U
    FADD_3.io.expected.out := 0.U
    FADD_3.io.expected.exceptionFlags := 0.U 
    c0loy := fNFromRecFN(8,24,FADD_3.io.actual.out)
    io.outt_3 := c0loy

    val FADD_4 = Module(new ValExec_MulAddRecF32_add)
    FADD_4.io.a := temp0_4
    FADD_4.io.b := offsign(oody_1)
    FADD_4.io.roundingMode := 0.U
    FADD_4.io.detectTininess := 1.U
    FADD_4.io.expected.out := 0.U
    FADD_4.io.expected.exceptionFlags := 0.U 
    c0hiy := fNFromRecFN(8,24,FADD_4.io.actual.out)
    io.outt_4 := c0hiy

    val FADD_5 = Module(new ValExec_MulAddRecF32_add)
    FADD_5.io.a := temp0_5
    FADD_5.io.b := offsign(oodz_1)
    FADD_5.io.roundingMode := 0.U
    FADD_5.io.detectTininess := 1.U
    FADD_5.io.expected.out := 0.U
    FADD_5.io.expected.exceptionFlags := 0.U 
    c0loz := fNFromRecFN(8,24,FADD_5.io.actual.out)
    io.outt_5 := c0loz

    val FADD_6 = Module(new ValExec_MulAddRecF32_add)
    FADD_6.io.a := temp0_6
    FADD_6.io.b := offsign(oodz_1)
    FADD_6.io.roundingMode := 0.U
    FADD_6.io.detectTininess := 1.U
    FADD_6.io.expected.out := 0.U
    FADD_6.io.expected.exceptionFlags := 0.U 
    c0hiz := fNFromRecFN(8,24,FADD_6.io.actual.out)
    io.outt_6 := c0hiz
    
    val FADD_7 = Module(new ValExec_MulAddRecF32_add)
    FADD_7.io.a := temp1_1
    FADD_7.io.b := offsign(oodx_1)
    FADD_7.io.roundingMode := 0.U
    FADD_7.io.detectTininess := 1.U
    FADD_7.io.expected.out := 0.U
    FADD_7.io.expected.exceptionFlags := 0.U 
    c1lox := fNFromRecFN(8,24,FADD_7.io.actual.out)
    io.outt_7 := c1lox

    val FADD_8 = Module(new ValExec_MulAddRecF32_add)
    FADD_8.io.a := temp1_2
    FADD_8.io.b := offsign(oodx_1)
    FADD_8.io.roundingMode := 0.U
    FADD_8.io.detectTininess := 1.U
    FADD_8.io.expected.out := 0.U
    FADD_8.io.expected.exceptionFlags := 0.U 
    c1hix := fNFromRecFN(8,24,FADD_8.io.actual.out)
    io.outt_8 := c1hix

    val FADD_9 = Module(new ValExec_MulAddRecF32_add)
    FADD_9.io.a := temp1_3
    FADD_9.io.b := offsign(oody_1)
    FADD_9.io.roundingMode := 0.U
    FADD_9.io.detectTininess := 1.U
    FADD_9.io.expected.out := 0.U
    FADD_9.io.expected.exceptionFlags := 0.U 
    c1loy := fNFromRecFN(8,24,FADD_9.io.actual.out)
    io.outt_9 := c1loy

    val FADD_10 = Module(new ValExec_MulAddRecF32_add)
    FADD_10.io.a := temp1_4
    FADD_10.io.b := offsign(oody_1)
    FADD_10.io.roundingMode := 0.U
    FADD_10.io.detectTininess := 1.U
    FADD_10.io.expected.out := 0.U
    FADD_10.io.expected.exceptionFlags := 0.U 
    c1hiy := fNFromRecFN(8,24,FADD_10.io.actual.out)
    io.outt_10 := c1hiy

    val FADD_11 = Module(new ValExec_MulAddRecF32_add)
    FADD_11.io.a := temp1_5
    FADD_11.io.b := offsign(oodz_1)
    FADD_11.io.roundingMode := 0.U
    FADD_11.io.detectTininess := 1.U
    FADD_11.io.expected.out := 0.U
    FADD_11.io.expected.exceptionFlags := 0.U 
    c1loz := fNFromRecFN(8,24,FADD_11.io.actual.out)
    io.outt_11 := c1loz

    val FADD_12 = Module(new ValExec_MulAddRecF32_add)
    FADD_12.io.a := temp1_6
    FADD_12.io.b := offsign(oodz_1)
    FADD_12.io.roundingMode := 0.U
    FADD_12.io.detectTininess := 1.U
    FADD_12.io.expected.out := 0.U
    FADD_12.io.expected.exceptionFlags := 0.U 
    c1hiz := fNFromRecFN(8,24,FADD_12.io.actual.out)
    io.outt_12 := c1hiz
    
    //step(3)
    tmin_3 := tmin_2
    hitT_3 := hitT_2
    child_mask_1_3 := child_mask_1_2
    val FCMP_1 = Module(new ValExec_CompareRecF32_le)
    FCMP_1.io.a := c0lox
    FCMP_1.io.b := c0hix
    FCMP_1.io.expected.out := 0.U
    FCMP_1.io.expected.exceptionFlags := 0.U
    when(FCMP_1.io.actual.out > 0.U){
        cmpMin0_1 := c0lox    
        cmpMax0_1 := c0hix
    } .otherwise{
        cmpMin0_1 := c0hix
        cmpMax0_1 := c0lox
    }
    io.ooutt_1 := cmpMin0_1
    io.ooutt_2 := cmpMax0_1

    val FCMP_2 = Module(new ValExec_CompareRecF32_le)
    FCMP_2.io.a := c0loy
    FCMP_2.io.b := c0hiy
    FCMP_2.io.expected.out := 0.U
    FCMP_2.io.expected.exceptionFlags := 0.U
    when(FCMP_2.io.actual.out > 0.U){
        cmpMin0_2 := c0loy    
        cmpMax0_2 := c0hiy
    } .otherwise{
        cmpMin0_2 := c0hiy
        cmpMax0_2 := c0loy
    }
    io.ooutt_3 := cmpMin0_2
    io.ooutt_4 := cmpMax0_2

    val FCMP_3 = Module(new ValExec_CompareRecF32_le)
    FCMP_3.io.a := c0loz
    FCMP_3.io.b := c0hiz
    FCMP_3.io.expected.out := 0.U
    FCMP_3.io.expected.exceptionFlags := 0.U
    when(FCMP_3.io.actual.out > 0.U){
        cmpMin0_3 := c0loz    
        cmpMax0_3 := c0hiz
    } .otherwise{
        cmpMin0_3 := c0hiz
        cmpMax0_3 := c0loz
    }
    io.ooutt_5 := cmpMin0_3
    io.ooutt_6 := cmpMax0_3

    val FCMP_4 = Module(new ValExec_CompareRecF32_le)
    FCMP_4.io.a := c1lox
    FCMP_4.io.b := c1hix
    FCMP_4.io.expected.out := 0.U
    FCMP_4.io.expected.exceptionFlags := 0.U
    when(FCMP_4.io.actual.out > 0.U){
        cmpMin1_1 := c1lox    
        cmpMax1_1 := c1hix
    } .otherwise{
        cmpMin1_1 := c1hix
        cmpMax1_1 := c1lox
    }
    io.ooutt_7 := cmpMin1_1
    io.ooutt_8 := cmpMax1_1

    val FCMP_5 = Module(new ValExec_CompareRecF32_le)
    FCMP_5.io.a := c1loy
    FCMP_5.io.b := c1hiy
    FCMP_5.io.expected.out := 0.U
    FCMP_5.io.expected.exceptionFlags := 0.U
    when(FCMP_5.io.actual.out > 0.U){
        cmpMin1_2 := c1loy    
        cmpMax1_2 := c1hiy
    } .otherwise{
        cmpMin1_2 := c1hiy
        cmpMax1_2 := c1loy
    }
    io.ooutt_9 := cmpMin1_2
    io.ooutt_10 := cmpMax1_2    

    val FCMP_6 = Module(new ValExec_CompareRecF32_le)
    FCMP_6.io.a := c1loz
    FCMP_6.io.b := c1hiz
    FCMP_6.io.expected.out := 0.U
    FCMP_6.io.expected.exceptionFlags := 0.U
    when(FCMP_6.io.actual.out > 0.U){
        cmpMin1_3 := c1loz    
        cmpMax1_3 := c1hiz
    } .otherwise{
        cmpMin1_3 := c1hiz
        cmpMax1_3 := c1loz
    }
    io.ooutt_11 := cmpMin1_3
    io.ooutt_12 := cmpMax1_3   

    //step(4)
    child_mask_1_4 := child_mask_1_3
    val FCMP_7 = Module(new ValExec_CompareRecF32_le)
    FCMP_7.io.a := cmpMin0_1
    FCMP_7.io.b := cmpMin0_2
    FCMP_7.io.expected.out := 0.U
    FCMP_7.io.expected.exceptionFlags := 0.U
    when(FCMP_7.io.actual.out > 0.U){   
        c0Min_temp_1 := cmpMin0_2
    } .otherwise{
        c0Min_temp_1 := cmpMin0_1
    }
    io.hmaxout_1 := c0Min_temp_1 

    val FCMP_8 = Module(new ValExec_CompareRecF32_le)
    FCMP_8.io.a := cmpMin0_3
    FCMP_8.io.b := tmin_3
    FCMP_8.io.expected.out := 0.U
    FCMP_8.io.expected.exceptionFlags := 0.U
    when(FCMP_8.io.actual.out > 0.U){   
        c0Min_temp_2 := tmin_3
    } .otherwise{
        c0Min_temp_2 := cmpMin0_3
    }
    io.hmaxout_2 := c0Min_temp_2 

    val FCMP_9 = Module(new ValExec_CompareRecF32_le)
    FCMP_9.io.a := cmpMax0_1
    FCMP_9.io.b := cmpMax0_2
    FCMP_9.io.expected.out := 0.U
    FCMP_9.io.expected.exceptionFlags := 0.U
    when(FCMP_9.io.actual.out > 0.U){   
        c0Max_temp_1 := cmpMax0_1
    } .otherwise{
        c0Max_temp_1 := cmpMax0_2
    }
    io.hmaxout_3 := c0Max_temp_1

    val FCMP_10 = Module(new ValExec_CompareRecF32_le)
    FCMP_10.io.a := cmpMax0_3
    FCMP_10.io.b := hitT_3
    FCMP_10.io.expected.out := 0.U
    FCMP_10.io.expected.exceptionFlags := 0.U
    when(FCMP_10.io.actual.out > 0.U){   
        c0Max_temp_2 := cmpMax0_3
    } .otherwise{
        c0Max_temp_2 := hitT_3
    }
    io.hmaxout_4 := c0Max_temp_2 

    val FCMP_11 = Module(new ValExec_CompareRecF32_le)
    FCMP_11.io.a := cmpMin1_1
    FCMP_11.io.b := cmpMin1_2
    FCMP_11.io.expected.out := 0.U
    FCMP_11.io.expected.exceptionFlags := 0.U
    when(FCMP_11.io.actual.out > 0.U){   
        c1Min_temp_1 := cmpMin1_2
    } .otherwise{
        c1Min_temp_1 := cmpMin1_1
    }
    io.hmaxout_5 := c1Min_temp_1 

    val FCMP_12 = Module(new ValExec_CompareRecF32_le)
    FCMP_12.io.a := cmpMin1_3
    FCMP_12.io.b := tmin_3
    FCMP_12.io.expected.out := 0.U
    FCMP_12.io.expected.exceptionFlags := 0.U
    when(FCMP_12.io.actual.out > 0.U){   
        c1Min_temp_2 := tmin_3
    } .otherwise{
        c1Min_temp_2 := cmpMin1_3
    }
    io.hmaxout_6 := c1Min_temp_2

    val FCMP_13 = Module(new ValExec_CompareRecF32_le)
    FCMP_13.io.a := cmpMax1_1
    FCMP_13.io.b := cmpMax1_2
    FCMP_13.io.expected.out := 0.U
    FCMP_13.io.expected.exceptionFlags := 0.U
    when(FCMP_13.io.actual.out > 0.U){   
        c1Max_temp_1 := cmpMax1_1
    } .otherwise{
        c1Max_temp_1 := cmpMax1_2
    }
    io.hmaxout_7 := c1Max_temp_1

    val FCMP_14 = Module(new ValExec_CompareRecF32_le)
    FCMP_14.io.a := cmpMax1_3
    FCMP_14.io.b := hitT_3
    FCMP_14.io.expected.out := 0.U
    FCMP_14.io.expected.exceptionFlags := 0.U
    when(FCMP_14.io.actual.out > 0.U){   
        c1Max_temp_2 := cmpMax1_3
    } .otherwise{
        c1Max_temp_2 := hitT_3
    }
    io.hmaxout_8 := c1Max_temp_2

    //step(5)
    child_mask_1_5 := child_mask_1_4
    val FCMP_15 = Module(new ValExec_CompareRecF32_le)
    FCMP_15.io.a := c0Min_temp_1
    FCMP_15.io.b := c0Min_temp_2
    FCMP_15.io.expected.out := 0.U
    FCMP_15.io.expected.exceptionFlags := 0.U
    when(FCMP_15.io.actual.out > 0.U){   
        c0Min := c0Min_temp_2
    } .otherwise{
        c0Min := c0Min_temp_1
    }
    io.c0Min_out := c0Min

    val FCMP_16 = Module(new ValExec_CompareRecF32_le)
    FCMP_16.io.a := c0Max_temp_1
    FCMP_16.io.b := c0Max_temp_2
    FCMP_16.io.expected.out := 0.U
    FCMP_16.io.expected.exceptionFlags := 0.U
    when(FCMP_16.io.actual.out > 0.U){   
        c0Max := c0Max_temp_1
    } .otherwise{
        c0Max := c0Max_temp_2
    }
    io.c0Max_out := c0Max

    val FCMP_17 = Module(new ValExec_CompareRecF32_le)
    FCMP_17.io.a := c1Min_temp_1
    FCMP_17.io.b := c1Min_temp_2
    FCMP_17.io.expected.out := 0.U
    FCMP_17.io.expected.exceptionFlags := 0.U
    when(FCMP_17.io.actual.out > 0.U){  
        c1Min := c1Min_temp_2
    } .otherwise{
        c1Min := c1Min_temp_1
    }
    io.c1Min_out := c1Min

    val FCMP_18 = Module(new ValExec_CompareRecF32_le)
    FCMP_18.io.a := c1Max_temp_1    
    FCMP_18.io.b := c1Max_temp_2
    FCMP_18.io.expected.out := 0.U
    FCMP_18.io.expected.exceptionFlags := 0.U
    when(FCMP_18.io.actual.out > 0.U){   
        c1Max := c1Max_temp_1
    } .otherwise{
        c1Max := c1Max_temp_2
    }
    io.c1Max_out := c1Max
    //step(6)
    val FCMP_19 = Module(new ValExec_CompareRecF32_lt)
    FCMP_19.io.a := c0Max
    FCMP_19.io.b := c0Min
    FCMP_19.io.expected.out := 0.U
    FCMP_19.io.expected.exceptionFlags := 0.U
    when(FCMP_19.io.actual.out > 0.U){   
        cmp0 := 0.U
    } .otherwise{
        cmp0 := 1.U
    }
    io.cmp0_out := cmp0
    //step(7)
    io.traverseChild0 := cmp0

    //step(6)
    when(!child_mask_1_5 === 0.U)
    {
    val FCMP_20 = Module(new ValExec_CompareRecF32_lt)
    FCMP_20.io.a := c1Max
    FCMP_20.io.b := c1Min
    FCMP_20.io.expected.out := 0.U
    FCMP_20.io.expected.exceptionFlags := 0.U
    when(FCMP_20.io.actual.out > 0.U){   
        cmp1 := 0.U
    } .otherwise{
        cmp1 := 1.U
    }
    io.cmp1_out := cmp1
    //step(7)
    io.traverseChild1 := cmp1
    }.otherwise
    {   
        io.cmp1_out := cmp1
        io.traverseChild1 := 0.U 
    }
    // }otherwise{
    
    // io.traverseChild1 := 0.U

    // //step(1)
    // oodx_1 := io.oodx
    // oody_1 := io.oody
    // oodz_1 := io.oodz
    // tmin_1 := io.tmin
    // hitT_1 := io.hitT

    // traverseChild1_1 := 0.U
    // traverseChild1_2 := traverseChild1_1
    // traverseChild1_3 := traverseChild1_2
    // traverseChild1_4 := traverseChild1_3
    // traverseChild1_5 := traverseChild1_4
    // io.cmp1_out      := traverseChild1_5
    // io.traverseChild1:= traverseChild1_5

    // val FMUL_1 = Module(new ValExec_MulAddRecF32_mul)
    // FMUL_1.io.a := io.n0xy.x
    // FMUL_1.io.b := io.idirx
    // FMUL_1.io.roundingMode := 0.U
    // FMUL_1.io.detectTininess := 1.U
    // FMUL_1.io.expected.out := 0.U
    // FMUL_1.io.expected.exceptionFlags := 0.U 
    // temp0_1 := fNFromRecFN(8,24,FMUL_1.io.actual.out)
    // io.out_1 := temp0_1

    // val FMUL_2 = Module(new ValExec_MulAddRecF32_mul)
    // FMUL_2.io.a := io.n0xy.y
    // FMUL_2.io.b := io.idirx
    // FMUL_2.io.roundingMode := 0.U
    // FMUL_2.io.detectTininess := 1.U
    // FMUL_2.io.expected.out := 0.U
    // FMUL_2.io.expected.exceptionFlags := 0.U 
    // temp0_2 := fNFromRecFN(8,24,FMUL_2.io.actual.out)
    // io.out_2 := temp0_2

    // val FMUL_3 = Module(new ValExec_MulAddRecF32_mul)
    // FMUL_3.io.a := io.n0xy.z
    // FMUL_3.io.b := io.idiry
    // FMUL_3.io.roundingMode := 0.U
    // FMUL_3.io.detectTininess := 1.U
    // FMUL_3.io.expected.out := 0.U
    // FMUL_3.io.expected.exceptionFlags := 0.U 
    // temp0_3 := fNFromRecFN(8,24,FMUL_3.io.actual.out)
    // io.out_3 := temp0_3
    
    // val FMUL_4 = Module(new ValExec_MulAddRecF32_mul)
    // FMUL_4.io.a := io.n0xy.w
    // FMUL_4.io.b := io.idiry
    // FMUL_4.io.roundingMode := 0.U
    // FMUL_4.io.detectTininess := 1.U
    // FMUL_4.io.expected.out := 0.U
    // FMUL_4.io.expected.exceptionFlags := 0.U 
    // temp0_4 := fNFromRecFN(8,24,FMUL_4.io.actual.out)
    // io.out_4 := temp0_4

    // val FMUL_5 = Module(new ValExec_MulAddRecF32_mul)
    // FMUL_5.io.a := io.nz.x
    // FMUL_5.io.b := io.idirz
    // FMUL_5.io.roundingMode := 0.U
    // FMUL_5.io.detectTininess := 1.U
    // FMUL_5.io.expected.out := 0.U
    // FMUL_5.io.expected.exceptionFlags := 0.U 
    // temp0_5 := fNFromRecFN(8,24,FMUL_5.io.actual.out)
    // io.out_5 := temp0_5

    // val FMUL_6 = Module(new ValExec_MulAddRecF32_mul)
    // FMUL_6.io.a := io.nz.y
    // FMUL_6.io.b := io.idirz
    // FMUL_6.io.roundingMode := 0.U
    // FMUL_6.io.detectTininess := 1.U
    // FMUL_6.io.expected.out := 0.U
    // FMUL_6.io.expected.exceptionFlags := 0.U 
    // temp0_6 := fNFromRecFN(8,24,FMUL_6.io.actual.out)
    // io.out_6 := temp0_6

    // //step(2)
    // tmin_2 := tmin_1
    // hitT_2 := hitT_1
    // val FADD_1 = Module(new ValExec_MulAddRecF32_add)
    // FADD_1.io.a := temp0_1
    // FADD_1.io.b := offsign(oodx_1)
    // FADD_1.io.roundingMode := 0.U
    // FADD_1.io.detectTininess := 1.U
    // FADD_1.io.expected.out := 0.U
    // FADD_1.io.expected.exceptionFlags := 0.U 
    // c0lox := fNFromRecFN(8,24,FADD_1.io.actual.out)
    // io.outt_1 := c0lox

    // val FADD_2 = Module(new ValExec_MulAddRecF32_add)
    // FADD_2.io.a := temp0_2
    // FADD_2.io.b := offsign(oodx_1)
    // FADD_2.io.roundingMode := 0.U
    // FADD_2.io.detectTininess := 1.U
    // FADD_2.io.expected.out := 0.U
    // FADD_2.io.expected.exceptionFlags := 0.U 
    // c0hix := fNFromRecFN(8,24,FADD_2.io.actual.out)
    // io.outt_2 := c0hix

    // val FADD_3 = Module(new ValExec_MulAddRecF32_add)
    // FADD_3.io.a := temp0_3
    // FADD_3.io.b := offsign(oody_1)
    // FADD_3.io.roundingMode := 0.U
    // FADD_3.io.detectTininess := 1.U
    // FADD_3.io.expected.out := 0.U
    // FADD_3.io.expected.exceptionFlags := 0.U 
    // c0loy := fNFromRecFN(8,24,FADD_3.io.actual.out)
    // io.outt_3 := c0loy

    // val FADD_4 = Module(new ValExec_MulAddRecF32_add)
    // FADD_4.io.a := temp0_4
    // FADD_4.io.b := offsign(oody_1)
    // FADD_4.io.roundingMode := 0.U
    // FADD_4.io.detectTininess := 1.U
    // FADD_4.io.expected.out := 0.U
    // FADD_4.io.expected.exceptionFlags := 0.U 
    // c0hiy := fNFromRecFN(8,24,FADD_4.io.actual.out)
    // io.outt_4 := c0hiy

    // val FADD_5 = Module(new ValExec_MulAddRecF32_add)
    // FADD_5.io.a := temp0_5
    // FADD_5.io.b := offsign(oodz_1)
    // FADD_5.io.roundingMode := 0.U
    // FADD_5.io.detectTininess := 1.U
    // FADD_5.io.expected.out := 0.U
    // FADD_5.io.expected.exceptionFlags := 0.U 
    // c0loz := fNFromRecFN(8,24,FADD_5.io.actual.out)
    // io.outt_5 := c0loz

    // val FADD_6 = Module(new ValExec_MulAddRecF32_add)
    // FADD_6.io.a := temp0_6
    // FADD_6.io.b := offsign(oodz_1)
    // FADD_6.io.roundingMode := 0.U
    // FADD_6.io.detectTininess := 1.U
    // FADD_6.io.expected.out := 0.U
    // FADD_6.io.expected.exceptionFlags := 0.U 
    // c0hiz := fNFromRecFN(8,24,FADD_6.io.actual.out)
    // io.outt_6 := c0hiz

    // //step(3)
    // tmin_3 := tmin_2
    // hitT_3 := hitT_2

    // val FCMP_1 = Module(new ValExec_CompareRecF32_le)
    // FCMP_1.io.a := c0lox
    // FCMP_1.io.b := c0hix
    // FCMP_1.io.expected.out := 0.U
    // FCMP_1.io.expected.exceptionFlags := 0.U
    // when(FCMP_1.io.actual.out > 0.U){
    //     cmpMin0_1 := c0lox    
    //     cmpMax0_1 := c0hix
    // } .otherwise{
    //     cmpMin0_1 := c0hix
    //     cmpMax0_1 := c0lox
    // }
    // io.ooutt_1 := cmpMin0_1
    // io.ooutt_2 := cmpMax0_1

    // val FCMP_2 = Module(new ValExec_CompareRecF32_le)
    // FCMP_2.io.a := c0loy
    // FCMP_2.io.b := c0hiy
    // FCMP_2.io.expected.out := 0.U
    // FCMP_2.io.expected.exceptionFlags := 0.U
    // when(FCMP_2.io.actual.out > 0.U){
    //     cmpMin0_2 := c0loy    
    //     cmpMax0_2 := c0hiy
    // } .otherwise{
    //     cmpMin0_2 := c0hiy
    //     cmpMax0_2 := c0loy
    // }
    // io.ooutt_3 := cmpMin0_2
    // io.ooutt_4 := cmpMax0_2

    // val FCMP_3 = Module(new ValExec_CompareRecF32_le)
    // FCMP_3.io.a := c0loz
    // FCMP_3.io.b := c0hiz
    // FCMP_3.io.expected.out := 0.U
    // FCMP_3.io.expected.exceptionFlags := 0.U
    // when(FCMP_3.io.actual.out > 0.U){
    //     cmpMin0_3 := c0loz    
    //     cmpMax0_3 := c0hiz
    // } .otherwise{
    //     cmpMin0_3 := c0hiz
    //     cmpMax0_3 := c0loz
    // }
    // io.ooutt_5 := cmpMin0_3
    // io.ooutt_6 := cmpMax0_3

    // //step(4)
    // val FCMP_7 = Module(new ValExec_CompareRecF32_le)
    // FCMP_7.io.a := cmpMin0_1
    // FCMP_7.io.b := cmpMin0_2
    // FCMP_7.io.expected.out := 0.U
    // FCMP_7.io.expected.exceptionFlags := 0.U
    // when(FCMP_7.io.actual.out > 0.U){   
    //     c0Min_temp_1 := cmpMin0_2
    // } .otherwise{
    //     c0Min_temp_1 := cmpMin0_1
    // }
    // io.hmaxout_1 := c0Min_temp_1 
    
    // val FCMP_8 = Module(new ValExec_CompareRecF32_le)
    // FCMP_8.io.a := cmpMin0_3
    // FCMP_8.io.b := tmin_3
    // FCMP_8.io.expected.out := 0.U
    // FCMP_8.io.expected.exceptionFlags := 0.U
    // when(FCMP_8.io.actual.out > 0.U){   
    //     c0Min_temp_2 := tmin_3
    // } .otherwise{
    //     c0Min_temp_2 := cmpMin0_3
    // }
    // io.hmaxout_2 := c0Min_temp_2

    // val FCMP_9 = Module(new ValExec_CompareRecF32_le)
    // FCMP_9.io.a := cmpMax0_1
    // FCMP_9.io.b := cmpMax0_2
    // FCMP_9.io.expected.out := 0.U
    // FCMP_9.io.expected.exceptionFlags := 0.U
    // when(FCMP_9.io.actual.out > 0.U){   
    //     c0Max_temp_1 := cmpMax0_1
    // } .otherwise{
    //     c0Max_temp_1 := cmpMax0_2
    // }
    // io.hmaxout_3 := c0Max_temp_1

    // val FCMP_10 = Module(new ValExec_CompareRecF32_le)
    // FCMP_10.io.a := cmpMax0_3
    // FCMP_10.io.b := hitT_3
    // FCMP_10.io.expected.out := 0.U
    // FCMP_10.io.expected.exceptionFlags := 0.U
    // when(FCMP_10.io.actual.out > 0.U){   
    //     c0Max_temp_2 := cmpMax0_3
    // } .otherwise{
    //     c0Max_temp_2 := hitT_3
    // }
    // io.hmaxout_4 := c0Max_temp_2 

    // //step(5)
    // val FCMP_15 = Module(new ValExec_CompareRecF32_le)
    // FCMP_15.io.a := c0Min_temp_1
    // FCMP_15.io.b := c0Min_temp_2
    // FCMP_15.io.expected.out := 0.U
    // FCMP_15.io.expected.exceptionFlags := 0.U
    // when(FCMP_15.io.actual.out > 0.U){   
    //     c0Min := c0Min_temp_2
    // } .otherwise{
    //     c0Min := c0Min_temp_1
    // }
    // io.c0Min_out := c0Min

    // val FCMP_16 = Module(new ValExec_CompareRecF32_le)
    // FCMP_16.io.a := c0Max_temp_1
    // FCMP_16.io.b := c0Max_temp_2
    // FCMP_16.io.expected.out := 0.U
    // FCMP_16.io.expected.exceptionFlags := 0.U
    // when(FCMP_16.io.actual.out > 0.U){   
    //     c0Max := c0Max_temp_1
    // } .otherwise{
    //     c0Max := c0Max_temp_2
    // }
    // io.c0Max_out := c0Max

    // //step(6)
    // val FCMP_19 = Module(new ValExec_CompareRecF32_lt)
    // FCMP_19.io.a := c0Max
    // FCMP_19.io.b := c0Min
    // FCMP_19.io.expected.out := 0.U
    // FCMP_19.io.expected.exceptionFlags := 0.U
    // when(FCMP_19.io.actual.out > 0.U){   
    //     cmp0 := 0.U
    // } .otherwise{
    //     cmp0 := 1.U
    // }
    // io.cmp0_out := cmp0
    // //step(7)
    // io.traverseChild0 := cmp0

    // }

}
class TesterSimple (dut:Child_node_traverse_pipeline) extends PeekPokeTester(dut){
// println("~~~~~~~~~~~~~~~~~~~~~~~~~   case 1   ~~~~~~~~~~~~~~~~~~~~~" )
//first
poke(dut.io.n0xy.x,1103764441.S)
poke(dut.io.n0xy.y,1107256619.S)
poke(dut.io.n0xy.z,3208617427L.S)
poke(dut.io.n0xy.w,1093219962.S)

poke(dut.io.idirx,1077118742.S)
poke(dut.io.idiry,1066134505.S)
poke(dut.io.idirz,1084785929.S)

poke(dut.io.nz.x,1084856729.S)
poke(dut.io.nz.y,1085381017.S)
poke(dut.io.nz.z,1028443336.S)
poke(dut.io.nz.w,1084856729.S)

poke(dut.io.n1xy.x,1107256619.S)
poke(dut.io.n1xy.y,1107256619.S)
poke(dut.io.n1xy.z,3208617427L.S)
poke(dut.io.n1xy.w,1093219962.S)

poke(dut.io.oodx,1116095487.S)
poke(dut.io.oody,3235163527L.S)
poke(dut.io.oodz,1057317650.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1090261870.S)

poke(dut.io.child_mask_1,1.S)

step(1)
// println("~~~~~~~~~~~~~~~~~  setp(1)  ~~~~~~~~~~~~~~~~~~" )
// println("temp0_1 is  :" +peek(dut.io.out_1).toString)
// println("temp0_2 is  :" +peek(dut.io.out_2).toString)
// println("temp0_3 is  :" +peek(dut.io.out_3).toString)
// println("temp0_4 is  :" +peek(dut.io.out_4).toString)
// println("temp0_5 is  :" +peek(dut.io.out_5).toString)
// println("temp0_6 is  :" +peek(dut.io.out_6).toString)
// println("temp1_1 is  :" +peek(dut.io.out_7).toString)
// println("temp1_2 is  :" +peek(dut.io.out_8).toString)
// println("temp1_3 is  :" +peek(dut.io.out_9).toString)
// println("temp1_4 is :" +peek(dut.io.out_10).toString)
// println("temp1_5 is :" +peek(dut.io.out_11).toString)
// println("temp1_6 is :" +peek(dut.io.out_12).toString)

// step(1)//second
poke(dut.io.n0xy.x,3256345846L.S)
poke(dut.io.n0xy.y,1110973612.S)
poke(dut.io.n0xy.z,3256073924L.S)
poke(dut.io.n0xy.w,1108594052.S)

poke(dut.io.idirx,1077118742.S)
poke(dut.io.idiry,1066134505.S)
poke(dut.io.idirz,1084785929.S)

poke(dut.io.nz.x,3198336450L.S)
poke(dut.io.nz.y,1097439641.S)
poke(dut.io.nz.z,3203211070L.S)
poke(dut.io.nz.w,1084856729.S)

poke(dut.io.n1xy.x,3223926897L.S)
poke(dut.io.n1xy.y,1111169276.S)
poke(dut.io.n1xy.z,3252967754L.S)
poke(dut.io.n1xy.w,1094234354.S)

poke(dut.io.oodx,1116095487.S)
poke(dut.io.oody,3235163527L.S)
poke(dut.io.oodz,1057317650.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1325400063.S)

poke(dut.io.child_mask_1,1.S)

// step(1)
// println("~~~~~~~~~~~~~~~~~  setp(2)  ~~~~~~~~~~~~~~~~~~" )


// println("temp0_1 is  :" +peek(dut.io.out_1).toString)
// println("temp0_2 is  :" +peek(dut.io.out_2).toString)
// println("temp0_3 is  :" +peek(dut.io.out_3).toString)
// println("temp0_4 is  :" +peek(dut.io.out_4).toString)
// println("temp0_5 is  :" +peek(dut.io.out_5).toString)
// println("temp0_6 is  :" +peek(dut.io.out_6).toString)
// println("temp1_1 is  :" +peek(dut.io.out_7).toString)
// println("temp1_2 is  :" +peek(dut.io.out_8).toString)
// println("temp1_3 is  :" +peek(dut.io.out_9).toString)
// println("temp1_4 is :" +peek(dut.io.out_10).toString)
// println("temp1_5 is :" +peek(dut.io.out_11).toString)
// println("temp1_6 is :" +peek(dut.io.out_12).toString)
step(1)//third
poke(dut.io.n0xy.x,1103726641.S)
poke(dut.io.n0xy.y,1107613634.S)
poke(dut.io.n0xy.z,3208617427L.S)
poke(dut.io.n0xy.w,1093219962.S)

poke(dut.io.idirx,1077118742.S)
poke(dut.io.idiry,1066134505.S)
poke(dut.io.idirz,1084785929.S)

poke(dut.io.nz.x,1028443336.S)
poke(dut.io.nz.y,1044651070.S)
poke(dut.io.nz.z,1028443336.S)
poke(dut.io.nz.w,1085381017.S)

poke(dut.io.n1xy.x,1103764441.S)
poke(dut.io.n1xy.y,1107256619.S)
poke(dut.io.n1xy.z,3208617427L.S)
poke(dut.io.n1xy.w,1093219962.S)

poke(dut.io.oodx,1116095487.S)
poke(dut.io.oody,3235163527L.S)
poke(dut.io.oodz,1057317650.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1090261870.S)

poke(dut.io.child_mask_1,1.S)
// println("first c0lox is   :" +peek(dut.io.outt_1).toString)
// println("first c0hix is   :" +peek(dut.io.outt_2).toString)
// println("first c0loy is   :" +peek(dut.io.outt_3).toString)
// println("first c0hiy is   :" +peek(dut.io.outt_4).toString)
// println("first c0loz is   :" +peek(dut.io.outt_5).toString)
// println("first c0hiz is   :" +peek(dut.io.outt_6).toString)
// println("\n" )
// println("first c1lox is   :" +peek(dut.io.outt_7).toString)
// println("first c1hix is   :" +peek(dut.io.outt_8).toString)
// println("first c1loy is   :" +peek(dut.io.outt_9).toString)
// println("first c1hiy is  :" +peek(dut.io.outt_10).toString)
// println("first c1loz is  :" +peek(dut.io.outt_11).toString)
// println("first c1hiz is  :" +peek(dut.io.outt_12).toString)
step(1)
// println("~~~~~~~~~~~~~~~~~  setp(3)  ~~~~~~~~~~~~~~~~~~" )
// println("fmin between c0lox and c0hix :" +peek(dut.io.ooutt_1).toString)
// println("fmax between c0lox and c0hix :" +peek(dut.io.ooutt_2).toString)
// println("fmin between c0loy and c0hiy :" +peek(dut.io.ooutt_3).toString)
// println("fmax between c0loy and c0hiy :" +peek(dut.io.ooutt_4).toString)
// println("fmin between c0loz and c0hiz :" +peek(dut.io.ooutt_5).toString)
// println("fmax between c0loz and c0hiz :" +peek(dut.io.ooutt_6).toString)
// println("\n" )
// println("fmin between c1lox and c1hix :" +peek(dut.io.ooutt_7).toString)
// println("fmax between c1lox and c1hix :" +peek(dut.io.ooutt_8).toString)
// println("fmin between c1loy and c1hiy :" +peek(dut.io.ooutt_9).toString)
// println("fmax between c1loy and c1hiy :" +peek(dut.io.ooutt_10).toString)
// println("fmin between c1loz and c1hiz :" +peek(dut.io.ooutt_11).toString)
// println("fmax between c1loz and c1hiz :" +peek(dut.io.ooutt_12).toString)

// println("second c0lox is   :" +peek(dut.io.outt_1).toString)
// println("second c0hix is   :" +peek(dut.io.outt_2).toString)
// println("second c0loy is   :" +peek(dut.io.outt_3).toString)
// println("second c0hiy is   :" +peek(dut.io.outt_4).toString)
// println("second c0loz is   :" +peek(dut.io.outt_5).toString)
// println("second c0hiz is   :" +peek(dut.io.outt_6).toString)
// println("\n" )
// println("second c1lox is   :" +peek(dut.io.outt_7).toString)
// println("second c1hix is   :" +peek(dut.io.outt_8).toString)
// println("second c1loy is   :" +peek(dut.io.outt_9).toString)
// println("second c1hiy is  :" +peek(dut.io.outt_10).toString)
// println("second c1loz is  :" +peek(dut.io.outt_11).toString)
// println("second c1hiz is  :" +peek(dut.io.outt_12).toString)
// println("fmin between c0loz and c0hiz :" +peek(dut.io.ooutt_5).toString)
// println("fmax between c0loz and c0hiz :" +peek(dut.io.ooutt_6).toString)
// step(1)//forth
poke(dut.io.n0xy.x,1104687609.S)
poke(dut.io.n0xy.y,1107618823.S)
poke(dut.io.n0xy.z,3216138200L.S)
poke(dut.io.n0xy.w,1081935187.S)

poke(dut.io.idirx,1077118742.S)
poke(dut.io.idiry,1066134505.S)
poke(dut.io.idirz,1084785929.S)

poke(dut.io.nz.x,1036049592.S)
poke(dut.io.nz.y,1085381017.S)
poke(dut.io.nz.z,1028443336.S)
poke(dut.io.nz.w,1085381017.S)

poke(dut.io.n1xy.x,1103726641.S)
poke(dut.io.n1xy.y,1107613634.S)
poke(dut.io.n1xy.z,3208617427L.S)
poke(dut.io.n1xy.w,1093219962.S)

poke(dut.io.oodx,1116095487.S)
poke(dut.io.oody,3235163527L.S)
poke(dut.io.oodz,1057317650.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1090261870.S)

poke(dut.io.child_mask_1,1.S)
step(1)//fifth
poke(dut.io.n0xy.x,1099686057.S)
poke(dut.io.n0xy.y,1103632112.S)
poke(dut.io.n0xy.z,3223598357L.S)
poke(dut.io.n0xy.w,1093219962.S)

poke(dut.io.idirx,3216858060L.S)
poke(dut.io.idiry,1068502928.S)
poke(dut.io.idirz,3238319147L.S)

poke(dut.io.nz.x,1039660316.S)
poke(dut.io.nz.y,1086585653.S)
poke(dut.io.nz.z,1085270663.S)
poke(dut.io.nz.w,1094426768.S)

poke(dut.io.n1xy.x,1100436418.S)
poke(dut.io.n1xy.y,1103446095.S)
poke(dut.io.n1xy.z,3219815598L.S)
poke(dut.io.n1xy.w,1082193063.S)

poke(dut.io.oodx,3256223037L.S)
poke(dut.io.oody,3236079201L.S)
poke(dut.io.oodz,3241546197L.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1075268298.S)

poke(dut.io.child_mask_1,1.S)
step(1)
// println("~~~~~~~~~~~~~~~~~  setp(4)  ~~~~~~~~~~~~~~~~~~" )
// println("fmax between cmpMin0_1 and cmpMin0_2  :" +peek(dut.io.hmaxout_1).toString)
// println("fmax between cmpMin0_3 and    tmin    :" +peek(dut.io.hmaxout_2).toString)
// println("fmin between cmpMax0_1 and cmpMax0_2  :" +peek(dut.io.hmaxout_3).toString)
// println("fmin between cmpMax0_3 and    tmin    :" +peek(dut.io.hmaxout_4).toString)
// println("fmax between cmpMin1_1 and cmpMin1_2  :" +peek(dut.io.hmaxout_5).toString)
// println("fmax between cmpMin1_3 and    tmin    :" +peek(dut.io.hmaxout_6).toString)
// println("fmin between cmpMax1_1 and cmpMax1_2  :" +peek(dut.io.hmaxout_7).toString)
// println("fmax between cmpMin1_3 and    tmin    :" +peek(dut.io.hmaxout_8).toString)
// println("~~~~~~~~~~~~~~~~~  setp(2)  ~~~~~~~~~~~~~~~~~~" )
//fifth
println("~~~~~~~~~~~~~~~~~  setp(5)  ~~~~~~~~~~~~~~~~~~" )
println("c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("c1max  is   :" +peek(dut.io.c1Max_out).toString)

// println("fmin between c0lox and c0hix :" +peek(dut.io.ooutt_1).toString)
// println("fmax between c0lox and c0hix :" +peek(dut.io.ooutt_2).toString)
// println("fmin between c0loy and c0hiy :" +peek(dut.io.ooutt_3).toString)
// println("fmax between c0loy and c0hiy :" +peek(dut.io.ooutt_4).toString)
// println("fmin between c0loz and c0hiz :" +peek(dut.io.ooutt_5).toString)
// println("fmax between c0loz and c0hiz :" +peek(dut.io.ooutt_6).toString)
// println("\n" )
// println("fmin between c1lox and c1hix :" +peek(dut.io.ooutt_7).toString)
// println("fmax between c1lox and c1hix :" +peek(dut.io.ooutt_8).toString)
// println("fmin between c1loy and c1hiy :" +peek(dut.io.ooutt_9).toString)
// println("fmax between c1loy and c1hiy :" +peek(dut.io.ooutt_10).toString)
// println("fmin between c1loz and c1hiz :" +peek(dut.io.ooutt_11).toString)
// println("fmax between c1loz and c1hiz :" +peek(dut.io.ooutt_12).toString)

// sixth
poke(dut.io.n0xy.x,1104852917.S)
poke(dut.io.n0xy.y,1106612794.S)
poke(dut.io.n0xy.z,3226462270L.S)
poke(dut.io.n0xy.w,3226462060L.S)

poke(dut.io.idirx,1072183925.S)
poke(dut.io.idiry,3248683761L.S)
poke(dut.io.idirz,1067035685.S)

poke(dut.io.nz.x,0.S)
poke(dut.io.nz.y,1075210627.S)
poke(dut.io.nz.z,0.S)
poke(dut.io.nz.w,0.S)

poke(dut.io.n1xy.x,1105414533.S)
poke(dut.io.n1xy.y,1106537769.S)
poke(dut.io.n1xy.z,3226462060L.S)
poke(dut.io.n1xy.w,3213243711L.S)

poke(dut.io.oodx,1111169336.S)
poke(dut.io.oody,1109513374.S)
poke(dut.io.oodz,3187007600L.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1087967223.S)

poke(dut.io.child_mask_1,1.S)

step(1)

// println("\n" )
// println("forth c0lox is   :" +peek(dut.io.outt_1).toString)
// println("forth c0hix is   :" +peek(dut.io.outt_2).toString)
// println("forth c0loy is   :" +peek(dut.io.outt_3).toString)
// println("forth c0hiy is   :" +peek(dut.io.outt_4).toString)
// println("forth c0loz is   :" +peek(dut.io.outt_5).toString)
// println("forth c0hiz is   :" +peek(dut.io.outt_6).toString)
// println("\n" )
// println("forth c1lox is   :" +peek(dut.io.outt_7).toString)
// println("forth c1hix is   :" +peek(dut.io.outt_8).toString)
// println("forth c1loy is   :" +peek(dut.io.outt_9).toString)
// println("forth c1hiy is  :" +peek(dut.io.outt_10).toString)
// println("forth c1loz is  :" +peek(dut.io.outt_11).toString)
// println("forth c1hiz is  :" +peek(dut.io.outt_12).toString)
// println("\n" )
// println("fmax between cmpMin0_1 and cmpMin0_2  :" +peek(dut.io.hmaxout_1).toString)
// println("fmax between cmpMin0_3 and    tmin    :" +peek(dut.io.hmaxout_2).toString)
// println("fmin between cmpMax0_1 and cmpMax0_2  :" +peek(dut.io.hmaxout_3).toString)
// println("fmin between cmpMax0_3 and    hitT    :" +peek(dut.io.hmaxout_4).toString)
// println("fmax between cmpMin1_1 and cmpMin1_2  :" +peek(dut.io.hmaxout_5).toString)
// println("fmax between cmpMin1_3 and    tmin    :" +peek(dut.io.hmaxout_6).toString)
// println("fmin between cmpMax1_1 and cmpMax1_2  :" +peek(dut.io.hmaxout_7).toString)
// println("fmax between cmpMin1_3 and    hitT    :" +peek(dut.io.hmaxout_8).toString)
// step(1)
//seventh
poke(dut.io.n0xy.x,3245201476L.S)
poke(dut.io.n0xy.y,3240694487L.S)
poke(dut.io.n0xy.z,3232813474L.S)
poke(dut.io.n0xy.w,1074777229.S)

poke(dut.io.idirx,1015490256.S)
poke(dut.io.idiry,1018003872.S)
poke(dut.io.idirz,3185290420L.S)

poke(dut.io.nz.x,1103731201.S)
poke(dut.io.nz.y,1104514750.S)
poke(dut.io.nz.z,1102879182.S)
poke(dut.io.nz.w,1104299425.S)

poke(dut.io.n1xy.x,3241868053L.S)
poke(dut.io.n1xy.y,3234427463L.S)
poke(dut.io.n1xy.z,3232102308L.S)
poke(dut.io.n1xy.w,1077083090.S)

poke(dut.io.oodx,3225994454L.S)
poke(dut.io.oody,3225137037L.S)
poke(dut.io.oodz,3232672658L.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1077496409.S)

poke(dut.io.child_mask_1,1.S)


println("~~~~~~~~~~~~~~~~~  setp(6)  ~~~~~~~~~~~~~~~~~~" )
println("cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("cmp1  is   :" +peek(dut.io.cmp1_out).toString)
// println("second c0min  is   :" +peek(dut.io.c0Min_out).toString)
// println("second c0max  is   :" +peek(dut.io.c0Max_out).toString)
// println("second c1min  is   :" +peek(dut.io.c1Min_out).toString)
// println("second c1max  is   :" +peek(dut.io.c1Max_out).toString)
// println("fmin between c0lox and c0hix :" +peek(dut.io.ooutt_1).toString)
// println("fmax between c0lox and c0hix :" +peek(dut.io.ooutt_2).toString)
// println("fmin between c0loy and c0hiy :" +peek(dut.io.ooutt_3).toString)
// println("fmax between c0loy and c0hiy :" +peek(dut.io.ooutt_4).toString)
// println("fmin between c0loz and c0hiz :" +peek(dut.io.ooutt_5).toString)
// println("fmax between c0loz and c0hiz :" +peek(dut.io.ooutt_6).toString)


println(" traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println(" traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)

println("second c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("second c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("second c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("second c1max  is   :" +peek(dut.io.c1Max_out).toString)
step(1)

//seventh
poke(dut.io.n0xy.x,3245510701L.S)
poke(dut.io.n0xy.y,3241082775L.S)
poke(dut.io.n0xy.z,3231344712L.S)
poke(dut.io.n0xy.w,3219979763L.S)

poke(dut.io.idirx,1015490256.S)
poke(dut.io.idiry,1018003872.S)
poke(dut.io.idirz,3185290420L.S)

poke(dut.io.nz.x,1101220073.S)
poke(dut.io.nz.y,1101783524.S)
poke(dut.io.nz.z,1103750182.S)
poke(dut.io.nz.w,1104039483.S)

poke(dut.io.n1xy.x,3247198962L.S)
poke(dut.io.n1xy.y,3243478771L.S)
poke(dut.io.n1xy.z,3231238701L.S)
poke(dut.io.n1xy.w,3222303701L.S)

poke(dut.io.oodx,3225994454L.S)
poke(dut.io.oody,3225137037L.S)
poke(dut.io.oodz,3232672658L.S)

poke(dut.io.tmin,0.S)
poke(dut.io.hitT,1077496409.S)

poke(dut.io.child_mask_1,0.S)

println("~~~~~~~~~~~~~~~~~  setp(7)  ~~~~~~~~~~~~~~~~~~" )
println("second cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("second cmp1  is   :" +peek(dut.io.cmp1_out).toString)
// println("fmax between cmpMin0_1 and cmpMin0_2  :" +peek(dut.io.hmaxout_1).toString)
// println("fmax between cmpMin0_3 and    tmin    :" +peek(dut.io.hmaxout_2).toString)
// step(1)
// println("~~~~~~~~~~~~~~~~~  setp(8)  ~~~~~~~~~~~~~~~~~~" )
println("second  traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println("second  traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)
println("third c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("third c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("third c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("third c1max  is   :" +peek(dut.io.c1Max_out).toString)

step(1)
println("~~~~~~~~~~~~~~~~~  setp(8)  ~~~~~~~~~~~~~~~~~~" )
println("third cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("third cmp1  is   :" +peek(dut.io.cmp1_out).toString)

println("third  traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println("third  traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)
println("forth c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("forth c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("forth c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("forth c1max  is   :" +peek(dut.io.c1Max_out).toString)

step(1)
println("~~~~~~~~~~~~~~~~~  setp(9)  ~~~~~~~~~~~~~~~~~~" )
println("forth cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("forth cmp1  is   :" +peek(dut.io.cmp1_out).toString)

println("forth  traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println("forth  traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)

println("fifth c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("fifth c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("fifth c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("fifth c1max  is   :" +peek(dut.io.c1Max_out).toString)
step(1)
println("~~~~~~~~~~~~~~~~~  setp(10)  ~~~~~~~~~~~~~~~~~~" )
println("fifth cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("fifth cmp1  is   :" +peek(dut.io.cmp1_out).toString)

println("fifth  traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println("fifth  traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)


println("sixth c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("sixth c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("sixth c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("sixth c1max  is   :" +peek(dut.io.c1Max_out).toString)

step(1)
println("~~~~~~~~~~~~~~~~~  setp(11)  ~~~~~~~~~~~~~~~~~~" )
println("sixth cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("sixth cmp1  is   :" +peek(dut.io.cmp1_out).toString)

println("sixth  traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println("sixth  traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)

println("seventh c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("seventh c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("seventh c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("seventh c1max  is   :" +peek(dut.io.c1Max_out).toString)

step(1)
println("~~~~~~~~~~~~~~~~~  setp(12)  ~~~~~~~~~~~~~~~~~~" )
println("seventh cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("seventh cmp1  is   :" +peek(dut.io.cmp1_out).toString)

println("seventh  traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println("seventh  traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)

println("eighth c0min  is   :" +peek(dut.io.c0Min_out).toString)
println("eighth c0max  is   :" +peek(dut.io.c0Max_out).toString)
println("eighth c1min  is   :" +peek(dut.io.c1Min_out).toString)
println("eighth c1max  is   :" +peek(dut.io.c1Max_out).toString)

step(1)
println("~~~~~~~~~~~~~~~~~  setp(13)  ~~~~~~~~~~~~~~~~~~" )
println("eighth cmp0  is   :" +peek(dut.io.cmp0_out).toString)
println("eighth cmp1  is   :" +peek(dut.io.cmp1_out).toString)

println("eighth  traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
println("eighth  traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)
}
// object pipelineSimple extends App{
//     chisel3. iotesters.Driver(()=> new Child_node_traverse_pipeline()){ c =>
//         new TesterSimple(c)
//     }
// }
// object pipelineSimple extends App {
//   chisel3.iotesters.Driver.execute(args, () => new Child_node_traverse_pipeline())(c => new TesterSimple(c))
// }