import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.io.File;

public class SeamCarverTest {
    private static final String RESOURCE = "/10x10.png";
    private final String fileNameResource = getClass().getResource(RESOURCE).getFile();
    private final File fileResource = new File(fileNameResource);
    @Test
    public void go(){
        Picture picture = new Picture(fileResource);
        SeamCarverBest sc = new SeamCarverBest(picture);
        int[] hSeam = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(hSeam);
        int[] vSeam = sc.findVerticalSeam();
        sc.removeVerticalSeam(vSeam);
    }
}
