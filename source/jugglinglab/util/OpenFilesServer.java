// OpenFilesServer.java
//
// Copyright 2002-2021 Jack Boyce and the Juggling Lab contributors

package jugglinglab.util;

import java.io.File;

import jugglinglab.core.Constants;


// This class is used to handle open file messages, i.e., when the user double-
// clicks on a .jml file. This is primarily used on Windows; on macOS the open
// file messages are handled through the Desktop class.
//
// The OS opens a new instance of Juggling Lab, and this class needs to use
// some kind of inter-process communication to hand off the open file request
// to another Juggling Lab instance that may be already running.
//
// There are two distinct implementations here: One that uses a memory-mapped
// file for IPC, and another that uses sockets. Both work well but the sockets
// version can trigger a Windows firewall warning that requires user approval,
// so we default to the other. Neither uses OS-specific APIs so they should be
// fairly portable.

public class OpenFilesServer {
    // implemented types
    public static final int memorymappedfile = 1;
    public static final int sockets = 2;

    private static OpenFilesServerMMF ofs_mmf;
    private static OpenFilesServerSockets ofs_sockets;


    public OpenFilesServer() {
        switch (Constants.OPEN_FILES_METHOD) {
            case memorymappedfile:
                if (ofs_mmf == null)
                    ofs_mmf = new OpenFilesServerMMF();
                break;
            case sockets:
                if (ofs_sockets == null)
                    ofs_sockets = new OpenFilesServerSockets();
                break;
        }
    }

    // Try to signal another instance of Juggling Lab on this machine to open
    // the file. If the open command is successfully handed off, return true.
    // Otherwise return false.
    public static boolean tryOpenFile(File f) {
        switch (Constants.OPEN_FILES_METHOD) {
            case memorymappedfile:
                return OpenFilesServerMMF.tryOpenFile(f);
            case sockets:
                return OpenFilesServerSockets.tryOpenFile(f);
        }
        return false;
    }

    // Do any needed cleanup when things are closing down
    public static void cleanup() {
        if (ofs_mmf != null)
            OpenFilesServerMMF.cleanup();
        if (ofs_sockets != null)
            OpenFilesServerSockets.cleanup();
    }
}
