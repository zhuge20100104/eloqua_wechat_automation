rem port range: port_start+1 to port_start+num
set port_start=4000

rem accept first parameter as selenium instance count, by default start 6 instances
set num=%1
if not defined num (set num=3)

cd /d %~dp0

set autoqe_root_folder=%~dp0\..\..
set stop_bat=%~dp0\selenium_grid_stop.bat

rem stop selenium server

rem unmount drive X:
subst x: /D
vsubst x: /D

rem attach drive X:
subst x: "%autoqe_root_folder%"
vsubst x: "%autoqe_root_folder%"

set file=%~dp0\selenium-server-standalone.jar
set chromeDriver=%~dp0\chromedriver.exe
set ie32Driver=%~dp0\IEDriverServer32.exe
set ie64Driver=%~dp0\IEDriverServer64.exe
set geckodriver32=%~dp0\geckodriver_32.exe
set geckodriver64=%~dp0\geckodriver_64.exe
set ie_auto_saver=%~dp0\ie_download_auto_saver\IEDownloadAutoSaver.exe

rem start ie auto saver
start %ie_auto_saver%

start java -jar %file% -port 4000 -role hub

for /l %%i in (1 1 %num%) do (
call:mystart %%i %port_start%
)

exit /b

:mystart
set /a port=%1+%2

start java -Dwebdriver.gecko.driver=%geckodriver32% -Dwebdriver.ie.driver=%ie32Driver% -Dwebdriver.chrome.driver=%chromeDriver% -jar %file% -port %port% -role node -hub http://127.0.0.1:4000/grid/register

rem need to wait for 5 seconds or else multiple session of nodes are duplicated.
ping 127.0.0.1 -n 5