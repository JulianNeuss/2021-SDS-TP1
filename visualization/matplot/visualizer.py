import matplotlib.pyplot as plt
import data_parser as data_parser
import matplotlib.collections
import sys

sim_data = data_parser.data_getter()

def show_particles(particleid = -1):
    patches = [plt.Circle((p.x,p.y), p.r) for p in  sim_data.particles]
    color = []
    for ind,p in  enumerate(sim_data.particles):
        pid = ind + 1
        if pid == particleid:
            color.append("red")
        elif particleid in sim_data.neighbours and pid in sim_data.neighbours[particleid]:
            color.append("yellow")
        else:
            color.append("black")
    ax = plt.gca()
    ax.clear()
    ax.figure.set_size_inches(14,10)
    coll = matplotlib.collections.PatchCollection(patches, facecolors=color)
    ax.add_collection(coll)
    ax.margins(0.01)
    ax.figure.canvas.draw()

# finish this
def onclick(event):
    particle_found = False
    for ind, p in enumerate(sim_data.particles):
        pid = ind + 1
        dist = (event.xdata - p.x) ** 2 + (event.ydata - p.y) ** 2
        if dist <= p.r:
            particle_found = True
            show_particles(pid)
        

if len(sys.argv) > 1:
    show_particles(int(sys.argv[1]))
else:
    show_particles()

plt.gca().figure.canvas.mpl_connect('button_press_event',onclick)
plt.show()