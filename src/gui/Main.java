package gui;

import dataStructure.DGraph;
import dataStructure.DNode;
import utils.Point3D;

public class Main {

	public static void main(String[] args) {
		
		
				DGraph gr = new DGraph ();
		
				DNode a = new DNode (1);	
				DNode b = new DNode (2);
				DNode c = new DNode (3);
				DNode d = new DNode (4);	
				
				gr.addNode(a);
				gr.addNode(b);
				gr.addNode(c);
				gr.addNode(d);
				
				gr.connect(a.getKey(),b.getKey(),4);
				gr.connect(a.getKey(),c.getKey(),15);
				gr.connect(b.getKey(),c.getKey(),6);
				gr.connect(b.getKey(),d.getKey(),5);
				gr.connect(d.getKey(),c.getKey(),11);
	
				
				a.setLocation(new Point3D(150,150));
				b.setLocation(new Point3D(150,300));
				c.setLocation(new Point3D(400,225));
				d.setLocation(new Point3D(250,450));
		
				Graph_GUI window = new Graph_GUI(gr);
				window.setVisible(true);
				
				
	}

}
