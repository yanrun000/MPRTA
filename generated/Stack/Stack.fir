circuit Stack :
  module Stack :
    input clock : Clock
    input reset : UInt<1>
    output io : { flip push : UInt<1>, flip pop : UInt<1>, flip en : UInt<1>, flip dataIn : UInt<32>, dataOut : UInt<32>}

    cmem stack_mem : UInt<32> [8] @[stack.scala 13:24]
    reg sp : UInt<4>, clock with :
      reset => (reset, UInt<4>("h0")) @[stack.scala 14:21]
    reg out : UInt<32>, clock with :
      reset => (reset, UInt<32>("h0")) @[stack.scala 15:22]
    when io.en : @[stack.scala 17:18]
      node _T = lt(sp, UInt<4>("h8")) @[stack.scala 18:29]
      node _T_1 = and(io.push, _T) @[stack.scala 18:22]
      when _T_1 : @[stack.scala 18:46]
        node _T_2 = bits(sp, 2, 0) @[stack.scala 19:18]
        infer mport MPORT = stack_mem[_T_2], clock @[stack.scala 19:18]
        MPORT <= io.dataIn @[stack.scala 19:23]
        node _T_3 = add(sp, UInt<1>("h1")) @[stack.scala 20:18]
        node _T_4 = tail(_T_3, 1) @[stack.scala 20:18]
        sp <= _T_4 @[stack.scala 20:12]
      else :
        node _T_5 = gt(sp, UInt<1>("h0")) @[stack.scala 21:31]
        node _T_6 = and(io.pop, _T_5) @[stack.scala 21:24]
        when _T_6 : @[stack.scala 21:39]
          node _T_7 = sub(sp, UInt<1>("h1")) @[stack.scala 22:18]
          node _T_8 = tail(_T_7, 1) @[stack.scala 22:18]
          sp <= _T_8 @[stack.scala 22:12]
      node _T_9 = gt(sp, UInt<1>("h0")) @[stack.scala 24:14]
      when _T_9 : @[stack.scala 24:21]
        node _T_10 = sub(sp, UInt<1>("h1")) @[stack.scala 25:29]
        node _T_11 = tail(_T_10, 1) @[stack.scala 25:29]
        node _T_12 = bits(_T_11, 2, 0) @[stack.scala 25:25]
        infer mport MPORT_1 = stack_mem[_T_12], clock @[stack.scala 25:25]
        out <= MPORT_1 @[stack.scala 25:13]
    io.dataOut <= out @[stack.scala 29:16]

