package com.liverpool.liverpooldev.shared.util;


import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Utility class for text normalization used in flexible, accent-insensitive search.
 *
 * <p>Also provides a Levenshtein distance implementation for typo-tolerant matching.
 */
public final class TextNormalizer {

    private static final Pattern ACCENT_PATTERN = Pattern.compile("\\p{M}");
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-z0-9\\s]");
    private static final Pattern EXTRA_SPACES = Pattern.compile("\\s+");

    private TextNormalizer() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String normalize(final String input) {
        if (input == null) {
            return "";
        }
        String decomposed = Normalizer.normalize(input, Normalizer.Form.NFD);
        String noAccents = ACCENT_PATTERN.matcher(decomposed).replaceAll("");
        String lower = noAccents.toLowerCase();
        String clean = NON_ALPHANUMERIC.matcher(lower).replaceAll("");
        return EXTRA_SPACES.matcher(clean).replaceAll(" ").trim();
    }

    public static boolean containsNormalized(final String text, final String query) {
        if (query == null || query.isBlank()) {
            return true;
        }
        return normalize(text).contains(normalize(query));
    }

    public static int levenshtein(final String a, final String b) {
        int la = a.length();
        int lb = b.length();
        int[][] dp = new int[la + 1][lb + 1];

        for (int i = 0; i <= la; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= lb; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= la; i++) {
            for (int j = 1; j <= lb; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost);
            }
        }
        return dp[la][lb];
    }

    public static boolean isSimilar(final String text, final String query, final int threshold) {
        if (query == null || query.isBlank()) {
            return true;
        }
        String normText = normalize(text);
        String normQuery = normalize(query);
        if (normText.contains(normQuery)) {
            return true;
        }
        return levenshtein(normText, normQuery) <= threshold;
    }
}