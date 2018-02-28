package diffutils;

import java.io.BufferedReader;
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
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// ignore ... any errors should already have been
					// reported via an IOException from the final flush.
				}
			}
		}
		return lines;
	}

	public void testEmptyUnifiedContextPatch() {
		List<String> origLines = fileToLines(TestConstants.MOCK_FOLDER + "unified_empty_context_original.txt");
		List<String> revLines = fileToLines(TestConstants.MOCK_FOLDER + "unified_empty_context_revised.txt");
		List<String> unifiedDiff = fileToLines(TestConstants.MOCK_FOLDER + "unified_empty_context_patch.txt");

		List<String> patchedLines = null;
		Patch patch = DiffUtils.parseUnifiedDiff(unifiedDiff);

		try {
			patchedLines = (List<String>) patch.applyTo(origLines);
		} catch (PatchFailedException e) {
			fail(e.getMessage());
		}

		verifyLinesEqual(patchedLines, revLines);
	}

	public void testEmptyUnifiedContextDiff() {
		List<String> origLines = fileToLines(TestConstants.MOCK_FOLDER + "unified_empty_context_original.txt");
		List<String> revLines = fileToLines(TestConstants.MOCK_FOLDER + "unified_empty_context_revised.txt");

		List<String> patchedLines = null;

		// Generate a 0-context diff then reapply
		Patch generatedPatch = DiffUtils.diff(origLines, revLines);
		List<String> generatedDiff = DiffUtils.generateUnifiedDiff("original", "revised", origLines, generatedPatch, 0);
		Patch newPatch = DiffUtils.parseUnifiedDiff(generatedDiff);

		try {
			patchedLines = (List<String>) newPatch.applyTo(origLines);
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
