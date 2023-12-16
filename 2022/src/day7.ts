import { readFile } from "fs";
import { min } from "lodash";

interface Directories {
  name: string;
  files: Array<{ name: string; size: number }>;
  children: { [dir: string]: Directories };
  size?: number;
}

readFile("./inputs/day7", "utf8", (_, data) => {
  const directories: Directories = {
    name: ".",
    files: [],
    children: {},
  };
  let head = directories;
  data
    .split("\n")
    .slice(1)
    .forEach((line) => {
      console.log(line);
      if (line.match(/^\$ cd .+$/)) {
        const dir = line.split(" ")[2];
        if (dir === "/") {
          head = directories;
        } else {
          if (dir !== "..") {
            head.children[dir] = {
              name: dir,
              files: [],
              children: { "..": head },
            };
          }
          head = head.children[dir];
        }
      } else if (!line.match(/^\$ ls$/) && !line.match(/^dir .+$/)) {
        const [size, name] = line.split(" ");
        console.log("Adding file " + name + " to " + head.name);
        head.files.push({ size: Number(size), name });
      }
    });

  console.log(directories);

  console.log(calcSizes(directories));

  console.log(traverse(directories));

  // Part 2
  const total = 70000000;

  const required = 30000000;

  const free = total - directories.size!;

  console.log("-------------");
  console.log(traverseDelete(directories, required - free));
});

const add = (arr: number[]) => arr.reduce((acc, x) => acc + x, 0);

const calcSizes = (dir: Directories): number => {
  const dirSize = add(dir.files.map((d) => d.size));
  const childSizes = add(
    Object.keys(dir.children)
      .filter((key) => key !== "..")
      .map((key) => {
        return calcSizes(dir.children[key]);
      })
  );

  dir.size = dirSize + childSizes;
  return dirSize + childSizes;
};

const traverse = (dir: Directories): number => {
  const childrenSizes = Object.keys(dir.children)
    .filter((key) => key !== "..")
    .reduce((acc, key) => {
      return acc + traverse(dir.children[key]);
    }, 0);

  return childrenSizes + (dir.size! < 100000 ? dir.size! : 0);
};
const traverseDelete = (dir: Directories, req: number): number => {
  const smallestSubDirectory = min(
    Object.keys(dir.children)
      .filter((key) => key !== "..")
      .map((key) => {
        if (dir.children[key].size! >= req) {
          return traverseDelete(dir.children[key], req);
        } else {
          return null;
        }
      })
      .filter((x) => x !== null)
  );

  if (smallestSubDirectory === undefined) {
    return dir.size!;
  } else {
    return smallestSubDirectory!;
  }
};
