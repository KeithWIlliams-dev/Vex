@echo off
set CLASSPATH=lib\lwjgl\*;bin
set SRCPATH=src\com\vex\*.java
echo %CLASSPATH%

rem Compile
mkdir bin 2>NUL
javac -d bin -cp %CLASSPATH% %SRCPATH%

echo Compilation complete.
