using System;

namespace StatePattern.flashlight
{
    public class FlashlightSwitch
    {
        private FlashlightStates currState;
        public void Power()
        {
            switch (currState)
            {
                case FlashlightStates.ON:
                    LightOff();
                    currState = FlashlightStates.OFF;
                    break;
                case FlashlightStates.OFF:
                    LightOn();
                    currState = FlashlightStates.ON;
                    break;
            }
        }

        private void LightOn()
        {
            Console.WriteLine("Light On");
        }

        private void LightOff()
        {
            Console.WriteLine("Light Off");
        }

    }
}