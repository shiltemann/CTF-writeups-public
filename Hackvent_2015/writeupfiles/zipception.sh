#!/bin/bash


level=1

while [[ $level -lt 31337 ]]
do
unzip -q -o -d  ${level} "${level}.zip"
nextlevel=$[$level+1]
cp "${level}/${nextlevel}.zip" .
rm -R ${level}
rm "${level}.zip"

level=$nextlevel


done 
