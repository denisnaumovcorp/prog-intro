package markup;

import java.util.List;

public class UnorderedList extends ListType {
    private static final String DOC_START_TEG = "<itemizedlist>";
    private static final String DOC_END_TEG = "</itemizedlist>";

    public UnorderedList(List<ListItem> content) {
        super(content);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, DOC_START_TEG, DOC_END_TEG);
    }
}
