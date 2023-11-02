// import hardfloat._
// // import Chisel._
// import chisel3._
// import chisel3 . iotesters . PeekPokeTester
// import chisel3 . iotesters . Driver
// import org. scalatest ._


// class DeviceUnderTest extends Module {
// val io = IO(new Bundle {
// val a = Input(UInt (2.W))
// val b = Input(UInt (2.W))
// val out = Output (UInt (2.W))
// })
// io.out := io.a 
// }

// class WaveformCounterTester (dut: DeviceUnderTest ) extends
// PeekPokeTester (dut) {
// for (a <- 0 until 4) {
//     for (b <- 0 until 4) {
//         poke(dut.io.a, a)
//         poke(dut.io.b, b)
//         step (1)
//         }
//     }
// }

// class WaveformCounterSpec extends FlatSpec with Matchers {
// " WaveformCounter " should "pass" in {
// Driver . execute (Array("--generate -vcd - output ", "on"), () =>
// new DeviceUnderTest ()) { c =>
// new WaveformCounterTester (c)
// } should be (true)
// }
// }