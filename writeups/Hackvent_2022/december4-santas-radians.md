---
layout: writeup

title: Santa's Radians
level:  # optional, for events that use levels
difficulty: easy
categories: []
tags: []

flag: HV22{C4lcul8_w1th_PI}

---

## Challenge

Santa, who is a passionate mathematician, has created a small website to train his animation coding skills. Although Santa lives in the north pole, where the degrees are very low, the website's animation luckily did not freeze. It just seems to move very slooowww. But how does this help...? The elves think there might be a flag in the application...


## Solution
en a website with some moving discs:

![](writeupfiles/dec4/radians.gif)

We look at the html code for the page:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>The UPICode</title>
</head>
<body>

<h3>[HV22.04] Santa's radians</h3>

<canvas width="1200" height="200" id="canvasPiCode" style="border: 1px solid black"></canvas>
<script>
    const canvas = document.getElementById("canvasPiCode");
    const context = canvas.getContext("2d");
    let clientX = 0;

    canvas.addEventListener('mousemove', e => {
        clientX = e.clientX*7/1000;
    });

    let rot = [2.5132741228718345, 0.4886921905584123, -1.2566370614359172, 0, 2.548180707911721, -1.9547687622336491, -0.5235987755982988, 1.9547687622336491, -0.3141592653589793, 0.6283185307179586, -0.3141592653589793, -1.8151424220741028, 1.361356816555577, 0.8377580409572781, -2.443460952792061, 2.3387411976724013, -0.41887902047863906, -0.3141592653589793, -0.5235987755982988, -0.24434609527920614, 1.8151424220741028];
    let size = canvas.width / (rot.length+2);

    context.strokeStyle = "black";
    context.lineWidth = size*5/16;
    context.shadowOffsetX = size/4;
    context.shadowOffsetY = size/4;
    context.shadowColor = "gray";
    context.shadowBlur = size/4;

    let animCount = 0;

    function anim() {
        context.clearRect(0,0,canvas.width,canvas.height);
        for (let i = 0; i < rot.length; i++) {
            context.beginPath();
            context.arc((i + 1) * size, canvas.height / 2, size * 2 / 7, rot[i]+animCount+clientX, rot[i] + 5 +animCount+clientX);
            context.stroke();
        }
        animCount+=0.001;
        requestAnimationFrame(anim);
    }
    anim();

</script>

</body>
</html>
```

So the `rot` variable likely encodes the flag, if we can just find out how..

```javascript
let rot = [2.5132741228718345, 0.4886921905584123, -1.2566370614359172, 0, 2.548180707911721, -1.9547687622336491, -0.5235987755982988, 1.9547687622336491, -0.3141592653589793, 0.6283185307179586, -0.3141592653589793, -1.8151424220741028, 1.361356816555577, 0.8377580409572781, -2.443460952792061, 2.3387411976724013, -0.41887902047863906, -0.3141592653589793, -0.5235987755982988, -0.24434609527920614, 1.8151424220741028]
```

It most likely starts with `HV22` ..but we see that the third and fourth element aren't equal, and the fourthe element is 0, so maybe it doesn't encode directly, but as a function of the previous character..

Maybe converting radians to degrees? Let's try the first couple:

```bash
Python 3.9.7 (default, Jun 22 2022, 20:11:26)
[GCC 11.2.0] on linux
Type "help", "copyright", "credits" or "license" for more information.
>>> ord("H")
72
>>> ord("V")
86
>>> ord("2")
50
>>> import math
>>> math.degrees(2.5132741228718345)
144.0
>>> math.degrees(0.4886921905584123)
28.0
>>> math.degrees(-1.2566370614359172)
-72.0

```

We are looking for these numbers to map to HV22 (`72 86 50 50` in ASCII). It doesn't quite work out, but we notice some things

- 144 is twice the expected value of 72
- 28 is twice the difference between H (72) and V (86)
- -72 is twice the difference from V (86) to 2 (50)

Ok, looks like we got it! Let's get a quick python script to decode for us:

```python
import math

rot = [2.5132741228718345, 0.4886921905584123, -1.2566370614359172, 0, 2.548180707911721, -1.9547687622336491, -0.5235987755982988, 1.9547687622336491, -0.3141592653589793, 0.6283185307179586, -0.3141592653589793, -1.8151424220741028, 1.361356816555577, 0.8377580409572781, -2.443460952792061, 2.3387411976724013, -0.41887902047863906, -0.3141592653589793, -0.5235987755982988, -0.24434609527920614, 1.8151424220741028]

last = 0
flag = ""
for i in rot:
  f = last+math.degrees(i)/2
  last = f
  flag += chr(round(f))

print(flag)

```

This gives us the flag:

```
HV22{C4lcul8_w1th_PI}
```


Note: at first I used `int(f)` instead of `round(f)` and it got off by one halfway through the flag, so if you are ending up with a flag like `HV22{C4lcul8_w1sg^OH|`, it may be because of this issue



