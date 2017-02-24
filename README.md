
# Snake Game
[![Code Climate](https://codeclimate.com/github/SST-CTF/snake/badges/gpa.svg)](https://codeclimate.com/github/SST-CTF/snake)
[![codebeat badge](https://codebeat.co/badges/8506277e-c116-4354-84d6-800ed1665f44)](https://codebeat.co/projects/github-com-sst-ctf-snake)

**Version:** 1.0.0

**Authors:** Andrew Quach and Stanislav Lyakhov

## Introduction

Snake game is a java implementation of the classic game Snake.

The game was made as a project for Computer Science A at SST.

## Usage

In order to play the program, either build the jar from the
given source or run the pre-included jar file. 

Setting up the local leaderboard system requires an installation of MySQL.

    $ java -jar Snake.jar

##### Database Setup:
Install [MySQL](https://dev.mysql.com/downloads/mysql/) (use default settings if unsure).
In the MySQL Command Line Client, run the following:

    CREATE DATABASE game DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
    CREATE USER 'guest'@'localhost' IDENTIFIED BY 'guest';
    GRANT ALL ON game.* TO 'guest'@'localhost' IDENTIFIED BY 'guest';
    USE game;
    CREATE TABLE leaderboards (name VARCHAR(3), score INT);

## License 
Snake Game is released under the [MIT License](LICENSE).


## Contact
Contact the developers at:
    
* andrew@sstctf.org

* stan@sstctf.org
