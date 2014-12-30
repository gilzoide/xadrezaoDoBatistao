# Gil Barbosa Reis - 8532248
# SCC 604 - POO - Turma C
# 29/06/2014

# Package: Tabuleiro (o tabuleiro do jogo, com suas casas)
src = *.java

.PHONY : all header clean

.SUFFIXES : .java .class
.java.class :
	javac $(JFLAGS) $*.java

# Compila cada .java desse pacote
all : $(src:.java=.class)


# Atualiza a data do cabeçalho de cada um dos arquivos-fonte
header :
	$(HEADERIZE_MAKE)
	$(foreach file, $(src), $(HEADERIZE) $(file);)

# limpa os descartáveis da vida
clean :
	$(RM) *~
