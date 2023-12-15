def hash(val: str):
    res = 0
    for i in val:
        res += ord(i)
        res *= 17
        res %= 256
    return res


def part1(input: str) -> str:
    res = 0
    for x in input.split(","):
        res += hash(x)
    return str(res)


def part2(input: str) -> str:
    hm: dict[int, list[tuple[str, int]]] = dict()
    for param in input.split(","):
        label = param.split("=")[0].split("-")[0]
        h = hash(label)
        inMap = hm.setdefault(h, [])
        if param.endswith("-"):
            inMap = [x for x in inMap if x[0] != label]
        else:
            amount = param.split("=")[1]
            inMap = [x if x[0] != label else (label, int(amount)) for x in inMap]
            if len([x for x in inMap if x[0] == label]) == 0:
                inMap.append(((label, int(amount))))
        hm[h] = inMap

    res = 0
    for slotNum in hm.keys():
        for idx, (label, focalLength) in enumerate(hm[slotNum]):
            res += (slotNum + 1) * (idx + 1) * focalLength

    return str(res)
