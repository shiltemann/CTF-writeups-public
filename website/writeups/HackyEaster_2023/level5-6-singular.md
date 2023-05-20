---
layout: writeup

title: Singular
level:  # optional, for events that use levels
difficulty: easy
points: 100
categories: [misc]
tags: []

flag: he2023{security_first_easy_catch}

---

## Challenge

Wow, so many flags!

Find the real flag, which is unique in multiple ways.

[singular.zip](writeupfiles/singular.zip)

Hint: This one can be solved with linux commands, with a one-liner.

## Solution

Here it is as a one liner:

```
cat writeupfiles/singular/singular.txt | sort | uniq -c | grep ' 1 ' | awk '{print length($0), $0}' | grep $(cat writeupfiles/singular/singular.txt | sort | uniq -c | grep ' 1 ' | awk '{print length($0)}' | sort | uniq -c | grep ' 1 ' | awk '{print $2}')
```

It looks for unique flags, then finds the one that has a unique length (33 characters).



*Sounds simple? hell no, this one really stumped me for a long time, I solved half of level 8 before this one lol. Below you can find a lot of the things we tried and notes we made of a lot of the rabbit holes we went down :P*


First let's get the real uniques:

```bash
cat singular.txt | sort | uniq -c | grep ' 1 ' | egrep he.* -o > unique.txt
```

Those are unique within the entire file which we can reasonably assume from the challenge description.

Tried a bunch of things next that didn't work, requiring each word to be unique in the file, in the unique flags, in its column, ..nothing. ..maybe the words all synonyms of unique? nope nope nope.


In discord we see the additional hint:

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

### Unique Column Values

I created lists of all the unique words in each column:


and then made this lovely grep:

```bash
egrep --color=auto '{(Congress|TV|able|about|above|according|across|action|activity|actually|address|administration|admit|adult|affect|against|ago|ahead|air|all|almost|always|animal|another|answer|appear|area|arm|around|art|article|artist|ask|assume|attack|audience|author|available|avoid|ball|bank|bar|before|begin|believe|benefit|best|bill|bit|black|body|box|break|bring|brother|building|buy|camera|campaign|capital|card|career|carry|case|challenge|character|charge|check|choice|choose|city|claim|clear|clearly|cold|color|common|community|computer|conference|consumer|contain|control|could|country|course|crime|culture|cup|current|dark|day|debate|decade|decide|decision|deep|defense|degree|design|despite|develop|development|different|difficult|director|discuss|drug|during|early|easy|education|enjoy|enough|even|evening|evidence|fact|far|father|fear|feeling|field|fight|figure|finally|find|firm|fish|focus|follow|foot|force|foreign|free|friend|front|full|fund|future|get|go|good|government|green|ground|growth|he|head|help|high|history|hold|home|how|human|hundred|idea|imagine|in|individual|industry|inside|instead|involve|kitchen|knowledge|language|last|lay|learn|least|less|letter|light|likely|local|lot|machine|major|manage|management|material|maybe|medical|meet|mention|method|middle|mind|mission|moment|most|movement|much|necessary|need|network|new|newspaper|no|note|notice|number|off|office|officer|on|only|opportunity|or|other|out|over|painting|paper|particularly|pay|per|person|personal|pick|picture|place|point|policy|poor|popular|positive|possible|practice|prepare|president|pressure|prevent|probably|product|production|professional|program|protect|quickly|quite|race|reach|real|reality|recently|recognize|record|red|relationship|religious|represent|research|return|rich|rise|rock|role|rule|safe|school|science|seat|second|section|security|seek|sell|send|sense|show|significant|sing|sister|sit|site|six|size|skin|small|society|someone|something|song|sound|south|southern|space|special|staff|stand|state|stay|stop|story|stuff|success|successful|suffer|summer|surface|system|table|talk|task|teacher|television|tell|term|than|that|their|themselves|theory|these|this|throw|thus|time|today|together|top|training|treat|treatment|tree|trial|trip|trouble|turn|unit|up|us|use|value|various|voice|walk|wall|want|watch|water|way|weight|well|what|whatever|where|who|whole|whom|wind|with|within|work|worry|would|wrong|you|young|your)_(American|ability|able|above|account|across|act|action|add|administration|again|against|agree|air|allow|almost|already|also|among|animal|answer|any|anyone|appear|apply|arrive|art|article|artist|at|attack|audience|authority|avoid|away|baby|base|be|because|before|behavior|behind|both|bring|budget|building|campaign|candidate|capital|car|care|carry|case|central|century|chance|choose|city|clearly|close|collection|college|common|concern|contain|cost|course|cultural|culture|data|daughter|deal|decade|defense|degree|design|develop|development|direction|director|discuss|do|doctor|door|down|draw|east|easy|education|effect|either|end|enough|entire|environmental|especially|establish|event|everybody|evidence|exactly|eye|face|factor|fall|fast|find|finish|firm|first|fish|force|forget|front|full|garden|gas|general|get|give|glass|go|grow|growth|hand|happen|head|health|heart|help|here|herself|history|hit|hope|hot|hotel|house|human|idea|image|impact|improve|in|indicate|industry|inside|into|involve|issue|it|job|join|kid|kitchen|knowledge|late|law|leader|least|leave|leg|let|letter|light|list|little|long|look|low|magazine|major|make|many|media|message|method|model|modern|morning|most|mouth|movie|much|my|name|national|near|necessary|network|never|nice|not|of|officer|official|often|once|only|operation|order|other|our|parent|part|partner|pass|performance|perhaps|phone|pick|position|positive|possible|present|pressure|pretty|process|product|purpose|push|put|quality|quickly|race|radio|ready|real|reason|receive|reduce|reflect|relate|relationship|religious|remain|response|result|risk|road|rule|run|science|sea|season|seat|see|seem|send|sense|series|short|simple|single|sit|site|skill|so|social|someone|soon|sound|source|staff|statement|stop|story|strong|support|sure|system|table|teacher|ten|tend|test|than|thank|the|their|them|there|these|they|think|thought|time|to|today|top|travel|treatment|trial|truth|type|understand|until|use|usually|value|various|voice|vote|water|we|weight|well|west|western|when|where|whether|which|whom|wife|with|within|wonder|write|year|you|your|yourself)_(I|Mr|Mrs|a|about|according|account|activity|actually|admit|adult|agree|agreement|ahead|air|allow|almost|already|amount|analysis|answer|anything|appear|approach|arm|article|as|ask|assume|attack|author|away|baby|bad|ball|bank|base|beat|become|before|behind|believe|best|bill|blood|blue|box|boy|break|bring|business|call|capital|care|career|case|cause|center|certain|certainly|character|child|choice|clear|cold|commercial|conference|contain|control|cost|could|country|couple|course|create|customer|cut|data|deal|deep|describe|develop|development|difference|dinner|direction|discover|discuss|do|dog|draw|dream|during|each|edge|eight|election|end|energy|enjoy|enter|environmental|establish|expert|explain|factor|fall|fast|fear|fight|figure|film|final|financial|find|fire|first|fish|five|floor|foot|forward|four|free|friend|from|future|general|generation|give|goal|good|government|great|green|grow|hair|hand|have|hear|heart|heavy|here|herself|high|him|history|hold|hope|hot|hotel|hour|identify|if|image|impact|important|improve|increase|information|interview|into|involve|itself|key|kid|kind|large|last|later|lawyer|lay|leader|learn|least|leg|let|light|like|line|list|listen|live|long|look|loss|lot|machine|main|make|man|manage|management|manager|market|may|mean|medical|meeting|member|mention|message|million|mind|minute|miss|most|mother|myself|nation|natural|near|new|news|newspaper|nice|night|nor|not|nothing|notice|occur|off|offer|officer|ok|once|one|only|open|outside|page|painting|paper|part|participant|particular|particularly|partner|pass|pay|peace|person|personal|political|popular|population|position|practice|present|president|prevent|product|production|program|project|protect|prove|purpose|push|race|raise|rate|rather|read|real|realize|really|reason|recently|reduce|reflect|report|represent|require|research|rock|rule|same|school|science|second|seek|seem|sell|sense|series|serious|set|shake|share|should|shoulder|show|sign|significant|simple|simply|situation|size|smile|so|soldier|some|soon|south|speak|staff|state|statement|station|step|store|story|strategy|stuff|success|such|support|surface|table|take|talk|task|tax|teach|team|television|tend|term|than|them|theory|there|these|thing|third|this|those|three|together|total|town|trade|traditional|travel|two|type|under|understand|unit|until|upon|value|very|view|visit|voice|vote|want|way|we|west|whatever|where|whether|why|wind|word|worker|would|wrong|you|young|your)_(American|Congress|Mrs|PM|TV|ability|able|action|address|affect|again|agree|air|also|amount|and|animal|another|any|arm|arrive|art|article|ask|attack|attention|authority|base|beat|beautiful|believe|better|beyond|bill|board|book|born|both|break|bring|build|building|buy|can|capital|car|case|cell|center|century|certain|certainly|chair|challenge|chance|change|charge|choice|choose|church|city|claim|clear|close|coach|collection|commercial|common|computer|condition|conference|consumer|contain|couple|cup|current|cut|debate|decade|decision|detail|determine|dinner|direction|discuss|do|down|either|employee|end|energy|enough|enter|environment|environmental|especially|establish|even|event|ever|every|everybody|exist|expect|experience|factor|family|far|fast|father|field|final|finally|find|firm|fish|follow|food|force|former|forward|four|from|game|general|girl|give|great|ground|gun|happy|hard|have|he|health|heart|himself|hold|home|hope|hospital|how|hundred|idea|identify|if|impact|including|information|institution|international|interview|involve|it|itself|join|just|key|last|law|lay|learn|left|let|letter|life|likely|list|little|loss|magazine|man|manage|management|manager|many|may|me|mean|media|medical|memory|message|method|middle|might|military|million|miss|mission|more|morning|most|move|movement|movie|much|music|my|need|new|news|next|nice|night|north|notice|number|offer|office|official|old|once|operation|opportunity|or|other|out|outside|page|painting|partner|people|per|perhaps|person|plant|player|point|policy|political|poor|population|possible|prepare|president|pretty|price|probably|product|provide|put|quality|question|quickly|radio|raise|range|rate|read|real|reality|realize|reason|recent|recognize|region|relationship|religious|remain|result|rich|run|safe|save|say|science|season|see|seem|sell|send|senior|sense|series|serious|service|set|side|single|size|small|society|some|sometimes|son|sort|south|speech|sport|staff|still|stop|store|subject|suffer|support|table|take|task|technology|than|the|their|think|third|threat|three|throughout|throw|total|trade|training|treatment|trip|understand|until|upon|use|very|view|visit|voice|vote|wait|watch|we|what|whatever|when|where|whether|whole|whom|why|wind|window|within|word|world|write|year|your)}' unique.txt
```

Which returned:

```
he2023{address_type_each_car}
he2023{early_race_very_outside}
he2023{education_exactly_market_cup}
he2023{enjoy_case_blood_health}
he2023{language_front_significant_their}
he2023{last_kitchen_dog_action}
he2023{or_event_part_rich}
he2023{practice_animal_account_enough}
he2023{production_within_science_quality}
he2023{represent_pretty_according_window}
he2023{school_stop_lot_break}
he2023{staff_hope_child_amount}
he2023{theory_they_off_when}
he2023{together_end_five_possible}
he2023{wall_leg_require_point}
```

### How about repeated initial letters?

Nothing super promising here:

```bash
$ cat unique.txt| egrep '\{(.).*_\1.*_\1'
he2023{among_American_author_agreement}
he2023{cell_course_church_heavy}
he2023{customer_concern_candidate_parent}
he2023{huge_man_hit_half}
he2023{nearly_never_best_newspaper}
he2023{several_street_somebody_represent}
he2023{six_involve_seat_someone}
he2023{sound_simply_wish_see}
he2023{specific_simple_service_two}
he2023{state_laugh_simply_sense}
he2023{study_strong_do_step}
```

### Short? Long?

Nope, nothing unique of either shortest or longest

```
$ cat unique.txt|awk '{print $0, length($0)}' | sort -nk2 | grep 25
he2023{I_nearly_book_bar} 25
he2023{air_bar_beat_note} 25
he2023{fall_still_TV_the} 25
he2023{fine_step_so_mind} 25
he2023{he_draw_yet_owner} 25
he2023{huge_man_hit_half} 25
he2023{in_how_open_treat} 25
he2023{left_how_tree_big} 25
he2023{most_you_cell_nor} 25
he2023{senior_a_nor_meet} 25
he2023{to_or_smile_civil} 25
he2023{top_but_left_then} 25
...
he2023{security_first_easy_catch} 33 # only one 33 long
...
he2023{catch_interesting_above_development} 43
he2023{decade_visit_responsibility_station} 43
he2023{describe_picture_their_organization} 43
he2023{different_product_environment_price} 43
he2023{international_make_board_individual} 43
he2023{local_different_wide_administration} 43
he2023{political_service_generation_career} 43
he2023{politics_democratic_support_between} 43
```

### Tried flags

```
he2023{worker_sister_everybody_next}    # No: Had two unique words
he2023{become_attorney_media_become}    # No: two extremely non-unique words.
he2023{memory_service_Mr_activity}      # No: (only use of a unique capitalised word)
he2023{my_PM_whole_part}                # No: re-used letters from left to right.

he2023{security_first_easy_catch}       # ??: Only one 33 long
he2023{among_American_author_agreement} # ??: All beginning with A

```

### Solution

Alright, here it is as a one liner:

```
cat writeupfiles/singular/singular.txt | sort | uniq -c | grep ' 1 ' | awk '{print length($0), $0}' | grep $(cat writeupfiles/singular/singular.txt | sort | uniq -c | grep ' 1 ' | awk '{print length($0)}' | sort | uniq -c | grep ' 1 ' | awk '{print $2}')
```

p gross.

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
