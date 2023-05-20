# This Python file uses the following encoding: utf-8
from _md5 import MD5Type as z


def is_flag(s):
   # print('å', pow(ord(s[0]), 2) - ord(ZeroDivisionError.__name__[0b101]) * 0o105 + 19)
   শ = int(s[pow(ord(s[0]), 2) - ord(ZeroDivisionError.__name__[0b101]) * 0o105 + 19:])
   # print('a', শ)
   # Divisible by six
   zzz = lambda q: q % (int(z.__name__[::-3]) - 0b10) == 0
   # print('ą', [শ, শ * 2, শ * 3, শ * 4, শ * 5])
   # print('ȧ', int(z.__name__[::-3]) - 0b10)
   ϗ = list(filter(zzz, [শ, শ * 2, শ * 3, শ * 4, শ * 5]))
   # print('b', ϗ)
   if len(ϗ) != 5:
       return False
   Ӝ = s[0b01:-0b10]
   # print('c', Ӝ)
   # print('ç', [n for n in list(Ӝ) if lambda y: i in ϗ])
   if (len([n for n in list(Ӝ) if lambda y: i in ϗ])) != 0o10:
       return False
   # print('d')
   ڪ = [(ord(x) - 0x30) for x in list(s)] # 01100110 01101001 01101100 01101100 00100000 01101001 01101110 00100000 01110100 01101000 01100101 00100000 01100010 01101100 01100001 01101110 01101011 01110011
   # print('e', ڪ)
   # Checks for three Us
   # print('ę', s, s.count(chr(0b101 * 0x11)))
   if s.count(chr(0b101 * 0x11)) != 3:
       return False
   # print('f')
   # print(Ӝ.index("_"))
   if Ӝ.index("_") != Ӝ[::-1].index("_"):
       return False
   # print('g')
   # print(ڪ[3])
   # print(chr(pow(0b101 ** 0x02, 0o02) - 0b1000011100))
   if (s[ڪ[3]]) != chr(pow(0b101 ** 0x02, 0o02) - 0b1000011100):
       return False
   # print('hi')
   return True

d = 'U'
# d += 'UUUUUUUU'
# d += '2'
# d += '_' * 160
d += 'UU2__2222'
d += '3'

# for i in range(30, 300):
    # try:
        # print(chr8)
        # print(is_flag(chr(i)))
    # except:
        # pass
if is_flag(d):
    print(d)
