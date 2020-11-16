using System;
using GoFTemplateMethodStrategy;

namespace GoFTemplateMethodStrategyApp
{
    class Program
    {
        static void Main(string[] args)
        {
            IGenerateStrategy strategy = new NormalArrayGenerator();

            var arr = strategy.Generate(10, 10, 10);
            ISuperSorter sorter = new BubbleSort();
            var watch = System.Diagnostics.Stopwatch.StartNew();
            sorter.Sort(arr);
            watch.Stop();
            Console.WriteLine("Time sorting: {0}", watch.Elapsed);

        }
    }
}
