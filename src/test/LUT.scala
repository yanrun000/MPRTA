package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class LUT extends Module{
    val io = IO (new Bundle{
        val push           = Input(Bool())
        val push_valid= Input(Bool())
        val pop             = Input(Bool())
        val pop_valid = Input(Bool())
        val empty_0   = Input(Bool())
        val empty_1   = Input(Bool())
        val empty_2   = Input(Bool())
        val empty_3   = Input(Bool())
        val empty_4   = Input(Bool())
        val empty_5   = Input(Bool())
        val empty_6   = Input(Bool())
        val empty_7   = Input(Bool())
    
        val ray_id_push                  = Input(UInt(32.W))//为了保证弹栈和入栈同时进行需要设置两个光线的收入口，这个是入栈的光线id口
        val ray_id_pop                    = Input(UInt(32.W))//这个是光线弹栈口
        val node_id_push_in       = Input(SInt(32.W))//这个是入栈的节点id
        val node_id_pop_in         = Input(SInt(32.W))//这个是弹栈的节点id,好像没什么意义
        val hitT_in                             = Input(UInt(32.W))//这个是在入栈时候的传递值

        
        val ray_id_pop_out          = Output(UInt(32.W))//这个是光线的id在LUT里的传递，只有弹栈的时候需要传递
        val node_id_pop_out     = Output(SInt(32.W))//这个是把栈里的数据弹出来的pop的传递值
        val node_id_push_out   = Output(SInt(32.W))//这个是push的节点的值需要用来传给栈
        val hitT_out                         = Output(UInt(32.W))//这个是用来在单元中来传递Hit的值，只有弹栈的时候才需要

        val pop_0        =  Output(Bool())
        val pop_1        =  Output(Bool())
        val pop_2        =  Output(Bool())
        val pop_3        =  Output(Bool())
        val pop_4        =  Output(Bool())
        val pop_5        =  Output(Bool())
        val pop_6        =  Output(Bool())
        val pop_7        =  Output(Bool())
        val pop_en     =  Output(Bool())

        val push_0      =  Output(Bool())
        val push_1      =  Output(Bool())
        val push_2      =  Output(Bool())
        val push_3      =  Output(Bool())
        val push_4      =  Output(Bool())
        val push_5      =  Output(Bool())
        val push_6      =  Output(Bool())
        val push_7      =  Output(Bool())
        val push_en   =  Output(Bool())
    })

    val LUT_mem     = Mem(8,UInt(32.W))
    val read_stack0 = RegInit(0.U(32.W))//这个是用来和表里的数据进行读取用的
    val read_stack1 = RegInit(0.U(32.W))
    val read_stack2 = RegInit(0.U(32.W))
    val read_stack3 = RegInit(0.U(32.W))
    val read_stack4 = RegInit(0.U(32.W))
    val read_stack5 = RegInit(0.U(32.W))
    val read_stack6 = RegInit(0.U(32.W))
    val read_stack7 = RegInit(0.U(32.W))
    // val ray_id_1        = RegInit(0.S(32.W))

    val push_0_1     = RegInit(0.U(1.W))
    val push_1_1     = RegInit(0.U(1.W))
    val push_2_1     = RegInit(0.U(1.W))
    val push_3_1     = RegInit(0.U(1.W))
    val push_4_1     = RegInit(0.U(1.W))
    val push_5_1     = RegInit(0.U(1.W))
    val push_6_1     = RegInit(0.U(1.W))
    val push_7_1     = RegInit(0.U(1.W))
    //这里还是需要一个短暂的状态来寄存输出的数据,所以,还是有必要的
    //push的话第一个stage (1),感觉栈空不空也需要一个寄存器保存一下状态
    val push_1                      = RegInit(0.U(1.W))
    val push_valid              = RegInit(0.U(1.W))//要把有效位传过去
    val push_node_id       = RegInit(0.S(32.W)) 
    val push_ray_id           = RegInit(0.U(32.W))
    val empty_0_1             = RegInit(0.U(1.W))
    val empty_1_1             = RegInit(0.U(1.W))
    val empty_2_1             = RegInit(0.U(1.W))
    val empty_3_1             = RegInit(0.U(1.W))
    val empty_4_1             = RegInit(0.U(1.W))
    val empty_5_1             = RegInit(0.U(1.W))
    val empty_6_1             = RegInit(0.U(1.W))
    val empty_7_1             = RegInit(0.U(1.W))
    

    //push 的话第二个stage(2)
    val push_ray_id_2        = RegInit(0.U(32.W))
    val push_node_id_2    = RegInit(0.S(32.W))
    val push_valid_2           = RegInit(0.U(1.W))
    
    val push_stack1_en   = RegInit(0.U(1.W))//这个的意义是已经取了数，判断和查找表里的内容是否匹配，如果匹配就压栈，如果不匹配，就去找空的
    val find_empty             = RegInit(0.U(1.W))//这个的意义是当前的栈没有找到匹配的，需要压到新的栈里


        empty_0_1        := io.empty_0
        empty_1_1        := io.empty_1
        empty_2_1        := io.empty_2
        empty_3_1        := io.empty_3
        empty_4_1        := io.empty_4
        empty_5_1        := io.empty_5
        empty_6_1        := io.empty_6
        empty_7_1        := io.empty_7

    // val push_from_io      = RegInit(0.U(1.W))
    // val push_v_from_io = RegInit(0.U(1.W))

        read_stack0      := LUT_mem(0)
        read_stack1      := LUT_mem(1)
        read_stack2      := LUT_mem(2)
        read_stack3      := LUT_mem(3)
        read_stack4      := LUT_mem(4)
        read_stack5      := LUT_mem(5)
        read_stack6      := LUT_mem(6)
        read_stack7      := LUT_mem(7)  

        val push_node_1     = RegInit(0.S(32.W))
        val push_ray_1         = RegInit(0.S(32.W))
        val push_en_1           = RegInit(0.U(1.W))
        val push1                     = RegInit(0.U(1.W))

        val push_node_2     = RegInit(0.S(32.W))
        val push_ray_2         = RegInit(0.S(32.W))
        val push_en_2          = RegInit(0.U(1.W))
        val push2                    = RegInit(0.U(1.W))

    when(io.push === true.B && io.push_valid){//这里还需要&&一个push请求的有效位来确定这次Push是不是真的

        push_1                := 1.U
        push_node_id   := io.node_id_push_in
        push_ray_id      := io.ray_id_push
        push_valid         := io.push_valid
    }.otherwise{
                push_valid := 0.U
                push_1        := 0.U
    }

        // push_node_2     := push_node_1
        // push_ray_2         := push_ray_1
        // push_en_2          := push_en_1
        // push2                    := push1

        // push_node_id    := push_node_2
        // push_ray_id        := push_ray_2
        // push_valid           := push_en_2
        // push_1                  := push2

    when(push_1 === 1.U&& push_valid === 1.U){
        when(LUT_mem(0) === push_ray_id &&push_valid === 1.U){
            push_0_1                 := 1.U
            push_1_1                 := 0.U
            push_2_1                 := 0.U
            push_3_1                 := 0.U
            push_4_1                 := 0.U
            push_5_1                 := 0.U
            push_6_1                 := 0.U
            push_7_1                 := 0.U
            push_valid_2         :=  1.U
            push_node_id_2  := push_node_id
        }.elsewhen(LUT_mem(1) === push_ray_id &&push_valid === 1.U){
            push_0_1                 := 0.U
            push_1_1                 := 1.U
            push_2_1                 := 0.U
            push_3_1                 := 0.U
            push_4_1                 := 0.U
            push_5_1                 := 0.U
            push_6_1                 := 0.U
            push_7_1                 := 0.U
            push_valid_2         := 1.U
            push_node_id_2  := push_node_id
        }.elsewhen(LUT_mem(2) === push_ray_id&&push_valid === 1.U){
            push_0_1                 := 0.U
            push_1_1                 := 0.U
            push_2_1                 := 1.U
            push_3_1                 := 0.U
            push_4_1                 := 0.U
            push_5_1                 := 0.U
            push_6_1                 := 0.U
            push_7_1                 := 0.U
            push_valid_2         := 1.U
            push_node_id_2  := push_node_id
        }.elsewhen(LUT_mem(3) === push_ray_id&&push_valid === 1.U){
            push_0_1                 := 0.U
            push_1_1                 := 0.U
            push_2_1                 := 0.U
            push_3_1                 := 1.U
            push_4_1                 := 0.U
            push_5_1                 := 0.U
            push_6_1                 := 0.U
            push_7_1                 := 0.U
            push_valid_2         := 1.U
            push_node_id_2  := push_node_id
        }.elsewhen(LUT_mem(4) === push_ray_id&&push_valid === 1.U){
            push_0_1                 := 0.U
            push_1_1                 := 0.U
            push_2_1                 := 0.U
            push_3_1                 := 0.U
            push_4_1                 := 1.U
            push_5_1                 := 0.U
            push_6_1                 := 0.U
            push_7_1                 := 0.U
            push_valid_2         := 1.U
            push_node_id_2 := push_node_id
        }.elsewhen(LUT_mem(5) === push_ray_id&&push_valid === 1.U){
            push_0_1                 := 0.U
            push_1_1                 := 0.U
            push_2_1                 := 0.U
            push_3_1                 := 0.U
            push_4_1                 := 0.U
            push_5_1                 := 1.U
            push_6_1                 := 0.U
            push_7_1                 := 0.U
            push_valid_2         := 1.U
            push_node_id_2  := push_node_id
        }.elsewhen(LUT_mem(6) === push_ray_id&&push_valid === 1.U){
            push_0_1                 := 0.U
            push_1_1                 := 0.U
            push_2_1                 := 0.U
            push_3_1                 := 0.U
            push_4_1                 := 0.U
            push_5_1                 := 0.U
            push_6_1                 := 1.U
            push_7_1                 := 0.U
            push_valid_2         := 1.U
           push_node_id_2  := push_node_id
        }.elsewhen(LUT_mem(7) === push_ray_id&&push_valid === 1.U){
            push_0_1                 := 0.U
            push_1_1                 := 0.U
            push_2_1                 := 0.U
            push_3_1                 := 0.U
            push_4_1                 := 0.U
            push_5_1                 := 0.U
            push_6_1                 := 0.U
            push_7_1                 := 1.U
            push_valid_2         := 1.U
            push_node_id_2:= push_node_id
        }.elsewhen(read_stack0 =/= push_ray_id && read_stack1 =/= push_ray_id && read_stack2 =/= push_ray_id && read_stack3 =/= push_ray_id && read_stack4 =/= push_ray_id && read_stack5 =/= push_ray_id && read_stack6 =/= push_ray_id && read_stack7 =/= push_ray_id&&(push_valid === 1.U)){
                when(io.empty_0 === 1.U&&push_valid === 1.U &&push_0_1 ===0.U){
                LUT_mem(0) := push_ray_id
                push_0_1                 := 1.U
                push_1_1                 := 0.U
                push_2_1                 := 0.U
                push_3_1                 := 0.U
                push_4_1                 := 0.U
                push_5_1                 := 0.U
                push_6_1                 := 0.U
                push_7_1                 := 0.U
                push_valid_2         := 1.U
                push_node_id_2      := push_node_id
            }.elsewhen(io.empty_1=== 1.U&&push_valid === 1.U&&push_1_1 ===0.U){
                LUT_mem(1) := push_ray_id
                push_0_1                 := 0.U
                push_1_1                 := 1.U
                push_2_1                 := 0.U
                push_3_1                 := 0.U
                push_4_1                 := 0.U
                push_5_1                 := 0.U
                push_6_1                 := 0.U
                push_7_1                 := 0.U
                push_valid_2         := 1.U
                push_node_id_2      := push_node_id
            }.elsewhen(io.empty_2=== 1.U&&push_valid === 1.U&&push_2_1 ===0.U){
                LUT_mem(2) := push_ray_id
                push_0_1                 := 0.U
                push_1_1                 := 0.U
                push_2_1                 := 1.U
                push_3_1                 := 0.U
                push_4_1                 := 0.U
                push_5_1                 := 0.U
                push_6_1                 := 0.U
                push_7_1                 := 0.U
                push_valid_2         := 1.U
                push_node_id_2      := push_node_id
            }.elsewhen(io.empty_3=== 1.U&&push_valid === 1.U&&push_3_1 ===0.U){
                LUT_mem(3) := push_ray_id
                push_0_1                 := 0.U
                push_1_1                 := 0.U
                push_2_1                 := 0.U
                push_3_1                 := 1.U
                push_4_1                 := 0.U
                push_5_1                 := 0.U
                push_6_1                 := 0.U
                push_7_1                 := 0.U
                push_valid_2         := 1.U
                push_node_id_2      := push_node_id
            }.elsewhen(io.empty_4=== 1.U&&push_valid === 1.U&&push_4_1 ===0.U){
                LUT_mem(4) := push_ray_id
                push_0_1                 := 0.U
                push_1_1                 := 0.U
                push_2_1                 := 0.U
                push_3_1                 := 0.U
                push_4_1                 := 1.U
                push_5_1                 := 0.U
                push_6_1                 := 0.U
                push_7_1                 := 0.U
                push_valid_2         := 1.U
                push_node_id_2     := push_node_id
            }.elsewhen(io.empty_5=== 1.U&&push_valid === 1.U&&push_5_1 ===0.U){
                LUT_mem(5) := push_ray_id
                push_0_1                 := 0.U
                push_1_1                 := 0.U
                push_2_1                 := 0.U
                push_3_1                 := 0.U
                push_4_1                 := 0.U
                push_5_1                 := 1.U
                push_6_1                 := 0.U
                push_7_1                 := 0.U
                push_valid_2         := 1.U
                push_node_id_2     := push_node_id
            }.elsewhen(io.empty_6=== 1.U&&push_valid === 1.U&&push_6_1 ===0.U){
                LUT_mem(6) := push_ray_id
                push_0_1                 := 0.U
                push_1_1                 := 0.U
                push_2_1                 := 0.U
                push_3_1                 := 0.U
                push_4_1                 := 0.U
                push_5_1                 := 0.U
                push_6_1                 := 1.U
                push_7_1                 := 0.U
                push_valid_2         := 1.U
                push_node_id_2     := push_node_id
            }.elsewhen(io.empty_7=== 1.U&&push_valid === 1.U&&push_7_1 ===0.U){
                LUT_mem(7) := push_ray_id
                push_0_1                 := 0.U
                push_1_1                 := 0.U
                push_2_1                 := 0.U
                push_3_1                 := 0.U
                push_4_1                 := 0.U
                push_5_1                 := 0.U
                push_6_1                 := 0.U
                push_7_1                 := 1.U
                push_valid_2         := 1.U
                push_node_id_2     := push_node_id
            }.otherwise{
                push_0_1       := false.B
                push_1_1       := false.B
                push_2_1       := false.B
                push_3_1       := false.B
                push_4_1       := false.B
                push_5_1       := false.B
                push_6_1       := false.B
                push_7_1       := false.B
                push_valid_2 := 0.U
            }
        }.otherwise{
                push_0_1       := false.B
                push_1_1       := false.B
                push_2_1       := false.B
                push_3_1       := false.B
                push_4_1       := false.B
                push_5_1       := false.B
                push_6_1       := false.B
                push_7_1       := false.B
                push_valid_2 := 0.U
        }
    }.otherwise{
                push_0_1       := false.B
                push_1_1       := false.B
                push_2_1       := false.B
                push_3_1       := false.B
                push_4_1       := false.B
                push_5_1       := false.B
                push_6_1       := false.B
                push_7_1       := false.B
                push_valid_2 := 0.U
    } 
        // val push_0_2                        = RegInit(0.U(1.W))
        // val push_1_2                        = RegInit(0.U(1.W))
        // val push_2_2                        = RegInit(0.U(1.W))
        // val push_3_2                        = RegInit(0.U(1.W))
        // val push_4_2                        = RegInit(0.U(1.W))
        // val push_5_2                        = RegInit(0.U(1.W))
        // val push_6_2                        = RegInit(0.U(1.W))
        // val push_7_2                        = RegInit(0.U(1.W))
        // val push_valid_3                = RegInit(0.U(1.W))          
        // val push_node_id_3         = RegInit(0.S(32.W))

        // push_0_2                                := push_0_1
        // push_1_2                                := push_1_1
        // push_2_2                                := push_2_1
        // push_3_2                                := push_3_1
        // push_4_2                                := push_4_1
        // push_5_2                                := push_5_1
        // push_6_2                                := push_6_1
        // push_7_2                                := push_7_1
        // push_valid_3                         := push_valid_2
        // push_node_id_3                  := push_node_id_2



        io.push_0                                 := push_0_1
        io.push_1                                 := push_1_1
        io.push_2                                 := push_2_1
        io.push_3                                 := push_3_1
        io.push_4                                 := push_4_1
        io.push_5                                 := push_5_1
        io.push_6                                 := push_6_1
        io.push_7                                 := push_7_1
        io.node_id_push_out         := push_node_id_2
        io.push_en                               := push_valid_2 
        //pop stage(1)
        val pop_1                       = RegInit(0.U(1.W))
        val read_stack0_pop = RegInit(0.U(32.W))//这个是用来和表里的数据进行读取用的，这个是要在栈里的读取信号
        val read_stack1_pop = RegInit(0.U(32.W))
        val read_stack2_pop = RegInit(0.U(32.W))
        val read_stack3_pop = RegInit(0.U(32.W))
        val read_stack4_pop = RegInit(0.U(32.W))
        val read_stack5_pop = RegInit(0.U(32.W))
        val read_stack6_pop = RegInit(0.U(32.W))
        val read_stack7_pop = RegInit(0.U(32.W))
        
        val pop_node_id = RegInit(0.S(32.W))
        val pop_ray_id     = RegInit(0.U(32.W))
        val pop_hitT_1     = RegInit(0.U(32.W))
        val pop_valid     = RegInit(0.U(1.W))

        //pop stage(2)
        val pop_0_1                 = RegInit(0.U(1.W))
        val pop_1_1                 = RegInit(0.U(1.W))
        val pop_2_1                 = RegInit(0.U(1.W))
        val pop_3_1                 = RegInit(0.U(1.W))
        val pop_4_1                 = RegInit(0.U(1.W))
        val pop_5_1                 = RegInit(0.U(1.W))
        val pop_6_1                 = RegInit(0.U(1.W))
        val pop_7_1                 = RegInit(0.U(1.W))
        val pop_valid_2          = RegInit(0.U(1.W))
        val pop_node_id_2   = RegInit(0.S(32.W))
        val pop_ray_id_2       = RegInit(0.U(32.W))
        val pop_hitT_2            = RegInit(0.U(32.W))

        read_stack0_pop      := LUT_mem(0)
        read_stack1_pop      := LUT_mem(1)
        read_stack2_pop      := LUT_mem(2)
        read_stack3_pop      := LUT_mem(3)
        read_stack4_pop      := LUT_mem(4)
        read_stack5_pop      := LUT_mem(5)
        read_stack6_pop      := LUT_mem(6)
        read_stack7_pop      := LUT_mem(7)

    when(io.pop === true.B && io.pop_valid){//这里pop是来自整体的输入

        pop_1                  := 1.U
        pop_valid          := 1.U
        pop_node_id   := io.node_id_pop_in
        pop_ray_id       := io.ray_id_pop
        pop_hitT_1       := io.hitT_in
    }.otherwise{
        pop_valid          := 0.U
        pop_1                  :=0.U
    }
    
    when(pop_1 === 1.U && pop_valid  === 1.U){
        when(read_stack0_pop === pop_ray_id && pop_valid  === 1.U){
            pop_0_1                    := 1.U
            pop_1_1                    := 0.U
            pop_2_1                    := 0.U
            pop_3_1                    := 0.U
            pop_4_1                    := 0.U
            pop_5_1                    := 0.U
            pop_6_1                    := 0.U
            pop_7_1                    := 0.U
            pop_valid_2            := 1.U
            pop_node_id_2     := pop_node_id
            pop_ray_id_2         := pop_ray_id
            pop_hitT_2              := pop_hitT_1
            }.elsewhen(read_stack1_pop === pop_ray_id && pop_valid  === 1.U){
                pop_0_1                    := 0.U
                pop_1_1                    := 1.U
                pop_2_1                    := 0.U
                pop_3_1                    := 0.U
                pop_4_1                    := 0.U
                pop_5_1                    := 0.U
                pop_6_1                    := 0.U
                pop_7_1                    := 0.U
                pop_valid_2            := 1.U
                pop_node_id_2     := pop_node_id
                pop_ray_id_2         := pop_ray_id
                pop_hitT_2              := pop_hitT_1
            }.elsewhen(read_stack2_pop === pop_ray_id && pop_valid  === 1.U){
                pop_0_1                    := 0.U
                pop_1_1                    := 0.U
                pop_2_1                    := 1.U
                pop_3_1                    := 0.U
                pop_4_1                    := 0.U
                pop_5_1                    := 0.U
                pop_6_1                    := 0.U
                pop_7_1                    := 0.U
                pop_valid_2            := 1.U
                pop_node_id_2     := pop_node_id
                pop_ray_id_2         := pop_ray_id
                pop_hitT_2              := pop_hitT_1
            }.elsewhen(read_stack3_pop === pop_ray_id && pop_valid  === 1.U){
                pop_0_1                    := 0.U
                pop_1_1                    := 0.U
                pop_2_1                    := 0.U
                pop_3_1                    := 1.U
                pop_4_1                    := 0.U
                pop_5_1                    := 0.U
                pop_6_1                    := 0.U
                pop_7_1                    := 0.U
                pop_valid_2            := 1.U
                pop_node_id_2     := pop_node_id
                pop_ray_id_2         := pop_ray_id
                pop_hitT_2              := pop_hitT_1
            }.elsewhen(read_stack4_pop === pop_ray_id && pop_valid  === 1.U){
                pop_0_1                    := 0.U
                pop_1_1                    := 0.U
                pop_2_1                    := 0.U
                pop_3_1                    := 0.U
                pop_4_1                    := 1.U
                pop_5_1                    := 0.U
                pop_6_1                    := 0.U
                pop_7_1                    := 0.U
                pop_valid_2            := 1.U
                pop_node_id_2     := pop_node_id
                pop_ray_id_2         := pop_ray_id
                pop_hitT_2              := pop_hitT_1
            }.elsewhen(read_stack5_pop === pop_ray_id && pop_valid  === 1.U){
                pop_0_1                    := 0.U
                pop_1_1                    := 0.U
                pop_2_1                    := 0.U
                pop_3_1                    := 0.U
                pop_4_1                    := 0.U
                pop_5_1                    := 1.U
                pop_6_1                    := 0.U
                pop_7_1                    := 0.U
                pop_valid_2            := 1.U
                pop_node_id_2     := pop_node_id
                pop_ray_id_2         := pop_ray_id
                pop_hitT_2              := pop_hitT_1
            }.elsewhen(read_stack6_pop === pop_ray_id && pop_valid  === 1.U){
                pop_0_1                    := 0.U
                pop_1_1                    := 0.U
                pop_2_1                    := 0.U
                pop_3_1                    := 0.U
                pop_4_1                    := 0.U
                pop_5_1                    := 0.U
                pop_6_1                    := 1.U
                pop_7_1                    := 0.U
                pop_valid_2            := 1.U
                pop_node_id_2     := pop_node_id
                pop_ray_id_2         := pop_ray_id
                pop_hitT_2              := pop_hitT_1
            }.elsewhen(read_stack7_pop === pop_ray_id && pop_valid  === 1.U){
                pop_0_1                    := 0.U
                pop_1_1                    := 0.U
                pop_2_1                    := 0.U
                pop_3_1                    := 0.U
                pop_4_1                    := 0.U
                pop_5_1                    := 0.U
                pop_6_1                    := 0.U
                pop_7_1                    := 1.U
                pop_valid_2            := 1.U
                pop_node_id_2     := pop_node_id
                pop_ray_id_2         := pop_ray_id
                pop_hitT_2              := pop_hitT_1
            }.otherwise{
                pop_0_1                    := false.B
                pop_1_1                    := false.B
                pop_2_1                    := false.B
                pop_3_1                    := false.B
                pop_4_1                    := false.B
                pop_5_1                    := false.B
                pop_6_1                    := false.B
                pop_7_1                    := false.B
                pop_valid_2             := 0.U
        }
    }.otherwise{
            pop_0_1                    := false.B
            pop_1_1                    := false.B
            pop_2_1                    := false.B
            pop_3_1                    := false.B
            pop_4_1                    := false.B
            pop_5_1                    := false.B
            pop_6_1                    := false.B
            pop_7_1                    := false.B
            pop_valid_2             := 0.U
    }

            io.pop_0                                    := pop_0_1
            io.pop_1                                    := pop_1_1
            io.pop_2                                    := pop_2_1
            io.pop_3                                    := pop_3_1
            io.pop_4                                    := pop_4_1
            io.pop_5                                    := pop_5_1
            io.pop_6                                    := pop_6_1
            io.pop_7                                    := pop_7_1
            io.pop_en                                 := pop_valid_2
            io.ray_id_pop_out                := pop_ray_id_2
            io.hitT_out                                := pop_hitT_2
            io.node_id_pop_out           := pop_node_id_2
}   
        
// object Main extends App {
//   chisel3.Driver.execute(args, () => new LUT())
// }

class LUTeste (dut:LUT) extends PeekPokeTester(dut){
        poke(dut.io.push,1.U)
        poke(dut.io.push_valid,1.U)
        poke(dut.io.empty_0,1.U)
        poke(dut.io.empty_1,1.U)
        poke(dut.io.empty_2,1.U)
        poke(dut.io.empty_3,1.U)
        poke(dut.io.empty_4,1.U)
        poke(dut.io.empty_5,1.U)
        poke(dut.io.empty_6,1.U)
        poke(dut.io.empty_7,1.U)
        poke(dut.io.ray_id_push,1.U)
        poke(dut.io.node_id_push_in,2.U)

        step(1)
        poke(dut.io.push,1.U)
        poke(dut.io.push_valid,1.U)
        poke(dut.io.empty_0,0.U)
        poke(dut.io.empty_1,1.U)
        poke(dut.io.empty_2,1.U)
        poke(dut.io.empty_3,1.U)
        poke(dut.io.empty_4,1.U)
        poke(dut.io.empty_5,1.U)
        poke(dut.io.empty_6,1.U)
        poke(dut.io.empty_7,1.U)
        poke(dut.io.ray_id_push,2.U)
        poke(dut.io.node_id_push_in,3.U)
        
        step(1)
        poke(dut.io.push,1.U)
        poke(dut.io.push_valid,1.U)
        poke(dut.io.empty_0,0.U)
        poke(dut.io.empty_1,1.U)
        poke(dut.io.empty_2,1.U)
        poke(dut.io.empty_3,1.U)
        poke(dut.io.empty_4,1.U)
        poke(dut.io.empty_5,1.U)
        poke(dut.io.empty_6,1.U)
        poke(dut.io.empty_7,1.U)
        poke(dut.io.ray_id_push,3.U)
        poke(dut.io.node_id_push_in,4.U)
        step(1)
        poke(dut.io.push,1.U)
        poke(dut.io.push_valid,1.U)
        poke(dut.io.empty_0,0.U)
        poke(dut.io.empty_1,0.U)
        poke(dut.io.empty_2,1.U)
        poke(dut.io.empty_3,1.U)
        poke(dut.io.empty_4,1.U)
        poke(dut.io.empty_5,1.U)
        poke(dut.io.empty_6,1.U)
        poke(dut.io.empty_7,1.U)
        poke(dut.io.ray_id_push,4.U)
        poke(dut.io.node_id_push_in,5.U)

        step(1)
        poke(dut.io.push,0.U)//这里的意思是这个周期不压栈了
        poke(dut.io.push_valid,0.U)
        poke(dut.io.pop,1.U)
        poke(dut.io.pop_valid,1.U)
        poke(dut.io.ray_id_pop,1.U)
        poke(dut.io.node_id_pop_in,10.U)
        poke(dut.io.hitT_in,11.U)

        step(1)
        poke(dut.io.push,0.U)//这里的意思是这个周期不压栈了
        poke(dut.io.push_valid,0.U)
        poke(dut.io.pop,1.U)
        poke(dut.io.pop_valid,1.U)
        poke(dut.io.ray_id_pop,2.U)
        poke(dut.io.node_id_pop_in,12.U)
        poke(dut.io.hitT_in,13.U)
        step(1)
        poke(dut.io.pop,0.U)
        poke(dut.io.pop_valid,0.U)
        step(10)
        // poke(dut.io.wr,0.S)
        // poke(dut.io.rd,1.S)
        //  step(2)
        // poke(dut.io.rd,1.S)
        //  step(1)
        // poke(dut.io.rd,1.S)
        // step(70)
}
object LUTester extends App {
  chisel3.iotesters.Driver.execute(args, () => new LUT())(c => new LUTeste(c))
}

