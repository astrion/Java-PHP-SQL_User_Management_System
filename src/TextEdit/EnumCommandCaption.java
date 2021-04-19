package TextEdit;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public enum EnumCommandCaption {

    FILE ("File"),
    NEW ("New"),
    OPEN ("Open"),
    SAVE ("Save"),
    QUIT ("Quit"),

    EDIT ("Edit"),
    VIEW_HIDE_UNDO_FORGET_EDIT_HISTORY("View/Hide Edit History..."),

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