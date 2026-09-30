package GrafoMatriz;
//biblioteca para ler arquivos
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//definição de uma estrutura Matriz de Adjacência para armezanar um grafo
public class TGrafo {
	// Atributos Privados
	private	int n; // quantidade de vértices
	private	int m; // quantidade de arestas
	private	float adj[][]; //matriz de adjacência com float agora

	// Métodos Públicos
	public TGrafo( int n) {  // construtor
	    this.n = n;
	    // No início dos tempos não há arestas
	    this.m = 0; 
	    // alocação da matriz do TGrafo
	    this.adj = new float [n][n];

	    // Inicia a matriz com zeros
		for(int i = 0; i< n; i++)
			for(int j = 0; j< n; j++)
				this.adj[i][j]= Float.POSITIVE_INFINITY;	
	}

	// Insere uma aresta no Grafo tal que
	// v é adjacente a w
	public void insereA(int v, int w, float peso) {
	    // testa se nao temos a aresta
	    if(adj[v][w] == Float.POSITIVE_INFINITY ){
	        adj[v][w] = peso;
	        m++; // atualiza qtd arestas
	    }
	}
	
	// remove uma aresta v->w do Grafo	
	public void removeA(int v, int w) {
	    // testa se temos a aresta
	    if(adj[v][w] != Float.POSITIVE_INFINITY ){
	        adj[v][w] = Float.POSITIVE_INFINITY;
	        m--; // atualiza qtd arestas
	    }
	}

	public void removeV(int v) {
		if (v < 0 || v >= n) {
				System.out.println("Vértice inválido!");
				return;
			}

		// cria nova matriz de suporte para ajuste
		int novoN = n - 1;
		float[][] novaAdj = new float[novoN][novoN];
		int novoM = 0;

		int novaLinha = 0;
		for (int i = 0; i < n; i++) {
			if (i == v) continue; // pula a linha do vértice removido
			int novaColuna = 0;
			for (int j = 0; j < n; j++) {
				if (j == v) continue; // pula a coluna do vértice removido
				novaAdj[novaLinha][novaColuna] = adj[i][j];
					if (adj[i][j] != Float.POSITIVE_INFINITY) {
						novoM++;
					}
					novaColuna++;
				}
				novaLinha++;
			}

			// passa os noovos valores usnado os suportes
			this.adj = novaAdj;
			this.n = novoN;
			this.m = novoM;

			System.out.println("Vértice " + v + " removido com sucesso.");
	}
	// Apresenta o Grafo contendo
	// número de vértices, arestas
	// e a matriz de adjacência obtida	
	public void show() {
	    System.out.println("n: " + n );
	    System.out.println("m: " + m );
	    for( int i=0; i < n; i++){
	    	System.out.print("\n");
	        for( int w=0; w < n; w++) {
            	float peso = adj[i][w];
            	if(peso != Float.POSITIVE_INFINITY)
            		System.out.print("Adj[" + i + "," + w + "]=" + peso + " ");
            	else System.out.print("Adj[" + i + "," + w + "]=inf ");
				}
	    }
	    System.out.println("\n\nfim da impressao do grafo." );
	}

	//calcula e retorna o grau de entrada de um vértice v de um grafo dirigido
	public int inDegree(int v){
		int degree = 0;
		for(int i = 0; i < n; i++){
			if(adj[i][v] != Float.POSITIVE_INFINITY){
				degree++;
			}
		}
		return degree;
	}

	public int outDegree(int v){
		int degree = 0;
		for(int i = 0; i < n; i++){
			if(adj[v][i] != Float.POSITIVE_INFINITY){
				degree++;
			}
		}
		return degree;
	}

	public int degree(int v){
		int entrada = inDegree(v);
		int saida = outDegree(v);
		int degree = entrada + saida;
		System.out.println("\nGrau de entrada é "+ entrada);
		System.out.println("\nGrau de saída é "+ saida);
		System.out.println("\nGrau de total é "+ degree);
		return degree;
	}

	public int fonte(int v){
		int entrada = inDegree(v);
		int saida = outDegree(v);
		if (entrada == 0 && saida > 0){
			System.out.println("É fonte "+1);
			return 1;
		}
		else{
			System.out.println("Não é fonte "+0);
			return 0;
		}
	}

	public int sorvedouro(int v){
		int entrada = inDegree(v);
		int saida = outDegree(v);
		if (saida == 0 && entrada > 0){
			System.out.println("É sorvedouro "+1);
			return 1;
		}
		else{
			System.out.println("Não é sorvedouro "+0);
			return 0;
		}
	}

	public int ehSimetrico(TGrafo g){
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(adj[i][j] != adj[j][i]){
					System.out.println("Não é simétrico "+0);
					return 0;
				}
			}
		}
		System.out.println("É simétrico "+1);
		return 1;
	}

	public void completo(){
		int resultado = n * (n-1);
		if (resultado == m) {
			System.out.println("Grafo é completo");
		} else {
			System.out.println("Grafo não é completo");

		}
	}

	public void complemento() {
		for( int i=0; i < n; i++){
			System.out.print("\n");
			for( int w=0; w < n; w++) {
            	float peso = adj[i][w];
            	if(peso == Float.POSITIVE_INFINITY)
					System.out.print("Adj[" + i + "," + w + "]= 1 "); // usei como 1 pq não saibia oq colocar como peso no complemento
            	else System.out.print("Adj[" + i + "," + w + "]= inf ");
				}
	    }
	    System.out.println("\n\nfim da impressao do grafo complementar do dirigido." );
	}

	public int conexidade(){
		//(3 – C3, 2 – C2, 1 – C1 ou 0 – c0
		//c0 - desconexo - algum vertice isolado
		int semAresta = 0;
		for (int i =0; i<n; i++){//linha
			for(int j=0; j<n; j++){//coluna
				if (adj[i][j]==0 && i!=j){
					semAresta += 1;
					//System.out.println("sem conexão, i: " + i + "j; " + j);
				}
			}
		}
		if(semAresta == n){
			System.out.println("Grau de conexividade c0");
			return 0;
		}
		//c3 - se existe caminho i, j, caminho j,i existe
		int todosCaminhos = 0;
		for (int i =0; i<n; i++){//linha
			for(int j=0; j<n; j++){//coluna
				if (adj[i][j]==1 && adj[j][i]==1 && i!=j){
					todosCaminhos += 1;
					//System.out.println("sem conexão, i: " + i + "j; " + j);
				}
			}
		}
		if(todosCaminhos == m ){
			System.out.println("Grau de conexividade c3 ");
			return 3;
		}
		//c2 - simplesmente conexo - apartir de um vertice atinge todos os outros por caminho, direta ou indiretamente;
		int caminho =0;
		for (int i =0; i<n; i++){//linha
			for(int j=0; j<n; j++){//coluna
				if (adj[i][j]==1 || adj[j][i]==1){
					caminho++;
					//System.out.println("sem conexão, i: " + i + "j; " + j);
				}
			}
		}
		if(caminho == m){
			System.out.println("Grau de conexividade c2: ");
			return 2;
		}
		//c1 - simplesmente conexo - apartir de um vertice atinge todos os outros por percurso, direta ou indiretamente;
		System.out.println("Grau de conexividade c1:");
		return 1;
	}

	public TGrafo reduzido() {
		boolean[][] alcanca = new boolean[n][n];

		// descobre quem cada vértice alcança
		for (int i = 0; i < n; i++) {
			buscaEmProfundidade(i, i, alcanca);
		}

		int[] grupo = new int[n];
		java.util.Arrays.fill(grupo, -1);
		int totalGrupos = 0;

		// agrupa vértices que se alcançam mutuamente
		for (int i = 0; i < n; i++) {
			if (grupo[i] == -1) {
				grupo[i] = totalGrupos;
				for (int j = i + 1; j < n; j++) {
					if (grupo[j] == -1 && alcanca[i][j] && alcanca[j][i]) {
						grupo[j] = totalGrupos;
					}
				}
				totalGrupos++;
			}
		}

		// monta o grafo reduzido
		TGrafo reduzido = new TGrafo(totalGrupos);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (adj[i][j] != Float.POSITIVE_INFINITY && grupo[i] != grupo[j]) {
					reduzido.insereA(grupo[i], grupo[j], 1.0f);
				}
			}
		}

		return reduzido;
	}

	

	private void buscaEmProfundidade(int origem, int atual, boolean[][] alcanca) {
    for (int w = 0; w < n; w++) {
        if (adj[atual][w] != Float.POSITIVE_INFINITY && !alcanca[origem][w]) {
            alcanca[origem][w] = true;
            buscaEmProfundidade(origem, w, alcanca);
       	 }
    	}
	}

	public void converte(TGrafo g){
			for(int i=0;i<n;i++){System.out.print(i+": ");
				for(int j=0; j<n;j++){
					if (adj[i][j]==1){
						System.out.print(j+", ");
					}
				}
				System.out.print("\n");
			}
	}
}
