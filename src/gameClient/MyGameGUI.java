package gameClient;
import org.json.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import org.json.JSONException;
import org.json.JSONObject;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DEdge;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Graph_GUI;
import utils.Point3D;

public class MyGameGUI extends JFrame implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	game_service game;
	List <Fruit> List_Fruits;
	List <Robot> List_Robots;
	graph g;
	
	public MyGameGUI ()  throws JSONException {
		g=new DGraph ();
		Graph_GUI gg= new Graph_GUI (g);
		game = Game_Server.getServer(gg.getScenario_num());
		init_Graph();
		init_Fruits();
		init_Robots();
		gg.addFruits(List_Fruits);
		gg.addRobots(List_Robots);
		gg.setVisible(true);
	}
	
	private void init_Graph() throws JSONException{
		g=new DGraph ();
		JSONObject graph = new JSONObject(game.getGraph());
		JSONArray  node =graph.getJSONArray("Nodes");
		for (int i = 0; i < node.length(); i++)
		{
		    Point3D pos = new Point3D (node.getJSONObject(i).getString("pos"));
		    int id = node.getJSONObject(i).getInt("id");
		    g.addNode(new DNode(id,pos));
		    
		}
		
		JSONArray  edge =graph.getJSONArray("Edges");
		for (int i = 0; i < edge.length(); i++)
		{
		    int src = edge.getJSONObject(i).getInt("src");
		    double w = edge.getJSONObject(i).getDouble("w");
		    int dest = edge.getJSONObject(i).getInt("dest");
		    
		    g.connect(src, dest, w);
		    //System.out.println("s "+src+" w "+w+" dest "+dest);
		}
		
	}
	
	private void init_Fruits ()  throws JSONException {
		List <String> String_Fruits= game.getFruits();
		List_Fruits= new LinkedList <Fruit>();
		for (String fruit: String_Fruits) {
			JSONObject fruit_json = new JSONObject(fruit);
			JSONObject details = fruit_json.getJSONObject("Fruit");
			double value = details.getDouble("value");
			int type = details.getInt("type");
			Point3D pos= new Point3D (details.getString("pos"));
			Fruit f= new Fruit (value,type,pos);
			List_Fruits.add(f);
			
		}
		for (Fruit f: List_Fruits) {
			double EPS=0.001;
			double edge_dis;
			double f_dis_src;
			double f_dis_dest;
			for (node_data n: g.getV()) {
				//System.out.println("1");
				for (edge_data e: g.getE(n.getKey())) {
					//System.out.println("2");
					f_dis_src=((DNode)n).getLocation().distance2D(f.getPos());
					f_dis_dest=((DNode)(g.getNode(e.getDest()))).getLocation().distance2D(f.getPos());
					edge_dis=((DNode)(g.getNode(e.getSrc()))).getLocation().distance2D(((DNode)(g.getNode(e.getSrc()))).getLocation());
					double hefresh=(f_dis_src+f_dis_dest)-edge_dis;
					if (hefresh<EPS && hefresh>(-1*EPS)) {
						//System.out.println("3");
						f.setEdge((DEdge)e);
					}
				}
				
			}
			//System.out.println("edge "+f.getEdge());
		}
	}
	
	private void init_Robots () throws JSONException {
		JSONObject robot_json = new JSONObject(game.toString());
		//System.out.println(game.toString());
		JSONObject ttt = robot_json.getJSONObject("GameServer");
		//int num_fruit= ttt.getInt("fruits");
		//System.out.println("n fruit "+num_fruit);
		int num_robot= ttt.getInt("robots");
		//System.out.println("num robot "+num_robot);
		for (int i=0; i<num_robot; i++) {
			game.addRobot((int)(Math.random()*g.nodeSize()));
		}
		
		List <String> String_Robots= game.getRobots();
		List_Robots= new LinkedList <Robot>();
		for (String robot: String_Robots) {
			JSONObject robot_json1 = new JSONObject(robot);
			JSONObject details = robot_json1.getJSONObject("Robot");
			int id = details.getInt("id");
			double value = details.getDouble("value");
			int src = details.getInt("src");
			int dest = details.getInt("dest");
			double speed = details.getDouble("speed");
			Point3D pos= new Point3D (details.getString("pos"));
			Robot r= new Robot (id,value,src,dest,speed,pos);
			List_Robots.add(r);
		}
				
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
