package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable<Event> {
	public enum EventType {
		PROMOZIONE,
		BOCCIATURA,
		STESSA_SQUADRA
	}
	
	private EventType type;
	private int reporter;
	//private Team team;
	private Match match;
	
	public Event(EventType type, int reporter, Match match) {
		super();
		this.type = type;
		this.reporter = reporter;
		//this.team = team;
		this.match = match;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public int getReporter() {
		return reporter;
	}

	public void setReporter(int reporter) {
		this.reporter = reporter;
	}

	/*
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	*/
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Override
	public int compareTo(Event o) {
		return this.match.getDate().compareTo(o.match.getDate());
	}
}
