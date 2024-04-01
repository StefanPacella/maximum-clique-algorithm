package cache;

import java.util.Set;

public abstract class Operation {

	private boolean failToUpdate;

	private int sizeCurrentSolution;

	private Set<Integer> currentSolution;

	public abstract void operation() throws BusinessException;

	public boolean isFailToUpdate() {
		return failToUpdate;
	}

	public void setFailToUpdate(boolean failToUpdate) {
		this.failToUpdate = failToUpdate;
	}

	public int getSizeCurrentSolution() {
		return sizeCurrentSolution;
	}

	public void setSizeCurrentSolution(int sizeCurrentSolution) {
		this.sizeCurrentSolution = sizeCurrentSolution;
	}

	public Set<Integer> getCurrentSolution() {
		return currentSolution;
	}

	public void setCurrentSolution(Set<Integer> currentSolution) {
		this.currentSolution = currentSolution;
	}

}
