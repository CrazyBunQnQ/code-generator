import java.io.*;

public class t2f {
    public static void main(String[] arg) throws IOException {
        text2File(arg[0], arg[1]);
    }

    public static void text2File(String path, String txtPath) throws IOException {
        File outFile = new File(path);
        File writeTxt = new File(txtPath);
        if (outFile.exists()) {
            outFile.delete();
        }
        if (!writeTxt.exists() || !writeTxt.isFile()) {
            System.out.println("文件不存在");
            return;
        }
        BufferedReader textReader = new BufferedReader(new FileReader(writeTxt));

        FileOutputStream fos = new FileOutputStream(outFile);
        String line = textReader.readLine();
        System.out.println("line length: " + line.length());
        while (line != null) {
            byte[] bys = hexString2Bytes(line);
            fos.write(bys, 0, bys.length);
            fos.flush();
            line = textReader.readLine();
        }
        fos.close();
        textReader.close();
    }

    public static byte[] hexString2Bytes(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return b;
    }
}
