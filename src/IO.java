import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IO {
    private static final String directoriesPath = "Resources/Directories.txt";
    private static final String foldersPath = "Resources/Folders.txt";

    private static final Map<String, String> configuration = new HashMap<>();

    public static DirectoryThread[] getDirectoryThreads() {
        try {
            List<Path> lines = Files.readAllLines(Paths.get(directoriesPath))
                    .stream().map(Paths::get).toList();
            return lines.stream().map(DirectoryThread::new).toArray(DirectoryThread[]::new);
        } catch (IOException e) {
            Logger.LogException(e);
        }
        return new DirectoryThread[0];
    }

    public static String getFolderName(Optional<String> extension) {
        if (configuration.isEmpty()) initializeConfiguration();
        if (extension.isEmpty() || !configuration.containsKey(extension.get())) {
            return "Misc";
        }
        return configuration.get(extension.get());


    }

    private static void initializeConfiguration() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(foldersPath));
            for (String line : lines) {
                String extension = line.split("->")[0];
                String folder = line.split("->")[1];
                configuration.put(extension, folder);
            }
        } catch (IOException e) {
            Logger.LogException(e);
        }
    }

    public static void moveFile(Path path, Path file) throws IOException {
        if(Files.isDirectory(file)) return;
        file = file.getFileName();
        String folder = IO.getFolderName(getExtension(file.getFileName().toString()));
        Path folderPath = Paths.get(path.toString(), folder);
        if (!Files.exists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        folderPath = Paths.get(folderPath.toString(), file.toString());
        Path filePath = Paths.get(path.toString(), file.toString());
        Files.move(filePath, folderPath, StandardCopyOption.REPLACE_EXISTING);
        Logger.Log("Moved " + filePath + " to " + folderPath);
    }

    private static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
