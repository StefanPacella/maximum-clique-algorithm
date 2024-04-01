package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import domain.Graph;

public class GreedyAlgorithmGraphColoring {

	private Map<Integer, Integer> mapNodesChromaticColor = new HashMap<Integer, Integer>();
	private Graph graph;
	private Set<Integer> stack = new HashSet<Integer>();

	public GreedyAlgorithmGraphColoring(Graph graph) {
		this.graph = graph;
	}

	public void colorTheGraph() {
		int node = getNodeWithLessNeighbors(graph.getListNodes());
		mapNodesChromaticColor.put(node, 1);
		stack.addAll(graph.getListNeighbors(node));

		while (!stack.isEmpty()) {
			int size = stack.size();
			Set<Integer> temp = new HashSet<Integer>();
			for (int i = 0; i < size; i++) {
				int nc = getNodeWithLessNeighbors(stack);
				colorTheNode(nc);
				stack.remove(nc);
				temp.add(nc);
			}
			for (Integer nc : temp) {
				stack.addAll(addNeighborsInTheStack(nc));
			}
		}
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
		int color = findTheColor(node);
		mapNodesChromaticColor.put(node, color);
	}

	private int findTheColor(Integer node) {
		Set<Integer> cN = new HashSet<Integer>();
		for (Integer n : graph.getListNeighbors(node)) {
			if (mapNodesChromaticColor.containsKey(n))
				cN.add(mapNodesChromaticColor.get(n));
		}
		cN = cN.stream().sorted().collect(Collectors.toSet());

		for (int i = 1; i < cN.size(); i++) {
			if (!cN.contains(i))
				return i;
		}

		int color = cN.size() + 1;
		return color;
	}

	private int getNodeWithLessNeighbors(Set<Integer> listNodes) {
		int i = listNodes.stream().findAny().get();
		for (Integer node : listNodes) {
			if (graph.getListNeighbors(i).size() < graph.getListNeighbors(node).size())
				i = node;
		}
		return i;
	}

	public Map<Integer, Integer> getMapNodesChromaticColor() {
		return mapNodesChromaticColor;
	}

}
