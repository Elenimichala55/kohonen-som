# Kohonen Self Organising Map (Java Implementation)

This project implements a Kohonen Self-Organizing Map (SOM) in Java for the clustering and visualization
of handwritten English characters.

The system reads training and testing datasets of handwritten characters, allows the user to configure SOM parameters,
and produces error data across epochs.

## Features:
  SOM implemented fully in Java
  Supports configurable:
      - Grid size(eg., 50x50, 80x80, 120x120)
      - Learning rate
      - Number of epochs
  Trains on 2/3 of the dataset and tests on 1/3
  Computes and presents training vs testing error per epoch
  Demonstrates how grid size, learning rate, and epochs affect SOM performance

## How the SOM works:
Each neuron stores a weight vector representing features of handwritten characters
The SOM updates:
  - Best Matching Unit (BMU)
  - Neighbouring neurons (Gaussian neighbourhood)
    
Learning rate and neighbourhood radius decay over time
Larger grids -> higher chance of finding better BMUs
Training error is consistently higher than testing error (as expected)

## Example Results
Here are several experiment configurations:

- Learning Rate 0.9, Grid 50×50, Epochs 50

<img width="647" height="401" alt="image" src="https://github.com/user-attachments/assets/7c7a4081-d9fd-475e-b8ae-a07c545b3ec8" />

<img width="688" height="743" alt="image" src="https://github.com/user-attachments/assets/7ee7cf66-6429-4ecd-ab65-f53f8978c6b0" />


- Learning Rate 0.5, Grid 50×50, Epochs 50

<img width="649" height="395" alt="image" src="https://github.com/user-attachments/assets/1a1257a7-c50c-45ef-900c-64ca43df8197" />

<img width="677" height="722" alt="image" src="https://github.com/user-attachments/assets/24b04bf1-f0dd-4825-8441-2a3bde2cf3c0" />


- Learning Rate 0.7, Grid 80×80, Epochs 50

<img width="685" height="407" alt="image" src="https://github.com/user-attachments/assets/08869a4d-3849-48c2-83c6-f075308c9170" />

<img width="707" height="707" alt="image" src="https://github.com/user-attachments/assets/df1a54dc-d17e-40cd-b061-880457b7f3ed" />


- Learning Rate 0.7, Grid 120×120, Epochs 50

<img width="642" height="388" alt="image" src="https://github.com/user-attachments/assets/014ded55-83e7-45d5-965b-5f2f292e3c05" />

<img width="752" height="753" alt="image" src="https://github.com/user-attachments/assets/088ecadb-7b60-47d6-be4f-23483233b046" />

## Conclusion
Larger grids and 50+ epochs lead to better clustering accuracy, while optimal learning rate is 0.5-0.7.

## Project Structure

kohonen-som/

│

├── src/

│   ├── Network.java

│   └── Node.java

│

├── datasets/

│   ├── training.txt

│   └── test.txt

│
├── results/

│   ├── results.txt

│   └── clustering.txt

│

├── .gitignore

└── README.md


## How to run

### **1. Clone the repository**
      git clone https://github.com/Elenimichala55/kohonen-som.git
      cd kohonen-som

### **2. Compile the Java source files**
      javac src/*.java

### **3. Run the program**
      java -cp src Network

The program will ask for:
- Grid size
- Learning rate
- Number of epochs

## Java version requirements
- Java 11 or newer (OpenJDK or Oracle JDK)
- Terminal access (Linux, macOS, or Windows WSL)

## Author:
**Eleni Michala**  
MSc Applied Artificial Intelligence (University of Warwick)
BSc Computer Science (University of Cyprus)