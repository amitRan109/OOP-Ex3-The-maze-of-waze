package gameClient;

import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

public class AutoAlgo  {

	game_service game;
	List <Fruit> List_Fruits;
	List <Robot> List_Robots;
	graph gr;
	Graph_Algo ga;

	public AutoAlgo (game_service game,graph gr,List <Fruit> List_Fruits) {
		this.List_Fruits=List_Fruits;
		this.gr=gr;
		ga = new Graph_Algo ();
		ga.init(gr);
		this.game=game;

	}

	public void setList_Robots(List<Robot> list_Robots) {
		List_Robots = list_Robots;
	}

	public void runGameAuto () throws JSONException {
		moveRobotByRatio();
	}
	/**
	 * move the robots at the auto game.
	 */
	//	private void moveRobotsByDis()  {
	//		List <node_data> way=null;
	//		double sp=Double.MAX_VALUE;
	//		Fruit closest=null;
	//		int next_edge=0;
	//		for(Robot rob:List_Robots) {
	//			for (Fruit fr:List_Fruits) {
	//
	//				double check=ga.shortestPathDist(rob.getSrc(), fr.getEdge().getSrc());
	//				if(check<sp) {
	//					sp=check;
	//					closest=fr;
	//					way=ga.shortestPath(rob.getSrc(), closest.getEdge().getSrc());
	//				}
	//			}
	//
	//			if (way !=null) {
	//				if(way.size()>1) {
	//					next_edge=way.get(1).getKey();
	//					game.chooseNextEdge(rob.getId(),next_edge);
	//				}
	//				if(way.size()==1) {	
	//					//System.out.println("way "+way+" src "+rob.getSrc()+" dest "+closest.getEdge().getDest());
	//					game.chooseNextEdge(rob.getId(),closest.getEdge().getDest());
	//				}
	//			}
	//		}
	//	}

	/**
	 * choose the robots first locations.
	 */

	public void init_Robots_auto () throws JSONException {
		JSONObject robot_json = new JSONObject(game.toString());
		JSONObject ttt = robot_json.getJSONObject("GameServer");
		int num_robot= ttt.getInt("robots");
		for (int i=0; i<num_robot; i++) {
			int pos = List_Fruits.get(i).getEdge().getSrc();
			game.addRobot(pos);


		}
	}

	/**
	 * check the next node to the robots 
	 */
	private void moveRobotByRatio() {
		edge_data edge;
		List <node_data> way = null;
		List <edge_data> dest = new LinkedList <edge_data>();//list of robots's dest
		for (Robot rob: List_Robots) {
			edge=ratio(rob,dest);
			if (edge !=null) {
				way=ga.shortestPath(rob.getSrc(), edge.getSrc());
				if (way !=null) {
					if(way.size()>1) {
						game.chooseNextEdge(rob.getId(),way.get(1).getKey());
					}
					if(way.size()==1) {	
						game.chooseNextEdge(rob.getId(),edge.getDest());
					}
				}
				//System.out.println("rob "+rob.getId()+" way "+way);
				//System.out.println("ratio "+edge);
				dest.add(edge);
			}
		}
	} 


	/**
	 * the function gets robot and return the src of edge that have the best ratio of 
	 * value of fruits\ distance from the robot
	 * @param r
	 * @return the src of the best edge
	 */
	private edge_data ratio (Robot rob ,List <edge_data> dest) {
		edge_data ans=null;
		double max_ratio=Double.MIN_VALUE;
		double temp_ratio;
		for (node_data node: gr.getV()) {
			for (edge_data edge: gr.getE(node.getKey())) {
				temp_ratio= (edge_value(edge))/(edge_dis(edge, rob));
				if (temp_ratio>max_ratio && !isContains(edge,dest)) {
					max_ratio=temp_ratio;
					ans=edge;
				}
			}
		}
		return ans;
	}

	private boolean isContains (edge_data edge, List <edge_data> dest) {
		if (dest == null) return false;
		for (edge_data e: dest) {
			if (e.getSrc() == edge.getSrc() && e.getDest() == edge.getDest()) return true;
		}
		return false;
	}

	/**
	 * Auxiliary function to ratio
	 * @param edge
	 * @return the fruit's value of the given edge
	 */
	private double edge_value (edge_data edge) {
		double value=0;
		for (Fruit f: List_Fruits) {
			if (f.getEdge().getSrc()==edge.getSrc() &&f.getEdge().getDest()==edge.getDest()) 
				value+=f.getValue();
		}
		return value;
	}
	/**
	 * Auxiliary function to ratio
	 * @param edge
	 * @return the distance between the edge and robot
	 */
	private double edge_dis (edge_data edge ,Robot rob) {
		double dis=ga.shortestPathDist(rob.getSrc(), edge.getSrc());
		return dis;
	}


}