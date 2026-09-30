package GrafoFloyd;

//definição de uma estrutura Matriz de Adjacência para armezanar um grafo
public class TGrafo {
	// Atributos Privados
	private	int n; // quantidade de vértices
	private	int m; // quantidade de arestas
	private	float adj[][]; //matriz de adjacência com float
	private float D[][]; // matriz distancia
	private float R[][]; // matriz rotas

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


	public void floyd(){

		D = new float[n][n]; //matriz de distancia minima D
		R = new int[n][n]; // matriz de rotas
		
		// Montando matriz de distancia, replica da adj original D0 = V(G)
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				D[i][j] = adj[i][j];

				// Monta a matriz de rotas R0, se tiver valor, coloco o J, se estiver infinito, coloco -1
				if (adj[i][j] < Float.POSITIVE_INFINITY) { // se (vij < infinito)
					R[i][j] = j; // então rij <- j
				} else {
					R[i][j] = -1; // senão rij <- -1 (usamos -1 ao inves de 0 porque um vertice pode se chamar 0 em outros casos)
				}
			}
		}
		
		//para k = 1, ..., n faça
		//	para i = 1, ..., n faça
		//		para j = 1, ..., n faça
		//			se ((i ≠ j) e (dik + dkj < dij)) então
		//				dij <- dik + dkj;
		//				rij<- rik;
		for (int k = 1; k < n; k++) {
			for (int i = 1; i < n; i++) {
				for (int j = 1; j < n; j++) {
					if (i != j && D[i][k] + D[k][j] < D[i][j]) {
						D[i][j] = D[i][k] + D[k][j]; // dij <- dik + dkj
						R[i][j] = R[i][k];           // rij <- rik
					}
				}
			}
		}
	}
	}
}