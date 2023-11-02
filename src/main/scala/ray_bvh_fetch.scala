package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._


class ray_bvh_fetch extends Module{
    val io = IO(new Bundle{
        val ray_bvh_0         = Input(SInt(96.W))//这个是弹栈后的节点，优先级最高
        // val bvh_id_0        = Input(SInt(32.W))
        // val hit_in_0          = Input(SInt(32.W))//这个值是弹栈之后出来的

        // val ray_id_1         = Input(SInt(32.W))//这个是未进栈的内部节点，优先级第二
        // val bvh_id_1        = Input(SInt(32.W))
        // val hit_in_1          = Input(SInt(32.W))//这个数是从aabb之后返回的
        val ray_bvh_1         = Input(SInt(96.W))

        // val ray_id_2         = Input(SInt(32.W))//这个是刚发射的光线
        // val bvh_id_2        = Input(SInt(32.W))

        val ray_bvh_2     = Input(SInt(64.W))

        val fetch_hit         = Output(Bool())
        // val ray_id_out     = Output(SInt(32.W))
        // val bvh_id_out    = Output(SInt(32.W))
        // val hit_out             = Output(SInt(32.W))
        val ray_bvh_out_0   = Output(SInt(96.W))
        val ray_bvh_out_1   = Output(SInt(96.W))
        val ray_bvh_out_2   = Output(SInt(64.W))
    })

    val FIFO_0                  = Module(new FIFO(96,8))
    val FIFO_1                  = Module(new FIFO(96,8))
    val FIFO_2                  = Module(new FIFO(64,8)) 

    val fifo_0_empty     = RegInit(0.U(1.W))
    val fifo_1_empty     = RegInit(0.U(1.W))
    val fifo_2_empty     = RegInit(0.U(1.W))
    
    fifo_0_empty            := FIFO_0.io.empty
    fifo_1_empty            := FIFO_1.io.empty
    fifo_2_empty            := FIFO_2.io.empty

    FIFO_0.io.wr                              := true.B//这里我觉得不太对
    FIFO_0.io.datain                      := io.ray_bvh_0

    FIFO_1.io.wr                              := true.B//这里我觉得不太对
    FIFO_1.io.datain                      := io.ray_bvh_1 


    FIFO_2.io.wr                              := true.B//这里我觉得不太对
    FIFO_2.io.datain                      := io.ray_bvh_2 

    when(fifo_0_empty === 0.U){
        FIFO_0.io.rd                                := true.B
        FIFO_1.io.rd                                := false.B
        FIFO_2.io.rd                                := false.B
    }.elsewhen(fifo_0_empty === 1.U && fifo_1_empty === 0.U){
        FIFO_0.io.rd                                := false.B
        FIFO_1.io.rd                                := true.B
        FIFO_2.io.rd                                := false.B
        // FIFO_1.io.rd                                := true.B
    }.elsewhen(fifo_0_empty === 1.U && fifo_1_empty === 0.U){
        // FIFO_2.io.rd                                := true.B
        FIFO_0.io.rd                                := false.B
        FIFO_1.io.rd                                := false.B
        FIFO_2.io.rd                                := true.B
    }.otherwise{
        FIFO_0.io.rd                                := false.B
        FIFO_1.io.rd                                := false.B
        FIFO_2.io.rd                                := false.B
    }


    when(FIFO_0.io.rd){    
        io.ray_bvh_out_0                     := FIFO_0.io.dataout 
        io.ray_bvh_out_1                     := 0.S
        io.ray_bvh_out_2                     := 0.S
        // io.bvh_id_out                        := FIFO_0.io.dataout(63,32)
        // io.hit_out                                 := FIFO_0.io.dataout(95,64)
        io.fetch_hit                                  := false.B
    }.elsewhen(!FIFO_0.io.rd && FIFO_1.io.rd){    
        io.ray_bvh_out_0                     := 0.S
        io.ray_bvh_out_1                     := FIFO_1.io.dataout
        io.ray_bvh_out_2                     := 0.S
        // io.ray_id_out                         := FIFO_1.io.dataout(31,0) 
        // io.bvh_id_out                        := FIFO_1.io.dataout(63,32)
        // io.hit_out                                 := FIFO_1.io.dataout(95,64)
        io.fetch_hit                                  := false.B
    }.elsewhen(!FIFO_0.io.rd && (!FIFO_1.io.rd)&&FIFO_2.io.rd){ 
        io.ray_bvh_out_0                     := 0.S
        io.ray_bvh_out_1                     := 0.S
        io.ray_bvh_out_2                     := FIFO_2.io.dataout
        // io.ray_id_out                         := FIFO_2.io.dataout(31,0) 
        // io.bvh_id_out                        := FIFO_2.io.dataout(63,32)
        io.fetch_hit                                  := true.B
    }.otherwise{
        io.ray_bvh_out_0                     := 0.S
        io.ray_bvh_out_1                     := 0.S
        io.ray_bvh_out_2                     := 0.S
        io.fetch_hit                                  := false.B
    }
}
//  object Main extends App {
//   chisel3.Driver.execute(args, () => new ray_bvh_fetch())
// }