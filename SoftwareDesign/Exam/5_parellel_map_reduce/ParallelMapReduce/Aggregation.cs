using System.Collections.Concurrent;
using System.Threading.Tasks;

namespace ParallelMapReduce
{
    public static class Aggregation
    {
        static double ParallelPartitionerPi()
        {
            double sum = 0.0;
            double step = 1.0 / (double)NUM_STEPS;
            object obj = new object();
            Parallel.ForEach(Partitioner.Create(0, NUM_STEPS),
            () => 0.0,
            (range, state, partial) =>
            {
                for (int i = range.Item1; i < range.Item2; i++)
                {
                    double x = (i + 0.5) * step;
                    partial += 4.0 / (1.0 + x * x);
                }
                return partial;
            }, partial => { lock (obj) sum += partial; });
            return step * sum;
        }
    }
}