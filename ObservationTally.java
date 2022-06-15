import java.util.Objects;

public class ObservationTally {
    private int count;
    private int total;

    /*
     * Note that this does not set the value or increment the counter.
     * It just creates an object with no count and no total so far.
     */
    public ObservationTally() {
        count = 0;
        total = 0;
    }

    public ObservationTally(int count, int total) {
        this.count = count;
        this.total = total;
    }

    public void increaseTotal(int value) {
        total += value;
        count++;
    }

    public double calculateScore() {
        if (count == 0) {
            return 0;
        }
        return ((double) total) / count;
    }

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ObservationTally)) return false;
        ObservationTally otherTally = (ObservationTally) other;
        return count == otherTally.count && total == otherTally.total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, total);
    }

    @Override
    public String toString() {
        return "ObservationTally{" +
                "count=" + count +
                ", total=" + total +
                '}';
    }
}
