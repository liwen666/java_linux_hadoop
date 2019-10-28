#!/bin/bash
for file in `find /usr/src/linux/ -name Kconfig.*`
do
vi +':w ++ff=unix' +':q' ${file}
done
