# Hadoop MapReduce Word Count Example

This is a basic Big Data application that demonstrates the MapReduce programming model using Hadoop.

## Prerequisites

- Java JDK 8 or later
- Apache Hadoop (2.7.x or later)
- Maven (for dependency management)

## How to Run

### 1. Set up Hadoop Environment

Make sure Hadoop is properly installed and configured on your system. You can download it from [Apache Hadoop](https://hadoop.apache.org/releases.html).

### 2. Compile the WordCount program

```bash
# Compile the Java file
javac -classpath `hadoop classpath` WordCount.java

# Create a JAR file
jar cf wc.jar WordCount*.class
```

### 3. Run the MapReduce job

```bash
# Create input directory in HDFS
hadoop fs -mkdir -p /user/input

# Copy the input file to HDFS
hadoop fs -put input.txt /user/input

# Run the MapReduce job
hadoop jar wc.jar WordCount /user/input /user/output
```

### 4. View the results

```bash
# Check the output
hadoop fs -cat /user/output/part-r-00000
```

## Understanding the Code

The WordCount application consists of three main components:

1. **Mapper**: Reads input data and emits key-value pairs (word, 1) for each word in the input.
2. **Reducer**: Takes the output from the mapper and aggregates the counts for each word.
3. **Driver**: Sets up and configures the MapReduce job.

This is a fundamental example of Big Data processing where:
- Data is distributed across multiple nodes
- Processing happens in parallel (Map phase)
- Results are aggregated (Reduce phase)
- The framework handles fault tolerance and distribution

## For Local Testing

If you don't have a Hadoop cluster set up, you can run this in local mode by modifying the configuration to use the local filesystem instead of HDFS. 