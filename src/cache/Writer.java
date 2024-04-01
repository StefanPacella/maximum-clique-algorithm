package cache;

import java.util.concurrent.Semaphore;

public class Writer<T> extends Thread {

	private Semaphore semaphoreWr;
	private Operation operation;

	public Writer(WriterReaderProblem<T> w, Operation operation) {
		this.semaphoreWr = w.getWriterSemaphore();
		this.operation = operation;
	}

	public void run() {
		try {
			write();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeAsync() {
		this.start();
	}

	public void writeSync() {
		try {
			write();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void write() throws BusinessException {
		acquireSemaphore();
		try {
			operation.operation();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			semaphoreWr.release();
			throw new BusinessException(e);
		}
		semaphoreWr.release();
	}

	private void acquireSemaphore() throws BusinessException {
		try {
			semaphoreWr.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new BusinessException("semaphore fail"); 
		}
	}

}
