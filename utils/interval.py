class Interval:
    _start: int
    _size: int

    def __init__(
        self, start: int, end: int | None = None, size: int | None = None
    ) -> None:
        self._start = start
        if size != None:
            self._size = size
        elif end != None:
            self._size = end - start
        else:
            print("One must be defined!")

    def left(self):
        return self._start

    def right(self):
        return self._start + self._size

    def shift(self, amount: int):
        self._start += amount

    def shifted(self, amount: int):
        newInterval = Interval(self._start, size=self._size)
        newInterval.shift(amount)
        return newInterval

    def __str__(self) -> str:
        return "[" + str(self.left()) + "," + str(self.right()) + "]"


class Mapping:
    _domain: Interval
    _shift: int
    _range: Interval

    def __init__(self, domain: Interval, shift: int):
        self._domain = domain
        self._shift = shift
        self._range = self.domain().shifted(self._shift)

    def domain(self):
        return self._domain

    def range(self):
        return self._range

    def shift(self):
        return self._shift

    def __str__(self) -> str:
        return (
            str(self.domain())
            + "->"
            + str(self.range())
            + "(shift="
            + str(self.shift())
            + ")"
        )


def remapAll(mappings: list[Mapping], fn: Mapping):
    res = []
    for mapping in mappings:
        res.extend(remapMapping(mapping, fn))
    return res


def remapMapping(mapping: Mapping, fn: Mapping):
    res = []

    unchangedLeft = fn.domain().left() - mapping.range().left() - 1

    if unchangedLeft >= 0:
        res.append(
            Mapping(
                Interval(mapping.domain().left(), size=unchangedLeft), mapping.shift()
            )
        )
    else:
        unchangedLeft = -1

    unchangedRight = mapping.range().right() - fn.domain().right() - 1

    if unchangedRight >= 0:
        res.append(
            Mapping(
                Interval(
                    mapping.domain().right() - unchangedRight, size=unchangedRight
                ),
                mapping.shift(),
            )
        )
    else:
        unchangedRight = -1

    leftChange = mapping.domain().left() + unchangedLeft + 1

    rightChange = mapping.domain().right() - unchangedRight - 1

    if rightChange - leftChange >= 0:
        res.append(
            Mapping(
                Interval(
                    leftChange,
                    rightChange,
                ),
                mapping.shift() + fn.shift(),
            )
        )

    return res


# mappingOnlyRight = Mapping(Interval(0, 10), 2)
# print(mappingOnlyRight)
# fn = Mapping(Interval(5, 8), -2)  # [5, 8] -> [3,6]
# print(fn)
# res = remapMapping(mappingOnlyRight, fn)
# print([str(m) for m in res])
