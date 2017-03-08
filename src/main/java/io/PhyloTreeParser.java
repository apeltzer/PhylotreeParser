package io;

import javafx.scene.control.TreeItem;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PhyloTreeParser extends DefaultHandler {

    private BufferedReader bfr ;
    private InputStreamReader fr;
    private TreeItem<String> finalTree = null;
    private MutationParser mutationParser;


    private void parseFile() throws IOException {
        mutationParser = new MutationParser();
        InputStream inputStream = getClass().getResourceAsStream("/mtdnacsv.csv");

       // File file = new File(getClass().getResourceAsStream("mtdnacsv.csv"));
        //We require a CSV file as input, get this by storign the HTML table (single file), open it in Excel as HTML -> save as CSV and you're done!
        //The ";" array size determines where to place a file correctly in our Tree
        fr = new InputStreamReader(inputStream);
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
        tree_items.add(rootItem);


        // iterate post-order through tree
        int currIndex = 0;
        int formerIndex = 0;

        for(String array : entries) {
            if(array.length() != 0){
                currIndex = getLevel(array);
                mutationParser.addHaplogroupWithMutations(array, currIndex);
                String haplogroup = getHaplogroup(array);
                TreeItem<String> item = new TreeItem<>(haplogroup);

                if(currIndex == 0) { // can only happen in the initialization phase (for L0, and L1'2'3'4'5'6')
                    rootItem.getChildren().add(item);
                    // update tree_item, set only rootItem
                    List<TreeItem> back_me_up = tree_items;
                    tree_items = updateIndices(back_me_up,1); // Update our "pointer" list
                    tree_items.add(item);
                    formerIndex = currIndex;
                    continue;
                }

                if (currIndex > formerIndex) { //then we are going down one level
                    tree_items.get(tree_items.size()-1).getChildren().add(item);
                    tree_items.add(item);
                    formerIndex = currIndex;

                } else if (currIndex == formerIndex) { //then we are in the same level with our sibling node
                    tree_items.get(tree_items.size()-1).getParent().getChildren().add(item);
                    // replace last item in treeItemList
                    tree_items.set(tree_items.size()-1, item);

                } else if (currIndex < formerIndex) { // then we are done traversing and have to go one level up again
                    formerIndex = currIndex;
                    List<TreeItem> back_me_up = tree_items;
                    tree_items = updateIndices(back_me_up,currIndex+1); // Update our "pointer" list
                    tree_items.get(tree_items.size()-1).getChildren().add(item);
                    tree_items.add(item);
                }
            }
        }

        finalTree = rootItem;
    }

    /**
     * Method returns the leading ";" symbols count of each line on search.
     * This is used to determine where we are in our CSV tree in the end.
     *
     * @param s
     * @return
     */
    public int getLevel(String s){
        int level = 0;
        for (int i = 0; i <= s.length(); i++){
            if(s.length()!=0 && s.charAt(i) == ';'){
                level++;
            } else {
                return level;
            }
        }
        return level;
    }

    /**
     * Method to return hpalogroup string
     *
     * @param s
     * @return
     */
    public static String getHaplogroup(String s){
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
        return index_array.subList(0,level); //sublist is (inclusive, exclusive)

    }


    /**
     * return finally parsed tree
     *
     * @return
     * @throws IOException
     */
    public TreeItem<String> getFinalTree() throws IOException{
        parseFile();
        return finalTree;
    }


    /**
     * This method returns true, if item with id exists in tree.
     * @param tree
     * @param id
     * @return
     */
    public boolean contains(TreeItem<String> tree, String id){
        boolean tmp = false;
        TreeIterator<String> iterator = new TreeIterator<>(tree);

        while (iterator.hasNext()) {
            if(iterator.next().getValue().equals(id)){
                return true;
            }
        }
        return tmp;
    }


    public MutationParser getMutationParser() {
        return mutationParser;
    }
}