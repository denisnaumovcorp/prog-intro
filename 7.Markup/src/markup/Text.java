package markup;

public class Text implements Mark {
    private final String text;
    String docStartTeg = "<emphasis role='strikeout'>";
    String docEndTeg = "</emphasis>";

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        strBuilder.append(text);
    }
}
