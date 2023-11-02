
package hardfloat
import Chisel._
// import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._


class  TOP_1 extends Module{
    val io = IO(new Bundle{
        val hitT                        =  Output(UInt(32.W))
        val hitIndex               =  Output(SInt(32.W))
        val rtp_finish            =  Output(Bool())
        val ray_id_triangle = Output(UInt(32.W))
        val counter_fdiv      = Output(UInt(64.W))
        // val dispatch_count  = Output(UInt(8.W))
        val TRV_1_valid                         = Output(UInt(64.W))
        val TRV_2_valid                         = Output(UInt(64.W))
        val IST_1_valid                          = Output(UInt(64.W))
        val clock_count                         = Output(UInt(64.W))
    })

    val  Ray_Dispatch             =  Module(new ray_dispatch(2000000))
    val  Ray_origx                    =  Module(new ray_memory(2073600))//32位
    val  Ray_origy                    =  Module(new ray_memory(2073600))//32位
    val  Ray_origz                    =  Module(new ray_memory(2073600))//32位
    // val  Ray_tmin                     =  Module(new ray_memory(2073600))//32位

    val  Ray_dirx                       =  Module(new ray_memory(2073600))//32位
    val  Ray_diry                      =  Module(new ray_memory(2073600))//32位
    val  Ray_dirz                      =  Module(new ray_memory(2073600))//32位
    val  Ray_hitT                      =  Module(new ray_memory(2073600))//32位

    val  Ray_idirx                     =  Module(new ray_memory(2073600))//32位
    val  Ray_idiry                     =  Module(new ray_memory(2073600))//32位
    val  Ray_idirz                     =  Module(new ray_memory(2073600))//32位

    val  Ray_oodx                     =  Module(new ray_memory(2073600))//32位
    val  Ray_oody                     =  Module(new ray_memory(2073600))//32位
    val  Ray_oodz                     =  Module(new ray_memory(2073600))//32位

    val  BVH_RAM_0_x           =  Module(new BVH_memory(21005100))//n0xy
    val  BVH_RAM_0_y           =  Module(new BVH_memory(21005100))
    val  BVH_RAM_0_z           =  Module(new BVH_memory(21005100))
    val  BVH_RAM_0_w          =  Module(new BVH_memory(21005100))

    val  BVH_RAM_1_x           =  Module(new BVH_memory(21005100))//n1xy
    val  BVH_RAM_1_y           =  Module(new BVH_memory(21005100))
    val  BVH_RAM_1_z           =  Module(new BVH_memory(21005100))
    val  BVH_RAM_1_w          =  Module(new BVH_memory(21005100))

    val  BVH_RAM_z_x           =  Module(new BVH_memory(21005100))//nz
    val  BVH_RAM_z_y           =  Module(new BVH_memory(21005100))
    val  BVH_RAM_z_z           =  Module(new BVH_memory(21005100))
    val  BVH_RAM_z_w          =  Module(new BVH_memory(21005100))

    val  BVH_RAM_tmp_x     =  Module(new BVH_memory_0(21005100))//tmp    
    val  BVH_RAM_tmp_y     =  Module(new BVH_memory_0(21005100))

    val  TRI_RAM_x                       = Module(new Triangle_memory_valid(21005100))
    val  TRI_RAM_y                       = Module(new Triangle_memory(21005100))
    val  TRI_RAM_z                       = Module(new Triangle_memory(21005100))
    val  TRI_RAM_w                      = Module(new Triangle_memory(21005100))
    val  RAY_AABB                   =  Module(new ray_AABB_1())//光线与BVH的相交测试
    val  RAY_AABB_2                   =  Module(new ray_AABB_1())//光线与BVH的相交测试
    val  Arbitration_1                  =  Module(new Arbitration_1())//关于三种同时到达的仲裁1
    val  Arbitration_1_2             =  Module(new Arbitration_1())//关于三种同时到达的仲裁1

    val  Arbitration_2             =  Module(new Arbitration_2_1())//关于四种弹栈请求的仲裁2
    val  Arbitration_2_2        =  Module(new Arbitration_2_1())//关于四种弹栈请求的仲裁2
    val  Arbitration_3             =  Module(new Arbitration_3())//关于四种三角形部分内容的仲裁3
    val  Arbitration_3_2        =  Module(new Arbitration_3())//关于四种三角形部分内容的仲裁3
    val  Arbitration_4             =  Module(new Arbitration_4())
    val  Stack_manage          =  Module(new Stackmanage())//栈的管理
    val  Stack_manage_2     =  Module(new Stackmanage())//栈的管理
    val  Triangle                        =  Module(new Triangle()) //三角形处理阶段的流水线       

    val clock_counter           = RegInit(0.U(64.W))
    clock_counter                  := clock_counter +1.U
    io.clock_count                  := clock_counter
   //整体设计还差发射的设计
    //光线发射,这里还有一点问题，就是发射信号发射之后，会一直发射
    Ray_Dispatch.io.dispatch                        := Stack_manage.io.Dis_en
    Ray_Dispatch.io.dispatch_2                   := Stack_manage_2.io.Dis_en

    when(Ray_Dispatch.io.ray_out===true.B){
        Arbitration_1.io.node_id_2                  := Ray_Dispatch.io.node_id
        Arbitration_1.io.ray_id_2                      := Ray_Dispatch.io.rayid_id
        Arbitration_1.io.valid_2                         := true.B
    }.otherwise{
        Arbitration_1.io.node_id_2                  := 0.S
        Arbitration_1.io.ray_id_2                      := 0.U
        Arbitration_1.io.valid_2                         := false.B 
    }

    when(Ray_Dispatch.io.ray_out_2===true.B){
        Arbitration_1_2.io.node_id_2                  := Ray_Dispatch.io.node_id
        Arbitration_1_2.io.ray_id_2                      := Ray_Dispatch.io.rayid_id_2
        Arbitration_1_2.io.valid_2                         := true.B
    }.otherwise{
        Arbitration_1_2.io.node_id_2                  := 0.S
        Arbitration_1_2.io.ray_id_2                      := 0.U
        Arbitration_1_2.io.valid_2                         := false.B 
    }
    //这个是整个系统完成计算的整体的输出
    io.rtp_finish                                       := Ray_Dispatch.io.ray_finish && Stack_manage.io.Finish
    // io.rtp_finish                                       := false.B
//------------------------------------------------------
    when(RAY_AABB.io.back&&RAY_AABB.io.valid_out){
        Arbitration_1.io.node_id_1  := RAY_AABB.io.nodeIdx_1
        Arbitration_1.io.ray_id_1      := RAY_AABB.io.rayid_out
        Arbitration_1.io.valid_1         := RAY_AABB.io.back
    }.otherwise{
        Arbitration_1.io.node_id_1  := 0.S
        Arbitration_1.io.ray_id_1      := 0.U
        Arbitration_1.io.valid_1         := false.B
    }
    when(RAY_AABB_2.io.back&&RAY_AABB_2.io.valid_out){
        Arbitration_1_2.io.node_id_1  := RAY_AABB_2.io.nodeIdx_1
        Arbitration_1_2.io.ray_id_1      := RAY_AABB_2.io.rayid_out
        Arbitration_1_2.io.valid_1         := RAY_AABB_2.io.back
    }.otherwise{
        Arbitration_1_2.io.node_id_1  := 0.S
        Arbitration_1_2.io.ray_id_1      := 0.U
        Arbitration_1_2.io.valid_1         := false.B
    }
//------------------------------------------------------
    val memory_valid                        = RegInit(0.U(1.W)) //这个是读数据时的有效
    val hit_temp                                   = RegInit(0.U(32.W))//这个用来暂存hit的值得
    val ray_id_temp                            = RegInit(0.U(32.W))
    val hit_from_arb                           = RegInit(0.U(1.W))//这个是用来判断hit有无从仲裁里面出来
    val TRV_1                                         = RegInit(0.U(64.W))      
    when(Arbitration_1.io.valid_out&&Arbitration_1.io.hit_out =/=0.U){
        memory_valid                              := 1.U
        Ray_idirx.io.Ray_id                       := Arbitration_1.io.ray_id_out
        Ray_idiry.io.Ray_id                       := Arbitration_1.io.ray_id_out
        Ray_idirz.io.Ray_id                       := Arbitration_1.io.ray_id_out
        Ray_oodx.io.Ray_id                        := Arbitration_1.io.ray_id_out
        Ray_oody.io.Ray_id                        := Arbitration_1.io.ray_id_out
        Ray_oodz.io.Ray_id                        := Arbitration_1.io.ray_id_out
        hit_temp                                  := Arbitration_1.io.hit_out
        BVH_RAM_0_x.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_0_y.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_0_z.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_0_w.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_x.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_y.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_z.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_w.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_x.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_y.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_z.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_w.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_tmp_x.io.BVH_id            := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_tmp_y.io.BVH_id            := Arbitration_1.io.node_id_out/4.S
        ray_id_temp                                       := Arbitration_1.io.ray_id_out
        hit_from_arb                                      := 1.U                    
    }.elsewhen(Arbitration_1.io.valid_out&&Arbitration_1.io.hit_out ===0.U){
        memory_valid                                   := 1.U
        Ray_idirx.io.Ray_id                         := Arbitration_1.io.ray_id_out
        Ray_idiry.io.Ray_id                         := Arbitration_1.io.ray_id_out
        Ray_idirz.io.Ray_id                         := Arbitration_1.io.ray_id_out
        Ray_oodx.io.Ray_id                        := Arbitration_1.io.ray_id_out
        Ray_oody.io.Ray_id                        := Arbitration_1.io.ray_id_out
        Ray_oodz.io.Ray_id                        := Arbitration_1.io.ray_id_out
        hit_temp                                              := 0.U
        BVH_RAM_0_x.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_0_y.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_0_z.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_0_w.io.BVH_id             := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_x.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_y.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_z.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_1_w.io.BVH_id             := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_x.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_y.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_z.io.BVH_id              := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_z_w.io.BVH_id             := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_tmp_x.io.BVH_id        := Arbitration_1.io.node_id_out/4.S
        BVH_RAM_tmp_y.io.BVH_id        := Arbitration_1.io.node_id_out/4.S
        Ray_hitT.io.Ray_id                          := Arbitration_1.io.hit_out
        ray_id_temp                                       := Arbitration_1.io.ray_id_out
        hit_from_arb                                      := 0.U
    }.otherwise{
        memory_valid                                   := 0.U
        hit_temp                                              := 0.U
        hit_from_arb                                      := 0.U
    }
    when(memory_valid===1.U){
        RAY_AABB.io.ray_idir.x                      := Ray_idirx.io.Ray_out
        RAY_AABB.io.ray_idir.y                      := Ray_idiry.io.Ray_out
        RAY_AABB.io.ray_idir.z                      := Ray_idirz.io.Ray_out
        RAY_AABB.io.ray_ood.x                     := Ray_oodx.io.Ray_out
        RAY_AABB.io.ray_ood.y                     := Ray_oody.io.Ray_out
        RAY_AABB.io.ray_ood.z                     := Ray_oodz.io.Ray_out
        RAY_AABB.io.ray_tmin                      := 0.U
        when(hit_from_arb===1.U){
            RAY_AABB.io.ray_hitT                    := hit_temp
        }.otherwise{
            RAY_AABB.io.ray_hitT                    := Ray_hitT.io.Ray_out
        }
        RAY_AABB.io.bvh_n0xy.x                  :=  BVH_RAM_0_x.io.BVH_out
        RAY_AABB.io.bvh_n0xy.y                  :=  BVH_RAM_0_y.io.BVH_out
        RAY_AABB.io.bvh_n0xy.z                  :=  BVH_RAM_0_z.io.BVH_out
        RAY_AABB.io.bvh_n0xy.w                 :=  BVH_RAM_0_w.io.BVH_out

        RAY_AABB.io.bvh_n1xy.x                  :=  BVH_RAM_1_x.io.BVH_out
        RAY_AABB.io.bvh_n1xy.y                  :=  BVH_RAM_1_y.io.BVH_out
        RAY_AABB.io.bvh_n1xy.z                  :=  BVH_RAM_1_z.io.BVH_out
        RAY_AABB.io.bvh_n1xy.w                 :=  BVH_RAM_1_w.io.BVH_out

        RAY_AABB.io.bvh_nz.x                      :=  BVH_RAM_z_x.io.BVH_out
        RAY_AABB.io.bvh_nz.y                      :=  BVH_RAM_z_y.io.BVH_out
        RAY_AABB.io.bvh_nz.z                      :=  BVH_RAM_z_z.io.BVH_out
        RAY_AABB.io.bvh_nz.w                     :=  BVH_RAM_z_w.io.BVH_out

        RAY_AABB.io.bvh_temp.x                 :=  BVH_RAM_tmp_x.io.BVH_out.asSInt//这个感觉不太对
        RAY_AABB.io.bvh_temp.y                 :=  BVH_RAM_tmp_y.io.BVH_out.asSInt
        RAY_AABB.io.rayid                               := ray_id_temp
        RAY_AABB.io.valid_en                        := true.B
        TRV_1                                                        := TRV_1 +1.U
    }.otherwise{
        RAY_AABB.io.ray_idir.x                      := 0.U
        RAY_AABB.io.ray_idir.y                      := 0.U
        RAY_AABB.io.ray_idir.z                      := 0.U
        RAY_AABB.io.ray_ood.x                     := 0.U
        RAY_AABB.io.ray_ood.y                     := 0.U
        RAY_AABB.io.ray_ood.z                     := 0.U
        RAY_AABB.io.ray_hitT                        := 0.U
        RAY_AABB.io.ray_tmin                      := 0.U
        RAY_AABB.io.bvh_n0xy.x                  :=  0.U
        RAY_AABB.io.bvh_n0xy.y                  :=  0.U
        RAY_AABB.io.bvh_n0xy.z                  :=  0.U
        RAY_AABB.io.bvh_n0xy.w                 :=  0.U
        RAY_AABB.io.bvh_n1xy.x                  :=  0.U
        RAY_AABB.io.bvh_n1xy.y                  :=  0.U
        RAY_AABB.io.bvh_n1xy.z                  :=  0.U
        RAY_AABB.io.bvh_n1xy.w                 :=  0.U
        RAY_AABB.io.bvh_nz.x                      :=  0.U
        RAY_AABB.io.bvh_nz.y                      :=  0.U
        RAY_AABB.io.bvh_nz.z                      :=  0.U
        RAY_AABB.io.bvh_nz.w                    :=  0.U
        RAY_AABB.io.bvh_temp.x                 :=  0.S
        RAY_AABB.io.bvh_temp.y                 :=  0.S
        RAY_AABB.io.rayid                               := 0.U
        RAY_AABB.io.valid_en                        := false.B
        TRV_1                                                        := TRV_1 
    }
    io.TRV_1_valid                                          := TRV_1
    when(RAY_AABB.io.pop&&RAY_AABB.io.valid_out){
    //    Arbitration_2.io.node_id_2_3         := RAY_AABB.io.nodeIdx_0//这个好像没意义，但是先保留把
       Arbitration_2.io.ray_id_2_3             := RAY_AABB.io.rayid_out
       Arbitration_2.io.hit_2_3                    := RAY_AABB.io.hitT_out
       Arbitration_2.io.valid_2_3                := 1.U
    }.otherwise{
        // Arbitration_2.io.node_id_2_3         := 0.S
       Arbitration_2.io.ray_id_2_3             := 0.U
       Arbitration_2.io.hit_2_3                    := 0.U
       Arbitration_2.io.valid_2_3                := 0.U
    }

//------------------------------------------------------
    val memory_valid_2                        = RegInit(0.U(1.W)) //这个是读数据时的有效
    val hit_temp_2                                   = RegInit(0.U(32.W))//这个用来暂存hit的值得
    val ray_id_temp_2                            = RegInit(0.U(32.W))
    val hit_from_arb_2                           = RegInit(0.U(1.W))//这个是用来判断hit有无从仲裁里面出来
    val TRV_2                                               = RegInit(0.U(64.W))
    when(Arbitration_1_2.io.valid_out&&Arbitration_1_2.io.hit_out =/=0.U){
        memory_valid_2                                   := 1.U
        // Ray_RAM_1.io.Ray_id                    := Arbitration_1.io.ray_id_out
        // Ray_RAM_2.io.Ray_id                    := Arbitration_1.io.ray_id_out
        Ray_idirx.io.Ray_id_2                         := Arbitration_1_2.io.ray_id_out
        Ray_idiry.io.Ray_id_2                         := Arbitration_1_2.io.ray_id_out
        Ray_idirz.io.Ray_id_2                         := Arbitration_1_2.io.ray_id_out
        Ray_oodx.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        Ray_oody.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        Ray_oodz.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        hit_temp_2                                              := Arbitration_1_2.io.hit_out
        BVH_RAM_0_x.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_0_y.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_0_z.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_0_w.io.BVH_id_2             := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_x.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_y.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_z.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_w.io.BVH_id_2             := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_x.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_y.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_z.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_w.io.BVH_id_2             := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_tmp_x.io.BVH_id_2        := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_tmp_y.io.BVH_id_2        := Arbitration_1_2.io.node_id_out/4.S
        ray_id_temp_2                                       := Arbitration_1_2.io.ray_id_out
        hit_from_arb_2                                      := 1.U                    
    }.elsewhen(Arbitration_1_2.io.valid_out&&Arbitration_1_2.io.hit_out ===0.U){
        memory_valid_2                                   := 1.U
        // Ray_RAM_1.io.Ray_id                    := Arbitration_1.io.ray_id_out
        // Ray_RAM_2.io.Ray_id                    := Arbitration_1.io.ray_id_out
        Ray_idirx.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        Ray_idiry.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        Ray_idirz.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        Ray_oodx.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        Ray_oody.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        Ray_oodz.io.Ray_id_2                        := Arbitration_1_2.io.ray_id_out
        hit_temp_2                                              := 0.U
        BVH_RAM_0_x.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_0_y.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_0_z.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_0_w.io.BVH_id_2             := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_x.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_y.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_z.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_1_w.io.BVH_id_2             := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_x.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_y.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_z.io.BVH_id_2              := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_z_w.io.BVH_id_2             := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_tmp_x.io.BVH_id_2        := Arbitration_1_2.io.node_id_out/4.S
        BVH_RAM_tmp_y.io.BVH_id_2        := Arbitration_1_2.io.node_id_out/4.S
        Ray_hitT.io.Ray_id_2                          := Arbitration_1_2.io.ray_id_out
        ray_id_temp_2                                       := Arbitration_1_2.io.ray_id_out
        hit_from_arb_2                                      := 0.U
    }.otherwise{
        memory_valid_2                                   := 0.U
        hit_temp_2                                              := 0.U
        hit_from_arb_2                                      := 0.U
    }
    when(memory_valid_2===1.U){
        RAY_AABB_2.io.ray_idir.x                      := Ray_idirx.io.Ray_out_2
        RAY_AABB_2.io.ray_idir.y                      := Ray_idiry.io.Ray_out_2
        RAY_AABB_2.io.ray_idir.z                      := Ray_idirz.io.Ray_out_2
        RAY_AABB_2.io.ray_ood.x                     := Ray_oodx.io.Ray_out_2
        RAY_AABB_2.io.ray_ood.y                     := Ray_oody.io.Ray_out_2
        RAY_AABB_2.io.ray_ood.z                     := Ray_oodz.io.Ray_out_2
        RAY_AABB_2.io.ray_tmin                      := 0.U
        when(hit_from_arb_2===1.U){
            RAY_AABB_2.io.ray_hitT                    := hit_temp_2
        }.otherwise{
            RAY_AABB_2.io.ray_hitT                    := Ray_hitT.io.Ray_out_2
        }
        RAY_AABB_2.io.bvh_n0xy.x                  :=  BVH_RAM_0_x.io.BVH_out_2
        RAY_AABB_2.io.bvh_n0xy.y                  :=  BVH_RAM_0_y.io.BVH_out_2
        RAY_AABB_2.io.bvh_n0xy.z                  :=  BVH_RAM_0_z.io.BVH_out_2
        RAY_AABB_2.io.bvh_n0xy.w                 :=  BVH_RAM_0_w.io.BVH_out_2
        RAY_AABB_2.io.bvh_n1xy.x                  :=  BVH_RAM_1_x.io.BVH_out_2
        RAY_AABB_2.io.bvh_n1xy.y                  :=  BVH_RAM_1_y.io.BVH_out_2
        RAY_AABB_2.io.bvh_n1xy.z                  :=  BVH_RAM_1_z.io.BVH_out_2
        RAY_AABB_2.io.bvh_n1xy.w                 :=  BVH_RAM_1_w.io.BVH_out_2
        RAY_AABB_2.io.bvh_nz.x                      :=  BVH_RAM_z_x.io.BVH_out_2
        RAY_AABB_2.io.bvh_nz.y                      :=  BVH_RAM_z_y.io.BVH_out_2
        RAY_AABB_2.io.bvh_nz.z                      :=  BVH_RAM_z_z.io.BVH_out_2
        RAY_AABB_2.io.bvh_nz.w                     :=  BVH_RAM_z_w.io.BVH_out_2
        RAY_AABB_2.io.bvh_temp.x                 :=  BVH_RAM_tmp_x.io.BVH_out_2.asSInt//这个感觉不太对
        RAY_AABB_2.io.bvh_temp.y                 :=  BVH_RAM_tmp_y.io.BVH_out_2.asSInt
        RAY_AABB_2.io.rayid                               := ray_id_temp_2
        RAY_AABB_2.io.valid_en                        := true.B
        TRV_2                                                              := TRV_2 +1.U
    }.otherwise{
        RAY_AABB_2.io.ray_idir.x                      := 0.U
        RAY_AABB_2.io.ray_idir.y                      := 0.U
        RAY_AABB_2.io.ray_idir.z                      := 0.U
        RAY_AABB_2.io.ray_ood.x                     := 0.U
        RAY_AABB_2.io.ray_ood.y                     := 0.U
        RAY_AABB_2.io.ray_ood.z                     := 0.U
        RAY_AABB_2.io.ray_hitT                        := 0.U
        RAY_AABB_2.io.ray_tmin                      := 0.U
        RAY_AABB_2.io.bvh_n0xy.x                  :=  0.U
        RAY_AABB_2.io.bvh_n0xy.y                  :=  0.U
        RAY_AABB_2.io.bvh_n0xy.z                  :=  0.U
        RAY_AABB_2.io.bvh_n0xy.w                 :=  0.U
        RAY_AABB_2.io.bvh_n1xy.x                  :=  0.U
        RAY_AABB_2.io.bvh_n1xy.y                  :=  0.U
        RAY_AABB_2.io.bvh_n1xy.z                  :=  0.U
        RAY_AABB_2.io.bvh_n1xy.w                 :=  0.U
        RAY_AABB_2.io.bvh_nz.x                      :=  0.U
        RAY_AABB_2.io.bvh_nz.y                      :=  0.U
        RAY_AABB_2.io.bvh_nz.z                      :=  0.U
        RAY_AABB_2.io.bvh_nz.w                    :=  0.U

        RAY_AABB_2.io.bvh_temp.x                 :=  0.S
        RAY_AABB_2.io.bvh_temp.y                 :=  0.S
        RAY_AABB_2.io.rayid                               := 0.U
        RAY_AABB_2.io.valid_en                        := false.B
        TRV_2                                                               := TRV_2
    }
    when(RAY_AABB_2.io.pop&&RAY_AABB_2.io.valid_out){
       Arbitration_2_2.io.ray_id_2_3             := RAY_AABB_2.io.rayid_out
       Arbitration_2_2.io.hit_2_3                    := RAY_AABB_2.io.hitT_out
       Arbitration_2_2.io.valid_2_3                := 1.U
    }.otherwise{
       Arbitration_2_2.io.ray_id_2_3             := 0.U
       Arbitration_2_2.io.hit_2_3                    := 0.U
       Arbitration_2_2.io.valid_2_3                := 0.U
    }
    io.TRV_2_valid                                               := TRV_2
//---------------------------------------------------------


    //AABB之后的入栈请求
    when(RAY_AABB.io.push&&RAY_AABB.io.valid_out){
        Stack_manage.io.push                                := true.B
        Stack_manage.io.push_en                        := true.B
        Stack_manage.io.node_id_push_in     := RAY_AABB.io.nodeIdx_0
        Stack_manage.io.ray_id_push                := RAY_AABB.io.rayid_out
    }.otherwise{
        Stack_manage.io.push                                 := false.B
        Stack_manage.io.push_en                         := false.B
        Stack_manage.io.node_id_push_in       := 0.S
        Stack_manage.io.ray_id_push                 := 0.U
    }

    when(RAY_AABB_2.io.push&&RAY_AABB_2.io.valid_out){
        Stack_manage_2.io.push                                := true.B
        Stack_manage_2.io.push_en                        := true.B
        Stack_manage_2.io.node_id_push_in     := RAY_AABB_2.io.nodeIdx_0
        Stack_manage_2.io.ray_id_push                := RAY_AABB_2.io.rayid_out
    }.otherwise{
        Stack_manage_2.io.push                                 := false.B
        Stack_manage_2.io.push_en                         := false.B
        Stack_manage_2.io.node_id_push_in       := 0.S
        Stack_manage_2.io.ray_id_push                 := 0.U
    }
//---------------------------------------------------------
    //这个是叶子节点的仲裁
    when(RAY_AABB.io.leaf&&RAY_AABB.io.valid_out){
        Arbitration_3.io.node_id_3_4        := complement(RAY_AABB.io.nodeIdx_2)
        Arbitration_3.io.ray_id_3_4            := RAY_AABB.io.rayid_out
        Arbitration_3.io.hit_3_4                   := RAY_AABB.io.hitT_out
        Arbitration_3.io.valid_3_4               := true.B 
    }.otherwise{
        Arbitration_3.io.node_id_3_4        := 0.S
        Arbitration_3.io.ray_id_3_4            := 0.U
        Arbitration_3.io.hit_3_4                   := 0.U
        Arbitration_3.io.valid_3_4               := false.B
    }
    when(RAY_AABB_2.io.leaf&&RAY_AABB_2.io.valid_out){
        Arbitration_3_2.io.node_id_3_4        := complement(RAY_AABB_2.io.nodeIdx_2)
        Arbitration_3_2.io.ray_id_3_4            := RAY_AABB_2.io.rayid_out
        Arbitration_3_2.io.hit_3_4                   := RAY_AABB_2.io.hitT_out
        Arbitration_3_2.io.valid_3_4               := true.B 
    }.otherwise{
        Arbitration_3_2.io.node_id_3_4        := 0.S
        Arbitration_3_2.io.ray_id_3_4            := 0.U
        Arbitration_3_2.io.hit_3_4                   := 0.U
        Arbitration_3_2.io.valid_3_4               := false.B
    }
    
//------------------------------------------------------
    //这里是栈的输出节点，有可能是叶子节点也可能是内部节点
    when(positive(Stack_manage.io.node_id_out)===1.U&&Stack_manage.io.pop_valid){//这个说明是内部节点，进入仲裁1
        Arbitration_1.io.node_id_0            := Stack_manage.io.node_id_out
        Arbitration_1.io.ray_id_0                := Stack_manage.io.ray_id_out
        Arbitration_1.io.hit_0                       := Stack_manage.io.hitT_out
        Arbitration_1.io.valid_0                   := true.B
    }.elsewhen(positive(Stack_manage.io.node_id_out)===0.U&&Stack_manage.io.pop_valid){
        Arbitration_3.io.node_id_3_3       := complement(Stack_manage.io.node_id_out)
        Arbitration_3.io.ray_id_3_3           := Stack_manage.io.ray_id_out
        Arbitration_3.io.hit_3_3                   := Stack_manage.io.hitT_out
        Arbitration_3.io.valid_3_3               := true.B
    }.otherwise{
        Arbitration_1.io.node_id_0            := 0.S
        Arbitration_1.io.ray_id_0                := 0.U
        Arbitration_1.io.hit_0                       := 0.U
        Arbitration_1.io.valid_0                   := false.B
        Arbitration_3.io.node_id_3_3       := 0.S
        Arbitration_3.io.ray_id_3_3           := 0.U
        Arbitration_3.io.hit_3_3                   := 0.U
        Arbitration_3.io.valid_3_3               := false.B
    }
    when(positive(Stack_manage_2.io.node_id_out)===1.U&&Stack_manage_2.io.pop_valid){//这个说明是内部节点，进入仲裁1
        Arbitration_1_2.io.node_id_0            := Stack_manage_2.io.node_id_out
        Arbitration_1_2.io.ray_id_0                := Stack_manage_2.io.ray_id_out
        Arbitration_1_2.io.hit_0                       := Stack_manage_2.io.hitT_out
        Arbitration_1_2.io.valid_0                   := true.B
    }.elsewhen(positive(Stack_manage_2.io.node_id_out)===0.U&&Stack_manage_2.io.pop_valid){
        Arbitration_3_2.io.node_id_3_3       := complement(Stack_manage_2.io.node_id_out)
        Arbitration_3_2.io.ray_id_3_3           := Stack_manage_2.io.ray_id_out
        Arbitration_3_2.io.hit_3_3                   := Stack_manage_2.io.hitT_out
        Arbitration_3_2.io.valid_3_3               := true.B
    }.otherwise{
        Arbitration_1_2.io.node_id_0            := 0.S
        Arbitration_1_2.io.ray_id_0                := 0.U
        Arbitration_1_2.io.hit_0                       := 0.U
        Arbitration_1_2.io.valid_0                   := false.B
        Arbitration_3_2.io.node_id_3_3       := 0.S
        Arbitration_3_2.io.ray_id_3_3           := 0.U
        Arbitration_3_2.io.hit_3_3                   := 0.U
        Arbitration_3_2.io.valid_3_3               := false.B
    }
//------------------------------------------------------
    when(Arbitration_3.io.valid_out){
        Arbitration_4.io.node_id_4_0      := Arbitration_3.io.node_id_out
        Arbitration_4.io.ray_id_4_0          := Arbitration_3.io.ray_id_out
        Arbitration_4.io.hit_4_0                 := Arbitration_3.io.hit_out
        Arbitration_4.io.valid_4_0            := true.B            
    }.otherwise{
        Arbitration_4.io.node_id_4_0      := 0.S
        Arbitration_4.io.ray_id_4_0          := 0.U
        Arbitration_4.io.hit_4_0                 := 0.U
        Arbitration_4.io.valid_4_0             := false.B             
    }

    when(Arbitration_3_2.io.valid_out){
        Arbitration_4.io.node_id_4_1      := Arbitration_3_2.io.node_id_out
        Arbitration_4.io.ray_id_4_1          := Arbitration_3_2.io.ray_id_out
        Arbitration_4.io.hit_4_1                 := Arbitration_3_2.io.hit_out
        Arbitration_4.io.valid_4_1            := true.B            
    }.otherwise{
        Arbitration_4.io.node_id_4_1      := 0.S
        Arbitration_4.io.ray_id_4_1          := 0.U
        Arbitration_4.io.hit_4_1                 := 0.U
        Arbitration_4.io.valid_4_1            :=  false.B          
    }
//------------------------------------------------------    
  when(Triangle.io.pop_1&&Triangle.io.RAY_AABB_1_out_IST1){//pop_1对应的是仲裁里的2
        Arbitration_3.io.node_id_3_2      := Triangle.io.node_id_out_1+3.S
        Arbitration_3.io.ray_id_3_2          := Triangle.io.ray_id_ist1
        Arbitration_3.io.hit_3_2                 := Triangle.io.hiT_out_1
        Arbitration_3.io.valid_3_2            := true.B            
    }.otherwise{
        Arbitration_3.io.node_id_3_2      := 0.S
        Arbitration_3.io.ray_id_3_2          := 0.U
        Arbitration_3.io.hit_3_2                 := 0.U
        Arbitration_3.io.valid_3_2            := false.B  
    }

    when(Triangle.io.pop_1&&Triangle.io.RAY_AABB_2_out_IST1){//pop_1对应的是仲裁里的2
        Arbitration_3_2.io.node_id_3_2      := Triangle.io.node_id_out_1+3.S
        Arbitration_3_2.io.ray_id_3_2          := Triangle.io.ray_id_ist1
        Arbitration_3_2.io.hit_3_2                 := Triangle.io.hiT_out_1
        Arbitration_3_2.io.valid_3_2            := true.B            
    }.otherwise{
        Arbitration_3_2.io.node_id_3_2      := 0.S
        Arbitration_3_2.io.ray_id_3_2          := 0.U
        Arbitration_3_2.io.hit_3_2                 := 0.U
        Arbitration_3_2.io.valid_3_2            := false.B  
    }
//------------------------------------------------------

    when(Triangle.io.pop_2&&Triangle.io.RAY_AABB_1_out_IST2){//pop_2对应的是仲裁里的1
        Arbitration_3.io.node_id_3_1      := Triangle.io.node_id_out_2+3.S
        Arbitration_3.io.ray_id_3_1          := Triangle.io.ray_id_ist2
        Arbitration_3.io.hit_3_1                 := Triangle.io.hiT_out_2
        Arbitration_3.io.valid_3_1             := true.B  
     }.otherwise{
        Arbitration_3.io.node_id_3_1      := 0.S
        Arbitration_3.io.ray_id_3_1          := 0.U
        Arbitration_3.io.hit_3_1                 := 0.U
        Arbitration_3.io.valid_3_1             := false.B  
     }
    when(Triangle.io.pop_2&&Triangle.io.RAY_AABB_2_out_IST2){//pop_2对应的是仲裁里的1
        Arbitration_3_2.io.node_id_3_1      := Triangle.io.node_id_out_2+3.S
        Arbitration_3_2.io.ray_id_3_1          := Triangle.io.ray_id_ist2
        Arbitration_3_2.io.hit_3_1                 := Triangle.io.hiT_out_2
        Arbitration_3_2.io.valid_3_1             := true.B  
     }.otherwise{
        Arbitration_3_2.io.node_id_3_1      := 0.S
        Arbitration_3_2.io.ray_id_3_1          := 0.U
        Arbitration_3_2.io.hit_3_1                 := 0.U
        Arbitration_3_2.io.valid_3_1             := false.B  
     }
//------------------------------------------------------

     when(Triangle.io.pop_3&&Triangle.io.RAY_AABB_1_out_IST3){//pop_3对应的是仲裁里的0
        Arbitration_3.io.node_id_3_0      := Triangle.io.node_id_out_3+3.S
        Arbitration_3.io.ray_id_3_0          := Triangle.io.ray_id_ist3
        Arbitration_3.io.hit_3_0                 := Triangle.io.hiT_out_3
        Arbitration_3.io.valid_3_0             := true.B    
     }.otherwise{
        Arbitration_3.io.node_id_3_0      := 0.S
        Arbitration_3.io.ray_id_3_0          := 0.U
        Arbitration_3.io.hit_3_0                 := 0.U
        Arbitration_3.io.valid_3_0             := false.B 
     }
    when(Triangle.io.pop_3&&Triangle.io.RAY_AABB_2_out_IST3){//pop_3对应的是仲裁里的0
        Arbitration_3_2.io.node_id_3_0      := Triangle.io.node_id_out_3+3.S
        Arbitration_3_2.io.ray_id_3_0          := Triangle.io.ray_id_ist3
        Arbitration_3_2.io.hit_3_0                 := Triangle.io.hiT_out_3
        Arbitration_3_2.io.valid_3_0             := true.B    
     }.otherwise{
        Arbitration_3_2.io.node_id_3_0      := 0.S
        Arbitration_3_2.io.ray_id_3_0          := 0.U
        Arbitration_3_2.io.hit_3_0                 := 0.U
        Arbitration_3_2.io.valid_3_0             := false.B 
     }

//------------------------------------------------------

    when(Triangle.io.break_1&&Triangle.io.RAY_AABB_1_out_IST1){//pop_1对应的是仲裁里的2
        // Arbitration_2.io.node_id_3_2      := Triangle.io.node_id_out_1+3.S
        Arbitration_2.io.ray_id_2_2          := Triangle.io.ray_id_ist1
        Arbitration_2.io.hit_2_2                 := Triangle.io.hiT_out_1
        Arbitration_2.io.valid_2_2            := true.B            
    }.otherwise{
        //  Arbitration_2.io.node_id_3_2      := 0.S
        Arbitration_2.io.ray_id_2_2          := 0.U
        Arbitration_2.io.hit_2_2                 := 0.U
        Arbitration_2.io.valid_2_2            := false.B  
    }

    when(Triangle.io.break_1&&Triangle.io.RAY_AABB_2_out_IST1){//pop_1对应的是仲裁里的2
        // Arbitration_2.io.node_id_3_2      := Triangle.io.node_id_out_1+3.S
        Arbitration_2_2.io.ray_id_2_2          := Triangle.io.ray_id_ist1
        Arbitration_2_2.io.hit_2_2                 := Triangle.io.hiT_out_1
        Arbitration_2_2.io.valid_2_2            := true.B            
    }.otherwise{
        //  Arbitration_2.io.node_id_3_2      := 0.S
        Arbitration_2_2.io.ray_id_2_2          := 0.U
        Arbitration_2_2.io.hit_2_2                 := 0.U
        Arbitration_2_2.io.valid_2_2            := false.B  
    }
//------------------------------------------------------

    when(Triangle.io.break_2&&Triangle.io.RAY_AABB_1_out_IST2){//pop_2对应的是仲裁里的1
        // Arbitration_2.io.node_id_3_1      := Triangle.io.node_id_out_2+3.S
        Arbitration_2.io.ray_id_2_1          := Triangle.io.ray_id_ist2
        Arbitration_2.io.hit_2_1                 := Triangle.io.hiT_out_2
        Arbitration_2.io.valid_2_1             := true.B  
     }.otherwise{
        // Arbitration_2.io.node_id_3_1      := 0.S
        Arbitration_2.io.ray_id_2_1          := 0.U
        Arbitration_2.io.hit_2_1                 := 0.U
        Arbitration_2.io.valid_2_1             := false.B  
     }
    when(Triangle.io.break_2&&Triangle.io.RAY_AABB_2_out_IST2){//pop_2对应的是仲裁里的1
        // Arbitration_2.io.node_id_3_1      := Triangle.io.node_id_out_2+3.S
        Arbitration_2_2.io.ray_id_2_1          := Triangle.io.ray_id_ist2
        Arbitration_2_2.io.hit_2_1                 := Triangle.io.hiT_out_2
        Arbitration_2_2.io.valid_2_1             := true.B  
     }.otherwise{
        // Arbitration_2.io.node_id_3_1      := 0.S
        Arbitration_2_2.io.ray_id_2_1          := 0.U
        Arbitration_2_2.io.hit_2_1                 := 0.U
        Arbitration_2_2.io.valid_2_1             := false.B  
     }

//------------------------------------------------------
    when(Triangle.io.break_3&&Triangle.io.RAY_AABB_1_out_IST3){//pop_3对应的是仲裁里的0
        // Arbitration_2.io.node_id_2_0      := Triangle.io.node_id_out_3+3.S
        Arbitration_2.io.ray_id_2_0          := Triangle.io.ray_id_ist3
        Arbitration_2.io.hit_2_0                 := Triangle.io.hiT_out_3
        Arbitration_2.io.valid_2_0             := true.B    
     }.otherwise{
        // Arbitration_2.io.node_id_2_0      := 0.S
        Arbitration_2.io.ray_id_2_0          := 0.U
        Arbitration_2.io.hit_2_0                 := 0.U
        Arbitration_2.io.valid_2_0             := false.B 
     }

 when(Triangle.io.break_3&&Triangle.io.RAY_AABB_2_out_IST3){//pop_3对应的是仲裁里的0
        // Arbitration_2.io.node_id_2_0      := Triangle.io.node_id_out_3+3.S
        Arbitration_2_2.io.ray_id_2_0          := Triangle.io.ray_id_ist3
        Arbitration_2_2.io.hit_2_0                 := Triangle.io.hiT_out_3
        Arbitration_2_2.io.valid_2_0             := true.B    
     }.otherwise{
        // Arbitration_2.io.node_id_2_0      := 0.S
        Arbitration_2_2.io.ray_id_2_0          := 0.U
        Arbitration_2_2.io.hit_2_0                 := 0.U
        Arbitration_2_2.io.valid_2_0             := false.B 
     }
 
//------------------------------------------------------
 

    val leaf_memory_valid                       = RegInit(0.U(1.W))
    val hitT_temp                                         = RegInit(0.U(32.W))
    val ray_leaf_temp                                 = RegInit(0.U(32.W))
    val leaf_node_id_temp                      = RegInit(0.S(32.W))    
    val ray_aabb                                           = RegInit(0.U(1.W))
    val ray_aabb_2                                           = RegInit(0.U(1.W))
    val IST_1                                                    = RegInit(0.U(64.W))      
    when(Arbitration_4.io.valid_out){
        leaf_memory_valid                        := 1.U
        TRI_RAM_x.io.Triangle_id              := Arbitration_4.io.node_id_out
        TRI_RAM_y.io.Triangle_id              := Arbitration_4.io.node_id_out
        TRI_RAM_z.io.Triangle_id              := Arbitration_4.io.node_id_out
        TRI_RAM_w.io.Triangle_id             := Arbitration_4.io.node_id_out
        Ray_origx.io.Ray_id                          := Arbitration_4.io.ray_id_out
        Ray_origy.io.Ray_id                          := Arbitration_4.io.ray_id_out
        Ray_origz.io.Ray_id                          := Arbitration_4.io.ray_id_out
        Ray_dirx.io.Ray_id                            := Arbitration_4.io.ray_id_out
        Ray_diry.io.Ray_id                            := Arbitration_4.io.ray_id_out
        Ray_dirz.io.Ray_id                            := Arbitration_4.io.ray_id_out
        hitT_temp                                     := Arbitration_4.io.hit_out
        ray_leaf_temp                                 := Arbitration_4.io.ray_id_out
        leaf_node_id_temp                             := Arbitration_4.io.node_id_out
        ray_aabb                                               := Arbitration_4.io.RAY_AABB_out
        ray_aabb_2                                          := Arbitration_4.io.RAY_AABB_2_out        
    }.otherwise{
        leaf_memory_valid                        := 0.U
        hitT_temp                                           := 0.U
        ray_leaf_temp                                   := 0.U
        leaf_node_id_temp                        := 0.S
        ray_aabb                                               := 0.U
        ray_aabb_2                                          := 0.U
    }
    when(leaf_memory_valid===1.U){
        Triangle.io.To_IST0_enable       := true.B
        Triangle.io.nodeid_leaf               := leaf_node_id_temp
        Triangle.io.rayid_leaf                   := ray_leaf_temp
        Triangle.io.hiT_in                           := hitT_temp
        Triangle.io.v00_in.x                       := TRI_RAM_x.io.v00_out
        Triangle.io.break_in                      := Mux(TRI_RAM_x.io.valid === 2147483648L.U,true.B,false.B)
        Triangle.io.v00_in.y                       := TRI_RAM_y.io.v00_out
        Triangle.io.v00_in.z                       := TRI_RAM_z.io.v00_out
        Triangle.io.v00_in.w                      := TRI_RAM_w.io.v00_out
        Triangle.io.v11_in.x                       := TRI_RAM_x.io.v11_out
        Triangle.io.v11_in.y                       := TRI_RAM_y.io.v11_out
        Triangle.io.v11_in.z                       := TRI_RAM_z.io.v11_out
        Triangle.io.v11_in.w                      := TRI_RAM_w.io.v11_out
        Triangle.io.v22_in.x                       := TRI_RAM_x.io.v22_out
        Triangle.io.v22_in.y                       := TRI_RAM_y.io.v22_out
        Triangle.io.v22_in.z                       := TRI_RAM_z.io.v22_out
        Triangle.io.v22_in.w                      := TRI_RAM_w.io.v22_out
        Triangle.io.ray_o_in.x                   := Ray_origx.io.Ray_out
        Triangle.io.ray_o_in.y                   := Ray_origy.io.Ray_out
        Triangle.io.ray_o_in.z                   := Ray_origz.io.Ray_out
        Triangle.io.ray_d_in.x                   := Ray_dirx.io.Ray_out
        Triangle.io.ray_d_in.y                   := Ray_diry.io.Ray_out
        Triangle.io.ray_d_in.z                   := Ray_dirz.io.Ray_out
        Triangle.io.RAY_AABB_1              := ray_aabb
        Triangle.io.RAY_AABB_2              := ray_aabb_2
        IST_1                                                     := IST_1 +1.U
    }.otherwise{
        Triangle.io.To_IST0_enable       := false.B
        Triangle.io.nodeid_leaf               := 0.S
        Triangle.io.rayid_leaf                   := 0.U
        Triangle.io.hiT_in                           := 0.U
        Triangle.io.v00_in.x                       := 0.U
         Triangle.io.break_in                     := false.B
        Triangle.io.v00_in.y                       := 0.U
        Triangle.io.v00_in.z                       := 0.U
        Triangle.io.v00_in.w                      := 0.U
        Triangle.io.v11_in.x                       := 0.U
        Triangle.io.v11_in.y                       := 0.U
        Triangle.io.v11_in.z                       := 0.U
        Triangle.io.v11_in.w                      := 0.U
        Triangle.io.v22_in.x                       := 0.U
        Triangle.io.v22_in.y                       := 0.U
        Triangle.io.v22_in.z                       := 0.U
        Triangle.io.v22_in.w                      := 0.U
        Triangle.io.ray_o_in.x                   := 0.U
        Triangle.io.ray_o_in.y                   := 0.U
        Triangle.io.ray_o_in.z                   := 0.U
        Triangle.io.ray_d_in.x                   := 0.U
        Triangle.io.ray_d_in.y                   := 0.U
        Triangle.io.ray_d_in.z                   := 0.U
        Triangle.io.RAY_AABB_1              := 0.U
        Triangle.io.RAY_AABB_2              := 0.U
        IST_1                                                     := IST_1
    }
    io.IST_1_valid                                        := IST_1 

    Stack_manage.io.pop                      := Arbitration_2.io.valid_out
    Stack_manage.io.pop_en               := Arbitration_2.io.valid_out
    Stack_manage.io.ray_id_pop       := Arbitration_2.io.ray_id_out
    Stack_manage.io.hitT_in                := Arbitration_2.io.hit_out
    io.counter_fdiv                                     := Triangle.io.counter_fdiv
    Stack_manage_2.io.pop                      := Arbitration_2_2.io.valid_out
    Stack_manage_2.io.pop_en               := Arbitration_2_2.io.valid_out
    Stack_manage_2.io.ray_id_pop       := Arbitration_2_2.io.ray_id_out
    Stack_manage_2.io.hitT_in                := Arbitration_2_2.io.hit_out
    when(Triangle.io.hitT_en){
        io.hitT                                                      := Triangle.io.hiT_out_3
        io.hitIndex                                             := Triangle.io.hitIndex
        io.ray_id_triangle                              := Triangle.io.ray_id_ist3
    }.otherwise{
        io.hitT                                                      := 0.U
        io.hitIndex                                             := 0.S
        io.ray_id_triangle                              := 0.U
    }
}
object TOP_1 extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new TOP_1())
}
