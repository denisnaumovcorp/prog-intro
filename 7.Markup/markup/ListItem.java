package markup;

import java.util.List;

public class ListItem implements DocBook {
    private final List<IsList> elements;
    private static final String DOC_START_TEG = "<listitem>";
    private static final String DOC_END_TEG = "</listitem>";

    public ListItem(List<IsList> elements) {
        this.elements = elements;
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        strBuilder.append(DOC_START_TEG);
        for (IsList element : elements) {
            element.toDocBook(strBuilder);
        }
        strBuilder.append(DOC_END_TEG);
    }

}
