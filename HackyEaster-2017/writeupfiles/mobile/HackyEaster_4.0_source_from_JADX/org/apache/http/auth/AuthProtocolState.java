package org.apache.http.auth;

public enum AuthProtocolState {
    UNCHALLENGED,
    CHALLENGED,
    HANDSHAKE,
    FAILURE,
    SUCCESS
}
