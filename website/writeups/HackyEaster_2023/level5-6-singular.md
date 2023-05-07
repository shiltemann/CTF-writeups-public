---
layout: writeup

title: Singular
level:  # optional, for events that use levels
difficulty: easy
points: 100
categories: [misc]
tags: []

flag:

---

## Challenge

Wow, so many flags!

Find the real flag, which is unique in multiple ways.

[singular.zip](writeupfiles/singular.zip)

Hint: This one can be solved with linux commands, with a one-liner.

## Solution

First let's get the real uniques:

```bash
cat singular.txt | sort | uniq -c | grep ' 1 ' | egrep he.* -o > unique.txt
```

Those are unique within the entire file which we can reasonably assume from the challenge description. In discord we see the additional hint:

> While it's unique from top to bottom, it's unique from left to right as well

So let's look for things which do not re-use a single letter letter from left to right:

```
$ cat unique.txt | egrep '\{(.*)\}' -o | egrep '([^_]).*\1' --invert-match
$
```

nothing. Every single entry re-uses letters left to right. 

### Word Frequency

```
$ cat unique.txt | egrep '\{(.*)\}' -o | wf | sort -n
Total words: 960
    1	Mr
    1	act
    1	as
    1	because
    1	begin
    1	body
    1	boy
    1	cause
    1	crime
    ...
   10	represent
   10	ten
   10	yes
   11	become
   11	party
   11	service
```

lots of unique, and non-unique words.

```
$ egrep -f <( cat unique.txt | egrep '\{(.*)\}' -o | wf | sort -n | egrep '\s1\s' | cut -f 2 | sed -r 's/(.*)/_?\1_?/g') unique.txt
Total words: 960
he2023{according_physical_success_ask}
he2023{account_consider_small_medical}
he2023{action_and_cell_indeed}
he2023{activity_know_shoulder_bring}
he2023{actually_still_thank_available}
he2023{add_girl_everything_care}
he2023{add_measure_staff_will}
he2023{affect_help_check_season}
he2023{again_different_economic_improve}
he2023{alone_of_Mrs_trade}
```
{: file="ones with unique words.txt"}

But none of them have two unique words (based on grep colouring). Similar result of you check the extremely non-unique ones:

```
$ egrep -f <( cat unique.txt | egrep '\{(.*)\}' -o | wf | sort -n | egrep '\s11\s' | cut -f 2 | sed -r 's/(.*)/_?\1_?/g') unique.txt
```

### Capitals?

Not that many have capitals

```
$ cat unique.txt|grep '[A-Z]'
he2023{American_address_item_book}
he2023{American_environmental_parent_like}
he2023{Congress_power_size_particular}
he2023{I_nearly_book_bar}
he2023{I_particularly_name_positive}
he2023{TV_economic_respond_race}
he2023{admit_change_start_Congress}
he2023{adult_number_I_year}
he2023{age_Republican_bed_must}
he2023{alone_of_Mrs_trade}
he2023{among_American_author_agreement}
he2023{blood_other_give_I}
he2023{especially_Mrs_cell_majority}
he2023{fall_still_TV_the}
he2023{fund_music_hotel_PM}
he2023{interview_seem_which_Mrs}
he2023{issue_Republican_war_six}
he2023{memory_service_Mr_activity}
he2023{modern_assume_TV_follow}
he2023{money_story_TV_future}
he2023{need_final_name_American}
he2023{never_PM_cover_camera}
he2023{news_Republican_true_actually}
he2023{owner_Mrs_reveal_provide}
he2023{serious_PM_statement_arm}
he2023{standard_risk_impact_I}
he2023{teacher_white_hear_TV}
```

Mr is the only one with a unique capital word:

```
$ cat unique.txt|grep '[A-Z][a-z]*' -o | wf
    5	I
    3	M
    3	P
    5	T
    5	V
    2	Congress
    4	Mrs
    4	American
    1	Mr
    3	Republican
Total words: 10
```

### Tried flags

```
he2023{worker_sister_everybody_next} # Had two unique words
he2023{become_attorney_media_become} # Also not, had two extremely non-unique words.
he2023{memory_service_Mr_activity}   # Nope
he2023{my_PM_whole_part}             # no re-used letters from left to right.
```

## Misc

`wf` is pretty nice, I didn't know such a command existed.

```
$ dnf info wf
Installed Packages
Name         : wf
Version      : 0.41
Release      : 28.fc36
Architecture : x86_64
Size         : 39 k
Source       : wf-0.41-28.fc36.src.rpm
Repository   : @System
From repo    : fedora
Summary      : Simple word frequency counter
URL          : http://www.async.com.br/~marcelo/wf/
License      : GPLv2
Description  : wf scans a text file and counts the frequency of words through the
             : whole text.
```
