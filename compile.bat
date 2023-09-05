@echo off
set CLASSPATH=lib\lwjgl\*;bin
set SRCPATH=src\com\vex

echo %CLASSPATH%

rem Compile
mkdir bin 2>NUL

for /r %SRCPATH% %%f in (*.java) do (
    javac -d bin -cp %CLASSPATH% "%%f"
)

echo Compilation complete.