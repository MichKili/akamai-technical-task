@echo off
chcp 65001
cmd /c start "" /wait bash -ic "export BROWSER='/mnt/c/Program Files/Google/Chrome/Application/chrome.exe' && cd '/mnt/c/Users/MichaÅ‚/IdeaProjects/giftcards-service/docker' && ./run_docker.sh %1 %2"

::echo and chcp is used to properly write polish sign like ³ 
:: cmd /c - > przekazuje comendy do cmd.exe i wtedy zamyka siê okno
:: start "" /wait -> tworzy nowe okno które czeka /wait na wykonanie siê ca³ego zadania i wspó³pracuj¹c z /c zamyka dopiero wtedy siê nowo otwarte okno.
:: bash -ic -> odpala interaktywny bash from wsl który wykonywaæ mo¿e wszelkie komendy (c) np scrypty sh, interaktywny oznacza ¿e coœ tam z profilami jest lepiej ustawione, np ¿e bash pobiera dane meta, jakieœ ustawienia z Windowsa czy coœ takiego
:: export ... wyeksrptowuje browser jako zmienna systemowa aby wiadomo by³o jaka przegl¹darka ma siê wywo³aæ podczas wywo³ywania skryptu run_dicker.sh
:: ./run_docker.sh %1 %2 wywo³anie skryptu z dwoma mo¿liwymi argumentami które mo¿emy pdoaæ na wejœcie do tego algorytmu.