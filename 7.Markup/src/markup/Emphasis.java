package markup;

import java.util.List;

public class Emphasis extends Formatting {
    String markSymbols = "*";

    public Emphasis(List<Mark> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, markSymbols);
    }
}
