package vm.java.io.evm.model;

public class Vote {

	private final long voterId;
	private final long candidateId;
	private final Rank rank;
	private final long timestamp;
	private final static int DEFAULT_MULTIPLIER = 1;

    public Vote(long voterId, long candidateId, Rank rank, long timestamp) {
		if(voterId <= 0 || candidateId <= 0 || rank == null) {
			throw new IllegalArgumentException("Invalid voterId, candidateId or rank");
		}
		if(timestamp <= 0 || timestamp > System.currentTimeMillis()) {
			throw new IllegalArgumentException("Invalid timestamp");
		}
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.rank = rank;
		this.timestamp = timestamp;
    }

	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}

		if(o instanceof Vote) {
			Vote that = (Vote) o;
			return this.voterId == that.voterId && 
				this.candidateId == that.candidateId && 
				this.rank == that.rank;
		}
		return false;
	}

	public int hashcode() {
		return Long.valueOf(voterId).hashCode() + Long.valueOf(candidateId).hashCode() + rank.hashCode();
	}

	public long getVoterId() {
		return voterId;
	}

	public long getCandidateId() {
		return candidateId;
	}

	public Rank getRank() {
		return rank;
	}

	public long getTimestamp() {
		return timestamp;
	}
}