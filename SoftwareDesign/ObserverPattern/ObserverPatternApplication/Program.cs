using System;
using System.Collections.Generic;
using ObserverPattern;

namespace ObserverPatternApplication
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");


            var portfolio = new Portfolio();
            var display = new Display();

            portfolio.Attatch(display);

            List<Stock> stocks = new List<Stock>();


            for (int i = 0; i < 10; i++)
            {
                var stock = new Stock(new StockData($"TEST{i}", 10 + i));
                portfolio.AddStock(stock);
                stocks.Add(stock);
            }

            while (true)
            {
                var m = Console.ReadLine();
                var values = m.Split(":");
                if (values.Length == 2)
                {
                    var stock = stocks.Find(s => s.Name == values[0]);
                    if(Int32.TryParse(values[1], out int numValue)){}
                        stock.Value = numValue;
                }


            }

        }
    }
}
