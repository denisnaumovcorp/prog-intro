package markup;

import java.util.List;

public class Paragraph implements Mark {
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
}
