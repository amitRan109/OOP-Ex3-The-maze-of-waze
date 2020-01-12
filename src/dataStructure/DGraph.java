package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;


public class DGraph implements graph,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ***params***
	HashMap <Integer,node_data> gmap; 
	int counter;
	
	public DGraph () {
		gmap= new HashMap <> ();
	}
	
	//***getters & setters***

	@Override
	public node_data getNode(int key) {
		//if ( gmap.get(key)==nu)
		return gmap.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if (getNode(src) == null || getNode(dest) == null) return null;
		return ((DNode)gmap.get(src)).getEdge(dest);
	}

	@Override
	public void addNode(node_data n) {
		counter++;
		gmap.put(n.getKey(),n);
	}

	@Override
	public void connect(int src, int dest, double w) {
		counter++;
		DEdge edge= new DEdge (src,dest,w);
		((DNode)gmap.get(src)).add(dest, edge);
	}

	@Override
	public Collection <node_data> getV() {
		return gmap.values();
	}

	@Override
	public Collection <edge_data> getE(int node_id) {
		return ((DNode)gmap.get(node_id)).getE();
	}

	@Override
	public node_data removeNode(int key) {
		counter++;
		Stack <edge_data> s= new Stack <edge_data> ();
		for (node_data n: gmap.values()) {
			for (edge_data e: getE(n.getKey())) { // the edge in n
				if (e.getDest()==key) s.add(e);
			}
		}
		
		while (!s.isEmpty()) {
			edge_data e=s.pop();
			 removeEdge(e.getSrc(),e.getDest());
		}
			
		return gmap.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		counter++;
		return ((DNode)gmap.get(src)).removeEdge(dest);
	}

	@Override
	public int nodeSize() {
		return gmap.size();
	}

	@Override
	public int edgeSize() {
		int counter=0;
		for (node_data node : gmap.values()) {
			counter+=((DNode)node).getSize();
		}
		return counter;
	}

	@Override
	public int getMC() {
		return counter;
	}

}
