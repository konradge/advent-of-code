import { readFile } from "fs";

readFile("./inputs/day10", "utf8", (_, data) => {
  const cycles = data
    .split(/[\n ]/g)
    .map((x) => (!Number.isNaN(Number(x)) ? Number(x) : 0));
  const crt = Array(Math.floor(cycles.length / 40))
    .fill(null)
    .map(() => Array(40).fill("."));
  let val = 1;
  const steps = [20, 60, 100, 140, 180, 220];
  let sum = 0;
  for (let i = 0; i < cycles.length; i++) {
    if (steps.includes(i + 1)) {
      sum += val * (i + 1);
    }

    const [crtX, crtY] = [i % 40, Math.floor(i / 40)];
    const sprite = [val - 1, val, val + 1];
    console.log("Cyle ", i, " ", crtX, crtY);
    if (sprite.includes(crtX)) {
      crt[crtY][crtX] = "#";
    }

    val += cycles[i];
  }
  console.log(sum);

  console.log(crt.map((x) => x.join("")).join("\n"));
});
