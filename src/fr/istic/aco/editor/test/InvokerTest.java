package fr.istic.aco.editor.test;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.invoker.impl.InvokerImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InvokerTest {

    @Test
    public void testInvoker() throws Exception {

        Invoker invoker = new InvokerImpl();

        Command mockCmd = Mockito.mock(Command.class);

        // Setup a mock input stream
        String mockInput = "Insert\nCopy\nPaste\nCut";
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);

        invoker.addCommand("Insert", mockCmd);

        invoker.runInvokerLoop();
        Mockito.verify(mockCmd).execute();
    }
}
