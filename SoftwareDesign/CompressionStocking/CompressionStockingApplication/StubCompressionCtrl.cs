using System;
using CompressionStocking;

namespace CompressionStockingApplication
{
    public class StubCompressionCtrl : ICompressionCtrl, ICompressionStatus
    {
        private readonly IPump _pump;
        private readonly ITimer _timer;
        private CompressionStatus _status;
        private ICompressionProgressListener _progressListener;

        public CompressionStatus Status
        {
            get => _status;
            private set
            {
                _status = value;
                _progressListener?.StateChanged(value);
            }
        }

        public StubCompressionCtrl(ITimer timer, IPump pump)
        {
            _timer = timer ?? throw new ArgumentNullException(nameof(timer));
            _pump = pump ?? throw new ArgumentNullException(nameof(pump));
            _status = CompressionStatus.Decompressed;
        }

        public void Compress()
        {
            Status = CompressionStatus.Compressing;
            _pump.Run();
            _timer.Sleep(5);
            _pump.Stop();
            Status = CompressionStatus.Compressed;
        }

        public void Decompress()
        {
            Status = CompressionStatus.Decompressing;
            _pump.RunBackwards();
            _timer.Sleep(2);
            _pump.Stop();
            Status = CompressionStatus.Decompressed;
        }

        public void SetCompressionProgressListener(ICompressionProgressListener progressListener)
        {
            _progressListener = progressListener;
        }

    }
}