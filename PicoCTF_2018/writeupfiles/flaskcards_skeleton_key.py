from itsdangerous import base64_decode, base64_encode
import zlib

from itsdangerous import Signer
from itsdangerous import JSONWebSignatureSerializer

def decrypt(cookie):
    """Decode a Flask cookie."""
    try:
        compressed = False
        payload = cookie

        if payload.startswith('.'):
            compressed = True
            payload = payload[1:]

        data = payload.split(".")[0]

        data = base64_decode(data)
        if compressed:
            data = zlib.decompress(data)

        return data.decode("utf-8")
    except Exception as e:
        return "[Decoding error: are you sure this was a Flask session cookie? {}]".format(e)

def encrypt(cookie, secret_key):
    data = zlib.compress(cookie)
    data = base64_encode(data)

    # concat, django-concat and hmac
    s = Signer(secret_key, key_derivation='hmac')
    data1 = s.sign(data)
    return data1

cookie = '.eJwlj8uKwzAMAP_F5xxsvSL3Z4JsSWwp7ELSnkr_vYa9z8DMuxx5xvVTbs_zFVs57l5uxRwDe6Vahcl2Ms0KXMUoh6I7IgE0HcSufYoKx0DHXcOYwKlPlkRAi-GZqYCcjEkBOVO7GUwOttaWbHtomzarTfLWxapj2cq8zjyef4_4XT0LVhBpwIyqMnqb3HRXRKUBoqQ-Vkwu73XF-T_Ravl8Aa_JPmw.DrIbMQ.mLvNGriozmsC4ufO9lqGP44D340'

cookie_plain = '{"_fresh":true,"_id":"ad3e390400654a74a8f02506a4fb83dd3342218b45d89c6865eb3d378ea542d49c56f323aebdfff8235f53f4e2fcf89aa2c5e5a119c6a7e81cac0ac4d196a0d3","csrf_token":"5e5826612553886b91c518783384b26848dbb45f","user_id":"10"}'


secret_key = '385c16dd09098b011d0086f9e218a0a2'
#secret_key = base64_decode('OFwW3QkJiwEdAIb54higog==')

print(decrypt(cookie))

print(encrypt(cookie_plain, secret_key))



