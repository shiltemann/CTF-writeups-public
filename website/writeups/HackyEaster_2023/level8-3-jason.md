---
layout: writeup

title: Jason
level: 8         # optional, for events that use levels (like HackyEaster)
difficulty: hard    # easy/medium/hard etc, if applicable
points: 300        # if used
categories: [misc]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2023{gr3pp1n_d4_js0n_l1k3_4_pr0!}

---

## Challenge

Jason has implemented an information service.

He has hidden a flag in it, can you find it?

Connect to the server:

`nc ch.hackyeaster.com 2304`


## Solution

Ahh the name should've been a give away huh? It took me a minute nonetheless

```
> 1/2
Result: 0.05
> enter "name", "surname", "street", "city", "country", or "q" to quit
> 20/1
Result: 0.2
> enter "name", "surname", "street", "city", "country", or "q" to quit
> 200/1
Result: 0.2
> enter "name", "surname", "street", "city", "country", or "q" to quit
> 1 + 1
Result: 1.1
> enter "name", "surname", "street", "city", "country", or "q" to quit
> 1 + 1 + 1
Result: 2.1
> enter "name", "surname", "street", "city", "country", or "q" to quit
```

From the above I finally understood it must be prepending `.` to the queries, and since it'll process math, we can use it as an annoying calculator. But the key realisation is the `.` is prepended.

So we can try some other JSON access things like you'd do with `jq`:

```
> []
Result: "Jason"
> enter "name", "surname", "street", "city", "country", or "q" to quit
> [][0]
Something went wrong.
> enter "name", "surname", "street", "city", "country", or "q" to quit
> [0]
Something went wrong.
> enter "name", "surname", "street", "city", "country", or "q" to quit
> [1]
Something went wrong.
> enter "name", "surname", "street", "city", "country", or "q" to quit
> {}
Invalid input!
> enter "name", "surname", "street", "city", "country", or "q" to quit
```

Ahh `keys` works

```
>  | to_entries
Result: [
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | to_keys
Something went wrong.
> enter "name", "surname", "street", "city", "country", or "q" to quit
> keys
Result: null
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | .keys
Result: null
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys
Result: [
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys | .[0]
Result: "city"
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys | .[1]
Result: "country"
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys | .[2]
Result: "covert"
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys | .[3]
Result: "name"
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys | .[4]
Result: "street"
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys | .[5]
Result: "surname"
> enter "name", "surname", "street", "city", "country", or "q" to quit
>  | keys | .[6]
Result: null
> enter "name", "surname", "street", "city", "country", or "q" to quit
> covert
Result: {
> enter "name", "surname", "street", "city", "country", or "q" to quit
```

and covert looks interesting!

```
> covert | keys | .[0]
Result: "flag"
> enter "name", "surname", "street", "city", "country", or "q" to quit
> covert.flag
Result: "he2023{gr3pp1n_d4_js0n_l1k3_4_pr0!}"
```

it can't be this easy?
