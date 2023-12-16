import queue

file = open("inputs/day24", "r")
input = file.read()

map = [[[] if y == "." else [y] for y in list(x)] for x in input.splitlines()]

map = map[1:-1]

map = [x[1:-1] for x in map]


def printMap(map):
    print("-------")
    for x in map:
        for y in x:
            if len(y) == 0:
                print(".", end="")
            elif len(y) > 1:
                print(len(y), end="")
            else:
                print(y[0], end="")
        print("")
    print("-------")


def nextTick(map):
    nextMap = [[[] for y in range(len(map[x]))] for x in range(len(map))]
    for x in range(len(map)):
        for y in range(len(map[x])):
            for entry in map[x][y]:
                if entry == ">":
                    nextMap[x][(y + 1) % len(map[x])].append(entry)
                elif entry == "<":
                    nextMap[x][(y - 1) % len(map[x])].append(entry)
                elif entry == "^":
                    nextMap[(x - 1) % len(map)][y].append(entry)
                elif entry == "v":
                    nextMap[(x + 1) % len(map)][y].append(entry)

    return nextMap


printMap(map)

for i in range(18):
    map = nextTick(map)

printMap(map)


def canMove(map, coords):
    (x, y) = coords
    if x < 0 or x >= len(map):
        return False


def search(map):
    q = queue.Queue()
    q.put((-1, 1))

    while not q.empty():
        (x, y) = q.get()

        if x - 1 >= 0 and map[x - 1][y] == []:
            q.put((x - 1, y))
        if x + 1 < len(map) and map[x + 1][y] == []:
            q.put((x + 1, y))
        if y - 1 >= 0 and map[x][y - 1] == []:
            q.put((x, y - 1))
        if y + 1 < len(map[x]) and map[x][y + 1] == []:
            q.put((x, y + 1))
