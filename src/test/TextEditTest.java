package test;

import TextEdit.TextEdit;
import TextEdit.EnumCommandCaption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.xml.transform.Source;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class TextEditTest {

    TextEdit textEdit = new TextEdit();

    class TestActionEventObject extends ActionEvent {
        public TestActionEventObject(Object source, int id, String command) {
            super(source, id, command);
        }
    }

    static Source getTestSource() {
        return new Source() {
            @Override
            public void setSystemId(String systemId) {

            }

            @Override
            public String getSystemId() {
                return null;
            }
        };
    }

    @Test
    void test_TextEdit_TextEdit() {
        assertTrue(new TextEdit() != null);
    }

    @Test
    void test_TextEdit_run() {
        assertAll(() -> textEdit.run());
    }


    @ParameterizedTest
    @EnumSource(EnumCommandCaption.class)
    void test_TextEdit_actionPerformed(EnumCommandCaption command) {
        // NOTE: skip unexpected commands
        List<EnumCommandCaption> unexpectedCommands = new ArrayList<EnumCommandCaption>();
        unexpectedCommands.add(EnumCommandCaption.FILE); // unused in this method
        unexpectedCommands.add(EnumCommandCaption.OPEN); // NOTE: used, but needs a dedicated test approach (e.g. mocking)
        unexpectedCommands.add(EnumCommandCaption.SAVE); // NOTE: used, but needs a dedicated test approach (e.g. mocking)
        unexpectedCommands.add(EnumCommandCaption.QUIT); // NOTE: used, but needs a dedicated test approach (e.g. mocking)
        unexpectedCommands.add(EnumCommandCaption.EDIT); // unused in this method
        unexpectedCommands.add(EnumCommandCaption.HELP); // unused in this method
        if (unexpectedCommands.contains(command))
            // skip
            return;

        // NOTE: for demonstration purposes, test all remaining commands
        TestActionEventObject testActionEvent = new TestActionEventObject(
                getTestSource(), -1, command.getCaption()
        );
        assertAll(() -> textEdit.actionPerformed(testActionEvent));
    }

}
