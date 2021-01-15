using System.Linq;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
using System.Collections.Generic;
namespace ObserverPattern
{
    public class Portfolio : Subject<ReadOnlyCollection<UserData>>, IObserver<StockData>
    {
        private Dictionary<string, UserData> stocks = new Dictionary<string, UserData>();
        public void Update(StockData data)
        {
            stocks[data.Name].StockData = data;
            Notify();
        }

        public void AddStock(Stock stock)
        {
            if (stocks.ContainsKey(stock.Name))
            {
                stocks[stock.Name].Amount++;
            }
            else
            {
                stock.Attatch(this);
                stocks[stock.Name] = new UserData()
                {
                    Amount = 1,
                    StockData = stock.Data
                };
            }
            Notify();
        }

        public void RemoveStock(Stock stock, int amount = 1)
        {
            if (stocks.ContainsKey(stock.Name))
            {
                stocks[stock.Name].Amount -= amount;
                if (stocks[stock.Name].Amount <= 0)
                {
                    stock.Unattatch(this);
                    stocks.Remove(stock.Name);
                }
            }
            Notify();
        }


        public void Notify(){
            Notify(stocks.Values.ToList().AsReadOnly());
        }
    }
}