import { readFile } from "fs";

readFile("./inputs/day9", "utf8", (_, data) => {
  const board = new Board();
  // console.log("====== INITIAL ======");
  // console.log(board.toString());

  data.split("\n").forEach((line) => {
    const [dir, num] = line.split(" ");
    let xAxis = true;
    let n = Number(num);
    switch (dir) {
      case "R":
        break;
      case "L":
        n *= -1;
        break;
      case "U":
        xAxis = false;
        break;
      case "D":
        xAxis = false;
        n *= -1;
        break;
    }

    board.moveHead(n, xAxis);
    // console.log("");
    // console.log("====" + dir + num + "====");
    // console.log("");
    // console.log(board.toString());
  });

  console.log(board.visited.size);
  // console.log(board.head);
  // console.log(board.tail);
  // console.log(board.visited);
});

class Board {
  visited: Set<string>;
  head: { x: number; y: number };
  tail: { x: number; y: number };
  constructor() {
    this.visited = new Set();
    this.visited.add(0 + "/" + 0);
    this.head = { x: 0, y: 0 };
    this.tail = { x: 0, y: 0 };
  }

  toString() {
    const visited = Array.from(this.visited);
    return Array(5)
      .fill(null)
      .map((_, y) =>
        Array(6)
          .fill(".")
          .map((_, x) => {
            if (y === this.head.y && x === this.head.x) return "H";
            if (y === this.tail.y && x === this.tail.x) return "T";
            if (
              !!visited.find(
                (s) => s.split("/")[0] === `${x}` && s.split("/")[1] === `${y}`
              )
            )
              return "#";
            return ".";
          })
      );
  }

  moveHead(n: number, xAxis: boolean) {
    for (let i = 0; i < Math.abs(n); i++) {
      this._moveHead(n > 0 ? 1 : -1, xAxis);
    }
  }

  _moveHead(n: number, xAxis: boolean) {
    if (xAxis) {
      this.head.x += n;
      if (Math.abs(this.head.x - this.tail.x) > 1) {
        // Move tail
        if (this.head.y === this.tail.y) {
          this.tail.x += n;
        } else {
          this.tail.x += n;
          this.tail.y = this.head.y;
        }
      }
    } else {
      this.head.y += n;
      if (Math.abs(this.head.y - this.tail.y) > 1) {
        // Move tail
        if (this.head.x === this.tail.x) {
          this.tail.y += n;
        } else {
          this.tail.y += n;
          this.tail.x = this.head.x;
        }
      }
    }

    this.visited.add(this.tail.x + "/" + this.tail.y);
  }
}
