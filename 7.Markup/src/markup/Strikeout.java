package markup;

import java.util.List;

public class Strikeout extends ElementType {
    private static final String markSymbols = "~";
    private static final String docStartTeg = "<emphasis role='strikeout'>";
    private static final String docEndTeg = "</emphasis>";

    public Strikeout(List<MarkupElements> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        super.insertFormattingMark(strBuilder, markSymbols);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        super.insertFormattingDoc(strBuilder, docStartTeg, docEndTeg);
    }
}
