package vm.java.io.evm.model;

import java.util.Random;

public class Voter {
	private final long id;
	private final String name;
	private final Random random = new Random();

	public Voter(String name) {
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

		if(o instanceof Voter) {
			Voter that = (Voter) o;
			return this.id == that.id && this.name.equals(that.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(this.id).hashCode() + name.hashCode();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}