package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	ExtFlightDelaysDAO dao;
	SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	Map<Integer, Airport> idMap;

	

	public Model() {
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap<Integer, Airport>();
		dao.loadAllAirports(idMap);
	}

	public void creaGrafo(int distanzaMediaMinima) {

		// grafo semplice, non orientato e pesato
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		// vertici = aereoporti
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap));

		// archi = rotte tra aereoporti collegati da almeno un volo
		for (Adiacenza r : dao.getAdiacenze(idMap, distanzaMediaMinima)) {
			if (this.grafo.containsVertex(r.getA1()) && this.grafo.containsVertex(r.getA2())) {
				DefaultWeightedEdge e = this.grafo.getEdge(r.getA1(), r.getA2());
				if (e == null) {
					Graphs.addEdgeWithVertices(grafo, r.getA1(), r.getA2(), r.getDistanza());
				}

				else {
					this.grafo.setEdgeWeight(e, r.getDistanza());
				}
			}

		}
		System.out.println("GRAFO CREATO");
		System.out.println("VERTICI: " + this.grafo.vertexSet().size());
		System.out.println("ARCHI: " + this.grafo.edgeSet().size());

		// peso arco = distanza media percorsa = media del campo DISTANCE di ciascun
		// volo
		// -> arco tra due aereoporti aggiunto solo se distanza media >
		// distanzaMediaMinima

	}
	
	public List <Airport> getVertici (Map <Integer, Airport> idMap){
		return dao.getVertici(idMap);
	}

	public List<Adiacenza> getAdiacenze(Map <Integer, Airport> idMap, int distanzaMediaMinima) {
		return dao.getAdiacenze(idMap, distanzaMediaMinima);

	}
	
	public Map<Integer, Airport> getIdMap() {
		return idMap;
	}

	public void setIdMap(Map<Integer, Airport> idMap) {
		this.idMap = idMap;
	}

	

	

}
