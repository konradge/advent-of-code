import { readFile } from "fs";
import { chunk, intersection, sum, uniq } from "lodash";

const a = "a".charCodeAt(0) - 1;

const A = "A".charCodeAt(0) - 1;

const getValue = (char: string) =>
  char.toLowerCase() === char
    ? char.charCodeAt(0) - a
    : char.charCodeAt(0) - A + 26;

readFile("./inputs/day3", "utf8", (_, data) => {
  const res1 = data.split("\n").map((line) => {
    const inSecondHalf: { [key: number]: boolean } = {};
    const rucksack = line.split("").map((item) => getValue(item));
    rucksack.splice(rucksack.length / 2).forEach((item) => {
      inSecondHalf[item] = true;
    });

    return uniq(rucksack.filter((item) => inSecondHalf[item]))[0];
  });

  console.log(sum(res1));

  const res2 = chunk(data.split("\n"), 3).map((rucksacks) => {
    let commons: string[] = rucksacks[0].split("");

    rucksacks.forEach((rucksack) => {
      commons = intersection(commons, rucksack.split(""));
    });

    return getValue(commons[0]);
  });

  console.log(sum(res2));
});
