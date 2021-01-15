using System.Threading.Tasks;
using System.Collections.Concurrent;
using System;
using System.Linq;

namespace Pipelines
{
    class StringCompression
    {
        private readonly int _nStrings;
        private readonly int _stringLength;
        private readonly string _charsInString;

        double _avgCompressionRatio = 0;

        public StringCompression(string charsInString, int nStrings, int stringLength)
        {
            _charsInString = charsInString;
            _nStrings = nStrings;
            _stringLength = stringLength;
        }


        public double Run()
        {
            int bufferSize = 10;
            var buffer1 = new BlockingCollection<string>(bufferSize);
            var buffer2 = new BlockingCollection<double>(bufferSize);
            var buffer3 = new BlockingCollection<double>(bufferSize);
            
            var f = new TaskFactory(TaskCreationOptions.LongRunning, TaskContinuationOptions.None);
            var stageGenerate = f.StartNew(() => Generate(_nStrings, _stringLength, buffer1));
            var stageCompress = f.StartNew(() => Compress(buffer1, buffer2));
            var stageCompress2 = f.StartNew(() => Compress(buffer1, buffer3));

            var stageUpdateCompresion = f.StartNew<double>(() => UpdateCompressionStats(_stringLength, new BlockingCollection<double>[] {buffer2, buffer3}));


            Task.WaitAll(stageGenerate, stageCompress, stageUpdateCompresion);

            return stageUpdateCompresion.Result;
        }


        private double UpdateCompressionStats(int strLen, BlockingCollection<double>[] inputs)
        {
            double avgCompressionRatio = 0;
            int i = 0;
            double len;
            while(!inputs.All(bc =>bc.IsCompleted)){
                BlockingCollection<double>.TakeFromAny(inputs, out len);
                avgCompressionRatio = ((i * avgCompressionRatio) + ((double)(len) / strLen)) / (i + 1);
                i++;
            }

            return avgCompressionRatio;
        }


        private void Compress(BlockingCollection<string> input, BlockingCollection<double> output)
        {
            try
            {
                foreach (var str in input.GetConsumingEnumerable())
                {
                    var result = "";
                    for (var i = 0; i < str.Length; i++)
                    {
                        var j = i;
                        result += str[i];
                        while ((j < str.Length) && (str[i] == str[j]))
                            j++;

                        if (j > i + 1)
                        {
                            result += (j - i);
                            i = j - 1;
                        }
                    }
                    output.Add(result.Length);
                }
            }
            finally
            {
                output.CompleteAdding();
            }


        }

        private void Generate(int numberOfStrings, int stringLength, BlockingCollection<string> output)
        {
            var random = new Random();

            try
            {
                for (var i = 0; i < numberOfStrings; i++)
                {
                    var result = new string(Enumerable.Repeat(_charsInString, stringLength).Select(s => s[random.Next(s.Length)]).ToArray());
                    output.Add(result);
                }

            }
            finally
            {
                output.CompleteAdding();
            }

        }
    }
}
