// package hardfloat
// import chisel3._
// import chisel3.util.log2Ceil
// import chisel3 . iotesters ._
// import org.scalatest._
// import chisel3.iotesters.PeekPokeTester
// import chisel3.iotesters.Driver
 
//  class Stackmanage extends Module{
//     val io = IO (new Bundle{
//         val push           = Input(Bool())
//         val pop             = Input(Bool())
//         val ray_id         = Input(SInt(32.W))//这个是用来寻找的
//         val node_id     = Input(SInt(32.W))
//         val hitT              = Input(UInt(32.W))//这个感觉在系统中只需要传递数值就可以，避免数据出错，只有弹栈的时候才需要传递
//         val trav_id        = Output(SInt(32.W))//这个是把栈里的数据弹出来的，已经从栈里面弹出来了
//         val ray_s_id     = Output(SInt(32.W))//这个是光线的id在LUT里的传递
//         val hitT_out    = Output(UInt(32.W))//这个是输出的光线hitT
//         val can_dis     = Output(Bool())
//     })

//         val stack_0       = Module(new Stack(64))
//         val stack_1       = Module(new Stack(64))
//         val stack_2       = Module(new Stack(64))
//         val stack_3       = Module(new Stack(64))
//         val stack_4       = Module(new Stack(64))
//         val stack_5       = Module(new Stack(64))
//         val stack_6       = Module(new Stack(64))
//         val stack_7       = Module(new Stack(64))
//         val LUT_0          = Module(new LUT())

//         LUT_0.io.hitT_in             := io.hitT
//         stack_0.io.hit_in             := LUT_0.io.hitT_out
//         stack_1.io.hit_in             := LUT_0.io.hitT_out
//         stack_2.io.hit_in             := LUT_0.io.hitT_out
//         stack_3.io.hit_in             := LUT_0.io.hitT_out
//         stack_4.io.hit_in             := LUT_0.io.hitT_out
//         stack_5.io.hit_in             := LUT_0.io.hitT_out
//         stack_6.io.hit_in             := LUT_0.io.hitT_out
//         stack_7.io.hit_in             := LUT_0.io.hitT_out   


//         LUT_0.io.empty_0          := stack_0.io.empty          
//         LUT_0.io.empty_1          := stack_1.io.empty
//         LUT_0.io.empty_2          := stack_2.io.empty          
//         LUT_0.io.empty_3          := stack_3.io.empty
//         LUT_0.io.empty_4          := stack_4.io.empty          
//         LUT_0.io.empty_5          := stack_5.io.empty
//         LUT_0.io.empty_6          := stack_6.io.empty          
//         LUT_0.io.empty_7          := stack_7.io.empty
//         when(stack_0.io.empty || stack_1.io.empty || stack_2.io.empty || stack_3.io.empty || stack_4.io.empty|| stack_5.io.empty || stack_6.io.empty || stack_7.io.empty){
//             io.can_dis                       := true.B
//         }.otherwise{
//             io.can_dis                      := false.B
//         }

//         LUT_0.io.push                  := io.push
//         LUT_0.io.pop                    := io.pop
//         LUT_0.io.ray_id               := io.ray_id
//         LUT_0.io.node_id                := io.node_id
//         // io.trav_id                            := LUT_0.trav_id
//         io.ray_s_id                         := LUT_0.io.ray_s_id

//         stack_0.io.dataIn             := LUT_0.io.push_id
//         stack_1.io.dataIn             := LUT_0.io.push_id
//         stack_2.io.dataIn             := LUT_0.io.push_id
//         stack_3.io.dataIn             := LUT_0.io.push_id
//         stack_4.io.dataIn             := LUT_0.io.push_id
//         stack_5.io.dataIn             := LUT_0.io.push_id
//         stack_6.io.dataIn             := LUT_0.io.push_id
//         stack_7.io.dataIn             := LUT_0.io.push_id

//         stack_0.io.pop                := (LUT_0.io.pop_0===1.U)
//         stack_1.io.pop                := (LUT_0.io.pop_1===1.U)
//         stack_2.io.pop                := (LUT_0.io.pop_2===1.U)
//         stack_3.io.pop                := (LUT_0.io.pop_3===1.U)
//         stack_4.io.pop                := (LUT_0.io.pop_4===1.U)
//         stack_5.io.pop                := (LUT_0.io.pop_5===1.U)
//         stack_6.io.pop                := (LUT_0.io.pop_6===1.U)
//         stack_7.io.pop                := (LUT_0.io.pop_7===1.U)

//         stack_0.io.push              :=(LUT_0.io.push_0===1.U)
//         stack_1.io.push              :=(LUT_0.io.push_1===1.U)
//         stack_2.io.push              :=(LUT_0.io.push_2===1.U)
//         stack_3.io.push              :=(LUT_0.io.push_3===1.U)
//         stack_4.io.push              :=(LUT_0.io.push_4===1.U)
//         stack_5.io.push              :=(LUT_0.io.push_5===1.U)
//         stack_6.io.push              :=(LUT_0.io.push_6===1.U)
//         stack_7.io.push              :=(LUT_0.io.push_7===1.U)

//         io.trav_id                            := stack_0.io.dataOut
//         io.trav_id                            := stack_1.io.dataOut
//         io.trav_id                            := stack_2.io.dataOut
//         io.trav_id                            := stack_3.io.dataOut
//         io.trav_id                            := stack_4.io.dataOut
//         io.trav_id                            := stack_5.io.dataOut
//         io.trav_id                            := stack_6.io.dataOut
//         io.trav_id                            := stack_7.io.dataOut

//         io.hitT_out                        := stack_0.io.hit_out
//         io.hitT_out                        := stack_1.io.hit_out
//         io.hitT_out                        := stack_2.io.hit_out
//         io.hitT_out                        := stack_3.io.hit_out
//         io.hitT_out                        := stack_4.io.hit_out
//         io.hitT_out                        := stack_5.io.hit_out
//         io.hitT_out                        := stack_6.io.hit_out
//         io.hitT_out                        := stack_7.io.hit_out

//  }

// //  object Main extends App {
// //   chisel3.Driver.execute(args, () => new Stackmanage())
// // }