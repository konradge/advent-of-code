import re
import numpy as np


def countMatches(input: str):
    lines = input.splitlines()
    matchesPerCard = np.full(len(lines), 0)
    for idx, card in enumerate(lines):
        _, winning, nums = re.split(r"[:|]", card)
        winning = [int(x) for x in winning.strip().split()]
        nums = [int(x) for x in nums.strip().split()]
        matches = np.sum([1 for num in nums if num in winning])
        matchesPerCard[idx] = matches

    return matchesPerCard


def part1(input: str):
    matchesPerCard = countMatches(input)
    return np.sum([pow(2, matches - 1) for matches in matchesPerCard if matches > 0])


def part2(input):
    matchesPerCard = countMatches(input)
    cardStock = np.full(len(matchesPerCard), 1)
    for cardIdx in range(len(matchesPerCard)):
        cardsToScratch = cardStock[cardIdx]
        matches = matchesPerCard[cardIdx]
        start = cardIdx + 1
        end = min(start + matches, len(cardStock))
        cardStock[start:end] = np.add(
            cardStock[start:end], np.full(end - start, cardsToScratch)
        )

    return str(np.sum(cardStock))
