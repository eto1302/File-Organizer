import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Initialize();
    }

    private static void Initialize() {
        DirectoryThread[] directoryThreads = IO.getDirectoryThreads();
        for(DirectoryThread thread : directoryThreads){
            OrganizeFiles(thread.getPath());
            thread.start();
        }
    }

    private static void OrganizeFiles(Path path) {
        try{
            Files.walk(path, 1).forEach(file -> {
                try {
                    IO.moveFile(path, file);
                } catch (IOException e) {
                    Logger.LogException(e);
                }
            });
        }
        catch(IOException e){
            Logger.LogException(e);
        }
    }
}
