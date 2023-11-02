
/*============================================================================

This Chisel source file is part of a pre-release version of the HardFloat IEEE
Floating-Point Arithmetic Package, by John R. Hauser (with some contributions
from Yunsup Lee and Andrew Waterman, mainly concerning testing).

Copyright 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018 The Regents of
the University of California.  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice,
    this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions, and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 3. Neither the name of the University nor the names of its contributors may
    be used to endorse or promote products derived from this software without
    specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS", AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, ARE
DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

=============================================================================*/

package hardfloat

import Chisel._

object consts {
    /*------------------------------------------------------------------------
    | For rounding to integer values, rounding mode 'odd' rounds to minimum
    | magnitude instead, same as 'minMag'.
    *------------------------------------------------------------------------*/
    def round_near_even   = UInt("b000", 3)
    def round_minMag      = UInt("b001", 3)
    def round_min         = UInt("b010", 3)
    def round_max         = UInt("b011", 3)
    def round_near_maxMag = UInt("b100", 3)
    def round_odd         = UInt("b110", 3)
    /*------------------------------------------------------------------------
    *------------------------------------------------------------------------*/
    def tininess_beforeRounding = UInt(0, 1)
    def tininess_afterRounding  = UInt(1, 1)
    /*------------------------------------------------------------------------
    *------------------------------------------------------------------------*/
    def flRoundOpt_sigMSBitAlwaysZero  = 1
    def flRoundOpt_subnormsAlwaysExact = 2
    def flRoundOpt_neverUnderflows     = 4
    def flRoundOpt_neverOverflows      = 8
}

class RawFloat(val expWidth: Int, val sigWidth: Int) extends Bundle
{
    val isNaN  = Bool()              // overrides all other fields
    val isInf  = Bool()              // overrides 'isZero', 'sExp', and 'sig'
    val isZero = Bool()              // overrides 'sExp' and 'sig'
    val sign   = Bool()
    val sExp = SInt(width = expWidth + 2)
    val sig  = UInt(width = sigWidth + 1)   // 2 m.s. bits cannot both be 0

    override def cloneType =
        new RawFloat(expWidth, sigWidth).asInstanceOf[this.type]
}

//*** CHANGE THIS INTO A '.isSigNaN' METHOD OF THE 'RawFloat' CLASS:
object isSigNaNRawFloat
{
    def apply(in: RawFloat): Bool = in.isNaN && !in.sig(in.sigWidth - 2)
}


object offsign
{
    def apply(in:Bits) =
    {
        val out = Wire(Bits(32.W))
        out := Cat(~in(31),in(30,0))
        out
    }
}

object positive//这个是高位，用来判断是否为负
{
    def apply(in:Bits) =
    {
        val out = Wire(Bits(1.W))
        out :=Mux(in(31)===0.U,1.U,0.U)//out为1说明是正数
        out
    }
}
object positive_s//这个是高位，用来判断是否为负
{
    def apply(in:Bits) =
    {
        val out = Wire(Bits(1.W))
        out :=Mux(in(5)===0.U,1.U,0.U)
        out
    }
}

object complement_s//这个是用来计算补码的，这样之后就可以把一个负数变成一个UInt
{
    def apply(in:Bits) =
    {
        val out = Wire(SInt(6.W))
        out :=Cat(Mux(in(5)===0.U,1.U,0.U),~in(4,0)+1.U).asSInt
        out
    }
}
object complement//这个是用来计算补码的，这样之后就可以把一个负数变成一个UInt
{
    def apply(in:Bits) =
    {
        val out = Wire(SInt(32.W))
        out :=Cat(Mux(in(31)===0.U,1.U,0.U),~in(30,0)).asSInt
        out
    }
}

// object complement_u//用UInt来实现符号位取反
// {
//     def apply(in:Bits) =
//     {
//         val out = Wire(UInt(32.W))
//         out :=Cat(Mux(in(31)===1.U,0.U,1.U),in(30,0))
//         out
//     }
// }
// object positive_s//这个是高位，用来判断是否为负
// {
//     def apply(in:SInt) =
//     {
//         val out = Wire(Bits(1.W))
//         out :=Mux(in(31)===1.S,0.U,1.U )
//         out
//     }
// }
// object get_triIdx
// {
//     def apply()

// }

class Float() extends Bundle
{
    val x = Bits(32.W)
    val y = Bits(32.W)
    val z = Bits(32.W)
    val w = Bits(32.W)
    
    override def cloneType =
        new Float().asInstanceOf[this.type]
}

class ray() extends Bundle
{
    val x = Bits(32.W)
    val y = Bits(32.W)
    val z = Bits(32.W)
    // val w = Bits(32.W)
    
    override def cloneType =
        new ray().asInstanceOf[this.type]
}

class Float_0() extends Bundle
{
    val x = SInt(32.W)
    val y = SInt(32.W)
    // val z = SInt(32.W)
    // val w = SInt(32.W)
    
    override def cloneType =
        new Float_0().asInstanceOf[this.type]
}
// class TriWoopPrim() extends Bundle
// {
//     val wo new Bundle{
//         val w0
//     }
//     val x = Bits(32.W)
//     val y = Bits(32.W)
//     val z = Bits(32.W)
//     val w = Bits(32.W)
    
//     override def cloneType =
//         new Float().asInstanceOf[this.type]
// }