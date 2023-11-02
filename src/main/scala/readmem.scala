import chisel3._
import chisel3.util._

class readmem_test  extends Module{
    val io = IO(new Bundle{
        val rdAddr = Input(UInt(10.W))
        val rdData = Output(UInt(32.W))
        val wrEna  = Input(Bool())
        val wrData = Input(UInt(32.W))
        val wrAddr = Input(UInt(10.W))
    })

    val mem = SyncReadMem(1024,UInt(32.W))
    
    io.rdData   := mem.read(io.rdAddr)
    when(io.wrEna){
        mem.write(io.wrAddr,io.wrData)
    }
}

object mem_test extends App {
  chisel3.Driver.execute(args, () => new readmem_test())
}