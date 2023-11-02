In order to test this part of the module, the data is taken from the C simulation as follows:
rayid = 1000  last traverse
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   case 1   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  n0xy.x | 25.263599  |  1103764441  |  n1xy.x | 31.924400  |  1107256619  |   nz.x  |  5.300000  |  1084856729  |
|  n0xy.y | 31.924400  |  1107256619  |  n1xy.y | 31.924400  |  1107256619  |   nz.y  |  5.550000  |  1085381017  |
|  n0xy.z | -0.748502  |  3208617427  |  n1xy.z | -0.748502  |  3208617427  |   nz.z  |  0.050000  |  1028443336  |
|  n0xy.w | 10.575800  |  1093219962  |  n1xy.w | 10.575800  |  1093219962  |   nz.w  |  5.300000  |  1084856729  |

|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  idirx  |  2.805120  |  1077118742  |  oodx   |  67.132812 |  1116095487  |
|  idiry  |  1.093137  |  1066134505  |  oody   |  -6.646183 |  3235163527  |
|  idirz  |  5.266240  |  1084785929  |  oodz   |  0.521043  |  1057317650  |

|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |
|  tmin   |     0      |       0      |
|  hitT   |  7.877372  |  1090261870  |

expected value setp(2):
|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  c0lox  |  3.734619  |  1081017343  |  c1lox  | 22.418968  |  1102273035  | 
|  c0hix  | 22.418968  |  1102273035  |  c1hix  | 22.418968  |  1102273035  |
|  c0loy  |  5.827968  |  1085963958  |  c1loy  | 5.827968   |  1085963958  | 
|  c0hiy  | 18.206978  |  1100064740  |  c1hiy  | 18.206978  |  1100064740  |
|  c0loz  | 27.390030  |  1104879304  |  c1loz  | -0.257730  |  3196319023  | 
|  c0hiz  | 28.706591  |  1105569561  |  c1hiz  | 27.390030  |  1104879304  |


expected value setp(3):
|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  fmin_1 |  3.734619  |  1081017343  |  fmax_1 | 22.418968  |  1102273035  | 
|  fmin_2 |  5.827968  |  1085963958  |  fmax_2 | 18.206978  |  1100064740  |
|  fmin_3 |  27.390030 |  1104879304  |  fmax_3 | 28.706591  |  1105569561  | 
|  fmin_4 | 22.418968  |  1102273035  |  fmax_4 | 22.418968  |  1102273035  |
|  fmin_5 |  5.827968  |  1085963958  |  fmax_5 | 18.206978  |  1100064740  | 
|  fmin_6 | -0.257730  |  3196319023  |  fmax_6 | 27.390030  |  1104879304  |


expected value setp(4):
|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  hmax_1 |  5.827968  |  1085963958  |  hmax_2 | 27.390030  |  1104879304  | 
|  hmin_1 |  18.206978 |  1100064740  |  hmin_2 |  7.877372  |  1090261870  |
|  hmax_3 |  22.418968 |  1102273035  |  hmax_4 |      0     |      0       | 
|  hmin_3 |  18.206978 |  1100064740  |  hmin_4 1111238470
expected value setp(5):
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  |  27.390030 |  1104879304  |
|  c0max  |  7.877372  |  1090261870  |
|  c1min  |  22.418968 |  1102273035  | 
|  c1max  |  7.877372  |  1090261870  |

expected value setp(6):
|  name   |   Bool    |
|  :----: |   :----:  |
|  cmp0   |     0     | 
|  cmp1   |     0     |


<!-- n0xy.x=25.263599	 n0xy.y=31.924400	 n0xy.z=-0.748502	 n0xy.w=10.575800	 
idirx=2.805120	 idiry=1.093137	 idirz=5.266240	 
oodx=67.132812	 oody=-6.646183	 oodz=0.521043	 
nz.x=5.300000	 nz.y=5.550000	 nz.z=0.050000	 nz.w=5.300000
c0lox=3.734619	 c0hix=22.418968	 c0loy=5.827968
c0hiy=18.206978	 c0hloz=27.390030	 c0hiz=28.706591
traverseChild0=0
child_mask[1]  2 
c0min=27.390030    c0max=7.877372
tmin=0.000000	 hitT=7.877372
n1xy.x=31.924400	 n1xy.y=31.924400	 n1xy.z=-0.748502	 n1xy.w=10.575800	 
idirx=2.805120	 idiry=1.093137	 idirz=5.266240	 
oodx=67.132812	 oody=-6.646183	 oodz=0.521043	 
nz.x=5.300000	 nz.y=5.550000	 nz.z=0.050000	 nz.w=5.300000
c1lox=22.418968	 c1hix=22.418968	 c1loy=5.827968
c1hiy=18.206978	 c1loz=-0.257730	 c1hiz=27.390030
traverseChild1=0
child_mask[1]  2 
c1min=22.418968    c1max=7.877372
tmin=0.000000	 hitT=7.877372 -->
------------------------------------------------------------------------------
rayid = 1000  first traverse

n0xy.x=-37.973598	 n0xy.y=46.028000	 n0xy.z=-36.936298	 n0xy.w=36.950699	 
idirx=2.805120	 idiry=1.093137	 idirz=5.266240	 
oodx=67.132812	 oody=-6.646183	 oodz=0.521043	 
nz.x=-0.317854	 nz.y=14.600000	 nz.z=-0.463129	 nz.w=53.519600
c0lox=-173.653320	 c0hix=61.981262	 c0loy=-33.730244
c0hiy=47.038353	 c0loz=-2.194938	 c0hiz=76.366066
traverseChild0=1
child_mask[1]  1 
c0min=0.000000    c0max=47.038353
tmin=0.000000	 hitT=340282346638528859811704183484516925440.000000
 
n1xy.x=-2.644070	 n1xy.y=46.774399	 n1xy.z=-28.543600	 n1xy.w=11.543200	 
c1lox=-74.549744	 c1hix=64.074997	 c1loy=-24.555876
c1hiy=19.264479	 c1loz=-2.959991	 c1hiz=281.326019
traverseChild1=1
child_mask[1]  1 
c1min=0.000000    c1max=19.264479
tmin=0.000000	 hitT=340282346638528859811704183484516925440.000000

<second>
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  |      0     |  0000000000  |
|  c0max  |  47.038353 |  1111238470  |
|  c1min  |      0     |  0000000000  | 
|  c1max  |  19.264479 |  1100619175  |



------------------------------------------------------------------------------
rayid = 1000  倒数第二次

n0xy.x=25.191500	 n0xy.y=33.210701	 n0xy.z=-0.748502	 n0xy.w=10.575800	 
idirx=2.805120	 idiry=1.093137	 idirz=5.266240	 
oodx=67.132812	 oody=-6.646183	 oodz=0.521043	 
nz.x=0.050000	 nz.y=0.191514	 nz.z=0.050000	 nz.w=5.550000
c0lox=3.532372	 c0hix=26.027199	 c0loy=5.827968
c0hiy=18.206978	 c0loz=-0.257730	 c0hiz=0.487516
traverseChild0=0
child_mask[1]  1 
c0min=5.827968    c0max=0.487516
tmin=0.000000	 hitT=7.877372
 
n1xy.x=25.263599	 n1xy.y=31.924400	 n1xy.z=-0.748502	 n1xy.w=10.575800	 
idirx=2.805120	 idiry=1.093137	 idirz=5.266240	 
oodx=67.132812	 oody=-6.646183	 oodz=0.521043	 
nz.x=0.050000	 nz.y=0.191514	 nz.z=0.050000	 nz.w=5.550000
c1lox=3.734619	 c1hix=22.418968	 c1loy=5.827968
c1hiy=18.206978	 c1loz=-0.257730	 c1hiz=28.706591
traverseChild1=1
child_mask[1]  1 
c1min=5.827968    c1max=7.877372
tmin=0.000000	 hitT=7.877372

<third>
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  |  5.827968  |  1085963958  |
|  c0max  |  0.487516  |  1056545714  |
|  c1min  |  5.827968  |  1085963958  | 
|  c1max  |  7.877372  |  1090261870  |

------------------------------------------------------------------------------
rayid = 1000  倒数第三次

n0xy.x=27.024401	 n0xy.y=33.230499	 n0xy.z=-1.393550	 n0xy.w=3.953450	 
idirx=2.805120	 idiry=1.093137	 idirz=5.266240	 
oodx=67.132812	 oody=-6.646183	 oodz=0.521043	 
nz.x=0.094171	 nz.y=5.550000	 nz.z=0.050000	 nz.w=5.550000
c0lox=8.673882	 c0hix=26.082733	 c0loy=5.122842
c0hiy=10.967844	 c0loz=-0.025114	 c0hiz=28.706591
traverseChild0=0
child_mask[1]  1 
c0min=8.673882    c0max=7.877372
tmin=0.000000	 hitT=7.877372
 
n1xy.x=25.191500	 n1xy.y=33.210701	 n1xy.z=-0.748502	 n1xy.w=10.575800	 
idirx=2.805120	 idiry=1.093137	 idirz=5.266240	 
oodx=67.132812	 oody=-6.646183	 oodz=0.521043	 
nz.x=0.094171	 nz.y=5.550000	 nz.z=0.050000	 nz.w=5.550000
c1lox=3.532372	 c1hix=26.027199	 c1loy=5.827968
c1hiy=18.206978	 c1loz=-0.257730	 c1hiz=28.706591
traverseChild1=1
child_mask[1]  1 
c1min=5.827968    c1max=7.877372
tmin=0.000000	 hitT=7.877372

<forth>
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  |  8.673882  |  1091225656  |
|  c0max  |  7.877372  |  1090261870  |
|  c1min  |  5.827968  |  1085963958  | 
|  c1max  |  7.877372  |  1090261870  |

<forth>
|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  c0lox  |  8.673882  |  1091225656  |  c1lox  | 3.532372   |  1080169056  | 
|  c0hix  | 26.082733  |  1104193900  |  c1hix  | 26.027199  |  1104164788  |
|  c0loy  |  5.122842  |  1084485202  |  c1loy  | 5.827968   |  1085963958  | 
|  c0hiy  | 18.206978  |  1100064740  |  c1hiy  | 18.206978  |  1100064740  |
|  c0loz  | -0.025114  |  3167600640  |  c1loz  | -0.257730  |  3196319059  | 
|  c0hiz  | 28.706591  |  1105569558  |  c1hiz  | 28.706591  |  1105569558  |
c0Min_out-----------------------------------------------
随机选择一条光线

n0xy.x=17.484699	 n0xy.y=25.011200	 n0xy.z=-2.565740	 n0xy.w=10.575800	 
idirx=-1.479364	 idiry=1.375475	 idirz=-8.301799	 
oodx=-37.505116	 oody=-7.082810	 oodz=-11.379354	 
nz.x=-0.121073	 nz.y=6.124415	 nz.z=5.497379	 nz.w=11.726700
c0lox=11.638878	 c0hix=0.504444	 c0loy=3.553699
c0hiy=21.629560	 c0loz=12.384474	 c0hiz=-39.464314
traverseChild0=0
child_mask[1]  1 
c0min=3.553699    c0max=2.363940
tmin=0.000000	 hitT=2.363940
 
n1xy.x=18.915899	 n1xy.y=24.656401	 n1xy.z=-1.831930	 n1xy.w=4.029865	 
idirx=-1.479364	 idiry=1.375475	 idirz=-8.301799	 
oodx=-37.505116	 oody=-7.082810	 oodz=-11.379354	 
nz.x=-0.121073	 nz.y=6.124415	 nz.z=5.497379	 nz.w=11.726700
c1lox=9.521612	 c1hix=1.029320	 c1loy=4.563036
c1hiy=12.625790	 c1loz=-34.258781	 c1hiz=-85.973343
traverseChild1=0
child_mask[1]  1 
c1min=4.563036    c1max=-34.258781
tmin=0.000000	 hitT=2.363940


|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  n0xy.x | 17.484699  |  1099686057  |  n1xy.x | 18.915899  |  1100436418  |   nz.x  |  0.121073  |  1039660316  |
|  n0xy.y | 25.011200  |  1103632112  |  n1xy.y | 24.656401  |  1103446095  |   nz.y  |  6.124415  |  1086585653  |
|  n0xy.z | -2.565740  |  3223598357  |  n1xy.z | -1.831930  |  3219815598  |   nz.z  |  5.497379  |  1085270663  |
|  n0xy.w | 10.575800  |  1093219962  |  n1xy.w |  4.029865  |  1082193063  |   nz.w  |  11.72670  |  1094426768  |

|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  idirx  | -1.479364  |  3216858060  |  oodx   | -37.505116 |  3256223037  |
|  idiry  |  1.375475  |  1068502928  |  oody   |  -7.082810 |  3236079201  |
|  idirz  | -8.301799  |  3238319147  |  oodz   | -11.379354 |  3241546197  |

<fifth>
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  |  3.553699  |  1080258509  |
|  c0max  |  2.363940  |  1075268298  |
|  c1min  |  4.563036  |  1083311204  | 
|  c1max  | -34.258781 |  3255372029  |

<fifth>
<!-- |  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  c0lox  |  8.673882  |  1091225656  |  c1lox  | 3.532372   |  1080169056  | 
|  c0hix  | 26.082733  |  1104193900  |  c1hix  | 26.027199  |  1104164788  |
|  c0loy  |  5.122842  |  1084485202  |  c1loy  | 5.827968   |  1085963958  | 
|  c0hiy  | 18.206978  |  1100064740  |  c1hiy  | 18.206978  |  1100064740  |
|  c0loz  | -0.025114  |  3167600640  |  c1loz  | -0.257730  |  3196319059  | 
|  c0hiz  | 28.706591  |  1105569558  |  c1hiz  | 28.706591  |  1105569558  | -->

|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |
|  tmin   |     0      |       0      |
|  hitT   |  2.363940  |  1075268298  |

------------------------------------------------------------------------------
随机选择第二条光线

n0xy.x=27.339701	 n0xy.y=30.696400	 n0xy.z=-3.248550	 n0xy.w=-3.248500	 
idirx=1.814284	 idiry=-20.372530	 idirz=1.200566	 
oodx=46.774628	 oody=40.457634	 oodz=-0.120057	 
nz.x=0.000000	 nz.y=2.350190	 nz.z=0.000000	 nz.w=0.000000
c0lox=2.827366	 c0hix=8.917374	 c0loy=25.723549
c0hiy=25.722534	 c0loz=0.120057	 c0hiz=2.941615
traverseChild0=0
child_mask[1]  1 
c0min=25.722534    c0max=2.941615
tmin=0.000000	 hitT=6.783199
 
n1xy.x=28.410900	 n1xy.y=30.553301	 n1xy.z=-3.248500	 n1xy.w=-1.048500	 
idirx=1.814284	 idiry=-20.372530	 idirz=1.200566	 
oodx=46.774628	 oody=40.457634	 oodz=-0.120057	 
nz.x=0.000000	 nz.y=2.350190	 nz.z=0.000000	 nz.w=0.000000
c1lox=4.770828	 c1hix=8.657749	 c1loy=25.722534
c1hiy=-19.097038	 c1loz=0.120057	 c1hiz=0.120057
traverseChild1=0
child_mask[1]  1 
c1min=4.770828    c1max=0.120057
tmin=0.000000	 hitT=6.783199


|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  n0xy.x | 27.339701  |  1104852917  |  n1xy.x | 28.410900  |  1105414533  |   nz.x  |      0     |  0000000000  |
|  n0xy.y | 30.696400  |  1106612794  |  n1xy.y | 30.553301  |  1106537769  |   nz.y  |  2.350190  |  1075210627  |
|  n0xy.z | -3.248550  |  3226462270  |  n1xy.z | -3.248500  |  3226462060  |   nz.z  |      0     |  0000000000  |
|  n0xy.w | -3.248500  |  3226462060  |  n1xy.w | -1.048500  |  3213243711  |   nz.w  |      0     |  0000000000  |

|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  idirx  |  1.814284  |  1072183925  |  oodx   |  46.774628 |  1111169336  |
|  idiry  | -20.372530 |  3248683761  |  oody   |  40.457634 |  1109513374  |
|  idirz  |  1.200566  |  1067035685  |  oodz   |  -0.120057 |  3187007600  |

<sixth>
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  | 25.722534  |  1104005055  |
|  c0max  |  2.941615  |  1077691243  |
|  c1min  |  4.770828  |  1083746975  | 
|  c1max  |  0.120057  |  1039523952  |

<sixth>
<!-- |  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  c0lox  |  8.673882  |  1091225656  |  c1lox  | 3.532372   |  1080169056  | 
|  c0hix  | 26.082733  |  1104193900  |  c1hix  | 26.027199  |  1104164788  |
|  c0loy  |  5.122842  |  1084485202  |  c1loy  | 5.827968   |  1085963958  | 
|  c0hiy  | 18.206978  |  1100064740  |  c1hiy  | 18.206978  |  1100064740  |
|  c0loz  | -0.025114  |  3167600640  |  c1loz  | -0.257730  |  3196319059  | 
|  c0hiz  | 28.706591  |  1105569558  |  c1hiz  | 28.706591  |  1105569558  | -->

|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |
|  tmin   |     0      |       0      |
|  hitT   |  6.783199  |  1087967223  |

------------------------------------------------------------------------------
随机选择第三条光线

n0xy.x=-14.865300	 n0xy.y=-10.567100	 n0xy.z=-5.525590	 n0xy.w=2.246860	 
idirx=0.016498	 idiry=0.021180	 idirz=-0.107263	 
oodx=-3.137014	 oody=-2.932590	 oodz=-5.458444	 
nz.x=25.200199	 nz.y=26.694700	 nz.z=23.575100	 nz.w=26.284000
c0lox=2.891773	 c0hix=2.962683	 c0loy=2.815558
c0hiy=2.980178	 c0loz=2.755406	 c0hiz=2.595102
traverseChild0=0
child_mask[1]  1 
c0min=2.891773    c0max=2.755406
tmin=0.000000	 hitT=2.895163
 
n1xy.x=-11.686300	 n1xy.y=-6.295200	 n1xy.z=-5.186480	 n1xy.w=2.796620	 
idirx=0.016498	 idiry=0.021180	 idirz=-0.107263	 
oodx=-3.137014	 oody=-2.932590	 oodz=-5.458444	 
nz.x=25.200199	 nz.y=26.694700	 nz.z=23.575100	 nz.w=26.284000
c1lox=2.944219	 c1hix=3.033159	 c1loy=2.822741
c1hiy=2.991822	 c1loz=2.929718	 c1hiz=2.639154
traverseChild1=0
child_mask[1]  1 
c1min=2.944219    c1max=2.895163
tmin=0.000000	 hitT=2.895163


|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  n0xy.x |-14.865300  |  3245201476  |  n1xy.x |-11.686300  |  3241868053  |   nz.x  | 25.200199  |  1103731201  |
|  n0xy.y |-10.567100  |  3240694487  |  n1xy.y | -6.295200  |  3234427463  |   nz.y  | 26.694700  |  1104514750  |
|  n0xy.z | -5.525590  |  3232813474  |  n1xy.z | -5.186480  |  3232102308  |   nz.z  | 23.575100  |  1102879182  |
|  n0xy.w |  2.246860  |  1074777229  |  n1xy.w |  2.796620  |  1077083090  |   nz.w  | 26.284000  |  1104299425  |

|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  idirx  |  0.016498  |  1015490256  |  oodx   |  -3.137014 |  3225994454  |
|  idiry  |  0.021180  |  1018003872  |  oody   |  -2.932590 |  3225137037  |
|  idirz  | -0.107263  |  3185290420  |  oodz   |  -5.458444 |  3232672658  |

<seventh>
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  |  2.891773  |  1077482191  |
|  c0max  |  2.755406  |  1076910226  |
|  c1min  |  2.944219  |  1077702165  | 
|  c1max  |  2.895163  |  1077496409  |

|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |
|  tmin   |     0      |       0      |
|  hitT   |  2.895163  |  1077496409  |

------------------------------------------------------------------------------
随机选择第四条光线

n0xy.x=-15.160200	 n0xy.y=-10.937400	 n0xy.z=-4.825230	 n0xy.w=-1.851500	 
idirx=0.016498	 idiry=0.021180	 idirz=-0.107263	 
oodx=-3.137014	 oody=-2.932590	 oodz=-5.458444	 
nz.x=20.410601	 nz.y=21.485300	 nz.z=25.236401	 nz.w=25.788200
c0lox=2.886908	 c0hix=2.956574	 c0loy=2.830392
c0hiy=2.893375	 c0loz=3.269150	 c0hiz=3.153875
traverseChild0=0
child_mask[1]  1 
c0min=3.153875    c0max=2.893375
tmin=0.000000	 hitT=2.895163
 
n1xy.x=-17.540501	 n1xy.y=-13.222400	 n1xy.z=-4.774680	 n1xy.w=-2.257070	 
idirx=0.016498	 idiry=0.021180	 idirz=-0.107263	 
oodx=-3.137014	 oody=-2.932590	 oodz=-5.458444	 
nz.x=20.410601	 nz.y=21.485300	 nz.z=25.236401	 nz.w=25.788200
c1lox=2.847639	 c1hix=2.918877	 c1loy=2.831463
c1hiy=2.884785	 c1loz=2.751523	 c1hiz=2.692335
traverseChild1=0
child_mask[1]  1 
c1min=2.847639    c1max=2.751523
tmin=0.000000	 hitT=2.895163



|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  n0xy.x |-15.160200  |  3245510701  |  n1xy.x |-17.540501  |  3247198962  |   nz.x  | 20.410601  |  1101220073  |
|  n0xy.y |-10.937400  |  3241082775  |  n1xy.y |-13.222400  |  3243478771  |   nz.y  | 21.485300  |  1101783524  |
|  n0xy.z | -4.825230  |  3231344712  |  n1xy.z | -4.774680  |  3231238701  |   nz.z  | 25.236401  |  1103750182  |
|  n0xy.w | -1.851500  |  3219979763  |  n1xy.w | -2.257070  |  3222303701  |   nz.w  | 25.788200  |  1104039483  |

|  name   |    data    |  IEEE format |  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |  :----: |   :----:   |     :----:   |
|  idirx  |  0.016498  |  1015490256  |  oodx   |  -3.137014 |  3225994454  |
|  idiry  |  0.021180  |  1018003872  |  oody   |  -2.932590 |  3225137037  |
|  idirz  | -0.107263  |  3185290420  |  oodz   |  -5.458444 |  3232672658  |

<eighth>
|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   | 
|  c0min  |  3.153875  |  1078581526  |
|  c0max  |  2.893375  |  1077488910  |
|  c1min  |  2.847639  |  1077297079  | 
|  c1max  |  2.751523  |  1076893939  |

|  name   |    data    |  IEEE format |
|  :----: |   :----:   |     :----:   |
|  tmin   |     0      |       0      |
|  hitT   |  2.895163  |  1077496409  |