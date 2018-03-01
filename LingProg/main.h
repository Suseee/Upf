//
//  main.h
//  LP Projecto - fase 2
//
//  Created by Suse Ribeiro Francisco Nery on 11/01/18.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define M50 50


/**
 * @brief Estrutura palavra
 */
typedef struct palavra{
	int id;                    //id palavra
	char * palavra;            //palavra repetida
	int nr_repeticoes;         //nr de palavras repetidas
	struct palavra * pnext;    //apontador para o proxima palavra
}PALAVRA;

/**
 * @brief Estrutura indices de palavra
 */
typedef struct indices{
	int id_palavra;
	struct indices * pnext;    //apontador para a primeira palavra
}INDICES;

/**
 * @brief Estrutura utilizador
 */
typedef struct utilizador{
	//    char nome[M50];       //nome utilizador
	int id_utilizador;
	char * nome;
	struct utilizador * pnext;
}UTILIZADOR;


/**
 * @brief Estrutura mensagem
 */
typedef struct mensagem{
	int id_mensagem;           //nr da mensagem
	char * mensagem;           //mensagem
	int id_utilizador;         //id mensagem
	char * time;
	INDICES * indices_pfirst;
	struct mensagem * pnext;   //apontador para a proxima mensagem
}MENSAGEM;

/**
 * @brief Estrutura conversa - todas as conversas
 */
typedef struct conversa{
	int id_conversa;           //nr da conversa
	int numero_mensagens;
	MENSAGEM * mensagens;      //estrutura de mensagem
	PALAVRA * bag_of_words;    //estrutura de palavras
	int nr_utilizadores;
	int id_utilizadores[M50];
	struct conversa * pnext;   //apontador para a proxima conversa
}CONVERSA;

/**
 * @brief Estrutura inicial - aplicaçao mensagem
 */
typedef struct corpus{
	int id_corpus;
	int numero_conversas;
	CONVERSA * conversas;       //estrutura de conversas
	UTILIZADOR * utilizadores;  //estrutura de utilizadores
	struct corpus * pnext;      //apontador para o proximo corpos
}CORPUS;

/**
 * @brief Estrutura inicial - aplicaçao mensagem
 */
typedef struct conjCorpus{
	CORPUS * pfirst;
	int numero_corpus;
}CONJCORPUS;


typedef struct substituicao{
	char * string;
	char * remover;
	int posicao;
}SUBSTITUICAO;

void bag_of_words(CONVERSA * conversa, MENSAGEM * msg, PALAVRA * pals);

int remover(SUBSTITUICAO * args);
int encontrar(SUBSTITUICAO * args, char a_remover[][2]);
PALAVRA * encontrar_palavra (PALAVRA * bag_of_words, char * string);
void inserir_index(int id, MENSAGEM * msg);
PALAVRA * test(char * string);
PALAVRA * inserir_palavra(char * palavra, PALAVRA * pfirst);
void inserir_corpus(CONJCORPUS * c);
void inserir_conversas(CONJCORPUS * c, int id);
void inserir_mensagem(CONJCORPUS * c, int id_corpus, int id_conversa, char * mensagem, int id_utilizador, char * time);
CORPUS * encontrar_corpus (CONJCORPUS * c, int id);
CONVERSA * encontrar_conversa (CORPUS * c, int id);
void print_corpus(CONJCORPUS * c);
void remover_mensagem(CONJCORPUS * c, int id_corpus, int id_conversa, int id_mensagem);
void print_conversas(CONJCORPUS * c);
UTILIZADOR * inserir_utilizador(CONJCORPUS * c, int id_corpus, int id_conversa, char * nome_utilizador);
void gravar_ficheiro(CONJCORPUS * conj, char fn[]);
void ler_ficheiro(CONJCORPUS * c, char fn[]);

void gravar_ficheiro_binario(CONJCORPUS * conj, char fn[]);
void ler_ficheiro_binario(CONJCORPUS * conj, char fn[]);
void print_indices(INDICES * i);
