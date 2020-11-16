using System.Collections.Generic;
using System;
namespace StatePattern.flashlight
{

    public abstract class FlashlightState
    {
        public abstract void HandlePower(Flashlight context);
        public virtual void HandleMode(Flashlight context) { }
        public virtual void OnEnter(Flashlight context) { }
        public virtual void OnExit(Flashlight context) { }

    }

    public enum FlashlightStates
    {
        ON,
        OFF,
        SOLID,
        FLASHING
    }

    public class OnState : FlashlightState
    {
        public override void HandlePower(Flashlight context)
        {
            OnExit(context);
            context.SetState(FlashlightStates.OFF);
            context.CurrState.OnEnter(context);
            context.LightOff();
        }

        public override void OnEnter(Flashlight context)
        {
            base.OnEnter(context);
            context.SetState(FlashlightStates.SOLID);
        }


    }

    public class SolidState : OnState
    {
        public override void HandleMode(Flashlight context)
        {
            context.SetState(FlashlightStates.FLASHING);
        }
    }
    public class FlashingState : OnState
    {
        public override void HandleMode(Flashlight context)
        {
            context.SetState(FlashlightStates.SOLID);
        }
    }

    public class OffState : FlashlightState
    {
        public override void HandlePower(Flashlight context)
        {
            OnExit(context);
            context.SetState(FlashlightStates.ON);
            context.CurrState.OnEnter(context);
            context.LightOn();
        }

    }
    public class Flashlight
    {
        private readonly Dictionary<FlashlightStates, FlashlightState> states = new Dictionary<FlashlightStates, FlashlightState>{
            {FlashlightStates.ON, new OnState()},
            {FlashlightStates.SOLID, new SolidState()},
            {FlashlightStates.FLASHING, new FlashingState()},
            {FlashlightStates.OFF, new OffState()}
        };
        private FlashlightState currState;
    public FlashlightState CurrState => currState;

        public Flashlight()
        {
            currState = states[FlashlightStates.OFF];
        }

        public void LightOn()
        {
            Console.WriteLine("Light On");
        }

        public void LightOff()
        {
            Console.WriteLine("Light Off");
        }

        public void Power()
        {
            currState.HandlePower(this);
        }

        public void SetState(FlashlightStates state)
        {
            currState = states[state];
        }

        
    }
}