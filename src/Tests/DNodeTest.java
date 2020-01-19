package Tests;

import static org.junit.jupiter.api.Assertions.*;


//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructure.DEdge;
import dataStructure.DNode;
import dataStructure.edge_data;
import utils.Point3D;

class DNodeTest {
	DNode n1;
	DNode n2;
	DNode n3;
	DNode n4;
	
	@BeforeEach
	void init () {
		n1= new DNode (1);
		n2= new DNode (2);
		n3= new DNode (3);
		n4= new DNode (4);
		
		DEdge d1_2 = new DEdge (1,2,3);
		n1.add(2, d1_2);
		
		DEdge d3_4 = new DEdge (3,4,5);
		n3.add(4, d3_4);
		
		//System.out.println("1 "+n1.toString());

	}

	@Test
	void constructors() {
		DNode empty= new DNode ();
		assertEquals (empty.getKey(),-1);
		assertEquals (empty.getInfo(),null);
		assertEquals (empty.getLocation(),null);
		assertEquals (empty.getWeight(),Double.MAX_VALUE);
		assertEquals (empty.getTag(),0);
		
		DNode node= new DNode (5);
		assertEquals (node.getKey(),5);
		assertEquals (node.getInfo(),null);
		assertEquals (node.getLocation(),null);
		assertEquals (node.getWeight(),Double.MAX_VALUE);
		assertEquals (node.getTag(),0);
		
		DNode nodeAll= new DNode (-9,"b",new Point3D(50,100));
		assertEquals (nodeAll.getKey(),-9);
		assertEquals (nodeAll.getInfo(),"b");
		assertEquals (nodeAll.getLocation().ix(),50);
		assertEquals (nodeAll.getLocation().iy(),100);	
		assertEquals (nodeAll.getWeight(),Double.MAX_VALUE);
		assertEquals (nodeAll.getTag(),0);
	}
	
	@Test
	void functions_map() {
		DEdge d3_1 = new DEdge (3,1,6);
		n3.add(1, d3_1); 
				
		//check edge
		edge_data n1_edge1= n1.getEdge(2);
		edge_data n1_edge2= n1.getEdge(3);
		edge_data n1_edge3= n1.getEdge(4);
		
		assertEquals(n1_edge1.getDest(),2);
		assertEquals(n1_edge2,null);
		assertEquals(n1_edge3,null);
		
		edge_data n2_edge1= n2.getEdge(1);
		edge_data n2_edge2= n2.getEdge(3);
		edge_data n2_edge3= n2.getEdge(4);
		
		assertEquals(n2_edge1,null);
		assertEquals(n2_edge2,null);
		assertEquals(n2_edge3,null);
		
		edge_data n3_edge1= n3.getEdge(1);
		edge_data n3_edge2= n3.getEdge(2);
		edge_data n3_edge3= n3.getEdge(4);
		
		assertEquals(n3_edge1.getDest(),1);
		assertEquals(n3_edge2,null);
		assertEquals(n3_edge3.getDest(),4);
		
		edge_data n4_edge1= n4.getEdge(1);
		edge_data n4_edge2= n4.getEdge(2);
		edge_data n4_edge3= n4.getEdge(3);
		
		assertEquals(n4_edge1,null);
		assertEquals(n4_edge2,null);
		assertEquals(n4_edge3,null);
		
		n3.removeEdge(4);
		n3_edge3= n3.getEdge(4);
		assertEquals(n3_edge3,null);
		
		n2.add(4, new DEdge (2,4,8));
		edge_data n2_edge4= n2.getEdge(4);
		assertEquals(n2_edge4.getDest(),4);
		
	}

}