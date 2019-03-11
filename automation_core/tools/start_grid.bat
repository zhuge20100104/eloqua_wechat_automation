cd /d %~dp0

set all_root_folder=%~dp0\..\..
echo  Stop Exisitng Selenium Servers
set stop_bat=%~dp0\selenium_grid_stop.bat

rem stop selenium server
call %stop_bat%

echo Unmount All Drives
rem unmount drive X:
subst x: /D
vsubst x: /D

echo Mount All Drives
rem attach drive X:
subst x: "%all_root_folder%"
vsubst x: "%all_root_folder%"

set file=%~dp0\selenium-server-standalone.jar

echo Start Selenium Server as a HUB
start java -jar %file% -port 4000 -role hub -maxSession=40 -newSessionWaitTimeout=1000 -browserTimeout=60

echo Wait for 5 seconds
rem need to wait for 5 seconds or else multiple session of nodes are duplicated.
ping 127.0.0.1 -n 5