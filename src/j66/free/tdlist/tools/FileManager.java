package j66.free.tdlist.tools;

import java.io.*;

abstract public class FileManager {
    public static boolean writeFile(Object object, String path, String nameFile){
        boolean rtn = false;

        File file = new File(path+nameFile);

        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));
            oos.writeObject(object);
            rtn = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rtn;
    }
}
