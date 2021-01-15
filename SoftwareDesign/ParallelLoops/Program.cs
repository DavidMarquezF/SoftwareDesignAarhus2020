using System.Threading.Tasks;
using System.Diagnostics;
using System;
using System.Collections.Generic;
using System.Threading;
using System.Linq;

namespace ParallelLoops
{
    class Program
    {
        static void Main(string[] args)
        {
            Stopwatch stopwatch = new Stopwatch();
            const long N = 40000000;
            double[] A, B, C;
            A = new double[N];
            B = new double[N];
            C = new double[N];

            Random rand = new Random();
            for (int i = 0; i < N; i++)
            {
                A[i] = rand.Next();
                B[i] = rand.Next();
                C[i] = rand.Next();
            }




            for (int j = 0; j < 3; j++)
            {
                Console.WriteLine("Starts sequential for now.");
                double[] copyC = (double[])C.Clone();
                stopwatch.Start();
                for (int i = 0; i < N; i++) { copyC[i] = A[i] * B[i]; }
                stopwatch.Stop();
                Console.WriteLine("Sequential loop time in milliseconds: {0}", stopwatch.ElapsedMilliseconds);
                Console.WriteLine("Starts sequential for now.");
               
                stopwatch.Reset();
               
                copyC = (double[])C.Clone();
                Console.WriteLine("Starts MyParallelLoop for now.");
                stopwatch.Start();
                MyParallelLoop(0, (int)N, (i => copyC[i] = A[i] * B[i]));
                stopwatch.Stop();
                Console.WriteLine("MyParallelLoop loop time in milliseconds: {0}", stopwatch.ElapsedMilliseconds);
               
                stopwatch.Reset();
               
                copyC = (double[])C.Clone();
                Console.WriteLine("Starts Parallel.For for now.");
                stopwatch.Start();
                Parallel.For(0, (int)N, (i => copyC[i] = A[i] * B[i]));
                stopwatch.Stop();
                Console.WriteLine("Parallel.For loop time in milliseconds: {0}", stopwatch.ElapsedMilliseconds);
               
                stopwatch.Reset();
               
                copyC = (double[])C.Clone();
                ABC[] abc = new ABC[N];
                for(int i = 0; i < N; i++){
                    abc[i] = new ABC();
                    abc[i].A = A[i];
                    abc[i].B = B[i];
                    abc[i].C = C[i];
                }
                Console.WriteLine("Starts Parallel.ForEach for now.");
                stopwatch.Start();
                Parallel.ForEach(abc, i => i.C = i.A * i.B);
                stopwatch.Stop();
                Console.WriteLine("Parallel.ForEach loop time in milliseconds: {0}", stopwatch.ElapsedMilliseconds);

            }

        }

        class ABC{
            public double A{get;set;}
            public double B{get;set;}
            public double C{get;set;}
        }

        public static void MyParallelLoop(int inclusiveLowerBound, int exclusiveUpperBound, Action<int> body)
        {
            int size = exclusiveUpperBound - inclusiveLowerBound;
            int numProcs = Environment.ProcessorCount;
            int range = size / numProcs;
            var threads = new List<Thread>(numProcs);
            for (int p = 0; p < numProcs; p++)
            {
                int start = p * range + inclusiveLowerBound;
                int end = (p == numProcs - 1) ? exclusiveUpperBound : start + range;
                threads.Add(new Thread(() =>
                {
                    for (int i = start; i < end; i++) body(i);
                }));
            }

            foreach (var thread in threads) thread.Start();
            foreach (var thread in threads) thread.Join();
        }
    }
}
