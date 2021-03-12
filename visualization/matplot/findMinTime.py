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

for n in n_label:
    print(min(filter(lambda t:t.N == n, test_results),key=lambda t:t.time))