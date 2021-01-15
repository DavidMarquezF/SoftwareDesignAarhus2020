using System;
using PortioningMachine.ItemProviders;
using PortioningMachine.SystemComponents;

namespace PortioningMachine
{
    public class SystemControl
    {
        private readonly IWeightListener _weightListener;
        private readonly IItemProvider _itemProvider;
        private readonly ILogger _logger;
        public SystemControl(IWeightListener weightListener, IItemProvider itemProvider, ILogger logger){
            _itemProvider = itemProvider;
            _weightListener = weightListener;
            _logger = logger;
            _itemProvider.ItemArrived += OnItemArrived;
        }

        public void Start()
        {
            _itemProvider.Go();
        }

        private void OnItemArrived(object o, IItem item)
        {
            _logger.WriteLine($"Item arrived=> id: {item.Id}, weight: {item.Weight}");
            _weightListener.NewWeight(item.Weight);
        }
    }
}