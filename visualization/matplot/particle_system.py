class Particle:
    def __init__( self, x = 0, y = 0, r = 0,unnamed_prop = 0):
        self.x = x
        self.y = y
        self.r = r
        self.unnamed_prop = unnamed_prop
    def __repr__(self):
        return "{{ x:{}, y:{}, r:{}, unnamed:{} }}".format(self.x,self.y,self.r,self.unnamed_prop)

class SimulationData:
    def __init__(self):
        self.num_particles = 0
        self.simulation_side = 0
        self.particles = []
        self.neighbours = {}

    def add_particle(self,particle):
        self.particles.append(particle)

    def __repr__(self):
        return "{{ num_particles:{}, simulation_side:{}, particles:{}, neighbours:{} }}".format(
            self.num_particles,
            self.simulation_side,
            self.particles,
            self.neighbours,
        )
