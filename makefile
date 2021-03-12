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
testmns:
	java $(minN) $(maxN) $(rc) $(r) $(L) -cp out TestMNs
