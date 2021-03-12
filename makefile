compile:
	mkdir -p out
	javac src/main/java/*.java -d out
clean:
	rm -r out
testmns:
	java -cp out TestMNs 
