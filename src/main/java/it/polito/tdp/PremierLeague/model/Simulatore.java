package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;
import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulatore {
	private PriorityQueue<Event> queue;
	private List<Match> partite;
	private List<Team> squadre;
	private int N;
	private int sogliaX;
	private Map<Team, Integer> squadraReporterMap;
	private Map<Integer, List<Match>> partiteReporterMap;
	private Model model;
	private PremierLeagueDAO dao;
	
	private double reporterMedi;
	private int partiteMinoriSoglia;
	
	public Simulatore(List<Match> matches, List<Team> teams, int N, int x, Model model) {
		this.model = model;
		dao = new PremierLeagueDAO();
		partite = new ArrayList<>(matches);
		squadre = new ArrayList<>(teams);
		queue = new PriorityQueue<>();
		squadraReporterMap = new HashMap<>();
		partiteReporterMap = new HashMap<>();
		reporterMedi = 0;
		partiteMinoriSoglia = 0;
		this.N = N;
		sogliaX = x;
		
		for(Team team : squadre) {
			squadraReporterMap.put(team, N);
		}
		
		for(int i = 1; i <= N; i++) {
			for(Team team : squadraReporterMap.keySet()) {
				partiteReporterMap.put(i, dao.partiteSquadra(team));
			}
		}
	}
	
	public void init() {
		for(Integer integer : partiteReporterMap.keySet()) {
			Collection<List<Match>> partite = partiteReporterMap.values();
			List<Match> match = this.partite;
			for(Match p : match) {
				double random = Math.random();
				if(random < 0.5) {
					queue.add(new Event(EventType.PROMOZIONE, integer, p));
				}
				else if(random >= 0.5 && random < 0.7) {
					queue.add(new Event(EventType.BOCCIATURA, integer, p));
				}
				else {
					queue.add(new Event(EventType.STESSA_SQUADRA, integer, p));
				}
			}
		}
		
	}
	
	public void run() {
		while (!queue.isEmpty()) {
			Event e = queue.poll();
			
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
			case PROMOZIONE:
				List<Classifica> migliori = model.getMigliori();
				int posMigliore = (int)(Math.random()*migliori.size());
				Classifica best = migliori.get(posMigliore);
				Team besTeam = best.getTeam();
				squadraReporterMap.replace(besTeam, squadraReporterMap.get(besTeam)+1);
				break;
			case BOCCIATURA:
				
				break;
			case STESSA_SQUADRA:
				
				break;

			default:
				break;
		}
		
	}
}
