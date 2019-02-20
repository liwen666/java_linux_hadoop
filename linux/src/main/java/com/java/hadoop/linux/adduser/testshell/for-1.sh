#!/bin/bash

for((i=1;i<=10;i++));
do
echo $(expr $i \* 3 + 1);
done
echo "---------------------------";
for i in $(seq 1 10)
do
echo $(expr $i \* 3 + 1);
done
echo "---------------------------";
for i in {1..10}
do
echo $(expr $i \* 3 + 1);
done
echo "---------------------------";
awk 'BEGIN{for(i=1; i<=10; i++) print i}'