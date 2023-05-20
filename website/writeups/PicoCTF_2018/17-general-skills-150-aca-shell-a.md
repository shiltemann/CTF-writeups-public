---
layout: writeup
title: 'General Skills 150: Aca-Shell-A'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
**Challenge**  
It's never a bad idea to brush up on those linux skills or even learn
some new ones  
before you set off on this adventure! Connect with `nc
2018shell1.picoctf.com 58422`.

## Solution

    $ nc 2018shell1.picoctf.com 58422
    Sweet! We have gotten access into the system but we aren't root.
    It's some sort of restricted shell! I can't see what you are typing
    but I can see your output. I'll be here to help you along.
    If you need help, type "echo 'Help Me!'" and I'll see what I can do
    There is not much time left!
    
    ~/$ ls
    blackmail
    executables
    passwords
    photos
    secret
    
    ~/$ cd secret
    Now we are cookin'! Take a look around there and tell me what you find!
    ~/secret$ ls
    intel_1
    intel_2
    intel_3
    intel_4
    intel_5
    profile_AipieG5Ua9aewei5ieSoh7aph
    profile_Xei2uu5suwangohceedaifohs
    profile_ahShaighaxahMooshuP1johgo
    profile_ahqueith5aekongieP4ahzugi
    profile_aik4hah9ilie9foru0Phoaph0
    profile_bah9Ech9oa4xaicohphahfaiG
    profile_ie7sheiP7su2At2ahw6iRikoe
    profile_of0Nee4laith8odaeLachoonu
    profile_poh9eij4Choophaweiwev6eev
    
    Sabatoge them! Get rid of all their intel files!
    
    ~/secret$ rm intel*
    
    Nice! Once they are all gone, I think I can drop you a file of an exploit!
    Just type "echo 'Drop it in!' " and we can give it a whirl!
    
    ~/secret$ echo "Drop it in!"
    Drop it in!
    I placed a file in the executables folder as it looks like the only place we can execute from!
    
    Run the script I wrotec to have a little more impact on the system!
    ~/secret$ cd ..
    ~/$ cd executables
    ~/executables$ ./dontLookHere
     0f5d ae3d 4183 37ae 3184 3f11 68e0 53af dbfd 7cd4 41fe 2b13 a3ef d071 54a9 f6b8 6535 5ebf a899 2136 4bbb 21cb 5088 031c f7e7
     818c ee94 3199 0366 b774 3452 13ea fa0a ae8f 9ec7 ad8b 34e5 7c6b 3ad2 e2a1 6aa6 4460 c455 2373 dcda 70b3 9c2b 0129 81e1 1c7e
     156e 74da 51f4 7a24 0ac8 6cc8 f3e3 b474 aa69 2e04 0373 1b16 ab8d ca36 4d48 9b36 cb31 bab3 b6f6 a59f 57ed 6d7a 0431 a520 c983
     343d c6e3 885f 9b32 d60d 4b67 cb7f 451d 230f 0e4d 5ed7 d58b cd59 cc8e d173 4adc 272a 3dc5 05f6 a076 9697 1949 d825 7821 b36e
     9fc4 58e8 a075 c5be 3517 dc17 fbd4 1fe2 5ece b992 b8e4 3618 bff6 3cb5 8fcd 3815 4364 055a 6143 be13 f771 e2aa 4766 e72c 19a3
     849c 26a4 c62d 8064 3691 9182 f357 8dd6 99b3 6d31 cda8 86cb 7eed 235c dd33 a4e6 8763 674d ea07 fdd9 b84a 32b5 7280 99f2 e920
     156b a1b3 d695 ffb2 25da e2bb dae5 6ec2 7990 a94e b6a4 c8d2 47ab 3c4b d945 834c cb19 d49d 4bf0 34a9 f79a 4a4f d87f 6c4d 6c2f
     79da a7d1 ce79 ec8d b33e 5a3d 5fcf 2ef4 3bbc eb2c 55cb 0278 6eb6 63af 063a 82cb ce55 7841 904b aabe 291c c69f 1311 e985 e8ee
     eee0 22bc 41e9 df14 d174 e9c2 7930 686e 6691 73c4 6ff9 2d37 821c 3d4c 7b6a 0321 a243 edc5 1c73 f4a0 4417 5d45 a59a 027f db2c
     88c2 2cca ca38 2361 9a06 5e73 b532 902d b485 f711 4856 c428 ff30 a40c 27ac 2849 ac67 b7a0 5cc2 3f7b a716 3cb1 eb28 a1ec 49c9
     ef0a e38f 7c8a a1b3 dddd 26cf 56bf b8f2 e0c9 d37d 6849 c6bf 2a6a d5b3 91ba 3b18 f522 7857 7393 5124 3c18 abd3 30ed 4766 9b82
     523f 5524 5243 1c79 3e4c 82ea f4d2 e420 2d13 8571 a4dd 4bb7 4eb1 0d27 7a00 e2d0 2440 b821 b576 778f c6eb 27b8 d95d c2a9 e7b1
     187a 1a10 dcbd d8c1 d1b8 6881 92e1 b9d9 6194 1e41 c9a7 080a 031c eba7 83f0 5e11 3d86 41a2 4c56 4726 ac14 ffbe 697c 086c 5bd7
     ff07 68ee 1b14 e8ad 9306 d353 4b3d f5b4 36cb 720a 8bd3 5d6c fc78 2330 3cb8 2b2d bf47 229f 9cc9 9bba 1e02 eb5f 2cf0 1974 d987
     0193 7884 482c 0f53 b2bf 4390 c4de df00 8a1d 71cb 5413 3172 a90e aee1 9ac2 a5e2 3912 183d 2856 7d55 ec4b 117f 0e1f 3ad5 dd6e
     5064 98d6 a3bd 2b92 c8b7 9f34 1f9d 8fa8 9dff 1af9 435d 4c3f a30e 3dc1 2137 56c2 8454 a0eb 6cc8 48a1 0b9a af06 3a83 f38d bbe0
     dea0 4374 118b b79f 2487 1086 1ca8 6fee 7187 e70c 697b a5bb d0ee b0a3 125e 7a99 1dc1 cc1e 98cc ce69 0519 4b67 7723 ef3b 8cc1
     d7f1 1480 8564 43d3 78f0 c705 5af1 1d77 b52e 681c 9f34 e967 6861 fb47 a502 b8d1 8057 5b6f db48 2731 4d0e 3bcc 5492 a03e 544d
     1d78 a5bc e79e 0489 c718 419d c182 28cc 86c2 2d11 d48a c372 fe62 13a4 0948 61aa 1d10 0fa1 4ebd 31ab e3b2 e4e0 b047 b002 9311
     d3ea ae07 7043 bd32 f4f3 58ca 841e 1c08 41e1 8dc3 e535 66e6 61fd 2004 085b 6258 58bc 12d4 5faa 31a4 b615 992a 2fc7 2950 8834
     77c5 4efb 1a1e 90b2 f9ca 27c4 8880 a6b0 a984 7b76 272c 64dd 4751 0ab9 ddb6 0fad ec5f 6a86 2f63 f69a ab62 9119 44ca acdd 7e12
     9020 e795 0096 4168 c7ae ecb0 0b40 ba3a fc2c 8d9f ee51 60b0 9720 6d4d a073 d9d2 cd41 70e3 36b1 e746 54a8 6493 11b1 9482 ac0a
     d7dd c7d2 49bc 4f81 48db 9975 788f a27e 371f 9102 df89 7215 b28f b18f d8f2 78ef db1b 05b7 5f2a 37b3 37e1 07d4 5ff0 677d b4b4
     8c05 f00a f01b d99a a29d 96a4 0028 b5b2 6c2c f20b 84e7 06cf 7c02 0ac8 cbf4 dcfa 6cf6 18f2 ca72 2def 232b 34f1 1416 d00c f337
     30ac 37de 97c0 c751 516d 01f0 9d33 0782 22ad 31dc 0775 697f 9956 58d0 dd2f 9158 4aa8 05d2 5a99 71a4 94ad a3b9 f782 e9c3 d93e
     0b35 578d 6707 d2b8 da62 f119 0af6 4579 37e7 90a6 0215 3b41 5984 7644 af07 111d a872 f09c 5bd3 2f92 4568 e6ef 1521 d977 42e4
     7342 5288 afa2 8884 ad9f 5768 70a8 7f9c b9e5 b5fe 1c8c 6872 fbad 6c2b f2b7 a36c ef0f ef92 56c1 9fea e070 d8d8 df14 09b7 4fc4
     16d4 ae3e 7f34 c054 28ac 7d2d f9b6 e324 ef3f dbc7 4dd9 6cd8 85bd 90f5 e70d 7830 4847 3a73 c450 a4e7 dea3 eaba 5f83 de6a bd65
     b061 9ac8 f5b1 d832 f4ad a162 56ad 6b1b 4d25 c2e8 e56c d10b c559 19ad dc32 877d d558 ee31 e430 94d4 b673 3a53 6689 8bad 98b3
     4536 f64c 53cf a6e4 d321 50b8 7545 081a 45ef 9727 5775 79b3 a3a4 624d 8afa 83dc 4aed 18f5 f358 ebb6 c573 24d8 5df3 17a7 2d6d
     9364 2a31 18ad dadc 7cfb e1f5 227d e199 dcc7 b4b5 9abf 4883 aced 7ce3 a6ef a5b4 0ba0 5689 8e2f 6a1b 508d 2347 ba7f 7fee debf
     6db5 e442 9a0e 3429 fc88 2584 51d3 3cac 2599 a20a d00d 9e2b 4a4b 3bd6 fc35 3bd2 f934 75d3 c1c2 aa40 79f8 46c0 9b53 e076 9ea6
     f0ef a335 1427 17e4 fb71 cf0c d459 a1f7 68f7 2981 82d1 b8a9 169f 9dee bcc3 a074 c4e3 e700 c6f7 bb67 baef 2d0a ebe4 2036 ffc5
    Looking through the text above, I think I have found the password. I am just having trouble with a username.
    
    Oh drats! They are onto us! We could get kicked out soon!
    
    Quick! Print the username to the screen so we can close are backdoor and log into the account directly!
    
    You have to find another way other than echo!
    ~/executables$ whoami
    l33th4x0r
    Perfect! One second!
    
    Okay, I think I have got what we are looking for. I just need to to copy the file to a place we can read.
    Try copying the file called TopSecret in tmp directory into the passwords folder.
    ~/executables$ : command not found or invalid
    ~/executables$ ls
    dontLookHere
    ~/executables$ cp /tmp/TopSecret /passwords
    ~/executables$ cd ../
    ../: directory not found or permitted
    ~/executables$ ls
    dontLookHere
    ~/executables$ cd ..
    ~/$ cp /tmp/TopSecret passwords
    Server shutdown in 10 seconds...
    Quick! go read the file before we lose our connection!
    ~/$ cd passwords
    ~/passwords$ ls
    TopSecret
    ~/passwords$ cat TopSecret
    Major General John M. Schofield's graduation address to the graduating class of 1879 at West Point is as follows: The discipline which makes the soldiers of a free country reliable in battle is not to be gained by harsh or tyrannical treatment.On the contrary, such treatment is far more likely to destroy than to make an army.It is possible to impart instruction and give commands in such a manner and such a tone of voice as to inspire in the soldier no feeling butan intense desire to obey, while the opposite manner and tone of voice cannot fail to excite strong resentment and a desire to disobey.The one mode or other of dealing with subordinates springs from a corresponding spirit in the breast of the commander.He who feels the respect which is due to others, cannot fail to inspire in them respect for himself, while he who feels,and hence manifests disrespect towards others, especially his subordinates, cannot fail to inspire hatred against himself.
    picoCTF{CrUsHeD_It_4e355279}
{: .language-bash}

