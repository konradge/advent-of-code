def buildSequences(line: str):
    initialSequence = [int(x) for x in line.split(" ")]
    sequences = [initialSequence]
    hasNonZero = True
    while hasNonZero:
        hasNonZero = False
        nextSequence = []
        for i in range(len(sequences[-1]) - 1):
            nextSequence.append(sequences[-1][i + 1] - sequences[-1][i])
            if nextSequence[-1] != 0:
                hasNonZero = True
        sequences.append(nextSequence)
    return sequences


def part1(input: str) -> str:
    res = 0
    for line in input.splitlines():
        sequences = buildSequences(line)
        endNumber = 0
        for i in reversed(range(len(sequences) - 1)):
            endNumber = sequences[i][-1] + endNumber
        res += endNumber
    return str(res)


def part2(input: str) -> str:
    res = 0
    for line in input.splitlines():
        sequences = buildSequences(line)
        startNumber = 0
        for i in reversed(range(len(sequences) - 1)):
            startNumber = sequences[i][0] - startNumber
        res += startNumber
    return str(res)
