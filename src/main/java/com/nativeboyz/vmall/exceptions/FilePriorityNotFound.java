package com.nativeboyz.vmall.exceptions;

public class FilePriorityNotFound extends RuntimeException {

    public FilePriorityNotFound() {
        super("The priorities of files are not provided");
    }

}
