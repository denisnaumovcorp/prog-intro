package markup;

import java.util.List;

public class Strikeout extends Formatting {
    String markSymbols = "~";

    public Strikeout(List<Mark> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, markSymbols);
    }
}
