package util;

import java.util.LinkedList;

import grafo.Aresta;
import grafo.Estado;
import grafo.Grafo;


public class Otimizador {
	



	public static void removeEstadosInalcansaveis(Grafo g){
		buscaProfundidade(g);
		LinkedList<Estado> inalcansaveis = new LinkedList<Estado>();
		for(Estado e: g.getEstados()){
			if(!e.isVisited())
				inalcansaveis.add(e);
		}

		for(Estado e: inalcansaveis)
			g.removeEstado(e);
	}

	public static void removeEstadosMortos(Grafo g){
		boolean vivo;
		LinkedList<Estado> mortos = new LinkedList<Estado>();
		
		g.getEstados().getFirst().setInicial(true);

		for(Estado e: g.getEstados()){
			vivo = false;
			if(!e.isInicial() && !e.isReconhecedor()){
				buscaProfundidade(g, e);
				for(Estado es:g.getEstadosReconhecedores()){
					if(es.isVisited()) {
						vivo = true;
						break;
					}
				}
				if(!vivo)
					mortos.add(e);
				
				
			}
		}

		for(Estado e: mortos){
			g.removeEstado(e);
		}
	}







	private static void buscaProfundidade(Grafo g, Estado origem){
		for(Estado e: g.getEstados())
			e.setVisited(false);

		origem.setVisited(true);
		percorreGrafo(g, origem);	
	}

	private static void buscaProfundidade(Grafo g){
		buscaProfundidade(g, g.getEstado(0));
	}

	private static void percorreGrafo(Grafo g, Estado origem){
		for(Aresta a: origem.getArestas())
			if(!a.getLast().isVisited()){
				a.getLast().setVisited(true);
				percorreGrafo(g, a.getLast());
			}		
	}



}

