package vm.java.io.evm;

import vm.java.io.evm.model.Candidate;

import java.util.Map;

public interface VotingResult {
    Map<Candidate, Long> getResult();
    default void printResult() {
        Map<Candidate, Long> result = getResult();
        System.out.println("Candidate" + " : " + "Votes");
        for (Map.Entry<Candidate, Long> entry : result.entrySet()) {
            System.out.println(entry.getKey().getName() + " : " + entry.getValue());
        }
    }
}