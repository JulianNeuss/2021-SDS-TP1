import matplotlib.pyplot as plt
import data_parser as data_parser

sim_data = data_parser.data_getter()
x_list = list(map(lambda p:p.x,sim_data.particles))
y_list = list(map(lambda p:p.y,sim_data.particles))
sizes_list = list(map(lambda p:p.r*100,sim_data.particles))

# show particles
plt.figure(figsize=(12,10))
plt.plot(x_list,y_list,'o')
plt.axis([0,sim_data.simulation_side,0,sim_data.simulation_side])
plt.show()