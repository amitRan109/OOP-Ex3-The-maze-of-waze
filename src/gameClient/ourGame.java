package gameClient;

import org.json.JSONException;

public class ourGame {
	public static void main (String[]args) throws JSONException {
	MyGameGUI fortnait= new MyGameGUI ();
	Thread t1 = new Thread(fortnait);
	
	t1.run();

	
	}
}
