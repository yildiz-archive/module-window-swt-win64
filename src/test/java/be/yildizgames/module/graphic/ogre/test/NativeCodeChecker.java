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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Retrieve all native methods in a native header or a source file.
 * @author Van den Borre Grégory
 * @since 0.1(17/09/2013)
 * @version 1.0
 */
public final class NativeCodeChecker {

    /**
     * Directory where the file is contained.
     */
    private final String basePath;

    /**
     * File name
     */
    private final String fileName;

    /**
     * End of line for the signature, can be ';' for a header file or '{' for a
     * source file.
     */
    private final String separator;

    /**
     * Retrieve all native methods from a native code.
     * @param base
     *            Directory where the file is contained.
     * @param file
     *            File name.
     */
    public NativeCodeChecker(final String base, final String file) {
        this.basePath = base;
        this.fileName = file;
        if (fileName.endsWith(".cpp")) {
            this.separator = "{";
        } else {
            this.separator = ";";
        }
    }

    /**
     * Retrieve all native methods from a native code.
     * @return The list of methods.
     */
    public Set < JniMethod > retrieveMethods() {
        Set < JniMethod > methods = new HashSet <>();
        Path path = Paths.get(this.basePath + "/" + this.fileName);
        try {
            List < String > lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            List < String > finalList = new ArrayList <>();
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (line.contains("JNIEXPORT")) {
                    sb = new StringBuilder();
                }
                line = line.replaceAll("\n", "");
                line = line.replace("JNIEXPORT ", "");
                line = line.replace("Java_", "");
                line = line.replace("JNICALL ", "");
                line = line.trim();
                sb.append(line);
                if (line.contains(this.separator)) {
                    finalList.add(sb.toString());
                }
            }
            for (String s : finalList) {
                methods.add(process(s));
            }
            return methods;
        } catch (IOException e) {
            throw new JniMethodException(e);
        }
    }

    /**
     * Process a line to extract an method.
     * @param method
     *            Line to process.
     * @return The method extracted from the line.
     */
    private JniMethod process(final String method) {
        String processedResult = method.replace(this.separator, "");
        String[] s1 = processedResult.split(" ");
        JniMethod.Type returnType = JniMethod.Type.get(s1[0]);
        // remove the return type from the string.
        processedResult = processedResult.replaceFirst(s1[0] + " ", "");
        // retrieve the parameters contained between ().
        String parameterString = processedResult.split("\\(")[1];
        // separate every parameters.
        String[] params = parameterString.split(",");
        List <JniMethod.Type> parameters = new ArrayList <>();
        for (String param : params) {
            param = param.replace(")", "").trim();
            // jni specific parameters are ignored
            if (!param.contains("JNIEnv") && !param.contains("jobject") && !param.contains("jclass")) {
                parameters.add(JniMethod.Type.get(param.trim().split(" ")[0]));
            }
        }
        processedResult = processedResult.split("\\(")[0].replace("Java_", "");
        processedResult = processedResult.replace("_", ".");
        return new JniMethod(returnType, processedResult, parameters);
    }

}
