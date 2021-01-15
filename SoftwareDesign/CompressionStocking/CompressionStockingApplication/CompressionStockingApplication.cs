using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CompressionStocking;

namespace CompressionStockingApplication
{


    class CompressionStockingApplication
    {
        static void Main(string[] args)
        {
            //var comp = new StubCompressionCtrl(new Timer(), new StubPump());
            var comp = new StubLaceCompressionCtrl(new Timer(), new Lace());

            var progressListener = new CompressorProgressIndicator();
            progressListener.AddCompressionIndicator(new StubLed("GREEN"));
            progressListener.AddDecompressionIndicator(new StubLed("RED"));
            progressListener.AddCompressionIndicator(new StubVibrate());
            progressListener.AddDecompressionIndicator(new StubVibrate());

            comp.SetCompressionProgressListener(progressListener);
            var compressionStockingstocking = new StockingCtrl(comp, comp);
            ConsoleKeyInfo consoleKeyInfo;

            Console.WriteLine("Compression Stocking Control User Interface");
            Console.WriteLine("A:   Compress");
            Console.WriteLine("Z:   Decompress");
            Console.WriteLine("ESC: Terminate application");

            do
            {
                consoleKeyInfo = Console.ReadKey(true); // true = do not echo character
                if (consoleKeyInfo.Key == ConsoleKey.A) compressionStockingstocking.StartCompression();
                if (consoleKeyInfo.Key == ConsoleKey.Z) compressionStockingstocking.StartDecompression();

            } while (consoleKeyInfo.Key != ConsoleKey.Escape);
        }
    }
}
