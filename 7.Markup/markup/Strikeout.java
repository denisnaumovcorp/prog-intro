package markup;

import java.util.List;

public class Strikeout extends ElementType {
    private static final String MARK_SYMBOLS = "~";
    private static final String DOC_ROLE = "strikeout";

    public Strikeout(List<MarkupElements> elements) {
        super(elements);
    }

    @Override
    public String getDocBookRole() {
        return DOC_ROLE;
    }

    @Override
    public String getMarkDownSymbols() {
        return MARK_SYMBOLS;
    }
}

