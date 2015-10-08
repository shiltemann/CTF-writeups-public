#!/bin/bash


for i in qrcodes/*
do
  zbarimg $i 2>&1

done
