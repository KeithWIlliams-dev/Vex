@echo off
set CLASSPATH=lib\lwjgl\*;lib\natives\windows\*;bin
set DORGLIBPATH=.\lib\natives\windows
set DJAVALIBPATH=.
set MAINCLASS=com.vex.Main

rem Run
java -Dorg.lwjgl.librarypath=%DORGLIBPATH% -Djava.library.path=%DJAVALIBPATH% -cp %CLASSPATH% %MAINCLASS%

