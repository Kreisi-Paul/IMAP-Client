package net.kreisi.imap_client;

import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImapConnection {
    
    private SSLSocket socket;
    private ImapSocketManager socketManager;
    private String hostname;
    private int port;
    private boolean connected = false;

    public ImapConnection(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        try {
            socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(hostname, port);
            socketManager = new ImapSocketManager(this, socket);
            socketManager.start();
            connected = true;
        }
        catch (IOException e) {
            log.error("Socket could not be created.", e);
        }
    }
}
