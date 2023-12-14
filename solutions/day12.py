def mayBeValid(partial: list[str], numsP: list[int]):
    numIdx = 0
    idx = 0
    start: int | None = None
    while idx < len(partial):
        if partial[idx] == "#":
            if start == None:
                start = idx
        elif partial[idx] == "." and start != None:
            if numIdx == len(numsP) or numsP[numIdx] != idx - start:
                return False
            else:
                numIdx += 1
                start = None
        elif partial[idx] == "?":
            if start != None:
                if numIdx == len(numsP) or numsP[numIdx] < idx - start:
                    return False
                else:
                    return None
            else:
                return None

        idx += 1

    if numIdx == len(numsP) - 1 and start != None and idx - start == numsP[numIdx]:
        return True
    elif numIdx == len(numsP) == 0 and start == None:
        return True
    else:
        return False


def solve(partial: list[str], nums: list[int]) -> int:
    res = 0
    mbV = mayBeValid(partial, nums)
    if not "?" in partial:
        if mbV == True:
            return 1
        else:
            assert mbV == False
            return 0
    elif mbV == False:
        return 0

    for idx, x in enumerate(partial):
        if x == "?":
            p1 = list(partial)
            p2 = list(partial)
            p1[idx] = "."
            p2[idx] = "#"
            return solve(p1, nums) + solve(p2, nums)

    raise RuntimeError("? should have been in the list")


def part1(input: str) -> str:
    lines = input.splitlines()
    x = 0
    for l in lines:
        x = max(pow(2, l.count("?")), x)
    partialRecord = [list(line.split(" ")[0]) for line in lines]
    numberedRecord = [[int(x) for x in line.split(" ")[1].split(",")] for line in lines]
    res = 0
    for idx in range(len(partialRecord)):
        print(solve(partialRecord[idx], numberedRecord[idx]))
    return str(res)


def part2(input: str) -> str:
    return "TODO: Implement part 2"
