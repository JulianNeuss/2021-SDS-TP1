class Particle:
    def __init__( self, x = 0, y = 0, r = 0,pr = 0):
        self.x = x
        self.y = y
        self.r = r
        self.pr = pr
    def __repr__(self):
        return "{{ x:{}, y:{}, r:{}, pr:{} }}".format(self.x,self.y,self.r,self.pr)

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
