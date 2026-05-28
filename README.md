# Spam Filter

Spam detection system developed in Java using the Naive Bayes algorithm for probabilistic text classification.

## Overview

The project analyzes messages and calculates the probability of them being spam based on the frequency and distribution of words in previously classified messages.

The classifier uses Bayesian probability to determine whether a message should be categorized as spam or legitimate.

## Technologies

* Java
* Maven
* CSV processing
* Object-Oriented Programming
* Naive Bayes

## Project Structure

```text
spam-filter/
├── src/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── util/
    ├── Main.java    
├── dataset.csv
├── pom.xml
└── README.md
```

## Bayesian Classification

The classification process is based on Bayes' theorem:

P(A | B) = (P(B | A) * P(A)) / P(B)

Where:

* A = message is spam
* B = words contained in the message

The system calculates:

* Probability of each word appearing in spam messages
* Probability of each word appearing in non-spam messages
* Final probability of the message belonging to each category

The category with the highest probability is selected as the result.

## Dataset Format

The dataset must follow the CSV structure below:

```csv
label,message
spam,"Win a free prize now!"
ham,"Meeting scheduled for tomorrow."
```

## Installation

Clone the repository:

```bash
git clone https://github.com/Leoocx/spam-filter.git
```

Access the project directory:

```bash
cd spam-filter
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn exec:java
```

## Example

Input:

```text
"Congratulations! You won a free iPhone!"
```

Output:

```text
SPAM detected with 98.2% probability
```


GitHub: https://github.com/Leoocx

## Authors

- [@Leoocx](https://github.com/Leoocx)
- [@gui-HS](https://github.com/gui-HS)
- [@eliezerdasilva](https://github.com/eliezerdasilva)

