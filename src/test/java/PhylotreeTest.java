import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by peltzer on 15/11/2016.
 */
public class PhylotreeTest {


    @Test
    public void phylotree_get_level_test() throws IOException {
        File f = new File("src/test/resources/mtdnacsv.csv");
        PhylotreeParser phyp = new PhylotreeParser(f);
        assertEquals(0, phyp.getLevel("test1234556"));
        assertEquals(2, phyp.getLevel(";;34234243"));
        assertEquals(5, phyp.getLevel(";;;;;L0d'"));
    }

    @Test
    public void phylotree_get_haplogroup_test() throws IOException{
        File f = new File("src/test/resources/mtdnacsv.csv");
        PhylotreeParser phyp = new PhylotreeParser(f);
        assertEquals("L0", phyp.getHaplogroup("L0; G263A  C1048T  C3516a  T5442C  T6185C  C9042T  A9347G  G10589A  G12007A  A12720G ;;;;;;;;;;;;;;;;;;;;;;;;;\n"));
        assertEquals("L0a1a1", phyp.getHaplogroup(";;;;;;;;;;L0a1a1; T2759C ;;;;;;;;;;;;;;EU092714;JQ044893\n"));
        assertEquals("L0d1'2", phyp.getHaplogroup(";;L0d1'2; C498d  A3756G  G9755A  T16278C ;;;;;;;;;;;;;;;;;;;;;;;\n"));

    }
}
