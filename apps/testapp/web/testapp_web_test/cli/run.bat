rem start run test cases in command line

cd /d %~dp0

rem accept CI setting via System environment
set autoqe.testapp.url=%autoqe.testapp.url%
echo ** get application console url = %web.console.url%

rem set script path
set automation_core=%~dp0..\..\..\..\..\automation_core
set app_commons_home=%~dp0..\..\..\..\app_commons
set testapp-commons-core=%~dp0..\..\..\testapp_commons_core
set testapp-web-core=%~dp0..\..\testapp_web_core
set testapp-web-tests=%~dp0..\..\testapp_web_test
set testapp-mobile-core=%~dp0..\..\..\mobile\testapp_mobile_core

set selenium_start_bat=%automation_core%\tools\selenium_grid_start.bat
set selenium_stop_bat=%automation_core%\tools\selenium_grid_stop.bat

rem ***start selenium grid**
call %selenium_start_bat%

cd %automation_core%
call mvn clean install

cd %app_commons_home%
call mvn clean install

cd %testapp-commons-core%
call mvn clean install

cd %testapp-mobile-core%
call mvn clean install

cd %testapp-web-core%
call mvn clean install

cd %testapp-web-tests%
call mvn clean install

rem *** copy sotry duration prorperties ifle***
set story_duration_prop_src=%testapp-web-tests%\cli\utilities
set story_duration_prop_target=%testapp-web-tests%\target\jbehave\
xcopy /e /R /C /Y %story_duration_prop_src% %story_duration_prop_target%

rem **build testapp-web-tests and run it**
cd %testapp-web-tests%
call mvn -Dtest=com.oracle.testlaunchers.AcceptanceTestSuites test
call mvn -Dtest=com.oracle.testlaunchers.MobileTestSuites test

rem ***stop selenium grid**
call %selenium_stop_bat%

rem ** copy report template **
set report_template_folder_src=%testapp-web-tests%\cli\report_template
set report_template_folder_target=%testapp-web-tests%\target\jbehave\view\
xcopy /e /R /C /Y %report_template_folder_src% %report_template_folder_target%

