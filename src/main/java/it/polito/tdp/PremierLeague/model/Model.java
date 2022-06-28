package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private Graph<Team, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Team, Integer> mappaPunti;
	private List<Classifica> migliori;
	private List<Classifica> peggiori;
	
	public Model() {
		dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		mappaPunti = new HashMap<>();
		Graphs.addAllVertices(grafo, dao.listAllTeams());
		
		for(Team team : grafo.vertexSet()) {
			int punti = 0;
			for(Integer v : dao.calcolaClassifica(team)) {
				if(v == 1) {
					punti += 3;
				} else if(v == 0) {
					punti += 1;
				}
			}
			
			mappaPunti.put(team, punti);
		}
		
		for(Team v1 : grafo.vertexSet()) {
			for(Team v2 : grafo.vertexSet()) {
				if(mappaPunti.get(v1) > mappaPunti.get(v2)) {
					Graphs.addEdgeWithVertices(grafo, v1, v2, mappaPunti.get(v1) - mappaPunti.get(v2));
				}
				else if(mappaPunti.get(v1) < mappaPunti.get(v2)) {
					Graphs.addEdgeWithVertices(grafo, v2, v1, mappaPunti.get(v2) - mappaPunti.get(v1));
				}
			}
		}
	}
	
	public Integer getNVertici() {
		return grafo.vertexSet().size();
	}
		 
	public Integer getNArchi() {
		return grafo.edgeSet().size();
	}
	
	public Set<Team> getVertici() {
		return grafo.vertexSet();
	}
	
	public String classifica(Team soglia) {
		String string = "";
		Integer puntiSoglia = mappaPunti.get(soglia);
		migliori = new ArrayList<>();
		peggiori = new ArrayList<>();
		
		for(Team team : mappaPunti.keySet()) {
			if(team != soglia) {
				if(mappaPunti.get(team) > puntiSoglia) {
					migliori.add(new Classifica(team, mappaPunti.get(team)-puntiSoglia));
				}
				else {
					peggiori.add(new Classifica(team, puntiSoglia-mappaPunti.get(team)));
				}
			}
			
		}
		
		Collections.sort(migliori);
		Collections.sort(peggiori);
		
		string += "SQUADRE MIGLIORI: \n";
		for(Classifica classifica : migliori) {
			string += classifica.getTeam() + " (" + classifica.getPunti() + ")\n";
		}
		
		string += "\nSQUADRE PEGGIORI: \n";
		for(Classifica classifica : peggiori) {
			string += classifica.getTeam() + " (" + classifica.getPunti() + ")\n";
		}
		return string;
	}
	
	public List<Classifica> getMigliori() {
		return migliori;
	}
}
