import sys
import random
import math

static_file = open("static.txt", "w")
dinamic_file = open("dinamic.txt", "w")

l = float(sys.argv[1])
n = int(sys.argv[2])
r1 = float(sys.argv[3])
randR = int(sys.argv[4])

static_file.write(str(n) + '\n')
static_file.write(str(l) + '\n')

dinamic_file.write('0\n') # time 0

for i in range(0, n):
    x = random.uniform(0, l)
    y = random.uniform(0, l)
    r = random.uniform(0.75*r1, r1) if randR == 1 else r1
    theta = random.uniform(0, 2*math.pi);
    vx = 0.03 * math.cos(theta)
    vy = 0.03 * math.sin(theta)
    static_file.write(str(r) + '\n')
    dinamic_file.write(str(x) + ' ' + str(y)+ ' ' + str(vy)+ ' ' + str(vy) + '\n')
