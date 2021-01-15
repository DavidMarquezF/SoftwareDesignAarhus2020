using System;
using CompressionStocking;

namespace CompressionStockingApplication
{
    public class StubVibrate : IOnOffDevice
    {
        public void On()
        {
            Console.WriteLine("Vibrate Start");
        }

        public void Off()
        {
            Console.WriteLine("Vibrate Stop");
        }
    }
}