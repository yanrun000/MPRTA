package hardfloat
import chisel3._
import chisel3.util._

class Triangle_memory (val  w : Int )extends Module{  //w用来做深度的参数
    val io = IO(new Bundle{
        val Triangle_id      = Input(SInt(32.W))
        val v00_out   = Output(UInt(32.W))
        val v11_out   = Output(UInt(32.W))
        val v22_out   = Output(UInt(32.W))
        val wrEna        = Input(Bool())
        val wrData      = Input(UInt(32.W))
        val wrAddr      = Input(UInt(32.W))
    })

    val mem = SyncReadMem(w,UInt(32.W))

    io.v00_out := mem.read(io.Triangle_id.asUInt)
    io.v11_out := mem.read(io.Triangle_id.asUInt+1.U)
    io.v22_out := mem.read(io.Triangle_id.asUInt+2.U)

    when(io.wrEna){//整个系统在最初时给BVH的输入
        mem.write(io.wrAddr , io.wrData)
    }
}
// object Main extends App {
//   chisel3.Driver.execute(args, () => new BVH_memory(16))
// }
