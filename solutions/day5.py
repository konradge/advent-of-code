from utils.interval import Interval, Mapping, remapAll


def arrTo3Tuple(arr: list[int]):
    a, b, c = arr
    return (a, b, c)


def buildDict(input: str):
    d = [[int(x) for x in line.split()] for line in input.splitlines()]

    return [arrTo3Tuple(x) for x in d]


def lookupItem(item: int, dict: list[tuple[int, int, int]]):
    for d in dict:
        destStart, srcStart, ran = d
        if item >= srcStart and item <= srcStart + ran:
            return destStart + (item - srcStart)
    return item


def lookup(seed: int, dicts: list[list[tuple[int, int, int]]]):
    for dict in dicts:
        nextSeed = lookupItem(seed, dict)
        if nextSeed != None:
            seed = nextSeed
    return seed


def part1(input: str) -> str:
    parts = input.split("\n\n")
    dicts = [buildDict(dict.split(":\n")[1]) for dict in parts[1:]]
    initialSeeds = [int(x) for x in parts[0].split(": ")[1].split()]
    seeds = [lookup(s, dicts) for s in initialSeeds]
    return str(min(seeds))


def dictToMapping(dict: tuple[int, int, int]):
    return Mapping(Interval(dict[1], size=dict[2] - 1), dict[0] - dict[1])


def part2(input: str) -> str:
    parts = input.split("\n\n")
    dicts = [buildDict(dict.split(":\n")[1]) for dict in parts[1:]]
    functions = [[dictToMapping(x) for x in mapping] for mapping in dicts]
    initialSeeds = [int(x) for x in parts[0].split(": ")[1].split()]
    idx = 0
    mappings: list[Mapping] = []
    while idx < len(initialSeeds):
        mappings.append(
            Mapping(Interval(initialSeeds[idx], size=initialSeeds[idx + 1]), 0)
        )
        idx += 2
    for functionSet in functions:
        for fn in functionSet:
            print(fn)
            mappings = remapAll(mappings, fn)
        print("\n\n\n")
        print([str(m) for m in mappings])

    res = min([x.range().left() for x in mappings])
    return str(res)
