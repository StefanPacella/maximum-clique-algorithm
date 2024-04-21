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
	private Integer nColor = 1;

	public AlgorithmGraphColoring(Graph graph) {
		this.graph = graph;
	}

	public void greedy() {
		colorTheGraph();
	}

	private void colorTheGraph() {
		List<Integer> listnode = graph.getListNodes().stream().collect(Collectors.toList());

		listnode = listnode.stream().sorted(Comparator.comparing(x -> -graph.getListNeighbors(x).size()))
				.collect(Collectors.toList());

		int limit = listnode.size();
		for (int i = 0; i < limit; i++) {
			Integer n = listnode.getFirst();
			listnode.remove(n);
			colorTheNode(n);
		}

	}

	private void colorTheNode(Integer node) {
		Integer color = findTheColor(node);

		if (color > nColor)
			nColor = color;

		mapNodesChromaticColor.put(node, color);
	}

	private Integer findTheColor(Integer node) {
		List<Integer> colorN = new ArrayList<Integer>();
		for (Integer n : graph.getListNeighbors(node)) {
			if (mapNodesChromaticColor.containsKey(n))
				colorN.add(mapNodesChromaticColor.get(n));
		}

		for (Integer color = 1; color <= nColor; color++) {
			if (!colorN.contains(color)) {
				return color;
			}
		}
		return nColor + 1;
	}


	public Map<Integer, Integer> getMap() {
		return mapNodesChromaticColor;
	}
}
