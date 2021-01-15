namespace PortioningMachine
{
    public class Roundrobin : IBestFit
    {
        private int curr_index = 0;
        public int FindBestFitIndex(double newVal, double[] currVal, double desiredVal)
        {
            return curr_index++ % currVal.Length;
        }
    }
}