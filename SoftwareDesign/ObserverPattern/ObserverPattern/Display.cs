using System;
using System.Collections.ObjectModel;
using System.Linq;

namespace ObserverPattern
{
    public class Display : IObserver<ReadOnlyCollection<UserData>>
    {
        public void Update(ReadOnlyCollection<UserData> data)
        {
            foreach (var item in data)
            {
              Console.WriteLine($"Stock: {item.StockData.Name}, Value: {item.StockData.Value}, Total Value: {item.StockData.Value * item.Amount}");  
            }

            Console.WriteLine($"TOTAL VALUE: {data.Select(a => a.Amount * a.StockData.Value).Sum()}"); 
        }
    }
}