package vm.java.io.evm;

import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Vote;

import java.util.List;
import java.util.Map;

public class ElectronicVotingMachine {

    private final VotingCounter votingCounter;


    public ElectronicVotingMachine(VotingCounter votingCounter) {
        this.votingCounter = votingCounter;
    }

    public void castVote(Vote vote) {
        if(vote == null) {
            throw new NullPointerException("Vote cannot be null");
        }
        votingCounter.castVote(vote);
    }

    public VotingResult getResults() {
        return new SimpleVotingResult(votingCounter.getCastedVotes());
    }

    public VotingResult getSortedVotingResult() {
        return new SortedVotingResultByCount(votingCounter.getCastedVotes());
    }
}
