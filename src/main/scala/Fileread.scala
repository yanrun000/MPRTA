import chisel3._
import chisel3.util.experimental.{loadMemoryFromFile}
 
class LoadMem extends Module {
  val io = IO(new Bundle {
    val address = Input(UInt(3.W))
    val value   = Output(UInt(8.W))
  })
  val memory = Mem(8, UInt(8.W))
  io.value := memory.read(io.address)
  loadMemoryFromFile(memory, "/home/workspace/RTP/mem.txt")
}
object RAM extends App {
  chisel3.Driver.execute(args, () => new LoadMem())
}
// object LoadMem_test extends App {
//   chisel3.iotesters.Driver.execute(args, () => new LoadMem())(c => new RAM(c))
// }