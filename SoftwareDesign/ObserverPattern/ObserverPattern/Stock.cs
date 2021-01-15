using System;

namespace ObserverPattern
{
    public class Stock : Subject<StockData>
    {
        private StockData stock;
        private Random rnd = new Random();
        public StockData Data
        {
            get => stock;
            private set => stock = value;
        }

        public Stock(StockData data)
        {
            this.Data = data;
            var timer = new System.Timers.Timer();
            timer.Interval = 2000;
            timer.Elapsed += OnTimedEvent;
            timer.Enabled = true;
        }
        private void OnTimedEvent(Object source, System.Timers.ElapsedEventArgs e)
        {
            Value = Value + rnd.Next(-5, 5);
        }

        public string Name => Data.Name;

        public int Value
        {
            get => Data.Value;
            set
            {
                Data = new StockData(Data.Name, value);
                Notify(Data);
            }
        }
    }
}