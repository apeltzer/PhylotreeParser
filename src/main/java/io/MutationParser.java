package io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by neukamm on 24.02.17.
 */
public class MutationParser {
    HashMap<String, List<String>> mutation_to_hg = new HashMap<>();
    HashMap<String, String[]> hg_to_mutation = new HashMap<>();


    public MutationParser(){

    }

    /**
     * This method iterates over entry of tree-documents (entry == line)
     * and assigns to each mutation all corresponding Haplotypes that have this mutation
     * and to each Haplotype all corresponding mutations.
     *
     * @param entry
     * @param currIndex
     */
    public void addHaplogroupWithMutations(String entry, int currIndex, String[] previousMutations){
        String line = entry.substring(currIndex);
        String[] line_splitted = line.split(";");

        if(line_splitted.length>=1){
            String hg = line_splitted[0];
            String[] mutations;
            if(line_splitted.length==1 || line_splitted[1].trim().equals("")){
                mutations = new String[]{line_splitted[0]};
            } else {
                mutations = line_splitted[1].trim().split("  ");
            }

            String[] mutations_all = concat(mutations, previousMutations);
            hg_to_mutation.put(hg, mutations_all);
            for(String mut : mutations){
                if(mut.length()>1){
                    mut = mut.trim();
                    if(mutation_to_hg.keySet().contains(mut)){
                        List<String> entries = mutation_to_hg.get(mut);
                        entries.add(hg);
                        mutation_to_hg.put(mut, entries);
                    } else {
                        List<String> e = new ArrayList<>();
                        e.add(hg);
                        mutation_to_hg.put(mut, e);
                    }
                }
            }
        }
    }


    public String[] concat(String[] a, String[] b) {

        int aLen = a.length;
        int bLen = b.length;
        String[] c = new String[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;

    }

    public HashMap<String, List<String>> getMutation_to_hg() {
        return mutation_to_hg;
    }
    public HashMap<String, String[]> getHg_to_mutation() {
        return hg_to_mutation;
    }

}
