/**
 * MIT License
 *
 * Copyright (C) 2014 Petteri Kivimäki
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pkrete.xrd4j.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers helper methods for handling files.
 *
 * @author Petteri Kivimäki
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * Constructs and initializes a new FileUtil object. Should never
     * be used.
     */
    private FileUtil() {
    }

    /**
     * Reads the contents of the file denoted by the abstract path name. If
     * the file doesn't exist, an empty string is returned.
     * @param path abstract path name
     * @return contents of the file
     */
    public static String read(String path) {
        File file = new File(path);
        if (!file.exists()) {
            logger.warn("Reading file failed! File doesn't exist : {}", file.getAbsolutePath());
            return "";
        }
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file.getAbsolutePath()), "UTF-8");
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine());
                text.append(NL);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return text.toString().trim();
    }
}
