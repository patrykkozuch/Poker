# Poker (IN PROGRESS)

Simple poker game for 4 players for programming classes. Project is configured with use of Maven.

## Stack
* Java
* JUnit
* Javadoc
* Maven (and its dependencies)
* SonarQube
* little of Design Patterns:
  * actions are classes derivied from abstract `Action` class
  * actions are parsed and created by abstract factory

## Features
* client-server architecture
* 1-4 players in single game lobby
* games can be played simultaneously
* **77,1% test coverage**

### Implemented rules and functionalities
  * poker hand evaluation
  * tie braking (if possible)
  * ALL-IN and side pools
  * betting (raising, checking, folding, calling)
  * changing cards (0-5)
 
## USAGE
This game requires two modules - server and client.
First, server needs to be run:
```
java -jar -D"file.encoding"="UTF-8" "oker-server.jar"
```
Then, every single client should be run using:
```
java -jar -D"file.encoding"="UTF-8" "poker-client.jar"
```
 
## Server Commands
All commands are case insensitive. Can be written both lower- and uppercase.

### `HELP`
Shows list of available commands. Can be used only when not in game.

### `CREATE <ante>`
Creates game with provided ante (integer). Ante is a amount, which u need to put in the pool at the beginning of the game. ID of created game is returned as a response. Can be used only when not in lobby.

### `LIST`
Shows list of all available games with details like ante, players count in lobby and current status. Can be used only when not in game.

### `JOIN <gameID`
Player joins game with provided ID. If the player is the only one, who is in the lobby, they become a 'host'. Host can start the game whenever they want.
Can be used only when not in lobby.

### `START`
Starts the game. Can be used only by host in lobby.

### `QUIT`
Quits current lobby. Can be used only before game starts. There is no returning back after starting.

### `BALANCE`
Shows current balance of player account. Balance is drawn randomly for player on server join.

## In-game commands
### `RAISE <amount>`
Raises current bet **TO** (not **BY**) provided amount (integer). For example, if player bet 30 before, `RAISE 50` will increase bet by 20.
* Amount cannot be lower than any amount provided by other players
* Amount cannot exceed player's balance

### `CALL`
Raises current bet to maximal bet done by other player. Cannot be used if player has to little money.

### `CHECK`
Allows player to wait until other player will bet. Cannot be used if bet was already done.

### `FOLD`
Allows player to retreat. After that, player is not count as an active, so they cannot win any prize.

### `ALLIN`
Allows player to put all the money in. Player can win maximal prize equals to theirs bet from every player. After betting ALLIN players stays active until games end.

### `CHANGE <card numbers>` or `CHANGE 0`
Change 0 allows player to skip card changing. In other cases player can change up to 5 cards. For example, `CHANGE 2 3 4` means player wants to change card number 2, 3 and 4.
