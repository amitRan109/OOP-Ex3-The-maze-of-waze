package dataStructure;

import java.io.Serializable;

public class DEdge implements edge_data,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//***params***
	
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
	
	//***constructor***
	
	public DEdge () {
		this.src=-1;
		this.dest=-1;
		this.weight=0;
		this.info=null;
		this.tag=0;
	}
	
	public DEdge (int src, int dest, double weight) {
		this.src=src;
		this.dest=dest;
		this.weight=weight;
		this.info=null;
		this.tag=0;
	}
	
	public DEdge (int src, int dest, double weight, String info) {
		this.src=src;
		this.dest=dest;
		this.weight=weight;
		this.info=info;
		this.tag=0;
	}
	
	//***functions***
	
	public String toString () {
		return src+"--->"+dest+" ";
	}
	
	//***getters & setters***
	
	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.dest;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;
	}

}
