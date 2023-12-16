# Repository for advent-of-code 2023

Here are my solutions to [Advent of Code 2023](https://adventofcode.com/2023) written in python.

## Prepare

To prepare all files for one day, run

```sh
python3 main.py <day> --prepare [--verbose]
```

This will prepare all files needed for the specified day. If a `COOKIE` is provided in the .env-File, the input for this day will also be automatically fetched.

## Run

The solution for one day can be run using:

```sh
python3 main.py <day> [--verbose --example]
```

where \<day\> is the number of the day for which the solution shall be run.
Optionally set `--verbose` to get the runtime of the solution alongside some more information.
Setting the flag `--example` runs the solution against the input provided in `input/day<day>_example`

## Disclaimer

This repository does follow the automation guidelines on the [/r/adventofcode community wiki](https://www.reddit.com/r/adventofcode/wiki/faqs/automation):

- Outbound calls are throttled to every 3 minutes by using python's `ratelimit` module in `utils.aoc.checkLimit`
- Once inputs are downloaded with the `-p`-flag, they are written into a file `inputs/dayx` and never fetched again, unless the file is deleted
- The User-Agent header in `utils.aoc.loadInput` is set to me since I maintain this tool
