import matplotlib.pyplot as plt
import data_parser as data_parser
import matplotlib.collections
import sys
import argparse

argp = argparse.ArgumentParser(description="Particle neighbour visualizer")
argp.add_argument('--static-file-path', dest='static_path', help='static file path')
argp.add_argument('--dynamic-file-path', dest='dynamic_path', help='dynamic file path')
argp.add_argument('--output-file-path', dest='output_path', help='output file path')
argp.add_argument('pid',nargs='?', help='particle id to highlight')

args = argp.parse_args()

sim_data = data_parser.data_getter(
    static_file_path=args.static_path,
    dynamic_file_path=args.dynamic_path,
    output_file_path=args.output_path
    )

def show_particles(particleid = -1):
    patches = [plt.Circle((p.x,p.y), p.r) for p in  sim_data.particles]
    color = []
    radius_shower = None
    for ind,p in  enumerate(sim_data.particles):
        pid = ind + 1
        if pid == particleid:
            color.append("red")
            if p.pr > 0:
                radius_shower = plt.Circle((p.x,p.y),p.pr + p.r,facecolor=(1,0,0,0.1))
        elif particleid in sim_data.neighbours and pid in sim_data.neighbours[particleid]:
            color.append("yellow")
        else:
            color.append("black")
    ax = plt.gca()
    ax.clear()
    ax.figure.set_size_inches(14,10)
    coll = matplotlib.collections.PatchCollection(patches, facecolors=color)
    if radius_shower != None:
        ax.add_patch(radius_shower)
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
        

if args.pid != None:
    show_particles(int(args.pid))
else:
    show_particles()

plt.gca().figure.canvas.mpl_connect('button_press_event',onclick)
plt.show()