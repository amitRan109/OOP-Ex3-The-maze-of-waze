package gameClient;
import dataStructure.DEdge;
import utils.Point3D;
/**
 * 
 *The class represent a fruit object that have four fildes:

private double value;//the fruit value
private int type;//if the type value is -1=the fruit is banana if the type value is 1=the fruit is apple
private Point3D pos;//the fruit location
private DEdge edge;//the edge that the fruit on
 *
 */

public class Fruit {
	private double value;
	private int type;
	private Point3D pos;
	private DEdge edge;
	private boolean visit;
	public Fruit (double value, int type, Point3D pos)  {
		this.value=value;
		this.type=type;
		this.pos=pos;
		this.edge=null;
	}
	
	//***functions***
	public String toString () {
		return "value "+value+" type "+type+" pos "+pos+" edge "+edge;
	}
	
	//***getters & setters***
	public void setEdge (DEdge e) {
		this.edge=e;
	}
	
	public DEdge getEdge () {
		return this.edge;
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
	public boolean getVisit() {
		return this.visit;
	}
	public void setVisit(boolean b) {
		this.visit= b;		
	}

}
