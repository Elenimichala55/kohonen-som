import random

class Node:
    def __init__(self):
        # weight vector of length 16
        self.weight_vector = [random.random() for _ in range(16)]
        self.distance = 0.0
        self.neighbourhood = 0.0
        self.label_distance = float('inf')
        self.label = None

    def printable(self):
        weights = " ".join(str(w) for w in self.weight_vector)
        return f"and the weights of the node are {weights} and the label is {self.label}"

    def return_label_distance(self):
        return self.label_distance

    def set_label_distance(self, d):
        self.label_distance = d

    def return_label(self):
        return self.label

    def set_label(self, l):
        self.label = l

    def return_weight(self, pos):
        return self.weight_vector[pos]

    def set_weight(self, pos, value):
        self.weight_vector[pos] = value

    def set_distance(self, d):
        self.distance = d

    def return_distance(self):
        return self.distance

    def return_neighbourhood(self):
        return self.neighbourhood

    def set_neighbourhood(self, value):
        self.neighbourhood = value
