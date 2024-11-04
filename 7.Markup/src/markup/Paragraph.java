package markup;

import java.util.List;

public class Paragraph implements IsList, DocBook, Mark {
    private final List<MarkupElements> elements;
    private static final String DOC_START_TEG = "<para>";
    private static final String DOC_END_TEG = "</para>";
    
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
        strBuilder.append(DOC_START_TEG);
        for (MarkupElements element : elements) {
            element.toDocBook(strBuilder);
        }
        strBuilder.append(DOC_END_TEG);
    }
}
