//
//  main.c
//  LP Projecto - fase 2
//
//  Created by Suse Ribeiro Francisco Nery on 11/01/18.
//

#include "main.h"


/**
 * @brief função por onde o programa começa
 */
int main(int argc, const char * argv[]) {
	CONJCORPUS conjCorpus = {NULL, 0};
	
PALAVRA palavra={0,"ola",3,NULL};
	INDICES index={0,&palavra};
	
	//char * path = "/Users/suseribeiro/Desktop/conversa.txt";
	//char * path_binario = "/Users/suseribeiro/Desktop/conversa_bin.bin";
	char * path = "/Users/francisconery/Desktop/conversa.txt";
	char * path_binario = "/Users/francisconery/Desktop/conversa_bin.bin";
	
	ler_ficheiro(&conjCorpus,path);
	//ler_ficheiro_binario(&conjCorpus, path_binario);
	
	//UTILIZADOR * userSuse = inserir_utilizador(&conjCorpus, 1,1, "Suse");
	//UTILIZADOR * userXico = inserir_utilizador(&conjCorpus, 1,1, "xico");
	//inserir_corpus(&conjCorpus);
	//inserir_conversas(&conjCorpus, 1);
	//inserir_conversas(&conjCorpus, 1);
	//inserir_mensagem(&conjCorpus, 1, 1,"Ola tudo bem?", 1, "20180112133045");
	//inserir_mensagem(&conjCorpus, 1, 1,"Esta tudo e contigo?", 2, "20180112134045");
	//inserir_mensagem(&conjCorpus, 1, 1,"Tambem obrigada.", 1, "20180112134445");
	//inserir_mensagem(&conjCorpus, 1,2, "Vamos ao cinema hoje?", 1, "20180112134045");
	//inserir_mensagem(&conjCorpus, 1,2, "Pode ser.", 2, "20180112134050");
	//remover_mensagem(&conjCorpus, 1,1,3);
	//remover_conversa(&conjCorpus, 1, 1);
	//inserir_utilziador(&conjCorpus,1,1, "Francisco e Susana");
	
	print_corpus(&conjCorpus);
	print_indices(&index);
	
	gravar_ficheiro(&conjCorpus, path);
//	gravar_ficheiro_binario(&conjCorpus, path_binario);
	return 0;
}

/**
 * @brief Insere um novo corpus
 * @param c CONJCORPUS
 */
void inserir_corpus(CONJCORPUS * c){
	
	// cria novo corpus
	CORPUS * corpus = (CORPUS*)malloc(sizeof(CORPUS));
	corpus->utilizadores = NULL;
	corpus->conversas = NULL;
	
	corpus->id_corpus = 1;
	corpus->pnext = NULL;
	corpus->numero_conversas = 0;
	
	c->numero_corpus++;
	
	// verifica se nao existe e insere
	if (c->pfirst == NULL){
		//        printf("Insere o primeiro corpus\n");
		c->pfirst = corpus;
		return;
	}
	
	CORPUS * cAux = c->pfirst;
	
	corpus->id_corpus = 2;
	// se existe percorre todos
	while (cAux->pnext != NULL) {
		corpus->id_corpus++;
		cAux = cAux->pnext;
	}
	
	//    printf("Insere na cauda.\n");
	cAux->pnext = corpus;
}


/**
 * @brief Insere uma nova conversa
 * @param c CONJCORPUS
 * @param id_corpus id a inserir
 */
void inserir_conversas(CONJCORPUS * c, int id_corpus){
	
	// cria nova conversa
	CONVERSA * conversa = (CONVERSA*)malloc(sizeof(CONVERSA));
	conversa->mensagens = NULL;
	conversa->bag_of_words = NULL;
	
	conversa->id_conversa = 1;
	conversa->nr_utilizadores =0;
	conversa->pnext = NULL;
	
	CORPUS * corpus = NULL;
	
	
	if ((corpus = encontrar_corpus(c, id_corpus)) == NULL){
		free(conversa);
		return;
	}
	
	corpus->numero_conversas++;
	
	// insere a convesa na cauda
	if (corpus->conversas == NULL) {
		//        printf("Inseriu a conversa\n");
		corpus->conversas = conversa;
		return;
	}
	
	//percorre todas ate econtrar a ultima
	CONVERSA * cAux = corpus->conversas;
	//Remove
	conversa->id_conversa = 2;
	while (cAux->pnext != NULL) {
		
		conversa->id_conversa++;
		cAux = cAux->pnext;
	}
	cAux->pnext = conversa;
}


/**
 * @brief Insere uma nova mensagem
 * @param c CONJCORPUS
 * @param id_corpus corpus onde inserir mensagem
 * @param id_conversa concersa onde inserir mensagem
 * @param mensagem mensagem a inserir
 */
void inserir_mensagem(CONJCORPUS * c, int id_corpus, int id_conversa, char * mensagem, int id_utilizador, char * time){
	
	MENSAGEM * msg=(MENSAGEM*)malloc(sizeof(MENSAGEM));
	
	msg->id_mensagem = 1;
	msg->mensagem=(char*)malloc(sizeof(char) * strlen(mensagem));
	msg->id_utilizador = id_utilizador;
	strcpy(msg->mensagem, mensagem);
	msg->pnext = NULL;
	msg->time = (char*)malloc(sizeof(char) * strlen(time));
	msg->indices_pfirst = NULL;
	strcpy(msg->time, time);
	
	CORPUS * corpus = NULL;
	CONVERSA * conversa = NULL;
	
	//procura o corpus a inserir
	if ((corpus = encontrar_corpus(c, id_corpus)) == NULL){
		free(msg->mensagem);
		free(msg);
		return;
	}
	
	//procura a conversa a inserir dentro do corpus
	if ((conversa = encontrar_conversa(corpus, id_conversa)) == NULL){
		free(msg->mensagem);
		free(msg);
		return;
	}
	
	// retorna lista ligada palavras
	PALAVRA * lista_palavras = test(mensagem); // inicio da lista ligada de palavras
	bag_of_words(conversa, msg, lista_palavras);
	conversa->numero_mensagens++;
	
	// insere a mensagem na cauda
	if(conversa->mensagens == NULL){
		conversa->mensagens = msg;
		return;
	}
	
	//percorre todas ate encontrar a ultima
	MENSAGEM * cAux = conversa->mensagens;
	msg->id_mensagem = 2;
	while (cAux->pnext != NULL) {
		//Remove
		msg->id_mensagem++;
		cAux = cAux->pnext;
	}
	
	cAux->pnext = msg;
}


void bag_of_words(CONVERSA * conversa, MENSAGEM * msg, PALAVRA * pals){
	PALAVRA * result = NULL;
	
	while(pals != NULL){
		
		if ((result = encontrar_palavra(conversa->bag_of_words, pals->palavra))){ // se a palavra existir, incrementa o nr e vai buscar o id
			result->nr_repeticoes++;
		}else{
			//insere na bag
			conversa->bag_of_words = inserir_palavra(pals->palavra, conversa->bag_of_words); // retorna sempre o inicio da lista ligada
			result = encontrar_palavra(conversa->bag_of_words, pals->palavra);
		}
		
		inserir_index(result->id, msg); // insere o index
		pals = pals->pnext;
	}
}
/**
 * Inserir os inserir os indices na mensagem
 *
 */
void inserir_index(int id, MENSAGEM * msg){
	INDICES * index = (INDICES*)malloc(sizeof(INDICES));
	index->id_palavra = id;
	index->pnext = NULL;
	
	// insere o indice se for diferente de null
	if (msg->indices_pfirst == NULL){
		msg->indices_pfirst = index;
		return;
	}
	
	INDICES * paux = msg->indices_pfirst;
	// insere a cauda
	while(paux->pnext != NULL){
		paux = paux->pnext;
	}
	paux->pnext = index;
}


/**
 * Funçao que encontra uma palavra
 * Retorna a posição da palavra entrada
 **/
PALAVRA * encontrar_palavra (PALAVRA * bag_of_words, char * string){
	PALAVRA * paux = bag_of_words;
	
	//se não encontrar retorna NULL
	while(paux != NULL){
		if (strcmp(paux->palavra, string) == 0){
			return paux;
		}
		paux = paux->pnext;
	}
	return NULL;
}

/**
 * @brief Verifica se o corpus existe e retorna NULL ou o endereço de memoria onde esta o corpus
 * @param c corpus
 * @param id id do corpus
 **/
CORPUS * encontrar_corpus (CONJCORPUS * c, int id){
	CORPUS * corpus = c->pfirst;
	
	//pesquisa o corpus pelo id
	while (corpus != NULL) {
		if (corpus->id_corpus == id){
			break;
		}
		corpus = corpus->pnext;
	}
	
	// se nao exitir sai fora
	if (corpus == NULL){
		printf("Nao foi encontrado corpus\n");
		return NULL;
	}
	return corpus;
}


/**
 * @brief Verifica se a conversa existe e retorna NULL ou o endereço de memoria onde esta a conversa
 * @param c corpus
 * @peram id id do corpus
 **/
CONVERSA * encontrar_conversa (CORPUS * c, int id){
	CONVERSA * conversa = c->conversas;
	
	//pesquisa o corpus pelo id
	while (conversa != NULL) {
		if (conversa->id_conversa == id){
			break;
		}
		
		conversa = conversa->pnext;
	}
	
	// se nao exitir sai fora
	if (conversa == NULL){
		printf("Nao foi encontrada conversa\n");
		return NULL;
	}
	
	return conversa;
}


/**
 * @brief Remover uma nova mensagem
 * @param c CONJCORPUS
 * @param id_corpus id do corpos onde esta mensagem a remover
 * @param id_conversa is da conversa odne esta mensagem a remover
 * @param id_mensagem mensagem a remover
 */
void remover_mensagem(CONJCORPUS * c, int id_corpus, int id_conversa, int id_mensagem){
	
	CORPUS * corpus = NULL;
	CONVERSA * conversa = NULL;
	
	
	//procura o corpus a remover
	if ((corpus = encontrar_corpus(c, id_corpus)) == NULL){
		return;
	}
	
	//procura a conversa a remover dentro do corpus
	if ((conversa = encontrar_conversa(corpus, id_conversa)) == NULL){
		return;
	}
	
	//remove a msg
	if (conversa->mensagens->id_mensagem == id_mensagem) {
		conversa->mensagens = conversa->mensagens->pnext;
		conversa->numero_mensagens--;
		return;
	}
	
	MENSAGEM * msg = conversa->mensagens;
	MENSAGEM * msgAux = conversa->mensagens;
	
	// apontador da msg actual passa para a msg anterior e aponta para o pnext
	while (msg != NULL){
		
		if (msg->id_mensagem == id_mensagem){
			msgAux->pnext = msg->pnext;
			conversa->numero_mensagens--;
			return;
		}
		
		msgAux = msg;
		msg = msg->pnext;
	}
	
	printf("Não foi encontrada a mensagem!\n");
}

/**
 * @brief Remover uma nova conversa
 * @param c CONJCORPUS
 * @param id_corpus corpus onde esta conversa a remover
 * @param id_conversa conversa a remover
 */
void remover_conversa(CONJCORPUS * c, int id_corpus, int id_conversa){
	
	CORPUS * corpus = NULL;
	CONVERSA * conversa = NULL;
	
	//procura o corpus da conversa  e remove
	if ((corpus = encontrar_corpus(c, id_corpus)) == NULL){
		return;
	}
	
	//procura a conversa a remover dentro do corpus
	if ((conversa = encontrar_conversa(corpus, id_conversa)) == NULL){
		return;
	}
	
	//remove a conversa
	if(corpus->conversas->id_conversa == id_conversa){
		corpus->conversas = corpus->conversas->pnext;
		corpus->numero_conversas--;
		return;
	}
	
	
	CONVERSA * conv = corpus->conversas;
	CONVERSA * convAux = corpus->conversas;
	
	
	while (conv != NULL){
		
		if (conv->id_conversa == id_conversa){
			convAux->pnext = conv->pnext;
			corpus->numero_conversas--;
			return;
		}
		
		convAux = conv;
		conv = conv->pnext;
	}
	
	printf("Não foi encontrada a conversa!\n");
}


/**
 * @brief Imprime todos os corpus
 * c conjunto corpus
 **/
void print_corpus(CONJCORPUS * c){
	CORPUS * corpus = c->pfirst;
	CONVERSA * conversa;
	MENSAGEM * mensagem;
	//    UTILIZADOR * utilizador = NULL;
	
	while (corpus != NULL) {
		conversa = corpus->conversas;
		printf("Corpus = %d\n",corpus->id_corpus);
		while (conversa != NULL) {
			mensagem = conversa->mensagens;
			printf("Conversa = %d\n",conversa->id_conversa);
			//          printf("\t\tUtilizador : %s\n",corpus->utilizadores->nome);
			while (mensagem != NULL) {
				printf("\t%d = %s\n", mensagem->id_utilizador, mensagem->mensagem);
				mensagem = mensagem->pnext;
			}
			conversa = conversa->pnext;
		}
		corpus = corpus->pnext;
	}
}


/**
 * @brief Inserir um utilizador no corpus
 * @param c CONJCORPUS
 * @param id_corpus corpus onde vai ser inserido user
 * @param id_conversa conversa onde vai ser inserido user
 * @param nome_utilizador user a inserir
 */
UTILIZADOR * inserir_utilizador(CONJCORPUS * c, int id_corpus, int id_conversa, char * nome_utilizador){
	
	CORPUS * corpus = NULL;
	
	UTILIZADOR * utilizador=(UTILIZADOR*)malloc(sizeof(UTILIZADOR));
	
	utilizador->id_utilizador = 1;
	
	utilizador->nome =(char*)malloc(sizeof(char) * strlen(nome_utilizador));
	strcpy(utilizador->nome, nome_utilizador);
	utilizador->pnext = NULL;
	
	
	//procura se o corpus a inserir existe
	if((corpus = encontrar_corpus(c, id_corpus)) == NULL){
		free(utilizador->nome);
		free(utilizador);
		return utilizador;
	}
	
	//insere o utilizador
	if(corpus->utilizadores == NULL){
		corpus->utilizadores = utilizador;
		return utilizador;
	}
	
	//percorre todos os utilizadores ate encontrar o ultimo e insere
	UTILIZADOR * cAux = corpus->utilizadores;
	utilizador->id_utilizador = 2;
	
	while (cAux->pnext != NULL) {
		
		utilizador->id_utilizador++;
		cAux = cAux->pnext;
	}
	
	cAux->pnext = utilizador;
	return utilizador;
}

/**
 * @brief Gravar no ficheiro
 * @param conj CONJCORPUS
 */
void gravar_ficheiro(CONJCORPUS * conj, char fn[]){
	FILE *fp;
	CONVERSA * paux;
	CORPUS * crp;
	MENSAGEM * maux;
	
	if(conj->pfirst == NULL){
		printf("Estrutura vazia!\n");
		return;
	}
	
	fp=fopen(fn,"w");
	
	fprintf(fp, "%d\n", conj->numero_corpus);
	crp = conj->pfirst;
	while (crp != NULL) {
		fprintf(fp, "%d\n", crp->numero_conversas);
		paux = crp->conversas;
		
		while (paux != NULL) {
			fprintf(fp, "%d\n", paux->numero_mensagens);
			maux = paux->mensagens;
			
			while (maux != NULL) {
				fprintf(fp, "%d|%s:%s\n",maux->id_utilizador, maux->time ,maux->mensagem);
				maux = maux->pnext;
			}
			
			paux = paux->pnext;
		}
		
		crp = crp->pnext;
	}
	
	fclose(fp);
}

/**
 * @brief Gravar no ficheiro binario
 * @param conj CONJCORPUS
 */
void gravar_ficheiro_binario(CONJCORPUS * conj, char fn[]){
	FILE *fp;
	CONVERSA * paux;
	CORPUS * crp;
	MENSAGEM * maux;
	unsigned long time=0;
	unsigned long  mensagem=0;
	
	fp=fopen(fn,"wb");
	
	fwrite(&conj->numero_corpus,sizeof(int),1,fp); // grava id corpus
	crp = conj->pfirst;
	while (crp != NULL) {
		fwrite(&crp->numero_conversas,sizeof(int),1,fp); //grava o nr de conversas
		paux = crp->conversas;
		while (paux != NULL) {
			fwrite(&paux->numero_mensagens,sizeof(int),1,fp); // grava o numero de msg
			maux = paux->mensagens;
			while (maux != NULL) {
				fwrite(&maux->id_utilizador, sizeof(int), 1, fp); // grava o id utilizador
				
				time=strlen(maux->time)+1; // +1 para o /0
				fwrite(&time, sizeof(int), 1, fp); // guarda o tamanho do time
				fwrite(maux->time, 1, time, fp); // escreve o time
				
				mensagem=strlen(maux->mensagem)+1;
				fwrite(&mensagem, sizeof(int), 1, fp);
				fwrite(maux->mensagem, 1, mensagem, fp);
				
				maux = maux->pnext;
			}
			paux = paux->pnext;
		}
		crp = crp->pnext;
	}
	fclose(fp);
}

/*
 * @brief  Ler do ficheiro
 *
 */
void ler_ficheiro(CONJCORPUS * c, char * fn){
	FILE *fp=NULL;
	
	fp=fopen(fn,"r");
	
	int numero_corpus, numero_conversas, numero_mensagens, id_utitizador;
	char buf[300], * msg, * time, * id;
	fscanf(fp, "%d", &numero_corpus);// ler numero de corpus
	
	for (int i = 0; i < numero_corpus; i++){
		inserir_corpus(c);
		fscanf(fp, "%d", &numero_conversas); // ler numero de conversas
		for (int j = 0; j < numero_conversas; j++){
			fscanf(fp, "%d", &numero_mensagens);// ler numero de mensagens
			inserir_conversas(c, i + 1); // insere mais uma conversa
			fgetc(fp);// retira o \n
			for (int k = 0; k < numero_mensagens; k++){
				fgets(buf, sizeof(char) * 200, fp); // alocar espaço para 200 chars
				id = strtok(buf, "|"); // le o char id
				id_utitizador = atoi(id); // char converte em inteiros - id utilizador
				time = strtok(NULL, ":");
				msg = strtok(NULL, "\n");
				
				inserir_mensagem(c, i + 1, j + 1, msg, id_utitizador, time);//insere msg
			}
		}
	}
	fclose(fp);
}

/**
 * @brief Ler do ficheiro binario
 * @param conj CONJCORPUS
 */
void ler_ficheiro_binario(CONJCORPUS * conj, char fn[]){
	FILE *fp;
	CONVERSA * paux;
	CORPUS * crp;
	MENSAGEM * maux;
	unsigned long time=0;
	unsigned long  mensagem=0;
	
	fp=fopen(fn,"rb");
	
	fwrite(&conj->numero_corpus,sizeof(int),1,fp); // le id corpus
	crp = conj->pfirst;
	while (crp != NULL) {
		fwrite(&crp->numero_conversas,sizeof(int),1,fp); //le o nr de conversas
		paux = crp->conversas;
		while (paux != NULL) {
			fwrite(&paux->numero_mensagens,sizeof(int),1,fp); // le o numero de msg
			maux = paux->mensagens;
			while (maux != NULL) {
				fread(&maux->id_utilizador, sizeof(int), 1, fp); // le o id utilizador
				fwrite(maux->time, 1, time, fp); // le o time
				fwrite(maux->mensagem, 1, mensagem, fp); // le a msg
				maux = maux->pnext;
			}
			paux = paux->pnext;
		}
		crp = crp->pnext;
	}
	fclose(fp);
}




/**
 * @brief Separar uma string em palavras e caracteres.
 * @param string original para separar
 */
PALAVRA * test(char * string){
	
	
	char * clone = strdup(string); //duplica a string
	char * token;
	char * end_strtok; // posicao de um caracter dentro da string
	SUBSTITUICAO * args = (SUBSTITUICAO*)malloc(sizeof(SUBSTITUICAO));
	args->remover = NULL;
	PALAVRA * palavra = NULL;
	
	int flag = 0; //flag indentifica os caracteres especiais
	
	token = strtok_r(clone, " ", &end_strtok); //parte as palavras por espaços 🤷‍♂
	while (token != NULL) {
		args->string = (char*)malloc(sizeof(char) * strlen(token));
		strcpy(args->string, token);
		//enquanto houver caracateres especiais
		while (remover(args)) {
			// se for =1 esta no fim senao foi removido
			if (args->posicao == 1){
				palavra = inserir_palavra(args->string, palavra);
				palavra = inserir_palavra(args->remover, palavra);
			}else{
				palavra = inserir_palavra(args->remover, palavra);
			}
			free(args->remover);
			flag = 1;
		}
		//se a flag=0 nao exite caracteres especiais
		if (!flag){
			palavra = inserir_palavra(args->string, palavra);
		}
		
		flag = 0;
		
		free(args->string);
		token = strtok_r(NULL, " ", &end_strtok);
		
		//se for diferente ainda ha espaços
		if (token != NULL){
			inserir_palavra(" ", palavra);
		}
	}
	
	free(args);
	return palavra;
}

/**
 *
 * Funçao que insere uma palvra na estrutura
 * Retorna o apontador para a primeira palavra
 */
PALAVRA * inserir_palavra(char * palavra, PALAVRA * pfirst){
	PALAVRA * plv = (PALAVRA*)malloc(sizeof(PALAVRA));
	plv->nr_repeticoes = 1;
	plv->id = 0;
	plv->palavra= malloc(sizeof(char) * strlen(palavra)); //aloca espaço para nova palavra
	strcpy(plv->palavra, palavra);
	plv->pnext = NULL;
	
	// insere a primeira palavra
	if (pfirst == NULL){
		return plv;
	}
	
	
	PALAVRA * paux = pfirst;
	plv->id = 1;
	
	// insere na cauda a proxima palavra
	while(paux->pnext != NULL){
		plv->id++;
		paux = paux->pnext;
	}
	
	paux->pnext = plv;
	return pfirst;
}

/**
 * @brief Remover um caracter especial de uma string.
 * @param args Struct com toda a informação da remoção. Posição, caracter removido
 * @return Bool. 1 (True) Caso seja removido algo, 0 (falso) caso nao tenha removido nada.
 */
int remover(SUBSTITUICAO * args){
	char a_remover[][2] = {"?", "!", "(", ")", ",", "-"};
	
	char * result = (char*)calloc((strlen(args->string)), sizeof(char)); //limpa toda as posições usadas
	char * token;
	char * end_strtok2; // variavel que guarda o estado da string
	
	//nao encontra retorna 0
	if (!encontrar(args, a_remover))
		return 0;
	
	//args->remover - caracter especial
	token = strtok_r(args->string, args->remover, &end_strtok2); //remoçao do caracter especial
	
	while (token != NULL) {
		strcat(result, token); //
		token = strtok_r(NULL, args->remover, &end_strtok2);
	}
	
	free(args->string);
	
	args->string = (char*)malloc(sizeof(char) * strlen(result));
	strcpy(args->string, result);
	return 1;
}

/**
 * @brief Função para pesquisar sempre o primeiro caracter da string para remover.
 * @param args Struct com toda a informação da remoção. Posição, caracter removido.
 * @param to_remove Matriz com os caracteres para remover.
 * @return Bool. 1 (True) Caso seja encontrado algum, 0 (falso) caso nao seja encontrado nada.
 */
int encontrar (SUBSTITUICAO * args, char to_remove[][2]) {
	
	char * aux;
	char finded[2];
	long pos_finded = -1;
	
	for(int i = 0; i < 6; i++){
		//char a_remover[][2] = {"?", "!", "(", ")", ",", "-"};
		//(tudo?
		
		// strstr("tudo?", "?") -> "?" strlen("?") == 1
		// strstr("tudo?", "(") -> "(tudo?" strlen("(tudo?") == 6
		
		//strstr procura de uma string para frente
		if ((aux = strstr(args->string, to_remove[i])) != NULL){
			// entra no if quando chega ao fim da frase
			//se o strlen for superior a posicao encontrada
			if (strlen(aux) > pos_finded || pos_finded == -1){
				strcpy(finded, to_remove[i]);
				pos_finded = strlen(aux);
			}
		}
	}
	
	if (pos_finded >= 0){
		args->remover = (char*)malloc(sizeof(char) * strlen(finded));
		strcpy(args->remover, finded);
		// cast porque o pos finded retorna um long e a funsao um int
		args->posicao = (int)pos_finded;
		return 1; //foi encontrado
	}
	return 0;
}
/*
 * Imprime os indice
 *
 */
void print_indices(INDICES * i){
	PALAVRA * pal;
	pal = i->pnext;
	
	while (i->pnext!= NULL){
		printf("Indice: %d\tPalavra: %s\n",i->id_palavra,pal->palavra);
		i = i->pnext;
		pal = pal->pnext;
	}
}
	
	
