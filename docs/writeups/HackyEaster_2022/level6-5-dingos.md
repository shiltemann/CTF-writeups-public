---
layout: writeup

title: Dingos
level: 6         # optional, for events that use levels (like HackyEaster)
difficulty: medkum     # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [cloud]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{4_b4rk1n9_D1NG0_n3v3r_b1735}


---

## Challenge

If you like 🐕 Dingos, check out my new web site!

👉 [my fancy Dingo web site](https://dingos.s3.eu-west-1.amazonaws.com/index.html)


## Solution
ite specifically mentions "version 2", and a file listing is available.

```
$ aws s3 ls --recursive s3://dingos/
2022-02-09 08:44:52      63320 img/dingo1.jpg
2022-02-09 08:44:52      50718 img/dingo2.jpg
2022-02-09 08:44:52     107979 img/dingo3.jpg
2022-02-09 08:44:51     149397 img/dingo4.jpg
2022-02-09 08:45:16      96515 img/dingo_egg_ognid.png
2022-02-09 08:45:16        731 index.html
```

the current version of the egg file:

![](./writeupfiles/dingo_egg_ognid.png)

doesn't have anything useful. But if you know that S3 supports file versioning, we can see older versions of the file.

```
$ aws s3api list-object-versions --bucket dingos | jq '.Versions[] | select(.Key == "img/dingo_egg_ognid.png")'
{
  "ETag": "\"ba360fc78d0e6a5fbd99a6de04230247\"",
  "Size": 96515,
  "StorageClass": "STANDARD",
  "Key": "img/dingo_egg_ognid.png",
  "VersionId": "bBYeh2BHMNmSMjrwPuwe3IqT00UCd0Dq",
  "IsLatest": true,
  "LastModified": "2022-02-09T07:45:16.000Z",
  "Owner": {
    "DisplayName": "philipp.ps.sieber",
    "ID": "5b93a57df84ba174c0c60cdea70ca63d204bc59e3877d4b7ff1d76b79500562f"
  }
}
{
  "ETag": "\"7aea46507f7d9c400854bf721fbc76ed\"",
  "Size": 34716,
  "StorageClass": "STANDARD",
  "Key": "img/dingo_egg_ognid.png",
  "VersionId": "efyGzmXduxQAcaQIBgsxEj5i8xlCUdjG",
  "IsLatest": false,
  "LastModified": "2022-02-09T07:44:51.000Z",
  "Owner": {
    "DisplayName": "philipp.ps.sieber",
    "ID": "5b93a57df84ba174c0c60cdea70ca63d204bc59e3877d4b7ff1d76b79500562f"
  }
}
```

And thus obtain our URL!

https://dingos.s3.eu-west-1.amazonaws.com/img/dingo_egg_ognid.png?versionId=efyGzmXduxQAcaQIBgsxEj5i8xlCUdjG

![](writeupfiles/dingo_egg_ognid.png?versionId=efyGzmXduxQAcaQIBgsxEj5i8xlCUdjG)


