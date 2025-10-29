package com.project.memory;

import com.project.memory.exceptions.MemoryAccessException;

import java.util.*;

// associative cache
// supports 16 lines of cache
public class Cache {

    // each cache entry
    private static class CacheLine {
        boolean valid;
        int tag;       // memory address
        short data;      // 16bit word
        long timestamp; // in order to make this a queue

        CacheLine(int tag, short data, long timestamp) {
            this.valid = true;
            this.tag = tag;
            this.data = data;
            this.timestamp = timestamp;
        }
    }

    private static final int CACHE_SIZE = 16;
    private final List<CacheLine> lines = new ArrayList<>();
    private final Memory memory;               // reference to main memory
    private long clock = 0;                    // for FIFO ordering

    // stats
    private int hits = 0;
    private int misses = 0;

    public Cache(Memory memory) {
        this.memory = memory;
    }

    // read a word from cache
    // or memory if it's missed
    public short read(int address) throws MemoryAccessException {
        clock++;
        // first search if its inside cache
        for (CacheLine line : lines) {
            if (line.valid && line.tag == address) {
                hits++;
                System.out.printf("[CACHE HIT] Read ADDRESS: %04d -> %d%n", address, line.data);
                return line.data;
            }
        }

        // missed! get it from the memory
        misses++;
        short value = memory.read(address);
        System.out.printf("[CACHE MISS] Read ADDRESS: %04d from MEMORY -> %d%n", address, value);

        // 3. Insert into cache
        insertLine(address, value);
        return value;
    }

    // implementation of the same method as the one from memory
    public int readUnsigned(int address) throws MemoryAccessException {
        return read(address) & 0xFFFF;
    }

    // write a word inside the cache
    public void write(int address, short value) throws MemoryAccessException {
        clock++;
        boolean hit = false;

        // if the address is in just update the timestamp
        for (CacheLine line : lines) {
            if (line.valid && line.tag == address) {
                line.data = value;
                hit = true;
                hits++;
                System.out.printf("[CACHE HIT] Write ADDRESS: %04d = %d%n", address, value);
                break;
            }
        }

        // load it into cache by following a queue (first in first out)
        if (!hit) {
            misses++;
            System.out.printf("[CACHE MISS] Write ADDRESS: %04d (new line) = %d%n", address, value);
            insertLine(address, value);
        }

        // save it in memory
        memory.write(address, (short) value);
    }

    private void insertLine(int address, short value) {
        if (lines.size() < CACHE_SIZE) {
            lines.add(new CacheLine(address, value, clock));
        } else {
            // if we have at least 16
            // then delete the oldest one
            // and add newer one
            CacheLine oldest = Collections.min(lines, Comparator.comparingLong(l -> l.timestamp));
            System.out.printf("[CACHE EVICT] Removing ADDRESS: %04d%n", oldest.tag);
            lines.remove(oldest);
            lines.add(new CacheLine(address, value, clock));
        }
    }

    // purge whole cache
    public void clear() {
        lines.clear();
        hits = 0;
        misses = 0;
        System.out.println("CACHE CLEARED");
    }

    // debug
    public void printCache() {
        System.out.println("\n--- CACHE ---");
        for (CacheLine line : lines) {
            System.out.printf("Address %04d | Data %d | Valid %b | Time %d%n",
                    line.tag, line.data, line.valid, line.timestamp);
        }
        System.out.printf("Hits: %d | Misses: %d | Hit Rate: %.2f%%%n",
                hits, misses, (hits + misses) == 0 ? 0.0 : (100.0 * hits / (hits + misses)));
        System.out.println("------------------------\n");
    }

    // gui
    public int getHits() { return hits; }
    public int getMisses() { return misses; }
}