import { readFile } from "fs";

readFile("./inputs/day1", "utf8", (_, data) => {
  const res = data
    .split("\n\n")
    .map((elve) => {
      console.log(elve);
      console.log("------");
      return elve.split("\n").reduce((acc, cal) => acc + Number(cal), 0);
    })
    .sort();

  console.log(res.reverse());
});
