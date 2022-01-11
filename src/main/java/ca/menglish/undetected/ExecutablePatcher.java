package ca.menglish.undetected;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExecutablePatcher {

    public static boolean patchExecutable(File chromedriver) {
        return patchExecutable(chromedriver, getReplacementCdc());
    }

    public static boolean patchExecutable(File chromedriver, String replacementCdc) {
        try {
            return replaceStringInFile(chromedriver, replacementCdc);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static String getReplacementCdc() {
        return "fwf_g6hq42r9ikl0987ghnbvwr";
    }

    /**
     * Finds the boundary in the given buffer using Boyer-Moore algo.
     * Copied from java.util.regex.Pattern.java
     *
     * @param file {@link File} to search through for the boundary
     * @param replacementText Text to replace the search text with
     */
    private static boolean replaceStringInFile(File file, String replacementText) throws IOException {
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        byte[] replacementBytes = replacementText.getBytes();
        boolean didReplacement = false;

        for(int offset = 0; offset < fileBytes.length - 24; offset ++) {
            if (fileBytes[offset] != 'c') {
                continue;
            }
            else if (fileBytes[offset + 1] != 'd') {
                continue;
            }
            else if (fileBytes[offset + 2] != 'c') {
                continue;
            }
            else if (fileBytes[offset + 3] != '_') {
                continue;
            }

            // Entire pattern matched starting at off
            for (int i = 0; i < replacementBytes.length; i++) {
                fileBytes[offset + i] = replacementBytes[i];
            }
            didReplacement = true;
        }

        Files.write(file.toPath(), fileBytes);

        return didReplacement;
    }


    /**
     * Boyer-Moore search method. Copied from java.util.regex.Pattern.java
     *
     * Pre calculates arrays needed to generate the bad character
     * shift and the good suffix shift. Only the last seven bits
     * are used to see if chars match; This keeps the tables small
     * and covers the heavily used ASCII range, but occasionally
     * results in an aliased match for the bad character shift.
     */
    private static void compileBoundaryPattern(byte[] fileBytes, int[] badCharacterShifts, int[] goodSuffixShifts) {
        int i, j;

        // Precalculate part of the bad character shift
        // It is a table for where in the pattern each
        // lower 7-bit value occurs
        for (i = 0; i < fileBytes.length; i++) {
            badCharacterShifts[fileBytes[i]&0x7F] = (i + 1);
        }

        // Precalculate the good suffix shift
        // i is the shift amount being considered
        NEXT:   for (i = fileBytes.length; i > 0; i--) {
            // j is the beginning index of suffix being considered
            for (j = fileBytes.length - 1; j >= i; j--) {
                // Testing for good suffix
                if (fileBytes[j] == fileBytes[j-i]) {
                    // src[j..len] is a good suffix
                    goodSuffixShifts[j-1] = i;
                } else {
                    // No match. The array has already been
                    // filled up with correct values before.
                    continue NEXT;
                }
            }
            // This fills up the remaining of optoSft
            // any suffix can not have larger shift amount
            // then its sub-suffix. Why???
            while (j > 0) {
                goodSuffixShifts[--j] = i;
            }
        }
        // Set the guard value because of unicode compression
        goodSuffixShifts[fileBytes.length -1] = 1;
    }

}
