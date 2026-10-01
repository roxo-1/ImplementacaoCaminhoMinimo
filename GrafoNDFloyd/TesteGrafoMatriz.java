package GrafoNDFloyd;

public class TesteGrafoMatriz {

	public static void main(String args[]) {
		// Cria um grafo com 5 vértices
		// Usando o exemplo direcional do arquivo
		TGrafoND g = new TGrafoND(5);

		// arestas (origem, destino, peso)
		g.insereAND(1, 2, 20.0f);
		g.insereAND(1, 3, 30.0f);
		g.insereAND(2, 3, 40.0f);
		g.insereAND(2, 4, 15.0f);
		g.insereAND(3, 4, 15.0f);
		g.insereAND(4, 1, 50.0f);

		System.out.println("Grafo original");
		g.show();

		// executa o Floyd
		g.floyd();
		g.showD();
		g.showR();
		
		g.caminho(2, 3);
	}
}