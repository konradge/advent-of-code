import { readFile } from "fs";

const yPos = 2000000;

let [minY, maxY, minX, maxX] = [0, 0, 0, 0];

const getEverythingInDistance = (x: number, y: number, dist: number) => {
  // console.log(dist);
  const res = new Set<string>();

  for (let i = x - dist; i <= x + dist; i++) {
    const xDistToBeacon = Math.abs(x - i);
    for (let j = y - dist + xDistToBeacon; j <= y + dist - xDistToBeacon; j++) {
      res.add(`${i},${j}`);
    }
  }

  return res;
};

readFile("./inputs/day15", "utf8", (_, data) => {
  let allPoints = new Set<string>();
  const map: { [coord: string]: string } = {};
  data.split("\n").forEach((line) => {
    console.log("nextLine");
    const match = line.match(
      /Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)/
    )!;

    const [sX, sY, bX, bY] = [
      Number(match[1]),
      Number(match[2]),
      Number(match[3]),
      Number(match[4]),
    ];

    map[`${bX},${bY}`] = "B";

    map[`${sX},${sY}`] = "S";

    const dist = Math.abs(sX - bX) + Math.abs(sY - bY);

    const points = getEverythingInDistance(sX, sY, dist);

    allPoints = new Set([...allPoints, ...points]);
  });

  console.log("Starting sum");

  let sum = 0;

  for (const point of allPoints) {
    const [x, y] = point.split(",").map((x) => Number(x));

    minX = Math.min(minX, x);
    maxX = Math.max(maxX, x);
    minY = Math.min(minY, y);
    maxY = Math.max(maxY, y);

    if (map[point] === undefined) {
      map[point] = "#";
    }
  }

  for (let i = minY; i <= maxY; i++) {
    // process.stdout.write(`${i}\t`);
    for (let j = minX; j <= maxX; j++) {
      const val = map[`${j},${i}`];
      if (val !== undefined) {
        if (i === yPos && val !== "B") {
          sum++;
        }
        // process.stdout.write(val);
      } else {
        // process.stdout.write(".");
      }
    }
    // process.stdout.write("\n");
  }

  console.log(sum);
});
