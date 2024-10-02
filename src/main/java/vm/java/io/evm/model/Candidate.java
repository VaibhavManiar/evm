package vm.java.io.evm.model;

import java.util.Map;
import java.util.Random;

public class Candidate {
	private final long id;
	private final String name;
	private final Random random = new Random();

	public Candidate(String name) {
		this.id = Math.abs(random.nextLong());
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o == this) {
			return true;
		}

		if(o instanceof Candidate) {
			Candidate that = (Candidate) o;
			return this.id == that.id && this.name.equals(that.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(this.id).hashCode() + name.hashCode();
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
}