package main;

public class Messages {
    public static final String MSG_220 = "220 service ready";
    public static final String MSG_425 = "425 Can't open data connection.";
    public static final String MSG_501 = "501 Syntax error in parameters or arguments.";
    public static final String MSG_501_NO_FILENAME = "501 No filename given";
    public static final String MSG_250 = "250 Requested file action okay, completed.";

    public static final String MSG_550_UNAVAILABLE = "550 Requested action not taken. File unavailable";
    public static final String MSG_550_NOT  = "550 File does not exist";
    public static final String MSG_550_ALREADY  = "550 File already exists";

    public static final String MSG_150 = "150 Opening ASCII mode data connection for requested file";
    public static final String MSG_226_CLOSE = "226 Closing data connection. Requested file action successful.";
    public static final String MSG_226_COMPLETE = "226 Transfer complete";
    public static final String MSG_125 = "125 Opening ASCII mode data connection for file list";

    public static final String MSG_229 = "229 Entering Extended Passive Mode (port).\r\n";

    public static final String MSG_530_NOT = "530 Not logged in";
    public static final String MSG_530_ALREADY = "530 User already logged in";
    public static final String MSG_331 = "331 User name ok, need password";
    public static final String MSG_230 = "230 User logged in";
    public static final String MSG_221 = "221 Closing connection";


}
