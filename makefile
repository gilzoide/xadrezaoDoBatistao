# Gil Barbosa Reis - 8532248
# SCC 604 - POO - Turma C
# 17/03/2014

# Make do Xadrezão do Batistão


# root dir: lugar dos arquivos-fonte .java
export ROOT := $(CURDIR)
# build dir: lugar dos arquivos-objetos .class
export BUILD := $(ROOT)/build
# flags pro javac
export JFLAGS := -d $(BUILD) -sourcepath $(ROOT) -g
# comando no sed pra atualizar a data dos cabeçalhos
export HEADERIZE = sed -i "3 c\ * $(shell date +%d/%m/%Y)"
export HEADERIZE_MAKE = sed -i "3 c\# $(shell date +%d/%m/%Y)" makefile

# pacotes do projeto
pacotes = xadrez ui


# compila todo o projeto
all : $(pacotes)
	cp -r ui/img build/ui

.PHONY : $(pacotes) run header zip clean debug
# compila cada pacote, usando o makefile lá dentro
$(pacotes) :
	$(MAKE) -C $@ all



# roda o projeto compilado
run :
	@java -classpath $(BUILD) xadrez.Xadrez

debug :
	jdb -classpath $(BUILD) xadrez.Xadrez


# Atualiza a data do cabeçalho de cada um dos arquivos-fonte
header :
	$(HEADERIZE_MAKE)
	$(foreach pac, $(pacotes), $(MAKE) -C $(pac) header;)

# zipa o projeto, pra mandar no SSP
zip :
	zip Xadrez -r $(pacotes) makefile

# limpa os descartáveis da vida
clean :
	$(RM) -r *~ *.zip build/*
	$(foreach pac, $(pacotes), $(MAKE) -C $(pac) clean;)
