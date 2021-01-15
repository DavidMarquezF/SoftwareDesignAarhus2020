using System;
namespace Strategy
{
    public interface IInterrogationBehavior{
        void Interrogate();
    }
    public class Cop
    {
        public IInterrogationBehavior InterrogationBehavior{private get;set;}

        public void Interrogate(){
            InterrogationBehavior.Interrogate();
        }
    }

    public class BadCopBehaviod: IInterrogationBehavior{
        public void Interrogate(){
            Console.WriteLine("Tell suspect it's Moday so that he breaks. Then ask questions");
        }
    }   

    public class GoodCopBehavior: IInterrogationBehavior{
         public void Interrogate(){
            Console.WriteLine("Give suspect a cookie. Then ask questions");
        }
    }

    public class BadCopBehavior: IInterrogationBehavior{
         public void Interrogate(){
            Console.WriteLine("Just ask questions");
        }
    }
}