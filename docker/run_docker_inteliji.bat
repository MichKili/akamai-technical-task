@echo off
chcp 65001
cmd /c start "" /wait bash -ic "export BROWSER='/mnt/c/Program Files/Google/Chrome/Application/chrome.exe' && cd '/mnt/c/Users/Michał/IdeaProjects/giftcards-service/docker' && ./run_docker.sh %1 %2"

::echo and chcp is used to properly write polish sign like � 
:: cmd /c - > przekazuje comendy do cmd.exe i wtedy zamyka si� okno
:: start "" /wait -> tworzy nowe okno kt�re czeka /wait na wykonanie si� ca�ego zadania i wsp�pracuj�c z /c zamyka dopiero wtedy si� nowo otwarte okno.
:: bash -ic -> odpala interaktywny bash from wsl kt�ry wykonywa� mo�e wszelkie komendy (c) np scrypty sh, interaktywny oznacza �e co� tam z profilami jest lepiej ustawione, np �e bash pobiera dane meta, jakie� ustawienia z Windowsa czy co� takiego
:: export ... wyeksrptowuje browser jako zmienna systemowa aby wiadomo by�o jaka przegl�darka ma si� wywo�a� podczas wywo�ywania skryptu run_dicker.sh
:: ./run_docker.sh %1 %2 wywo�anie skryptu z dwoma mo�liwymi argumentami kt�re mo�emy pdoa� na wej�cie do tego algorytmu.