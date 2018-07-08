package com.alfainmo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import com.alfainmo.extra.AlfaException;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUtils {

    protected static final int TIMEOUT = 30000;
    protected final String hostFtp;
    protected final String userFtp;
    protected final String passFtp;
    protected String homeFtp = null;
    protected FTPClient ftp = null;

    /**
     * @param hostFtp
     * @param userFtp
     * @param passFtp
     */
    public FtpUtils(String hostFtp, String userFtp, String passFtp) {
        this.hostFtp = hostFtp;
        this.userFtp = userFtp;
        this.passFtp = passFtp;
    }

    /**
     * @throws AlfaException
     */
    public void login() throws AlfaException {

        ftp = new FTPClient();
        try {
            ftp.connect(hostFtp);
            ftp.login(userFtp, passFtp);
            ftp.setDataTimeout(TIMEOUT);
            ftp.setDefaultTimeout(TIMEOUT);
            ftp.setSoTimeout(TIMEOUT);
            ftp.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();

            if (homeFtp != null) {
                ftp.changeWorkingDirectory(homeFtp);
            }

        } catch (SocketException ex) {
            throw new AlfaException(ex);
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    /**
     *
     */
    public void logout() {
        if (!ftp.isConnected()) {
            return;
        }
        try {
            ftp.logout();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void sendFile(String fileFnt, String fileDst) throws AlfaException {
        try {
            ftp.storeFile(fileDst, new FileInputStream(fileFnt));
        } catch (IOException e) {
            throw new AlfaException(e);
        }

    }

    public void setHomeFtp(String homeFtp) {
        this.homeFtp = homeFtp;
    }
}
