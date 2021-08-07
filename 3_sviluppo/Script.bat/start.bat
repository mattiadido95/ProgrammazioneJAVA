@echo off
@echo Script bat per la compilazione progetto
cd "src\server"
C:\prg\jdk8\bin\javac -classpath ".;c:\prg\libs\*"  *.java
start cmd /k C:\prg\jdk8\bin\java -classpath ".;c:\prg\libs\*" ServerDiLog

cd "..\client"
C:\prg\jdk8\bin\javac -classpath ".;c:\prg\libs\*" *.java
start cmd /k C:\prg\jdk8\bin\java -classpath ".;c:\prg\libs\*" RegistroSpese

pause