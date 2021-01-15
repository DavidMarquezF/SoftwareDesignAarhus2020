using System;
using PortioningMachine;
using PortioningMachine.ItemProviders;

namespace ItemProvidersApplication
{
    class Program
    {
        static void Main(string[] args)
        {
            var logger = new Logger();
            var cUnit = new ControlUnit(new Roundrobin(), 1000, logger);
            for (int i = 0; i < 10; i++)
            {
                cUnit.AddBin(new Bin());
            }

            var itemProvider = new ItemProvider(new GaussianDistribution(100, 20));
            var systemComp = new SystemControl(cUnit, itemProvider, logger);
            systemComp.Start();

            while (true) { }
        }
    }
}
