using System;
using System.Threading;

namespace CompressionStocking
{
    public class Timer : ITimer
    {
        public void Sleep(float seconds)
        {
            Thread.Sleep((int)Math.Round(seconds * 1000));
        }
    }
}