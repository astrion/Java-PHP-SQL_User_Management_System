package TextEdit;


public enum EnumWindowCaption {
    PRIMARY ("SMARTY"),
    VIEW_OF_LAST_EDITS ("View of last edits"),
    SMART_UNDO ("Smart undo");

    public final String caption;

    EnumWindowCaption(String caption) {
        this.caption = caption;
    }
}