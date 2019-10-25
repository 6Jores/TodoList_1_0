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

    public static Object readAnObject (File file){

        Object object = null;

        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            object = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return object;
    }

    public static String[] listFile (String path){
        return new File(path).list();
    }

    public static boolean removeFile(String path,String nameFile){
        return new File(path+nameFile).delete();
    }

    public static boolean itsOkForFile(String nameFile){
        String[] listForbidden = {"<",">",":","\"","\\","/","*","|","?"};
        int j=0;
        for (String forbidden : listForbidden){
            if (nameFile.contains(forbidden))
                j++;
        }
        return j==0;
    }

}
