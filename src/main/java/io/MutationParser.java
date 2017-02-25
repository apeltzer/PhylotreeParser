package io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by neukamm on 24.02.17.
 */
public class MutationParser {
    HashMap<String, List<String>> mutation_to_hg = new HashMap<>();

    public MutationParser(){

    }

    public void addHaplogroupWithMutations(String entry, int currIndex){
        String line = entry.substring(currIndex);
        String[] line_splitted = line.split(";");
        if(line_splitted.length>=2){
            String hg = line_splitted[0];
            String[] mutations = line_splitted[1].trim().split(" ");
            for(String mut : mutations){
                if(mut.length()>0){
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

    public HashMap<String, List<String>> getMutation_to_hg() {
        return mutation_to_hg;
    }
}
