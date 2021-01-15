using System.Collections.Generic;

namespace ObserverPattern
{
    public class Subject<T>
    {
        protected List<IObserver<T>> _observers;

        public Subject(){
            _observers = new List<IObserver<T>>();
        }
        public void Attatch(IObserver<T> observer){
            _observers.Add(observer);
        }

        public void Unattatch(IObserver<T> observer){
            _observers.Remove(observer);
        }

        protected void Notify(T data){
            _observers.ForEach(obs => obs.Update(data));
        }
    }
}