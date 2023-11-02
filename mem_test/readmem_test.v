module readmem_test(
  input         clock,
  input         reset,
  input  [9:0]  io_rdAddr,
  output [31:0] io_rdData,
  input         io_wrEna,
  input  [31:0] io_wrData,
  input  [9:0]  io_wrAddr
);
`ifdef RANDOMIZE_MEM_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_1;
`endif // RANDOMIZE_REG_INIT
  reg [31:0] mem [0:1023]; // @[readmem.scala 13:26]
  wire [31:0] mem_MPORT_data; // @[readmem.scala 13:26]
  wire [9:0] mem_MPORT_addr; // @[readmem.scala 13:26]
  wire [31:0] mem_MPORT_1_data; // @[readmem.scala 13:26]
  wire [9:0] mem_MPORT_1_addr; // @[readmem.scala 13:26]
  wire  mem_MPORT_1_mask; // @[readmem.scala 13:26]
  wire  mem_MPORT_1_en= 1'h1; // @[readmem.scala 13:26]
  reg [9:0] mem_MPORT_addr_pipe_0;
  assign mem_MPORT_addr = mem_MPORT_addr_pipe_0;
  assign mem_MPORT_data = mem[mem_MPORT_addr]; // @[readmem.scala 13:26]
  assign mem_MPORT_1_data = io_wrData;
  assign mem_MPORT_1_addr = io_wrAddr;
  assign mem_MPORT_1_mask = 1'h1;
  assign mem_MPORT_1_en = io_wrEna;
  assign io_rdData = mem_MPORT_data; // @[readmem.scala 15:17]
  always @(posedge clock) begin
    if(mem_MPORT_1_en & mem_MPORT_1_mask) begin
      mem[mem_MPORT_1_addr] <= mem_MPORT_1_data; // @[readmem.scala 13:26]
    end
    mem_MPORT_addr_pipe_0 <= io_rdAddr;
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_MEM_INIT
  _RAND_0 = {1{`RANDOM}};
  for (initvar = 0; initvar < 1024; initvar = initvar+1)
    mem[initvar] = _RAND_0[31:0];
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{`RANDOM}};
  mem_MPORT_addr_pipe_0 = _RAND_1[9:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
