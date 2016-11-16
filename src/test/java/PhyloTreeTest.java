import javafx.scene.control.TreeItem;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by peltzer on 16/11/2016.
 */
public class PhyloTreeTest {

    private PhyloTreeParser php = new PhyloTreeParser();
    private TreeItem<String> item = php.getFinalTree();

    public PhyloTreeTest() throws ParserConfigurationException, SAXException, IOException {
    }


    @Test
    public void test_subtree_h2() throws ParserConfigurationException, SAXException, IOException {
        assertEquals(php.contains(item, "H2a2a1"), true);
    }

    @Test
    public void test_subtree_h36() throws ParserConfigurationException, SAXException, IOException {
        php.getFinalTree();
        assertEquals(php.contains(item, "H36"), true);
    }

    @Test
    public void test_subtree_u6() throws ParserConfigurationException, SAXException, IOException {
        php.getFinalTree();
        assertEquals(php.contains(item, "U6"), true);
    }
}
