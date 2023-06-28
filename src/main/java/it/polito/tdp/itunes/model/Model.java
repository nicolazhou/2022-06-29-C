package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
private ItunesDAO dao;
	
	private Graph<Album, DefaultWeightedEdge> grafo;
	
	private List<Album> vertici;


	// Ricorsione
	private List<Album> soluzione;
	
	
	public Model() {
		
		this.dao = new ItunesDAO();
		
		//this.lista = new ArrayList<>();
		
	}
	
	
	public void creaGrafo(double n) {
		
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
	
		this.vertici = new ArrayList<>(this.dao.getVertici(n));
		
		// Vertici:
		Graphs.addAllVertices(this.grafo, this.vertici);

		int i = 1;
		
		// Archi
		for(Album a1 : this.grafo.vertexSet()) {
			for(Album a2: this.grafo.vertexSet()) {
				
				if(a1.getAlbumId() > a2.getAlbumId()) { //!a1.equals(a2)
					
					DefaultWeightedEdge e = this.grafo.getEdge(a1, a2);
					
					if(e == null) {
						
						double differenza = a1.getCosto() - a2.getCosto();
						
						
						
						/*if(differenza == 0) {
							System.out.println(a1.getCosto());
							System.out.println(a2.getCosto());
							System.out.println("0");
						}*/
						
						
						//System.out.println(a1.getCosto());
						//System.out.println(a2.getCosto());
						if(differenza > 0) { 
							
							Graphs.addEdgeWithVertices(this.grafo, a1, a2, differenza);
							
						} else if(differenza < 0) {
							
							Graphs.addEdgeWithVertices(this.grafo, a2, a1, (-1)*differenza);
							
						}
						
						
					}
					

					
					
					
				}
				
				
			}
			
		}

		
	}
	
	
	public int getNNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public boolean isGrafoLoaded() {
		
		if(this.grafo == null)
			return false;
		
		return true;
	}
	
	
	public List<AlbumBilancio> getAdiacenze(Album a1) {
		
		List<Album> adiacenze = Graphs.neighborListOf(this.grafo, a1);
		
		List<AlbumBilancio> result = new ArrayList<>();
		
		for(Album a : adiacenze) {
			
			
			double somma = 0;
			
			for(DefaultWeightedEdge e : this.grafo.edgesOf(a)) {
				
				somma += this.grafo.getEdgeWeight(e);
				
			}
			
			somma = somma/this.grafo.edgesOf(a).size();
			
			AlbumBilancio ab = new AlbumBilancio(a, somma);
			
			result.add(ab);
			
		}
		
		Collections.sort(result);
		
		return result;
		
	}


	public List<Album> getVertici() {
		return vertici;
	}
	
	
	private double calcolaBilancio(Album a) {
		
		double somma = 0;
		
		for(DefaultWeightedEdge e : this.grafo.edgesOf(a)) {
			
			somma += this.grafo.getEdgeWeight(e);
			
		}
		
		somma = somma/this.grafo.edgesOf(a).size();
		
		return somma;
		
	}
	
	private int contaMaggioreDiA1(List<Album> parziale) {
		
		double bilancioA1 = calcolaBilancio(parziale.get(0));
		
		int cont = 0;
		
		for(Album a : parziale) {
			
			if(calcolaBilancio(a) > bilancioA1) {
				
				cont++;
				
			}
			
			
		}
		
		
		return cont;
	}
	
	
	
	public List<Album> trovaPercorso(double x, Album a1, Album a2) {
		
		// Inizializzazione
		List<Album> parziale = new ArrayList<>();
		this.soluzione = new ArrayList<>();
		
		
		// Avvia Ricorsione
		parziale.add(a1);
		
		this.soluzione = new ArrayList<>(parziale);
		cerca(parziale, a2, x);
		
		System.out.println("Soluzione " + soluzione);
		
		return this.soluzione;
		
	}
	
	
	public void cerca(List<Album> parziale, Album a2, double x) {
		
		System.out.println(parziale);
		
		Album ultimo = parziale.get(parziale.size()-1);
		
		// Condizione di terminazione
		if(ultimo.equals(a2)) {
			
			// Aggiungo sol migliore
			if(contaMaggioreDiA1(parziale) > contaMaggioreDiA1(this.soluzione)) {
				
				this.soluzione = new ArrayList<>(parziale);
				
			}
			
			return;
			
		}

		
		
		// Se non siamo nella condizione di terminazione, andiamo avanti
		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(ultimo)) {
			
			if(this.grafo.getEdgeWeight(e) >= x) { // Archi con peso maggiore o uguale a x
				
				Album opposto = Graphs.getOppositeVertex(this.grafo, e, ultimo);
				
				if(!parziale.contains(opposto)) {
					
					parziale.add(opposto);
					
					cerca(parziale, a2, x);
					
					//backtracking
					parziale.remove(parziale.size()-1);
					
				}
				
				
			}
				
		}
		
	}
	
	
	
	
	
}
