namespace PortioningMachine
{
    public interface IBin
    {
        double CurrentWeight { get; }
        void AddWeight(double weight);
        void Empty();
    }
}