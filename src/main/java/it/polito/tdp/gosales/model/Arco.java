package it.polito.tdp.gosales.model;

import java.util.Objects;

import org.jgrapht.alg.util.Pair;

public class Arco implements Comparable<Arco>{
	
	Pair<Retailers, Retailers> coppia;
	double peso;
	
	public Arco(Pair<Retailers, Retailers> coppia, double d) {
		super();
		this.coppia = coppia;
		this.peso = d;
	}
	public Pair<Retailers, Retailers> getCoppia() {
		return coppia;
	}
	public void setCoppia(Pair<Retailers, Retailers> coppia) {
		this.coppia = coppia;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(coppia, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		return Objects.equals(coppia, other.coppia) && peso == other.peso;
	}
	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return Double.compare(this.peso, o.peso);
	}
	
	

}
