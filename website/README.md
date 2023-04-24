# Galaxians writeups website

## Writeups folder

The folder structure
```
writeups
├── EventTitle
│   ├── writeupfiles
│   │   ├── file1.py
│   │   ├── file2.txt
│   │   ├── file3.png
│   ├── index.md
│   ├── challenge1-title-of-chall.md
│   ├── challenge2-title-of-chall.md
│   ├── challenge3-title.md
├── AnotherEventTitle
│   ├── index.md
... ...
```

## Adding writeup
Writeups in the `writeups` folder, in a subfolder per event

To create an empty writup with frontmatter etc: `./bin/create-writeup.sh <filename>`

Writeups will appear in alphabetical order of filename, so use a namingscheme like `challenge1-title-of-challege.md`, orso

## Adding event
To add a new event, create a folder in `writeups` with the event name.

Add an `index.md` file:

```markdown

---
layout: ctf-event
title: Title of Event
date: 2042-04-02
---

Some description of the event

```


