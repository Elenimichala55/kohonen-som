import math
from node import Node

class Network:
    def __init__(self, dimension):
        self.dimension = dimension
        self.Map = [[Node() for _ in range(dimension)] for _ in range(dimension)]

    def clustering_text(self):
        with open("results/clustering.txt", "w") as f:
            node_id = 0
            for i in range(self.dimension):
                for j in range(self.dimension):
                    f.write(
                        f"Position = {i},{j} and Node ID = {node_id} {self.Map[i][j].printable()}\n"
                    )
                    node_id += 1

    def print_labels(self):
        for i in range(self.dimension):
            row = [self.Map[i][j].return_label() for j in range(self.dimension)]
            print(" ".join(label if label else "-" for label in row))

    def labeling(self, x, letter):
        for i in range(self.dimension):
            for j in range(self.dimension):
                # compute distance from x to node weights
                d = sum((x[k] - self.Map[i][j].return_weight(k))**2 
                        for k in range(len(x)))

                if d < self.Map[i][j].return_label_distance():
                    self.Map[i][j].set_label_distance(d)
                    self.Map[i][j].set_label(letter)

    def winnerD(self, winner):
        i, j = winner
        return self.Map[i][j].return_distance()

    def calculate_distance(self, x):
        for i in range(self.dimension):
            for j in range(self.dimension):
                d = sum((x[k] - self.Map[i][j].return_weight(k))**2
                        for k in range(len(x)))
                self.Map[i][j].set_distance(d)

    def calculate_neighbourhood(self, winner, s):
        winner_i, winner_j = winner
        for i in range(self.dimension):
            for j in range(self.dimension):
                dij = (winner_i - i)**2 + (winner_j - j)**2
                h = math.exp(-(dij / (2 * s * s)))
                self.Map[i][j].set_neighbourhood(h)

    def find_winner(self):
        best = float('inf')
        best_pos = (0, 0)

        for i in range(self.dimension):
            for j in range(self.dimension):
                dist = self.Map[i][j].return_distance()
                if dist < best:
                    best = dist
                    best_pos = (i, j)

        return best_pos

    def update_weights(self, x, learning_rate):
        for i in range(self.dimension):
            for j in range(self.dimension):
                h = self.Map[i][j].return_neighbourhood()
                for k in range(len(x)):
                    w = self.Map[i][j].return_weight(k)
                    new_w = w + learning_rate * h * (x[k] - w)
                    self.Map[i][j].set_weight(k, new_w)
