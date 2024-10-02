package vm.java.io.evm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Rank;
import vm.java.io.evm.model.Vote;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ElectronicVotingMachineTest {

    private VotingCounter votingCounter;
    private ElectronicVotingMachine votingMachine;

    @BeforeEach
    public void setUp() {
        votingCounter = mock(VotingCounter.class);
        votingMachine = new ElectronicVotingMachine(votingCounter);
    }

    @Test
    public void testCastVote() {
        Vote vote = new Vote(1L, 1L, Rank.LOW, System.currentTimeMillis());
        votingMachine.castVote(vote);
        verify(votingCounter, times(1)).castVote(vote);
    }

    @Test
    public void testGetResults() {
        Map<Candidate, List<Vote>> votes = new HashMap<>();
        when(votingCounter.getCastedVotes()).thenReturn(votes);

        VotingResult result = votingMachine.getResults();
        assertTrue(result instanceof SimpleVotingResult);
        assertEquals(votes, ((SimpleVotingResult) result).getResult());
    }

    @Test
    public void testGetSortedVotingResult() {
        Map<Candidate, List<Vote>> votes = new HashMap<>();
        when(votingCounter.getCastedVotes()).thenReturn(votes);

        VotingResult result = votingMachine.getSortedVotingResult();
        assertTrue(result instanceof SortedVotingResultByCount);
        assertEquals(votes, ((SortedVotingResultByCount) result).getResult());
    }

    @Test
    public void testCastVoteWithNullVote() {
        assertThrows(NullPointerException.class, () -> votingMachine.castVote(null));
    }

    @Test
    public void testGetResultsWithNoVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(Collections.emptyMap());
        VotingResult result = votingMachine.getResults();
        assertTrue(result instanceof SimpleVotingResult);
        assertTrue(((SimpleVotingResult) result).getResult().isEmpty());
    }

    @Test
    public void testGetSortedVotingResultWithNoVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(Collections.emptyMap());
        VotingResult result = votingMachine.getSortedVotingResult();
        assertTrue(result instanceof SortedVotingResultByCount);
        assertTrue(((SortedVotingResultByCount) result).getResult().isEmpty());
    }

    @Test
    public void testGetResultsWithInvalidVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> votingMachine.getResults());
    }

    @Test
    public void testGetSortedVotingResultWithInvalidVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> votingMachine.getSortedVotingResult());
    }

    @Test
    public void testGetResultsWithNullVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> votingMachine.getResults());
    }

    @Test
    public void testGetSortedVotingResultWithNullVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> votingMachine.getSortedVotingResult());
    }

    @Test
    public void testGetResultsWithEmptyVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(Collections.emptyMap());

        VotingResult result = votingMachine.getResults();
        assertTrue(result instanceof SimpleVotingResult);
        assertTrue(((SimpleVotingResult) result).getResult().isEmpty());
    }

    @Test
    public void testGetSortedVotingResultWithEmptyVotes() {
        when(votingCounter.getCastedVotes()).thenReturn(Collections.emptyMap());

        VotingResult result = votingMachine.getSortedVotingResult();
        assertTrue(result instanceof SortedVotingResultByCount);
        assertTrue(((SortedVotingResultByCount) result).getResult().isEmpty());
    }

    @Test
    public void testCastVoteWithInvalidCandidateId() {
        assertThrows(IllegalArgumentException.class, () -> new Vote(-1L, 1L, Rank.LOW, System.currentTimeMillis()));
    }

    @Test
    public void testGetResultsWithDuplicateVotes() {
        Candidate candidate = new Candidate("Candidate 1");
        Vote vote1 = new Vote(1L, 1L, Rank.LOW, System.currentTimeMillis());
        Vote vote2 = new Vote(1L, 1L, Rank.LOW, System.currentTimeMillis());

        Map<Candidate, List<Vote>> votes = new HashMap<>();
        votes.put(candidate, List.of(vote1, vote2));

        when(votingCounter.getCastedVotes()).thenReturn(votes);

        VotingResult result = votingMachine.getResults();
        assertTrue(result instanceof SimpleVotingResult);
        assertEquals(2, ((SimpleVotingResult) result).getResult().get(candidate));
    }

    @Test
    public void testCastVoteWithFutureTimestamp() {
        assertThrows(IllegalArgumentException.class, () -> new Vote(1L, 1L, Rank.LOW, System.currentTimeMillis() + 100000));
    }
}