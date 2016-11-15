
import javafx.scene.control.TreeItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by peltzer on 04/11/2016.
 */


public class PhylotreeParser {
    private BufferedReader bfr ;
    private FileReader fr;

    public static void main(String[] args) throws IOException {
        //need this for main access
        if(args.length != 1) {
            System.err.println("You have to specify the input HTML object first!");
            System.exit(-1);
        } else {
            File f = new File(args[0]);
            PhylotreeParser phyp = new PhylotreeParser(f);
        }
    }


    public PhylotreeParser(File input) throws IOException {
        parseFile(input);
        //TODO write output ?
    }


    private void parseFile(File f) throws IOException {
        //We require a CSV file as input, get this by storign the HTML table (single file), open it in Excel as HTML -> save as CSV and you're done!
        //The ";" array size determines where to place a file correctly in our Tree
        fr = new FileReader(f);
        bfr = new BufferedReader(fr);

        ArrayList<String> entries = new ArrayList<String>();
        String currline = "";

        int startindex = 0;

        while((currline = bfr.readLine()) != null){
            //Ignore first 18 lines, ignore lines consisting solely of ";;;;;;;;;;;;;;;;;;;;;;;;;;"
            if((startindex < 18) || (currline.equals(";;;;;;;;;;;;;;;;;;;;;;;;;;"))){
                startindex++;
                continue;
            } else {
                entries.add(currline);
            }
        }


        /**
         * We have now cleared items here in the list and can start creating our tree.
         * There are three cases for tree traversal:
         *
         * - keep current level in the index, add other nodes to the current parent, repeat till end or level changes
         * - if level up -> add children to former node, start new parent node
         * - if level down -> continue going down, add to parent of current node
         * - ideally recursive function or something like this (!)
         */

        TreeItem rootItem = new TreeItem("RSRS");

        for(String array : entries){
            int index = getLevel(array);
            System.out.println(index + "\n");

        }

    }

    /**
     * Method returns the leading ";" symbols count of each line on search. This is used to determine where we are in our CSV tree in the end.
     * @param s
     * @return
     */
    public int getLevel(String s){
        int level = 0;
        for (int i = 0; i <= s.length(); i++){
            if(s.charAt(i) == ';'){
                level++;
            } else {
                return level;
            }
        }
        return level;
    }
}
