import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def load_data(filename):
    df = pd.read_csv("CutoffValues.csv")
    return pd.read_csv(filename)

def plotMergeSortBaseCase(df, output_file=None):
    fig, axes = plt.subplots(2, 1, figsize=(10, 12))

    ax1 = axes[0]
    algorithms = df['algorithm'].unique()
    for algorithm in algorithms:
        median_times = df[df['algorithm'] == algorithm].groupby('n')['time'].median()
        ax1.plot(median_times.index, median_times.values, label=f'{algorithm} (Empirical)', marker='o')

    n_values = np.array(df['n'].unique(), dtype=float)
    theoretical = n_values * np.log(n_values)
    scaling_factor = df.groupby('n')['time'].median().values[0] / theoretical[0]
    theoretical_scaled = theoretical * scaling_factor
    ax1.plot(n_values, theoretical_scaled, label='n log n (theoretical, scaled)', linestyle='--', color='red')
    ax1.set_xlabel('n')
    ax1.set_ylabel('Median Time (s)')
    ax1.set_title("Empirical Median Time vs n for MergeSort Base Case (By DataType)")
    ax1.legend()
    ax1.grid(True)

    ax2 = axes[1]
    markers = ['o', 's', '^', 'D', 'v', 'P', '*']
    colors = ['blue', 'red', 'green', 'purple', 'orange', 'cyan', 'magenta']

    for idx, algorithm in enumerate(algorithms):
        df_algo = df[df['algorithm'] == algorithm]
        median_df = df_algo.groupby('n').agg({'time': 'median', 'comparisons': 'median'}).reset_index()
        ax2.scatter(median_df['comparisons'], median_df['time'],
                    label=algorithm, marker=markers[idx % len(markers)], color=colors[idx % len(colors)])
        coeffs = np.polyfit(median_df['comparisons'], median_df['time'], 1)
        poly_eqn = np.poly1d(coeffs)
        x_vals = np.linspace(median_df['comparisons'].min(), median_df['comparisons'].max(), 100)
        ax2.plot(x_vals, poly_eqn(x_vals), linestyle='--', color=colors[idx % len(colors)], alpha=0.7)

    ax2.set_xlabel("Median Comparisons")
    ax2.set_ylabel("Median Time (s)")
    ax2.set_title("Empirical Running Time vs. Number of Comparisons (By DataType)")
    ax2.legend(title="Algorithm")
    ax2.grid(True)

    plt.tight_layout()
    plt.savefig(f"MergeSortBaseCasePic.png")


# Generate plots for cutoff values
def plot_cutoff_values(df, output_file=None):
    """Plot median time vs cutoff for various algorithms."""
    df["time"] = pd.to_numeric(df["time"], errors="coerce")
    plt.figure(figsize=(10, 6))
    for algorithm in df['algorithm'].unique():
        subset = df[df['algorithm'] == algorithm].groupby('cutoff')['time'].median()
        plt.plot(subset.index, subset.values, label=f'Algorithm {algorithm}', marker='o')
    plt.xlabel('Cutoff')
    plt.ylabel('Median Time')
    title = "Median Time vs Cutoff for Various Algorithms"
    plt.title(title)
    plt.legend()
    plt.grid(True)
    # plt.savefig(output_file, dpi=300, bbox_inches="tight")
    plt.savefig(f"{title}.png")


def plot_level_biosort_subplots(df, output_file=None):
    """Create two subplots stacked vertically:
    1. Degree of presortedness vs. average comparisons
    2. Degree of presortedness vs. median time
    """
    df = df.sort_values(by=["degree of presortedness"])

    #making grps of label and degree of presortedness
    avg_comparisons_df = (
        df.groupby(["algorithm", "degree of presortedness"])["comparisons"]
        .mean()
        .reset_index()
    )
    median_time_df = (
        df.groupby(["algorithm", "degree of presortedness"])["time"]
        .median()
        .reset_index()
    )
    
    fig, axes = plt.subplots(2, 1, figsize=(10, 12)) ## (2, 1) vertical (1, 2) for side by side

    for algorithm, group in avg_comparisons_df.groupby("algorithm"):
        axes[0].plot(
            group["degree of presortedness"],
            group["comparisons"],
            marker="o",
            label=algorithm.replace("_", " ").title(),
        )
    axes[0].set_xlabel("Degree of Presortedness")
    axes[0].set_ylabel("Average Comparisons")
    axes[0].set_title("Average Comparisons vs. Degree of Presortedness")
    axes[0].set_xticks([0, 1, 2, 3])
    axes[0].grid(True)
    axes[0].legend()

    for algorithm, group in median_time_df.groupby("algorithm"):
        axes[1].plot(
            group["degree of presortedness"],
            group["time"],
            marker="o",
            label=algorithm.replace("_", " ").title(),
        )
    axes[1].set_xlabel("Degree of Presortedness")
    axes[1].set_ylabel("Median Time")
    axes[1].set_title("Median Time vs. Degree of Presortedness")
    axes[1].set_xticks([0, 1, 2, 3])
    axes[1].grid(True)
    axes[1].legend()
    plt.savefig("presortedness.png")


def plot_level_biosort_by_cutoff(df, output_file=None):
    """Plot algorithm performance with cutoff on the x-axis and median time on the y-axis,
    showing only four lines for level and bionomial sort (adaptive and non-adaptive)."""
    df = df.sort_values(by=["cutoff"])
    median_df = df.groupby(["algorithm", "cutoff"])["time"].median().reset_index()

    plt.figure(figsize=(10, 6))
    for algorithm, group in median_df.groupby("algorithm"):
        plt.plot(
            group["cutoff"],
            group["time"],
            marker="o",
            label=algorithm.replace("_", " ").title(),
        )

    plt.xlabel("Cutoff")
    plt.ylabel("Median Time")
    title = "Level and Bionomial Sort Performance (Median Time)"
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}.png")


# Horse race plot
def plot_horse_race(df, output_file=None):
    median_times = df.groupby(["n", "algorithm"])["time"].median().reset_index()
    algorithms = median_times["algorithm"].unique()

    plt.figure(figsize=(12, 8))
    for algo in algorithms:
        subset = median_times[median_times["algorithm"] == algo]
        plt.plot(subset["n"], subset["time"], marker="o", label=algo)
    plt.xlabel("n")
    plt.ylabel("Median Time")
    title = "Algorithm Performance Comparison"
    plt.title(title)
    plt.legend(title="Algorithm")
    plt.grid(True)
    plt.savefig(f"{title}.png")


def plot_presortedness(df, output_file = None):

    df["cutoff"] = df["cutoff"]
    df["time"] = df["time"]
    median_times = df.groupby(["algorithm", "degree of presortedness", "cutoff"])["time"].median().reset_index()

    group_1 = ["binomialSort Presort INTEGERS Adaptive", "binomialSort Presort INTEGERS NonAdaptive"]
    group_2 = ["levelSort Presort INTEGERS NonAdaptive", "levelSort Presort INTEGERS Adaptive"]

    fig, axes = plt.subplots(1, 2, figsize=(16, 8), sharey=True)

    # group 1
    ax = axes[0]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_1)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["time"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_ylabel("Median Time")
    ax.set_title("BinomialSort Algorithms")
    ax.legend()
    ax.grid(True)

    # group 2
    ax = axes[1]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_2)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["time"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_title("LevelSort Algorithms")
    ax.legend()
    ax.grid(True)

    plt.tight_layout()
    plt.savefig("PresortednessLevelAndBioSort.png")

def plot_presortednessComparisons(df, output_file = None):
    df["cutoff"] = df["cutoff"]
    df["time"] = df["time"]
    median_times = df.groupby(["algorithm", "degree of presortedness", "cutoff"])["comparisons"].median().reset_index()

    group_1 = ["binomialSort Presort INTEGERS Adaptive", "binomialSort Presort INTEGERS NonAdaptive"]
    group_2 = ["levelSort Presort INTEGERS NonAdaptive", "levelSort Presort INTEGERS Adaptive"]

    fig, axes = plt.subplots(1, 2, figsize=(16, 8), sharey=True)

    # group 1
    ax = axes[0]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_1)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["comparisons"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_ylabel("Avg Comparisons")
    ax.set_title("BinomialSort Algorithms Comparisons")
    ax.legend()
    ax.grid(True)

    # group 2
    ax = axes[1]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_2)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["comparisons"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_title("LevelSort Algorithms Comparisons")
    ax.legend()
    ax.grid(True)

    plt.tight_layout()
    plt.savefig("PresortednessLevelAndBioSortComparisons.png")


# Parallel Cutoff plots
def load_parallel_data_cutoff(filename):
    df = pd.read_csv(filename)
    # Replace commas with dots and convert to float
    df['time'] = df['time'].apply(lambda x: float(str(x).replace(',', '.')))
    df['variation'] = df['variation'].apply(lambda x: float(str(x).replace(',', '.')))
    return df

def plot_all_cutoff_parallel(file_list, labels, output_file='parallel_cutoff_plots.png'):
    n_plots = len(file_list)
    fig, axes = plt.subplots(1, n_plots, figsize=(6 * n_plots + 6, 3), constrained_layout=True)

    if n_plots == 1:
        axes = [axes]

    for ax, filename, label in zip(axes, file_list, labels):
        df = load_parallel_data_cutoff(filename)
        df.sort_values('cutoff', inplace=True)

        ax.errorbar(df['cutoff'], df['time'], yerr=df['variation'],
                    fmt='-o', capsize=5, label=f'Array size: {label}')

        # find and mark the best cutoff (minimum time)
        best_idx = df['time'].idxmin()
        best_cutoff = df.loc[best_idx, 'cutoff']
        best_time = df.loc[best_idx, 'time']
        ax.plot(best_cutoff, best_time, 'ks', markersize=10)
        ax.text(best_cutoff, best_time, f' {best_cutoff}', verticalalignment='bottom', fontsize=9)

        ax.set_xlabel('Cutoff')
        ax.set_ylabel('Time (ns)')
        ax.set_title(f'Array Size: {label}')
        ax.legend()
        ax.grid(True)

    plt.savefig(output_file)
    plt.show()


    # Parallel Scaling plots
def load_parallel_data(filename):
    df = pd.read_csv(filename)
    # 'nanoseconds' and 'variance' from comma decimals to floats.
    df['nanoseconds'] = df['nanoseconds'].apply(lambda x: float(str(x).replace(',', '.')))
    df['variance'] = df['variance'].apply(lambda x: float(str(x).replace(',', '.')))
    # assigning thread count as 1, else convert normally.
    df['threads'] = df.apply(lambda row: 1 if row['name'] == 'Arrays.parallelSort'
    else pd.to_numeric(row['threads'], errors='coerce'), axis=1)
    return df

def plot_all_parallel_scaling(file_list, labels, output_file="parallel_thread_scaling_all.png"):
    n_plots = len(file_list)
    fig, axes = plt.subplots(1, n_plots, figsize=(10, 6), constrained_layout=True)

    if n_plots == 1:
        axes = [axes]
    for ax, filename, label in zip(axes, file_list, labels):
        df = load_parallel_data(filename)

        max_threads = df[df['name'] != 'Arrays.parallelSort']['threads'].max()
        algorithms = df['name'].unique()
        for alg in algorithms:
            sub = df[df['name'] == alg].copy()
            if alg == 'Arrays.parallelSort':
                # Use the baseline value (assumes one row)
                baseline_value = sub['nanoseconds'].iloc[0]
                ax.hlines(baseline_value, xmin=1, xmax=max_threads,
                          colors='black', linestyles='dotted', label=alg)
            else:
                sub = sub.dropna(subset=['threads'])
                sub = sub.sort_values('threads')
                ax.plot(sub['threads'], sub['nanoseconds'], marker='o', label=alg)
        ax.set_xlabel('Number of Threads')
        ax.set_ylabel('Execution Time (ns)')
        ax.set_title(f'Array Size: {label}')
        ax.grid(True)
        ax.legend(title='Algorithm')
    plt.savefig(output_file)
    plt.show()

if __name__ == "__main__":
    
    df_comp = pd.read_csv("mergeSortBaseCase.csv")
    plotMergeSortBaseCase(df_comp)

    df_cutoff = load_data('CutoffValues.csv')
    plot_cutoff_values(df_cutoff)

    df_lvlbio_presort = load_data('LevelAndBioSort.csv')
    plot_level_biosort_subplots(df_lvlbio_presort)

    df_lvlbio_presort = load_data('LevelAndBioSort.csv')
    plot_presortedness(df_lvlbio_presort)

    df_lvlbio_presort = load_data('LevelAndBioSort.csv')
    plot_presortednessComparisons(df_lvlbio_presort)

    df_lvlbio_cutoff= load_data("LevelAndBioSort.csv") #useless now that we have the other one. Convert to c based on presortedness
    plot_level_biosort_by_cutoff(df_lvlbio_cutoff)

    df_horse_race = load_data('HorseRaceResults.csv')
    df_horse_race = df_horse_race.replace(
        {"recursiveMergeSort HorseRace INTEGERS Arrays.sort": "Arrays.sort"}
    )
    plot_horse_race(df_horse_race)

    file_list = [
        'Parallel_Thread_Scaling_FirstTest ThreadScaling threadScaling100K.csv',
        'Parallel_Thread_Scaling_FirstTest ThreadScaling threadScaling1M.csv',
        'Parallel_Thread_Scaling_FirstTest ThreadScaling threadScaling10M.csv'
    ]
    labels = ['100k', '1M', '10M']
    plot_all_parallel_scaling(file_list, labels)

    file_list = ['Parallel_testing_FirstTest parallelTesting ParallelCutoff100k.csv', 'Parallel_testing_FirstTest parallelTesting ParallelCutoff1M.csv', 'Parallel_testing_FirstTest parallelTesting ParallelCutoff10M.csv']
    labels = ['100k', '1M', '10M']
    plot_all_cutoff_parallel(file_list, labels)