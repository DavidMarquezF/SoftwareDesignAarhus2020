using System;
using CompressionStocking;

namespace CompressionStockingApplication
{
    public class StubPump : IPump
    {
        public void Run()
        {
            Console.WriteLine("Run...");
        }

        public void RunBackwards()
        {
            Console.WriteLine("Run Backwards...");
        }

        public void Stop()
        {
            Console.WriteLine("Stop...");
        }
    }
}