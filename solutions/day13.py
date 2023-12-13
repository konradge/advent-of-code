import numpy as np


def findReflection(input, allowedDiff: int):
    for col in range(1, len(input[0])):
        # Get reflected parts of array
        m1 = min(col, len(input[0]) - col)
        left = input[:, (col - m1) : col][:, ::-1]
        right = input[:, col : (col + m1)]
        assert len(right[0]) == len(left[0])

        # Calculate diff between those two parts
        diff = (left != right).sum()
        if diff == allowedDiff:
            return col
    return 0


def solve(input: str, part: int):
    parts = [np.array([list(y) for y in x.splitlines()]) for x in input.split("\n\n")]
    res = 0
    for x in parts:
        # Allowed diff for part 1 is 0, for part 2 1
        hor = findReflection(x, part - 1)
        vert = findReflection(x.transpose(), part - 1)
        res += hor + 100 * vert
    return str(res)


def part1(input: str) -> str:
    return solve(input, 1)


def part2(input: str) -> str:
    return solve(input, 2)
