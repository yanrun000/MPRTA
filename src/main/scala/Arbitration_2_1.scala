package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class Arbitration_2_1 extends Module{
    val io = IO (new Bundle{
        // val node_id_2_0            = Input(SInt(32.W))
        val ray_id_2_0                = Input(UInt(32.W))
        val hit_2_0                       = Input(UInt(32.W))
        val valid_2_0                   = Input(Bool())

        // val node_id_2_1            = Input(SInt(32.W))
        val ray_id_2_1                = Input(UInt(32.W))
        val hit_2_1                       = Input(UInt(32.W))
        val valid_2_1                   = Input(Bool())

        

        // val node_id_2_2            = Input(SInt(32.W))
        val ray_id_2_2                = Input(UInt(32.W))
        val hit_2_2                       = Input(UInt(32.W))
        val valid_2_2                   = Input(Bool())

        // val node_id_2_3            = Input(SInt(32.W))
        val ray_id_2_3                = Input(UInt(32.W))
        val hit_2_3                       = Input(UInt(32.W))
        val valid_2_3                   = Input(Bool())

        // val node_id_out       = Output(SInt(32.W))
        val ray_id_out           = Output(UInt(32.W))
        val hit_out                  = Output(UInt(32.W))

        val valid_out              = Output(Bool())
    })

        val FIFO_A_2_0_ray      = Module(new FIFO_0(32,35))
        val FIFO_A_2_0_hit       = Module(new FIFO_0(32,35))
        
        val FIFO_A_2_1_ray      = Module(new FIFO_0(32,35))
        val FIFO_A_2_1_hit       = Module(new FIFO_0(32,35))

        val FIFO_A_2_2_ray      = Module(new FIFO_0(32,35))
        val FIFO_A_2_2_hit       = Module(new FIFO_0(32,35))

        val FIFO_A_2_3_ray      = Module(new FIFO_0(32,35))
        val FIFO_A_2_3_hit       = Module(new FIFO_0(32,35))

        FIFO_A_2_0_ray.io.wr                  := io.valid_2_0
        FIFO_A_2_0_hit.io.wr                   := io.valid_2_0
        FIFO_A_2_0_ray.io.datain          := io.ray_id_2_0
        FIFO_A_2_0_hit.io.datain           := io.hit_2_0

        FIFO_A_2_1_ray.io.wr                  := io.valid_2_1
        FIFO_A_2_1_hit.io.wr                   := io.valid_2_1
        FIFO_A_2_1_ray.io.datain          := io.ray_id_2_1
        FIFO_A_2_1_hit.io.datain           := io.hit_2_1

        FIFO_A_2_2_ray.io.wr                  := io.valid_2_2
        FIFO_A_2_2_hit.io.wr                   := io.valid_2_2
        FIFO_A_2_2_ray.io.datain          := io.ray_id_2_2
        FIFO_A_2_2_hit.io.datain           := io.hit_2_2

        FIFO_A_2_3_ray.io.wr                  := io.valid_2_3
        FIFO_A_2_3_hit.io.wr                   := io.valid_2_3
        FIFO_A_2_3_ray.io.datain          := io.ray_id_2_3
        FIFO_A_2_3_hit.io.datain           := io.hit_2_3

      
        val ray_id_out_temp                    = RegInit(0.U(32.W))
        val hit_out_temp                           = RegInit(0.U(32.W))
        val valid_out_temp                       = RegInit(0.U(1.W))

        when(FIFO_A_2_0_ray.io.empty === 0.U){
            FIFO_A_2_0_ray.io.rd              := true.B
            FIFO_A_2_0_hit.io.rd               := true.B
            FIFO_A_2_1_ray.io.rd              := false.B
            FIFO_A_2_1_hit.io.rd               := false.B
            FIFO_A_2_2_ray.io.rd              := false.B
            FIFO_A_2_2_hit.io.rd               := false.B
            FIFO_A_2_3_ray.io.rd              := false.B
            FIFO_A_2_3_hit.io.rd               := false.B
        }.elsewhen(FIFO_A_2_0_ray.io.empty === 1.U && FIFO_A_2_1_ray.io.empty === 0.U){
            FIFO_A_2_0_ray.io.rd              := false.B
            FIFO_A_2_0_hit.io.rd               := false.B
            FIFO_A_2_1_ray.io.rd              := true.B
            FIFO_A_2_1_hit.io.rd               := true.B
            FIFO_A_2_2_ray.io.rd              := false.B
            FIFO_A_2_2_hit.io.rd               := false.B
            FIFO_A_2_3_ray.io.rd              := false.B
            FIFO_A_2_3_hit.io.rd               := false.B
        }.elsewhen(FIFO_A_2_0_ray.io.empty === 1.U && FIFO_A_2_1_ray.io.empty === 1.U&&FIFO_A_2_2_ray.io.empty === 0.U){
            FIFO_A_2_0_ray.io.rd              := false.B
            FIFO_A_2_0_hit.io.rd               := false.B
            FIFO_A_2_1_ray.io.rd              := false.B
            FIFO_A_2_1_hit.io.rd               := false.B
            FIFO_A_2_2_ray.io.rd              := true.B
            FIFO_A_2_2_hit.io.rd               := true.B
            FIFO_A_2_3_ray.io.rd              := false.B
            FIFO_A_2_3_hit.io.rd               := false.B
        }.elsewhen(FIFO_A_2_0_ray.io.empty === 1.U && FIFO_A_2_1_ray.io.empty === 1.U&&FIFO_A_2_2_ray.io.empty === 1.U&& FIFO_A_2_3_ray.io.empty === 0.U){
            FIFO_A_2_0_ray.io.rd              := false.B
            FIFO_A_2_0_hit.io.rd               := false.B
            FIFO_A_2_1_ray.io.rd              := false.B
            FIFO_A_2_1_hit.io.rd               := false.B
            FIFO_A_2_2_ray.io.rd              := false.B
            FIFO_A_2_2_hit.io.rd               := false.B
            FIFO_A_2_3_ray.io.rd              := true.B
            FIFO_A_2_3_hit.io.rd               := true.B
        }.otherwise{
            FIFO_A_2_0_ray.io.rd              := false.B
            FIFO_A_2_0_hit.io.rd               := false.B
            FIFO_A_2_1_ray.io.rd              := false.B
            FIFO_A_2_1_hit.io.rd               := false.B
            FIFO_A_2_2_ray.io.rd              := false.B
            FIFO_A_2_2_hit.io.rd               := false.B
            FIFO_A_2_3_ray.io.rd              := false.B
            FIFO_A_2_3_hit.io.rd               := false.B
        }
        val FIFO_0_empty                        = RegInit(0.U(1.W))
        val FIFO_1_empty                        = RegInit(0.U(1.W))
        val FIFO_2_empty                        = RegInit(0.U(1.W))
        val FIFO_3_empty                        = RegInit(0.U(1.W))

        FIFO_0_empty                               := FIFO_A_2_0_ray.io.empty
        FIFO_1_empty                               := FIFO_A_2_1_ray.io.empty
        FIFO_2_empty                               := FIFO_A_2_2_ray.io.empty
        FIFO_3_empty                               := FIFO_A_2_3_ray.io.empty

        val rd_fifo_0_1                               =RegInit(0.U(1.W))
        val rd_fifo_1_1                               =RegInit(0.U(1.W))
        val rd_fifo_2_1                               =RegInit(0.U(1.W))
        val rd_fifo_3_1                               =RegInit(0.U(1.W))

        // rd_fifo_0_1                                     := FIFO_A_2_0_ray.io.rd
        // rd_fifo_1_1                                     := FIFO_A_2_1_ray.io.rd
        // rd_fifo_2_1                                     := FIFO_A_2_2_ray.io.rd
        // rd_fifo_3_1                                     := FIFO_A_2_3_ray.io.rd

        // val rd_fifo_0_2                               =RegInit(0.U(1.W))
        // val rd_fifo_1_2                               =RegInit(0.U(1.W))
        // val rd_fifo_2_2                               =RegInit(0.U(1.W))

        // rd_fifo_0_2                                      := rd_fifo_0_1
        // rd_fifo_1_2                                      := rd_fifo_1_1
        // rd_fifo_2_2                                      := rd_fifo_2_1         
        // valid_out_temp                            := Mux(rd_fifo_0_1===1.U||rd_fifo_1_1===1.U||rd_fifo_2_1===1.U||rd_fifo_3_1===1.U,1.U,0.U)
        valid_out_temp                            := Mux(FIFO_A_2_0_ray.io.rd || FIFO_A_2_1_ray.io.rd || FIFO_A_2_2_ray.io.rd || FIFO_A_2_3_ray.io.rd,1.U,0.U)
        when(FIFO_0_empty === 0.U){
            io.ray_id_out                             := FIFO_A_2_0_ray.io.dataout
            io.hit_out                                    := FIFO_A_2_0_hit.io.dataout
            io.valid_out                                := valid_out_temp
        }.elsewhen(FIFO_0_empty === 1.U&&FIFO_1_empty === 0.U){
            io.ray_id_out                              := FIFO_A_2_1_ray.io.dataout
            io.hit_out                                      := FIFO_A_2_1_hit.io.dataout
            io.valid_out                                  := valid_out_temp
        }.elsewhen(FIFO_0_empty === 1.U&&FIFO_1_empty === 1.U&&FIFO_2_empty === 0.U){
            io.ray_id_out                              := FIFO_A_2_2_ray.io.dataout
            io.hit_out                                      := FIFO_A_2_2_hit.io.dataout
            io.valid_out                                  := valid_out_temp
        }.elsewhen(FIFO_0_empty === 1.U&&FIFO_1_empty === 1.U&&FIFO_2_empty === 1.U&&FIFO_3_empty === 0.U){
        //     // node_id_out_temp                  := FIFO_A_2_3_node.io.dataout
        //     ray_id_out_temp                      := FIFO_A_2_3_ray.io.dataout
        //     hit_out_temp                              := FIFO_A_2_3_hit.io.dataout
            io.ray_id_out                              := FIFO_A_2_3_ray.io.dataout
            io.hit_out                                      := FIFO_A_2_3_hit.io.dataout
            io.valid_out                                  := valid_out_temp
        }.otherwise{
            // node_id_out_temp                  := 0.S
            // ray_id_out_temp                      := 0.U
            // hit_out_temp                             := 0.U
            io.ray_id_out                                  := 0.U
            io.hit_out                                         := 0.U
            io.valid_out                                     := false.B
        }
        // io.node_id_out                              := node_id_out_temp
        // io.ray_id_out                                  := ray_id_out_temp
        // io.hit_out                                         := hit_out_temp
        // io.valid_out                                     := valid_out_temp

}
object Main_arb_1 extends App {
  chisel3.Driver.execute(args, () => new Arbitration_2_1())
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