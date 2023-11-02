
// package hardfloat
// import Chisel._
// import consts._
// // import chisel3 ._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
 


// class fdiv_Simple (dut:FDIV_ds) extends PeekPokeTester(dut){
// poke(dut.io.inValid,1.U)
// poke(dut.io.a,1067450368.U)
// poke(dut.io.b,1073741824.U)
// step(1)
// poke(dut.io.inValid,0.U)
// // poke(dut.io.hiT_in,3.S)
// // poke(dut.io.Oz_in,4.U)
// // poke(dut.io.invDz_div,1056964608.U)
// // poke(dut.io.v11_in,5.U)
// // poke(dut.io.v22_in,6.U)
// // poke(dut.io.Div_en,1.U)
// step(24)
// poke(dut.io.inValid,1.U)

// poke(dut.io.a,1067450322.U)
// poke(dut.io.b,1073741822.U)

// step(1)
// poke(dut.io.inValid,0.U)
// step(50)

// }
// object FDIV_dstest extends App {
//   chisel3.iotesters.Driver.execute(args, () => new FDIV_ds())(c => new fdiv_Simple(c))
// }
