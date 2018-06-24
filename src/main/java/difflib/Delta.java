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

import java.util.*;

/**
 * Describes the delta between original and revised texts.
 * 
 * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
 * @param T The type of the compared elements in the 'lines'.
 */
public abstract class Delta<T> {
	
	/** The original chunk. */
    private Chunk<T> original;
    
    /** The revised chunk. */
    private Chunk<T> revised;
    
    /**
     * Specifies the type of the delta.
     *
     */
    public enum TYPE {
    	/** A change in the original. */
        CHANGE, 
        /** A delete from the original. */
        DELETE, 
        /** An insert into the original. */
        INSERT
    }
    
    /**
     * Construct the delta for original and revised chunks
     * 
     * @param original Chunk describing the original text. Must not be {@code null}.
     * @param revised Chunk describing the revised text. Must not be {@code null}.
     */
    public Delta(Chunk<T> original, Chunk<T> revised) {
    	if (original == null) {
    		throw new IllegalArgumentException("original must not be null");
    	}
    	if (revised == null) {
    		throw new IllegalArgumentException("revised must not be null");
    	}
        this.original = original;
        this.revised = revised;
    }
    
    /**
     * Verifies that this delta can be used to patch the given text.
     * 
     * @param target the text to patch.
     * @throws PatchFailedException if the patch cannot be applied.
     */
    public abstract void verify(List<T> target) throws PatchFailedException;
    
    /**
     * Applies this delta as the patch for a given target
     * 
     * @param target the given target
     * @throws PatchFailedException
     */
    public abstract void applyTo(List<T> target) throws PatchFailedException;
    
    /**
     * Cancel this delta for a given revised text. The action is opposite to
     * patch.
     * 
     * @param target the given revised text
     */
    public abstract void restore(List<T> target);
    
    /**
     * Returns the type of delta
     * @return the type enum
     */
    public abstract TYPE getType();
    
    /**
     * @return The Chunk describing the original text.
     */
    public Chunk<T> getOriginal() {
        return original;
    }
    
    /**
     * @param original The Chunk describing the original text to set.
     */
    public void setOriginal(Chunk<T> original) {
        this.original = original;
    }
    
    /**
     * @return The Chunk describing the revised text.
     */
    public Chunk<T> getRevised() {
        return revised;
    }
    
    /**
     * @param revised The Chunk describing the revised text to set.
     */
    public void setRevised(Chunk<T> revised) {
        this.revised = revised;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((original == null) ? 0 : original.hashCode());
        result = prime * result + ((revised == null) ? 0 : revised.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Delta<T> other = (Delta) obj;
        if (original == null) {
            if (other.original != null)
                return false;
        } else if (!original.equals(other.original))
            return false;
        if (revised == null) {
            if (other.revised != null)
                return false;
        } else if (!revised.equals(other.revised))
            return false;
        return true;
    }
    
}
