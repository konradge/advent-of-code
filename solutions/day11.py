import math


def getGalaxiesCoordinates(input: str, expansion: int):
    galaxyMap = []
    for line in input.splitlines():
        galaxyLine = []
        for x in list(line):
            galaxyLine.append(x)
        galaxyMap.append(galaxyLine)

    rowsWithoutSpring = []
    for i in range(len(galaxyMap)):
        hasSpring = False
        for j in range(len(galaxyMap[i])):
            if galaxyMap[i][j] == "#":
                hasSpring = True
                break
        if not hasSpring:
            rowsWithoutSpring.append(i)

    colsWithoutSpring = []
    for j in range(len(galaxyMap[0])):
        hasSpring = False
        for i in range(len(galaxyMap)):
            if galaxyMap[i][j] == "#":
                hasSpring = True
                break
        if not hasSpring:
            colsWithoutSpring.append(j)

    galaxies: list[tuple[int, int]] = []
    for i in range(len(galaxyMap)):
        deltaI = len([x for x in rowsWithoutSpring if x < i]) * (expansion - 1)
        for j in range(len(galaxyMap[i])):
            deltaJ = len([x for x in colsWithoutSpring if x < j]) * (expansion - 1)
            if galaxyMap[i][j] == "#":
                galaxies.append((i + deltaI, j + deltaJ))

    return galaxies


def getDistances(springs: list[tuple[int, int]]):
    res = 0
    for i in range(len(springs)):
        for j in range(len(springs)):
            if i != j:
                x1, y1 = springs[i]
                x2, y2 = springs[j]

                dist = abs(x1 - x2) + abs(y1 - y2)

                res += dist
    return math.floor(res / 2)


def part1(input: str) -> str:
    return str(getDistances(getGalaxiesCoordinates(input, 2)))


def part2(input: str) -> str:
    return str(getDistances(getGalaxiesCoordinates(input, 1000000)))
