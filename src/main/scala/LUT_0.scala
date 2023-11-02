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
//         val empty_25   = Input(Bool())
//         val empty_26   = Input(Bool())
//         val empty_27   = Input(Bool())
//         val empty_28   = Input(Bool())
//         val empty_29   = Input(Bool())
//         val empty_30   = Input(Bool())
//         val empty_31   = Input(Bool())
//         val empty_32   = Input(Bool())
//         val empty_33   = Input(Bool())
//         val empty_34   = Input(Bool())

//         val empty_35   = Input(Bool())
//         val empty_36   = Input(Bool())
//         val empty_37   = Input(Bool())
//         val empty_38   = Input(Bool())
//         val empty_39   = Input(Bool())
//         val empty_40   = Input(Bool())
//         val empty_41   = Input(Bool())
//         val empty_42   = Input(Bool())
//         val empty_43   = Input(Bool())
    
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
//         val pop_25        =  Output(Bool())
//         val pop_26        =  Output(Bool())
//         val pop_27        =  Output(Bool())
//         val pop_28        =  Output(Bool())
//         val pop_29        =  Output(Bool())
//         val pop_30        =  Output(Bool())
//         val pop_31        =  Output(Bool())
//         val pop_32        =  Output(Bool())
//         val pop_33        =  Output(Bool())
//         val pop_34        =  Output(Bool())
//         val pop_35        =  Output(Bool())
//         val pop_36        =  Output(Bool())
//         val pop_37        =  Output(Bool())
//         val pop_38        =  Output(Bool())
//         val pop_39        =  Output(Bool())
//         val pop_40        =  Output(Bool())
//         val pop_41        =  Output(Bool())
//         val pop_42        =  Output(Bool())
//         val pop_43        =  Output(Bool())

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
//         val push_25      =  Output(Bool())
//         val push_26      =  Output(Bool())
//         val push_27      =  Output(Bool())
//         val push_28      =  Output(Bool())
//         val push_29      =  Output(Bool())
//         val push_30      =  Output(Bool())
//         val push_31      =  Output(Bool())
//         val push_32      =  Output(Bool())
//         val push_33      =  Output(Bool())
//         val push_34      =  Output(Bool())
//         val push_35      =  Output(Bool())
//         val push_36      =  Output(Bool())
//         val push_37      =  Output(Bool())
//         val push_38      =  Output(Bool())
//         val push_39      =  Output(Bool())
//         val push_40      =  Output(Bool())
//         val push_41      =  Output(Bool())
//         val push_42      =  Output(Bool())
//         val push_43      =  Output(Bool())

//         val push_en   =  Output(Bool())
//     })

//     val LUT_mem     = Mem(44,UInt(32.W))
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
//     val read_stack25 = RegInit(0.U(32.W))
//     val read_stack26 = RegInit(0.U(32.W))
//     val read_stack27 = RegInit(0.U(32.W))
//     val read_stack28 = RegInit(0.U(32.W))
//     val read_stack29 = RegInit(0.U(32.W))
//     val read_stack30 = RegInit(0.U(32.W))
//     val read_stack31 = RegInit(0.U(32.W))
//     val read_stack32 = RegInit(0.U(32.W))
//     val read_stack33 = RegInit(0.U(32.W))
//     val read_stack34 = RegInit(0.U(32.W))
//     val read_stack35 = RegInit(0.U(32.W))
//     val read_stack36 = RegInit(0.U(32.W))
//     val read_stack37 = RegInit(0.U(32.W))
//     val read_stack38 = RegInit(0.U(32.W))
//     val read_stack39 = RegInit(0.U(32.W))
//     val read_stack40 = RegInit(0.U(32.W))
//     val read_stack41 = RegInit(0.U(32.W))
//     val read_stack42 = RegInit(0.U(32.W))
//     val read_stack43 = RegInit(0.U(32.W))
    
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
//     val push_25_1     = RegInit(0.U(1.W))
//     val push_26_1     = RegInit(0.U(1.W))
//     val push_27_1     = RegInit(0.U(1.W))
//     val push_28_1     = RegInit(0.U(1.W))
//     val push_29_1     = RegInit(0.U(1.W))
//     val push_30_1     = RegInit(0.U(1.W))
//     val push_31_1     = RegInit(0.U(1.W))
//     val push_32_1     = RegInit(0.U(1.W))
//     val push_33_1     = RegInit(0.U(1.W))
//     val push_34_1     = RegInit(0.U(1.W))
//     val push_35_1     = RegInit(0.U(1.W))
//     val push_36_1     = RegInit(0.U(1.W))
//     val push_37_1     = RegInit(0.U(1.W))
//     val push_38_1     = RegInit(0.U(1.W))
//     val push_39_1     = RegInit(0.U(1.W))
//     val push_40_1     = RegInit(0.U(1.W))
//     val push_41_1     = RegInit(0.U(1.W))
//     val push_42_1     = RegInit(0.U(1.W))
//     val push_43_1     = RegInit(0.U(1.W))

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
//     val empty_25_1             = RegInit(0.U(1.W))
//     val empty_26_1             = RegInit(0.U(1.W))
//     val empty_27_1             = RegInit(0.U(1.W))
//     val empty_28_1             = RegInit(0.U(1.W))
//     val empty_29_1             = RegInit(0.U(1.W))
//     val empty_30_1             = RegInit(0.U(1.W))
//     val empty_31_1             = RegInit(0.U(1.W))
//     val empty_32_1             = RegInit(0.U(1.W))
//     val empty_33_1             = RegInit(0.U(1.W))
//     val empty_34_1             = RegInit(0.U(1.W))
//     val empty_35_1             = RegInit(0.U(1.W))
//     val empty_36_1             = RegInit(0.U(1.W))
//     val empty_37_1             = RegInit(0.U(1.W))
//     val empty_38_1             = RegInit(0.U(1.W))
//     val empty_39_1             = RegInit(0.U(1.W))
//     val empty_40_1             = RegInit(0.U(1.W))
//     val empty_41_1             = RegInit(0.U(1.W))
//     val empty_42_1             = RegInit(0.U(1.W))
//     val empty_43_1             = RegInit(0.U(1.W))

//     //push 的话第二个stage(2)
//     val push_ray_id_2        = RegInit(0.U(32.W))
//     val push_node_id_2    = RegInit(0.S(32.W))
//     val push_valid_2           = RegInit(0.U(1.W))
    
//     val push_stack1_en   = RegInit(0.U(1.W))//这个的意义是已经取了数，判断和查找表里的内容是否匹配，如果匹配就压栈，如果不匹配，就去找空的
//     val find_empty             = RegInit(0.U(1.W))//这个的意义是当前的栈没有找到匹配的，需要压到新的栈里


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
//         empty_25_1        := io.empty_25
//         empty_26_1        := io.empty_26
//         empty_27_1        := io.empty_27
//         empty_28_1        := io.empty_28
//         empty_29_1        := io.empty_29
//         empty_30_1        := io.empty_30
//         empty_31_1        := io.empty_31
//         empty_32_1        := io.empty_32
//         empty_33_1        := io.empty_33
//         empty_34_1        := io.empty_34
//         empty_35_1        := io.empty_35
//         empty_36_1        := io.empty_36
//         empty_37_1        := io.empty_37
//         empty_38_1        := io.empty_38
//         empty_39_1        := io.empty_39
//         empty_40_1        := io.empty_40
//         empty_41_1        := io.empty_41
//         empty_42_1        := io.empty_42
//         empty_43_1        := io.empty_43
        
//     // val push_from_io      = RegInit(0.U(1.W))
//     // val push_v_from_io = RegInit(0.U(1.W))

//         read_stack0      := LUT_mem(0)
//         read_stack1      := LUT_mem(1)
//         read_stack2      := LUT_mem(2)
//         read_stack3      := LUT_mem(3)
//         read_stack4      := LUT_mem(4)
//         read_stack5      := LUT_mem(5)
//         read_stack6      := LUT_mem(6)
//         read_stack7      := LUT_mem(7)  
//         read_stack8      := LUT_mem(8)
//         read_stack9      := LUT_mem(9)  
//         read_stack10      := LUT_mem(10)
//         read_stack11      := LUT_mem(11)
//         read_stack12      := LUT_mem(12)
//         read_stack13      := LUT_mem(13)
//         read_stack14      := LUT_mem(14)
//         read_stack15      := LUT_mem(15)
//         read_stack16      := LUT_mem(16)
//         read_stack17      := LUT_mem(17) 
//         read_stack18      := LUT_mem(18)
//         read_stack19      := LUT_mem(19)  
//         read_stack20      := LUT_mem(20)
//         read_stack21      := LUT_mem(21)
//         read_stack22      := LUT_mem(22)
//         read_stack23      := LUT_mem(23)
//         read_stack24      := LUT_mem(24)
//         read_stack25      := LUT_mem(25)
//         read_stack26      := LUT_mem(26)
//         read_stack27      := LUT_mem(27) 
//         read_stack28      := LUT_mem(28)
//         read_stack29      := LUT_mem(29)  
//         read_stack30      := LUT_mem(30)
//         read_stack31      := LUT_mem(31)
//         read_stack32      := LUT_mem(32)
//         read_stack33      := LUT_mem(33)
//         read_stack34      := LUT_mem(34)
//         read_stack35      := LUT_mem(35)
//         read_stack36      := LUT_mem(36)
//         read_stack37      := LUT_mem(37) 
//         read_stack38      := LUT_mem(38)
//         read_stack39      := LUT_mem(39)  
//         read_stack40      := LUT_mem(40)
//         read_stack41      := LUT_mem(41)
//         read_stack42      := LUT_mem(42)
//         read_stack43      := LUT_mem(43)
    
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
//         when(LUT_mem(0) === push_ray_id &&push_valid === 1.U){
//             push_0_1                 := 1.U
//             push_valid_2         :=  1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(1) === push_ray_id &&push_valid === 1.U){
//             push_1_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(2) === push_ray_id&&push_valid === 1.U){
//             push_2_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(3) === push_ray_id&&push_valid === 1.U){
//             push_3_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(4) === push_ray_id&&push_valid === 1.U){
//             push_4_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2 := push_node_id
//         }.elsewhen(LUT_mem(5) === push_ray_id&&push_valid === 1.U){
//             push_5_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(6) === push_ray_id&&push_valid === 1.U){
//             push_6_1                 := 1.U
//             push_valid_2         := 1.U
//            push_node_id_2  := push_node_id
//         }.elsewhen(LUT_mem(7) === push_ray_id&&push_valid === 1.U){
//             push_7_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(8) === push_ray_id&&push_valid === 1.U){
//             push_8_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(9) === push_ray_id&&push_valid === 1.U){
//             push_9_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(10) === push_ray_id&&push_valid === 1.U){
//             push_10_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(11) === push_ray_id&&push_valid === 1.U){
//             push_11_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(12) === push_ray_id&&push_valid === 1.U){
//             push_12_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(13) === push_ray_id&&push_valid === 1.U){
//             push_13_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(14) === push_ray_id&&push_valid === 1.U){
//             push_14_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(15) === push_ray_id&&push_valid === 1.U){
//             push_15_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(16) === push_ray_id&&push_valid === 1.U){
//             push_16_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(17) === push_ray_id&&push_valid === 1.U){
//             push_17_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(18) === push_ray_id&&push_valid === 1.U){
//             push_18_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(19) === push_ray_id&&push_valid === 1.U){
//             push_19_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id 
//         }.elsewhen(LUT_mem(20) === push_ray_id&&push_valid === 1.U){
//             push_20_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id 
//         }.elsewhen(LUT_mem(21) === push_ray_id&&push_valid === 1.U){
//             push_21_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(22) === push_ray_id&&push_valid === 1.U){
//             push_22_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(23) === push_ray_id&&push_valid === 1.U){
//             push_23_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(24) === push_ray_id&&push_valid === 1.U){
//             push_24_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(25) === push_ray_id&&push_valid === 1.U){
//             push_25_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(26) === push_ray_id&&push_valid === 1.U){
//             push_26_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(27) === push_ray_id&&push_valid === 1.U){
//             push_27_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(28) === push_ray_id&&push_valid === 1.U){
//             push_28_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(29) === push_ray_id&&push_valid === 1.U){
//             push_29_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(30) === push_ray_id&&push_valid === 1.U){
//             push_30_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(31) === push_ray_id&&push_valid === 1.U){
//             push_31_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(32) === push_ray_id&&push_valid === 1.U){
//             push_32_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(33) === push_ray_id&&push_valid === 1.U){
//             push_33_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(34) === push_ray_id&&push_valid === 1.U){
//             push_34_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(35) === push_ray_id&&push_valid === 1.U){
//             push_35_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(36) === push_ray_id&&push_valid === 1.U){
//             push_36_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//          }.elsewhen(LUT_mem(37) === push_ray_id&&push_valid === 1.U){
//             push_37_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(38) === push_ray_id&&push_valid === 1.U){
//             push_38_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(39) === push_ray_id&&push_valid === 1.U){
//             push_39_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(40) === push_ray_id&&push_valid === 1.U){
//             push_40_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(41) === push_ray_id&&push_valid === 1.U){
//             push_41_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(42) === push_ray_id&&push_valid === 1.U){
//             push_42_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id
//         }.elsewhen(LUT_mem(43) === push_ray_id&&push_valid === 1.U){
//             push_43_1                 := 1.U
//             push_valid_2         := 1.U
//             push_node_id_2:= push_node_id

//         }.elsewhen(read_stack0 =/= push_ray_id && read_stack1 =/= push_ray_id && read_stack2 =/= push_ray_id && read_stack3 =/= push_ray_id && read_stack4 =/= push_ray_id && read_stack5 =/= push_ray_id && read_stack6 =/= push_ray_id && read_stack7 =/= push_ray_id&& read_stack8 =/= push_ray_id
//         && read_stack9 =/= push_ray_id&& read_stack10 =/= push_ray_id&& read_stack11 =/= push_ray_id&& read_stack12 =/= push_ray_id&& read_stack13 =/= push_ray_id&& read_stack14 =/= push_ray_id&& read_stack15 =/= push_ray_id
//         && read_stack16 =/= push_ray_id&& read_stack17 =/= push_ray_id&& read_stack18 =/= push_ray_id&& read_stack19 =/= push_ray_id&& read_stack20 =/= push_ray_id&& read_stack21 =/= push_ray_id
//         && read_stack22 =/= push_ray_id&& read_stack23 =/= push_ray_id&& read_stack24 =/= push_ray_id&& read_stack25 =/= push_ray_id&& read_stack26 =/= push_ray_id&& read_stack27 =/= push_ray_id
//         && read_stack28 =/= push_ray_id&& read_stack29 =/= push_ray_id&& read_stack30 =/= push_ray_id&& read_stack31 =/= push_ray_id&& read_stack32 =/= push_ray_id&& read_stack33 =/= push_ray_id&& read_stack34 =/= push_ray_id
//         && read_stack35 =/= push_ray_id&& read_stack36 =/= push_ray_id&& read_stack37 =/= push_ray_id
//         && read_stack38 =/= push_ray_id&& read_stack39 =/= push_ray_id&& read_stack40 =/= push_ray_id&& read_stack41 =/= push_ray_id&& read_stack42 =/= push_ray_id&& read_stack43 =/= push_ray_id&&(push_valid === 1.U)){
//                 when(io.empty_0 === 1.U&&push_valid === 1.U &&push_0_1 ===0.U){
//                 LUT_mem(0) := push_ray_id
//                 push_0_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_1=== 1.U&&push_valid === 1.U&&push_1_1 ===0.U){
//                 LUT_mem(1) := push_ray_id
//                 push_1_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_2=== 1.U&&push_valid === 1.U&&push_2_1 ===0.U){
//                 LUT_mem(2) := push_ray_id
//                 push_2_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_3=== 1.U&&push_valid === 1.U&&push_3_1 ===0.U){
//                 LUT_mem(3) := push_ray_id
//                 push_3_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2      := push_node_id
//             }.elsewhen(io.empty_4=== 1.U&&push_valid === 1.U&&push_4_1 ===0.U){
//                 LUT_mem(4) := push_ray_id
//                 push_4_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_5=== 1.U&&push_valid === 1.U&&push_5_1 ===0.U){
//                 LUT_mem(5) := push_ray_id
//                 push_5_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_6=== 1.U&&push_valid === 1.U&&push_6_1 ===0.U){
//                 LUT_mem(6) := push_ray_id
//                 push_6_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_7=== 1.U&&push_valid === 1.U&&push_7_1 ===0.U){
//                 LUT_mem(7) := push_ray_id
//                 push_7_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_8=== 1.U&&push_valid === 1.U&&push_8_1 ===0.U){
//                 LUT_mem(8) := push_ray_id
//                 push_8_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_9=== 1.U&&push_valid === 1.U&&push_9_1 ===0.U){
//                 LUT_mem(9) := push_ray_id
//                 push_9_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_10=== 1.U&&push_valid === 1.U&&push_10_1 ===0.U){
//                 LUT_mem(10) := push_ray_id
//                 push_10_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_11=== 1.U&&push_valid === 1.U&&push_11_1 ===0.U){
//                 LUT_mem(11) := push_ray_id
//                 push_11_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_12=== 1.U&&push_valid === 1.U&&push_12_1 ===0.U){
//                 LUT_mem(12) := push_ray_id
//                 push_12_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_13=== 1.U&&push_valid === 1.U&&push_13_1 ===0.U){
//                 LUT_mem(13) := push_ray_id
//                 push_13_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_14=== 1.U&&push_valid === 1.U&&push_14_1 ===0.U){
//                 LUT_mem(14) := push_ray_id
//                 push_14_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_15=== 1.U&&push_valid === 1.U&&push_15_1 ===0.U){
//                 LUT_mem(15) := push_ray_id
//                 push_15_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_16=== 1.U&&push_valid === 1.U&&push_16_1 ===0.U){
//                 LUT_mem(16) := push_ray_id
//                 push_16_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_17=== 1.U&&push_valid === 1.U&&push_17_1 ===0.U){
//                 LUT_mem(17) := push_ray_id
//                 push_17_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_18=== 1.U&&push_valid === 1.U&&push_18_1 ===0.U){
//                 LUT_mem(18) := push_ray_id
//                 push_18_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_19=== 1.U&&push_valid === 1.U&&push_19_1 ===0.U){
//                 LUT_mem(19) := push_ray_id
//                 push_19_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_20=== 1.U&&push_valid === 1.U&&push_20_1 ===0.U){
//                 LUT_mem(20) := push_ray_id
//                 push_20_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_21=== 1.U&&push_valid === 1.U&&push_21_1 ===0.U){
//                 LUT_mem(21) := push_ray_id
//                 push_21_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_22=== 1.U&&push_valid === 1.U&&push_22_1 ===0.U){
//                 LUT_mem(22) := push_ray_id
//                 push_22_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_23=== 1.U&&push_valid === 1.U&&push_23_1 ===0.U){
//                 LUT_mem(23) := push_ray_id
//                 push_23_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id

//             }.elsewhen(io.empty_24=== 1.U&&push_valid === 1.U&&push_24_1 ===0.U){
//                 LUT_mem(24) := push_ray_id
//                 push_24_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_25=== 1.U&&push_valid === 1.U&&push_25_1 ===0.U){
//                 LUT_mem(25) := push_ray_id
//                 push_25_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_26=== 1.U&&push_valid === 1.U&&push_26_1 ===0.U){
//                 LUT_mem(26) := push_ray_id
//                 push_26_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_27=== 1.U&&push_valid === 1.U&&push_27_1 ===0.U){
//                 LUT_mem(27) := push_ray_id
//                 push_27_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_28=== 1.U&&push_valid === 1.U&&push_28_1 ===0.U){
//                 LUT_mem(28) := push_ray_id
//                 push_28_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_29=== 1.U&&push_valid === 1.U&&push_29_1 ===0.U){
//                 LUT_mem(29) := push_ray_id
//                 push_29_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_30=== 1.U&&push_valid === 1.U&&push_30_1 ===0.U){
//                 LUT_mem(30) := push_ray_id
//                 push_30_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_31=== 1.U&&push_valid === 1.U&&push_31_1 ===0.U){
//                 LUT_mem(31) := push_ray_id
//                 push_31_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_32=== 1.U&&push_valid === 1.U&&push_32_1 ===0.U){
//                 LUT_mem(32) := push_ray_id
//                 push_32_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_33=== 1.U&&push_valid === 1.U&&push_33_1 ===0.U){
//                 LUT_mem(33) := push_ray_id
//                 push_33_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_34=== 1.U&&push_valid === 1.U&&push_34_1 ===0.U){
//                 LUT_mem(34) := push_ray_id
//                 push_34_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//                  }.elsewhen(io.empty_35=== 1.U&&push_valid === 1.U&&push_35_1 ===0.U){
//                 LUT_mem(35) := push_ray_id
//                 push_35_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_36=== 1.U&&push_valid === 1.U&&push_36_1 ===0.U){
//                 LUT_mem(36) := push_ray_id
//                 push_36_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_37=== 1.U&&push_valid === 1.U&&push_37_1 ===0.U){
//                 LUT_mem(37) := push_ray_id
//                 push_37_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_38=== 1.U&&push_valid === 1.U&&push_38_1 ===0.U){
//                 LUT_mem(38) := push_ray_id
//                 push_38_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_39=== 1.U&&push_valid === 1.U&&push_39_1 ===0.U){
//                 LUT_mem(39) := push_ray_id
//                 push_39_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_40=== 1.U&&push_valid === 1.U&&push_40_1 ===0.U){
//                 LUT_mem(40) := push_ray_id
//                 push_40_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_41=== 1.U&&push_valid === 1.U&&push_41_1 ===0.U){
//                 LUT_mem(41) := push_ray_id
//                 push_41_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_42=== 1.U&&push_valid === 1.U&&push_42_1 ===0.U){
//                 LUT_mem(42) := push_ray_id
//                 push_42_1                 := 1.U
//                 push_valid_2         := 1.U
//                 push_node_id_2     := push_node_id
//             }.elsewhen(io.empty_43=== 1.U&&push_valid === 1.U&&push_43_1 ===0.U){
//                 LUT_mem(43) := push_ray_id
//                 push_43_1                 := 1.U
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
//                 push_25_1                 := false.B
//                 push_26_1                 := false.B
//                 push_27_1                 := false.B
//                 push_28_1                 := false.B
//                 push_29_1                 := false.B
//                 push_30_1                 := false.B
//                 push_31_1                 := false.B
//                 push_32_1                 := false.B
//                 push_33_1                 := false.B
//                 push_34_1                 := false.B
 
//                 push_35_1                 := false.B
//                 push_36_1                 := false.B
//                 push_37_1                 := false.B
//                 push_38_1                 := false.B
//                 push_39_1                 := false.B
//                 push_40_1                 := false.B
//                 push_41_1                 := false.B
//                 push_42_1                 := false.B
//                 push_43_1                 := false.B
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
//                 push_25_1                 := false.B
//                 push_26_1                 := false.B
//                 push_27_1                 := false.B
//                 push_28_1                 := false.B
//                 push_29_1                 := false.B
//                 push_30_1                 := false.B
//                 push_31_1                 := false.B
//                 push_32_1                 := false.B
//                 push_33_1                 := false.B
//                 push_34_1                 := false.B
//                 push_35_1                 := false.B
//                 push_36_1                 := false.B
//                 push_37_1                 := false.B
//                 push_38_1                 := false.B
//                 push_39_1                 := false.B
//                 push_40_1                 := false.B
//                 push_41_1                 := false.B
//                 push_42_1                 := false.B
//                 push_43_1                 := false.B

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
//                 push_25_1                 := false.B
//                 push_26_1                 := false.B
//                 push_27_1                 := false.B
//                 push_28_1                 := false.B
//                 push_29_1                 := false.B
//                 push_30_1                 := false.B
//                 push_31_1                 := false.B
//                 push_32_1                 := false.B
//                 push_33_1                 := false.B
//                 push_34_1                 := false.B
//                 push_34_1                 := false.B
//                 push_35_1                 := false.B
//                 push_36_1                 := false.B
//                 push_37_1                 := false.B
//                 push_38_1                 := false.B
//                 push_39_1                 := false.B
//                 push_40_1                 := false.B
//                 push_41_1                 := false.B
//                 push_42_1                 := false.B
//                 push_43_1                 := false.B

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
//         io.push_25                                 := push_25_1
//         io.push_26                                 := push_26_1
//         io.push_27                                 := push_27_1
//         io.push_28                                 := push_28_1
//         io.push_29                                 := push_29_1
//         io.push_30                                 := push_30_1
//         io.push_31                                 := push_31_1
//         io.push_32                                 := push_32_1
//         io.push_33                                 := push_33_1
//         io.push_34                                 := push_34_1
//         io.push_35                                 := push_35_1
//         io.push_36                                 := push_36_1
//         io.push_37                                 := push_37_1
//         io.push_38                                 := push_38_1
//         io.push_39                                 := push_39_1
//         io.push_40                                 := push_40_1
//         io.push_41                                 := push_41_1
//         io.push_42                                 := push_42_1
//         io.push_43                                 := push_43_1
        
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
//         val read_stack25_pop = RegInit(0.U(32.W))
//         val read_stack26_pop = RegInit(0.U(32.W))
//         val read_stack27_pop = RegInit(0.U(32.W))
//         val read_stack28_pop = RegInit(0.U(32.W))
//         val read_stack29_pop = RegInit(0.U(32.W))
//         val read_stack30_pop = RegInit(0.U(32.W))
//         val read_stack31_pop = RegInit(0.U(32.W))
//         val read_stack32_pop = RegInit(0.U(32.W))
//         val read_stack33_pop = RegInit(0.U(32.W))
//         val read_stack34_pop = RegInit(0.U(32.W))
//         val read_stack35_pop = RegInit(0.U(32.W))
//         val read_stack36_pop = RegInit(0.U(32.W))
//         val read_stack37_pop = RegInit(0.U(32.W))
//         val read_stack38_pop = RegInit(0.U(32.W))
//         val read_stack39_pop = RegInit(0.U(32.W))
//         val read_stack40_pop = RegInit(0.U(32.W))
//         val read_stack41_pop = RegInit(0.U(32.W))
//         val read_stack42_pop = RegInit(0.U(32.W))
//         val read_stack43_pop = RegInit(0.U(32.W))
       
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
//         val pop_25_1                 = RegInit(0.U(1.W))
//         val pop_26_1                 = RegInit(0.U(1.W))
//         val pop_27_1                 = RegInit(0.U(1.W))
//         val pop_28_1                 = RegInit(0.U(1.W))
//         val pop_29_1                 = RegInit(0.U(1.W))
//         val pop_30_1                 = RegInit(0.U(1.W))
//         val pop_31_1                 = RegInit(0.U(1.W))
//         val pop_32_1                 = RegInit(0.U(1.W))
//         val pop_33_1                 = RegInit(0.U(1.W))
//         val pop_34_1                 = RegInit(0.U(1.W))
//         val pop_35_1                 = RegInit(0.U(1.W))
//         val pop_36_1                 = RegInit(0.U(1.W))
//         val pop_37_1                 = RegInit(0.U(1.W))
//         val pop_38_1                 = RegInit(0.U(1.W))
//         val pop_39_1                 = RegInit(0.U(1.W))
//         val pop_40_1                 = RegInit(0.U(1.W))
//         val pop_41_1                 = RegInit(0.U(1.W))
//         val pop_42_1                 = RegInit(0.U(1.W))
//         val pop_43_1                 = RegInit(0.U(1.W))
        
//         val pop_valid_2          = RegInit(0.U(1.W))
//         val pop_node_id_2   = RegInit(0.S(32.W))
//         val pop_ray_id_2       = RegInit(0.U(32.W))
//         val pop_hitT_2            = RegInit(0.U(32.W))

//         read_stack0_pop      := LUT_mem(0)
//         read_stack1_pop      := LUT_mem(1)
//         read_stack2_pop      := LUT_mem(2)
//         read_stack3_pop      := LUT_mem(3)
//         read_stack4_pop      := LUT_mem(4)
//         read_stack5_pop      := LUT_mem(5)
//         read_stack6_pop      := LUT_mem(6)
//         read_stack7_pop      := LUT_mem(7)
//         read_stack8_pop      := LUT_mem(8)
//         read_stack9_pop      := LUT_mem(9)

//         read_stack10_pop      := LUT_mem(10)
//         read_stack11_pop      := LUT_mem(11)
//         read_stack12_pop      := LUT_mem(12)
//         read_stack13_pop      := LUT_mem(13)
//         read_stack14_pop      := LUT_mem(14)
//         read_stack15_pop      := LUT_mem(15)
//         read_stack16_pop      := LUT_mem(16)
//         read_stack17_pop      := LUT_mem(17)
//         read_stack18_pop      := LUT_mem(18)
//         read_stack19_pop      := LUT_mem(19)

//         read_stack20_pop      := LUT_mem(20)
//         read_stack21_pop      := LUT_mem(21)
//         read_stack22_pop      := LUT_mem(22)
//         read_stack23_pop      := LUT_mem(23)
//         read_stack24_pop      := LUT_mem(24)
//         read_stack25_pop      := LUT_mem(25)
//         read_stack26_pop      := LUT_mem(26)
//         read_stack27_pop      := LUT_mem(27)
//         read_stack28_pop      := LUT_mem(28)
//         read_stack29_pop      := LUT_mem(29)

//         read_stack30_pop      := LUT_mem(30)
//         read_stack31_pop      := LUT_mem(31)
//         read_stack32_pop      := LUT_mem(32)
//         read_stack33_pop      := LUT_mem(33)
//         read_stack34_pop      := LUT_mem(34)
//         read_stack35_pop      := LUT_mem(35)
//         read_stack36_pop      := LUT_mem(36)
//         read_stack37_pop      := LUT_mem(37)
//         read_stack38_pop      := LUT_mem(38)
//         read_stack39_pop      := LUT_mem(39)

//         read_stack40_pop      := LUT_mem(40)
//         read_stack41_pop      := LUT_mem(41)
//         read_stack42_pop      := LUT_mem(42)
//         read_stack43_pop      := LUT_mem(43)
        
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
//             pop_valid_2            := 1.U
//             pop_node_id_2     := pop_node_id
//             pop_ray_id_2         := pop_ray_id
//             pop_hitT_2              := pop_hitT_1
//         }.elsewhen(read_stack1_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_1_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack2_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_2_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack3_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_3_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack4_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_4_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack5_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_5_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack6_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_6_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack7_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_7_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack8_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_8_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1

//             }.elsewhen(read_stack9_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_9_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack10_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_10_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack11_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_11_1                    := 1.U         
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack12_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_12_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack13_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_13_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack14_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_14_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack15_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_15_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack16_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_16_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack17_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_17_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack18_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_18_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack19_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_19_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack20_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_20_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack21_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_21_1                    := 1.U         
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack22_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_22_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack23_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_23_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack24_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_24_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack25_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_25_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack26_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_26_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack27_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_27_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack28_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_28_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack29_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_29_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack30_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_30_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack31_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_31_1                    := 1.U         
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack32_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_32_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack33_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_33_1                    := 1.U
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack34_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_34_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//             }.elsewhen(read_stack35_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_35_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack36_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_36_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack37_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_37_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack38_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_38_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack39_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_39_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack40_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_40_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack41_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_41_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack42_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_42_1                    := 1.U    
//                 pop_valid_2            := 1.U
//                 pop_node_id_2     := pop_node_id
//                 pop_ray_id_2         := pop_ray_id
//                 pop_hitT_2              := pop_hitT_1
//            }.elsewhen(read_stack43_pop === pop_ray_id && pop_valid  === 1.U){
//                 pop_43_1                    := 1.U    
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
//                 pop_25_1                    := false.B
//                 pop_26_1                    := false.B
//                 pop_27_1                    := false.B
//                 pop_28_1                    := false.B
//                 pop_29_1                    := false.B
//                 pop_30_1                    := false.B
//                 pop_31_1                    := false.B         
//                 pop_32_1                    := false.B
//                 pop_33_1                    := false.B
//                 pop_34_1                    := false.B
//                 pop_35_1                    := false.B
//                 pop_36_1                    := false.B
//                 pop_37_1                    := false.B
//                 pop_38_1                    := false.B
//                 pop_39_1                    := false.B
//                 pop_40_1                    := false.B
//                 pop_41_1                    := false.B         
//                 pop_42_1                    := false.B
//                 pop_43_1                    := false.B
                
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
//             pop_25_1                    := false.B
//             pop_26_1                    := false.B
//             pop_27_1                    := false.B
//             pop_28_1                    := false.B
//             pop_29_1                    := false.B
//             pop_30_1                    := false.B
//             pop_31_1                    := false.B         
//             pop_32_1                    := false.B
//             pop_33_1                    := false.B
//             pop_34_1                    := false.B

//             pop_35_1                    := false.B
//             pop_36_1                    := false.B
//             pop_37_1                    := false.B
//             pop_38_1                    := false.B
//             pop_39_1                    := false.B
//             pop_40_1                    := false.B
//             pop_41_1                    := false.B         
//             pop_42_1                    := false.B
//             pop_43_1                    := false.B
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
            
//             io.pop_25                                    := pop_25_1
//             io.pop_26                                    := pop_26_1
//             io.pop_27                                    := pop_27_1
//             io.pop_28                                    := pop_28_1
//             io.pop_29                                    := pop_29_1

//             io.pop_30                                    := pop_30_1
//             io.pop_31                                    := pop_31_1
//             io.pop_32                                    := pop_32_1
//             io.pop_33                                    := pop_33_1
//             io.pop_34                                    := pop_34_1
//             io.pop_35                                    := pop_35_1
//             io.pop_36                                    := pop_36_1
//             io.pop_37                                    := pop_37_1
//             io.pop_38                                    := pop_38_1
//             io.pop_39                                    := pop_39_1

//             io.pop_40                                    := pop_40_1
//             io.pop_41                                    := pop_41_1
//             io.pop_42                                    := pop_42_1
//             io.pop_43                                    := pop_43_1
            


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

