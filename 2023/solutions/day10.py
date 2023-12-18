import numpy as np
import numpy.typing as npt
from queue import Queue

up = np.array([0, -1])
down = np.array([0, 1])
left = np.array([-1, 0])
right = np.array([1, 0])

directions = ["|", "-", "L", "J", "F"]


def connections(connectionType: str):
    # Change S based on inspection
    match connectionType:
        case "|":
            return [up, down]
        case "-":
            return [left, right]
        case "L":
            return [up, right]
        case "J" | "S":
            return [up, left]
        case "F":
            return [down, right]
        case "7":
            return [left, down]
        case _:
            return []


def part1(input: str) -> str:
    start = np.array([-1, -1])
    grid = input.splitlines()
    for idx, line in enumerate(grid):
        s = line.find("S")
        if s >= 0:
            start = np.array([s, idx])
            break
    steps = 0
    queue = Queue()
    for x in connections(grid[start[1]][start[0]]):
        queue.put(start + x)
    visited = dict()
    visited[tuple(start)] = True
    while (queue.queue[0] != queue.queue[1]).any():
        steps += 0.5
        pos = queue.get()
        visited[tuple(pos)] = True
        connectionType = grid[pos[1]][pos[0]]
        neighbours = connections(connectionType)
        newNeighbours = [x for x in neighbours if visited.get(tuple(pos + x)) == None]
        assert len(newNeighbours) == 1
        queue.put(pos + newNeighbours[0])
        assert len(queue.queue) == 2
    return str(int(steps) + 1)


def part2(input: str) -> str:
    return "TODO: Implement part 2"
