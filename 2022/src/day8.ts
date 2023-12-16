import { readFile } from "fs";
import { cloneDeep, dropRight } from "lodash";

// enum SEEN_FROM {
//   RIGHT,
//   LEFT,
//   TOP,
//   BOTTOM,
// }

readFile("./inputs/day8", "utf8", (_, data) => {
  const map = data
    .split("\n")
    .map((x) => x.split("").map((n) => ({ height: Number(n), seen: false })));

  console.log(countAll(0, cloneDeep(map), (a, b) => a + b));
});

const countAll = (
  from: number,
  map: { height: number; seen: boolean }[][],
  acc: (a: number, b: number) => number
) => {
  let counter = 0;

  counter = acc(counter, count(from, map));
  counter = acc(
    counter,
    count(
      from,
      map.map((x) => x.reverse())
    )
  );

  counter = acc(
    counter,
    count(
      from,
      map[0].map((_, i) => map.map((y) => y[i]))
    )
  );
  counter = acc(
    counter,
    count(
      from,
      map[0].map((_, i) => map.map((y) => y[i]).reverse())
    )
  );
  return counter;
};

const count = (from: number, map: { height: number; seen: boolean }[][]) => {
  let counter = 0;
  // console.log("-----------");
  // console.log(map.map((x) => x.map((y) => y.height).join("")).join("\n"));
  // console.log("-------------");
  for (let i = from; i < map.length; i++) {
    if (!map[i][0].seen) counter++;
    map[i][0].seen = true;
    for (let j = 1; j < map[i].length; j++) {
      if (canBeSeen(map[i], j)) {
        if (!map[i][j].seen) counter++;
        map[i][j].seen = true;
      }
    }
  }
  return counter;
};

const part2 = (map: { height: number; seen: boolean }[][]) => {
  const newMap: {
    height: number;
    seen: boolean;
    viewable?: { right: number; left: number; top: number; bottom: number };
  }[][] = cloneDeep(map).map((x) =>
    x.map((y) => ({ ...y, viewable: { right: 0, left: 0, top: 0, bottom: 0 } }))
  );

  for (let i = 0; i < newMap.length; i++) {
    for (let j = 0; j < newMap.length; j++) {
      if (canBeSeen(newMap[i], j)) {
        newMap[i][j].viewable!.right++;
      }
    }
  }
};

const canBeSeen = (row: { height: number }[], pos: number) => {
  return dropRight(row, row.length - pos).every(
    (x) => x.height < row[pos].height
  );
};
