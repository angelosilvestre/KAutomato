package automato;

import grafo.Aresta;
import grafo.Estado;
import grafo.Grafo;

import java.io.Serializable;
import java.util.LinkedList;

public class AutomatoFinito extends Grafo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutomatoFinito(){
		super();
	
	}

	private boolean pertence(char a,LinkedList<Character> entradas){
		for(char aux:entradas)
			if(a == aux) return true;	
		return false;
	}

	public  boolean verificaCadeia(String cadeia){		
		boolean erro = false;
		Estado estado = getEstado(0); 
		char caracteres[] = cadeia.toCharArray();

		for(char a: caracteres){
			if(estado.getArestas().size() == 0)
				erro = true;
			for(Aresta aresta:  estado.getArestas()){			
				if(!pertence(a, aresta.getEntradas()))
					erro = true;
				else{
					estado = aresta.getLast();	
					erro = false;
					break;
				}
			}
			if(erro) return false;
		}

		if(estado.isReconhecedor() && !erro) return true;
		else return false;
	}


}
