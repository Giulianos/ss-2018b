import sys
import matplotlib.pyplot as plt

# Read file with positions

file = open(sys.argv[1], "r")

bodies = {
'jupiter': [],
'saturn': [],
'sun': [],
'earth': [],
'spaceship': []
}

bodies_colors = {
'jupiter': [[0, 255, 0]],
'saturn': [[255, 0, 0]],
'sun': [[255, 255, 0]],
'earth': [[0, 0, 255]]
}
while True:
    bodies_quantity = file.readline()
    if len(bodies_quantity) == 0:
        break;
    else:
        bodies_quantity = int(bodies_quantity)
        comment = file.readline()
        for i in range(0, bodies_quantity):
            body = file.readline().split(',')
            bodies[body[0]].append((float(body[1]), float(body[2])))

for body in bodies:
    if body != 'saturn' :
        plt.scatter(*zip(*bodies[body]), label=body)
plt.legend()
plt.show()
