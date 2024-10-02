package vm.java.io.evm;

import vm.java.io.evm.model.Vote;

public class DefaultElectronicVotingMachine implements ElectronicVotingMachine {

    private final VotingCounter votingCounter;

    public DefaultElectronicVotingMachine(VotingCounter votingCounter) {
        this.votingCounter = votingCounter;
    }

    @Override
    public void castVote(Vote vote) {
        if(vote == null) {
            throw new NullPointerException("Vote cannot be null");
        }
        votingCounter.castVote(vote);
    }

    @Override
    public VotingResult getResults() {
        return new SimpleVotingResult(votingCounter.getCastedVotes());
    }

    @Override
    public VotingResult getSortedVotingResult() {
        return new SortedVotingResultByCount(votingCounter.getCastedVotes());
    }

    @Override
    public VotingResult getSortedVotingResultByRank() {
        return new SortedVotingResultByCountAndRank(votingCounter.getCastedVotes());
    }
}
