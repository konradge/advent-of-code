import { readFile } from "fs";
import { max } from "lodash";

const reset = (data: string) => {
  const blocked: { [coord: string]: boolean } = {};

  let maxY = 0;

  data.split("\n").forEach((line) => {
    const points = line.split(" -> ");
    for (let i = 0; i < points.length - 1; i++) {
      // console.log(points[i], "->", points[i + 1]);
      const [sX, sY] = points[i].split(",").map((x) => Number(x));
      const [eX, eY] = points[i + 1].split(",").map((x) => Number(x));

      maxY = max([maxY, sY, eY])!;

      if (sX === eX) {
        const [start, end] = [sY, eY].sort();
        for (let i = start; i <= end; i++) {
          blocked[`${sX},${i}`] = true;
          // console.log("Placing at ", sX, i);
        }
      } else {
        const [start, end] = [sX, eX].sort();
        for (let i = start; i <= end; i++) {
          blocked[`${i},${sY}`] = true;
          // console.log("Placing at ", i, sY);
        }
      }
    }
  });
  return { blocked, maxY };
};

readFile("./inputs/day14", "utf8", async (_, data) => {
  const { blocked, maxY } = reset(data);
  const sand = [500, 0];

  let sandCount = 1;

  while (sand[1] <= maxY) {
    // await new Promise((r) => setTimeout(r, 100));
    // console.log(sand);
    // console.log(blocked[`${sand[0]},${sand[1]}`]);
    if (!blocked[`${sand[0]},${sand[1] + 1}`]) {
      sand[1]++;
    } else if (!blocked[`${sand[0] - 1},${sand[1] + 1}`]) {
      sand[0]--;
      sand[1]++;
    } else if (!blocked[`${sand[0] + 1},${sand[1] + 1}`]) {
      sand[0]++;
      sand[1]++;
    } else {
      blocked[`${sand[0]},${sand[1]}`] = true;
      sandCount++;
      sand[0] = 500;
      sand[1] = 0;
      // console.log("Next sand");
    }
  }

  console.log(sandCount - 1);

  const { blocked: blocked2 } = reset(data);

  sandCount = 1;

  sand[0] = 500;
  sand[1] = 0;

  const nextSand = () => {
    blocked2[`${sand[0]},${sand[1]}`] = true;
    sandCount++;
    sand[0] = 500;
    sand[1] = 0;
  };

  while (!blocked2[`500,0`]) {
    // await new Promise((r) => setTimeout(r, 100));
    // console.log(sand);
    // console.log(blocked[`${sand[0]},${sand[1]}`]);

    if (sand[1] === maxY + 1) {
      nextSand();
    } else if (!blocked2[`${sand[0]},${sand[1] + 1}`]) {
      sand[1]++;
    } else if (!blocked2[`${sand[0] - 1},${sand[1] + 1}`]) {
      sand[0]--;
      sand[1]++;
    } else if (!blocked2[`${sand[0] + 1},${sand[1] + 1}`]) {
      sand[0]++;
      sand[1]++;
    } else {
      nextSand();
      // console.log("Next sand");
    }
  }

  console.log(sandCount - 1);
});
