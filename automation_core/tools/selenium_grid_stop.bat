cd /d %~dp0

rem call stop2
set stop_bat2=%~dp0\selenium_grid_stop2.bat

set port_start=4000

rem for non-win7
FOR /F "tokens=4 delims= " %%P IN ('%windir%\system32\netstat -a -n -o ^| %windir%\system32\findstr :%port_start%') DO  %windir%\system32\taskkill.exe /f /PID %%P

rem fro win7
FOR /F "tokens=5 delims= " %%P IN ('%windir%\system32\netstat -a -n -o ^| %windir%\system32\findstr :%port_start%') DO  %windir%\system32\taskkill.exe /f /PID %%P

rem wait for 3 seconds
ping 127.0.0.1 -n 3

rem kill all web drivers.
%windir%\system32\taskkill /f /im chromedriver.exe
%windir%\system32\taskkill /f /im geckodriver_32.exe
%windir%\system32\taskkill /f /im geckodriver_64.exe
%windir%\system32\taskkill /f /im IEDriverServer32.exe
%windir%\system32\taskkill /f /im IEDriverServer64.exe

rem kill all ie autosaver
%windir%\system32\taskkill /f /im IEDownloadAutoSaver.exe
%windir%\system32\taskkill /f /im IEDownloadAutoSaver.vchost.exe

rem close all browsers
%windir%\system32\taskkill /im iexplore.exe
%windir%\system32\taskkill /im chrome.exe
%windir%\system32\taskkill /im firefox.exe

rem wait for 3 seconds
ping 127.0.0.1 -n 3

rem kill all browsers
%windir%\system32\taskkill /f /im iexplore.exe
%windir%\system32\taskkill /f /im chrome.exe
%windir%\system32\taskkill /f /im firefox.exe

rem kill node 
%windir%\system32\taskkill /f /im node.exe

rem call stop2
call %stop_bat2%
