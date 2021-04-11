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
    CLEAR_LAST_EDIT_HISTORY("Clear last-edit history..."),
    CLEAR_LAST_EDIT_HISTORY_BY_N_STEPS("Clear last-edit history by N steps..."),
    CLEAR_LAST_EDIT_HISTORY_BY_ELAPSED_TIME("Clear last-edit history by elapsed time..."),
    REDO ("Redo"),
    UNDO ("Undo"),
    UNDO_SEQUENTIALLY_BY_N_STEPS("Undo last edits sequentially by N steps..."),
    UNDO_SEQUENTIALLY_BY_ELAPSED_TIME("Undo last edits sequentially by elapsed time..."),
    UNDO_BY_ANY_ORDER("Undo last edits by any order..."),
    VIEW_HIDE_LAST_EDIT_HISTORY("View/Hide last-edit history..."),

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