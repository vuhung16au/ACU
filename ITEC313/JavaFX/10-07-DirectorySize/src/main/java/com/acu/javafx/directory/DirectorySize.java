package com.acu.javafx.directory;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/**
 * Compute directory size using an iterative breadth-first traversal with a queue.
 * This avoids recursion by enqueuing subdirectories and processing until empty.
 */
public final class DirectorySize {

    private DirectorySize() { }

    /**
     * Returns total bytes of all regular files under the given root directory.
     * Non-existent paths return 0. Symlinks are treated as files by {@link File#isDirectory()} semantics.
     */
    public static long computeSize(File root) {
        if (root == null || !root.exists()) {
            return 0L;
        }
        if (root.isFile()) {
            long length = root.length();
            return Math.max(0L, length);
        }

        long total = 0L;
        Queue<File> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            File dir = queue.remove();
            File[] children = dir.listFiles();
            if (children == null) {
                // Permission issues or IO error; skip
                continue;
            }
            for (File child : children) {
                if (child == null) continue;
                if (child.isFile()) {
                    total += Math.max(0L, child.length());
                } else if (child.isDirectory()) {
                    queue.add(child);
                }
            }
        }
        return total;
    }
}


