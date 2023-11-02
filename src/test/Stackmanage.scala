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

    LUT_stack.io.empty_0    := Stack_0.io.empty
    LUT_stack.io.empty_1    := Stack_1.io.empty
    LUT_stack.io.empty_2    := Stack_2.io.empty
    LUT_stack.io.empty_3    := Stack_3.io.empty
    LUT_stack.io.empty_4    := Stack_4.io.empty
    LUT_stack.io.empty_5    := Stack_5.io.empty
    LUT_stack.io.empty_6    := Stack_6.io.empty
    LUT_stack.io.empty_7    := Stack_7.io.empty

  
    Stack_0.io.pop                   := LUT_stack.io.pop_0
    Stack_1.io.pop                   := LUT_stack.io.pop_1
    Stack_2.io.pop                   := LUT_stack.io.pop_2
    Stack_3.io.pop                   := LUT_stack.io.pop_3
    Stack_4.io.pop                   := LUT_stack.io.pop_4
    Stack_5.io.pop                   := LUT_stack.io.pop_5
    Stack_6.io.pop                   := LUT_stack.io.pop_6
    Stack_7.io.pop                   := LUT_stack.io.pop_7
    
    Stack_0.io.push                 := LUT_stack.io.push_0
    Stack_1.io.push                 := LUT_stack.io.push_1
    Stack_2.io.push                 := LUT_stack.io.push_2
    Stack_3.io.push                 := LUT_stack.io.push_3
    Stack_4.io.push                 := LUT_stack.io.push_4
    Stack_5.io.push                 := LUT_stack.io.push_5
    Stack_6.io.push                 := LUT_stack.io.push_6
    Stack_7.io.push                 := LUT_stack.io.push_7




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
    val push_from_lut       = RegInit(0.U(1.W))           
    push_0                             :=  LUT_stack.io.push_0
    push_1                             :=  LUT_stack.io.push_1
    push_2                             :=  LUT_stack.io.push_2
    push_3                             :=  LUT_stack.io.push_3
    push_4                             :=  LUT_stack.io.push_4
    push_5                             :=  LUT_stack.io.push_5
    push_6                             :=  LUT_stack.io.push_6
    push_7                             :=  LUT_stack.io.push_7
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
    }.elsewhen(LUT_stack.io.push_1===1.U&&LUT_stack.io.push_en===1.U){
        Stack_1.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_2===1.U&&LUT_stack.io.push_en===1.U){
        Stack_2.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_3===1.U&&LUT_stack.io.push_en===1.U){
        Stack_3.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_4===1.U&&LUT_stack.io.push_en===1.U){
        Stack_4.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_5===1.U&&LUT_stack.io.push_en===1.U){
        Stack_5.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_6===1.U&&LUT_stack.io.push_en===1.U){
        Stack_6.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
    }.elsewhen(LUT_stack.io.push_7===1.U&&LUT_stack.io.push_en===1.U){
        Stack_7.io.dataIn   := node_push_in_2
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
    }.otherwise{
        Stack_0.io.dataIn    := 0.S
        Stack_1.io.dataIn    := 0.S
        Stack_2.io.dataIn    := 0.S
        Stack_3.io.dataIn    := 0.S
        Stack_4.io.dataIn    := 0.S
        Stack_5.io.dataIn    := 0.S
        Stack_6.io.dataIn    := 0.S
        Stack_7.io.dataIn    := 0.S
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

    val empty_0                 = RegInit(0.U(1.W)) 
    val empty_1                 = RegInit(0.U(1.W)) 
    val empty_2                 = RegInit(0.U(1.W)) 
    val empty_3                 = RegInit(0.U(1.W)) 

    val empty_4                 = RegInit(0.U(1.W)) 
    val empty_5                 = RegInit(0.U(1.W)) 
    val empty_6                 = RegInit(0.U(1.W)) 
    val empty_7                 = RegInit(0.U(1.W)) 

    empty_0                       := Stack_0.io.empty
    empty_1                       := Stack_1.io.empty
    empty_2                       := Stack_2.io.empty
    empty_3                       := Stack_3.io.empty
    empty_4                       := Stack_4.io.empty
    empty_5                       := Stack_5.io.empty
    empty_6                       := Stack_6.io.empty
    empty_7                       := Stack_7.io.empty



    when( pop_0===1.U&&empty_0===1.U){
        dispatch_0              := 1.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U

    }.elsewhen(pop_1===1.U&&empty_1===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 1.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U

    }.elsewhen(pop_2===1.U&&empty_2===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 1.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
    
    }.elsewhen(pop_3===1.U&&empty_3===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 1.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U

    }.elsewhen(pop_4===1.U&&empty_4===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 1.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U

    }.elsewhen(pop_5===1.U&&empty_5===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 1.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U

    }.elsewhen(pop_6===1.U&&empty_6===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 1.U
        dispatch_7              := 0.U

    }.elsewhen(pop_7===1.U&&empty_7===1.U){
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 1.U

    }.otherwise{
        dispatch_0              := 0.U
        dispatch_1              := 0.U
        dispatch_2              := 0.U
        dispatch_3              := 0.U

        dispatch_4              := 0.U
        dispatch_5              := 0.U
        dispatch_6              := 0.U
        dispatch_7              := 0.U
    }
    io.Dis_en                               := dispatch_0===1.U || dispatch_1===1.U || dispatch_2===1.U || dispatch_3===1.U || dispatch_4 ===1.U|| dispatch_5 ===1.U|| dispatch_6===1.U || dispatch_7===1.U 
    io.Finish                                 := Stack_0.io.empty&&Stack_1.io.empty&&Stack_2.io.empty&&Stack_3.io.empty&&Stack_4.io.empty&&Stack_5.io.empty&&Stack_6.io.empty&&Stack_7.io.empty



}

// object Main extends App {
//   chisel3.Driver.execute(args, () => new Stackmanage())
// }


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