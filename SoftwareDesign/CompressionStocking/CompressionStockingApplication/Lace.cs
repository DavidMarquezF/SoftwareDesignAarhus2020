using System;

namespace CompressionStockingApplication
{
    public class Lace
    {
        private Int32 clicks = 0;
        public Int32 Clicks => clicks;

        public void Tighten()
        {
            clicks++;
            System.Console.WriteLine("Tightening lace. Now at {0} clicks.", clicks);

        }
        public void Loose()
        {
            clicks++;
            System.Console.WriteLine("Loosening lace. Now at {0} clicks.", clicks);

        }

    }
}