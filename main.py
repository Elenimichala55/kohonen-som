import numpy as np
from network import Network

def load_dataset(path):
    lines = [line.strip().split() for line in open(path)]
    return np.array(lines)

def normalize(data):
    max_vals = data.max(axis=0)
    min_vals = data.min(axis=0)
    return (data - min_vals) / (max_vals - min_vals)

def letter_to_onehot(letter):
    vec = np.zeros(26)
    idx = ord(letter) - ord('A')
    vec[idx] = 1.0
    return vec


def main():
    dimension = int(input("Enter map dimension: "))
    learning_rate0 = float(input("Enter learning rate (0.5–1): "))
    iterations = int(input("Enter number of iterations: "))

    train_raw = load_dataset("datasets/training.txt")
    test_raw  = load_dataset("datasets/test.txt")

    train_data = train_raw[1:, 1:].astype(int)
    test_data  = test_raw[1:, 1:].astype(int)

    # combine for normalization
    combined = np.vstack((train_data, test_data))
    combined_norm = normalize(combined)

    train_norm = combined_norm[:len(train_data)]
    test_norm  = combined_norm[len(train_data):]

    train_labels = train_raw[1:, 0]
    test_labels  = test_raw[1:, 0]

    train_outputs = np.array([letter_to_onehot(x) for x in train_labels])
    test_outputs  = np.array([letter_to_onehot(x) for x in test_labels])

    net = Network(dimension)

    s0 = dimension / 2
    s = s0
    lr = learning_rate0

    errors = []

    for epoch in range(iterations):
        print(f"\nEpoch {epoch}")

        s = s0 * np.exp(-epoch / (iterations / np.log10(s0)))
        lr = learning_rate0 * np.exp(-epoch / iterations)

        sumD = 0
        for pattern in train_norm:
            net.calculate_distance(pattern)
            w = net.find_winner()
            net.calculate_neighbourhood(w, s)
            net.update_weights(pattern, lr)
            sumD += net.winnerD(w)

        train_error = (sumD * sumD) / len(train_norm)
        print("Training error:", train_error)

        # testing error
        sumD_test = 0
        for pattern in test_norm:
            net.calculate_distance(pattern)
            w = net.find_winner()
            sumD_test += net.winnerD(w)
        test_error = (sumD_test * sumD_test) / len(test_norm)
        print("Testing error:", test_error)

        errors.append((epoch, train_error, test_error))

    # Assign labels
    for pattern, letter in zip(test_norm, test_labels):
        net.labeling(pattern, letter)

    net.print_labels()
    net.clustering_text()

    # save errors
    with open("results/results.txt", "w") as f:
        for row in errors:
            f.write("\t".join(map(str, row)) + "\n")

    print("\nEnd.")

if __name__ == "__main__":
    main()
