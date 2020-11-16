using System;
using System.Collections.Generic;

namespace GoFTemplateMethodStrategy
{

    public interface ITesterFactory{
        List<IGenerateStrategy> CreateGenerateStrategies();
        List<ISuperSorter> CreateSortingStrategies();
    }
    public class Tester
    {
        private List<IGenerateStrategy> generateStrategies;
        private List<ISuperSorter> sortStrategies;

        public Tester(ITesterFactory testFactory)
        {
            this.generateStrategies = testFactory.CreateGenerateStrategies();
            this.sortStrategies = testFactory.CreateSortingStrategies();
        }
        public void TestAllCombinations(int n, int seed)
        {
            foreach (var genStrat in generateStrategies)
            {
                var arr = genStrat.Generate(n, 10, seed);
                Console.WriteLine("New Generation-----------------");
                foreach (var sorter in sortStrategies)
                {
                    var watch = System.Diagnostics.Stopwatch.StartNew();
                    sorter.Sort(arr);
                    watch.Stop();
                    Console.WriteLine("Time sorting: {0}", watch.Elapsed);

                }
            }
        }

    }
}