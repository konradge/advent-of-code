import numpy as np
import numpy.typing as npt
import sys


moveMemory: dict[tuple[int, int, int, int], set[tuple[int, int]]] = dict()


def move(
    grid,
    beam: tuple[npt.NDArray, npt.NDArray],
) -> set[tuple[int, int]]:
    pos = np.array(beam[0])
    vel = np.array(beam[1])

    key = (pos[0], pos[1], vel[0], vel[1])
    if pos[1] == 1:
        # print("In 2")
        pass
    if moveMemory.get(key) == None:
        pos += vel
        moveMemory[key] = set()
        if not (
            pos[0] < 0
            or pos[0] >= len(grid)
            or pos[1] < 0
            or pos[1] >= len(grid[pos[0]])
        ):
            moveMemory[key].add((pos[0], pos[1]))

            tile = grid[pos[0]][pos[1]]
            matrices = dict()
            matrices["/"] = np.array([[0, -1], [-1, 0]])
            matrices["\\"] = np.array([[0, 1], [1, 0]])
            matrices["-"] = np.array([[0, 0], [1, 1]])
            matrices["|"] = np.array([[1, 1], [0, 0]])
            matrices["."] = np.eye(2)

            split = (tile == "-" and vel[0] != 0) or (tile == "|" and vel[1] != 0)
            vel = matrices[tile] @ vel
            for x in move(grid, (pos, vel.astype(int))):
                moveMemory[key].add(x)
            if split:
                for x in move(grid, (pos, -1 * vel.astype(int))):
                    moveMemory[key].add(x)
    else:
        # print(pos)
        pass

    return moveMemory.setdefault(key, set())


def printGrid(grid, energized):
    newGrid = [[x for x in list(line)] for line in grid]
    for x in energized:
        # print(energized)
        if newGrid[x[0]][x[1]] == ".":
            newGrid[x[0]][x[1]] = "#"

    print("\n".join(["".join(x) for x in newGrid]))


def part1(input: str) -> str:
    sys.setrecursionlimit(99999)
    grid = input.splitlines()
    beam = (np.array([0, -1]), np.array([0, 1]))

    # energized = move(grid, beam)

    # print(energizedPositions)

    # return str(len(energized))
    return ""


def part2(input: str) -> str:
    grid = input.splitlines()
    beam = (np.array([0, -1]), np.array([0, 1]))
    N = len(input.splitlines())
    maxEnergy = 0
    # print("\n".join([str((x, len(moveMemory[x]))) for x in moveMemory.keys()]))
    energized = move(grid, (np.array([-1, 3]), np.array([1, 0])))
    # printGrid(grid, energized)
    for i in reversed(range(N)):
        print(i)
        # right
        beam = (np.array([i, -1]), np.array([0, 1]))
        maxEnergy = max(maxEnergy, len(move(grid, beam)))
        # left
        beam = (np.array([i, N]), np.array([0, -1]))
        maxEnergy = max(maxEnergy, len(move(grid, beam)))
        # top
        beam = (np.array([-1, i]), np.array([1, 0]))
        maxEnergy = max(maxEnergy, len(move(grid, beam)))
        # bottom
        beam = (np.array([N, i]), np.array([-1, 0]))
        maxEnergy = max(maxEnergy, len(move(grid, beam)))
    return str(maxEnergy)
