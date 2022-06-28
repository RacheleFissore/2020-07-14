package it.polito.tdp.PremierLeague.model;

public class Classifica implements Comparable<Classifica> {
	private Team team;
	private int punti;
	
	public Classifica(Team team, int punti) {
		super();
		this.team = team;
		this.punti = punti;
	}
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getPunti() {
		return punti;
	}
	public void setPunti(int punti) {
		this.punti = punti;
	}

	@Override
	public int compareTo(Classifica o) {
		return this.punti - o.punti;
	}
}
