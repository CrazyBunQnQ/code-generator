import java.io.*;

public class f2t {
    public static void main(String[] arg) throws IOException {
        for (int i = 0; i < arg.length; i++) {
            System.out.println("第 " + (i + 1) + " 个参数为: " + arg[i]);
        }
        if (arg.length < 3) {
            file2Text(arg[0], arg[1]);
        } else {
            int size = Integer.parseInt(arg[2]);
            file2Text(arg[0], arg[1], size);
        }
    }

    public static void file2Text(String path, String txtPath) throws IOException {
        File inFile = new File(path);
        File writeTxt = new File(txtPath);
        if (!inFile.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if (!inFile.isFile()) {
            System.out.println("不是一个文件");
            return;
        }
        if (writeTxt.exists()) {
            writeTxt.delete();
        }
        writeTxt.createNewFile();
        BufferedWriter textWriter = new BufferedWriter(new FileWriter(writeTxt));

        FileInputStream fis = new FileInputStream(inFile);
        int len;
        byte[] bys = new byte[2048];
        while ((len = fis.read(bys, 0, bys.length)) != -1) {
            StringBuffer sb = new StringBuffer();
            String str = bytes2HexString(len, bys);
            textWriter.write(str);
            textWriter.write(sb.toString() + "\r\n");
        }
        fis.close();
        textWriter.close();
    }

    public static void file2Text(String path, String txtPath, int size) throws IOException {
        int m = 0;//记录当前 size
        int n = 1;//记录文件名
        boolean end = false;
        String nameTmp;
        String curTxtPath = "";
        File inFile = new File(path);
        if (!inFile.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if (!inFile.isFile()) {
            System.out.println("不是一个文件");
            return;
        }
        FileInputStream fis = new FileInputStream(inFile);
        String directory = txtPath.substring(0, txtPath.lastIndexOf(File.separator));
        String name = txtPath.substring(txtPath.lastIndexOf(File.separator) + 1, txtPath.lastIndexOf("."));
        System.out.println("目录：" + directory + ", 名称：" + name);
        int len;//字节数组长度
        File writeTxt = null;
        BufferedWriter textWriter = null;

        while (!end) {
            nameTmp = curTxtPath;
            curTxtPath = directory + File.separator + name + n + ".txt";
            if (nameTmp != curTxtPath) {
                writeTxt = new File(curTxtPath);
                if (writeTxt.exists()) {
                    writeTxt.delete();
                }
                writeTxt.createNewFile();
                System.out.println("创建新文件：" + curTxtPath);
                textWriter = new BufferedWriter(new FileWriter(writeTxt));
            }

            byte[] bys = new byte[2048];
            while ((len = fis.read(bys, 0, bys.length)) != -1) {
                String str = bytes2HexString(len, bys);
                textWriter.write(str);
                m++;
                if (m > size) {
                    m = 0;
                    break;
                }
            }
            end = len == -1;
            textWriter.close();
            n++;
        }
        fis.close();
    }

    public static String bytes2HexString(int len, byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < len; i++) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((bytes[i] >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(bytes[i] & 0x0f));
        }

        return sb.toString();
    }
}
