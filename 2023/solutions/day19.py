class Workflow:
    name: str
    fallback: str
    rules: list[tuple[str, str, int, str]]

    def __init__(self, workflowString: str) -> None:
        name, rest = workflowString.split("{")
        self.name = name
        rest = rest[:-1].split(",")
        self.fallback = rest[-1]
        self.rules = [parseRule(r) for r in rest[:-1]]
        pass

    def __str__(self) -> str:
        return self.name + ": " + str(self.rules) + " or " + self.fallback

    def nextRule(self, ratings: dict[str, int]):
        for category, comparison, value, next in self.rules:
            rating = ratings[category]
            if (comparison == "<" and rating < value) or (
                comparison == ">" and rating > value
            ):
                return next
        return self.fallback


def parseRule(ruleString: str):
    category = ruleString[0]
    comparison = ruleString[1]
    value, next = ruleString[2:].split(":")
    return (category, comparison, int(value), next)


def parseRating(rating: str):
    ratingList = [(x[0], x[2:]) for x in rating[1:-1].split(",")]

    ratingDict = dict()
    for x in ratingList:
        ratingDict[x[0]] = int(x[1])
    return ratingDict


def part1(input: str) -> str:
    workflowStrings, ratings = input.split("\n\n")
    ratingsDicts = [parseRating(r) for r in ratings.splitlines()]
    workflows = [Workflow(w) for w in workflowStrings.splitlines()]
    workflows = {w.name: w for w in workflows}

    res = 0
    for ratings in ratingsDicts:
        currentWorkflow = workflows["in"]
        while True:
            nextWorkflow = currentWorkflow.nextRule(ratings)
            print(nextWorkflow, " ", end="")
            if nextWorkflow == "A" or nextWorkflow == "R":
                if nextWorkflow == "A":
                    res += sum([x for x in ratings.values()])
                break
            currentWorkflow = workflows[nextWorkflow]
        print()
    return str(res)


def part2(input: str) -> str:
    return "TODO: Implement part 2"
