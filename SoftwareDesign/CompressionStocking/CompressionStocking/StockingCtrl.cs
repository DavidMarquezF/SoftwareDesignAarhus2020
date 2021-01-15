using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CompressionStocking
{
    public interface ICompressionStocking
    {
        void StartCompression();
        void StartDecompression();
    }


    public class StockingCtrl : ICompressionStocking
    {
        private readonly ICompressionCtrl _compressionCtrl;
        private readonly ICompressionStatus _compressionStatus;

        public StockingCtrl(ICompressionCtrl compressionCtrl, ICompressionStatus compressionStatus)
        {
            _compressionCtrl = compressionCtrl;
            _compressionStatus = compressionStatus;
        }


        // From IBtnHandler
        public void StartCompression()
        {
            Console.WriteLine("Start Pushed");
            if(_compressionStatus.Status == CompressionStatus.Decompressed)
                _compressionCtrl.Compress();
        }

        public void StartDecompression()
        {
            Console.WriteLine("Stop Pushed");
            if(_compressionStatus.Status == CompressionStatus.Compressed)
                _compressionCtrl.Decompress();
        }

    }
}
