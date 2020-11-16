using System;

namespace QuickSort
{
    class DataGenerator
    {
        private long[] _numbers;

        public void Generate(int numberOfItems)
        {
            Random rand = new Random();
            _numbers = new long[numberOfItems];
            for (int i = 0; i < numberOfItems; i++)
            {
                _numbers[i] = rand.Next();
            }
        }

        public long[] GetNumbers()
        {
            long[] aCopy = new long[_numbers.Length];
            _numbers.CopyTo(aCopy, 0);
            return aCopy;
        }
    }

}
