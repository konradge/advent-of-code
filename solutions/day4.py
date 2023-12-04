import re
import numpy as np


def prepare(input: str):
    lines = input.split("\n")
    matchesPerCard = np.array([0] * len(lines))
    for card in lines:
        number, winning, nums = re.split(r"[:|]", card)
        number = int(re.split(r" +", number)[1])
        winning = list(map(lambda x: int(x), re.split(r" +", winning.strip())))
        nums = list(map(lambda x: int(x), re.split(r" +", nums.strip())))
        matches = 0
        for num in nums:
            if num in winning:
                matches += 1
        matchesPerCard[number - 1] = matches

    return matchesPerCard


def part1(input: str):
    matchesPerCard = prepare(input)
    return np.sum([pow(2, matches - 1) for matches in matchesPerCard if matches > 0])


def part2(input):
    matchesPerCard = prepare(input)
    cardStock = np.array([1] * len(matchesPerCard))
    for num in range(len(matchesPerCard)):
        cardsToScratch = cardStock[num]
        matches = matchesPerCard[num]
        # add <cardsToScratch> to the next <matches> cards
        start = num + 1
        end = min(start + matches, len(cardStock))
        cardStock[start:end] = np.add(
            cardStock[start:end], np.full(end - start, cardsToScratch)
        )

    return str(np.sum(cardStock))
