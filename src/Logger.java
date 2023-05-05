import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static String logPath = "Resources/Log.txt";

    public static void Log(String message){
        try{
            FileWriter fw = new FileWriter(logPath, true);
            fw.append(message);
            fw.append("\n");
            fw.flush();
            fw.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void LogException(Exception e) {
        Log("Exception is caught " + e.getClass().getName() + ": "+e.getMessage());
    }
}
