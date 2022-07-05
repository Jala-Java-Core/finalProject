package entertainment.helpers;

import java.io.*;
import java.util.Date;

public class FileHelper {
    String path;

    public FileHelper(String path) {
        this.path = path;
    }

    public boolean write(String text) {
        return write(text, false);
    }

    public boolean write(String text, boolean append) {
        File file = new File(this.path);
        Boolean fileExists = file.exists();
        if (!fileExists) {
            try {
//                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred when creating the file " + this.path);
                e.printStackTrace();
            }
        } else {
            text = '\n' + text;
        }
        try {
            FileWriter fw = new FileWriter(this.path, append);
            fw.write(text);
            fw.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred when trying to write to " + this.path);
            e.printStackTrace();
            return false;
        }
    }

    public String read() {
        File file = new File(this.path);
        Boolean fileExists = file.exists();
        if (!fileExists) {
            return "";
        }
        try {
            FileReader fr = new FileReader(this.path);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            fr.close();
            return sb.toString();
        } catch (IOException e) {
            System.out.println("An error occurred when trying to write to " + this.path);
            e.printStackTrace();
            return null;
        }
    }
}
