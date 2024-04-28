package algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import cache.CacheMaster;
import cache.CacheSlave;
import domain.Graph;

public class MaximumCliqueAlgorithm extends Thread {

	private CacheSlave cache;
	private static CacheMaster cacheMaster = new CacheMaster();
	private static volatile Map<Integer, Integer> mapNodesChromaticColor = new HashMap<Integer, Integer>();
	private static Semaphore semaphoreSolve;
	private static Semaphore semaphoreMainThread = new Semaphore(0);
	private static volatile int nThread = 0;
	private Integer node;
	private List<Integer> p;
	private Graph graph;

	public MaximumCliqueAlgorithm(Integer node, List<Integer> p, Graph graph) {
		// TODO Auto-generated constructor stub
		this.node = node;
		this.p = p;
		this.graph = graph;
		cache = new CacheSlave(cacheMaster);
		cacheMaster.addSlave(cache);
	}

	public static Set<Integer> solve(Graph graph) {
		AlgorithmGraphColoring algorithmGraphColoring = new AlgorithmGraphColoring(graph);
		algorithmGraphColoring.greedy();
		mapNodesChromaticColor = algorithmGraphColoring.getMap();

		List<Integer> p = graph.getListNodes().stream().collect(Collectors.toList());

		setManyCores(p);

		p = p.stream().sorted(Comparator.comparing(x -> mapNodesChromaticColor.get(x))).collect(Collectors.toList());

		int limit = p.size();
		for (int i = 0; i < limit; i++) {
			Integer n = p.getFirst();
			tryacquire(semaphoreSolve);
			p = difference(p, n);
			List<Integer> newP = intersection(p, graph.getListNeighbors(n));

			MaximumCliqueAlgorithm maximumCliqueAlgorithm = new MaximumCliqueAlgorithm(n, newP, graph);
			maximumCliqueAlgorithm.start();

			nThread++;
			System.out.println("Loalding...");
		}

		for (int i = 0; i < nThread; i++) {
			tryacquire(semaphoreMainThread);
		}

		return cacheMaster.getCurrentSolution();
	}

	private void findClique(Set<Integer> clique, List<Integer> p) {
		Integer currentSizeSolution = cache.getSizeCurrentSolution();
		if (clique.size() > currentSizeSolution) {
			cache.updateTheCurrentSolution(clique);
			currentSizeSolution = cache.getSizeCurrentSolution();
		}
		if (clique.size() + p.size() > currentSizeSolution) {
			
			Graph graph = Graph.createTheSubgraph(p, this.graph);
			AlgorithmGraphColoring algorithmGraphColoring = new AlgorithmGraphColoring(graph);
			algorithmGraphColoring.greedy();
			Map<Integer, Integer> mapSubNodesChromaticColor = algorithmGraphColoring.getMap();
			
			p = p.stream().sorted(Comparator.comparing(x -> -mapSubNodesChromaticColor.get(x)))
					.collect(Collectors.toList());
			
			Integer limit = p.size();
			for (int i = 0; i < limit; i++) {
				Integer n = p.getFirst();
				p = difference(p, n);
				currentSizeSolution = cache.getSizeCurrentSolution();
				if (clique.size() + mapSubNodesChromaticColor.get(n) > currentSizeSolution) {
					Set<Integer> newClique = union(clique, n);
					List<Integer> newP = intersection(p, graph.getListNeighbors(n));
					findClique(newClique, newP);
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		graph = Graph.createTheSubgraph(p, this.graph);
		if (graph.getListNodes().isEmpty()) {
			semaphoreSolve.release();
			semaphoreMainThread.release();
			return;
		}
		if (mapNodesChromaticColor.get(node) + 1 > cacheMaster.getCurrentSolution().size()) {
			Set<Integer> newClique = union(new HashSet<Integer>(), node);
			findClique(newClique, p);
		}
		semaphoreSolve.release();
		semaphoreMainThread.release();
	}

	private static List<Integer> difference(List<Integer> listvertices, Integer node) {
		listvertices.remove(node);
		return listvertices;
	}

	private static Set<Integer> union(Set<Integer> sClique, Integer node) {
		sClique = sClique.stream().collect(Collectors.toSet());
		sClique.add(node);
		return sClique;
	}

	private static List<Integer> intersection(List<Integer> listvertices, Set<Integer> listNeighbors) {
		return listNeighbors.stream().filter(x -> listvertices.contains(x)).collect(Collectors.toList());
	}

	public static int setManyCores(List<Integer> listvertices) {
		int cores = Runtime.getRuntime().availableProcessors();
		if (listvertices.size() > cores) {
			semaphoreSolve = new Semaphore(cores);
			return cores;
		} else {
			semaphoreSolve = new Semaphore(listvertices.size());
			return listvertices.size();
		}
	}

	public static void tryacquire(Semaphore semaphore) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}

}
