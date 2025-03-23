package markup;

import java.util.List;

public abstract class ListType implements IsList {
    private final List<ListItem> elements;

    public ListType(List<ListItem> elements) {
        this.elements = elements;
    }

    protected void insertFormatting(StringBuilder strBuilder, String docStartTeg, String docEndTeg) {
        strBuilder.append(docStartTeg);
        for (ListItem element : elements) {
            element.toDocBook(strBuilder);
        }
        strBuilder.append(docEndTeg);
    }
}
