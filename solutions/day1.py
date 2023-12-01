import re

digitMap = {
    "one": 1,
    "two": 2,
    "three": 3,
    "four": 4,
    "five": 5,
    "six": 6,
    "seven": 7,
    "eight": 8,
    "nine": 9,
    "zero": 0,
}

print()


def part1(input: str):
    res = 0
    for x in input.split("\n"):
        line = re.sub(r"[a-z]", "", x)
        if len(line) > 0:
            res += int(line[0] + line[-1])
        else:
            raise RuntimeError("% has no number in it" % line)
    return str(res)


def part2(input):
    for strNum in list(digitMap.keys()):
        input = re.sub(strNum, strNum + str(digitMap[strNum]) + strNum, input)
    return part1(input)
