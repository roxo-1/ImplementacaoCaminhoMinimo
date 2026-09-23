package GrafoMatriz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TesteGrafoMatriz {

	public static TGrafo carregaGrafo(String nomeArq) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(nomeArq))) {
			int n = Integer.parseInt(br.readLine().trim()); // 1ª linha: nº de vértices = n
			int m = Integer.parseInt(br.readLine().trim()); // 2ª linha: nº de arestas = m

			TGrafo g = new TGrafo(n);

			String linha;
			int lidas = 0;
			while ((linha = br.readLine()) != null) {
				linha = linha.trim();
				if (linha.isEmpty()) continue; // ignora linha em branco no final do arquivo

				String[] partes = linha.split("\\s+"); // \\s+ lida com espaço(s) e tab
				int v = Integer.parseInt(partes[0]);
				int w = Integer.parseInt(partes[1]);

				g.insereA(v, w, 1.0f); // arquivo não traz peso, então uso 1.0 como padrão
				lidas++;
			}

			if (lidas != m) {
				System.out.println("Aviso: cabeçalho dizia " + m + " arestas, mas foram lidas " + lidas);
			}

			return g;
		}
	}

	public static void main(String args[]) throws IOException {
		// //  chama o construtor para criar um grafo 4x4
		// TGrafo g = new TGrafo(4);
		
		// // insere as arestas do grafo com pesos
		// // A={(0,1,2.5),(0,2,1.0),(2,1,3.2),(2,3,4.0),(1,3,5.5)}
		// g.insereA(0, 1, 2.5f);
		// g.insereA(2,0 , 1.0f);
		// g.insereA(1, 2, 3.2f);
		// g.insereA(2, 3, 4.0f);
		// g.insereA(1, 3, 5.5f);

		
		// g.inDegree(3);
		// g.outDegree(3);
		// g.degree(3);
		// g.fonte(3);
		// g.sorvedouro(3);
		// g.ehSimetrico(g);
		
		// // mostra o grafo preenchido com pesos e infinito
		// g.show();
		// g.completo();
		// g.removeV(0);
		// g.show();
		// g.completo();
		// g.complemento();
		// g.conexidade();
		
		TGrafo g = carregaGrafo("TesteGrafoMatriz/src/GrafoMatriz/GRAFO.txt");
		TGrafo reduzido = g.reduzido();
		g.show();
		// reduzido.show();
		g.converte(g);


		// TGrafoND g = new TGrafoND(4);
		// g.insereAND(0 , 1);
		// g.insereAND(0,2);
		// g.insereAND(2,1);
		// g.insereAND(2,3);
		// g.insereAND(1,3);
		// g.degreeND(3);
		// g.showND();
		// g.completoND();
		// g.removeVND(0);
		// g.showND();
		// g.completoND();
		// g.conexidadeND();


	}

	
}