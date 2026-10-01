package GrafoNDFloyd;


public class TGrafoND {
	// Atributos Privados
	private	int n; // quantidade de vértices
	private	int m; // quantidade de arestas
	private	float adj[][]; //matriz de adjacência com float
	private float D[][]; // matriz distancia
	private int R[][]; // matriz rotas
	// Métodos Públicos
	public TGrafoND( int n) {  // construtor
	    this.n = n;
	    // No início dos tempos não há arestas
	    this.m = 0; 
	    // alocação da matriz do TGrafo
	    this.adj = new float [n+1][n+1];

	    // Inicia a matriz com zeros
		for(int i = 0; i< n; i++)
			for(int j = 0; j< n; j++)
				this.adj[i][j]= Float.POSITIVE_INFINITY;	
	}

	// Insere uma aresta no Grafo Não Dirigido tal que
	// v é adjacente a w
	public void insereAND(int v, int w, float peso) {
	    // testa se nao temos a aresta
	    if(adj[v][w] == Float.POSITIVE_INFINITY ){
	        adj[v][w] = peso;
			adj[w][v] = peso;
	        m++; // atualiza qtd arestas
	    }
	}
	
	// remove uma aresta v->w do Grafo Não Dirigido
	public void removeAND(int v, int w) {
	    // testa se temos a aresta
	    if(adj[v][w] == Float.POSITIVE_INFINITY ){
	        adj[v][w] = Float.POSITIVE_INFINITY;
			adj[w][v] = Float.POSITIVE_INFINITY;
	        m--; // atualiza qtd arestas
	    }
	}
	public void removeVND(int v) {
		if (v < 0 || v >= n) {
			System.out.println("Vértice inválido!");
			return;
		}

		int novoN = n - 1;
		float[][] novaAdj = new float[novoN][novoN];
		int novoM = 0;

		int novaLinha = 0;
		for (int i = 0; i < n; i++) {
			if (i == v) continue;
			int novaColuna = 0;
			for (int j = 0; j < n; j++) {
				if (j == v) continue;
				novaAdj[novaLinha][novaColuna] = adj[i][j];
				novaColuna++;
			}
			novaLinha++;
		}

		// recontar arestas: como o grafo é simétrico,
		// conto só a "metade de cima" da matriz para não contar cada aresta 2x
		for (int i = 0; i < novoN; i++) {
			for (int j = i + 1; j < novoN; j++) {
				if (novaAdj[i][j] == 1) {
					novoM++;
				}
			}
		}

		this.adj = novaAdj;
		this.n = novoN;
		this.m = novoM;

		System.out.println("Vértice " + v + " removido com sucesso.");
	}

	public void show() {
        System.out.println("n: " + n );
	    System.out.println("m: " + m );
	    for( int i=1; i <= n; i++){
	    	System.out.print("\n");
	        for( int w=1; w <= n; w++) {
            	float peso = adj[i][w];
            	if(peso != Float.POSITIVE_INFINITY)
            		System.out.print("Adj[" + i + "," + w + "]=" + peso + " ");
            	else System.out.print("Adj[" + i + "," + w + "]=inf ");
				}
	    }
	    System.out.println("\n\nfim da impressao do grafo." );
    }


    
	public void floyd(){

		D = new float[n+1][n+1]; //matriz de distancia minima D
		R = new int[n+1][n+1]; // matriz de rotas
		// Montando matriz de distancia, replica da adj original D0 = V(G)
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				D[i][j] = adj[i][j];

				// Monta a matriz de rotas R0, se tiver valor, coloco o J, se estiver infinito, coloco -1
				if (adj[i][j] < Float.POSITIVE_INFINITY) { // se (vij < infinito)
					R[i][j] = j; // então rij <- j
				} else {
					R[i][j] = 0; // senão rij <- 0 (usamos 0 ao inves de -1 porque um vertice pode se chamar 0 em outros casos)
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
	public void showD() {
	    System.out.println("\nMatriz de distancias D:" );
		for( int i=1; i <= n; i++){
			System.out.print("\n");
	        for( int j=1; j <= n; j++) {
				float peso = D[i][j];
            	if(peso == Float.POSITIVE_INFINITY)
            		System.out.print("D[" + i + "," + j + "]= 0 ");
            	else System.out.print("D[" + i + "," + j + "]= " + peso + " ");
				}
	    }
	    System.out.println("\n\nfim da impressao da matriz D." );
	}


	public void showR() {
		System.out.println("\nMatriz de rotas R: (0 = sem rotas ou diagonal)" );
	    for( int i=1; i <= n; i++){
			System.out.print("\n");
	        for( int j=1; j <= n; j++) {
				System.out.print("R[" + i + "," + j + "]= " + R[i][j] + " ");
			}
	    }
	    System.out.println("\n\nfim da impressao da matriz R." );
	}

	// 	Para encontrarmos um caminho uij utilizamos a matriz de rotas R5 da seguinte forma:
	// Seja rij = k1,
	// se (k1 = j) então o caminho será (i, j);
	// senão seja rk1,j = k2 , se (k2 = j) então o caminho será (i, k1, j)
	// 		senão seja rk2,j = k3, se (k3 = j) então o caminho será (i, k1, k2, j)
	// 		e assim sucessivamente.
	public void caminho(int i, int j){
		if (i == j) {
			System.out.println("\nCaminho " + i + " -> " + j + ": (" + i + ") custo 0");
			return;
		}
		if (R[i][j] == 0) {
			System.out.println("\nNão existe caminho de " + i + "para " + j);
			return;
		}

		System.out.println("\nCaminho " + i + " -> " + j + ":");

		int k = R[i][j]; //k1 = rij
		if (k == j){
			System.out.println("(" + i + ", " + j + ") custo " + D[i][j] + " ");
			return;
		} 

		int k2 = R[k][j]; //rk1,j = k2
		if (k2 == j){
			System.out.println("(" + i + ", " + k + ", " + j + ") custo " + D[i][j] + " ");
			return;
		}

		int k3 = R[k2][j]; //rk2,j = k3
		if (k3 == j) {
			System.out.println("(" + i + ", " + k + ", " + k2 + ", " + j + ") custo " + D[i][j] + " ");
			return;
		}
	}
}
