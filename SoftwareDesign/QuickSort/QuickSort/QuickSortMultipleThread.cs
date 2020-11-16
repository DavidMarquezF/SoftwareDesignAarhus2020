using System.Threading.Tasks;

namespace QuickSort
{
    public class QuickSortMultipleSingleThread
    {
        public static async Task SerialQuicksort(long[] elements, long left, long right)
        {
            long i = left, j = right;
            var pivot = elements[(left + right) / 2];

            while (i <= j)
            {
                while (elements[i].CompareTo(pivot) < 0) i++;
                while (elements[j].CompareTo(pivot) > 0) j--;

                if (i <= j)
                {
                    // Swap
                    var tmp = elements[i];
                    elements[i] = elements[j];
                    elements[j] = tmp;

                    i++;
                    j--;
                }
            }

            // Recursive calls
            if (left - right < 2000)
            {
                if (left < j) QuickSortSingleThread.SerialQuicksort(elements, left, j);
                if (i < right) QuickSortSingleThread.SerialQuicksort(elements, i, right);
            }
            else
            {
                var t1 = left < j ? SerialQuicksort(elements, left, j) : Task.CompletedTask;
                var t2 = i < right ? SerialQuicksort(elements, i, right) : Task.CompletedTask;
                await Task.WhenAll(t1, t2).ConfigureAwait(false);
            }

        }
    }
}
