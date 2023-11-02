module MulAddRecFNToRaw_preMul(
  input  [32:0] io_a,
  input  [32:0] io_b,
  input  [32:0] io_c,
  output [23:0] io_mulAddA,
  output [23:0] io_mulAddB,
  output [47:0] io_mulAddC,
  output        io_toPostMul_isSigNaNAny,
  output        io_toPostMul_isNaNAOrB,
  output        io_toPostMul_isInfA,
  output        io_toPostMul_isZeroA,
  output        io_toPostMul_isInfB,
  output        io_toPostMul_isZeroB,
  output        io_toPostMul_signProd,
  output        io_toPostMul_isNaNC,
  output        io_toPostMul_isInfC,
  output        io_toPostMul_isZeroC,
  output [9:0]  io_toPostMul_sExpSum,
  output        io_toPostMul_doSubMags,
  output        io_toPostMul_CIsDominant,
  output [4:0]  io_toPostMul_CDom_CAlignDist,
  output [25:0] io_toPostMul_highAlignedSigC,
  output        io_toPostMul_bit0AlignedSigC
);
  wire  rawA_isZero = io_a[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  wire  _T_4 = io_a[31:30] == 2'h3; // @[rawFloatFromRecFN.scala 52:54]
  wire  rawA_isNaN = _T_4 & io_a[29]; // @[rawFloatFromRecFN.scala 55:33]
  wire  rawA_sign = io_a[32]; // @[rawFloatFromRecFN.scala 58:25]
  wire [9:0] rawA_sExp = {1'b0,$signed(io_a[31:23])}; // @[rawFloatFromRecFN.scala 59:27]
  wire  hi_lo = ~rawA_isZero; // @[rawFloatFromRecFN.scala 60:39]
  wire [22:0] lo = io_a[22:0]; // @[rawFloatFromRecFN.scala 60:51]
  wire [24:0] rawA_sig = {1'h0,hi_lo,lo}; // @[Cat.scala 30:58]
  wire  rawB_isZero = io_b[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  wire  _T_17 = io_b[31:30] == 2'h3; // @[rawFloatFromRecFN.scala 52:54]
  wire  rawB_isNaN = _T_17 & io_b[29]; // @[rawFloatFromRecFN.scala 55:33]
  wire  rawB_sign = io_b[32]; // @[rawFloatFromRecFN.scala 58:25]
  wire [9:0] rawB_sExp = {1'b0,$signed(io_b[31:23])}; // @[rawFloatFromRecFN.scala 59:27]
  wire  hi_lo_1 = ~rawB_isZero; // @[rawFloatFromRecFN.scala 60:39]
  wire [22:0] lo_1 = io_b[22:0]; // @[rawFloatFromRecFN.scala 60:51]
  wire [24:0] rawB_sig = {1'h0,hi_lo_1,lo_1}; // @[Cat.scala 30:58]
  wire  rawC_isZero = io_c[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  wire  _T_30 = io_c[31:30] == 2'h3; // @[rawFloatFromRecFN.scala 52:54]
  wire  rawC_isNaN = _T_30 & io_c[29]; // @[rawFloatFromRecFN.scala 55:33]
  wire  rawC_sign = io_c[32]; // @[rawFloatFromRecFN.scala 58:25]
  wire [9:0] rawC_sExp = {1'b0,$signed(io_c[31:23])}; // @[rawFloatFromRecFN.scala 59:27]
  wire  hi_lo_2 = ~rawC_isZero; // @[rawFloatFromRecFN.scala 60:39]
  wire [22:0] lo_2 = io_c[22:0]; // @[rawFloatFromRecFN.scala 60:51]
  wire [24:0] rawC_sig = {1'h0,hi_lo_2,lo_2}; // @[Cat.scala 30:58]
  wire  signProd = rawA_sign ^ rawB_sign; // @[MulAddRecFN.scala 98:30]
  wire [10:0] _T_41 = $signed(rawA_sExp) + $signed(rawB_sExp); // @[MulAddRecFN.scala 101:19]
  wire [10:0] sExpAlignedProd = $signed(_T_41) - 11'she5; // @[MulAddRecFN.scala 101:32]
  wire  doSubMags = signProd ^ rawC_sign; // @[MulAddRecFN.scala 103:30]
  wire [10:0] _GEN_0 = {{1{rawC_sExp[9]}},rawC_sExp}; // @[MulAddRecFN.scala 107:42]
  wire [10:0] sNatCAlignDist = $signed(sExpAlignedProd) - $signed(_GEN_0); // @[MulAddRecFN.scala 107:42]
  wire [9:0] posNatCAlignDist = sNatCAlignDist[9:0]; // @[MulAddRecFN.scala 108:42]
  wire  isMinCAlign = rawA_isZero | rawB_isZero | $signed(sNatCAlignDist) < 11'sh0; // @[MulAddRecFN.scala 109:50]
  wire  CIsDominant = hi_lo_2 & (isMinCAlign | posNatCAlignDist <= 10'h18); // @[MulAddRecFN.scala 111:23]
  wire [6:0] _T_55 = posNatCAlignDist < 10'h4a ? posNatCAlignDist[6:0] : 7'h4a; // @[MulAddRecFN.scala 115:16]
  wire [6:0] CAlignDist = isMinCAlign ? 7'h0 : _T_55; // @[MulAddRecFN.scala 113:12]
  wire [24:0] _T_56 = ~rawC_sig; // @[MulAddRecFN.scala 121:28]
  wire [24:0] hi_3 = doSubMags ? _T_56 : rawC_sig; // @[MulAddRecFN.scala 121:16]
  wire [52:0] lo_3 = doSubMags ? 53'h1fffffffffffff : 53'h0; // @[Bitwise.scala 72:12]
  wire [77:0] _T_59 = {hi_3,lo_3}; // @[MulAddRecFN.scala 123:11]
  wire [77:0] mainAlignedSigC = $signed(_T_59) >>> CAlignDist; // @[MulAddRecFN.scala 123:17]
  wire [26:0] _T_60 = {rawC_sig, 2'h0}; // @[MulAddRecFN.scala 125:30]
  wire  _T_62 = |_T_60[3:0]; // @[primitives.scala 121:54]
  wire  _T_64 = |_T_60[7:4]; // @[primitives.scala 121:54]
  wire  _T_66 = |_T_60[11:8]; // @[primitives.scala 121:54]
  wire  _T_68 = |_T_60[15:12]; // @[primitives.scala 121:54]
  wire  _T_70 = |_T_60[19:16]; // @[primitives.scala 121:54]
  wire  _T_72 = |_T_60[23:20]; // @[primitives.scala 121:54]
  wire  _T_74 = |_T_60[26:24]; // @[primitives.scala 124:57]
  wire [6:0] _T_75 = {_T_74,_T_72,_T_70,_T_68,_T_66,_T_64,_T_62}; // @[primitives.scala 125:20]
  wire [32:0] _T_77 = 33'sh100000000 >>> CAlignDist[6:2]; // @[primitives.scala 77:58]
  wire  hi_5 = _T_77[14]; // @[Bitwise.scala 109:18]
  wire  lo_5 = _T_77[15]; // @[Bitwise.scala 109:44]
  wire  hi_7 = _T_77[16]; // @[Bitwise.scala 109:18]
  wire  lo_6 = _T_77[17]; // @[Bitwise.scala 109:44]
  wire  hi_9 = _T_77[18]; // @[Bitwise.scala 109:18]
  wire  lo_8 = _T_77[19]; // @[Bitwise.scala 109:44]
  wire [5:0] _T_83 = {hi_5,lo_5,hi_7,lo_6,hi_9,lo_8}; // @[Cat.scala 30:58]
  wire [6:0] _GEN_1 = {{1'd0}, _T_83}; // @[MulAddRecFN.scala 125:68]
  wire [6:0] _T_84 = _T_75 & _GEN_1; // @[MulAddRecFN.scala 125:68]
  wire  reduced4CExtra = |_T_84; // @[MulAddRecFN.scala 133:11]
  wire  _T_89 = &mainAlignedSigC[2:0] & ~reduced4CExtra; // @[MulAddRecFN.scala 137:44]
  wire  _T_92 = |mainAlignedSigC[2:0] | reduced4CExtra; // @[MulAddRecFN.scala 138:44]
  wire  lo_10 = doSubMags ? _T_89 : _T_92; // @[MulAddRecFN.scala 136:16]
  wire [74:0] hi_10 = mainAlignedSigC[77:3]; // @[Cat.scala 30:58]
  wire [75:0] alignedSigC = {hi_10,lo_10}; // @[Cat.scala 30:58]
  wire  _T_96 = rawA_isNaN & ~rawA_sig[22]; // @[common.scala 81:46]
  wire  _T_99 = rawB_isNaN & ~rawB_sig[22]; // @[common.scala 81:46]
  wire  _T_103 = rawC_isNaN & ~rawC_sig[22]; // @[common.scala 81:46]
  wire [10:0] _T_108 = $signed(sExpAlignedProd) - 11'sh18; // @[MulAddRecFN.scala 161:53]
  wire [10:0] _T_109 = CIsDominant ? $signed({{1{rawC_sExp[9]}},rawC_sExp}) : $signed(_T_108); // @[MulAddRecFN.scala 161:12]
  assign io_mulAddA = rawA_sig[23:0]; // @[MulAddRecFN.scala 144:16]
  assign io_mulAddB = rawB_sig[23:0]; // @[MulAddRecFN.scala 145:16]
  assign io_mulAddC = alignedSigC[48:1]; // @[MulAddRecFN.scala 146:30]
  assign io_toPostMul_isSigNaNAny = _T_96 | _T_99 | _T_103; // @[MulAddRecFN.scala 149:58]
  assign io_toPostMul_isNaNAOrB = rawA_isNaN | rawB_isNaN; // @[MulAddRecFN.scala 151:42]
  assign io_toPostMul_isInfA = _T_4 & ~io_a[29]; // @[rawFloatFromRecFN.scala 56:33]
  assign io_toPostMul_isZeroA = io_a[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  assign io_toPostMul_isInfB = _T_17 & ~io_b[29]; // @[rawFloatFromRecFN.scala 56:33]
  assign io_toPostMul_isZeroB = io_b[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  assign io_toPostMul_signProd = rawA_sign ^ rawB_sign; // @[MulAddRecFN.scala 98:30]
  assign io_toPostMul_isNaNC = _T_30 & io_c[29]; // @[rawFloatFromRecFN.scala 55:33]
  assign io_toPostMul_isInfC = _T_30 & ~io_c[29]; // @[rawFloatFromRecFN.scala 56:33]
  assign io_toPostMul_isZeroC = io_c[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  assign io_toPostMul_sExpSum = _T_109[9:0]; // @[MulAddRecFN.scala 160:28]
  assign io_toPostMul_doSubMags = signProd ^ rawC_sign; // @[MulAddRecFN.scala 103:30]
  assign io_toPostMul_CIsDominant = hi_lo_2 & (isMinCAlign | posNatCAlignDist <= 10'h18); // @[MulAddRecFN.scala 111:23]
  assign io_toPostMul_CDom_CAlignDist = CAlignDist[4:0]; // @[MulAddRecFN.scala 164:47]
  assign io_toPostMul_highAlignedSigC = alignedSigC[74:49]; // @[MulAddRecFN.scala 166:20]
  assign io_toPostMul_bit0AlignedSigC = alignedSigC[0]; // @[MulAddRecFN.scala 167:48]
endmodule
module MulAddRecFNToRaw_postMul(
  input         io_fromPreMul_isSigNaNAny,
  input         io_fromPreMul_isNaNAOrB,
  input         io_fromPreMul_isInfA,
  input         io_fromPreMul_isZeroA,
  input         io_fromPreMul_isInfB,
  input         io_fromPreMul_isZeroB,
  input         io_fromPreMul_signProd,
  input         io_fromPreMul_isNaNC,
  input         io_fromPreMul_isInfC,
  input         io_fromPreMul_isZeroC,
  input  [9:0]  io_fromPreMul_sExpSum,
  input         io_fromPreMul_doSubMags,
  input         io_fromPreMul_CIsDominant,
  input  [4:0]  io_fromPreMul_CDom_CAlignDist,
  input  [25:0] io_fromPreMul_highAlignedSigC,
  input         io_fromPreMul_bit0AlignedSigC,
  input  [48:0] io_mulAddResult,
  output        io_invalidExc,
  output        io_rawOut_isNaN,
  output        io_rawOut_isInf,
  output        io_rawOut_isZero,
  output        io_rawOut_sign,
  output [9:0]  io_rawOut_sExp,
  output [26:0] io_rawOut_sig
);
  wire  CDom_sign = io_fromPreMul_signProd ^ io_fromPreMul_doSubMags; // @[MulAddRecFN.scala 192:42]
  wire [25:0] _T_2 = io_fromPreMul_highAlignedSigC + 26'h1; // @[MulAddRecFN.scala 195:47]
  wire [25:0] hi_hi = io_mulAddResult[48] ? _T_2 : io_fromPreMul_highAlignedSigC; // @[MulAddRecFN.scala 194:16]
  wire [47:0] hi_lo = io_mulAddResult[47:0]; // @[MulAddRecFN.scala 198:28]
  wire [74:0] sigSum = {hi_hi,hi_lo,io_fromPreMul_bit0AlignedSigC}; // @[Cat.scala 30:58]
  wire [1:0] _T_3 = {1'b0,$signed(io_fromPreMul_doSubMags)}; // @[MulAddRecFN.scala 205:69]
  wire [9:0] _GEN_0 = {{8{_T_3[1]}},_T_3}; // @[MulAddRecFN.scala 205:43]
  wire [9:0] CDom_sExp = $signed(io_fromPreMul_sExpSum) - $signed(_GEN_0); // @[MulAddRecFN.scala 205:43]
  wire [49:0] _T_7 = ~sigSum[74:25]; // @[MulAddRecFN.scala 208:13]
  wire [1:0] hi_lo_1 = io_fromPreMul_highAlignedSigC[25:24]; // @[MulAddRecFN.scala 211:46]
  wire [46:0] lo = sigSum[72:26]; // @[MulAddRecFN.scala 212:23]
  wire [49:0] _T_8 = {1'h0,hi_lo_1,lo}; // @[Cat.scala 30:58]
  wire [49:0] CDom_absSigSum = io_fromPreMul_doSubMags ? _T_7 : _T_8; // @[MulAddRecFN.scala 207:12]
  wire [23:0] _T_10 = ~sigSum[24:1]; // @[MulAddRecFN.scala 217:14]
  wire  _T_11 = |_T_10; // @[MulAddRecFN.scala 217:36]
  wire  _T_13 = |sigSum[25:1]; // @[MulAddRecFN.scala 218:37]
  wire  CDom_absSigSumExtra = io_fromPreMul_doSubMags ? _T_11 : _T_13; // @[MulAddRecFN.scala 216:12]
  wire [80:0] _GEN_1 = {{31'd0}, CDom_absSigSum}; // @[MulAddRecFN.scala 221:24]
  wire [80:0] _T_14 = _GEN_1 << io_fromPreMul_CDom_CAlignDist; // @[MulAddRecFN.scala 221:24]
  wire [28:0] CDom_mainSig = _T_14[49:21]; // @[MulAddRecFN.scala 221:56]
  wire [26:0] _T_16 = {CDom_absSigSum[23:0], 3'h0}; // @[MulAddRecFN.scala 224:53]
  wire  _T_18 = |_T_16[3:0]; // @[primitives.scala 121:54]
  wire  _T_20 = |_T_16[7:4]; // @[primitives.scala 121:54]
  wire  _T_22 = |_T_16[11:8]; // @[primitives.scala 121:54]
  wire  _T_24 = |_T_16[15:12]; // @[primitives.scala 121:54]
  wire  _T_26 = |_T_16[19:16]; // @[primitives.scala 121:54]
  wire  _T_28 = |_T_16[23:20]; // @[primitives.scala 121:54]
  wire  _T_30 = |_T_16[26:24]; // @[primitives.scala 124:57]
  wire [6:0] _T_31 = {_T_30,_T_28,_T_26,_T_24,_T_22,_T_20,_T_18}; // @[primitives.scala 125:20]
  wire [2:0] _T_33 = ~io_fromPreMul_CDom_CAlignDist[4:2]; // @[primitives.scala 51:21]
  wire [8:0] _T_34 = 9'sh100 >>> _T_33; // @[primitives.scala 77:58]
  wire  hi_3 = _T_34[1]; // @[Bitwise.scala 109:18]
  wire  lo_2 = _T_34[2]; // @[Bitwise.scala 109:44]
  wire  hi_5 = _T_34[3]; // @[Bitwise.scala 109:18]
  wire  lo_3 = _T_34[4]; // @[Bitwise.scala 109:44]
  wire  hi_7 = _T_34[5]; // @[Bitwise.scala 109:18]
  wire  lo_5 = _T_34[6]; // @[Bitwise.scala 109:44]
  wire [5:0] _T_40 = {hi_3,lo_2,hi_5,lo_3,hi_7,lo_5}; // @[Cat.scala 30:58]
  wire [6:0] _GEN_2 = {{1'd0}, _T_40}; // @[MulAddRecFN.scala 224:72]
  wire [6:0] _T_41 = _T_31 & _GEN_2; // @[MulAddRecFN.scala 224:72]
  wire  CDom_reduced4SigExtra = |_T_41; // @[MulAddRecFN.scala 225:73]
  wire [25:0] hi_8 = CDom_mainSig[28:3]; // @[MulAddRecFN.scala 227:25]
  wire  lo_7 = |CDom_mainSig[2:0] | CDom_reduced4SigExtra | CDom_absSigSumExtra; // @[MulAddRecFN.scala 228:61]
  wire [26:0] CDom_sig = {hi_8,lo_7}; // @[Cat.scala 30:58]
  wire  notCDom_signSigSum = sigSum[51]; // @[MulAddRecFN.scala 234:36]
  wire [50:0] _T_46 = ~sigSum[50:0]; // @[MulAddRecFN.scala 237:13]
  wire [50:0] _GEN_3 = {{50'd0}, io_fromPreMul_doSubMags}; // @[MulAddRecFN.scala 238:41]
  wire [50:0] _T_49 = sigSum[50:0] + _GEN_3; // @[MulAddRecFN.scala 238:41]
  wire [50:0] notCDom_absSigSum = notCDom_signSigSum ? _T_46 : _T_49; // @[MulAddRecFN.scala 236:12]
  wire  _T_51 = |notCDom_absSigSum[1:0]; // @[primitives.scala 104:54]
  wire  _T_53 = |notCDom_absSigSum[3:2]; // @[primitives.scala 104:54]
  wire  _T_55 = |notCDom_absSigSum[5:4]; // @[primitives.scala 104:54]
  wire  _T_57 = |notCDom_absSigSum[7:6]; // @[primitives.scala 104:54]
  wire  _T_59 = |notCDom_absSigSum[9:8]; // @[primitives.scala 104:54]
  wire  _T_61 = |notCDom_absSigSum[11:10]; // @[primitives.scala 104:54]
  wire  _T_63 = |notCDom_absSigSum[13:12]; // @[primitives.scala 104:54]
  wire  _T_65 = |notCDom_absSigSum[15:14]; // @[primitives.scala 104:54]
  wire  _T_67 = |notCDom_absSigSum[17:16]; // @[primitives.scala 104:54]
  wire  _T_69 = |notCDom_absSigSum[19:18]; // @[primitives.scala 104:54]
  wire  _T_71 = |notCDom_absSigSum[21:20]; // @[primitives.scala 104:54]
  wire  _T_73 = |notCDom_absSigSum[23:22]; // @[primitives.scala 104:54]
  wire  _T_75 = |notCDom_absSigSum[25:24]; // @[primitives.scala 104:54]
  wire  _T_77 = |notCDom_absSigSum[27:26]; // @[primitives.scala 104:54]
  wire  _T_79 = |notCDom_absSigSum[29:28]; // @[primitives.scala 104:54]
  wire  _T_81 = |notCDom_absSigSum[31:30]; // @[primitives.scala 104:54]
  wire  _T_83 = |notCDom_absSigSum[33:32]; // @[primitives.scala 104:54]
  wire  _T_85 = |notCDom_absSigSum[35:34]; // @[primitives.scala 104:54]
  wire  _T_87 = |notCDom_absSigSum[37:36]; // @[primitives.scala 104:54]
  wire  _T_89 = |notCDom_absSigSum[39:38]; // @[primitives.scala 104:54]
  wire  _T_91 = |notCDom_absSigSum[41:40]; // @[primitives.scala 104:54]
  wire  _T_93 = |notCDom_absSigSum[43:42]; // @[primitives.scala 104:54]
  wire  _T_95 = |notCDom_absSigSum[45:44]; // @[primitives.scala 104:54]
  wire  _T_97 = |notCDom_absSigSum[47:46]; // @[primitives.scala 104:54]
  wire  _T_99 = |notCDom_absSigSum[49:48]; // @[primitives.scala 104:54]
  wire  _T_101 = |notCDom_absSigSum[50]; // @[primitives.scala 107:57]
  wire [5:0] lo_lo = {_T_61,_T_59,_T_57,_T_55,_T_53,_T_51}; // @[primitives.scala 108:20]
  wire [12:0] lo_8 = {_T_75,_T_73,_T_71,_T_69,_T_67,_T_65,_T_63,lo_lo}; // @[primitives.scala 108:20]
  wire [5:0] hi_lo_3 = {_T_87,_T_85,_T_83,_T_81,_T_79,_T_77}; // @[primitives.scala 108:20]
  wire [25:0] notCDom_reduced2AbsSigSum = {_T_101,_T_99,_T_97,_T_95,_T_93,_T_91,_T_89,hi_lo_3,lo_8}; // @[primitives.scala 108:20]
  wire [4:0] _T_128 = notCDom_reduced2AbsSigSum[1] ? 5'h18 : 5'h19; // @[Mux.scala 47:69]
  wire [4:0] _T_129 = notCDom_reduced2AbsSigSum[2] ? 5'h17 : _T_128; // @[Mux.scala 47:69]
  wire [4:0] _T_130 = notCDom_reduced2AbsSigSum[3] ? 5'h16 : _T_129; // @[Mux.scala 47:69]
  wire [4:0] _T_131 = notCDom_reduced2AbsSigSum[4] ? 5'h15 : _T_130; // @[Mux.scala 47:69]
  wire [4:0] _T_132 = notCDom_reduced2AbsSigSum[5] ? 5'h14 : _T_131; // @[Mux.scala 47:69]
  wire [4:0] _T_133 = notCDom_reduced2AbsSigSum[6] ? 5'h13 : _T_132; // @[Mux.scala 47:69]
  wire [4:0] _T_134 = notCDom_reduced2AbsSigSum[7] ? 5'h12 : _T_133; // @[Mux.scala 47:69]
  wire [4:0] _T_135 = notCDom_reduced2AbsSigSum[8] ? 5'h11 : _T_134; // @[Mux.scala 47:69]
  wire [4:0] _T_136 = notCDom_reduced2AbsSigSum[9] ? 5'h10 : _T_135; // @[Mux.scala 47:69]
  wire [4:0] _T_137 = notCDom_reduced2AbsSigSum[10] ? 5'hf : _T_136; // @[Mux.scala 47:69]
  wire [4:0] _T_138 = notCDom_reduced2AbsSigSum[11] ? 5'he : _T_137; // @[Mux.scala 47:69]
  wire [4:0] _T_139 = notCDom_reduced2AbsSigSum[12] ? 5'hd : _T_138; // @[Mux.scala 47:69]
  wire [4:0] _T_140 = notCDom_reduced2AbsSigSum[13] ? 5'hc : _T_139; // @[Mux.scala 47:69]
  wire [4:0] _T_141 = notCDom_reduced2AbsSigSum[14] ? 5'hb : _T_140; // @[Mux.scala 47:69]
  wire [4:0] _T_142 = notCDom_reduced2AbsSigSum[15] ? 5'ha : _T_141; // @[Mux.scala 47:69]
  wire [4:0] _T_143 = notCDom_reduced2AbsSigSum[16] ? 5'h9 : _T_142; // @[Mux.scala 47:69]
  wire [4:0] _T_144 = notCDom_reduced2AbsSigSum[17] ? 5'h8 : _T_143; // @[Mux.scala 47:69]
  wire [4:0] _T_145 = notCDom_reduced2AbsSigSum[18] ? 5'h7 : _T_144; // @[Mux.scala 47:69]
  wire [4:0] _T_146 = notCDom_reduced2AbsSigSum[19] ? 5'h6 : _T_145; // @[Mux.scala 47:69]
  wire [4:0] _T_147 = notCDom_reduced2AbsSigSum[20] ? 5'h5 : _T_146; // @[Mux.scala 47:69]
  wire [4:0] _T_148 = notCDom_reduced2AbsSigSum[21] ? 5'h4 : _T_147; // @[Mux.scala 47:69]
  wire [4:0] _T_149 = notCDom_reduced2AbsSigSum[22] ? 5'h3 : _T_148; // @[Mux.scala 47:69]
  wire [4:0] _T_150 = notCDom_reduced2AbsSigSum[23] ? 5'h2 : _T_149; // @[Mux.scala 47:69]
  wire [4:0] _T_151 = notCDom_reduced2AbsSigSum[24] ? 5'h1 : _T_150; // @[Mux.scala 47:69]
  wire [4:0] notCDom_normDistReduced2 = notCDom_reduced2AbsSigSum[25] ? 5'h0 : _T_151; // @[Mux.scala 47:69]
  wire [5:0] notCDom_nearNormDist = {notCDom_normDistReduced2, 1'h0}; // @[MulAddRecFN.scala 242:56]
  wire [6:0] _T_152 = {1'b0,$signed(notCDom_nearNormDist)}; // @[MulAddRecFN.scala 243:69]
  wire [9:0] _GEN_4 = {{3{_T_152[6]}},_T_152}; // @[MulAddRecFN.scala 243:46]
  wire [9:0] notCDom_sExp = $signed(io_fromPreMul_sExpSum) - $signed(_GEN_4); // @[MulAddRecFN.scala 243:46]
  wire [113:0] _GEN_5 = {{63'd0}, notCDom_absSigSum}; // @[MulAddRecFN.scala 245:27]
  wire [113:0] _T_155 = _GEN_5 << notCDom_nearNormDist; // @[MulAddRecFN.scala 245:27]
  wire [28:0] notCDom_mainSig = _T_155[51:23]; // @[MulAddRecFN.scala 245:50]
  wire  _T_159 = |notCDom_reduced2AbsSigSum[1:0]; // @[primitives.scala 104:54]
  wire  _T_161 = |notCDom_reduced2AbsSigSum[3:2]; // @[primitives.scala 104:54]
  wire  _T_163 = |notCDom_reduced2AbsSigSum[5:4]; // @[primitives.scala 104:54]
  wire  _T_165 = |notCDom_reduced2AbsSigSum[7:6]; // @[primitives.scala 104:54]
  wire  _T_167 = |notCDom_reduced2AbsSigSum[9:8]; // @[primitives.scala 104:54]
  wire  _T_169 = |notCDom_reduced2AbsSigSum[11:10]; // @[primitives.scala 104:54]
  wire  _T_171 = |notCDom_reduced2AbsSigSum[12]; // @[primitives.scala 107:57]
  wire [6:0] _T_172 = {_T_171,_T_169,_T_167,_T_165,_T_163,_T_161,_T_159}; // @[primitives.scala 108:20]
  wire [3:0] _T_174 = ~notCDom_normDistReduced2[4:1]; // @[primitives.scala 51:21]
  wire [16:0] _T_175 = 17'sh10000 >>> _T_174; // @[primitives.scala 77:58]
  wire  hi_11 = _T_175[1]; // @[Bitwise.scala 109:18]
  wire  lo_10 = _T_175[2]; // @[Bitwise.scala 109:44]
  wire  hi_13 = _T_175[3]; // @[Bitwise.scala 109:18]
  wire  lo_11 = _T_175[4]; // @[Bitwise.scala 109:44]
  wire  hi_15 = _T_175[5]; // @[Bitwise.scala 109:18]
  wire  lo_13 = _T_175[6]; // @[Bitwise.scala 109:44]
  wire [5:0] _T_181 = {hi_11,lo_10,hi_13,lo_11,hi_15,lo_13}; // @[Cat.scala 30:58]
  wire [6:0] _GEN_6 = {{1'd0}, _T_181}; // @[MulAddRecFN.scala 249:78]
  wire [6:0] _T_182 = _T_172 & _GEN_6; // @[MulAddRecFN.scala 249:78]
  wire  notCDom_reduced4SigExtra = |_T_182; // @[MulAddRecFN.scala 251:11]
  wire [25:0] hi_16 = notCDom_mainSig[28:3]; // @[MulAddRecFN.scala 253:28]
  wire  lo_15 = |notCDom_mainSig[2:0] | notCDom_reduced4SigExtra; // @[MulAddRecFN.scala 254:39]
  wire [26:0] notCDom_sig = {hi_16,lo_15}; // @[Cat.scala 30:58]
  wire  notCDom_completeCancellation = notCDom_sig[26:25] == 2'h0; // @[MulAddRecFN.scala 257:50]
  wire  _T_186 = io_fromPreMul_signProd ^ notCDom_signSigSum; // @[MulAddRecFN.scala 261:36]
  wire  notCDom_sign = notCDom_completeCancellation ? 1'h0 : _T_186; // @[MulAddRecFN.scala 259:12]
  wire  notNaN_isInfProd = io_fromPreMul_isInfA | io_fromPreMul_isInfB; // @[MulAddRecFN.scala 266:49]
  wire  notNaN_isInfOut = notNaN_isInfProd | io_fromPreMul_isInfC; // @[MulAddRecFN.scala 267:44]
  wire  notNaN_addZeros = (io_fromPreMul_isZeroA | io_fromPreMul_isZeroB) & io_fromPreMul_isZeroC; // @[MulAddRecFN.scala 269:58]
  wire  _T_188 = io_fromPreMul_isInfA & io_fromPreMul_isZeroB; // @[MulAddRecFN.scala 274:31]
  wire  _T_189 = io_fromPreMul_isSigNaNAny | _T_188; // @[MulAddRecFN.scala 273:35]
  wire  _T_190 = io_fromPreMul_isZeroA & io_fromPreMul_isInfB; // @[MulAddRecFN.scala 275:32]
  wire  _T_191 = _T_189 | _T_190; // @[MulAddRecFN.scala 274:57]
  wire  _T_194 = ~io_fromPreMul_isNaNAOrB & notNaN_isInfProd; // @[MulAddRecFN.scala 276:36]
  wire  _T_195 = _T_194 & io_fromPreMul_isInfC; // @[MulAddRecFN.scala 277:61]
  wire  _T_196 = _T_195 & io_fromPreMul_doSubMags; // @[MulAddRecFN.scala 278:35]
  wire  _T_200 = ~io_fromPreMul_CIsDominant & notCDom_completeCancellation; // @[MulAddRecFN.scala 285:42]
  wire  _T_203 = io_fromPreMul_isInfC & CDom_sign; // @[MulAddRecFN.scala 288:31]
  wire  _T_204 = notNaN_isInfProd & io_fromPreMul_signProd | _T_203; // @[MulAddRecFN.scala 287:54]
  wire  _T_207 = notNaN_addZeros & io_fromPreMul_signProd; // @[MulAddRecFN.scala 289:48]
  wire  _T_208 = _T_207 & CDom_sign; // @[MulAddRecFN.scala 290:36]
  wire  _T_209 = _T_204 | _T_208; // @[MulAddRecFN.scala 288:43]
  wire  _T_217 = io_fromPreMul_CIsDominant ? CDom_sign : notCDom_sign; // @[MulAddRecFN.scala 294:17]
  wire  _T_218 = ~notNaN_isInfOut & ~notNaN_addZeros & _T_217; // @[MulAddRecFN.scala 293:49]
  assign io_invalidExc = _T_191 | _T_196; // @[MulAddRecFN.scala 275:57]
  assign io_rawOut_isNaN = io_fromPreMul_isNaNAOrB | io_fromPreMul_isNaNC; // @[MulAddRecFN.scala 280:48]
  assign io_rawOut_isInf = notNaN_isInfProd | io_fromPreMul_isInfC; // @[MulAddRecFN.scala 267:44]
  assign io_rawOut_isZero = notNaN_addZeros | _T_200; // @[MulAddRecFN.scala 284:25]
  assign io_rawOut_sign = _T_209 | _T_218; // @[MulAddRecFN.scala 292:50]
  assign io_rawOut_sExp = io_fromPreMul_CIsDominant ? $signed(CDom_sExp) : $signed(notCDom_sExp); // @[MulAddRecFN.scala 295:26]
  assign io_rawOut_sig = io_fromPreMul_CIsDominant ? CDom_sig : notCDom_sig; // @[MulAddRecFN.scala 296:25]
endmodule
module RoundAnyRawFNToRecFN(
  input         io_invalidExc,
  input         io_in_isNaN,
  input         io_in_isInf,
  input         io_in_isZero,
  input         io_in_sign,
  input  [9:0]  io_in_sExp,
  input  [26:0] io_in_sig,
  output [32:0] io_out
);
  wire  doShiftSigDown1 = io_in_sig[26]; // @[RoundAnyRawFNToRecFN.scala 118:61]
  wire [8:0] _T_4 = ~io_in_sExp[8:0]; // @[primitives.scala 51:21]
  wire [64:0] _T_11 = 65'sh10000000000000000 >>> _T_4[5:0]; // @[primitives.scala 77:58]
  wire [15:0] _T_17 = {{8'd0}, _T_11[57:50]}; // @[Bitwise.scala 103:31]
  wire [15:0] _T_19 = {_T_11[49:42], 8'h0}; // @[Bitwise.scala 103:65]
  wire [15:0] _T_21 = _T_19 & 16'hff00; // @[Bitwise.scala 103:75]
  wire [15:0] _T_22 = _T_17 | _T_21; // @[Bitwise.scala 103:39]
  wire [15:0] _GEN_0 = {{4'd0}, _T_22[15:4]}; // @[Bitwise.scala 103:31]
  wire [15:0] _T_27 = _GEN_0 & 16'hf0f; // @[Bitwise.scala 103:31]
  wire [15:0] _T_29 = {_T_22[11:0], 4'h0}; // @[Bitwise.scala 103:65]
  wire [15:0] _T_31 = _T_29 & 16'hf0f0; // @[Bitwise.scala 103:75]
  wire [15:0] _T_32 = _T_27 | _T_31; // @[Bitwise.scala 103:39]
  wire [15:0] _GEN_1 = {{2'd0}, _T_32[15:2]}; // @[Bitwise.scala 103:31]
  wire [15:0] _T_37 = _GEN_1 & 16'h3333; // @[Bitwise.scala 103:31]
  wire [15:0] _T_39 = {_T_32[13:0], 2'h0}; // @[Bitwise.scala 103:65]
  wire [15:0] _T_41 = _T_39 & 16'hcccc; // @[Bitwise.scala 103:75]
  wire [15:0] _T_42 = _T_37 | _T_41; // @[Bitwise.scala 103:39]
  wire [15:0] _GEN_2 = {{1'd0}, _T_42[15:1]}; // @[Bitwise.scala 103:31]
  wire [15:0] _T_47 = _GEN_2 & 16'h5555; // @[Bitwise.scala 103:31]
  wire [15:0] _T_49 = {_T_42[14:0], 1'h0}; // @[Bitwise.scala 103:65]
  wire [15:0] _T_51 = _T_49 & 16'haaaa; // @[Bitwise.scala 103:75]
  wire [15:0] hi = _T_47 | _T_51; // @[Bitwise.scala 103:39]
  wire  hi_1 = _T_11[58]; // @[Bitwise.scala 109:18]
  wire  lo = _T_11[59]; // @[Bitwise.scala 109:44]
  wire  hi_3 = _T_11[60]; // @[Bitwise.scala 109:18]
  wire  lo_1 = _T_11[61]; // @[Bitwise.scala 109:44]
  wire  hi_5 = _T_11[62]; // @[Bitwise.scala 109:18]
  wire  lo_3 = _T_11[63]; // @[Bitwise.scala 109:44]
  wire [21:0] _T_57 = {hi,hi_1,lo,hi_3,lo_1,hi_5,lo_3}; // @[Cat.scala 30:58]
  wire [21:0] _T_58 = ~_T_57; // @[primitives.scala 74:36]
  wire [21:0] _T_59 = _T_4[6] ? 22'h0 : _T_58; // @[primitives.scala 74:21]
  wire [21:0] hi_6 = ~_T_59; // @[primitives.scala 74:17]
  wire [24:0] _T_60 = {hi_6,3'h7}; // @[Cat.scala 30:58]
  wire  hi_7 = _T_11[0]; // @[Bitwise.scala 109:18]
  wire  lo_6 = _T_11[1]; // @[Bitwise.scala 109:44]
  wire  lo_7 = _T_11[2]; // @[Bitwise.scala 109:44]
  wire [2:0] _T_66 = {hi_7,lo_6,lo_7}; // @[Cat.scala 30:58]
  wire [2:0] _T_67 = _T_4[6] ? _T_66 : 3'h0; // @[primitives.scala 61:24]
  wire [24:0] _T_68 = _T_4[7] ? _T_60 : {{22'd0}, _T_67}; // @[primitives.scala 66:24]
  wire [24:0] _T_69 = _T_4[8] ? _T_68 : 25'h0; // @[primitives.scala 61:24]
  wire [24:0] _GEN_3 = {{24'd0}, doShiftSigDown1}; // @[RoundAnyRawFNToRecFN.scala 157:23]
  wire [24:0] hi_9 = _T_69 | _GEN_3; // @[RoundAnyRawFNToRecFN.scala 157:23]
  wire [26:0] _T_70 = {hi_9,2'h3}; // @[Cat.scala 30:58]
  wire [25:0] lo_8 = _T_70[26:1]; // @[RoundAnyRawFNToRecFN.scala 160:57]
  wire [26:0] _T_71 = {1'h0,lo_8}; // @[Cat.scala 30:58]
  wire [26:0] _T_72 = ~_T_71; // @[RoundAnyRawFNToRecFN.scala 161:28]
  wire [26:0] _T_73 = _T_72 & _T_70; // @[RoundAnyRawFNToRecFN.scala 161:46]
  wire [26:0] _T_74 = io_in_sig & _T_73; // @[RoundAnyRawFNToRecFN.scala 162:40]
  wire  _T_75 = |_T_74; // @[RoundAnyRawFNToRecFN.scala 162:56]
  wire [26:0] _T_76 = io_in_sig & _T_71; // @[RoundAnyRawFNToRecFN.scala 163:42]
  wire  _T_77 = |_T_76; // @[RoundAnyRawFNToRecFN.scala 163:62]
  wire [26:0] _T_83 = io_in_sig | _T_70; // @[RoundAnyRawFNToRecFN.scala 172:32]
  wire [25:0] _T_85 = _T_83[26:2] + 25'h1; // @[RoundAnyRawFNToRecFN.scala 172:49]
  wire  _T_87 = ~_T_77; // @[RoundAnyRawFNToRecFN.scala 174:30]
  wire [25:0] _T_90 = _T_75 & _T_87 ? lo_8 : 26'h0; // @[RoundAnyRawFNToRecFN.scala 173:25]
  wire [25:0] _T_91 = ~_T_90; // @[RoundAnyRawFNToRecFN.scala 173:21]
  wire [25:0] _T_92 = _T_85 & _T_91; // @[RoundAnyRawFNToRecFN.scala 172:61]
  wire [26:0] _T_93 = ~_T_70; // @[RoundAnyRawFNToRecFN.scala 178:32]
  wire [26:0] _T_94 = io_in_sig & _T_93; // @[RoundAnyRawFNToRecFN.scala 178:30]
  wire [25:0] _T_99 = {{1'd0}, _T_94[26:2]}; // @[RoundAnyRawFNToRecFN.scala 178:47]
  wire [25:0] _T_100 = _T_75 ? _T_92 : _T_99; // @[RoundAnyRawFNToRecFN.scala 171:16]
  wire [2:0] _T_102 = {1'b0,$signed(_T_100[25:24])}; // @[RoundAnyRawFNToRecFN.scala 183:69]
  wire [9:0] _GEN_4 = {{7{_T_102[2]}},_T_102}; // @[RoundAnyRawFNToRecFN.scala 183:40]
  wire [10:0] _T_103 = $signed(io_in_sExp) + $signed(_GEN_4); // @[RoundAnyRawFNToRecFN.scala 183:40]
  wire [8:0] common_expOut = _T_103[8:0]; // @[RoundAnyRawFNToRecFN.scala 185:37]
  wire [22:0] common_fractOut = doShiftSigDown1 ? _T_100[23:1] : _T_100[22:0]; // @[RoundAnyRawFNToRecFN.scala 187:16]
  wire [3:0] _T_108 = _T_103[10:7]; // @[RoundAnyRawFNToRecFN.scala 194:30]
  wire  common_overflow = $signed(_T_108) >= 4'sh3; // @[RoundAnyRawFNToRecFN.scala 194:50]
  wire  common_totalUnderflow = $signed(_T_103) < 11'sh6b; // @[RoundAnyRawFNToRecFN.scala 198:31]
  wire  isNaNOut = io_invalidExc | io_in_isNaN; // @[RoundAnyRawFNToRecFN.scala 233:34]
  wire  commonCase = ~isNaNOut & ~io_in_isInf & ~io_in_isZero; // @[RoundAnyRawFNToRecFN.scala 235:61]
  wire  overflow = commonCase & common_overflow; // @[RoundAnyRawFNToRecFN.scala 236:32]
  wire  notNaN_isInfOut = io_in_isInf | overflow; // @[RoundAnyRawFNToRecFN.scala 246:32]
  wire  signOut = isNaNOut ? 1'h0 : io_in_sign; // @[RoundAnyRawFNToRecFN.scala 248:22]
  wire [8:0] _T_157 = io_in_isZero | common_totalUnderflow ? 9'h1c0 : 9'h0; // @[RoundAnyRawFNToRecFN.scala 251:18]
  wire [8:0] _T_158 = ~_T_157; // @[RoundAnyRawFNToRecFN.scala 251:14]
  wire [8:0] _T_159 = common_expOut & _T_158; // @[RoundAnyRawFNToRecFN.scala 250:24]
  wire [8:0] _T_167 = notNaN_isInfOut ? 9'h40 : 9'h0; // @[RoundAnyRawFNToRecFN.scala 263:18]
  wire [8:0] _T_168 = ~_T_167; // @[RoundAnyRawFNToRecFN.scala 263:14]
  wire [8:0] _T_169 = _T_159 & _T_168; // @[RoundAnyRawFNToRecFN.scala 262:17]
  wire [8:0] _T_174 = notNaN_isInfOut ? 9'h180 : 9'h0; // @[RoundAnyRawFNToRecFN.scala 275:16]
  wire [8:0] _T_175 = _T_169 | _T_174; // @[RoundAnyRawFNToRecFN.scala 274:15]
  wire [8:0] _T_176 = isNaNOut ? 9'h1c0 : 9'h0; // @[RoundAnyRawFNToRecFN.scala 276:16]
  wire [8:0] expOut = _T_175 | _T_176; // @[RoundAnyRawFNToRecFN.scala 275:77]
  wire [22:0] _T_179 = isNaNOut ? 23'h400000 : 23'h0; // @[RoundAnyRawFNToRecFN.scala 279:16]
  wire [22:0] fractOut = isNaNOut | io_in_isZero | common_totalUnderflow ? _T_179 : common_fractOut; // @[RoundAnyRawFNToRecFN.scala 278:12]
  wire [9:0] hi_10 = {signOut,expOut}; // @[Cat.scala 30:58]
  assign io_out = {hi_10,fractOut}; // @[Cat.scala 30:58]
endmodule
module RoundRawFNToRecFN(
  input         io_invalidExc,
  input         io_in_isNaN,
  input         io_in_isInf,
  input         io_in_isZero,
  input         io_in_sign,
  input  [9:0]  io_in_sExp,
  input  [26:0] io_in_sig,
  output [32:0] io_out
);
  wire  roundAnyRawFNToRecFN_io_invalidExc; // @[RoundAnyRawFNToRecFN.scala 307:15]
  wire  roundAnyRawFNToRecFN_io_in_isNaN; // @[RoundAnyRawFNToRecFN.scala 307:15]
  wire  roundAnyRawFNToRecFN_io_in_isInf; // @[RoundAnyRawFNToRecFN.scala 307:15]
  wire  roundAnyRawFNToRecFN_io_in_isZero; // @[RoundAnyRawFNToRecFN.scala 307:15]
  wire  roundAnyRawFNToRecFN_io_in_sign; // @[RoundAnyRawFNToRecFN.scala 307:15]
  wire [9:0] roundAnyRawFNToRecFN_io_in_sExp; // @[RoundAnyRawFNToRecFN.scala 307:15]
  wire [26:0] roundAnyRawFNToRecFN_io_in_sig; // @[RoundAnyRawFNToRecFN.scala 307:15]
  wire [32:0] roundAnyRawFNToRecFN_io_out; // @[RoundAnyRawFNToRecFN.scala 307:15]
  RoundAnyRawFNToRecFN roundAnyRawFNToRecFN ( // @[RoundAnyRawFNToRecFN.scala 307:15]
    .io_invalidExc(roundAnyRawFNToRecFN_io_invalidExc),
    .io_in_isNaN(roundAnyRawFNToRecFN_io_in_isNaN),
    .io_in_isInf(roundAnyRawFNToRecFN_io_in_isInf),
    .io_in_isZero(roundAnyRawFNToRecFN_io_in_isZero),
    .io_in_sign(roundAnyRawFNToRecFN_io_in_sign),
    .io_in_sExp(roundAnyRawFNToRecFN_io_in_sExp),
    .io_in_sig(roundAnyRawFNToRecFN_io_in_sig),
    .io_out(roundAnyRawFNToRecFN_io_out)
  );
  assign io_out = roundAnyRawFNToRecFN_io_out; // @[RoundAnyRawFNToRecFN.scala 315:23]
  assign roundAnyRawFNToRecFN_io_invalidExc = io_invalidExc; // @[RoundAnyRawFNToRecFN.scala 310:44]
  assign roundAnyRawFNToRecFN_io_in_isNaN = io_in_isNaN; // @[RoundAnyRawFNToRecFN.scala 312:44]
  assign roundAnyRawFNToRecFN_io_in_isInf = io_in_isInf; // @[RoundAnyRawFNToRecFN.scala 312:44]
  assign roundAnyRawFNToRecFN_io_in_isZero = io_in_isZero; // @[RoundAnyRawFNToRecFN.scala 312:44]
  assign roundAnyRawFNToRecFN_io_in_sign = io_in_sign; // @[RoundAnyRawFNToRecFN.scala 312:44]
  assign roundAnyRawFNToRecFN_io_in_sExp = io_in_sExp; // @[RoundAnyRawFNToRecFN.scala 312:44]
  assign roundAnyRawFNToRecFN_io_in_sig = io_in_sig; // @[RoundAnyRawFNToRecFN.scala 312:44]
endmodule
module MY_MULADD(
  input         clock,
  input         reset,
  input  [31:0] io_a,
  input  [31:0] io_b,
  input  [31:0] io_c,
  output [31:0] io_out
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [63:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
  reg [31:0] _RAND_5;
  reg [31:0] _RAND_6;
  reg [31:0] _RAND_7;
  reg [31:0] _RAND_8;
  reg [31:0] _RAND_9;
  reg [31:0] _RAND_10;
  reg [31:0] _RAND_11;
  reg [31:0] _RAND_12;
  reg [31:0] _RAND_13;
  reg [31:0] _RAND_14;
  reg [31:0] _RAND_15;
  reg [31:0] _RAND_16;
  reg [31:0] _RAND_17;
  reg [31:0] _RAND_18;
`endif // RANDOMIZE_REG_INIT
  wire [32:0] mulAddRecFNToRaw_preMul_io_a; // @[FMULADD_1G_2.scala 36:15]
  wire [32:0] mulAddRecFNToRaw_preMul_io_b; // @[FMULADD_1G_2.scala 36:15]
  wire [32:0] mulAddRecFNToRaw_preMul_io_c; // @[FMULADD_1G_2.scala 36:15]
  wire [23:0] mulAddRecFNToRaw_preMul_io_mulAddA; // @[FMULADD_1G_2.scala 36:15]
  wire [23:0] mulAddRecFNToRaw_preMul_io_mulAddB; // @[FMULADD_1G_2.scala 36:15]
  wire [47:0] mulAddRecFNToRaw_preMul_io_mulAddC; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isSigNaNAny; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isNaNAOrB; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isInfA; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isZeroA; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isInfB; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isZeroB; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_signProd; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isNaNC; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isInfC; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_isZeroC; // @[FMULADD_1G_2.scala 36:15]
  wire [9:0] mulAddRecFNToRaw_preMul_io_toPostMul_sExpSum; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_doSubMags; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_CIsDominant; // @[FMULADD_1G_2.scala 36:15]
  wire [4:0] mulAddRecFNToRaw_preMul_io_toPostMul_CDom_CAlignDist; // @[FMULADD_1G_2.scala 36:15]
  wire [25:0] mulAddRecFNToRaw_preMul_io_toPostMul_highAlignedSigC; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_preMul_io_toPostMul_bit0AlignedSigC; // @[FMULADD_1G_2.scala 36:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isSigNaNAny; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isNaNAOrB; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isInfA; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroA; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isInfB; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroB; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_signProd; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isNaNC; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isInfC; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroC; // @[FMULADD_1G_2.scala 116:15]
  wire [9:0] mulAddRecFNToRaw_postMul_io_fromPreMul_sExpSum; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_doSubMags; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_CIsDominant; // @[FMULADD_1G_2.scala 116:15]
  wire [4:0] mulAddRecFNToRaw_postMul_io_fromPreMul_CDom_CAlignDist; // @[FMULADD_1G_2.scala 116:15]
  wire [25:0] mulAddRecFNToRaw_postMul_io_fromPreMul_highAlignedSigC; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_fromPreMul_bit0AlignedSigC; // @[FMULADD_1G_2.scala 116:15]
  wire [48:0] mulAddRecFNToRaw_postMul_io_mulAddResult; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_invalidExc; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_rawOut_isNaN; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_rawOut_isInf; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_rawOut_isZero; // @[FMULADD_1G_2.scala 116:15]
  wire  mulAddRecFNToRaw_postMul_io_rawOut_sign; // @[FMULADD_1G_2.scala 116:15]
  wire [9:0] mulAddRecFNToRaw_postMul_io_rawOut_sExp; // @[FMULADD_1G_2.scala 116:15]
  wire [26:0] mulAddRecFNToRaw_postMul_io_rawOut_sig; // @[FMULADD_1G_2.scala 116:15]
  wire  roundRawFNToRecFN_io_invalidExc; // @[FMULADD_1G_2.scala 137:15]
  wire  roundRawFNToRecFN_io_in_isNaN; // @[FMULADD_1G_2.scala 137:15]
  wire  roundRawFNToRecFN_io_in_isInf; // @[FMULADD_1G_2.scala 137:15]
  wire  roundRawFNToRecFN_io_in_isZero; // @[FMULADD_1G_2.scala 137:15]
  wire  roundRawFNToRecFN_io_in_sign; // @[FMULADD_1G_2.scala 137:15]
  wire [9:0] roundRawFNToRecFN_io_in_sExp; // @[FMULADD_1G_2.scala 137:15]
  wire [26:0] roundRawFNToRecFN_io_in_sig; // @[FMULADD_1G_2.scala 137:15]
  wire [32:0] roundRawFNToRecFN_io_out; // @[FMULADD_1G_2.scala 137:15]
  reg [23:0] premul_a; // @[FMULADD_1G_2.scala 15:37]
  reg [23:0] premul_b; // @[FMULADD_1G_2.scala 16:37]
  reg [47:0] premul_c; // @[FMULADD_1G_2.scala 17:37]
  reg  isSigNaNAny; // @[FMULADD_1G_2.scala 18:33]
  reg  isNaNAOrB; // @[FMULADD_1G_2.scala 19:34]
  reg  isInfA; // @[FMULADD_1G_2.scala 20:43]
  reg  isZeroA; // @[FMULADD_1G_2.scala 21:40]
  reg  isInfB; // @[FMULADD_1G_2.scala 22:43]
  reg  isZeroB; // @[FMULADD_1G_2.scala 23:40]
  reg  signProd; // @[FMULADD_1G_2.scala 24:38]
  reg  isNaNC; // @[FMULADD_1G_2.scala 25:39]
  reg  isInfC; // @[FMULADD_1G_2.scala 26:42]
  reg  isZeroC; // @[FMULADD_1G_2.scala 27:39]
  reg [9:0] sExpSum; // @[FMULADD_1G_2.scala 28:36]
  reg  doSubMags; // @[FMULADD_1G_2.scala 29:33]
  reg  CIsDominant; // @[FMULADD_1G_2.scala 30:33]
  reg [4:0] CDom_CAlignDist; // @[FMULADD_1G_2.scala 31:34]
  reg [25:0] highAlignedSigC; // @[FMULADD_1G_2.scala 32:37]
  reg  bit0AlignedSigC; // @[FMULADD_1G_2.scala 33:38]
  wire  _T_3 = io_a[30:23] == 8'h0; // @[rawFloatFromFN.scala 50:34]
  wire  _T_4 = io_a[22:0] == 23'h0; // @[rawFloatFromFN.scala 51:38]
  wire [4:0] _T_28 = io_a[1] ? 5'h15 : 5'h16; // @[Mux.scala 47:69]
  wire [4:0] _T_29 = io_a[2] ? 5'h14 : _T_28; // @[Mux.scala 47:69]
  wire [4:0] _T_30 = io_a[3] ? 5'h13 : _T_29; // @[Mux.scala 47:69]
  wire [4:0] _T_31 = io_a[4] ? 5'h12 : _T_30; // @[Mux.scala 47:69]
  wire [4:0] _T_32 = io_a[5] ? 5'h11 : _T_31; // @[Mux.scala 47:69]
  wire [4:0] _T_33 = io_a[6] ? 5'h10 : _T_32; // @[Mux.scala 47:69]
  wire [4:0] _T_34 = io_a[7] ? 5'hf : _T_33; // @[Mux.scala 47:69]
  wire [4:0] _T_35 = io_a[8] ? 5'he : _T_34; // @[Mux.scala 47:69]
  wire [4:0] _T_36 = io_a[9] ? 5'hd : _T_35; // @[Mux.scala 47:69]
  wire [4:0] _T_37 = io_a[10] ? 5'hc : _T_36; // @[Mux.scala 47:69]
  wire [4:0] _T_38 = io_a[11] ? 5'hb : _T_37; // @[Mux.scala 47:69]
  wire [4:0] _T_39 = io_a[12] ? 5'ha : _T_38; // @[Mux.scala 47:69]
  wire [4:0] _T_40 = io_a[13] ? 5'h9 : _T_39; // @[Mux.scala 47:69]
  wire [4:0] _T_41 = io_a[14] ? 5'h8 : _T_40; // @[Mux.scala 47:69]
  wire [4:0] _T_42 = io_a[15] ? 5'h7 : _T_41; // @[Mux.scala 47:69]
  wire [4:0] _T_43 = io_a[16] ? 5'h6 : _T_42; // @[Mux.scala 47:69]
  wire [4:0] _T_44 = io_a[17] ? 5'h5 : _T_43; // @[Mux.scala 47:69]
  wire [4:0] _T_45 = io_a[18] ? 5'h4 : _T_44; // @[Mux.scala 47:69]
  wire [4:0] _T_46 = io_a[19] ? 5'h3 : _T_45; // @[Mux.scala 47:69]
  wire [4:0] _T_47 = io_a[20] ? 5'h2 : _T_46; // @[Mux.scala 47:69]
  wire [4:0] _T_48 = io_a[21] ? 5'h1 : _T_47; // @[Mux.scala 47:69]
  wire [4:0] _T_49 = io_a[22] ? 5'h0 : _T_48; // @[Mux.scala 47:69]
  wire [53:0] _GEN_0 = {{31'd0}, io_a[22:0]}; // @[rawFloatFromFN.scala 54:36]
  wire [53:0] _T_50 = _GEN_0 << _T_49; // @[rawFloatFromFN.scala 54:36]
  wire [22:0] _T_52 = {_T_50[21:0], 1'h0}; // @[rawFloatFromFN.scala 54:64]
  wire [8:0] _GEN_1 = {{4'd0}, _T_49}; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_53 = _GEN_1 ^ 9'h1ff; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_54 = _T_3 ? _T_53 : {{1'd0}, io_a[30:23]}; // @[rawFloatFromFN.scala 56:16]
  wire [1:0] _T_55 = _T_3 ? 2'h2 : 2'h1; // @[rawFloatFromFN.scala 60:27]
  wire [7:0] _GEN_2 = {{6'd0}, _T_55}; // @[rawFloatFromFN.scala 60:22]
  wire [7:0] _T_56 = 8'h80 | _GEN_2; // @[rawFloatFromFN.scala 60:22]
  wire [8:0] _GEN_3 = {{1'd0}, _T_56}; // @[rawFloatFromFN.scala 59:15]
  wire [8:0] _T_58 = _T_54 + _GEN_3; // @[rawFloatFromFN.scala 59:15]
  wire  _T_59 = _T_3 & _T_4; // @[rawFloatFromFN.scala 62:34]
  wire  _T_61 = _T_58[8:7] == 2'h3; // @[rawFloatFromFN.scala 63:62]
  wire  _T_63 = _T_61 & ~_T_4; // @[rawFloatFromFN.scala 66:33]
  wire [9:0] _T_66 = {1'b0,$signed(_T_58)}; // @[rawFloatFromFN.scala 70:48]
  wire  hi_lo = ~_T_59; // @[rawFloatFromFN.scala 72:29]
  wire [22:0] lo = _T_3 ? _T_52 : io_a[22:0]; // @[rawFloatFromFN.scala 72:42]
  wire [24:0] _T_67 = {1'h0,hi_lo,lo}; // @[Cat.scala 30:58]
  wire [2:0] _T_69 = _T_59 ? 3'h0 : _T_66[8:6]; // @[recFNFromFN.scala 48:16]
  wire [2:0] _GEN_4 = {{2'd0}, _T_63}; // @[recFNFromFN.scala 48:79]
  wire [2:0] hi_lo_1 = _T_69 | _GEN_4; // @[recFNFromFN.scala 48:79]
  wire [5:0] lo_hi = _T_66[5:0]; // @[recFNFromFN.scala 50:23]
  wire [22:0] lo_lo = _T_67[22:0]; // @[recFNFromFN.scala 51:22]
  wire [28:0] lo_1 = {lo_hi,lo_lo}; // @[Cat.scala 30:58]
  wire [3:0] hi_1 = {io_a[31],hi_lo_1}; // @[Cat.scala 30:58]
  wire  _T_75 = io_b[30:23] == 8'h0; // @[rawFloatFromFN.scala 50:34]
  wire  _T_76 = io_b[22:0] == 23'h0; // @[rawFloatFromFN.scala 51:38]
  wire [4:0] _T_100 = io_b[1] ? 5'h15 : 5'h16; // @[Mux.scala 47:69]
  wire [4:0] _T_101 = io_b[2] ? 5'h14 : _T_100; // @[Mux.scala 47:69]
  wire [4:0] _T_102 = io_b[3] ? 5'h13 : _T_101; // @[Mux.scala 47:69]
  wire [4:0] _T_103 = io_b[4] ? 5'h12 : _T_102; // @[Mux.scala 47:69]
  wire [4:0] _T_104 = io_b[5] ? 5'h11 : _T_103; // @[Mux.scala 47:69]
  wire [4:0] _T_105 = io_b[6] ? 5'h10 : _T_104; // @[Mux.scala 47:69]
  wire [4:0] _T_106 = io_b[7] ? 5'hf : _T_105; // @[Mux.scala 47:69]
  wire [4:0] _T_107 = io_b[8] ? 5'he : _T_106; // @[Mux.scala 47:69]
  wire [4:0] _T_108 = io_b[9] ? 5'hd : _T_107; // @[Mux.scala 47:69]
  wire [4:0] _T_109 = io_b[10] ? 5'hc : _T_108; // @[Mux.scala 47:69]
  wire [4:0] _T_110 = io_b[11] ? 5'hb : _T_109; // @[Mux.scala 47:69]
  wire [4:0] _T_111 = io_b[12] ? 5'ha : _T_110; // @[Mux.scala 47:69]
  wire [4:0] _T_112 = io_b[13] ? 5'h9 : _T_111; // @[Mux.scala 47:69]
  wire [4:0] _T_113 = io_b[14] ? 5'h8 : _T_112; // @[Mux.scala 47:69]
  wire [4:0] _T_114 = io_b[15] ? 5'h7 : _T_113; // @[Mux.scala 47:69]
  wire [4:0] _T_115 = io_b[16] ? 5'h6 : _T_114; // @[Mux.scala 47:69]
  wire [4:0] _T_116 = io_b[17] ? 5'h5 : _T_115; // @[Mux.scala 47:69]
  wire [4:0] _T_117 = io_b[18] ? 5'h4 : _T_116; // @[Mux.scala 47:69]
  wire [4:0] _T_118 = io_b[19] ? 5'h3 : _T_117; // @[Mux.scala 47:69]
  wire [4:0] _T_119 = io_b[20] ? 5'h2 : _T_118; // @[Mux.scala 47:69]
  wire [4:0] _T_120 = io_b[21] ? 5'h1 : _T_119; // @[Mux.scala 47:69]
  wire [4:0] _T_121 = io_b[22] ? 5'h0 : _T_120; // @[Mux.scala 47:69]
  wire [53:0] _GEN_5 = {{31'd0}, io_b[22:0]}; // @[rawFloatFromFN.scala 54:36]
  wire [53:0] _T_122 = _GEN_5 << _T_121; // @[rawFloatFromFN.scala 54:36]
  wire [22:0] _T_124 = {_T_122[21:0], 1'h0}; // @[rawFloatFromFN.scala 54:64]
  wire [8:0] _GEN_6 = {{4'd0}, _T_121}; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_125 = _GEN_6 ^ 9'h1ff; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_126 = _T_75 ? _T_125 : {{1'd0}, io_b[30:23]}; // @[rawFloatFromFN.scala 56:16]
  wire [1:0] _T_127 = _T_75 ? 2'h2 : 2'h1; // @[rawFloatFromFN.scala 60:27]
  wire [7:0] _GEN_7 = {{6'd0}, _T_127}; // @[rawFloatFromFN.scala 60:22]
  wire [7:0] _T_128 = 8'h80 | _GEN_7; // @[rawFloatFromFN.scala 60:22]
  wire [8:0] _GEN_8 = {{1'd0}, _T_128}; // @[rawFloatFromFN.scala 59:15]
  wire [8:0] _T_130 = _T_126 + _GEN_8; // @[rawFloatFromFN.scala 59:15]
  wire  _T_131 = _T_75 & _T_76; // @[rawFloatFromFN.scala 62:34]
  wire  _T_133 = _T_130[8:7] == 2'h3; // @[rawFloatFromFN.scala 63:62]
  wire  _T_135 = _T_133 & ~_T_76; // @[rawFloatFromFN.scala 66:33]
  wire [9:0] _T_138 = {1'b0,$signed(_T_130)}; // @[rawFloatFromFN.scala 70:48]
  wire  hi_lo_2 = ~_T_131; // @[rawFloatFromFN.scala 72:29]
  wire [22:0] lo_2 = _T_75 ? _T_124 : io_b[22:0]; // @[rawFloatFromFN.scala 72:42]
  wire [24:0] _T_139 = {1'h0,hi_lo_2,lo_2}; // @[Cat.scala 30:58]
  wire [2:0] _T_141 = _T_131 ? 3'h0 : _T_138[8:6]; // @[recFNFromFN.scala 48:16]
  wire [2:0] _GEN_9 = {{2'd0}, _T_135}; // @[recFNFromFN.scala 48:79]
  wire [2:0] hi_lo_3 = _T_141 | _GEN_9; // @[recFNFromFN.scala 48:79]
  wire [5:0] lo_hi_1 = _T_138[5:0]; // @[recFNFromFN.scala 50:23]
  wire [22:0] lo_lo_1 = _T_139[22:0]; // @[recFNFromFN.scala 51:22]
  wire [28:0] lo_3 = {lo_hi_1,lo_lo_1}; // @[Cat.scala 30:58]
  wire [3:0] hi_3 = {io_b[31],hi_lo_3}; // @[Cat.scala 30:58]
  wire  _T_147 = io_c[30:23] == 8'h0; // @[rawFloatFromFN.scala 50:34]
  wire  _T_148 = io_c[22:0] == 23'h0; // @[rawFloatFromFN.scala 51:38]
  wire [4:0] _T_172 = io_c[1] ? 5'h15 : 5'h16; // @[Mux.scala 47:69]
  wire [4:0] _T_173 = io_c[2] ? 5'h14 : _T_172; // @[Mux.scala 47:69]
  wire [4:0] _T_174 = io_c[3] ? 5'h13 : _T_173; // @[Mux.scala 47:69]
  wire [4:0] _T_175 = io_c[4] ? 5'h12 : _T_174; // @[Mux.scala 47:69]
  wire [4:0] _T_176 = io_c[5] ? 5'h11 : _T_175; // @[Mux.scala 47:69]
  wire [4:0] _T_177 = io_c[6] ? 5'h10 : _T_176; // @[Mux.scala 47:69]
  wire [4:0] _T_178 = io_c[7] ? 5'hf : _T_177; // @[Mux.scala 47:69]
  wire [4:0] _T_179 = io_c[8] ? 5'he : _T_178; // @[Mux.scala 47:69]
  wire [4:0] _T_180 = io_c[9] ? 5'hd : _T_179; // @[Mux.scala 47:69]
  wire [4:0] _T_181 = io_c[10] ? 5'hc : _T_180; // @[Mux.scala 47:69]
  wire [4:0] _T_182 = io_c[11] ? 5'hb : _T_181; // @[Mux.scala 47:69]
  wire [4:0] _T_183 = io_c[12] ? 5'ha : _T_182; // @[Mux.scala 47:69]
  wire [4:0] _T_184 = io_c[13] ? 5'h9 : _T_183; // @[Mux.scala 47:69]
  wire [4:0] _T_185 = io_c[14] ? 5'h8 : _T_184; // @[Mux.scala 47:69]
  wire [4:0] _T_186 = io_c[15] ? 5'h7 : _T_185; // @[Mux.scala 47:69]
  wire [4:0] _T_187 = io_c[16] ? 5'h6 : _T_186; // @[Mux.scala 47:69]
  wire [4:0] _T_188 = io_c[17] ? 5'h5 : _T_187; // @[Mux.scala 47:69]
  wire [4:0] _T_189 = io_c[18] ? 5'h4 : _T_188; // @[Mux.scala 47:69]
  wire [4:0] _T_190 = io_c[19] ? 5'h3 : _T_189; // @[Mux.scala 47:69]
  wire [4:0] _T_191 = io_c[20] ? 5'h2 : _T_190; // @[Mux.scala 47:69]
  wire [4:0] _T_192 = io_c[21] ? 5'h1 : _T_191; // @[Mux.scala 47:69]
  wire [4:0] _T_193 = io_c[22] ? 5'h0 : _T_192; // @[Mux.scala 47:69]
  wire [53:0] _GEN_10 = {{31'd0}, io_c[22:0]}; // @[rawFloatFromFN.scala 54:36]
  wire [53:0] _T_194 = _GEN_10 << _T_193; // @[rawFloatFromFN.scala 54:36]
  wire [22:0] _T_196 = {_T_194[21:0], 1'h0}; // @[rawFloatFromFN.scala 54:64]
  wire [8:0] _GEN_11 = {{4'd0}, _T_193}; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_197 = _GEN_11 ^ 9'h1ff; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_198 = _T_147 ? _T_197 : {{1'd0}, io_c[30:23]}; // @[rawFloatFromFN.scala 56:16]
  wire [1:0] _T_199 = _T_147 ? 2'h2 : 2'h1; // @[rawFloatFromFN.scala 60:27]
  wire [7:0] _GEN_12 = {{6'd0}, _T_199}; // @[rawFloatFromFN.scala 60:22]
  wire [7:0] _T_200 = 8'h80 | _GEN_12; // @[rawFloatFromFN.scala 60:22]
  wire [8:0] _GEN_13 = {{1'd0}, _T_200}; // @[rawFloatFromFN.scala 59:15]
  wire [8:0] _T_202 = _T_198 + _GEN_13; // @[rawFloatFromFN.scala 59:15]
  wire  _T_203 = _T_147 & _T_148; // @[rawFloatFromFN.scala 62:34]
  wire  _T_205 = _T_202[8:7] == 2'h3; // @[rawFloatFromFN.scala 63:62]
  wire  _T_207 = _T_205 & ~_T_148; // @[rawFloatFromFN.scala 66:33]
  wire [9:0] _T_210 = {1'b0,$signed(_T_202)}; // @[rawFloatFromFN.scala 70:48]
  wire  hi_lo_4 = ~_T_203; // @[rawFloatFromFN.scala 72:29]
  wire [22:0] lo_4 = _T_147 ? _T_196 : io_c[22:0]; // @[rawFloatFromFN.scala 72:42]
  wire [24:0] _T_211 = {1'h0,hi_lo_4,lo_4}; // @[Cat.scala 30:58]
  wire [2:0] _T_213 = _T_203 ? 3'h0 : _T_210[8:6]; // @[recFNFromFN.scala 48:16]
  wire [2:0] _GEN_14 = {{2'd0}, _T_207}; // @[recFNFromFN.scala 48:79]
  wire [2:0] hi_lo_5 = _T_213 | _GEN_14; // @[recFNFromFN.scala 48:79]
  wire [5:0] lo_hi_2 = _T_210[5:0]; // @[recFNFromFN.scala 50:23]
  wire [22:0] lo_lo_2 = _T_211[22:0]; // @[recFNFromFN.scala 51:22]
  wire [28:0] lo_5 = {lo_hi_2,lo_lo_2}; // @[Cat.scala 30:58]
  wire [3:0] hi_5 = {io_c[31],hi_lo_5}; // @[Cat.scala 30:58]
  wire [47:0] _T_216 = premul_a * premul_b; // @[FMULADD_1G_2.scala 113:19]
  wire  _T_219 = roundRawFNToRecFN_io_out[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  wire  _T_221 = roundRawFNToRecFN_io_out[31:30] == 2'h3; // @[rawFloatFromRecFN.scala 52:54]
  wire  _T_223 = _T_221 & roundRawFNToRecFN_io_out[29]; // @[rawFloatFromRecFN.scala 55:33]
  wire  _T_226 = _T_221 & ~roundRawFNToRecFN_io_out[29]; // @[rawFloatFromRecFN.scala 56:33]
  wire [9:0] _T_228 = {1'b0,$signed(roundRawFNToRecFN_io_out[31:23])}; // @[rawFloatFromRecFN.scala 59:27]
  wire  hi_lo_6 = ~_T_219; // @[rawFloatFromRecFN.scala 60:39]
  wire [22:0] lo_6 = roundRawFNToRecFN_io_out[22:0]; // @[rawFloatFromRecFN.scala 60:51]
  wire [24:0] _T_229 = {1'h0,hi_lo_6,lo_6}; // @[Cat.scala 30:58]
  wire  _T_230 = $signed(_T_228) < 10'sh82; // @[fNFromRecFN.scala 50:39]
  wire [4:0] _T_233 = 5'h1 - _T_228[4:0]; // @[fNFromRecFN.scala 51:39]
  wire [23:0] _T_235 = _T_229[24:1] >> _T_233; // @[fNFromRecFN.scala 52:42]
  wire [7:0] _T_239 = _T_228[7:0] - 8'h81; // @[fNFromRecFN.scala 57:45]
  wire [7:0] _T_240 = _T_230 ? 8'h0 : _T_239; // @[fNFromRecFN.scala 55:16]
  wire  _T_241 = _T_223 | _T_226; // @[fNFromRecFN.scala 59:44]
  wire [7:0] _T_243 = _T_241 ? 8'hff : 8'h0; // @[Bitwise.scala 72:12]
  wire [7:0] hi_lo_7 = _T_240 | _T_243; // @[fNFromRecFN.scala 59:15]
  wire [22:0] _T_245 = _T_226 ? 23'h0 : _T_229[22:0]; // @[fNFromRecFN.scala 63:20]
  wire [22:0] lo_7 = _T_230 ? _T_235[22:0] : _T_245; // @[fNFromRecFN.scala 61:16]
  wire [8:0] hi_7 = {roundRawFNToRecFN_io_out[32],hi_lo_7}; // @[Cat.scala 30:58]
  MulAddRecFNToRaw_preMul mulAddRecFNToRaw_preMul ( // @[FMULADD_1G_2.scala 36:15]
    .io_a(mulAddRecFNToRaw_preMul_io_a),
    .io_b(mulAddRecFNToRaw_preMul_io_b),
    .io_c(mulAddRecFNToRaw_preMul_io_c),
    .io_mulAddA(mulAddRecFNToRaw_preMul_io_mulAddA),
    .io_mulAddB(mulAddRecFNToRaw_preMul_io_mulAddB),
    .io_mulAddC(mulAddRecFNToRaw_preMul_io_mulAddC),
    .io_toPostMul_isSigNaNAny(mulAddRecFNToRaw_preMul_io_toPostMul_isSigNaNAny),
    .io_toPostMul_isNaNAOrB(mulAddRecFNToRaw_preMul_io_toPostMul_isNaNAOrB),
    .io_toPostMul_isInfA(mulAddRecFNToRaw_preMul_io_toPostMul_isInfA),
    .io_toPostMul_isZeroA(mulAddRecFNToRaw_preMul_io_toPostMul_isZeroA),
    .io_toPostMul_isInfB(mulAddRecFNToRaw_preMul_io_toPostMul_isInfB),
    .io_toPostMul_isZeroB(mulAddRecFNToRaw_preMul_io_toPostMul_isZeroB),
    .io_toPostMul_signProd(mulAddRecFNToRaw_preMul_io_toPostMul_signProd),
    .io_toPostMul_isNaNC(mulAddRecFNToRaw_preMul_io_toPostMul_isNaNC),
    .io_toPostMul_isInfC(mulAddRecFNToRaw_preMul_io_toPostMul_isInfC),
    .io_toPostMul_isZeroC(mulAddRecFNToRaw_preMul_io_toPostMul_isZeroC),
    .io_toPostMul_sExpSum(mulAddRecFNToRaw_preMul_io_toPostMul_sExpSum),
    .io_toPostMul_doSubMags(mulAddRecFNToRaw_preMul_io_toPostMul_doSubMags),
    .io_toPostMul_CIsDominant(mulAddRecFNToRaw_preMul_io_toPostMul_CIsDominant),
    .io_toPostMul_CDom_CAlignDist(mulAddRecFNToRaw_preMul_io_toPostMul_CDom_CAlignDist),
    .io_toPostMul_highAlignedSigC(mulAddRecFNToRaw_preMul_io_toPostMul_highAlignedSigC),
    .io_toPostMul_bit0AlignedSigC(mulAddRecFNToRaw_preMul_io_toPostMul_bit0AlignedSigC)
  );
  MulAddRecFNToRaw_postMul mulAddRecFNToRaw_postMul ( // @[FMULADD_1G_2.scala 116:15]
    .io_fromPreMul_isSigNaNAny(mulAddRecFNToRaw_postMul_io_fromPreMul_isSigNaNAny),
    .io_fromPreMul_isNaNAOrB(mulAddRecFNToRaw_postMul_io_fromPreMul_isNaNAOrB),
    .io_fromPreMul_isInfA(mulAddRecFNToRaw_postMul_io_fromPreMul_isInfA),
    .io_fromPreMul_isZeroA(mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroA),
    .io_fromPreMul_isInfB(mulAddRecFNToRaw_postMul_io_fromPreMul_isInfB),
    .io_fromPreMul_isZeroB(mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroB),
    .io_fromPreMul_signProd(mulAddRecFNToRaw_postMul_io_fromPreMul_signProd),
    .io_fromPreMul_isNaNC(mulAddRecFNToRaw_postMul_io_fromPreMul_isNaNC),
    .io_fromPreMul_isInfC(mulAddRecFNToRaw_postMul_io_fromPreMul_isInfC),
    .io_fromPreMul_isZeroC(mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroC),
    .io_fromPreMul_sExpSum(mulAddRecFNToRaw_postMul_io_fromPreMul_sExpSum),
    .io_fromPreMul_doSubMags(mulAddRecFNToRaw_postMul_io_fromPreMul_doSubMags),
    .io_fromPreMul_CIsDominant(mulAddRecFNToRaw_postMul_io_fromPreMul_CIsDominant),
    .io_fromPreMul_CDom_CAlignDist(mulAddRecFNToRaw_postMul_io_fromPreMul_CDom_CAlignDist),
    .io_fromPreMul_highAlignedSigC(mulAddRecFNToRaw_postMul_io_fromPreMul_highAlignedSigC),
    .io_fromPreMul_bit0AlignedSigC(mulAddRecFNToRaw_postMul_io_fromPreMul_bit0AlignedSigC),
    .io_mulAddResult(mulAddRecFNToRaw_postMul_io_mulAddResult),
    .io_invalidExc(mulAddRecFNToRaw_postMul_io_invalidExc),
    .io_rawOut_isNaN(mulAddRecFNToRaw_postMul_io_rawOut_isNaN),
    .io_rawOut_isInf(mulAddRecFNToRaw_postMul_io_rawOut_isInf),
    .io_rawOut_isZero(mulAddRecFNToRaw_postMul_io_rawOut_isZero),
    .io_rawOut_sign(mulAddRecFNToRaw_postMul_io_rawOut_sign),
    .io_rawOut_sExp(mulAddRecFNToRaw_postMul_io_rawOut_sExp),
    .io_rawOut_sig(mulAddRecFNToRaw_postMul_io_rawOut_sig)
  );
  RoundRawFNToRecFN roundRawFNToRecFN ( // @[FMULADD_1G_2.scala 137:15]
    .io_invalidExc(roundRawFNToRecFN_io_invalidExc),
    .io_in_isNaN(roundRawFNToRecFN_io_in_isNaN),
    .io_in_isInf(roundRawFNToRecFN_io_in_isInf),
    .io_in_isZero(roundRawFNToRecFN_io_in_isZero),
    .io_in_sign(roundRawFNToRecFN_io_in_sign),
    .io_in_sExp(roundRawFNToRecFN_io_in_sExp),
    .io_in_sig(roundRawFNToRecFN_io_in_sig),
    .io_out(roundRawFNToRecFN_io_out)
  );
  assign io_out = {hi_7,lo_7}; // @[Cat.scala 30:58]
  assign mulAddRecFNToRaw_preMul_io_a = {hi_1,lo_1}; // @[Cat.scala 30:58]
  assign mulAddRecFNToRaw_preMul_io_b = {hi_3,lo_3}; // @[Cat.scala 30:58]
  assign mulAddRecFNToRaw_preMul_io_c = {hi_5,lo_5}; // @[Cat.scala 30:58]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isSigNaNAny = isSigNaNAny; // @[FMULADD_1G_2.scala 119:84]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isNaNAOrB = isNaNAOrB; // @[FMULADD_1G_2.scala 120:86]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isInfA = isInfA; // @[FMULADD_1G_2.scala 121:94]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroA = isZeroA; // @[FMULADD_1G_2.scala 122:91]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isInfB = isInfB; // @[FMULADD_1G_2.scala 123:94]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroB = isZeroB; // @[FMULADD_1G_2.scala 124:91]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_signProd = signProd; // @[FMULADD_1G_2.scala 125:89]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isNaNC = isNaNC; // @[FMULADD_1G_2.scala 126:90]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isInfC = isInfC; // @[FMULADD_1G_2.scala 127:94]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_isZeroC = isZeroC; // @[FMULADD_1G_2.scala 128:91]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_sExpSum = sExpSum; // @[FMULADD_1G_2.scala 129:88]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_doSubMags = doSubMags; // @[FMULADD_1G_2.scala 130:85]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_CIsDominant = CIsDominant; // @[FMULADD_1G_2.scala 131:84]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_CDom_CAlignDist = CDom_CAlignDist; // @[FMULADD_1G_2.scala 132:78]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_highAlignedSigC = highAlignedSigC; // @[FMULADD_1G_2.scala 133:80]
  assign mulAddRecFNToRaw_postMul_io_fromPreMul_bit0AlignedSigC = bit0AlignedSigC; // @[FMULADD_1G_2.scala 134:82]
  assign mulAddRecFNToRaw_postMul_io_mulAddResult = _T_216 + premul_c; // @[FMULADD_1G_2.scala 113:31]
  assign roundRawFNToRecFN_io_invalidExc = mulAddRecFNToRaw_postMul_io_invalidExc; // @[FMULADD_1G_2.scala 138:39]
  assign roundRawFNToRecFN_io_in_isNaN = mulAddRecFNToRaw_postMul_io_rawOut_isNaN; // @[FMULADD_1G_2.scala 140:39]
  assign roundRawFNToRecFN_io_in_isInf = mulAddRecFNToRaw_postMul_io_rawOut_isInf; // @[FMULADD_1G_2.scala 140:39]
  assign roundRawFNToRecFN_io_in_isZero = mulAddRecFNToRaw_postMul_io_rawOut_isZero; // @[FMULADD_1G_2.scala 140:39]
  assign roundRawFNToRecFN_io_in_sign = mulAddRecFNToRaw_postMul_io_rawOut_sign; // @[FMULADD_1G_2.scala 140:39]
  assign roundRawFNToRecFN_io_in_sExp = mulAddRecFNToRaw_postMul_io_rawOut_sExp; // @[FMULADD_1G_2.scala 140:39]
  assign roundRawFNToRecFN_io_in_sig = mulAddRecFNToRaw_postMul_io_rawOut_sig; // @[FMULADD_1G_2.scala 140:39]
  always @(posedge clock) begin
    if (reset) begin // @[FMULADD_1G_2.scala 15:37]
      premul_a <= 24'h0; // @[FMULADD_1G_2.scala 15:37]
    end else begin
      premul_a <= mulAddRecFNToRaw_preMul_io_mulAddA; // @[FMULADD_1G_2.scala 103:63]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 16:37]
      premul_b <= 24'h0; // @[FMULADD_1G_2.scala 16:37]
    end else begin
      premul_b <= mulAddRecFNToRaw_preMul_io_mulAddB; // @[FMULADD_1G_2.scala 104:63]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 17:37]
      premul_c <= 48'h0; // @[FMULADD_1G_2.scala 17:37]
    end else begin
      premul_c <= mulAddRecFNToRaw_preMul_io_mulAddC; // @[FMULADD_1G_2.scala 105:63]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 18:33]
      isSigNaNAny <= 1'h0; // @[FMULADD_1G_2.scala 18:33]
    end else begin
      isSigNaNAny <= mulAddRecFNToRaw_preMul_io_toPostMul_isSigNaNAny; // @[FMULADD_1G_2.scala 43:61]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 19:34]
      isNaNAOrB <= 1'h0; // @[FMULADD_1G_2.scala 19:34]
    end else begin
      isNaNAOrB <= mulAddRecFNToRaw_preMul_io_toPostMul_isNaNAOrB; // @[FMULADD_1G_2.scala 44:62]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 20:43]
      isInfA <= 1'h0; // @[FMULADD_1G_2.scala 20:43]
    end else begin
      isInfA <= mulAddRecFNToRaw_preMul_io_toPostMul_isInfA; // @[FMULADD_1G_2.scala 45:70]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 21:40]
      isZeroA <= 1'h0; // @[FMULADD_1G_2.scala 21:40]
    end else begin
      isZeroA <= mulAddRecFNToRaw_preMul_io_toPostMul_isZeroA; // @[FMULADD_1G_2.scala 46:67]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 22:43]
      isInfB <= 1'h0; // @[FMULADD_1G_2.scala 22:43]
    end else begin
      isInfB <= mulAddRecFNToRaw_preMul_io_toPostMul_isInfB; // @[FMULADD_1G_2.scala 47:70]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 23:40]
      isZeroB <= 1'h0; // @[FMULADD_1G_2.scala 23:40]
    end else begin
      isZeroB <= mulAddRecFNToRaw_preMul_io_toPostMul_isZeroB; // @[FMULADD_1G_2.scala 48:67]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 24:38]
      signProd <= 1'h0; // @[FMULADD_1G_2.scala 24:38]
    end else begin
      signProd <= mulAddRecFNToRaw_preMul_io_toPostMul_signProd; // @[FMULADD_1G_2.scala 49:65]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 25:39]
      isNaNC <= 1'h0; // @[FMULADD_1G_2.scala 25:39]
    end else begin
      isNaNC <= mulAddRecFNToRaw_preMul_io_toPostMul_isNaNC; // @[FMULADD_1G_2.scala 50:66]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 26:42]
      isInfC <= 1'h0; // @[FMULADD_1G_2.scala 26:42]
    end else begin
      isInfC <= mulAddRecFNToRaw_preMul_io_toPostMul_isInfC; // @[FMULADD_1G_2.scala 51:69]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 27:39]
      isZeroC <= 1'h0; // @[FMULADD_1G_2.scala 27:39]
    end else begin
      isZeroC <= mulAddRecFNToRaw_preMul_io_toPostMul_isZeroC; // @[FMULADD_1G_2.scala 52:66]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 28:36]
      sExpSum <= 10'sh0; // @[FMULADD_1G_2.scala 28:36]
    end else begin
      sExpSum <= mulAddRecFNToRaw_preMul_io_toPostMul_sExpSum; // @[FMULADD_1G_2.scala 53:63]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 29:33]
      doSubMags <= 1'h0; // @[FMULADD_1G_2.scala 29:33]
    end else begin
      doSubMags <= mulAddRecFNToRaw_preMul_io_toPostMul_doSubMags; // @[FMULADD_1G_2.scala 54:60]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 30:33]
      CIsDominant <= 1'h0; // @[FMULADD_1G_2.scala 30:33]
    end else begin
      CIsDominant <= mulAddRecFNToRaw_preMul_io_toPostMul_CIsDominant; // @[FMULADD_1G_2.scala 55:59]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 31:34]
      CDom_CAlignDist <= 5'h0; // @[FMULADD_1G_2.scala 31:34]
    end else begin
      CDom_CAlignDist <= mulAddRecFNToRaw_preMul_io_toPostMul_CDom_CAlignDist; // @[FMULADD_1G_2.scala 56:54]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 32:37]
      highAlignedSigC <= 26'h0; // @[FMULADD_1G_2.scala 32:37]
    end else begin
      highAlignedSigC <= mulAddRecFNToRaw_preMul_io_toPostMul_highAlignedSigC; // @[FMULADD_1G_2.scala 57:56]
    end
    if (reset) begin // @[FMULADD_1G_2.scala 33:38]
      bit0AlignedSigC <= 1'h0; // @[FMULADD_1G_2.scala 33:38]
    end else begin
      bit0AlignedSigC <= mulAddRecFNToRaw_preMul_io_toPostMul_bit0AlignedSigC; // @[FMULADD_1G_2.scala 58:57]
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
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  premul_a = _RAND_0[23:0];
  _RAND_1 = {1{`RANDOM}};
  premul_b = _RAND_1[23:0];
  _RAND_2 = {2{`RANDOM}};
  premul_c = _RAND_2[47:0];
  _RAND_3 = {1{`RANDOM}};
  isSigNaNAny = _RAND_3[0:0];
  _RAND_4 = {1{`RANDOM}};
  isNaNAOrB = _RAND_4[0:0];
  _RAND_5 = {1{`RANDOM}};
  isInfA = _RAND_5[0:0];
  _RAND_6 = {1{`RANDOM}};
  isZeroA = _RAND_6[0:0];
  _RAND_7 = {1{`RANDOM}};
  isInfB = _RAND_7[0:0];
  _RAND_8 = {1{`RANDOM}};
  isZeroB = _RAND_8[0:0];
  _RAND_9 = {1{`RANDOM}};
  signProd = _RAND_9[0:0];
  _RAND_10 = {1{`RANDOM}};
  isNaNC = _RAND_10[0:0];
  _RAND_11 = {1{`RANDOM}};
  isInfC = _RAND_11[0:0];
  _RAND_12 = {1{`RANDOM}};
  isZeroC = _RAND_12[0:0];
  _RAND_13 = {1{`RANDOM}};
  sExpSum = _RAND_13[9:0];
  _RAND_14 = {1{`RANDOM}};
  doSubMags = _RAND_14[0:0];
  _RAND_15 = {1{`RANDOM}};
  CIsDominant = _RAND_15[0:0];
  _RAND_16 = {1{`RANDOM}};
  CDom_CAlignDist = _RAND_16[4:0];
  _RAND_17 = {1{`RANDOM}};
  highAlignedSigC = _RAND_17[25:0];
  _RAND_18 = {1{`RANDOM}};
  bit0AlignedSigC = _RAND_18[0:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
module CompareRecFN(
  input  [32:0] io_a,
  input  [32:0] io_b,
  output        io_lt,
  output        io_eq
);
  wire  rawA_isZero = io_a[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  wire  _T_4 = io_a[31:30] == 2'h3; // @[rawFloatFromRecFN.scala 52:54]
  wire  rawA_isNaN = _T_4 & io_a[29]; // @[rawFloatFromRecFN.scala 55:33]
  wire  rawA_isInf = _T_4 & ~io_a[29]; // @[rawFloatFromRecFN.scala 56:33]
  wire  rawA_sign = io_a[32]; // @[rawFloatFromRecFN.scala 58:25]
  wire [9:0] rawA_sExp = {1'b0,$signed(io_a[31:23])}; // @[rawFloatFromRecFN.scala 59:27]
  wire  hi_lo = ~rawA_isZero; // @[rawFloatFromRecFN.scala 60:39]
  wire [22:0] lo = io_a[22:0]; // @[rawFloatFromRecFN.scala 60:51]
  wire [24:0] rawA_sig = {1'h0,hi_lo,lo}; // @[Cat.scala 30:58]
  wire  rawB_isZero = io_b[31:29] == 3'h0; // @[rawFloatFromRecFN.scala 51:54]
  wire  _T_17 = io_b[31:30] == 2'h3; // @[rawFloatFromRecFN.scala 52:54]
  wire  rawB_isNaN = _T_17 & io_b[29]; // @[rawFloatFromRecFN.scala 55:33]
  wire  rawB_isInf = _T_17 & ~io_b[29]; // @[rawFloatFromRecFN.scala 56:33]
  wire  rawB_sign = io_b[32]; // @[rawFloatFromRecFN.scala 58:25]
  wire [9:0] rawB_sExp = {1'b0,$signed(io_b[31:23])}; // @[rawFloatFromRecFN.scala 59:27]
  wire  hi_lo_1 = ~rawB_isZero; // @[rawFloatFromRecFN.scala 60:39]
  wire [22:0] lo_1 = io_b[22:0]; // @[rawFloatFromRecFN.scala 60:51]
  wire [24:0] rawB_sig = {1'h0,hi_lo_1,lo_1}; // @[Cat.scala 30:58]
  wire  ordered = ~rawA_isNaN & ~rawB_isNaN; // @[CompareRecFN.scala 57:32]
  wire  bothInfs = rawA_isInf & rawB_isInf; // @[CompareRecFN.scala 58:33]
  wire  bothZeros = rawA_isZero & rawB_isZero; // @[CompareRecFN.scala 59:33]
  wire  eqExps = $signed(rawA_sExp) == $signed(rawB_sExp); // @[CompareRecFN.scala 60:29]
  wire  common_ltMags = $signed(rawA_sExp) < $signed(rawB_sExp) | eqExps & rawA_sig < rawB_sig; // @[CompareRecFN.scala 62:33]
  wire  common_eqMags = eqExps & rawA_sig == rawB_sig; // @[CompareRecFN.scala 63:32]
  wire  _T_33 = ~rawB_sign; // @[CompareRecFN.scala 67:28]
  wire  _T_41 = _T_33 & common_ltMags; // @[CompareRecFN.scala 70:41]
  wire  _T_42 = rawA_sign & ~common_ltMags & ~common_eqMags | _T_41; // @[CompareRecFN.scala 69:74]
  wire  _T_43 = ~bothInfs & _T_42; // @[CompareRecFN.scala 68:30]
  wire  _T_44 = rawA_sign & ~rawB_sign | _T_43; // @[CompareRecFN.scala 67:41]
  wire  ordered_lt = ~bothZeros & _T_44; // @[CompareRecFN.scala 66:21]
  wire  ordered_eq = bothZeros | rawA_sign == rawB_sign & (bothInfs | common_eqMags); // @[CompareRecFN.scala 72:19]
  assign io_lt = ordered & ordered_lt; // @[CompareRecFN.scala 78:22]
  assign io_eq = ordered & ordered_eq; // @[CompareRecFN.scala 79:22]
endmodule
module ValExec_CompareRecF32_le(
  input  [31:0] io_a,
  input  [31:0] io_b,
  output        io_actual_out
);
  wire [32:0] compareRecFN_io_a; // @[ValExec_CompareRecFN.scala 94:30]
  wire [32:0] compareRecFN_io_b; // @[ValExec_CompareRecFN.scala 94:30]
  wire  compareRecFN_io_lt; // @[ValExec_CompareRecFN.scala 94:30]
  wire  compareRecFN_io_eq; // @[ValExec_CompareRecFN.scala 94:30]
  wire  _T_3 = io_a[30:23] == 8'h0; // @[rawFloatFromFN.scala 50:34]
  wire  _T_4 = io_a[22:0] == 23'h0; // @[rawFloatFromFN.scala 51:38]
  wire [4:0] _T_28 = io_a[1] ? 5'h15 : 5'h16; // @[Mux.scala 47:69]
  wire [4:0] _T_29 = io_a[2] ? 5'h14 : _T_28; // @[Mux.scala 47:69]
  wire [4:0] _T_30 = io_a[3] ? 5'h13 : _T_29; // @[Mux.scala 47:69]
  wire [4:0] _T_31 = io_a[4] ? 5'h12 : _T_30; // @[Mux.scala 47:69]
  wire [4:0] _T_32 = io_a[5] ? 5'h11 : _T_31; // @[Mux.scala 47:69]
  wire [4:0] _T_33 = io_a[6] ? 5'h10 : _T_32; // @[Mux.scala 47:69]
  wire [4:0] _T_34 = io_a[7] ? 5'hf : _T_33; // @[Mux.scala 47:69]
  wire [4:0] _T_35 = io_a[8] ? 5'he : _T_34; // @[Mux.scala 47:69]
  wire [4:0] _T_36 = io_a[9] ? 5'hd : _T_35; // @[Mux.scala 47:69]
  wire [4:0] _T_37 = io_a[10] ? 5'hc : _T_36; // @[Mux.scala 47:69]
  wire [4:0] _T_38 = io_a[11] ? 5'hb : _T_37; // @[Mux.scala 47:69]
  wire [4:0] _T_39 = io_a[12] ? 5'ha : _T_38; // @[Mux.scala 47:69]
  wire [4:0] _T_40 = io_a[13] ? 5'h9 : _T_39; // @[Mux.scala 47:69]
  wire [4:0] _T_41 = io_a[14] ? 5'h8 : _T_40; // @[Mux.scala 47:69]
  wire [4:0] _T_42 = io_a[15] ? 5'h7 : _T_41; // @[Mux.scala 47:69]
  wire [4:0] _T_43 = io_a[16] ? 5'h6 : _T_42; // @[Mux.scala 47:69]
  wire [4:0] _T_44 = io_a[17] ? 5'h5 : _T_43; // @[Mux.scala 47:69]
  wire [4:0] _T_45 = io_a[18] ? 5'h4 : _T_44; // @[Mux.scala 47:69]
  wire [4:0] _T_46 = io_a[19] ? 5'h3 : _T_45; // @[Mux.scala 47:69]
  wire [4:0] _T_47 = io_a[20] ? 5'h2 : _T_46; // @[Mux.scala 47:69]
  wire [4:0] _T_48 = io_a[21] ? 5'h1 : _T_47; // @[Mux.scala 47:69]
  wire [4:0] _T_49 = io_a[22] ? 5'h0 : _T_48; // @[Mux.scala 47:69]
  wire [53:0] _GEN_0 = {{31'd0}, io_a[22:0]}; // @[rawFloatFromFN.scala 54:36]
  wire [53:0] _T_50 = _GEN_0 << _T_49; // @[rawFloatFromFN.scala 54:36]
  wire [22:0] _T_52 = {_T_50[21:0], 1'h0}; // @[rawFloatFromFN.scala 54:64]
  wire [8:0] _GEN_1 = {{4'd0}, _T_49}; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_53 = _GEN_1 ^ 9'h1ff; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_54 = _T_3 ? _T_53 : {{1'd0}, io_a[30:23]}; // @[rawFloatFromFN.scala 56:16]
  wire [1:0] _T_55 = _T_3 ? 2'h2 : 2'h1; // @[rawFloatFromFN.scala 60:27]
  wire [7:0] _GEN_2 = {{6'd0}, _T_55}; // @[rawFloatFromFN.scala 60:22]
  wire [7:0] _T_56 = 8'h80 | _GEN_2; // @[rawFloatFromFN.scala 60:22]
  wire [8:0] _GEN_3 = {{1'd0}, _T_56}; // @[rawFloatFromFN.scala 59:15]
  wire [8:0] _T_58 = _T_54 + _GEN_3; // @[rawFloatFromFN.scala 59:15]
  wire  _T_59 = _T_3 & _T_4; // @[rawFloatFromFN.scala 62:34]
  wire  _T_61 = _T_58[8:7] == 2'h3; // @[rawFloatFromFN.scala 63:62]
  wire  _T_63 = _T_61 & ~_T_4; // @[rawFloatFromFN.scala 66:33]
  wire [9:0] _T_66 = {1'b0,$signed(_T_58)}; // @[rawFloatFromFN.scala 70:48]
  wire  hi_lo = ~_T_59; // @[rawFloatFromFN.scala 72:29]
  wire [22:0] lo = _T_3 ? _T_52 : io_a[22:0]; // @[rawFloatFromFN.scala 72:42]
  wire [24:0] _T_67 = {1'h0,hi_lo,lo}; // @[Cat.scala 30:58]
  wire [2:0] _T_69 = _T_59 ? 3'h0 : _T_66[8:6]; // @[recFNFromFN.scala 48:16]
  wire [2:0] _GEN_4 = {{2'd0}, _T_63}; // @[recFNFromFN.scala 48:79]
  wire [2:0] hi_lo_1 = _T_69 | _GEN_4; // @[recFNFromFN.scala 48:79]
  wire [5:0] lo_hi = _T_66[5:0]; // @[recFNFromFN.scala 50:23]
  wire [22:0] lo_lo = _T_67[22:0]; // @[recFNFromFN.scala 51:22]
  wire [28:0] lo_1 = {lo_hi,lo_lo}; // @[Cat.scala 30:58]
  wire [3:0] hi_1 = {io_a[31],hi_lo_1}; // @[Cat.scala 30:58]
  wire  _T_75 = io_b[30:23] == 8'h0; // @[rawFloatFromFN.scala 50:34]
  wire  _T_76 = io_b[22:0] == 23'h0; // @[rawFloatFromFN.scala 51:38]
  wire [4:0] _T_100 = io_b[1] ? 5'h15 : 5'h16; // @[Mux.scala 47:69]
  wire [4:0] _T_101 = io_b[2] ? 5'h14 : _T_100; // @[Mux.scala 47:69]
  wire [4:0] _T_102 = io_b[3] ? 5'h13 : _T_101; // @[Mux.scala 47:69]
  wire [4:0] _T_103 = io_b[4] ? 5'h12 : _T_102; // @[Mux.scala 47:69]
  wire [4:0] _T_104 = io_b[5] ? 5'h11 : _T_103; // @[Mux.scala 47:69]
  wire [4:0] _T_105 = io_b[6] ? 5'h10 : _T_104; // @[Mux.scala 47:69]
  wire [4:0] _T_106 = io_b[7] ? 5'hf : _T_105; // @[Mux.scala 47:69]
  wire [4:0] _T_107 = io_b[8] ? 5'he : _T_106; // @[Mux.scala 47:69]
  wire [4:0] _T_108 = io_b[9] ? 5'hd : _T_107; // @[Mux.scala 47:69]
  wire [4:0] _T_109 = io_b[10] ? 5'hc : _T_108; // @[Mux.scala 47:69]
  wire [4:0] _T_110 = io_b[11] ? 5'hb : _T_109; // @[Mux.scala 47:69]
  wire [4:0] _T_111 = io_b[12] ? 5'ha : _T_110; // @[Mux.scala 47:69]
  wire [4:0] _T_112 = io_b[13] ? 5'h9 : _T_111; // @[Mux.scala 47:69]
  wire [4:0] _T_113 = io_b[14] ? 5'h8 : _T_112; // @[Mux.scala 47:69]
  wire [4:0] _T_114 = io_b[15] ? 5'h7 : _T_113; // @[Mux.scala 47:69]
  wire [4:0] _T_115 = io_b[16] ? 5'h6 : _T_114; // @[Mux.scala 47:69]
  wire [4:0] _T_116 = io_b[17] ? 5'h5 : _T_115; // @[Mux.scala 47:69]
  wire [4:0] _T_117 = io_b[18] ? 5'h4 : _T_116; // @[Mux.scala 47:69]
  wire [4:0] _T_118 = io_b[19] ? 5'h3 : _T_117; // @[Mux.scala 47:69]
  wire [4:0] _T_119 = io_b[20] ? 5'h2 : _T_118; // @[Mux.scala 47:69]
  wire [4:0] _T_120 = io_b[21] ? 5'h1 : _T_119; // @[Mux.scala 47:69]
  wire [4:0] _T_121 = io_b[22] ? 5'h0 : _T_120; // @[Mux.scala 47:69]
  wire [53:0] _GEN_5 = {{31'd0}, io_b[22:0]}; // @[rawFloatFromFN.scala 54:36]
  wire [53:0] _T_122 = _GEN_5 << _T_121; // @[rawFloatFromFN.scala 54:36]
  wire [22:0] _T_124 = {_T_122[21:0], 1'h0}; // @[rawFloatFromFN.scala 54:64]
  wire [8:0] _GEN_6 = {{4'd0}, _T_121}; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_125 = _GEN_6 ^ 9'h1ff; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_126 = _T_75 ? _T_125 : {{1'd0}, io_b[30:23]}; // @[rawFloatFromFN.scala 56:16]
  wire [1:0] _T_127 = _T_75 ? 2'h2 : 2'h1; // @[rawFloatFromFN.scala 60:27]
  wire [7:0] _GEN_7 = {{6'd0}, _T_127}; // @[rawFloatFromFN.scala 60:22]
  wire [7:0] _T_128 = 8'h80 | _GEN_7; // @[rawFloatFromFN.scala 60:22]
  wire [8:0] _GEN_8 = {{1'd0}, _T_128}; // @[rawFloatFromFN.scala 59:15]
  wire [8:0] _T_130 = _T_126 + _GEN_8; // @[rawFloatFromFN.scala 59:15]
  wire  _T_131 = _T_75 & _T_76; // @[rawFloatFromFN.scala 62:34]
  wire  _T_133 = _T_130[8:7] == 2'h3; // @[rawFloatFromFN.scala 63:62]
  wire  _T_135 = _T_133 & ~_T_76; // @[rawFloatFromFN.scala 66:33]
  wire [9:0] _T_138 = {1'b0,$signed(_T_130)}; // @[rawFloatFromFN.scala 70:48]
  wire  hi_lo_2 = ~_T_131; // @[rawFloatFromFN.scala 72:29]
  wire [22:0] lo_2 = _T_75 ? _T_124 : io_b[22:0]; // @[rawFloatFromFN.scala 72:42]
  wire [24:0] _T_139 = {1'h0,hi_lo_2,lo_2}; // @[Cat.scala 30:58]
  wire [2:0] _T_141 = _T_131 ? 3'h0 : _T_138[8:6]; // @[recFNFromFN.scala 48:16]
  wire [2:0] _GEN_9 = {{2'd0}, _T_135}; // @[recFNFromFN.scala 48:79]
  wire [2:0] hi_lo_3 = _T_141 | _GEN_9; // @[recFNFromFN.scala 48:79]
  wire [5:0] lo_hi_1 = _T_138[5:0]; // @[recFNFromFN.scala 50:23]
  wire [22:0] lo_lo_1 = _T_139[22:0]; // @[recFNFromFN.scala 51:22]
  wire [28:0] lo_3 = {lo_hi_1,lo_lo_1}; // @[Cat.scala 30:58]
  wire [3:0] hi_3 = {io_b[31],hi_lo_3}; // @[Cat.scala 30:58]
  CompareRecFN compareRecFN ( // @[ValExec_CompareRecFN.scala 94:30]
    .io_a(compareRecFN_io_a),
    .io_b(compareRecFN_io_b),
    .io_lt(compareRecFN_io_lt),
    .io_eq(compareRecFN_io_eq)
  );
  assign io_actual_out = compareRecFN_io_lt | compareRecFN_io_eq; // @[ValExec_CompareRecFN.scala 99:41]
  assign compareRecFN_io_a = {hi_1,lo_1}; // @[Cat.scala 30:58]
  assign compareRecFN_io_b = {hi_3,lo_3}; // @[Cat.scala 30:58]
endmodule
module ValExec_CompareRecF32_lt(
  input  [31:0] io_a,
  input  [31:0] io_b,
  output        io_actual_out
);
  wire [32:0] compareRecFN_io_a; // @[ValExec_CompareRecFN.scala 59:30]
  wire [32:0] compareRecFN_io_b; // @[ValExec_CompareRecFN.scala 59:30]
  wire  compareRecFN_io_lt; // @[ValExec_CompareRecFN.scala 59:30]
  wire  compareRecFN_io_eq; // @[ValExec_CompareRecFN.scala 59:30]
  wire  _T_3 = io_a[30:23] == 8'h0; // @[rawFloatFromFN.scala 50:34]
  wire  _T_4 = io_a[22:0] == 23'h0; // @[rawFloatFromFN.scala 51:38]
  wire [4:0] _T_28 = io_a[1] ? 5'h15 : 5'h16; // @[Mux.scala 47:69]
  wire [4:0] _T_29 = io_a[2] ? 5'h14 : _T_28; // @[Mux.scala 47:69]
  wire [4:0] _T_30 = io_a[3] ? 5'h13 : _T_29; // @[Mux.scala 47:69]
  wire [4:0] _T_31 = io_a[4] ? 5'h12 : _T_30; // @[Mux.scala 47:69]
  wire [4:0] _T_32 = io_a[5] ? 5'h11 : _T_31; // @[Mux.scala 47:69]
  wire [4:0] _T_33 = io_a[6] ? 5'h10 : _T_32; // @[Mux.scala 47:69]
  wire [4:0] _T_34 = io_a[7] ? 5'hf : _T_33; // @[Mux.scala 47:69]
  wire [4:0] _T_35 = io_a[8] ? 5'he : _T_34; // @[Mux.scala 47:69]
  wire [4:0] _T_36 = io_a[9] ? 5'hd : _T_35; // @[Mux.scala 47:69]
  wire [4:0] _T_37 = io_a[10] ? 5'hc : _T_36; // @[Mux.scala 47:69]
  wire [4:0] _T_38 = io_a[11] ? 5'hb : _T_37; // @[Mux.scala 47:69]
  wire [4:0] _T_39 = io_a[12] ? 5'ha : _T_38; // @[Mux.scala 47:69]
  wire [4:0] _T_40 = io_a[13] ? 5'h9 : _T_39; // @[Mux.scala 47:69]
  wire [4:0] _T_41 = io_a[14] ? 5'h8 : _T_40; // @[Mux.scala 47:69]
  wire [4:0] _T_42 = io_a[15] ? 5'h7 : _T_41; // @[Mux.scala 47:69]
  wire [4:0] _T_43 = io_a[16] ? 5'h6 : _T_42; // @[Mux.scala 47:69]
  wire [4:0] _T_44 = io_a[17] ? 5'h5 : _T_43; // @[Mux.scala 47:69]
  wire [4:0] _T_45 = io_a[18] ? 5'h4 : _T_44; // @[Mux.scala 47:69]
  wire [4:0] _T_46 = io_a[19] ? 5'h3 : _T_45; // @[Mux.scala 47:69]
  wire [4:0] _T_47 = io_a[20] ? 5'h2 : _T_46; // @[Mux.scala 47:69]
  wire [4:0] _T_48 = io_a[21] ? 5'h1 : _T_47; // @[Mux.scala 47:69]
  wire [4:0] _T_49 = io_a[22] ? 5'h0 : _T_48; // @[Mux.scala 47:69]
  wire [53:0] _GEN_0 = {{31'd0}, io_a[22:0]}; // @[rawFloatFromFN.scala 54:36]
  wire [53:0] _T_50 = _GEN_0 << _T_49; // @[rawFloatFromFN.scala 54:36]
  wire [22:0] _T_52 = {_T_50[21:0], 1'h0}; // @[rawFloatFromFN.scala 54:64]
  wire [8:0] _GEN_1 = {{4'd0}, _T_49}; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_53 = _GEN_1 ^ 9'h1ff; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_54 = _T_3 ? _T_53 : {{1'd0}, io_a[30:23]}; // @[rawFloatFromFN.scala 56:16]
  wire [1:0] _T_55 = _T_3 ? 2'h2 : 2'h1; // @[rawFloatFromFN.scala 60:27]
  wire [7:0] _GEN_2 = {{6'd0}, _T_55}; // @[rawFloatFromFN.scala 60:22]
  wire [7:0] _T_56 = 8'h80 | _GEN_2; // @[rawFloatFromFN.scala 60:22]
  wire [8:0] _GEN_3 = {{1'd0}, _T_56}; // @[rawFloatFromFN.scala 59:15]
  wire [8:0] _T_58 = _T_54 + _GEN_3; // @[rawFloatFromFN.scala 59:15]
  wire  _T_59 = _T_3 & _T_4; // @[rawFloatFromFN.scala 62:34]
  wire  _T_61 = _T_58[8:7] == 2'h3; // @[rawFloatFromFN.scala 63:62]
  wire  _T_63 = _T_61 & ~_T_4; // @[rawFloatFromFN.scala 66:33]
  wire [9:0] _T_66 = {1'b0,$signed(_T_58)}; // @[rawFloatFromFN.scala 70:48]
  wire  hi_lo = ~_T_59; // @[rawFloatFromFN.scala 72:29]
  wire [22:0] lo = _T_3 ? _T_52 : io_a[22:0]; // @[rawFloatFromFN.scala 72:42]
  wire [24:0] _T_67 = {1'h0,hi_lo,lo}; // @[Cat.scala 30:58]
  wire [2:0] _T_69 = _T_59 ? 3'h0 : _T_66[8:6]; // @[recFNFromFN.scala 48:16]
  wire [2:0] _GEN_4 = {{2'd0}, _T_63}; // @[recFNFromFN.scala 48:79]
  wire [2:0] hi_lo_1 = _T_69 | _GEN_4; // @[recFNFromFN.scala 48:79]
  wire [5:0] lo_hi = _T_66[5:0]; // @[recFNFromFN.scala 50:23]
  wire [22:0] lo_lo = _T_67[22:0]; // @[recFNFromFN.scala 51:22]
  wire [28:0] lo_1 = {lo_hi,lo_lo}; // @[Cat.scala 30:58]
  wire [3:0] hi_1 = {io_a[31],hi_lo_1}; // @[Cat.scala 30:58]
  wire  _T_75 = io_b[30:23] == 8'h0; // @[rawFloatFromFN.scala 50:34]
  wire  _T_76 = io_b[22:0] == 23'h0; // @[rawFloatFromFN.scala 51:38]
  wire [4:0] _T_100 = io_b[1] ? 5'h15 : 5'h16; // @[Mux.scala 47:69]
  wire [4:0] _T_101 = io_b[2] ? 5'h14 : _T_100; // @[Mux.scala 47:69]
  wire [4:0] _T_102 = io_b[3] ? 5'h13 : _T_101; // @[Mux.scala 47:69]
  wire [4:0] _T_103 = io_b[4] ? 5'h12 : _T_102; // @[Mux.scala 47:69]
  wire [4:0] _T_104 = io_b[5] ? 5'h11 : _T_103; // @[Mux.scala 47:69]
  wire [4:0] _T_105 = io_b[6] ? 5'h10 : _T_104; // @[Mux.scala 47:69]
  wire [4:0] _T_106 = io_b[7] ? 5'hf : _T_105; // @[Mux.scala 47:69]
  wire [4:0] _T_107 = io_b[8] ? 5'he : _T_106; // @[Mux.scala 47:69]
  wire [4:0] _T_108 = io_b[9] ? 5'hd : _T_107; // @[Mux.scala 47:69]
  wire [4:0] _T_109 = io_b[10] ? 5'hc : _T_108; // @[Mux.scala 47:69]
  wire [4:0] _T_110 = io_b[11] ? 5'hb : _T_109; // @[Mux.scala 47:69]
  wire [4:0] _T_111 = io_b[12] ? 5'ha : _T_110; // @[Mux.scala 47:69]
  wire [4:0] _T_112 = io_b[13] ? 5'h9 : _T_111; // @[Mux.scala 47:69]
  wire [4:0] _T_113 = io_b[14] ? 5'h8 : _T_112; // @[Mux.scala 47:69]
  wire [4:0] _T_114 = io_b[15] ? 5'h7 : _T_113; // @[Mux.scala 47:69]
  wire [4:0] _T_115 = io_b[16] ? 5'h6 : _T_114; // @[Mux.scala 47:69]
  wire [4:0] _T_116 = io_b[17] ? 5'h5 : _T_115; // @[Mux.scala 47:69]
  wire [4:0] _T_117 = io_b[18] ? 5'h4 : _T_116; // @[Mux.scala 47:69]
  wire [4:0] _T_118 = io_b[19] ? 5'h3 : _T_117; // @[Mux.scala 47:69]
  wire [4:0] _T_119 = io_b[20] ? 5'h2 : _T_118; // @[Mux.scala 47:69]
  wire [4:0] _T_120 = io_b[21] ? 5'h1 : _T_119; // @[Mux.scala 47:69]
  wire [4:0] _T_121 = io_b[22] ? 5'h0 : _T_120; // @[Mux.scala 47:69]
  wire [53:0] _GEN_5 = {{31'd0}, io_b[22:0]}; // @[rawFloatFromFN.scala 54:36]
  wire [53:0] _T_122 = _GEN_5 << _T_121; // @[rawFloatFromFN.scala 54:36]
  wire [22:0] _T_124 = {_T_122[21:0], 1'h0}; // @[rawFloatFromFN.scala 54:64]
  wire [8:0] _GEN_6 = {{4'd0}, _T_121}; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_125 = _GEN_6 ^ 9'h1ff; // @[rawFloatFromFN.scala 57:26]
  wire [8:0] _T_126 = _T_75 ? _T_125 : {{1'd0}, io_b[30:23]}; // @[rawFloatFromFN.scala 56:16]
  wire [1:0] _T_127 = _T_75 ? 2'h2 : 2'h1; // @[rawFloatFromFN.scala 60:27]
  wire [7:0] _GEN_7 = {{6'd0}, _T_127}; // @[rawFloatFromFN.scala 60:22]
  wire [7:0] _T_128 = 8'h80 | _GEN_7; // @[rawFloatFromFN.scala 60:22]
  wire [8:0] _GEN_8 = {{1'd0}, _T_128}; // @[rawFloatFromFN.scala 59:15]
  wire [8:0] _T_130 = _T_126 + _GEN_8; // @[rawFloatFromFN.scala 59:15]
  wire  _T_131 = _T_75 & _T_76; // @[rawFloatFromFN.scala 62:34]
  wire  _T_133 = _T_130[8:7] == 2'h3; // @[rawFloatFromFN.scala 63:62]
  wire  _T_135 = _T_133 & ~_T_76; // @[rawFloatFromFN.scala 66:33]
  wire [9:0] _T_138 = {1'b0,$signed(_T_130)}; // @[rawFloatFromFN.scala 70:48]
  wire  hi_lo_2 = ~_T_131; // @[rawFloatFromFN.scala 72:29]
  wire [22:0] lo_2 = _T_75 ? _T_124 : io_b[22:0]; // @[rawFloatFromFN.scala 72:42]
  wire [24:0] _T_139 = {1'h0,hi_lo_2,lo_2}; // @[Cat.scala 30:58]
  wire [2:0] _T_141 = _T_131 ? 3'h0 : _T_138[8:6]; // @[recFNFromFN.scala 48:16]
  wire [2:0] _GEN_9 = {{2'd0}, _T_135}; // @[recFNFromFN.scala 48:79]
  wire [2:0] hi_lo_3 = _T_141 | _GEN_9; // @[recFNFromFN.scala 48:79]
  wire [5:0] lo_hi_1 = _T_138[5:0]; // @[recFNFromFN.scala 50:23]
  wire [22:0] lo_lo_1 = _T_139[22:0]; // @[recFNFromFN.scala 51:22]
  wire [28:0] lo_3 = {lo_hi_1,lo_lo_1}; // @[Cat.scala 30:58]
  wire [3:0] hi_3 = {io_b[31],hi_lo_3}; // @[Cat.scala 30:58]
  CompareRecFN compareRecFN ( // @[ValExec_CompareRecFN.scala 59:30]
    .io_a(compareRecFN_io_a),
    .io_b(compareRecFN_io_b),
    .io_lt(compareRecFN_io_lt),
    .io_eq(compareRecFN_io_eq)
  );
  assign io_actual_out = compareRecFN_io_lt; // @[ValExec_CompareRecFN.scala 64:19]
  assign compareRecFN_io_a = {hi_1,lo_1}; // @[Cat.scala 30:58]
  assign compareRecFN_io_b = {hi_3,lo_3}; // @[Cat.scala 30:58]
endmodule
module ray_AABB_1(
  input         clock,
  input         reset,
  input  [31:0] io_ray_idir_x,
  input  [31:0] io_ray_idir_y,
  input  [31:0] io_ray_idir_z,
  input  [31:0] io_ray_ood_x,
  input  [31:0] io_ray_ood_y,
  input  [31:0] io_ray_ood_z,
  input  [31:0] io_ray_tmin,
  input  [31:0] io_ray_hitT,
  input  [31:0] io_bvh_n0xy_x,
  input  [31:0] io_bvh_n0xy_y,
  input  [31:0] io_bvh_n0xy_z,
  input  [31:0] io_bvh_n0xy_w,
  input  [31:0] io_bvh_n1xy_x,
  input  [31:0] io_bvh_n1xy_y,
  input  [31:0] io_bvh_n1xy_z,
  input  [31:0] io_bvh_n1xy_w,
  input  [31:0] io_bvh_nz_x,
  input  [31:0] io_bvh_nz_y,
  input  [31:0] io_bvh_nz_z,
  input  [31:0] io_bvh_nz_w,
  input  [31:0] io_bvh_temp_x,
  input  [31:0] io_bvh_temp_y,
  input  [31:0] io_rayid,
  input         io_valid_en,
  output [31:0] io_rayid_out,
  output [31:0] io_nodeIdx_0,
  output [31:0] io_nodeIdx_1,
  output [31:0] io_nodeIdx_2,
  output        io_push,
  output        io_pop,
  output        io_leaf,
  output        io_back,
  output [31:0] io_hitT_out,
  output        io_valid_out
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
  reg [31:0] _RAND_5;
  reg [31:0] _RAND_6;
  reg [31:0] _RAND_7;
  reg [31:0] _RAND_8;
  reg [31:0] _RAND_9;
  reg [31:0] _RAND_10;
  reg [31:0] _RAND_11;
  reg [31:0] _RAND_12;
  reg [31:0] _RAND_13;
  reg [31:0] _RAND_14;
  reg [31:0] _RAND_15;
  reg [31:0] _RAND_16;
  reg [31:0] _RAND_17;
  reg [31:0] _RAND_18;
  reg [31:0] _RAND_19;
  reg [31:0] _RAND_20;
  reg [31:0] _RAND_21;
  reg [31:0] _RAND_22;
  reg [31:0] _RAND_23;
  reg [31:0] _RAND_24;
  reg [31:0] _RAND_25;
  reg [31:0] _RAND_26;
  reg [31:0] _RAND_27;
  reg [31:0] _RAND_28;
  reg [31:0] _RAND_29;
  reg [31:0] _RAND_30;
  reg [31:0] _RAND_31;
  reg [31:0] _RAND_32;
  reg [31:0] _RAND_33;
  reg [31:0] _RAND_34;
  reg [31:0] _RAND_35;
  reg [31:0] _RAND_36;
  reg [31:0] _RAND_37;
  reg [31:0] _RAND_38;
  reg [31:0] _RAND_39;
  reg [31:0] _RAND_40;
  reg [31:0] _RAND_41;
  reg [31:0] _RAND_42;
  reg [31:0] _RAND_43;
  reg [31:0] _RAND_44;
  reg [31:0] _RAND_45;
  reg [31:0] _RAND_46;
  reg [31:0] _RAND_47;
  reg [31:0] _RAND_48;
  reg [31:0] _RAND_49;
  reg [31:0] _RAND_50;
  reg [31:0] _RAND_51;
  reg [31:0] _RAND_52;
  reg [31:0] _RAND_53;
  reg [31:0] _RAND_54;
  reg [31:0] _RAND_55;
  reg [31:0] _RAND_56;
  reg [31:0] _RAND_57;
  reg [31:0] _RAND_58;
  reg [31:0] _RAND_59;
  reg [31:0] _RAND_60;
  reg [31:0] _RAND_61;
  reg [31:0] _RAND_62;
  reg [31:0] _RAND_63;
  reg [31:0] _RAND_64;
  reg [31:0] _RAND_65;
  reg [31:0] _RAND_66;
  reg [31:0] _RAND_67;
  reg [31:0] _RAND_68;
`endif // RANDOMIZE_REG_INIT
  wire  FADD_MUL_1_clock; // @[Ray_AABB_1.scala 121:33]
  wire  FADD_MUL_1_reset; // @[Ray_AABB_1.scala 121:33]
  wire [31:0] FADD_MUL_1_io_a; // @[Ray_AABB_1.scala 121:33]
  wire [31:0] FADD_MUL_1_io_b; // @[Ray_AABB_1.scala 121:33]
  wire [31:0] FADD_MUL_1_io_c; // @[Ray_AABB_1.scala 121:33]
  wire [31:0] FADD_MUL_1_io_out; // @[Ray_AABB_1.scala 121:33]
  wire  FADD_MUL_2_clock; // @[Ray_AABB_1.scala 131:33]
  wire  FADD_MUL_2_reset; // @[Ray_AABB_1.scala 131:33]
  wire [31:0] FADD_MUL_2_io_a; // @[Ray_AABB_1.scala 131:33]
  wire [31:0] FADD_MUL_2_io_b; // @[Ray_AABB_1.scala 131:33]
  wire [31:0] FADD_MUL_2_io_c; // @[Ray_AABB_1.scala 131:33]
  wire [31:0] FADD_MUL_2_io_out; // @[Ray_AABB_1.scala 131:33]
  wire  FADD_MUL_3_clock; // @[Ray_AABB_1.scala 141:33]
  wire  FADD_MUL_3_reset; // @[Ray_AABB_1.scala 141:33]
  wire [31:0] FADD_MUL_3_io_a; // @[Ray_AABB_1.scala 141:33]
  wire [31:0] FADD_MUL_3_io_b; // @[Ray_AABB_1.scala 141:33]
  wire [31:0] FADD_MUL_3_io_c; // @[Ray_AABB_1.scala 141:33]
  wire [31:0] FADD_MUL_3_io_out; // @[Ray_AABB_1.scala 141:33]
  wire  FADD_MUL_4_clock; // @[Ray_AABB_1.scala 151:33]
  wire  FADD_MUL_4_reset; // @[Ray_AABB_1.scala 151:33]
  wire [31:0] FADD_MUL_4_io_a; // @[Ray_AABB_1.scala 151:33]
  wire [31:0] FADD_MUL_4_io_b; // @[Ray_AABB_1.scala 151:33]
  wire [31:0] FADD_MUL_4_io_c; // @[Ray_AABB_1.scala 151:33]
  wire [31:0] FADD_MUL_4_io_out; // @[Ray_AABB_1.scala 151:33]
  wire  FADD_MUL_5_clock; // @[Ray_AABB_1.scala 161:33]
  wire  FADD_MUL_5_reset; // @[Ray_AABB_1.scala 161:33]
  wire [31:0] FADD_MUL_5_io_a; // @[Ray_AABB_1.scala 161:33]
  wire [31:0] FADD_MUL_5_io_b; // @[Ray_AABB_1.scala 161:33]
  wire [31:0] FADD_MUL_5_io_c; // @[Ray_AABB_1.scala 161:33]
  wire [31:0] FADD_MUL_5_io_out; // @[Ray_AABB_1.scala 161:33]
  wire  FADD_MUL_6_clock; // @[Ray_AABB_1.scala 171:33]
  wire  FADD_MUL_6_reset; // @[Ray_AABB_1.scala 171:33]
  wire [31:0] FADD_MUL_6_io_a; // @[Ray_AABB_1.scala 171:33]
  wire [31:0] FADD_MUL_6_io_b; // @[Ray_AABB_1.scala 171:33]
  wire [31:0] FADD_MUL_6_io_c; // @[Ray_AABB_1.scala 171:33]
  wire [31:0] FADD_MUL_6_io_out; // @[Ray_AABB_1.scala 171:33]
  wire  FADD_MUL_7_clock; // @[Ray_AABB_1.scala 181:33]
  wire  FADD_MUL_7_reset; // @[Ray_AABB_1.scala 181:33]
  wire [31:0] FADD_MUL_7_io_a; // @[Ray_AABB_1.scala 181:33]
  wire [31:0] FADD_MUL_7_io_b; // @[Ray_AABB_1.scala 181:33]
  wire [31:0] FADD_MUL_7_io_c; // @[Ray_AABB_1.scala 181:33]
  wire [31:0] FADD_MUL_7_io_out; // @[Ray_AABB_1.scala 181:33]
  wire  FADD_MUL_8_clock; // @[Ray_AABB_1.scala 191:33]
  wire  FADD_MUL_8_reset; // @[Ray_AABB_1.scala 191:33]
  wire [31:0] FADD_MUL_8_io_a; // @[Ray_AABB_1.scala 191:33]
  wire [31:0] FADD_MUL_8_io_b; // @[Ray_AABB_1.scala 191:33]
  wire [31:0] FADD_MUL_8_io_c; // @[Ray_AABB_1.scala 191:33]
  wire [31:0] FADD_MUL_8_io_out; // @[Ray_AABB_1.scala 191:33]
  wire  FADD_MUL_9_clock; // @[Ray_AABB_1.scala 201:33]
  wire  FADD_MUL_9_reset; // @[Ray_AABB_1.scala 201:33]
  wire [31:0] FADD_MUL_9_io_a; // @[Ray_AABB_1.scala 201:33]
  wire [31:0] FADD_MUL_9_io_b; // @[Ray_AABB_1.scala 201:33]
  wire [31:0] FADD_MUL_9_io_c; // @[Ray_AABB_1.scala 201:33]
  wire [31:0] FADD_MUL_9_io_out; // @[Ray_AABB_1.scala 201:33]
  wire  FADD_MUL_10_clock; // @[Ray_AABB_1.scala 211:33]
  wire  FADD_MUL_10_reset; // @[Ray_AABB_1.scala 211:33]
  wire [31:0] FADD_MUL_10_io_a; // @[Ray_AABB_1.scala 211:33]
  wire [31:0] FADD_MUL_10_io_b; // @[Ray_AABB_1.scala 211:33]
  wire [31:0] FADD_MUL_10_io_c; // @[Ray_AABB_1.scala 211:33]
  wire [31:0] FADD_MUL_10_io_out; // @[Ray_AABB_1.scala 211:33]
  wire  FADD_MUL_11_clock; // @[Ray_AABB_1.scala 221:33]
  wire  FADD_MUL_11_reset; // @[Ray_AABB_1.scala 221:33]
  wire [31:0] FADD_MUL_11_io_a; // @[Ray_AABB_1.scala 221:33]
  wire [31:0] FADD_MUL_11_io_b; // @[Ray_AABB_1.scala 221:33]
  wire [31:0] FADD_MUL_11_io_c; // @[Ray_AABB_1.scala 221:33]
  wire [31:0] FADD_MUL_11_io_out; // @[Ray_AABB_1.scala 221:33]
  wire  FADD_MUL_12_clock; // @[Ray_AABB_1.scala 231:33]
  wire  FADD_MUL_12_reset; // @[Ray_AABB_1.scala 231:33]
  wire [31:0] FADD_MUL_12_io_a; // @[Ray_AABB_1.scala 231:33]
  wire [31:0] FADD_MUL_12_io_b; // @[Ray_AABB_1.scala 231:33]
  wire [31:0] FADD_MUL_12_io_c; // @[Ray_AABB_1.scala 231:33]
  wire [31:0] FADD_MUL_12_io_out; // @[Ray_AABB_1.scala 231:33]
  wire [31:0] FCMP_1_io_a; // @[Ray_AABB_1.scala 279:24]
  wire [31:0] FCMP_1_io_b; // @[Ray_AABB_1.scala 279:24]
  wire  FCMP_1_io_actual_out; // @[Ray_AABB_1.scala 279:24]
  wire [31:0] FCMP_2_io_a; // @[Ray_AABB_1.scala 294:24]
  wire [31:0] FCMP_2_io_b; // @[Ray_AABB_1.scala 294:24]
  wire  FCMP_2_io_actual_out; // @[Ray_AABB_1.scala 294:24]
  wire [31:0] FCMP_3_io_a; // @[Ray_AABB_1.scala 307:24]
  wire [31:0] FCMP_3_io_b; // @[Ray_AABB_1.scala 307:24]
  wire  FCMP_3_io_actual_out; // @[Ray_AABB_1.scala 307:24]
  wire [31:0] FCMP_4_io_a; // @[Ray_AABB_1.scala 320:24]
  wire [31:0] FCMP_4_io_b; // @[Ray_AABB_1.scala 320:24]
  wire  FCMP_4_io_actual_out; // @[Ray_AABB_1.scala 320:24]
  wire [31:0] FCMP_5_io_a; // @[Ray_AABB_1.scala 333:25]
  wire [31:0] FCMP_5_io_b; // @[Ray_AABB_1.scala 333:25]
  wire  FCMP_5_io_actual_out; // @[Ray_AABB_1.scala 333:25]
  wire [31:0] FCMP_6_io_a; // @[Ray_AABB_1.scala 346:24]
  wire [31:0] FCMP_6_io_b; // @[Ray_AABB_1.scala 346:24]
  wire  FCMP_6_io_actual_out; // @[Ray_AABB_1.scala 346:24]
  wire [31:0] FCMP_7_io_a; // @[Ray_AABB_1.scala 391:24]
  wire [31:0] FCMP_7_io_b; // @[Ray_AABB_1.scala 391:24]
  wire  FCMP_7_io_actual_out; // @[Ray_AABB_1.scala 391:24]
  wire [31:0] FCMP_8_io_a; // @[Ray_AABB_1.scala 402:24]
  wire [31:0] FCMP_8_io_b; // @[Ray_AABB_1.scala 402:24]
  wire  FCMP_8_io_actual_out; // @[Ray_AABB_1.scala 402:24]
  wire [31:0] FCMP_9_io_a; // @[Ray_AABB_1.scala 413:24]
  wire [31:0] FCMP_9_io_b; // @[Ray_AABB_1.scala 413:24]
  wire  FCMP_9_io_actual_out; // @[Ray_AABB_1.scala 413:24]
  wire [31:0] FCMP_10_io_a; // @[Ray_AABB_1.scala 424:25]
  wire [31:0] FCMP_10_io_b; // @[Ray_AABB_1.scala 424:25]
  wire  FCMP_10_io_actual_out; // @[Ray_AABB_1.scala 424:25]
  wire [31:0] FCMP_11_io_a; // @[Ray_AABB_1.scala 435:25]
  wire [31:0] FCMP_11_io_b; // @[Ray_AABB_1.scala 435:25]
  wire  FCMP_11_io_actual_out; // @[Ray_AABB_1.scala 435:25]
  wire [31:0] FCMP_12_io_a; // @[Ray_AABB_1.scala 446:25]
  wire [31:0] FCMP_12_io_b; // @[Ray_AABB_1.scala 446:25]
  wire  FCMP_12_io_actual_out; // @[Ray_AABB_1.scala 446:25]
  wire [31:0] FCMP_13_io_a; // @[Ray_AABB_1.scala 457:25]
  wire [31:0] FCMP_13_io_b; // @[Ray_AABB_1.scala 457:25]
  wire  FCMP_13_io_actual_out; // @[Ray_AABB_1.scala 457:25]
  wire [31:0] FCMP_14_io_a; // @[Ray_AABB_1.scala 468:25]
  wire [31:0] FCMP_14_io_b; // @[Ray_AABB_1.scala 468:25]
  wire  FCMP_14_io_actual_out; // @[Ray_AABB_1.scala 468:25]
  wire [31:0] FCMP_15_io_a; // @[Ray_AABB_1.scala 506:25]
  wire [31:0] FCMP_15_io_b; // @[Ray_AABB_1.scala 506:25]
  wire  FCMP_15_io_actual_out; // @[Ray_AABB_1.scala 506:25]
  wire [31:0] FCMP_16_io_a; // @[Ray_AABB_1.scala 517:25]
  wire [31:0] FCMP_16_io_b; // @[Ray_AABB_1.scala 517:25]
  wire  FCMP_16_io_actual_out; // @[Ray_AABB_1.scala 517:25]
  wire [31:0] FCMP_17_io_a; // @[Ray_AABB_1.scala 528:25]
  wire [31:0] FCMP_17_io_b; // @[Ray_AABB_1.scala 528:25]
  wire  FCMP_17_io_actual_out; // @[Ray_AABB_1.scala 528:25]
  wire [31:0] FCMP_18_io_a; // @[Ray_AABB_1.scala 539:25]
  wire [31:0] FCMP_18_io_b; // @[Ray_AABB_1.scala 539:25]
  wire  FCMP_18_io_actual_out; // @[Ray_AABB_1.scala 539:25]
  wire [31:0] FCMP_19_io_a; // @[Ray_AABB_1.scala 582:25]
  wire [31:0] FCMP_19_io_b; // @[Ray_AABB_1.scala 582:25]
  wire  FCMP_19_io_actual_out; // @[Ray_AABB_1.scala 582:25]
  wire [31:0] FCMP_20_io_a; // @[Ray_AABB_1.scala 595:25]
  wire [31:0] FCMP_20_io_b; // @[Ray_AABB_1.scala 595:25]
  wire  FCMP_20_io_actual_out; // @[Ray_AABB_1.scala 595:25]
  wire [31:0] FCMP_21_io_a; // @[Ray_AABB_1.scala 608:25]
  wire [31:0] FCMP_21_io_b; // @[Ray_AABB_1.scala 608:25]
  wire  FCMP_21_io_actual_out; // @[Ray_AABB_1.scala 608:25]
  reg  traverseChild0; // @[Ray_AABB_1.scala 73:33]
  reg  traverseChild1; // @[Ray_AABB_1.scala 74:33]
  reg [31:0] c0lox; // @[Ray_AABB_1.scala 76:34]
  reg [31:0] c0hix; // @[Ray_AABB_1.scala 77:34]
  reg [31:0] c0loy; // @[Ray_AABB_1.scala 78:34]
  reg [31:0] c0hiy; // @[Ray_AABB_1.scala 79:33]
  reg [31:0] c0loz; // @[Ray_AABB_1.scala 80:34]
  reg [31:0] c0hiz; // @[Ray_AABB_1.scala 81:34]
  reg [31:0] c1lox; // @[Ray_AABB_1.scala 83:34]
  reg [31:0] c1hix; // @[Ray_AABB_1.scala 84:34]
  reg [31:0] c1loy; // @[Ray_AABB_1.scala 85:34]
  reg [31:0] c1hiy; // @[Ray_AABB_1.scala 86:34]
  reg [31:0] c1loz; // @[Ray_AABB_1.scala 87:34]
  reg [31:0] c1hiz; // @[Ray_AABB_1.scala 88:34]
  reg [31:0] rayid_1; // @[Ray_AABB_1.scala 90:32]
  reg [31:0] hitT_1; // @[Ray_AABB_1.scala 91:33]
  reg [31:0] valid_1; // @[Ray_AABB_1.scala 93:32]
  reg [31:0] cidx_0_1; // @[Ray_AABB_1.scala 94:45]
  reg [31:0] cidx_1_1; // @[Ray_AABB_1.scala 95:45]
  reg [31:0] rayid_temp; // @[Ray_AABB_1.scala 103:35]
  reg [31:0] hitT_temp; // @[Ray_AABB_1.scala 104:36]
  reg [31:0] valid_temp; // @[Ray_AABB_1.scala 106:35]
  reg [31:0] cidx_0_temp; // @[Ray_AABB_1.scala 113:48]
  reg [31:0] cidx_1_temp; // @[Ray_AABB_1.scala 114:48]
  wire  hi = ~io_ray_ood_x[31]; // @[common.scala 90:20]
  wire [30:0] lo = io_ray_ood_x[30:0]; // @[common.scala 90:30]
  wire  hi_2 = ~io_ray_ood_y[31]; // @[common.scala 90:20]
  wire [30:0] lo_2 = io_ray_ood_y[30:0]; // @[common.scala 90:30]
  wire  hi_4 = ~io_ray_ood_z[31]; // @[common.scala 90:20]
  wire [30:0] lo_4 = io_ray_ood_z[30:0]; // @[common.scala 90:30]
  reg [31:0] cidx_0_2; // @[Ray_AABB_1.scala 242:45]
  reg [31:0] cidx_1_2; // @[Ray_AABB_1.scala 243:45]
  reg [31:0] rayid_2; // @[Ray_AABB_1.scala 256:32]
  reg [31:0] hitT_2; // @[Ray_AABB_1.scala 257:33]
  reg [31:0] valid_2; // @[Ray_AABB_1.scala 259:32]
  reg [31:0] cmpMin0_1; // @[Ray_AABB_1.scala 266:28]
  reg [31:0] cmpMin0_2; // @[Ray_AABB_1.scala 267:28]
  reg [31:0] cmpMin0_3; // @[Ray_AABB_1.scala 268:28]
  reg [31:0] cmpMax0_1; // @[Ray_AABB_1.scala 269:28]
  reg [31:0] cmpMax0_2; // @[Ray_AABB_1.scala 270:28]
  reg [31:0] cmpMax0_3; // @[Ray_AABB_1.scala 271:28]
  reg [31:0] cmpMin1_1; // @[Ray_AABB_1.scala 272:28]
  reg [31:0] cmpMin1_2; // @[Ray_AABB_1.scala 273:28]
  reg [31:0] cmpMin1_3; // @[Ray_AABB_1.scala 274:28]
  reg [31:0] cmpMax1_1; // @[Ray_AABB_1.scala 275:28]
  reg [31:0] cmpMax1_2; // @[Ray_AABB_1.scala 276:28]
  reg [31:0] cmpMax1_3; // @[Ray_AABB_1.scala 277:28]
  wire  _T_24 = FCMP_1_io_actual_out; // @[Ray_AABB_1.scala 284:47]
  reg [31:0] c0Min_temp_1; // @[Ray_AABB_1.scala 360:31]
  reg [31:0] c0Min_temp_2; // @[Ray_AABB_1.scala 361:31]
  reg [31:0] c0Max_temp_1; // @[Ray_AABB_1.scala 362:31]
  reg [31:0] c0Max_temp_2; // @[Ray_AABB_1.scala 363:31]
  reg [31:0] c1Min_temp_1; // @[Ray_AABB_1.scala 364:31]
  reg [31:0] c1Min_temp_2; // @[Ray_AABB_1.scala 365:31]
  reg [31:0] c1Max_temp_1; // @[Ray_AABB_1.scala 366:31]
  reg [31:0] c1Max_temp_2; // @[Ray_AABB_1.scala 367:31]
  reg [31:0] cidx_0_3; // @[Ray_AABB_1.scala 369:45]
  reg [31:0] cidx_1_3; // @[Ray_AABB_1.scala 370:45]
  reg [31:0] hitT_3; // @[Ray_AABB_1.scala 384:49]
  reg [31:0] rayid_3; // @[Ray_AABB_1.scala 386:48]
  reg  valid_3; // @[Ray_AABB_1.scala 388:49]
  reg [31:0] c0Min; // @[Ray_AABB_1.scala 479:24]
  reg [31:0] c0Max; // @[Ray_AABB_1.scala 480:24]
  reg [31:0] c1Min; // @[Ray_AABB_1.scala 481:24]
  reg [31:0] c1Max; // @[Ray_AABB_1.scala 482:24]
  reg [31:0] cidx_0_4; // @[Ray_AABB_1.scala 484:45]
  reg [31:0] cidx_1_4; // @[Ray_AABB_1.scala 485:45]
  reg [31:0] hitT_4; // @[Ray_AABB_1.scala 499:49]
  reg [31:0] rayid_4; // @[Ray_AABB_1.scala 501:48]
  reg  valid_4; // @[Ray_AABB_1.scala 503:49]
  reg [31:0] rayid_5; // @[Ray_AABB_1.scala 551:48]
  reg [31:0] hitT_5; // @[Ray_AABB_1.scala 553:49]
  reg  valid_5; // @[Ray_AABB_1.scala 555:49]
  reg [31:0] cidx_0_5; // @[Ray_AABB_1.scala 560:45]
  reg [31:0] cidx_1_5; // @[Ray_AABB_1.scala 561:45]
  reg  swp; // @[Ray_AABB_1.scala 567:49]
  wire  _T_47 = FCMP_21_io_actual_out > 1'h0; // @[Ray_AABB_1.scala 613:36]
  wire  _T_48 = ~traverseChild0; // @[Ray_AABB_1.scala 621:10]
  wire  _T_49 = ~traverseChild1; // @[Ray_AABB_1.scala 621:29]
  wire  _T_60 = ~cidx_1_5[31]; // @[common.scala 100:25]
  wire  _T_62 = ~_T_60; // @[Ray_AABB_1.scala 641:32]
  wire [31:0] _GEN_28 = _T_60 ? $signed(cidx_1_5) : $signed(32'sh0); // @[Ray_AABB_1.scala 650:45 Ray_AABB_1.scala 656:29 Ray_AABB_1.scala 665:29]
  wire  _GEN_31 = ~_T_60 ? 1'h0 : _T_60; // @[Ray_AABB_1.scala 641:39 Ray_AABB_1.scala 645:37]
  wire [31:0] _GEN_33 = ~_T_60 ? $signed(32'sh0) : $signed(_GEN_28); // @[Ray_AABB_1.scala 641:39 Ray_AABB_1.scala 647:30]
  wire [31:0] _GEN_34 = ~_T_60 ? $signed(cidx_1_5) : $signed(32'sh0); // @[Ray_AABB_1.scala 641:39 Ray_AABB_1.scala 648:30]
  wire  _GEN_35 = ~_T_60 | _T_60; // @[Ray_AABB_1.scala 641:39 Ray_AABB_1.scala 649:32]
  wire  _T_73 = ~cidx_0_5[31]; // @[common.scala 100:25]
  wire  _T_75 = ~_T_73; // @[Ray_AABB_1.scala 680:32]
  wire [31:0] _GEN_39 = _T_73 ? $signed(cidx_0_5) : $signed(32'sh0); // @[Ray_AABB_1.scala 689:45 Ray_AABB_1.scala 695:33 Ray_AABB_1.scala 704:33]
  wire  _GEN_42 = ~_T_73 ? 1'h0 : _T_73; // @[Ray_AABB_1.scala 680:39 Ray_AABB_1.scala 684:41]
  wire [31:0] _GEN_44 = ~_T_73 ? $signed(32'sh0) : $signed(_GEN_39); // @[Ray_AABB_1.scala 680:39 Ray_AABB_1.scala 686:34]
  wire [31:0] _GEN_45 = ~_T_73 ? $signed(cidx_0_5) : $signed(32'sh0); // @[Ray_AABB_1.scala 680:39 Ray_AABB_1.scala 687:34]
  wire  _GEN_46 = ~_T_73 | _T_73; // @[Ray_AABB_1.scala 680:39 Ray_AABB_1.scala 688:36]
  wire [31:0] _GEN_49 = _T_60 ? $signed(cidx_0_5) : $signed(32'sh0); // @[Ray_AABB_1.scala 719:49 Ray_AABB_1.scala 724:32 Ray_AABB_1.scala 733:32]
  wire [31:0] _GEN_56 = _T_62 ? $signed(cidx_0_5) : $signed(_GEN_49); // @[Ray_AABB_1.scala 710:43 Ray_AABB_1.scala 715:32]
  wire [31:0] _GEN_61 = _T_73 ? $signed(cidx_1_5) : $signed(32'sh0); // @[Ray_AABB_1.scala 748:49 Ray_AABB_1.scala 753:32 Ray_AABB_1.scala 762:32]
  wire [31:0] _GEN_68 = _T_75 ? $signed(cidx_1_5) : $signed(_GEN_61); // @[Ray_AABB_1.scala 739:43 Ray_AABB_1.scala 744:32]
  wire  _GEN_71 = ~swp & valid_5 & _GEN_46; // @[Ray_AABB_1.scala 738:49 Ray_AABB_1.scala 787:38]
  wire  _GEN_73 = ~swp & valid_5 & _T_75; // @[Ray_AABB_1.scala 738:49 Ray_AABB_1.scala 789:41]
  wire  _GEN_74 = ~swp & valid_5 & _GEN_42; // @[Ray_AABB_1.scala 738:49 Ray_AABB_1.scala 790:39]
  wire [31:0] _GEN_75 = ~swp & valid_5 ? $signed(_GEN_68) : $signed(32'sh0); // @[Ray_AABB_1.scala 738:49 Ray_AABB_1.scala 791:32]
  wire [31:0] _GEN_76 = ~swp & valid_5 ? $signed(_GEN_44) : $signed(32'sh0); // @[Ray_AABB_1.scala 738:49 Ray_AABB_1.scala 792:32]
  wire [31:0] _GEN_77 = ~swp & valid_5 ? $signed(_GEN_45) : $signed(32'sh0); // @[Ray_AABB_1.scala 738:49 Ray_AABB_1.scala 793:32]
  wire  _GEN_78 = swp & valid_5 ? _GEN_35 : _GEN_71; // @[Ray_AABB_1.scala 709:43]
  wire  _GEN_80 = swp & valid_5 ? _T_62 : _GEN_73; // @[Ray_AABB_1.scala 709:43]
  wire  _GEN_81 = swp & valid_5 ? _GEN_31 : _GEN_74; // @[Ray_AABB_1.scala 709:43]
  wire [31:0] _GEN_82 = swp & valid_5 ? $signed(_GEN_56) : $signed(_GEN_75); // @[Ray_AABB_1.scala 709:43]
  wire [31:0] _GEN_83 = swp & valid_5 ? $signed(_GEN_33) : $signed(_GEN_76); // @[Ray_AABB_1.scala 709:43]
  wire [31:0] _GEN_84 = swp & valid_5 ? $signed(_GEN_34) : $signed(_GEN_77); // @[Ray_AABB_1.scala 709:43]
  wire  _GEN_85 = traverseChild0 & traverseChild1 & valid_5 & _GEN_78; // @[Ray_AABB_1.scala 708:84 Ray_AABB_1.scala 797:30]
  wire  _GEN_87 = traverseChild0 & traverseChild1 & valid_5 & _GEN_80; // @[Ray_AABB_1.scala 708:84 Ray_AABB_1.scala 799:33]
  wire  _GEN_88 = traverseChild0 & traverseChild1 & valid_5 & _GEN_81; // @[Ray_AABB_1.scala 708:84 Ray_AABB_1.scala 800:31]
  wire [31:0] _GEN_89 = traverseChild0 & traverseChild1 & valid_5 ? $signed(_GEN_82) : $signed(32'sh0); // @[Ray_AABB_1.scala 708:84 Ray_AABB_1.scala 801:24]
  wire [31:0] _GEN_90 = traverseChild0 & traverseChild1 & valid_5 ? $signed(_GEN_83) : $signed(32'sh0); // @[Ray_AABB_1.scala 708:84 Ray_AABB_1.scala 802:24]
  wire [31:0] _GEN_91 = traverseChild0 & traverseChild1 & valid_5 ? $signed(_GEN_84) : $signed(32'sh0); // @[Ray_AABB_1.scala 708:84 Ray_AABB_1.scala 803:24]
  wire  _GEN_92 = traverseChild0 & _T_49 & valid_5 ? _T_75 : _GEN_87; // @[Ray_AABB_1.scala 669:78]
  wire  _GEN_94 = traverseChild0 & _T_49 & valid_5 ? 1'h0 : _GEN_85; // @[Ray_AABB_1.scala 669:78]
  wire  _GEN_95 = traverseChild0 & _T_49 & valid_5 ? _GEN_42 : _GEN_88; // @[Ray_AABB_1.scala 669:78]
  wire [31:0] _GEN_96 = traverseChild0 & _T_49 & valid_5 ? $signed(32'sh0) : $signed(_GEN_89); // @[Ray_AABB_1.scala 669:78]
  wire [31:0] _GEN_97 = traverseChild0 & _T_49 & valid_5 ? $signed(_GEN_44) : $signed(_GEN_90); // @[Ray_AABB_1.scala 669:78]
  wire [31:0] _GEN_98 = traverseChild0 & _T_49 & valid_5 ? $signed(_GEN_45) : $signed(_GEN_91); // @[Ray_AABB_1.scala 669:78]
  wire  _GEN_99 = traverseChild0 & _T_49 & valid_5 ? _GEN_46 : _GEN_85; // @[Ray_AABB_1.scala 669:78]
  wire  _GEN_100 = _T_48 & traverseChild1 & valid_5 ? _T_62 : _GEN_92; // @[Ray_AABB_1.scala 630:74]
  wire  _GEN_102 = _T_48 & traverseChild1 & valid_5 ? 1'h0 : _GEN_94; // @[Ray_AABB_1.scala 630:74]
  wire  _GEN_103 = _T_48 & traverseChild1 & valid_5 ? _GEN_31 : _GEN_95; // @[Ray_AABB_1.scala 630:74]
  wire [31:0] _GEN_104 = _T_48 & traverseChild1 & valid_5 ? $signed(32'sh0) : $signed(_GEN_96); // @[Ray_AABB_1.scala 630:74]
  wire [31:0] _GEN_105 = _T_48 & traverseChild1 & valid_5 ? $signed(_GEN_33) : $signed(_GEN_97); // @[Ray_AABB_1.scala 630:74]
  wire [31:0] _GEN_106 = _T_48 & traverseChild1 & valid_5 ? $signed(_GEN_34) : $signed(_GEN_98); // @[Ray_AABB_1.scala 630:74]
  wire  _GEN_107 = _T_48 & traverseChild1 & valid_5 ? _GEN_35 : _GEN_99; // @[Ray_AABB_1.scala 630:74]
  MY_MULADD FADD_MUL_1 ( // @[Ray_AABB_1.scala 121:33]
    .clock(FADD_MUL_1_clock),
    .reset(FADD_MUL_1_reset),
    .io_a(FADD_MUL_1_io_a),
    .io_b(FADD_MUL_1_io_b),
    .io_c(FADD_MUL_1_io_c),
    .io_out(FADD_MUL_1_io_out)
  );
  MY_MULADD FADD_MUL_2 ( // @[Ray_AABB_1.scala 131:33]
    .clock(FADD_MUL_2_clock),
    .reset(FADD_MUL_2_reset),
    .io_a(FADD_MUL_2_io_a),
    .io_b(FADD_MUL_2_io_b),
    .io_c(FADD_MUL_2_io_c),
    .io_out(FADD_MUL_2_io_out)
  );
  MY_MULADD FADD_MUL_3 ( // @[Ray_AABB_1.scala 141:33]
    .clock(FADD_MUL_3_clock),
    .reset(FADD_MUL_3_reset),
    .io_a(FADD_MUL_3_io_a),
    .io_b(FADD_MUL_3_io_b),
    .io_c(FADD_MUL_3_io_c),
    .io_out(FADD_MUL_3_io_out)
  );
  MY_MULADD FADD_MUL_4 ( // @[Ray_AABB_1.scala 151:33]
    .clock(FADD_MUL_4_clock),
    .reset(FADD_MUL_4_reset),
    .io_a(FADD_MUL_4_io_a),
    .io_b(FADD_MUL_4_io_b),
    .io_c(FADD_MUL_4_io_c),
    .io_out(FADD_MUL_4_io_out)
  );
  MY_MULADD FADD_MUL_5 ( // @[Ray_AABB_1.scala 161:33]
    .clock(FADD_MUL_5_clock),
    .reset(FADD_MUL_5_reset),
    .io_a(FADD_MUL_5_io_a),
    .io_b(FADD_MUL_5_io_b),
    .io_c(FADD_MUL_5_io_c),
    .io_out(FADD_MUL_5_io_out)
  );
  MY_MULADD FADD_MUL_6 ( // @[Ray_AABB_1.scala 171:33]
    .clock(FADD_MUL_6_clock),
    .reset(FADD_MUL_6_reset),
    .io_a(FADD_MUL_6_io_a),
    .io_b(FADD_MUL_6_io_b),
    .io_c(FADD_MUL_6_io_c),
    .io_out(FADD_MUL_6_io_out)
  );
  MY_MULADD FADD_MUL_7 ( // @[Ray_AABB_1.scala 181:33]
    .clock(FADD_MUL_7_clock),
    .reset(FADD_MUL_7_reset),
    .io_a(FADD_MUL_7_io_a),
    .io_b(FADD_MUL_7_io_b),
    .io_c(FADD_MUL_7_io_c),
    .io_out(FADD_MUL_7_io_out)
  );
  MY_MULADD FADD_MUL_8 ( // @[Ray_AABB_1.scala 191:33]
    .clock(FADD_MUL_8_clock),
    .reset(FADD_MUL_8_reset),
    .io_a(FADD_MUL_8_io_a),
    .io_b(FADD_MUL_8_io_b),
    .io_c(FADD_MUL_8_io_c),
    .io_out(FADD_MUL_8_io_out)
  );
  MY_MULADD FADD_MUL_9 ( // @[Ray_AABB_1.scala 201:33]
    .clock(FADD_MUL_9_clock),
    .reset(FADD_MUL_9_reset),
    .io_a(FADD_MUL_9_io_a),
    .io_b(FADD_MUL_9_io_b),
    .io_c(FADD_MUL_9_io_c),
    .io_out(FADD_MUL_9_io_out)
  );
  MY_MULADD FADD_MUL_10 ( // @[Ray_AABB_1.scala 211:33]
    .clock(FADD_MUL_10_clock),
    .reset(FADD_MUL_10_reset),
    .io_a(FADD_MUL_10_io_a),
    .io_b(FADD_MUL_10_io_b),
    .io_c(FADD_MUL_10_io_c),
    .io_out(FADD_MUL_10_io_out)
  );
  MY_MULADD FADD_MUL_11 ( // @[Ray_AABB_1.scala 221:33]
    .clock(FADD_MUL_11_clock),
    .reset(FADD_MUL_11_reset),
    .io_a(FADD_MUL_11_io_a),
    .io_b(FADD_MUL_11_io_b),
    .io_c(FADD_MUL_11_io_c),
    .io_out(FADD_MUL_11_io_out)
  );
  MY_MULADD FADD_MUL_12 ( // @[Ray_AABB_1.scala 231:33]
    .clock(FADD_MUL_12_clock),
    .reset(FADD_MUL_12_reset),
    .io_a(FADD_MUL_12_io_a),
    .io_b(FADD_MUL_12_io_b),
    .io_c(FADD_MUL_12_io_c),
    .io_out(FADD_MUL_12_io_out)
  );
  ValExec_CompareRecF32_le FCMP_1 ( // @[Ray_AABB_1.scala 279:24]
    .io_a(FCMP_1_io_a),
    .io_b(FCMP_1_io_b),
    .io_actual_out(FCMP_1_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_2 ( // @[Ray_AABB_1.scala 294:24]
    .io_a(FCMP_2_io_a),
    .io_b(FCMP_2_io_b),
    .io_actual_out(FCMP_2_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_3 ( // @[Ray_AABB_1.scala 307:24]
    .io_a(FCMP_3_io_a),
    .io_b(FCMP_3_io_b),
    .io_actual_out(FCMP_3_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_4 ( // @[Ray_AABB_1.scala 320:24]
    .io_a(FCMP_4_io_a),
    .io_b(FCMP_4_io_b),
    .io_actual_out(FCMP_4_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_5 ( // @[Ray_AABB_1.scala 333:25]
    .io_a(FCMP_5_io_a),
    .io_b(FCMP_5_io_b),
    .io_actual_out(FCMP_5_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_6 ( // @[Ray_AABB_1.scala 346:24]
    .io_a(FCMP_6_io_a),
    .io_b(FCMP_6_io_b),
    .io_actual_out(FCMP_6_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_7 ( // @[Ray_AABB_1.scala 391:24]
    .io_a(FCMP_7_io_a),
    .io_b(FCMP_7_io_b),
    .io_actual_out(FCMP_7_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_8 ( // @[Ray_AABB_1.scala 402:24]
    .io_a(FCMP_8_io_a),
    .io_b(FCMP_8_io_b),
    .io_actual_out(FCMP_8_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_9 ( // @[Ray_AABB_1.scala 413:24]
    .io_a(FCMP_9_io_a),
    .io_b(FCMP_9_io_b),
    .io_actual_out(FCMP_9_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_10 ( // @[Ray_AABB_1.scala 424:25]
    .io_a(FCMP_10_io_a),
    .io_b(FCMP_10_io_b),
    .io_actual_out(FCMP_10_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_11 ( // @[Ray_AABB_1.scala 435:25]
    .io_a(FCMP_11_io_a),
    .io_b(FCMP_11_io_b),
    .io_actual_out(FCMP_11_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_12 ( // @[Ray_AABB_1.scala 446:25]
    .io_a(FCMP_12_io_a),
    .io_b(FCMP_12_io_b),
    .io_actual_out(FCMP_12_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_13 ( // @[Ray_AABB_1.scala 457:25]
    .io_a(FCMP_13_io_a),
    .io_b(FCMP_13_io_b),
    .io_actual_out(FCMP_13_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_14 ( // @[Ray_AABB_1.scala 468:25]
    .io_a(FCMP_14_io_a),
    .io_b(FCMP_14_io_b),
    .io_actual_out(FCMP_14_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_15 ( // @[Ray_AABB_1.scala 506:25]
    .io_a(FCMP_15_io_a),
    .io_b(FCMP_15_io_b),
    .io_actual_out(FCMP_15_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_16 ( // @[Ray_AABB_1.scala 517:25]
    .io_a(FCMP_16_io_a),
    .io_b(FCMP_16_io_b),
    .io_actual_out(FCMP_16_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_17 ( // @[Ray_AABB_1.scala 528:25]
    .io_a(FCMP_17_io_a),
    .io_b(FCMP_17_io_b),
    .io_actual_out(FCMP_17_io_actual_out)
  );
  ValExec_CompareRecF32_le FCMP_18 ( // @[Ray_AABB_1.scala 539:25]
    .io_a(FCMP_18_io_a),
    .io_b(FCMP_18_io_b),
    .io_actual_out(FCMP_18_io_actual_out)
  );
  ValExec_CompareRecF32_lt FCMP_19 ( // @[Ray_AABB_1.scala 582:25]
    .io_a(FCMP_19_io_a),
    .io_b(FCMP_19_io_b),
    .io_actual_out(FCMP_19_io_actual_out)
  );
  ValExec_CompareRecF32_lt FCMP_20 ( // @[Ray_AABB_1.scala 595:25]
    .io_a(FCMP_20_io_a),
    .io_b(FCMP_20_io_b),
    .io_actual_out(FCMP_20_io_actual_out)
  );
  ValExec_CompareRecF32_lt FCMP_21 ( // @[Ray_AABB_1.scala 608:25]
    .io_a(FCMP_21_io_a),
    .io_b(FCMP_21_io_b),
    .io_actual_out(FCMP_21_io_actual_out)
  );
  assign io_rayid_out = rayid_5; // @[Ray_AABB_1.scala 619:47]
  assign io_nodeIdx_0 = ~traverseChild0 & ~traverseChild1 & valid_5 ? $signed(32'sh0) : $signed(_GEN_104); // @[Ray_AABB_1.scala 621:69 Ray_AABB_1.scala 626:26]
  assign io_nodeIdx_1 = ~traverseChild0 & ~traverseChild1 & valid_5 ? $signed(32'sh0) : $signed(_GEN_105); // @[Ray_AABB_1.scala 621:69 Ray_AABB_1.scala 627:26]
  assign io_nodeIdx_2 = ~traverseChild0 & ~traverseChild1 & valid_5 ? $signed(32'sh0) : $signed(_GEN_106); // @[Ray_AABB_1.scala 621:69 Ray_AABB_1.scala 628:26]
  assign io_push = ~traverseChild0 & ~traverseChild1 & valid_5 ? 1'h0 : _GEN_102; // @[Ray_AABB_1.scala 621:69 Ray_AABB_1.scala 623:32]
  assign io_pop = ~traverseChild0 & ~traverseChild1 & valid_5; // @[Ray_AABB_1.scala 621:51]
  assign io_leaf = ~traverseChild0 & ~traverseChild1 & valid_5 ? 1'h0 : _GEN_100; // @[Ray_AABB_1.scala 621:69 Ray_AABB_1.scala 624:35]
  assign io_back = ~traverseChild0 & ~traverseChild1 & valid_5 ? 1'h0 : _GEN_103; // @[Ray_AABB_1.scala 621:69 Ray_AABB_1.scala 625:33]
  assign io_hitT_out = hitT_5; // @[Ray_AABB_1.scala 620:48]
  assign io_valid_out = ~traverseChild0 & ~traverseChild1 & valid_5 | _GEN_107; // @[Ray_AABB_1.scala 621:69 Ray_AABB_1.scala 629:28]
  assign FADD_MUL_1_clock = clock;
  assign FADD_MUL_1_reset = reset;
  assign FADD_MUL_1_io_a = io_bvh_n0xy_x; // @[Ray_AABB_1.scala 122:21]
  assign FADD_MUL_1_io_b = io_ray_idir_x; // @[Ray_AABB_1.scala 123:21]
  assign FADD_MUL_1_io_c = {hi,lo}; // @[Cat.scala 30:58]
  assign FADD_MUL_2_clock = clock;
  assign FADD_MUL_2_reset = reset;
  assign FADD_MUL_2_io_a = io_bvh_n0xy_y; // @[Ray_AABB_1.scala 132:21]
  assign FADD_MUL_2_io_b = io_ray_idir_x; // @[Ray_AABB_1.scala 133:21]
  assign FADD_MUL_2_io_c = {hi,lo}; // @[Cat.scala 30:58]
  assign FADD_MUL_3_clock = clock;
  assign FADD_MUL_3_reset = reset;
  assign FADD_MUL_3_io_a = io_bvh_n0xy_z; // @[Ray_AABB_1.scala 142:21]
  assign FADD_MUL_3_io_b = io_ray_idir_y; // @[Ray_AABB_1.scala 143:21]
  assign FADD_MUL_3_io_c = {hi_2,lo_2}; // @[Cat.scala 30:58]
  assign FADD_MUL_4_clock = clock;
  assign FADD_MUL_4_reset = reset;
  assign FADD_MUL_4_io_a = io_bvh_n0xy_w; // @[Ray_AABB_1.scala 152:21]
  assign FADD_MUL_4_io_b = io_ray_idir_y; // @[Ray_AABB_1.scala 153:21]
  assign FADD_MUL_4_io_c = {hi_2,lo_2}; // @[Cat.scala 30:58]
  assign FADD_MUL_5_clock = clock;
  assign FADD_MUL_5_reset = reset;
  assign FADD_MUL_5_io_a = io_bvh_nz_x; // @[Ray_AABB_1.scala 162:21]
  assign FADD_MUL_5_io_b = io_ray_idir_z; // @[Ray_AABB_1.scala 163:21]
  assign FADD_MUL_5_io_c = {hi_4,lo_4}; // @[Cat.scala 30:58]
  assign FADD_MUL_6_clock = clock;
  assign FADD_MUL_6_reset = reset;
  assign FADD_MUL_6_io_a = io_bvh_nz_y; // @[Ray_AABB_1.scala 172:21]
  assign FADD_MUL_6_io_b = io_ray_idir_z; // @[Ray_AABB_1.scala 173:21]
  assign FADD_MUL_6_io_c = {hi_4,lo_4}; // @[Cat.scala 30:58]
  assign FADD_MUL_7_clock = clock;
  assign FADD_MUL_7_reset = reset;
  assign FADD_MUL_7_io_a = io_bvh_n1xy_x; // @[Ray_AABB_1.scala 182:21]
  assign FADD_MUL_7_io_b = io_ray_idir_x; // @[Ray_AABB_1.scala 183:21]
  assign FADD_MUL_7_io_c = {hi,lo}; // @[Cat.scala 30:58]
  assign FADD_MUL_8_clock = clock;
  assign FADD_MUL_8_reset = reset;
  assign FADD_MUL_8_io_a = io_bvh_n1xy_y; // @[Ray_AABB_1.scala 192:21]
  assign FADD_MUL_8_io_b = io_ray_idir_x; // @[Ray_AABB_1.scala 193:21]
  assign FADD_MUL_8_io_c = {hi,lo}; // @[Cat.scala 30:58]
  assign FADD_MUL_9_clock = clock;
  assign FADD_MUL_9_reset = reset;
  assign FADD_MUL_9_io_a = io_bvh_n1xy_z; // @[Ray_AABB_1.scala 202:21]
  assign FADD_MUL_9_io_b = io_ray_idir_y; // @[Ray_AABB_1.scala 203:21]
  assign FADD_MUL_9_io_c = {hi_2,lo_2}; // @[Cat.scala 30:58]
  assign FADD_MUL_10_clock = clock;
  assign FADD_MUL_10_reset = reset;
  assign FADD_MUL_10_io_a = io_bvh_n1xy_w; // @[Ray_AABB_1.scala 212:22]
  assign FADD_MUL_10_io_b = io_ray_idir_y; // @[Ray_AABB_1.scala 213:22]
  assign FADD_MUL_10_io_c = {hi_2,lo_2}; // @[Cat.scala 30:58]
  assign FADD_MUL_11_clock = clock;
  assign FADD_MUL_11_reset = reset;
  assign FADD_MUL_11_io_a = io_bvh_nz_z; // @[Ray_AABB_1.scala 222:22]
  assign FADD_MUL_11_io_b = io_ray_idir_z; // @[Ray_AABB_1.scala 223:22]
  assign FADD_MUL_11_io_c = {hi_4,lo_4}; // @[Cat.scala 30:58]
  assign FADD_MUL_12_clock = clock;
  assign FADD_MUL_12_reset = reset;
  assign FADD_MUL_12_io_a = io_bvh_nz_w; // @[Ray_AABB_1.scala 232:22]
  assign FADD_MUL_12_io_b = io_ray_idir_z; // @[Ray_AABB_1.scala 233:22]
  assign FADD_MUL_12_io_c = {hi_4,lo_4}; // @[Cat.scala 30:58]
  assign FCMP_1_io_a = c0lox; // @[Ray_AABB_1.scala 280:21]
  assign FCMP_1_io_b = c0hix; // @[Ray_AABB_1.scala 281:21]
  assign FCMP_2_io_a = c0loy; // @[Ray_AABB_1.scala 295:21]
  assign FCMP_2_io_b = c0hiy; // @[Ray_AABB_1.scala 296:21]
  assign FCMP_3_io_a = c0loz; // @[Ray_AABB_1.scala 308:21]
  assign FCMP_3_io_b = c0hiz; // @[Ray_AABB_1.scala 309:21]
  assign FCMP_4_io_a = c1lox; // @[Ray_AABB_1.scala 321:21]
  assign FCMP_4_io_b = c1hix; // @[Ray_AABB_1.scala 322:21]
  assign FCMP_5_io_a = c1loy; // @[Ray_AABB_1.scala 334:21]
  assign FCMP_5_io_b = c1hiy; // @[Ray_AABB_1.scala 335:21]
  assign FCMP_6_io_a = c1loz; // @[Ray_AABB_1.scala 347:21]
  assign FCMP_6_io_b = c1hiz; // @[Ray_AABB_1.scala 348:21]
  assign FCMP_7_io_a = cmpMin0_1; // @[Ray_AABB_1.scala 392:21]
  assign FCMP_7_io_b = cmpMin0_2; // @[Ray_AABB_1.scala 393:21]
  assign FCMP_8_io_a = cmpMin0_3; // @[Ray_AABB_1.scala 403:21]
  assign FCMP_8_io_b = 32'h0; // @[Ray_AABB_1.scala 404:21]
  assign FCMP_9_io_a = cmpMax0_1; // @[Ray_AABB_1.scala 414:21]
  assign FCMP_9_io_b = cmpMax0_2; // @[Ray_AABB_1.scala 415:21]
  assign FCMP_10_io_a = cmpMax0_3; // @[Ray_AABB_1.scala 425:22]
  assign FCMP_10_io_b = hitT_2; // @[Ray_AABB_1.scala 426:22]
  assign FCMP_11_io_a = cmpMin1_1; // @[Ray_AABB_1.scala 436:22]
  assign FCMP_11_io_b = cmpMin1_2; // @[Ray_AABB_1.scala 437:22]
  assign FCMP_12_io_a = cmpMin1_3; // @[Ray_AABB_1.scala 447:22]
  assign FCMP_12_io_b = 32'h0; // @[Ray_AABB_1.scala 448:22]
  assign FCMP_13_io_a = cmpMax1_1; // @[Ray_AABB_1.scala 458:22]
  assign FCMP_13_io_b = cmpMax1_2; // @[Ray_AABB_1.scala 459:22]
  assign FCMP_14_io_a = cmpMax1_3; // @[Ray_AABB_1.scala 469:22]
  assign FCMP_14_io_b = hitT_2; // @[Ray_AABB_1.scala 470:22]
  assign FCMP_15_io_a = c0Min_temp_1; // @[Ray_AABB_1.scala 507:22]
  assign FCMP_15_io_b = c0Min_temp_2; // @[Ray_AABB_1.scala 508:22]
  assign FCMP_16_io_a = c0Max_temp_1; // @[Ray_AABB_1.scala 518:22]
  assign FCMP_16_io_b = c0Max_temp_2; // @[Ray_AABB_1.scala 519:22]
  assign FCMP_17_io_a = c1Min_temp_1; // @[Ray_AABB_1.scala 529:22]
  assign FCMP_17_io_b = c1Min_temp_2; // @[Ray_AABB_1.scala 530:22]
  assign FCMP_18_io_a = c1Max_temp_1; // @[Ray_AABB_1.scala 540:22]
  assign FCMP_18_io_b = c1Max_temp_2; // @[Ray_AABB_1.scala 541:22]
  assign FCMP_19_io_a = c0Max; // @[Ray_AABB_1.scala 583:22]
  assign FCMP_19_io_b = c0Min; // @[Ray_AABB_1.scala 584:22]
  assign FCMP_20_io_a = c1Max; // @[Ray_AABB_1.scala 596:22]
  assign FCMP_20_io_b = c1Min; // @[Ray_AABB_1.scala 597:22]
  assign FCMP_21_io_a = c1Min; // @[Ray_AABB_1.scala 609:22]
  assign FCMP_21_io_b = c0Min; // @[Ray_AABB_1.scala 610:22]
  always @(posedge clock) begin
    if (reset) begin // @[Ray_AABB_1.scala 73:33]
      traverseChild0 <= 1'h0; // @[Ray_AABB_1.scala 73:33]
    end else if (FCMP_19_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 587:42]
      traverseChild0 <= 1'h0; // @[Ray_AABB_1.scala 588:28]
    end else begin
      traverseChild0 <= 1'h1; // @[Ray_AABB_1.scala 590:28]
    end
    if (reset) begin // @[Ray_AABB_1.scala 74:33]
      traverseChild1 <= 1'h0; // @[Ray_AABB_1.scala 74:33]
    end else if (FCMP_20_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 600:42]
      traverseChild1 <= 1'h0; // @[Ray_AABB_1.scala 601:28]
    end else begin
      traverseChild1 <= 1'h1; // @[Ray_AABB_1.scala 603:28]
    end
    if (reset) begin // @[Ray_AABB_1.scala 76:34]
      c0lox <= 32'h0; // @[Ray_AABB_1.scala 76:34]
    end else begin
      c0lox <= FADD_MUL_1_io_out; // @[Ray_AABB_1.scala 129:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 77:34]
      c0hix <= 32'h0; // @[Ray_AABB_1.scala 77:34]
    end else begin
      c0hix <= FADD_MUL_2_io_out; // @[Ray_AABB_1.scala 139:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 78:34]
      c0loy <= 32'h0; // @[Ray_AABB_1.scala 78:34]
    end else begin
      c0loy <= FADD_MUL_3_io_out; // @[Ray_AABB_1.scala 149:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 79:33]
      c0hiy <= 32'h0; // @[Ray_AABB_1.scala 79:33]
    end else begin
      c0hiy <= FADD_MUL_4_io_out; // @[Ray_AABB_1.scala 159:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 80:34]
      c0loz <= 32'h0; // @[Ray_AABB_1.scala 80:34]
    end else begin
      c0loz <= FADD_MUL_5_io_out; // @[Ray_AABB_1.scala 169:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 81:34]
      c0hiz <= 32'h0; // @[Ray_AABB_1.scala 81:34]
    end else begin
      c0hiz <= FADD_MUL_6_io_out; // @[Ray_AABB_1.scala 179:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 83:34]
      c1lox <= 32'h0; // @[Ray_AABB_1.scala 83:34]
    end else begin
      c1lox <= FADD_MUL_7_io_out; // @[Ray_AABB_1.scala 189:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 84:34]
      c1hix <= 32'h0; // @[Ray_AABB_1.scala 84:34]
    end else begin
      c1hix <= FADD_MUL_8_io_out; // @[Ray_AABB_1.scala 199:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 85:34]
      c1loy <= 32'h0; // @[Ray_AABB_1.scala 85:34]
    end else begin
      c1loy <= FADD_MUL_9_io_out; // @[Ray_AABB_1.scala 209:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 86:34]
      c1hiy <= 32'h0; // @[Ray_AABB_1.scala 86:34]
    end else begin
      c1hiy <= FADD_MUL_10_io_out; // @[Ray_AABB_1.scala 219:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 87:34]
      c1loz <= 32'h0; // @[Ray_AABB_1.scala 87:34]
    end else begin
      c1loz <= FADD_MUL_11_io_out; // @[Ray_AABB_1.scala 229:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 88:34]
      c1hiz <= 32'h0; // @[Ray_AABB_1.scala 88:34]
    end else begin
      c1hiz <= FADD_MUL_12_io_out; // @[Ray_AABB_1.scala 239:37]
    end
    if (reset) begin // @[Ray_AABB_1.scala 90:32]
      rayid_1 <= 32'h0; // @[Ray_AABB_1.scala 90:32]
    end else begin
      rayid_1 <= io_rayid; // @[Ray_AABB_1.scala 96:41]
    end
    if (reset) begin // @[Ray_AABB_1.scala 91:33]
      hitT_1 <= 32'h0; // @[Ray_AABB_1.scala 91:33]
    end else begin
      hitT_1 <= io_ray_hitT; // @[Ray_AABB_1.scala 98:43]
    end
    if (reset) begin // @[Ray_AABB_1.scala 93:32]
      valid_1 <= 32'h0; // @[Ray_AABB_1.scala 93:32]
    end else begin
      valid_1 <= {{31'd0}, io_valid_en}; // @[Ray_AABB_1.scala 99:42]
    end
    if (reset) begin // @[Ray_AABB_1.scala 94:45]
      cidx_0_1 <= 32'sh0; // @[Ray_AABB_1.scala 94:45]
    end else begin
      cidx_0_1 <= io_bvh_temp_x; // @[Ray_AABB_1.scala 100:44]
    end
    if (reset) begin // @[Ray_AABB_1.scala 95:45]
      cidx_1_1 <= 32'sh0; // @[Ray_AABB_1.scala 95:45]
    end else begin
      cidx_1_1 <= io_bvh_temp_y; // @[Ray_AABB_1.scala 101:44]
    end
    if (reset) begin // @[Ray_AABB_1.scala 103:35]
      rayid_temp <= 32'h0; // @[Ray_AABB_1.scala 103:35]
    end else begin
      rayid_temp <= rayid_1; // @[Ray_AABB_1.scala 110:31]
    end
    if (reset) begin // @[Ray_AABB_1.scala 104:36]
      hitT_temp <= 32'h0; // @[Ray_AABB_1.scala 104:36]
    end else begin
      hitT_temp <= hitT_1; // @[Ray_AABB_1.scala 108:32]
    end
    if (reset) begin // @[Ray_AABB_1.scala 106:35]
      valid_temp <= 32'h0; // @[Ray_AABB_1.scala 106:35]
    end else begin
      valid_temp <= valid_1; // @[Ray_AABB_1.scala 111:32]
    end
    if (reset) begin // @[Ray_AABB_1.scala 113:48]
      cidx_0_temp <= 32'sh0; // @[Ray_AABB_1.scala 113:48]
    end else begin
      cidx_0_temp <= cidx_0_1; // @[Ray_AABB_1.scala 117:41]
    end
    if (reset) begin // @[Ray_AABB_1.scala 114:48]
      cidx_1_temp <= 32'sh0; // @[Ray_AABB_1.scala 114:48]
    end else begin
      cidx_1_temp <= cidx_1_1; // @[Ray_AABB_1.scala 118:41]
    end
    if (reset) begin // @[Ray_AABB_1.scala 242:45]
      cidx_0_2 <= 32'sh0; // @[Ray_AABB_1.scala 242:45]
    end else begin
      cidx_0_2 <= cidx_0_temp; // @[Ray_AABB_1.scala 249:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 243:45]
      cidx_1_2 <= 32'sh0; // @[Ray_AABB_1.scala 243:45]
    end else begin
      cidx_1_2 <= cidx_1_temp; // @[Ray_AABB_1.scala 250:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 256:32]
      rayid_2 <= 32'h0; // @[Ray_AABB_1.scala 256:32]
    end else begin
      rayid_2 <= rayid_temp; // @[Ray_AABB_1.scala 263:28]
    end
    if (reset) begin // @[Ray_AABB_1.scala 257:33]
      hitT_2 <= 32'h0; // @[Ray_AABB_1.scala 257:33]
    end else begin
      hitT_2 <= hitT_temp; // @[Ray_AABB_1.scala 261:29]
    end
    if (reset) begin // @[Ray_AABB_1.scala 259:32]
      valid_2 <= 32'h0; // @[Ray_AABB_1.scala 259:32]
    end else begin
      valid_2 <= valid_temp; // @[Ray_AABB_1.scala 264:29]
    end
    if (reset) begin // @[Ray_AABB_1.scala 266:28]
      cmpMin0_1 <= 32'h0; // @[Ray_AABB_1.scala 266:28]
    end else if (FCMP_1_io_actual_out) begin // @[Ray_AABB_1.scala 284:25]
      cmpMin0_1 <= c0lox;
    end else begin
      cmpMin0_1 <= c0hix;
    end
    if (reset) begin // @[Ray_AABB_1.scala 267:28]
      cmpMin0_2 <= 32'h0; // @[Ray_AABB_1.scala 267:28]
    end else if (FCMP_2_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 299:41]
      cmpMin0_2 <= c0loy; // @[Ray_AABB_1.scala 300:23]
    end else begin
      cmpMin0_2 <= c0hiy; // @[Ray_AABB_1.scala 303:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 268:28]
      cmpMin0_3 <= 32'h0; // @[Ray_AABB_1.scala 268:28]
    end else if (FCMP_3_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 312:41]
      cmpMin0_3 <= c0loz; // @[Ray_AABB_1.scala 313:23]
    end else begin
      cmpMin0_3 <= c0hiz; // @[Ray_AABB_1.scala 316:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 269:28]
      cmpMax0_1 <= 32'h0; // @[Ray_AABB_1.scala 269:28]
    end else if (_T_24) begin // @[Ray_AABB_1.scala 285:25]
      cmpMax0_1 <= c0hix;
    end else begin
      cmpMax0_1 <= c0lox;
    end
    if (reset) begin // @[Ray_AABB_1.scala 270:28]
      cmpMax0_2 <= 32'h0; // @[Ray_AABB_1.scala 270:28]
    end else if (FCMP_2_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 299:41]
      cmpMax0_2 <= c0hiy; // @[Ray_AABB_1.scala 301:23]
    end else begin
      cmpMax0_2 <= c0loy; // @[Ray_AABB_1.scala 304:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 271:28]
      cmpMax0_3 <= 32'h0; // @[Ray_AABB_1.scala 271:28]
    end else if (FCMP_3_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 312:41]
      cmpMax0_3 <= c0hiz; // @[Ray_AABB_1.scala 314:23]
    end else begin
      cmpMax0_3 <= c0loz; // @[Ray_AABB_1.scala 317:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 272:28]
      cmpMin1_1 <= 32'h0; // @[Ray_AABB_1.scala 272:28]
    end else if (FCMP_4_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 325:41]
      cmpMin1_1 <= c1lox; // @[Ray_AABB_1.scala 326:23]
    end else begin
      cmpMin1_1 <= c1hix; // @[Ray_AABB_1.scala 329:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 273:28]
      cmpMin1_2 <= 32'h0; // @[Ray_AABB_1.scala 273:28]
    end else if (FCMP_5_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 338:41]
      cmpMin1_2 <= c1loy; // @[Ray_AABB_1.scala 339:23]
    end else begin
      cmpMin1_2 <= c1hiy; // @[Ray_AABB_1.scala 342:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 274:28]
      cmpMin1_3 <= 32'h0; // @[Ray_AABB_1.scala 274:28]
    end else if (FCMP_6_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 351:41]
      cmpMin1_3 <= c1loz; // @[Ray_AABB_1.scala 352:23]
    end else begin
      cmpMin1_3 <= c1hiz; // @[Ray_AABB_1.scala 355:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 275:28]
      cmpMax1_1 <= 32'h0; // @[Ray_AABB_1.scala 275:28]
    end else if (FCMP_4_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 325:41]
      cmpMax1_1 <= c1hix; // @[Ray_AABB_1.scala 327:23]
    end else begin
      cmpMax1_1 <= c1lox; // @[Ray_AABB_1.scala 330:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 276:28]
      cmpMax1_2 <= 32'h0; // @[Ray_AABB_1.scala 276:28]
    end else if (FCMP_5_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 338:41]
      cmpMax1_2 <= c1hiy; // @[Ray_AABB_1.scala 340:23]
    end else begin
      cmpMax1_2 <= c1loy; // @[Ray_AABB_1.scala 343:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 277:28]
      cmpMax1_3 <= 32'h0; // @[Ray_AABB_1.scala 277:28]
    end else if (FCMP_6_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 351:41]
      cmpMax1_3 <= c1hiz; // @[Ray_AABB_1.scala 353:23]
    end else begin
      cmpMax1_3 <= c1loz; // @[Ray_AABB_1.scala 356:23]
    end
    if (reset) begin // @[Ray_AABB_1.scala 360:31]
      c0Min_temp_1 <= 32'h0; // @[Ray_AABB_1.scala 360:31]
    end else if (FCMP_7_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 396:41]
      c0Min_temp_1 <= cmpMin0_2; // @[Ray_AABB_1.scala 397:26]
    end else begin
      c0Min_temp_1 <= cmpMin0_1; // @[Ray_AABB_1.scala 399:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 361:31]
      c0Min_temp_2 <= 32'h0; // @[Ray_AABB_1.scala 361:31]
    end else if (FCMP_8_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 407:41]
      c0Min_temp_2 <= 32'h0; // @[Ray_AABB_1.scala 408:26]
    end else begin
      c0Min_temp_2 <= cmpMin0_3; // @[Ray_AABB_1.scala 410:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 362:31]
      c0Max_temp_1 <= 32'h0; // @[Ray_AABB_1.scala 362:31]
    end else if (FCMP_9_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 418:41]
      c0Max_temp_1 <= cmpMax0_1; // @[Ray_AABB_1.scala 419:26]
    end else begin
      c0Max_temp_1 <= cmpMax0_2; // @[Ray_AABB_1.scala 421:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 363:31]
      c0Max_temp_2 <= 32'h0; // @[Ray_AABB_1.scala 363:31]
    end else if (FCMP_10_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 429:42]
      c0Max_temp_2 <= cmpMax0_3; // @[Ray_AABB_1.scala 430:26]
    end else begin
      c0Max_temp_2 <= hitT_2; // @[Ray_AABB_1.scala 432:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 364:31]
      c1Min_temp_1 <= 32'h0; // @[Ray_AABB_1.scala 364:31]
    end else if (FCMP_11_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 440:42]
      c1Min_temp_1 <= cmpMin1_2; // @[Ray_AABB_1.scala 441:26]
    end else begin
      c1Min_temp_1 <= cmpMin1_1; // @[Ray_AABB_1.scala 443:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 365:31]
      c1Min_temp_2 <= 32'h0; // @[Ray_AABB_1.scala 365:31]
    end else if (FCMP_12_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 451:42]
      c1Min_temp_2 <= 32'h0; // @[Ray_AABB_1.scala 452:26]
    end else begin
      c1Min_temp_2 <= cmpMin1_3; // @[Ray_AABB_1.scala 454:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 366:31]
      c1Max_temp_1 <= 32'h0; // @[Ray_AABB_1.scala 366:31]
    end else if (FCMP_13_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 462:42]
      c1Max_temp_1 <= cmpMax1_1; // @[Ray_AABB_1.scala 463:26]
    end else begin
      c1Max_temp_1 <= cmpMax1_2; // @[Ray_AABB_1.scala 465:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 367:31]
      c1Max_temp_2 <= 32'h0; // @[Ray_AABB_1.scala 367:31]
    end else if (FCMP_14_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 473:42]
      c1Max_temp_2 <= cmpMax1_3; // @[Ray_AABB_1.scala 474:26]
    end else begin
      c1Max_temp_2 <= hitT_2; // @[Ray_AABB_1.scala 476:26]
    end
    if (reset) begin // @[Ray_AABB_1.scala 369:45]
      cidx_0_3 <= 32'sh0; // @[Ray_AABB_1.scala 369:45]
    end else begin
      cidx_0_3 <= cidx_0_2; // @[Ray_AABB_1.scala 377:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 370:45]
      cidx_1_3 <= 32'sh0; // @[Ray_AABB_1.scala 370:45]
    end else begin
      cidx_1_3 <= cidx_1_2; // @[Ray_AABB_1.scala 378:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 384:49]
      hitT_3 <= 32'h0; // @[Ray_AABB_1.scala 384:49]
    end else begin
      hitT_3 <= hitT_2; // @[Ray_AABB_1.scala 385:42]
    end
    if (reset) begin // @[Ray_AABB_1.scala 386:48]
      rayid_3 <= 32'h0; // @[Ray_AABB_1.scala 386:48]
    end else begin
      rayid_3 <= rayid_2; // @[Ray_AABB_1.scala 387:41]
    end
    if (reset) begin // @[Ray_AABB_1.scala 388:49]
      valid_3 <= 1'h0; // @[Ray_AABB_1.scala 388:49]
    end else begin
      valid_3 <= valid_2[0]; // @[Ray_AABB_1.scala 389:41]
    end
    if (reset) begin // @[Ray_AABB_1.scala 479:24]
      c0Min <= 32'h0; // @[Ray_AABB_1.scala 479:24]
    end else if (FCMP_15_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 511:42]
      c0Min <= c0Min_temp_2; // @[Ray_AABB_1.scala 512:19]
    end else begin
      c0Min <= c0Min_temp_1; // @[Ray_AABB_1.scala 514:19]
    end
    if (reset) begin // @[Ray_AABB_1.scala 480:24]
      c0Max <= 32'h0; // @[Ray_AABB_1.scala 480:24]
    end else if (FCMP_16_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 522:42]
      c0Max <= c0Max_temp_1; // @[Ray_AABB_1.scala 523:19]
    end else begin
      c0Max <= c0Max_temp_2; // @[Ray_AABB_1.scala 525:19]
    end
    if (reset) begin // @[Ray_AABB_1.scala 481:24]
      c1Min <= 32'h0; // @[Ray_AABB_1.scala 481:24]
    end else if (FCMP_17_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 533:42]
      c1Min <= c1Min_temp_2; // @[Ray_AABB_1.scala 534:19]
    end else begin
      c1Min <= c1Min_temp_1; // @[Ray_AABB_1.scala 536:19]
    end
    if (reset) begin // @[Ray_AABB_1.scala 482:24]
      c1Max <= 32'h0; // @[Ray_AABB_1.scala 482:24]
    end else if (FCMP_18_io_actual_out > 1'h0) begin // @[Ray_AABB_1.scala 544:42]
      c1Max <= c1Max_temp_1; // @[Ray_AABB_1.scala 545:19]
    end else begin
      c1Max <= c1Max_temp_2; // @[Ray_AABB_1.scala 547:19]
    end
    if (reset) begin // @[Ray_AABB_1.scala 484:45]
      cidx_0_4 <= 32'sh0; // @[Ray_AABB_1.scala 484:45]
    end else begin
      cidx_0_4 <= cidx_0_3; // @[Ray_AABB_1.scala 492:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 485:45]
      cidx_1_4 <= 32'sh0; // @[Ray_AABB_1.scala 485:45]
    end else begin
      cidx_1_4 <= cidx_1_3; // @[Ray_AABB_1.scala 493:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 499:49]
      hitT_4 <= 32'h0; // @[Ray_AABB_1.scala 499:49]
    end else begin
      hitT_4 <= hitT_3; // @[Ray_AABB_1.scala 500:43]
    end
    if (reset) begin // @[Ray_AABB_1.scala 501:48]
      rayid_4 <= 32'h0; // @[Ray_AABB_1.scala 501:48]
    end else begin
      rayid_4 <= rayid_3; // @[Ray_AABB_1.scala 502:42]
    end
    if (reset) begin // @[Ray_AABB_1.scala 503:49]
      valid_4 <= 1'h0; // @[Ray_AABB_1.scala 503:49]
    end else begin
      valid_4 <= valid_3; // @[Ray_AABB_1.scala 504:41]
    end
    if (reset) begin // @[Ray_AABB_1.scala 551:48]
      rayid_5 <= 32'h0; // @[Ray_AABB_1.scala 551:48]
    end else begin
      rayid_5 <= rayid_4; // @[Ray_AABB_1.scala 552:42]
    end
    if (reset) begin // @[Ray_AABB_1.scala 553:49]
      hitT_5 <= 32'h0; // @[Ray_AABB_1.scala 553:49]
    end else begin
      hitT_5 <= hitT_4; // @[Ray_AABB_1.scala 554:42]
    end
    if (reset) begin // @[Ray_AABB_1.scala 555:49]
      valid_5 <= 1'h0; // @[Ray_AABB_1.scala 555:49]
    end else begin
      valid_5 <= valid_4; // @[Ray_AABB_1.scala 556:41]
    end
    if (reset) begin // @[Ray_AABB_1.scala 560:45]
      cidx_0_5 <= 32'sh0; // @[Ray_AABB_1.scala 560:45]
    end else begin
      cidx_0_5 <= cidx_0_4; // @[Ray_AABB_1.scala 572:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 561:45]
      cidx_1_5 <= 32'sh0; // @[Ray_AABB_1.scala 561:45]
    end else begin
      cidx_1_5 <= cidx_1_4; // @[Ray_AABB_1.scala 573:38]
    end
    if (reset) begin // @[Ray_AABB_1.scala 567:49]
      swp <= 1'h0; // @[Ray_AABB_1.scala 567:49]
    end else begin
      swp <= _T_47;
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
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  traverseChild0 = _RAND_0[0:0];
  _RAND_1 = {1{`RANDOM}};
  traverseChild1 = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  c0lox = _RAND_2[31:0];
  _RAND_3 = {1{`RANDOM}};
  c0hix = _RAND_3[31:0];
  _RAND_4 = {1{`RANDOM}};
  c0loy = _RAND_4[31:0];
  _RAND_5 = {1{`RANDOM}};
  c0hiy = _RAND_5[31:0];
  _RAND_6 = {1{`RANDOM}};
  c0loz = _RAND_6[31:0];
  _RAND_7 = {1{`RANDOM}};
  c0hiz = _RAND_7[31:0];
  _RAND_8 = {1{`RANDOM}};
  c1lox = _RAND_8[31:0];
  _RAND_9 = {1{`RANDOM}};
  c1hix = _RAND_9[31:0];
  _RAND_10 = {1{`RANDOM}};
  c1loy = _RAND_10[31:0];
  _RAND_11 = {1{`RANDOM}};
  c1hiy = _RAND_11[31:0];
  _RAND_12 = {1{`RANDOM}};
  c1loz = _RAND_12[31:0];
  _RAND_13 = {1{`RANDOM}};
  c1hiz = _RAND_13[31:0];
  _RAND_14 = {1{`RANDOM}};
  rayid_1 = _RAND_14[31:0];
  _RAND_15 = {1{`RANDOM}};
  hitT_1 = _RAND_15[31:0];
  _RAND_16 = {1{`RANDOM}};
  valid_1 = _RAND_16[31:0];
  _RAND_17 = {1{`RANDOM}};
  cidx_0_1 = _RAND_17[31:0];
  _RAND_18 = {1{`RANDOM}};
  cidx_1_1 = _RAND_18[31:0];
  _RAND_19 = {1{`RANDOM}};
  rayid_temp = _RAND_19[31:0];
  _RAND_20 = {1{`RANDOM}};
  hitT_temp = _RAND_20[31:0];
  _RAND_21 = {1{`RANDOM}};
  valid_temp = _RAND_21[31:0];
  _RAND_22 = {1{`RANDOM}};
  cidx_0_temp = _RAND_22[31:0];
  _RAND_23 = {1{`RANDOM}};
  cidx_1_temp = _RAND_23[31:0];
  _RAND_24 = {1{`RANDOM}};
  cidx_0_2 = _RAND_24[31:0];
  _RAND_25 = {1{`RANDOM}};
  cidx_1_2 = _RAND_25[31:0];
  _RAND_26 = {1{`RANDOM}};
  rayid_2 = _RAND_26[31:0];
  _RAND_27 = {1{`RANDOM}};
  hitT_2 = _RAND_27[31:0];
  _RAND_28 = {1{`RANDOM}};
  valid_2 = _RAND_28[31:0];
  _RAND_29 = {1{`RANDOM}};
  cmpMin0_1 = _RAND_29[31:0];
  _RAND_30 = {1{`RANDOM}};
  cmpMin0_2 = _RAND_30[31:0];
  _RAND_31 = {1{`RANDOM}};
  cmpMin0_3 = _RAND_31[31:0];
  _RAND_32 = {1{`RANDOM}};
  cmpMax0_1 = _RAND_32[31:0];
  _RAND_33 = {1{`RANDOM}};
  cmpMax0_2 = _RAND_33[31:0];
  _RAND_34 = {1{`RANDOM}};
  cmpMax0_3 = _RAND_34[31:0];
  _RAND_35 = {1{`RANDOM}};
  cmpMin1_1 = _RAND_35[31:0];
  _RAND_36 = {1{`RANDOM}};
  cmpMin1_2 = _RAND_36[31:0];
  _RAND_37 = {1{`RANDOM}};
  cmpMin1_3 = _RAND_37[31:0];
  _RAND_38 = {1{`RANDOM}};
  cmpMax1_1 = _RAND_38[31:0];
  _RAND_39 = {1{`RANDOM}};
  cmpMax1_2 = _RAND_39[31:0];
  _RAND_40 = {1{`RANDOM}};
  cmpMax1_3 = _RAND_40[31:0];
  _RAND_41 = {1{`RANDOM}};
  c0Min_temp_1 = _RAND_41[31:0];
  _RAND_42 = {1{`RANDOM}};
  c0Min_temp_2 = _RAND_42[31:0];
  _RAND_43 = {1{`RANDOM}};
  c0Max_temp_1 = _RAND_43[31:0];
  _RAND_44 = {1{`RANDOM}};
  c0Max_temp_2 = _RAND_44[31:0];
  _RAND_45 = {1{`RANDOM}};
  c1Min_temp_1 = _RAND_45[31:0];
  _RAND_46 = {1{`RANDOM}};
  c1Min_temp_2 = _RAND_46[31:0];
  _RAND_47 = {1{`RANDOM}};
  c1Max_temp_1 = _RAND_47[31:0];
  _RAND_48 = {1{`RANDOM}};
  c1Max_temp_2 = _RAND_48[31:0];
  _RAND_49 = {1{`RANDOM}};
  cidx_0_3 = _RAND_49[31:0];
  _RAND_50 = {1{`RANDOM}};
  cidx_1_3 = _RAND_50[31:0];
  _RAND_51 = {1{`RANDOM}};
  hitT_3 = _RAND_51[31:0];
  _RAND_52 = {1{`RANDOM}};
  rayid_3 = _RAND_52[31:0];
  _RAND_53 = {1{`RANDOM}};
  valid_3 = _RAND_53[0:0];
  _RAND_54 = {1{`RANDOM}};
  c0Min = _RAND_54[31:0];
  _RAND_55 = {1{`RANDOM}};
  c0Max = _RAND_55[31:0];
  _RAND_56 = {1{`RANDOM}};
  c1Min = _RAND_56[31:0];
  _RAND_57 = {1{`RANDOM}};
  c1Max = _RAND_57[31:0];
  _RAND_58 = {1{`RANDOM}};
  cidx_0_4 = _RAND_58[31:0];
  _RAND_59 = {1{`RANDOM}};
  cidx_1_4 = _RAND_59[31:0];
  _RAND_60 = {1{`RANDOM}};
  hitT_4 = _RAND_60[31:0];
  _RAND_61 = {1{`RANDOM}};
  rayid_4 = _RAND_61[31:0];
  _RAND_62 = {1{`RANDOM}};
  valid_4 = _RAND_62[0:0];
  _RAND_63 = {1{`RANDOM}};
  rayid_5 = _RAND_63[31:0];
  _RAND_64 = {1{`RANDOM}};
  hitT_5 = _RAND_64[31:0];
  _RAND_65 = {1{`RANDOM}};
  valid_5 = _RAND_65[0:0];
  _RAND_66 = {1{`RANDOM}};
  cidx_0_5 = _RAND_66[31:0];
  _RAND_67 = {1{`RANDOM}};
  cidx_1_5 = _RAND_67[31:0];
  _RAND_68 = {1{`RANDOM}};
  swp = _RAND_68[0:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
