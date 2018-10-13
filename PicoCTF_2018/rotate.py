>>> for i in range(1, 18):
...     q = '0b' + ('0' * i) + '0010010000111100011111100000101000001110101000101100111001001110101011100110001001001110101111001000011010000100100011100'[0:-i]
...     q = "{0:032x}".format(int(q, 2))
...     print("{0:02d} {1} {2}".format(i, q, binascii.unhexlify(q)))
...
01 00243c7e0a0ea2ce4eae624ebc86848e b'\x00$<~\n\x0e\xa2\xceN\xaebN\xbc\x86\x84\x8e'
02 00121e3f05075167275731275e434247 b"\x00\x12\x1e?\x05\x07Qg'W1'^CBG"
03 00090f1f8283a8b393ab9893af21a123 b'\x00\t\x0f\x1f\x82\x83\xa8\xb3\x93\xab\x98\x93\xaf!\xa1#'
04 0004878fc141d459c9d5cc49d790d091 b'\x00\x04\x87\x8f\xc1A\xd4Y\xc9\xd5\xccI\xd7\x90\xd0\x91'
05 000243c7e0a0ea2ce4eae624ebc86848 b'\x00\x02C\xc7\xe0\xa0\xea,\xe4\xea\xe6$\xeb\xc8hH'
06 000121e3f05075167275731275e43424 b'\x00\x01!\xe3\xf0Pu\x16rus\x12u\xe44$'
07 000090f1f8283a8b393ab9893af21a12 b'\x00\x00\x90\xf1\xf8(:\x8b9:\xb9\x89:\xf2\x1a\x12'
08 00004878fc141d459c9d5cc49d790d09 b'\x00\x00Hx\xfc\x14\x1dE\x9c\x9d\\\xc4\x9dy\r\t'
09 0000243c7e0a0ea2ce4eae624ebc8684 b'\x00\x00$<~\n\x0e\xa2\xceN\xaebN\xbc\x86\x84'
10 0000121e3f05075167275731275e4342 b"\x00\x00\x12\x1e?\x05\x07Qg'W1'^CB"
11 0000090f1f8283a8b393ab9893af21a1 b'\x00\x00\t\x0f\x1f\x82\x83\xa8\xb3\x93\xab\x98\x93\xaf!\xa1'
12 000004878fc141d459c9d5cc49d790d0 b'\x00\x00\x04\x87\x8f\xc1A\xd4Y\xc9\xd5\xccI\xd7\x90\xd0'
13 00000243c7e0a0ea2ce4eae624ebc868 b'\x00\x00\x02C\xc7\xe0\xa0\xea,\xe4\xea\xe6$\xeb\xc8h'
14 00000121e3f05075167275731275e434 b'\x00\x00\x01!\xe3\xf0Pu\x16rus\x12u\xe44'
15 00000090f1f8283a8b393ab9893af21a b'\x00\x00\x00\x90\xf1\xf8(:\x8b9:\xb9\x89:\xf2\x1a'
16 0000004878fc141d459c9d5cc49d790d b'\x00\x00\x00Hx\xfc\x14\x1dE\x9c\x9d\\\xc4\x9dy\r'
17 000000243c7e0a0ea2ce4eae624ebc86 b'\x00\x00\x00$<~\n\x0e\xa2\xceN\xaebN\xbc\x86'
