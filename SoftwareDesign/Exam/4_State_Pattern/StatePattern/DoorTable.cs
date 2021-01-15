using System;

namespace StatePattern
{
    public class DoorTable
    {
        public enum DoorState{
            Open,
            Opening,
            Close,
            Closing
        }

        public enum Event{
            CompleteOpened,
            CompleteClosed,
            ButtonOpen,
            ButtonClose
        }

       private Action[,] fsm;
       private DoorState currState;
       public DoorTable(){
           currState = DoorState.Close;
           this.fsm = new Action[4,4]{
               {null, null, null, this.PerformClose}, //Opened
               {this.PerformStop, null, null, this.PerformClose},
               {null, null, this.PerformOpen, null},
               {null, this.PerformStop, this.PerformOpen, null}
           };
       }

        public void doEvent(Event ev){
            this.fsm[(int)this.currState, (int)ev].Invoke();
        }
        public void PerformOpen(){
            Console.WriteLine("Rotate motor to the right");
        }

        public void PerformClose(){
            Console.WriteLine("Rotate motor to the left");
        }

        public void PerformStop(){
            Console.WriteLine("Stop motor");
        }
    }
}