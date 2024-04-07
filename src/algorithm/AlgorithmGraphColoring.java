package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import domain.Graph;

public class AlgorithmGraphColoring {

	private Map<Integer, Integer> mapNodesChromaticColor = new HashMap<Integer, Integer>();
	private Graph graph;
	private List<Integer> stack = new ArrayList<Integer>();
	private Integer nColor = 1;

	public AlgorithmGraphColoring(Graph graph) {
		this.graph = graph;
	}

	public void greedy() {
		colorTheGraph();
	}

	private void colorTheGraph() {
		List<Integer> listnode = graph.getListNodes().stream().collect(Collectors.toList());
		while (!listnode.isEmpty()) {
			List<Integer> ln = listnode.stream().toList();
			Set<Integer> colored = colorTheNodes(ln);
			listnode.removeAll(colored);
		}
	}

	private Set<Integer> colorTheNodes(List<Integer> listnode) {
		Set<Integer> colored = new HashSet<Integer>();

		Integer node = getNodeWithMoreNeighbors(listnode);
		colored.add(node);

		mapNodesChromaticColor.put(node, 1);
		stack.addAll(graph.getListNeighbors(node));

		while (!stack.isEmpty()) {
			Integer size = stack.size();
			Set<Integer> temp = new HashSet<Integer>();
			for (Integer i = 0; i < size; i++) {
				Integer nc = getNodeWithMoreNeighbors(stack);
				colorTheNode(nc);
				stack.remove(nc);
				colored.add(nc);
				temp.add(nc);
			}
			for (Integer nc : temp) {
				stack.addAll(addNeighborsInTheStack(nc));
			}
		}
		return colored;
	}

	private Set<Integer> addNeighborsInTheStack(Integer node) {
		Set<Integer> stack = new HashSet<Integer>();
		for (Integer n : graph.getListNeighbors(node)) {
			if (!mapNodesChromaticColor.containsKey(n))
				stack.add(n);
		}
		return stack;
	}

	private void colorTheNode(Integer node) {
		Integer color = findTheColor(node);

		if (color > nColor)
			nColor = color;

		mapNodesChromaticColor.put(node, color);
	}

	private Integer findTheColor(Integer node) {
		for (Integer color = 1; color <= nColor; color++) {
			if (itIsColorable(color, graph.getListNeighbors(node))) {
				return color;
			}
		}
		return nColor + 1;
	}

	private boolean itIsColorable(Integer color, Set<Integer> nodes) {
		for (Integer n : nodes) {
			if (mapNodesChromaticColor.containsKey(n) && mapNodesChromaticColor.get(n).equals(color))
				return false;
		}
		return true;
	}

	private Integer getNodeWithMoreNeighbors(List<Integer> listNodes) {
		Integer i = listNodes.stream().findAny().get();
		for (Integer node : listNodes) {
			if (graph.getListNeighbors(i).size() < graph.getListNeighbors(node).size())
				i = node;
		}
		return i;
	}

	public Map<Integer, Integer> getMap() {
		return mapNodesChromaticColor;
	}

}
