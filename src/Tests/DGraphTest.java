package Tests;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.node_data;


class DGraphTest {
	DGraph gr;
	DNode n1;
	DNode n2;
	DNode n3;
	DNode n4;
	DNode n5;
	
	@BeforeAll
	static void constructors() {
		DGraph empty = new DGraph ();
		assertEquals (empty.nodeSize(),0);
	}
	
	@BeforeEach
	void init () {
		gr= new DGraph ();
		n1= new DNode (1);
		n2= new DNode (2);
		n3= new DNode (3);
		n4= new DNode (4);
		n5= new DNode (5);
		
		gr.addNode(n1);
		gr.addNode(n2);
		gr.addNode(n3);
		gr.addNode(n4);
		gr.addNode(n5);
				
		gr.connect(1, 5, 5);
		gr.connect(2, 1, 1);
		gr.connect(2, 4, 4);
		gr.connect(3, 1, 3);
		gr.connect(5, 4, 2);
		
	}
	

	@Test
	void connect() {
		
		edge_data n1_edge1= n1.getEdge(2);
		edge_data n1_edge2= n1.getEdge(3);
		edge_data n1_edge3= n1.getEdge(4);
		edge_data n1_edge4= n1.getEdge(5);
		
		assertEquals(n1_edge1,null);
		assertEquals(n1_edge2,null);
		assertEquals(n1_edge3,null);
		assertEquals(n1_edge4.getDest(),5);
		
		edge_data n2_edge1= n2.getEdge(1);
		edge_data n2_edge2= n2.getEdge(3);
		edge_data n2_edge3= n2.getEdge(4);
		edge_data n2_edge4= n2.getEdge(5);

		assertEquals(n2_edge1.getDest(),1);
		assertEquals(n2_edge2,null);
		assertEquals(n2_edge3.getDest(),4);
		assertEquals(n2_edge4,null);
		
		edge_data n3_edge1= n3.getEdge(1);
		edge_data n3_edge2= n3.getEdge(2);
		edge_data n3_edge3= n3.getEdge(4);
		edge_data n3_edge4= n3.getEdge(5);

		assertEquals(n3_edge1.getDest(),1);
		assertEquals(n3_edge2,null);
		assertEquals(n3_edge3,null);
		assertEquals(n3_edge4,null);
		
		edge_data n4_edge1= n4.getEdge(1);
		edge_data n4_edge2= n4.getEdge(2);
		edge_data n4_edge3= n4.getEdge(3);
		edge_data n4_edge4= n4.getEdge(5);

		assertEquals(n4_edge1,null);
		assertEquals(n4_edge2,null);
		assertEquals(n4_edge3,null);
		assertEquals(n4_edge4,null);
		
		edge_data n5_edge1= n5.getEdge(1);
		edge_data n5_edge2= n5.getEdge(2);
		edge_data n5_edge3= n5.getEdge(3);
		edge_data n5_edge4= n5.getEdge(4);

		assertEquals(n5_edge1,null);
		assertEquals(n5_edge2,null);
		assertEquals(n5_edge3,null);
		assertEquals(n5_edge4.getDest(),4);
	}
	
	@Test 
	void getV () {
		Collection <node_data> getv=gr.getV();
		Collection <node_data> ex=new ArrayList <node_data>();
		ex.add(n1);
		ex.add(n2);
		ex.add(n3);
		ex.add(n4);
		ex.add(n5);
		
		for (node_data n: getv) {
			if (!ex.contains(n)) {
				fail();
			}
		}
		
	}
	
	@Test 
	void getE () {
		Collection <edge_data> gete=gr.getE(n2.getKey());
		Collection <edge_data> ex=new ArrayList <edge_data>();
		ex.add(n2.getEdge(1));
		ex.add(n2.getEdge(4));		
		
		for (edge_data e: gete) {
			if (!ex.contains(e)) {
				fail();
			}
		}
	}
	
	@Test 
	void remove () {
		gr.removeEdge(2, 1);
		assertEquals (n2.getEdge(1),null);
		
		gr.removeNode(n5.getKey());
		assertEquals (gr.getNode(n5.getKey()),null);
		assertEquals (gr.getEdge(1,5),null);
		assertEquals (gr.getEdge(5,4),null);
	}
	
	@Test 
	void MC () {
		assertEquals (gr.getMC(),10);
		gr.removeNode(n1.getKey());
		assertEquals (gr.getMC(),13);
		
		gr.addNode(n1);
		gr.connect(2, 5, 10);
		assertEquals (gr.getMC(),15);
	}
	
	@Test 
	void size () {
		assertEquals (gr.nodeSize(),5);
		assertEquals (gr.edgeSize(),5);
		
		gr.removeNode(n3.getKey());
		assertEquals (gr.nodeSize(),4);
		
		gr.removeEdge(n5.getKey(),n4.getKey());
		gr.removeEdge(n1.getKey(),n4.getKey());
		assertEquals (gr.edgeSize(),3);
		
		gr.removeEdge(n2.getKey(),n4.getKey());
		assertEquals (gr.edgeSize(),2);
		
	}

}