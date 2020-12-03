package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.dto.TransactionDto;
import com.nativeboyz.vmall.services.storage.StorageService;
import com.nativeboyz.vmall.tools.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final StorageService service;

    @Autowired
    public FilesController(StorageService service) {
        this.service = service;
    }

    @GetMapping()
    public List<String> getFiles() {
        return service.findAll()
                .map(UrlGenerator::fileNameToUrl)
                .collect(Collectors.toList());
    }

    @GetMapping("/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = service.findAsResource(filename);
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\""
        ).body(file);
    }

    @PostMapping()
    public List<String> saveFile(@RequestParam("files") List<MultipartFile> files) {
        return service.save(files)
                .map(UrlGenerator::fileNameToUrl)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{filename}")
    public TransactionDto deleteFile(@PathVariable() String filename) {
        service.delete(filename);
        return new TransactionDto("File: " + filename + " deleted successfully");
    }

    @DeleteMapping()
    public TransactionDto deleteFiles() {
        service.deleteAll();
        return new TransactionDto("All files deleted successfully");
    }

}
