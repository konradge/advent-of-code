from collections import defaultdict
import numpy as np

gears: defaultdict[tuple[int, int], list[int]] = defaultdict()


def saveAccess(rows: list[str], i: int, j: int, di: int, dj: int):
    try:
        return [rows[i + dj][j + dj]]
    except IndexError:
        return []


def getAdjacent(rows: list[str], i: int, j: int):
    adjacent: list[tuple[str, tuple[int, int]]] = []
    for di in range(-1, 2):
        for dj in range(-1, 2):
            if i != 0 or j != 0:
                try:
                    adjacent.append((rows[i + di][j + dj], (i + di, j + dj)))
                except IndexError:
                    pass
    return adjacent


def part1(input: str):
    res = 0
    rows = str.split(input, "\n")
    for i in range(len(rows)):
        j = 0
        while j < len(rows[i]):
            end = j
            if rows[i][j].isdigit():
                start = j
                while end + 1 < len(rows[i]) and rows[i][end + 1].isdigit():
                    end += 1
                adjacent: set[tuple[str, tuple[int, int]]] = set()
                num = ""
                for k in range(start, end + 1):
                    num += rows[i][k]
                    adjacent = adjacent.union(getAdjacent(rows, i, k))
                adjacent = set(
                    [x for x in adjacent if (not x[0].isdigit()) and (x[0] != ".")]
                )
                neighbouredGears = [x for x in adjacent if x[0] == "*"]
                for gear in neighbouredGears:
                    gears.setdefault(gear[1], []).append(int(num))
                if len(adjacent) > 0:
                    res += int(num)
            j = end + 1
    return str(res)


def part2(input):
    res = 0
    for gearPos in gears:
        adjacentNums = gears.get(gearPos, [])
        if len(adjacentNums) == 2:
            res += np.prod(adjacentNums)

    return str(res)
