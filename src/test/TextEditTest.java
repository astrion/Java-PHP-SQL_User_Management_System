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

import static org.junit.jupiter.api.Assertions.assertTrue;



public class TextEditTest {

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
        TextEdit textEdit = new TextEdit();
        assertTrue(textEdit != null);
    }

    @Test
    void test_TextEdit_run() {
        TextEdit textEdit = new TextEdit();

        boolean isMethodExecutedWithoutInterruptions;
        try {

            // test run() here
            textEdit.run();

            isMethodExecutedWithoutInterruptions = true;
        } catch (Exception e) {
            isMethodExecutedWithoutInterruptions = false;
        }
        assertTrue(isMethodExecutedWithoutInterruptions);
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
        TextEdit textEdit = new TextEdit();
        boolean isMethodExecutedWithoutInterruptions;
        try {
            TestActionEventObject actionEvent = new TestActionEventObject(
                    getTestSource(), -1, command.getCaption()
            );

            // test actionPerformed() here
            textEdit.actionPerformed(actionEvent);

            isMethodExecutedWithoutInterruptions = true;
        } catch (Exception e) {
            isMethodExecutedWithoutInterruptions = false;
        }
        assertTrue(isMethodExecutedWithoutInterruptions);
    }

}
