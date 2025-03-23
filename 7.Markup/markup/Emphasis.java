package markup;

import java.util.List;

public class Emphasis extends ElementType {
    private static final String MARK_SYMBOLS = "*";
    private static final String DOC_ROLE = "";

    public Emphasis(List<MarkupElements> elements) {
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
