import numpy as np
import pickle


def tilt(grid):
    for j in range(len(grid[0])):
        for i in range(len(grid)):
            if grid[i][j] == "O":
                k = i
                while k >= 1 and grid[k - 1][j] == ".":
                    k -= 1
                grid[i][j] = "."
                grid[k][j] = "O"
    return grid


def printGrid(grid):
    print("\n".join(["".join(x) for x in grid]) + "\n")


def getResult(grid):
    res = 0
    for idx, x in enumerate(grid):
        for _, y in enumerate(x):
            if y == "O":
                res += len(x) - idx
    return res


def part1(input: str) -> str:
    grid = np.array([list(x) for x in input.splitlines()])
    return str(getResult(tilt(grid)))


def part2(input: str) -> str:
    grid = np.array([list(x) for x in input.splitlines()])

    found: dict[bytes, tuple[int, int]] = dict()

    circle = 0
    C = 1000000000

    for i in range(C):
        b = pickle.dumps(grid)
        assert (pickle.loads(b) == grid).all()
        val = found.get(b)
        if val != None:
            circle = val[0]
            break
        else:
            found[b] = (i, getResult(grid))
        # North
        grid = tilt(grid)
        # West
        grid = tilt(grid.transpose()).transpose()
        # south
        grid = tilt(grid[::-1, :])[::-1, :]
        # East
        grid = tilt(grid.transpose()[::-1, :])[::-1, :].transpose()

    circLength = len(found) - circle
    return str(list(found.values())[-circLength:][(C - circle) % circLength][1])
