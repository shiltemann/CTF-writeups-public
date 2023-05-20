---
layout: writeup
title: 'Cryptography 150: hertz'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

Here's another simple cipher for you where we made a bunch of
substitutions. Can you decrypt it? Connect with `nc
2018shell1.picoctf.com 43324`.

## Solution

when we connect to the service, we are greeted with a different text
every time:

    -------------------------------------------------------------------------------
    hqbjcizw xoco dw sqgc ayij - wguwzdzgzdqb_hdtxocw_ico_wqykiuyo_mpozzaowkb
    -------------------------------------------------------------------------------
    "poyy, tcdbho, wq jobqi ibl yghhi ico bqp fgwz aimdys owzizow qa zxo
    ugqbiticzow. ugz d picb sqg, da sqg lqbz zoyy mo zxiz zxdw moibw pic,
    da sqg wzdyy zcs zq loaobl zxo dbaimdow ibl xqccqcw toctozcizol us zxiz
    ibzdhxcdwz-d coiyys uoydoko xo dw ibzdhxcdwz-d pdyy xiko bqzxdbj
    mqco zq lq pdzx sqg ibl sqg ico bq yqbjoc ms acdobl, bq yqbjoc ms
    'aidzxagy wyiko,' iw sqg hiyy sqgcwoya! ugz xqp lq sqg lq? d woo d
    xiko acdjxzobol sqg-wdz lqpb ibl zoyy mo iyy zxo bopw."
    
    dz piw db fgys, 1805, ibl zxo wtoieoc piw zxo poyy-ebqpb ibbi tikyqkbi
    whxococ, midl qa xqbqc ibl aikqcdzo qa zxo omtcoww micsi aolqcqkbi.
    pdzx zxowo pqclw wxo jcoozol tcdbho kiwdyd egcijdb, i mib qa xdjx
    cibe ibl dmtqczibho, pxq piw zxo adcwz zq iccdko iz xoc cohotzdqb. ibbi
    tkyqkbi xil xil i hqgjx aqc wqmo lisw. wxo piw, iw wxo widl, wgaaocdbj
    acqm yi jcdtto; jcdtto uodbj zxob i bop pqcl db wz. tozocwugcj, gwol
    qbys us zxo oydzo.
    
    iyy xoc dbkdzizdqbw pdzxqgz orhotzdqb, pcdzzob db acobhx, ibl loydkocol
    us i whicyoz-ydkocdol aqqzmib zxiz mqcbdbj, cib iw aqyyqpw:
    
    "da sqg xiko bqzxdbj uozzoc zq lq, hqgbz (qc tcdbho), ibl da zxo
    tcqwtohz qa wtobldbj ib okobdbj pdzx i tqqc dbkiydl dw bqz zqq zoccduyo,
    d wxiyy uo kocs hxicmol zq woo sqg zqbdjxz uozpoob 7 ibl 10 ibbozzo
    whxococ."
    
    "xoikobw! pxiz i kdcgyobz izzihe!" cotydol zxo tcdbho, bqz db zxo
    yoiwz ldwhqbhoczol us zxdw cohotzdqb. xo xil fgwz obzocol, poicdbj ib
    omucqdlocol hqgcz gbdaqcm, eboo ucoohxow, ibl wxqow, ibl xil wzicw qb
    xdw ucoiwz ibl i wocobo ortcowwdqb qb xdw ayiz aiho. xo wtqeo db zxiz
    coadbol acobhx db pxdhx qgc jciblaizxocw bqz qbys wtqeo ugz zxqgjxz, ibl
    pdzx zxo jobzyo, tizcqbdndbj dbzqbizdqb bizgciy zq i mib qa dmtqczibho
    pxq xil jcqpb qyl db wqhdozs ibl iz hqgcz. xo pobz gt zq ibbi tkyqkbi,
    edwwol xoc xibl, tcowobzdbj zq xoc xdw uiyl, whobzol, ibl wxdbdbj xoil,
    ibl hqmtyihobzys woizol xdmwoya qb zxo wqai.
    
    "adcwz qa iyy, loic acdobl, zoyy mo xqp sqg ico. woz sqgc acdobl'w
    mdbl iz cowz," widl xo pdzxqgz iyzocdbj xdw zqbo, uoboizx zxo
    tqydzoboww ibl iaaohzol wsmtizxs qa pxdhx dbldaaocobho ibl okob dcqbs
    hqgyl uo ldwhocbol.

another example:

    -------------------------------------------------------------------------------
    ntwjhzpu xchc su etmh qrzj - umbupspmpstw_nsdxchu_zhc_utrkzbrc_aocppqcukw
    -------------------------------------------------------------------------------
    nzrr ac suxazcr. utac eczhu zjt-wckch aswy xto rtwj dhcnsucre-xzkswj rspprc th wt atwce sw ae dmhuc, zwy wtpxswj dzhpsnmrzh pt swpchcup ac tw uxthc, s pxtmjxp s otmry uzsr zbtmp z rspprc zwy ucc pxc ozpche dzhp
    tq pxc othry. sp su z oze s xzkc tq yhskswj tqq pxc udrccw zwy hcjmrzpswj pxc nshnmrzpstw. oxcwckch s qswy aeucrq jhtoswj jhsa zbtmp pxc atmpx; oxcwckch sp su z yzad, yhsffre wtkcabch sw ae utmr; oxcwckch s qswy aeucrq swktrmwpzhsre dzmuswj bcqthc ntqqsw ozhcxtmucu, zwy bhswjswj md pxc hczh tq ckche qmwchzr s accp; zwy cudcnszrre oxcwckch ae xedtu jcp umnx zw mddch xzwy tq ac, pxzp sp hcvmshcu z uphtwj athzr dhswnsdrc
    pt dhckcwp ac qhta ycrsbchzpcre upcddswj swpt pxc uphccp, zwy acpxtysnzrre gwtngswj dctdrc'u xzpu tqq-pxcw, s znntmwp sp xsjx psac pt jcp pt ucz zu uttw zu s nzw. pxsu su ae umbupspmpc qth dsuptr zwy bzrr. ospx
    z dxsrtutdxsnzr qrtmhsux nzpt pxhtou xsaucrq mdtw xsu uothy; s vmscpre pzgc pt pxc uxsd. pxchc su wtpxswj umhdhsuswj sw pxsu. sq pxce bmp gwco sp, zratup zrr acw sw pxcsh ycjhcc, utac psac th tpxch, nxchsux kche wczhre pxc uzac qccrswju ptozhyu pxc tnczw ospx ac.
    
    pxchc wto su etmh swumrzh nspe tq pxc azwxzpptcu, bcrpcy htmwy be oxzhkcu zu swyszw surcu be nthzr hccqu-ntaachnc umhhtmwyu sp ospx xch umhq. hsjxp zwy rcqp, pxc uphccpu pzgc etm ozpchozhy. spu ciphcac ytowptow
    su pxc bzppche, oxchc pxzp wtbrc atrc su ozuxcy be ozkcu, zwy nttrcy be bhccfcu, oxsnx z qco xtmhu dhckstmu ochc tmp tq usjxp tq rzwy. rttg zp pxc nhtoyu tq ozpch-jzfchu pxchc.
    
    nshnmazabmrzpc pxc nspe tq z yhczae uzbbzpx zqpchwttw. jt qhta nthrczhu xttg pt ntcwpscu ursd, zwy qhta pxcwnc, be oxspcxzrr, wthpxozhy. oxzp yt etm ucc?-dtupcy rsgc usrcwp ucwpswcru zrr zhtmwy pxc ptow, upzwy pxtmuzwyu mdtw pxtmuzwyu tq athpzr acw qsicy sw tnczw hckchscu. utac rczwswj zjzswup pxc udsrcu; utac uczpcy mdtw pxc dsch-xczyu; utac rttgswj tkch pxc bmrozhgu tq uxsdu qhta nxswz; utac xsjx zrtqp sw pxc hsjjswj, zu sq uphskswj pt jcp z upsrr bcppch uczozhy dccd. bmp pxcuc zhc zrr rzwyuacw; tq occg yzeu dcwp md sw rzpx zwy drzupch-pscy pt ntmwpchu, wzsrcy pt bcwnxcu, nrswnxcy pt ycugu. xto pxcw su pxsu? zhc pxc jhccw qscryu jtwc? oxzp yt pxce xchc?
    
    bmp rttg! xchc ntac athc nhtoyu, dznswj uphzsjxp qth pxc ozpch, zwy uccaswjre btmwy qth z yskc. uphzwjc! wtpxswj osrr ntwpcwp pxca bmp pxc ciphcacup rsasp tq pxc rzwy; rtspchswj mwych pxc uxzye rcc tq etwych ozhcxtmucu osrr wtp umqqsnc. wt. pxce amup jcp lmup zu wsjx pxc ozpch zu pxce dtuusbre nzw ospxtmp qzrrswj sw. zwy pxchc pxce upzwy-asrcu tq pxca-rczjmcu. swrzwychu zrr, pxce ntac qhta rzwcu zwy zrrceu, uphccpu zwy zkcwmcu-wthpx, czup, utmpx, zwy ocup. ecp xchc pxce zrr mwspc. pcrr ac, ytcu pxc azjwcpsn kshpmc tq pxc wccyrcu tq pxc ntadzuucu tq zrr pxtuc uxsdu zpphznp pxca pxspxch?

maybe a substitution cipher? we can solve these with
https://quipqiup.com/ the flag is in the header of each of the outputs

