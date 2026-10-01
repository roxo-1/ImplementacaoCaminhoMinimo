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

		// TODO: tem que fazer um show do D e do R para ver conferir se realmente fez certo

		//o segundo grafo acho que é nao direcionado, entao tem que modificar o outro codigo ainda
		//TGrafo g2 = new TGrafo(4);
	}
}