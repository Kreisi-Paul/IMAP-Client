package net.kreisi.imap_client;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImapServerResponse {

    public String tag;
    public ImapServerResponseStatus status;

}
