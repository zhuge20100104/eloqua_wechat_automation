rem start run test cases in command line

cd /d %~dp0

rem set script path
set automation_core=%~dp0..\..\..\..\..\automation_core
set app_commons=%~dp0..\..\..\..\app_commons
set testapp_commons_core=%~dp0..\..\..\testapp_commons_core
set testapp-mobile-core=%~dp0..\..\..\mobile\testapp_mobile_core
set testapp_web_core=%~dp0..\..\testapp_web_core
set testapp_web_test=%~dp0..\..\testapp_web_test

rem ** move report to temporary folder, then move back
set report_folder=%testapp_web_test%\target\jbehave
set report_folder_temp=%testapp_web_core%\target\jbehave-report-%random%
xcopy /e /R /C /Y %report_folder% %report_folder_temp%\

rem ** clean project targe and report
rem cd %eloqua_web_test_home%
rem call mvn clean

rem ** move back
xcopy /e /R /C /Y %report_folder_temp% %report_folder%\

rem ** clean other projects

cd %automation_core%
call mvn clean
cd %app_commons%
call mvn clean
cd %testapp_commons_core%
call mvn clean
cd %testapp-mobile-core%
call mvn clean
cd %testapp_web_core%
call mvn clean
cd %testapp_web_test%
call mvn clean

cd %testapp_web_test%\ci
