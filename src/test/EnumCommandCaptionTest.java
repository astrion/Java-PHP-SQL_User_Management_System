package test;

import TextEdit.EnumCommandCaption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumCommandCaptionTest {

    /**
     * assert get() returns an enumeration, given its caption
     * */
    @Test
    void test_EnumCommandCaption_get() {

        assertEquals(EnumCommandCaption.FILE, EnumCommandCaption.get("File"));
        assertEquals(EnumCommandCaption.NEW, EnumCommandCaption.get("New"));
        assertEquals(EnumCommandCaption.OPEN, EnumCommandCaption.get("Open"));
        assertEquals(EnumCommandCaption.SAVE, EnumCommandCaption.get("Save"));
        assertEquals(EnumCommandCaption.QUIT, EnumCommandCaption.get("Quit"));

        assertEquals(EnumCommandCaption.EDIT, EnumCommandCaption.get("Edit"));
        assertEquals(EnumCommandCaption.VIEW_HIDE_UNDO_FORGET_EDIT_HISTORY, EnumCommandCaption.get("View/Hide Edit History..."));

        assertEquals(EnumCommandCaption.HELP, EnumCommandCaption.get("Help"));
        assertEquals(EnumCommandCaption.ABOUT, EnumCommandCaption.get("About"));

    }
}
