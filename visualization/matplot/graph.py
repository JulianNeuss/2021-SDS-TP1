import matplotlib.pyplot as plt

FILE_PATH = '../../data/timegraph.txt'
SEPARATOR = ' '

class TestResult:
    def __init__(self,N,M,L,time,maxtime,mintime):
        self.N = N
        self.M = M
        self.L = L
        self.time = time
        self.maxtime = maxtime
        self.mintime = mintime
    def __repr__(self):
        return "{{N:{},M:{},L:{},time:{},maxtime:{},mintime:{}}}".format(self.N,self.M,self.L,self.time,self.maxtime,self.mintime)

test_results = []
f = open(FILE_PATH,'r')
for l in f.readlines():
    fields = l.split(SEPARATOR)
    test_results.append(TestResult(
        int(fields[0]),int(fields[1]),
        float(fields[2]),float(fields[3]),
        float(fields[4]),float(fields[5])
        ))

g1_x_values = list(set(map(lambda tr:tr.M,test_results)))
g2_x_values = list(set(map(lambda tr:tr.N,test_results)))
g1_x_values.sort()
max_n = max(map(lambda tr:tr.N,test_results))

g1_y_values = []
g1_y_errors = []
for v in g1_x_values:
    g1_y_values.append(next(iter(filter(lambda tr: tr.N == max_n and tr.M == v,test_results))).time)
    g1_y_errors.append(next(iter(filter(lambda tr: tr.N == max_n and tr.M == v,test_results))))
g1_y_errors = list(map(lambda v:(v.mintime,v.maxtime),g1_y_errors))
plt.errorbar(g1_x_values,g1_y_values,yerr = list(map(list, zip(*g1_y_errors))),ecolor="red")
plt.show()