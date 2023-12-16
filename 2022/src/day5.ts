import { readFile } from "fs";
import { dropRight } from "lodash";

const prepare = (data: string): [string[][], number[][]] => {
  const [cr, pr] = data.split("\n\n");

  const crates = dropRight(
    cr.split("\n").map((x) =>
      x
        .match(/.{1,4}/g)!
        .toString()
        .split(",")
        .map((x) => String(x).replace(/(\[|\]| )/g, ""))
        .map((x) => {
          return x === "" ? "0" : x;
        })
    ),
    1
  );

  const stacks = crates
    .reduce<string[][]>(
      (acc, crates) => {
        crates.forEach((crate, i) => {
          acc[i].push(crate);
        });
        return acc;
      },
      crates[0].map(() => [])
    )
    .map((x) => x.filter((v) => v !== "0"));

  const procedures = pr.split("\n").map((x) =>
    x
      .replace(/(move|from|to) /g, "")
      .split(" ")
      .map(Number)
  );

  return [stacks, procedures];
};

readFile("./inputs/day5", "utf8", (_, data) => {
  const [stacks1, procedures1] = prepare(data);

  procedures1.forEach((proc) => {
    const [count, from, to] = proc;
    for (let i = 0; i < count; i++) {
      stacks1[to - 1].unshift(stacks1[from - 1].shift()!);
    }
  });

  console.log(stacks1.map((s) => s[0]).join(""));

  const [stacks2, procedures2] = prepare(data);

  procedures2.forEach((proc) => {
    const [count, from, to] = proc;
    const newFrom = stacks2[from - 1].splice(count);
    stacks2[to - 1] = [...stacks2[from - 1], ...stacks2[to - 1]];
    stacks2[from - 1] = newFrom;
  });

  console.log(stacks2.map((s) => s[0]).join(""));
});
