#!/bin/bash
mkdir ovito
cd ovito
wget https://www.ovito.org/download/master/ovito-basic-3.4.3-x86_64.tar.xz
tar -x -f *
rm *.xz
mv * ovitoFolder
mv ovitoFolder/* .
mv ovitoFolder/.* .
rmdir ovitoFolder