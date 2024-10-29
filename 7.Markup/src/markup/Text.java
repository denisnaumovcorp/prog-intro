package markup;

public class Text implements MarkupElements {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        strBuilder.append(text);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        strBuilder.append(text);
    }
}
