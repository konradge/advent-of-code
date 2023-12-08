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


def findZs(instructions: list[int], network: dict[str, tuple[str, str]], start: str):
    current = start
    steps = 0
    visited: dict[str, int] = dict()
    stop = False
    visited[start] = 0
    while True:
        instruction = instructions[steps % len(instructions)]
        current = network[current][instruction]
        steps += 1
        if visited.get(current) != None:
            if stop:
                break
            stop = True
            visited = dict()
        visited[current] = steps
    print(visited)
    return {key: visited[key] for key in visited.keys() if key.endswith("Z")}


def part2(input: str) -> str:
    instructions, network = prepare(input)
    currents = [node for node in network.keys() if node.endswith("Z")]
    steps = 0
    for c in currents:
        print(findZs(instructions, network, c))
    return str(steps)
