package it.polito.tdp.gosales.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;


public class Model {
	

	
	private GOsalesDAO dao;
	private Graph<Retailers, DefaultWeightedEdge> grafo;
	private Map<Retailers, List<Integer>> retailersMap;
	private List<Retailers> retailersList;
	
	public Model() {
		this.dao = new GOsalesDAO();
		
		this.retailersList = this.dao.getAllRetailers();
		
	}
	

	public void creaGrafo(String nazione , int anno , int m) {
		// TODO Auto-generated method stub

	this.grafo = new SimpleWeightedGraph<Retailers, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
	// Aggiunta VERTICI 
	List<Retailers> vertici=new LinkedList<>();
	
	for(Retailers i : this.retailersList) {
		if(i.getCountry().equals(nazione)) {
			vertici.add(i);
		}
	}
	
	Graphs.addAllVertices(this.grafo, vertici);
	
	
	this.retailersMap=new HashMap<>();
	for(Retailers i : retailersList) {
		this.retailersMap.put((i), this.dao.getProdottiPerRetailerInAnno(i, anno));
	}

	
	// Aggiunta ARCHI
	
	for (Retailers v1 : vertici) {
		for (Retailers v2 : vertici) {
			if(this.dao.getPeso(v1, v2, anno)>=m && v1.getCode()!=v2.getCode()){ 

		      this.grafo.addEdge(v1,v2);
		      this.grafo.setEdgeWeight(this.grafo.getEdge(v1, v2), this.dao.getPeso(v1, v2, anno));
			}
			}
			}

	}

public int nVertici() {
	return this.grafo.vertexSet().size();
}

public int nArchi() {
	return this.grafo.edgeSet().size();
}

public Set<Retailers> getVertici(){
	
	Set<Retailers> vertici=this.grafo.vertexSet();
	
	return vertici;
}

public Set<DefaultWeightedEdge> getArchi(){
	
	Set<DefaultWeightedEdge> archi=this.grafo.edgeSet();
	
	return archi;
}

//public List<Set<User>> getComponente() {
//	ConnectivityInspector<User, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo) ;
//	return ci.connectedSets() ;
//}

public Set<Retailers> getComponente(Retailers v) {
	ConnectivityInspector<Retailers, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo) ;
	return ci.connectedSetOf(v) ;
}

public List<String> getVerticiName(){
	
	List<String> nomi=new LinkedList<>();
	
	for(Retailers i : this.grafo.vertexSet()) {
		nomi.add(i.getName());
	}
	
	Collections.sort(nomi);
	
	return nomi;
}	
	
	public List<String> getNazioni(){
		
		List<String> nazioni = new LinkedList<>();
		
		for(Retailers i : retailersList) {
			if(!nazioni.contains(i.getCountry())) {
			nazioni.add(i.getCountry());
			}
		}
		
		Collections.sort(nazioni);
		
		
		return nazioni;
	}
	
	public List<String> stampaArchi(){
		
		List<Arco> archi = new LinkedList<>();
		
		for(DefaultWeightedEdge i : this.grafo.edgeSet()) {
			archi.add(new Arco(new Pair<Retailers, Retailers>(this.grafo.getEdgeSource(i), this.grafo.getEdgeTarget(i)), this.grafo.getEdgeWeight(i)));
		}
		
		Collections.sort(archi);
		
		List<String> stampe = new LinkedList<>();
		
		for(Arco i : archi) {
			stampe.add((int)this.grafo.getEdgeWeight(this.grafo.getEdge(i.getCoppia().getFirst(), i.getCoppia().getSecond()))+": "+this.grafo.getEdgeSource(this.grafo.getEdge(i.getCoppia().getFirst(), i.getCoppia().getSecond()))+"<->"+this.grafo.getEdgeTarget(this.grafo.getEdge(i.getCoppia().getFirst(), i.getCoppia().getSecond())));
		}
		
		return stampe;
	}


	public double getPesoTot(Retailers r) {
		// TODO Auto-generated method stub
		
		double pesoTot=0.0;
		
		for(Retailers i1 : this.getComponente(r)) {
			for(Retailers i2 : this.getComponente(r)) {
				if(this.grafo.containsEdge(this.grafo.getEdge(i1, i2))) {
			pesoTot=pesoTot+this.grafo.getEdgeWeight(this.grafo.getEdge(i1, i2));
				}
			}
		}
		
		return pesoTot/2;
	}
	
}
