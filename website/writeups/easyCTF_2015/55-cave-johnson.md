---
layout: writeup
title: Cave Johnson
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{this_is_aperture_and_we_talk_too_much}
---
**Challenge**  
Welcome to Small Hole Sciences.

(Since all of their budget was spent on moon rocks, they were only able
to afford an old-fashioned Vigenere Cipher.)

    pexxcrbqcurmvwqxqfarvcklmabqfkieggtmmnoqqfacwhvviipqrvbekqyqmnfosehrtsysxekaiipekswhmtqzhzcakbklmrqxooogkceziuxfvrgogseexlqmgrbgqbvcwudedvqvoselisnqsedeqqvwaepmbukrcfvrwwdqoumgqpvkqnftsksfvdisxednswcegkvwbadfvfkrxsiomebubdwafhyebaxfvfitjtlrinpxsrfakbxezeftsgfvooicoomxgftnnzvrziotavbgesexmrmohzjvvwvwaeduclgvpxlvqeeyopcpeiijwrkaiicjpgrjmtkmbuhuggrjmtkatfhugfjttemesisstozrnrayhlfakbxmvttqtcotyfrtxepubkvruhrrladptffzchklqs_ue_ogseviii_inp_is_koym_hfs_uuot

## Solution

We use an online solver [http://www.guballa.de/vigenere-solver][1]

We find the cleartext

    helloandagainwelcometotheaperturesciencecomputeraidedenrichmentcenterehopeyourbriefdetentionintherelaxationvaulthasbeenapleasantoneyourspecimenhasbeenprocessedandwearenowreadytobeginthetestproperbeforewestarthoweverkeepinmindthatalthoughfunandlearningaretheprimarygoalsofallenrichmentcenteractivitiesseriousinjuriesmayoccurforyourownsafetyandthesafetyofotherspleaserefrainfromturningintheflagwrappedinthestandardformatthis_is_aperture_and_we_talk_too_much

which with some whitespace inserted reads:

    hello and again welcome to the aperture science computer aided enrichment center e hope your brief detention in the relaxation vault has been a pleasant one your specimen has been processed and we are now ready to begin the test proper before we start however keep in mind that although fun and learning are the primary goals of all enrichment center activities serious injuries may occur for your own safety and the safety of others please refrain from turning in the flag wrapped in the standard format this_is_aperture_and_we_talk_too_much

with the key

    iammoroncore



[1]: http://www.guballa.de/vigenere-solver
