package cat.yoink.dream.api.util;

import java.io.*;
import java.util.ArrayList;

public class FileUtil
{
    public static void saveFile(final File file, final ArrayList<String> content) throws IOException
    {
        final BufferedWriter out = new BufferedWriter(new FileWriter(file));

        for (final String s : content)
        {
            out.write(s);
            out.write("\r\n");
        }
        out.close();
    }

    public static ArrayList<String> loadFile(final File file) throws IOException
    {
        final ArrayList<String> content = new ArrayList<>();

        final FileInputStream stream = new FileInputStream(file.getAbsolutePath());
        final DataInputStream in = new DataInputStream(stream);
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;

        while ((line = br.readLine()) != null) content.add(line);

        br.close();

        return content;
    }
}
