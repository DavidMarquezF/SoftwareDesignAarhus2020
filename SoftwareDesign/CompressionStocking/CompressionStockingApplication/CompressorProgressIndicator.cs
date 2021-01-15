using System.Collections.Generic;
using CompressionStocking;

namespace CompressionStockingApplication
{
    public class CompressorProgressIndicator : ICompressionProgressListener
    {
        private List<IOnOffDevice> compressionIndicators = new List<IOnOffDevice>();
        private List<IOnOffDevice> decompressionIndicators = new List<IOnOffDevice>();

        public void StateChanged(CompressionStatus status)
        {
            switch (status)
            {
                case CompressionStatus.Compressing:
                    foreach (var c in compressionIndicators)
                        c.On();
                    break;
                case CompressionStatus.Decompressing:
                    foreach (var c in decompressionIndicators)
                        c.On();
                    break;
                case CompressionStatus.Compressed:
                    foreach (var c in compressionIndicators)
                        c.Off();
                    break;
                case CompressionStatus.Decompressed:
                    foreach (var c in decompressionIndicators)
                        c.Off();
                    break;
            }
        }

        public void AddCompressionIndicator(IOnOffDevice device)
        {
            compressionIndicators.Add(device);
        }


        public void AddDecompressionIndicator(IOnOffDevice device)
        {
            decompressionIndicators.Add(device);
        }
    }
}