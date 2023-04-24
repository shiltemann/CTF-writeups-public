---
layout: writeup

title: Sanity Check
level: 1
difficulty: noob
points: 50
categories: [misc]
tags: [intro]

flag: he2023{just_A_sanity_chEck}

---

## Challenge

This is your first flag!

Right here --> `he2023{             }`

🚩 Flags are in format `he2023{...}`, unless noted otherwise. Always check additional information given (uppercase, lowercase, spaces, etc.).

## Solution

The inner part of the flag is invisible, but inspecting the source for the empty-looking space gives us the hidden flag:

```html
<span style="color: black; background-color: black; opacity: 0;">just_A_sanity_chEck</span>
```
