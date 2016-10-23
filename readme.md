#Abstract 

This repository contains the scala code for a small lottery project.
It will read a winning ticket and participating tickets and calculates the amount of winning tickets for each winning class.

The tickets can be found in `winning-ticket.txt` and `tickets.txt` for the winning ticket and all participating tickets.

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

# Running the application

To run the application just type 
> sbt run

or download the file from [https://github.com/Chumper/lottery/releases](here) all options are listed there.
