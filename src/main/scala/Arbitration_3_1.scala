// package hardfloat
// import chisel3._
// import chisel3.util._
// import chisel3 . iotesters ._
// import org.scalatest._
// //这个是叶子的地址的仲裁，之前写的有点问题，需要再增加一条输入，用来将处理完的光线数据地址+1处理，这个优先级应该是最高的
// class Arbitration_3 extends Module{
//     val io = IO (new Bundle{
//         val node_id_3_0            = Input(SInt(32.W))
//         val ray_id_3_0                = Input(UInt(32.W))
//         val hit_3_0                       = Input(UInt(32.W))
//         val valid_3_0                   = Input(Bool())

//         val node_id_3_1            = Input(SInt(32.W))
//         val ray_id_3_1                = Input(UInt(32.W))
//         val hit_3_1                       = Input(UInt(32.W))
//         val valid_3_1                   = Input(Bool())

//         val node_id_3_2            = Input(SInt(32.W))
//         val ray_id_3_2                = Input(UInt(32.W))
//         val hit_3_2                       = Input(UInt(32.W))
//         val valid_3_2                   = Input(Bool())

//         val node_id_3_3            = Input(SInt(32.W))
//         val ray_id_3_3                = Input(UInt(32.W))
//         val hit_3_3                       = Input(UInt(32.W))
//         val valid_3_3                   = Input(Bool())

//         val node_id_3_4            = Input(SInt(32.W))
//         val ray_id_3_4                = Input(UInt(32.W))
//         val hit_3_4                       = Input(UInt(32.W))
//         val valid_3_4                   = Input(Bool())


//         val node_id_out       = Output(SInt(32.W))
//         val ray_id_out           = Output(UInt(32.W))
//         val hit_out                  = Output(UInt(32.W))

//         val valid_out              = Output(Bool())
//     })

//         val FIFO_A_3_0_node  = Module(new FIFO(32,35))
//         val FIFO_A_3_0_ray      = Module(new FIFO_0(32,35))
//         val FIFO_A_3_0_hit       = Module(new FIFO_0(32,35))
        
//         val FIFO_A_3_1_node  = Module(new FIFO(32,35))
//         val FIFO_A_3_1_ray      = Module(new FIFO_0(32,35))
//         val FIFO_A_3_1_hit       = Module(new FIFO_0(32,35))
        
//         val FIFO_A_3_2_node  = Module(new FIFO(32,35))
//         val FIFO_A_3_2_ray      = Module(new FIFO_0(32,35))
//         val FIFO_A_3_2_hit       = Module(new FIFO_0(32,35))

//         val FIFO_A_3_3_node  = Module(new FIFO(32,35))
//         val FIFO_A_3_3_ray      = Module(new FIFO_0(32,35))
//         val FIFO_A_3_3_hit       = Module(new FIFO_0(32,35))
        
//         val FIFO_A_3_4_node  = Module(new FIFO(32,35))
//         val FIFO_A_3_4_ray      = Module(new FIFO_0(32,35))
//         val FIFO_A_3_4_hit       = Module(new FIFO_0(32,35))
           
           
//         FIFO_A_3_0_node.io.wr              := io.valid_3_0
//         FIFO_A_3_0_ray.io.wr                  := io.valid_3_0
//         FIFO_A_3_0_hit.io.wr                   := io.valid_3_0
//         FIFO_A_3_0_node.io.datain      := io.node_id_3_0
//         FIFO_A_3_0_ray.io.datain          := io.ray_id_3_0
//         FIFO_A_3_0_hit.io.datain           := io.hit_3_0

//         FIFO_A_3_1_node.io.wr              := io.valid_3_1
//         FIFO_A_3_1_ray.io.wr                  := io.valid_3_1
//         FIFO_A_3_1_hit.io.wr                   := io.valid_3_1
//         FIFO_A_3_1_node.io.datain      := io.node_id_3_1
//         FIFO_A_3_1_ray.io.datain          := io.ray_id_3_1
//         FIFO_A_3_1_hit.io.datain           := io.hit_3_1

//         FIFO_A_3_2_node.io.wr              := io.valid_3_2
//         FIFO_A_3_2_ray.io.wr                  := io.valid_3_2
//         FIFO_A_3_2_hit.io.wr                   := io.valid_3_2
//         FIFO_A_3_2_node.io.datain      := io.node_id_3_2
//         FIFO_A_3_2_ray.io.datain          := io.ray_id_3_2
//         FIFO_A_3_2_hit.io.datain           := io.hit_3_2

//         FIFO_A_3_3_node.io.wr              := io.valid_3_3
//         FIFO_A_3_3_ray.io.wr                  := io.valid_3_3
//         FIFO_A_3_3_hit.io.wr                   := io.valid_3_3
//         FIFO_A_3_3_node.io.datain      := io.node_id_3_3
//         FIFO_A_3_3_ray.io.datain          := io.ray_id_3_3
//         FIFO_A_3_3_hit.io.datain           := io.hit_3_3

//         FIFO_A_3_4_node.io.wr              := io.valid_3_4
//         FIFO_A_3_4_ray.io.wr                  := io.valid_3_4
//         FIFO_A_3_4_hit.io.wr                   := io.valid_3_4
//         FIFO_A_3_4_node.io.datain      := io.node_id_3_4
//         FIFO_A_3_4_ray.io.datain          := io.ray_id_3_4
//         FIFO_A_3_4_hit.io.datain           := io.hit_3_4
    
//         val node_id_out_temp                = RegInit(0.S(32.W))
//         val ray_id_out_temp                    = RegInit(0.U(32.W))
//         val hit_out_temp                           = RegInit(0.U(32.W))
//         val valid_out_temp                       = RegInit(0.U(1.W))

//         when(FIFO_A_3_4_node.io.empty === 0.U){
//             FIFO_A_3_4_node.io.rd          := true.B
//             FIFO_A_3_4_ray.io.rd              := true.B
//             FIFO_A_3_4_hit.io.rd               := true.B
//             FIFO_A_3_1_node.io.rd          := false.B
//             FIFO_A_3_1_ray.io.rd              := false.B
//             FIFO_A_3_1_hit.io.rd               := false.B
//             FIFO_A_3_2_node.io.rd          := false.B
//             FIFO_A_3_2_ray.io.rd              := false.B
//             FIFO_A_3_2_hit.io.rd               := false.B
//             FIFO_A_3_3_node.io.rd          := false.B
//             FIFO_A_3_3_ray.io.rd              := false.B
//             FIFO_A_3_3_hit.io.rd               := false.B
//             FIFO_A_3_0_node.io.rd          := false.B
//             FIFO_A_3_0_ray.io.rd              := false.B
//             FIFO_A_3_0_hit.io.rd               := false.B
//         }.elsewhen(FIFO_A_3_4_node.io.empty === 1.U && FIFO_A_3_3_node.io.empty === 0.U){
//             FIFO_A_3_4_node.io.rd          := false.B
//             FIFO_A_3_4_ray.io.rd              := false.B
//             FIFO_A_3_4_hit.io.rd               := false.B
//             FIFO_A_3_3_node.io.rd          := true.B
//             FIFO_A_3_3_ray.io.rd              := true.B
//             FIFO_A_3_3_hit.io.rd               := true.B
//             FIFO_A_3_2_node.io.rd          := false.B
//             FIFO_A_3_2_ray.io.rd              := false.B
//             FIFO_A_3_2_hit.io.rd               := false.B
//             FIFO_A_3_1_node.io.rd          := false.B
//             FIFO_A_3_1_ray.io.rd              := false.B
//             FIFO_A_3_1_hit.io.rd               := false.B
//             FIFO_A_3_0_node.io.rd          := false.B
//             FIFO_A_3_0_ray.io.rd              := false.B
//             FIFO_A_3_0_hit.io.rd               := false.B
//         }.elsewhen(FIFO_A_3_4_node.io.empty === 1.U && FIFO_A_3_3_node.io.empty === 1.U&& FIFO_A_3_2_node.io.empty === 0.U){
//             FIFO_A_3_4_node.io.rd          := false.B
//             FIFO_A_3_4_ray.io.rd              := false.B
//             FIFO_A_3_4_hit.io.rd               := false.B
//             FIFO_A_3_3_node.io.rd          := false.B
//             FIFO_A_3_3_ray.io.rd              := false.B
//             FIFO_A_3_3_hit.io.rd               := false.B
//             FIFO_A_3_2_node.io.rd          := true.B
//             FIFO_A_3_2_ray.io.rd              := true.B
//             FIFO_A_3_2_hit.io.rd               := true.B
//             FIFO_A_3_1_node.io.rd          := false.B
//             FIFO_A_3_1_ray.io.rd              := false.B
//             FIFO_A_3_1_hit.io.rd               := false.B
//             FIFO_A_3_0_node.io.rd          := false.B
//             FIFO_A_3_0_ray.io.rd              := false.B
//             FIFO_A_3_0_hit.io.rd               := false.B
//         }.elsewhen(FIFO_A_3_4_node.io.empty === 1.U && FIFO_A_3_3_node.io.empty === 1.U&& FIFO_A_3_2_node.io.empty === 1.U&&FIFO_A_3_1_node.io.empty === 0.U){
//             FIFO_A_3_4_node.io.rd          := false.B
//             FIFO_A_3_4_ray.io.rd              := false.B
//             FIFO_A_3_4_hit.io.rd               := false.B
//             FIFO_A_3_3_node.io.rd          := false.B
//             FIFO_A_3_3_ray.io.rd              := false.B
//             FIFO_A_3_3_hit.io.rd               := false.B
//             FIFO_A_3_2_node.io.rd          := false.B
//             FIFO_A_3_2_ray.io.rd              := false.B
//             FIFO_A_3_2_hit.io.rd               := false.B
//             FIFO_A_3_1_node.io.rd          := true.B
//             FIFO_A_3_1_ray.io.rd              := true.B
//             FIFO_A_3_1_hit.io.rd               := true.B
//             FIFO_A_3_0_node.io.rd          := false.B
//             FIFO_A_3_0_ray.io.rd              := false.B
//             FIFO_A_3_0_hit.io.rd               := false.B
//         }.elsewhen(FIFO_A_3_4_node.io.empty === 1.U && FIFO_A_3_3_node.io.empty === 1.U&& FIFO_A_3_2_node.io.empty === 1.U&&FIFO_A_3_1_node.io.empty === 1.U&&FIFO_A_3_0_node.io.empty === 0.U){
//             FIFO_A_3_4_node.io.rd          := false.B
//             FIFO_A_3_4_ray.io.rd              := false.B
//             FIFO_A_3_4_hit.io.rd               := false.B
//             FIFO_A_3_1_node.io.rd          := false.B
//             FIFO_A_3_1_ray.io.rd              := false.B
//             FIFO_A_3_1_hit.io.rd               := false.B
//             FIFO_A_3_2_node.io.rd          := false.B
//             FIFO_A_3_2_ray.io.rd              := false.B
//             FIFO_A_3_2_hit.io.rd               := false.B
//             FIFO_A_3_3_node.io.rd          := false.B
//             FIFO_A_3_3_ray.io.rd              := false.B
//             FIFO_A_3_3_hit.io.rd               := false.B
//             FIFO_A_3_0_node.io.rd          := true.B
//             FIFO_A_3_0_ray.io.rd              := true.B
//             FIFO_A_3_0_hit.io.rd               := true.B
//         }.otherwise{
//             FIFO_A_3_0_node.io.rd          := false.B
//             FIFO_A_3_0_ray.io.rd              := false.B
//             FIFO_A_3_0_hit.io.rd               := false.B
//             FIFO_A_3_1_node.io.rd          := false.B
//             FIFO_A_3_1_ray.io.rd              := false.B
//             FIFO_A_3_1_hit.io.rd               := false.B
//             FIFO_A_3_2_node.io.rd          := false.B
//             FIFO_A_3_2_ray.io.rd              := false.B
//             FIFO_A_3_2_hit.io.rd               := false.B
//             FIFO_A_3_3_node.io.rd          := false.B
//             FIFO_A_3_3_ray.io.rd              := false.B
//             FIFO_A_3_3_hit.io.rd               := false.B
//             FIFO_A_3_4_node.io.rd          := false.B
//             FIFO_A_3_4_ray.io.rd              := false.B
//             FIFO_A_3_4_hit.io.rd               := false.B
//         }
//         val FIFO_0_empty                        = RegInit(0.U(1.W))
//         val FIFO_1_empty                        = RegInit(0.U(1.W))
//         val FIFO_2_empty                        = RegInit(0.U(1.W))
//         val FIFO_3_empty                        = RegInit(0.U(1.W))
//         val FIFO_4_empty                        = RegInit(0.U(1.W))

//         FIFO_0_empty                               := FIFO_A_3_0_node.io.empty
//         FIFO_1_empty                               := FIFO_A_3_1_node.io.empty
//         FIFO_2_empty                               := FIFO_A_3_2_node.io.empty
//         FIFO_3_empty                               := FIFO_A_3_3_node.io.empty
//         FIFO_4_empty                               := FIFO_A_3_4_node.io.empty

//         val rd_fifo_0_1                               =RegInit(0.U(1.W))
//         val rd_fifo_1_1                               =RegInit(0.U(1.W))
//         val rd_fifo_2_1                               =RegInit(0.U(1.W))
//         val rd_fifo_3_1                               =RegInit(0.U(1.W))
//         val rd_fifo_4_1                               =RegInit(0.U(1.W))
//         // val rd_fifo_3_1                               =RegInit(0.U(1.W))

//         // rd_fifo_0_1                                     := FIFO_A_3_0_node.io.rd
//         // rd_fifo_1_1                                     := FIFO_A_3_1_node.io.rd
//         // rd_fifo_2_1                                     := FIFO_A_3_2_node.io.rd
//         // rd_fifo_3_1                                     := FIFO_A_3_3_node.io.rd
//         // rd_fifo_4_1                                     := FIFO_A_3_4_node.io.rd

//         valid_out_temp                            := Mux(FIFO_A_3_0_node.io.rd || FIFO_A_3_1_node.io.rd || FIFO_A_3_2_node.io.rd || FIFO_A_3_3_node.io.rd ||FIFO_A_3_4_node.io.rd,1.U,0.U)

//         when(FIFO_4_empty === 0.U){
//             io.node_id_out                   := FIFO_A_3_4_node.io.dataout
//             io.ray_id_out                      := FIFO_A_3_4_ray.io.dataout
//             io.hit_out                              := FIFO_A_3_4_hit.io.dataout
//             io.valid_out                         := valid_out_temp
//         }.elsewhen(FIFO_4_empty === 1.U&&FIFO_3_empty === 0.U){
//             io.node_id_out                  := FIFO_A_3_3_node.io.dataout
//             io.ray_id_out                      := FIFO_A_3_3_ray.io.dataout
//             io.hit_out                              := FIFO_A_3_3_hit.io.dataout
//             io.valid_out                         := valid_out_temp
//         }.elsewhen(FIFO_4_empty === 1.U&&FIFO_3_empty === 1.U&&FIFO_2_empty === 0.U){
//             io.node_id_out                   := FIFO_A_3_2_node.io.dataout
//             io.ray_id_out                      := FIFO_A_3_2_ray.io.dataout
//             io.hit_out                              := FIFO_A_3_2_hit.io.dataout
//             io.valid_out                         := valid_out_temp
//         }.elsewhen(FIFO_4_empty === 1.U&&FIFO_3_empty === 1.U&&FIFO_2_empty === 1.U&&FIFO_1_empty === 0.U){
//             io.node_id_out                   := FIFO_A_3_1_node.io.dataout
//             io.ray_id_out                     := FIFO_A_3_1_ray.io.dataout
//             io.hit_out                              := FIFO_A_3_1_hit.io.dataout
//             io.valid_out                         := valid_out_temp
//         }.elsewhen(FIFO_4_empty === 1.U&&FIFO_3_empty === 1.U&&FIFO_2_empty === 1.U&&FIFO_1_empty === 1.U&&FIFO_0_empty === 0.U){
//             io.node_id_out                   := FIFO_A_3_0_node.io.dataout
//             io.ray_id_out                      := FIFO_A_3_0_ray.io.dataout
//             io.hit_out                             := FIFO_A_3_0_hit.io.dataout
//             io.valid_out                         := valid_out_temp
//         }.otherwise{
//             io.node_id_out                   := 0.S
//             io.ray_id_out                      := 0.U
//             io.hit_out                              := 0.U
//             io.valid_out                         := false.B
//         }
//         // io.node_id_out                              := node_id_out_temp
//         // io.ray_id_out                                  := ray_id_out_temp
//         // io.hit_out                                         := hit_out_temp
//         // io.valid_out                                     := valid_out_temp

// }
// object Main_arb_2 extends App {
//   chisel3.Driver.execute(args, () => new Arbitration_2())
// }

// // class ArbTester (dut:Arbitration_1) extends PeekPokeTester(dut){
// //         poke(dut.io.valid_0,true.B)
// //         poke(dut.io.node_id_0,1.S)
// //         poke(dut.io.ray_id_0,2.S)
// //         poke(dut.io.hit_0,3.S)
// //         poke(dut.io.valid_1,true.B)
// //         poke(dut.io.node_id_1,11.S)
// //         poke(dut.io.ray_id_1,12.S)
// //         poke(dut.io.valid_2,true.B)
// //         poke(dut.io.node_id_2,21.S)
// //         poke(dut.io.ray_id_2,22.S)
// //         // poke()
// //         step(1)
// //         poke(dut.io.valid_0,true.B)
// //         poke(dut.io.node_id_0,31.S)
// //         poke(dut.io.ray_id_0,32.S)
// //         poke(dut.io.hit_0,33.S)
// //         poke(dut.io.valid_1,true.B)
// //         poke(dut.io.node_id_1,41.S)
// //         poke(dut.io.ray_id_1,42.S)
// //         poke(dut.io.valid_2,true.B)
// //         poke(dut.io.node_id_2,51.S)
// //         poke(dut.io.ray_id_2,52.S)
// //         step(1)
// //         poke(dut.io.valid_0,false.B)
// //         poke(dut.io.valid_1,false.B)
// //         poke(dut.io.valid_2,false.B)
// //         step(10)
// // }
// // object Arbtest extends App {
// //   chisel3.iotesters.Driver.execute(args, () => new Arbitration_1())(c => new ArbTester(c))
// // }