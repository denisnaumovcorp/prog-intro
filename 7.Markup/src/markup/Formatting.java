package markup;

import java.util.List;

public abstract class Formatting implements Mark {
    private final List<Mark> elements;

    protected Formatting(List<Mark> elements)
    {
        this.elements = elements;
    }

    protected void insertFormatting(StringBuilder strBuilder, String markSymbols) {
        strBuilder.append(markSymbols);
        for (Mark element : elements) {
            element.toMarkdown(strBuilder);
        } 
        strBuilder.append(markSymbols);
    }
}
