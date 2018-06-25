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
package difflib;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StringUtills {
    
    public static <T> String join(final Iterable<T> objs, final String delimiter) {
        Iterator<T> iter = objs.iterator();
        if (!iter.hasNext()) {
            return "";
        }
        StringBuffer buffer = new StringBuffer(String.valueOf(iter.next()));
        while (iter.hasNext()) {
            buffer.append(delimiter).append(String.valueOf(iter.next()));
        }
        return buffer.toString();
    }
    
    /**
     * Replaces all tabs with 4 spaces.
     * @param str The string.
     * @return
     */
    public static String expandTabs(String str) {
        return str.replace("\t", "    ");
    }
    
    /**
     * Replaces all opening an closing tags with <code>&lt;</code> or <code>&gt;</code>.
     * @param str
     * @return
     */
    public static String htmlEntites(String str) {
        return str.replace("<", "&lt;").replace(">", "&gt;");
    }
    
    public static String normalize(String str) {
        return expandTabs(htmlEntites(str));
    }
    
    public static List<String> normalize(List<String> list) {
        List<String> result = new LinkedList<String>();
        for (String line : list) {
            result.add(normalize(line));
        }
        return result;
    }
    
    public static List<String> wrapText(List<String> list, int columnWidth) {
        List<String> result = new LinkedList<String>();
        for (String line : list) {
            result.add(wrapText(line, columnWidth));
        }
        return result;
    }
    
    /**
     * Wrap the text with the given column width 
     * @param line the text
     * @param columnWidth the given column
     * @return the wrapped text
     */
    public static String wrapText(String line, int columnWidth) {
        int lenght = line.length();
        int delimiter = "<br>".length();
        int widthIndex = columnWidth;
        
        for (int count = 0; lenght > widthIndex; count++) {
            line = line.subSequence(0, widthIndex + delimiter * count) + "<br>"
                    + line.substring(widthIndex + delimiter * count);
            widthIndex += columnWidth;
        }
        
        return line;
    }
}
