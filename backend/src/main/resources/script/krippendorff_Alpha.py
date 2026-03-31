# Import the required library
import krippendorff
import numpy as np
import json
import sys

# Compute kippendorff value

data = json.loads(sys.stdin.read())
matrix= np.array(data, dtype=float)

alpha= krippendorff.alpha(reliability_data=matrix,level_of_measurement='ordinal')
print(alpha)