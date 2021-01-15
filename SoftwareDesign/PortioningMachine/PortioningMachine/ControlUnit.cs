using System;
using System.Collections.Generic;
using System.Linq;

namespace PortioningMachine
{
    public class ControlUnit : IWeightListener
    {
        private readonly List<IBin> _bins = new List<IBin>();
        private readonly IBestFit _bestFitAlgorithm;
        private readonly double _maxWeight;
        private readonly ILogger _logger;
        public ControlUnit(IBestFit bestFit, double maxWeight, ILogger logger)
        {
            _bestFitAlgorithm = bestFit;
            _maxWeight = maxWeight;
            _logger = logger;
        }

        public void AddBin(IBin bin)
        {
            _bins.Add(bin);
        }

        public void NewWeight(double weight)
        {   
            var bestFit = _bestFitAlgorithm.FindBestFitIndex(weight, _bins.Select(b => b.CurrentWeight).ToArray(), _maxWeight);
            _logger.WriteLine($"Item assigned to bin {bestFit}");
            var selectedBin = _bins[bestFit];
            selectedBin.AddWeight(weight);
            if(selectedBin.CurrentWeight >= _maxWeight){
                _logger.WriteLine($"Bin ${bestFit} emptied, Weight: {selectedBin.CurrentWeight}, Give away: {selectedBin.CurrentWeight - _maxWeight}");
                selectedBin.Empty();
            }
        }
    }
}