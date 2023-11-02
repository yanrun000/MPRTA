circuit DeviceUnderTest :
  module DeviceUnderTest :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<2>
    input io_b : UInt<2>
    output io_out : UInt<2>
  
    node _T = and(io_a, io_b) @[test.scala 79:16]
    io_out <= _T @[test.scala 79:8]
