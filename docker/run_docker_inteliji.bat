@echo off
chcp 65001
cmd /c start "" /wait bash -ic "export BROWSER='/mnt/c/Program Files/Google/Chrome/Application/chrome.exe' && cd '/mnt/c/Users/Michał/IdeaProjects/giftcards-service/docker' && ./run_docker.sh %1 %2"

::echo and chcp is used to properly write polish sign like ł
:: cmd /c - > przekazuje comendy do cmd.exe i wtedy zamyka się okno
:: start "" /wait -> tworzy nowe okno które czeka /wait na wykonanie się całego zadania i wspópracując z /c zamyka dopiero wtedy się nowo otwarte okno.
:: bash -ic -> odpala interaktywny bash from wsl który wykonywać może wszelkie komendy (c) np scrypty sh, interaktywny oznacza że coś tam z profilami jest lepiej ustawione, np że bash pobiera dane meta, jakieś ustawienia z Windowsa czy coś takiego
:: export ... wyeksrptowuje browser jako zmienna systemowa aby wiadomo było jaka przeglądarka ma się wywołać podczas wywoływania skryptu run_dicker.sh
:: ./run_docker.sh %1 %2 wywołanie skryptu z dwoma możliwymi argumentami które możemy podać na wejście do tego algorytmu.