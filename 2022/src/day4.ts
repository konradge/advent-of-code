import { readFile } from "fs";
import { intersection, range } from "lodash";

readFile("./inputs/day4", "utf8", (_, data) => {
  const res = data.split("\n").reduce((acc, pair) => {
    const [a, b] = pair.split(",").map((elv) => {
      const [lower, upper] = elv.split("-").map(Number);
      return range(lower, upper + 1);
    });
    // console.log(a, b);
    const intersect = intersection(a, b);
    // console.log(intersect);
    return intersect.length ? acc + 1 : acc;
  }, 0);

  console.log(res);
});
