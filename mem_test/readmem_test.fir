;buildInfoPackage: chisel3, version: 3.4.1, scalaVersion: 2.12.12, sbtVersion: 1.3.10
circuit readmem_test : 
  module readmem_test : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip rdAddr : UInt<10>, rdData : UInt<32>, flip wrEna : UInt<1>, flip wrData : UInt<32>, flip wrAddr : UInt<10>}
    
    smem mem : UInt<32>[1024], undefined @[readmem.scala 13:26]
    read mport MPORT = mem[io.rdAddr], clock @[readmem.scala 15:28]
    io.rdData <= MPORT @[readmem.scala 15:17]
    when io.wrEna : @[readmem.scala 16:19]
      write mport MPORT_1 = mem[io.wrAddr], clock
      MPORT_1 <= io.wrData
      skip @[readmem.scala 16:19]
    
