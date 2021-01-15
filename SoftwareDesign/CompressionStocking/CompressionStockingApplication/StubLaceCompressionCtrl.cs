using System;
using CompressionStocking;

namespace CompressionStockingApplication
{
    public class StubLaceCompressionCtrl : ICompressionCtrl, ICompressionStatus
    {
        private CompressionStatus _status;
        private ICompressionProgressListener _progressListener;
        private Lace _lace;
        private readonly ITimer _timer;

        const UInt32 COMPRESSION_TICKS = 40;
        const UInt32 DECOMPRESSION_TICKS = 40;
        const float TIME_CLICK_SECONDS = 100 / 1000f;
        public CompressionStatus Status
        {
            get => _status;
            private set
            {
                _status = value;
                _progressListener?.StateChanged(value);
            }
        }

        public StubLaceCompressionCtrl(ITimer timer, Lace lace)
        {
            _lace = lace ?? throw new ArgumentNullException(nameof(lace));
            _timer = timer ?? throw new ArgumentNullException(nameof(timer));


            _status = CompressionStatus.Decompressed;
        }

        public void Compress()
        {
            Status = CompressionStatus.Compressing;
            for (int i = 0; i < COMPRESSION_TICKS; i++)
            {
                _lace.Tighten();
                _timer.Sleep(TIME_CLICK_SECONDS);
            }

            Status = CompressionStatus.Compressed;
        }

        public void Decompress()
        {
            Status = CompressionStatus.Decompressing;
            for (int i = 0; i < DECOMPRESSION_TICKS; i++)
            {
                _lace.Loose();
                _timer.Sleep(TIME_CLICK_SECONDS);
            }
            Status = CompressionStatus.Decompressed;
        }

        public void SetCompressionProgressListener(ICompressionProgressListener progressListener)
        {
            _progressListener = progressListener;
        }
    }
}