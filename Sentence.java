import java.util.Objects;

public class Sentence {
    protected int score;
    protected String text;

    public Sentence(int score, String text) {
        this.score = score;
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sentence)) return false;
        Sentence otherSentence = (Sentence) o;
        return score == otherSentence.score && Objects.equals(text, otherSentence.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, text);
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "score=" + score +
                ", text='" + text + '\'' +
                '}';
    }
}
