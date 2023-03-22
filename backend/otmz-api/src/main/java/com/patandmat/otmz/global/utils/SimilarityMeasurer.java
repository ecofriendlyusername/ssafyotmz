package com.patandmat.otmz.global.utils;

import java.util.List;

public class SimilarityMeasurer {

    public static Double getCosineSimilarity(final List<Double> vector1, final List<Double> vector2) {
        if (vector1 == null || vector2 == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }

        final double dotProduct = calculateDotProduct(vector1, vector2);
        double sum1 = 0.0d;
        for (final Double value : vector1) {
            sum1 += Math.pow(value, 2);
        }
        double sum2 = 0.0d;
        for (final Double value : vector2) {
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

    private static double calculateDotProduct(final List<Double> vector1, final List<Double> vector2) {
        long dotProduct = 0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
        }

        return dotProduct;
    }
}
