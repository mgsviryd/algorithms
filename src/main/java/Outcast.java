public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(final WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(final String[] nouns) {
        int max = 0;
        String out = null;
        for (int i = 0; i < nouns.length; i++) {
            String temp = nouns[i];
            int dis = 0;
            for (String noun : nouns) {
                dis += wordNet.distance(temp, noun);
            }
            if (dis > max) {
                max = dis;
                out = temp;
            }
        }
        return out;
    }
}
