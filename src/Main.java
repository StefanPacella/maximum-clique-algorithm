import java.io.IOException;
import java.util.Set;

import algorithm.MaximumCliqueAlgorithm;
import cache.BusinessException;
import domain.Graph;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//// https://networkrepository.com/dimacs.php

		try {
			Graph graph = readTheClique(args[0]);
			long currentTimeMillisBefore = System.currentTimeMillis();
			System.out.println("Solving " + args[0]);

			Set<Integer> solution = MaximumCliqueAlgorithm.solve(graph);

			graph.checkTheSolution(solution);

			System.out.println("size of the solution " + solution.size());

			solution.stream().sorted().forEach(x -> System.out.print(x + " "));

			System.out.println();

			long currentTimeMillisAfter = System.currentTimeMillis();

			System.out.println(
					"Time : " + ((double) (currentTimeMillisAfter - currentTimeMillisBefore)) / 1000 + " seconds");

			System.out.println(" ");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid file");
		}

	}

	private static Graph readTheClique(String file) throws BusinessException {
		try {
			Graph graph = new Graph(file);
			graph.readfromfileAndMakeGraph();
			return graph;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				String path = System.getProperty("user.dir");
				Graph graph = new Graph(path + "/" + file);
				graph.readfromfileAndMakeGraph();
				return graph;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				throw new BusinessException();
			}
		}
	}

}
