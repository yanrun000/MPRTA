module RTP_x_wrapper(
  input reset,
  input clock  
);

wire [31:0]io_hitT;
wire [31:0]io_hitIndex;
wire io_rtp_finish;
wire [31:0]io_ray_id_triangle;;
wire [95:0]  io_o_wrData;
wire [31:0]  io_o_wrAddr;
wire  [95:0]  io_d_wrData;
wire  [31:0]  io_d_wrAddr;
wire  [31:0]  io_hit_wrData;
wire  [31:0]  io_hit_wrAddr;
wire  [95:0]  io_idir_wrData;
wire  [31:0]  io_idir_wrAddr;
wire  [95:0]  io_ood_wrData;
wire  [31:0]  io_ood_wrAddr;
wire  [127:0] io_bvh_n0xy_wrData;
wire  [31:0]  io_bvh_n0xy_wrAddr;
wire  [127:0] io_bvh_n1xy_wrData;
wire  [31:0]  io_bvh_n1xy_wrAddr;
wire  [127:0] io_bvh_nz_wrData;
wire  [31:0]  io_bvh_nz_wrAddr;
wire  [63:0]  io_bvh_temp_wrData;
wire  [31:0]  io_bvh_temp_wrAddr;
wire  [127:0] io_tri_v00_wrData;
wire  [31:0]  io_tri_v00_wrAddr;
wire  [127:0] io_tri_v11_wrData;
wire  [31:0]  io_tri_v11_wrAddr;
wire  [127:0] io_tri_v22_wrData;
wire  [31:0]  io_tri_v22_wrAddr;

RTP RTP_x(
  .clock(clock),
  .reset(reset),
  .io_hitT(io_hitT),
  .io_hitIndex(io_hitIndex),
  .io_rtp_finish(io_rtp_finish),
  .io_ray_id_triangle(io_ray_id_triangle),
  .io_o_wrData(io_o_wrData),
  .io_o_wrAddr(io_o_wrAddr),
  .io_d_wrData(io_d_wrData),
  .io_d_wrAddr(io_d_wrAddr),
  .io_hit_wrData(io_hit_wrData),
  .io_hit_wrAddr(io_hit_wrAddr),
  .io_idir_wrData(io_idir_wrData),
  .io_idir_wrAddr(io_idir_wrAddr),
  .io_ood_wrData(io_ood_wrData),
  .io_ood_wrAddr(io_ood_wrAddr),
  .io_bvh_n0xy_wrData(io_bvh_n0xy_wrData),
  .io_bvh_n0xy_wrAddr(io_bvh_n0xy_wrAddr),
  .io_bvh_n1xy_wrData(io_bvh_n1xy_wrData),
  .io_bvh_n1xy_wrAddr(io_bvh_n1xy_wrAddr),
  .io_bvh_nz_wrData(io_bvh_nz_wrData),
  .io_bvh_nz_wrAddr(io_bvh_nz_wrAddr),
  .io_bvh_temp_wrData(io_bvh_temp_wrData),
  .io_bvh_temp_wrAddr(io_bvh_temp_wrAddr),
  .io_tri_v00_wrData(io_tri_v00_wrData),
  .io_tri_v00_wrAddr(io_tri_v00_wrAddr),
  .io_tri_v11_wrData(io_tri_v11_wrData),
  .io_tri_v11_wrAddr(io_tri_v11_wrAddr),
  .io_tri_v22_wrData(io_tri_v22_wrData),
  .io_tri_v22_wrAddr(io_tri_v22_wrAddr)
);

endmodule