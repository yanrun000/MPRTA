package hardfloat
import Chisel._
import consts._
// import chisel3 ._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
import chisel3.iotesters.PeekPokeTester
import chisel3.iotesters.Driver
 

 
class TesterSimple (dut:FDIV) extends PeekPokeTester(dut){
poke(dut.io.nodeid_leaf,1.S)
poke(dut.io.rayid_leaf,2.S)
poke(dut.io.hiT_in,3.S)
poke(dut.io.Oz_in,4.U)
poke(dut.io.invDz_div,1056964608.U)
poke(dut.io.v11_in,5.U)
poke(dut.io.v22_in,6.U)
poke(dut.io.Div_en,1.U)
step(50)
}
object fdivtest extends App {
  chisel3.iotesters.Driver.execute(args, () => new FDIV())(c => new TesterSimple(c))
}