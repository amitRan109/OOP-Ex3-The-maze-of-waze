package gameClient;

public class Range {
	double x;
	double y;
	double max_x;
	double min_x;
	double min_y;
	double max_y;
	
	//***constructor***
	
	public Range (double max_x, double min_x, double max_y, double min_y) {
		this.x=0;
		this.y=0;
		this.max_x=max_x;
		this.min_x=min_x;
		this.max_y=max_y;
		this.min_y=min_y;
	}
	
	public double getMax_x() {
		return max_x;
	}

	public void setMax_x(double max_x) {
		this.max_x = max_x;
	}

	public double getMin_x() {
		return min_x;
	}

	public void setMin_x(double min_x) {
		this.min_x = min_x;
	}

	public double getMin_y() {
		return min_y;
	}

	public void setMin_y(double min_y) {
		this.min_y = min_y;
	}

	public double getMax_y() {
		return max_y;
	}

	public void setMax_y(double max_y) {
		this.max_y = max_y;
	}

	
	public Range (double x, double y,double max_x, double min_x, double max_y, double min_y) {
		this.x=x;
		this.y=y;
		this.max_x=max_x;
		this.min_x=min_x;
		this.max_y=max_y;
		this.min_y=min_y;
		//System.out.println(max_x+" "+min_x+" "+max_y+" "+min_y);
	}
	
	//***functions***
	public void scale (double farme_min_x, double frame_max_x,double farme_min_y, double frame_max_y) {
		double resx = ((this.x - min_x) / (max_x-min_x)) * (frame_max_x - farme_min_x) + farme_min_x;
		this.x=resx;
		double resy = ((this.y - min_y) / (max_y-min_y)) * (frame_max_y - farme_min_y) + farme_min_y;
		this.y=resy;
	}
	
	
	
	
	//***getters & setters***
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	

	public void setY(double y) {
		this.y = y;
	}
}
