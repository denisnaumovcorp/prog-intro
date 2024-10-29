package markup;

import java.util.List;

public class Emphasis extends ElementType {
    private static final String markSymbols = "*";
    private static final String docStartTeg = "<emphasis>";
    private static final String docEndTeg = "</emphasis>";

    public Emphasis(List<MarkupElements> content) {
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
