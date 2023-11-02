module Stack(
  input         clock,
  input         reset,
  input         io_push,
  input         io_pop,
  input         io_en,
  input  [31:0] io_dataIn,
  output [31:0] io_dataOut
);
`ifdef RANDOMIZE_MEM_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
`endif // RANDOMIZE_REG_INIT
  reg [31:0] stack_mem [0:7]; // @[stack.scala 13:24]
  wire [31:0] stack_mem_MPORT_1_data; // @[stack.scala 13:24]
  wire [2:0] stack_mem_MPORT_1_addr; // @[stack.scala 13:24]
  wire [31:0] stack_mem_MPORT_data; // @[stack.scala 13:24]
  wire [2:0] stack_mem_MPORT_addr; // @[stack.scala 13:24]
  wire  stack_mem_MPORT_mask; // @[stack.scala 13:24]
  wire  stack_mem_MPORT_en; // @[stack.scala 13:24]
  reg [3:0] sp; // @[stack.scala 14:21]
  reg [31:0] out; // @[stack.scala 15:22]
  wire  _T_1 = io_push & sp < 4'h8; // @[stack.scala 18:22]
  wire [3:0] _T_4 = sp + 4'h1; // @[stack.scala 20:18]
  wire  _T_5 = sp > 4'h0; // @[stack.scala 21:31]
  wire [3:0] _T_8 = sp - 4'h1; // @[stack.scala 22:18]
  assign stack_mem_MPORT_1_addr = _T_8[2:0];
  assign stack_mem_MPORT_1_data = stack_mem[stack_mem_MPORT_1_addr]; // @[stack.scala 13:24]
  assign stack_mem_MPORT_data = io_dataIn;
  assign stack_mem_MPORT_addr = sp[2:0];
  assign stack_mem_MPORT_mask = 1'h1;
  assign stack_mem_MPORT_en = io_en & _T_1;
  assign io_dataOut = out; // @[stack.scala 29:16]
  always @(posedge clock) begin
    if(stack_mem_MPORT_en & stack_mem_MPORT_mask) begin
      stack_mem[stack_mem_MPORT_addr] <= stack_mem_MPORT_data; // @[stack.scala 13:24]
    end
    if (reset) begin // @[stack.scala 14:21]
      sp <= 4'h0; // @[stack.scala 14:21]
    end else if (io_en) begin // @[stack.scala 17:18]
      if (io_push & sp < 4'h8) begin // @[stack.scala 18:46]
        sp <= _T_4; // @[stack.scala 20:12]
      end else if (io_pop & sp > 4'h0) begin // @[stack.scala 21:39]
        sp <= _T_8; // @[stack.scala 22:12]
      end
    end
    if (reset) begin // @[stack.scala 15:22]
      out <= 32'h0; // @[stack.scala 15:22]
    end else if (io_en) begin // @[stack.scala 17:18]
      if (_T_5) begin // @[stack.scala 24:21]
        out <= stack_mem_MPORT_1_data; // @[stack.scala 25:13]
      end
    end
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
  for (initvar = 0; initvar < 8; initvar = initvar+1)
    stack_mem[initvar] = _RAND_0[31:0];
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{`RANDOM}};
  sp = _RAND_1[3:0];
  _RAND_2 = {1{`RANDOM}};
  out = _RAND_2[31:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
