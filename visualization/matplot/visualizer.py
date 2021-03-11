import matplotlib.pyplot as plt
import data_parser as data_parser
import matplotlib.collections
import sys

sim_data = data_parser.data_getter()

def show_particles(id = -1):
    patches = [plt.Circle((p.x,p.y), p.r) for p in  sim_data.particles]
    color = []
    for ind,p in  enumerate(sim_data.particles):
        pid = ind + 1
        if pid == id:
            color.append("red")
        elif id in sim_data.neighbours and pid in sim_data.neighbours[id]:
            color.append("yellow")
        else:
            color.append("black")
    ax = plt.gca()
    ax.clear()
    ax.figure.set_size_inches(14,10)
    coll = matplotlib.collections.PatchCollection(patches, facecolors=color)
    ax.add_collection(coll)
    ax.margins(0.01)
if len(sys.argv) > 1:
    show_particles(int(sys.argv[1]))
    plt.show()