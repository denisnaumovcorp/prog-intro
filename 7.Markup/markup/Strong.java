package markup;

import java.util.List;

public class Strong extends ElementType {
    private static final String MARK_SYMBOLS = "__";
    private static final String DOC_ROLE = "bold";

    public Strong(List<MarkupElements> elements) {
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
