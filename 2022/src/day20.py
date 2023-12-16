import numpy as np

file = open("inputs/day20", "r")
input = file.read()

list = np.array(
    [(x, idx) for idx, x in enumerate(np.array(input.splitlines()).astype(int))]
)

# print(list)
# print("Initial arrangement:")
# print([x[0] for x in list])

for idx in range(len(list)):
    currentIdx = [index for index, x in enumerate(list) if x[1] == idx][0]
    value = list[currentIdx]
    if value[0] == 0:
        print("Skipping 0")
        continue

    dx = list[currentIdx][0] % len(list)

    if list[currentIdx, 0] < 0:
        dx = (dx - 1) % len(list)

    if currentIdx + dx >= len(list):
        dx += 1

    destinationIdx = (currentIdx + dx) % len(list)
    # print(
    #     value[0],
    #     " moves between ",
    #     list[destinationIdx][0],
    #     " and ",
    #     list[(destinationIdx + 1) % len(list)][0],
    #     " for ",
    #     dx,
    # )
    if len(list[:currentIdx]) > 0:
        list = np.concatenate((list[:currentIdx], list[currentIdx + 1 :]))
    else:
        list = list[currentIdx + 1 :]
    list = np.insert(list, destinationIdx, value, axis=0)
    # print([x[0] for x in list])

zeroIdx = [index for index, x in enumerate(list) if x[0] == 0][0]
print(
    list[(zeroIdx + 1000) % len(list), 0]
    + list[(zeroIdx + 2000) % len(list), 0]
    + list[(zeroIdx + 3000) % len(list), 0],
)
