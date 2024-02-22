package controlador.DAO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import java.io.File;
import java.security.Permission;

/**
 *
 * @author Asus
 */
public class Connection {
    private static String URL = "data/"+File.separatorChar;
    private static XStream xstream;
    private Connection(){
        
    }

    public static String getURL() {
        return URL;
    }

    public static XStream getXstream() {
        if(xstream == null){
            xstream = new XStream(new JettisonMappedXmlDriver());
            xstream.addPermission(AnyTypePermission.ANY);
        }
        return xstream;
    }
    
}
