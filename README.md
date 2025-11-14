# kohonen-som
# Kohonen Self Organising Map (Java Implementation)

This project implements a Kohonen Self-Organizing Map (SOM) in Java for the clustering and visualization
of handwritten English characters.

The system reads training and testing datasets of handwritten characters, allows the user to configure SOM parameters,
and produces error data across epochs.

# Features:
  SOM implemented fully in Java
  Supports configurable:
      - Grid size(eg., 50x50, 80x80, 120x120)
      - Learning rate
      - Number of epochs
  Trains on 2/3 of the dataset and tests on 1/3
  Computes and presents training vs testing error per epoch
  Demonstrates how grid size, learning rate, and epochs affect SOM performance

# How the SOM works:
Each newuron stores a weight vector representing features of handwritten characters
The SOM updates:
  - Best Matching Unit (BMU)
  - Neighbouring neurons (Gaussian neighbourhood)
Learning rate and neighbourhood radius decay over time
Larger grids -> higher chance of finding better BMUs
Training error is consistently higher than testing error (as expected)

      <img width="665" height="390" alt="image" src="https://github.com/user-attachments/assets/aebdc265-a114-4019-a8a0-67e3a09dd6cb" />

