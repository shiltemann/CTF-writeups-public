import base64
import itertools

b64string="HOlAfOVWOqVd1o6q7u5Vj8Mv"
alphabet="=abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/"

perms = list(itertools.permutations(alphabet, 4))
print perms
print base64.b64decode(b64string)