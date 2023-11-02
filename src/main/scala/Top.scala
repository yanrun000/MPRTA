
package hardfloat
import Chisel._
// import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._


class  TOP extends Module{
    val io = IO(new Bundle{
        val hitT                        =  Output(UInt(32.W))
        val hitIndex               =  Output(SInt(32.W))
        val rtp_finish            =  Output(Bool())
        val ray_id_triangle = Output(UInt(32.W))
        val counter_fdiv      = Output(UInt(64.W))
        // val dispatch_count  = Output(UInt(8.W))
   
    })

    val  Ray_Dispatch             =  Module(new ray_dispatch(2000000))
    val  Ray_origx                    =  Module(new ray_memory(2073600))//32位
    val  Ray_origy                    =  Module(new ray_memory(2073600))//32位
    val  Ray_origz                    =  Module(new ray_memory(2073600))//32位
    // val  Ray_tmin                    =  Module(new ray_memory(8))//32位

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

    val  BVH_RAM_0_x           =  Module(new BVH_memory(105025))//n0xy
    val  BVH_RAM_0_y           =  Module(new BVH_memory(105025))
    val  BVH_RAM_0_z           =  Module(new BVH_memory(105025))
    val  BVH_RAM_0_w          =  Module(new BVH_memory(105025))

    val  BVH_RAM_1_x           =  Module(new BVH_memory(105025))//n1xy
    val  BVH_RAM_1_y           =  Module(new BVH_memory(105025))
    val  BVH_RAM_1_z           =  Module(new BVH_memory(105025))
    val  BVH_RAM_1_w          =  Module(new BVH_memory(105025))

    val  BVH_RAM_z_x           =  Module(new BVH_memory(105025))//nz
    val  BVH_RAM_z_y           =  Module(new BVH_memory(105025))
    val  BVH_RAM_z_z           =  Module(new BVH_memory(105025))
    val  BVH_RAM_z_w          =  Module(new BVH_memory(105025))

    val  BVH_RAM_tmp_x     =  Module(new BVH_memory_0(105025))//tmp    
    val  BVH_RAM_tmp_y     =  Module(new BVH_memory_0(105025))

    val  TRI_RAM_v00_x        =  Module(new BVH_memory(350950))//v00
    val  TRI_RAM_v00_y        =  Module(new BVH_memory(350950))
    val  TRI_RAM_v00_z        =  Module(new BVH_memory(350950))
    val  TRI_RAM_v00_w      =  Module(new BVH_memory(350950))

    val  TRI_RAM_v11_x        =  Module(new BVH_memory(350950))//v11
    val  TRI_RAM_v11_y        =  Module(new BVH_memory(350950))
    val  TRI_RAM_v11_z        =  Module(new BVH_memory(350950))
    val  TRI_RAM_v11_w      =  Module(new BVH_memory(350950))

    val  TRI_RAM_v22_x        =  Module(new BVH_memory(350950))//v22
    val  TRI_RAM_v22_y        =  Module(new BVH_memory(350950))
    val  TRI_RAM_v22_z        =  Module(new BVH_memory(350950))
    val  TRI_RAM_v22_w      =  Module(new BVH_memory(350950))

    // val  TRI_RAM_v00            =  Module(new BVH_memory(350950))
    // val  TRI_RAM_v11            =  Module(new BVH_memory(350950))
    // val  TRI_RAM_v22            =  Module(new BVH_memory(350950))
    val  RAY_AABB                   =  Module(new ray_AABB())//光线与BVH的相交测试
    val  Arbitration_1             =  Module(new Arbitration_1())//关于三种同时到达的仲裁1
    val  Arbitration_2             =  Module(new Arbitration_2())//关于两种弹栈请求的仲裁2
    val  Arbitration_3             =  Module(new Arbitration_3())//关于四种三角形部分内容的仲裁3
    val  Stack_manage          =  Module(new Stackmanage())//栈的管理
    val  Triangle                        =  Module(new Triangle()) //三角形处理阶段的流水线            
    val  Ray_BVH_fetch          = Module(new ray_bvh_fetch())

   //整体设计还差发射的设计
    //光线发射,这里还有一点问题，就是发射信号发射之后，会一直发射
    Ray_Dispatch.io.dispatch                        := Stack_manage.io.Dis_en
    // Ray_Dispatch.io.dispatch                            := Stack_manage.io.Dis_en
    when(Ray_Dispatch.io.ray_out===true.B){
        Arbitration_1.io.node_id_2                  := Ray_Dispatch.io.node_id
        Arbitration_1.io.ray_id_2                      := Ray_Dispatch.io.rayid_id
        Arbitration_1.io.valid_2                         := true.B
    }.otherwise{
        Arbitration_1.io.node_id_2                  := 0.S
        Arbitration_1.io.ray_id_2                      := 0.U
        Arbitration_1.io.valid_2                         := false.B 
    }
    //这个是整个系统完成计算的整体的输出
    io.rtp_finish                                       := Ray_Dispatch.io.ray_finish && Stack_manage.io.Finish
    // io.rtp_finish                                       := false.B

    when(RAY_AABB.io.back&&RAY_AABB.io.valid_out){
        Arbitration_1.io.node_id_1  := RAY_AABB.io.nodeIdx_1
        Arbitration_1.io.ray_id_1      := RAY_AABB.io.rayid_out
        Arbitration_1.io.valid_1         := RAY_AABB.io.back
    }.otherwise{
        Arbitration_1.io.node_id_1  := 0.S
        Arbitration_1.io.ray_id_1      := 0.U
        Arbitration_1.io.valid_1         := false.B
    }
    //到此把仲裁1实例化完毕
    when(Stack_manage.io.pop_valid){
        Arbitration_1.io.node_id_0  := Stack_manage.io.node_id_out
        Arbitration_1.io.ray_id_0      := Stack_manage.io.ray_id_out
        Arbitration_1.io.hit_0             := Stack_manage.io.hitT_out
        Arbitration_1.io.valid_0         := Stack_manage.io.pop_valid
    }.otherwise{
        Arbitration_1.io.node_id_0  := 0.S
        Arbitration_1.io.ray_id_0      := 0.U
        Arbitration_1.io.hit_0             := 0.U
        Arbitration_1.io.valid_0         := false.B
    }

    val memory_valid                        = RegInit(0.U(1.W)) //这个是读数据时的有效
    val hit_temp                                   = RegInit(0.U(32.W))//这个用来暂存hit的值得
    val ray_id_temp                            = RegInit(0.U(32.W))
    val hit_from_arb                           = RegInit(0.U(1.W))//这个是用来判断hit有无从仲裁里面出来
    when(Arbitration_1.io.valid_out&&Arbitration_1.io.hit_out =/=0.U){
        memory_valid                                   := 1.U
        // Ray_RAM_1.io.Ray_id                    := Arbitration_1.io.ray_id_out
        // Ray_RAM_2.io.Ray_id                    := Arbitration_1.io.ray_id_out
        Ray_idirx.io.Ray_id                         := Arbitration_1.io.ray_id_out
        Ray_idiry.io.Ray_id                         := Arbitration_1.io.ray_id_out
        Ray_idirz.io.Ray_id                         := Arbitration_1.io.ray_id_out
        Ray_oodx.io.Ray_id                        := Arbitration_1.io.ray_id_out
        Ray_oody.io.Ray_id                        := Arbitration_1.io.ray_id_out
        Ray_oodz.io.Ray_id                        := Arbitration_1.io.ray_id_out
        hit_temp                                              := Arbitration_1.io.hit_out
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
        ray_id_temp                                       := Arbitration_1.io.ray_id_out
        hit_from_arb                                      := 1.U                    
    }.elsewhen(Arbitration_1.io.valid_out&&Arbitration_1.io.hit_out ===0.U){
        memory_valid                                   := 1.U
        // Ray_RAM_1.io.Ray_id                    := Arbitration_1.io.ray_id_out
        // Ray_RAM_2.io.Ray_id                    := Arbitration_1.io.ray_id_out
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
        // Ray_RAM_1.io.Ray_id                    := Arbitration_1.io.ray_id_out
        // Ray_RAM_2.io.Ray_id                    := Arbitration_1.io.ray_id_out
        // Ray_RAM_1_After_1.io.Ray_id   := Arbitration_1.io.ray_id_out
        // Ray_RAM_1_After_2.io.Ray_id   := Arbitration_1.io.ray_id_out
        // Ray_RAM_hitT.io.Ray_id               := Arbitration_1.io.hit_out
        // BVH_RAM_1.io.BVH_id                   := Arbitration_1.io.node_id_out
        // BVH_RAM_2.io.BVH_id                   := Arbitration_1.io.node_id_out
        // BVH_RAM_3.io.BVH_id                   := Arbitration_1.io.node_id_out
        // BVH_RAM_4.io.BVH_id                   := Arbitration_1.io.node_id_out
        // ray_id_temp                                       := Arbitration_1.io.ray_id_out
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
        // RAY_AABB.io.ray_ood                        := Ray_RAM_After_2.io.Ray_out
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

        // RAY_AABB.io.bvh_n1xy                     :=  BVH_RAM_2.io.BVH_out
        // RAY_AABB.io.bvh_nz                          :=  BVH_RAM_3.io.BVH_out
        RAY_AABB.io.bvh_temp.x                 :=  BVH_RAM_tmp_x.io.BVH_out.asSInt//这个感觉不太对
        RAY_AABB.io.bvh_temp.y                 :=  BVH_RAM_tmp_y.io.BVH_out.asSInt
        RAY_AABB.io.rayid                               := ray_id_temp
        RAY_AABB.io.valid_en                        := true.B
    }.otherwise{
        RAY_AABB.io.ray_idir.x                      := 0.U
        RAY_AABB.io.ray_idir.y                      := 0.U
        RAY_AABB.io.ray_idir.z                      := 0.U
        RAY_AABB.io.ray_ood.x                     := 0.U
        RAY_AABB.io.ray_ood.y                     := 0.U
        RAY_AABB.io.ray_ood.z                     := 0.U
        // RAY_AABB.io.ray_ood                        := 0.U
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

        // RAY_AABB.io.bvh_n1xy                     :=  BVH_RAM_2.io.BVH_out
        // RAY_AABB.io.bvh_nz                          :=  BVH_RAM_3.io.BVH_out
        RAY_AABB.io.bvh_temp.x                 :=  0.S
        RAY_AABB.io.bvh_temp.y                 :=  0.S
        RAY_AABB.io.rayid                               := 0.U
        RAY_AABB.io.valid_en                        := false.B
    }

    when(RAY_AABB.io.pop&&RAY_AABB.io.valid_out){
    //    Arbitration_2.io.node_id_2_3         := RAY_AABB.io.nodeIdx_0//这个好像没意义，但是先保留把
       Arbitration_2.io.ray_id_2_1             := RAY_AABB.io.rayid_out
       Arbitration_2.io.hit_2_1                    := RAY_AABB.io.hitT_out
       Arbitration_2.io.valid_2_1                := 1.U
    }.otherwise{
        // Arbitration_2.io.node_id_2_3         := 0.S
       Arbitration_2.io.ray_id_2_1             := 0.U
       Arbitration_2.io.hit_2_1                    := 0.U
       Arbitration_2.io.valid_2_1                := 0.U
    }

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

    //这里是栈的输出节点，有可能是叶子节点也可能是内部节点
    when(positive(Stack_manage.io.node_id_out)===1.U&&Stack_manage.io.pop_valid){//这个说明是内部节点，进入仲裁1
        Arbitration_1.io.node_id_0            := Stack_manage.io.node_id_out
        Arbitration_1.io.ray_id_0                := Stack_manage.io.ray_id_out
        Arbitration_1.io.hit_0                       := Stack_manage.io.hitT_out
        Arbitration_1.io.valid_0                   := true.B
    }.elsewhen(positive(Stack_manage.io.node_id_out)===0.U&&Stack_manage.io.pop_valid){
        Arbitration_3.io.node_id_3_3       := Stack_manage.io.node_id_out
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

    when(Triangle.io.pop_1){//pop_1对应的是仲裁里的2
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
    when(Triangle.io.pop_2){//pop_2对应的是仲裁里的1
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

    when(Triangle.io.pop_3){//pop_3对应的是仲裁里的0
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


    val leaf_memory_valid                       = RegInit(0.U(1.W))
    val hitT_temp                                         = RegInit(0.U(32.W))
    val ray_leaf_temp                                 = RegInit(0.U(32.W))
    val leaf_node_id_temp                      = RegInit(0.S(32.W))
    when(Arbitration_3.io.valid_out){
        leaf_memory_valid                        := 1.U
        TRI_RAM_v00_x.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v00_y.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v00_z.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v00_w.io.BVH_id         := Arbitration_3.io.node_id_out/3.S

        TRI_RAM_v11_x.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v11_y.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v11_z.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v11_w.io.BVH_id         := Arbitration_3.io.node_id_out/3.S

        TRI_RAM_v22_x.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v22_y.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v22_z.io.BVH_id          := Arbitration_3.io.node_id_out/3.S
        TRI_RAM_v22_w.io.BVH_id         := Arbitration_3.io.node_id_out/3.S
        // TRI_RAM_v11.io.BVH_id               := Arbitration_3.io.node_id_out
        // TRI_RAM_v22.io.BVH_id               := Arbitration_3.io.node_id_out
        Ray_origx.io.Ray_id                        := Arbitration_3.io.ray_id_out
        Ray_origy.io.Ray_id                        := Arbitration_3.io.ray_id_out
        Ray_origz.io.Ray_id                        := Arbitration_3.io.ray_id_out

        Ray_dirx.io.Ray_id                          := Arbitration_3.io.ray_id_out
        Ray_diry.io.Ray_id                          := Arbitration_3.io.ray_id_out
        Ray_dirz.io.Ray_id                          := Arbitration_3.io.ray_id_out
        
        // Ray_RAM_2.io.Ray_id                    := Arbitration_3.io.ray_id_out
        hitT_temp                                           := Arbitration_3.io.hit_out
        ray_leaf_temp                                   := Arbitration_3.io.ray_id_out
        leaf_node_id_temp                        := Arbitration_3.io.node_id_out
    }.otherwise{
        leaf_memory_valid                        := 0.U
        hitT_temp                                           := 0.U
        ray_leaf_temp                                   := 0.U
        leaf_node_id_temp                        := 0.S
    }

    when(leaf_memory_valid===1.U&&(TRI_RAM_v00_x.io.BVH_out=/=2147483648L.U)){
        Triangle.io.To_IST0_enable       := true.B
        Triangle.io.nodeid_leaf               := leaf_node_id_temp
        Triangle.io.rayid_leaf                   := ray_leaf_temp
        Triangle.io.hiT_in                           := hitT_temp
        Triangle.io.v00_in.x                       := TRI_RAM_v00_x.io.BVH_out
        Triangle.io.v00_in.y                       := TRI_RAM_v00_y.io.BVH_out
        Triangle.io.v00_in.z                       := TRI_RAM_v00_z.io.BVH_out
        Triangle.io.v00_in.w                      := TRI_RAM_v00_w.io.BVH_out
        Triangle.io.v11_in.x                       := TRI_RAM_v11_x.io.BVH_out
        Triangle.io.v11_in.y                       := TRI_RAM_v11_y.io.BVH_out
        Triangle.io.v11_in.z                       := TRI_RAM_v11_z.io.BVH_out
        Triangle.io.v11_in.w                      := TRI_RAM_v11_w.io.BVH_out
        Triangle.io.v22_in.x                       := TRI_RAM_v22_x.io.BVH_out
        Triangle.io.v22_in.y                       := TRI_RAM_v22_y.io.BVH_out
        Triangle.io.v22_in.z                       := TRI_RAM_v22_z.io.BVH_out
        Triangle.io.v22_in.w                      := TRI_RAM_v22_w.io.BVH_out
        Triangle.io.ray_o_in.x                   := Ray_origx.io.Ray_out
        Triangle.io.ray_o_in.y                   := Ray_origy.io.Ray_out
        Triangle.io.ray_o_in.z                   := Ray_origz.io.Ray_out
        Triangle.io.ray_d_in.x                   := Ray_dirx.io.Ray_out
        Triangle.io.ray_d_in.y                   := Ray_diry.io.Ray_out
        Triangle.io.ray_d_in.z                   := Ray_dirz.io.Ray_out
        Arbitration_2.io.valid_2_0          := false.B
        Arbitration_2.io.ray_id_2_0       := 0.U
        Arbitration_2.io.hit_2_0              := 0.U
    }.elsewhen(leaf_memory_valid===1.U&&(TRI_RAM_v00_x.io.BVH_out===2147483648L.U)){
        // Stack_manage.io.pop                  := true.B
        // Stack_manage.io.pop_en          := true.B
        Arbitration_2.io.valid_2_0          := true.B
        Arbitration_2.io.ray_id_2_0       := ray_leaf_temp
        Arbitration_2.io.hit_2_0              := hitT_temp
        Triangle.io.To_IST0_enable       := false.B
        Triangle.io.nodeid_leaf               := 0.S
        Triangle.io.rayid_leaf                   := 0.U
        Triangle.io.hiT_in                           := 0.U
        Triangle.io.v00_in.x                       := 0.U
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
    }.otherwise{
        Arbitration_2.io.valid_2_0          := false.B
        Arbitration_2.io.ray_id_2_0       := 0.U
        Arbitration_2.io.hit_2_0              := 0.U
        Triangle.io.To_IST0_enable       := false.B
        Triangle.io.nodeid_leaf               := 0.S
        Triangle.io.rayid_leaf                   := 0.U
        Triangle.io.hiT_in                           := 0.U
        Triangle.io.v00_in.x                       := 0.U
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
    }

    // Arbitration_2.io.valid_2_0              := Triangle.io.pop_3
    // Arbitration_2.io.ray_id_2_0           := Triangle.io.ray_id_ist3
    // Arbitration_2.io.hit_2_0                  :=  Triangle.io.hiT_out_3

    // Arbitration_2.io.valid_2_1              := Triangle.io.pop_2
    // Arbitration_2.io.ray_id_2_1           := Triangle.io.ray_id_ist2
    // Arbitration_2.io.hit_2_1                  :=  Triangle.io.hiT_out_2

    // Arbitration_2.io.valid_2_2              := Triangle.io.pop_1
    // Arbitration_2.io.ray_id_2_2           := Triangle.io.ray_id_ist1
    // Arbitration_2.io.hit_2_2                  :=  Triangle.io.hiT_out_1


    Arbitration_2.io.valid_2_1              := RAY_AABB.io.pop && RAY_AABB.io.valid_out 
    Arbitration_2.io.ray_id_2_1           := RAY_AABB.io.rayid_out
    Arbitration_2.io.hit_2_1                  := RAY_AABB.io.hitT_out

    Stack_manage.io.pop                      := Arbitration_2.io.valid_out
    Stack_manage.io.pop_en               := Arbitration_2.io.valid_out
    Stack_manage.io.ray_id_pop       := Arbitration_2.io.ray_id_out
    Stack_manage.io.hitT_in                := Arbitration_2.io.hit_out
    // Stack_manage.io.push                    := RAY_AABB.io.push
    // Stack_manage.io.push_en            := RAY_AABB.io.push
    io.counter_fdiv                                     := Triangle.io.counter_fdiv
    // io.dispatch_count                               := Stack_manage.io.dis_count
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
object TOP extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new TOP())
}
