#Abstract 

This repository contains the scala code for a small lottery project.
It will read a winning ticket and participating tickets and calculates the amount of winning tickets for each winning class.

The tickets can be found in `winning-ticket.txt` and `tickets.txt` for the winning ticket and all participating tickets.

All settings can be manipulated through the options, e.g. 100 numbers instead of 50 and so on.

It may not be the best scala code but I think its quite well done for this exercise.

# Code

The code is documented with javadoc as well as line comments and consists of only three classes.

## Ticket

A `Ticket` consists of two `Sets`, the first one for the normal numbers and the second one for the star numbers.
I am not differentiating between system and normal tickets as the `Set` can hold up to the max amount of numbers.
However, each `Ticket` has a method where you can get a list of all possible combinations for this `Ticket`, if the `Ticket` is a normal one, then just one combination will be returned.
 
As the number of combination can not exceed 2520 (in this exercise) I did not implement a `Future` return. THis could be implemented if needed.

I also added a few methods to parse the `Ticket` to a text representation and back and a method to generate random `Tickets`.
 
## Lottery

The `Lottery` is responsible to take a winning `Ticket` and will simulate a lottery with the given ticket.
It will read the participating tickets from a file and evaluates them in a `Future`. It will then update statistics

## Main

The main class starts the program. It will create the tickets if they are not available yet and then starts the process.
For all combinations of the winning `Ticket` a new `Lottery` is created and all participating tickets will be evaluated for this one combination and it will wait on all futures.
In the end it will print the time taken and the results of the draw.

The `Main` class also contained a programm that will list all combinations of tickets read from a file, but atm it is not active.

# Tests

To start the tests just run 

> sbt test

# Options

I used the [https://github.com/typesafehub/config](Typesafe Config) for parsing and managing the options in the application, so everything is configurable.
The default config looks like this:
```hocon
lottery {
  number {
    normal {
      max = 50
      min = 1
    }
    star {
      max = 12
      min = 1
    }
  }
  winningTicket = "winning-ticket.txt"
  participatingTickets = "tickets.txt"
  ticket {
    numbers {
      normal {
        max = 10
        min = 5
      }
      star {
        max = 5
        min = 2
      }
    }
  }
}
```

Available options are:
`-Dlottery.number.normal.max=50` -> highest normal number possible in a ticket
`-Dlottery.number.normal.min=1` -> lowest normal number possible in a ticket
`-Dlottery.number.star.max=12` -> highest star number possible in a ticket 
`-Dlottery.number.star.min=1` -> lowest star number possible in a ticket
`-Dlottery.winningTicket=winning-ticket.txt` -> file name of the winning ticket
`-Dlottery.participatingTickets=tickets.txt` -> file name of all participating tickets
`-Dlottery.ticket.numbers.normal.max=10` -> max amount of normal numbers possible in a ticket
`-Dlottery.ticket.numbers.normal.min=5` -> min amount of normal numbers possible in a ticket
`-Dlottery.ticket.numbers.star.max=5` -> max amount of star numbers possible in a ticket
`-Dlottery.ticket.numbers.star.min=2` -> min amount of star numbers possible in a ticket

# Running the application

To run the application just type 
> sbt run

or download the file from [https://github.com/Chumper/lottery/releases](here) all options are listed there.

It will output something like this:
```bash
===========================
Calculation took 1275 ms.
Winning ticket: 5,13,2,31,19:6,10
Winning class 1 has 0 winner!
Winning class 2 has 0 winner!
Winning class 3 has 0 winner!
Winning class 4 has 0 winner!
Winning class 5 has 54 winner!
Winning class 6 has 222 winner!
Winning class 7 has 278 winner!
Winning class 8 has 4170 winner!
Winning class 9 has 5187 winner!
Winning class 10 has 11953 winner!
Winning class 11 has 20497 winner!
Winning class 12 has 82436 winner!
Winning class 13 has 186391 winner!
Total number of tickets: 3761044
===========================
```
