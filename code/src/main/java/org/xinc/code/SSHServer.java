package org.xinc.code;


import org.apache.sshd.common.util.security.bouncycastle.BouncyCastleGeneratorHostKeyProvider;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ProcessShellFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.security.Security;

public class SSHServer {
    static public void main(String[] argv) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
            @Override
            public boolean authenticate(String s, String s1, ServerSession serverSession) throws PasswordChangeRequiredException, AsyncAuthException {
                return true;
            }
        });
        sshd.setKeyPairProvider(new BouncyCastleGeneratorHostKeyProvider(Paths.get("ssh.pem")));
        sshd.setPort(22);
        sshd.setHost("0.0.0.0");
        sshd.setShellFactory(new ProcessShellFactory("bash","bash","-i","-l"));
        sshd.start();
    }
}
