package net.kreisi.imap_client;

import javax.net.ssl.SSLSocket;
import lombok.extern.slf4j.Slf4j;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ImapSocketManager extends Thread{

    private OutputStream outputStream;
    private InputStream inputStream;
    private SSLSocket socket;
    private ImapConnection imapConnection;
    private List<Byte> bytesRead = new ArrayList<>();

    public ImapSocketManager(ImapConnection imapConnection, SSLSocket socket) {
        this.imapConnection = imapConnection;
        this.socket = socket;

        try {
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        }
        catch (IOException e) {
            log.error("Streams could not be collected from Socket", e);
        }
    }

    @Override
    public void run() {
        while(!socket.isClosed()) {
            try {
                bytesRead.add((byte)inputStream.read());

                if(bytesRead.get(bytesRead.size()-1) == '\n') {
                    byte[] debug = new byte[bytesRead.size()];
                    for (int i = 0; i < bytesRead.size(); i++) {
                        debug[i] = bytesRead.get(i);
                        log.info(new String(debug));
                    }
                }
            }
            catch(IOException e) {
                log.error("Could not read byte from InputStream", e);
            }
        }
    }
}
