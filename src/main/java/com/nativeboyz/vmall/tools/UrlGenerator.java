package com.nativeboyz.vmall.tools;

import com.nativeboyz.vmall.controllers.FilesController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public class UrlGenerator {

    public static String fileNameToUrl(String filename) {
        return MvcUriComponentsBuilder.fromMethodName(
                FilesController.class,
                "getFile",
                filename
        ).build().toUri().toString();
    }

}
