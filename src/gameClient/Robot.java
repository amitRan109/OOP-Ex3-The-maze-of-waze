package gameClient;

import java.util.List;

import dataStructure.node_data;
import utils.Point3D;
/**
 * The class represent a robot object that have six fildes:

private int id;//the robot id
private double value;//the robot score
private int src;//the node it's on now
private int dest;//the node it's about to move to
private double speed;//the robot speed
private Point3D pos;//his location
 * @author CHEN KATZOVER
 *
 */

public class Robot {
	private int id;
	private double value;
	private int src;
	private int dest;
	private double speed;
	private Point3D pos;
	private List<node_data> path;
	
	public Robot (int id, double value, int src, int dest, double speed, Point3D pos) {
		this.id=id;
		this.value=value;
		this.src=src;
		this.dest=dest;
		this.speed=speed;
		this.pos=pos;
	}
	
	public String toString () {
		return "id "+id+" src "+src+" dest "+dest+" pos "+pos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Point3D getPos() {
		return pos;
	}

	public void setPos(Point3D pos) {
		this.pos = pos;
	}
	public List<node_data> getPath(){
		return this.path;
	}
	public void setPath(List<node_data> way) {
		this.path= way;
		
	}
	
	
}
