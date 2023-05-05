import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Initialize();
        PutToSystemTray();
    }

    private static void PutToSystemTray() {
        if (!SystemTray.isSupported()) {
            Logger.Log("System tray is not supported!");
            return;
        }

        SystemTray systemTray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().getImage("Resources/icon.png");

        PopupMenu trayPopupMenu = new PopupMenu();

        MenuItem close = new MenuItem("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayPopupMenu.add(close);
        TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo", trayPopupMenu);
        trayIcon.setImageAutoSize(true);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException awtException) {
            Logger.LogException(awtException);
        }
    }

    private static void Initialize() {
        DirectoryThread[] directoryThreads = IO.getDirectoryThreads();
        for (DirectoryThread thread : directoryThreads) {
            OrganizeFiles(thread.getPath());
            thread.start();
        }
    }

    private static void OrganizeFiles(Path path) {
        try {
            Files.walk(path, 1).forEach(file -> {
                try {
                    IO.moveFile(path, file);
                } catch (IOException e) {
                    Logger.LogException(e);
                }
            });
        } catch (IOException e) {
            Logger.LogException(e);
        }
    }
}
