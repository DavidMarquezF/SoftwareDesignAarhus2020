using System;

namespace PortioningMachine
{
    public class Bin : IBin
    {
        private double _weight = 0;
        public double CurrentWeight => _weight;
        
        public void AddWeight(double weight)
        { 
            _weight += weight;
        }

        public void Empty()
        {
            this._weight = 0;
        }
    }
}