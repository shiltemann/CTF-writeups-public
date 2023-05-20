---
layout: writeup
title: 'Cryptography 200: blaise’s cipher'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{v1gn3r3_c1ph3rs_ar3n7_bad_cdf08bf0}
---
## Challenge

My buddy Blaise told me he learned about this cool cipher invented by a
guy also named Blaise! Can you figure out what it says?

Connect with `nc 2018shell1.picoctf.com 46966`

## Solution

We connect and are greeted by this message:

    Yse lncsz bplr-izcarpnzjo dkxnroueius zf g uzlefwpnfmeznn cousex bls ltcmaqltki my Rjzn Hfetoxea Gqmexyt axtfnj 1467 fyd axpd g rptgq nivmpr jndc zt dwoynh hjewkjy cousex fwpnfmezx. Llhjcto'x dyyypm uswy ybttimpd gqahggpty fqtkw debjcar bzrjx, lnj xhizhsey bprk nydohltki my cwttosr tnj wezypr uk ehk hzrxjdpusoitl llvmlbky tn zmp cousexypxz. Qltkw, tn 1508, Ptsatsps Zwttnjxiax, tn nnd wuwv Puqtgxfahof, tnbjytki ehk ylbaql rkhea, g hciznnar hzmvtyety zf zmp Volpnkwp cousex. Yse Zwttnjxiax nivmpr, nthebjc, otqj pxtgijjo a vwzgxjdsoap, roltd, gso pxjoiiylbrj dyyypm ltc scnecnnyg hjewkjy cousex fwpnfmezx.
    
    Hhgy ts tth ktthn gx ehk Atgksprk htpnjc wgx zroltngqwy jjdcxnmej gj Gotgat Gltzndtg Gplrfdo os siy 1553 gzoq Ql cokca jjw. Sol. Riualn Hfetoxea Hjwlgxz. Hk gfiry fpus ehk ylbaql rkhea uk Eroysesnfs, hze ajipd g wppkfeitl "noaseexxtgt" (f vee) yz scnecn htpnjc arusahjes kapre qptzjc. Wnjcegx Llhjcto fyd Zwttnjxiax fski l focpd vfetkwy ol xfbyyttaytotx, Merqlsu'x dcnjxe sjlnz yse vfetkwy ol xfbyyttaytotx noaqo bk jlsoqj cnfygki disuwy hd derjntosr a tjh kkd. Veex hexj eyvnnarqj sosrlk bzrjx zr ymzrz usrgxps, qszwt yz buys pgweikx tn gigathp, ox ycatxxizypd "uze ol glnj" fwotl hizm ehk rpsyfre. Hjwlgxz's sjehui ehax cewztrki dtxtyg yjnuxney ltc otqj tnj vee. Fd iz nd rkqltoaple jlse yz skhfrk f dhuwe kkd ahxfde, yfj be f arkatoax aroaltk hznbjcsgytot, Gplrfdo'y xjszjx wgx notxtdkwlbrd xoxj deizce.
    
    Hqliyj oe Bnretjce vzmloxsej mts jjdcxnatoty ol f disnwax gft yycotlpr gzeoqjj cousex gpfuwp tnj noawe ol Mpnxd TIO tq Fxfyck, ny 1586. Lgypr, os ehk 19ys ckseuxd, ehk nyvkseius zf Hjwlgxz's inahkw hay rtsgyerogftki eo Bnretjce. Jfgij Plht ny hox moup Ehk Hzdkgcegppry qlmkseej yse sndazycihzeius my yfjitl ehgy siyyzre mld "olyoxjo tnnd isuzrzfyt itytxnmuznzn gso itxeegi yasjo a xjrrkxdibj lnj jwesjytgwj cousex kzr nnx [Volpnkwp] tntfgn mp hgi yozmtnm yz du bttn ne". pohzCZK{g1gt3w3_n1pn3wd_ax3s7_maj_hof08hk0}
    
    Ehk Atgksprk htpnjc ggnyej f cevzeaznzn ltc bknyg kcnevytotfwle xerusr. Nuypd gzehuw lnj rltnjxaznnigs Nhgwwey Qftcnogk Izdmxzn (Rjhiy Hlrxtwl) ifwlki ehk Atgksprk htpnjc utgcegplbrj tn nnd 1868 pojne "Zmp Arusahje Cousex" ny a imtljwpn'y rlggetnk. Ny 1917, Sinpnznqii Fxexnnat ipsiwtbki ehk Atgksprk htpnjc ay "nxpuxdihqp ol ycatxwaznzn". Zmts xjauzfeius hay szt jjdexapd. Imlrrjd Bggmamj ts qszwt yz hgap bxtvet f gaxnlnz tq tnj nivmpr gx paxqj ay 1854; mzwkapr, nj oijs'e pagwiym siy bzrq. Plsoxvi kseixjwy hwzkk yse inahkw lnj ufbrndhki ehk ypcnstqaj tn zmp 19tn hpnzzcy. Kapn hjqoxj ehox, ehuzrh, ytxe yptlrjo cxdatgsllexes itflj tncgxtotfwle gcegp ehk htpnjc it yse 16zm netyfre.
    
    Hcyvyzgxfahoh dloip raqp uyjo ay f narhflgytot ftd hd ehk Xhiyx Lrsd mezbpet 1914 fyd 1940.
    Zmp Volpnkwp cousex nd soralk jyoals tu gp a lnplj htpnjc il ne iy zdej ny cusuutheius hizm nivmpr jndky. Yse Ityfkiprgyp Szfeey tq Asjciif, qox jiasuwe, axpd g gcayx nivmpr jndk zt tmvqpmkse tnj Gimjyexj nivmpr jzcitl ehk Fxexnnat Htvoq Hax. Yse Ityfkiprghj's sjdsglps cjce lfc fxtx skhcez fyd zmp Utnzn xjrurfcle hcaippd zmpix rpsyfrey. Ysruzrhuze tnj hax, yse Ityfkiprgyp lkfoexxsiv ucisfcird cernpd auzn zmcek ppy vmcayjd, "Mgsnhkxeex Gwulk", "Nosuwezj Giiyzre" fyd, gx ehk blr ifxe zt l crtde, "Itxe Xjerogftoty".
    
    Goqmexy Gexslm zwtej yz rkulix yse hwzkks nivmpr (iwpaznyg zmp Vkwyas–Atgksprk htpnjc it 1918), gft, tt xazypr cmlt nj oij, yse inahkw hay xeirq gursprggwe zt nreueatfwyynd. Vkwyas'x hoxp, socjgex, jgetyfarqj lki eo zmp otj-eisj aaj, f ehktceznnarqj utgcegplbrj nivmpr.

It is encrypted and we see something that clearly will be the flag. With
*blaise* in the title, this has got to be Vigenere ciper.

We use [this site]() to find the key, which turns out to be `FLAG`. The
decrypted message is:

    The first well-documented description of a polyalphabetic cipher was formulated by Leon Battista Alberti around 1467 and used a metal cipher disc to switch between cipher alphabets. Alberti's system only switched alphabets after several words, and switches were indicated by writing the letter of the corresponding alphabet in the ciphertext. Later, in 1508, Johannes Trithemius, in his work Poligraphia, invented the tabula recta, a critical component of the Vigenere cipher. The Trithemius cipher, however, only provided a progressive, rigid, and predictable system for switching between cipher alphabets.
    
    What is now known as the Vigenere cipher was originally described by Giovan Battista Bellaso in his 1553 book La cifra del. Sig. Giovan Battista Bellaso. He built upon the tabula recta of Trithemius, but added a repeating "countersign" (a key) to switch cipher alphabets every letter. Whereas Alberti and Trithemius used a fixed pattern of substitutions, Bellaso's scheme meant the pattern of substitutions could be easily changed simply by selecting a new key. Keys were typically single words or short phrases, known to both parties in advance, or transmitted "out of band" along with the message. Bellaso's method thus required strong security for only the key. As it is relatively easy to secure a short key phrase, say by a previous private conversation, Bellaso's system was considerably more secure.
    
    Blaise de Vigenere published his description of a similar but stronger autokey cipher before the court of Henry III of France, in 1586. Later, in the 19th century, the invention of Bellaso's cipher was misattributed to Vigenere. David Kahn in his book The Codebreakers lamented the misattribution by saying that history had "ignored this important contribution and instead named a regressive and elementary cipher for him [Vigenere] though he had nothing to do with it". picoCTF{v1gn3r3_c1ph3rs_ar3n7_bad_cdf08bf0}
    
    The Vigenere cipher gained a reputation for being exceptionally strong. Noted author and mathematician Charles Lutwidge Dodgson (Lewis Carroll) called the Vigenere cipher unbreakable in his 1868 piece "The Alphabet Cipher" in a children's magazine. In 1917, Scientific American described the Vigenere cipher as "impossible of translation". This reputation was not deserved. Charles Babbage is known to have broken a variant of the cipher as early as 1854; however, he didn't publish his work. Kasiski entirely broke the cipher and published the technique in the 19th century. Even before this, though, some skilled cryptanalysts could occasionally break the cipher in the 16th century.
    
    Cryptographic slide rule used as a calculation aid by the Swiss Army between 1914 and 1940.
    The Vigenere cipher is simple enough to be a field cipher if it is used in conjunction with cipher disks. The Confederate States of America, for example, used a brass cipher disk to implement the Vigenere cipher during the American Civil War. The Confederacy's messages were far from secret and the Union regularly cracked their messages. Throughout the war, the Confederate leadership primarily relied upon three key phrases, "Manchester Bluff", "Complete Victory" and, as the war came to a close, "Come Retribution".
    
    Gilbert Vernam tried to repair the broken cipher (creating the Vernam–Vigenere cipher in 1918), but, no matter what he did, the cipher was still vulnerable to cryptanalysis. Vernam's work, however, eventually led to the one-time pad, a theoretically unbreakable cipher.

