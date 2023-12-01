import time
from typing import Callable, Tuple
import requests
from ratelimit import limits
import os
from dotenv import load_dotenv

load_dotenv()


def timeSolution(solution: Callable[[str], str], input: str) -> Tuple[str, float]:
    """
    Measures the runtime of a solution function for a given input.

    Args:
        solution (Callable[[str], str]): The solution function to be timed.
        input (str): The input for the solution function.

    Returns:
        Tuple[str, float]: A tuple containing the result of the solution function and the runtime in seconds.
    """
    start_time = time.time()
    res = solution("".join(input))
    runtime = time.time() - start_time
    return res, runtime


@limits(calls=1, period=3 * 60)
def checkLimit():
    """
    Checks the rate limit for API calls and enforces a limit of 1 call per 3 minutes.
    """
    pass


def loadInput(dayNumber: int, verbose: bool) -> str:
    """
    Loads the input for a given day of Advent of Code from adventofcode.com. Note that this requires a session cookie to be set in the .env file.

    Args:
        dayNumber (int): The day number for which to load the input.
        verbose (bool): Whether to print verbose output.

    Returns:
        str: The input content as a string.
    """
    cookie = os.getenv("COOKIE")
    if not cookie:
        print(
            "No cookie found. Please add a .env file with a COOKIE variable containing your session cookie from adventofcode.com. \n An empty file will be created. Please copy the input manually."
        )
        return ""
    url = "https://adventofcode.com/2023/day/%s/input" % dayNumber
    checkLimit()
    if verbose:
        print("Downloading input from %s" % url)

    response = requests.get(
        url,
        headers={
            "Cookie": "session=%s" % cookie,
            "User-Agent": "https://github.com/konradge/advent-of-code-2023 by gellerkonrad@gmail.com",
        },
    )

    if verbose:
        print("Download completed with status code: %s" % response.status_code)

    return response.content.decode("utf-8")
