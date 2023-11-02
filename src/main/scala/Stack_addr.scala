// package shadowstack
// //这个的应用场景是汇编语言里面的地址变化
// import chisel3._

// class ShadowStack(depth: Int = 32) extends Module {
//   val io = IO(new Bundle {
//     val curentAddr  = Input(UInt(32.W))
//     val jal         = Input(Bool())
//     val ret         = Input(Bool())
//     val targetAddr  = Input(UInt(32.W))
//     val outputValid = Output(Bool())
//   })

//   val savedAddr = Mem(depth, UInt(32.W))
//   val idx = RegInit(0.U(32.W)) //这个是栈的索引
//   val out = RegInit(Bool(true))//这个是有无有效输出，在运用时，当这个栈空了的时候，就说明我们在栈里的光线处理完毕
//   io.outputValid := out

//   when(io.jal) {
//     // stack one
//     when(idx === (depth-1).U) {
//       // TODO internal vec overflowed, 这个栈的深度我们不用考虑，直接采用最大值进行设置，因为最大深度可以通过软件来模拟，也避免设置出现问题
//     } .otherwise {
//       // save the current address
//       savedAddr(idx) := io.curentAddr//这个就是栈的存储，就对应于索引已经入栈
//       idx := idx+1.U//而后索引加1
//     }
//   }

//   when(io.ret) {//这个猜测是弹栈的请求
//     // check if we can destack 检查弹栈
//     when(idx === 0.U) { //如果当前的索引为0，说明栈是空的
//       out := false.B//输出的有效符号为假
//     } .otherwise {
//       // Check if we return to the good location 检查是否返回正确的位置
//       when(io.targetAddr === savedAddr(idx-1.U)) {//
//         out := true.B
//       } .otherwise {
//         out := false.B
//       }
//       idx := idx-1.U
//     }
//   } .otherwise {
//       out := true.B
//   }
// }

// object ShadowStackMain extends App {
//   chisel3.Driver.execute(args, () => new ShadowStack)
// }