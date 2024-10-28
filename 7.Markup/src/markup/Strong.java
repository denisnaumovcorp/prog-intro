package markup;

import java.util.List;

public class Strong extends Paragraph {
    String markSymbols = "__";
    String docStartTeg = "<emphasis role='strikeout'>";
    String docEndTeg = "</emphasis>";

    public Strong(List<Mark> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, markSymbols);
    }
}
