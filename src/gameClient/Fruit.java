package gameClient;
import dataStructure.DEdge;
import utils.Point3D;


public class Fruit {
	private double value;
	private int type;
	private Point3D pos;
	private DEdge edge;
	
	public Fruit (double value, int type, Point3D pos)  {
		this.value=value;
		this.type=type;
		this.pos=pos;
		this.edge=null;
	}
	
	public void setEdge (DEdge e) {
		this.edge=e;
	}
	
	public DEdge getEdge () {
		return this.edge;
	}
	
	public String toString () {
		return "value "+value+" type "+type+" pos "+pos;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Point3D getPos() {
		return pos;
	}
	public void setPos(Point3D pos) {
		this.pos = pos;
	}

}
