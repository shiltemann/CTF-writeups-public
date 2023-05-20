# Advent of code

Yearly coding advent calendar http://adventofcode.com

## Dec1: Inverse Captcha

**Challenge**  

The night before Christmas, one of Santa's Elves calls you in a panic. "The printer's broken! We can't print the Naughty or Nice List!" By the time you make it to sub-basement 17, there are only a few minutes until midnight. "We have a big problem," she says; "there must be almost fifty bugs in this system, but nothing else can print The List. Stand in this square, quick! There's no time to explain; if you can convince them to pay you in stars, you'll be able to--" She pulls a lever and the world goes blurry.

When your eyes can focus again, everything seems a lot more pixelated than before. She must have sent you inside the computer! You check the system clock: 25 milliseconds until midnight. With that much time, you should be able to collect all fifty stars by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day millisecond in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

You're standing in a room with "digitization quarantine" written in LEDs along one wall. The only door is locked, but it includes a small interface. "Restricted Area - Strictly No Digitized Users Allowed."

It goes on to explain that you may only leave by solving a captcha to prove you're not a human. Apparently, you only get one millisecond to solve the captcha: too fast for a normal human, but it feels like hours to you.

The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match the next digit in the list. The list is circular, so the digit after the last digit is the first digit in the list.

For example:

- `1122` produces a sum of `3` (`1` + `2`) because the first digit (`1`) matches the second digit and the third digit (`2`) matches the fourth digit.  
- `1111` produces `4` because each digit (all `1`) matches the next.  
- `1234` produces `0` because no digit matches the next.  
- `91212129` produces `9` because the only digit that matches the next one is the last digit, `9`.  

What is the solution to your captcha?

To begin, get your puzzle input: http://adventofcode.com/2017/day/1/input.

**Solution**

our input was:

```
21752342814933766938172121674976879111362417653261522357855816893656462449168377359285244818489723869987861247912289729579296691684761143544956991583942215236568961875851755854977946147178746464675227699149925227227137557479769948569788884399379821111382536722699575759474473273939756348992714667963596189765734743169489599125771443348193383566159843593541134749392569865481578359825844394454173219857919349341442148282229689541561169341622222354651397342928678496478671339383923769856425795211323673389723181967933933832711545885653952861879231537976292517866354812943192728263269524735698423336673735158993853556148833861327959262254756647827739145283577793481526768156921138428318939361859721778556264519643435871835744859243167227889562738712953651128317624673985213525897522378259178625416722152155728615936587369515254936828668564857283226439881266871945998796488472249182538883354186573925183152663862683995449671663285775397453876262722567452435914777363522817594741946638986571793655889466419895996924122915777224499481496837343194149123735355268151941712871245863553836953349887831949788869852929147849489265325843934669999391846286319268686789372513976522282587526866148166337215961493536262851512218794139272361292811529888161198799297966893366553115353639298256788819385272471187213579185523521341651117947676785341146235441411441813242514813227821843819424619974979886871646621918865274574538951761567855845681272364646138584716333599843835167373525248547542442942583122624534494442516259616973235858469131159773167334953658673271599748942956981954699444528689628848694446818825465485122869742839711471129862632128635779658365756362863627135983617613332849756371986376967117549251566281992964573929655589313871976556784849231916513831538254812347116253949818633527185174221565279775766742262687713114114344843534958833372634182176866315441583887177759222598853735114191874277711434653854816841589229914164681364497429324463193669337827467661773833517841763711156376147664749175267212562321567728575765844893232718971471289841171642868948852136818661741238178676857381583155547755219837116125995361896562498721571413742
```

And we calcaltate and submit our answer in python

```python
import requests
import re

url="http://adventofcode.com/2017/day/1/input"
url2="http://adventofcode.com/2017/day/1/answer"

cookies = {'session': '53616c7465645f5f9b4fa58a8dd0de4217fdc7ee993d93ea7685b7f370745a902c565ea941dac2c73fc5b71da6546b4a'}

r = requests.get(url, cookies=cookies)
numbers = r.text.rstrip()
numbers += numbers[0] # make circular

# calculate the answer
answer = 0
for digit in range(1,10):
    repeated_digit = chr(ord('0')+digit)*2
    occurrences = len(re.findall('(?='+repeated_digit+')', numbers))
    answer += occurrences*digit

# send answer
print('Sending answer: '+ str(answer))
r2 = requests.post(url2, cookies=cookies, data={'level':'1', 'answer': str(answer)})

print(r2.text)
```

response from server was:

```
[..]
<article><p>That's the right answer!  You are <span class="day-success">one gold star</span> closer to debugging the printer. <a href="/2017/day/1">[Return to Day 1]</a></p></article>
[..]
```


After submitting this, part two openened:

--- Part Two ---

You notice a progress bar that jumps to 50% completion. Apparently, the door isn't yet satisfied, but it did emit a star as encouragement. The instructions change:

Now, instead of considering the next digit, it wants you to consider the digit halfway around the circular list. That is, if your list contains 10 items, only include a digit in your sum if the digit 10/2 = 5 steps forward matches it. Fortunately, your list has an even number of elements.

For example:

- `1212` produces `6`: the list contains `4` items, and all four digits match the digit `2` items ahead.
- `1221` produces `0`, because every comparison is between a `1` and a `2`.
- `123425` produces `4`, because both `2`s match each other, but no other digit has a match.
- `123123` produces `12`.
- `12131415` produces `4`.

What is the solution to your new captcha?

Although it hasn't changed, you can still get your puzzle input: http://adventofcode.com/2017/day/1/input.

solution:

```python
import requests
import re

url="http://adventofcode.com/2017/day/1/input"
url2="http://adventofcode.com/2017/day/1/answer"

cookies = {'session': '53616c7465645f5f9b4fa58a8dd0de4217fdc7ee993d93ea7685b7f370745a902c565ea941dac2c73fc5b71da6546b4a'}

r = requests.get(url, cookies=cookies)
numbers = r.text.rstrip()
l = len(numbers)

# calculate the answer
answer = 0
for i in range(0,l):
    if numbers[i] == numbers[ (i+int(l/2))%l ]:
        answer += (ord(numbers[i])-ord('0'))
    print(answer)

# send answer
print('Sending answer: '+ str(answer))
r2 = requests.post(url2, cookies=cookies, data={'level':'2', 'answer': str(answer)})

print(r2.text)
```
