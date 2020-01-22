package gameClient;

import java.io.File;

import java.io.PrintWriter;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

/**
 * this class responsible to create 24 kml files
 * the file can be loaded to google earth
 * and view the game course in a specific level
 * the kml relevant only for auto game.
 */
class KML_Logger {
    /**
     * private data types of the class
     * int level
     * StringBuffer str- the string will be written there.
     */
    private int level;
    private StringBuffer str;

    /**
     * simple constructor
     * @param level
     */
    KML_Logger(int level) {
        this.level = level;
        str = new StringBuffer();
        //KML_Play();
        KML_Start();
    }
    /**
     * this function initialize the working platform to KML
     */
    private void KML_Start(){
        str.append(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                        "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
                        "  <Document>\r\n" +
                        "    <name>" + "Game stage :"+level + "</name>" +"\r\n"
        );
        
        str.append("<Style id=\"yellowLineGreenPoly\">\r\n" + 
        		"      <LineStyle>\r\n" + 
        		"        <color>7f00ffff</color>\r\n" + 
        		"        <width>4</width>\r\n" + 
        		"      </LineStyle>\r\n" + 
        		"      <PolyStyle>\r\n" + 
        		"        <color>7f00ff00</color>\r\n" + 
        		"      </PolyStyle>\r\n" + 
        		"    </Style>");
        
        KML_node();
    }
    /**
     * this function initialize the node icon to KML
     */
    private void KML_node(){
        str.append(" <Style id=\"node\">\r\n" +
                "      <IconStyle>\r\n" +
                "        <Icon>\r\n" +
                "          <href>http://maps.google.com/mapfiles/kml/pal3/icon35.png</href>\r\n" +
                "        </Icon>\r\n" +
                "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                "      </IconStyle>\r\n" +
                "    </Style>"
        );
        KML_Fruit();
    }
    /**
     * this function initialize the Fruits icon to KML (Type 1 and -1)
     */
    private void KML_Fruit(){
        str.append(
                " <Style id=\"fruit_-1\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/paddle/purple-stars.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"fruit_1\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/paddle/red-stars.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>"
        );
        KML_Robot();
    }
    /**
     * this function initialize the Robots icon to KML
     */
    private void KML_Robot(){
        str.append(
                " <Style id=\"robot\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/shapes/motorcycling.png></href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>"
        );
    }

    private long time = 0;
    
    public void increesTime() {
    	time++;
    }
    
    
    /**
     * this function is used in "paint"
     * after painting each element
     * the function enters the kml the location of each element
     * @param style
     * @param location
     */
    void Place_Mark(String style, String location)
    {
//        LocalDateTime Present_time = LocalDateTime.now();
//        "      <TimeStamp>\r\n" +
//        "        <when>" + Present_time+ "</when>\r\n" +
//        "      </TimeStamp>\r\n" +
        str.append(
                "    <Placemark>\r\n" +
                		"      <TimeSpan>\r\n" +
                     	"        <begin>" + time+ "</begin>\r\n" +
                     	"        <end>" + (time+1) + "</end>\r\n" +
                     	"      </TimeSpan>\r\n" +
                        "      <styleUrl>#" + style + "</styleUrl>\r\n" +
                        "      <Point>\r\n" +
                        "        <coordinates>" + location + "</coordinates>\r\n" +
                        "      </Point>\r\n" +
                        "    </Placemark>\r\n"
        );
    }

    /**
     * mark the kml the end of the script
     */
    void KML_Stop()
    {
        str.append("  \r\n</Document>\r\n" +
                "</kml>");
        SaveFile();
    }

    /**
     * save the kml string to a file
     */
    private void SaveFile(){
        try
        {
            File file=new File("data/"+level+".kml");
            PrintWriter pw=new PrintWriter(file);
            pw.write(str.toString());
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void paintGraph(graph gr) {
		for (node_data n : gr.getV()) {
			for (edge_data e: gr.getE(n.getKey())) {
				printEdge(gr, e);
				
			}
		}
	}
	private void printEdge(graph gr, edge_data e) {
		str.append("<Placemark>\r\n" + 
				"      <name>edge</name>\r\n" + 
				"      <styleUrl>#yellowLineGreenPoly</styleUrl>\r\n" + 
				"      <LineString>\r\n" + 
				"        <coordinates> " + 
							gr.getNode(e.getSrc()).getLocation()+
							"\r\n"+
							gr.getNode(e.getDest()).getLocation()+
				"        </coordinates>\r\n" + 
				"      </LineString>\r\n" + 
				"    </Placemark>");
		
	}
	

}