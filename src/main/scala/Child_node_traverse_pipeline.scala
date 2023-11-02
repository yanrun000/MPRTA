// import hardfloat._
// import Chisel._
// import chisel3 . iotesters ._

// class Child_node_traverse_pipeline extends Module{
//     val io = IO(new  Bundle{
//         val n0xy = new Float().asInput 
//         // Bundle{
//         //     val x = Input(Bits(32.W))
//         //     val y = Input(Bits(32.W))
//         //     val z = Input(Bits(32.W))
//         //     val w = Input(Bits(32.W))  
//         // }

//         val n1xy = new Bundle{
//             val x = Input(Bits(32.W))
//             val y = Input(Bits(32.W))
//             val z = Input(Bits(32.W))
//             val w = Input(Bits(32.W))  
//         }

//         val nz = new Bundle{
//             val x = Input(Bits(32.W))
//             val y = Input(Bits(32.W))
//             val z = Input(Bits(32.W))
//             val w = Input(Bits(32.W)) 
//         }    

//         val idirx = Input(Bits(32.W))
//         val idiry = Input(Bits(32.W))
//         val idirz = Input(Bits(32.W))
//         val oodx = Input(Bits(32.W))
//         val oody = Input(Bits(32.W))
//         val oodz = Input(Bits(32.W))
//         val tmin = Input(Bits(32.W))
//         val hitT = Input(Bits(32.W))
//         // val child_mask_0 = Input(Bits(8.W))
//         // val child_mask_1 = Input(Bits(8.W))
//         val traverseChild0 = Output(Bool())
//         val traverseChild1 = Output(Bool())

//         //以下均是打印的观测值
//         val out_1 = Output(Bits(32.W)) //作为一个观测的输出值
//         val out_2 = Output(Bits(32.W))
//         val out_3 = Output(Bits(32.W))
//         val out_4 = Output(Bits(32.W))
//         val out_5 = Output(Bits(32.W))
//         val out_6 = Output(Bits(32.W))
//         val out_7 = Output(Bits(32.W))
//         val out_8 = Output(Bits(32.W))
//         val out_9 = Output(Bits(32.W))
//         val out_10 = Output(Bits(32.W))
//         val out_11 = Output(Bits(32.W))
//         val out_12 = Output(Bits(32.W))

//         val outt_1 = Output(Bits(32.W)) //第二组观测输出值
//         val outt_2 = Output(Bits(32.W))
//         val outt_3 = Output(Bits(32.W))
//         val outt_4 = Output(Bits(32.W))
//         val outt_5 = Output(Bits(32.W))
//         val outt_6 = Output(Bits(32.W))
//         val outt_7 = Output(Bits(32.W))
//         val outt_8 = Output(Bits(32.W))
//         val outt_9 = Output(Bits(32.W))
//         val outt_10 = Output(Bits(32.W))
//         val outt_11 = Output(Bits(32.W))
//         val outt_12 = Output(Bits(32.W))

//         val ooutt_1 = Output(Bits(32.W)) //第三组观测输出值
//         val ooutt_2 = Output(Bits(32.W))
//         val ooutt_3 = Output(Bits(32.W))
//         val ooutt_4 = Output(Bits(32.W))
//         val ooutt_5 = Output(Bits(32.W))
//         val ooutt_6 = Output(Bits(32.W))
//         val ooutt_7 = Output(Bits(32.W))
//         val ooutt_8 = Output(Bits(32.W))
//         val ooutt_9 = Output(Bits(32.W))
//         val ooutt_10 = Output(Bits(32.W))
//         val ooutt_11 = Output(Bits(32.W))
//         val ooutt_12 = Output(Bits(32.W))

//         val hmaxout_1 = Output(Bits(32.W))
//         val hmaxout_2 = Output(Bits(32.W))
//         val hmaxout_3 = Output(Bits(32.W))
//         val hmaxout_4 = Output(Bits(32.W))
//         val hmaxout_5 = Output(Bits(32.W))
//         val hmaxout_6 = Output(Bits(32.W))
//         val hmaxout_7 = Output(Bits(32.W))
//         val hmaxout_8 = Output(Bits(32.W))
            
//         val c0Min_out = Output(Bits(32.W))
//         val c0Max_out = Output(Bits(32.W))
//         val c1Min_out = Output(Bits(32.W))
//         val c1Max_out = Output(Bits(32.W))    

//         val cmp0_out = Output(Bool())
//         val cmp1_out = Output(Bool())
//     })
//     val temp0_1 = Reg(Bits(32.W))
//     val temp0_2 = Reg(Bits(32.W))
//     val temp0_3 = Reg(Bits(32.W))
//     val temp0_4 = Reg(Bits(32.W))    
//     val temp0_5 = Reg(Bits(32.W))
//     val temp0_6 = Reg(Bits(32.W))
//     val temp1_1 = Reg(Bits(32.W))
//     val temp1_2 = Reg(Bits(32.W))
//     val temp1_3 = Reg(Bits(32.W))
//     val temp1_4 = Reg(Bits(32.W))    
//     val temp1_5 = Reg(Bits(32.W))
//     val temp1_6 = Reg(Bits(32.W))

//     val c0lox = Reg(Bits(32.W))
//     val c0hix = Reg(Bits(32.W))
//     val c0loy = Reg(Bits(32.W))
//     val c0hiy = Reg(Bits(32.W))
//     val c0loz = Reg(Bits(32.W))
//     val c0hiz = Reg(Bits(32.W))
//     val c1lox = Reg(Bits(32.W))
//     val c1hix = Reg(Bits(32.W))
//     val c1loy = Reg(Bits(32.W))
//     val c1hiy = Reg(Bits(32.W))
//     val c1loz = Reg(Bits(32.W))
//     val c1hiz = Reg(Bits(32.W))

//     val cmpMin0_1 = Reg(Bits(32.W))
//     val cmpMin0_2 = Reg(Bits(32.W))
//     val cmpMin0_3 = Reg(Bits(32.W))
//     val cmpMax0_1 = Reg(Bits(32.W))
//     val cmpMax0_2 = Reg(Bits(32.W))
//     val cmpMax0_3 = Reg(Bits(32.W))
//     val cmpMin1_1 = Reg(Bits(32.W))
//     val cmpMin1_2 = Reg(Bits(32.W))
//     val cmpMin1_3 = Reg(Bits(32.W))
//     val cmpMax1_1 = Reg(Bits(32.W))
//     val cmpMax1_2 = Reg(Bits(32.W))
//     val cmpMax1_3 = Reg(Bits(32.W))

//     val c0Min = Reg(Bits(32.W))
//     val c0Max = Reg(Bits(32.W))
//     val c1Min = Reg(Bits(32.W))
//     val c1Max = Reg(Bits(32.W))

//     val c0Min_temp_1 = Reg(Bits(32.W))
//     val c0Min_temp_2 = Reg(Bits(32.W))
//     val c0Max_temp_1 = Reg(Bits(32.W))
//     val c0Max_temp_2 = Reg(Bits(32.W))
//     val c1Min_temp_1 = Reg(Bits(32.W))
//     val c1Min_temp_2 = Reg(Bits(32.W))
//     val c1Max_temp_1 = Reg(Bits(32.W))
//     val c1Max_temp_2 = Reg(Bits(32.W))

//     val cmp0 = Reg(Bits(1.W))
//     val cmp1 = Reg(Bits(1.W))


//     val FMUL_1 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_1.io.a := io.n0xy.x
//     FMUL_1.io.b := io.idirx
//     FMUL_1.io.roundingMode := 0.U
//     FMUL_1.io.detectTininess := 1.U
//     FMUL_1.io.expected.out := 0.U
//     FMUL_1.io.expected.exceptionFlags := 0.U 
//     temp0_1 := fNFromRecFN(8,24,FMUL_1.io.actual.out)
//     io.out_1 := temp0_1

//     val FMUL_2 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_2.io.a := io.n0xy.y
//     FMUL_2.io.b := io.idirx
//     FMUL_2.io.roundingMode := 0.U
//     FMUL_2.io.detectTininess := 1.U
//     FMUL_2.io.expected.out := 0.U
//     FMUL_2.io.expected.exceptionFlags := 0.U 
//     temp0_2 := fNFromRecFN(8,24,FMUL_2.io.actual.out)
//     io.out_2 := temp0_2

//     val FMUL_3 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_3.io.a := io.n0xy.z
//     FMUL_3.io.b := io.idiry
//     FMUL_3.io.roundingMode := 0.U
//     FMUL_3.io.detectTininess := 1.U
//     FMUL_3.io.expected.out := 0.U
//     FMUL_3.io.expected.exceptionFlags := 0.U 
//     temp0_3 := fNFromRecFN(8,24,FMUL_3.io.actual.out)
//     io.out_3 := temp0_3
    
//     val FMUL_4 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_4.io.a := io.n0xy.w
//     FMUL_4.io.b := io.idiry
//     FMUL_4.io.roundingMode := 0.U
//     FMUL_4.io.detectTininess := 1.U
//     FMUL_4.io.expected.out := 0.U
//     FMUL_4.io.expected.exceptionFlags := 0.U 
//     temp0_4 := fNFromRecFN(8,24,FMUL_4.io.actual.out)
//     io.out_4 := temp0_4

//     val FMUL_5 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_5.io.a := io.nz.x
//     FMUL_5.io.b := io.idirz
//     FMUL_5.io.roundingMode := 0.U
//     FMUL_5.io.detectTininess := 1.U
//     FMUL_5.io.expected.out := 0.U
//     FMUL_5.io.expected.exceptionFlags := 0.U 
//     temp0_5 := fNFromRecFN(8,24,FMUL_5.io.actual.out)
//     io.out_5 := temp0_5

//     val FMUL_6 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_6.io.a := io.nz.y
//     FMUL_6.io.b := io.idirz
//     FMUL_6.io.roundingMode := 0.U
//     FMUL_6.io.detectTininess := 1.U
//     FMUL_6.io.expected.out := 0.U
//     FMUL_6.io.expected.exceptionFlags := 0.U 
//     temp0_6 := fNFromRecFN(8,24,FMUL_6.io.actual.out)
//     io.out_6 := temp0_6

//     val FMUL_7 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_7.io.a := io.n1xy.x
//     FMUL_7.io.b := io.idirx
//     FMUL_7.io.roundingMode := 0.U
//     FMUL_7.io.detectTininess := 1.U
//     FMUL_7.io.expected.out := 0.U
//     FMUL_7.io.expected.exceptionFlags := 0.U 
//     temp1_1 := fNFromRecFN(8,24,FMUL_7.io.actual.out)
//     io.out_7 := temp1_1

//     val FMUL_8 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_8.io.a := io.n1xy.y
//     FMUL_8.io.b := io.idirx
//     FMUL_8.io.roundingMode := 0.U
//     FMUL_8.io.detectTininess := 1.U
//     FMUL_8.io.expected.out := 0.U
//     FMUL_8.io.expected.exceptionFlags := 0.U 
//     temp1_2 := fNFromRecFN(8,24,FMUL_8.io.actual.out)
//     io.out_8 := temp1_2

//     val FMUL_9 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_9.io.a := io.n1xy.z
//     FMUL_9.io.b := io.idiry
//     FMUL_9.io.roundingMode := 0.U
//     FMUL_9.io.detectTininess := 1.U
//     FMUL_9.io.expected.out := 0.U
//     FMUL_9.io.expected.exceptionFlags := 0.U
//     temp1_3 := fNFromRecFN(8,24,FMUL_9.io.actual.out)
//     io.out_9 := temp1_3

//     val FMUL_10 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_10.io.a := io.n1xy.w
//     FMUL_10.io.b := io.idiry
//     FMUL_10.io.roundingMode := 0.U
//     FMUL_10.io.detectTininess := 1.U
//     FMUL_10.io.expected.out := 0.U
//     FMUL_10.io.expected.exceptionFlags := 0.U 
//     temp1_4 := fNFromRecFN(8,24,FMUL_10.io.actual.out)
//     io.out_10 := temp1_4   

//     val FMUL_11 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_11.io.a := io.nz.z
//     FMUL_11.io.b := io.idirz
//     FMUL_11.io.roundingMode := 0.U
//     FMUL_11.io.detectTininess := 1.U
//     FMUL_11.io.expected.out := 0.U
//     FMUL_11.io.expected.exceptionFlags := 0.U 
//     temp1_5 := fNFromRecFN(8,24,FMUL_11.io.actual.out)
//     io.out_11 := temp1_5    

//     val FMUL_12 = Module(new ValExec_MulAddRecF32_mul)
//     FMUL_12.io.a := io.nz.w
//     FMUL_12.io.b := io.idirz
//     FMUL_12.io.roundingMode := 0.U
//     FMUL_12.io.detectTininess := 1.U
//     FMUL_12.io.expected.out := 0.U
//     FMUL_12.io.expected.exceptionFlags := 0.U 
//     temp1_6 := fNFromRecFN(8,24,FMUL_12.io.actual.out)
//     io.out_12 := temp1_6   

//     val FADD_1 = Module(new ValExec_MulAddRecF32_add)
//     FADD_1.io.a := temp0_1
//     FADD_1.io.b := offsign(io.oodx)
//     FADD_1.io.roundingMode := 0.U
//     FADD_1.io.detectTininess := 1.U
//     FADD_1.io.expected.out := 0.U
//     FADD_1.io.expected.exceptionFlags := 0.U 
//     c0lox := fNFromRecFN(8,24,FADD_1.io.actual.out)
//     io.outt_1 := c0lox

//     val FADD_2 = Module(new ValExec_MulAddRecF32_add)
//     FADD_2.io.a := temp0_2
//     FADD_2.io.b := offsign(io.oodx)
//     FADD_2.io.roundingMode := 0.U
//     FADD_2.io.detectTininess := 1.U
//     FADD_2.io.expected.out := 0.U
//     FADD_2.io.expected.exceptionFlags := 0.U 
//     c0hix := fNFromRecFN(8,24,FADD_2.io.actual.out)
//     io.outt_2 := c0hix

//     val FADD_3 = Module(new ValExec_MulAddRecF32_add)
//     FADD_3.io.a := temp0_3
//     FADD_3.io.b := offsign(io.oody)
//     FADD_3.io.roundingMode := 0.U
//     FADD_3.io.detectTininess := 1.U
//     FADD_3.io.expected.out := 0.U
//     FADD_3.io.expected.exceptionFlags := 0.U 
//     c0loy := fNFromRecFN(8,24,FADD_3.io.actual.out)
//     io.outt_3 := c0loy

//     val FADD_4 = Module(new ValExec_MulAddRecF32_add)
//     FADD_4.io.a := temp0_4
//     FADD_4.io.b := offsign(io.oody)
//     FADD_4.io.roundingMode := 0.U
//     FADD_4.io.detectTininess := 1.U
//     FADD_4.io.expected.out := 0.U
//     FADD_4.io.expected.exceptionFlags := 0.U 
//     c0hiy := fNFromRecFN(8,24,FADD_4.io.actual.out)
//     io.outt_4 := c0hiy

//     val FADD_5 = Module(new ValExec_MulAddRecF32_add)
//     FADD_5.io.a := temp0_5
//     FADD_5.io.b := offsign(io.oodz)
//     FADD_5.io.roundingMode := 0.U
//     FADD_5.io.detectTininess := 1.U
//     FADD_5.io.expected.out := 0.U
//     FADD_5.io.expected.exceptionFlags := 0.U 
//     c0loz := fNFromRecFN(8,24,FADD_5.io.actual.out)
//     io.outt_5 := c0loz

//     val FADD_6 = Module(new ValExec_MulAddRecF32_add)
//     FADD_6.io.a := temp0_6
//     FADD_6.io.b := offsign(io.oodz)
//     FADD_6.io.roundingMode := 0.U
//     FADD_6.io.detectTininess := 1.U
//     FADD_6.io.expected.out := 0.U
//     FADD_6.io.expected.exceptionFlags := 0.U 
//     c0hiz := fNFromRecFN(8,24,FADD_6.io.actual.out)
//     io.outt_6 := c0hiz
    
//     val FADD_7 = Module(new ValExec_MulAddRecF32_add)
//     FADD_7.io.a := temp1_1
//     FADD_7.io.b := offsign(io.oodx)
//     FADD_7.io.roundingMode := 0.U
//     FADD_7.io.detectTininess := 1.U
//     FADD_7.io.expected.out := 0.U
//     FADD_7.io.expected.exceptionFlags := 0.U 
//     c1lox := fNFromRecFN(8,24,FADD_7.io.actual.out)
//     io.outt_7 := c1lox

//     val FADD_8 = Module(new ValExec_MulAddRecF32_add)
//     FADD_8.io.a := temp1_2
//     FADD_8.io.b := offsign(io.oodx)
//     FADD_8.io.roundingMode := 0.U
//     FADD_8.io.detectTininess := 1.U
//     FADD_8.io.expected.out := 0.U
//     FADD_8.io.expected.exceptionFlags := 0.U 
//     c1hix := fNFromRecFN(8,24,FADD_8.io.actual.out)
//     io.outt_8 := c1hix

//     val FADD_9 = Module(new ValExec_MulAddRecF32_add)
//     FADD_9.io.a := temp1_3
//     FADD_9.io.b := offsign(io.oody)
//     FADD_9.io.roundingMode := 0.U
//     FADD_9.io.detectTininess := 1.U
//     FADD_9.io.expected.out := 0.U
//     FADD_9.io.expected.exceptionFlags := 0.U 
//     c1loy := fNFromRecFN(8,24,FADD_9.io.actual.out)
//     io.outt_9 := c1loy

//     val FADD_10 = Module(new ValExec_MulAddRecF32_add)
//     FADD_10.io.a := temp1_4
//     FADD_10.io.b := offsign(io.oody)
//     FADD_10.io.roundingMode := 0.U
//     FADD_10.io.detectTininess := 1.U
//     FADD_10.io.expected.out := 0.U
//     FADD_10.io.expected.exceptionFlags := 0.U 
//     c1hiy := fNFromRecFN(8,24,FADD_10.io.actual.out)
//     io.outt_10 := c1hiy

//     val FADD_11 = Module(new ValExec_MulAddRecF32_add)
//     FADD_11.io.a := temp1_5
//     FADD_11.io.b := offsign(io.oodz)
//     FADD_11.io.roundingMode := 0.U
//     FADD_11.io.detectTininess := 1.U
//     FADD_11.io.expected.out := 0.U
//     FADD_11.io.expected.exceptionFlags := 0.U 
//     c1loz := fNFromRecFN(8,24,FADD_11.io.actual.out)
//     io.outt_11 := c1loz

//     val FADD_12 = Module(new ValExec_MulAddRecF32_add)
//     FADD_12.io.a := temp1_6
//     FADD_12.io.b := offsign(io.oodz)
//     FADD_12.io.roundingMode := 0.U
//     FADD_12.io.detectTininess := 1.U
//     FADD_12.io.expected.out := 0.U
//     FADD_12.io.expected.exceptionFlags := 0.U 
//     c1hiz := fNFromRecFN(8,24,FADD_12.io.actual.out)
//     io.outt_12 := c1hiz

//     val FCMP_1 = Module(new ValExec_CompareRecF32_le)
//     FCMP_1.io.a := c0lox
//     FCMP_1.io.b := c0hix
//     FCMP_1.io.expected.out := 0.U
//     FCMP_1.io.expected.exceptionFlags := 0.U
//     when(FCMP_1.io.actual.out > 0.U){
//         cmpMin0_1 := c0lox    
//         cmpMax0_1 := c0hix
//     } .otherwise{
//         cmpMin0_1 := c0hix
//         cmpMax0_1 := c0lox
//     }
//     io.ooutt_1 := cmpMin0_1
//     io.ooutt_2 := cmpMax0_1

//     val FCMP_2 = Module(new ValExec_CompareRecF32_le)
//     FCMP_2.io.a := c0loy
//     FCMP_2.io.b := c0hiy
//     FCMP_2.io.expected.out := 0.U
//     FCMP_2.io.expected.exceptionFlags := 0.U
//     when(FCMP_2.io.actual.out > 0.U){
//         cmpMin0_2 := c0loy    
//         cmpMax0_2 := c0hiy
//     } .otherwise{
//         cmpMin0_2 := c0hiy
//         cmpMax0_2 := c0loy
//     }
//     io.ooutt_3 := cmpMin0_2
//     io.ooutt_4 := cmpMax0_2

//     val FCMP_3 = Module(new ValExec_CompareRecF32_le)
//     FCMP_3.io.a := c0loz
//     FCMP_3.io.b := c0hiz
//     FCMP_3.io.expected.out := 0.U
//     FCMP_3.io.expected.exceptionFlags := 0.U
//     when(FCMP_3.io.actual.out > 0.U){
//         cmpMin0_3 := c0loz    
//         cmpMax0_3 := c0hiz
//     } .otherwise{
//         cmpMin0_3 := c0hiz
//         cmpMax0_3 := c0loz
//     }
//     io.ooutt_5 := cmpMin0_3
//     io.ooutt_6 := cmpMax0_3

//     val FCMP_4 = Module(new ValExec_CompareRecF32_le)
//     FCMP_4.io.a := c1lox
//     FCMP_4.io.b := c1hix
//     FCMP_4.io.expected.out := 0.U
//     FCMP_4.io.expected.exceptionFlags := 0.U
//     when(FCMP_4.io.actual.out > 0.U){
//         cmpMin1_1 := c1lox    
//         cmpMax1_1 := c1hix
//     } .otherwise{
//         cmpMin1_1 := c1hix
//         cmpMax1_1 := c1lox
//     }
//     io.ooutt_7 := cmpMin1_1
//     io.ooutt_8 := cmpMax1_1

//     val FCMP_5 = Module(new ValExec_CompareRecF32_le)
//     FCMP_5.io.a := c1loy
//     FCMP_5.io.b := c1hiy
//     FCMP_5.io.expected.out := 0.U
//     FCMP_5.io.expected.exceptionFlags := 0.U
//     when(FCMP_5.io.actual.out > 0.U){
//         cmpMin1_2 := c1loy    
//         cmpMax1_2 := c1hiy
//     } .otherwise{
//         cmpMin1_2 := c1hiy
//         cmpMax1_2 := c1loy
//     }
//     io.ooutt_9 := cmpMin1_2
//     io.ooutt_10 := cmpMax1_2    

//     val FCMP_6 = Module(new ValExec_CompareRecF32_le)
//     FCMP_6.io.a := c1loz
//     FCMP_6.io.b := c1hiz
//     FCMP_6.io.expected.out := 0.U
//     FCMP_6.io.expected.exceptionFlags := 0.U
//     when(FCMP_6.io.actual.out > 0.U){
//         cmpMin1_3 := c1loz    
//         cmpMax1_3 := c1hiz
//     } .otherwise{
//         cmpMin1_3 := c1hiz
//         cmpMax1_3 := c1loz
//     }
//     io.ooutt_11 := cmpMin1_3
//     io.ooutt_12 := cmpMax1_3   

//     val FCMP_7 = Module(new ValExec_CompareRecF32_le)
//     FCMP_7.io.a := cmpMin0_1
//     FCMP_7.io.b := cmpMin0_2
//     FCMP_7.io.expected.out := 0.U
//     FCMP_7.io.expected.exceptionFlags := 0.U
//     when(FCMP_7.io.actual.out > 0.U){   
//         c0Min_temp_1 := cmpMin0_2
//     } .otherwise{
//         c0Min_temp_1 := c1hiz
//     }
//     io.hmaxout_1 := c0Min_temp_1 
    
//     val FCMP_8 = Module(new ValExec_CompareRecF32_le)
//     FCMP_8.io.a := cmpMin0_3
//     FCMP_8.io.b := io.tmin
//     FCMP_8.io.expected.out := 0.U
//     FCMP_8.io.expected.exceptionFlags := 0.U
//     when(FCMP_8.io.actual.out > 0.U){   
//         c0Min_temp_2 := io.tmin
//     } .otherwise{
//         c0Min_temp_2 := cmpMin0_3
//     }
//     io.hmaxout_2 := c0Min_temp_2 

//     val FCMP_9 = Module(new ValExec_CompareRecF32_le)
//     FCMP_9.io.a := cmpMax0_1
//     FCMP_9.io.b := cmpMax0_2
//     FCMP_9.io.expected.out := 0.U
//     FCMP_9.io.expected.exceptionFlags := 0.U
//     when(FCMP_9.io.actual.out > 0.U){   
//         c0Max_temp_1 := cmpMax0_1
//     } .otherwise{
//         c0Max_temp_1 := cmpMax0_2
//     }
//     io.hmaxout_3 := c0Max_temp_1 

//     val FCMP_10 = Module(new ValExec_CompareRecF32_le)
//     FCMP_10.io.a := cmpMax0_3
//     FCMP_10.io.b := io.hitT
//     FCMP_10.io.expected.out := 0.U
//     FCMP_10.io.expected.exceptionFlags := 0.U
//     when(FCMP_10.io.actual.out > 0.U){   
//         c0Max_temp_2 := cmpMax0_3
//     } .otherwise{
//         c0Max_temp_2 := io.hitT
//     }
//     io.hmaxout_4 := c0Max_temp_2 

//     val FCMP_11 = Module(new ValExec_CompareRecF32_le)
//     FCMP_11.io.a := cmpMin1_1
//     FCMP_11.io.b := cmpMin1_2
//     FCMP_11.io.expected.out := 0.U
//     FCMP_11.io.expected.exceptionFlags := 0.U
//     when(FCMP_11.io.actual.out > 0.U){   
//         c1Min_temp_1 := cmpMin1_2
//     } .otherwise{
//         c1Min_temp_1 := cmpMin1_1
//     }
//     io.hmaxout_5 := c1Min_temp_1 

//     val FCMP_12 = Module(new ValExec_CompareRecF32_le)
//     FCMP_12.io.a := cmpMin1_3
//     FCMP_12.io.b := io.tmin
//     FCMP_12.io.expected.out := 0.U
//     FCMP_12.io.expected.exceptionFlags := 0.U
//     when(FCMP_12.io.actual.out > 0.U){   
//         c1Min_temp_2 := io.tmin
//     } .otherwise{
//         c1Min_temp_2 := cmpMin1_3
//     }
//     io.hmaxout_6 := c1Min_temp_2

//     val FCMP_13 = Module(new ValExec_CompareRecF32_le)
//     FCMP_13.io.a := cmpMax1_1
//     FCMP_13.io.b := cmpMax1_2
//     FCMP_13.io.expected.out := 0.U
//     FCMP_13.io.expected.exceptionFlags := 0.U
//     when(FCMP_13.io.actual.out > 0.U){   
//         c1Max_temp_1 := cmpMax1_1
//     } .otherwise{
//         c1Max_temp_1 := cmpMax1_2
//     }
//     io.hmaxout_7 := c1Max_temp_1

//     val FCMP_14 = Module(new ValExec_CompareRecF32_le)
//     FCMP_14.io.a := cmpMax1_3
//     FCMP_14.io.b := io.hitT
//     FCMP_14.io.expected.out := 0.U
//     FCMP_14.io.expected.exceptionFlags := 0.U
//     when(FCMP_14.io.actual.out > 0.U){   
//         c1Max_temp_2 := cmpMax1_3
//     } .otherwise{
//         c1Max_temp_2 := io.hitT
//     }
//     io.hmaxout_8 := c1Max_temp_2

//     val FCMP_15 = Module(new ValExec_CompareRecF32_le)
//     FCMP_15.io.a := c0Min_temp_1
//     FCMP_15.io.b := c0Min_temp_2
//     FCMP_15.io.expected.out := 0.U
//     FCMP_15.io.expected.exceptionFlags := 0.U
//     when(FCMP_15.io.actual.out > 0.U){   
//         c0Min := c0Min_temp_2
//     } .otherwise{
//         c0Min := c0Min_temp_1
//     }
//     io.c0Min_out := c0Min

//     val FCMP_16 = Module(new ValExec_CompareRecF32_le)
//     FCMP_16.io.a := c0Max_temp_1
//     FCMP_16.io.b := c0Max_temp_2
//     FCMP_16.io.expected.out := 0.U
//     FCMP_16.io.expected.exceptionFlags := 0.U
//     when(FCMP_16.io.actual.out > 0.U){   
//         c0Max := c0Max_temp_1
//     } .otherwise{
//         c0Max := c0Max_temp_2
//     }
//     io.c0Max_out := c0Max

//     val FCMP_17 = Module(new ValExec_CompareRecF32_le)
//     FCMP_17.io.a := c1Min_temp_1
//     FCMP_17.io.b := c1Min_temp_2
//     FCMP_17.io.expected.out := 0.U
//     FCMP_17.io.expected.exceptionFlags := 0.U
//     when(FCMP_17.io.actual.out > 0.U){   
//         c1Min := c1Min_temp_2
//     } .otherwise{
//         c1Min := c1Min_temp_1
//     }
//     io.c1Min_out := c1Min

//     val FCMP_18 = Module(new ValExec_CompareRecF32_le)
//     FCMP_18.io.a := c1Max_temp_1
//     FCMP_18.io.b := c1Max_temp_2
//     FCMP_18.io.expected.out := 0.U
//     FCMP_18.io.expected.exceptionFlags := 0.U
//     when(FCMP_18.io.actual.out > 0.U){   
//         c1Max := c1Max_temp_1
//     } .otherwise{
//         c1Max := c1Max_temp_2
//     }
//     io.c1Max_out := c1Max

//     val FCMP_19 = Module(new ValExec_CompareRecF32_le)
//     FCMP_19.io.a := c0Max
//     FCMP_19.io.b := c0Min
//     FCMP_19.io.expected.out := 0.U
//     FCMP_19.io.expected.exceptionFlags := 0.U
//     when(FCMP_1.io.actual.out > 0.U){   
//         cmp0 := 0.U
//     } .otherwise{
//         cmp0 := 1.U
//     }
//     io.cmp0_out := cmp0
//     io.traverseChild0 := io.cmp0_out

//     val FCMP_20 = Module(new ValExec_CompareRecF32_le)
//     FCMP_20.io.a := c1Max
//     FCMP_20.io.b := c1Min
//     FCMP_20.io.expected.out := 0.U
//     FCMP_20.io.expected.exceptionFlags := 0.U
//     when(FCMP_20.io.actual.out > 0.U){   
//         cmp1 := 0.U
//     } .otherwise{
//         cmp1 := 1.U
//     }
//     io.cmp1_out := cmp1
//     io.traverseChild1 := io.cmp1_out
// }
// class TesterSimple (dut:Child_node_traverse_pipeline) extends PeekPokeTester(dut){
// poke(dut.io.n0xy.x,1051931443.S)
// poke(dut.io.n0xy.y,1051931443.S)
// poke(dut.io.n0xy.z,1051931443.S)
// poke(dut.io.n0xy.w,1051931443.S)

// poke(dut.io.idirx,1045220556.S)
// poke(dut.io.idiry,1045220556.S)
// poke(dut.io.idirz,1045220556.S)

// poke(dut.io.nz.x,1051931443.S)
// poke(dut.io.nz.y,1051931443.S)
// poke(dut.io.nz.z,1051931443.S)
// poke(dut.io.nz.w,1051931443.S)

// poke(dut.io.n1xy.x,1051931443.S)
// poke(dut.io.n1xy.y,1051931443.S)
// poke(dut.io.n1xy.w,1051931443.S)
// poke(dut.io.n1xy.z,1051931443.S)

// step(1)
// println("MUL out_1 is  :" +peek(dut.io.out_1).toString)
// println("MUL out_2 is  :" +peek(dut.io.out_2).toString)
// println("MUL out_3 is  :" +peek(dut.io.out_3).toString)
// println("MUL out_4 is  :" +peek(dut.io.out_4).toString)
// println("MUL out_5 is  :" +peek(dut.io.out_5).toString)
// println("MUL out_6 is  :" +peek(dut.io.out_6).toString)
// println("MUL out_7 is  :" +peek(dut.io.out_7).toString)
// println("MUL out_8 is  :" +peek(dut.io.out_8).toString)
// println("MUL out_9 is  :" +peek(dut.io.out_9).toString)
// println("MUL out_10 is :" +peek(dut.io.out_10).toString)
// println("MUL out_11 is :" +peek(dut.io.out_11).toString)
// println("MUL out_12 is :" +peek(dut.io.out_12).toString)

// poke(dut.io.oodx,1051931443.S)

// poke(dut.io.oody,1051931443.S)

// poke(dut.io.oodz,1051931443.S)

// step(1)
// println("ADD outt_1 is   :" +peek(dut.io.outt_1).toString)
// println("ADD outt_2 is   :" +peek(dut.io.outt_2).toString)
// println("ADD outt_3 is   :" +peek(dut.io.outt_3).toString)
// println("ADD outt_4 is   :" +peek(dut.io.outt_4).toString)
// println("ADD outt_5 is   :" +peek(dut.io.outt_5).toString)
// println("ADD outt_6 is   :" +peek(dut.io.outt_6).toString)
// println("ADD outt_7 is   :" +peek(dut.io.outt_7).toString)
// println("ADD outt_8 is   :" +peek(dut.io.outt_8).toString)
// println("ADD outt_9 is   :" +peek(dut.io.outt_9).toString)
// println("ADD outt_10 is  :" +peek(dut.io.outt_10).toString)
// println("ADD outt_11 is  :" +peek(dut.io.outt_11).toString)
// println("ADD outt_12 is  :" +peek(dut.io.outt_12).toString)

// step(1)
// println("CMP ooutt_1  is  :" +peek(dut.io.ooutt_1).toString)
// println("CMP ooutt_2  is  :" +peek(dut.io.ooutt_2).toString)
// println("CMP ooutt_3  is  :" +peek(dut.io.ooutt_3).toString)
// println("CMP ooutt_4  is  :" +peek(dut.io.ooutt_4).toString)
// println("CMP ooutt_5  is  :" +peek(dut.io.ooutt_5).toString)
// println("CMP ooutt_6  is  :" +peek(dut.io.ooutt_6).toString)
// println("CMP ooutt_7  is  :" +peek(dut.io.ooutt_7).toString)
// println("CMP ooutt_8  is  :" +peek(dut.io.ooutt_8).toString)
// println("CMP ooutt_9  is  :" +peek(dut.io.ooutt_9).toString)
// println("CMP ooutt_10 is  :" +peek(dut.io.ooutt_10).toString)
// println("CMP ooutt_11 is  :" +peek(dut.io.ooutt_11).toString)
// println("CMP ooutt_12 is  :" +peek(dut.io.ooutt_12).toString)

// poke(dut.io.tmin,1051931443.S)
// poke(dut.io.hitT,1051931442.S)
// step(1)
// println("CMP hmaxout_1 is  :" +peek(dut.io.hmaxout_1).toString)
// println("CMP hmaxout_2 is  :" +peek(dut.io.hmaxout_2).toString)
// println("CMP hmaxout_3 is  :" +peek(dut.io.hmaxout_3).toString)
// println("CMP hmaxout_4 is  :" +peek(dut.io.hmaxout_4).toString)
// println("CMP hmaxout_5 is  :" +peek(dut.io.hmaxout_5).toString)
// println("CMP hmaxout_6 is  :" +peek(dut.io.hmaxout_6).toString)
// println("CMP hmaxout_7 is  :" +peek(dut.io.hmaxout_7).toString)
// println("CMP hmaxout_8 is  :" +peek(dut.io.hmaxout_8).toString)

// step(1)
// println("CMP c0Min_out is  :" +peek(dut.io.c0Min_out).toString)
// println("CMP c0Max_out is  :" +peek(dut.io.c0Max_out).toString)
// println("CMP c1Min_out is  :" +peek(dut.io.c1Min_out).toString)
// println("CMP c1Max_out is  :" +peek(dut.io.c1Max_out).toString)

// step(1)

// step(1)
// println("CMP traverseChild0 is  :" +peek(dut.io.traverseChild0).toString)
// println("CMP traverseChild1 is  :" +peek(dut.io.traverseChild1).toString)
// }
// object TesterSimple extends App{
//     chisel3. iotesters.Driver(()=> new Child_node_traverse_pipeline()){ c =>
//         new TesterSimple(c)
//     }
// }