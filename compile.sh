#!/bin/bash

cd src
javac -cp ../lib/jxl.jar -target 1.5 *.java WhoHasMore/*.java
jar -cvfm QTSI.jar whohasmore.MF *.class WhoHasMore/*.class
cd ..
mv src/QTSI.jar .
