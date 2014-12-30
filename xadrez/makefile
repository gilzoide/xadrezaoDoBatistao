# Gil Barbosa Reis - 8532248
# SCC 604 - POO - Turma C
# 29/06/2014

# Package: Xadrez (motor do jogo)
pacotes = peca tabuleiro movimento
src = *.java

export ROOT BUILD JFLAGS HEADERIZE HEADERIZE_MAKE

.PHONY : all $(pacotes) header clean

.SUFFIXES : .java .class
.java.class :
	javac $(JFLAGS) $*.java

# Compila cada .java desse pacote
all : $(src:.java=.class)

# Compila os subpacotes
$(pacotes) :
	$(MAKE) -C $@ all


# Atualiza a data do cabeçalho de cada um dos arquivos-fonte
header :
	$(HEADERIZE_MAKE)
	$(foreach file, $(src), $(HEADERIZE) $(file);)
	$(foreach pac, $(pacotes), $(MAKE) -C $(pac) header;)

# limpa os descartáveis da vida
clean :
	$(RM) *~
	$(foreach pac, $(pacotes), $(MAKE) -C $(pac) clean;)
