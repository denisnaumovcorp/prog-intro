package markup;

import java.util.List;

public class Paragraph implements IsList, DocBook, Mark {
    private final List<MarkupElements> elements;
    private static final String docStartTegPara = "<para>";
    private static final String docEndTegPara = "</para>";
    
    public Paragraph(List<MarkupElements> elements) {
        this.elements = elements;
    }


    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        for (MarkupElements element : elements) {
            element.toMarkdown(strBuilder);
        }
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        strBuilder.append(docStartTegPara);
        for (MarkupElements element : elements) {
            element.toDocBook(strBuilder);
        }
        strBuilder.append(docEndTegPara);
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
