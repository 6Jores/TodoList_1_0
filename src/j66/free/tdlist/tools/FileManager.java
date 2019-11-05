package j66.free.tdlist.tools;

import java.io.*;

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-11-08
 *
 * Class : FileManager
 * Object : Manage reading and writing of TodoList
 *
 */
abstract public class FileManager {
    /**
     *
     * @param object : The object which are persisted
     * @param path : The path where it's going to write
     * @param nameFile : The name of the wrote file
     * @return a boolean to notice error
     */
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

    /**
     *
     * @param file the file to read
     * @return the read object
     */
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

    /**
     * To get all file of a repertory
     * @param path : the repertory path
     * @return a table of String
     */
    public static String[] listFile (String path){
        return new File(path).list();
    }

    /**
     *
     * @param path : The path of the file
     * @param nameFile : The name of the file
     * @return : a boolean to confirm the action
     */
    public static boolean removeFile(String path,String nameFile){
        return new File(path+nameFile).delete();
    }

    /**
     * To be sure that a name file it is acceptable
     * @param nameFile : the name to check
     * @return : a boolean to confirm or not
     */
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
