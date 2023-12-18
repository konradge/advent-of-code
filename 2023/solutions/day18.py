import numpy as np
import numpy.typing as npt


def part1(input: str) -> str:
    instructions = [
        (Direction(x.split()[0]), int(x.split()[1])) for x in input.splitlines()
    ]

    return calculate(instructions)


class Direction:
    dir: npt.NDArray

    def __init__(self, directionString: str) -> None:
        match directionString:
            case "R" | "0":
                self.dir = np.array([1, 0])
            case "L" | "2":
                self.dir = np.array([-1, 0])
            case "U" | "3":
                self.dir = np.array([0, -1])
            case "D" | "1":
                self.dir = np.array([0, 1])
            case _:
                raise RuntimeError("Unknown direction ", dir)


def part2(input: str) -> str:
    instructions = [
        (Direction(x.split()[-1][-2]), int(x.split()[-1][2:-2], 16))
        for x in input.splitlines()
    ]
    return calculate(instructions)


def calculate(instructions: list[tuple[Direction, int]]):
    coords = [np.array([0.5, 0.5])]

    edgeCount = 0
    for instruction in instructions:
        dir, steps = instruction
        coords.append(coords[-1] + steps * dir.dir)
        edgeCount += (steps - 1) / 2
    res = 0
    coords = coords[:-1]
    for i in range(len(coords)):
        edgeCount += 0.5
        pi = coords[i]
        pip1 = coords[(i + 1) % len(coords)]
        res += (pi[1] + pip1[1]) * (pi[0] - pip1[0])
    res = res / 2 + edgeCount + 1
    return str(int(res))
