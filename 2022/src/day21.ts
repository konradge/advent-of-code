import { readFile } from "fs";
import { isArray } from "lodash";

type Unknown = (x: number) => number;

const monkeys: {
  [monkey: string]: {
    needed: string[];
    op: "+" | "-" | "*" | "/" | null;
    number: number | null;
    unknownNumber: Unknown | null;
    descendantUnknown?: boolean;
  };
} = {};

const isOperation = (a: string): a is "+" | "-" | "*" | "/" =>
  ["+", "-", "*", "/"].includes(a);

const yell = (monkey: string): number => {
  if (monkeys[monkey].number === null) {
    const [x, y] = monkeys[monkey].needed;
    const xValue = monkeys[x].number ?? yell(x);
    const yValue = monkeys[y].number ?? yell(y);
    switch (monkeys[monkey].op) {
      case "+":
        monkeys[monkey].number = xValue + yValue;
        break;
      case "-":
        monkeys[monkey].number = xValue - yValue;
        break;
      case "*":
        monkeys[monkey].number = xValue * yValue;
        break;
      case "/":
        monkeys[monkey].number = xValue / yValue;
        break;
      default:
        throw new Error("Invalid operation: " + monkeys[monkey].op);
    }
  }
  return monkeys[monkey].number!;
};

const yell2 = (monkey: string): Unknown | [Unknown, Unknown] => {
  if (monkey === "humn") return (x: number) => x;

  if (monkeys[monkey].number !== null) return () => monkeys[monkey].number!;

  if (monkeys[monkey].unknownNumber === null) {
    const [x, y] = monkeys[monkey].needed;
    const xValue = yell2(x);
    const yValue = yell2(y);

    if (isArray(xValue) || isArray(yValue)) throw new Error("Array error");

    if (monkey === "root") {
      return [xValue, yValue];
    }

    switch (monkeys[monkey].op) {
      case "+":
        monkeys[monkey].unknownNumber = (x: number) => xValue(x) + yValue(x);
        break;
      case "-":
        monkeys[monkey].unknownNumber = (x: number) => xValue(x) - yValue(x);
        break;
      case "*":
        monkeys[monkey].unknownNumber = (x: number) => xValue(x) * yValue(x);
        break;
      case "/":
        monkeys[monkey].unknownNumber = (x: number) => xValue(x) / yValue(x);
        break;
      default:
        throw new Error("Invalid operation: " + monkeys[monkey].op);
    }
  }

  if (monkeys[monkey].unknownNumber === null)
    throw new Error("Number should not be undefined");
  return monkeys[monkey].unknownNumber!;
};

const descendantIsUnknown = (monkey: string): boolean => {
  let descendantUnknown: boolean | undefined;
  if (monkey === "humn") {
    descendantUnknown = true;
  } else if (monkeys[monkey].number !== null) {
    descendantUnknown = false;
  } else if (monkeys[monkey].descendantUnknown !== undefined) {
    descendantUnknown = monkeys[monkey].descendantUnknown;
  } else {
    const [d1, d2] = monkeys[monkey].needed;
    descendantUnknown = descendantIsUnknown(d1) || descendantIsUnknown(d2);
  }

  if (descendantUnknown === undefined)
    throw new Error("Descendant unknown should not be undefined");

  monkeys[monkey].descendantUnknown = descendantUnknown;
  return descendantUnknown;
};

const findUnknown = (monkey: string, needed: number): number => {
  const [x, y] = monkeys[monkey].needed;

  if (monkey === "humn") {
    return needed;
  } else if (descendantIsUnknown(x)) {
    const otherSide = yell(y);

    let newNeeded;
    switch (monkeys[monkey].op) {
      case "+":
        newNeeded = needed - otherSide;
        break;
      case "-":
        newNeeded = needed + otherSide;
        break;
      case "*":
        newNeeded = needed / otherSide;
        break;
      case "/":
        newNeeded = needed * otherSide;
        break;
      default:
        throw new Error("Invalid operation: " + monkeys[monkey].op);
    }
    return findUnknown(x, newNeeded);
  } else if (descendantIsUnknown(y)) {
    const otherSide = yell(x);

    let newNeeded;
    switch (monkeys[monkey].op) {
      case "+":
        newNeeded = needed - otherSide;
        break;
      case "-":
        newNeeded = otherSide - needed;
        break;
      case "*":
        newNeeded = needed / otherSide;
        break;
      case "/":
        newNeeded = otherSide / needed;
        break;
      default:
        throw new Error("Invalid operation: " + monkeys[monkey].op);
    }
    return findUnknown(y, newNeeded);
  } else {
    throw new Error("Both sides are known");
  }
};

const prepare = (data: string) => {
  data.split("\n").forEach((line) => {
    const [name, op] = line.split(": ");
    monkeys[name] = { needed: [], op: null, number: null, unknownNumber: null };
    if (op.match(/\d+/)) {
      monkeys[name].number = Number(op);
    } else {
      const [x, a, y] = op.split(" ");
      if (!isOperation(a)) throw new Error("Invalid operation: " + a);
      monkeys[name].needed = [x, y];
      monkeys[name].op = a;
    }
  });
};

readFile("./inputs/day21", "utf8", (_, data) => {
  prepare(data);
  console.log(yell("root"));

  prepare(data);

  const res = yell2("root");

  if (!isArray(res)) throw new Error("Something went wrong");

  const needed = res[1](0);

  prepare(data);

  descendantIsUnknown("root");

  console.log(findUnknown("lrnp", needed));
});
