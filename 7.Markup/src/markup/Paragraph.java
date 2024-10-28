package markup;

import java.util.List;

public class Paragraph implements Mark, DocBook {
    private final List<Mark> elements;
    
    public Paragraph(List<Mark> elements) {
        this.elements = elements;
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        for (Mark element : elements) {
            element.toMarkdown(strBuilder);
        }
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        for (Mark element : elements) {
            element.toMarkdown(strBuilder);
        }
    }

    protected void insertFormatting(StringBuilder strBuilder, String markSymbols) {
        strBuilder.append(markSymbols);
        for (Mark element : elements) {
            element.toMarkdown(strBuilder);
        }
        strBuilder.append(markSymbols);
    }

    protected void insertFormatting(StringBuilder strBuilder, String docStartTeg, docEndTeg) {
        strBuilder.append(docStartTeg);
        for (Mark element : elements) {
            element.toMarkdown(strBuilder);
        }
        strBuilder.append(docEndTeg);
    }
}
