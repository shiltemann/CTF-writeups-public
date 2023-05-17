---
layout: writeup

title: Layer Cake
level: 7        # optional, for events that use levels (like HackyEaster)
difficulty: medkum     # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [forensics]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{th3_c4k3_is_a_l1e!}

---

## Challenge

Someone said there would be cake.

```
hackyeaster/layercake:latest
```

## Solution

Upon pulling the first layer was fat, the next 30 were small, so, guessing they're just overwriting a file repeatedly.

So we immediately write this handy one liner, to search through every diff directory provided by docker, copying any egg.png files into tmp.

```
for x in $(docker inspect hackyeaster/layercake:latest | jq '.[0].GraphDriver.Data.LowerDir' -r | tr ':' '\n'); do
	egg=$(sudo find $x -name egg.png); hash=$(echo $egg | cut -d/ -f 6); sudo cp $egg /tmp/egg-$hash.png;
done;
```

And then we see [all of the eggs](./writeupfiles/docker/) and rely on manual inspection to find our egg.

![](./writeupfiles/docker/egg-d1e3694e8325c7e62e33e7d67454f5643843d2224f99711baa22c3c1c6af3b96-solve.png)

