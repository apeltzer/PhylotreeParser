import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by peltzer on 15/11/2016.
 */
public class PhylotreeTest {


    @Test
    public void phylotree_get_level() throws IOException {
        File f = new File("src/test/resources/mtdnacsv.csv");
        PhylotreeParser phyp = new PhylotreeParser(f);
        assertEquals(0, phyp.getLevel("test1234556"));
        assertEquals(2, phyp.getLevel(";;34234243"));
        assertEquals(5, phyp.getLevel(";;;;;L0d'"));


    }
}
