Overview

This project processes text data for sentiment analysis by tokenizing it and converting it into numerical vectors for machine learning.

Features

Tokenization: Splits text into tokens.

Feature Selection: Filters words using frequency and Information Gain.

Vectorization: Converts text into numerical vectors.

Data Processing: Reads positive and negative reviews.

Exporting: Saves vectors and labels to files.

├── CsvFileReader.java      # Reads and processes data

├── Example.java            # Represents a text example

├── Main.java               # Main entry point

├── RegexTokenizer.java     # Tokenizer for text

├── SortByFrequency.java    # Sorts words by frequency

├── SortByIG.java           # Sorts words by Information Gain

├── VectorExporter.java     # Exports vectors and labels

├── Vocabulary.java         # Creates vocabulary and vectors

├── Word.java               # Word representation


How It Works

Read Data: Reads labeled reviews from folders.

Tokenization: Splits text into tokens.

Feature Selection: Filters words using frequency and Information Gain.

Vectorization: Converts text into feature vectors.

Exporting Data: Saves processed data to files.

Usage:

Input Data:


Store training and test reviews in pos and neg folders.

Update dataset paths in Main.java.


Output Files:


train_vectors.txt: Training feature vectors.

train_labels.txt: Training labels.

features.txt: Selected vocabulary.

test_vectors.txt: Testing feature vectors.

test_labels.txt: Testing labels.
