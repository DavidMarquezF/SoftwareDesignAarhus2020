using System;

namespace _3_Factories
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
        }
    }

    interface IGlass{
        void Drink();
        void Fill(IBeer beer);
    }

    interface IMat{
        void PutGlass(IGlass glass);
    }

    interface IBeer{
        string Pour();
    }

    class TuborgBeer : IBeer
    {
        public string Pour()
        {
            return "liquid";
        }
    }

    class TuborgMat : IMat
    {
        private IGlass glass1; 
        public void PutGlass(IGlass glass)
        {
            Console.WriteLine("Putting glass");
            glass1 = glass;
        }
    }

    class TuborgGlass : IGlass
    {
        private string drink;
        public void Drink()
        {
            Console.WriteLine("Drinking beer %s", drink);
        }

        public void Fill(IBeer beer)
        {
            drink = beer.Pour();
        }
    }

    interface IBeerSetFactory{
        IGlass CreateGlass();
        IBeer CreateBeer();
        IMat CreateMat();
    }

    class TuborgSetFactory : IBeerSetFactory
    {
        public IBeer CreateBeer()
        {
            return new TuborgBeer();
        }

        public IGlass CreateGlass()
        {
            return new TuborgGlass();
        }

        public IMat CreateMat()
        {
            return new TuborgMat();
        }
    }

    abstract class Client{

        public void PrepareAndDrink(){
            var factory = CreateBeerSet();
            var beer = factory.CreateBeer();
            var glass = factory.CreateGlass();
            var mat = factory.CreateMat();
            
            glass.Fill(beer);
            mat.PutGlass(glass);
            glass.Drink();
        }

        protected abstract IBeerSetFactory CreateBeerSet();
    }

    class StudentHouseOrder : Client
    {
        protected override IBeerSetFactory CreateBeerSet()
        {
            return new TuborgSetFactory();
        }
    }


}
