import { readFile } from "fs";

readFile("./inputs/day16", "utf8", (_, data) => {
  const valves = data.split("\n").map((line) => {
    const id = line.match(/Valve (\w+)/)![1];
    const rate = Number(line.match(/rate=(\d+)/)![1]);
    const to = line.match(/valves? ([\w, ]+)/)![1].split(", ");

    return { id, rate, to };
  });

  const sortedValves = valves.sort((a, b) => b.rate - a.rate);

  let currentFlow = 0;
  let totalFlow = 0;
  let j = -1;

  for (let i = 1; i <= 30; i += 2) {
    console.log("i: ", i, currentFlow);
    totalFlow += currentFlow;
    if (j < sortedValves.length - 1) {
      // Walk
      j++;
      // Open
      currentFlow += sortedValves[j].rate;
    } else {
      totalFlow += currentFlow;
    }
  }

  console.log(totalFlow);
});
