
cd /d %~dp0

set port_start=4000

rem for non-win7
FOR /F "tokens=4 delims= " %%P IN ('%windir%\system32\netstat -a -n -o ^| %windir%\system32\findstr :%port_start%') DO  pskill.exe /accepteula %%P

rem fro win7
FOR /F "tokens=5 delims= " %%P IN ('%windir%\system32\netstat -a -n -o ^| %windir%\system32\findstr :%port_start%') DO  pskill.exe /accepteula  %%P

rem wait for 3 seconds
ping 127.0.0.1 -n 3

rem kill all web drivers.
pskill.exe /accepteula  chromedriver.exe
pskill.exe /accepteula  IEDriverServer32.exe
pskill.exe /accepteula  IEDriverServer64.exe

rem kill all ie autosaver
pskill.exe /accepteula IEDownloadAutoSaver.exe
pskill.exe /accepteula IEDownloadAutoSaver.vchost.exe

rem close all browsers
pskill.exe /accepteula iexplore.exe
pskill.exe /accepteula chrome.exe
pskill.exe /accepteula firefox.exe
