package GragoFloyd;

public class TesteGrafoMatriz {

	public static void main(String args[]) {
		// Cria um grafo com 5 vértices
		// Usando o exemplo direcional do arquivo
		TGrafo g = new TGrafo(5);

		// arestas (origem, destino, peso)
		g.insereA(1, 2, 1.0f);
		g.insereA(1, 5, 1.0f);
		g.insereA(2, 3, 1.0f);
		g.insereA(2, 4, 2.0f);
		g.insereA(3, 4, 4.0f);
		g.insereA(3, 5, 2.0f);
		g.insereA(4, 1, 3.0f);
		g.insereA(5, 1, 2.0f);
		g.insereA(5, 4, 1.0f);

		System.out.println("Grafo original");
		g.show();

		// executa o Floyd
		g.floyd();
		g.showD();
		g.showR();

		//g.caminho(1, 1);
		g.caminho(6, 6);
		//g.caminho(4, 3);
	}
}