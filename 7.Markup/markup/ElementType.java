package markup;

import java.util.List;

public abstract class ElementType implements MarkupElements {
    private final List<MarkupElements> elements;

    public ElementType(List<MarkupElements> elements) {
        this.elements = elements;
    }

    public abstract String getMarkDownSymbols();

    public abstract String getDocBookRole();


    protected void insertFormattingMark(StringBuilder strBuilder) {
        strBuilder.append(getMarkDownSymbols());
        for (MarkupElements element : elements) {
            element.toMarkdown(strBuilder);
        }
        strBuilder.append(getMarkDownSymbols());
    }

    protected void insertFormattingDoc(StringBuilder strBuilder) {
        strBuilder.append("<emphasis");
        if (getDocBookRole() != "") {
            strBuilder.append(" role='").append(getDocBookRole()).append("'>");
        } else {
            strBuilder.append(">");
        }
        for (MarkupElements element : elements) {
            element.toDocBook(strBuilder);
        }
        strBuilder.append("</emphasis>");
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        insertFormattingMark(strBuilder);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        insertFormattingDoc(strBuilder);
    }

}