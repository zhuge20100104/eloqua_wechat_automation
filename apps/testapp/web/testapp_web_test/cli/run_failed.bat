rem start run test cases in command line

cd /d %~dp0

set testapp-web-tests=%~dp0\..\..\testapp-web-tests
set show_report_bat=%testapp-web-tests%\ci\show_report.bat
set clean_report_bat=%testapp-web-tests%\ci\clean_report.bat

rem ************************************
rem    overall re-run from script level
rem ************************************

rem ***setup to only run failed cases
set ignore.story.list.and.use.failed=true

rem **build testapp-web-tests and run it**
cd %testapp-web-tests%
call mvn -Dtest=com.oracle.testlaunchers.AcceptanceTestSuites test

rem ***unset variable
set ignore.story.list.and.use.failed=
