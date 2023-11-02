
// package hardfloat
// import chisel3._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._


// class rtp_test extends Module{
//     val io = IO(new Bundle{
//         //ray_dispatch
//         val dispatch               =  Input(Bool())
//         val ray_out                 = Output(Bits(32.W))
//         val ray_finish             = Output(Bool())

//         //Ray_memory
//         val wrEna                     = Input(Bool())
//         val wrData                   = Input(UInt(128.W))
//         val wrAddr                   = Input(UInt(32.W))
//         //BVH_memory
//         val wrEna_B               = Input(Bool())
//         val wrData_B             = Input(UInt(128.W))
//         val wrAddr_B             = Input(UInt(32.W))

//         //RAY_AABB      
//         val nodeIdx_0           = Output(Bits(32.W))//这个地址表示进栈的地址
//         val nodeIdx_1           = Output(Bits(32.W))//这个地址表示内部的地址
//         val nodeIdx_2           = Output(Bits(32.W))//这个地址表示叶子的地址
//         val push                       = Output(Bool())
//         val pop                         = Output(Bool())
//         val leaf                          = Output(Bool())
//     })

//     val  Ray_Dispatch       =  Module (new ray_dispatch(8))
//     val  Ray_FIFO                =  Module (new FIFO(32,8))
//     val  BVH_FIFO               =  Module (new FIFO(32,8))
//     val  Ray_RAM                =  Module (new ray_memory(8))
//     val  Orig_Ray_RAM     =  Module (new ray_memory(8))
//     val  Ray_AABB              =  Module (new ray_AABB())
//     val  BVH_RAM               =  Module(new BVH_memory(20))
   
//     val rayid                          = RegInit(0.U(32.W))//这个连接两个变量，一个是初始化时的节点地址为0，一个是出栈时候的节点地址
//     val internal_nodeid   = RegInit(0.U(32.W))//这个连接三个变量，一个是初始化的BVH节点，一个是未入栈的BVH节点，一个是弹栈的BVH节点
//     //ray_dispatch
//     // Ray_Dispatch.io.dispatch       := stack_en//这里的逻辑为栈使能，也即栈为空
//     when(Ray_Dispatch.io.ray_out === true.B){
//         internal_nodeid                      := 0.U
//         rayid                                             := Ray_Dispatch.io.rayid_id
//     }.otherwise{
//         internal_nodeid                      := 7654321.U//这个我还不确定要怎么搞
//     } 

//     io.ray_out                    := Ray_Dispatch.io.ray_out
//     io.ray_finish                := Ray_Dispatch.io.ray_finish  

//     Ray_FIFO.io.datain      := rayid
//     Ray_FIFO.io.wr               := true.B
//     Ray_FIFO.io.rd                := true.B

//     //ray_ram
//     Ray_RAM.io.Ray_id     := Ray_FIFO.io.dataout//这里少一个传入流水线的id
//     Ray_RAM.io.wrEna      := io.wrEna
//     Ray_RAM.io.wrData    := io.wrData
//     Ray_RAM.io.wrAddr    := io.wrAddr
    
//     //BVH的FIFO
//     BVH_FIFO.io.datain       := internal_nodeid//这个还少东西
//     BVH_FIFO.io.wr               := true.B
//     BVH_FIFO.io.rd                := true.B

//     //bvh_ram
//     BVH_RAM.io.BVH_id   := BVH_FIFO.dataout
//     BVH_RAM.io.wrEna      := io.wrEna_B
//     BVH_RAM.io.wrData    := io.wrData_B
//     BVH_RAM.io.wrAddr    := io.wrAddr_B
    
//     //ray_aabb
//     Ray_AABB.io.iray                  := Ray_RAM.io.Ray_out(191,0)
//     Ray_AABB.io.tmin                := Ray_RAM.io.Ray_out(127,96)
//     Ray_AABB.io.hitT                 := Ray_RAM.io.Ray_out(255,128)
//     Ray_AABB.io.rayid               := Ray_Dispatch.io.rayid_id//这个不对
//     Ray_AABB.io.BVH_node    := BVH_RAM.io.BVH_out

//     io.nodeIdx_0                      := Ray_AABB.io.nodeIdx_0
//     io.nodeIdx_1                      := Ray_AABB.io.nodeIdx_1
//     io.nodeIdx_2                      := Ray_AABB.io.nodeIdx_2
//     io.push                                  := Ray_AABB.io.push
//     io.pop                                    := Ray_AABB.io.pop
//     io.leaf                                     := Ray_AABB.io.leaf


    




// }
// // object Main extends App {
// //   chisel3.Driver.execute(args, () => new rtp_test())
// // }
