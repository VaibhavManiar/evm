package vm.java.io.evm.model;

public enum Rank {
        HIGH (3), MEDIUM(2), LOW(1);
        private final int multiplier;
        Rank(int multiplier) {
            this.multiplier = multiplier;
        }
        public int getMultiplier() {
            return multiplier;
        }
        public static Rank getRank(int multiplier) {
            for (Rank rank : Rank.values()) {
                if (rank.getMultiplier() == multiplier) {
                    return rank;
                }
            }
            return null;
        }
    }