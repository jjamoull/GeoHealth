# Import the required library
import krippendorff
import numpy as np
import json
import sys

# Compute kippendorff value

data = json.loads(sys.argv[1])
matrix= np.array(data)

alpha= krippendorff.alpha(reliability_data=matrix,level_of_measurent='ordinal')
print(alpha)