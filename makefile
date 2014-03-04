# Make do Xadrezão do Batistão

export ROOT := $(CURDIR)		# root dir: lugar dos arquivos-fonte .java
export BUILD := $(ROOT)/build	# build dir: lugar dos arquivos-objetos .class
export JFLAGS := -d $(BUILD) -sourcepath $(ROOT)	# flags pro javac
export HEADERIZE = sed -i "3 c\ * $(shell date +%d/%m/%Y)"

# pacotes do projeto
pacotes = xadrez ui


# compila todo o projeto
all : $(pacotes)

.PHONY : $(pacotes) run header zip clean
# compila cada pacote, usando o makefile lá dentro
$(pacotes) :
	$(MAKE) -C $@ all



# roda o projeto compilado
run :
	@java -classpath $(BUILD) xadrez.Xadrez


# Atualiza a data do cabeçalho de cada um dos arquivos-fonte
header :
	$(foreach pac, $(pacotes), $(MAKE) -C $(pac) header;)

# zipa o projeto, pra mandar no SSP
zip :
	zip Xadrez -r $(pacotes) makefile

# limpa os descartáveis da vida
clean :
	$(RM) -r *~ *.zip build/*
	$(foreach pac, $(pacotes), $(MAKE) -C $(pac) clean;)
