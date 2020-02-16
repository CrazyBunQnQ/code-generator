import java.io.*;

public class mt {
	public static void main(String[] arg) throws IOException {
        mergeTxt(arg[0]);
    }

    public static void mergeTxt(String path) throws IOException {
        File newFile = new File(path);
        if (newFile.exists()) {
            newFile.delete();
        }
        newFile.createNewFile();
        FileWriter fw = new FileWriter(newFile, true);
        PrintWriter pw = new PrintWriter(fw);
        String dic = path.substring(0, path.lastIndexOf(File.separator) + 1);
        String name = path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf("."));
        System.out.println("dic: " + dic + "  name:" + name);
        int n = 1;
        String newPath = dic + name + n + ".txt";
        File file = new File(newPath);
        while (file.exists()) {
            BufferedReader textReader = new BufferedReader(new FileReader(file));
            String str = textReader.readLine();
            while (str != null && str.length() > 0) {
                pw.println(str);
                str = textReader.readLine();
            }
            textReader.close();
            n++;
            newPath = dic + name + n + ".txt";
            file = new File(newPath);
        }
        pw.flush();
        fw.flush();
        pw.close();
        fw.close();
    }
}