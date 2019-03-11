rem start run test cases in command line

cd /d %~dp0

set eloqua_wechat_web_test_home=%~dp0\..\..\testapp_web_test


set report_template_folder_src=%eloqua_wechat_web_test_home%\cli\report_template
set report_template_folder_target=%eloqua_wechat_web_test_home%\target\jbehave\view\

set report_file=%eloqua_wechat_web_test_home%\target\jbehave\view\reports.html


xcopy /e /R /C /Y %report_template_folder_src% %report_template_folder_target%


start %report_file%