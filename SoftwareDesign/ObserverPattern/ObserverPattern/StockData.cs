namespace ObserverPattern
{
    public struct StockData
    {
        public string Name{get;}
        public int Value{get;}
        public StockData(string name, int value){
            Name = name;
            Value = value;
        }
    }
}