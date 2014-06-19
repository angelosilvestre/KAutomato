package grafo;

import java.io.Serializable;
import java.util.LinkedList;


public class Aresta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Estado first, last;
	private String label;
	private static int number = 0;
	private boolean visited;
	private LinkedList<Character> entradas;
	private Aresta concorrente;


	public boolean possuiArestaConcorrente(Grafo g){
		for(Aresta a:g.arestas){
			if(a.getLast() == first && a.getFirst() == last){
				concorrente = a;
				return true;
				}
		}
		
		return false;		
	}

	public void removeEntrada(char c){
		entradas.remove(c);			
	}
	
	public void addEntradas(LinkedList<Character> caracteres){
		for(char c: caracteres){
			if(!entradas.contains(c))
			entradas.add(c);
		}
	}
	
	public Aresta(Estado first, Estado last, LinkedList<Character> entradas) {
		label = "(" + first+ ","+last+")";
		this.first = first;
		this.last = last;
		this.entradas = entradas;
		visited = false;
		number++;
		concorrente = null;
	}
	
	

	public LinkedList<Character> getEntradas() {
		return entradas;
	}

	

	public boolean equals(Aresta a){
		return first == a.getFirst() && last == a.getLast();
	}

	public Aresta getConcorrente() {
		return concorrente;
	}

	public void setConcorrente(Aresta concorrente) {
		this.concorrente = concorrente;
	}

	public Estado getFirst() {
		return first;
	}
	
//	public Vertice getVizinho(Vertice v){
//		if(v.equals(first)) return last;
//		else return first;
//	}

	public void setFirst(Estado first) {
		this.first = first;
	}

	public Estado getLast() {
		return last;
	}

	public void setLast(Estado last) {
		this.last = last;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public String toString(){
		return label;
	}

}
