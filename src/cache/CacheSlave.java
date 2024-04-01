package cache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class CacheSlave implements WriterReaderProblem<Integer> {

	private volatile Set<Integer> currentSolution = new HashSet<Integer>();

	private volatile int nWriter = 0;
	private volatile Semaphore semaphoreR = new Semaphore(1);
	private volatile Semaphore semaphoreWr = new Semaphore(1);
	private CacheMaster cacheMaster;

	public CacheSlave(CacheMaster cacheMaster) {
		this.cacheMaster = cacheMaster;
	}

	public int getSizeCurrentSolution() {
		Operation o = new Operation() {
			@Override
			public void operation() throws BusinessException {
				this.setSizeCurrentSolution(currentSolution.size());
			}
		};
		Reader<Integer> reader = new Reader<Integer>(o, this);
		try {
			reader.read();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
		}

		return o.getSizeCurrentSolution();
	}

	public void updateTheCurrentSolution(Set<Integer> sClique) {
		Operation o = new Operation() {
			@Override
			public void operation() throws BusinessException {
				if (sClique.size() > currentSolution.size()) {
					currentSolution = sClique;
					cacheMaster.updateTheCurrentSolutionMaster(sClique);
				} 
			}
		};
		Writer<Integer> writer = new Writer<Integer>(this, o);
		writer.writeSync();
	}

	public void updateTheCurrentSolutionAsync(Set<Integer> sClique) {
		Operation o = new Operation() {
			@Override
			public void operation() throws BusinessException {
				if (sClique.size() > currentSolution.size()) {
					currentSolution = sClique;
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

}
