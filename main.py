import argparse
import importlib
import utils.aoc as aoc


import utils.files as files


def prepareFiles(day, verbose):
    # check if file exists
    createdInputFile = files.createEmpty("inputs/day" + str(day), verbose)

    if createdInputFile:
        input = aoc.loadInput(day, verbose)
        files.write("inputs/day" + str(day), input, verbose)

    files.createEmpty("inputs/day" + str(day) + "_example", verbose)

    # files.copy("tests/test_template.py", "tests/test_" + str(day) + ".py", verbose)


def runSolution(dayNumber, verbose, example):
    day = importlib.import_module("solutions.day" + str(dayNumber))

    # read content of day1.txt
    content = files.read(
        "inputs/day" + str(dayNumber) + ("_example" if example else ""), verbose
    )
    if verbose:
        print("----- Day %d, Part 1 -----" % dayNumber)
    res, runtime = aoc.timeSolution(day.part1, content)
    print(res)
    if verbose:
        print("Part 1 finished in %s ms" % (runtime * 10e6))
    print("\n\n")
    if verbose:
        print("----- Day %d, Part 2 -----" % dayNumber)
    res, runtime = aoc.timeSolution(day.part2, content)
    print(res)
    if verbose:
        print("Part 2 finished in %s ms" % (runtime * 10e6))


# Instantiate the parser
parser = argparse.ArgumentParser(description="Optional app description")

parser.add_argument("day", type=int, help="The day to be run")
parser.add_argument(
    "-v", "--verbose", action="store_true", help="Pretty print the output"
)
parser.add_argument(
    "-p",
    "--prepare",
    action="store_true",
    help="Download the input file, add an example-input file and create a file for the solution alongside a test-file",
)

# parser.add_argument(
#     "-t",
#     "--test",
#     action="store_true",
#     help="Run the tests for the specified day",
# )

parser.add_argument(
    "-e",
    "--example",
    action="store_true",
    help="Run the tests on the example-input",
)

dayNumber = parser.parse_args().day

verbose = parser.parse_args().verbose

prepare = parser.parse_args().prepare

# test = parser.parse_args().test

example = parser.parse_args().example

if prepare and example:
    raise RuntimeError("Cannot prepare and run example at the same time")

if prepare:
    prepareFiles(dayNumber, verbose)
else:
    runSolution(dayNumber, verbose, example)
