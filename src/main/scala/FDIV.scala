
package hardfloat
import Chisel._
// import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
//import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
class FDIV() extends Module{
	val io = IO(new Bundle{
		val inValid         = Input(Bool()) //输入是否有效
		val a                     = Input(Bits(32.W))
		val b                    = Input(Bits(32.W))
		// val positive      = Input(Bool())
		val v11               = new Float().asInput
		val v22               =  new Float().asInput
		val ray_in          =  Input(UInt(32.W))
		val Oz                  = Input(UInt(32.W))
		val ray_o_in     = new ray().asInput
		val ray_d_in     = new ray().asInput
		val node_id_in= Input(SInt(32.W))
		val hitT_in         = Input(UInt(32.W))   

		// val inValid_out             = Output(Bool())//表示在忙
		val outValid             = Output(Bool())//当前脉冲输出有效
		val out                       = Output(Bits(32.W))
		val v11_out              = new Float().asOutput
		val v22_out              = new Float().asOutput
		val ray_out              = Output(UInt(32.W))
		val Oz_out               = Output(UInt(32.W))
		val ray_o_out         = new ray().asOutput
		val ray_d_out         = new ray().asOutput
		// val Oz_out               = Output(UInt(32.W))
		val node_id_out   = Output(SInt(32.W))
		val hitT_out            = Output(UInt(32.W))
		val counter_fdiv   = Output(UInt(64.W))
	})

	val  fdiv  = Module(new fpDiv(32))
	//stage(1)
	val v11_temp_1          = RegInit(0.U(128.W))
	val v22_temp_1          = RegInit(0.U(128.W))
	val ray_temp_1           = RegInit(0.U(32.W))
	val Oz_temp_1            = RegInit(0.U(32.W))
	val ray_o_temp_1      = RegInit(0.U(96.W))
	val ray_d_temp_1      = RegInit(0.U(96.W))
	val node_id_temp_1 = RegInit(0.S(32.W))
	val hitT_temp_1          = RegInit(0.U(32.W))
	val inValid_1				   = RegInit(0.U(1.W))
	// val positive               = RegInit(0.U(1.W))
	v11_temp_1                             := chisel3.util.Cat(io.v11.w,io.v11.z,io.v11.y,io.v11.x)
	v22_temp_1                             := chisel3.util.Cat(io.v22.w,io.v22.z,io.v22.y,io.v22.x)
	ray_temp_1                              := io.ray_in
	Oz_temp_1                               := io.Oz
	ray_o_temp_1                         := chisel3.util.Cat(io.ray_o_in.z,io.ray_o_in.y,io.ray_o_in.x)
	ray_d_temp_1                         := chisel3.util.Cat(io.ray_d_in.z,io.ray_d_in.y,io.ray_d_in.x)
	node_id_temp_1                    := io.node_id_in
	hitT_temp_1                             := io.hitT_in

	fdiv.io.in1								:= io.a
	fdiv.io.in2								:= io.b
	//counter
	val counter					   = RegInit(0.U(64.W))
	when(io.inValid){
		counter 					:= counter +1.U
	}.otherwise{
		counter 					:= counter
	}

	io.counter_fdiv				:= counter
	
	val v11_temp_2          = RegInit(0.U(128.W))
	val v22_temp_2          = RegInit(0.U(128.W))
	val ray_temp_2           = RegInit(0.U(32.W))
	val Oz_temp_2            = RegInit(0.U(32.W))
	val ray_o_temp_2      = RegInit(0.U(96.W))
	val ray_d_temp_2      = RegInit(0.U(96.W))
	val node_id_temp_2 = RegInit(0.S(32.W))
	val hitT_temp_2          = RegInit(0.U(32.W))
	val inValid_2				   = RegInit(0.U(1.W))

	v11_temp_2					:= v11_temp_1
	v22_temp_2					:= v22_temp_1
	ray_temp_2				   	 := ray_temp_1
	Oz_temp_2				     := Oz_temp_1
	ray_o_temp_2		      := ray_o_temp_1
	ray_d_temp_2		      := ray_d_temp_1
	node_id_temp_2		  := node_id_temp_1
	hitT_temp_2					:= hitT_temp_1
	inValid_2						 := inValid_1

	val v11_temp_3          = RegInit(0.U(128.W))
	val v22_temp_3          = RegInit(0.U(128.W))
	val ray_temp_3           = RegInit(0.U(32.W))
	val Oz_temp_3            = RegInit(0.U(32.W))
	val ray_o_temp_3      = RegInit(0.U(96.W))
	val ray_d_temp_3      = RegInit(0.U(96.W))
	val node_id_temp_3 = RegInit(0.S(32.W))
	val hitT_temp_3          = RegInit(0.U(32.W))
	val inValid_3				   = RegInit(0.U(1.W))

	v11_temp_3					:= v11_temp_2
	v22_temp_3					:= v22_temp_2
	ray_temp_3				   	 := ray_temp_2
	Oz_temp_3				     := Oz_temp_2
	ray_o_temp_3		      := ray_o_temp_2
	ray_d_temp_3		      := ray_d_temp_2
	node_id_temp_3		  := node_id_temp_2
	hitT_temp_3  				:= hitT_temp_2
	inValid_3						 := inValid_2

	val v11_temp_4          = RegInit(0.U(128.W))
	val v22_temp_4          = RegInit(0.U(128.W))
	val ray_temp_4           = RegInit(0.U(32.W))
	val Oz_temp_4            = RegInit(0.U(32.W))
	val ray_o_temp_4      = RegInit(0.U(96.W))
	val ray_d_temp_4      = RegInit(0.U(96.W))
	val node_id_temp_4 = RegInit(0.S(32.W))
	val hitT_temp_4          = RegInit(0.U(32.W))
	val inValid_4				   = RegInit(0.U(1.W))

	v11_temp_4					:= v11_temp_3
	v22_temp_4					:= v22_temp_3
	ray_temp_4				   	 := ray_temp_3
	Oz_temp_4				     := Oz_temp_3
	ray_o_temp_4		      := ray_o_temp_3
	ray_d_temp_4		      := ray_d_temp_3
	node_id_temp_4		  := node_id_temp_3
	hitT_temp_4  				:= hitT_temp_3
	inValid_4						 := inValid_3

	io.out								:= fdiv.io.out
	io.outValid					   := inValid_4
	io.v11_out.x                           := v11_temp_4(31,0)
	io.v11_out.y                           := v11_temp_4(63,32)
	io.v11_out.z                           := v11_temp_4(95,64)
	io.v11_out.w                          := v11_temp_4(127,96)
	io.v22_out.x                           := v22_temp_4(31,0)
	io.v22_out.y                           := v22_temp_4(63,32)
	io.v22_out.z                           := v22_temp_4(95,64)
	io.v22_out.w                         := v22_temp_4(127,96)
	// io.v22_out                              := v22_temp
	io.ray_out                               := ray_temp_4
	io.Oz_out                                := Oz_temp_4
	io.ray_o_out.x                      := ray_o_temp_4(31,0)
	io.ray_o_out.y                      := ray_o_temp_4(63,32)
	io.ray_o_out.z                      := ray_o_temp_4(95,64)
	io.ray_d_out.x                      := ray_d_temp_4(31,0)
	io.ray_d_out.y                      := ray_d_temp_4(63,32)
	io.ray_d_out.z                      := ray_d_temp_4(95,64)
	io.node_id_out					  := node_id_temp_4
	io.hitT_out 							:= hitT_temp_4
}

class fdiv_tester (dut:FDIV) extends PeekPokeTester(dut){
  poke(dut.io.inValid,1.U)
  poke(dut.io.a,1067450368.U)
  poke(dut.io.b,1073741824.U)
//   step(1)
  poke(dut.io.inValid,0.U)
  poke(dut.io.hitT_in,3.S)
  poke(dut.io.Oz,4.U)
  // poke(dut.io.invDz_div,1056964608.U)
  poke(dut.io.ray_in,10.U)
  poke(dut.io.node_id_in,20.S)
//   poke(dut.io.v22,6.U)
//   poke(dut.io.Div_en,1.U)
//   step(24)
//   poke(dut.io.inValid,1.U)

  // poke(dut.io.a,1067450322.U)
//   poke(dut.io.b,1073741822.U)

  step(1)
  poke(dut.io.inValid,0.U)
  step(50)

  }

object FDIV_tester extends App {
  chisel3.iotesters.Driver.execute(args, () => new FDIV())(c => new fdiv_tester(c))
}
object FDIV extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new FDIV())
}
