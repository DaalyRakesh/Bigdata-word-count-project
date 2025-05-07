# Simple Java MapReduce Implementation

This is a simplified implementation of the MapReduce programming model in plain Java, without requiring the Hadoop framework.

## Overview

This application demonstrates the core concepts of Big Data processing with a word count algorithm:

1. **Data Partitioning**: Splits input data into chunks
2. **Parallel Processing**: Processes chunks concurrently using Java threads
3. **Map Phase**: Counts word occurrences in each chunk
4. **Reduce Phase**: Aggregates results from all mappers
5. **Result Output**: Sorts and saves the final word counts

## How to Run

### Prerequisites
- Java JDK 8 or later

### Compilation and Execution

```bash
# Compile the Java file
javac SimpleWordCount.java

# Run the program with input file
java SimpleWordCount input.txt
```

The program will:
1. Read the input file
2. Split it into chunks
3. Process each chunk in parallel (map phase)
4. Combine the results (reduce phase)
5. Write the final word counts to `output.txt`

## Understanding Big Data Concepts

This simplified implementation demonstrates key Big Data principles:

- **Scalability**: Handles large amounts of data by splitting it into manageable chunks
- **Parallel Processing**: Utilizes multiple threads for concurrent execution
- **Data Locality**: Processes data where it resides (simulated)
- **Fault Tolerance**: Basic error handling (though not as robust as Hadoop)

While this is a simplified version that runs on a single machine, the same principles apply to distributed Big Data systems like Hadoop, where:
- Data is stored across many nodes in a cluster
- Processing tasks are distributed to nodes containing the data
- Results are combined from all nodes
- The system handles node failures and recovery

## Example Output

The output file will contain each unique word followed by its count, sorted alphabetically:

```
applications  1
big           3
data          3
...
``` 