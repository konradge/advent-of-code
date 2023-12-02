import numpy as np
import re

allowed = {"red": 12, "green": 13, "blue": 14}


def solve(input):
    possibles = 0
    power = 0
    for i, game in enumerate(input.split("\n")):
        rounds = map(lambda x: x.split(", "), game.split(": ")[1].split("; "))
        maximums = {"red": 0, "green": 0, "blue": 0}
        for round in rounds:
            for pick in round:
                color = pick.split(" ")[1]
                count = int(pick.split(" ")[0])
                maximums[color] = max(maximums[color], count)

        power += np.prod([maximums[x] for x in maximums])
        possibles += int(all([maximums[x] <= allowed[x] for x in maximums])) * (i + 1)
    return str(possibles), str(power)


def solveWithRegex(input):
    possibles = 0
    power = 0
    for i, game in enumerate(input.split("\n")):
        red = max([int(x) for x in re.findall(r"(\d+) red", game)])
        blue = max([int(x) for x in re.findall(r"(\d+) blue", game)])
        green = max([int(x) for x in re.findall(r"(\d+) green", game)])
        if red <= 12 and blue <= 14 and green <= 13:
            possibles += i + 1
        power += red * blue * green
    return str(possibles), str(power)


def part1(input):
    possibles, _ = solveWithRegex(input)
    return possibles


def part2(input):
    _, power = solveWithRegex(input)
    return power
