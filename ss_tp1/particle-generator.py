import sys
import random

static_file = open("static.txt", "w")
dinamic_file = open("dinamic.txt", "w")

l = float(sys.argv[1])
n = int(sys.argv[2])
r = float(sys.argv[3])

static_file.write(str(n) + '\n')
static_file.write(str(l) + '\n')

dinamic_file.write('0\n') # time 0

for i in range(0, n):
    x = random.uniform(0, l)
    y = random.uniform(0, l)
    r = random.uniform(0, r)
    static_file.write(str(r) + '\n')
    dinamic_file.write(str(x) + ' ' + str(y) + '\n')
