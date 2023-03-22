package com.patandmat.otmz.global.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimilarityMeasurer {

    public static Double getCosineSimilarity(final Map<String, Double> vector1, final Map<String, Double> vector2) {
        if (vector1 == null || vector2 == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }

        Set<String> intersection = getIntersection(vector1, vector2);
        final double dotProduct = calculateDotProduct(vector1, vector2, intersection);

        double sum1 = 0.0d;
        for (final Double value : vector1.values()) {
            sum1 += Math.pow(value, 2);
        }
        double sum2 = 0.0d;
        for (final Double value : vector2.values()) {
            sum2 += Math.pow(value, 2);
        }

        double cosineSimilarity;

        if (sum1 <= 0.0 || sum2 <= 0.0) {
            cosineSimilarity = 0.0;
        } else {
            cosineSimilarity = dotProduct / (Math.sqrt(sum1) * Math.sqrt(sum2));
        }

        return (double) Math.round(cosineSimilarity * 100) / 100;
    }

    private static Set<String> getIntersection(Map<String, Double> vector1, Map<String, Double> vector2) {
        Set<String> set = new HashSet<>(vector1.keySet());
        set.addAll(vector2.keySet());

        return set;
    }

    private static double calculateDotProduct(final Map<String, Double> vector1, final Map<String, Double> vector2, final Set<String> intersection) {
        long dotProduct = 0;
        for (String key : intersection) {
            dotProduct += vector1.get(key) * vector2.get(key);
        }

        return dotProduct;
    }
}
