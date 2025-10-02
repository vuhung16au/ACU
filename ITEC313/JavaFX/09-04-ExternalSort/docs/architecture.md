# Architecture

- Create phase: sequential write of random integers.
- Run generation: read up to chunkSize lines, parse, sort in-memory, write as a temp run.
- Merge phase: k-way merge from run files via min-heap; stream to output.


