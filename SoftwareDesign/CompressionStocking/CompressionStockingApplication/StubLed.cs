using System;
using CompressionStocking;

namespace CompressionStockingApplication
{
    public class StubLed : IOnOffDevice
    {
        private string _color;
        public StubLed(string color){
            _color = color;
        }
        public void On()
        {
            Console.WriteLine("LED "+_color+" ON");
        }

        public void Off()
        {
            Console.WriteLine("LED "+_color+" OFF");
        }
    }
}