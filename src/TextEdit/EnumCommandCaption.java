package TextEdit;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


enum EnumCommandCaption {

    FILE ("File"),
    NEW ("New"),
    OPEN ("Open"),
    SAVE ("Save"),
    QUIT ("Quit"),

    EDIT ("Edit"),
    UNDO ("Undo"),
    REDO ("Redo"),
    SMART_UNDO ("Smart undo..."),

    POC ("POC"),
    VIEW_EDITS ("View last edits..."),
    APPLY_MULTIPLE_SEQUENTIAL_UNDO_BY_N_STEPS ("Apply smart undo of last edits by steps..."),
    APPLY_MULTIPLE_SEQUENTIAL_UNDO_BY_ELAPSED_TIME ("Apply smart undo of last edits by elapsed time..."),
    APPLY_MULTIPLE_ANY_ORDER_UNDO ("Apply smart undo of last edits by any order..."),

    HELP ("Help"),
    ABOUT ("About");

    private String caption;
    private static final Map<String,EnumCommandCaption> ENUM_MAP;

    EnumCommandCaption (String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return this.caption;
    }

    static {
        // immutable mapping of String name to enum
        Map<String, EnumCommandCaption> map = new ConcurrentHashMap<String, EnumCommandCaption>();
        for (EnumCommandCaption instance : EnumCommandCaption.values()) {
            map.put(instance.getCaption().toUpperCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static EnumCommandCaption get (String caption) {
        return ENUM_MAP.get(caption.toUpperCase());
    }
}