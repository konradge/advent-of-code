import { readFile } from "fs";

const types: { [key: string]: number } = {
  A: 0,
  B: 1,
  C: 2,
  X: 2,
  Y: 0,
  Z: 1,
};

const multiplier = [3, 6, 0];

readFile("./inputs/day2", "utf8", (_, data) => {
  const rounds = data.split("\n").map((round) => {
    const [opp, self] = round.split(" ").map((x) => types[x]);

    let score = (opp + self) % 3;

    return { score: score + 1, multiplier: multiplier[self] };
  });

  console.log(rounds);

  console.log(
    rounds.reduce((acc, round) => acc + round.score + round.multiplier, 0)
  );
});
