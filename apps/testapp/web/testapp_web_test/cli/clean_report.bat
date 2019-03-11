rem start run test cases in command line

cd /d %~dp0

set eloqua_wechat_web_test_home=%~dp0\..\..\testapp_web_test

set report_folder=%eloqua_wechat_web_test_home%\target\jbehave
set report_view_folder=%eloqua_wechat_web_test_home%\target\jbehave\view

rem ***delete report files**
cd %report_folder%
call del *.* /F /Q

rem **delete view files**
cd %report_view_folder%
call del *.* /F /Q