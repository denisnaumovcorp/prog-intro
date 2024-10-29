package markup;

import java.util.List;

public class ListItem implements DocBook {
    private final List<IsList> elements;
    private static final String docStartTeg = "<listitem>";
    private static final String docEndTeg = "</listitem>";

    public ListItem(List<IsList> elements) {
        this.elements = elements;
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
            strBuilder.append(docStartTeg);
            for (IsList element : elements) {
                element.toDocBook(strBuilder);
            }
            strBuilder.append(docEndTeg);
    }

}
