package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	EventsDao dao;
	SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	List<Adiacenza> adiacenze;
	
	public Model() {
		dao= new EventsDao();
	}
	
	public List<String> getOffense_category_id() {
		return dao.getOffenseCategoryId();
	}
	public List<Integer> getMonths(){
		return dao.getMonths();
	}
	
	public List<Adiacenza> creaGrafo(int mese, String categoria) {
		this.grafo= new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<String> vertici= dao.getVertici(mese, categoria);
		Graphs.addAllVertices(this.grafo, vertici);
		adiacenze= dao.getArchi(mese, categoria);
		for(Adiacenza a: adiacenze) {
			Graphs.addEdgeWithVertices(this.grafo, a.getS1(), a.getS2(), a.getPeso());
		}
		System.out.println("Grafo creato \n");
		System.out.println("#vertici "+this.grafo.vertexSet().size()+"\n");
		System.out.println("#archi "+this.grafo.edgeSet().size()+"\n");
		List<Adiacenza> d= new ArrayList<Adiacenza>();
		Double pesoTotale=0.0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()){
			pesoTotale= this.grafo.getEdgeWeight(e);
		}
		Double pesoMedio= pesoTotale/this.grafo.edgeSet().size();
		String s="";
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)> pesoMedio) {
				d.add(new Adiacenza(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), this.grafo.getEdgeWeight(e)));
			}
		}
		return d;
		
	}
	
	
	
}
