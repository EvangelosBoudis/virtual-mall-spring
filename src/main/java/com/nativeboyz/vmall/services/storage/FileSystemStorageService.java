package com.nativeboyz.vmall.services.storage;

import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${files.storage.directory}") String storageDirectory) {
        this.rootLocation = Paths.get(storageDirectory);
    }

    @Override
    public String save(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        createDirectoryIfDoesNotExists();
        Path absolutePath = applyAbsolutePath(UUID.randomUUID().toString()); // TODO: Apply file type (.jpg etc)
        if (!absolutePath.getParent().equals(rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new StorageException("Cannot store file outside current directory.");
        }
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(
                        inputStream,
                        absolutePath,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
            return extractFileName(absolutePath);
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<String> save(List<MultipartFile> files) {
        return files.stream().map(this::save);
    }

    @Override
    public Stream<String> findAll() {
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize)
                    .map(this::extractFileName);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files.", e);
        }
    }

    @Override
    public Resource findAsResource(String filename) {
        createDirectoryIfDoesNotExists();
        try {
            Resource resource = new UrlResource(findAbsolutePath(filename).toUri());
            if (!resource.exists() && !resource.isReadable()) {
                throw new StorageFileNotFoundException("File not found: " + filename);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException(e.getMessage());
        }
    }

    @Override
    public String replaceIfExists(@Nullable String filename, @Nullable MultipartFile file) {
        if (filename != null) deleteIfExists(filename);
        return (file != null) ? save(file)  : null;
    }

    @Override
    public void delete(String filename) {
        delete(filename, true);
    }

    @Override
    public void deleteIfExists(String filename) {
        delete(filename, false);
    }

    @Override
    public void delete(List<String> filenames) {
        filenames.forEach(filename -> delete(filename, true));
    }

    @Override
    public void deleteIfExists(List<String> filenames) {
        filenames.forEach(filename -> delete(filename, false));
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Tools
    //////////////////////////////////////////////////////////////////////////////////////////

    private void createDirectoryIfDoesNotExists() {
        if (Files.exists(rootLocation)) return;
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    private Path findAbsolutePath(String filename) {
        return rootLocation.resolve(filename);
    }

    private Path applyAbsolutePath(String filename) {
        return rootLocation.resolve(
                Paths.get(filename)
        ).normalize().toAbsolutePath();
    }

    private String extractFileName(Path path) {
        return path.getFileName().toString();
    }

    private void delete(String filename, boolean throwIfDoesNotExists) {
        Path path = applyAbsolutePath(filename);
        if (Files.exists(path)) {
            try {
                Files.delete(applyAbsolutePath(filename));
            } catch (IOException e) {
                throw new StorageException(e.getMessage());
            }
        } else if (throwIfDoesNotExists) {
            throw new StorageFileNotFoundException("File not found: " + filename);
        }
    }

}
