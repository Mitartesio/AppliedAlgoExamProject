from typing import List, Dict, Tuple
import numpy as np  # type: ignore
from typing import List
import csv
import string

# Specifics for generating random input with increasing n
# Seed for reproducibility
SEED = 42
# Maximum iterations for scaling `NS`
I_MAX = 15
# Number of repetitions per value of `n`
M = 5

rng = np.random.default_rng(SEED)

NS: List[int] = [int(10000*1.45**i) for i in range(I_MAX)]

# Specifics for generating random input with 10 million elements
I_MAX2 = 1
# Number of repetitions per value of `n`
M2 = 5

NS2: List[int] = [int(10000000*1**i) for i in range(I_MAX2)]

# Specifics for generating random input increasing to 10 million elements
I_MAX3 = 5
# Number of repetitions per value of `n`
M3 = 5

NS3: List[int] = [int(2000000*(i+1)) for i in range(I_MAX3)]

letters = string.ascii_lowercase

def generateRandomLetters():
    output = ""
    length = rng.integers(1, 20)
    output = [letters[rng.integers(0, 26)] for _ in range(length)]
    return "".join(output)


def generatePresortedArray(array_size: int, presortedness: int) -> List[int]:
    array = rng.integers(1, 2**28, size=array_size).tolist()

    if presortedness == 0:
        # Completely unsorted array (no modification)
        rng.shuffle(array)
    elif presortedness == 1:
        # Partially sorted array (shuffle 50% of it)
        sorted_part = sorted(array)
        partial_array = sorted_part[: array_size // 2]  # Keep first half sorted
        shuffled_part = rng.choice(array, size=array_size // 2, replace=False)
        array = partial_array + shuffled_part.tolist()
        rng.shuffle(array)  # aleast some disorder
    elif presortedness == 2: 
        # Nearly sorted aka sorted with a few inversions
        array.sort()
        for _ in range(5): # 5 random inversions
            idx1 = rng.integers(0, array_size)
            idx2 = rng.integers(0, array_size)
            array[idx1], array[idx2] = array[idx2], array[idx1]
    elif presortedness == 3:  # Fully sorted
        array.sort()
    return array


with open('RandomInputString.csv', 'w', newline='') as f:
    writer = csv.writer(f)

    # Header
    writer.writerow(["n", "values"])

    # I_MAX M times per value and create a random input of ints
    for i in range(I_MAX):
        for _ in range(M):
            writer.writerow([NS[i], " ".join(str(generateRandomLetters()) for _ in range(NS[i]))])
    print("Done generating Random Input Strings")


with open('RandomInputIntegers.csv', 'w', newline='') as f:
    writer = csv.writer(f)

    # Header
    writer.writerow(["n", "values"])

    # I_MAX M times per value and create a random input of ints
    for i in range(I_MAX):
        for _ in range(M):
            writer.writerow([NS[i], " ".join(str(rng.integers(1, 2**28)) for _ in range(NS[i]))])
    print("Done generating Random Input Integers")


with open('HorseRace.csv', 'w', newline='') as f:
    writer = csv.writer(f)

    # Header
    writer.writerow(["n", "values"])

    # I_MAX M times per value and create a random input of ints
    for i in range(I_MAX3):
        for _ in range(M3):
            writer.writerow([NS3[i], " ".join(str(rng.integers(1, 2**28)) for _ in range(NS3[i]))])
    print("Done generating the Horserace")


with open("PresortedRandomInput.csv", "w", newline='') as f:
    writer = csv.writer(f)

    # Header including presortedness level
    writer.writerow(["presortedness","n", "values"])

    for i in range(I_MAX2):
        for _ in range(M2):
            # Generate inputs with varying presortedness
            for presortedness in [
                0, 
                1,
                2,
                3,
            ]:
                array = generatePresortedArray(NS2[i], presortedness)
                writer.writerow([presortedness, NS2[i], " ".join(map(str, array))])
    print("Done generating Random Presorted Input")
