package markup;

import java.util.List;

public abstract class ElementType implements MarkupElements {
    private final List<MarkupElements> elements;
    public ElementType(List<MarkupElements> elements) {
        this.elements = elements;
    }

    protected void insertFormattingMark(StringBuilder strBuilder, String markSymbols) {
        strBuilder.append(markSymbols);
        for (MarkupElements element : elements) {
            element.toMarkdown(strBuilder);
        }
        strBuilder.append(markSymbols);
    }

    protected void insertFormattingDoc(StringBuilder strBuilder, String docStartTeg, String docEndTeg) {
        strBuilder.append(docStartTeg);
        for (MarkupElements element : elements) {
            element.toDocBook(strBuilder);
        }
        strBuilder.append(docEndTeg);
    }
}