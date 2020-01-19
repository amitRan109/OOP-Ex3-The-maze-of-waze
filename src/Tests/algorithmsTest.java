package Tests;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

class algorithmsTest {
	Graph_Algo ga;
	DGraph gr;
	DNode a;	
	DNode b;
	DNode c;
	DNode d;	
	DNode e;
	DNode f;
	DNode g;
	
	@BeforeEach
	void init() {
		ga=new Graph_Algo();
		gr = new DGraph ();
		
		a = new DNode (1);	
		b = new DNode (2);
		c = new DNode (3);
		d = new DNode (4);	
		e = new DNode (5);
		f = new DNode (6);
		g = new DNode (7);
		

		gr.addNode(a);
		gr.addNode(b);
		gr.addNode(c);
		gr.addNode(d);
		gr.addNode(e);
		gr.addNode(f);
		gr.addNode(g);
		
		gr.connect(a.getKey(),b.getKey(),4);
		gr.connect(a.getKey(),c.getKey(),15);
		gr.connect(a.getKey(),e.getKey(),7);
		gr.connect(b.getKey(),c.getKey(),6);
		gr.connect(b.getKey(),d.getKey(),5);
		gr.connect(c.getKey(),e.getKey(),8);
		gr.connect(d.getKey(),c.getKey(),11);
		gr.connect(d.getKey(),f.getKey(),2);
		gr.connect(e.getKey(),d.getKey(),10);
		gr.connect(e.getKey(),g.getKey(),5);
		gr.connect(f.getKey(),g.getKey(),3);
				
		ga.init(gr);

	}
	
	
	@Test
	void From_toFile () {
		ga.save("graph_algo");
		
		Graph_Algo gaC= new Graph_Algo ();
		gaC.init("graph_algo");
		
		//check if equals
		for (node_data n: ga.copy().getV()) {
			int count=0;
			for (node_data k: gaC.copy().getV()) {
				if (n.getKey()==k.getKey()) {
					for (edge_data ne: ((DNode)n).getE()) {
						int count2=0;
						for (edge_data ke: ((DNode)n).getE()) {
							if (ne.getDest()==ke.getDest() && ne.getSrc()== ke.getSrc()) {
								count2++;
								break;
							}
						}
						if (count2==0) fail ();
					}
					
					count++;
					break;
				}
			}
			
			if (count==0) fail ();	
		}
		
	}
		
	@Test
	void isConnected () {
		assertEquals(ga.isConnected(),false);
		gr.connect(g.getKey(), a.getKey(), 1);
		ga.init(gr);
		assertEquals(ga.isConnected(),true);
		
		gr.removeEdge(d.getKey(), e.getKey());
		ga.init(gr);
		assertEquals(ga.isConnected(),true);
	}
	
	@Test
	void shortestPathDist () {
		assertEquals(ga.shortestPathDist(a.getKey(),c.getKey()),10);
		
		assertEquals(ga.shortestPathDist(b.getKey(),d.getKey()),
				ga.shortestPathDist(d.getKey(),g.getKey()));
		
		assertEquals(ga.shortestPathDist(b.getKey(),a.getKey()),-1);
	}
	
	@Test
	void shortestPath () {
				
		assertEquals(ga.shortestPath(b.getKey(),a.getKey()),null);
		
		ArrayList<node_data> ex= new ArrayList <node_data>();
		ex.add(a);
		ex.add(e);
		ex.add(g);
		
		assertEquals(ga.shortestPath(a.getKey(),g.getKey()),ex);
		
		gr.connect(c.getKey(), a.getKey(), 2);
		ga.init(gr);
		
		ArrayList<node_data> ex2= new ArrayList <node_data>();
		ex2.add(e);
		ex2.add(d);
		ex2.add(c);
		ex2.add(a);
		ex2.add(b);
	
		assertEquals(ga.shortestPath(e.getKey(),b.getKey()),ex2);
		
	}
	
	@Test
	void TSP () {
		ArrayList <Integer> l= new ArrayList <Integer>();
		l.add(d.getKey());
		l.add(c.getKey());
		l.add(b.getKey());
		assertEquals(ga.TSP(l),null);
		
		ArrayList <Integer> l2= new ArrayList <Integer>();
		l2.add(a.getKey());
		l2.add(c.getKey());
		l2.add(b.getKey());
		l2.add(d.getKey());
		l2.add(e.getKey());
		l2.add(f.getKey());
		l2.add(g.getKey());

		System.out.println(ga.TSP(l2));
		
		ArrayList <node_data> ex= new ArrayList <node_data>();
		ex.add(a);
		ex.add(b);
		ex.add(c);
		ex.add(e);
		ex.add(d);
		ex.add(f);
		ex.add(g);
		
		assertEquals(ga.TSP(l2),ex);
		
	}
	
	@Test
	void copy () {
		graph copy= ga.copy();
		
		gr.removeNode(a.getKey());
		ga.init(gr);
		
		Object[] g= gr.getV().toArray();
		int i=0;
		int count=0;
		for (node_data n: copy.getV()) {
			if (n.equals(g[i])) count++;
		}
		if (count==gr.nodeSize()) fail();
		
	}
	

}