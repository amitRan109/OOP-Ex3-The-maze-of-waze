package gameClient;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import Server.game_service;
import algorithms.Graph_Algo;
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
		moveRobots ();		

	}
	/**
	 * move the robots at the auto game.
	 */
	private void moveRobots() throws JSONException {
		List <node_data> way=null;
		double sp=Double.MAX_VALUE;
		Fruit closest=null;
		int next_edge=0;
		for(Robot rob:List_Robots) {
			for (Fruit fr:List_Fruits) {
				
				double check=ga.shortestPathDist(rob.getSrc(), fr.getEdge().getSrc());
				if(check<sp) {
					sp=check;
					closest=fr;
					way=ga.shortestPath(rob.getSrc(), closest.getEdge().getSrc());
				}
			}

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
	}
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


}