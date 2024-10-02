package vm.java.io.evm;

import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Rank;
import vm.java.io.evm.model.Vote;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SortedVotingResultByCount implements VotingResult {

    private final Map<Candidate, List<Vote>> voteMap;

    public SortedVotingResultByCount(Map<Candidate, List<Vote>> voteMap) {
        if(voteMap == null) {
            throw new NullPointerException("Vote map cannot be null");
        }
        this.voteMap = voteMap;
    }

    @Override
    public Map<Candidate, Long> getResult() {
        Map<Candidate, Long> result = new LinkedHashMap<>();
        // Sort the result by vote count in descending order
        voteMap.entrySet().stream()
                .sorted((e1, e2) -> {
                    List<Vote> votes1 = e1.getValue();
                    List<Vote> votes2 = e2.getValue();
                    return votes1.size() == votes2.size() ?
                            Long.compare(votes2.get(votes2.size() - 1).getTimestamp(), votes1.get(votes1.size() - 1).getTimestamp()) :
                            Integer.compare(votes2.size(), votes1.size());
                })
                .forEach(e -> result.put(e.getKey(), (long) e.getValue().stream().map(Vote::getRank).map(Rank::getMultiplier).reduce(0, Integer::sum)));
        return result;
    }
}
