def prepare(input: str):
    instructions, networkInput = input.split("\n\n")
    instructions = [
        int(x) for x in list(instructions.replace("L", "0").replace("R", "1"))
    ]
    network: dict[str, tuple[str, str]] = dict()
    for line in networkInput.splitlines():
        line = line.replace("(", "").replace(")", "")
        node, lr = line.split(" = ")
        lr = lr.split(", ")
        network[node] = (lr[0], lr[1])

    return instructions, network


def part1(input: str) -> str:
    instructions, network = prepare(input)
    current = "AAA"
    steps = 0
    # while current != "ZZZ":
    #     instruction = instructions[steps % len(instructions)]
    #     current = network[current][instruction]
    #     steps += 1
    return str(steps)


def atTarget(currents: list[str]):
    yes = True
    for c in currents:
        if not c.endswith("Z"):
            yes = False

    return yes


def findFirstZ(instructions: list[int], network: dict[str, tuple[str, str]], start):
    current = start
    steps = 0
    while not current.endswith("Z"):
        instruction = instructions[steps % len(instructions)]
        current = network[current][instruction]
        steps += 1
    return steps, current


def findZs(
    instructions: list[int],
    network: dict[str, tuple[str, str]],
    start: str,
    offset: int,
):
    current = start
    steps = offset
    visited: dict[tuple[str, int], int] = dict()
    visited[(start, 0)] = steps
    while True:
        instruction = instructions[steps % len(instructions)]
        current = network[current][instruction]
        steps += 1
        if visited.get((current, steps % len(instructions))) != None:
            break
        visited[(current, steps % len(instructions))] = steps
    d = {
        key[0]: (visited[key] % len(instructions)) + offset
        for key in visited.keys()
        if key[0].endswith("Z")
    }
    return (d, len(visited.keys()))


def part2(input: str) -> str:
    instructions, network = prepare(input)
    currents = [node for node in network.keys() if node.endswith("A")]
    steps = 0
    for c in currents:
        offset, start = findFirstZ(instructions, network, c)
        print(findZs(instructions, network, start, offset))
    return str(steps)
