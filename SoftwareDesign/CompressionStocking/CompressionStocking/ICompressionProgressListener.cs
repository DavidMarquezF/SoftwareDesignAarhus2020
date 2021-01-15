namespace CompressionStocking
{
    public interface ICompressionProgressListener
    {
         void StateChanged(CompressionStatus status);
    }
}