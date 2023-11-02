package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
//这个是叶子的地址的仲裁，之前写的有点问题，需要再增加一条输入，用来将处理完的光线数据地址+1处理，这个优先级应该是最高的
class Arbitration_4 extends Module{
    val io = IO (new Bundle{
        val node_id_4_0            = Input(SInt(32.W))
        val ray_id_4_0                = Input(UInt(32.W))
        val hit_4_0                       = Input(UInt(32.W))
        val valid_4_0                   = Input(Bool())
        // val RAY_AABB                 = Input(Bool())

        val node_id_4_1            = Input(SInt(32.W))
        val ray_id_4_1                = Input(UInt(32.W))
        val hit_4_1                       = Input(UInt(32.W))
        val valid_4_1                   = Input(Bool())
        // val RAY_AABB_2            = Input(Bool())

        val node_id_out       = Output(SInt(32.W))
        val ray_id_out           = Output(UInt(32.W))
        val hit_out                  = Output(UInt(32.W))

        val RAY_AABB_out = Output(Bool())
        val RAY_AABB_2_out = Output(Bool())
        // val RAY_AABB_3_out = Output(Bool())

        val valid_out              = Output(Bool())
    })
        val FSM                               = Module(new FSM_1())

        val FIFO_A_4_0_node  = Module(new FIFO(32,35))
        val FIFO_A_4_0_ray      = Module(new FIFO_0(32,35))
        val FIFO_A_4_0_hit       = Module(new FIFO_0(32,35))
        
        val FIFO_A_4_1_node  = Module(new FIFO(32,35))
        val FIFO_A_4_1_ray      = Module(new FIFO_0(32,35))
        val FIFO_A_4_1_hit       = Module(new FIFO_0(32,35))
        
        FIFO_A_4_0_node.io.wr              := io.valid_4_0
        FIFO_A_4_0_ray.io.wr                  := io.valid_4_0
        FIFO_A_4_0_hit.io.wr                   := io.valid_4_0
        FIFO_A_4_0_node.io.datain      := io.node_id_4_0
        FIFO_A_4_0_ray.io.datain          := io.ray_id_4_0
        FIFO_A_4_0_hit.io.datain           := io.hit_4_0

        FIFO_A_4_1_node.io.wr              := io.valid_4_1
        FIFO_A_4_1_ray.io.wr                  := io.valid_4_1
        FIFO_A_4_1_hit.io.wr                   := io.valid_4_1
        FIFO_A_4_1_node.io.datain      := io.node_id_4_1
        FIFO_A_4_1_ray.io.datain          := io.ray_id_4_1
        FIFO_A_4_1_hit.io.datain           := io.hit_4_1
  
        val node_id_out_temp                = RegInit(0.S(32.W))
        val ray_id_out_temp                    = RegInit(0.U(32.W))
        val hit_out_temp                           = RegInit(0.U(32.W))
        val valid_out_temp                       = RegInit(0.U(1.W))
        FSM.io.request_0                        := FIFO_A_4_0_ray.io.empty ===0.U
        FSM.io.request_1                        := FIFO_A_4_1_ray.io.empty ===0.U
        val  valid_0                                    = RegInit(0.U(1.W))
        val  valid_1                                    = RegInit(0.U(1.W))
        when(FSM.io.grant_0&&(!FIFO_A_4_0_ray.io.empty)){
            FIFO_A_4_0_node.io.rd          := true.B
            FIFO_A_4_0_ray.io.rd              := true.B
            FIFO_A_4_0_hit.io.rd               := true.B
            FIFO_A_4_1_node.io.rd          := false.B
            FIFO_A_4_1_ray.io.rd              := false.B
            FIFO_A_4_1_hit.io.rd               := false.B
            valid_0                                            := 1.U
            valid_1                                            := 0.U
         }.elsewhen(FSM.io.grant_1&&(!FIFO_A_4_1_ray.io.empty)){
            FIFO_A_4_0_node.io.rd          := false.B
            FIFO_A_4_0_ray.io.rd              := false.B
            FIFO_A_4_0_hit.io.rd               := false.B
            FIFO_A_4_1_node.io.rd          := true.B
            FIFO_A_4_1_ray.io.rd              := true.B
            FIFO_A_4_1_hit.io.rd               := true.B
            valid_0                                            := 0.U
            valid_1                                            := 1.U
        }.otherwise{
            FIFO_A_4_0_node.io.rd          := false.B
            FIFO_A_4_0_ray.io.rd              := false.B
            FIFO_A_4_0_hit.io.rd               := false.B
            FIFO_A_4_1_node.io.rd          := false.B
            FIFO_A_4_1_ray.io.rd              := false.B
            FIFO_A_4_1_hit.io.rd               := false.B
            valid_0                                            := 0.U
            valid_1                                            := 0.U

        }
        // val FIFO_0_empty                        = RegInit(0.U(1.W))
        // val FIFO_1_empty                        = RegInit(0.U(1.W))
    

        // FIFO_0_empty                               := FIFO_A_4_0_node.io.empty
        // FIFO_1_empty                               := FIFO_A_4_1_node.io.empty
    
        // val rd_fifo_0_1                               =RegInit(0.U(1.W))
        // val rd_fifo_1_1                               =RegInit(0.U(1.W))
       
        // valid_out_temp                            := Mux(FIFO_A_4_0_node.io.rd || FIFO_A_4_1_node.io.rd,1.U,0.U)

        // when(FIFO_0_empty === 0.U){
        //     io.node_id_out                   := FIFO_A_4_0_node.io.dataout
        //     io.ray_id_out                      := FIFO_A_4_0_ray.io.dataout
        //     io.hit_out                              := FIFO_A_4_0_hit.io.dataout
        //     io.valid_out                         := valid_out_temp
        //     io.RAY_AABB_out             := true.B
        //     io.RAY_AABB_2_out        := false.B
        // }.elsewhen(FIFO_0_empty === 1.U&&FIFO_1_empty === 0.U){
        //     io.node_id_out                  := FIFO_A_4_1_node.io.dataout
        //     io.ray_id_out                      := FIFO_A_4_1_ray.io.dataout
        //     io.hit_out                              := FIFO_A_4_1_hit.io.dataout
        //     io.valid_out                         := valid_out_temp
        //     io.RAY_AABB_out             := false.B
        //     io.RAY_AABB_2_out        := true.B
        // }.otherwise{
        //     io.node_id_out                   := 0.S
        //     io.ray_id_out                      := 0.U
        //     io.hit_out                              := 0.U
        //     io.valid_out                         := false.B
        //     io.RAY_AABB_out             := false.B
        //     io.RAY_AABB_2_out        := false.B
        // }
        when (valid_0===1.U){
            io.node_id_out                   := FIFO_A_4_0_node.io.dataout
            io.ray_id_out                      := FIFO_A_4_0_ray.io.dataout
            io.hit_out                              := FIFO_A_4_0_hit.io.dataout
            io.valid_out                         := true.B
            io.RAY_AABB_out             := true.B
            io.RAY_AABB_2_out        := false.B
            // io.RAY_AABB_3_out        := false.B
        }.elsewhen(valid_1===1.U){
            io.node_id_out                  := FIFO_A_4_1_node.io.dataout
            io.ray_id_out                      := FIFO_A_4_1_ray.io.dataout
            io.hit_out                              := FIFO_A_4_1_hit.io.dataout
            io.valid_out                         := true.B
            io.RAY_AABB_out             := false.B
            io.RAY_AABB_2_out        := true.B
            // io.RAY_AABB_3_out        := false.B
        // }.elsewhen(valid_2===1.U){
        //     io.node_id_out                  := FIFO_A_4_2_node.io.dataout
        //     io.ray_id_out                      := FIFO_A_4_2_ray.io.dataout
        //     io.hit_out                              := FIFO_A_4_2_hit.io.dataout
        //     io.valid_out                         := true.B
        //     io.RAY_AABB_out             := false.B
        //     io.RAY_AABB_2_out        := false.B
        //     io.RAY_AABB_3_out        := true.B
        }.otherwise{
            io.node_id_out                   := 0.S
            io.ray_id_out                      := 0.U
            io.hit_out                              := 0.U
            io.valid_out                         := false.B
            io.RAY_AABB_out             := false.B
            io.RAY_AABB_2_out        := false.B
            // io.RAY_AABB_3_out        := false.B
        }
        // io.node_id_out                              := node_id_out_temp
        // io.ray_id_out                                  := ray_id_out_temp
        // io.hit_out                                         := hit_out_temp
        // io.valid_out                                     := valid_out_temp

}
object Main_arb_3 extends App {
  chisel3.Driver.execute(args, () => new Arbitration_4())
}

// class ArbTester (dut:Arbitration_1) extends PeekPokeTester(dut){
//         poke(dut.io.valid_0,true.B)
//         poke(dut.io.node_id_0,1.S)
//         poke(dut.io.ray_id_0,2.S)
//         poke(dut.io.hit_0,3.S)
//         poke(dut.io.valid_1,true.B)
//         poke(dut.io.node_id_1,11.S)
//         poke(dut.io.ray_id_1,12.S)
//         poke(dut.io.valid_2,true.B)
//         poke(dut.io.node_id_2,21.S)
//         poke(dut.io.ray_id_2,22.S)
//         // poke()
//         step(1)
//         poke(dut.io.valid_0,true.B)
//         poke(dut.io.node_id_0,31.S)
//         poke(dut.io.ray_id_0,32.S)
//         poke(dut.io.hit_0,33.S)
//         poke(dut.io.valid_1,true.B)
//         poke(dut.io.node_id_1,41.S)
//         poke(dut.io.ray_id_1,42.S)
//         poke(dut.io.valid_2,true.B)
//         poke(dut.io.node_id_2,51.S)
//         poke(dut.io.ray_id_2,52.S)
//         step(1)
//         poke(dut.io.valid_0,false.B)
//         poke(dut.io.valid_1,false.B)
//         poke(dut.io.valid_2,false.B)
//         step(10)
// }
// object Arbtest extends App {
//   chisel3.iotesters.Driver.execute(args, () => new Arbitration_1())(c => new ArbTester(c))
// }