// Süleyman Savas, Halmstad University
// 2017-02-13


/*
	If the inputs are given in the first cycle
	the result comes out at fifth cycle
*/

package hardfloat

import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
//import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class fpDiv(val w : Int) extends Module{ //w = 32
	val io = IO(new Bundle{
		val in1  = Input(UInt(w.W))	
		val in2  = Input(UInt(w.W))
		val out  = Output(UInt( w.W))
	})

	val inverter   = Module(new fpInverter(23))
	val FMUL_20 = Module(new ValExec_MulAddRecF32_mul)

	inverter.io.in1 := io.in2(22, 0)	//mantissa 
	// delay input1 due to the inverter
	// val in1Reg0 = Reg(init = 0.U, next = io.in1)	// stage 0
	// val in1Reg1 = Reg(init = 0.U, next = in1Reg0)	// stage 1
	// val in1Reg2 = Reg(init = 0.U, next = in1Reg1)	// stage 2
	// val in1Reg3 = Reg(init = 0.U, next = in1Reg2)	// stage 3

	val in1Reg0			= RegInit(0.U(32.W))//stage0
	in1Reg0				   :=  io.in1
	val in1Reg1			= RegInit(0.U(32.W))//stage1
	in1Reg1				   :=  in1Reg0
	val in1Reg2			= RegInit(0.U(32.W))//stage2
	in1Reg2				   :=  in1Reg1
	val in1Reg3			= RegInit(0.U(32.W))//stage3
	in1Reg3				   :=  in1Reg2

	// delay input2 exponent and sign due to the inverter
	// val in2ExpReg0  = Reg(init = 0.U, next = io.in2(30,23))	// stage 0
	// val in2SignReg0 = Reg(init = 0.U, next = io.in2(31))	// stage 0
	// val in2ExpReg1  = Reg(init = 0.U, next = in2ExpReg0)	// stage 1
	// val in2SignReg1 = Reg(init = 0.U, next = in2SignReg0)	// stage 1
	// val in2ExpReg2  = Reg(init = 0.U, next = in2ExpReg1)	// stage 2
	// val in2SignReg2 = Reg(init = 0.U, next = in2SignReg1)	// stage 2
	// val in2ExpReg3  = Reg(init = 0.U, next = in2ExpReg2)	// stage 3
	// val in2SignReg3 = Reg(init = 0.U, next = in2SignReg2)	// stage 3
	// val invMantReg  = Reg(init = 0.U, next = inverter.io.out(23, 0)) //stage 3



	val in2ExpReg0		= RegInit(0.U(8.W))//stage 0
	in2ExpReg0			   := io.in2(30,23)
	val in2SignReg0		= RegInit(0.U(1.W))//stage 0
	in2SignReg0			  := io.in2(31)
	val in2ExpReg1  	=  RegInit(0.U(8.W))//stage 1
	in2ExpReg1			  := in2ExpReg0
	val in2SignReg1		= RegInit(0.U(1.W))//stage 1
	in2SignReg1			  := in2SignReg0

	val in2ExpReg2  	=  RegInit(0.U(8.W))//stage 2
	in2ExpReg2			  := in2ExpReg1
	val in2SignReg2		= RegInit(0.U(1.W))//stage 2
	in2SignReg2			  := in2SignReg1

	val in2ExpReg3  	=  RegInit(0.U(8.W))//stage 3
	in2ExpReg3			  := in2ExpReg2
	val in2SignReg3		= RegInit(0.U(1.W))//stage 3
	in2SignReg3			  := in2SignReg2

	val invMantReg		= RegInit(0.U(24.W))
	invMantReg				:= inverter.io.out(23,0)

	val invMant       = invMantReg
	val negExpTmp     = 254.U - in2ExpReg3
	val negExp        = Mux(invMant === 0.U, negExpTmp, negExpTmp - 1.U)
	// we should raise an execption if both mantissa and exponent are zero (the final result should be inf

	// multiplier.io.a := in1Reg3
	// multiplier.io.b := Cat(in2SignReg3, negExp, invMant(23, 1))
	// skipping msb of inverter (multiplying mantissa by 2)
	FMUL_20.io.a    := in1Reg3
    FMUL_20.io.b    :=  Cat(in2SignReg3, negExp, invMant(23, 1))
    FMUL_20.io.roundingMode := 0.U
    FMUL_20.io.detectTininess := 1.U
    FMUL_20.io.expected.out := 0.U
    FMUL_20.io.expected.exceptionFlags := 0.U 
	io.out := fNFromRecFN(8,24,FMUL_20.io.actual.out)
/*	
	printf("\nin1: %d, in2: %d\n", io.in1, io.in2)
	printf("inverter in   : %d out: %d\n", io.in2(22, 0), inverter.io.out)
	printf("invmant       : %d \n", invMant)
	printf("multiplier a  : %d\n", in1Reg3)
	printf("multiplier b  : %d\n", Cat(in2SignReg3, negExp, inverter.io.out(23,1)))
	//printf("exponent2: %d\n", exponent2)
	printf("negExp        : %d, in1 exp %d\n", negExp, io.in1(30, 23))
	printf("multiplier out: %d\n", multiplier.io.res)
	*/
//	printf("%d\n", multiplier.io.res)

}
// object fpDiv extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new fpDiv(32))
// }


class fpDivTest(c: fpDiv) extends PeekPokeTester(c) {

	poke(c.io.in1, 1096155136.U)		// 13.375
	poke(c.io.in2, 1091829760.U)		// 9.25
        //1.445945946      3FB914C1
 	step(1)
	// poke(c.io.in1, 2139095039.U)
	// poke(c.io.in2, 2139095039.U)	
        poke(c.io.in1, 1199570944.U)	//65536
        poke(c.io.in2, 1106771968.U)	//31

    // //2114.064453125         45042108
    step(1)
	poke(c.io.in1, 1082130431.U)	// exp = 1, mantissa = 28 ones  3.9999997615814
	poke(c.io.in2, 1106771968.U)	// 31
    //     //0.129032258 3E042107
    	step(1)
	poke(c.io.in1, 1207435265.U)//126976.0078125
	poke(c.io.in2, 1082130431.U)//3.9999997615814
 
    //31744.002032485  46F80001

        	step(1)
	poke(c.io.in1, 3268169302L.U)//-102.153
	poke(c.io.in2, 1108137738.U)//35.21
 //-2.901249645  4039AE12取反
	step(10)
}

object fpDiv_Test extends App {
  chisel3.iotesters.Driver.execute(args, () => new fpDiv(32))(c => new fpDivTest(c))
}
// 	expect(c.io.out, 1123512320.U)		//123.1875
// 	step(1)


// 	poke(c.io.in1, 1065353216.U)	//65536
// 	poke(c.io.in2, 0.U)	//0
// 	step(1)
// 	expect(c.io.out, 0.U)

	
// 	step(1)
// 	expect(c.io.out, 0.U)

// 	poke(c.io.in1, 1199570944.U)	//65536
// 	poke(c.io.in2, 1106771968.U)	//31
// 	step(1)
// 	expect(c.io.out, 0.U)

// 	//poke(c.io.in2, 1098383360.U) // 15.5
// 	poke(c.io.in1, 2139095039.U)	// biggest single precision float
// 	poke(c.io.in2, 1207435264.U)
// 	step(1)
// 	expect(c.io.out, 0.U)

// 	poke(c.io.in1, 2139095039.U)
// 	poke(c.io.in2, 1200035266.U)	
	
// 	step(1)
// 	expect(c.io.out, 0.U)

// 	poke(c.io.in1, 1082130431.U)	// exp = 1, mantissa = 28 ones
// 	poke(c.io.in2, 1106771968.U)	// 31

// 	step(1)
// 	expect(c.io.out, 0.U)
// 	poke(c.io.in1, 1207435265.U)
// 	poke(c.io.in2, 2139095039.U)
// 	step(1)
// 	expect(c.io.out, 0.U)
// 	step(1)
// 	expect(c.io.out, 0.U)
// 	poke(c.io.in1, 1207959552.U)
// //	poke(c.io.in1, 1200035266.U)
// 	step(1)
// 	expect(c.io.out, 0.U)

// 	poke(c.io.in1, 0.U)
// 	poke(c.io.in2, 0.U)
// 	step(1)
// 	expect(c.io.out, 0.U)
// 	step(1)
// 	expect(c.io.out, 0.U)
// 	step(1)
// 	expect(c.io.out, 0.U)
// 	step(1)
// 	expect(c.io.out, 0.U)

	//poke(c.io.in2, 0.U)

// 1125056512 = 143.0

// 1199570944 = 65536
// 1207959552 = 131072
// 1106771968 = 31
/*
	for (i <- 1199570944 until 1207959552){
//	for (i <- 1199570944 until 1200095232){
		poke(c.io.in1, i)
		poke(c.io.in2, 1106771968.U)
		step(1)

	}
*/

/*
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
*/
// }

// object fpDiv {
//   def main(args: Array[String]): Unit = {
//     if (!Driver(() => new fpDiv(32))(c => new fpDivTest(c))) System.exit(1)
//   }
// }










