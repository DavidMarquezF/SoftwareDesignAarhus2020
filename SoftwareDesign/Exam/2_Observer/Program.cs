using System;
using System.Collections.Generic;

namespace _2_Observer
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
        }
    }



    class Hospital
    {
        public CoronaEvent CoronaEvent { get; private set; }

    }

    class CoronaData
    {
        public int Deaths { get; private set; }
        public int Positives { get; private set; }

        public CoronaData(int deaths, int positives)
        {
            Deaths = deaths;
            Positives = positives;
        }
    }

    class CoronaEvent : IObservable<CoronaData>
    {
        private List<IObserver<CoronaData>> observers;
        public CoronaEvent()
        {
            observers = new List<IObserver<CoronaData>>();
        }
        public IDisposable Subscribe(IObserver<CoronaData> observer)
        {
            if (!observers.Contains(observer))
            {
                observers.Add(observer);
            }
            return new Unsubscriber<CoronaData>(observers, observer);
        }

        public void Notify(CoronaData coronaData)
        {
            foreach (var observer in observers)
                observer.OnNext(coronaData);
        }
    }

    class Unsubscriber<CoronaData> : IDisposable
    {
        private List<IObserver<CoronaData>> _observers;
        private IObserver<CoronaData> _observer;

        internal Unsubscriber(List<IObserver<CoronaData>> observers, IObserver<CoronaData> observer)
        {
            this._observers = observers;
            this._observer = observer;
        }

        public void Dispose()
        {
            if (_observers.Contains(_observer))
                _observers.Remove(_observer);
        }
    }

    class Particular : IObserver<CoronaData>
    {
        private CoronaData stateCorona;
        private IDisposable subscriptionCorona;

        public void StartWebpage(Hospital hospital)
        {
            Console.WriteLine("Do some stuff with the hospital");
            subscriptionCorona = hospital.CoronaEvent.Subscribe(this);
        }

        public void StopUpdating(){
            subscriptionCorona.Dispose();
        }
        public void OnCompleted()
        {
            this.stateCorona = null;
            Console.WriteLine("Close graph, etc.");
        }

        public void OnError(Exception error)
        {
            Console.WriteLine("Tell user there has been a problem");
        }

        public void OnNext(CoronaData value)
        {
            stateCorona = value;
            Console.WriteLine("Update graph");
        }
    }
}
