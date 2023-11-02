// package hardfloat
// import chisel3._
// import chisel3.util._

// class MyStack(val w : Int, val depth : Int) extends Module{
//     val io = IO(new Bundle{
//         val datain = Input(UInt(w.W))
//         val dataout = Output(UInt(w.W))
//         val push = Input(Bool())
//         val pop = Input(Bool())
//         val full = Output(Bool())
//         val empty = Output(Bool())
//     })
//     val count = RegInit(0.U(depth.W))//栈的指针
//     val mem = Mem(depth,UInt(w.W))
//     // val out = RegInit(Bool(true))
//     // val push_i = RegInit(Bool())

//     when(io.push === true.B && io.pop === false.B){//在我们的实现中不考虑栈满的情况
//         when(count < (depth-1). U){
//         mem(count) := io.datain
//         count := count+1.U
//         }
//     }.elsewhen(io.push === false.B && io.pop === true.B){
//         when(count  > 0. U){
//         io.dataout  := mem(count)
//         count := count -1.U
//         }
//     }.elsewhen(io.push === true.B && io.pop === true.B){
//         // when{count  > 0. U}{
//         io.dataout := io.datain
//     }.otherwise{
//         io.dataout := 0.U
//     }


//     io.full := (depth.U === count)
//     io.empty := (count === 0.U)
// }
// object Main extends App {
//   chisel3.Driver.execute(args, () => new MyStack(8,16))
// }
