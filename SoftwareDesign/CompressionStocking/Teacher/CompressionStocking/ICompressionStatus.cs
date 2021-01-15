namespace CompressionStocking
{
    public enum CompressionStatus
    {
        Compressed,
        Decompressed,
        Compressing,
        Decompressing
    }
    public interface ICompressionStatus
    {
        CompressionStatus Status { get; }
    }
}