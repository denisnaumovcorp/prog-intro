package markup;

import java.util.List;

public class Strong extends Formatting {
    String markSymbols = "__";

    public Strong(List<Mark> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, markSymbols);
    }
}
