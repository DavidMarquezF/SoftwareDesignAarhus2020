using System;
namespace GoFTemplateMethodStrategy
{
    public interface IGenerateStrategy
    {
        int[] Generate(int n, int max, int seed);
    }
    public class NormalArrayGenerator: IGenerateStrategy
    {
        public int[] Generate(int n, int max, int seed){
            var rnd = new Random(seed);
            int[] array = new int[n];
            for(int i = 0; i < n; i++)
                array[i] = rnd.Next(max);

            return array;
        }
    }
}