package hardfloat
import Chisel._
// import chisel3._
import chisel3.util._
import chisel3 . iotesters ._
import org.scalatest._
// 感觉第一级流水线不是很有必要，可以在后续考虑删除,这个的输入需要调整
class ray_AABB extends Module{
    val io = IO(new  Bundle{
        // val BVH_node            = Input(Bits(480.W))
        //取光线的数据
        // val ray_idir                  = Input(Bits(96.W))
        // val ray_ood                 = Input(Bits(96.W))
        // val ray_idirx                 = Input(Bits(32.W))
        // val ray_idiry                 = Input(Bits(32.W))
        // val ray_idirz                 = Input(Bits(32.W))

        val ray_idir                    = new ray().asInput
        val ray_ood                   = new ray().asInput

        // val ray_oodx                 = Input(Bits(32.W))
        // val ray_oody                 = Input(Bits(32.W))
        // val ray_oodz                 = Input(Bits(32.W))

        val ray_tmin                = Input(Bits(32.W))
        val ray_hitT                 = Input(Bits(32.W))

        // val bvh_n0xy_x         = Input(Bits(32.W))
        // val bvh_n0xy_y         = Input(Bits(32.W))
        // val bvh_n0xy_z         = Input(Bits(32.W))
        // val bvh_n0xy_w       = Input(Bits(32.W))

        val bvh_n0xy             = new Float().asInput

        val bvh_n1xy             = new Float().asInput
        // val bvh_n1xy_x         = Input(Bits(32.W))
        // val bvh_n1xy_y         = Input(Bits(32.W))
        // val bvh_n1xy_z         = Input(Bits(32.W))
        // val bvh_n1xy_w       = Input(Bits(32.W))

        val bvh_nz                  = new Float().asInput
        // val bvh_nz_x              = Input(Bits(32.W))
        // val bvh_nz_y              = Input(Bits(32.W))
        // val bvh_nz_z              = Input(Bits(32.W))
        // val bvh_nz_w            = Input(Bits(32.W))
        val bvh_temp           = new Float_0().asInput
        // val bvh_temp_0       = Input(Bits(32.W))
        // val bvh_temp_1       = Input(Bits(32.W))

        // val bvh_n0xy              = Input(Bits(128.W))
        // val bvh_n1xy              = Input(Bits(128.W))
        // val bvh_nz                   = Input(Bits(128.W))
        // val bvh_temp             = Input(Bits(64.W))
        // val iray                          = Input(Bits(192.W))
        // val tmin                        = Input(Bits(32.W))
        // val hitT                         = Input(Bits(32.W))
        val rayid                       = Input(Bits(32.W))//这个是光线的id,用于指示当前正在处理的光线,在处理过程中要一直保留
        val valid_en                = Input(Bool())
        // val nodeid                   = Input (Bits(32.W))//这个是输入的节点的地址，后续
        val rayid_out             = Output(UInt(32.W))
        val nodeIdx_0           = Output(SInt(32.W))//这个地址表示进栈的地址
        val nodeIdx_1           = Output(SInt(32.W))//这个地址表示内部的地址
        val nodeIdx_2           = Output(SInt(32.W))//这个地址表示叶子的地址
        // val triCount                = Output(Bits(8.W))
        val push                       = Output(Bool())
        val pop                         = Output(Bool())
        val leaf                          = Output(Bool())
        val back                        = Output(Bool())//这个指示的是返回AABB阶段再进行计算
        val hitT_out                = Output(Bits(32.W))//传递出来的hitT数据
        val valid_out              = Output(Bool())
    })
    //取数译码step(1)。这个不需要的，可以直接从输入的数据来取
    // val n0xy_x               = RegInit(0.U(32.W))
    // val n0xy_y               = RegInit(0.U(32.W))
    // val n0xy_z               = RegInit(0.U(32.W))
    // val n0xy_w              = RegInit(0.U(32.W))

    // val n1xy_x               = RegInit(0.U(32.W))
    // val n1xy_y               = RegInit(0.U(32.W))
    // val n1xy_z               = RegInit(0.U(32.W))
    // val n1xy_w             = RegInit(0.U(32.W))

    // val nz_x                   = RegInit(0.U(32.W))
    // val nz_y                   = RegInit(0.U(32.W))
    // val nz_z                   = RegInit(0.U(32.W))
    // val nz_w                  = RegInit(0.U(32.W))

    // val cidx_0               = RegInit(0.U(32.W))
    // val cidx_1               = RegInit(0.U(32.W))

    // val idirx                     = RegInit(0.U(32.W))
    // val idiry                     = RegInit(0.U(32.W))
    // val idirz                     = RegInit(0.U(32.W))

    // val oodx                     = RegInit(0.U(32.W))
    // val oody                     = RegInit(0.U(32.W))
    // val oodz                     = RegInit(0.U(32.W))


    // val tmin_1                = RegInit(0.U(32.W))
    // val hitT_1                  = RegInit(0.U(32.W))
    // val rayid_1                = RegInit(0.U(32.W))
    // val nodeid_1            = RegInit(0.U(32.W))
    // when(io.valid_en){
    val traverseChild0 = RegInit(0.U(1.W))
    val traverseChild1 = RegInit(0.U(1.W))
    //解码BVH
    // n0xy_x                      := io.bvh_n0xy(31,0)
    // n0xy_y                      := io.bvh_n0xy(63,32)
    // n0xy_z                      := io.bvh_n0xy(95,64)
    // n0xy_w                     := io.bvh_n0xy(127,96)

    // n1xy_x                      := io.bvh_n1xy(31,0)
    // n1xy_y                      := io.bvh_n1xy(63,32)
    // n1xy_z                      := io.bvh_n1xy(95,64)
    // n1xy_w                     := io.bvh_n1xy(127,96)

    // nz_x                            := io.bvh_nz(31,0)
    // nz_y                            := io.bvh_nz(63,32)
    // nz_z                            := io.bvh_nz(95,64)
    // nz_w                           := io.bvh_nz(127,96)

    // cidx_0                         := io.bvh_temp(31,0)
    // cidx_1                         := io.bvh_temp(63,32)
    //解码光线数据
    // idirx                             := io.ray_idir(31,0)
    // idiry                             := io.ray_idir(63,32)
    // idirz                             := io.ray_idir(95,64)

    // oodx                            := io.ray_ood(31,0)
    // oody                            := io.ray_ood(63,32)
    // oodz                            := io.ray_ood(95,64)
    //第一级的时候进行传递数据

    // nodeid_1                    := io.nodeid
    //浮点乘加运算step(1)  我可以直接从输入的数据来进行运算
    val c0lox           = RegInit(0.U(32.W))
    val c0hix           = RegInit(0.U(32.W))
    val c0loy           = RegInit(0.U(32.W))
    val c0hiy          = RegInit(0.U(32.W))
    val c0loz           = RegInit(0.U(32.W))
    val c0hiz           = RegInit(0.U(32.W))

    val c1lox           = RegInit(0.U(32.W))
    val c1hix           = RegInit(0.U(32.W))
    val c1loy           = RegInit(0.U(32.W))
    val c1hiy           = RegInit(0.U(32.W))
    val c1loz           = RegInit(0.U(32.W))
    val c1hiz           = RegInit(0.U(32.W))
    
    val rayid_1       = RegInit(0.U(32.W))
    // val nodeid_2   = RegInit(0.U(32.W))
    val hitT_1         = RegInit(0.U(32.W))
    val tmin_1       = RegInit(0.U(32.W))
    val valid_1       = RegInit(0.U(32.W))
    // val nodeid_2  = RegInit(0.U(32.W))
    //传递BVH数据    
    val cidx_0_1                   = RegInit(0.S(32.W))
    val cidx_1_1                   = RegInit(0.S(32.W))
    rayid_1                             := io.rayid
    // tmin_1                              := io.ray_tmin
    tmin_1                              := 0.U
    hitT_1                                := io.ray_hitT
    valid_1                              := io.valid_en                  
    // val tri_count_0_2        = RegInit(0.U(8.W))
    // val tri_count_1_2        = RegInit(0.U(8.W))
    // val child_mask_0_2   =RegInit(0.U(8.W))
    // val child_mask_1_2   =RegInit(0.U(8.W))
    // val traverseChild1_0  = Bool()
    cidx_0_1                               := io.bvh_temp.x
    cidx_1_1                               := io.bvh_temp.y
    // tri_count_0_2               := tri_count_0
    // tri_count_1_2               := tri_count_1
    // child_mask_0_2          := child_mask_0
    // child_mask_1_2          := child_mask_1
    //传递光线数据
    // val idirx_2                     = RegInit(0.U(32.W))
    // val idiry_2                     = RegInit(0.U(32.W))
    // val idirz_2                     = RegInit(0.U(32.W))

    // val oodx_2                     = RegInit(0.U(32.W))
    // val oody_2                     = RegInit(0.U(32.W))
    // val oodz_2                     = RegInit(0.U(32.W))



    val FADD_MUL_1      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_1.io.a := io.bvh_n0xy.x
    FADD_MUL_1.io.b := io.ray_idir.x
    FADD_MUL_1.io.c := offsign(io.ray_ood.x)
    FADD_MUL_1.io.roundingMode := 0.U
    FADD_MUL_1.io.detectTininess             := 1.U
    FADD_MUL_1.io.expected.out                := 0.U
    FADD_MUL_1.io.expected.exceptionFlags := 0.U 
    c0lox                           :=  fNFromRecFN(8,24,FADD_MUL_1.io.actual.out)

    val FADD_MUL_2      = Module(new ValExec_MulAddRecF32)
    FADD_MUL_2.io.a := io.bvh_n0xy.y
    FADD_MUL_2.io.b := io.ray_idir.x
    FADD_MUL_2.io.c := offsign(io.ray_ood.x)
    FADD_MUL_2.io.roundingMode := 0.U
    FADD_MUL_2.io.detectTininess             := 1.U
    FADD_MUL_2.io.expected.out                := 0.U
    FADD_MUL_2.io.expected.exceptionFlags := 0.U 
    c0hix                           :=  fNFromRecFN(8,24,FADD_MUL_2.io.actual.out)

    val FADD_MUL_3      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_3.io.a := io.bvh_n0xy.z
    FADD_MUL_3.io.b := io.ray_idir.y
    FADD_MUL_3.io.c := offsign(io.ray_ood.y)
    FADD_MUL_3.io.roundingMode := 0.U
    FADD_MUL_3.io.detectTininess             := 1.U
    FADD_MUL_3.io.expected.out                := 0.U
    FADD_MUL_3.io.expected.exceptionFlags := 0.U 
    c0loy                           :=  fNFromRecFN(8,24,FADD_MUL_3.io.actual.out)

    val FADD_MUL_4      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_4.io.a := io.bvh_n0xy.w
    FADD_MUL_4.io.b := io.ray_idir.y
    FADD_MUL_4.io.c := offsign(io.ray_ood.y)
    FADD_MUL_4.io.roundingMode := 0.U
    FADD_MUL_4.io.detectTininess             := 1.U
    FADD_MUL_4.io.expected.out                := 0.U
    FADD_MUL_4.io.expected.exceptionFlags := 0.U 
    c0hiy                           :=  fNFromRecFN(8,24,FADD_MUL_4.io.actual.out)
    
    val FADD_MUL_5      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_5.io.a := io.bvh_nz.x
    FADD_MUL_5.io.b := io.ray_idir.z
    FADD_MUL_5.io.c := offsign(io.ray_ood.z)
    FADD_MUL_5.io.roundingMode := 0.U
    FADD_MUL_5.io.detectTininess             := 1.U
    FADD_MUL_5.io.expected.out                := 0.U
    FADD_MUL_5.io.expected.exceptionFlags := 0.U 
    c0loz                           :=  fNFromRecFN(8,24,FADD_MUL_5.io.actual.out)

    val FADD_MUL_6      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_6.io.a := io.bvh_nz.y
    FADD_MUL_6.io.b := io.ray_idir.z
    FADD_MUL_6.io.c := offsign(io.ray_ood.z)
    FADD_MUL_6.io.roundingMode := 0.U
    FADD_MUL_6.io.detectTininess             := 1.U
    FADD_MUL_6.io.expected.out                := 0.U
    FADD_MUL_6.io.expected.exceptionFlags := 0.U 
    c0hiz                           :=  fNFromRecFN(8,24,FADD_MUL_6.io.actual.out)

    val FADD_MUL_7      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_7.io.a := io.bvh_n1xy.x
    FADD_MUL_7.io.b := io.ray_idir.x
    FADD_MUL_7.io.c := offsign(io.ray_ood.x)
    FADD_MUL_7.io.roundingMode := 0.U
    FADD_MUL_7.io.detectTininess             := 1.U
    FADD_MUL_7.io.expected.out                := 0.U
    FADD_MUL_7.io.expected.exceptionFlags := 0.U 
    c1lox                           :=  fNFromRecFN(8,24,FADD_MUL_7.io.actual.out)

    val FADD_MUL_8      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_8.io.a := io.bvh_n1xy.y
    FADD_MUL_8.io.b := io.ray_idir.x
    FADD_MUL_8.io.c := offsign(io.ray_ood.x)
    FADD_MUL_8.io.roundingMode := 0.U
    FADD_MUL_8.io.detectTininess             := 1.U
    FADD_MUL_8.io.expected.out                := 0.U
    FADD_MUL_8.io.expected.exceptionFlags := 0.U 
    c1hix                           :=  fNFromRecFN(8,24,FADD_MUL_8.io.actual.out)

    val FADD_MUL_9      = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_9.io.a := io.bvh_n1xy.z
    FADD_MUL_9.io.b := io.ray_idir.y
    FADD_MUL_9.io.c := offsign(io.ray_ood.y)
    FADD_MUL_9.io.roundingMode := 0.U
    FADD_MUL_9.io.detectTininess             := 1.U
    FADD_MUL_9.io.expected.out                := 0.U
    FADD_MUL_9.io.expected.exceptionFlags := 0.U 
    c1loy                           :=  fNFromRecFN(8,24,FADD_MUL_9.io.actual.out)

    val FADD_MUL_10     = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_10.io.a := io.bvh_n1xy.w
    FADD_MUL_10.io.b := io.ray_idir.y
    FADD_MUL_10.io.c := offsign(io.ray_ood.y)
    FADD_MUL_10.io.roundingMode := 0.U
    FADD_MUL_10.io.detectTininess             := 1.U
    FADD_MUL_10.io.expected.out                := 0.U
    FADD_MUL_10.io.expected.exceptionFlags := 0.U 
    c1hiy                           :=  fNFromRecFN(8,24,FADD_MUL_10.io.actual.out)
    
    val FADD_MUL_11     = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_11.io.a := io.bvh_nz.z
    FADD_MUL_11.io.b := io.ray_idir.z
    FADD_MUL_11.io.c := offsign(io.ray_ood.z)
    FADD_MUL_11.io.roundingMode := 0.U
    FADD_MUL_11.io.detectTininess             := 1.U
    FADD_MUL_11.io.expected.out                := 0.U
    FADD_MUL_11.io.expected.exceptionFlags := 0.U 
    c1loz                           :=  fNFromRecFN(8,24,FADD_MUL_11.io.actual.out)

    val FADD_MUL_12     = Module(new ValExec_MulAddRecF32 )
    FADD_MUL_12.io.a := io.bvh_nz.w
    FADD_MUL_12.io.b := io.ray_idir.z
    FADD_MUL_12.io.c := offsign(io.ray_ood.z)
    FADD_MUL_12.io.roundingMode := 0.U
    FADD_MUL_12.io.detectTininess             := 1.U
    FADD_MUL_12.io.expected.out                := 0.U
    FADD_MUL_12.io.expected.exceptionFlags := 0.U 
    c1hiz                           :=  fNFromRecFN(8,24,FADD_MUL_12.io.actual.out)

    //第三级的流水线---------------第二级
    val cidx_0_2                   = RegInit(0.S(32.W))
    val cidx_1_2                   = RegInit(0.S(32.W))
    // val tri_count_0_3        = RegInit(0.U(8.W))
    // val tri_count_1_3        = RegInit(0.U(8.W))
    // val child_mask_0_3   =RegInit(0.U(8.W))
    // val child_mask_1_3   =RegInit(0.U(8.W))

    cidx_0_2                         := cidx_0_1
    cidx_1_2                         := cidx_1_1
    // tri_count_0_3               := tri_count_0_2
    // tri_count_1_3               := tri_count_1_2
    // child_mask_0_3          := child_mask_0_2
    // child_mask_1_3          := child_mask_1_2

    val rayid_2       = RegInit(0.U(32.W))
    val hitT_2         = RegInit(0.U(32.W))
    val tmin_2       = RegInit(0.U(32.W))
    val valid_2       = RegInit(0.U(32.W))

    hitT_2                  := hitT_1
    tmin_2                 := tmin_1
    rayid_2                := rayid_1
    valid_2                 := valid_1

    val cmpMin0_1 = RegInit(0.U(32.W))
    val cmpMin0_2 = RegInit(0.U(32.W))
    val cmpMin0_3 = RegInit(0.U(32.W))
    val cmpMax0_1 = RegInit(0.U(32.W))
    val cmpMax0_2 = RegInit(0.U(32.W))
    val cmpMax0_3 = RegInit(0.U(32.W))
    val cmpMin1_1 = RegInit(0.U(32.W))
    val cmpMin1_2 = RegInit(0.U(32.W))
    val cmpMin1_3 = RegInit(0.U(32.W))
    val cmpMax1_1 = RegInit(0.U(32.W))
    val cmpMax1_2 = RegInit(0.U(32.W))
    val cmpMax1_3 = RegInit(0.U(32.W))

    val FCMP_1 = Module(new ValExec_CompareRecF32_le)
        FCMP_1.io.a := c0lox
        FCMP_1.io.b := c0hix
        FCMP_1.io.expected.out := 0.U
        FCMP_1.io.expected.exceptionFlags := 0.U
        cmpMin0_1 := Mux(FCMP_1.io.actual.out === 1.U,c0lox,c0hix)
        cmpMax0_1 := Mux(FCMP_1.io.actual.out === 1.U,c0hix,c0lox)
        // when(FCMP_1.io.actual.out > 0.U){
        //     cmpMin0_1 := c0lox    
        //     cmpMax0_1 := c0hix
        // } .otherwise{
        //     cmpMin0_1 := c0hix
        //     cmpMax0_1 := c0lox
        // }

    val FCMP_2 = Module(new ValExec_CompareRecF32_le)
        FCMP_2.io.a := c0loy
        FCMP_2.io.b := c0hiy
        FCMP_2.io.expected.out := 0.U
        FCMP_2.io.expected.exceptionFlags := 0.U
        when(FCMP_2.io.actual.out > 0.U){
            cmpMin0_2 := c0loy    
            cmpMax0_2 := c0hiy
        } .otherwise{
            cmpMin0_2 := c0hiy
            cmpMax0_2 := c0loy
        }

    val FCMP_3 = Module(new ValExec_CompareRecF32_le)
        FCMP_3.io.a := c0loz
        FCMP_3.io.b := c0hiz
        FCMP_3.io.expected.out := 0.U
        FCMP_3.io.expected.exceptionFlags := 0.U
        when(FCMP_3.io.actual.out > 0.U){
            cmpMin0_3 := c0loz    
            cmpMax0_3 := c0hiz
        } .otherwise{
            cmpMin0_3 := c0hiz
            cmpMax0_3 := c0loz
        }
    
    val FCMP_4 = Module(new ValExec_CompareRecF32_le)
        FCMP_4.io.a := c1lox
        FCMP_4.io.b := c1hix
        FCMP_4.io.expected.out := 0.U
        FCMP_4.io.expected.exceptionFlags := 0.U
        when(FCMP_4.io.actual.out > 0.U){
            cmpMin1_1 := c1lox    
            cmpMax1_1 := c1hix
        } .otherwise{
            cmpMin1_1 := c1hix
            cmpMax1_1 := c1lox
        }
    
     val FCMP_5 = Module(new ValExec_CompareRecF32_le)
        FCMP_5.io.a := c1loy
        FCMP_5.io.b := c1hiy
        FCMP_5.io.expected.out := 0.U
        FCMP_5.io.expected.exceptionFlags := 0.U
        when(FCMP_5.io.actual.out > 0.U){
            cmpMin1_2 := c1loy    
            cmpMax1_2 := c1hiy
        } .otherwise{
            cmpMin1_2 := c1hiy
            cmpMax1_2 := c1loy
        }

    val FCMP_6 = Module(new ValExec_CompareRecF32_le)
        FCMP_6.io.a := c1loz
        FCMP_6.io.b := c1hiz
        FCMP_6.io.expected.out := 0.U
        FCMP_6.io.expected.exceptionFlags := 0.U
        when(FCMP_6.io.actual.out > 0.U){
            cmpMin1_3 := c1loz    
            cmpMax1_3 := c1hiz
        } .otherwise{
            cmpMin1_3 := c1hiz
            cmpMax1_3 := c1loz
        }

    //第四级流水线----------第三级流水线
    val c0Min_temp_1 = RegInit(0.U(32.W))
    val c0Min_temp_2 = RegInit(0.U(32.W))
    val c0Max_temp_1 = RegInit(0.U(32.W))
    val c0Max_temp_2 = RegInit(0.U(32.W))
    val c1Min_temp_1 = RegInit(0.U(32.W))
    val c1Min_temp_2 = RegInit(0.U(32.W))
    val c1Max_temp_1 = RegInit(0.U(32.W))
    val c1Max_temp_2 = RegInit(0.U(32.W))

    val cidx_0_3                   = RegInit(0.S(32.W))
    val cidx_1_3                   = RegInit(0.S(32.W))
    // val tri_count_0_4        = RegInit(0.U(8.W))
    // val tri_count_1_4        = RegInit(0.U(8.W))
    // val child_mask_0_4   =RegInit(0.U(8.W))
    // val child_mask_1_4   =RegInit(0.U(8.W))
    

    cidx_0_3                         := cidx_0_2
    cidx_1_3                         := cidx_1_2
    // tri_count_0_4               := tri_count_0_3
    // tri_count_1_4               := tri_count_1_3
    // child_mask_0_4          := child_mask_0_3
    // child_mask_1_4          := child_mask_1_3

    val hitT_3                         = RegInit(0.U(32.W))
    hitT_3                               := hitT_2
    val rayid_3                       = RegInit(0.U(32.W))
    rayid_3                             := rayid_2
    val valid_3                        = RegInit(0.U(1.W))
    valid_3                             := valid_2

    val FCMP_7 = Module(new ValExec_CompareRecF32_le)
        FCMP_7.io.a := cmpMin0_1
        FCMP_7.io.b := cmpMin0_2
        FCMP_7.io.expected.out := 0.U
        FCMP_7.io.expected.exceptionFlags := 0.U
        when(FCMP_7.io.actual.out > 0.U){   
            c0Min_temp_1 := cmpMin0_2
        } .otherwise{
            c0Min_temp_1 := cmpMin0_1
        }

    val FCMP_8 = Module(new ValExec_CompareRecF32_le)
        FCMP_8.io.a := cmpMin0_3
        FCMP_8.io.b := tmin_2
        FCMP_8.io.expected.out := 0.U
        FCMP_8.io.expected.exceptionFlags := 0.U
        when(FCMP_8.io.actual.out > 0.U){   
            c0Min_temp_2 := tmin_2
        } .otherwise{
            c0Min_temp_2 := cmpMin0_3
        }

    val FCMP_9 = Module(new ValExec_CompareRecF32_le)
        FCMP_9.io.a := cmpMax0_1
        FCMP_9.io.b := cmpMax0_2
        FCMP_9.io.expected.out := 0.U
        FCMP_9.io.expected.exceptionFlags := 0.U
        when(FCMP_9.io.actual.out > 0.U){   
            c0Max_temp_1 := cmpMax0_1
        } .otherwise{
            c0Max_temp_1 := cmpMax0_2
        }

    val FCMP_10 = Module(new ValExec_CompareRecF32_le)
        FCMP_10.io.a := cmpMax0_3
        FCMP_10.io.b := hitT_2
        FCMP_10.io.expected.out := 0.U
        FCMP_10.io.expected.exceptionFlags := 0.U
        when(FCMP_10.io.actual.out > 0.U){   
            c0Max_temp_2 := cmpMax0_3
        } .otherwise{
            c0Max_temp_2 := hitT_2
        }

    val FCMP_11 = Module(new ValExec_CompareRecF32_le)
        FCMP_11.io.a := cmpMin1_1
        FCMP_11.io.b := cmpMin1_2
        FCMP_11.io.expected.out := 0.U
        FCMP_11.io.expected.exceptionFlags := 0.U
        when(FCMP_11.io.actual.out > 0.U){   
            c1Min_temp_1 := cmpMin1_2
        } .otherwise{
            c1Min_temp_1 := cmpMin1_1
        }

    val FCMP_12 = Module(new ValExec_CompareRecF32_le)
        FCMP_12.io.a := cmpMin1_3
        FCMP_12.io.b := tmin_2
        FCMP_12.io.expected.out := 0.U
        FCMP_12.io.expected.exceptionFlags := 0.U
        when(FCMP_12.io.actual.out > 0.U){   
            c1Min_temp_2 := tmin_2
        } .otherwise{
            c1Min_temp_2 := cmpMin1_3
        }

    val FCMP_13 = Module(new ValExec_CompareRecF32_le)
        FCMP_13.io.a := cmpMax1_1
        FCMP_13.io.b := cmpMax1_2
        FCMP_13.io.expected.out := 0.U
        FCMP_13.io.expected.exceptionFlags := 0.U
        when(FCMP_13.io.actual.out > 0.U){   
            c1Max_temp_1 := cmpMax1_1
        } .otherwise{
            c1Max_temp_1 := cmpMax1_2
        }

    val FCMP_14 = Module(new ValExec_CompareRecF32_le)
        FCMP_14.io.a := cmpMax1_3
        FCMP_14.io.b := hitT_2
        FCMP_14.io.expected.out := 0.U
        FCMP_14.io.expected.exceptionFlags := 0.U
        when(FCMP_14.io.actual.out > 0.U){   
            c1Max_temp_2 := cmpMax1_3
        } .otherwise{
            c1Max_temp_2 := hitT_2
        }
    //第五级流水线-------------第四级流水线
    val c0Min = RegInit(0.U(32.W))
    val c0Max = RegInit(0.U(32.W))
    val c1Min = RegInit(0.U(32.W))
    val c1Max = RegInit(0.U(32.W))

    val cidx_0_4                   = RegInit(0.S(32.W))
    val cidx_1_4                   = RegInit(0.S(32.W))
    // val tri_count_0_5        = RegInit(0.U(8.W))
    // val tri_count_1_5        = RegInit(0.U(8.W))

    // val child_mask_0_5   =RegInit(0.U(8.W))
    // val child_mask_1_5   =RegInit(0.U(8.W))

    cidx_0_4                         := cidx_0_3
    cidx_1_4                         := cidx_1_3
    // tri_count_0_5               := tri_count_0_4
    // tri_count_1_5               := tri_count_1_4
    // child_mask_0_5          := child_mask_0_4
    // child_mask_1_5          := child_mask_1_4

    val hitT_4                         = RegInit(0.U(32.W))
    hitT_4                                := hitT_3
    val rayid_4                       = RegInit(0.U(32.W))
    rayid_4                              := rayid_3
    val valid_4                        = RegInit(0.U(1.W))
    valid_4                             := valid_3

    val FCMP_15 = Module(new ValExec_CompareRecF32_le)
        FCMP_15.io.a := c0Min_temp_1
        FCMP_15.io.b := c0Min_temp_2
        FCMP_15.io.expected.out := 0.U
        FCMP_15.io.expected.exceptionFlags := 0.U
        when(FCMP_15.io.actual.out > 0.U){   
            c0Min := c0Min_temp_2
        } .otherwise{
            c0Min := c0Min_temp_1
        }

    val FCMP_16 = Module(new ValExec_CompareRecF32_le)
        FCMP_16.io.a := c0Max_temp_1
        FCMP_16.io.b := c0Max_temp_2
        FCMP_16.io.expected.out := 0.U
        FCMP_16.io.expected.exceptionFlags := 0.U
        when(FCMP_16.io.actual.out > 0.U){   
            c0Max := c0Max_temp_1
        } .otherwise{
            c0Max := c0Max_temp_2
        }
    
    val FCMP_17 = Module(new ValExec_CompareRecF32_le)
        FCMP_17.io.a := c1Min_temp_1
        FCMP_17.io.b := c1Min_temp_2
        FCMP_17.io.expected.out := 0.U
        FCMP_17.io.expected.exceptionFlags := 0.U
        when(FCMP_17.io.actual.out > 0.U){  
            c1Min := c1Min_temp_2
        } .otherwise{
            c1Min := c1Min_temp_1
        }

    val FCMP_18 = Module(new ValExec_CompareRecF32_le)
        FCMP_18.io.a := c1Max_temp_1    
        FCMP_18.io.b := c1Max_temp_2
        FCMP_18.io.expected.out := 0.U
        FCMP_18.io.expected.exceptionFlags := 0.U
        when(FCMP_18.io.actual.out > 0.U){   
            c1Max := c1Max_temp_1
        } .otherwise{
            c1Max := c1Max_temp_2
        }

    //第六级流水---------------第五级流水线
    val rayid_5                       = RegInit(0.U(32.W))
    rayid_5                              := rayid_4
    val hitT_5                         = RegInit(0.U(32.W))
    hitT_5                               := hitT_4
    val valid_5                        = RegInit(0.U(1.W))
    valid_5                             := valid_4
    // val cmp0                           = RegInit(0.U(1.W))
    // val cmp1                           = RegInit(0.U(1.W))

    val cidx_0_5                   = RegInit(0.S(32.W))
    val cidx_1_5                   = RegInit(0.S(32.W))
    // val tri_count_0_6        = RegInit(0.U(8.W))
    // val tri_count_1_6        = RegInit(0.U(8.W))
    // val child_mask_0_6   =RegInit(0.U(8.W))
    // val child_mask_1_6   =RegInit(0.U(8.W))

    val swp                             =RegInit(0.U(1.W))
    val c0Min_5                    = RegInit(0.U(32.W))
    val c1Min_5                    = RegInit(0.U(32.W))
    

    cidx_0_5                         := cidx_0_4
    cidx_1_5                         := cidx_1_4
    // tri_count_0_6               := tri_count_0_5
    // tri_count_1_6               := tri_count_1_5
    // child_mask_0_6          := child_mask_0_5
    // child_mask_1_6          := child_mask_1_5

    // c0Min_5                           := c0Min
    // c1Min_5                           := c1Min
  
    val FCMP_19 = Module(new ValExec_CompareRecF32_lt)
        FCMP_19.io.a := c0Max
        FCMP_19.io.b := c0Min
        FCMP_19.io.expected.out := 0.U
        FCMP_19.io.expected.exceptionFlags := 0.U
        when(FCMP_19.io.actual.out > 0.U){   
            traverseChild0 := 0.U
        } .otherwise{
            traverseChild0 := 1.U
        }   
    
    // when(!child_mask_1_5 === 0.U)
    // {
    val FCMP_20 = Module(new ValExec_CompareRecF32_lt)
        FCMP_20.io.a := c1Max
        FCMP_20.io.b := c1Min
        FCMP_20.io.expected.out := 0.U
        FCMP_20.io.expected.exceptionFlags := 0.U
        when(FCMP_20.io.actual.out > 0.U){   
            traverseChild1 := 0.U
        } .otherwise{ 
            traverseChild1 := 1.U 
        }
        // }.otherwise
    // { traverseChild1 := false.B }        
    
    val FCMP_21 = Module(new ValExec_CompareRecF32_lt)
        FCMP_21.io.a := c1Min 
        FCMP_21.io.b := c0Min
        FCMP_21.io.expected.out := 0.U
        FCMP_21.io.expected.exceptionFlags := 0.U
        when(FCMP_21.io.actual.out > 0.U){   
            swp                 := 1.U
        } .otherwise{
            swp                 := 0.U}

        //第七级流水，这里的结果输出为是否栈操作------------第六级流水
    io.rayid_out                              := rayid_5
    io.hitT_out                                := hitT_5 
    when(!traverseChild0 & (!traverseChild1)===1.U&&(valid_5===1.U)){//这种情况是两个节点都没有相交   0,0
        io.pop                  :=  1.U
        io.push                :=  0.U
        io.leaf                   :=  0.U
        io.back                 := 0.U
        io.nodeIdx_0     := 0.S
        io.nodeIdx_1     := 0.S
        io.nodeIdx_2     := 0.S
        io.valid_out       := true.B
    }.elsewhen((!traverseChild0) & traverseChild1===1.U&&(valid_5===1.U)){//这中情况是0.1，也即第一个节点没有相交，第二个节点相交
        // when(child_mask_1_6 === 1){//0.1情况下，是内部节点,输出nodeIdx_1为孩子节点的第二个地址
        //     io.leaf               :=  0.U
        //     io.pop               :=  0.U
        //     io.push             :=  0.U
        //     io.nodeIdx_1 :=  cidx_1_6
        // }.otherwise(child_mask_1_6 === 2)
        //     io.leaf                :=  1.U
        //     io.pop               :=  0.U
        //     io.push             :=  0.U
        //     io.nodeIdx_2 :=  cidx_1_6
        when(positive(cidx_1_5)===0.U){//判断是否为负数
            io.leaf                    := 1.U
            io.pop                   := 0.U
            io.push                 := 0.U
            io.back                 := 0.U
            io.nodeIdx_0     := 0.S
            io.nodeIdx_1     := 0.S
            io.nodeIdx_2     := cidx_1_5
            io.valid_out       := true.B
        }.elsewhen(positive(cidx_1_5)===1.U){//这个可以判断是正数
            io.leaf                   := 0.U
            io.pop                  := 0.U
            io.push                := 0.U
            io.back                 := 1.U
            io.nodeIdx_0     := 0.S
            io.nodeIdx_1    := cidx_1_5
            io.nodeIdx_2     := 0.S
            io.valid_out       := true.B
        }.otherwise{
            io.leaf                   := 0.U
            io.pop                  := 0.U
            io.push                := 0.U
            io.back                 := 0.U
            io.nodeIdx_0     := 0.S
            io.nodeIdx_1    := 0.S
            io.nodeIdx_2     := 0.S
            io.valid_out       := false.B
        }
    }.elsewhen((traverseChild0 & (!traverseChild1))  ===1.U&&(valid_5===1.U)){//1,0
        // when(child_mask_0_6 === 1){
        //     io.leaf                 :=  0.U
        //     io.pop                 :=  0.U
        //     io.push               :=  0.U
        //     io.nodeIdx_1   :=  cidx_0_6
        // }.otherwise(child_mask_0_6 === 2)
        //     io.leaf   :=  1.U
        //     io.pop  :=  0.U
        //     io.push:=  0.U
        //     io.nodeIdx_2 :=  cidx_1_6
        when(positive(cidx_0_5)===0.U){
                io.leaf                    := 1.U
                io.pop                   := 0.U
                io.push                 := 0.U
                io.back                 := 0.U
                io.nodeIdx_0     := 0.S
                io.nodeIdx_1     := 0.S
                io.nodeIdx_2     := cidx_0_5
                io.valid_out       := true.B
        }.elsewhen(positive(cidx_0_5)===1.U){
                io.leaf                   := 0.U
                io.push                := 0.U
                io.pop                  := 0.U
                io.back                 := 1.U
                io.nodeIdx_0     := 0.S
                io.nodeIdx_1    := cidx_0_5
                io.nodeIdx_2     := 0.S
                io.valid_out       := true.B
        }.otherwise{
                io.leaf                   := 0.U
                io.push                := 0.U
                io.pop                  := 0.U
                io.back                 := 0.U
                io.nodeIdx_0     := 0.S
                io.nodeIdx_1    := 0.S
                io.nodeIdx_2     := 0.S
                io.valid_out       := false.B
        }
    }.elsewhen((traverseChild0===1.U) && (traverseChild1  ===1.U)&&(valid_5===1.U)){//1,1
        when(swp === 1.U&&(valid_5===1.U)){
            when(positive(cidx_1_5)===0.U){
                io.push              := 1.U
                io.pop                := 0.U
                io.leaf                 := 1.U
                io.back               := 0.U
                io.nodeIdx_0   :=  cidx_0_5//这个是进栈的地址
                io.nodeIdx_1   := 0.S
                io.nodeIdx_2   :=  cidx_1_5//这个说明是一个叶子
                io.valid_out       := true.B
            }.elsewhen(positive(cidx_1_5)===1.U){
                io.push              := 1.U
                io.pop                := 0.U
                io.leaf                 := 0.U
                io.back               := 1.U
                io.nodeIdx_0   :=  cidx_0_5//这个是进栈的地址
                io.nodeIdx_1   :=  cidx_1_5//这个是内部节点的地址
                io.nodeIdx_2   := 0.S
                io.valid_out       := true.B
            }.otherwise{
                io.push              := 0.U
                io.pop                := 0.U
                io.leaf                 := 0.U
                io.back               := 0.U
                io.nodeIdx_0   :=  0.S//这个是进栈的地址
                io.nodeIdx_1   :=  0.S//这个是内部节点的地址
                io.nodeIdx_2   := 0.S
                io.valid_out       := false.B
            }
        }.elsewhen(swp === 0.U&&(valid_5===1.U)){
            when(positive(cidx_0_5)===0.U){//表示负数
                io.push              := 1.U
                io.pop                := 0.U
                io.leaf                 := 1.U
                io.back               := 0.U
                io.nodeIdx_0   :=  cidx_1_5//这个是进栈的地址
                io.nodeIdx_1   := 0.S             
                io.nodeIdx_2   :=  cidx_0_5
                 io.valid_out       := true.B
            }.elsewhen(positive(cidx_0_5)===1.U){
                io.push              := 1.U
                io.pop                := 0.U
                io.leaf                 := 0.U
                io.back               := 1.U
                io.nodeIdx_0   :=  cidx_1_5//这个是进栈的地址
                io.nodeIdx_1   :=  cidx_0_5//这个是内部节点的地址
                io.nodeIdx_2   := 0.S
                io.valid_out       := true.B
            }.otherwise{
                io.push              := 0.U
                io.pop                := 0.U
                io.leaf                 := 0.U
                io.back               := 0.U
                io.nodeIdx_0   :=  0.S//这个是进栈的地址
                io.nodeIdx_1   :=  0.S//这个是内部节点的地址
                io.nodeIdx_2   := 0.S
                io.valid_out       := false.B
            }
        }.otherwise{
            // when(positive(cidx_0_5)===0.U){//表示负数
            //     io.push              := 1.U
            //     io.pop                := 0.U
            //     io.leaf                 := 0.U
            //     io.back               := 0.U
            //     io.nodeIdx_0   :=  cidx_1_5//这个是进栈的地址
            //     io.nodeIdx_1   := 0.S             
            //     io.nodeIdx_2   :=  cidx_0_5
            //      io.valid_out       := true.B
            // }.otherwise{
            //     io.push              := 1.U
            //     io.pop                := 0.U
            //     io.leaf                 := 0.U
            //     io.back               := 1.U
            //     io.nodeIdx_0   :=  cidx_1_5//这个是进栈的地址
            //     io.nodeIdx_1   :=  cidx_0_5//这个是内部节点的地址
            //     io.nodeIdx_2   := 0.S
            //     io.valid_out       := true.B
            // }
                io.push              := 0.U
                io.pop                := 0.U
                io.leaf                 := 0.U
                io.back               := 0.U
                io.nodeIdx_0   :=  0.S//这个是进栈的地址
                io.nodeIdx_1   :=  0.S//这个是内部节点的地址
                io.nodeIdx_2   := 0.S
                io.valid_out       := false.B
        }
    }.otherwise{
        io.push              := 0.U
        io.pop                := 0.U
        io.leaf                 := 0.U
        io.back               := 0.U
        io.nodeIdx_0   :=  0.S//这个是进栈的地址
        io.nodeIdx_1   :=  0.S//这个是内部节点的地址
        io.nodeIdx_2   := 0.S
        io.valid_out       := false.B
    }
    // }.otherwise{
    //     io.push              := 0.U
    //     io.pop                := 0.U
    //     io.leaf                 := 0.U
    //     io.back               := 0.U
    //     io.nodeIdx_0   :=  0.S//这个是进栈的地址
    //     io.nodeIdx_1   :=  0.S//这个是内部节点的地址
    //     io.nodeIdx_2   := 0.S
    //     io.valid_out       := false.B
    //     io.rayid_out      := 0.U
    //     io.hitT_out        := 0.U

    // }
 }   
// class bvh (dut:ray_AABB) extends PeekPokeTester(dut){
//         // poke(dut.io.ray_ood(31,0), 323830220010932965333264739612L.U)
//         poke(dut.io.ray_ood.x, 3264739612L.U)//-75.986542
//         poke(dut.io.ray_ood.y, 1093296533.U)//10.648824
//         poke(dut.io.ray_ood.z,3238302200L.U)//-8.285637

//         poke(dut.io.ray_idir.x, 3241038635L.U)//-10.895305
//         poke(dut.io.ray_idir.y, 1067152077.U)//1.214441
//         poke(dut.io.ray_idir.z,3219429143L.U)//-1.785861
        
//         // poke(dut.io.ray_idir, 321942914310671520773241038635L.U)
//         poke(dut.io.ray_tmin,0.U)
//         poke(dut.io.ray_hitT,1120403456.U)
//         poke(dut.io.bvh_n0xy.x,3205845798L.U)//-0.5833
//         poke(dut.io.bvh_n0xy.y,1100541961.U)//19.117205
//         poke(dut.io.bvh_n0xy.w,3205119344L.U)//-0.540000
//         poke(dut.io.bvh_n0xy.z,1102577664.U)//23

//         poke(dut.io.bvh_n1xy.x,1100541961.U)//19.117205
//         poke(dut.io.bvh_n1xy.y,1108475904.U)//36.5
//         poke(dut.io.bvh_n1xy.w,3205119344L.U)//-0.540000
//         poke(dut.io.bvh_n1xy.z,1102577664.U)//23

//         poke(dut.io.bvh_nz.x,3163060864L.U)//-0.016660
//         poke(dut.io.bvh_nz.y,1091462758.U)//8.900000
//         poke(dut.io.bvh_nz.w,3163060864L.U)//-0.016660
//         poke(dut.io.bvh_nz.z,1091462758.U)//8.900000
        
        
//         // poke(dut.io.bvh_n0xy, 1102577664320511934411005419613205845798L.U)
//         // poke(dut.io.bvh_n1xy, 1102577664320511934411084759041100541961L.U)
//         // poke(dut.io.bvh_nz, 1091462758316306086410914627583163060864L.U)
//         poke(dut.io.bvh_temp.x,1132232222L.U)
//         poke(dut.io.bvh_temp.y,3232323232L.U)
//         poke(dut.io.rayid,1.U)
//         step(10)
// }
// object bvhtest extends App {
//   chisel3.iotesters.Driver.execute(args, () => new ray_AABB())(c => new bvh(c))
// }
object AABB extends App {
  chisel3.Driver.execute(args, () => new ray_AABB())
}
