package gameClient;

import java.util.ArrayList;
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
		//chooseAutoWay();
		moveAutomaticallyRobots();
	}
	
	public void init_Robots_auto () throws JSONException {
		JSONObject robot_json = new JSONObject(game.toString());
		JSONObject ttt = robot_json.getJSONObject("GameServer");
		int num_robot= ttt.getInt("robots");
		for (int i=0; i<num_robot; i++) {
			int pos = List_Fruits.get(i).getEdge().getSrc();
			game.addRobot(pos);


		}
	}
//	public void init_Robots_auto () throws JSONException {
//		JSONObject robot_json = new JSONObject(game.toString());
//		JSONObject ttt = robot_json.getJSONObject("GameServer");
//		int num_robot= ttt.getInt("robots");
//		Fruit bigest=null;
//		List<Fruit> copy=List_Fruits;
//		//System.out.println("copy1 "+copy);
//		for (int i=0; i<num_robot; i++) {
//			double maxF=0;
//			for(Fruit f:copy) {
//				if(f.getValue()>maxF) {
//					maxF=f.getValue();
//					 bigest=f;
//					 System.out.println("biggg "+bigest);
//
//				}
//			}
//			int pos=bigest.getEdge().getSrc();
//			copy.remove(bigest);
//			game.addRobot(pos);
//			//System.out.println("copy2 "+copy);
//
//
//		}
//	}
//
//	111private void chooseAutoWay() throws JSONException {
//		double check;
//		Fruit closest=null;
//		System.out.println("robots "+List_Robots);
//		for(Robot rob:List_Robots) {
//			double sp=Double.MAX_VALUE;
//			for (Fruit fr:List_Fruits) {
//				if(!fr.getVisit()) {
//					if(rob.getSrc()== fr.getEdge().getSrc()) {
//						System.out.println("if");
//						check = fr.getEdge().getWeight();
//						System.out.println("check "+check);
//						closest=fr;
//					}
//					else {
//						System.out.println("else");
//						System.out.println("short "+ga.shortestPathDist(rob.getSrc(), fr.getEdge().getSrc()));
//						check = ga.shortestPathDist(rob.getSrc(), fr.getEdge().getSrc());
//						check = check+ fr.getEdge().getWeight();
//						System.out.println("check "+check);
//						if(check<sp) {
//							System.out.println("check "+check);
//							sp=check;
//							closest=fr;
//						}
//					}
//				}
//			}
//			List <node_data> way = new ArrayList<node_data>();
//			System.out.println("clo "+closest);
//			closest.setVisit(true);
//			if(rob.getSrc() == closest.getEdge().getSrc()) {
//
//				way=ga.shortestPath(rob.getSrc(), closest.getEdge().getDest());
//				way.remove(0);
//			}
//			else {
//				way=ga.shortestPath(rob.getSrc(), closest.getEdge().getSrc());
//				way.add(gr.getNode(closest.getEdge().getDest()));
//				way.remove(0);
//			}
//			rob.setPath(way);
//			//game.chooseNextEdge(rob.getId(), way.get(0).getKey());
//
//			//repaint();
//		}
//				for (Robot r : List_Robots) {
//					System.out.println("path "+r.getPath());
//					while(!r.getPath().isEmpty()) {
//						System.out.println("totot");
//						game.chooseNextEdge(r.getId(), r.getPath().get(0).getKey());
//						r.getPath().remove(0);
//					}
//				}
//	}
	//function that choose the robot path for collecting fruits
		public void moveAutomaticallyRobots() {
			Fruit temp=null ;
			//find the shortest path between each robot to a fruit on the game
			for(Robot rob:List_Robots) {
				
				double dist = Double.MAX_VALUE;
				for (Fruit fr:List_Fruits) {
					if(dist>(ga.shortestPathDist(rob.getSrc(), fr.getEdge().getSrc())+fr.getEdge().getWeight())&&(!fr.getVisit())) {
						dist =ga.shortestPathDist(rob.getSrc(), fr.getEdge().getSrc())+fr.getEdge().getWeight();
						temp=fr;
					}
				}
				rob.setPath(ga.shortestPath(rob.getSrc(), temp.getEdge().getSrc()));
				if(rob.getPath().size()>1)	rob.getPath().remove(0);
				rob.getPath().add(gr.getNode(temp.getEdge().getDest()));
				for (Fruit fr:List_Fruits) {
					if(fr.getPos()==temp.getPos()) {
						fr.setVisit(true);
						
					}
				}
			}

			//move the robots by the path we find for him
		
					for (Robot r : List_Robots) {						
					while(!r.getPath().isEmpty()) {
						 if(game.timeToEnd()<18000 && game.timeToEnd()>16500)	{
							 game.chooseNextEdge(r.getId(),2);
						 }
						
						 else	game.chooseNextEdge(r.getId(), r.getPath().get(0).getKey());
							r.getPath().remove(0);
				}
			}	
			
		}
		
		
	private void moveRobotsByDis()  {
		List <node_data> way=null;

		Fruit closest=null;
		int next_edge=0;
		for(Robot rob:List_Robots) {
			double sp=Double.MAX_VALUE;
			for (Fruit fr:List_Fruits) {
				if(!fr.getVisit()) {
					double check=ga.shortestPathDist(rob.getSrc(), fr.getEdge().getSrc());
					check = check+ fr.getEdge().getWeight();

					if(check<sp) {
						sp=check;
						closest=fr;
					}
				}
			}
			closest.setVisit(true);
			way=ga.shortestPath(rob.getSrc(), closest.getEdge().getSrc());
			//System.out.println("size"+way.size());
			way.add(gr.getNode(closest.getEdge().getDest()));
			if(way.size()> 1)way.remove(0);
			rob.setPath(way);
			if (way !=null) {
				if(way.size()>1) {
					next_edge=way.get(1).getKey();
					game.chooseNextEdge(rob.getId(),next_edge);
				}
				if(way.size()==1) {	
					//System.out.println("way "+way+" src "+rob.getSrc()+" dest "+closest.getEdge().getDest());
					game.chooseNextEdge(rob.getId(),closest.getEdge().getDest());
				}
			}
		}
		//		for (Robot r : List_Robots) {
		//			while(!r.getPath().isEmpty()) {
		//				//System.out.println(r.getId());
		//				//System.out.println(r.getPath().get(0).getKey());
		//				game.chooseNextEdge(r.getId(), r.getPath().get(0).getKey());
		//				r.getPath().remove(0);
		//			}
		//		}
		//paint again

	}
	
	
	
//	/**
//	 * check the next node to the robots 
//	 */
//	private void moveRobotByRatio() {
//		edge_data edge;
//		List <node_data> way = null;
//		List <edge_data> dest = new LinkedList <edge_data>();//list of robots's dest
//		for (Robot rob: List_Robots) {
//			edge=ratio(rob,dest);
//			if (edge !=null) {
//				way=ga.shortestPath(rob.getSrc(), edge.getSrc());
//				if (way !=null) {
//					if(way.size()>1) {
//						game.chooseNextEdge(rob.getId(),way.get(1).getKey());
//					}
//					if(way.size()==1) {	
//						game.chooseNextEdge(rob.getId(),edge.getDest());
//					}
//				}
//				//System.out.println("rob "+rob.getId()+" way "+way);
//				//System.out.println("ratio "+edge);
//				dest.add(edge);
//			}
//		}
//	} 
//
//
//	/**
//	 * the function gets robot and return the src of edge that have the best ratio of 
//	 * value of fruits\ distance from the robot
//	 * @param r
//	 * @return the src of the best edge
//	 */
//	private edge_data ratio (Robot rob ,List <edge_data> dest) {
//		edge_data ans=null;
//		double max_ratio=Double.MIN_VALUE;
//		double temp_ratio;
//		for (node_data node: gr.getV()) {
//			for (edge_data edge: gr.getE(node.getKey())) {
//				temp_ratio= (edge_value(edge))/(edge_dis(edge, rob));
//				if (temp_ratio>max_ratio && !isContains(edge,dest)) {
//					max_ratio=temp_ratio;
//					ans=edge;
//				}
//			}
//		}
//		return ans;
//	}
//
//	private boolean isContains (edge_data edge, List <edge_data> dest) {
//		if (dest == null) return false;
//		for (edge_data e: dest) {
//			if (e.getSrc() == edge.getSrc() && e.getDest() == edge.getDest()) return true;
//		}
//		return false;
//	}
//
//	/**
//	 * Auxiliary function to ratio
//	 * @param edge
//	 * @return the fruit's value of the given edge
//	 */
//	private double edge_value (edge_data edge) {
//		double value=0;
//		for (Fruit f: List_Fruits) {
//			if (f.getEdge().getSrc()==edge.getSrc() &&f.getEdge().getDest()==edge.getDest()) 
//				value+=f.getValue();
//		}
//		return value;
//	}
//	/**
//	 * Auxiliary function to ratio
//	 * @param edge
//	 * @return the distance between the edge and robot
//	 */
//	private double edge_dis (edge_data edge ,Robot rob) {
//		double dis=ga.shortestPathDist(rob.getSrc(), edge.getSrc());
//		return dis;
//	}


}