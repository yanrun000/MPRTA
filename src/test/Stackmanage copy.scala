package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class Stackmanage extends Module{
    val io = IO (new Bundle{
        val push                               = Input(Bool())
        val push_en                       = Input(Bool())
        val pop                                 = Input(Bool())
        val pop_en                         = Input(Bool())

        val ray_id_push               = Input(UInt(32.W))//压入栈时的id
        val ray_id_pop                 = Input(UInt(32.W))//弹出栈时的id
        val node_id_push_in    = Input(SInt(32.W))//压栈时的节点
        val node_id_pop_in      = Input(SInt(32.W))//出栈时的节点
        val hitT_in                          = Input(UInt(32.W))//hitT传递值，仅弹栈的时候需要

        val dispatch_stop           = Input(Bool())

        val hitT_out                      = Output(UInt(32.W))
        val node_id_out             = Output(SInt(32.W))
        val ray_id_out                 = Output(UInt(32.W))
        val pop_valid                   = Output(Bool())
        // val Dispatch                      = Output(Bool())
        val Dis_en                          = Output(Bool())//先用八条光线试试，这个暂时不接
        val Finish                            = Output(Bool())   

    })

    val LUT_stack                   = Module(new LUT())
    val Stack_0                        = Module(new Stack(64))
    val Stack_1                        = Module(new Stack(64))
    val Stack_2                        = Module(new Stack(64))
    val Stack_3                        = Module(new Stack(64))
    val Stack_4                        = Module(new Stack(64))
    val Stack_5                        = Module(new Stack(64))
    val Stack_6                        = Module(new Stack(64))
    val Stack_7                        = Module(new Stack(64))
    val Stack_8                        = Module(new Stack(64))
    val Stack_9                        = Module(new Stack(64))
    val Stack_10                      = Module(new Stack(64))
    val Stack_11                      = Module(new Stack(64))
    val Stack_12                      = Module(new Stack(64))
    val Stack_13                      = Module(new Stack(64))
    val Stack_14                      = Module(new Stack(64))
    val Stack_15                      = Module(new Stack(64))
    val Stack_16                        = Module(new Stack(64))
    val Stack_17                        = Module(new Stack(64))
    val Stack_18                        = Module(new Stack(64))
    val Stack_19                        = Module(new Stack(64))
    val Stack_20                        = Module(new Stack(64))
    val Stack_21                        = Module(new Stack(64))
    val Stack_22                        = Module(new Stack(64))
    val Stack_23                       = Module(new Stack(64))
    val Stack_24                       = Module(new Stack(64))
    // val Stack_25                        = Module(new Stack(64))
    // val Stack_26                      = Module(new Stack(64))
    // val Stack_27                      = Module(new Stack(64))
    // val Stack_28                      = Module(new Stack(64))
    // val Stack_13                      = Module(new Stack(64))
    // val Stack_14                      = Module(new Stack(64))
    // val Stack_15                      = Module(new Stack(64))

    LUT_stack.io.empty_0    := Stack_0.io.empty
    LUT_stack.io.empty_1    := Stack_1.io.empty
    LUT_stack.io.empty_2    := Stack_2.io.empty
    LUT_stack.io.empty_3    := Stack_3.io.empty
    LUT_stack.io.empty_4    := Stack_4.io.empty
    LUT_stack.io.empty_5    := Stack_5.io.empty
    LUT_stack.io.empty_6    := Stack_6.io.empty
    LUT_stack.io.empty_7    := Stack_7.io.empty
    LUT_stack.io.empty_8    := Stack_8.io.empty
    LUT_stack.io.empty_9    := Stack_9.io.empty
    
    LUT_stack.io.empty_10    := Stack_10.io.empty
    LUT_stack.io.empty_11    := Stack_11.io.empty
    LUT_stack.io.empty_12    := Stack_12.io.empty
    LUT_stack.io.empty_13    := Stack_13.io.empty
    LUT_stack.io.empty_14    := Stack_14.io.empty
    LUT_stack.io.empty_15    := Stack_15.io.empty
    LUT_stack.io.empty_16    := Stack_16.io.empty
    LUT_stack.io.empty_17    := Stack_17.io.empty
    LUT_stack.io.empty_18    := Stack_18.io.empty
    LUT_stack.io.empty_19    := Stack_19.io.empty
    
    LUT_stack.io.empty_20    := Stack_20.io.empty
    LUT_stack.io.empty_21    := Stack_21.io.empty
    LUT_stack.io.empty_22    := Stack_22.io.empty
    LUT_stack.io.empty_23    := Stack_23.io.empty
    LUT_stack.io.empty_24    := Stack_24.io.empty
    

  
    Stack_0.io.pop                   := LUT_stack.io.pop_0
    Stack_1.io.pop                   := LUT_stack.io.pop_1
    Stack_2.io.pop                   := LUT_stack.io.pop_2
    Stack_3.io.pop                   := LUT_stack.io.pop_3
    Stack_4.io.pop                   := LUT_stack.io.pop_4
    Stack_5.io.pop                   := LUT_stack.io.pop_5
    Stack_6.io.pop                   := LUT_stack.io.pop_6
    Stack_7.io.pop                   := LUT_stack.io.pop_7
    Stack_8.io.pop                   := LUT_stack.io.pop_8
    Stack_9.io.pop                   := LUT_stack.io.pop_9

    Stack_10.io.pop                   := LUT_stack.io.pop_10
    Stack_11.io.pop                   := LUT_stack.io.pop_11
    Stack_12.io.pop                   := LUT_stack.io.pop_12
    Stack_13.io.pop                   := LUT_stack.io.pop_13
    Stack_14.io.pop                   := LUT_stack.io.pop_14
    Stack_15.io.pop                   := LUT_stack.io.pop_15
    Stack_16.io.pop                   := LUT_stack.io.pop_16
    Stack_17.io.pop                   := LUT_stack.io.pop_17
    Stack_18.io.pop                   := LUT_stack.io.pop_18
    Stack_19.io.pop                   := LUT_stack.io.pop_19
    
    Stack_20.io.pop                   := LUT_stack.io.pop_20
    Stack_21.io.pop                   := LUT_stack.io.pop_21
    Stack_22.io.pop                   := LUT_stack.io.pop_22
    Stack_23.io.pop                   := LUT_stack.io.pop_23
    Stack_24.io.pop                   := LUT_stack.io.pop_24
    

    Stack_0.io.push                 := LUT_stack.io.push_0
    Stack_1.io.push                 := LUT_stack.io.push_1
    Stack_2.io.push                 := LUT_stack.io.push_2
    Stack_3.io.push                 := LUT_stack.io.push_3
    Stack_4.io.push                 := LUT_stack.io.push_4
    Stack_5.io.push                 := LUT_stack.io.push_5
    Stack_6.io.push                 := LUT_stack.io.push_6
    Stack_7.io.push                 := LUT_stack.io.push_7
    Stack_8.io.push                 := LUT_stack.io.push_8
    Stack_9.io.push                 := LUT_stack.io.push_9

    Stack_10.io.push                 := LUT_stack.io.push_10
    Stack_11.io.push                 := LUT_stack.io.push_11
    Stack_12.io.push                 := LUT_stack.io.push_12
    Stack_13.io.push                 := LUT_stack.io.push_13
    Stack_14.io.push                 := LUT_stack.io.push_14
    Stack_15.io.push                 := LUT_stack.io.push_15
    Stack_16.io.push                 := LUT_stack.io.push_16
    Stack_17.io.push                 := LUT_stack.io.push_17
    Stack_18.io.push                 := LUT_stack.io.push_18
    Stack_19.io.push                 := LUT_stack.io.push_19

    Stack_20.io.push                 := LUT_stack.io.push_20
    Stack_21.io.push                 := LUT_stack.io.push_21
    Stack_22.io.push                 := LUT_stack.io.push_22
    Stack_23.io.push                 := LUT_stack.io.push_23
    Stack_24.io.push                 := LUT_stack.io.push_24




    LUT_stack.io.push                    := io.push
    LUT_stack.io.push_valid       := io.push_en
    LUT_stack.io.pop                     := io.pop
    LUT_stack.io.pop_valid         := io.pop_en
    LUT_stack.io.ray_id_push    := io.ray_id_push
    LUT_stack.io.ray_id_pop      := io.ray_id_pop
    LUT_stack.io.node_id_push_in    := io.node_id_push_in
    LUT_stack.io.node_id_pop_in      := io.node_id_pop_in
    LUT_stack.io.hitT_in                          := io.hitT_in

    val push_0                       = RegInit(0.U(1.W))
    val push_1                       = RegInit(0.U(1.W))
    val push_2                       = RegInit(0.U(1.W))
    val push_3                       = RegInit(0.U(1.W))
    val push_4                       = RegInit(0.U(1.W))
    val push_5                       = RegInit(0.U(1.W))
    val push_6                       = RegInit(0.U(1.W))
    val push_7                       = RegInit(0.U(1.W))
    val push_8                       = RegInit(0.U(1.W))
    val push_9                       = RegInit(0.U(1.W))

    val push_10                       = RegInit(0.U(1.W))
    val push_11                       = RegInit(0.U(1.W))
    val push_12                       = RegInit(0.U(1.W))
    val push_13                       = RegInit(0.U(1.W))
    val push_14                       = RegInit(0.U(1.W))
    val push_15                       = RegInit(0.U(1.W))
    val push_16                       = RegInit(0.U(1.W))
    val push_17                       = RegInit(0.U(1.W))
    val push_18                       = RegInit(0.U(1.W))
    val push_19                       = RegInit(0.U(1.W))

    val push_20                       = RegInit(0.U(1.W))
    val push_21                       = RegInit(0.U(1.W))
    val push_22                       = RegInit(0.U(1.W))
    val push_23                       = RegInit(0.U(1.W))
    val push_24                       = RegInit(0.U(1.W))

    val push_from_lut       = RegInit(0.U(1.W))           
    push_0                             :=  LUT_stack.io.push_0
    push_1                             :=  LUT_stack.io.push_1
    push_2                             :=  LUT_stack.io.push_2
    push_3                             :=  LUT_stack.io.push_3
    push_4                             :=  LUT_stack.io.push_4
    push_5                             :=  LUT_stack.io.push_5
    push_6                             :=  LUT_stack.io.push_6
    push_7                             :=  LUT_stack.io.push_7
    push_8                             :=  LUT_stack.io.push_8
    push_9                             :=  LUT_stack.io.push_9
   
    push_10                             :=  LUT_stack.io.push_10
    push_11                             :=  LUT_stack.io.push_11
    push_12                             :=  LUT_stack.io.push_12
    push_13                             :=  LUT_stack.io.push_13
    push_14                             :=  LUT_stack.io.push_14
    push_15                             :=  LUT_stack.io.push_15
    push_16                             :=  LUT_stack.io.push_16
    push_17                             :=  LUT_stack.io.push_17
    push_18                             :=  LUT_stack.io.push_18
    push_19                             :=  LUT_stack.io.push_19

    push_20                             :=  LUT_stack.io.push_20
    push_21                             :=  LUT_stack.io.push_21
    push_22                             :=  LUT_stack.io.push_22
    push_23                             :=  LUT_stack.io.push_23
    push_24                             :=  LUT_stack.io.push_24

    push_from_lut              := LUT_stack.io.push_en
    
    // Stack_0.io.push                     := Mux(push_0===1.U,true.B,false.B)
    // Stack_1.io.push                     := Mux(push_1===1.U,true.B,false.B)
    // Stack_2.io.push                     := Mux(push_2===1.U,true.B,false.B)
    // Stack_3.io.push                     := Mux(push_3===1.U,true.B,false.B)
    // Stack_4.io.push                     := Mux(push_4===1.U,true.B,false.B)
    // Stack_5.io.push                     := Mux(push_5===1.U,true.B,false.B)
    // Stack_6.io.push                     := Mux(push_6===1.U,true.B,false.B)
    // Stack_7.io.push                     := Mux(push_7===1.U,true.B,false.B)

    val push_0_node        = RegInit(0.S(32.W))
    val push_1_node        = RegInit(0.S(32.W))
    val push_2_node        = RegInit(0.S(32.W))
    val push_3_node        = RegInit(0.S(32.W))
    val push_4_node        = RegInit(0.S(32.W))
    val push_5_node        = RegInit(0.S(32.W))
    val push_6_node        = RegInit(0.S(32.W))
    val push_7_node        = RegInit(0.S(32.W))
    val push_8_node        = RegInit(0.S(32.W))
    val push_9_node        = RegInit(0.S(32.W))

    val push_10_node        = RegInit(0.S(32.W))
    val push_11_node        = RegInit(0.S(32.W))
    val push_12_node        = RegInit(0.S(32.W))
    val push_13_node        = RegInit(0.S(32.W))
    val push_14_node        = RegInit(0.S(32.W))
    val push_15_node        = RegInit(0.S(32.W))
    val push_16_node        = RegInit(0.S(32.W))
    val push_17_node        = RegInit(0.S(32.W))
    val push_18_node        = RegInit(0.S(32.W))
    val push_19_node        = RegInit(0.S(32.W))
    
    val push_20_node        = RegInit(0.S(32.W))
    val push_21_node        = RegInit(0.S(32.W))
    val push_22_node        = RegInit(0.S(32.W))
    val push_23_node        = RegInit(0.S(32.W))
    val push_24_node        = RegInit(0.S(32.W))
        
    //这里是时序对不上，需要暂存两拍的node_id_push_in
    val node_push_in_1  = RegInit(0.S(32.W))
    val node_push_in_2  = RegInit(0.S(32.W))
    val node_push_in_3  = RegInit(0.S(32.W))

    node_push_in_1          := LUT_stack.io.node_id_push_in
    node_push_in_2          := node_push_in_1
    node_push_in_3          := node_push_in_2

    when(LUT_stack.io.push_0===1.U&&LUT_stack.io.push_en===1.U){
        Stack_0.io.dataIn  := node_push_in_2    
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_1===1.U&&LUT_stack.io.push_en===1.U){
        Stack_1.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_2===1.U&&LUT_stack.io.push_en===1.U){
        Stack_2.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_3===1.U&&LUT_stack.io.push_en===1.U){
        Stack_3.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_4===1.U&&LUT_stack.io.push_en===1.U){
        Stack_4.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_5===1.U&&LUT_stack.io.push_en===1.U){
        Stack_5.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_6===1.U&&LUT_stack.io.push_en===1.U){
        Stack_6.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_7===1.U&&LUT_stack.io.push_en===1.U){
        Stack_7.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_8===1.U&&LUT_stack.io.push_en===1.U){
        Stack_8.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_9===1.U&&LUT_stack.io.push_en===1.U){
        Stack_9.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_10===1.U&&LUT_stack.io.push_en===1.U){
        Stack_10.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S    
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_11===1.U&&LUT_stack.io.push_en===1.U){
        Stack_11.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_12===1.U&&LUT_stack.io.push_en===1.U){
        Stack_12.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_13===1.U&&LUT_stack.io.push_en===1.U){
        Stack_13.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_14===1.U&&LUT_stack.io.push_en===1.U){
        Stack_14.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_15===1.U&&LUT_stack.io.push_en===1.U){
        Stack_15.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_16===1.U&&LUT_stack.io.push_en===1.U){
        Stack_16.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_17===1.U&&LUT_stack.io.push_en===1.U){
        Stack_17.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_18===1.U&&LUT_stack.io.push_en===1.U){
        Stack_18.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_19===1.U&&LUT_stack.io.push_en===1.U){
        Stack_19.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_20===1.U&&LUT_stack.io.push_en===1.U){
        Stack_20.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S    
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_21===1.U&&LUT_stack.io.push_en===1.U){
        Stack_21.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_22===1.U&&LUT_stack.io.push_en===1.U){
        Stack_22.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_23===1.U&&LUT_stack.io.push_en===1.U){
        Stack_23.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_24===1.U&&LUT_stack.io.push_en===1.U){
        Stack_24.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
    }.otherwise{
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
        Stack_8.io.dataIn    := 0.S
        Stack_9.io.dataIn    := 0.S
        Stack_10.io.dataIn    := 0.S    
        Stack_11.io.dataIn    := 0.S
        Stack_12.io.dataIn    := 0.S
        Stack_13.io.dataIn    := 0.S
        Stack_14.io.dataIn    := 0.S
        Stack_15.io.dataIn    := 0.S
        Stack_16.io.dataIn    := 0.S
        Stack_17.io.dataIn    := 0.S
        Stack_18.io.dataIn    := 0.S
        Stack_19.io.dataIn    := 0.S
        Stack_20.io.dataIn    := 0.S    
        Stack_21.io.dataIn    := 0.S
        Stack_22.io.dataIn    := 0.S
        Stack_23.io.dataIn    := 0.S
        Stack_24.io.dataIn    := 0.S
    }


    val hitT_out_temp   = RegInit(0.U(32.W))//hitT在栈中的传递值寄存
    val ray_out_temp     = RegInit(0.U(32.W))
    val node_out_temp = RegInit(0.S(32.W))
    val pop_valid_1         = RegInit(0.U(1.W))
    when(LUT_stack.io.pop_en===1.U&& LUT_stack.io.pop_0===1.U){
        Stack_0.io.hit_in  := LUT_stack.io.hitT_out
        Stack_0.io.ray_id  :=LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U
        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_1===1.U){
        Stack_1.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_1.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_2===1.U){
        Stack_2.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_2.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_3===1.U){
        Stack_3.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_3.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_4===1.U){
        Stack_4.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_4.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_5===1.U){
        Stack_5.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_5.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U        
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_6===1.U){
        Stack_6.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_6.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_7===1.U){
        Stack_7.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_7.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_8===1.U){
        Stack_8.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_8.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_9===1.U){
        Stack_9.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_9.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_10===1.U){
        Stack_10.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_10.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_11===1.U){
        Stack_11.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_11.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_12===1.U){
        Stack_12.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_12.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_13===1.U){
        Stack_13.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_13.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_14===1.U){
        Stack_14.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_14.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_15===1.U){
        Stack_15.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_15.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_16===1.U){
        Stack_16.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_16.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_17===1.U){
        Stack_17.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_17.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_18===1.U){
        Stack_18.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_18.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_19===1.U){
        Stack_19.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_19.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_20===1.U){
        Stack_20.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_20.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_21===1.U){
        Stack_21.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_21.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_22===1.U){
        Stack_22.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_22.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_23===1.U){
        Stack_23.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_23.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U        
    }.elsewhen(LUT_stack.io.pop_en===1.U&&LUT_stack.io.pop_24===1.U){
        Stack_24.io.hit_in  :=LUT_stack.io.hitT_out
        Stack_24.io.ray_id := LUT_stack.io.ray_id_pop_out
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
    }.otherwise{
        Stack_0.io.hit_in  := 0.U
        Stack_0.io.ray_id := 0.U
        Stack_1.io.hit_in  := 0.U
        Stack_1.io.ray_id := 0.U
        Stack_2.io.hit_in  := 0.U
        Stack_2.io.ray_id := 0.U
        Stack_3.io.hit_in  := 0.U
        Stack_3.io.ray_id := 0.U
        Stack_4.io.hit_in  := 0.U
        Stack_4.io.ray_id := 0.U
        Stack_5.io.hit_in  := 0.U
        Stack_5.io.ray_id := 0.U
        Stack_6.io.hit_in  := 0.U
        Stack_6.io.ray_id := 0.U
        Stack_7.io.hit_in  := 0.U
        Stack_7.io.ray_id := 0.U
        Stack_8.io.hit_in  := 0.U
        Stack_8.io.ray_id := 0.U
        Stack_9.io.hit_in  := 0.U
        Stack_9.io.ray_id := 0.U
        Stack_10.io.hit_in  := 0.U
        Stack_10.io.ray_id  :=0.U
        Stack_11.io.hit_in  := 0.U
        Stack_11.io.ray_id := 0.U
        Stack_12.io.hit_in  := 0.U
        Stack_12.io.ray_id := 0.U
        Stack_13.io.hit_in  := 0.U
        Stack_13.io.ray_id := 0.U
        Stack_14.io.hit_in  := 0.U
        Stack_14.io.ray_id := 0.U
        Stack_15.io.hit_in  := 0.U
        Stack_15.io.ray_id := 0.U
        Stack_16.io.hit_in  := 0.U
        Stack_16.io.ray_id := 0.U
        Stack_17.io.hit_in  := 0.U
        Stack_17.io.ray_id := 0.U
        Stack_18.io.hit_in  := 0.U
        Stack_18.io.ray_id := 0.U
        Stack_19.io.hit_in  := 0.U
        Stack_19.io.ray_id := 0.U
        Stack_20.io.hit_in  := 0.U
        Stack_20.io.ray_id  :=0.U
        Stack_21.io.hit_in  := 0.U
        Stack_21.io.ray_id := 0.U
        Stack_22.io.hit_in  := 0.U
        Stack_22.io.ray_id := 0.U
        Stack_23.io.hit_in  := 0.U
        Stack_23.io.ray_id := 0.U
        Stack_24.io.hit_in  := 0.U
        Stack_24.io.ray_id := 0.U
    }

    val pop_en_1               = RegInit(0.U(1.W))
    val pop_0                       = RegInit(0.U(1.W))
    val pop_1                       = RegInit(0.U(1.W))
    val pop_2                       = RegInit(0.U(1.W))
    val pop_3                       = RegInit(0.U(1.W))
    val pop_4                       = RegInit(0.U(1.W))
    val pop_5                       = RegInit(0.U(1.W))
    val pop_6                       = RegInit(0.U(1.W))
    val pop_7                       = RegInit(0.U(1.W))
    val pop_8                       = RegInit(0.U(1.W))
    val pop_9                       = RegInit(0.U(1.W))

    val pop_10                       = RegInit(0.U(1.W))
    val pop_11                       = RegInit(0.U(1.W))
    val pop_12                       = RegInit(0.U(1.W))
    val pop_13                       = RegInit(0.U(1.W))
    val pop_14                       = RegInit(0.U(1.W))
    val pop_15                       = RegInit(0.U(1.W))
    val pop_16                       = RegInit(0.U(1.W))
    val pop_17                       = RegInit(0.U(1.W))
    val pop_18                       = RegInit(0.U(1.W))
    val pop_19                       = RegInit(0.U(1.W))
    
    val pop_20                       = RegInit(0.U(1.W))
    val pop_21                       = RegInit(0.U(1.W))
    val pop_22                       = RegInit(0.U(1.W))
    val pop_23                       = RegInit(0.U(1.W))
    val pop_24                       = RegInit(0.U(1.W))
    
    // val pop_en_2               = RegInit(0.U(1.W))
    // val pop_0_2                  = RegInit(0.U(1.W))
    // val pop_1_2                  = RegInit(0.U(1.W))
    // val pop_2_2                  = RegInit(0.U(1.W))
    // val pop_3_2                  = RegInit(0.U(1.W))
    // val pop_4_2                  = RegInit(0.U(1.W))
    // val pop_5_2                  = RegInit(0.U(1.W))
    // val pop_6_2                  = RegInit(0.U(1.W))
    // val pop_7_2                  = RegInit(0.U(1.W))

    pop_en_1                      := LUT_stack.io.pop_en
    pop_0                              := LUT_stack.io.pop_0
    pop_1                              := LUT_stack.io.pop_1
    pop_2                              := LUT_stack.io.pop_2
    pop_3                              := LUT_stack.io.pop_3
    pop_4                              := LUT_stack.io.pop_4
    pop_5                              := LUT_stack.io.pop_5
    pop_6                              := LUT_stack.io.pop_6
    pop_7                              := LUT_stack.io.pop_7
    pop_8                              := LUT_stack.io.pop_8
    pop_9                              := LUT_stack.io.pop_9
    pop_10                              := LUT_stack.io.pop_10
    pop_11                              := LUT_stack.io.pop_11
    pop_12                              := LUT_stack.io.pop_12
    pop_13                              := LUT_stack.io.pop_13
    pop_14                              := LUT_stack.io.pop_14
    pop_15                              := LUT_stack.io.pop_15
    pop_16                              := LUT_stack.io.pop_16
    pop_17                              := LUT_stack.io.pop_17
    pop_18                              := LUT_stack.io.pop_18
    pop_19                              := LUT_stack.io.pop_19
    pop_20                              := LUT_stack.io.pop_20
    pop_21                              := LUT_stack.io.pop_21
    pop_22                              := LUT_stack.io.pop_22
    pop_23                              := LUT_stack.io.pop_23
    pop_24                              := LUT_stack.io.pop_24
    

    when( pop_0===1.U&&Stack_0.io.enable===1.U){
        
        hitT_out_temp     := Stack_0.io.hit_out
        ray_out_temp       := Stack_0.io.ray_out
        node_out_temp   := Stack_0.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_1===1.U&&Stack_1.io.enable===1.U){
        hitT_out_temp     := Stack_1.io.hit_out
        ray_out_temp       := Stack_1.io.ray_out
        node_out_temp   := Stack_1.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_2===1.U&&Stack_2.io.enable===1.U){
        hitT_out_temp     := Stack_2.io.hit_out
        ray_out_temp       := Stack_2.io.ray_out
        node_out_temp   := Stack_2.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_3===1.U&&Stack_3.io.enable===1.U){
        hitT_out_temp     := Stack_3.io.hit_out
        ray_out_temp       := Stack_3.io.ray_out
        node_out_temp   := Stack_3.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_4===1.U&&Stack_4.io.enable===1.U){
        hitT_out_temp     := Stack_4.io.hit_out
        ray_out_temp       := Stack_4.io.ray_out
        node_out_temp   := Stack_4.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_5===1.U&&Stack_5.io.enable===1.U){
        hitT_out_temp     := Stack_5.io.hit_out
        ray_out_temp       := Stack_5.io.ray_out
        node_out_temp   := Stack_5.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_6===1.U&&Stack_6.io.enable===1.U){
        hitT_out_temp     := Stack_6.io.hit_out
        ray_out_temp       := Stack_6.io.ray_out
        node_out_temp   := Stack_6.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_7===1.U&&Stack_7.io.enable===1.U){
        hitT_out_temp     := Stack_7.io.hit_out
        ray_out_temp       := Stack_7.io.ray_out
        node_out_temp   := Stack_7.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_8===1.U&&Stack_8.io.enable===1.U){
        hitT_out_temp     := Stack_8.io.hit_out
        ray_out_temp       := Stack_8.io.ray_out
        node_out_temp   := Stack_8.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_9===1.U&&Stack_9.io.enable===1.U){
        hitT_out_temp     := Stack_9.io.hit_out
        ray_out_temp       := Stack_9.io.ray_out
        node_out_temp   := Stack_9.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_10===1.U&&Stack_10.io.enable===1.U){
        hitT_out_temp     := Stack_10.io.hit_out
        ray_out_temp       := Stack_10.io.ray_out
        node_out_temp   := Stack_10.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_11===1.U&&Stack_11.io.enable===1.U){
        hitT_out_temp     := Stack_11.io.hit_out
        ray_out_temp       := Stack_11.io.ray_out
        node_out_temp   := Stack_11.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_12===1.U&&Stack_12.io.enable===1.U){
        hitT_out_temp     := Stack_12.io.hit_out
        ray_out_temp       := Stack_12.io.ray_out
        node_out_temp   := Stack_12.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_13===1.U&&Stack_13.io.enable===1.U){
        hitT_out_temp     := Stack_13.io.hit_out
        ray_out_temp       := Stack_13.io.ray_out
        node_out_temp   := Stack_13.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_14===1.U&&Stack_14.io.enable===1.U){
        hitT_out_temp     := Stack_14.io.hit_out
        ray_out_temp       := Stack_14.io.ray_out
        node_out_temp   := Stack_14.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_15===1.U&&Stack_15.io.enable===1.U){
        hitT_out_temp     := Stack_15.io.hit_out
        ray_out_temp       := Stack_15.io.ray_out
        node_out_temp   := Stack_15.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_16===1.U&&Stack_16.io.enable===1.U){
        hitT_out_temp     := Stack_16.io.hit_out
        ray_out_temp       := Stack_16.io.ray_out
        node_out_temp   := Stack_16.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_17===1.U&&Stack_17.io.enable===1.U){
        hitT_out_temp     := Stack_17.io.hit_out
        ray_out_temp       := Stack_17.io.ray_out
        node_out_temp   := Stack_17.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_18===1.U&&Stack_18.io.enable===1.U){
        hitT_out_temp     := Stack_18.io.hit_out
        ray_out_temp       := Stack_18.io.ray_out
        node_out_temp   := Stack_18.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_19===1.U&&Stack_19.io.enable===1.U){
        hitT_out_temp     := Stack_19.io.hit_out
        ray_out_temp       := Stack_19.io.ray_out
        node_out_temp   := Stack_19.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_20===1.U&&Stack_20.io.enable===1.U){
        hitT_out_temp     := Stack_20.io.hit_out
        ray_out_temp       := Stack_20.io.ray_out
        node_out_temp   := Stack_20.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_21===1.U&&Stack_21.io.enable===1.U){
        hitT_out_temp     := Stack_21.io.hit_out
        ray_out_temp       := Stack_21.io.ray_out
        node_out_temp   := Stack_21.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_22===1.U&&Stack_22.io.enable===1.U){
        hitT_out_temp     := Stack_22.io.hit_out
        ray_out_temp       := Stack_22.io.ray_out
        node_out_temp   := Stack_22.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_23===1.U&&Stack_23.io.enable===1.U){
        hitT_out_temp     := Stack_23.io.hit_out
        ray_out_temp       := Stack_23.io.ray_out
        node_out_temp   := Stack_23.io.dataOut
        pop_valid_1           := 1.U
    }.elsewhen(pop_24===1.U&&Stack_24.io.enable===1.U){
        hitT_out_temp     := Stack_24.io.hit_out
        ray_out_temp       := Stack_24.io.ray_out
        node_out_temp   := Stack_24.io.dataOut
        pop_valid_1           := 1.U
    }.otherwise{
        pop_valid_1           := 0.U
        hitT_out_temp     := 0.U
        ray_out_temp       := 0.U
        node_out_temp   := 0.S
    }
    
    io.hitT_out                 := hitT_out_temp
    io.node_id_out        := node_out_temp
    io.ray_id_out            := ray_out_temp
    io.pop_valid              := pop_valid_1

    val dispatch_0             = RegInit(0.U(1.W)) 
    val dispatch_1             = RegInit(0.U(1.W)) 
    val dispatch_2             = RegInit(0.U(1.W)) 
    val dispatch_3             = RegInit(0.U(1.W)) 
    val dispatch_4             = RegInit(0.U(1.W)) 
    val dispatch_5             = RegInit(0.U(1.W)) 
    val dispatch_6             = RegInit(0.U(1.W)) 
    val dispatch_7             = RegInit(0.U(1.W)) 
    val dispatch_8             = RegInit(0.U(1.W)) 
    val dispatch_9             = RegInit(0.U(1.W)) 

    val dispatch_10             = RegInit(0.U(1.W)) 
    val dispatch_11             = RegInit(0.U(1.W)) 
    val dispatch_12             = RegInit(0.U(1.W)) 
    val dispatch_13             = RegInit(0.U(1.W)) 
    val dispatch_14             = RegInit(0.U(1.W)) 
    val dispatch_15             = RegInit(0.U(1.W)) 
    val dispatch_16             = RegInit(0.U(1.W)) 
    val dispatch_17             = RegInit(0.U(1.W)) 
    val dispatch_18             = RegInit(0.U(1.W)) 
    val dispatch_19             = RegInit(0.U(1.W))


    val dispatch_20             = RegInit(0.U(1.W)) 
    val dispatch_21             = RegInit(0.U(1.W)) 
    val dispatch_22             = RegInit(0.U(1.W)) 
    val dispatch_23             = RegInit(0.U(1.W)) 
    val dispatch_24             = RegInit(0.U(1.W))

    val empty_0                 = RegInit(0.U(1.W)) 
    val empty_1                 = RegInit(0.U(1.W)) 
    val empty_2                 = RegInit(0.U(1.W)) 
    val empty_3                 = RegInit(0.U(1.W)) 
    val empty_4                 = RegInit(0.U(1.W)) 
    val empty_5                 = RegInit(0.U(1.W)) 
    val empty_6                 = RegInit(0.U(1.W)) 
    val empty_7                 = RegInit(0.U(1.W)) 
    val empty_8                 = RegInit(0.U(1.W)) 
    val empty_9                 = RegInit(0.U(1.W)) 

    val empty_10                 = RegInit(0.U(1.W)) 
    val empty_11                 = RegInit(0.U(1.W)) 
    val empty_12                 = RegInit(0.U(1.W)) 
    val empty_13                 = RegInit(0.U(1.W)) 
    val empty_14                 = RegInit(0.U(1.W)) 
    val empty_15                 = RegInit(0.U(1.W)) 
    val empty_16                 = RegInit(0.U(1.W)) 
    val empty_17                 = RegInit(0.U(1.W)) 
    val empty_18                 = RegInit(0.U(1.W)) 
    val empty_19                 = RegInit(0.U(1.W))

    val empty_20                 = RegInit(0.U(1.W)) 
    val empty_21                 = RegInit(0.U(1.W)) 
    val empty_22                 = RegInit(0.U(1.W)) 
    val empty_23                 = RegInit(0.U(1.W)) 
    val empty_24                 = RegInit(0.U(1.W)) 
    
    empty_0                       := Stack_0.io.empty
    empty_1                       := Stack_1.io.empty
    empty_2                       := Stack_2.io.empty
    empty_3                       := Stack_3.io.empty
    empty_4                       := Stack_4.io.empty
    empty_5                       := Stack_5.io.empty
    empty_6                       := Stack_6.io.empty
    empty_7                       := Stack_7.io.empty
    empty_8                       := Stack_8.io.empty
    empty_9                       := Stack_9.io.empty
  
    empty_10                       := Stack_10.io.empty
    empty_11                       := Stack_11.io.empty
    empty_12                       := Stack_12.io.empty
    empty_13                       := Stack_13.io.empty
    empty_14                       := Stack_14.io.empty
    empty_15                       := Stack_15.io.empty
    empty_16                       := Stack_16.io.empty
    empty_17                       := Stack_17.io.empty
    empty_18                       := Stack_18.io.empty
    empty_19                       := Stack_19.io.empty

    empty_20                       := Stack_20.io.empty
    empty_21                       := Stack_21.io.empty
    empty_22                       := Stack_22.io.empty
    empty_23                       := Stack_23.io.empty
    empty_24                       := Stack_24.io.empty


    when( pop_0===1.U&&empty_0===1.U){
        dispatch_0              := 1.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U
        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_1===1.U&&empty_1===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 1.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U
        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U

    }.elsewhen(pop_2===1.U&&empty_2===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 1.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    
    }.elsewhen(pop_3===1.U&&empty_3===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 1.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U

    }.elsewhen(pop_4===1.U&&empty_4===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 1.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U

    }.elsewhen(pop_5===1.U&&empty_5===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 1.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U

    }.elsewhen(pop_6===1.U&&empty_6===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 1.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U

    }.elsewhen(pop_7===1.U&&empty_7===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 1.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U

    }.elsewhen(pop_8===1.U&&empty_8===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 1.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_9===1.U&&empty_9===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 1.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U

    }.elsewhen(pop_10===1.U&&empty_10===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 1.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_11===1.U&&empty_11===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 1.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_12===1.U&&empty_12===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 1.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_13===1.U&&empty_13===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 1.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_14===1.U&&empty_14===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 1.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_15===1.U&&empty_15===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 1.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_16===1.U&&empty_16===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 1.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_17===1.U&&empty_17===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 1.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_18===1.U&&empty_18===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 1.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_19===1.U&&empty_19===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 1.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 1.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_20===1.U&&empty_20===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 1.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_21===1.U&&empty_21===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 1.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_22===1.U&&empty_22===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 1.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }.elsewhen(pop_23===1.U&&empty_23===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 1.U
        dispatch_24              := 0.U
    }.elsewhen(pop_24===1.U&&empty_24===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 1.U
    }.otherwise{
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
        dispatch_8              := 0.U
        dispatch_9              := 0.U
        dispatch_10              := 0.U
        dispatch_11              := 0.U
        dispatch_12              := 0.U
        dispatch_13              := 0.U
        dispatch_14              := 0.U
        dispatch_15              := 0.U
        dispatch_16              := 0.U
        dispatch_17              := 0.U
        dispatch_18              := 0.U
        dispatch_19              := 0.U
        dispatch_20              := 0.U
        dispatch_21              := 0.U
        dispatch_22              := 0.U
        dispatch_23              := 0.U
        dispatch_24              := 0.U
    }
    io.Dis_en                               := dispatch_0===1.U || dispatch_1===1.U || dispatch_2===1.U || dispatch_3===1.U || dispatch_4 ===1.U|| dispatch_5 ===1.U|| dispatch_6===1.U || dispatch_7===1.U || dispatch_8===1.U || dispatch_9===1.U ||dispatch_10===1.U || dispatch_11===1.U || dispatch_12===1.U || dispatch_13===1.U || dispatch_14 ===1.U|| dispatch_15 ===1.U|| dispatch_16===1.U || dispatch_17===1.U || dispatch_18===1.U || dispatch_19===1.U ||dispatch_20===1.U || dispatch_21===1.U || dispatch_22===1.U || dispatch_23===1.U || dispatch_24 ===1.U        
    io.Finish                                 := Stack_0.io.empty&&Stack_1.io.empty&&Stack_2.io.empty&&Stack_3.io.empty&&Stack_4.io.empty&&Stack_5.io.empty&&Stack_6.io.empty&&Stack_7.io.empty&&Stack_8.io.empty&&Stack_9.io.empty&&Stack_10.io.empty&&Stack_11.io.empty&&Stack_12.io.empty&&Stack_13.io.empty&&Stack_14.io.empty&&Stack_15.io.empty&&Stack_16.io.empty&&Stack_17.io.empty&&Stack_18.io.empty&&Stack_19.io.empty &&Stack_20.io.empty&&Stack_21.io.empty&&Stack_22.io.empty&&Stack_23.io.empty&&Stack_24.io.empty


}

object Stackmanage_rtl extends App {
  chisel3.Driver.execute(args, () => new Stackmanage())
}


class StackmanegeTest (dut:Stackmanage) extends PeekPokeTester(dut){

        poke(dut.io.push,1.U)
        poke(dut.io.push_en,1.U)
        poke(dut.io.pop,0.U)
        poke(dut.io.pop_en,0.U)
        poke(dut.io.ray_id_push,1.S)
        poke(dut.io.node_id_push_in,3.S)
        poke(dut.io.dispatch_stop,1.U)
        // poke(dut.io.node_id_push_in,2.U)

        step(1)
        poke(dut.io.push,1.U)
        poke(dut.io.push_en,1.U)
        poke(dut.io.pop,0.U)
        poke(dut.io.pop_en,0.U)
        poke(dut.io.ray_id_push,2.S)
        poke(dut.io.node_id_push_in,4.S)
        poke(dut.io.dispatch_stop,1.U)
                
        step(1)
        poke(dut.io.push,1.U)
        poke(dut.io.push_en,1.U)
        poke(dut.io.pop,0.U)
        poke(dut.io.pop_en,0.U)
        poke(dut.io.ray_id_push,1.S)
        poke(dut.io.node_id_push_in,5.S)
        poke(dut.io.dispatch_stop,1.U)

        step(1)
        poke(dut.io.push,0.U)
        poke(dut.io.push_en,0.U)
        poke(dut.io.pop,1.U)
        poke(dut.io.pop_en,1.U)
        poke(dut.io.ray_id_pop,2.S)
        poke(dut.io.node_id_pop_in,10.S)
        poke(dut.io.hitT_in,20.U)
        poke(dut.io.dispatch_stop,1.U)
        // step(1)       
        // poke(dut.io.push,0.U)
        // poke(dut.io.push_en,0.U)
        // poke(dut.io.pop,1.U)
        // poke(dut.io.pop_en,1.U)
        // poke(dut.io.ray_id_pop,2.S)
        // poke(dut.io.node_id_pop_in,11.S)
        // poke(dut.io.hitT_in,21.U)

        step(1)       
        poke(dut.io.push,0.U)
        poke(dut.io.push_en,0.U)
        poke(dut.io.pop,1.U)
        poke(dut.io.pop_en,1.U)
        poke(dut.io.ray_id_pop,1.S)
        poke(dut.io.node_id_pop_in,12.S)
        poke(dut.io.hitT_in,22.U)
        poke(dut.io.dispatch_stop,1.U)

        step(1)       
        poke(dut.io.push,0.U)
        poke(dut.io.push_en,0.U)
        poke(dut.io.pop,1.U)
        poke(dut.io.pop_en,1.U)
        poke(dut.io.ray_id_pop,1.S)
        poke(dut.io.node_id_pop_in,12.S)
        poke(dut.io.hitT_in,22.U)
        poke(dut.io.dispatch_stop,1.U)

        step(1)       
        poke(dut.io.push,0.U)
        poke(dut.io.push_en,0.U)
        poke(dut.io.pop,1.U)
        poke(dut.io.pop_en,1.U)
        poke(dut.io.ray_id_pop,1.S)
        poke(dut.io.node_id_pop_in,12.S)
        poke(dut.io.hitT_in,22.U)
        poke(dut.io.dispatch_stop,1.U)

        step(1)       
        poke(dut.io.push,0.U)
        poke(dut.io.push_en,0.U)
        poke(dut.io.pop,1.U)
        poke(dut.io.pop_en,1.U)
        poke(dut.io.ray_id_pop,2.S)
        poke(dut.io.node_id_pop_in,12.S)
        poke(dut.io.hitT_in,22.U)
        poke(dut.io.dispatch_stop,1.U)
       
        step(1)
        poke(dut.io.push,0.U)
        poke(dut.io.push_en,0.U)
        poke(dut.io.pop,0.U)
        poke(dut.io.pop_en,0.U)
        poke(dut.io.dispatch_stop,0.U)
        // poke(dut.io.push,1.U)
        // poke(dut.io.push_valid,1.U)
        // poke(dut.io.empty_0,0.U)
        // poke(dut.io.empty_1,0.U)
        // poke(dut.io.empty_2,1.U)
        // poke(dut.io.empty_3,1.U)
        // poke(dut.io.empty_4,1.U)
        // poke(dut.io.empty_5,1.U)
        // poke(dut.io.empty_6,1.U)
        // poke(dut.io.empty_7,1.U)
        // poke(dut.io.ray_id_push,2.U)
        // poke(dut.io.node_id_push_in,5.U)

        // step(3)  
        // poke(dut.io.push,0.U)//这里的意思是这个周期不压栈了
        // poke(dut.io.push_valid,0.U)
        // poke(dut.io.pop,1.U)
        // poke(dut.io.pop_valid,1.U)
        // poke(dut.io.ray_id_pop,1.U)
        // poke(dut.io.node_id_pop_in,10.U)
        // poke(dut.io.hitT_in,11.U)

        // step(1)
        // poke(dut.io.push,0.U)//这里的意思是这个周期不压栈了
        // poke(dut.io.push_valid,0.U)
        // poke(dut.io.pop,1.U)
        // poke(dut.io.pop_valid,1.U)
        // poke(dut.io.ray_id_pop,2.U)
        // poke(dut.io.node_id_pop_in,12.U)
        // poke(dut.io.hitT_in,13.U)
        // step(1)
        // poke(dut.io.pop,0.U)
        // poke(dut.io.pop_valid,0.U)
        step(10)
        // poke(dut.io.wr,0.S)
        // poke(dut.io.rd,1.S)
        //  step(2)
        // poke(dut.io.rd,1.S)
        //  step(1)
        // poke(dut.io.rd,1.S)
        // step(70)
}
object Stackmanagetester extends App {
  chisel3.iotesters.Driver.execute(args, () => new Stackmanage())(c => new StackmanegeTest(c))
}