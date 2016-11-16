
import javafx.scene.control.TreeItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peltzer on 04/11/2016.
 */


public class PhylotreeParser {
    private BufferedReader bfr ;
    private FileReader fr;
    private TreeItem<String> mtDnaTree;

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
        this.mtDnaTree = parseFile(input);
    }


    private TreeItem<String> parseFile(File f) throws IOException {
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
        rootItem.setExpanded(true);
        List<TreeItem> tree_items = new ArrayList<TreeItem>();


        // iterate post-order through tree
        int currIndex = 0;
        int formerIndex = 0;
        int counter = 0;

        for(String array : entries) {
            currIndex = getLevel(array);
            String haplogroup_untrimmed = getHaplogroup(array);
            String haplogroup = haplogroup_untrimmed.trim();
            Pattern p = Pattern.compile("[ACTGactg]{1}[0-9]{1,5}[ACTGactg]{1}!{0,2}");
            Matcher matcher = p.matcher(haplogroup);
            TreeItem<String> item;


            if(matcher.matches()){//Then  we skip that shit...
                continue;
            } else {
                item = new TreeItem<String>(haplogroup);
            }


            if(currIndex == 0) { // can only happen in the initialization phase (for L0, and L1'2'3'4'5'6')
                rootItem.getChildren().add(item);
                tree_items.add(item);
                continue;
            }

            if (currIndex > formerIndex) { //then we are going down one level
                tree_items.get(tree_items.size()-1).getChildren().add(item);
                tree_items.add(item);
                formerIndex = currIndex;

            } else if (currIndex == formerIndex) { //then we are in the same level with our sibling node

                tree_items.get(tree_items.size()-1).getParent().getChildren().add(item);



            } else if (currIndex < formerIndex) { // then we are done traversing and have to go one level up again
                formerIndex = currIndex;
                List<TreeItem> back_me_up = tree_items;
                tree_items = updateIndices(back_me_up,currIndex); // Update our "pointer" list
                tree_items.get(tree_items.size()-1).getChildren().add(item);

            }
        }

        return rootItem;
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

    /**
     * Method to return hpalogroup string
     * @param s
     * @return
     */
    public String getHaplogroup(String s){
        String tmp = s.replaceFirst("^;*","");
        String[] splitted = tmp.split(";");
        return splitted[0];
    }

    /**
     * Method to update index array.
     * Deletes all indices which are not needed anymore.
     *
     * @param index_array
     * @param level
     * @return
     */
    public List<TreeItem> updateIndices(List<TreeItem> index_array, int level){
        if(level == 11) {
            System.out.println("teschd!");
        }
        return index_array.subList(0,level); //sublist is (inclusive, exclusive)
    }


    /**
     * Returns our mtDNATree as TreeItem<String>
     * @return
     */
    public TreeItem<String> getMtDnaTree() {
        return mtDnaTree;
    }
}
