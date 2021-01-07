package com.nativeboyz.vmall.services.storage;

import com.sun.istack.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    String save(MultipartFile file);

    Stream<String> save(List<MultipartFile> files);

    Stream<String> findAll();

    // TODO: Optional
    Resource findAsResource(String filename);

    String replaceIfExists(@Nullable String filename, @Nullable MultipartFile file);

    void delete(String filename);

    void deleteIfExists(String filename);

    void delete(List<String> filenames);

    void deleteIfExists(List<String> filenames);

    void deleteAll();

}
