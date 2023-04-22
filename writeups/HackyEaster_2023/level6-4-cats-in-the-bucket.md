---
layout: writeup

title: Cats in the Bucket
level: 6 # optional, for events that use levels
difficulty: medium
categories: [cloud]
tags: []

flag:

---

## Challenge

There is a bucket full of cat images. One of them contains a flag. Go get it!

```
Bucket: cats-in-a-bucket
Access Key ID: AKIATZ2X44NMCEQW46PL
Secret Access Key: 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
```

## Solution

We got a bucket name, so try a region:

When we visit [http://cats-in-a-bucket.s3-website-eu-west-1.amazonaws.com/](http://cats-in-a-bucket.s3-website-eu-west-1.amazonaws.com/) we get the message

```
400 Bad Request

- Code: IncorrectEndpoint
- Message: The specified bucket exists in another region. Please direct requests to the specified endpoint.
- Endpoint: cats-in-a-bucket.s3-website.eu-central-1.amazonaws.com
- RequestId: FB490WD7T4HGKGVW
- HostId: 6O/1HC9Rkpbz7CTt0N9LdLU8HBDG4r+soXelEScHgHp+opFS5f+hrlJEFUEJdDlTJwu8gLhCK9U=

```

ok so we go to [cats-in-a-bucket.s3-website.eu-central-1.amazonaws.com](cats-in-a-bucket.s3-website.eu-central-1.amazonaws.com), and here we see

```
404 Not Found

- Code: NoSuchWebsiteConfiguration
- Message: The specified bucket does not have a website configuration
- BucketName: cats-in-a-bucket
- RequestId: PM3VWVHPJF5P9JKR
- HostId: jOkYruD+efHf1nGXz/NehA/fVQxgqYrbm8c+Ia/4nl+fAnhO8ldZK40Z6WJM/1BYRL/RU8ymw58=
```

so there is no website, but we got the right bucket, now what? We have the access keys

we use awscli for further exploration:

```
$ aws s3 ls s3://cats-in-a-bucket

An error occurred (AccessDenied) when calling the ListObjectsV2 operation: Access Denied
```

ok, let's set up our credentials

```bash
$ aws configure
AWS Access Key ID [****************UWDA]: AKIATZ2X44NMCEQW46PL
AWS Secret Access Key [****************8Kup]: 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
Default region name [us-east-1]: eu-central-1
Default output format [None]:
```

and try again:

```bash
$ aws s3 ls s3://cats-in-a-bucket
2022-10-09 17:23:46      83709 cat1.jpg
2022-10-09 17:23:48      92350 cat2.jpg
2022-10-09 17:23:47     119214 cat3.jpg
2022-10-09 17:23:47      87112 cat4.jpg

```

ok, let's download those files:

```bash
$ aws s3 cp s3://cats-in-a-bucket/cat1.jpg .
download: s3://cats-in-a-bucket/cat1.jpg to ./cat1.jpg
$ aws s3 cp s3://cats-in-a-bucket/cat2.jpg .
download: s3://cats-in-a-bucket/cat2.jpg to ./cat2.jpg
$ aws s3 cp s3://cats-in-a-bucket/cat3.jpg .
download: s3://cats-in-a-bucket/cat3.jpg to ./cat3.jpg
$ aws s3 cp s3://cats-in-a-bucket/cat4.jpg .
fatal error: An error occurred (403) when calling the HeadObject operation: Forbidden
```

We get 3 very cute cat pictures

![](writeupfiles/cat1.jpg)
![](writeupfiles/cat2.jpg)
![](writeupfiles/cat3.jpg)

but an error on the fourth image, hmm..
