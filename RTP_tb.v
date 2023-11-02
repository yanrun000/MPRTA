`timescale 1ns/1ns
module RTP_tb(
  
);

parameter PERIOD = 10;
  reg clock;
  reg reset;
  wire [31:0]io_hitT;
  wire [31:0]io_hitIndex;
  wire io_rtp_finish;
  wire io_ray_id_triangle;

initial begin
  clock = 0;
  reset = 1;

  $dumpfile("wave.vcd");//生成的vcd文件名称
  $dumpvars(0,RTP_tb);
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/origx_h.txt" , RTP_top.io_o_wrData(31,0)       );       
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/origy_h.txt" , RTP_top.io_o_wrData(63,32)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/origz_h.txt" , RTP_top.io_o_wrData(95,64)       ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/dirx_h.txt" , RTP_top.io_d_wrData(31,0)       );       
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/diry_h.txt" , RTP_top.io_d_wrData(63,32)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/dirz_h.txt" , RTP_top.io_d_wrData(95,64)       ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/hitT.txt" , RTP_top.io_hit_wrData        ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/idirx.txt" , RTP_top.io_idir_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/idiry.txt" , RTP_top.io_idir_wrData(63,32)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/idirz.txt" , RTP_top.io_idir_wrData(95,64)       ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/oodx.txt" , RTP_top.io_ood_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/oody.txt" , RTP_top.io_ood_wrData(63,32)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/oody.txt" , RTP_top.io_ood_wrData(63,32)       ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n0xy_x.txt" , RTP_top.io_bvh_n0xy_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n0xy_y.txt" , RTP_top.io_bvh_n0xy_wrData(63,32)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n0xy_z.txt" , RTP_top.io_bvh_n0xy_wrData(95,64)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n0xy_w.txt" , RTP_top.io_bvh_n0xy_wrData(127,96)       ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n1xy_x.txt" , RTP_top.io_bvh_n1xy_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n1xy_y.txt" , RTP_top.io_bvh_n1xy_wrData(63,32)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n1xy_z.txt" , RTP_top.io_bvh_n1xy_wrData(95,64)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/n1xy_w.txt" , RTP_top.io_bvh_n1xy_wrData(127,96)       ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/nz_x.txt" , RTP_top.io_bvh_nz_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/nz_y.txt" , RTP_top.io_bvh_nz_wrData(63,32)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/nz_z.txt" , RTP_top.io_bvh_nz_wrData(95,64)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/nz_w.txt" , RTP_top.io_bvh_nz_wrData(127,96)       ); 

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/leaf_x.txt" , RTP_top.io_bvh_temp_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/leaf_y.txt" , RTP_top.io_bvh_temp_wrData(63,32)       );

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v00_x.txt" , RTP_top.io_tri_v00_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v00_y.txt" , RTP_top.io_tri_v00_wrData(63,32)       );
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v00_z.txt" , RTP_top.io_tri_v00_wrData(95,64)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v00_w.txt" , RTP_top.io_tri_v00_wrData(127,96)       );

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v11_x.txt" , RTP_top.io_tri_v11_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v11_y.txt" , RTP_top.io_tri_v11_wrData(63,32)       );
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v11_z.txt" , RTP_top.io_tri_v11_wrData(95,64)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v11_w.txt" , RTP_top.io_tri_v11_wrData(127,96)       );

  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v22_x.txt" , RTP_top.io_tri_v22_wrData(31,0)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v22_y.txt" , RTP_top.io_tri_v22_wrData(63,32)       );
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v22_z.txt" , RTP_top.io_tri_v22_wrData(95,64)       ); 
  $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/v22_w.txt" , RTP_top.io_tri_v22_wrData(127,96)       );

  // $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/oody.txt" , RTP_top.io_ood_wrData(63,32)       ); 
  
  // $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/oodz.txt" , RTP_top.io_o_wrData(95,64)       ); 
  // $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h"    , rv32_x_top.ccm_i.dccm.dccm_d0.ram_core       );        // dccm
  #10 reset = 0;
  $display("\n simulation begin: \n");
end

initial begin
    forever #(PERIOD/2) clock = ~clock;
end

logic [31:0] total_cycle;
always @(posedge clock or posedge reset) begin
    if(reset)begin
        total_cycle <= 0;
    end
    else begin
        total_cycle <= total_cycle+1;
    end
end


RTP_x_wrapper   RTP_top(
  .clock  (clock),
  .reset  (reset)
  // .io_hitT(io_hitT),
  // .io_hitIndex(io_hitIndex),
  // .io_rtp_finish(io_rtp_finish),
  // .io_ray_id_triangle(io_ray_id_triangle)
);
endmodule