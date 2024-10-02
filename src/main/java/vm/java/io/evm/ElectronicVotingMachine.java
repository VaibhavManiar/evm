package vm.java.io.evm;

import vm.java.io.evm.model.Vote;

public interface ElectronicVotingMachine {
    void castVote(Vote vote);
    VotingResult getResults();
    VotingResult getSortedVotingResult();
    VotingResult getSortedVotingResultByRank();
}
