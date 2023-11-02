package hardfloat
import chisel3._
import chisel3.util._

class BVH_memory_valid (val  w : Int )extends Module{  //w用来做深度的参数
    val io = IO(new Bundle{
        val BVH_id           = Input(SInt(32.W))
        val BVH_out        = Output(UInt(32.W))
        val BVH_out_1   = Output(UInt(32.W))
        val wrEna             = Input(Bool())
        val wrData           = Input(UInt(32.W))
        val wrAddr           = Input(UInt(32.W))
    })

    val mem = SyncReadMem(w,UInt(32.W))

    io.BVH_out := mem.read(io.BVH_id.asUInt)

    io.BVH_out_1 := mem.read((io.BVH_id+3.S).asUInt)

    when(io.wrEna){//整个系统在最初时给BVH的输入
        mem.write(io.wrAddr , io.wrData)
    }
}
// object Main extends App {
//   chisel3.Driver.execute(args, () => new BVH_memory(16))
// }
