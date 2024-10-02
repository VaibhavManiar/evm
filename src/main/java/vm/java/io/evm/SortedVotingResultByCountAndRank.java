package vm.java.io.evm;

import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Rank;
import vm.java.io.evm.model.Vote;

import java.util.*;

public class SortedVotingResultByCountAndRank implements VotingResult {

    private final Map<Candidate, List<Vote>> voteMap;

    public SortedVotingResultByCountAndRank(Map<Candidate, List<Vote>> voteMap) {
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
                            Long.compare(getCountByRank(votes2, Rank.HIGH), getCountByRank(votes1, Rank.HIGH)) :
                            Integer.compare(votes2.size(), votes1.size());
                })
                .forEach(e -> result.put(e.getKey(), (long) e.getValue().stream().map(Vote::getRank).map(Rank::getMultiplier).reduce(0, Integer::sum) ));
        return result;
    }

    public Long getCountByRank(List<Vote> votes, Rank rank) {
        Map<Rank, Long> rankMap = getRank(votes);
        return rankMap.get(rank);
    }

    public Map<Rank, Long> getRank(List<Vote> votes) {
        Map<Rank, Long> result = new HashMap<>();
        for(Vote vote : votes) {
            Rank rank = vote.getRank();
            if(rank != null)
                result.put(rank, result.getOrDefault(rank, 0L) + 1);
        }
        return result;
    }


}
