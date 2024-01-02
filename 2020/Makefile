all: out/Start2020.class

out/Start2020.class: src/Start2020.java
	cd src && javac -d ../out Start2020.java


run: all
	cd out && java Start2020 ${day}

clean:
	rm -r out

.PHONY: clean