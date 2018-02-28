package diffutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;

import junit.framework.TestCase;

public class EmptyContextUnifiedDiffTest extends TestCase {

	public List<String> fileToLines(String filename) {
		List<String> lines = new LinkedList<String>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		return lines;
	}

	public void testUnifiedContextInsert() {
		testUnifiedContextXXX(TestConstants.MOCK_FOLDER + "uc_insert_patch.txt", TestConstants.MOCK_FOLDER
				+ "uc_insert_revised.txt");
	}

	public void testUnifiedContextXXX(String patch_file, String revised_file) {
    	List<String> origLines = fileToLines(TestConstants.MOCK_FOLDER + "uc_original.txt");
    	List<String> revLines = fileToLines(revised_file);
    	List<String> unifiedDiff = fileToLines(patch_file);
    	List<String> patchedLines = null;
    	Patch patch = DiffUtils.parseUnifiedDiff(unifiedDiff);
    	    	
    	 try {
    		 patchedLines = (List<String>) patch.applyTo(origLines);
         } catch (PatchFailedException e) {
             fail(e.getMessage());
         }
         
         verifyLinesEqual(patchedLines, revLines);
    }
    
    public void verifyLinesEqual(List<String> patchedLines, List<String> revLines) {
    	assertTrue(revLines.size() == patchedLines.size());
        for (int i = 0; i < revLines.size(); i++) {
            String l1 = revLines.get(i);
            String l2 = patchedLines.get(i);
            if (!l1.equals(l2)) {
                fail("Line " + (i + 1) + " of the patched file did not match the revised original");
            }
        }
    }
    
}
