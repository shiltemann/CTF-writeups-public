import secrets
import hashlib
from base64 import b64encode
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad

g = 3
p = 0x00e1a540d72bb311db26ea6e58b7dc207cf55d0c3a90d7c1f74e7fcb67c7af097d99c73e002c9266e70cbdf735ebd864ea279a0a4d41dd6537837bfc07d84943a376d163ec20a51dd6073dbfc34cbdce9d88ad22a9bb72f5bb143b5c9e531ab100590b9f97d1e9c7a3dfe7961fd6e86078ad43918b47816925803db47862e5f69c90078c6dc287fc6cf7742a9f1717d828a610fe469c92f34783351b21ac1ec988eae0e16ff4ef89c1a19ccd7e3b5cb0c14e0424dfde338789923013aeb7791e19ba378cb2e0e0b318f46865d438ac53999f69f0ae8045d2ff40821b5fdcb0a3b9942f29a0cd8e55febd0ee9006d936d51335a2e63b6affbed6175e1228a53d6a9

class Server():
    def __init__(self, port=3331):
        self.port = port
        self.fKE = FasterKeyExchange(g, p)
        self.y_server = self.fKE.calculate_y()
        self.y_client = 0
        self.IV = "d724c349c2b28831"
        self.key = "313371337"

    def print_banner(self):
        print("       ---_ ......._-_--.")
        print("      (|\ /      / /| \  \\")
        print("      /  /     .'  -=-'   `.")
        print("     /  /    .'             )")
        print("   _/  /   .'        _.)   /")
        print("  / o   o        _.-' /  .'")
        print("  \          _.-'    / .'*|")
        print("   \______.-'//    .'.' \*|")
        print("    \|  \ | //   .'.' _ |*|")
        print("     `   \|//  .'.'_ _ _|*|")
        print("      .  .// .'.' | _ _ \*|")
        print("      \`-|\_/ /    \ _ _ \*\\")
        print("       `/'\__/      \ _ _ \*\\")
        print("      /^|            \ _ _ \*")
        print("     '  `             \ _ _ \\")
        print("                       \_")
        print("Challenge by pyth0n33. Have fun!")
        
    def run(self):
        self.print_banner()
        input("Enter to start")
        print("\x1b[2J\x1b[H")
        print("Here's my y={0}\n\n".format(self.y_server))
        self.y_client = int(input("Now give me your y please: "))
        self.key = str(self.fKE.calculate_key(self.y_client))
        self.iv = self.key[0:16]
        self.encrypt()

    def encrypt(self):
        key = bytes(hashlib.md5(bytes(self.key, "utf-8")).hexdigest(), "utf-8")
        cipher = AES.new(key, AES.MODE_CBC, iv=bytes(self.iv, "utf-8"))
        cipher_text_bytes = cipher.encrypt(pad(b"The Advanced Encryption Standard (AES), also known by its original name Rijndael, is a specification for the encryption of electronic data established by the U.S. National Institute of Standards and Technology (NIST) in 2001.", AES.block_size))
        print(b64encode(cipher_text_bytes))
        
class FasterKeyExchange():
    def __init__(self, g, p):
        self.g = g
        self.p = p
        self.x = self.get_random_x()

    def get_random_x(self):
        return secrets.SystemRandom().randint(g, p-2)
    
    def calculate_y(self):
        return (self.g * self.x) % self.p

    def calculate_key(self, y):
        return (y * self.x) % self.p

if __name__ == "__main__":
    server = Server()
    server.run()
        
        


    
