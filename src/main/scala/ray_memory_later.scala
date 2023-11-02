package hardfloat
import chisel3._
import chisel3.util._

class ray_memory_hitT (val  w : Int )extends Module{  //w用来做深度的参数
    val io = IO(new Bundle{
        val Ray_id       = Input(UInt(32.W))
        val Ray_out    = Output(UInt(32.W))
        val wrEna        = Input(Bool())
        val wrData      = Input(UInt(32.W))
        val wrAddr      = Input(UInt(32.W))
    })

    val mem = SyncReadMem(w,UInt(32.W))

    io.Ray_out := mem.read(io.Ray_id)

    when(io.wrEna){//整个系统在最初时给BVH的输入
        mem.write(io.wrAddr , io.wrData)
    }
}
// object Main extends App {
//   chisel3.Driver.execute(args, () => new Ray_memory(16))
// }
