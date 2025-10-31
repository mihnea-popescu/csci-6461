package com.project.util;

import com.project.memory.Cache;

public class CacheToString {
    public static String cacheToString(Cache cache) {
        StringBuilder sb = new StringBuilder();

        try {
            // --- Stats line (first line) ---
            int hits = cache.getHits();
            int misses = cache.getMisses();
            double hitRate = (hits + misses) == 0 ? 0.0 : (100.0 * hits / (hits + misses));

            sb.append(String.format("Hits: %d | Misses: %d | Hit Rate: %.2f%%%n", hits, misses, hitRate));

            // --- Header line (second line) ---
            sb.append(String.format("%-10s %-10s %-8s %-10s%n", "Address", "Data", "Valid", "Time"));
            sb.append("------------------------------------------------\n");

            // --- Get cache lines via reflection ---
            java.lang.reflect.Field linesField = Cache.class.getDeclaredField("lines");
            linesField.setAccessible(true);
            java.util.List<?> lines = (java.util.List<?>) linesField.get(cache);

            for (Object line : lines) {
                java.lang.reflect.Field tagField = line.getClass().getDeclaredField("tag");
                java.lang.reflect.Field dataField = line.getClass().getDeclaredField("data");
                java.lang.reflect.Field validField = line.getClass().getDeclaredField("valid");
                java.lang.reflect.Field timeField = line.getClass().getDeclaredField("timestamp");

                tagField.setAccessible(true);
                dataField.setAccessible(true);
                validField.setAccessible(true);
                timeField.setAccessible(true);

                int tag = tagField.getInt(line);
                short data = dataField.getShort(line);
                boolean valid = validField.getBoolean(line);
                long time = timeField.getLong(line);

                sb.append(String.format("%-10d %-10d %-8s %-10d%n", tag, data, valid, time));
            }

        } catch (Exception e) {
            sb.append("Error reading cache content: ").append(e.getMessage());
        }

        return sb.toString();
    }

}