// package hardfloat
// import chisel3._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._

// class LUT extends Module{
//     val io = IO (new Bundle{
//         val push           = Input(Bool())
//         val push_valid= Input(Bool())
//         val pop             = Input(Bool())
//         val pop_valid = Input(Bool())
//         val empty_0   = Input(Bool())
//         val empty_1   = Input(Bool())
//         val empty_2   = Input(Bool())
//         val empty_3   = Input(Bool())
//         val empty_4   = Input(Bool())
//         val empty_5   = Input(Bool())
//         val empty_6   = Input(Bool())
//         val empty_7   = Input(Bool())
//         val empty_8   = Input(Bool())
//         val empty_9   = Input(Bool())
//         val empty_10   = Input(Bool())
//         val empty_11   = Input(Bool())
//         val empty_12   = Input(Bool())
//         val empty_13   = Input(Bool())
//         val empty_14   = Input(Bool())
//         val empty_15   = Input(Bool())
//         val empty_16   = Input(Bool())
//         val empty_17   = Input(Bool())
//         val empty_18   = Input(Bool())
//         val empty_19   = Input(Bool())
//         val empty_20   = Input(Bool())
//         val empty_21   = Input(Bool())
//         val empty_22   = Input(Bool())
//         val empty_23   = Input(Bool())
//         val empty_24   = Input(Bool())

//         val dispatch_0   = Input(Bool())
//         val dispatch_1   = Input(Bool())
//         val dispatch_2   = Input(Bool())
//         val dispatch_3   = Input(Bool())
//         val dispatch_4   = Input(Bool())
//         val dispatch_5   = Input(Bool())
//         val dispatch_6   = Input(Bool())
//         val dispatch_7   = Input(Bool())
//         val dispatch_8   = Input(Bool())
//         val dispatch_9   = Input(Bool())
//         val dispatch_10   = Input(Bool())
//         val dispatch_11   = Input(Bool())
//         val dispatch_12   = Input(Bool())
//         val dispatch_13   = Input(Bool())
//         val dispatch_14   = Input(Bool())
//         val dispatch_15   = Input(Bool())
//         val dispatch_16   = Input(Bool())
//         val dispatch_17   = Input(Bool())
//         val dispatch_18   = Input(Bool())
//         val dispatch_19   = Input(Bool())
//         val dispatch_20   = Input(Bool())
//         val dispatch_21   = Input(Bool())
//         val dispatch_22   = Input(Bool())
//         val dispatch_23   = Input(Bool())
//         val dispatch_24   = Input(Bool())
    
//         val ray_id_push                  = Input(UInt(32.W))//为了保证弹栈和入栈同时进行需要设置两个光线的收入口，这个是入栈的光线id口
//         val ray_id_pop                    = Input(UInt(32.W))//这个是光线弹栈口
//         val node_id_push_in       = Input(SInt(32.W))//这个是入栈的节点id
//         val node_id_pop_in         = Input(SInt(32.W))//这个是弹栈的节点id,好像没什么意义
//         val hitT_in                             = Input(UInt(32.W))//这个是在入栈时候的传递值

        
//         val ray_id_pop_out          = Output(UInt(32.W))//这个是光线的id在LUT里的传递，只有弹栈的时候需要传递
//         val node_id_pop_out     = Output(SInt(32.W))//这个是把栈里的数据弹出来的pop的传递值
//         val node_id_push_out   = Output(SInt(32.W))//这个是push的节点的值需要用来传给栈
//         val hitT_out                         = Output(UInt(32.W))//这个是用来在单元中来传递Hit的值，只有弹栈的时候才需要

//         val pop_0        =  Output(Bool())
//         val pop_1        =  Output(Bool())
//         val pop_2        =  Output(Bool())
//         val pop_3        =  Output(Bool())
//         val pop_4        =  Output(Bool())
//         val pop_5        =  Output(Bool())
//         val pop_6        =  Output(Bool())
//         val pop_7        =  Output(Bool())
//         val pop_8        =  Output(Bool())
//         val pop_9        =  Output(Bool())
//         val pop_10        =  Output(Bool())
//         val pop_11        =  Output(Bool())
//         val pop_12        =  Output(Bool())
//         val pop_13        =  Output(Bool())
//         val pop_14        =  Output(Bool())
//         val pop_15        =  Output(Bool())
//         val pop_16        =  Output(Bool())
//         val pop_17        =  Output(Bool())
//         val pop_18        =  Output(Bool())
//         val pop_19        =  Output(Bool())
//         val pop_20        =  Output(Bool())
//         val pop_21        =  Output(Bool())
//         val pop_22        =  Output(Bool())
//         val pop_23        =  Output(Bool())
//         val pop_24        =  Output(Bool())

//         val pop_en     =  Output(Bool())

//         val push_0      =  Output(Bool())
//         val push_1      =  Output(Bool())
//         val push_2      =  Output(Bool())
//         val push_3      =  Output(Bool())
//         val push_4      =  Output(Bool())
//         val push_5      =  Output(Bool())
//         val push_6      =  Output(Bool())
//         val push_7      =  Output(Bool())
//         val push_8      =  Output(Bool())
//         val push_9      =  Output(Bool())
//         val push_10      =  Output(Bool())
//         val push_11      =  Output(Bool())
//         val push_12      =  Output(Bool())
//         val push_13      =  Output(Bool())
//         val push_14      =  Output(Bool())
//         val push_15      =  Output(Bool())
//         val push_16      =  Output(Bool())
//         val push_17      =  Output(Bool())
//         val push_18      =  Output(Bool())
//         val push_19      =  Output(Bool())
//         val push_20      =  Output(Bool())
//         val push_21      =  Output(Bool())
//         val push_22      =  Output(Bool())
//         val push_23      =  Output(Bool())
//         val push_24      =  Output(Bool())

//         val push_en   =  Output(Bool())
//     })

//     val LUT_mem     = Mem(25,UInt(33.W))
//     val read_stack0 = RegInit(0.U(32.W))//这个是用来和表里的数据进行读取用的
//     val read_stack1 = RegInit(0.U(32.W))
//     val read_stack2 = RegInit(0.U(32.W))
//     val read_stack3 = RegInit(0.U(32.W))
//     val read_stack4 = RegInit(0.U(32.W))
//     val read_stack5 = RegInit(0.U(32.W))
//     val read_stack6 = RegInit(0.U(32.W))
//     val read_stack7 = RegInit(0.U(32.W))
//     val read_stack8 = RegInit(0.U(32.W))
//     val read_stack9 = RegInit(0.U(32.W))
//     val read_stack10 = RegInit(0.U(32.W))
//     val read_stack11 = RegInit(0.U(32.W))
//     val read_stack12 = RegInit(0.U(32.W))
//     val read_stack13 = RegInit(0.U(32.W))
//     val read_stack14 = RegInit(0.U(32.W))
//     val read_stack15 = RegInit(0.U(32.W))
//     val read_stack16 = RegInit(0.U(32.W))
//     val read_stack17 = RegInit(0.U(32.W))
//     val read_stack18 = RegInit(0.U(32.W))
//     val read_stack19 = RegInit(0.U(32.W))
//     val read_stack20 = RegInit(0.U(32.W))
//     val read_stack21 = RegInit(0.U(32.W))
//     val read_stack22 = RegInit(0.U(32.W))
//     val read_stack23 = RegInit(0.U(32.W))
//     val read_stack24 = RegInit(0.U(32.W))
//     // val ray_id_1        = RegInit(0.S(32.W))

//     val push_0_1     = RegInit(0.U(1.W))
//     val push_1_1     = RegInit(0.U(1.W))
//     val push_2_1     = RegInit(0.U(1.W))
//     val push_3_1     = RegInit(0.U(1.W))
//     val push_4_1     = RegInit(0.U(1.W))
//     val push_5_1     = RegInit(0.U(1.W))
//     val push_6_1     = RegInit(0.U(1.W))
//     val push_7_1     = RegInit(0.U(1.W))
//     val push_8_1     = RegInit(0.U(1.W))
//     val push_9_1     = RegInit(0.U(1.W))
//     val push_10_1     = RegInit(0.U(1.W))
//     val push_11_1     = RegInit(0.U(1.W))
//     val push_12_1     = RegInit(0.U(1.W))
//     val push_13_1     = RegInit(0.U(1.W))
//     val push_14_1     = RegInit(0.U(1.W))
//     val push_15_1     = RegInit(0.U(1.W))
//     val push_16_1     = RegInit(0.U(1.W))
//     val push_17_1     = RegInit(0.U(1.W))
//     val push_18_1     = RegInit(0.U(1.W))
//     val push_19_1     = RegInit(0.U(1.W))
//     val push_20_1     = RegInit(0.U(1.W))
//     val push_21_1     = RegInit(0.U(1.W))
//     val push_22_1     = RegInit(0.U(1.W))
//     val push_23_1     = RegInit(0.U(1.W))
//     val push_24_1     = RegInit(0.U(1.W))

//     //这里还是需要一个短暂的状态来寄存输出的数据,所以,还是有必要的
//     //push的话第一个stage (1),感觉栈空不空也需要一个寄存器保存一下状态
//     val push_1                      = RegInit(0.U(1.W))
//     val push_valid              = RegInit(0.U(1.W))//要把有效位传过去
//     val push_node_id       = RegInit(0.S(32.W)) 
//     val push_ray_id           = RegInit(0.U(32.W))
//     val empty_0_1             = RegInit(0.U(1.W))
//     val empty_1_1             = RegInit(0.U(1.W))
//     val empty_2_1             = RegInit(0.U(1.W))
//     val empty_3_1             = RegInit(0.U(1.W))
//     val empty_4_1             = RegInit(0.U(1.W))
//     val empty_5_1             = RegInit(0.U(1.W))
//     val empty_6_1             = RegInit(0.U(1.W))
//     val empty_7_1             = RegInit(0.U(1.W))
//     val empty_8_1             = RegInit(0.U(1.W))
//     val empty_9_1             = RegInit(0.U(1.W))
//     val empty_10_1             = RegInit(0.U(1.W))
//     val empty_11_1             = RegInit(0.U(1.W))
//     val empty_12_1             = RegInit(0.U(1.W))
//     val empty_13_1             = RegInit(0.U(1.W))
//     val empty_14_1             = RegInit(0.U(1.W))
//     val empty_15_1             = RegInit(0.U(1.W))
//     val empty_16_1             = RegInit(0.U(1.W))
//     val empty_17_1             = RegInit(0.U(1.W))
//     val empty_18_1             = RegInit(0.U(1.W))
//     val empty_19_1             = RegInit(0.U(1.W))
//     val empty_20_1             = RegInit(0.U(1.W))
//     val empty_21_1             = RegInit(0.U(1.W))
//     val empty_22_1             = RegInit(0.U(1.W))
//     val empty_23_1             = RegInit(0.U(1.W))
//     val empty_24_1             = RegInit(0.U(1.W))

//     //push 的话第二个stage(2)
//     val push_ray_id_2        = RegInit(0.U(32.W))
//     val push_node_id_2    = RegInit(0.S(32.W))
//     val push_valid_2           = RegInit(0.U(1.W))
    
//     val push_stack1_en   = RegInit(0.U(1.W))//这个的意义是已经取了数，判断和查找表里的内容是否匹配，如果匹配就压栈，如果不匹配，就去找空的
//     val find_empty             = RegInit(0.U(1.W))//这个的意义是当前的栈没有找到匹配的，需要压到新的栈里

//     when(io.dispatch_0===1.U){
//         LUT_mem(0)   :=Cat(0.U,LUT_mem(0)(31,0))  
//     }.otherwise{
//         LUT_mem(0)   := LUT_mem(0)
//     }

//     when(io.dispatch_1===1.U){
//         LUT_mem(1)   :=Cat(0.U,LUT_mem(1)(31,0))  
//     }.otherwise{
//         LUT_mem(1)   := LUT_mem(1)
//     }

//     when(io.dispatch_2===1.U){
//         LUT_mem(2)   :=Cat(0.U,LUT_mem(2)(31,0))  
//     }.otherwise{
//         LUT_mem(2)   := LUT_mem(2)
//     }

//     when(io.dispatch_3===1.U){
//         LUT_mem(3)   :=Cat(0.U,LUT_mem(3)(31,0))  
//     }.otherwise{
//         LUT_mem(3)   := LUT_mem(3)
//     }

//     when(io.dispatch_4===1.U){
//         LUT_mem(4)   :=Cat(0.U,LUT_mem(4)(31,0))  
//     }.otherwise{
//         LUT_mem(4)   := LUT_mem(4)
//     }

//     when(io.dispatch_5===1.U){
//         LUT_mem(5)   :=Cat(0.U,LUT_mem(5)(31,0))  
//     }.otherwise{
//         LUT_mem(5)   := LUT_mem(5)
//     }

//     when(io.dispatch_6===1.U){
//         LUT_mem(6)   :=Cat(0.U,LUT_mem(6)(31,0))  
//     }.otherwise{
//         LUT_mem(6)   := LUT_mem(6)
//     }

//     when(io.dispatch_7===1.U){
//         LUT_mem(7)   :=Cat(0.U,LUT_mem(7)(31,0))  
//     }.otherwise{
//         LUT_mem(7)   := LUT_mem(7)
//     }
    
//     when(io.dispatch_8===1.U){
//         LUT_mem(8)   :=Cat(0.U,LUT_mem(8)(31,0))  
//     }.otherwise{
//         LUT_mem(8)   := LUT_mem(8)
//     }

//     when(io.dispatch_9===1.U){
//         LUT_mem(9)   :=Cat(0.U,LUT_mem(9)(31,0))  
//     }.otherwise{
//         LUT_mem(9)   := LUT_mem(9)
//     }

//     when(io.dispatch_10===1.U){
//         LUT_mem(10)   :=Cat(0.U,LUT_mem(10)(31,0))  
//     }.otherwise{
//         LUT_mem(10)   := LUT_mem(10)
//     }

//     when(io.dispatch_11===1.U){
//         LUT_mem(11)   :=Cat(0.U,LUT_mem(11)(31,0))  
//     }.otherwise{
//         LUT_mem(11)   := LUT_mem(11)
//     }

//     when(io.dispatch_12===1.U){
//         LUT_mem(12)   :=Cat(0.U,LUT_mem(12)(31,0))  
//     }.otherwise{
//         LUT_mem(12)   := LUT_mem(12)
//     }

//     when(io.dispatch_13===1.U){
//         LUT_mem(13)   :=Cat(0.U,LUT_mem(13)(31,0))  
//     }.otherwise{
//         LUT_mem(13)   := LUT_mem(13)
//     }

//     when(io.dispatch_14===1.U){
//         LUT_mem(14)   :=Cat(0.U,LUT_mem(14)(31,0))  
//     }.otherwise{
//         LUT_mem(14)   := LUT_mem(14)
//     }

//     when(io.dispatch_15===1.U){
//         LUT_mem(15)   :=Cat(0.U,LUT_mem(15)(31,0))  
//     }.otherwise{
//         LUT_mem(15)   := LUT_mem(15)
//     }

//     when(io.dispatch_16===1.U){
//         LUT_mem(16)   :=Cat(0.U,LUT_mem(16)(31,0))  
//     }.otherwise{
//         LUT_mem(16)   := LUT_mem(16)
//     }

//     when(io.dispatch_17===1.U){
//         LUT_mem(17)   :=Cat(0.U,LUT_mem(17)(31,0))  
//     }.otherwise{
//         LUT_mem(17)   := LUT_mem(17)
//     }
    
//     when(io.dispatch_18===1.U){
//         LUT_mem(18)   :=Cat(0.U,LUT_mem(18)(31,0))  
//     }.otherwise{
//         LUT_mem(18)   := LUT_mem(18)
//     }

//     when(io.dispatch_19===1.U){
//         LUT_mem(19)   :=Cat(0.U,LUT_mem(19)(31,0))  
//     }.otherwise{
//         LUT_mem(19)   := LUT_mem(19)
//     }

//     when(io.dispatch_20===1.U){
//         LUT_mem(20)   :=Cat(0.U,LUT_mem(20)(31,0))  
//     }.otherwise{
//         LUT_mem(20)   := LUT_mem(20)
//     }

//     when(io.dispatch_21===1.U){
//         LUT_mem(21)   :=Cat(0.U,LUT_mem(21)(31,0))  
//     }.otherwise{
//         LUT_mem(21)   := LUT_mem(21)
//     }

//     when(io.dispatch_22===1.U){
//         LUT_mem(22)   :=Cat(0.U,LUT_mem(22)(31,0))  
//     }.otherwise{
//         LUT_mem(22)   := LUT_mem(22)
//     }

//     when(io.dispatch_23===1.U){
//         LUT_mem(23)   :=Cat(0.U,LUT_mem(23)(31,0))  
//     }.otherwise{
//         LUT_mem(23)   := LUT_mem(23)
//     }

//     when(io.dispatch_24===1.U){
//         LUT_mem(24)   :=Cat(0.U,LUT_mem(24)(31,0))  
//     }.otherwise{
//         LUT_mem(24)   := LUT_mem(24)
//     }


//         empty_0_1        := io.empty_0
//         empty_1_1        := io.empty_1
//         empty_2_1        := io.empty_2
//         empty_3_1        := io.empty_3
//         empty_4_1        := io.empty_4
//         empty_5_1        := io.empty_5
//         empty_6_1        := io.empty_6
//         empty_7_1        := io.empty_7
//         empty_8_1        := io.empty_8
//         empty_9_1        := io.empty_9
//         empty_10_1        := io.empty_10
//         empty_11_1        := io.empty_11
//         empty_12_1        := io.empty_12
//         empty_13_1        := io.empty_13
//         empty_14_1        := io.empty_14
//         empty_15_1        := io.empty_15
//         empty_16_1        := io.empty_16
//         empty_17_1        := io.empty_17
//         empty_18_1        := io.empty_18
//         empty_19_1        := io.empty_19
//         empty_20_1        := io.empty_20
//         empty_21_1        := io.empty_21
//         empty_22_1        := io.empty_22
//         empty_23_1        := io.empty_23
//         empty_24_1        := io.empty_24
        
//     // val push_from_io      = RegInit(0.U(1.W))
//     // val push_v_from_io = RegInit(0.U(1.W))

//         read_stack0      := LUT_mem(0)(31,0)
//         read_stack1      := LUT_mem(1)(31,0)
//         read_stack2      := LUT_mem(2)(31,0)
//         read_stack3      := LUT_mem(3)(31,0)
//         read_stack4      := LUT_mem(4)(31,0)
//         read_stack5      := LUT_mem(5)(31,0)
//         read_stack6      := LUT_mem(6)(31,0)
//         read_stack7      := LUT_mem(7)(31,0)
//         read_stack8      := LUT_mem(8)(31,0)
//         read_stack9      := LUT_mem(9)(31,0)  
//         read_stack10      := LUT_mem(10)(31,0)
//         read_stack11      := LUT_mem(11)(31,0)
//         read_stack12      := LUT_mem(12)(31,0)
//         read_stack13      := LUT_mem(13)(31,0)
//         read_stack14      := LUT_mem(14)(31,0)
//         read_stack15      := LUT_mem(15)(31,0)
//         read_stack16      := LUT_mem(16)(31,0)
//         read_stack17      := LUT_mem(17)(31,0)
//         read_stack18      := LUT_mem(18)(31,0)
//         read_stack19      := LUT_mem(19)(31,0)  
//         read_stack20      := LUT_mem(20)(31,0)
//         read_stack21      := LUT_mem(21)(31,0)
//         read_stack22      := LUT_mem(22)(31,0)
//         read_stack23      := LUT_mem(23)(31,0)
//         read_stack24      := LUT_mem(24)(31,0)
        
//         val push_node_1     = RegInit(0.S(32.W))
//         val push_ray_1         = RegInit(0.S(32.W))
//         val push_en_1           = RegInit(0.U(1.W))
//         val push1                     = RegInit(0.U(1.W))

//         val push_node_2     = RegInit(0.S(32.W))
//         val push_ray_2         = RegInit(0.S(32.W))
//         val push_en_2          = RegInit(0.U(1.W))
//         val push2                    = RegInit(0.U(1.W))

//     when(io.push === true.B && io.push_valid){//这里还需要&&一个push请求的有效位来确定这次Push是不是真的

//         push_1                := 1.U
//         push_node_id   := io.node_id_push_in
//         push_ray_id      := io.ray_id_push
//         push_valid         := io.push_valid
//     }.otherwise{
//                 push_valid := 0.U
//                 push_1        := 0.U
//     }

//         // push_node_2     := push_node_1
//         // push_ray_2         := push_ray_1
//         // push_en_2          := push_en_1
//         // push2                    := push1

//         // push_node_id    := push_node_2
//         // push_ray_id        := push_ray_2
//         // push_valid           := push_en_2
//         // push_1                  := push2

//     when(push_1 === 1.U&& push_valid === 1.U){
//         when(LUT_mem(0)(31,0) === push_ray_id &&push_valid === 1.U){
//             push_0_1                 := 1.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         :=  1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(1)(31,0) === push_ray_id &&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 1.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
            
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(2)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 1.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(3)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 1.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(4)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 1.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2 := push_node_id
//         }.elsewhen(LUT_mem(5)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 1.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(6)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 1.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//            push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(7)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 1.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(8)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 1.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(9)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 1.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(10)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 1.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(11)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 1.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(12)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 1.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(13)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 1.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(14)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 1.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(15)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 1.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(16)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 1.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(17)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 1.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(18)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 1.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(19)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 1.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id 
//         }.elsewhen(LUT_mem(20)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 1.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id 
//         }.elsewhen(LUT_mem(21)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 1.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(22)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 1.U
//             push_23_1                 := 0.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(23)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 1.U
//             push_24_1                 := 0.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(24)(31,0) === push_ray_id&&push_valid === 1.U){
//             push_0_1                 := 0.U
//             push_1_1                 := 0.U
//             push_2_1                 := 0.U
//             push_3_1                 := 0.U
//             push_4_1                 := 0.U
//             push_5_1                 := 0.U
//             push_6_1                 := 0.U
//             push_7_1                 := 0.U
//             push_8_1                 := 0.U
//             push_9_1                 := 0.U
//             push_10_1                 := 0.U
//             push_11_1                 := 0.U
//             push_12_1                 := 0.U
//             push_13_1                 := 0.U
//             push_14_1                 := 0.U
//             push_15_1                 := 0.U
//             push_16_1                 := 0.U
//             push_17_1                 := 0.U
//             push_18_1                 := 0.U
//             push_19_1                 := 0.U
//             push_20_1                 := 0.U
//             push_21_1                 := 0.U
//             push_22_1                 := 0.U
//             push_23_1                 := 0.U
//             push_24_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(read_stack0 =/= push_ray_id && read_stack1 =/= push_ray_id && read_stack2 =/= push_ray_id && read_stack3 =/= push_ray_id && read_stack4 =/= push_ray_id && read_stack5 =/= push_ray_id && read_stack6 =/= push_ray_id && read_stack7 =/= push_ray_id&& read_stack8 =/= push_ray_id
//         && read_stack9 =/= push_ray_id&& read_stack10 =/= push_ray_id&& read_stack11 =/= push_ray_id&& read_stack12 =/= push_ray_id&& read_stack13 =/= push_ray_id&& read_stack14 =/= push_ray_id&& read_stack15 =/= push_ray_id
//         && read_stack16 =/= push_ray_id&& read_stack17 =/= push_ray_id&& read_stack18 =/= push_ray_id&& read_stack19 =/= push_ray_id&& read_stack20 =/= push_ray_id&& read_stack21 =/= push_ray_id
//         && read_stack22 =/= push_ray_id&& read_stack23 =/= push_ray_id&& read_stack24 =/= push_ray_id&&(push_valid === 1.U)){
//                 when(io.empty_0 === 1.U&&push_valid === 1.U &&push_0_1 ===0.U&&LUT_mem(0)(32) ===0.U ){
//                 LUT_mem(0) := Cat(1.U,push_ray_id)
//                 // LUT_mem(0)(32)    := 1.U  
//                 push_0_1                 := 1.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_1=== 1.U&&push_valid === 1.U&&push_1_1 ===0.U&&LUT_mem(1)(32) ===0.U){
//                 LUT_mem(1) := Cat(1.U,push_ray_id)
//                 // LUT_mem(1)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 1.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_2=== 1.U&&push_valid === 1.U&&push_2_1 ===0.U&&LUT_mem(2)(32) ===0.U){
//                 LUT_mem(2) := Cat(1.U,push_ray_id)
//                 // LUT_mem(2)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 1.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_3=== 1.U&&push_valid === 1.U&&push_3_1 ===0.U&&LUT_mem(3)(32) ===0.U){
//                 LUT_mem(3) := Cat(1.U,push_ray_id)
//                 // LUT_mem(3)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 1.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_4=== 1.U&&push_valid === 1.U&&push_4_1 ===0.U&&LUT_mem(4)(32) ===0.U){
//                 LUT_mem(4) := Cat(1.U,push_ray_id)
//                 // LUT_mem(4)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 1.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_5=== 1.U&&push_valid === 1.U&&push_5_1 ===0.U&&LUT_mem(5)(32) ===0.U){
//                 LUT_mem(5) := Cat(1.U,push_ray_id)
//                 // LUT_mem(5)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 1.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_6=== 1.U&&push_valid === 1.U&&push_6_1 ===0.U&&LUT_mem(6)(32) ===0.U){
//                 LUT_mem(6) := Cat(1.U,push_ray_id)
//                 // LUT_mem(6)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 1.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_7=== 1.U&&push_valid === 1.U&&push_7_1 ===0.U&&LUT_mem(7)(32) ===0.U){
//                 LUT_mem(7) := Cat(1.U,push_ray_id)
//                 // LUT_mem(7)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 1.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_8=== 1.U&&push_valid === 1.U&&push_8_1 ===0.U&&LUT_mem(8)(32) ===0.U){
//                 LUT_mem(8) := Cat(1.U,push_ray_id)
//                 // LUT_mem(8)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 1.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_9=== 1.U&&push_valid === 1.U&&push_9_1 ===0.U&&LUT_mem(9)(32) ===0.U){
//                 LUT_mem(9) := Cat(1.U,push_ray_id)
//                 // LUT_mem(9)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 1.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_10=== 1.U&&push_valid === 1.U&&push_10_1 ===0.U&&LUT_mem(10)(32) ===0.U){
//                 LUT_mem(10) := Cat(1.U,push_ray_id)
//                 // LUT_mem(10)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 1.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_11=== 1.U&&push_valid === 1.U&&push_11_1 ===0.U&&LUT_mem(11)(32) ===0.U){
//                 LUT_mem(11) := Cat(1.U,push_ray_id)
//                 // LUT_mem(11)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 1.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_12=== 1.U&&push_valid === 1.U&&push_12_1 ===0.U&&LUT_mem(12)(32) ===0.U){
//                 LUT_mem(12) := Cat(1.U,push_ray_id)
//                 // LUT_mem(12)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 1.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_13=== 1.U&&push_valid === 1.U&&push_13_1 ===0.U&&LUT_mem(13)(32) ===0.U){
//                 LUT_mem(13) := Cat(1.U,push_ray_id)
//                 // LUT_mem(13)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 1.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_14=== 1.U&&push_valid === 1.U&&push_14_1 ===0.U&&LUT_mem(14)(32) ===0.U){
//                 LUT_mem(14) := Cat(1.U,push_ray_id)
//                 // LUT_mem(14)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 1.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_15=== 1.U&&push_valid === 1.U&&push_15_1 ===0.U&&LUT_mem(15)(32) ===0.U){
//                 LUT_mem(15) := Cat(1.U,push_ray_id)
//                 // LUT_mem(15)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 1.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_16=== 1.U&&push_valid === 1.U&&push_16_1 ===0.U&&LUT_mem(16)(32) ===0.U){
//                 LUT_mem(16) := Cat(1.U,push_ray_id)
//                 // LUT_mem(16)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 1.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_17=== 1.U&&push_valid === 1.U&&push_17_1 ===0.U&&LUT_mem(17)(32) ===0.U){
//                 LUT_mem(17) := Cat(1.U,push_ray_id)
//                 // LUT_mem(17)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 1.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_18=== 1.U&&push_valid === 1.U&&push_18_1 ===0.U&&LUT_mem(18)(32) ===0.U){
//                 LUT_mem(18) := Cat(1.U,push_ray_id)
//                 // LUT_mem(18)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 1.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_19=== 1.U&&push_valid === 1.U&&push_19_1 ===0.U&&LUT_mem(19)(32) ===0.U){
//                 LUT_mem(19) := Cat(1.U,push_ray_id)
//                 // LUT_mem(19)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 1.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_20=== 1.U&&push_valid === 1.U&&push_20_1 ===0.U&&LUT_mem(20)(32) ===0.U){
//                 LUT_mem(20) := Cat(1.U,push_ray_id)
//                 // LUT_mem(20)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 1.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_21=== 1.U&&push_valid === 1.U&&push_21_1 ===0.U&&LUT_mem(21)(32) ===0.U){
//                 LUT_mem(21) := Cat(1.U,push_ray_id)
//                 // LUT_mem(21)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 1.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_22=== 1.U&&push_valid === 1.U&&push_22_1 ===0.U&&LUT_mem(22)(32) ===0.U){
//                 LUT_mem(22) := Cat(1.U,push_ray_id)
//                 // LUT_mem(22)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 1.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_23=== 1.U&&push_valid === 1.U&&push_23_1 ===0.U&&LUT_mem(23)(32) ===0.U){
//                 LUT_mem(23) := Cat(1.U,push_ray_id)
//                 // LUT_mem(23)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 1.U
//                 push_24_1                 := 0.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id

//             }.elsewhen(io.empty_24=== 1.U&&push_valid === 1.U&&push_24_1 ===0.U&&LUT_mem(24)(32) ===0.U){
//                 LUT_mem(24) := Cat(1.U,push_ray_id)
//                 // LUT_mem(24)(32)     := 1.U
//                 push_0_1                 := 0.U
//                 push_1_1                 := 0.U
//                 push_2_1                 := 0.U
//                 push_3_1                 := 0.U
//                 push_4_1                 := 0.U
//                 push_5_1                 := 0.U
//                 push_6_1                 := 0.U
//                 push_7_1                 := 0.U
//                 push_8_1                 := 0.U
//                 push_9_1                 := 0.U
//                 push_10_1                 := 0.U
//                 push_11_1                 := 0.U
//                 push_12_1                 := 0.U
//                 push_13_1                 := 0.U
//                 push_14_1                 := 0.U
//                 push_15_1                 := 0.U
//                 push_16_1                 := 0.U
//                 push_17_1                 := 0.U
//                 push_18_1                 := 0.U
//                 push_19_1                 := 0.U
//                 push_20_1                 := 0.U
//                 push_21_1                 := 0.U
//                 push_22_1                 := 0.U
//                 push_23_1                 := 0.U
//                 push_24_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.otherwise{
//                 push_0_1       := false.B
//                 push_1_1       := false.B
//                 push_2_1       := false.B
//                 push_3_1       := false.B
//                 push_4_1       := false.B
//                 push_5_1       := false.B
//                 push_6_1       := false.B
//                 push_7_1       := false.B
//                 push_8_1                 := false.B
//                 push_9_1                 := false.B
//                 push_10_1                 := false.B
//                 push_11_1                 := false.B
//                 push_12_1                 := false.B
//                 push_13_1                 := false.B
//                 push_14_1                 := false.B
//                 push_15_1                 := false.B
//                 push_16_1                 := false.B
//                 push_17_1                 := false.B
//                 push_18_1                 := false.B
//                 push_19_1                 := false.B
//                 push_20_1                 := false.B
//                 push_21_1                 := false.B
//                 push_22_1                 := false.B
//                 push_23_1                 := false.B
//                 push_24_1                 := false.B
//                 push_valid_2 := 0.U
//             }
//         }.otherwise{
//                 push_0_1       := false.B
//                 push_1_1       := false.B
//                 push_2_1       := false.B
//                 push_3_1       := false.B
//                 push_4_1       := false.B
//                 push_5_1       := false.B
//                 push_6_1       := false.B
//                 push_7_1       := false.B
//                 push_8_1                 := false.B
//                 push_9_1                 := false.B
//                 push_10_1                 := false.B
//                 push_11_1                 := false.B
//                 push_12_1                 := false.B
//                 push_13_1                 := false.B
//                 push_14_1                 := false.B
//                 push_15_1                 := false.B
//                 push_16_1                 := false.B
//                 push_17_1                 := false.B
//                 push_18_1                 := false.B
//                 push_19_1                 := false.B
//                 push_20_1                 := false.B
//                 push_21_1                 := false.B
//                 push_22_1                 := false.B
//                 push_23_1                 := false.B
//                 push_24_1                 := false.B
//                 push_valid_2 := 0.U
//         }
//     }.otherwise{
//                 push_0_1       := false.B
//                 push_1_1       := false.B
//                 push_2_1       := false.B
//                 push_3_1       := false.B
//                 push_4_1       := false.B
//                 push_5_1       := false.B
//                 push_6_1       := false.B
//                 push_7_1       := false.B
//                 push_8_1                 := false.B
//                 push_9_1                 := false.B
//                 push_10_1                 := false.B
//                 push_11_1                 := false.B
//                 push_12_1                 := false.B
//                 push_13_1                 := false.B
//                 push_14_1                 := false.B
//                 push_15_1                 := false.B
//                 push_16_1                 := false.B
//                 push_17_1                 := false.B
//                 push_18_1                 := false.B
//                 push_19_1                 := false.B
//                 push_20_1                 := false.B
//                 push_21_1                 := false.B
//                 push_22_1                 := false.B
//                 push_23_1                 := false.B
//                 push_24_1                 := false.B
//                 push_valid_2 := 0.U
//     } 
//         // val push_0_2                        = RegInit(0.U(1.W))
//         // val push_1_2                        = RegInit(0.U(1.W))
//         // val push_2_2                        = RegInit(0.U(1.W))
//         // val push_3_2                        = RegInit(0.U(1.W))
//         // val push_4_2                        = RegInit(0.U(1.W))
//         // val push_5_2                        = RegInit(0.U(1.W))
//         // val push_6_2                        = RegInit(0.U(1.W))
//         // val push_7_2                        = RegInit(0.U(1.W))
//         // val push_valid_3                = RegInit(0.U(1.W))          
//         // val push_node_id_3         = RegInit(0.S(32.W))

//         // push_0_2                                := push_0_1
//         // push_1_2                                := push_1_1
//         // push_2_2                                := push_2_1
//         // push_3_2                                := push_3_1
//         // push_4_2                                := push_4_1
//         // push_5_2                                := push_5_1
//         // push_6_2                                := push_6_1
//         // push_7_2                                := push_7_1
//         // push_valid_3                         := push_valid_2
//         // push_node_id_3                  := push_node_id_2



//         io.push_0                                 := push_0_1
//         io.push_1                                 := push_1_1
//         io.push_2                                 := push_2_1
//         io.push_3                                 := push_3_1
//         io.push_4                                 := push_4_1
//         io.push_5                                 := push_5_1
//         io.push_6                                 := push_6_1
//         io.push_7                                 := push_7_1
//         io.push_8                                 := push_8_1
//         io.push_9                                 := push_9_1
//         io.push_10                                 := push_10_1
//         io.push_11                                 := push_11_1
//         io.push_12                                 := push_12_1
//         io.push_13                                 := push_13_1
//         io.push_14                                 := push_14_1
//         io.push_15                                 := push_15_1
//         io.push_16                                 := push_16_1
//         io.push_17                                 := push_17_1
//         io.push_18                                 := push_18_1
//         io.push_19                                 := push_19_1
//         io.push_20                                 := push_20_1
//         io.push_21                                 := push_21_1
//         io.push_22                                 := push_22_1
//         io.push_23                                 := push_23_1
//         io.push_24                                 := push_24_1
        
//         io.node_id_push_out         := push_node_id_2
//         io.push_en                               := push_valid_2 
//         //pop stage(1)
//         val pop_1                       = RegInit(0.U(1.W))
//         val read_stack0_pop = RegInit(0.U(32.W))//这个是用来和表里的数据进行读取用的，这个是要在栈里的读取信号
//         val read_stack1_pop = RegInit(0.U(32.W))
//         val read_stack2_pop = RegInit(0.U(32.W))
//         val read_stack3_pop = RegInit(0.U(32.W))
//         val read_stack4_pop = RegInit(0.U(32.W))
//         val read_stack5_pop = RegInit(0.U(32.W))
//         val read_stack6_pop = RegInit(0.U(32.W))
//         val read_stack7_pop = RegInit(0.U(32.W))
//         val read_stack8_pop = RegInit(0.U(32.W))
//         val read_stack9_pop = RegInit(0.U(32.W))
//         val read_stack10_pop = RegInit(0.U(32.W))
//         val read_stack11_pop = RegInit(0.U(32.W))
//         val read_stack12_pop = RegInit(0.U(32.W))
//         val read_stack13_pop = RegInit(0.U(32.W))
//         val read_stack14_pop = RegInit(0.U(32.W))
//         val read_stack15_pop = RegInit(0.U(32.W))
//         val read_stack16_pop = RegInit(0.U(32.W))
//         val read_stack17_pop = RegInit(0.U(32.W))
//         val read_stack18_pop = RegInit(0.U(32.W))
//         val read_stack19_pop = RegInit(0.U(32.W))
//         val read_stack20_pop = RegInit(0.U(32.W))
//         val read_stack21_pop = RegInit(0.U(32.W))
//         val read_stack22_pop = RegInit(0.U(32.W))
//         val read_stack23_pop = RegInit(0.U(32.W))
//         val read_stack24_pop = RegInit(0.U(32.W))
        
//         val pop_node_id = RegInit(0.S(32.W))
//         val pop_ray_id     = RegInit(0.U(32.W))
//         val pop_hitT_1     = RegInit(0.U(32.W))
//         val pop_valid     = RegInit(0.U(1.W))

//         //pop stage(2)
//         val pop_0_1                 = RegInit(0.U(1.W))
//         val pop_1_1                 = RegInit(0.U(1.W))
//         val pop_2_1                 = RegInit(0.U(1.W))
//         val pop_3_1                 = RegInit(0.U(1.W))
//         val pop_4_1                 = RegInit(0.U(1.W))
//         val pop_5_1                 = RegInit(0.U(1.W))
//         val pop_6_1                 = RegInit(0.U(1.W))
//         val pop_7_1                 = RegInit(0.U(1.W))
//         val pop_8_1                 = RegInit(0.U(1.W))
//         val pop_9_1                 = RegInit(0.U(1.W))
//         val pop_10_1                 = RegInit(0.U(1.W))
//         val pop_11_1                 = RegInit(0.U(1.W))
//         val pop_12_1                 = RegInit(0.U(1.W))
//         val pop_13_1                 = RegInit(0.U(1.W))
//         val pop_14_1                 = RegInit(0.U(1.W))
//         val pop_15_1                 = RegInit(0.U(1.W))
//         val pop_16_1                 = RegInit(0.U(1.W))
//         val pop_17_1                 = RegInit(0.U(1.W))
//         val pop_18_1                 = RegInit(0.U(1.W))
//         val pop_19_1                 = RegInit(0.U(1.W))
//         val pop_20_1                 = RegInit(0.U(1.W))
//         val pop_21_1                 = RegInit(0.U(1.W))
//         val pop_22_1                 = RegInit(0.U(1.W))
//         val pop_23_1                 = RegInit(0.U(1.W))
//         val pop_24_1                 = RegInit(0.U(1.W))
        
//         val pop_valid_2          = RegInit(0.U(1.W))
//         val pop_node_id_2   = RegInit(0.S(32.W))
//         val pop_ray_id_2       = RegInit(0.U(32.W))
//         val pop_hitT_2            = RegInit(0.U(32.W))

//         read_stack0_pop      := LUT_mem(0)(31,0)
//         read_stack1_pop      := LUT_mem(1)(31,0)
//         read_stack2_pop      := LUT_mem(2)(31,0)
//         read_stack3_pop      := LUT_mem(3)(31,0)
//         read_stack4_pop      := LUT_mem(4)(31,0)
//         read_stack5_pop      := LUT_mem(5)(31,0)
//         read_stack6_pop      := LUT_mem(6)(31,0)
//         read_stack7_pop      := LUT_mem(7)(31,0)
//         read_stack8_pop      := LUT_mem(8)(31,0)
//         read_stack9_pop      := LUT_mem(9)(31,0)

//         read_stack10_pop      := LUT_mem(10)(31,0)
//         read_stack11_pop      := LUT_mem(11)(31,0)
//         read_stack12_pop      := LUT_mem(12)(31,0)
//         read_stack13_pop      := LUT_mem(13)(31,0)
//         read_stack14_pop      := LUT_mem(14)(31,0)
//         read_stack15_pop      := LUT_mem(15)(31,0)
//         read_stack16_pop      := LUT_mem(16)(31,0)
//         read_stack17_pop      := LUT_mem(17)(31,0)
//         read_stack18_pop      := LUT_mem(18)(31,0)
//         read_stack19_pop      := LUT_mem(19)(31,0)

//         read_stack20_pop      := LUT_mem(20)(31,0)
//         read_stack21_pop      := LUT_mem(21)(31,0)
//         read_stack22_pop      := LUT_mem(22)(31,0)
//         read_stack23_pop      := LUT_mem(23)(31,0)
//         read_stack24_pop      := LUT_mem(24)(31,0)
        
//     when(io.pop === true.B && io.pop_valid){//这里pop是来自整体的输入

//         pop_1                  := 1.U
//         pop_valid          := 1.U
//         pop_node_id   := io.node_id_pop_in
//         pop_ray_id       := io.ray_id_pop
//         pop_hitT_1       := io.hitT_in
//     }.otherwise{
//         pop_valid          := 0.U
//         pop_1                  :=0.U
//     }
    
//     when(pop_1 === 1.U && pop_valid  === 1.U){
//         when(read_stack0_pop === pop_ray_id && pop_valid  === 1.U){
//             pop_0_1                    := 1.U
//             pop_1_1                    := 0.U
//             pop_2_1                    := 0.U
//             pop_3_1                    := 0.U
//             pop_4_1                    := 0.U
//             pop_5_1                    := 0.U
//             pop_6_1                    := 0.U
//             pop_7_1                    := 0.U
//             pop_8_1                    := 0.U
//             pop_9_1                    := 0.U
//             pop_10_1                    := 0.U
//             pop_11_1                    := 0.U         
//             pop_12_1                    := 0.U
//             pop_13_1                    := 0.U
//             pop_14_1                    := 0.U
//             pop_15_1                    := 0.U
//             pop_16_1                    := 0.U
//             pop_17_1                    := 0.U
//             pop_18_1                    := 0.U
//             pop_19_1                    := 0.U
//             pop_20_1                    := 0.U
//             pop_21_1                    := 0.U         
//             pop_22_1                    := 0.U
//             pop_23_1                    := 0.U
//             pop_24_1                    := 0.U
//             pop_valid_2            := 1.U
//             pop_node_id_2     := pop_node_id
//             pop_ray_id_2         := pop_ray_id
//             pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack1_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 1.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack2_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 1.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack3_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 1.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack4_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 1.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack5_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 1.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack6_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 1.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack7_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 1.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack8_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 1.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1

//             }.elsewhen(read_stack9_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 1.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack10_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 1.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack11_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 1.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack12_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 1.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack13_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 1.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack14_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 1.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack15_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 1.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack16_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 1.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack17_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 1.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack18_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 1.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack19_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 1.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack20_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 1.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack21_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 1.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack22_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 1.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack23_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 1.U
//                 pop_24_1                    := 0.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack24_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_0_1                    := 0.U
//                 pop_1_1                    := 0.U
//                 pop_2_1                    := 0.U
//                 pop_3_1                    := 0.U
//                 pop_4_1                    := 0.U
//                 pop_5_1                    := 0.U
//                 pop_6_1                    := 0.U
//                 pop_7_1                    := 0.U
//                 pop_8_1                    := 0.U
//                 pop_9_1                    := 0.U
//                 pop_10_1                    := 0.U
//                 pop_11_1                    := 0.U         
//                 pop_12_1                    := 0.U
//                 pop_13_1                    := 0.U
//                 pop_14_1                    := 0.U
//                 pop_15_1                    := 0.U
//                 pop_16_1                    := 0.U
//                 pop_17_1                    := 0.U
//                 pop_18_1                    := 0.U
//                 pop_19_1                    := 0.U
//                 pop_20_1                    := 0.U
//                 pop_21_1                    := 0.U         
//                 pop_22_1                    := 0.U
//                 pop_23_1                    := 0.U
//                 pop_24_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.otherwise{
//                 pop_0_1                    := false.B
//                 pop_1_1                    := false.B
//                 pop_2_1                    := false.B
//                 pop_3_1                    := false.B
//                 pop_4_1                    := false.B
//                 pop_5_1                    := false.B
//                 pop_6_1                    := false.B
//                 pop_7_1                    := false.B
//                 pop_8_1                    := false.B
//                 pop_9_1                    := false.B
//                 pop_10_1                    := false.B
//                 pop_11_1                    := false.B         
//                 pop_12_1                    := false.B
//                 pop_13_1                    := false.B
//                 pop_14_1                    := false.B
//                 pop_15_1                    := false.B
//                 pop_16_1                    := false.B
//                 pop_17_1                    := false.B
//                 pop_18_1                    := false.B
//                 pop_19_1                    := false.B
//                 pop_20_1                    := false.B
//                 pop_21_1                    := false.B         
//                 pop_22_1                    := false.B
//                 pop_23_1                    := false.B
//                 pop_24_1                    := false.B
//                 pop_valid_2             := 0.U
//         }
//     }.otherwise{
//             pop_0_1                    := false.B
//             pop_1_1                    := false.B
//             pop_2_1                    := false.B
//             pop_3_1                    := false.B
//             pop_4_1                    := false.B
//             pop_5_1                    := false.B
//             pop_6_1                    := false.B
//             pop_7_1                    := false.B
//             pop_8_1                    := false.B
//             pop_9_1                    := false.B
//             pop_10_1                    := false.B
//             pop_11_1                    := false.B         
//             pop_12_1                    := false.B
//             pop_13_1                    := false.B
//             pop_14_1                    := false.B
//             pop_15_1                    := false.B
//             pop_16_1                    := false.B
//             pop_17_1                    := false.B
//             pop_18_1                    := false.B
//             pop_19_1                    := false.B
//             pop_20_1                    := false.B
//             pop_21_1                    := false.B         
//             pop_22_1                    := false.B
//             pop_23_1                    := false.B
//             pop_24_1                    := false.B
//             pop_valid_2             := 0.U
//     }

//             io.pop_0                                    := pop_0_1
//             io.pop_1                                    := pop_1_1
//             io.pop_2                                    := pop_2_1
//             io.pop_3                                    := pop_3_1
//             io.pop_4                                    := pop_4_1
//             io.pop_5                                    := pop_5_1
//             io.pop_6                                    := pop_6_1
//             io.pop_7                                    := pop_7_1
//             io.pop_8                                    := pop_8_1
//             io.pop_9                                    := pop_9_1

//             io.pop_10                                    := pop_10_1
//             io.pop_11                                    := pop_11_1
//             io.pop_12                                    := pop_12_1
//             io.pop_13                                    := pop_13_1
//             io.pop_14                                    := pop_14_1
//             io.pop_15                                    := pop_15_1
//             io.pop_16                                    := pop_16_1
//             io.pop_17                                    := pop_17_1
//             io.pop_18                                    := pop_18_1
//             io.pop_19                                    := pop_19_1

//             io.pop_20                                    := pop_20_1
//             io.pop_21                                    := pop_21_1
//             io.pop_22                                    := pop_22_1
//             io.pop_23                                    := pop_23_1
//             io.pop_24                                    := pop_24_1
            


//             io.pop_en                                 := pop_valid_2
//             io.ray_id_pop_out                := pop_ray_id_2
//             io.hitT_out                                := pop_hitT_2
//             io.node_id_pop_out           := pop_node_id_2
// }   
        
// object LUT extends App {
//   chisel3.Driver.execute(args, () => new LUT())
// }

// class LUTeste (dut:LUT) extends PeekPokeTester(dut){
//         poke(dut.io.push,1.U)
//         poke(dut.io.push_valid,1.U)
//         poke(dut.io.empty_0,1.U)
//         poke(dut.io.empty_1,1.U)
//         poke(dut.io.empty_2,1.U)
//         poke(dut.io.empty_3,1.U)
//         poke(dut.io.empty_4,1.U)
//         poke(dut.io.empty_5,1.U)
//         poke(dut.io.empty_6,1.U)
//         poke(dut.io.empty_7,1.U)
//         poke(dut.io.ray_id_push,1.U)
//         poke(dut.io.node_id_push_in,2.U)

//         step(1)
//         poke(dut.io.push,1.U)
//         poke(dut.io.push_valid,1.U)
//         poke(dut.io.empty_0,0.U)
//         poke(dut.io.empty_1,1.U)
//         poke(dut.io.empty_2,1.U)
//         poke(dut.io.empty_3,1.U)
//         poke(dut.io.empty_4,1.U)
//         poke(dut.io.empty_5,1.U)
//         poke(dut.io.empty_6,1.U)
//         poke(dut.io.empty_7,1.U)
//         poke(dut.io.ray_id_push,2.U)
//         poke(dut.io.node_id_push_in,3.U)
        
//         step(1)
//         poke(dut.io.push,1.U)
//         poke(dut.io.push_valid,1.U)
//         poke(dut.io.empty_0,0.U)
//         poke(dut.io.empty_1,1.U)
//         poke(dut.io.empty_2,1.U)
//         poke(dut.io.empty_3,1.U)
//         poke(dut.io.empty_4,1.U)
//         poke(dut.io.empty_5,1.U)
//         poke(dut.io.empty_6,1.U)
//         poke(dut.io.empty_7,1.U)
//         poke(dut.io.ray_id_push,3.U)
//         poke(dut.io.node_id_push_in,4.U)
//         step(1)
//         poke(dut.io.push,1.U)
//         poke(dut.io.push_valid,1.U)
//         poke(dut.io.empty_0,0.U)
//         poke(dut.io.empty_1,0.U)
//         poke(dut.io.empty_2,1.U)
//         poke(dut.io.empty_3,1.U)
//         poke(dut.io.empty_4,1.U)
//         poke(dut.io.empty_5,1.U)
//         poke(dut.io.empty_6,1.U)
//         poke(dut.io.empty_7,1.U)
//         poke(dut.io.ray_id_push,4.U)
//         poke(dut.io.node_id_push_in,5.U)

//         step(1)
//         poke(dut.io.push,0.U)//这里的意思是这个周期不压栈了
//         poke(dut.io.push_valid,0.U)
//         poke(dut.io.pop,1.U)
//         poke(dut.io.pop_valid,1.U)
//         poke(dut.io.ray_id_pop,1.U)
//         poke(dut.io.node_id_pop_in,10.U)
//         poke(dut.io.hitT_in,11.U)

//         step(1)
//         poke(dut.io.push,0.U)//这里的意思是这个周期不压栈了
//         poke(dut.io.push_valid,0.U)
//         poke(dut.io.pop,1.U)
//         poke(dut.io.pop_valid,1.U)
//         poke(dut.io.ray_id_pop,2.U)
//         poke(dut.io.node_id_pop_in,12.U)
//         poke(dut.io.hitT_in,13.U)
//         step(1)
//         poke(dut.io.pop,0.U)
//         poke(dut.io.pop_valid,0.U)
//         step(10)
//         // poke(dut.io.wr,0.S)
//         // poke(dut.io.rd,1.S)
//         //  step(2)
//         // poke(dut.io.rd,1.S)
//         //  step(1)
//         // poke(dut.io.rd,1.S)
//         // step(70)
// }
// object LUTester extends App {
//   chisel3.iotesters.Driver.execute(args, () => new LUT())(c => new LUTeste(c))
// }

