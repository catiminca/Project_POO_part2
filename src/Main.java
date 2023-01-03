import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import inputs.DataBaseInput;
import platform.AllActions;
import platform.DataBase;

import java.io.File;
import java.io.IOException;


public final class Main {
    private Main() { }

    /**
     * main
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException  {

        ObjectMapper objectMapper = new ObjectMapper();
        DataBaseInput database = objectMapper.readValue(new File(args[0]), DataBaseInput.class);
        DataBase dataBase = new DataBase(database);
        ArrayNode output = objectMapper.createArrayNode();
        AllActions allActions = new AllActions(dataBase, "homepage", output);
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        objectWriter.writeValue(new File(args[1]), output);
//        Iterator as = database.getActions().iterator();

    }
}
