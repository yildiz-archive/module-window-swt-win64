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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Retrieve all native methods in a java class.
 * @author Van den Borre Grégory
 * @since 0.1(17/09/2013)
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public final class JavaCodeChecker {

    /**
     * Class to retrieve the native methods from.
     */
    private final Class clazz;

    /**
     * Create the java code checker for a given class.
     * @param clazz
     *            Class to check.
     */
    public JavaCodeChecker(final Class clazz) {
        this.clazz = clazz;
    }

    /**
     * Retrieve all native methods from a java class.
     * @return The list of methods.
     */
    public Set < JniMethod > retrieveMethods() {
        Set < JniMethod > toReturn = new HashSet <>();
        Method[] mList = this.clazz.getDeclaredMethods();
        for (Method m : mList) {
            if (Modifier.isNative(m.getModifiers())) {
                String name = this.clazz.getName() + "." + m.getName();
                JniMethod.Type returnType = JniMethod.Type.get(m.getReturnType().getSimpleName());
                List <JniMethod.Type> parameters = new ArrayList <>();
                for (Class cs : m.getParameterTypes()) {
                    parameters.add(JniMethod.Type.get(cs.getSimpleName()));
                }
                toReturn.add(new JniMethod(returnType, name, parameters));
            }
        }
        return toReturn;
    }

}
