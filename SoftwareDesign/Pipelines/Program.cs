using System.Diagnostics;
using System;

namespace Pipelines
{
    class Program
    {
        static void Main(string[] args)
        {
            Stopwatch stopwatch = new Stopwatch();
            //Time 00:00:04.4929199
            var s = new SequentialStringCompression("ABC", 1000, 25000);
            // 29.55 seconds
            //var s = new StringCompression("ABC", 1000, 25000);
            stopwatch.Start();
            var cRate = s.Run();
            stopwatch.Stop();
            Console.WriteLine("Time {0}", stopwatch.Elapsed);
            Console.WriteLine("Average compression rate: {0}", cRate);
        }
    }
}
