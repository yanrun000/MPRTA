package hardfloat
import chisel3._
import chisel3.util._

class BVH_memory_0 (val  w : Int )extends Module{  //w用来做深度的参数
    val io = IO(new Bundle{
        val BVH_id      = Input(SInt(32.W))
        val BVH_id_2 = Input(SInt(32.W))
        val BVH_out   = Output(SInt(32.W))
        val BVH_out_2   = Output(SInt(32.W))    
        val wrEna        = Input(Bool())
        val wrData      = Input(SInt(32.W))
        val wrAddr      = Input(UInt(32.W))
    })

    val mem = SyncReadMem(w,SInt(32.W))

    io.BVH_out := mem.read(io.BVH_id.asUInt).asSInt
    io.BVH_out_2 := mem.read(io.BVH_id_2.asUInt).asSInt

    when(io.wrEna){//整个系统在最初时给BVH的输入
        mem.write(io.wrAddr , io.wrData)
    }
}
// object Main extends App {
//   chisel3.Driver.execute(args, () => new BVH_memory(16))
// }
