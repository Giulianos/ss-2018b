import matplotlib.pyplot as plt

# Plotting parameters
shown_particle = 1

# Static file loading
static_file = open("static.txt", "r")
static_file_str = static_file.read()
static_file_arr = static_file_str.split('\n');

n = int(static_file_arr[0])
l = float(static_file_arr[1])

particles = list()
for i in range(0, n):
    particle_radius = (static_file_arr[i+2])
    particles.append(float(particle_radius))

# Dinamic file loading
dinamic_file = open("dinamic.txt", "r")
dinamic_file_str = dinamic_file.read()
dinamic_file_arr = dinamic_file_str.split('\n');

for i in range(0, n):
    particle_pos = (dinamic_file_arr[i+1]).split(' ')
    particles[i] = (float(particle_pos[0]), float(particle_pos[1]), particles[i])

# Simulation result file loading
simulation_file = open("simulation.txt", "r")
simulation_file_str = simulation_file.read().replace('[', '').replace(']', '')
simulation_file_arr = simulation_file_str.split('\n');

adjacents = list()

for i in range(0, n):
    sim_result = simulation_file_arr[i].split(' ')
    particle_id = int(sim_result[0])
    if(particle_id == shown_particle):
        for j in range(1, len(sim_result)):
            adjacents.append(int(sim_result[j]))

print adjacents
print particles

print "Number of particles: " + `n`
print "Length of the side: " + `l`

fig, ax = plt.subplots()
plt.axis([0, l, l, 0])

for i in range(0, n):
    if i in adjacents:
        circle = plt.Circle((particles[i][0], particles[i][1]), particles[i][2], color='red')
    elif i == shown_particle:
        circle = plt.Circle((particles[i][0], particles[i][1]), particles[i][2], color='orange')
    else:
        circle = plt.Circle((particles[i][0], particles[i][1]), particles[i][2], color='blue')
    ax.add_artist(circle)

plt.show()