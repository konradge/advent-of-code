import argparse
import importlib
import os.path
import shutil
import time


def prepareFiles(day):
    # chceck if file exists
    if not os.path.isfile("inputs/day" + str(day)):
        # if not, create it
        with open("inputs/day" + str(day), "w") as f:
            f.write("")

    if not os.path.isfile("solutions/day" + str(day) + ".py"):
        shutil.copy("solutions/day_template.py", "solutions/day" + str(day) + ".py")


# Instantiate the parser
parser = argparse.ArgumentParser(description="Optional app description")

parser.add_argument("day", type=int, help="The day to be run")
parser.add_argument(
    "-v", "--verbose", action="store_true", help="Pretty print the output"
)


d = parser.parse_args().day

verbose = parser.parse_args().verbose

prepareFiles(d)

day = importlib.import_module("solutions.day" + str(d))


# read content of day1.txt
with open("inputs/day" + str(d)) as f:
    content = f.readlines()
    if verbose:
        print("----- Day %d Part 1 -----" % d)
    start_time = time.time()
    res = day.part1("".join(content))
    runtime = time.time() - start_time
    print("\t" + res)
    if verbose:
        print("Runtime: %s ms" % (runtime * 10e6))
    print("\n\n")
    if verbose:
        print("----- Day %d Part 2 -----" % d)
    start_time = time.time()
    res = day.part2("".join(content))
    runtime = time.time() - start_time
    print("\t" + res)
    if verbose:
        print("Runtime: %s ms" % (runtime * 10e6))
