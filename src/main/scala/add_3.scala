// import hardfloat._
// import chisel3 ._
// import chisel3 . iotesters ._


// class UI_32TO_REC32 extends Module{
//     val io = IO(new Bundle {
//         val in = Input(UInt(32.W))
//         val roundingMode = Input(UInt(3.W))
//         // val detectTininess = Input(UInt(1.W))
        
//         val expected = new Bundle{ 
//             val out = Input(UInt(32.W))
//             val exceptionFlags = Input(UInt(5.W))
//             // val recOut = Output(UInt(33.W))
//         }

//         val actual = new Bundle {
//             val out = Output(UInt(33.W))
//             val exceptionFlags = Output(UInt(5.W))
//         }

//         val check = Output(Bool())
//         val pass = Output(Bool())
//     })
// val ui_32to_rec32 = Module (new ValExec_UI32ToRecF32)
//     ui_32to_rec32.io.in := io.in
//     ui_32to_rec32.io.roundingMode := io.roundingMode
//     // ui_32to_rec32.io.detectTininess := io.detectTininess

//     ui_32to_rec32.io.expected.out := io.expected.out
//     ui_32to_rec32.io.expected.exceptionFlags := io.expected.exceptionFlags
//     // io.expected.recOut := ui_32to_rec32.io.expected.recOut  
    
//     io.actual.out := ui_32to_rec32.io.actual.out
//     io.actual.exceptionFlags := ui_32to_rec32.io.actual.exceptionFlags

//     io.check := ui_32to_rec32.io.check
//     io.pass := ui_32to_rec32.io.pass
// }

// class TesterSimple (dut:UI_32TO_REC32) extends PeekPokeTester(dut){
// poke(dut.io.in,1073741824.U)
// // poke(dut.io.b,1.U)
// poke(dut.io.roundingMode,0.U)
// step(1)
// println("Result is :" +peek(dut.io.actual.out).toString)
// }
// object TesterSimple extends App{
//     chisel3. iotesters.Driver(()=> new UI_32TO_REC32()){ c =>
//         new TesterSimple(c)
//     }
// }


// // object add extends App {
// //   (new chisel3.stage.ChiselStage).emitVerilog(new add())
// // }
package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class memory (val  w : Int )extends Module{  //w用来做深度的参数
    val io = IO(new Bundle{
        val id_1      = Input(SInt(32.W))
        val id_2      = Input(SInt(32.W))
        val out_1   = Output(UInt(32.W))
        val out_2   = Output(UInt(32.W))
        val out_3   = Output(UInt(32.W))
        val out_4   = Output(UInt(32.W))
        val wrEna        = Input(Bool())
        val wrData      = Input(UInt(32.W))
        val wrAddr      = Input(UInt(32.W))
        val a                   = Input(SInt(32.W))
        val b                   = Output(Bool())
    })

    val mem = SyncReadMem(w,UInt(32.W))

    io.out_1 := mem.read(io.id_1.asUInt)
    io.out_2 := mem.read((io.id_1+1.S).asUInt)

    io.out_3 := mem.read(io.id_2.asUInt)
    io.out_4 := mem.read((io.id_2+1.S).asUInt)

    when(io.wrEna){//整个系统在最初时给BVH的输入
        mem.write(io.wrAddr , io.wrData)
    }

    io.b            := positive(io.a)
}
 class memtester (dut:memory) extends PeekPokeTester(dut){
        poke(dut.io.wrEna,true.B)
        poke(dut.io.wrData,1.S)
        poke(dut.io.wrAddr,1.U)
        poke(dut.io.a,349312.U)
        step(1)
        poke(dut.io.wrEna,true.B)
        poke(dut.io.wrData,2.S)
        poke(dut.io.wrAddr,2.U)
        step(1)
        poke(dut.io.wrEna,true.B)
        poke(dut.io.wrData,3.S)
        poke(dut.io.wrAddr,3.U)
        step(1)
        poke(dut.io.wrEna,true.B)
        poke(dut.io.wrData,4.S)
        poke(dut.io.wrAddr,4.U)
        step(1)
        poke(dut.io.wrEna,false.B)
        poke(dut.io.id_1,1.S)
        poke(dut.io.id_2,2.S)
        // poke(dut.io.valid_1,false.B)
        // poke(dut.io.valid_2,false.B)
        step(10)
}
object memorytest extends App {
  chisel3.iotesters.Driver.execute(args, () => new memory(32))(c => new memtester(c))
}