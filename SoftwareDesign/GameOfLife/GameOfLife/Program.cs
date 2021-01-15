using System.Diagnostics;
using System;

namespace GameOfLife
{
    class Program
    {
        static void Main(string[] args)
        {
            var iterations = 100;
            GameOfLife gol = new GameOfLife(1000);
            Stopwatch s = new Stopwatch();
            s.Start();
            //gol.RunSerial(iterations);
            s.Stop();
            Console.WriteLine("Ellapsed : {0}", s.Elapsed);
            s.Restart();
            //gol.RunParallel(iterations);
            s.Stop();
            Console.WriteLine("Ellapsed : {0}", s.Elapsed);
            s.Restart();
            gol.RunBarrier(iterations);
            s.Stop();
            Console.WriteLine("Ellapsed : {0}", s.Elapsed);
            s.Restart();
            
        }
    }
}
