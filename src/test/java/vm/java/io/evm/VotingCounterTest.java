package vm.java.io.evm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vm.java.io.evm.model.Candidate;
import vm.java.io.evm.model.Rank;
import vm.java.io.evm.model.Vote;
import vm.java.io.evm.model.Voter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VotingCounterTest {

    private VotingCounter votingCounter;
    private Map<Long, Candidate> candidates;
    private Map<Long, Voter> voters;

    @BeforeEach
    public void setUp() {
        candidates = new HashMap<>();
        voters = new HashMap<>();
        votingCounter = new VotingCounter(candidates, voters);
    }

    @Test
    public void testCastVoteValid() {
        Candidate candidate = new Candidate("Candidate 1");
        Voter voter = new Voter("Voter 1");
        candidates.put(candidate.getId(), candidate);
        voters.put(voter.getId(), voter);

        Vote vote = new Vote(voter.getId(), candidate.getId(), Rank.LOW, System.currentTimeMillis());
        votingCounter.castVote(vote);

        Map<Candidate, List<Vote>> castedVotes = votingCounter.getCastedVotes();
        assertTrue(castedVotes.containsKey(candidate));
        assertEquals(1, castedVotes.get(candidate).size());
        assertEquals(vote, castedVotes.get(candidate).get(0));
    }

    @Test
    public void testCastVoteInvalidCandidate() {
        Voter voter = new Voter("Voter 1");
        Candidate candidate = new Candidate("Candidate 1");
        voters.put(voter.getId(), voter);
        //candidates.put(candidate.getId(), candidate);

        Vote vote = new Vote(Math.abs(voter.getId()), Math.abs(candidate.getId()), Rank.LOW, System.currentTimeMillis());
        assertThrows(IllegalArgumentException.class, () -> votingCounter.castVote(vote));
    }

    @Test
    public void testCastVoteInvalidVoter() {
        Candidate candidate = new Candidate("Candidate 1");
        candidates.put(candidate.getId(), candidate);

        Vote vote = new Vote(999L, candidate.getId(), Rank.LOW, System.currentTimeMillis());
        assertThrows(IllegalArgumentException.class, () -> votingCounter.castVote(vote));
    }

    @Test
    public void testCastVoteInvalidCandidateAndVoter() {
        Vote vote = new Vote(999L, 999L, Rank.LOW, System.currentTimeMillis());
        assertThrows(IllegalArgumentException.class, () -> votingCounter.castVote(vote));
    }

    @Test
    public void testGetCastedVotesEmpty() {
        Map<Candidate, List<Vote>> castedVotes = votingCounter.getCastedVotes();
        assertTrue(castedVotes.isEmpty());
    }

    @Test
    public void testGetCastedVotesWithVotes() {
        Candidate candidate = new Candidate("Candidate 1");
        Voter voter = new Voter("Voter 1");
        candidates.put(candidate.getId(), candidate);
        voters.put(voter.getId(), voter);

        Vote vote = new Vote(voter.getId(), candidate.getId(), Rank.LOW, System.currentTimeMillis());
        votingCounter.castVote(vote);

        Map<Candidate, List<Vote>> castedVotes = votingCounter.getCastedVotes();
        assertTrue(castedVotes.containsKey(candidate));
        assertEquals(1, castedVotes.get(candidate).size());
        assertEquals(vote, castedVotes.get(candidate).get(0));
    }
}