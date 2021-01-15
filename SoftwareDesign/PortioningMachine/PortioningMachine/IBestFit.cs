namespace PortioningMachine
{
    public interface IBestFit
    {
         int FindBestFitIndex(double newVal, double[] currVal, double desiredVal);
    }
}