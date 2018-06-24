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

import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class PatchTest extends TestCase {

    public void testPatch_Insert()  {
        final List<String> insertTest_from = Arrays.asList("hhh");
        final List<String> insertTest_to = Arrays.asList("hhh", "jjj", "kkk", "lll");

        final Patch<String> patch = DiffUtils.diff(insertTest_from, insertTest_to);
        try {
            assertEquals(insertTest_to, DiffUtils.patch(insertTest_from, patch));
        } catch (PatchFailedException e) {
            fail(e.getMessage());
        }
    }

    public void testPatch_Delete() {
        final List<String> deleteTest_from = Arrays.asList("ddd", "fff", "ggg", "hhh");
        final List<String> deleteTest_to = Arrays.asList("ggg");

        final Patch<String> patch = DiffUtils.diff(deleteTest_from, deleteTest_to);
        try {
            assertEquals(deleteTest_to, DiffUtils.patch(deleteTest_from, patch));
        } catch (PatchFailedException e) {
            fail(e.getMessage());
        }
    }

    public void testPatch_Change() {
        final List<String> changeTest_from = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        final List<String> changeTest_to = Arrays.asList("aaa", "bxb", "cxc", "ddd");

        final Patch<String> patch = DiffUtils.diff(changeTest_from, changeTest_to);
        try {
            assertEquals(changeTest_to, DiffUtils.patch(changeTest_from, patch));
        } catch (PatchFailedException e) {
            fail(e.getMessage());
        }
    }
}
