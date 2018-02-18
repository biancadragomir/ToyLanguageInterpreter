package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializationUtil {

    public static List<? extends Object> deserialize(String filename) throws IOException, ClassNotFoundException
    {
        int done=0;
        Object o;
        List<Object> finalList = new ArrayList<>();
        FileInputStream fileStream= new FileInputStream(filename);
        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        while(done==0)
        {
            try
            {
                o = objectStream.readObject();
                finalList.add(o);
            }
            catch (Exception ne){done = 1;}
        }
        objectStream.close();
        fileStream.close();
        return finalList;
    }

    public static void serialize(List<? extends Object> l, String filename) throws IOException
    {
        FileOutputStream fileStream = new FileOutputStream(filename);
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
        for(Object o : l)
            objectStream.writeObject(o);

        objectStream.close();
    }

}