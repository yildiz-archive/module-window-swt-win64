/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.module.graphic.ogre.test;

import java.util.Set;

/**
 * Check if the java class, the native header and the native source file
 * contains the same methods signature.
 * @author Van den Borre Grégory
 * @since 0.1 (17/09/2013)
 * @version 1.0
 */
public final class JniChecker {

    /**
     * Base path for the native header files.
     */
    private final String nativeHeaderPath;

    /**
     * Base path for the native source files.
     */
    private final String nativeSourcePath;

    /**
     * Build a JniChecker.
     * @param nativeHeader
     *            Base path for the native header files.
     * @param nativeSource
     *            Base path for the native source files.
     */
    public JniChecker(final String nativeHeader, final String nativeSource) {
        super();
        this.nativeHeaderPath = nativeHeader;
        this.nativeSourcePath = nativeSource;
    }

    /**
     * Check if a java class, a jni header file and a jni code file are
     * matching.
     * @param clazz
     *            Java class to use.
     * @param headerFile
     *            JNI header file to use.
     * @param sourceFile
     *            JNI source file to use.
     */
    @SuppressWarnings("rawtypes")
    public void check(final Class clazz, final String headerFile, final String sourceFile) {
        JavaCodeChecker jcc = new JavaCodeChecker(clazz);
        Set < JniMethod > javaResult = jcc.retrieveMethods();

        NativeCodeChecker checkerC = new NativeCodeChecker(this.nativeSourcePath, sourceFile);
        Set < JniMethod > cResult = checkerC.retrieveMethods();
        NativeCodeChecker checkerH = new NativeCodeChecker(this.nativeHeaderPath, headerFile);
        Set < JniMethod > hResult = checkerH.retrieveMethods();

        if (!hResult.containsAll(cResult) || !cResult.containsAll(hResult)) {
            for (JniMethod h : hResult) {
                if (!cResult.contains(h)) {
                    throw new JniMethodException(h + " is present in header missing in source " + sourceFile);
                }
            }
            for (JniMethod c : cResult) {
                if (!hResult.contains(c)) {
                    throw new JniMethodException(c + " is present in source and missing in header " + headerFile);
                }
            }
        }
        if (!javaResult.containsAll(hResult) || !hResult.containsAll(javaResult)) {
            for (JniMethod h : hResult) {
                if (!javaResult.contains(h)) {
                    throw new JniMethodException(h + " is is present in header and missing in java " + clazz.getName());
                }
            }
            for (JniMethod java : javaResult) {
                if (!hResult.contains(java)) {
                    throw new JniMethodException(java + " is present in java and missing in header " + headerFile);
                }
            }
        }
    }

}

