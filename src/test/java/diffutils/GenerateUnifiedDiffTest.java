/*
 * SPDX-License-Identifier: Apache-1.1
 *
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.
 * Copyright (c) 2010 Dmitry Naumenko (dm.naumenko@gmail.com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package diffutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;

public class GenerateUnifiedDiffTest extends TestCase {


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

    public void testGenerateUnified() {
        List<String> origLines = fileToLines(TestConstants.MOCK_FOLDER + "original.txt");
        List<String> revLines = fileToLines(TestConstants.MOCK_FOLDER + "revised.txt");

        verify(origLines, revLines, "original.txt", "revised.txt");
    }

    public void testGenerateUnifiedWithOneDelta() {
        List<String> origLines = fileToLines(TestConstants.MOCK_FOLDER + "one_delta_test_original.txt");
        List<String> revLines = fileToLines(TestConstants.MOCK_FOLDER + "one_delta_test_revised.txt");

        verify(origLines, revLines, "one_delta_test_original.txt", "one_delta_test_revised.txt");
    }

    public void testGenerateUnifiedDiffWithoutAnyDeltas() {
        List<String> test = Arrays.asList("abc");
        Patch<String> patch = DiffUtils.diff(test, test);
        DiffUtils.generateUnifiedDiff("abc", "abc", test, patch, 0);
    }

    public void testDiff_Issue10() {
        final List<String> baseLines = fileToLines(TestConstants.MOCK_FOLDER + "issue10_base.txt");
        final List<String> patchLines = fileToLines(TestConstants.MOCK_FOLDER + "issue10_patch.txt");
        final Patch<String> p = DiffUtils.parseUnifiedDiff(patchLines);
        try {
            DiffUtils.patch(baseLines, p);
        } catch (PatchFailedException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Issue 12
     */
    public void testPatchWithNoDeltas() {
        final List<String> lines1 = fileToLines(TestConstants.MOCK_FOLDER + "issue11_1.txt");
        final List<String> lines2 = fileToLines(TestConstants.MOCK_FOLDER + "issue11_2.txt");
        verify(lines1, lines2, "issue11_1.txt", "issue11_2.txt");
    }

    public void testDiff5() {
        final List<String> lines1 = fileToLines(TestConstants.MOCK_FOLDER + "5A.txt");
        final List<String> lines2 = fileToLines(TestConstants.MOCK_FOLDER + "5B.txt");
        verify(lines1, lines2, "5A.txt", "5B.txt");
    }

    /**
     * Issue 19
     */
    public void testDiffWithHeaderLineInText() {
        List<String> original = new ArrayList<String>();
        List<String> revised  = new ArrayList<String>();

        original.add("test line1");
        original.add("test line2");
        original.add("test line 4");
        original.add("test line 5");

        revised.add("test line1");
        revised.add("test line2");
        revised.add("@@ -2,6 +2,7 @@");
        revised.add("test line 4");
        revised.add("test line 5");

        Patch<String> patch = DiffUtils.diff(original, revised);
        List<String> udiff = DiffUtils.generateUnifiedDiff("original", "revised",
                original, patch, 10);
        DiffUtils.parseUnifiedDiff(udiff);
    }

    private void verify(List<String> origLines, List<String> revLines,
            String originalFile, String revisedFile) {
        Patch<String> patch = DiffUtils.diff(origLines, revLines);
        List<String> unifiedDiff = DiffUtils.generateUnifiedDiff(originalFile, revisedFile,
                origLines, patch, 10);

        Patch<String> fromUnifiedPatch = DiffUtils.parseUnifiedDiff(unifiedDiff);
        List<String> patchedLines;
        try {
            patchedLines = (List<String>) fromUnifiedPatch.applyTo(origLines);
            assertTrue(revLines.size() == patchedLines.size());
            for (int i = 0; i < revLines.size(); i++) {
                String l1 = revLines.get(i);
                String l2 = patchedLines.get(i);
                if (!l1.equals(l2)) {
                    fail("Line " + (i + 1) + " of the patched file did not match the revised original");
                }
            }
        } catch (PatchFailedException e) {
            fail(e.getMessage());
        }
    }
}
