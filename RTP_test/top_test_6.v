`timescale 1ns/1ns
module top_test(
  output[31:0]io_hitT,
  output[31:0]io_ray_id_triangle,
  output io_rtp_finish,
  output[63:0]io_counter_fdiv

);

parameter PERIOD = 10;
  reg clock;
  reg reset;
  // reg [31:0]io_hitT;
  // reg [31:0]io_hitIndex;
  // reg io_rtp_finish;
  // reg [31:0] io_ray_id_triangle;

initial begin
  clock = 0;
  reset = 1;


    $dumpfile("sanmiguel.vcd");//生成的vcd文件名称
    $dumpvars(0,top_test);
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/origx_h.txt" , rtp_test.Ray_origx.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/origy_h.txt" , rtp_test.Ray_origy.mem      );            
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/origz_h.txt" , rtp_test.Ray_origz.mem      );     

    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/dirx_h.txt" , rtp_test.Ray_dirx.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/diry_h.txt" , rtp_test.Ray_diry.mem      );            
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/dirz_h.txt" , rtp_test.Ray_dirz.mem      );     

    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/hitT.txt" , rtp_test.Ray_hitT.mem      );   

    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/idirx.txt" , rtp_test.Ray_idirx.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/idiry.txt" , rtp_test.Ray_idiry.mem      );            
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/idirz.txt" , rtp_test.Ray_idirz.mem      );     

    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/oodx.txt" , rtp_test.Ray_oodx.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/oody.txt" , rtp_test.Ray_oody.mem      );            
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/oodz.txt" , rtp_test.Ray_oodz.mem      );     

    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n0xy_x.txt" , rtp_test.BVH_RAM_0_x.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n0xy_y.txt" , rtp_test.BVH_RAM_0_y.mem      );        
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n0xy_z.txt" , rtp_test.BVH_RAM_0_z.mem      );   
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n0xy_w.txt" , rtp_test.BVH_RAM_0_w.mem      );   

    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n1xy_x.txt" , rtp_test.BVH_RAM_1_x.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n1xy_y.txt" , rtp_test.BVH_RAM_1_y.mem      );        
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n1xy_z.txt" , rtp_test.BVH_RAM_1_z.mem      );   
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n1xy_w.txt" , rtp_test.BVH_RAM_1_w.mem      );   

    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n2xy_x.txt" , rtp_test.BVH_RAM_z_x.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n2xy_y.txt" , rtp_test.BVH_RAM_z_y.mem      );        
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n2xy_z.txt" , rtp_test.BVH_RAM_z_z.mem      );   
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/n2xy_w.txt" , rtp_test.BVH_RAM_z_w.mem      );   


    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/leaf_H_x.txt" , rtp_test.BVH_RAM_tmp_x.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/leaf_H_y.txt" , rtp_test.BVH_RAM_tmp_y.mem      );        

    // $readmemh("./fairy_data_H/v00_x.txt" , rtp_test.TRI_RAM_v00_x.mem      );  
    // $readmemh("./fairy_data_H/v00_y.txt" , rtp_test.TRI_RAM_v00_y.mem      );        
    // $readmemh("./fairy_data_H/v00_z.txt" , rtp_test.TRI_RAM_v00_z.mem      );   
    // $readmemh("./fairy_data_H/v00_w.txt" , rtp_test.TRI_RAM_v00_w.mem      );   

    // $readmemh("./fairy_data_H/v11_x.txt" , rtp_test.TRI_RAM_v11_x.mem      );  
    // $readmemh("./fairy_data_H/v11_y.txt" , rtp_test.TRI_RAM_v11_y.mem      );        
    // $readmemh("./fairy_data_H/v11_z.txt" , rtp_test.TRI_RAM_v11_z.mem      );   
    // $readmemh("./fairy_data_H/v11_w.txt" , rtp_test.TRI_RAM_v11_w.mem      );   

    // $readmemh("./fairy_data_H/v22_x.txt" , rtp_test.TRI_RAM_v22_x.mem      );  
    // $readmemh("./fairy_data_H/v22_y.txt" , rtp_test.TRI_RAM_v22_y.mem      );        
    // $readmemh("./fairy_data_H/v22_z.txt" , rtp_test.TRI_RAM_v22_z.mem      );   
    // $readmemh("./fairy_data_H/v22_w.txt" , rtp_test.TRI_RAM_v22_w.mem      );   
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/triangle_x_8_16_2" , rtp_test.TRI_RAM_x.mem      );  
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/triangle_y_8_16_2" , rtp_test.TRI_RAM_y.mem      );
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/triangle_z_8_16_2" , rtp_test.TRI_RAM_z.mem      );
    $readmemh("/home/workspace/ICCD/RTP_1G/RTP_test/sanmiguel_data_H/triangle_w_8_16_2" , rtp_test.TRI_RAM_w.mem      );      

    $readmemb("./mem.txt" , rtp_test.Stack_manage.LUT_stack.LUT_mem      );   //需要把栈的管理里的地址初始化一下
    $readmemb("./mem.txt" , rtp_test.Stack_manage_2.LUT_stack.LUT_mem      );   //需要把栈的管理里的地址初始化一下
    #10 reset = 0 ;
    $display("\n simulation begin: \n");
end

initial begin
    forever #(PERIOD/2) clock = ~clock;
end

logic [31:0] total_cycle;
always @(posedge clock ) begin
    if(reset)begin
        total_cycle <= 0;
    end
    else begin
        total_cycle <= total_cycle+1;
    end
end


TOP_1  rtp_test(
  .clock  (clock),
  .reset  (reset),
  .io_hitT(io_hitT),
  // .io_hitIndex(io_hitIndex),
  // .io_rtp_finish(io_rtp_finish),
  .io_ray_id_triangle(io_ray_id_triangle),
//   .io_wrEna(1'b1),
//   .io_rdAddr(addr)
  // .io_hitIndex(io_hitIndex),
  .io_rtp_finish(io_rtp_finish),
  .io_counter_fdiv(io_counter_fdiv)
  // .io_ray_id_triangle(io_ray_id_triangle)
);
endmodule