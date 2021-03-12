compile:
	mkdir -p out
	javac src/main/java/*.java -d out
clean:
	rm -r out

ifneq ($(minN),)
override minN := -DminN=$(minN)
endif
ifneq ($(maxN),)
override maxN := -DmaxN=$(maxN)
endif
ifneq ($(rc),)
override rc := -Drc=$(rc)
endif
ifneq ($(r),)
override r := -Dr=$(r)
endif
ifneq ($(L),)
override L := -DL=$(L)
endif
ifneq ($(dataPath),)
override dataPath := -DdataPath="$(dataPath)"
endif
ifneq ($(dataFilename),)
override dataFilename := -DdataFilenam="$(dataFilename)"
endif
ifneq ($(dynamicFilename),)
override dynamicFilename := -DdynamicFilename=$(dynamicFilename)
endif
ifneq ($(staticFilename),)
override staticFilename := -DstaticFilename=$(staticFilenamer)
endif
ifneq ($(outputFilename),)
override outputFilename := -DoutputFilename=$(outputFilename)
endif
ifneq ($(periodicBorder),)
override periodicBorder := -DperiodicBorder=$(periodicBorder)
endif

testmns:
	java $(minN) $(maxN) $(rc) $(r) $(L) $(dataPath) $(dataFilename) -cp out TestMNs
simulationApp:
	java  $(dynamicFilename) $(staticFilename) $(outputFilename) $(periodicBorder) $(N) $(rc) $(r) $(L) -cp out SimulationApp

generateFiles:
	java  $(dynamicFilename) $(staticFilename) $(N) $(rc) $(r) $(L) -cp out GenerateFiles

data_visualizer:
	@bash -c "cd visualization/matplot;source .env/bin/activate;python visualizer.py"

testmns_visualizer:
	@bash -c "cd visualization/matplot;source .env/bin/activate;python testmnsVisualizer.py"