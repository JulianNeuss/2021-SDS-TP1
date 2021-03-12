import os
from particle_system import *

DATA_PATH = "./dataExamples"
DYNAMIC_FILE = "dynamicExample.txt"
STATIC_FILE = "staticExample.txt"
OUTPUT_FILE = "outputExample.txt"

def static_file_getter(sim_data,static_file_path):
    STATIC_FILE_SEPARATOR = '    '
    # open static file
    sfile = open(static_file_path,'r')

    # read number of particles and side of simulation
    line = sfile.readline()
    sim_data.num_particles = float(line)
    line = sfile.readline()
    sim_data.simulation_side = float(line)
    
    # parse particles
    line = sfile.readline()
    i = 0
    while line:
        d = line.strip().split(STATIC_FILE_SEPARATOR)
        p = Particle(r=float(d[0]),pr=float(d[1]))
        sim_data.add_particle(p)
        
        # advance loop
        line = sfile.readline()
        i += 1
    # close file
    sfile.close()

    if i != sim_data.num_particles:
        raise Exception("Particle number does not match the number of particles")

def dynamic_file_getter(sim_data,dynamic_file_path):
    DYNAMIC_FILE_SEPARATOR = '  '
    # open dynamic file
    dfile = open(dynamic_file_path,'r')

    # ignore time
    line = dfile.readline()

    # parse particles
    line = dfile.readline()
    i = 0
    while line:
        # check number of particles match else close file and exit
        if i >= sim_data.num_particles:
            dfile.close()
            raise Exception("Particle number does not match the number of particles")
            

        d = line.strip().split(DYNAMIC_FILE_SEPARATOR)
        sim_data.particles[i].x = float(d[0])
        sim_data.particles[i].y = float(d[1])
        
        # advance loop
        line = dfile.readline()
        i += 1
        
    # close file
    dfile.close()

def output_file_getter(sim_data,output_file_path):
    OUTPUT_FILE_SEPARATOR = ','
    # open output file
    ofile = open(output_file_path,'r')

    # parse particles
    line = ofile.readline()
    while line:
        d = line.strip().split(OUTPUT_FILE_SEPARATOR)
        sim_data.neighbours[int(d[0])] = list(map(lambda x: int(x),d[1:]))

        # advance loop
        line = ofile.readline()
        
    # close file
    ofile.close()


def data_getter( static_file_path = None,dynamic_file_path = None,output_file_path = None ):
    if static_file_path == None:
        static_file_path = os.path.join(DATA_PATH,STATIC_FILE)
    if dynamic_file_path == None:
        dynamic_file_path = os.path.join(DATA_PATH,DYNAMIC_FILE)
    if output_file_path == None:
        output_file_path = os.path.join(DATA_PATH,OUTPUT_FILE)

    data = SimulationData()
    static_file_getter( data, static_file_path )
    dynamic_file_getter( data, dynamic_file_path )
    output_file_getter( data, output_file_path )
    return data
