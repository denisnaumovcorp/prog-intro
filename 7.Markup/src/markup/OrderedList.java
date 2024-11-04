package markup;

import java.util.List;

public class OrderedList extends ListType {
    private static final String DOC_START_TEG = "<orderedlist>";
    private static final String DOC_END_TEG = "</orderedlist>";

    public OrderedList(List<ListItem> content) {
        super(content);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, DOC_START_TEG, DOC_END_TEG);
    }

}
