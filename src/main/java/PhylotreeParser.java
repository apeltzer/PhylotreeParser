import com.googlecode.jctree.ArrayListTree;
import com.googlecode.jctree.Tree;

import java.io.File;

/**
 * Created by peltzer on 04/11/2016.
 */


public class PhylotreeParser {
    //Need these methods for parsing the tree
    private Tree<String> tree = new ArrayListTree<String>();


    public static void main(String[] args) {
        //need this for main access
    }


    public PhylotreeParser(File input){
        parseFile(input);

    }


    private void parseFile(File f){
        //TODO
    }
}
