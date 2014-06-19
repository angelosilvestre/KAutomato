package grafo;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;



public class Estado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private boolean visited;	
	protected LinkedList<Aresta> arestas;
	private Point point;
	private boolean reconhecedor,inicial;



	

	public Estado destinoCom(char c){
		for(Aresta a: arestas){
			for(char ca: a.getEntradas())
				if(ca == c)
					return a.getLast();
		}
		return null;
	}

	public boolean isInicial() {
		return inicial;
	}

	public void setInicial(boolean inicial) {
		this.inicial = inicial;
	}

	public boolean isReconhecedor() {
		return reconhecedor;
	}

	public void setReconhecedor(boolean reconhecedor) {
		this.reconhecedor = reconhecedor;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point ponto) {
		this.point = ponto;
	}




	public Estado(String nome, Point ponto){
		this.nome = nome;
		this.point = ponto;
		visited = false;		
		arestas = new LinkedList<Aresta>();
		reconhecedor = false;

	}

	public LinkedList<Estado> listaDestinoCom(char c){
		LinkedList<Estado> estados = new LinkedList<Estado>();
		for(Aresta a: arestas){
			for(char ca: a.getEntradas())
				if(ca == c)
					estados.add(a.getLast());
		}
		return estados;
	}


	public void addAresta(Aresta a){
		arestas.add(a);
		
	}


	public boolean isVisited(){
		return visited;
	}

	public boolean equals(Estado v){
		return nome.equals(v.getNome());
	}

	public String toString(){
		return nome;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public LinkedList<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(LinkedList<Aresta> arestas) {
		this.arestas = arestas;
	}



}

