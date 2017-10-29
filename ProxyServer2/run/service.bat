@echo off 
setlocal 

set SERVICE_NAME=SoccarProxy 
set PR_INSTALL=%~dp0%prunsrv.exe 
set PR_DESCRIPTION="Proxy Server Service for Soccar" 

REM Service log configuration 
set PR_LOGPREFIX=%SERVICE_NAME% 
set PR_LOGPATH=%~dp0%\ 
set PR_STDOUTPUT=%~dp0%\log\stdout.txt 
set PR_STDERROR=%~dp0%\log\stderr.txt 
set PR_LOGLEVEL=Debug   

REM Path to java installation 
set PR_JVM=%JAVA_HOME%\jre\bin\server\jvm.dll 
set PR_CLASSPATH=%SOCCAR_HOME%\pt-ar-game-3\ProxyServer2\server\server-impl\target\server-impl-1.0-SNAPSHOT-jar-with-dependencies.jar   

REM Startup configuration 
set PR_STARTUP=auto 
set PR_STARTMODE=jvm 
set PR_STARTCLASS=com.playtech.ptargame3.server.Starter 
set PR_STARTMETHOD=start

REM Shutdown configuration 
set PR_STOPMODE=jvm 
set PR_STOPCLASS=com.playtech.ptargame3.server.Starter 
set PR_STOPMETHOD=stop
set PR_STOPTIMEOUT=120   

REM JVM configuration 
set PR_JVMMS=32 
set PR_JVMMX=256
rem set PR_JVMSS=4000 

REM JVM options 
set PR_JVMOPTIONS=-Djava.util.logging.config.file=conf/logging.properties -XX:+UseG1GC

REM current file 
set "SELF=%~dp0%service.bat" 

REM current directory 
set "CURRENT_DIR=%cd%"   

REM start - This takes the input from installService and places it between x's 
REM       - if there are none then you get xx as a null check 
if "x%1x" == "xx" goto displayUsage 
set SERVICE_CMD=%1 
REM ahift moves to next field 
shift 
if "x%1x" == "xx" goto checkServiceCmd 
:checkServiceCmd 
if /i %SERVICE_CMD% == install goto doInstall 
if /i %SERVICE_CMD% == remove goto doRemove 
if /i %SERVICE_CMD% == uninstall goto doRemove 
echo Unknown parameter "%SERVICE_CMD%" 
:displayUsage 
echo. 
echo Usage: service.bat install/remove 
goto end 
:doRemove 
echo Removing the service '%PR_INSTALL%' '%SERVICE_NAME%' ... 
%PR_INSTALL% //DS//%SERVICE_NAME% 
if not errorlevel 1 goto removed 
echo Failed removing '%SERVICE_NAME%' service 
goto end 
:removed 
echo The service '%SERVICE_NAME%' has been removed 
goto end 
:doInstall 
echo Installing the service '%PR_INSTALL%' '%SERVICE_NAME%' ... 
%PR_INSTALL% //IS//%SERVICE_NAME% 
goto end 
:end 
echo Exiting service.bat ... 
cd "%CURRENT_DIR%"