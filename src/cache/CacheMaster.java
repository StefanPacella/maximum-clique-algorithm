package cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class CacheMaster implements WriterReaderProblem<Integer> {

	private volatile Set<Integer> currentSolution = new HashSet<Integer>();

	private volatile int nWriter = 0;
	private volatile Semaphore semaphoreR = new Semaphore(1);
	private volatile Semaphore semaphoreWr = new Semaphore(1);
	private volatile List<CacheSlave> listSlaveCache = new ArrayList<CacheSlave>();

	public CacheMaster() {

	}

	public Set<Integer> getCurrentSolution() {
		Operation o = new Operation() {
			@Override
			public void operation() throws BusinessException {
				this.setCurrentSolution(currentSolution.stream().collect(Collectors.toSet()));
			}
		};
		Reader<Integer> reader = new Reader<Integer>(o, this);
		try {
			reader.read();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
		}

		return o.getCurrentSolution();
	}

	public void updateTheCurrentSolutionMaster(Set<Integer> sClique) {
		Operation o = new Operation() {
			@Override
			public void operation() throws BusinessException {
				if (sClique.size() > currentSolution.size()) {
					currentSolution = sClique;
					listSlaveCache.forEach(x -> x.updateTheCurrentSolutionAsync(sClique));
					System.out.println("Current solution ");
					currentSolution.stream().sorted().forEach(x -> System.out.print(x + " "));
					System.out.println("");
				}
			}
		};
		Writer<Integer> writer = new Writer<Integer>(this, o);
		writer.writeAsync();
	}

	public int getnWriter() {
		return nWriter;
	}

	public void addOneToNWriter() {
		nWriter++;
	}

	public void minusOneToNWriter() {
		nWriter--;
	}

	public Semaphore getWriterSemaphore() {
		return this.semaphoreWr;
	}

	public Semaphore getReaderSemaphore() {
		return this.semaphoreR;
	}

	public void addSlave(CacheSlave cache) {
		Operation o = new Operation() {
			@Override
			public void operation() throws BusinessException {
				listSlaveCache.add(cache);
			}
		};
		Writer<Integer> writer = new Writer<Integer>(this, o);
		writer.writeAsync();
	}

}
