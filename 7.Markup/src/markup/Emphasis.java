package markup;

import java.util.List;

public class Emphasis extends Paragraph {
    String markSymbols = "*";
    String docStartTeg = "<emphasis>";
    String docEndTeg = "</emphasis>";

    public Emphasis(List<Mark> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder strBuilder) {
        super.insertFormatting(strBuilder, markSymbols);
    }
}
