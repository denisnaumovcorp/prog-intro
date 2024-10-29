package markup;

import java.util.List;

public class OrderedList extends ListType {
    private static final String docStartTeg = "<orderedlist>";
    private static final String docEndTeg = "</orderedlist>";

    public OrderedList(List<ListItem> content) {
        super(content);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, docStartTeg, docEndTeg);
    }

}
