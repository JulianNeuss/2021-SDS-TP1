import matplotlib.pyplot as plt
import numpy as np

FILE_PATH = '../../data/timegraph2.txt'
SEPARATOR = ' '
TEST_TYPE_SEPARATOR = '\n'

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

test_results_same_n = []
test_results_same_m = []
test_type = 0
f = open(FILE_PATH,'r')
for l in f.readlines():
    if l == TEST_TYPE_SEPARATOR:
        test_type += 1
        continue
    if test_type == 0:
        fields = l.split(SEPARATOR)
        test_results_same_n.append(TestResult(
            int(fields[0]),int(fields[1]),
            float(fields[2]),float(fields[3]),
            float(fields[4]),float(fields[5])
            ))
    elif test_type==1:
        fields = l.split(SEPARATOR)
        test_results_same_m.append(TestResult(
            int(fields[0]),int(fields[1]),
            float(fields[2]),float(fields[3]),
            float(fields[4]),float(fields[5])
            ))

g1_x_values = list(map(lambda tr:tr.M,test_results_same_n))
g1_y_values = list(map(lambda tr:tr.time,test_results_same_n))
g1_errors = [[],[]]
for i,e in enumerate(list(map(lambda tr:(tr.mintime,tr.maxtime),test_results_same_n))):
    g1_errors[0].append(g1_y_values[i] - e[0])
    g1_errors[1].append(e[1] - g1_y_values[i])


plt.rcParams["figure.figsize"] = (20,10)
plt.rcParams.update({'font.size': 18})


plt.subplot(1,2,1)
plt.title("f(M)=time")
plt.xlabel("M")
plt.ylabel("t(ms)")
plt.errorbar(g1_x_values,g1_y_values,yerr=g1_errors,ecolor="red")
# plt.show()

g2_x_values = list(map(lambda tr:tr.N,test_results_same_m))
g2_y_values = list(map(lambda tr:tr.time,test_results_same_m))
g2_errors = [[],[]]
for i,e in enumerate(list(map(lambda tr:(tr.mintime,tr.maxtime),test_results_same_m))):
    g2_errors[0].append(g2_y_values[i] - e[0])
    g2_errors[1].append(e[1] - g2_y_values[i])

plt.subplot(1,2,2)
plt.title("f(N)=time")
plt.xlabel("N")
plt.ylabel("t(ms)")
plt.errorbar(g1_x_values,g1_y_values,yerr=g1_errors,ecolor="red")


plt.show()