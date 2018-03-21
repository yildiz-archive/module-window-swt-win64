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

import java.security.InvalidParameterException;
import java.util.List;


/**
 * Wrap a JNI method signature.
 * @author Van den Borre Grégory
 * @since 0.1 (17/09/2013)
 */
public final class JniMethod {

    /**
     * Method return type.
     */
    private final Type returnType;

    /**
     * Method name.
     */
    private final String methodName;

    /**
     * Ordered list of parameter types.
     */
    private final List < Type > parameters;

    public JniMethod(Type returnType, String methodName, List<Type> parameters) {
        this.returnType = returnType;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (Type s : parameters) {
            sb.append(s.name + ", ");
        }
        sb.append(")");
        String result = sb.toString().replace(", )", ")");
        return this.returnType.name + " " + this.methodName + result;
    }

    /**
     * List of possible types.
     * @author Van den Borre Grégory
     * @since 0.1 (17/09/2013)
     * @version 1.0
     */
    public enum Type {

        /**
         * Void.
         */
        VOID("void", "void"),

        /**
         * String.
         */
        STRING("String", "jstring"),

        /**
         * Short.
         */
        SHORT("short", "jshort"),

        /**
         * int.
         */
        INT("int", "jint"),

        /**
         * long.
         */
        LONG("long", "POINTER"),

        /**
         * float.
         */
        FLOAT("float", "jfloat"),

        /**
         * double.
         */
        DOUBLE("double", "jdouble"),

        /**
         * boolean.
         */
        BOOLEAN("boolean", "jboolean"),

        /**
         * int[].
         */
        INT_ARRAY("int[]", "jintArray"),

        /**
         * long[].
         */
        LONG_ARRAY("long[]", "jlongArray"),

        /**
         * float[].
         */
        FLOAT_ARRAY("float[]", "jfloatArray"),

        /**
         * double[].
         */
        DOUBLE_ARRAY("double[]", "jdoubleArray");

        /**
         * Java name.
         */
        private String name;

        /**
         * Native name.
         */
        private String jniName;

        /**
         * Type.
         * @param name
         *            Java name.
         * @param jniName
         *            Native name.
         */
        Type(final String name, final String jniName) {
            this.name = name;
            this.jniName = jniName;
        }

        /**
         * Retrieve a type from its java or native name.
         * @param s
         *            Type name.
         * @return The matching type, or throw an unvalid parameter exception if
         *         nothing is found.
         */
        public static Type get(final String s) {
            for (Type t : Type.values()) {
                if (t.name.equals(s) || t.jniName.equals(s)) {
                    return t;
                }
            }
            throw new InvalidParameterException(s);
        }
    }

}

