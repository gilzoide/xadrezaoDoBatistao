# Make do Xadrezão do Batistão

# todos os pacotes do projeto
pacotes = peca gui tabuleiro jogador
src = */*.java


# compila todo o projeto
all : $(pacotes)

.PHONY : $(pacotes) header zip clean

# compila cada pacote, usando o makefile lá dentro
peca :
	$(MAKE) -C $@

gui :
	$(MAKE) -C $@

tabuleiro :
	$(MAKE) -C $@

jogador :
	$(MAKE) -C $@




# Atualiza a data do cabeçalho de cada um dos arquivos-fonte
header :
	$(foreach file, $(src), sed -i "3 c\ * $(shell date +%d/%m/%Y)" $(file))

# zipa o projeto, pra mandar no SSP
zip :
	zip Xadrez -r $(pacotes) makefile

# limpa os descartáveis da vida
clean :
	rm -rf *~ *.class *.zip build/*
