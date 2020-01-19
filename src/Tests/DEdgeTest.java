package Tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import dataStructure.DEdge;

class DEdgeTest {

	@Test
	void test() {
		//empty edge
		DEdge empty= new DEdge ();
		assertEquals (empty.getSrc(),-1);
		assertEquals (empty.getDest(),-1);
		assertEquals (empty.getWeight(),0);
		assertEquals (empty.getInfo(),null);
		assertEquals (empty.getTag(),0);
		
		DEdge edge= new DEdge (10,-2,5);
		assertEquals (edge.getSrc(),10);
		assertEquals (edge.getDest(),-2);
		assertEquals (edge.getWeight(),5);
		assertEquals (edge.getInfo(),null);
		assertEquals (edge.getTag(),0);
		
		DEdge edgeAll= new DEdge (1,2,55,"a");
		assertEquals (edgeAll.getSrc(),1);
		assertEquals (edgeAll.getDest(),2);
		assertEquals (edgeAll.getWeight(),55);
		assertEquals (edgeAll.getInfo(),"a");
		assertEquals (edgeAll.getTag(),0);
		
	}

}