# Poker - Patryk Kożuch

Projekt zawiera zaimplementowaną pełną rozgrywkę pokerową, w tym zasady takie jak:

* Sprawdzanie starszeństwa układów
* Porównywanie układów o tym samym starszeństwie
* Rozstrzyganie remisów (w tym dopuszczalny remis oraz podział nagrody)
* Zasady ALL IN (pule poboczne)
* Wprowadzanie ante podczas rozpoczęcia rozgrywki
* Licytacje
* Wymiana kart

Dodatkowo, w projekcie znajdują się serwer oraz klient, które umożliwiają płynną rozgrywkę oraz funkcjonalności takie
jak:

* Tworzenie nowych gier
* Listowanie gier
* Dołączanie do/opuszczanie gier
* Wyświetlanie pomocy
* Wyświetlanie stanu konta

# Przebieg rozgrywki

1. Gracz tworzy rozgrywkę poleceniem
2. Gracze dołączają do stworzonej rozgrywki
3. Host gry rozpoczyna rozgrywkę
4. Następuje faza licytacji (gracze licytują dopóki wszyscy nie zalicytują lub nie spasują)
5. Następuje wymiana kart (0-5)
6. Następuje ponowna licytacji
7. Rozdzielane są nagrody
8. Gracze wracają do lobby

# Dostępne polecenia

## Polecenia serwerowe

* CREATE <ante>
  * Tworzy nową grę o podanym ante.
  * W odpowiedzi od serwera otrzymuje ID utworzonej gry.
* LIST
    * Wyświetla listę wszystkich gier dostępnych na serwerze wraz z wysokością ANTE, ilością graczy w lobby oraz
      statusem
* JOIN <id_gry>
    * Pozwala dołączyć do gry o podanym ID. Jeżeli gracz jest jedynym, który dołączył do lobby staje się hostem. Host
      może rozpocząć rozgrywkę w dowolnym momencie.
* START
    * Rozpoczyna rozgrywkę
* QUIT
    * Pozwala opuścić rozgrywkę przed jej rozpoczęciem lub po jej zakończeniu
* HELP
    * Wyświetla dostępne polecenia
* BALANCE
    * Wyświetla aktualny stan konta gracza

## Polecenia w trakcie rozgrywki

* RAISE <kwota>
    * Wyrównuje do podanej kwoty. Kwota powinna być liczbą całkowitą.
    * Nie może być niższa niż najwyższa kwota dołożona do puli.
    * Nie może być wyższa niż aktualny stan konta gracza.
* CALL
    * Wyrównuje do kwoty obstawionej przez innych graczy
    * Nie można dorównać, jeżeli gracz nie posiada wystarczającej ilości pieniędzy
* CHECK
    * Pozwala wstrzymać się z obstawianiem do momentu obstawienia przez innych graczy
    * Nie można wstrzymać się, jeżeli ktoś już obstawił
* FOLD
    * Pasowanie
* ALLIN
    * Wejście za wszystko.
    * Pozwala wygrać od każdego gracza maksymalnie tyle, za ile się weszło.
* CHANGE 0 lub CHANGE <numery_kart>
    * Pozwala wymienić karty w fazie wymiany
    * CHANGE 0 oznacza rezygnację z wymiany
    * <numer_karty> powinien być liczbą całkowitą z zakresu 1 do 5

# Uruchomienie programu

Będąc w katalogu głównym należy najpierw uruchomić serwer:

java -jar -D"file.encoding"="UTF-8" "poker-server/target/poker-server.jar"

A następnie klienta:

java -jar -D"file.encoding"="UTF-8" "poker-client/target/poker-client.jar"
