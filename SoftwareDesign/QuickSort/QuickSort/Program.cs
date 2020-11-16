using System;
using System.Diagnostics;

namespace QuickSort
{
    class Program
    {
        static void Main(string[] args)
        {
            const int numberOfElements = 20000000;
            DataGenerator dataGenerator = new DataGenerator();
            dataGenerator.Generate(numberOfElements);

            for (int i = 0; i < 3; i++)
            {
                long[] numbers = dataGenerator.GetNumbers();
                var stopwatch = new Stopwatch();

                Console.WriteLine("QuickSort By Recursive Method - run # {0}", i);
                stopwatch.Reset();
                stopwatch.Start();
                QuickSortSingleThread.SerialQuicksort(numbers, 0, numberOfElements - 1);
                stopwatch.Stop();

                var singleThreadRuntime = stopwatch.ElapsedMilliseconds;

                System.Console.WriteLine("Single thread calculation runtime: {0} ms", singleThreadRuntime);
                stopwatch.Reset();
                stopwatch.Start();
                QuickSortMultipleSingleThread.SerialQuicksort(numbers, 0, numberOfElements - 1).Wait();
                stopwatch.Stop();

                var multithreadRuntime = stopwatch.ElapsedMilliseconds;
                System.Console.WriteLine("MultiThread thread calculation runtime: {0} ms", multithreadRuntime);


            }
        }
    }
}
