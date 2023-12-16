package Utils.IO;

import java.io.*;

public class FileUtils {
    /**
     * Test, wether a file exists
     *
     * @param filename Name of the file to test as relative path from root-directory
     * @return true, if file exists; false else
     */
    public static boolean fileExists(String filename) {
        try {
            new FileReader(filename);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * Creates empty file, if it does not exist
     *
     * @param filename Name of the file to create as relative path from root-directory
     */
    public static void createFile(String filename) {
        if (!fileExists(filename)) {
            try {
                new File(filename).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes some text to a file (does not need to be existent
     *
     * @param input    Text that is being written to the file as relative path from root-directory
     * @param filename Name of the file
     */
    public static void writeFile(String input, String filename) {
        createFile(filename);
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(input);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the content of a file and create an empty file, if no file exists
     *
     * @param filename Name of the file to read from as relative path from root-directory
     * @return Content of the file
     */
    public static String readFile(String filename) {
        BufferedReader br;
        String everything = "";
        createFile(filename);
        try {
            br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return everything;
    }
}
