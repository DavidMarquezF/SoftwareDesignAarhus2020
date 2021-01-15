using System;

namespace TemplateMethod
{
    public abstract class CarProcess
    {
        public void Manufacture(){
            BuildChasis();
            Paint();
            BuildInterior();
        }

        protected abstract void BuildChasis();
        protected abstract void Paint();
        protected abstract void BuildInterior();
    }

    public class CarX : CarProcess
    {
        protected override void BuildChasis()
        {
            Console.WriteLine("Chasis with an X shape");
        }

        protected override void BuildInterior()
        {
            Console.WriteLine("Put Yellow chairs with an X embedded");
        }

        protected override void Paint()
        {
            Console.WriteLine("Paint the car yellow");
        }
    }
}