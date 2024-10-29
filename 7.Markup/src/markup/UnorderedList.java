package markup;

import java.util.List;

public class UnorderedList extends ListType {
    private static final String docStartTeg = "<itemizedlist>";
    private static final String docEndTeg = "</itemizedlist>";

    public UnorderedList(List<ListItem> content) {
        super(content);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, docStartTeg, docEndTeg);
    }
}
