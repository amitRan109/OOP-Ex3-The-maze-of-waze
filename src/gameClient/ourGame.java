package gameClient;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DEdge;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;


public class ourGame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	game_service game;
	List <Fruit> List_Fruits;
	List <Robot> List_Robots;
	graph gr;
	Range scaleR; //the limits of x and y

	public ourGame () {
		initWindow ();
	}

	private void initWindow() {

		openWindow();

	}

	private void initGUI (int sNumber) throws JSONException {
		System.out.println("init gui");
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List_Fruits=null;
		List_Robots=null;
		game = Game_Server.getServer(sNumber);
		setMenuBar();
		init_Graph();
		init_Fruits();
		setPointsLoc ();
		init_Robots();
		//repaint();
		this.setVisible(true);
		//runGame();
	}

	private void setMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu play = new JMenu("play");
		JMenuItem playGame = new JMenuItem ("play game");
		playGame.addActionListener(this);
		play.add(playGame);
		menuBar.add(play);
		this.setJMenuBar(menuBar);
		 
	}
	
	private void setPointsLoc () {
		System.out.println("setPointsLoc");

		scaleR= new Range (find_max_x(),find_min_x(),find_max_y(),find_min_y());
		for (node_data node: gr.getV()) {
			//System.out.println("1 "+node.getLocation());
			Range range = new Range (node.getLocation().x(), node.getLocation().y(),scaleR.getMax_x(),scaleR.getMin_x(),scaleR.getMax_y(),scaleR.getMin_y());
			range.scale((this.getWidth())*0.01,(this.getWidth())*0.9,(this.getHeight())*0.1,(this.getHeight())*0.8);
			node.setLocation(new Point3D(range.getX(),range.getY()));
			//System.out.println("2 "+node.getLocation());

		}

		for (Fruit f: List_Fruits) {
			Range range = new Range (f.getPos().x(), f.getPos().y(),scaleR.getMax_x(),scaleR.getMin_x(),scaleR.getMax_y(),scaleR.getMin_y());
			range.scale((this.getWidth())*0.01,(this.getWidth())*0.9,(this.getHeight())*0.1,(this.getHeight())*0.8);
			f.setPos(new Point3D(range.getX(),range.getY()));
		}
	}

	public double find_max_x () {

		double ans=Double.MIN_VALUE;
		for (node_data node: gr.getV()) {
			if (node.getLocation().x()>ans) {
				ans=node.getLocation().x();
			}
		}
		return ans;
	}

	public double find_min_x () {
		double ans=Double.MAX_VALUE;
		for (node_data node: gr.getV() ) {
			if (node.getLocation().x()<ans) {
				ans=node.getLocation().x();
			}
		}
		return ans;
	}

	public double find_max_y () {
		double ans=Double.MIN_VALUE;
		for (node_data node: gr.getV()) {
			if (node.getLocation().y()>ans) {
				ans=node.getLocation().y();
			}
		}
		return ans;
	}

	public double find_min_y () {
		double ans=Double.MAX_VALUE;
		for (node_data node: gr.getV() ) {
			if (node.getLocation().y()<ans) {
				ans=node.getLocation().y();
			}
		}
		return ans;
	}

	@Override
	public void paint(Graphics g) {

		System.out.println("paint");
		super.paint(g);

		paint_graph (g);
		paint_robots(g);
		paint_fruits(g);


	}

	private void paint_graph(Graphics g) {
		System.out.println("paint_graph");
		for (node_data n : gr.getV()) {
			n.setTag(0);
		}

		for (node_data n : gr.getV()) {
			//location of n
			Point3D loc = n.getLocation(); 
			for (edge_data dest : gr.getE(n.getKey())) {

				//location of neighbor
				Point3D loc1 = ((DNode) gr.getNode(dest.getDest())).getLocation(); 

				//print edge between n and dest
				g.setColor(Color.PINK);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(2));
				g.drawLine(loc.ix(), loc.iy(), loc1.ix(), loc1.iy()); 

				//edge weight
				g.setColor(Color.black);
				Graphics2D g3 = (Graphics2D) g;
				g3.setStroke(new BasicStroke(2));
				g.drawString((Double.toString(dest.getWeight())), (int)((loc.x()+loc1.x())/2),(int)((loc.y()+loc1.y())/2)); 

				//mark src	
				g.setColor(Color.PINK);
				g.fillOval((int)((loc.ix()*0.7)+(0.3*loc1.ix())), 1+(int)((loc.iy()*0.7)+(0.3*loc1.iy())), 8, 8); 

				//print neighbor
				if (gr.getNode(dest.getDest()).getTag() == 0) {
					//neighbor point
					g.setColor(Color.BLUE);
					g.fillOval(loc1.ix(), loc1.iy(), 8, 8); 

					g.setColor(Color.black);
					g.setFont(new Font("Courier", Font.PLAIN, 20));
					//neighber key
					g.setColor(Color.black);
					g.drawString(Integer.toString(((DNode) gr.getNode(dest.getDest())).getKey()), loc1.ix(),loc1.iy());


					//mark the neighbor
					gr.getNode(dest.getDest()).setTag(1); 
				}
			}
			if (n.getTag() == 0) {
				g.setColor(Color.BLUE);
				//n point
				g.fillOval(loc.ix(), loc.iy(), 10, 10); 

				g.setColor(Color.black);
				g.setFont(new Font("Courier", Font.PLAIN, 20));
				// n key
				g.drawString(Integer.toString(n.getKey()), loc.ix(), loc.iy()); 
			}

			//mark n
			n.setTag(1);
		}
	}

	private void paint_robots(Graphics g) {
		System.out.println("paint_robots");
		if (List_Robots!=null) {
			for (Robot r: List_Robots) {
				g.setColor(Color.green);
				g.fillOval(r.getPos().ix(), r.getPos().iy(), 15, 15); 
			}
		}
	}

	private void paint_fruits(Graphics g) {
		System.out.println("paint_fruits");
		for (Fruit f: List_Fruits) {
			if (f.getType()==1) {
				g.setColor(Color.YELLOW);
			}
			if (f.getType()==-1) {
				g.setColor(Color.RED);
			}
			g.fillOval(f.getPos().ix(), f.getPos().iy(),10, 20); 
		}
	}

	private void openWindow () {
		System.out.println("openWindow");

		JFrame window=new JFrame(); 

		//submit button
		JButton buttont=new JButton("Ok");    
		buttont.setBounds(100,110,140, 40);   

		//enter name label
		JLabel labelt = new JLabel();		
		labelt.setText("Enter graph number (1-23)");
		labelt.setBounds(10, 10, 500, 100);

		//empty label which will show event after button clicked
		JLabel labelt1 = new JLabel();
		labelt1.setBounds(10, 110, 200, 100);

		//textfield to enter name
		JTextField textfieldt= new JTextField();
		textfieldt.setBounds(110, 70, 130, 30);

		//add to frame
		window.add(labelt1);
		window.add(textfieldt);
		window.add(labelt);
		window.add(buttont);    
		window.setSize(500,300);    
		window.setLayout(null);    
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//action listener
		buttont.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = textfieldt.getText();
				int sNumber= Integer.parseInt(text);
				if (sNumber<1 || sNumber>23) {
					labelt1.setText("choose number between 1 to 23");	
				}
				else {
					window.dispose();
					try {
						initGUI(sNumber);
					} catch (JSONException e) {e.printStackTrace();}
				}

			}          
		});

	}

	private void init_Graph() throws JSONException{
		System.out.println("init_Graph");

		gr=new DGraph ();
		JSONObject graph = new JSONObject(game.getGraph());
		JSONArray  node =graph.getJSONArray("Nodes");
		for (int i = 0; i < node.length(); i++)
		{
			Point3D pos = new Point3D (node.getJSONObject(i).getString("pos"));
			int id = node.getJSONObject(i).getInt("id");
			gr.addNode(new DNode(id,pos));

		}
		JSONArray  edge =graph.getJSONArray("Edges");
		for (int i = 0; i < edge.length(); i++)
		{
			int src = edge.getJSONObject(i).getInt("src");
			double w = edge.getJSONObject(i).getDouble("w");
			int dest = edge.getJSONObject(i).getInt("dest");

			gr.connect(src, dest, w);
			//System.out.println("s "+src+" w "+w+" dest "+dest);
		}

	}

	private void init_Fruits ()  throws JSONException {
		System.out.println("init_Fruits");
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
			for (node_data n: gr.getV()) {
				//System.out.println("1");
				for (edge_data e: gr.getE(n.getKey())) {
					//System.out.println("2");
					f_dis_src=((DNode)n).getLocation().distance2D(f.getPos());
					f_dis_dest=((DNode)(gr.getNode(e.getDest()))).getLocation().distance2D(f.getPos());
					edge_dis=((DNode)(gr.getNode(e.getSrc()))).getLocation().distance2D(((DNode)(gr.getNode(e.getSrc()))).getLocation());
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
		JSONObject ttt = robot_json.getJSONObject("GameServer");
		int num_robot= ttt.getInt("robots");
		for (int i=0; i<num_robot; i++) {
			game.addRobot((int)(Math.random()*gr.nodeSize()));
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
			Range ra = new Range (pos.x(),pos.y(),scaleR.max_x,scaleR.min_x,scaleR.max_y,scaleR.min_y);
			ra.scale((this.getWidth())*0.01,(this.getWidth())*0.9,(this.getHeight())*0.1,(this.getHeight())*0.8);
			Point3D new_pos = new Point3D (ra.getX(),ra.getY());
			Robot r= new Robot (id,value,src,dest,speed,new_pos);
			List_Robots.add(r);
		}
//		runGame();
	}

	private void runGame () {
		System.out.println("run game");
		game.startGame();
		while(game.isRunning()) {
			moveRobot();
		}

		String results = game.toString();
		System.out.println("Game Over: "+results);
	}

	public void moveRobot() {

		
		System.out.println(game.move());
		
		for (Robot rob: List_Robots) {
			
			Iterator <edge_data> it=gr.getE(rob.getSrc()).iterator();
			int dest= it.next().getDest();
			game.chooseNextEdge(rob.getId(), dest);
			
		}
		try {
			game.move();
			updateRobots();
			updateFruit();
			repaint();

		} catch (JSONException e) {e.printStackTrace();}
	}

	private void updateRobots () throws JSONException {
		System.out.println("update robot");
		for (String robot: game.move()) {
			//System.out.println("robot "+robot);
			JSONObject robot_json = new JSONObject(robot);
			JSONObject details = robot_json.getJSONObject("Robot");
			int id = details.getInt("id");
			int dest = details.getInt("dest");
			Point3D pos= new Point3D (details.getString("pos"));
			List_Robots.get(id).setDest(dest);
			List_Robots.get(id).setPos(pos);
			//System.out.println("get id "+List_Robots.get(id));
		}
	}

	private void updateFruit () throws JSONException {
		System.out.println("updateFruit");
		for (Fruit fruit: List_Fruits) {
			if (!isContainsF_list(fruit.getPos(),fruit.getType())) {
				List_Fruits.remove(fruit);
			}
		}

		for (String s: game.getFruits()) {
			JSONObject fruit_json = new JSONObject(s);
			JSONObject details = fruit_json.getJSONObject("Fruit");
			double value = details.getDouble("value");
			int type = details.getInt("type");
			Point3D pos= new Point3D (details.getString("pos"));

			if (!isContainsF_game(pos, type)) {
				List_Fruits.add(new Fruit (value,type,pos));
			}
		}
	}

	private boolean isContainsF_list (Point3D pos, int type) {
		for (Fruit f: List_Fruits) {
			if (f.getPos()==pos && f.getType()==type) return true;
		}
		return false;
	}

	private boolean isContainsF_game (Point3D pos, int type) throws JSONException {
		for (String s: game.getFruits()) {
			JSONObject robot_json = new JSONObject(s);
			JSONObject details = robot_json.getJSONObject("Fruit");
			int type1 = details.getInt("type");
			Point3D pos1= new Point3D (details.getString("pos"));
			if (pos1==pos && type1==type) return true;
		}
		return false;
	}

	public static void main (String[]args) {
		ourGame h= new ourGame ();
		h.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("action");
		if (e.getActionCommand()=="play game") {
			runGame();
		}
		
	}

}