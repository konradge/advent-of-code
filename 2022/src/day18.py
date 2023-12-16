import numpy as np

file = open("inputs/day18", "r")
input = file.read()


coords = [np.array(x.split(",")).astype(int) for x in input.splitlines()]

cubes = {}

for coord in coords:
    cubes[tuple(coord)] = 0


res = 0

for coord in coords:
    for dx in range(-1, 2):
        for dy in range(-1, 2):
            for dz in range(-1, 2):
                if abs(dx) + abs(dy) + abs(dz) == 1:
                    # print(coord, coord + np.array([dx, dy, dz]))
                    res += cubes.get(tuple(coord + np.array([dx, dy, dz])), 1)


print(res)
