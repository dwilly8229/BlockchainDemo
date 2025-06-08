import java.util.Random;

public class App {

    public static void main(String[] args) {
        Random random = new Random();

        // PoW: miners with power
        String[] miners = {"Miner1", "Miner2", "Miner3"};
        int[] powers = {random.nextInt(100), random.nextInt(100), random.nextInt(100)};

        // PoS: stakers with stake
        String[] stakers = {"Staker1", "Staker2", "Staker3"};
        int[] stakes = {random.nextInt(100), random.nextInt(100), random.nextInt(100)};

        // DPoS: delegates and votes
        String[] delegates = {"Delegate1", "Delegate2", "Delegate3"};
        int[] votes = {0, 0, 0};

        String[] voters = {"VoterA", "VoterB", "VoterC"};

        // Select PoW winner (highest power)
        int maxPowerIndex = 0;
        for (int i = 1; i < powers.length; i++) {
            if (powers[i] > powers[maxPowerIndex]) {
                maxPowerIndex = i;
            }
        }

        // Select PoS winner (highest stake)
        int maxStakeIndex = 0;
        for (int i = 1; i < stakes.length; i++) {
            if (stakes[i] > stakes[maxStakeIndex]) {
                maxStakeIndex = i;
            }
        }

        // Each voter votes randomly for a delegate in DPoS
        for (int i = 0; i < voters.length; i++) {
            int voteFor = random.nextInt(delegates.length);
            votes[voteFor]++;
            System.out.println(voters[i] + " voted for " + delegates[voteFor]);
        }

        // Find delegate with most votes
        int maxVotesIndex = 0;
        for (int i = 1; i < votes.length; i++) {
            if (votes[i] > votes[maxVotesIndex]) {
                maxVotesIndex = i;
            }
        }

        System.out.println("\nPoW winner is " + miners[maxPowerIndex] + " with power " + powers[maxPowerIndex]);
        System.out.println("PoS winner is " + stakers[maxStakeIndex] + " with stake " + stakes[maxStakeIndex]);
        System.out.println("DPoS winner is " + delegates[maxVotesIndex] + " with votes " + votes[maxVotesIndex]);
    }
}
