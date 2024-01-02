all: out/Main.class

out/Main.class: src/Main.java
	cd src && javac -d ../out Main.java

run: all
	cd out && java Main ${day}

clean:
	rm -r out

.PHONY: clean