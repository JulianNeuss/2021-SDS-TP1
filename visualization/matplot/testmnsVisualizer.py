import matplotlib.pyplot as plt
import numpy as np

FILE_PATH = '../../data/testmns.txt'
SEPARATOR = ' '

class TestResult:
    def __init__(self,N,M,density,time):
        self.N = N
        self.M = M
        self.density = density
        self.time = time
    def __repr__(self):
        return "{{N:{},M:{},density:{},time:{}}}".format(self.N,self.M,self.density,self.time)

test_results = []
f = open(FILE_PATH,'r')
for l in f.readlines():
    fields = l.split(SEPARATOR)
    test_results.append(TestResult(
        int(fields[0]),int(fields[1]),float(fields[2]),float(fields[3])
        ))

n_label = list(set(map(lambda pr:pr.N,test_results)))
n_label.sort()
m_label = list(set(map(lambda pr:pr.M,test_results)))
m_label.sort()
t_label = list(set(map(lambda pr: pr.time,test_results)))
t_label.sort()

cells = []
for nl in n_label:
    c = []
    for ml in m_label:
        c.append(next(filter(lambda tr:tr.N == nl and tr.M == ml,test_results)).time)
    cells.append(c)


ax = plt.gca()
ax.set_xticks(np.arange(len(m_label)))
ax.set_yticks(np.arange(len(n_label)))
ax.set_xticklabels(m_label)
ax.set_yticklabels(n_label)

for i in range(len(n_label)):
    for j in range(len(m_label)):
        ax.text(j, i, cells[i][j], ha="center", va="center", color="w")

plt.imshow(cells)
plt.show()


# ax = plt.axes(projection='3d')

# zline = t_label
# xline = m_label
# yline = n_label
# ax.plot3D(xline, yline, zline, 'gray')

# zdata = list(map(lambda pr:pr.time,test_results))
# xdata = list(map(lambda pr:pr.M,test_results))
# ydata = list(map(lambda pr:pr.N,test_results))
# ax.scatter3D(xdata, ydata, zdata, c=zdata, cmap='Greens')
# plt.show()