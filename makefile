# Make do Xadrezão do Batistão

# pacotes do projeto
pacotes = xadrez ui
src = */*.java


# compila todo o projeto
all : $(pacotes)

.PHONY : $(pacotes) run header zip clean
# compila cada pacote, usando o makefile lá dentro
xadrez :
	$(MAKE) -C $@

ui :
	$(MAKE) -C $@



# roda o projeto compilado
run :
	@java -classpath build/ xadrez.Xadrez


# Atualiza a data do cabeçalho de cada um dos arquivos-fonte
header :
	$(foreach file, $(src), sed -i "3 c\ * $(shell date +%d/%m/%Y)" $(file))

# zipa o projeto, pra mandar no SSP
zip :
	zip Xadrez -r $(pacotes) makefile

# limpa os descartáveis da vida
clean :
	rm -rf *~ *.class *.zip build/*
