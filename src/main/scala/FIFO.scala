package hardfloat
import Chisel._
import consts._
// import chisel3 ._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
import chisel3.iotesters.PeekPokeTester
import chisel3.iotesters.Driver

class FIFO(val width : Int, val size : Int) extends Module {
  val io = IO(new Bundle {
    val datain = Input(SInt(width.W))
    val dataout = Output(SInt(width.W))
    val wr = Input(Bool())
    val rd = Input(Bool())
    val full = Output(Bool())
    val empty = Output(Bool())
    val enable= Output(Bool())
  })

  val count = RegInit(0.U(width.W))
  val mem = Mem(size, SInt(width.W))
  val wPointer = RegInit(0.U(width.W))
  val rPointer = RegInit(0.U(width.W))
  val dataout = RegInit(0.S(width.W))
  val enable    = RegInit(0.U(1.W))
  val empty     = RegInit(0.U(1.W))

  def indexAdd(index : UInt) : UInt = {
      Mux(index === (size - 1).U, 0.U, index + 1.U)
  }

  when(io.wr === true.B && io.rd === true.B) {
    when(count === 0.U) { 
        dataout := io.datain  
        enable:= 1.U
     }.otherwise {
      dataout := mem(rPointer)
      rPointer := indexAdd(rPointer)
      mem(wPointer) := io.datain
      enable      := 1.U
      wPointer := indexAdd(wPointer)
    } 
  } .elsewhen (io.wr === true.B && io.rd === false.B) {
    dataout := 0.S
    enable    :=0.U
    when(count < size.U) {
      mem(wPointer) := io.datain
      wPointer := indexAdd(wPointer)
      count := count + 1.U
      enable:=0.U    
    }
  } .elsewhen (io.wr === false.B && io.rd === true.B) {
    when(count > 0.U) {
      dataout := mem(rPointer)
      rPointer := indexAdd(rPointer)
      enable:=1.U    
      count := count - 1.U
    } .otherwise {
      dataout := 0.S
      enable:=0.U    
    }
  } .otherwise {
    dataout := 0.S
    enable:=0.U    
  }
  io.enable   := enable
  io.dataout := dataout
  io.full := (size.U === count)
  io.empty := (count === 0.U)
  //  when (count === 0.U){
  //   empty := 1.U
  // }.otherwise{
  //   empty := 0.U
  // }
  // io.empty
}
// object Main extends App {
//   chisel3.Driver.execute(args, () => new FIFO(8,16))
// }
class FIFOTester (dut:FIFO) extends PeekPokeTester(dut){
        poke(dut.io.datain,1.U)
        poke(dut.io.wr,1.S)
        step(1)
        poke(dut.io.datain,2.U)
        poke(dut.io.wr,1.S)
        step(1)
        poke(dut.io.datain,3.U)
        poke(dut.io.wr,1.S)
        step(1)
        poke(dut.io.wr,0.S)
        poke(dut.io.rd,1.S)
         step(2)
        poke(dut.io.rd,1.S)
         step(1)
        poke(dut.io.rd,1.S)
        step(70)
}
object FIFOtest extends App {
  chisel3.iotesters.Driver.execute(args, () => new FIFO(5,3))(c => new FIFOTester(c))
}