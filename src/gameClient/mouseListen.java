package gameClient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.node_data;
import utils.Point3D;

public class mouseListen implements MouseListener{

	 //where initialization occurs:
    //Register for mouse events on blankArea and the panel.
    blankArea.addMouseListener(this);
    addMouseListener(this);
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		pressed(arg0.getX(),arg0.getY());

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		JSONObject robot_json;
		try {
			robot_json = new JSONObject(game.toString());
			JSONObject ttt = robot_json.getJSONObject("GameServer");
			int num_robot= ttt.getInt("robots");
			if (List_Robots.size() == num_robot && isRun==false) {
				System.out.println("r");
				runGame();
			}
		} catch (JSONException e) {e.printStackTrace();}
	}

}
