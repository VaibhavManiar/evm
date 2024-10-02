package vm.java.io.evm;

import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Rank;
import vm.java.io.evm.model.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleVotingResult implements VotingResult {

    private final Map<Candidate, List<Vote>> voteMap;

    public SimpleVotingResult(Map<Candidate, List<Vote>> voteMap) {
        if(voteMap == null) {
            throw new NullPointerException("Vote map cannot be null");
        }
        this.voteMap = voteMap;
    }

    @Override
    public Map<Candidate, Long> getResult() {
        Map<Candidate, Long> result = new HashMap<>();
        for (Map.Entry<Candidate, List<Vote>> entry : voteMap.entrySet()) {
            result.put(entry.getKey(), (long) entry.getValue().stream().map(Vote::getRank).map(Rank::getMultiplier).reduce(0, Integer::sum));
        }
        return result;
    }
}
