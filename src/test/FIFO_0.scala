package hardfloat
import chisel3._
import chisel3.util._

class FIFO_0 (val w : Int, val depth : Int) extends Module {
  val io = IO(new Bundle {
    val datain = Input(UInt(w.W))//数据输入
    val dataout = Output(UInt(w.W))//数据输出
    val wr = Input(Bool())//写信号
    val rd = Input(Bool())//读信号
    val full = Output(Bool())//FIFO满
    val empty = Output(Bool())//FIFO空
  })

  val count = RegInit(0.U(depth.W))//FIFO的一个计数
  val mem = Mem(depth, UInt(w.W))//位宽为w的depth层存储空间
  val wp = RegInit(0.U(depth.W))
  val rp = RegInit(0.U(depth.W))
  val dataout = RegInit(0.U(w.W))

  def IndexAdd(index : UInt) : UInt = {
    val temp = RegInit(index)
    when(index === (depth - 1).U) { temp := 0.U }//depth是这个函数的输入
    .otherwise { temp := index + 1.U }
    temp
  }

  when(io.wr === true.B && io.rd === true.B) {//当写信号和读信号同时为真时
    when(count === 0.U) { io.dataout := io.datain }//当计数器为0，那么数据输出等于数据输入
    .otherwise {
      io.dataout := mem(rp)//否则的话，等于
      rp := IndexAdd(rp)
      mem(wp) := io.datain
      wp := IndexAdd(wp)
    } 
  } .elsewhen (io.wr === true.B && io.rd === false.B) {//当写信号为真，读信号为假时
    io.dataout := 0.U
    when(count < depth.U) {
      mem(wp) := io.datain
      wp := IndexAdd(wp)
      count := count + 1.U
    }
  } .elsewhen (io.wr === false.B && io.rd === true.B) {//当写信号为假，读信号为真时
    when(count > 0.U) {
      io.dataout := mem(rp)
      rp := IndexAdd(rp)
      count := count - 1.U
    } .otherwise {//count=0时
      io.dataout := 0.U
    }
  } .otherwise {
    io.dataout := 0.U
  }

  io.full := (depth.U === count)
  io.empty := (count === 0.U)
}

// object Main extends App {
//   chisel3.Driver.execute(args, () => new MyFifo(8,16))
// }
