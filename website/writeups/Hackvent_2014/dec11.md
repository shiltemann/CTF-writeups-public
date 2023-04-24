# December 11th: Good Old Times

**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=11](http://hackvent.hacking-lab.com/challenge.php?day=11)

**Hint**

*back in time, once again*

**Challenge**

We get a windows binary: [GoodOldTimes.exe](images/GoodOldTimes.exe)

**Solution**

When we run the executable, we get a message saying:

```
Sorry, all present are gone. Maybe you should go a bit back in time?
```

Faking the system time does not seem to help, and running the code in a debugger also leads nowhere, then we think old-school, and try Dos:

We run the program in DosBox and get:

```
I'm GLaD you found me. Now go back to your time and feed the Ball-O-Mat: I'm Still Alive
```

So we input *I'm Still Alive* into the ball-o-matic to get our bauble:

![](images/aaax__9OLm2bHs_zgWBf.png)


**Flag**

```
HV14-JpE4-CTg9-RuDG-9GFF-195M
```

