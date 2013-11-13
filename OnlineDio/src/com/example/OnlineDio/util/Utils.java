package com.example.OnlineDio.util;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils
{
    public static void copyStream(InputStream in, OutputStream out)
    {
        final int buffer_size = 512;
        try
        {
            byte[] bytes = new byte[buffer_size];

            for (; ; )
            {
                int count = in.read(bytes, 0, buffer_size);
                if (count == -1)
                {
                    break;
                }
                out.write(bytes, 0, count);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
