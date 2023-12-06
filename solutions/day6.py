import math


def getPossibilities(time, dist):
    x = math.sqrt(math.pow(time / 2, 2) - dist)
    lower = math.floor(time / 2 - x + 1)
    upper = math.ceil(time / 2 + x - 1)
    return upper - lower + 1


def part1(input: str) -> str:
    lines = [[int(y) for y in x.split()[1:]] for x in input.splitlines()]
    res = 1
    for i in range(len(lines[0])):
        time = lines[0][i]
        dist = lines[1][i]

        res *= getPossibilities(time, dist)
    return str(res)


def part2(input: str) -> str:
    lines = [int("".join(x.split()[1:])) for x in input.splitlines()]
    return str(getPossibilities(lines[0], lines[1]))
