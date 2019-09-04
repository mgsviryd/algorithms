import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class WordNet {
    private static final String LINE_SPLITTER = ",";
    private static final String WORD_SPLITTER = " ";
    private ArrayList<String> synsets;
    private HashMap<String, LinkedList<Integer>> nounsToIndexSynset;
    private Digraph digraph;
    private SAP sap;


    // constructor takes the name of the two input files
    public WordNet(final String synsetsFile, final String hypernymsFile) {
        if (synsetsFile == null || hypernymsFile == null) {
            throw new IllegalArgumentException();
        }
        this.nounsToIndexSynset = new HashMap<>();
        this.synsets = new ArrayList<>();
        readAndSaveSynsetsAndNouns(synsetsFile);
        this.digraph = new Digraph(synsets.size());
        readAndPutEdgesInDigraph(hypernymsFile);
        checkCycle(digraph);
        checkUnity(digraph);
        this.sap = new SAP(digraph);
    }

    private void checkUnity(final Digraph dig) {
        int countRoot = 0;
        for (int i = 0; i < dig.V(); i++) {
            int outdegree = dig.outdegree(i);
            if (outdegree == 0) {
                if (countRoot == 1) {
                    throw new IllegalArgumentException("Graph have many part. Unity absent.");
                }
                countRoot = 1;
            }
        }
    }

    private void checkCycle(final Digraph dig) {
        DirectedCycle directedCycle = new DirectedCycle(dig);
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException("Cycle in " + dig);
        }
    }

    private void readAndSaveSynsetsAndNouns(final String fileName) {
        In in = new In(fileName);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] elements = line.split(LINE_SPLITTER);
            Integer id = Integer.parseInt(elements[0]);
            String lineNouns = elements[1].trim();
            synsets.add(lineNouns);
            String[] nouns = lineNouns.split(WORD_SPLITTER);
            putAllNouns(id, nouns);
        }
    }

    private void readAndPutEdgesInDigraph(final String fileName) {
        In in = new In(fileName);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] elements = line.split(LINE_SPLITTER);
            int idSynset = Integer.parseInt(elements[0]);
            for (int i = 1; i < elements.length; i++) {
                int idHypernyms = Integer.parseInt(elements[i]);
                digraph.addEdge(idSynset, idHypernyms);
            }
        }
    }


    private void putAllNouns(final Integer id, final String[] nouns) {
        for (String noun : nouns) {
            LinkedList<Integer> indexes;
            if (nounsToIndexSynset.containsKey(noun)) {
                indexes = nounsToIndexSynset.get(noun);
                indexes.add(id);
            } else {
                indexes = new LinkedList<>();
                indexes.add(id);
                nounsToIndexSynset.put(noun, indexes);
            }
        }
    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsToIndexSynset.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(final String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nounsToIndexSynset.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(final String nounA, final String nounB) {
        if (!isNoun(nounA) && !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return helper(nounA, nounB)[0];
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(final String nounA, final String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int iAncestor = helper(nounA, nounB)[1];
        return synsets.get(iAncestor);
    }

    private int[] helper(final String nounA, final String nounB) {
        int length = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int val1 : nounsToIndexSynset.get(nounA)) {
            for (int val2 : nounsToIndexSynset.get(nounB)) {
                int temp = sap.length(val1, val2);

                if (temp < length) {
                    length = temp;
                    ancestor = sap.ancestor(val1, val2);
                }
            }
        }
        return new int[]{length, ancestor};
    }
}

