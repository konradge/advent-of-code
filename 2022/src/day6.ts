import { readFile } from "fs";

readFile("./inputs/day6", "utf8", (_, data) => {
  const input = data.split("");

  for (let i = 0; i < input.length - 14; i++) {
    const nums = new Set<string>();
    for (let j = i; j < i + 14; j++) {
      nums.add(input[j]);
    }
    console.log(nums);
    if (nums.size === 14) {
      console.log(i + 14);
      break;
    }
  }
});
