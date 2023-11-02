;buildInfoPackage: chisel3, version: 3.3-SNAPSHOT, scalaVersion: 2.11.12, sbtVersion: 1.3.10
circuit DeviceUnderTest : 
  module DeviceUnderTest : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip a : UInt<2>, flip b : UInt<2>, out : UInt<2>}
    
    node _T = and(io.a, io.b) @[test.scala 79:16]
    io.out <= _T @[test.scala 79:8]
    
