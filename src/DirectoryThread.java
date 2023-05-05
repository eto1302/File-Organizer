import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryThread extends Thread {
    private final Path path;

    public DirectoryThread(Path path) {
        this.path = path;
    }

    public void run() {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path,
                    "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + path
                        + " is not a folder");
            }
        } catch (Exception e) {
            Logger.LogException(e);
            return;
        }

        FileSystem fs = path.getFileSystem();

        try (WatchService service = fs.newWatchService()) {
            path.register(service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            WatchKey key = null;
            while (true) {
                key = service.take();

                WatchEvent.Kind<?> kind = null;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    kind = watchEvent.kind();
                    switch (kind.name()) {
                        case "OVERFLOW":
                            Logger.LogException(new Exception("Overflow occurred!"));
                            break;
                        case "ENTRY_CREATE":
                            handleFileCreated(((WatchEvent<Path>) watchEvent).context());
                            break;
                        case "ENTRY_MODIFY":
                            handleFileModified();
                            break;
                        case "ENTRY_DELETE":
                            handleFileDeleted();
                            break;
                    }
                }

                if (!key.reset()) {
                    break;
                }
            }

        } catch (Exception e) {
            Logger.LogException(e);
        }
    }

    private void handleFileCreated(Path file) throws IOException {
        Logger.Log("File Created: " + file);
        IO.moveFile(path, file);
    }

    private void handleFileDeleted() {
    }

    private void handleFileModified() {
    }

    public Path getPath() {
        return this.path;
    }
}
