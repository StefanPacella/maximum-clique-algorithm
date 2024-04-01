package domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utility.FileManager;
import utility.StringToInterger;

public class Graph {

	private Map<Integer, Set<Integer>> mapNodes = new HashMap<Integer, Set<Integer>>();
	private String pathfile;

	public Graph(String pathfile) {
		// TODO Auto-generated constructor stub
		this.pathfile = pathfile;
	}

	public Graph() {
		// TODO Auto-generated constructor stub
	}

	public void readfromfileAndMakeGraph() throws IOException {
		if (pathfile.contains("txt") || pathfile.contains("clq"))
			readTXT();
		else
			readMTX();
	}

	private void readTXT() throws IOException {
		FileManager f = new FileManager(pathfile);
		List<String> ls = f.readTheFile();
		Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
		for (String s : ls) {
			if (containsE(s)) {
				addToMap(map, getPairofNode(s));
			}
		}
		mapNodes = map;
	}

	private void readMTX() throws IOException {
		FileManager f = new FileManager(pathfile);
		List<String> ls = f.readTheFile();
		Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
		List<List<Integer>> listnodes = new ArrayList<List<Integer>>();
		for (String s : ls) {
			List<Integer> ln = getPairofNode(s);
			if (getPairofNode(s).size() == 2) {
				listnodes.add(ln);
			}
		}
		listnodes.stream().forEach(x -> addToMap(map, x));
		mapNodes = map;
	}

	private List<Integer> getPairofNode(String s) {
		StringToInterger sti = new StringToInterger();
		List<Integer> arrayNode = sti.getNumeri(s);
		return arrayNode;
	}

	private void addToMap(Map<Integer, Set<Integer>> map, List<Integer> arrayNode) {
		for (Integer node : arrayNode) {
			if (!map.containsKey(node)) {
				map.put(node, new HashSet<Integer>());
			}
			for (Integer nb : arrayNode) {
				if (nb != node)
					map.get(node).add(nb);
			}
		}
	}

	public Set<Integer> getListNeighbors(Integer node) {
		return mapNodes.get(node);
	}

	public Map<Integer, Set<Integer>> getMapNodes() {
		return mapNodes;
	}

	public Set<Integer> getListNodes() {
		return mapNodes.keySet();
	}

	public void addNewNodeAndListNeighbors(Integer node, Set<Integer> s) {
		mapNodes.put(node, s);
	}

	private boolean containsE(String s) {
		s = s.toLowerCase();

		for (int i = 97; i < 122; i++) {
			if (i != 101 && s.contains(((char) i) + "")) {
				return false;
			}
		}
		return true;
	}

}
