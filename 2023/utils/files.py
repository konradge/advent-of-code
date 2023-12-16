import os
import shutil


def createEmpty(filename: str, verbose: bool):
    """
    Creates an empty file with the given filename if it does not already exist.

    Args:
        filename (str): The name of the file to be created.
        verbose (bool): If True, prints a message indicating whether the file was created or already exists.

    Returns:
        bool: True if the file was created, False if it already exists.
    """
    createsFile = not os.path.isfile(filename)
    if createsFile:
        with open(filename, "w") as f:
            f.write("")
            if verbose:
                print("File %s created" % f.name)
    elif verbose:
        print("File %s already exists" % filename)
    return createsFile


def copy(source: str, destination: str, verbose: bool):
    """
    Copies a file from the source path to the destination path.

    Args:
        source (str): The path of the source file.
        destination (str): The path of the destination file.
        verbose (bool): If True, prints a message indicating whether the file was copied or already exists.

    Returns:
        None
    """
    if not os.path.isfile(destination):
        shutil.copy(source, destination)
        if verbose:
            print("File %s created" % destination)
    elif verbose:
        print("File %s already exists" % destination)


def read(filename: str, verbose: bool):
    """
    Reads the content of a file.

    Args:
        filename (str): The path of the file to be read.
        verbose (bool): If True, prints a message indicating that the file was read.

    Returns:
        str: The content of the file.
    """
    with open(filename) as f:
        content = f.read()
        if verbose:
            print("File %s read" % f.name)
        return content.strip()


def write(filename: str, content: str, verbose: bool):
    """
    Writes the given content to a file.

    Args:
        filename (str): The path of the file to be written.
        content (str): The content to be written to the file.
        verbose (bool): If True, prints a message indicating that the file was written.

    Returns:
        None
    """
    with open(filename, "w") as f:
        f.write(content)
        if verbose:
            print("File %s written" % f.name)
