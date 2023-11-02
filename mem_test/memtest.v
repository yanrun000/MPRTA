`timescale 1ns/1ns
module memtest(
  
);

parameter PERIOD = 10;
  reg clock;
  reg reset;
  // wire [31:0]io_hitT;
  // wire [31:0]io_hitIndex;
  // wire io_rtp_finish;
  // wire io_ray_id_triangle;
  reg [9:0]addr;
initial begin
  clock = 0;
  reset = 1;

  $dumpfile("wave.vcd");//生成的vcd文件名称
  $dumpvars(0,memtest);
  $readmemh("./mem.txt" , readmem.mem [5:0]     );       
  
  
  // $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h/oodz.txt" , RTP_top.io_o_wrData(95,64)       ); 
  // $readmemh("/home/workspace/Files/gpu-ray-traversal/test_scene/test.conference_8_h"    , rv32_x_top.ccm_i.dccm.dccm_d0.ram_core       );        // dccm
  #10 reset = 0;
  $display("\n simulation begin: \n");

  #20 addr = 0;
  #20 addr = 1;
  #20 addr = 2;
  #20 addr = 3;
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


readmem_test   readmem(
  .clock  (clock),
  .reset  (reset),
  .io_wrEna(1'b1),
  .io_rdAddr(addr)
  // .io_hitIndex(io_hitIndex),
  // .io_rtp_finish(io_rtp_finish),
  // .io_ray_id_triangle(io_ray_id_triangle)
);
endmodule