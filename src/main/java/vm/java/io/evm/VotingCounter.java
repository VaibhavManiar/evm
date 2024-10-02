package vm.java.io.evm;

import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Vote;
import vm.java.io.evm.model.Voter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class VotingCounter {
    private final Map<Long, Candidate> candidates;
    private final Map<Long, Voter> voters;
    private final Map<Candidate, List<Vote>> voteMap = new ConcurrentHashMap<>();

    public VotingCounter(Map<Long, Candidate> candidates, Map<Long, Voter> voters) {
        this.candidates = candidates;
        this.voters = voters;
    }

    public void castVote(Vote vote) {
        if(candidates.containsKey(vote.getCandidateId()) && voters.containsKey(vote.getVoterId())) {
            Candidate candidate = candidates.get(vote.getCandidateId());
            voteMap.computeIfAbsent(candidate, k -> new ArrayList<>());
            voteMap.get(candidate).add(vote);
            return;
        }
        throw new IllegalArgumentException("Invalid candidate or voter");
    }

    public Map<Candidate, List<Vote>> getCastedVotes() {
        return voteMap;
    }
}
