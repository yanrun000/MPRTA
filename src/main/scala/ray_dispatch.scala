package hardfloat
import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._

class ray_dispatch(val totalray : Int ) extends Module{
    val io = IO(new Bundle{
        val dispatch         =   Input(Bool())
        val dispatch_2    =   Input(Bool())
        val rayid_id          =   Output(UInt(32.W))
        val rayid_id_2     =   Output(UInt(32.W))
        // val rayid_ood =   Output(Bits(32.W))
        val ray_out           =   Output(Bool())
        val ray_out_2      =   Output(Bool())
        val ray_finish  =   Output(Bool())
        val node_id     =   Output(SInt(32.W)) 
        val dispatch_stop   = Output(Bool())
    })
    // val raynumber   = RegInit(0.U(32.W))
    val count              = RegInit(70.U(32.W))
    val ray_id             = RegInit(0.U(32.W))
    val ray_id_2       = RegInit(0.U(32.W))
    val node_id         = RegInit(0.S(32.W))
    val ray_out          = RegInit(0.U(1.W))
    val ray_out_2     = RegInit(0.U(1.W))
    val dispatch_s    =RegInit(0.U(1.W))//这个是是否停止的标记位
    val base                = RegInit(0.U(32.W))
    // val temp               = RegInit(0.U(1.W))
    when(count===70.U){
        ray_id               := 0.U
        ray_id_2          := 1.U
        count                := count -2.U
        node_id           := 0.S
        ray_out             := 1.U
        ray_out_2        := 1.U
        base                    := 1.U
    }.elsewhen(count>=1.U){
        ray_id               := base + 1.U
        ray_id_2          := base + 2.U
        base                  := base +2.U
        count                := count -2.U
        node_id           := 0.S
        ray_out             := 1.U
        ray_out_2        := 1.U
    }

    when(count ===0.U){
        when(io.dispatch ===1.U && io.dispatch_2 === 0.U&& ray_id < totalray.U -1.U){
            ray_id           := base +1.U
            ray_id_2      := ray_id_2
            base              := base +1.U
            node_id       := 0.S
            ray_out        := 1.U
            ray_out_2   := 0.U
            dispatch_s  := 1.U
        }.elsewhen(io.dispatch ===0.U && io.dispatch_2 === 1.U&& ray_id < totalray.U -1.U){
            ray_id           := ray_id 
            ray_id_2      := base +1.U
            base              := base +1.U
            node_id       := 0.S
            ray_out        := 0.U
            ray_out_2    := 1.U
            dispatch_s  := 0.U  
        }.elsewhen(io.dispatch ===1.U && io.dispatch_2 === 1.U&& ray_id < totalray.U -1.U){
            ray_id           := base + 1.U 
            ray_id_2      := base + 2.U
            base              := base + 2.U
            node_id       := 0.S
            ray_out        := 1.U
            ray_out_2    := 1.U
            dispatch_s  := 0.U  
        }.elsewhen(io.dispatch ===0.U && io.dispatch_2 === 0.U&& ray_id < totalray.U -1.U){
            ray_id           := ray_id 
            ray_id_2      := ray_id_2
            base              := base 
            node_id       := 0.S
            ray_out        := 0.U
            ray_out_2    := 0.U
            dispatch_s  := 0.U        
        }.otherwise{
            ray_id           := ray_id
            ray_id_2      := ray_id_2
            base              := base 
            node_id       := 0.S
            ray_out        := 0.U
            ray_out_2   := 0.U
            dispatch_s  := 0.U
        }
    }
    // }.otherwise{
    //     ray_id           := ray_id
    //     node_id       := 0.S
    //     ray_out        := 0.U
    //     dispatch_s  := 0.U
    // }

    when(ray_id === totalray.U){
        io.ray_finish    := true.B
    }.otherwise{
        io.ray_finish   := false.B
    }
    io.dispatch_stop:= dispatch_s
    io.rayid_id         := ray_id
    io.rayid_id_2    := ray_id_2
    io.node_id         := node_id
    io.ray_out          := ray_out            
    io.ray_out_2     := ray_out_2            
}
class raydispatch (dut:ray_dispatch) extends PeekPokeTester(dut){
        step(20)  
        poke(dut.io.dispatch,1.U)
      
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
        step(1)        
        poke(dut.io.dispatch,1.U)
         step(1)        
        poke(dut.io.dispatch,0.U)
        step(5)  
        // poke(dut.io.dispatch,0.U)
        step(20)    
}
object dispatchtest extends App {
  chisel3.iotesters.Driver.execute(args, () => new ray_dispatch(20))(c => new raydispatch(c))
}
//         io.rayid_id           := raynumber +1.U
//         // io.rayid_ood        := raynumber *2.U + 2.U
//         io.ray_out             := true.B
//         raynumber           := raynumber + 1.U
//         io.node_id            :=  0.S
        
//         when(raynumber === totalray. asUInt){
//             io.ray_finish         := true.B 

//         }.otherwise{
//             io.ray_finish    := false.B
//         }
//     }.otherwise{
//         io.rayid_id           := raynumber 
//         // io.rayid_ood        :=  raynumber 
//         raynumber           :=  raynumber 
//         io.ray_out             :=  false.B 
//         io.ray_finish         :=  false.B
//     }
// }
// object ray_dispatch extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new ray_dispatch(20))
// }
