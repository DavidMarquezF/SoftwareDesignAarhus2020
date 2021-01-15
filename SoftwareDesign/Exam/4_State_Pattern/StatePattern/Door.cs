using System;
namespace StatePattern
{
    public abstract class DoorState
    {
        public virtual void handleOpen(Door door) { }
        public virtual void handleClose(Door door) { }
        public virtual void hanldeDetectOpened(Door door) { }

        public virtual void handleDetectClosed(Door door) { }

    }

    public class OpenState: DoorState{
        public override void handleClose(Door door)
        {
            door.PerformClose();
            door.SetState(new ClosingState());
        }
    }

    public class ClosingState: DoorState{
        public override void handleDetectClosed(Door door)
        {
            door.PerformStop();
            door.SetState(new ClosedState());
        }

        public override void handleOpen(Door door)
        {
            door.PerformOpen();
            door.SetState(new OpeningState());
        }
    }

    public class ClosedState: DoorState{

    }

    public class OpeningState: DoorState{

    }
    public class Door
    {
        private DoorState _state;

        public DoorState State => _state;

        public void SetState(DoorState state)
        {
            _state = state;
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
        public void Open()
        {
            _state.handleOpen(this);
        }

        public void Close()
        {
            _state.handleClose(this);
        }

        public void DetectClose()
        {
            _state.handleDetectClosed(this);
        }

        public void DetectOpen()
        {
            _state.hanldeDetectOpened(this);
        }
    }
}