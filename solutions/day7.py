from functools import cmp_to_key


class Hand:
    bid: int
    cards: list[int]
    rank: int

    def __init__(self, bid: int, cards: str, joker=False):
        self.bid = bid
        if joker:
            self.cards = [getCardsRankJoker(x) for x in list(cards)]
            self.rank = getHandRankJoker(self.cards)
        else:
            self.cards = [getCardsRank(x) for x in list(cards)]
            self.rank = getHandRank(self.cards)

    def __str__(self) -> str:
        return str(self.cards) + "(" + str(self.rank) + ")"


def getCardsRank(input: str):
    input = (
        input.replace("T", "10")
        .replace("J", "11")
        .replace("Q", "12")
        .replace("K", "13")
        .replace("A", "14")
    )
    return int(input)


def getCardsRankJoker(input: str):
    rank = getCardsRank(input)
    if rank == 11:
        return 1
    else:
        return rank


def parseHand(line: str, joker=False):
    bid = int(line.split()[1])
    cards = line.split()[0]

    return Hand(bid, cards, joker)


def isTwoPair(values: dict[int, int], jokers=0):
    sortedCounts = sorted(values.values())
    if jokers == 0:
        return sortedCounts[-1] == 2 and sortedCounts[-2] == 2
    if jokers == 1:
        return sortedCounts[-1] == 2
    else:
        return len(sortedCounts) + jokers >= 4


def isFullHouse(values: dict[int, int], jokers=0):
    if jokers == 0:
        return max(values.values()) == 3 and min(values.values()) == 2
    else:
        return isTwoPair(values, jokers - 1)


def getHandRank(hand: list[int], jokers=0):
    values: dict[int, int] = dict()

    for card in hand:
        count = values.setdefault(card, 0)
        values[card] = count + 1

    result = 0
    if jokers >= 4 or max(values.values()) + jokers == 5:
        # Five of a kind
        result = 7
    elif max(values.values()) + jokers == 4:
        # Four of a kind
        result = 6
    elif isFullHouse(values, jokers):
        # Full house
        result = 5
    elif max(values.values()) + jokers == 3:
        # Three of a kind
        result = 4
    elif isTwoPair(values, jokers):
        # Two pair
        result = 3
    elif max(values.values()) + jokers == 2:
        # One pair
        result = 2
    else:
        result = 1

    return result


def getHandRankJoker(hand: list[int]):
    handWithoutJokers = [x for x in hand if x != 1]

    return getHandRank(handWithoutJokers, len(hand) - len(handWithoutJokers))


def sortByResult(h1: Hand, h2: Hand):
    if h1.rank > h2.rank:
        return -1
    elif h1.rank < h2.rank:
        return 1

    for idx, _ in enumerate(h1.cards):
        if h1.cards[idx] > h2.cards[idx]:
            return -1
        elif h1.cards[idx] < h2.cards[idx]:
            return 1

    raise RuntimeError("Cards are tied")


def getResult(input: str, joker=False):
    hands = [parseHand(x, joker) for x in input.splitlines()]

    hands = sorted(hands, key=cmp_to_key(sortByResult))

    res = 0

    for idx, hand in enumerate(reversed(hands)):
        res += hand.bid * (idx + 1)

    return res


def part1(input: str) -> str:
    return str(getResult(input))


def part2(input: str) -> str:
    return str(getResult(input, True))
