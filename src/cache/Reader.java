package cache;

import java.util.concurrent.Semaphore;

public class Reader<T> {

	private Operation operation;
	private Semaphore semaphoreR;
	private Semaphore semaphoreWr;
	private T element;
	private WriterReaderProblem<T> writerReaderProblem;

	public Reader(Operation operation, WriterReaderProblem<T> w) {
		// TODO Auto-generated constructor stub
		this.operation = operation;
		this.semaphoreR = w.getReaderSemaphore();
		this.semaphoreWr = w.getWriterSemaphore();
		this.writerReaderProblem = w;
	}

	public void read() throws BusinessException {
		acquireResource();

		try {
			operation.operation();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			releaseResource();
			throw new BusinessException(e);
		}
		releaseResource();
	}

	private void acquireResource() {
		acquireSemaphore(semaphoreR);
		writerReaderProblem.addOneToNWriter();
		if (writerReaderProblem.getnWriter() == 1) {
			acquireSemaphore(semaphoreWr);
		}
		semaphoreR.release();
	}
	
	private void releaseResource() {
		acquireSemaphore(semaphoreR);
		writerReaderProblem.minusOneToNWriter();
		if (writerReaderProblem.getnWriter() == 0) {
			semaphoreWr.release();
		}
		semaphoreR.release();
	}

	private void acquireSemaphore(Semaphore s) {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

}
