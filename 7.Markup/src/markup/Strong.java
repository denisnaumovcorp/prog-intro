package markup;

import java.util.List;

public class Strong extends ElementType {
    private static final String markSymbols = "__";
    private static final String docStartTeg = "<emphasis role='bold'>";
    private static final String docEndTeg = "</emphasis>";

    public Strong(List<MarkupElements> content) {
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
