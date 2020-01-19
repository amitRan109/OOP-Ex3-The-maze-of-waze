package algorithms;

import java.util.LinkedList;

import java.util.List;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import dataStructure.DNode;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

/**
 * This empty class represents the set of graph-theory algorithms which should
 * be implemented as part of Ex2 - Do edit this class.
 * 
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ***params***
	private graph g;

	// ***constructor***
	public Graph_Algo(graph g) {
		this.g = g;
	}

	public Graph_Algo() {

	}

	// ***functions***
	@Override
	public void init(graph g) {
		this.g = g;
	}

	@Override
	public void init(String file_name) {
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);

			g = (graph) in.readObject();

			in.close();
			file.close();

			System.out.println("Object has been deserialized");

		}

		catch (IOException ex) {
			System.out.println("IOException is caught");
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}
	}

	@Override
	public void save(String file_name) {
		try {
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(g);

			out.close();
			file.close();

			System.out.println("Object has been serialized");
		} catch (IOException ex) {
			System.out.println("IOException is caught");
		}

	}

	@Override
	public boolean isConnected() {
		if (g.nodeSize() == 1)
			return true;
		Queue<DNode> q = new ArrayBlockingQueue<DNode>(g.nodeSize());
		clear();
		for (node_data node : g.getV()) {
			DNode n = (DNode) node;
			if (n.getE() == null) {
				return false;
			}
			q.add(n);
			n.setTag(1);
			while (!q.isEmpty()) {
				for (edge_data edge : q.peek().getE()) {
					DNode dest = (DNode) g.getNode(edge.getDest());
					if (dest.getTag() == 0) {
						dest.setTag(1);
						q.add(dest);
					}
				}
				q.remove();
			}
			for (node_data nodes : g.getV()) {
				if (nodes.getTag() == 0)
					return false;
				else
					nodes.setTag(0);
			}
		}
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {

		if (g.getNode(src) == null || g.getNode(dest) == null) {
			throw new RuntimeException("Those nodes are'nt exist");
		}
		clear();
		DNode src1 = (DNode) g.getNode(src);
		src1.setWeight(0);

		DNode min;
		while ((min = (DNode) g.getNode(getMin())) != null) {

			min.setTag(1);
			for (edge_data edge : min.getE()) {
				DNode neighbor = (DNode) g.getNode(edge.getDest());
				if (min.getWeight() + edge.getWeight() < neighbor.getWeight()) {
					neighbor.setWeight(min.getWeight() + edge.getWeight());
					neighbor.setInfo(Integer.toString(min.getKey()));
				}
			}
		}
		if (g.getNode(dest).getWeight() == Double.MAX_VALUE)
			return -1;

		return g.getNode(dest).getWeight();
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {

		if (g.getNode(src) == null || g.getNode(dest) == null) {
			throw new RuntimeException("Those nodes are'nt exist");
		}

		if (shortestPathDist(src, dest) == -1)
			return null;

		DNode temp = (DNode) g.getNode(dest);
		List<node_data> ans = new LinkedList<node_data>();
		Stack<node_data> s = new Stack<node_data>();
		while (temp.getKey() != src) {
			s.push(temp);
			temp = (DNode) g.getNode(Integer.parseInt(temp.getInfo()));
		}
		s.push(temp);
		while (!s.isEmpty()) {
			ans.add(s.pop());
		}
		return ans;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		List<node_data> ans = new LinkedList<node_data>();
		Iterator<Integer> it = targets.iterator();

		int target = it.next();

		// check if there is at least 1 edge to every node
		if (TwoNodesNoEdge(targets)) {
			while (it.hasNext()) {
				if (((DNode) g.getNode(target)).getE().isEmpty()) {
					it.remove();
					targets.add(target);
					// System.out.println("targets: "+targets);
					break;
				}
				target = it.next();
			}
		} else {
			System.out.println("There is no way between this nodes");
			return null;
		}

		it = targets.iterator();
		ans = shortestPath(it.next(), it.next());

		if (ans == null)
			return null;

		while (it.hasNext()) {
			int pointer = it.next();
			if (ans.contains(g.getNode(pointer))) {
				continue;
			} else {
				int last = ((LinkedList<node_data>) ans).peekLast().getKey();
				((LinkedList<node_data>) ans).removeLast();
				if (shortestPath(last, pointer) == null)
					return null;
				ans.addAll(shortestPath(last, pointer));
			}
		}
		return ans;
	}

	@Override
	public graph copy() {
		String file = "copied";
		save(file);
		Graph_Algo newGraph = new Graph_Algo();
		newGraph.init(file);
		return newGraph.g;
	}

	private void clear() {
		for (node_data nodes : g.getV()) {
			nodes.setTag(0);
			nodes.setWeight(Double.MAX_VALUE);
		}
	}

	private int getMin() {
		DNode min = new DNode(-1);
		min.setWeight(Double.MAX_VALUE);
		int minKey = -1;
		for (node_data nodes : g.getV()) {
			if (nodes.getWeight() < min.getWeight() && nodes.getTag() == 0) {
				min.setWeight(nodes.getWeight());
				;
				minKey = nodes.getKey();
			}
		}
		return minKey;
	}

	private boolean TwoNodesNoEdge(List<Integer> targets) {
		Iterator<Integer> it = targets.iterator();
		int counter = 0;
		while (it.hasNext()) {
			int target = it.next();
			if (((DNode) g.getNode(target)).getE().isEmpty()) {
				counter++;
			}
		}
		if (counter > 1)
			return false;
		return true;
	}

}
