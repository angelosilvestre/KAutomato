package grafo;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

import automato.Tela;




public class Grafo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Estado> estados, estadosReconhecedores;
	protected LinkedList<Aresta> arestas;
	

	public Grafo(){
		estados = new LinkedList<Estado>();
		arestas = new LinkedList<Aresta>();	
		estadosReconhecedores = new LinkedList<Estado>();
	}
	
	
	
	public LinkedList<Estado> getEstadosReconhecedores() {
		return estadosReconhecedores;
	}



	public void setEstadosReconhecedores(LinkedList<Estado> estadosReconhecedores) {
		this.estadosReconhecedores = estadosReconhecedores;
	}



	public void addEstado(Estado v){
		estados.add(v);
	}
	
	public boolean possuiReconhecedor(){
		for(Estado e: estados)
			if(e.isReconhecedor()) return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void removeEstado(Estado e){		
		for(Aresta a:e.getArestas()){
			arestas.remove(a);
		}
		
		LinkedList<Aresta> aux = (LinkedList<Aresta>) arestas.clone();
		for(Aresta a: arestas){
			if(a.getLast() == e)
				aux.remove(a);
		}
		arestas = aux;
	
		estados.remove(e);
	}

	public void verificaNaoDeterminismo(Estado e){
		boolean deterministico = true;
		LinkedList<Aresta> aux = new LinkedList<Aresta>();
		LinkedList<Character> c = new LinkedList<Character>();
		LinkedList<Estado> destino= new LinkedList<Estado>();
		for(Aresta a:e.getArestas()){
			System.out.println(a);
			for(char entrada: a.getEntradas()){
				 destino = e.listaDestinoCom(entrada);
				if(destino.size() > 1){
					System.out.println("nao deterministico");
					if(!c.contains(entrada))
						c.add(entrada);
					deterministico = false;
					aux.add(a);
				}				
			}
		}
		if(!deterministico){
			int i = -1;
			for(Aresta a:aux){
				for(char ca:c){
					if(a.getEntradas().contains(ca)){
						System.out.println("ha");
						i = a.getEntradas().indexOf(ca);
					}
				}
				if(i != -1)a.getEntradas().remove(i);
			}

			for(Aresta a: aux){
				if(a.getEntradas().size() == 0){
					e.getArestas().remove(a);
					arestas.remove(a);
				}
			}
			Random r = new Random();
			int x, y;

			do
				x = r.nextInt(710);				
			while(x < 130);
			do 
				y = r.nextInt(500);
			while(y < 41);
			Estado novo = new Estado("q"+estados.size(), new Point(x,y));
			estados.add(novo);
			addAresta(e, estados.getLast(), c);
			String f = "";
			for(Estado estado:destino){
				f+="\n"+estado;
				for(Aresta a:estado.getArestas())
					novo.addAresta(a);
			}
		
			
			JOptionPane.showMessageDialog(null, "Não-Determinismo encontrado\nAdicione ao estado "+ estados.getLast()+ " todas as arestas de " +f );
			
		}


	}
	
	
	
	
	
	public void addAresta(Estado first, Estado last, LinkedList<Character> entradas){
		Aresta a = new Aresta(first, last, entradas);
		arestas.add(a);
		first.addAresta(a);	
		verificaNaoDeterminismo(first);
	}

	public LinkedList<Estado> getEstados() {
		return estados;
	}

	public void setEstados(LinkedList<Estado> Estados) {
		this.estados = Estados;
	}

	
	public Estado getEstado(int posicao){
		return estados.get(posicao);
	}
	
	public String toString(){
		return estados.toString();
	}

	public LinkedList<Aresta> getArestas() {
		return arestas;
	}
//
//	public void setArestas(LinkedList<Aresta> arestas) {
//		this.arestas = arestas;
//	}
//	
	public Estado getEstadoAt(Point p){
		for(Estado v: estados){
			if(
					(p.x >= v.getPoint().x - Tela.RAIO && p.x <= v.getPoint().x + Tela.RAIO)&&
					(p.y >= v.getPoint().y - Tela.RAIO && p.y <= v.getPoint().y + Tela.RAIO))
				
				return v;
		

		}
		return null;
	}


	
	
	
	
	
}
