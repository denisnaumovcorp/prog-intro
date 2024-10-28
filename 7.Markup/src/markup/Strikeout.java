package markup;

import java.util.List;

public class Strikeout extends Paragraph {
    String markSymbols = "~";
    String docStartTeg = "<emphasis role='strikeout'>";
    String docEndTeg = "</emphasis>";

    public Strikeout(List<Mark> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, markSymbols);
    }

    @Override
    public void toDocBook(StringBuilder strBuilder) {
        for (Mark element : elements) {
            super.insertFormatting(strBuilder, docStartTeg, docEndTeg);
        }
    }
}
