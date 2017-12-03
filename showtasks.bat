@echo.
@echo RunBrowser has started.
@echo.
@echo Starting RunCRUD in progress...
@echo.

@call ./runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo RUNCRUD.BAT execution failed.
goto end

:openbrowser
echo.
echo Opening Chrome in progress...
sleep 10
"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" http://localhost:8080/crud/v1/task/getTasks
echo.
echo Chrome has been opened successfully.
goto end

:end
echo.
echo RunBrowser has finished its work.