import io.PhyloTreeParser;

import java.io.IOException;

/**
 * Created by neukamm on 16.12.2016.
 */
public class Runner {

    public static void main(String[] args) throws IOException {
        PhyloTreeParser phyloTreeParser = new PhyloTreeParser();
        phyloTreeParser.getFinalTree();
        phyloTreeParser.getMutationParser();
    }
}
