package vm.java.io.evm;

import org.junit.jupiter.api.Test;
import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Rank;
import vm.java.io.evm.model.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortedVotingResultByCountAndRankTest {
    @Test
    public void testGetTieResultValid() {
        Map<Candidate, List<Vote>> voteMap = new HashMap<>();
        Candidate candidate1 = new Candidate("Candidate 1");
        Candidate candidate2 = new Candidate("Candidate 2");
        Candidate candidate3 = new Candidate("Candidate 3");
        Vote vote1 = new Vote(1L, 1L, Rank.HIGH, System.currentTimeMillis());
        Vote vote2 = new Vote(2L, 2L, Rank.HIGH, System.currentTimeMillis());
        Vote vote3 = new Vote(3L, 3L, Rank.HIGH, System.currentTimeMillis());
        Vote vote4 = new Vote(4L, 1L, Rank.MEDIUM, System.currentTimeMillis());
        Vote vote5 = new Vote(5L, 2L, Rank.MEDIUM, System.currentTimeMillis());
        Vote vote6 = new Vote(6L, 3L, Rank.MEDIUM, System.currentTimeMillis());
        voteMap.put(candidate1, List.of(vote1, vote4));
        voteMap.put(candidate2, List.of(vote2, vote5));
        voteMap.put(candidate3, List.of(vote3, vote6));
        VotingResult sortedVotingResultByCount = new SortedVotingResultByCountAndRank(voteMap);

        Map<Candidate, Long> result = sortedVotingResultByCount.getResult();
        assertEquals(3, result.size());
        assertEquals(5, result.get(candidate1));
        assertEquals(5, result.get(candidate2));
        sortedVotingResultByCount.printResult();
    }

    @Test
    public void testGetResultValid() {
        Map<Candidate, List<Vote>> voteMap = new HashMap<>();
        Candidate candidate1 = new Candidate("Candidate 1");
        Candidate candidate2 = new Candidate("Candidate 2");
        Candidate candidate3 = new Candidate("Candidate 3");
        Vote vote1 = new Vote(1L, 1L, Rank.HIGH, System.currentTimeMillis());
        Vote vote2 = new Vote(2L, 2L, Rank.HIGH, System.currentTimeMillis());
        Vote vote3 = new Vote(3L, 3L, Rank.HIGH, System.currentTimeMillis());
        Vote vote4 = new Vote(4L, 1L, Rank.MEDIUM, System.currentTimeMillis());
        Vote vote5 = new Vote(5L, 2L, Rank.MEDIUM, System.currentTimeMillis());
        Vote vote6 = new Vote(6L, 3L, Rank.MEDIUM, System.currentTimeMillis());
        Vote vote7 = new Vote(7L, 3L, Rank.LOW, System.currentTimeMillis());
        voteMap.put(candidate1, List.of(vote1, vote4));
        voteMap.put(candidate2, List.of(vote2, vote5));
        voteMap.put(candidate3, List.of(vote3, vote6, vote7));
        VotingResult sortedVotingResultByCount = new SortedVotingResultByCountAndRank(voteMap);

        Map<Candidate, Long> result = sortedVotingResultByCount.getResult();
        assertEquals(3, result.size());
        assertEquals(5, result.get(candidate1));
        assertEquals(5, result.get(candidate2));
        sortedVotingResultByCount.printResult();
    }
}
