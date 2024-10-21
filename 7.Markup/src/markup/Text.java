package markup;

public class Text implements Mark {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        strBuilder.append(text);
    }
}
