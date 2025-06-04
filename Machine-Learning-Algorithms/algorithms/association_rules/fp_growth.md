# FP-Growth Algorithm

## 1. Overview
The FP-Growth (Frequent Pattern Growth) algorithm is an efficient algorithm for mining frequent itemsets without candidate generation. It uses a compressed data structure called FP-tree to store the database in a compact form.

### Type of Learning
- Unsupervised Learning
- Association Rule Learning
- Pattern Mining

### Key Characteristics
- No candidate generation
- Uses FP-tree data structure
- Pattern growth approach
- Memory efficient
- Faster than Apriori

### When to Use
- Large-scale data mining
- Frequent pattern mining
- Association rule discovery
- Market basket analysis
- Pattern recognition

## 2. Historical Context
- Developed by Han et al. in 2000
- Improvement over Apriori algorithm
- Eliminates candidate generation
- More efficient for large datasets
- Still widely used in practice

## 3. Technical Details

### Mathematical Foundation

#### Support
The support of an itemset $X$ is defined as:
$$
\text{support}(X) = \frac{|\{t \in T \mid X \subseteq t\}|}{|T|}
$$
where:
- $T$ is the set of all transactions
- $t$ is a single transaction
- $|T|$ is the total number of transactions

#### FP-Tree Construction
- Scan the database to determine the frequency of each item.
- Discard infrequent items (below minimum support).
- Sort frequent items in each transaction in descending order of frequency.
- Insert each transaction into the FP-tree, sharing common prefixes.

#### FP-Tree Node Structure
Each node in the FP-tree contains:
- Item name
- Support count
- Node link (to next node with the same item)

#### Conditional Pattern Base
For an item $i$, its conditional pattern base is the set of prefix paths in the FP-tree co-occurring with $i$:
$$
\text{CPB}(i) = \{(P, \text{count}) \mid P \text{ is a prefix path of } i\}
$$

#### Conditional FP-Tree
For an item $i$, its conditional FP-tree is:
$$
\text{CFP}(i) = \text{FP-tree}(\text{CPB}(i))
$$

#### Pattern Growth
Frequent patterns are mined recursively by growing patterns from the conditional FP-trees:
$$
\text{FP-growth}(X \cup \{i\}) = \text{FP-growth}(X) \times \text{FP-growth}(i)
$$

#### Association Rule Metrics
- **Confidence:**
$$
\text{confidence}(X \rightarrow Y) = \frac{\text{support}(X \cup Y)}{\text{support}(X)}
$$
- **Lift:**
$$
\text{lift}(X \rightarrow Y) = \frac{\text{confidence}(X \rightarrow Y)}{\text{support}(Y)} = \frac{\text{support}(X \cup Y)}{\text{support}(X) \times \text{support}(Y)}
$$

### Training Process
1. Scan database once
2. Build FP-tree
3. Mine frequent patterns recursively
4. Generate association rules
5. Filter rules by confidence

### Key Parameters
- Minimum support threshold
- Minimum confidence threshold
- Minimum lift threshold
- Maximum pattern length

## 4. Performance Analysis

### Time Complexity
1. **FP-Tree Construction:**
$$
O(n \times m)
$$
where:
- $n$ = number of transactions
- $m$ = average transaction length

2. **Pattern Mining:**
Let $k$ be the number of frequent patterns and $l$ the average pattern length:
$$
O(k \times l)
$$

3. **Rule Generation:**
$$
O(k \times 2^l)
$$

**Overall Complexity:**
$$
O(n \times m + k \times l + k \times 2^l)
$$

### Space Complexity
1. **FP-Tree Storage:**
$$
O(|I| \times \text{avg\_path\_length})
$$
where $|I|$ is the number of unique items.

2. **Pattern Storage:**
$$
O(k \times l)
$$

3. **Rule Storage:**
$$
O(r \times l)
$$
where $r$ is the number of rules.

### Computational Requirements
- Memory efficient
- Single database scan
- Parallel processing possible
- Suitable for big data

## 5. Practical Applications
- Market basket analysis
- Web usage mining
- Bioinformatics
- Network traffic analysis
- Customer behavior analysis
- Intrusion detection
- Medical diagnosis

## 6. Advantages and Limitations

### Advantages
- Single database scan
- No candidate generation
- Memory efficient
- Faster than Apriori
- Compressed data structure

### Limitations
- Memory usage for FP-tree
- Complex implementation
- Sensitive to data order
- May not scale well
- Requires careful pruning

## 7. Comparison with Similar Algorithms

### vs Apriori
- **FP-Growth**: Pattern growth, single scan
- **Apriori**: Candidate generation, multiple scans
- **Use Case**: Choose FP-Growth for better performance

### vs ECLAT
- **FP-Growth**: FP-tree structure, pattern growth
- **ECLAT**: Vertical format, depth-first search
- **Use Case**: Choose based on memory constraints

### vs H-Mine
- **FP-Growth**: Tree-based, pattern growth
- **H-Mine**: Hyper-structure, hybrid approach
- **Use Case**: Choose based on data characteristics

### vs LCM
- **FP-Growth**: General purpose
- **LCM**: Closed itemset mining
- **Use Case**: Choose based on rule requirements

### vs Top-K
- **FP-Growth**: All frequent itemsets
- **Top-K**: K most frequent itemsets
- **Use Case**: Choose based on result size needs

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Matplotlib
- Seaborn

### Data Requirements
- Transactional data
- Binary or categorical features
- Clean data
- Appropriate encoding

### Best Practices
- Choose appropriate thresholds
- Preprocess data carefully
- Handle missing values
- Consider data sparsity
- Use efficient data structures

## 9. Python Implementation
See `fp_growth.py` for complete implementation. 