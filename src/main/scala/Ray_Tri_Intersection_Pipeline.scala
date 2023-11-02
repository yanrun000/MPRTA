// import hardfloat._
// import Chisel._
// // import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver

//     class Ray_Tri_Intersection_Pipeline_1 extends Module{
//         val io = IO(new Bundle{
//             val nodeIdx = Input(SInt(32.W))
//             val triWoop = new TriWoopPrim().asInput

//             val v000 = Input(Bits(32.W))

//             val origx = Input(Bits(32.W))
//             val origy = Input(Bits(32.W))
//             val origz = Input(Bits(32.W))

//             val dirx = Input(Bits(32.W))
//             val diry = Input(Bits(32.W))
//             val dirz = Input(Bits(32.W))

//             val invDz = Input(Bits(32.W))

//             val tmin = Input(Bits(32.W))
//             val hitT = Input(Bits(32.W))

//             // val out_1 = Output(Bits(32.W))
//             //test for step(2)
//             val fmul_1_out_1 = Output(Bits(32.W))  //=mul_1_1
//             val fmul_1_out_2 = Output(Bits(32.W))  //=mul_1_2
//             val fmul_1_out_3 = Output(Bits(32.W))  //=mul_1_3
//             val fmul_1_out_4 = Output(Bits(32.W))  //=mul_1_4
//             val fmul_1_out_5 = Output(Bits(32.W))  //=mul_1_5
//             val fmul_1_out_6 = Output(Bits(32.W))  //=mul_1_6
//             // //test for step(3) and step(4)
//             val fadd_1_out_1 = Output(Bits(32.W))  //=add_1_1
//             val fadd_1_out_2 = Output(Bits(32.W))  //=add_1_2
//             val fadd_1_out_3 = Output(Bits(32.W))  //=add_1_3
//             val fadd_1_out_Oz = Output(Bits(32.W))  //=Oz
//             val fadd_1_out_4 = Output(Bits(32.W))  //=add_1_4

//             // //test for step(5) ie.t
//             val t_out = Output(Bits(32.W))  //t

//             //test for step(6)
//             val T_bigger_tmin = Output(Bool())
//             val T_smaller_hitT = Output(Bool())

//             //test for step(8)
//             val fmul_2_out_1 = Output(Bits(32.W))  //=mul_2_1
//             val fmul_2_out_2 = Output(Bits(32.W))  //=mul_2_2
//             val fmul_2_out_3 = Output(Bits(32.W))  //=mul_2_3
//             val fmul_2_out_4 = Output(Bits(32.W))  //=mul_2_4
//             val fmul_2_out_5 = Output(Bits(32.W))  //=mul_2_5
//             val fmul_2_out_6 = Output(Bits(32.W))  //=mul_2_5

//             //test for step(9)
//             val fadd_2_out_1 = Output(Bits(32.W))  // = v11.w + origx*v11.x
//             val fadd_2_out_2 = Output(Bits(32.W))  // = origy*v11.y + origz*v11.z
//             val fadd_2_out_3 = Output(Bits(32.W))  // = dirx*v11.x + diry*v11.y
            
//             //test for step(10)
//             val fadd_2_out_Ox = Output(Bits(32.W)) // = Ox
//             val fadd_2_out_Dx = Output(Bits(32.W)) // = Dx
            
//             //test for step(11)
//             val fadd_2_out_u_temp = Output(Bits(32.W))  // = u_temp

//             //test for step(12)
//             val fadd_2_out_u = Output(Bits(32.W))  // u

//             //test for step(13)
//             val U_bigger_0 = Output(Bool())
//             val U_smaller_1 = Output(Bool())

//             //test for step(15)
//             val fmul_3_out_1 = Output(Bits(32.W))  //=mul_3_1
//             val fmul_3_out_2 = Output(Bits(32.W))  //=mul_3_2
//             val fmul_3_out_3 = Output(Bits(32.W))  //=mul_3_3
//             val fmul_3_out_4 = Output(Bits(32.W))  //=mul_3_4
//             val fmul_3_out_5 = Output(Bits(32.W))  //=mul_3_5
//             val fmul_3_out_6 = Output(Bits(32.W))  //=mul_3_5

//             //test for step(16)
//             val fadd_3_out_1 = Output(Bits(32.W))  // = v22.w + origx*v22.x
//             val fadd_3_out_2 = Output(Bits(32.W))  // = origy*v22.y + origz*v22.z
//             val fadd_3_out_3 = Output(Bits(32.W))  // = dirx*v22.x + diry*v22.y

//             //test for step(17)
//             val fadd_3_out_Oy = Output(Bits(32.W)) // = Oy
//             val fadd_3_out_Dy = Output(Bits(32.W)) // = Dy

//             //test for step(18)
//             val fadd_3_out_v_temp = Output(Bits(32.W))  // = u_temp

//             //test for step(19)
//             val fadd_3_out_v = Output(Bits(32.W))  // v
            
//             //test for step(20)
//             val fadd_3_out_v_u = Output(Bits(32.W))  // v+u

//             //test for step(21)
//             val V_bigger_0 = Output(Bool())
//             val V_U_smaller_1 = Output(Bool())

//             //test for step(22)
//             val hitResult = Output(UInt(32.W))

//         })
//         // val triIdx = Bits(32.W)
//         val triVisited  = RegInit(0.U(64.W))
        
//         //for step(1)
//         val v00         = new Float()
//         val v11         = new Float()
//         val v22         = new Float()
//         val v00_w       = RegInit(0.U(32.W))
        
//         // temp for step (2)
//         val mul_1_1     = RegInit(0.U(32.W)) // = origx*v00.x
//         val mul_1_2     = RegInit(0.U(32.W))// = origy*v00.y  
//         val mul_1_3     = RegInit(0.U(32.W)) // = origz*v00.z
//         val mul_1_4     = RegInit(0.U(32.W)) // = dirx*v00.x
//         val mul_1_5     = RegInit(0.U(32.W))// = diry*v00.y
//         val mul_1_6     = RegInit(0.U(32.W)) // = dirz*v00.z
//         val v11_2       = new Float()
//         val v22_2       = new Float()
//         val origx_2     = RegInit(0.U(32.W))
//         val origy_2     = RegInit(0.U(32.W))
//         val origz_2     = RegInit(0.U(32.W))
//         val dirx_2      = RegInit(0.U(32.W))
//         val diry_2      = RegInit(0.U(32.W))
//         val dirz_2      = RegInit(0.U(32.W))
//         val tmin_2      = RegInit(0.U(32.W))
//         val hitT_2      = RegInit(0.U(32.W))

//         //temp for step(3) 
//         val add_1_1     = RegInit(0.U(32.W)) // = v00.w - origx*v00.x
//         val add_1_2     = RegInit(0.U(32.W)) // = origy*v00.y - origz*v00.z
//         val add_1_3     = RegInit(0.U(32.W)) // = dirx*v00.x + diry*v00.y
//         val mul_1_6_3   = RegInit(0.U(32.W))
//         val v11_3       = new Float()
//         val v22_3       = new Float()
//         val origx_3     = RegInit(0.U(32.W))
//         val origy_3     = RegInit(0.U(32.W))
//         val origz_3     = RegInit(0.U(32.W))
//         val dirx_3      = RegInit(0.U(32.W))
//         val diry_3      = RegInit(0.U(32.W))
//         val dirz_3      = RegInit(0.U(32.W))
//         val tmin_3      = RegInit(0.U(32.W))
//         val hitT_3      = RegInit(0.U(32.W))
//         //temp for step(4)
//         val Oz          = RegInit(0.U(32.W))
//         val add_1_4     = RegInit(0.U(32.W)) // = add_1_3 + dirz*v00.z
//         val v11_4       = new Float()
//         val v22_4       = new Float()
//         val origx_4     = RegInit(0.U(32.W))
//         val origy_4     = RegInit(0.U(32.W))
//         val origz_4     = RegInit(0.U(32.W))
//         val dirx_4      = RegInit(0.U(32.W))
//         val diry_4      = RegInit(0.U(32.W))
//         val dirz_4      = RegInit(0.U(32.W))
//         val tmin_4      = RegInit(0.U(32.W))
//         val hitT_4      = RegInit(0.U(32.W))

//         //temp for step(5)
//         val t           = RegInit(0.U(32.W))
//         val v11_5       = new Float()
//         val v22_5       = new Float()
//         val origx_5     = RegInit(0.U(32.W))
//         val origy_5     = RegInit(0.U(32.W))
//         val origz_5     = RegInit(0.U(32.W))
//         val dirx_5      = RegInit(0.U(32.W))
//         val diry_5      = RegInit(0.U(32.W))
//         val dirz_5      = RegInit(0.U(32.W))
//         val tmin_5      = RegInit(0.U(32.W))
//         val hitT_5      = RegInit(0.U(32.W))
//         //temp for step(6)
//         val t_bigger_tmin = Reg(Bool())
//         val t_smaller_hitT = Reg(Bool())
//         val v11_6       = new Float()
//         val v22_6       = new Float()
//         val origx_6     = RegInit(0.U(32.W))
//         val origy_6     = RegInit(0.U(32.W))
//         val origz_6     = RegInit(0.U(32.W))
//         val dirx_6      = RegInit(0.U(32.W))
//         val diry_6      = RegInit(0.U(32.W))
//         val dirz_6      = RegInit(0.U(32.W))
//         val t_6         = RegInit(0.U(32.W))
//         val tmin_6      = RegInit(0.U(32.W))
//         val hitT_6      = RegInit(0.U(32.W))
//         //do not how to get invDz

//         //temp for step(7)
//         val v11_7       = new Float()
//         val v22_7       = new Float()
//         val origx_7     = RegInit(0.U(32.W))
//         val origy_7     = RegInit(0.U(32.W))
//         val origz_7     = RegInit(0.U(32.W))
//         val dirx_7      = RegInit(0.U(32.W))
//         val diry_7      = RegInit(0.U(32.W))
//         val dirz_7      = RegInit(0.U(32.W))
//         val t_7         = RegInit(0.U(32.W))
//         val tmin_7      = RegInit(0.U(32.W))
//         val hitT_7      = RegInit(0.U(32.W))
//         //temp for step(8)
//         val mul_2_1     = RegInit(0.U(32.W)) // = origx*v11.x
//         val mul_2_2     = RegInit(0.U(32.W)) // = origy*v11.y 
//         val mul_2_3     = RegInit(0.U(32.W)) // = origz*v11.z
//         val mul_2_4     = RegInit(0.U(32.W)) // = dirx*v11.x
//         val mul_2_5     = RegInit(0.U(32.W)) // = diry*v11.y
//         val mul_2_6     = RegInit(0.U(32.W)) // = dirz*v11.z
//         val v11_8_w     = RegInit(0.U(32.W))
//         val v22_8       = new Float()
//         val origx_8     = RegInit(0.U(32.W))
//         val origy_8     = RegInit(0.U(32.W))
//         val origz_8     = RegInit(0.U(32.W))
//         val dirx_8      = RegInit(0.U(32.W))
//         val diry_8      = RegInit(0.U(32.W))
//         val dirz_8      = RegInit(0.U(32.W))
//         val t_8         = RegInit(0.U(32.W))
//         val tmin_8      = RegInit(0.U(32.W))
//         val hitT_8      = RegInit(0.U(32.W))
//         //temp for step(9)
//         val add_2_1     = RegInit(0.U(32.W)) // = v11.w + origx*v11.x
//         val add_2_2     = RegInit(0.U(32.W))// = origy*v11.y + origz*v11.z
//         val add_2_3     = RegInit(0.U(32.W)) // = dirx*v11.x + diry*v11.y
//         val v22_9       = new Float()
//         val origx_9     = RegInit(0.U(32.W))
//         val origy_9     = RegInit(0.U(32.W))
//         val origz_9     = RegInit(0.U(32.W))
//         val dirx_9      = RegInit(0.U(32.W))
//         val diry_9      = RegInit(0.U(32.W))
//         val dirz_9      = RegInit(0.U(32.W))
//         val t_9         = RegInit(0.U(32.W))
//         val mul_2_6_9   = RegInit(0.U(32.W))
        
//         //temp for step(10)
//         val Ox           = RegInit(0.U(32.W))
//         val Dx           = RegInit(0.U(32.W))
//         val u            = RegInit(0.U(32.W))
//         val v22_10       = new Float()
//         val origx_10     = RegInit(0.U(32.W))
//         val origy_10     = RegInit(0.U(32.W))
//         val origz_10     = RegInit(0.U(32.W))
//         val dirx_10      = RegInit(0.U(32.W))
//         val diry_10      = RegInit(0.U(32.W))
//         val dirz_10      = RegInit(0.U(32.W))
//         val t_10         = RegInit(0.U(32.W))
        
//         //temp for step(11)
//         val mul_u_temp   = RegInit(0.U(32.W))// = t*Dx
//         val v22_11       = new Float()
//         val origx_11     = RegInit(0.U(32.W))
//         val origy_11     = RegInit(0.U(32.W))
//         val origz_11     = RegInit(0.U(32.W))
//         val dirx_11      = RegInit(0.U(32.W))
//         val diry_11      = RegInit(0.U(32.W))
//         val dirz_11      = RegInit(0.U(32.W))
//         val t_11         = RegInit(0.U(32.W))
//         val Ox_11        = RegInit(0.U(32.W))

//         //temp for step(12)
//         val v22_12       = new Float()
//         val origx_12     = RegInit(0.U(32.W))
//         val origy_12     = RegInit(0.U(32.W))
//         val origz_12     = RegInit(0.U(32.W))
//         val dirx_12      = RegInit(0.U(32.W))
//         val diry_12      = RegInit(0.U(32.W))
//         val dirz_12      = RegInit(0.U(32.W))
//         val t_12         = RegInit(0.U(32.W))
        
//         //temp for step(13)
//         val u_bigger_0   = Reg(Bool())
//         val u_smaller_1  = Reg(Bool())
//         val v22_13       = new Float()
//         val origx_13     = RegInit(0.U(32.W))
//         val origy_13     = RegInit(0.U(32.W))
//         val origz_13     = RegInit(0.U(32.W))
//         val dirx_13      = RegInit(0.U(32.W))
//         val diry_13      = RegInit(0.U(32.W))
//         val dirz_13      = RegInit(0.U(32.W))
//         val t_13         = RegInit(0.U(32.W))

//         //temp for step(14)
//         val v22_14       = new Float()
//         val origx_14     = RegInit(0.U(32.W))
//         val origy_14     = RegInit(0.U(32.W))
//         val origz_14     = RegInit(0.U(32.W))
//         val dirx_14      = RegInit(0.U(32.W))
//         val diry_14      = RegInit(0.U(32.W))
//         val dirz_14      = RegInit(0.U(32.W))
//         val t_14         = RegInit(0.U(32.W))

//         //temp for step(15)
//         val mul_3_1      = RegInit(0.U(32.W)) // = origx*v22.x
//         val mul_3_2      = RegInit(0.U(32.W)) // = origy*v22.y  
//         val mul_3_3      = RegInit(0.U(32.W)) // = origz*v22.z
//         val mul_3_4      = RegInit(0.U(32.W)) // = dirx*v22.x
//         val mul_3_5      = RegInit(0.U(32.W)) // = diry*v22.y
//         val mul_3_6      = RegInit(0.U(32.W)) // = dirz*v22.z
//         val v22_15_w     = RegInit(0.U(32.W))
//         val origx_15     = RegInit(0.U(32.W))
//         val origy_15     = RegInit(0.U(32.W))
//         val origz_15     = RegInit(0.U(32.W))
//         val dirx_15      = RegInit(0.U(32.W))
//         val diry_15      = RegInit(0.U(32.W))
//         val dirz_15      = RegInit(0.U(32.W))
//         val t_15         = RegInit(0.U(32.W))

//         //temp for step(16)
//         val add_3_1      = RegInit(0.U(32.W)) // = v22.w + origx*v22.x
//         val add_3_2      = RegInit(0.U(32.W)) // = origy*v22.y + origz*v22.z
//         val add_3_3      = RegInit(0.U(32.W)) // = dirx*v22.x + diry*v22.y
//         val mul_3_6_16   = RegInit(0.U(32.W))  
//         val t_16         = RegInit(0.U(32.W))
//         //temp for step(17)
//         val Oy           = RegInit(0.U(32.W))
//         val Dy           = RegInit(0.U(32.W))
//         val v            = RegInit(0.U(32.W))
//         val t_17         = RegInit(0.U(32.W))
//         //temp for step(18)
//         val mul_v_temp = RegInit(0.U(32.W))// = t*Dy

//         //temp for step(20)
//         val add_u_v = RegInit(0.U(32.W))// = u+v
//         val v_20    = RegInit(0.U(32.W))

//         //temp for step(21)
//         val v_bigger_0 = Reg(Bool())
//         val v_u_smaller_1 = Reg(Bool())

//         //temp for step(22)
//         val hitResult_temp = RegInit(0.U(32.W))

//         // io.hitT           := io.hitT
//         // io.hitResult      := 0.U

//         when( io.nodeIdx < 0.S ){
//             //step(1)  这一步的数据要从上一级取数据
//             triVisited  := triVisited + 1.U
//             v00.x       := io.triWoop.w0(0)
//             v00.y       := io.triWoop.w0(1)
//             v00.z       := io.triWoop.w0(2)
//             v00.w       := io.triWoop.w0(3)

//             v11.x       := io.triWoop.w1(0)
//             v11.y       := io.triWoop.w1(1)
//             v11.z       := io.triWoop.w1(2)
//             v11.w       := io.triWoop.w1(3)

//             v22.x       := io.triWoop.w2(0)
//             v22.y       := io.triWoop.w2(1)
//             v22.z       := io.triWoop.w2(2)
//             v22.w       := io.triWoop.w2(3)
             
//             //step(2)  6*mul
//             v11_2.x     := v11.x
//             v11_2.y     := v11.y
//             v11_2.z     := v11.z
//             v11_2.w     := v11.w

//             v22_2.x     := v22.x
//             v22_2.y     := v22.y
//             v22_2.z     := v22.z
//             v22_2.w     := v22.w

//             v00_w       := v00.w

//             origx_2     := io.origx
//             origy_2     := io.origy
//             origz_2     := io.origz

//             dirx_2      := io.dirx
//             diry_2      := io.diry
//             dirz_2      := io.dirz
            
//             tmin_2      := io.tmin
//             hitT_2      := io.hitT 


//             val fmul_1 = Module(new ValExec_MulAddRecF32_mul)
//             fmul_1.io.a := io.origx
//             fmul_1.io.b := v00.x
//             fmul_1.io.roundingMode := 0.U
//             fmul_1.io.detectTininess := 1.U
//             fmul_1.io.expected.out := 0.U
//             fmul_1.io.expected.exceptionFlags := 0.U 
//             mul_1_1 := fNFromRecFN(8,24,fmul_1.io.actual.out)
//             io.fmul_1_out_1 := mul_1_1

//             val fmul_2 = Module(new ValExec_MulAddRecF32_mul)
//             fmul_2.io.a := io.origy
//             fmul_2.io.b := v00.y
//             fmul_2.io.roundingMode := 0.U
//             fmul_2.io.detectTininess := 1.U
//             fmul_2.io.expected.out := 0.U
//             fmul_2.io.expected.exceptionFlags := 0.U 
//             mul_1_2 := fNFromRecFN(8,24,fmul_2.io.actual.out)
//             io.fmul_1_out_2 := mul_1_2

//             val fmul_3 = Module(new ValExec_MulAddRecF32_mul)
//             fmul_3.io.a := io.origz
//             fmul_3.io.b := v00.z
//             fmul_3.io.roundingMode := 0.U
//             fmul_3.io.detectTininess := 1.U
//             fmul_3.io.expected.out := 0.U
//             fmul_3.io.expected.exceptionFlags := 0.U 
//             mul_1_3 := fNFromRecFN(8,24,fmul_3.io.actual.out)
//             io.fmul_1_out_3 := mul_1_3

//             val fmul_4 = Module(new ValExec_MulAddRecF32_mul)
//             fmul_4.io.a := io.dirx
//             fmul_4.io.b := v00.x
//             fmul_4.io.roundingMode := 0.U
//             fmul_4.io.detectTininess := 1.U
//             fmul_4.io.expected.out := 0.U
//             fmul_4.io.expected.exceptionFlags := 0.U 
//             mul_1_4 := fNFromRecFN(8,24,fmul_4.io.actual.out)
//             io.fmul_1_out_4 := mul_1_4

//             val fmul_5 = Module(new ValExec_MulAddRecF32_mul)
//             fmul_5.io.a := io.diry
//             fmul_5.io.b := v00.y
//             fmul_5.io.roundingMode := 0.U
//             fmul_5.io.detectTininess := 1.U
//             fmul_5.io.expected.out := 0.U
//             fmul_5.io.expected.exceptionFlags := 0.U 
//             mul_1_5 := fNFromRecFN(8,24,fmul_5.io.actual.out)
//             io.fmul_1_out_5 := mul_1_5

//             val fmul_6 = Module(new ValExec_MulAddRecF32_mul)
//             fmul_6.io.a := io.dirz
//             fmul_6.io.b := v00.z
//             fmul_6.io.roundingMode := 0.U
//             fmul_6.io.detectTininess := 1.U
//             fmul_6.io.expected.out := 0.U
//             fmul_6.io.expected.exceptionFlags := 0.U 
//             mul_1_6 := fNFromRecFN(8,24,fmul_6.io.actual.out)
//             io.fmul_1_out_6 := mul_1_6

//             // //step(3)
//             v11_3.x     := v11_2.x
//             v11_3.y     := v11_2.y
//             v11_3.z     := v11_2.z
//             v11_3.w     := v11_2.w

//             v22_3.x     := v22_2.x
//             v22_3.y     := v22_2.y
//             v22_3.z     := v22_2.z
//             v22_3.w     := v22_2.w
            
//             origx_3     := origx_2
//             origy_3     := origy_2
//             origz_3     := origz_2

//             dirx_3      := dirx_2
//             diry_3      := diry_2
//             dirz_3      := dirz_2

//             mul_1_6_3   := mul_1_6

//             tmin_3      := tmin_2
//             hitT_3      := hitT_2 

//             val fadd_1 = Module(new ValExec_MulAddRecF32_add)
//             fadd_1.io.a := v00_w
//             fadd_1.io.b := offsign(mul_1_1)
//             fadd_1.io.roundingMode := 0.U
//             fadd_1.io.detectTininess := 1.U
//             fadd_1.io.expected.out := 0.U
//             fadd_1.io.expected.exceptionFlags := 0.U 
//             add_1_1 := fNFromRecFN(8,24,fadd_1.io.actual.out)
//             io.fadd_1_out_1 := add_1_1

//             val fadd_2 = Module(new ValExec_MulAddRecF32_add)
//             fadd_2.io.a := mul_1_2
//             fadd_2.io.b := offsign(mul_1_3)
//             fadd_2.io.roundingMode := 0.U
//             fadd_2.io.detectTininess := 1.U
//             fadd_2.io.expected.out := 0.U
//             fadd_2.io.expected.exceptionFlags := 0.U 
//             add_1_2 := fNFromRecFN(8,24,fadd_2.io.actual.out)
//             io.fadd_1_out_2 := add_1_2

//             val fadd_3 = Module(new ValExec_MulAddRecF32_add)
//             fadd_3.io.a := mul_1_4
//             fadd_3.io.b := mul_1_5
//             fadd_3.io.roundingMode := 0.U
//             fadd_3.io.detectTininess := 1.U
//             fadd_3.io.expected.out := 0.U
//             fadd_3.io.expected.exceptionFlags := 0.U 
//             add_1_3 := fNFromRecFN(8,24,fadd_3.io.actual.out)
//             io.fadd_1_out_3 := add_1_3

//             //step(4)  暂且未能实例化浮点除法的运算部件
//             v11_4.x     := v11_3.x
//             v11_4.y     := v11_3.y
//             v11_4.z     := v11_3.z
//             v11_4.w     := v11_3.w

//             v22_4.x     := v22_3.x
//             v22_4.y     := v22_3.y
//             v22_4.z     := v22_3.z
//             v22_4.w     := v22_3.w
            
//             origx_4     := origx_3
//             origy_4     := origy_3
//             origz_4     := origz_3

//             dirx_4      := dirx_3
//             diry_4      := diry_3
//             dirz_4      := dirz_3

//             tmin_4      := tmin_3
//             hitT_4      := hitT_3

//             val fadd_Oz = Module(new ValExec_MulAddRecF32_add)
//             fadd_Oz.io.a := add_1_1
//             fadd_Oz.io.b := offsign(add_1_2)
//             fadd_Oz.io.roundingMode := 0.U
//             fadd_Oz.io.detectTininess := 1.U
//             fadd_Oz.io.expected.out := 0.U
//             fadd_Oz.io.expected.exceptionFlags := 0.U 
//             Oz := fNFromRecFN(8,24,fadd_Oz.io.actual.out)
//             io.fadd_1_out_Oz := Oz

//             val fadd_4 = Module(new ValExec_MulAddRecF32_add)
//             fadd_4.io.a := add_1_3
//             fadd_4.io.b := mul_1_6_3
//             fadd_4.io.roundingMode := 0.U
//             fadd_4.io.detectTininess := 1.U
//             fadd_4.io.expected.out := 0.U
//             fadd_4.io.expected.exceptionFlags := 0.U 
//             add_1_4 := fNFromRecFN(8,24,fadd_4.io.actual.out)
//             io.fadd_1_out_4 := add_1_4  //invDz是add_1_4的倒数，在体系结构报告的文档中，这里有一些出入，因为第三拍算不出来这个结果

//             //step(5)这里少一拍
//             v11_5.x     := v11_4.x
//             v11_5.y     := v11_4.y
//             v11_5.z     := v11_4.z
//             v11_5.w     := v11_4.w

//             v22_5.x     := v22_4.x
//             v22_5.y     := v22_4.y
//             v22_5.z     := v22_4.z
//             v22_5.w     := v22_4.w
            
//             origx_5     := origx_4
//             origy_5     := origy_4
//             origz_5     := origz_4

//             dirx_5      := dirx_4
//             diry_5      := diry_4
//             dirz_5      := dirz_4

//             tmin_5      := tmin_4
//             hitT_5      := hitT_4

//             val fmul_7 = Module(new ValExec_MulAddRecF32_mul)
//             fmul_7.io.a := Oz
//             fmul_7.io.b := io.invDz
//             fmul_7.io.roundingMode := 0.U
//             fmul_7.io.detectTininess := 1.U
//             fmul_7.io.expected.out := 0.U
//             fmul_7.io.expected.exceptionFlags := 0.U 
//             t := fNFromRecFN(8,24,fmul_7.io.actual.out)
//             io.t_out := t

//             //step(6)
//             v11_6.x     := v11_5.x
//             v11_6.y     := v11_5.y
//             v11_6.z     := v11_5.z
//             v11_6.w     := v11_5.w

//             v22_6.x     := v22_5.x
//             v22_6.y     := v22_5.y
//             v22_6.z     := v22_5.z
//             v22_6.w     := v22_5.w
            
//             origx_6     := origx_5
//             origy_6     := origy_5
//             origz_6     := origz_5

//             dirx_6      := dirx_5
//             diry_6      := diry_5
//             dirz_6      := dirz_5

//             t_6         := t
//             tmin_6      := tmin_5
//             hitT_6      := hitT_5

//             val fcmp_1 = Module(new ValExec_CompareRecF32_le)
//             fcmp_1.io.a := t
//             fcmp_1.io.b := tmin_6
//             fcmp_1.io.expected.out := 0.U
//             fcmp_1.io.expected.exceptionFlags := 0.U
//             when(fcmp_1.io.actual.out > 0.U){
//                 t_bigger_tmin := false.B    
//                 io.T_bigger_tmin := false.B
//             } .otherwise{
//                 t_bigger_tmin := true.B    
//                 io.T_bigger_tmin := true.B
//             }

//             val fcmp_2 = Module(new ValExec_CompareRecF32_lt)
//             fcmp_2.io.a := t
//             fcmp_2.io.b := hitT_6
//             fcmp_2.io.expected.out := 0.U
//             fcmp_2.io.expected.exceptionFlags := 0.U    
//             when(fcmp_2.io.actual.out > 0.U){
//                 t_smaller_hitT := true.B    
//                 io.T_smaller_hitT := true.B
//             } .otherwise{
//                 t_smaller_hitT := false.B    
//                 io.T_smaller_hitT := false.B
//             }

//                 //step(7) it should be 
//                 when(t_bigger_tmin & t_smaller_hitT){
                    
//                     v11_7.x     := v11_6.x
//                     v11_7.y     := v11_6.y
//                     v11_7.z     := v11_6.z
//                     v11_7.w     := v11_6.w

//                     origx_7     := origx_6
//                     origy_7     := origy_6
//                     origz_7     := origz_6

//                     dirx_7      := dirx_6
//                     diry_7      := diry_6
//                     dirz_7      := dirz_6

//                 }
//                 // .otherwise{
//                 //     v11_7.x     := 1065353216.U //1
//                 //     v11_7.y     := 0.U
//                 //     v11_7.z     := 0.U
//                 //     v11_7.w     := 1065353216.U

//                 //     origx_7     := 1065353216.U
//                 //     origy_7     := 0.U
//                 //     origz_7     := 0.U

//                 //     dirx_7      := 0.U
//                 //     diry_7      := 0.U
//                 //     dirz_7      := 0.U
//                 // }
//                     v22_7.x     := v22_6.x
//                     v22_7.y     := v22_6.y
//                     v22_7.z     := v22_6.z
//                     v22_7.w     := v22_6.w
//                     t_7         := t_6
//                     //step(8)
//                     v11_8_w     := v11_7.w

//                     v22_8.x     := v22_7.x
//                     v22_8.y     := v22_7.y
//                     v22_8.z     := v22_7.z
//                     v22_8.w     := v22_7.w

//                     origx_8     := origx_7
//                     origy_8     := origy_7
//                     origz_8     := origz_7

//                     dirx_8      := dirx_7
//                     diry_8      := diry_7
//                     dirz_8      := dirz_7

//                     t_8         := t_7

//                     val fmul_8 = Module(new ValExec_MulAddRecF32_mul)
//                     fmul_8.io.a := origx_7
//                     fmul_8.io.b := v11_7.x
//                     fmul_8.io.roundingMode := 0.U
//                     fmul_8.io.detectTininess := 1.U
//                     fmul_8.io.expected.out := 0.U
//                     fmul_8.io.expected.exceptionFlags := 0.U 
//                     mul_2_1 := fNFromRecFN(8,24,fmul_8.io.actual.out)
//                     io.fmul_2_out_1 := mul_2_1

//                     val fmul_9 = Module(new ValExec_MulAddRecF32_mul)
//                     fmul_9.io.a := origy_7
//                     fmul_9.io.b := v11_7.y
//                     fmul_9.io.roundingMode := 0.U
//                     fmul_9.io.detectTininess := 1.U
//                     fmul_9.io.expected.out := 0.U
//                     fmul_9.io.expected.exceptionFlags := 0.U 
//                     mul_2_2 := fNFromRecFN(8,24,fmul_9.io.actual.out)
//                     io.fmul_2_out_2 := mul_2_2

//                     val fmul_10 = Module(new ValExec_MulAddRecF32_mul)
//                     fmul_10.io.a := origz_7
//                     fmul_10.io.b := v11_7.z
//                     fmul_10.io.roundingMode := 1.U
//                     fmul_10.io.detectTininess := 1.U
//                     fmul_10.io.expected.out := 0.U
//                     fmul_10.io.expected.exceptionFlags := 0.U 
//                     mul_2_3 := fNFromRecFN(8,24,fmul_10.io.actual.out)
//                     io.fmul_2_out_3 := mul_2_3

//                     val fmul_11 = Module(new ValExec_MulAddRecF32_mul)
//                     fmul_11.io.a := dirx_7
//                     fmul_11.io.b := v11_7.x
//                     fmul_11.io.roundingMode := 0.U
//                     fmul_11.io.detectTininess := 1.U
//                     fmul_11.io.expected.out := 0.U
//                     fmul_11.io.expected.exceptionFlags := 0.U 
//                     mul_2_4 := fNFromRecFN(8,24,fmul_11.io.actual.out)
//                     io.fmul_2_out_4 := mul_2_4

//                     val fmul_12 = Module(new ValExec_MulAddRecF32_mul)
//                     fmul_12.io.a := diry_7
//                     fmul_12.io.b := v11_7.y
//                     fmul_12.io.roundingMode := 0.U
//                     fmul_12.io.detectTininess := 1.U
//                     fmul_12.io.expected.out := 0.U
//                     fmul_12.io.expected.exceptionFlags := 0.U 
//                     mul_2_5 := fNFromRecFN(8,24,fmul_12.io.actual.out)
//                     io.fmul_2_out_5 := mul_2_5

//                     val fmul_13 = Module(new ValExec_MulAddRecF32_mul)
//                     fmul_13.io.a := dirz_7
//                     fmul_13.io.b := v11_7.z
//                     fmul_13.io.roundingMode := 0.U
//                     fmul_13.io.detectTininess := 1.U
//                     fmul_13.io.expected.out := 0.U
//                     fmul_13.io.expected.exceptionFlags := 0.U 
//                     mul_2_6 := fNFromRecFN(8,24,fmul_13.io.actual.out)
//                     io.fmul_2_out_6 := mul_2_6

//                     //step(9)
//                     v22_9.x     := v22_8.x
//                     v22_9.y     := v22_8.y
//                     v22_9.z     := v22_8.z
//                     v22_9.w     := v22_8.w

//                     origx_9     := origx_8
//                     origy_9     := origy_8
//                     origz_9     := origz_8

//                     dirx_9      := dirx_8
//                     diry_9      := diry_8
//                     dirz_9      := dirz_8

//                     t_9         := t_8
//                     mul_2_6_9   := mul_2_6

//                     val fadd_5 = Module(new ValExec_MulAddRecF32_add)
//                     fadd_5.io.a := v11_8_w
//                     fadd_5.io.b := mul_2_1
//                     fadd_5.io.roundingMode := 0.U
//                     fadd_5.io.detectTininess := 1.U
//                     fadd_5.io.expected.out := 0.U
//                     fadd_5.io.expected.exceptionFlags := 0.U 
//                     add_2_1 := fNFromRecFN(8,24,fadd_5.io.actual.out)
//                     io.fadd_2_out_1 := add_2_1

//                     val fadd_6 = Module(new ValExec_MulAddRecF32_add)
//                     fadd_6.io.a := mul_2_2
//                     fadd_6.io.b := mul_2_3
//                     fadd_6.io.roundingMode := 0.U
//                     fadd_6.io.detectTininess := 1.U
//                     fadd_6.io.expected.out := 0.U
//                     fadd_6.io.expected.exceptionFlags := 0.U 
//                     add_2_2 := fNFromRecFN(8,24,fadd_6.io.actual.out)
//                     io.fadd_2_out_2 := add_2_2

//                     val fadd_7 = Module(new ValExec_MulAddRecF32_add)
//                     fadd_7.io.a := mul_2_4
//                     fadd_7.io.b := mul_2_5
//                     fadd_7.io.roundingMode := 0.U
//                     fadd_7.io.detectTininess := 1.U
//                     fadd_7.io.expected.out := 0.U
//                     fadd_7.io.expected.exceptionFlags := 0.U 
//                     add_2_3 := fNFromRecFN(8,24,fadd_7.io.actual.out)
//                     io.fadd_2_out_3 := add_2_3
                    
//                     //step(10)
//                     v22_10.x     := v22_9.x
//                     v22_10.y     := v22_9.y
//                     v22_10.z     := v22_9.z
//                     v22_10.w     := v22_9.w

//                     origx_10     := origx_9
//                     origy_10     := origy_9
//                     origz_10     := origz_9

//                     dirx_10      := dirx_9
//                     diry_10      := diry_9
//                     dirz_10      := dirz_9

//                     t_10         := t_9    

//                     val fadd_8 = Module(new ValExec_MulAddRecF32_add)
//                     fadd_8.io.a := add_2_1
//                     fadd_8.io.b := add_2_2
//                     fadd_8.io.roundingMode := 0.U
//                     fadd_8.io.detectTininess := 1.U
//                     fadd_8.io.expected.out := 0.U
//                     fadd_8.io.expected.exceptionFlags := 0.U 
//                     Ox := fNFromRecFN(8,24,fadd_8.io.actual.out)
//                     io.fadd_2_out_Ox := Ox

//                     val fadd_9 = Module(new ValExec_MulAddRecF32_add)
//                     fadd_9.io.a := add_2_3
//                     fadd_9.io.b := mul_2_6
//                     fadd_9.io.roundingMode := 0.U
//                     fadd_9.io.detectTininess := 1.U
//                     fadd_9.io.expected.out := 0.U
//                     fadd_9.io.expected.exceptionFlags := 0.U 
//                     Dx := fNFromRecFN(8,24,fadd_9.io.actual.out)
//                     io.fadd_2_out_Dx := Dx

//                     //step(11)
//                     v22_11.x     := v22_10.x
//                     v22_11.y     := v22_10.y
//                     v22_11.z     := v22_10.z
//                     v22_11.w     := v22_10.w

//                     origx_11     := origx_10
//                     origy_11     := origy_10
//                     origz_11     := origz_10

//                     dirx_11      := dirx_10
//                     diry_11      := diry_10
//                     dirz_11      := dirz_10

//                     t_11         := t_10
//                     Ox_11        := Ox

//                     val fmul_14 = Module(new ValExec_MulAddRecF32_mul)
//                     fmul_14.io.a := t_10
//                     fmul_14.io.b := Dx
//                     fmul_14.io.roundingMode := 0.U
//                     fmul_14.io.detectTininess := 1.U
//                     fmul_14.io.expected.out := 0.U
//                     fmul_14.io.expected.exceptionFlags := 0.U 
//                     mul_u_temp := fNFromRecFN(8,24,fmul_14.io.actual.out)
//                     io.fadd_2_out_u_temp := mul_u_temp

//                     //step(12)
//                     v22_12.x     := v22_11.x
//                     v22_12.y     := v22_11.y
//                     v22_12.z     := v22_11.z
//                     v22_12.w     := v22_11.w

//                     origx_12     := origx_11
//                     origy_12     := origy_11
//                     origz_12     := origz_11

//                     dirx_12      := dirx_11
//                     diry_12      := diry_11
//                     dirz_12      := dirz_11

//                     t_12         := t_11

//                     val fadd_10 = Module(new ValExec_MulAddRecF32_add)
//                     fadd_10.io.a := Ox_11
//                     fadd_10.io.b := mul_u_temp
//                     fadd_10.io.roundingMode := 0.U
//                     fadd_10.io.detectTininess := 1.U
//                     fadd_10.io.expected.out := 0.U
//                     fadd_10.io.expected.exceptionFlags := 0.U 
//                     u := fNFromRecFN(8,24,fadd_10.io.actual.out)
//                     io.fadd_2_out_u := u

//                     //step(13)
//                     v22_13.x     := v22_12.x
//                     v22_13.y     := v22_12.y
//                     v22_13.z     := v22_12.z
//                     v22_13.w     := v22_12.w

//                     origx_13     := origx_12
//                     origy_13     := origy_12
//                     origz_13     := origz_12

//                     dirx_13      := dirx_12
//                     diry_13      := diry_12
//                     dirz_13      := dirz_12

//                     t_13         := t_12

//                     val fcmp_3 = Module(new ValExec_CompareRecF32_le)
//                     fcmp_3.io.a := u
//                     fcmp_3.io.b := 0.U
//                     fcmp_3.io.expected.out := 0.U
//                     fcmp_3.io.expected.exceptionFlags := 0.U
//                     when(fcmp_3.io.actual.out > 0.U){
//                         u_bigger_0 := false.B    
//                         io.U_bigger_0 := false.B
//                     } .otherwise{
//                         u_bigger_0 := true.B    
//                         io.U_bigger_0 := true.B
//                     }

//                     val fcmp_4 = Module(new ValExec_CompareRecF32_lt)
//                     fcmp_4.io.a := u
//                     fcmp_4.io.b := 1065353216.U
//                     fcmp_4.io.expected.out := 0.U
//                     fcmp_4.io.expected.exceptionFlags := 0.U
//                     when(fcmp_4.io.actual.out > 0.U){
//                         u_smaller_1 := true.B    
//                         io.U_smaller_1 := true.B
//                     } .otherwise{
//                         u_smaller_1 := false.B    
//                         io.U_smaller_1 := false.B
//                     }

//                     //step(14)    
//                     when(u_bigger_0 & u_smaller_1){
                        
//                         v22_14.x     := v22_13.x
//                         v22_14.y     := v22_13.y
//                         v22_14.z     := v22_13.z
//                         v22_14.w     := v22_13.w

//                         origx_14     := origx_13
//                         origy_14     := origy_13
//                         origz_14     := origz_13

//                         dirx_14      := dirx_13
//                         diry_14      := diry_13
//                         dirz_14      := dirz_13

//                     }

//                         t_14         := t_13
                        
//                         //step(15)  6*mul
//                         v22_15_w     := v22_14.w 
//                         origx_15     := origx_14
//                         origy_15     := origy_14
//                         origz_15     := origz_14

//                         dirx_15      := dirx_14
//                         diry_15      := diry_14
//                         dirz_15      := dirz_14
//                         t_15         := t_14
//                         val fmul_15 = Module(new ValExec_MulAddRecF32_mul)
//                         fmul_15.io.a := origx_14
//                         fmul_15.io.b := v22_14.x
//                         fmul_15.io.roundingMode := 0.U
//                         fmul_15.io.detectTininess := 1.U
//                         fmul_15.io.expected.out := 0.U
//                         fmul_15.io.expected.exceptionFlags := 0.U 
//                         mul_3_1 := fNFromRecFN(8,24,fmul_15.io.actual.out)
//                         io.fmul_3_out_1 := mul_3_1

//                         val fmul_16 = Module(new ValExec_MulAddRecF32_mul)
//                         fmul_16.io.a := origy_14
//                         fmul_16.io.b := v22_14.y
//                         fmul_16.io.roundingMode := 0.U
//                         fmul_16.io.detectTininess := 1.U
//                         fmul_16.io.expected.out := 0.U
//                         fmul_16.io.expected.exceptionFlags := 0.U 
//                         mul_3_2 := fNFromRecFN(8,24,fmul_16.io.actual.out)
//                         io.fmul_3_out_2 := mul_3_2
                    
//                         val fmul_17 = Module(new ValExec_MulAddRecF32_mul)
//                         fmul_17.io.a := origz_14
//                         fmul_17.io.b := v22_14.z
//                         fmul_17.io.roundingMode := 0.U
//                         fmul_17.io.detectTininess := 1.U
//                         fmul_17.io.expected.out := 0.U
//                         fmul_17.io.expected.exceptionFlags := 0.U 
//                         mul_3_3 := fNFromRecFN(8,24,fmul_17.io.actual.out)
//                         io.fmul_3_out_3 := mul_3_3

//                         val fmul_18 = Module(new ValExec_MulAddRecF32_mul)
//                         fmul_18.io.a := dirx_14
//                         fmul_18.io.b := v22_14.x
//                         fmul_18.io.roundingMode := 0.U
//                         fmul_18.io.detectTininess := 1.U
//                         fmul_18.io.expected.out := 0.U
//                         fmul_18.io.expected.exceptionFlags := 0.U 
//                         mul_3_4 := fNFromRecFN(8,24,fmul_18.io.actual.out)
//                         io.fmul_3_out_4 := mul_3_4

//                         val fmul_19 = Module(new ValExec_MulAddRecF32_mul)
//                         fmul_19.io.a := diry_14
//                         fmul_19.io.b := v22_14.y
//                         fmul_19.io.roundingMode := 0.U
//                         fmul_19.io.detectTininess := 1.U
//                         fmul_19.io.expected.out := 0.U
//                         fmul_19.io.expected.exceptionFlags := 0.U 
//                         mul_3_5 := fNFromRecFN(8,24,fmul_18.io.actual.out)
//                         io.fmul_3_out_5 := mul_3_5
                    
//                         val fmul_20 = Module(new ValExec_MulAddRecF32_mul)
//                         fmul_20.io.a := dirz_14
//                         fmul_20.io.b := v22_14.z
//                         fmul_20.io.roundingMode := 0.U
//                         fmul_20.io.detectTininess := 1.U
//                         fmul_20.io.expected.out := 0.U
//                         fmul_20.io.expected.exceptionFlags := 0.U 
//                         mul_3_6 := fNFromRecFN(8,24,fmul_20.io.actual.out)
//                         io.fmul_3_out_6 := mul_3_6

//                         //step(16)
//                         mul_3_6_16   := mul_3_6
//                         t_16         := t_15
//                         val fadd_11 = Module(new ValExec_MulAddRecF32_add)
//                         fadd_11.io.a := v22_15_w
//                         fadd_11.io.b := mul_3_1
//                         fadd_11.io.roundingMode := 0.U
//                         fadd_11.io.detectTininess := 1.U
//                         fadd_11.io.expected.out := 0.U
//                         fadd_11.io.expected.exceptionFlags := 0.U 
//                         add_3_1 := fNFromRecFN(8,24,fadd_11.io.actual.out)
//                         io.fadd_3_out_1 := add_3_1

//                         val fadd_12 = Module(new ValExec_MulAddRecF32_add)
//                         fadd_12.io.a := mul_3_2
//                         fadd_12.io.b := mul_3_3
//                         fadd_12.io.roundingMode := 0.U
//                         fadd_12.io.detectTininess := 1.U
//                         fadd_12.io.expected.out := 0.U
//                         fadd_12.io.expected.exceptionFlags := 0.U 
//                         add_3_2 := fNFromRecFN(8,24,fadd_12.io.actual.out)
//                         io.fadd_3_out_2 := add_3_2

//                         val fadd_13 = Module(new ValExec_MulAddRecF32_add)
//                         fadd_13.io.a := mul_3_4
//                         fadd_13.io.b := mul_3_5
//                         fadd_13.io.roundingMode := 0.U
//                         fadd_13.io.detectTininess := 1.U
//                         fadd_13.io.expected.out := 0.U
//                         fadd_13.io.expected.exceptionFlags := 0.U 
//                         add_3_3 := fNFromRecFN(8,24,fadd_13.io.actual.out)
//                         io.fadd_3_out_3 := add_3_3
                        
//                         //step(17)    
//                         t_17         := t_16
//                         val fadd_14 = Module(new ValExec_MulAddRecF32_add)
//                         fadd_14.io.a := add_3_1
//                         fadd_14.io.b := add_3_2
//                         fadd_14.io.roundingMode := 0.U
//                         fadd_14.io.detectTininess := 1.U
//                         fadd_14.io.expected.out := 0.U
//                         fadd_14.io.expected.exceptionFlags := 0.U 
//                         Oy := fNFromRecFN(8,24,fadd_14.io.actual.out)
//                         io.fadd_3_out_Oy := Oy

//                         val fadd_15 = Module(new ValExec_MulAddRecF32_add)
//                         fadd_15.io.a := add_3_3
//                         fadd_15.io.b := mul_3_6_16
//                         fadd_15.io.roundingMode := 0.U
//                         fadd_15.io.detectTininess := 1.U
//                         fadd_15.io.expected.out := 0.U
//                         fadd_15.io.expected.exceptionFlags := 0.U 
//                         Dy := fNFromRecFN(8,24,fadd_15.io.actual.out)
//                         io.fadd_3_out_Dy := Dy

//                         //step(18)
//                         val fmul_21 = Module(new ValExec_MulAddRecF32_mul)
//                         fmul_21.io.a := t
//                         fmul_21.io.b := Dy
//                         fmul_21.io.roundingMode := 0.U
//                         fmul_21.io.detectTininess := 1.U
//                         fmul_21.io.expected.out := 0.U
//                         fmul_21.io.expected.exceptionFlags := 0.U 
//                         mul_v_temp := fNFromRecFN(8,24,fmul_21.io.actual.out)
//                         io.fadd_3_out_v_temp := mul_v_temp

//                         //step(19)
//                         val fadd_16 = Module(new ValExec_MulAddRecF32_add)
//                         fadd_16.io.a := Oy
//                         fadd_16.io.b := mul_v_temp
//                         fadd_16.io.roundingMode := 0.U
//                         fadd_16.io.detectTininess := 1.U
//                         fadd_16.io.expected.out := 0.U
//                         fadd_16.io.expected.exceptionFlags := 0.U 
//                         v := fNFromRecFN(8,24,fadd_16.io.actual.out)
//                         io.fadd_3_out_v := v

//                         //step(20)
//                         v_20   := v
//                         val fadd_17 = Module(new ValExec_MulAddRecF32_add)
//                         fadd_17.io.a := v
//                         fadd_17.io.b := u
//                         fadd_17.io.roundingMode := 0.U
//                         fadd_17.io.detectTininess := 1.U
//                         fadd_17.io.expected.out := 0.U
//                         fadd_17.io.expected.exceptionFlags := 0.U 
//                         add_u_v := fNFromRecFN(8,24,fadd_17.io.actual.out)
//                         io.fadd_3_out_v_u := add_u_v

//                         //step(21)
//                         val fcmp_5 = Module(new ValExec_CompareRecF32_le)
//                         fcmp_5.io.a := v_20
//                         fcmp_5.io.b := 0.U
//                         fcmp_5.io.expected.out := 0.U
//                         fcmp_5.io.expected.exceptionFlags := 0.U
//                         when(fcmp_5.io.actual.out > 0.U){
//                             v_bigger_0 := false.B    
//                             io.V_bigger_0 := false.B
//                         } .otherwise{
//                             v_bigger_0 := true.B    
//                             io.V_bigger_0 := true.B
//                         }

//                         val fcmp_6 = Module(new ValExec_CompareRecF32_lt)
//                         fcmp_6.io.a := add_u_v
//                         fcmp_6.io.b := 1065353216.U
//                         fcmp_6.io.expected.out := 0.U
//                         fcmp_6.io.expected.exceptionFlags := 0.U
//                         when(fcmp_6.io.actual.out > 0.U){
//                             v_u_smaller_1 := true.B    
//                             io.V_U_smaller_1 := true.B
//                         } .otherwise{
//                             u_smaller_1 := false.B    
//                             io.V_U_smaller_1 := false.B
//                         }

//                         // step(22)
//                         when(v_bigger_0 & v_u_smaller_1){
//                             hitResult_temp := 1.U
//                             io.hitResult := hitResult_temp
//                         }.otherwise{
//                             hitResult_temp := 0.U
//                             io.hitResult := hitResult_temp
//                         }
                      
                    
//                     // }.otherwise{
//                     //     io.fmul_3_out_1 := io.v000
//                     //     io.fmul_3_out_2 := io.v000
//                     //     io.fmul_3_out_3 := io.v000   
//                     //     io.fmul_3_out_4 := io.v000
//                     //     io.fmul_3_out_5 := io.v000
//                     //     io.fmul_3_out_6 := io.v000
//                     //     io.fadd_3_out_1 := io.v000
//                     //     io.fadd_3_out_2 := io.v000
//                     //     io.fadd_3_out_3 := io.v000
//                     //     io.fadd_3_out_Oy := io.v000
//                     //     io.fadd_3_out_Dy := io.v000
//                     //     io.fadd_3_out_v_temp := io.v000
//                     //     io.fadd_3_out_v := io.v000
//                     //     io.fadd_3_out_v_u := io.v000
//                     //     io.V_bigger_0 := false.B
//                     //     io.V_U_smaller_1 := false.B
//                     //     io.hitResult := 0.U
//                     // }

//                 // }.otherwise{
//                 //     io.fmul_2_out_1 := io.v000
//                 //     io.fmul_2_out_2 := io.v000
//                 //     io.fmul_2_out_3 := io.v000
//                 //     io.fmul_2_out_4 := io.v000
//                 //     io.fmul_2_out_5 := io.v000
//                 //     io.fmul_2_out_6 := io.v000
//                 //     io.fadd_2_out_1 := io.v000
//                 //     io.fadd_2_out_2 := io.v000
//                 //     io.fadd_2_out_3 := io.v000
//                 //     io.fadd_2_out_Ox := io.v000
//                 //     io.fadd_2_out_Dx := io.v000
//                 //     io.fadd_2_out_u_temp := io.v000
//                 //     io.fadd_2_out_u := io.v000
//                 //     io.U_bigger_0 := false.B
//                 //     io.U_smaller_1 := false.B
//                 //     io.fmul_3_out_1 := io.v000
//                 //     io.fmul_3_out_2 := io.v000
//                 //     io.fmul_3_out_3 := io.v000
//                 //     io.fmul_3_out_4 := io.v000
//                 //     io.fmul_3_out_5 := io.v000
//                 //     io.fmul_3_out_6 := io.v000
//                 //     io.fadd_3_out_1 := io.v000
//                 //     io.fadd_3_out_2 := io.v000
//                 //     io.fadd_3_out_3 := io.v000
//                 //     io.fadd_3_out_Oy := io.v000
//                 //     io.fadd_3_out_Dy := io.v000
//                 //     io.fadd_3_out_v_temp := io.v000
//                 //     io.fadd_3_out_v := io.v000
//                 //     io.fadd_3_out_v_u := io.v000
//                 //     io.V_bigger_0 := false.B
//                 //     io.V_U_smaller_1 := false.B
//                 //     io.hitResult := 0.U
//                 // }
//         }
//         // .otherwise{
//         //     io.fmul_1_out_1 := io.v000
//         //     io.fmul_1_out_2 := io.v000
//         //     io.fmul_1_out_3 := io.v000
//         //     io.fmul_1_out_4 := io.v000
//         //     io.fmul_1_out_5 := io.v000
//         //     io.fmul_1_out_6 := io.v000
//         //     io.fadd_1_out_1 := io.v000
//         //     io.fadd_1_out_2 := io.v000
//         //     io.fadd_1_out_3 := io.v000
//         //     io.fadd_1_out_Oz := io.v000
//         //     io.fadd_1_out_4 := io.v000
//         //     io.t_out := io.v000
//         //     io.T_bigger_tmin := false.B
//         //     io.T_smaller_hitT := false.B
//         //     io.fmul_2_out_1 := io.v000
//         //     io.fmul_2_out_2 := io.v000
//         //     io.fmul_2_out_3 := io.v000
//         //     io.fmul_2_out_4 := io.v000
//         //     io.fmul_2_out_5 := io.v000
//         //     io.fmul_2_out_6 := io.v000
//         //     io.fadd_2_out_1 := io.v000
//         //     io.fadd_2_out_2 := io.v000
//         //     io.fadd_2_out_3 := io.v000
//         //     io.fadd_2_out_Ox := io.v000
//         //     io.fadd_2_out_Dx := io.v000
//         //     io.fadd_2_out_u_temp := io.v000
//         //     io.fadd_2_out_u := io.v000
//         //     io.U_bigger_0 := false.B
//         //     io.U_smaller_1 := false.B
//         //     io.fmul_3_out_1 := io.v000
//         //     io.fmul_3_out_2 := io.v000
//         //     io.fmul_3_out_3 := io.v000
//         //     io.fmul_3_out_4 := io.v000
//         //     io.fmul_3_out_5 := io.v000
//         //     io.fmul_3_out_6 := io.v000
//         //     io.fadd_3_out_1 := io.v000
//         //     io.fadd_3_out_2 := io.v000
//         //     io.fadd_3_out_3 := io.v000
//         //     io.fadd_3_out_Oy := io.v000
//         //     io.fadd_3_out_Dy := io.v000
//         //     io.fadd_3_out_v_temp := io.v000
//         //     io.fadd_3_out_v := io.v000
//         //     io.fadd_3_out_v_u := io.v000
//         //     io.V_bigger_0 := false.B
//         //     io.V_U_smaller_1 := false.B
//         //     io.hitResult := 0.U
//         // }
//     }

//     // class TesterSimple (dut:Ray_Tri_Intersection_Pipeline_1) extends PeekPokeTester(dut){

//     // poke(dut.io.nodeIdx,-1194117.S)

//     // poke(dut.io.origx,1103066426.S)
//     // poke(dut.io.origy,3233975986L.S)
//     // poke(dut.io.origz,1036689676.S)

//     // poke(dut.io.dirx,1052149245.S)
//     // poke(dut.io.diry,1063923780.S)
//     // poke(dut.io.dirz,1044542018.S)

//     // // poke(dut.io.v00.x,0.S)
//     // poke(dut.io.triWoop.w0(0),0.S)
//     // poke(dut.io.triWoop.w0(1),3149275072L.S)
//     // poke(dut.io.triWoop.w0(2),0L.S)
//     // poke(dut.io.triWoop.w0(3),3178284920L.S)

//     // poke(dut.io.invDz,3276057193L.S)

//     // poke(dut.io.tmin,0.S)
//     // poke(dut.io.hitT,1325400063.S)

//     // poke(dut.io.triWoop.w1(0),1022299376.S)
//     // poke(dut.io.triWoop.w1(1),0.S)
//     // poke(dut.io.triWoop.w1(2),0.S)
//     // poke(dut.io.triWoop.w1(3),1032591068.S)

//     // poke(dut.io.triWoop.w2(0),0.S)
//     // poke(dut.io.triWoop.w2(1),0.S)
//     // poke(dut.io.triWoop.w2(2),1044581410.S)
//     // poke(dut.io.triWoop.w2(3),3155954304L.S)
    
//     // step(1)
//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(2)  ~~~~~~~~~~~~~~~~~~" )
//     // println("origx*v00.x = " +peek(dut.io.fmul_1_out_1).toString)
//     // println("origy*v00.y = " +peek(dut.io.fmul_1_out_2).toString)
//     // println("origz*v00.z = " +peek(dut.io.fmul_1_out_3).toString)
//     // println("dirx*v00.x  = " +peek(dut.io.fmul_1_out_4).toString)
//     // println("diry*v00.y  = " +peek(dut.io.fmul_1_out_5).toString)
//     // println("dirz*v00.z  = " +peek(dut.io.fmul_1_out_6).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(3)  ~~~~~~~~~~~~~~~~~~" )
//     // println("add_1 = " +peek(dut.io.fadd_1_out_1).toString)
//     // println("add_2 = " +peek(dut.io.fadd_1_out_2).toString)
//     // println("add_3 = " +peek(dut.io.fadd_1_out_3).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(4)  ~~~~~~~~~~~~~~~~~~" )
//     // println("  Oz  = " +peek(dut.io.fadd_1_out_Oz).toString)
//     // println("add_4 = " +peek(dut.io.fadd_1_out_4).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(5)  ~~~~~~~~~~~~~~~~~~" )
//     // println("  t   = " +peek(dut.io.t_out).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(6)  ~~~~~~~~~~~~~~~~~~" )
//     // println("t is bigger than tmin or not = " +peek(dut.io.T_bigger_tmin).toString)
//     // println("t is smaller than hitT or not = " +peek(dut.io.T_smaller_hitT).toString)

//     // step(1)
//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(8)  ~~~~~~~~~~~~~~~~~~" )
//     // println("origx*v11.x = " +peek(dut.io.fmul_2_out_1).toString)
//     // println("origy*v11.y = " +peek(dut.io.fmul_2_out_2).toString)
//     // println("origz*v11.z = " +peek(dut.io.fmul_2_out_3).toString)
//     // println("dirx*v11.x  = " +peek(dut.io.fmul_2_out_4).toString)
//     // println("diry*v11.y  = " +peek(dut.io.fmul_2_out_5).toString)
//     // println("dirz*v11.z  = " +peek(dut.io.fmul_2_out_6).toString)
    
//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(9)  ~~~~~~~~~~~~~~~~~~" )
//     // println("v11.w + origx*v11.x = " +peek(dut.io.fadd_2_out_1).toString)
//     // println("origy*v11.y + origz*v11.z = " +peek(dut.io.fadd_2_out_2).toString)
//     // println("dirx*v11.x + diry*v11.y = " +peek(dut.io.fadd_2_out_3).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(10)  ~~~~~~~~~~~~~~~~~~" )
//     // println("         Ox      = " +peek(dut.io.fadd_2_out_Ox).toString)
//     // println("         Dx      = " +peek(dut.io.fadd_2_out_Dx).toString)
    
//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(11)  ~~~~~~~~~~~~~~~~~~" )
//     // println(" the temp of u   = " +peek(dut.io.fadd_2_out_u_temp).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(12)  ~~~~~~~~~~~~~~~~~~" )
//     // println("        u        = " +peek(dut.io.fadd_2_out_u).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(13)  ~~~~~~~~~~~~~~~~~~" )
//     // println("U is bigger than 0 or not = " +peek(dut.io.U_bigger_0).toString)
//     // println("U is smaller than 1 or not = " +peek(dut.io.U_smaller_1).toString)

//     // step(1)
//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(15)  ~~~~~~~~~~~~~~~~~~" )
//     // println("origx*v22.x = " +peek(dut.io.fmul_3_out_1).toString)
//     // println("origy*v22.y = " +peek(dut.io.fmul_3_out_2).toString)
//     // println("origz*v22.z = " +peek(dut.io.fmul_3_out_3).toString)
//     // println("dirx*v22.x  = " +peek(dut.io.fmul_3_out_4).toString)
//     // println("diry*v22.y  = " +peek(dut.io.fmul_3_out_5).toString)
//     // println("dirz*v22.z  = " +peek(dut.io.fmul_3_out_6).toString)
    
//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(16)  ~~~~~~~~~~~~~~~~~~" )
//     // println("v22.w + origx*v22.x = " +peek(dut.io.fadd_3_out_1).toString)
//     // println("origy*v22.y + origz*v22.z = " +peek(dut.io.fadd_3_out_2).toString)
//     // println("dirx*v22.x + diry*v22.y = " +peek(dut.io.fadd_3_out_3).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(17)  ~~~~~~~~~~~~~~~~~~" )
//     // println("         Oy      = " +peek(dut.io.fadd_3_out_Oy).toString)
//     // println("         Dy      = " +peek(dut.io.fadd_3_out_Dy).toString)
    
//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(18)  ~~~~~~~~~~~~~~~~~~" )
//     // println(" the temp of v   = " +peek(dut.io.fadd_3_out_v_temp).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(19)  ~~~~~~~~~~~~~~~~~~" )
//     // println("        v        = " +peek(dut.io.fadd_3_out_v).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(20)  ~~~~~~~~~~~~~~~~~~" )
//     // println("      u+v        = " +peek(dut.io.fadd_3_out_v_u).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(21)  ~~~~~~~~~~~~~~~~~~" )
//     // println("V is bigger than 0 or not = " +peek(dut.io.V_bigger_0).toString)
//     // println("V+U is smaller than 1 or not = " +peek(dut.io.V_U_smaller_1).toString)

//     // step(1)
//     // println("~~~~~~~~~~~~~~~~~  setp(22)  ~~~~~~~~~~~~~~~~~~" )
//     // println("    hitResult        = " +peek(dut.io.hitResult).toString)
//     // }
//     // object TesterSimple extends App{
//     //     chisel3. iotesters.Driver(()=> new Ray_Tri_Intersection_Pipeline_1()){ c =>
//     //         new TesterSimple(c)
//     //     }
//     // }

// //  class WaveformTester (dut: Ray_Tri_Intersection_Pipeline_1 ) extends
// //     PeekPokeTester (dut) {
// //     step(1)
// //     poke(dut.io.nodeIdx,-1194117.S)

// //     poke(dut.io.origx,1103066426.S)
// //     poke(dut.io.origy,3233975986L.S)
// //     poke(dut.io.origz,1036689676.S)

// //     poke(dut.io.dirx,1052149245.S)
// //     poke(dut.io.diry,1063923780.S)
// //     poke(dut.io.dirz,1044542018.S)

// //     // poke(dut.io.v00.x,0.S)
// //     poke(dut.io.triWoop.w0(0),0.S)
// //     poke(dut.io.triWoop.w0(1),3149275072L.S)
// //     poke(dut.io.triWoop.w0(2),0L.S)
// //     poke(dut.io.triWoop.w0(3),3178284920L.S)

  

// //     poke(dut.io.tmin,0.S)
// //     poke(dut.io.hitT,1325400063.S)

// //     poke(dut.io.triWoop.w1(0),1022299376.S)
// //     poke(dut.io.triWoop.w1(1),0.S)
// //     poke(dut.io.triWoop.w1(2),0.S)
// //     poke(dut.io.triWoop.w1(3),1032591068.S)

// //     poke(dut.io.triWoop.w2(0),0.S)
// //     poke(dut.io.triWoop.w2(1),0.S)
// //     poke(dut.io.triWoop.w2(2),1044581410.S)
// //     poke(dut.io.triWoop.w2(3),3155954304L.S)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     poke(dut.io.invDz,3276057193L.S)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)    
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)
// //     step(1)    
// //     step(1)
// //     step(1)    
// //     step(1)

// //     }
// // class WaveformSpec extends FlatSpec with Matchers {
// //     "Waveform" should "pass" in {
// //     Driver.execute(Array("--generate-vcd-output", "on"), () =>
// //             new Ray_Tri_Intersection_Pipeline_1 ()) { c =>
// //         new WaveformTester (c)
// //     } should be (true)
// //     }
// // }//生成.vcd